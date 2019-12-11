package com.wgf.test.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author: wgf
 * @create: 2019-12-06 20:28
 * @description:
 **/

@Configuration
@MapperScan(basePackages = H2DataSourceConf.SCAN_PACKAGE, sqlSessionFactoryRef = "h2SqlSessionFactory")
public class H2DataSourceConf {

    public static final String SCAN_PACKAGE = "com.wgf.test.mapper.h2";
    public static final String MAPPER_LOCATION = "classpath*:mapper/h2/*.xml";

    // 指定h2 为主数据源才会执行初始化脚本
    @Primary
    @Bean(name = "h2DataSource")
    @ConfigurationProperties("spring.datasource")
    public DataSource h2DataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    /**
     * @Author:
     * @Description: 会话工厂
     * @Date: 2018/4/15 17:15
     * @Modified by:
     * @params: * @param null
     * @return:
     */
    @Bean(name = "h2SqlSessionFactory")
    @Primary
    public SqlSessionFactory h2SqlSessionFactory(@Qualifier("h2DataSource") DataSource h2DataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(h2DataSource);

        //分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();

        //更多详细配置见: https://pagehelper.github.io/docs/howtouse/
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "HSQLDB");           //方言
        properties.setProperty("rowBoundsWithCount", "true");       //使用 RowBounds 分页会进行 count 查询
        properties.setProperty("reasonable", "false");               //pageNum<=0 时会查询第一页， pageNum>pages（超过总数时），会查询最后一页
        properties.setProperty("pageSizeZero", "true");             //如果 pageSize=0 或者 RowBounds.limit = 0 就会查询出全部的结果
        properties.setProperty("supportMethodsArguments", "true");  //支持通过 Mapper 接口参数来传递分页参数
        properties.setProperty("offsetAsPageNum", "true");          //将 RowBounds 中的 offset 参数当成 pageNum 使用

        pageInterceptor.setProperties(properties);

        //添加插件
        sessionFactory.setPlugins(new Interceptor[]{pageInterceptor});

        //设置mapper location
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(H2DataSourceConf.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Bean
    @Primary
    public SqlSessionTemplate h2TestSqlSessionTemplate(@Qualifier("h2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * @Author:
     * @Description: 事务
     * @Date: 2018/4/15 17:16
     * @Modified by:
     * @params: * @param null
     * @return:
     */
    @Bean(name = "h2TransactionManager")
    @Primary
    public DataSourceTransactionManager h2TransactionManager(@Qualifier("h2DataSource") DataSource h2DataSource) {
        return new DataSourceTransactionManager(h2DataSource);
    }

    /**
     * Mybatis 通用Mapper配置
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public MapperHelper h2MapperHelper(@Qualifier("h2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperHelper mapperHelper = new MapperHelper();
        //特殊配置
        Config config = new Config();
        config.setNotEmpty(false);
        config.setIDENTITY("HSQLDB");

        //更多详细配置: http://git.oschina.net/free/Mapper/blob/master/wiki/mapper3/2.Integration.md
        mapperHelper.setConfig(config);
        mapperHelper.registerMapper(com.wgf.test.common.CommonMapper.class);
        mapperHelper.processConfiguration(sqlSessionFactory.getConfiguration());

        return mapperHelper;
    }
}