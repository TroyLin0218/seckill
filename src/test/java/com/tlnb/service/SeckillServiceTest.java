package com.tlnb.service;

import com.tlnb.dto.ExcuteseckillResult;
import com.tlnb.dto.Exposer;
import com.tlnb.entity.Seckill;
import com.tlnb.exception.RepeatSeckillException;
import com.tlnb.exception.SeckillCloseException;
import com.tlnb.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


import static org.junit.Assert.*;

/**
 * @Description: service层的业务测试
 * @Auther: TianLin
 * @Date: 2019/03/24 024 17:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/*.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //注入service
    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("seckillList={}",seckillList);
    }

    @Test
    public void getSeckillById() {
        long id = 1002;
        Seckill seckill = seckillService.getSeckillById(id);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void getSeckillUrlOrCurrentTime() {
        long id = 1001l;
        Exposer exposer = seckillService.getSeckillUrlOrCurrentTime(id);
        if (exposer.isExpose()){
            //秒杀开启，地址暴露
            logger.info("expose={}",exposer);
            String md5 = exposer.getMd5();
            ExcuteseckillResult result = null;
            try {
                //执行秒杀
                result = seckillService.excuteSeckill(id, md5, 15033526542l);
            } catch (RepeatSeckillException e) {
                logger.error(e.getMessage());
            }catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            }
            logger.info("result={}",result);
        }else{
            logger.warn("exposer={}",exposer );
        }
    }
    @Test
    public void testExcuteSeckillByProducer(){
        long seckillId = 1003l;
        long userPhone = 15012636587l;
        Exposer exposer = seckillService.getSeckillUrlOrCurrentTime(seckillId);
        if (exposer.isExpose()){
            String md5 = exposer.getMd5();
            ExcuteseckillResult excuteseckillResult = seckillService.excuteSeckillByProducer(seckillId, md5, userPhone);
            logger.info(excuteseckillResult.getStateInfo());

        }

    }

}