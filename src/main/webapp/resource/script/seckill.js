var seckill = {
    //封装秒杀的url
    URL : {},
    //校验手机号
    validatePhone : function(phone){
        //手机号存在 长度是11               非不是数字
        if(phone && phone.length==11 && !isNaN(phone)){
            //验证通过
            return true;
        }else{
            //不通过
            return false;
        }

    },
    //秒杀的详情页
    detail : {
        init(params){
            //1;手机校验
            var seckillId = params['seckillId'];//cookie中的手机号
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var phone = $.cookie('killPhone');
            //如果cookie中没有用户手机号
            if(!seckill.validatePhone(phone)){
                //验证无法通过，弹出手机号输入框
                var killPhoneModal = $("#killPhoneModal");
                killPhoneModal.modal({
                    show:true,//显示弹出层
                    backdrop:'static',//禁止位置关闭
                    keybord:false//关闭键盘事件，防止使用esc退出弹窗
                });
                $("#killPhoneModal").click(function () {
                    var inputPhone = $("#killPhoneKey").val();
                    if (seckill.validatePhone(phone)){
                        //校验通过，写入cookie
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        //重新加载
                        window.location.reload();
                    }else{

                    }
                });


            }
            //2；计时交互
        }
    },
}