package com.wgf.mysqlxa;

import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.jdbc.MysqlXAConnection;
import com.mysql.cj.jdbc.MysqlXid;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author: wgf
 * @create: 2020-05-25 17:17
 * @description: XA两阶段提交测试
 * <p>
 * MySql XA分支事务流程
 * <p>
 * # 开启xa
 * xa start 'xa_test';
 * <p>
 * # DML操作
 * <p>
 * # 结束xa
 * xa end 'xa_test';      将事务状态改为 DIEL
 * <p>
 * # 第一阶段提交
 * xa prepare 'xa_test';  将事务状态改为 PREPARE
 * <p>
 * # 第二阶段提交
 * xa commit 'xa_test';
 * <p>
 * # xa事务回滚
 * xa rollback 'xa_test';
 * <p>
 * # 在提交、回滚前查询事务状态
 * xa recover;
 *
 *
 * 分布式事务实现思路
 * TM       全局事务管理器
 * RM       资源管理器
 *
 * 1.首先是由TM开启一个全局事务 生成全局事务标识 gtrid
 * 2.各自RM由gtrid生成自己的事务id
 * 3.各自RM将XA事务的prepare结果上报给TM
 * 4.TM根据上报结果决定是进行全局事务提交或者回滚
 **/
public class TMTest {
    //true表示打印XA语句,，用于调试
    private static final boolean logXaCommands = true;

    private Connection userConn;
    private Connection orderConn;
    private Connection productConn;

    // 分支事务id
    private Xid userXid;
    private Xid orderXid;
    private Xid productXid;

    // 资源管理器 RM
    private XAResource userRm;
    private XAResource orderRm;
    private XAResource productRm;

    // 是否回滚事务
    private boolean isRollBack = false;


    /**
     * 模拟全局事务管理器 GlobalTransactionManager
     *
     * 正常情况下回扣减库存，扣减用户余额，创建订单
     * 异常情况整个下单所有XA分支事务回滚
     */
    @Test
    public void GTM() {
        // APP请求TM执行一个分布式事务，TM生成全局事务id
        byte[]               gtrid    = "g001".getBytes();
        int                  formatId = 1;
        Map<Xid, XAResource> xaMap    = new LinkedHashMap<>();

        // TM生成userRm上的事务分支id
        byte[] userBqual = "u001".getBytes();
        userXid = new MysqlXid(gtrid, userBqual, formatId);
        xaMap.put(userXid, userRm);

        // TM生成orderRm上的事务分支id
        byte[] orderBqual = "o001".getBytes();
        orderXid = new MysqlXid(gtrid, orderBqual, formatId);
        xaMap.put(orderXid, orderRm);

        // TM生成productRm上的事务分支id
        byte[] productBqual = "p001".getBytes();
        productXid = new MysqlXid(gtrid, productBqual, formatId);
        xaMap.put(productXid, productRm);

        // 是否事务回滚
        isRollBack = true;

        // 模拟TM RM 预备阶段通信
        boolean result = payOrder();

        // 业务异常所有事务回滚
        if (!result) {
            rollback(xaMap);
            return;
        }

        // 所有分支事务已准备好,开始第一阶段XA提交
        boolean tmFlag = prepare(xaMap);

        // 第一阶段是否所有分支事务全部准备完毕
        if (tmFlag) {
            // 第二阶段事务提交
            commit(xaMap);
        } else {
            // 第一阶段有分支事务没有准备完毕，所有分支事务全部回滚
            rollback(xaMap);
        }

    }


    /**
     * 模拟下单
     *
     * @return
     */
    private boolean payOrder() {
        boolean isSuccess = true;
        int     userId    = 1;
        int     productId = 1;
        int     num       = 1;

        /**
         * 模拟微服务接口调用
         */
        try {
            BigDecimal price      = deductionsInventory(productId, num);
            BigDecimal totalPrice = deductionsAmount(productId, price, userId);
            createOrder(userId, productId, num, price, totalPrice);
        } catch (Exception e) {
            isSuccess = false;
        }

        return isSuccess;
    }


