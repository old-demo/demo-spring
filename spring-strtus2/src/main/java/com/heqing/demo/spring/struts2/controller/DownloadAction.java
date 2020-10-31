package com.heqing.demo.spring.struts2.controller;

import com.opensymphony.xwork2.ActionSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class DownloadAction extends ActionSupport {

    private InputStream fileInput;

    private String fileName;

    public InputStream getFileInput() {
        return fileInput;
    }

    public void setFileInput(InputStream fileInput) {
        this.fileInput = fileInput;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String execute() throws Exception {
        File f= new File("E:/test/"+fileName) ;
        fileInput = new FileInputStream(f);
        return "success";
    }

}
