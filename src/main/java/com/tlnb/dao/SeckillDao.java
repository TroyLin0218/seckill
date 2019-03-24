package com.tlnb.dao;

import com.tlnb.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Description: TODO
 * @Auther: TianLin
 * @Date: 2019/03/23 023 15:47
 */
public interface SeckillDao {
    /**
     *  减库存
     * @param seckillId 要秒杀的商品id
     * @param killTime 执行秒杀的时间
     * @return
     */
    int reduceNumber(@Param("seckillId")long seckillId, @Param("killTime")Date killTime);

    /**
     * 根据商品id查询
     * @param seckillId 要查询的商品id
     * @return
     */
    Seckill queryById(@Param("seckillId") long seckillId);

    /**
     * 查询所有商品
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit")int limit);
}
