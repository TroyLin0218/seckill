package com.tlnb.dto;

import com.tlnb.entity.SeckillSuccess;
import com.tlnb.enums.SeckillStaticEnum;

/**
 * @Description: 执行秒杀结果对象
 * @Auther: TianLin
 * @Date: 2019/03/24 024 12:14
 */
public class ExcuteseckillResult {

    //秒杀商品id
    private long seckillId;

    //秒杀状态
    private int state;

    //秒杀状态表示
    private String stateInfo;

    //秒杀成功对象
    private SeckillSuccess seckillSuccess;

    //秒杀成功构造器
    public ExcuteseckillResult(long seckillId, SeckillStaticEnum seckillStaticEnum, SeckillSuccess seckillSuccess) {
        this.seckillId = seckillId;
        this.state = seckillStaticEnum.getState();
        this.stateInfo = seckillStaticEnum.getStateInfo();
        this.seckillSuccess = seckillSuccess;
    }

    //秒杀失败构造器
    public ExcuteseckillResult(long seckillId, SeckillStaticEnum seckillStaticEnum) {
        this.seckillId = seckillId;
        this.state = seckillStaticEnum.getState();
        this.stateInfo = seckillStaticEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SeckillSuccess getSeckillSuccess() {
        return seckillSuccess;
    }

    public void setSeckillSuccess(SeckillSuccess seckillSuccess) {
        this.seckillSuccess = seckillSuccess;
    }

    @Override
    public String toString() {
        return "ExcuteseckillResult{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", seckillSuccess=" + seckillSuccess +
                '}';
    }
}

