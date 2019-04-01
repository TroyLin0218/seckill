package com.tlnb.web;

import com.tlnb.dto.ExcuteseckillResult;
import com.tlnb.dto.Exposer;
import com.tlnb.dto.SeckillResult;
import com.tlnb.entity.Seckill;
import com.tlnb.enums.SeckillStaticEnum;
import com.tlnb.exception.RepeatSeckillException;
import com.tlnb.exception.SeckillCloseException;
import com.tlnb.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Description: 秒杀控制器
 * @Auther: TianLin
 * @Date: 2019/03/25 025 11:01
 */
@Controller
@RequestMapping("/seckill")//url  /模块/资源/{id}/处理  /seckill/list
public class SeckillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;

    /**
     * 获取秒杀列表页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String seckillList(Model model) {
        //获取列表页
        List<Seckill> seckillList = seckillService.getSeckillList();
        //list.jsp= jsp骨架+数据（model）
        System.out.println(seckillList);
        model.addAttribute("seckillList", seckillList);
        /*
        * 在Spring-web中配置了
        <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
            <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
            <property name="prefix" value="/WEB-INFO/jsp"/>
            <property name="suffix" value=".jsp"/>
        </bean>
        "list"就相当于/WEB-INFO/jsp/list.jsp
        */
        return "list";
    }

    /**
     * 获取秒杀某个商品的详情页
     *
     * @return
     */
    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            //如果id为空重定向到列表页
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getSeckillById(seckillId);
        if (seckill == null) {
            //如果id不存在或者id对应的秒杀商品不存在
            return "forward:/seckill/list";
        }
        //否则添加到页面数据中
        System.out.println("startTime:" + seckill.getStartTime().getTime());
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    /**
     * ajax请求，暴露秒杀地址
     *
     * @param seckillId
     * @return
     */
    @RequestMapping(value = "/{seckillId}/exposer/", method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        Exposer exposer;
        try {
            exposer = seckillService.getSeckillUrlOrCurrentTime(seckillId);
            System.out.println(exposer);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println(false);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    /**
     * 执行秒杀
     *
     * @param seckillId
     * @param md5
     * @param userPhone
     * @return
     */
    @RequestMapping(value = "/{seckillId}/{md5}/", method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<ExcuteseckillResult> excute(@PathVariable("seckillId") Long seckillId,
                                                     @PathVariable("md5") String md5,
                                                     @CookieValue(value = "killPhone", required = false) Long userPhone) {
        if (userPhone == null) {
            return new SeckillResult<>(true, "未注册");
        }
        ExcuteseckillResult excuteseckillResult;
        try {
            //秒杀成功
            /*excuteseckillResult = seckillService.excuteSeckill(seckillId, md5, userPhone);*/
            //通过调用存储过程达到优化
            excuteseckillResult = seckillService.excuteSeckillByProducer(seckillId, md5, userPhone);
            return new SeckillResult<ExcuteseckillResult>(true, excuteseckillResult);
        } catch (RepeatSeckillException e) {
            //重复秒杀
            excuteseckillResult = new ExcuteseckillResult(seckillId, SeckillStaticEnum.REPEAT_KILL);
            return new SeckillResult<ExcuteseckillResult>(true, excuteseckillResult);
        } catch (SeckillCloseException e) {
            //秒杀结束
            excuteseckillResult = new ExcuteseckillResult(seckillId, SeckillStaticEnum.END);
            return new SeckillResult<ExcuteseckillResult>(true, excuteseckillResult);
        } catch (Exception e) {
            //其他异常
            excuteseckillResult = new ExcuteseckillResult(seckillId, SeckillStaticEnum.INNER＿ERROE);
            return new SeckillResult<ExcuteseckillResult>(true, excuteseckillResult);
        }
    }

    /**
     * 获取系统当前时间
     *
     * @return
     */
    @RequestMapping(value = "/time/current/", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> currentTime() {
        Date currentTime = new Date();
        SeckillResult<Long> result = new SeckillResult<>(true, currentTime.getTime());
        return result;
    }
}

