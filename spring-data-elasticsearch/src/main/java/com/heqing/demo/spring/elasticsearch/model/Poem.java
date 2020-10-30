package com.heqing.demo.spring.elasticsearch.model;

import lombok.Data;

@Data
public class Poem {

    private String title;
    private String author;
    private String content;

    public Poem(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }
}
