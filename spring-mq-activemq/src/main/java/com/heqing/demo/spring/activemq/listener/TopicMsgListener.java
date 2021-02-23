package com.heqing.demo.spring.activemq.listener;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 一对多监听容器
 */
@Component
public class TopicMsgListener implements MessageListener{

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        System.out.println("topic监听者正在监听消息.............");
        try {
            System.out.println("topic --> "+textMessage.getText());
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}