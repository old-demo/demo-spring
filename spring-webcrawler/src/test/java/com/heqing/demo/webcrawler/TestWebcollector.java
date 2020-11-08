package com.heqing.demo.webcrawler;

import com.heqing.demo.webcrawler.webcollector.PoemCrawler;
import org.junit.Test;

public class TestWebcollector {

    @Test
    public void testWebcollector() throws Exception {
        PoemCrawler crawler = new PoemCrawler("D:\\webcrawler\\", true);
        crawler.start(2);//启动爬虫。查询的深度
    }
}
