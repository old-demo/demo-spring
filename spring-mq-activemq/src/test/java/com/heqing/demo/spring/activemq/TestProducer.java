package com.heqing.demo.spring.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.heqing.demo.spring.activemq.config.SpringCoreConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringCoreConfig.class
)
public class TestProducer {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    Destination queueDestination;

    @Autowired
    Destination topicDestination;

    //测试点对点
    @Test
    public void testActiveMq(){
        //发送消息
        jmsTemplate.send(queueDestination, session -> {
            TextMessage message = session.createTextMessage();
            message.setText("this is a activemq  message for queue");
            return message;
        });
    }

    //测试topic发送
    @Test
    public void testActiveMq2(){
        //发送消息
        jmsTemplate.send(topicDestination, session -> {
            TextMessage message = session.createTextMessage();
            message.setText("this is a activemq  message for topic");
            return message;
        });
    }

}
