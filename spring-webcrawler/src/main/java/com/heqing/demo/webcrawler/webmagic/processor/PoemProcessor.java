package com.heqing.demo.webcrawler.webmagic.processor;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.webcrawler.jsoup.Poem;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class PoemProcessor implements PageProcessor {

    /** 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等 */
    private static Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {

        // 部分三：从页面发现后续的url地址来抓取
        List<String> urls = page.getHtml().xpath("//[@class='typecont']").links().all();
        page.addTargetRequests(urls);

        // 部分二：定义如何抽取页面信息，并保存下来
        String url =  page.getUrl().toString();
        String title = page.getHtml().xpath("//[@id='sonsyuanwen']/[@class='cont']/h1/text()").toString();
        String author = page.getHtml().xpath("//[@id='sonsyuanwen']/[@class='cont']/p/a[2]/text()").toString();
        String id = url.substring(url.indexOf("_")+1, url.indexOf(".aspx"));
        String content = page.getHtml().xpath("//[@id='sonsyuanwen']/[@class='cont']/[@id='contson"+id+"']/text()").toString();

        if(!StringUtils.isEmpty(title)) {
            Poem poem = new Poem();
            poem.setUrl(url);
            poem.setTitle(title);
            poem.setAuthor(author);
            poem.setContent(content);
            System.out.println("--> " + JSONObject.toJSONString(poem));

            page.putField("poem", poem);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
