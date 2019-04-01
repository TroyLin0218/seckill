package com.tlnb.dto;

/**
 * @Description: web层的秒杀数据传输对象，封装所有Ajax请求返回的json数据
 * @Auther: TianLin
 * @Date: 2019/03/25 025 12:01
 */

public class SeckillResult<T> {

    //是否秒杀成功
    private boolean success;

    //秒杀成功的数据
    private T data;

    //出错信息
    private String error;

    /**
     * 成功构造器
     * @param success  true
     * @param data
     */
    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    /**
     * 失败构造器
     * @param success false
     * @param error
     */
    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccuss() {
        return success;
    }

    public void setSuccuss(boolean succuss) {
        this.success = succuss;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "SeckillResult{" +
                "success=" + success +
                ", data=" + data +
                ", error='" + error + '\'' +
                '}';
    }
}
