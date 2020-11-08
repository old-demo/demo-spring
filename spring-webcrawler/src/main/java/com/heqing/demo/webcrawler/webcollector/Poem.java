package com.heqing.demo.webcrawler.webcollector;

import lombok.Data;

@Data
public class Poem {

    private String url;
    private String id;
    private String title;
    private String author;
    private String content;

}
