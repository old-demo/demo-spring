package com.heqing.demo.spring.activemq.config;

import com.heqing.demo.spring.activemq.listener.QueueMsgListener;
import com.heqing.demo.spring.activemq.listener.TopicMsgListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.Destination;

@EnableJms
@Configuration
@PropertySource("classpath:mq.properties")
public class ConsumerConfig {

    @Value("${activemq.url}")
    String URL;

    @Value("${activemq.queueName}")
    String QUEUE_NAME;

    @Value("${activemq.topicName}")
    String TOPIC_NAME;

    @Autowired
    TopicMsgListener topicMsgListener;

    @Autowired
    QueueMsgListener queueMsgListener;

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


    /**
     * 配置topic监听容器，从ioc容器当中注入 ConnectionFactory  和 queueMsgListener
     * 在xml当中的如下配置 效果相同
     * <bean class="org.springframework.jms.listener.DefaultMessageListenerContainer">
     *		<property name="connectionFactory" ref="connectionFactory" />
     *		<property name="destination" ref="topicDestination" />
     *		<property name="messageListener" ref="itemListenerMessage" />
     *	</bean>
     **/
    @Bean
    public DefaultMessageListenerContainer topicMsgListenerContainer(SingleConnectionFactory singleConnectionFactory){
        //创建容器
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        //设置监听器
        container.setMessageListener(topicMsgListener);
        //设置连接工厂
        container.setConnectionFactory(singleConnectionFactory);
        //设置监听目的地的名字/也可以直接设置对象目的地
        container.setDestination(topicDestination());
        return container;
    }

    @Bean
    public DefaultMessageListenerContainer queueMsgListenerContainer(SingleConnectionFactory singleConnectionFactory){
        //创建容器
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        //设置监听器
        container.setMessageListener(queueMsgListener);
        //设置连接工厂
        container.setConnectionFactory(singleConnectionFactory);
        //设置监听目的地的名字/也可以直接设置对象目的地
        container.setDestination(queueDestination());
        return container;
    }

    /**
     * 配置队列目的的： 测试的时候 根据需要配置其中一个
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
