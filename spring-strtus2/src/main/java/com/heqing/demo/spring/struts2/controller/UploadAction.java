package com.heqing.demo.spring.struts2.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.heqing.demo.spring.struts2.util.ResultUtil;

import com.opensymphony.xwork2.ActionSupport;

public class UploadAction extends ActionSupport {

    private ResultUtil resultUtil;

    public ResultUtil getResultUtil() {
        return resultUtil;
    }

    private List<File> uploads;

    private List<String> uploadFileName;

    private List<String> uploadContentType;

    private long maximumSize;

    private String allowedTypes;

    public List<File> getUpload() {
        return uploads;
    }

    public void setUpload(List<File> uploads) {
        this.uploads = uploads;
    }

    public List<String> getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(List<String> uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public List<String> getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(List<String> uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public long getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(long maximumSize) {
        this.maximumSize = maximumSize;
    }

    public String getAllowedTypes() {
        return allowedTypes;
    }

    public void setAllowedTypes(String allowedTypes) {
        this.allowedTypes = allowedTypes;
    }

    @Override
    public String execute() throws Exception {
        //取得文件上传路径（用于存放上传的文件）
        File uploadFile = new File("E:/test");
        //判断上述路径是否存在，如果不存在则创建该路径
        if (!uploadFile.exists()) {
            uploadFile.mkdir();
        }

        if(uploads != null){
            String[] allowedTypesStr = allowedTypes.split(",");
            //将允许的文件类型列表放入List中
            List allowedTypesList = new ArrayList();
            for(int i = 0;i < allowedTypesStr.length; i++){
                allowedTypesList.add(allowedTypesStr[i]);
            }

            //判断上传文件的类型是否是允许的类型之一
            for(int i = 0; i < uploads.size();i++){
                if(!allowedTypesList.contains(uploadContentType.get(i))){
                    System.out.println("上传文件中包含非法文件类型");
                    resultUtil = ResultUtil.buildError("上传文件中包含非法文件类型");
                }
            }

            //判断文件的大小
            for(int i = 0 ;i < uploads.size();i++){
                System.out.println(uploads.get(i).length());
                // 判断文件长度
                if (maximumSize < uploads.get(i).length()) {
                    resultUtil = ResultUtil.buildError(uploadFileName.get(i)+ "文件过大");
                }
            }

            for(int i = 0; i < uploads.size();i++){
                // 第一种文件上传的读写方式
                FileInputStream input = new FileInputStream(uploads.get(i));
                FileOutputStream out = new FileOutputStream(uploadFile + "\\" + uploadFileName.get(i));

                try{
                    byte[] b = new byte[1024];
                    int m = 0;
                    while ((m = input.read(b)) > 0) {
                        out.write(b, 0, m);
                    }
                }catch(Exception e){
                    resultUtil = ResultUtil.buildError("上传过程中发生未知错误!");
                }finally{
                    input.close();
                    out.close();
                    //删除临时文件
                    uploads.get(i).delete();
                }
            }
            resultUtil = ResultUtil.buildSuccess();
        }else{
            resultUtil = ResultUtil.buildError("请选择上传文件");
        }
        // 返回结果
        return "myJson";
    }
}

