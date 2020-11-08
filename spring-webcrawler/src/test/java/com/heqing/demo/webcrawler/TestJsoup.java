package com.heqing.demo.webcrawler;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.webcrawler.jsoup.Poem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestJsoup {

    @Test
    public void testParseClass() throws Exception {
        // 最外层的URL
        String wrapUrl = "https://www.gushiwen.org/gushi/tangshi.aspx";
        // 使用Jsoup.parse，把HTML结果解析成Document对象，我们可以像js那样使用里面的方法
        Document document = Jsoup.parse(new URL(wrapUrl), 50000);
        // 遍历分类信息
        List<Poem> poemList = parseHtml(document);
        // 获取详细信息
        parsePoem(poemList);
        for(Poem c : poemList) {
            System.out.println("--> " + JSONObject.toJSONString(c));
        }
    }

    List<Poem> parseHtml(Document document) {
        List<Poem> poemList = new ArrayList<>();
        Elements elements = document.getElementsByClass("typecont");
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            Elements spans = element.getElementsByTag("a");
            for (int j = 0; j < spans.size(); j++) {
                Element span = spans.get(j);
                String url = span.getElementsByTag("a").eq(0).attr("href");
                Poem poem = new Poem();
                poem.setUrl(url);
                poemList.add(poem);
            }
        }
        return poemList;
    }

    void parsePoem(List<Poem> poemList) throws Exception {
        for(Poem poem : poemList) {
            String src = poem.getUrl();
            // 请求每一个URL，得到诗词体
            Document sonDoc = Jsoup.parse(new URL(src), 50000);
            // 获取url中的ID，下面获取诗词体的时候用得到
            String id = src.substring(src.indexOf("_")+1, src.indexOf(".aspx"));
            Element body = sonDoc.getElementById("sonsyuanwen");
            Element cont = body.getElementsByClass("cont").get(0);
            String title = cont.getElementsByTag("h1").eq(0).text();
            String author = cont.getElementsByTag("p").get(0).getElementsByTag("a").eq(1).text();
            String content = cont.getElementById("contson" + id).text();
            poem.setTitle(title);
            poem.setAuthor(author);
            poem.setContent(content);
        }
    }
}

