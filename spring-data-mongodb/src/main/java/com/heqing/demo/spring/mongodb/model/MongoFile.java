package com.heqing.demo.spring.mongodb.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Data
public class MongoFile implements Serializable {

    private String fileId;
    private String fileName;
    private long fileSize;
    private Date uploadTime;

}
