package com.heqing.demo.spring.redisson.dao.impl;

import com.heqing.demo.spring.redisson.dao.RedissonLockDao;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RedissonLockDaoImpl implements RedissonLockDao {

    @Autowired
    RedissonClient redissonClient;


}
