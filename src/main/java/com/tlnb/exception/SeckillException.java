package com.tlnb.exception;

/**
 * @Description: 秒杀相关业务异常
 * @Auther: TianLin
 * @Date: 2019/03/24 024 15:38
 */
public class SeckillException extends RuntimeException {
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
