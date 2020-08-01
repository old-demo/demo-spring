package com.heqing.demo.spring.hibernate;

import com.heqing.demo.spring.hibernate.config.SpringHibernateConfig;
import com.heqing.demo.spring.hibernate.service.TransactionaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringHibernateConfig.class
)
public class TransactionalTest {

    @Autowired
    TransactionaService transactionaService;

    @Test
    public void testSave() {
        try {
            transactionaService.save(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
