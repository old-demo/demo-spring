package com.heqing.demo.webcrawler;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.heqing.demo.webcrawler.config.SpringCoreConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringCoreConfig.class
)
public class TestGecco {

    @Test
    public void testHttpGetRequest() {
        HttpGetRequest start = new HttpGetRequest("https://www.gushiwen.org/gushi/tangshi.aspx");
        start.setCharset("GBK");
        GeccoEngine.create()
                .classpath("com.heqing.demo.webcrawler.gecco")
                .thread(1)
                .start(start)
                .run();
    }

    @Test
    public void testGeccoEngine() {
        GeccoEngine.create()
                //Gecco搜索的包路径
                .classpath("com.heqing.demo.webcrawler.gecco")
                //开始抓取的页面地址
                .start("https://www.gushiwen.org/gushi/tangshi.aspx")
                //开启几个爬虫线程
                .thread(1)
                //单个爬虫每次抓取完一个请求后的间隔时间
                .interval(100)
                .run();
    }
}
