package com.heqing.demo.webcrawler;

import com.heqing.demo.webcrawler.webmagic.model.Poem;
import com.heqing.demo.webcrawler.webmagic.processor.PoemProcessor;
import org.junit.Test;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

/* http://webmagic.io/docs/zh/ */
public class TestWebmagic {

    @Test
    public void testPoemProcessor() {
        Spider.create(new PoemProcessor())
                // 从"https://www.gushiwen.org/gushi/tangshi.aspx"开始抓
                .addUrl("https://www.gushiwen.org/gushi/tangshi.aspx")
                // 下载到本地
                .addPipeline(new JsonFilePipeline("D:\\webcrawler\\"))
                // 开启5个线程抓取
                .thread(5)
                // 启动爬虫
                .run();
    }

    @Test
    public void testPoem() {
        Site site = Site.me().setRetryTimes(3).setSleepTime(1);
        OOSpider.create(site, new ConsolePageModelPipeline(), Poem.class)
                .addUrl("https://www.gushiwen.org/gushi/tangshi.aspx")
                .addPipeline(new ConsolePipeline())
                .thread(4)
                .run();
    }
}
