package com.tlnb.dto;

/**
 * @Description: 暴露秒杀地址的数据传输对象
 * @Auther: TianLin
 * @Date: 2019/03/24 024 11:59
 */
public class Exposer {

    //是否暴露秒杀地址
    private boolean isExpose;

    //加密方式
    private String md5;

    //秒杀商品id
    private long seckillId;

    //系统当前时间（毫秒）
    private long now;

    //开启时间
    private long start;

    //结束时间
    private long end;

    public Exposer(boolean isExpose, String md5, long seckillId) {
        this.isExpose = isExpose;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean isExpose, long seckillId, long now, long start, long end) {
        this.isExpose = isExpose;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean isExpose, long seckillId) {
        this.isExpose = isExpose;
        this.seckillId = seckillId;
    }

    public boolean isExpose() {
        return isExpose;
    }

    public void setExpose(boolean expose) {
        isExpose = expose;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "isExpose=" + isExpose +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
