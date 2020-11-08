package com.heqing.demo.webcrawler.webmagic.model;

import lombok.Data;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@Data
@TargetUrl("https://so.gushiwen.org/\\w+.aspx")
public class Poem {

    @ExtractByUrl
    private String url;

    @ExtractByUrl("shiwenv_(.*?).aspx")
    private String id;

    @ExtractBy(value = "//[@id='sonsyuanwen']/[@class='cont']/h1/text()", notNull = true)
    private String title;

    @ExtractBy("//[@id='sonsyuanwen']/[@class='cont']/p/a[2]/text()")
    private String author;

    @ExtractBy("//[@id='sonsyuanwen']/[@class='cont']/[@class='contson']/text()")
    private String content;

}
