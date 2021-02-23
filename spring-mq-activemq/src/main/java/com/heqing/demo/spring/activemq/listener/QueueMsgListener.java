package com.heqing.demo.spring.activemq.listener;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 点对点监听容器
 */
@Component
public class QueueMsgListener implements MessageListener {

    /**
     * @JmsListener(containerFactory="defaultMessageListenerContainer",destination="queueDestination")
     * 经过测 发现这种方式 声明 监听的消费 者无效。几经探究，无果。
     * */
    @Override
    public void onMessage(Message message){
        TextMessage textMessage = (TextMessage) message;
        System.out.println("queue监听者正在监听消息.............");
        try {
            System.out.println("queue --> "+textMessage.getText());
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
