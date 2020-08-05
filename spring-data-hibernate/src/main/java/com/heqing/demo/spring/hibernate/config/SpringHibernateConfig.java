package com.heqing.demo.spring.hibernate.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
    public DataSource dataSource(DBProperty dBProperty) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(dBProperty.getUser());
        dataSource.setPassword(dBProperty.getPassword());
        dataSource.setUrl(dBProperty.getUrl());
        dataSource.setDriverClassName(dBProperty.getDriverClass());
        dataSource.setInitialSize(dBProperty.getInitialSize());
        dataSource.setMinIdle(dBProperty.getMinIdle());
        dataSource.setMaxActive(dBProperty.getMaxActive());
        dataSource.setMaxWait(dBProperty.getMaxWait());
        dataSource.setValidationQuery(dBProperty.getValidationQuery());
        dataSource.setTestWhileIdle(dBProperty.getTestWhileIdle());
        dataSource.setTestOnBorrow(dBProperty.getTestOnBorrow());
        dataSource.setTestOnReturn(dBProperty.getTestOnReturn());
        dataSource.setTimeBetweenEvictionRunsMillis(dBProperty.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(dBProperty.getMinEvictableIdleTimeMillis());
        dataSource.setPoolPreparedStatements(dBProperty.getPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(dBProperty.getMaxPoolPreparedStatementPerConnectionSize());
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
