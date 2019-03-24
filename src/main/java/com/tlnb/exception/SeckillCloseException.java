package com.tlnb.exception;

/**
 * @Description: 秒杀异常
 * @Auther: TianLin
 * @Date: 2019/03/24 024 15:36
 */
public class SeckillCloseException  extends SeckillException{
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}

