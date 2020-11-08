package com.heqing.demo.webcrawler.webcollector;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import com.alibaba.fastjson.JSONObject;

public class PoemCrawler extends BreadthCrawler {

    public PoemCrawler(String crawlPath, boolean autoParse) {
        /**
         * crawlPath：表示设置保存爬取记录的文件夹，本例运行之后会在应用根目录下生成一个 "crawl" 目录存放爬取信息
         */
        super(crawlPath, autoParse);

        /**设置爬取的网站地址
         * addSeed 表示添加种子
         * 种子链接会在爬虫启动之前加入到抓取信息中并标记为未抓取状态.这个过程称为注入
         * */
        this.addSeed("https://www.gushiwen.org/gushi/tangshi.aspx");

        //正则规则设置 寻找符合http
        this.addRegex("https://so.gushiwen.org/shiwenv_[^/]+.aspx");

        this.addRegex("-.*\\.(jpg|png|gif).*");

        /** 过滤 jpg|png|gif 等图片地址 时 */
        this.addRegex("-.*\\.(jpg|png|gif).*");

        /** 过滤 链接值为 "#" 的地址时：*/
        this.addRegex("-.*#.*");

        /**设置线程数*/
        setThreads(50);

        /**设置每次迭代中爬取数量的上限*/
        getConf().setTopN(100);

        /**
         *设置是否为断点爬取，如果设置为false，任务启动前会清空历史数据。
         *如果设置为true，会在已有crawlPath(构造函数的第一个参数)的基础上继
         *续爬取。对于耗时较长的任务，很可能需要中途中断爬虫，也有可能遇到
         *死机、断电等异常情况，使用断点爬取模式，可以保证爬虫不受这些因素
         *的影响，爬虫可以在人为中断、死机、断电等情况出现后，继续以前的任务
         *进行爬取。断点爬取默认为false
         * */
//        setResumable(true);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        if (page.matchUrl("https://so.gushiwen.org/shiwenv_[^/]+.aspx")) {
            String url = page.url();
            String title = page.selectText(".cont h1");
            String author = page.selectText(".cont p a b");
            String content = page.selectText(".cont .contson");

            Poem poem = new Poem();
            poem.setUrl(url);
            poem.setTitle(title);
            poem.setAuthor(author);
            poem.setContent(content);
            System.out.println("-->" + JSONObject.toJSONString(poem));
        }
    }
}
