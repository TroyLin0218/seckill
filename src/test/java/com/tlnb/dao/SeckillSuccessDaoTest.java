package com.tlnb.dao;

import com.tlnb.entity.SeckillSuccess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Description: DAO层的业务测试
 * @Auther: TianLin
 * @Date: 2019/03/24 024 11:05
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})//告诉junit，spring的配置文件路径
public class SeckillSuccessDaoTest {

    @Autowired
    private SeckillSuccessDao seckillSuccessDao;
    @Test
    public void insertSeckillSuccess() {
        int i = seckillSuccessDao.insertSeckillSuccess(1001L, 13313355395L);
        System.out.println(i);
    }

    @Test
    public void queryBySeckillIdWithSeckillSuccess() {
        SeckillSuccess seckillSuccess =
                seckillSuccessDao.queryBySeckillIdWithSeckillSuccess(1001L,13313355395L);
        System.out.println(seckillSuccess);
    }
}