package com.heqing.demo.spring.neo4j.config;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.transaction.SessionFactoryUtils;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories("com.heqing.demo.spring.neo4j.repository")
@ComponentScan("com.heqing.demo.spring.neo4j.*")
public class SpringNeo4jDBConfig  {

    @Autowired
    Neo4jDBProperty neo4jDBProperty;

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        System.out.println();
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
                .uri(neo4jDBProperty.getUri())
                .credentials(neo4jDBProperty.getUsername(), neo4jDBProperty.getPassword())
                .build();
        return configuration;
    }

    @Bean
    public SessionFactory sessionFactory() {
        // 默认为bolt,只有从配置文件修改后会变成http
        return new SessionFactory(configuration(), neo4jDBProperty.getPackages());
    }

    @Bean
    public Session neo4jSession(SessionFactory sessionFactory) {
        return sessionFactory.openSession();
    }

    @Bean("transactionManager")
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }

    @Bean("neo4jTransactionTemplate")
    public TransactionTemplate neo4jTransactionTemplate(Neo4jTransactionManager neo4jTransactionManager) {
        return new TransactionTemplate(neo4jTransactionManager);
    }
}
