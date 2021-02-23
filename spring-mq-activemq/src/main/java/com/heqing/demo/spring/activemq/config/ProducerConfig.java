package com.heqing.demo.spring.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@Configuration
@PropertySource("classpath:mq.properties")
public class ProducerConfig {

    @Value("${activemq.url}")
    String URL;

    @Value("${activemq.queueName}")
    String QUEUE_NAME;

    @Value("${activemq.topicName}")
    String TOPIC_NAME;

    /** 配置ConnectionFactory用于生成connection */
    @Bean
    public ActiveMQConnectionFactory connectionFactory(){
        return new ActiveMQConnectionFactory(URL);
    }

    /** 注册SingleConnectionFactory,这个spring的一个包装工厂 用于管理真正的ConnectionFactory */
    @Bean
    public SingleConnectionFactory singleConnectionFactory(ActiveMQConnectionFactory activeMQconnectionFactory){
        SingleConnectionFactory connectionFactory = new SingleConnectionFactory();
        //设置目标工厂
        connectionFactory.setTargetConnectionFactory(activeMQconnectionFactory);
        return connectionFactory;
    }

    /** 配置生产者，jmsTemplate */
    @Bean
    public JmsTemplate jmsTemplate(SingleConnectionFactory connectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    /**
     * 配置队列目的的： 根据测试需要配置其中一个
     * 	1.队列  点对点 queue
     *  2.主题  一对多  topic
     * */
    @Bean
    public ActiveMQQueue queueDestination(){
        ActiveMQQueue activeMQQueue = new ActiveMQQueue(QUEUE_NAME);
        return activeMQQueue;
    }

    @Bean
    public ActiveMQTopic topicDestination(){
        ActiveMQTopic activeMQTopic = new ActiveMQTopic(TOPIC_NAME);
        return activeMQTopic;
    }

}
