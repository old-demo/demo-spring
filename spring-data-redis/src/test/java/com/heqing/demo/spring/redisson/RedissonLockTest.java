package com.heqing.demo.spring.redisson;

import com.heqing.demo.spring.redisson.config.SpringRedissonConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringRedissonConfig.class
)
@ActiveProfiles("single")
public class RedissonLockTest {

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void testReentrantLock() {
        RLock lock = redissonClient.getLock("testReentrantLock");
        try {
            // 可重入锁
            for (int i = 1; i <= 100; i++) {
                // 获取锁
                // 判断是否锁住
                if (lock.isLocked()) {
                    System.out.println(i + "-->锁住了");
                } else {
                    System.out.println(i + "-->没有锁住");
                    // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
                    boolean res = lock.tryLock(10, 5, TimeUnit.SECONDS);
                    if (res) {
                        System.out.println("--->加锁成功");
                    } else {
                        System.out.println("--->加锁失败");
                    }
                }

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWatchdog() {
        RLock lock = redissonClient.getLock("testWatchdog");
        try {
            lock.lock(3, TimeUnit.SECONDS);
            if (lock.isLocked()) {
                System.out.println("-->锁住了");
            } else {
                System.out.println("-->没有锁住");
            }

            Thread.sleep(5000);

            if (lock.isLocked()) {
                System.out.println("-->锁住了");
            } else {
                System.out.println("-->没有锁住");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
