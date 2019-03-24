package com.tlnb.enums;

/**
 * @Description: 定义秒杀状态的信息
 * @Auther: TianLin
 * @Date: 2019/03/24 024 16:48
 */
public enum SeckillStaticEnum {
    SUCCESS(1,"秒杀成功！"),
    END(0,"秒杀结束！"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER＿ERROE(-2,"系统错误！"),
    DATA_REWRITE(-3,"数据篡改");

    private int state;

    private String stateInfo;

    SeckillStaticEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillStaticEnum stateOf(int index) {
        for (SeckillStaticEnum state : values()) {
            if (state.getState() == index)
                return state;
        }
        return null;
    }
}
