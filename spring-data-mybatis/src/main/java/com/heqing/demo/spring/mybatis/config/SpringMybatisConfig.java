package com.heqing.demo.spring.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan("com.heqing.demo.spring.mybatis.*")
@MapperScan("com.heqing.demo.spring.mybatis.dao, com.heqing.demo.spring.mybatis.mapper") //扫描Mybatis的Mapper接口
@EnableTransactionManagement //开启事务管理
public class SpringMybatisConfig {

    /**
     * 配置数据源
     **/
    @Bean
    public DataSource dataSource(JDBCConfig jdbcConfig) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(jdbcConfig.getUser());
        dataSource.setPassword(jdbcConfig.getPassword());
        dataSource.setUrl(jdbcConfig.getUrl());
        dataSource.setDriverClassName(jdbcConfig.getDriverClass());
        dataSource.setInitialSize(jdbcConfig.getInitialSize());
        dataSource.setMinIdle(jdbcConfig.getMinIdle());
        dataSource.setMaxActive(jdbcConfig.getMaxActive());
        dataSource.setMaxWait(jdbcConfig.getMaxWait());
        dataSource.setValidationQuery(jdbcConfig.getValidationQuery());
        dataSource.setTestWhileIdle(jdbcConfig.getTestWhileIdle());
        dataSource.setTestOnBorrow(jdbcConfig.getTestOnBorrow());
        dataSource.setTestOnReturn(jdbcConfig.getTestOnReturn());
        dataSource.setTimeBetweenEvictionRunsMillis(jdbcConfig.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(jdbcConfig.getMinEvictableIdleTimeMillis());
        dataSource.setPoolPreparedStatements(jdbcConfig.getPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(jdbcConfig.getMaxPoolPreparedStatementPerConnectionSize());
        return dataSource;
    }

    /**
     * 配置mybatis的SqlSessionFactoryBean
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource, MapperConfig mapperConfig) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(mapperConfig.getTypeAliasesPackage());

        // 动态获取SqlMapper
        PathMatchingResourcePatternResolver classPathResource = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(classPathResource.getResources(mapperConfig.getMapperLocations()));

        // 分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        pageInterceptor.setProperties(properties);
        sqlSessionFactoryBean.setPlugins(pageInterceptor);

        return sqlSessionFactoryBean;
    }

    /**
     * mybatis自动扫描加载Sql映射文件
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        return propertySourcesPlaceholderConfigurer;
    }

    /**
     * 配置spring的声明式事务
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        return dataSourceTransactionManager;

    }
}
