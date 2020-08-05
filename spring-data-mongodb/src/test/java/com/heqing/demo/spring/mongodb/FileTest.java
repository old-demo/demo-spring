package com.heqing.demo.spring.mongodb;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.mongodb.config.SpringMongoDBConfig;
import com.heqing.demo.spring.mongodb.dao.FileDao;
import com.heqing.demo.spring.mongodb.model.MongoFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileNotFoundException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = SpringMongoDBConfig.class
)
public class FileTest {

    @Autowired
    FileDao fileDao;

    @Test
    public void writeFile() throws FileNotFoundException {
        String orderid = fileDao.uploadFile("D:\\test\\welcome.png","test3.png");
        System.out.println("-->"+ orderid);
    }

    @Test
    public void downLoadByFileId() {
         fileDao.downLoadByFileId("5f2a734da944e464d051ac4b","D:\\test\\11.jpg");
    }

    @Test
    public void searchFileById() {
        MongoFile result = fileDao.searchFileById("5f2a7742a944e40970dc9fb7");
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void searchFile() {
        Query query = new Query(Criteria.where("filename").is("test1.png"));
        List<MongoFile> result = fileDao.searchFile(query);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void deleteFileById() {
        fileDao.deletFileById("5f2a7742a944e40970dc9fb7");
    }

    @Test
    public void deleteFile() {
        Query query = new Query(Criteria.where("filename").is("test3.png"));
        fileDao.deleteFile(query);
    }

}
