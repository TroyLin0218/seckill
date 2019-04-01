package com.tlnb.dao.cache;

import com.tlnb.dao.SeckillDao;
import com.tlnb.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Description: TODO
 * @Auther: TianLin
 * @Date: 2019/03/31 031 10:45
 */
/*配置spring和junit的整合*/
@RunWith(SpringJUnit4ClassRunner.class) //Junit启动时加载SpringIOC容器
@ContextConfiguration({"classpath:spring/spring-dao.xml"})//告诉junit，spring的配置文件路径
public class RedisDaoTest {
    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    private Long seckillId = 1001l;

    @Test
    public void testRedisDao() throws Exception {
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            Seckill queryById = seckillDao.queryById(seckillId);
            String result = redisDao.putSeckill(queryById);
            System.out.println(result);
            Seckill seckill1 = redisDao.getSeckill(seckillId);
            System.out.println(seckill1);
        }
    }


}