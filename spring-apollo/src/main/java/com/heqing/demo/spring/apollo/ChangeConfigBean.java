package com.heqing.demo.spring.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChangeConfigBean {

    @ApolloConfig
    private Config config;

    private String test;


    public String getTest() {
        return test;
    }

    @Value("${test:200}")
    public void setTest(String test) {
        this.test = test;
    }


    @ApolloConfigChangeListener
    private void testChange(ConfigChangeEvent changeEvent) {
        //update injected value of batch if it is changed in Apollo
        if (changeEvent.isChanged("test")) {
            test=config.getProperty("test","");
            System.out.println("--->" + test);
        }
    }
}
