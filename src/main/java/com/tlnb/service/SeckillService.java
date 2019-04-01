package com.tlnb.service;

import com.tlnb.dto.ExcuteseckillResult;
import com.tlnb.dto.Exposer;
import com.tlnb.entity.Seckill;
import com.tlnb.exception.RepeatSeckillException;
import com.tlnb.exception.SeckillCloseException;
import com.tlnb.exception.SeckillException;

import java.util.List;

/**
 * @Description: 秒杀业务接口
 * @Auther: TianLin
 * @Date: 2019/03/24 024 11:37
 */
public interface SeckillService {
    /**
     * 查询所有秒杀列表
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 根据id查询一个秒杀商品对象
     * @param seckillId 秒杀商品对象id
     * @return
     */
    Seckill getSeckillById(long seckillId);

    /**
     * 秒杀开启时返回秒杀地址，秒杀未开启时返回系统当前时间
     * @param seckillId 秒杀商品对象id
     * @return 返回一个dto，返回秒杀地址或者系统当前时间
     */
    Exposer getSeckillUrlOrCurrentTime(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId 秒杀商品对象id
     * @param md5 传入加密地址
     * @param userPhone 用户身份标识
     * @return 返回一个dto，表示执行秒杀的结果
     */
    ExcuteseckillResult excuteSeckill(long seckillId, String md5, long userPhone)
            throws RepeatSeckillException, SeckillCloseException, SeckillException;

    /**
     * 调用存储过程执行秒杀操作
     * @param seckillId 秒杀商品对象id
     * @param md5 传入加密地址
     * @param userPhone 用户身份标识
     * @return 返回一个dto，表示执行秒杀的结果
     */
    ExcuteseckillResult excuteSeckillByProducer(long seckillId, String md5, long userPhone);

}
