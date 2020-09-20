package com.heqing.demo.spring.solr.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:solr.properties")
public class SolrProperty {

    @Value("${solr.host.url}")
    private String solrHostUrl ;

    @Value("${solr.basic.username}")
    private String username ;

    @Value("${solr.basic.password}")
    private String password ;

    @Value("${solr.host.connection.timeout}")
    private int connectionTimeout ;

    @Value("${solr.host.socket.timeout}")
    private int socketTimeout ;

    @Value("${solr.host.max.connections}")
    private int maxConnection ;

    @Value("${solr.host.per.max.connections}")
    private int preMaxConnection ;

}
