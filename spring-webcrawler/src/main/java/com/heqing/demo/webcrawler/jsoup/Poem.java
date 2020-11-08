package com.heqing.demo.webcrawler.jsoup;

import lombok.Data;

@Data
public class Poem {

    private String url;
    private String title;
    private String author;
    private String content;

}
