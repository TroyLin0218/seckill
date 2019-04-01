package com.tlnb.dao.cache;

import com.dyuproject.protostuff.ByteString;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.tlnb.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Description: 使用redis优化后端
 * @Auther: TianLin
 * @Date: 2019/03/30 030 10:35
 */
public class RedisDao {
    //日志对象
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //定义schema，为protostuff准本
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    //jedis的连接池
    private final JedisPool jedisPool;

    /**
     * 连接池的构造方法
     *
     * @param ip
     * @param port
     */
    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    /**
     * 根据id从redis中获取seckill
     *
     * @param seckillId id
     * @return
     */
    public Seckill getSeckill(Long seckillId) {
        try {
            //类似于数据库链接池中获取一个连接
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                //redis或者jedis都没有实现内部序列化
                //get->byte[]->反序列化->Object(jedis)
                //采用自定义序列化->protostuff ->要求对象是pojo（需要get/set方法）
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    //如果不为空，反序列化获得对象
                    //空对象
                    Seckill seckill = schema.newMessage();
                    //mergeFrom()根据字节数组bytes和schema对象把要反序列化的对象反序列化后填入空对象中
                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                    return seckill;
                }
            } finally {
                //关闭连接
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 把seckill序列化后存入redis中
     * Object -> 序列化 -> byte[]
     *
     * @param seckill seckill对象
     * @return
     */
    public String putSeckill(Seckill seckill) {
        Jedis jedis = jedisPool.getResource();
        try {
            String key = "seckill:" + seckill.getSeckillId();
            /**
             * toByteArray(arg1,arg2,arg3)参数
             * arg1:需要反序列化的对象
             * arg2:需要反序列化对象的结构
             * arg3:缓冲器
             */
            byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            //缓存超时时间，单位是秒
            int timeout = 60*60;
            String resuult = jedis.setex(key.getBytes(), timeout, bytes);
            return resuult;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        }
        return null;
    }
}
