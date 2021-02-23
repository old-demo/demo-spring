package com.heqing.demo.spring.mq;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import java.util.Date;
import java.util.Properties;

public class ProducerTest {
    public static void main(String[] args) {
        Properties properties = new Properties();
        // AccessKeyId 阿里云身份验证，在阿里云用户信息管理控制台获取。
        properties.put(PropertyKeyConst.AccessKey,"LTAI4G4zoFHFXzpBwVqyJ1fb");
        // AccessKeySecret 阿里云身份验证，在阿里云用户信息管理控制台获取。
        properties.put(PropertyKeyConst.SecretKey, "oVn4AznRaZ6rokTcFF8Dn1IGckZWvj");
        //设置发送超时时间，单位毫秒。
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
        // 设置 TCP 接入域名，进入控制台的实例详情页面的 TCP 协议客户端接入点区域查看。
        properties.put(PropertyKeyConst.NAMESRV_ADDR, "http://1137904576193554.mqrest.cn-beijing.aliyuncs.com");
        Producer producer = ONSFactory.createProducer(properties);
        // 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可。
        producer.start();

        //循环发送消息。
        for (int i = 0; i < 100; i++){
            Message msg = new Message( //
                    // 普通消息所属的 Topic，切勿使用普通消息的 Topic 来收发其他类型的消息。
                    "TOPIC_demo_rocketmq_normal",
                    // Message Tag 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在消息队列 RocketMQ 版的服务器过滤。
                    "TagA",
                    // Message Body 可以是任何二进制形式的数据，消息队列 RocketMQ 版不做任何干预。
                    // 需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式。
                    "Hello MQ".getBytes());
            // 设置代表消息的业务关键属性，请尽可能全局唯一。
            // 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发。
            // 注意：不设置也不会影响消息正常收发。
            msg.setKey("ORDERID_" + i);

            try {
                SendResult sendResult = producer.send(msg);
                // 同步发送消息，只要不抛异常就是成功。
                if (sendResult != null) {
                    System.out.println(new Date() + " Send mq message success. Topic is:" + msg.getTopic() + " msgId is: " + sendResult.getMessageId());
                }
            }
            catch (Exception e) {
                // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理。
                System.out.println(new Date() + " Send mq message failed. Topic is:" + msg.getTopic());
                e.printStackTrace();
            }
        }

        // 在应用退出前，销毁 Producer 对象。
        // 注意：如果不销毁也没有问题。
        producer.shutdown();
    }
}
