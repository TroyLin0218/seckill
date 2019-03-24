package com.tlnb.exception;

/**
 * @Description: 重复秒杀异常
 * @Auther: TianLin
 * @Date: 2019/03/24 024 15:34
 */
public class RepeatSeckillException extends SeckillException {
    public RepeatSeckillException(String message) {
        super(message);
    }

    public RepeatSeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
