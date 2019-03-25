package com.tlnb.dto;

/**
 * @Description: web层的秒杀数据传输对象，封装所有Ajax请求返回的json数据
 * @Auther: TianLin
 * @Date: 2019/03/25 025 12:01
 */

public class SeckillResult<T> {

    //是否秒杀成功
    private boolean succuss;

    //秒杀成功的数据
    private T data;

    //出错信息
    private String error;

    /**
     * 秒杀成功构造器
     * @param succuss  true
     * @param data
     */
    public SeckillResult(boolean succuss, T data) {
        this.succuss = succuss;
        this.data = data;
    }

    /**
     * 失败的构造器
     * @param succuss false
     * @param error
     */
    public SeckillResult(boolean succuss, String error) {
        this.succuss = succuss;
        this.error = error;
    }

    public boolean isSuccuss() {
        return succuss;
    }

    public void setSuccuss(boolean succuss) {
        this.succuss = succuss;
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
}
