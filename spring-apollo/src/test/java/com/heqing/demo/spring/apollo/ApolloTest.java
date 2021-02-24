package com.heqing.demo.spring.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = AppConfig.class
)
public class ApolloTest {

    @ApolloConfig
    private Config config;

    @Test
    public void test() {
        Consumer<String> show = o -> System.out.println(o + " : " + config.getProperty(o,""));

        Set<String> fieldnames = config.getPropertyNames();
        fieldnames.forEach(show);

        config.getProperty("test", "");
    }

    @Test
    public void testChange() throws IOException {
        System.in.read();
    }
}
