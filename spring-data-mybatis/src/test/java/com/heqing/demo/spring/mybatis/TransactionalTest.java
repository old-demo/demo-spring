package com.heqing.demo.spring.mybatis;

import com.heqing.demo.spring.mybatis.service.TransactionaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring_core.xml","classpath:spring_mybatis.xml"}
)
public class TransactionalTest {

    @Autowired
    TransactionaService transactionaService;

    @Test
    public void testSave() {
        try {
            transactionaService.save(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
