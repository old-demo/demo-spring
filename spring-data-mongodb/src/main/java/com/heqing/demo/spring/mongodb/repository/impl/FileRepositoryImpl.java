package com.heqing.demo.spring.mongodb.repository.impl;

import com.heqing.demo.spring.mongodb.repository.FileRepository;
import com.heqing.demo.spring.mongodb.model.MongoFile;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class FileRepositoryImpl implements FileRepository {

    private static Pattern NUMBER_PATTERN = Pattern.compile("(?<==).*(?=})");

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFsBucket;

    @Override
    public String uploadFile(String localFilePath, String originFileName) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(localFilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("上传文件失败："+e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ObjectId objectId = gridFsTemplate.store(inputStream, originFileName);
        if(objectId != null) {
            return objectId.toString();
        }
        return "";
    }

    @Override
    public void downLoadByFileId(String id, String localFilePath) {
        GridFSFile gridFsFile = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(id)));
        if(gridFsFile != null) {
            GridFSDownloadStream gridFsDownloadStream = gridFsBucket.openDownloadStream(gridFsFile.getFilename());
            if (gridFsDownloadStream != null) {
                GridFsResource gridFsResource = new GridFsResource(gridFsFile, gridFsDownloadStream);

                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    is = gridFsResource.getInputStream();
                    fos = new FileOutputStream(new File(localFilePath));
                    int bytesRead = 0;
                    byte[] buffer = new byte[1024];
                    while ((bytesRead = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("下载文件失败：" + e.getMessage());
                } finally {
                    try {
                        is.close();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            throw new RuntimeException("无法获取此id文件");
        }
    }

    @Override
    public MongoFile searchFileById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        List<MongoFile> fileList = searchFile(query);
        if(fileList != null && fileList.size() > 0) {
            return fileList.get(0);
        }
        return null;
    }

    @Override
    public List<MongoFile> searchFile(Query query) {
        GridFSFindIterable gridFsFindIterable = gridFsTemplate.find(query);
        List<MongoFile> fileList = new ArrayList<>();
        for (GridFSFile file : gridFsFindIterable) {
            MongoFile mongoFile = new MongoFile();
            mongoFile.setFileId(getFileId(file.getId().toString()));
            mongoFile.setFileName(file.getFilename());
            mongoFile.setFileSize(file.getLength()/1024);
            mongoFile.setUploadTime(file.getUploadDate());
            fileList.add(mongoFile);
        }
        return fileList;
    }

    @Override
    public void deletFileById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        deleteFile(query);
    }

    @Override
    public void deleteFile(Query query) {
        gridFsTemplate.delete(query);
    }

    /**
     * 因为从mongo中获取的文件Id是BsonObjectId {value=5d7068bbcfaf962be4c7273f}的样子
     * 需要字符串截取
     * @param bsonObjectId 数据库文件的BsonObjectId
     */
    private String getFileId(String bsonObjectId) {
        Matcher m = NUMBER_PATTERN.matcher(bsonObjectId);
        if(!m.find()){
            return bsonObjectId;
        }
        return m.group();
    }
}
