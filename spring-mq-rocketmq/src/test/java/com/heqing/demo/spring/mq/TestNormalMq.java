package com.heqing.demo.spring.mq;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.heqing.demo.spring.mq.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring_core.xml", "classpath*:mq-producer.xml"}
)
public class TestNormalMq {

    @Value("${tag}")
    private String tag;

    @Value("${topic.normal}")
    private String topic;

    @Autowired
    Producer normalProducer;

    @Test
    public void sendMsg() {
        User user = new User();
        user.setId(1L);
        user.setName("贺小白");
        user.setAge(30);
        user.setAddress("安徽/安庆");
        user.setCreateTime(new Date());
        String body = JSONObject.toJSONString(user);

        Message message = new Message();
        message.setTopic(topic);
        message.setTag(tag);
        message.setKey(System.currentTimeMillis() + "");
        message.setBody(body.getBytes());

        SendResult sendResult = normalProducer.send(message);
        System.out.println("-->" + JSONObject.toJSONString(sendResult));
    }
}
