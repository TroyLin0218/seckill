package com.tlnb.dao;

import com.tlnb.entity.SeckillSuccess;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 秒杀成功明细表的接口
 * @Auther: TianLin
 * @Date: 2019/03/23 023 15:52
 */
public interface SeckillSuccessDao {
    /**
     * 插入购买明细，过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSeckillSuccess(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    /**
     * 根据秒杀id查询秒杀成功对象并携带秒杀对象实体
     * @param seckillId
     * @return
     */
    SeckillSuccess queryBySeckillIdWithSeckillSuccess(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
