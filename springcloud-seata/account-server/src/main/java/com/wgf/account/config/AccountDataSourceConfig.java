package com.wgf.account.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import com.wgf.mybatis.mapper.Mapper;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.util.Properties;

/**
 * @author: wgf
 * @create: 2019-06-25 16:04
 * @description:
 **/
@Configuration
@MapperScan(basePackages = {AccountDataSourceConfig.BASE_PACKAGES}, sqlSessionTemplateRef = "accountSqlSessionTemplate")
public class AccountDataSourceConfig {

    public final static String BASE_PACKAGES = "com.wgf.account.mapper";
    public static final String MAPPER_LOCATION = "classpath:mapper/account/*.xml";

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource accountDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    @Bean
    public DataSourceProxy accountDataSourceProxy(@Qualifier("accountDataSource") DruidDataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

    @Bean
    public DataSourceTransactionManager accountTransactionManager(@Qualifier("accountDataSourceProxy") DataSourceProxy dataSourceProxy) {
        return new DataSourceTransactionManager(dataSourceProxy);
    }

    @Bean
    public SqlSessionFactory accountSqlSessionFactory(@Qualifier("accountDataSourceProxy") DataSourceProxy dataSourceProxy) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSourceProxy);


        //更多详细配置见: https://pagehelper.github.io/docs/howtouse/
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");           //方言
        properties.setProperty("rowBoundsWithCount", "true");       //使用 RowBounds 分页会进行 count 查询
        properties.setProperty("reasonable", "true");               //pageNum<=0 时会查询第一页， pageNum>pages（超过总数时），会查询最后一页
        properties.setProperty("pageSizeZero", "true");             //如果 pageSize=0 或者 RowBounds.limit = 0 就会查询出全部的结果
        properties.setProperty("supportMethodsArguments", "true");  //支持通过 Mapper 接口参数来传递分页参数
        properties.setProperty("offsetAsPageNum", "true");          //将 RowBounds 中的 offset 参数当成 pageNum 使用

        //添加插件
        //分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(properties);
        sessionFactory.setPlugins(new Interceptor[]{pageInterceptor});

        //设置mapper location
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(AccountDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate accountSqlSessionTemplate(@Qualifier("accountSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    /**
     * Mybatis 通用Mapper配置
     *
     * @param sqlSessionFactory
     * @return
     */

    @Bean
    public MapperHelper accountMapperHelper(@Qualifier("accountSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperHelper mapperHelper = new MapperHelper();
        //特殊配置
        Config config = new Config();
        config.setNotEmpty(false);
        config.setIDENTITY("MYSQL");
        //更多详细配置: http://git.oschina.net/free/Mapper/blob/master/wiki/mapper3/2.Integration.md
        mapperHelper.setConfig(config);
        mapperHelper.registerMapper(Mapper.class);
        mapperHelper.processConfiguration(sqlSessionFactory.getConfiguration());
        return mapperHelper;
    }
}