    /**
     * 用户资源服务器
     *
     * @return
     */
    @Before
    public void userRm() throws SQLException {
        userConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "root");
        XAConnection xaConn = new MysqlXAConnection((JdbcConnection) userConn, logXaCommands);
        userRm = xaConn.getXAResource();
    }


    @After
    public void closeAllConn() {
        closeConn(userConn);
        closeConn(productConn);
        closeConn(orderConn);
    }


    public void closeConn(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 订单资源服务器
     *
     * @return
     */
    @Before
    public void orderRm() throws SQLException {
        orderConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/order", "root", "root");
        XAConnection xaConn = new MysqlXAConnection((JdbcConnection) orderConn, logXaCommands);
        orderRm = xaConn.getXAResource();
    }


    /**
     * 产品资源服务器
     *
     * @return
     * @throws SQLException
     */
    @Before
    public void productRm() throws SQLException {
        productConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/product", "root", "root");
        XAConnection xaConn = new MysqlXAConnection((JdbcConnection) productConn, logXaCommands);
        productRm = xaConn.getXAResource();
    }


    /**
     * XA分支事务第一阶段提交
     *
     * @param xaMap
     */
    private boolean prepare(Map<Xid, XAResource> xaMap) {
        for (Map.Entry<Xid, XAResource> xidXAResourceEntry : xaMap.entrySet()) {
            Xid        xid = xidXAResourceEntry.getKey();
            XAResource rm  = xidXAResourceEntry.getValue();

            try {
                int result = rm.prepare(xid);

                if (result != XAResource.XA_OK) {
                    return false;
                }
            } catch (XAException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }


    /**
     * 模拟 GlobalTransactionManager 二阶段提交
     *
     * @param xaMap
     */
    private void commit(Map<Xid, XAResource> xaMap) {
        System.out.println("事务提交");
        // 是否只有一个事务，如果是就直接一阶段提交
        boolean onePhase = xaMap.keySet().size() > 1 ? false : true;

        // 事务是否成功
        boolean isSuccess = true;

        // 二阶段提交
        for (Map.Entry<Xid, XAResource> xidXAResourceEntry : xaMap.entrySet()) {
            Xid        xid = xidXAResourceEntry.getKey();
            XAResource rm  = xidXAResourceEntry.getValue();
            try {
                rm.commit(xid, onePhase);
            } catch (XAException e) {
                e.printStackTrace();
                isSuccess = false;
                break;
            }
        }

        // 如果有一个事务提交异常则回滚
        if (!isSuccess) {
            rollback(xaMap);
        }
    }


    /**
     * 二阶段回滚
     *
     * @param xaMap
     */
    private void rollback(Map<Xid, XAResource> xaMap) {
        System.out.println("事务回滚");
        for (Map.Entry<Xid, XAResource> xidXAResourceEntry : xaMap.entrySet()) {
            Xid        xid = xidXAResourceEntry.getKey();
            XAResource rm  = xidXAResourceEntry.getValue();
            try {
                rm.rollback(xid);
            } catch (XAException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 模拟库存服务扣减库存
     *
     * @param productId 商品id
     * @param num       商品数量
     * @return
     */
    private BigDecimal deductionsInventory(int productId, int num) throws Exception {
        ResultSet         rs    = null;
        PreparedStatement ps    = null;
        BigDecimal        price = null;
        try {
            // 库存服务开启库存分支事务
            productRm.start(productXid, XAResource.TMNOFLAGS);

            // 判断库存是否足够
            String selectSql = String.format("select * from product_info where id = %d", productId);
            ps = productConn.prepareStatement(selectSql);
            rs = ps.executeQuery();

            if (!rs.next()) {
                throw new RuntimeException("商品信息错误");
            }

            int stock = rs.getInt("stock");
            price = rs.getBigDecimal("price");

            if (num > stock) {
                throw new RuntimeException("库存不足");
            }

            String updateSql = String.format("update product_info set stock = stock - %d where id= %d", num, productId);
            productConn.prepareStatement(updateSql).executeUpdate();

            System.out.println("扣减库存完成");
        } finally {
            closePs(ps);
            closeRs(rs);
            productRm.end(productXid, XAResource.TMSUCCESS);
        }

        return price;
    }


    /**
     * 模拟用户服务金额扣减
     *
     * @param num    购买数量
     * @param price  产品价格
     * @param userId 用户
     * @return
     * @throws Exception
     */
    private BigDecimal deductionsAmount(int num, BigDecimal price, int userId) throws Exception {
        ResultSet         rs = null;
        PreparedStatement ps = null;

        BigDecimal totalPrice = null;
        try {
            // 开启商品服务分支事务
            userRm.start(userXid, XAResource.TMNOFLAGS);

            totalPrice = price.multiply(new BigDecimal(num));
            String selectSql = String.format("select * from user_info where id = %s", userId);
            ps = userConn.prepareStatement(selectSql);
            rs = ps.executeQuery();

            if (!rs.next()) {
                throw new RuntimeException("用户信息错误");
            }

            BigDecimal amount = rs.getBigDecimal("amount");

            if (amount.compareTo(totalPrice) == -1) {
                throw new RuntimeException("余额不足");
            }

            String updateSql = String.format("update user_info set amount = amount - %.2f where id= %d", totalPrice.doubleValue(), userId);
            userConn.prepareStatement(updateSql).executeUpdate();

            System.out.println("扣减金额完成");
        } finally {
            closePs(ps);
            closeRs(rs);
            userRm.end(userXid, XAResource.TMSUCCESS);
        }

        return totalPrice;
    }


    /**
     * 模拟订单服务创建订单
     *
     * @param userId     用户id
     * @param productId  产品id
     * @param num        产品数量
     * @param totalPrice 总价格
     * @throws Exception
     */
    private void createOrder(int userId, int productId, int num, BigDecimal price, BigDecimal totalPrice) throws Exception {
        PreparedStatement ps = null;
        try {
            // 开启订单分支事务
            orderRm.start(orderXid, XAResource.TMNOFLAGS);
            String orderNo = UUID.randomUUID().toString();
            String insertSql = String.format("insert into order_info(order_no, product_id, user_id, num, price, total_price) " +
                    "values('%s', %d, %d, %d, %.2f, %.2f)", orderNo, productId, userId, num, price.doubleValue(), totalPrice.doubleValue());

            ps = orderConn.prepareStatement(insertSql);
            ps.executeUpdate();

            // 全局回滚测试
            if (isRollBack) {
                throw new RuntimeException();
            }
        } finally {
            closePs(ps);
            orderRm.end(orderXid, XAResource.TMSUCCESS);
        }
    }


    private void closePs(PreparedStatement ps) {
        if (Objects.nonNull(ps)) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void closeRs(ResultSet rs) {
        if (Objects.nonNull(rs)) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}