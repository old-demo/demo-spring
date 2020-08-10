package com.heqing.demo.spring.mongodb.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.util.Arrays;
import java.util.List;

@Configuration
@ComponentScan("com.heqing.demo.spring.mongodb.*")
public class SpringMongoDBConfig extends AbstractMongoConfiguration {

    @Autowired
    MongoDBProperty mongo;

    @Override
    protected String getDatabaseName() {
        return mongo.getDbname();
    }

    @Override
    public MongoClient mongoClient() {
        MongoClientOptions options = MongoClientOptions.builder()
                .threadsAllowedToBlockForConnectionMultiplier(mongo.getMultiplier())
                .connectionsPerHost(mongo.getConnectionsPerHost())
                .connectTimeout(mongo.getConnectTimeout())
                .maxWaitTime(mongo.getMaxWaitTime())
                .socketTimeout(mongo.getSocketTimeout())
                .build();
        // 多个ip地址
        List<ServerAddress> listHost = Arrays.asList(new ServerAddress(mongo.getHost(), mongo.getPort()));
        return new MongoClient(listHost, options);
    }

    @Bean
    public GridFsTemplate gridFsTemplate(MongoDbFactory mongoDbFactory) {
        GridFsTemplate gridFsTemplate = null;
        try {
            gridFsTemplate = new GridFsTemplate(mongoDbFactory, mappingMongoConverter());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridFsTemplate;
    }

    @Bean
    public GridFSBucket gridFSBucket(MongoDbFactory mongoDbFactory) {
        return GridFSBuckets.create(mongoDbFactory.getDb());
    }

}
