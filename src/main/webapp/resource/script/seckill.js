var seckill = {
    //封装秒杀的url
    URL: {
        //获取系统当前时间的地址
        timeNowURL: function () {
            return "/seckill/time/current/";
        },
        //获取秒杀地址
        exposerURL: function (seckillId) {
            return "/seckill/" + seckillId + "/exposer/";
        },
        //执行秒杀的地址
        excuteKillURL: function (seckillId, md5) {
            return "/seckill/" + seckillId + "/" + md5 + "/";
        }
    },
    //校验手机号
    validatePhone: function (phone) {
        //手机号存在 长度是11               非不是数字
        if (phone && phone.length == 11 && !isNaN(phone)) {
            //手机验证通过
            return true;
        } else {
            //手机校验不通过
            return false;
        }

    },
    //处理秒杀逻辑
    handleSeckill: function (seckillId, node) {
        //添加秒杀按钮
        /*node.html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');*/
        node.hide().html('<button class = "btn-primary btn-lg"  id="killbtn">立即秒杀</button>');
        //请求秒杀地址,并完成秒杀操作
        $.post(seckill.URL.exposerURL(seckillId), function (result) {
            console.log("请求秒杀地址的result：");/*todo*/
            console.log(result);
            var exposer = result['data'];
            console.log("exposer：");
            console.log(exposer);
            var md5 = exposer['md5'];
            if (result && result['succuss']) {/*succuss: true*/
                console.log("是否开启秒杀："+exposer['expose']);
                if (exposer['expose']) {
                    //开启秒杀
                    //获取秒杀地址
                    var killURL = seckill.URL.excuteKillURL(seckillId, md5);
                    console.log(killURL);/!*todo*!/
                    /**
                     * 不使用click事件的原因：click是一直绑定元素
                     * one事件只执行一次就不再绑定元素,连续点击无效
                     */
                    $("#killbtn").one("click", function () {
                        console.log("执行秒杀");/*todo*/
                        //执行秒杀请求
                        //1；禁用按钮
                        $(this).addClass('disabled');
                        //2；发送请求，执行秒杀
                        $.post(killURL, {},function (result1) {
                            console.log("执行秒杀的result：");
                            console.log(result1)
                            //判断回调是否成功
                            if (result1 && result1['succuss']) {
                                var killResult = result1['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //3;显示秒杀结果
                                node.html('<span class="label label-success" >' + stateInfo + '</span>').show();
                            }
                        });
                    });
                    node.show();
                } else {
                    //未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //重新计算计时逻辑
                    seckill.countDown(seckillId, now, start, end);
                }
            } else {
                console.log("result:"+result);
            }
        })

    },
    //倒计时函数
    countDown: function (seckillId, timeNow, startTime, endTime) {
        var seckillbox = $("#seckill-box");
        if (timeNow > endTime) {
            //秒杀已结束，显示已结束
            seckillbox.html('秒杀已结束！');
        } else if (timeNow < startTime) {
            //秒杀未开始，显示倒计时
            var killTime = new Date(startTime + 1000);
            seckillbox.countdown(killTime, function (event) {
                var formatTime = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                seckillbox.html(formatTime);
            }).on('finish.conuntdown', function () {
                //时间跑完之后回调，获取秒杀地址，秒杀开始
                seckill.handleSeckill(seckillId, seckillbox);
            });
        } else {
            console.log("调用秒杀处理函数")
            //秒杀已开启，调用处理秒杀函数
            seckill.handleSeckill(seckillId, seckillbox);
            /*seckill.handleSeckill(seckillId,seckillbox);*/
            /*seckillbox.html('<a class="btn btn-info" href="#">立即秒杀</a>');*/
        }
    },
    //秒杀的详情页
    detail: {
        init(params) {
            //1;手机校验
            var phone = $.cookie('killPhone');
            //如果cookie中没有用户手机号
            if (!seckill.validatePhone(phone)) {
                //验证无法通过，弹出手机号输入框
                var killPhoneModal = $("#killPhoneModal");
                //显示弹出层，禁用关闭
                killPhoneModal.modal({
                    show: true,//显示弹出层
                    backdrop: 'static',//禁止位置关闭
                    keybord: false//关闭键盘事件，防止使用esc退出弹窗
                });
                $("#killPhoneBtn").click(function () {
                    var inputPhone = $("#killPhoneKey").val();
                    if (seckill.validatePhone(inputPhone)) {
                        //校验通过，写入cookie
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        //重新加载
                        window.location.reload();
                    } else {
                        $("#killPhoneMessage").hide().html('<label class = "lable">手机号有误！</label>').show(300);
                    }
                });
            }
            //2；计时交互
            var seckillId = params['seckillId'];//cookie中的手机号
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            //获取系统当前时间，为计时交互做准备
            $.get(seckill.URL.timeNowURL(), function (result) {
                //result["success"]是判断回调函数是否获取成功，成功返回true否则返回false
                if (result && result["succuss"]) {
                    //获取回调函数的返回值
                    var timeNow = result["data"];
                    //调用倒计时函数
                    seckill.countDown(seckillId, timeNow, startTime, endTime);
                } else {
                    var seckillbox = $("#seckill-box");
                    seckillbox.html('服务异常！');
                }

            });
        }
    }
}