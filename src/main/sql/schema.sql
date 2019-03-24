-- 数据库脚本
CREATE DATABASE seckill;
-- 使用数据库
use seckill;
-- 创建秒杀库存表
DROP TABLE IF EXISTS seckill;
CREATE TABLE seckill(
  `seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT ''商品库存id'',
  `name` varchar (120) NOT NULL COMMENT ''商品名称'',
  `number` int NOT NULL COMMENT ''库存数量'',
  `start_time` timestamp NOT NULL COMMENT ''秒杀开启时间'',
  `end_time` timestamp NOT NULL COMMENT ''秒杀结束时间'',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT ''秒杀创建时间'',
  PRIMARY KEY (seckill_id),
  /*建立索引*/
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT ''秒杀库存表''
  -- 初始化数据
  insert into
  seckill(name,number,start_time,end_time)
values
(''2000元秒杀iPhone8'',50,''2019-3-23 00:00:00'',''2019-3-24 00:00:00''),
(''1200元秒杀Mix2'',100,''2019-3-23 00:00:00'',''2019-3-24 00:00:00''),
(''999元秒杀小米6'',50,''2019-3-23 00:00:00'',''2019-3-24 00:00:00''),
(''399秒杀慕课网高并发课程'',50,''2019-3-23 00:00:00'',''2019-3-24 00:00:00'');

-- 描述成功明细表
DROP TABLE IF EXISTS seckill_success;
CREATE TABLE seckill_success(
`seckill_id` bigint NOT NULL COMMENT ''秒杀商品id'',
`user_phone` bigint NOT NULL COMMENT ''用户手机号'',
`state` tinyint NOT NULL DEFAULT -1 COMMENT ''状态标识 -1：无效 0：秒杀成功 1：未支付 2：已支付'',
`create_time` timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT ''创建时间'',
primary key (seckill_id,user_phone),/*使用联合主键*/
/*建立索引*/
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT ''秒杀成功明细表'';

