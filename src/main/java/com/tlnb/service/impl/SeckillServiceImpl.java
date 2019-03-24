package com.tlnb.service.impl;

import com.tlnb.dao.SeckillDao;
import com.tlnb.dao.SeckillSuccessDao;
import com.tlnb.dto.ExcuteseckillResult;
import com.tlnb.dto.Exposer;
import com.tlnb.entity.Seckill;
import com.tlnb.entity.SeckillSuccess;
import com.tlnb.enums.SeckillStaticEnum;
import com.tlnb.exception.RepeatSeckillException;
import com.tlnb.exception.SeckillCloseException;
import com.tlnb.exception.SeckillException;
import com.tlnb.service.SeckillService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @Description: 实现service层的业务
 * @Auther: TianLin
 * @Date: 2019/03/24 024 15:48
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    //获取日志对象
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SeckillSuccessDao seckillSuccessDao;

    //md5的盐
    private final String salt = "(*73rkcshsdvbUIHKBKGU@#$%^&*fehifUIGVYG?><M";

    /**
     * 根据id计算MD5
     *
     * @param seckillId
     * @return
     */
    private String getMD5(long seckillId) {
        String base = seckillId + "?" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    @Override
    public Seckill getSeckillById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer getSeckillUrlOrCurrentTime(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            //没有这个商品
            return new Exposer(false, seckillId);
        }

        long startTime = seckill.getStartTime().getTime();
        long endTime = seckill.getEndTime().getTime();
        //系统当前时间
        long currentTime = new Date().getTime();
        if (startTime > currentTime || endTime < currentTime) {
            //秒杀未开启
            return new Exposer(false, seckillId, currentTime, startTime, endTime);
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Override
    @Transactional//声明需要Spring管理事务
    public ExcuteseckillResult excuteSeckill(long seckillId, String md5, long userPhone)
            throws RepeatSeckillException, SeckillCloseException, SeckillException {

        //判断是否正常
        if (md5 == null || !getMD5(seckillId).equals(md5)) {
            throw new SeckillException("seckill data rewrite!");
        }

        //执行秒杀=减库存+记录购买明细
        Date currentTime = new Date();

        try {
            //1 减库存
            int reduceNumber = seckillDao.reduceNumber(seckillId, currentTime);
            if (reduceNumber <= 0) {
                throw new SeckillCloseException("seckill closed!");
            } else {
                //2 记录购买行为 采用insert ignore插入，主键冲突不报错返回0，此时表示重复秒杀
                int insertNumber = seckillSuccessDao.insertSeckillSuccess(seckillId, userPhone);
                if (insertNumber == 0) {
                    //重复秒杀
                    throw new RepeatSeckillException("repeat seckill!");
                } else {
                    //秒杀成功
                    SeckillSuccess seckillSuccess =
                            seckillSuccessDao.queryBySeckillIdWithSeckillSuccess(seckillId, userPhone);
                    return new ExcuteseckillResult(seckillId, SeckillStaticEnum.SUCCESS, seckillSuccess);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatSeckillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //编译异常转换为运行时异常
            throw new SeckillException("seckill inner error!" + e.getMessage(), e.getCause());
        }
    }
}
