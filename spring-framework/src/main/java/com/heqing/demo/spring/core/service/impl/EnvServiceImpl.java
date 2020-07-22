package com.heqing.demo.spring.core.service.impl;

import com.heqing.demo.spring.core.service.EnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EnvServiceImpl implements EnvService {

    @Autowired
    Environment envConfig;

    @Value("${env}")
    private String env;

    @Value("${index:2}")
    private int index;

    @Value("${user}")
    private String user;

    @Value("${password}")
    private String password;

    @Override
    public void placeholder() {
        System.out.println("--> index=" + envConfig.getProperty("index", Integer.class, 1)
                    + ", env=" + envConfig.getProperty("env")
                    + ", user=" + envConfig.getProperty("user")
                    + ", password="+ envConfig.getProperty("password", String.class));
    }

    @Override
    public void spel() {
        System.out.println("--> index=" + index
                + ", env=" + env
                + ", user=" + user
                + ", password=" + password);
    }
}
