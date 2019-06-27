package com.wgf.order.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import java.math.BigDecimal;

@ApiModel(value = "OrderTbl", description = "")
@Table(name = "order_tbl")
public class OrderTbl {
    @Id
    @ApiModelProperty(value = "")
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * 商品编码
     */
    @Column(name = "commodity_code")
    @ApiModelProperty(value = "商品编码")
    private String commodityCode;

    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量")
    private Integer count;

    /**
     * 商品金额
     */
    @ApiModelProperty(value = "商品金额")
    private BigDecimal money;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取商品编码
     *
     * @return commodity_code - 商品编码
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * 设置商品编码
     *
     * @param commodityCode 商品编码
     */
    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    /**
     * 获取购买数量
     *
     * @return count - 购买数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置购买数量
     *
     * @param count 购买数量
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}