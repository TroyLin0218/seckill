package com.tlnb.dto;

/**
 * @Description: 暴露秒杀地址的数据传输对象
 * @Auther: TianLin
 * @Date: 2019/03/24 024 11:59
 */
public class Exposer {

    //是否暴露秒杀地址
    private boolean expose;

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

    public Exposer(boolean expose, String md5, long seckillId) {
        this.expose = expose;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    /**
     * 秒杀未开启
     *
     * @param expose
     * @param seckillId
     * @param now
     * @param start
     * @param end
     */
    public Exposer(boolean expose, long seckillId, long now, long start, long end) {
        this.expose = expose;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean expose, long seckillId) {
        this.expose = expose;
        this.seckillId = seckillId;
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

    public boolean isExpose() {
        return expose;
    }

    public void setExpose(boolean expose) {
        this.expose = expose;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "expose=" + expose +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
