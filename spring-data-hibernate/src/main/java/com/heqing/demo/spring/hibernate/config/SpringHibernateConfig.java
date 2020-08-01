package com.heqing.demo.spring.hibernate.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.heqing.demo.spring.hibernate.*")
@EnableTransactionManagement
public class SpringHibernateConfig {

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

    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean(DataSource dataSource) {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        localSessionFactoryBean.setHibernateProperties(properties);
        localSessionFactoryBean.setPackagesToScan("com.heqing.demo.spring.hibernate.model");
        return localSessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory localSessionFactoryBean) {
        return new HibernateTransactionManager(localSessionFactoryBean);
    }

}
