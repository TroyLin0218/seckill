package com.tlnb.dao;

import com.tlnb.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Description: seckillDAO的测试类
 * @Auther: TianLin
 * @Date: 2019/03/24 024 08:30
 */
/*配置spring和junit的整合*/
@RunWith(SpringJUnit4ClassRunner.class) //Junit启动时加载SpringIOC容器
@ContextConfiguration({"classpath:spring/spring-dao.xml"})//告诉junit，spring的配置文件路径
public class SeckillDaoTest {

    @Resource
    private SeckillDao seckillDao;

    @Test
    public void queryById() {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill);
    }

    @Test
    public void queryAll() {
        List<Seckill> seckills = seckillDao.queryAll(0,30);
        for(Seckill seckill :seckills){
            System.out.println(seckill);
        }
    }
    @Test
    public void reduceNumber() {
        long id = 1002;
        Date killTime = new Date();
        int i = seckillDao.reduceNumber(id, killTime);
        System.out.println(i);
    }


}