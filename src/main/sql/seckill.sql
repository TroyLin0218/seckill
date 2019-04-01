-- 秒杀执行的存储过程
DROP PROCEDURE IF EXISTS execute_seckill;
DELIMITER $$ -- 把结束符转换为$$
-- 参数: in 输入参数; out 输出参数
-- 定义存储过程
CREATE PROCEDURE `seckill`.`execute_seckill`
(in v_seckill_id bigint,in v_phone bigint,
 in v_kill_time timestamp,out r_result int)
begin
  declare insert_count int default 0; -- 定义一个int类型的变量insert_conunt
  declare update_count int default 0; -- 定义一个int类型的变量insert_conunt
  start transaction ; -- 开启事务

  -- 1；插入购买明细
  insert ignore into seckill_success
  (seckill_id,user_phone,create_time)
  values (v_seckill_id,v_phone,v_kill_time);

  -- 2；获取插入结果赋值给insert_count变量
  -- row_count():返回上一条修改类型sql(delete,insert,update)的影响行数
  -- row_count()-> =0:未修改数据  >0:修改行数 <0:sql错误或未执行
  select row_count() into insert_count;
  -- 3；根据结果判断减库存提交还是直接回滚
  if (insert_count = 0) then
    rollback;   -- 1）没有修改数据，插入失败，回滚
    set r_result = -1;
  elseif(insert_count<0) then
    rollback;   -- 2）sql语句异常，回滚
    set r_result = -2;
  else          -- 3）否则insert_count肯定大于0，插入成功，修改库存
    update seckill
    set number = number -1
    where seckill_id = v_seckill_id
      and end_time > v_kill_time
      and start_time < v_kill_time
      and number >0;

    -- 4；获取更新库存结果赋值给update_count变量
    select row_count() into update_count;
    -- 5；根据结果判断提交还是回滚
    if(update_count  = 0 ) then
      rollback; -- 1）未修改数据，回滚
      set r_result=0;
    elseif (update_count < 0) then
      rollback; -- 2）sql语句异常，回滚
      set r_result = -2;
    else        -- 3） 更新成功，提交事务
      commit;
      set r_result = 1; -- 置存储过程的输出参数为1，表示执行成功
    end if;
  end if;
end $$
-- 存储过程定义完毕

-- 把结束符转换为;
delimiter ;
-- 初始化r_result值
set @r_result = -3;
-- 调用存储过程
call execute_seckill(1003,11111111111,now(),@r_result);
-- 获取结果
select @r_result;

-- 存储过程优化:
--        1:减少事务行级锁所持有时间
--		   	2:不要过度依赖存储过程,银行业使用多,互联网公司使用较少
--		   	3:简单的逻辑,可以应用存储过程
--			  4:qps:一个秒杀单6000/qps