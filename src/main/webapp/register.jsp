<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>注册页面</title>
    <meta name="keywords" content="注册页面">
    <meta name="description" content="注册页面">

    <link rel="shortcut icon" href="../favicon.ico"> <link href="./static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="./static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="./static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="./static/css/animate.css" rel="stylesheet">
    <link href="./static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="./static/js/layui/css/layui.css" rel="stylesheet">
    <%--    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>--%>

</head>

<body class="gray-bg">

<div class="middle-box text-center loginscreen   animated fadeInDown">
    <div>
        <!--            <div>-->

        <!--                <h1 class="logo-name">签</h1>-->

        <!--            </div>-->
        <h3 style="margin-bottom: 20px">注册新用户</h3>

        <form class="form-horizontal" role="form" action="##">
            <div class="form-group">
                <input type="text" id="idCard"   class="form-control" placeholder="请输入身份证" required="" onBlur="checkAccount()">
            </div>

            <div class="form-group">
                <input type="text" id="phone" class="form-control" placeholder="请输入手机号" required="" onBlur="checkModbile()">
            </div>

            <%--                <div class="form-group">--%>
            <%--                    <div class="col-sm-12">--%>
            <%--                        <label class="col-sm-2 control-label">性别：</label>--%>
            <%--                        <div class="col-xs-10">--%>
            <%--                            <select name="state" v-model="sex" id="sex"--%>
            <%--                                    class="form-control input-sm valid">--%>
            <%--                                <option value="0">女</option>--%>
            <%--                                <option value="1">男</option>--%>
            <%--                                <option value="2">保密</option>--%>
            <%--                            </select>--%>
            <%--                        </div>--%>
            <%--                    </div>--%>
            <%--                </div>--%>
            <div class="form-group" style="margin-bottom: 0px;">
                <label class="col-sm-3 control-label">性别：</label>

                <div class="col-sm-9">
                    <select class="form-control m-b" name="state" id="sex" v-model="sex" >
                        <option value="0">女</option>
                        <option value="1">男</option>
                        <option value="2">保密</option>
                    </select>
                </div>
            </div>
            <div class="form-group" style="margin-bottom: 0px;">
                <label class="col-sm-3 control-label">类型：</label>

                <div class="col-sm-9">
                    <select class="form-control m-b" name="type" id="type">
                        <option value="1">会员卡</option>
                        <option value="0">临时卡</option>
                    </select>
                </div>
            </div>

            <button id="btnLogin" type="button" class="btn btn-primary block full-width m-b">注 册</button>

        </form>
    </div>
</div>

<!-- 全局js -->
<script src="./static/js/jquery.min.js?v=2.1.4"></script>
<script src="./static/js/bootstrap.min.js?v=3.3.6"></script>
<!-- iCheck -->
<script src="./static/js/plugins/iCheck/icheck.min.js"></script>
<script src="./static/js/layui/layui.all.js"></script>
<script>
    $(document).ready(function () {
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
    });
</script>

<script>
    // window.onkeypress = function () {
    //     if (event.keyCode == 13) {
    //         $("#btnLogin").click();
    //     }
    // };

    layui.use(['layer', 'form'], function(){
        var layer = layui.layer
            ,form = layui.form;
    });

    var flag = false;

    function IDCardCheck() {
        //账号
        var num = $('#idCard').val();
        num = num.toUpperCase();
        //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
        if (!(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(num))){
            layer.msg('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。', {
                icon: 5,
                time: 2000 //1秒关闭（如果不配置，默认是3秒）
            });
            return false;
        }
        //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
        //下面分别分析出生日期和校验位
        var len, re;
        len = num.length;
        if (len == 15) {
            re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
            var arrSplit = num.match(re);

            //检查生日日期是否正确
            var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
            var bGoodDay;
            bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
            if (!bGoodDay) {
                layer.msg('输入的身份证号里出生日期不对！', {
                    icon: 5,
                    time: 2000 //1秒关闭（如果不配置，默认是3秒）
                }, function(){
                    flag = true;
                });
                return false;
            }
            else {
                //将15位身份证转成18位
                //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
                // var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
                // var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
                // var nTemp = 0, i;
                // num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
                // for (i = 0; i < 17; i++) {
                //     nTemp += num.substr(i, 1) * arrInt[i];
                // }
                // num += arrCh[nTemp % 11];
                return true;
            }
        }
        if (len == 18) {
            re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
            var arrSplit = num.match(re);

            //检查生日日期是否正确
            var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
            var bGoodDay;
            bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
            if (!bGoodDay) {
                // alert(dtmBirth.getYear());
                //  alert(arrSplit[2]);
                layer.msg('输入的身份证号里出生日期不对！', {
                    icon: 5,
                    time: 2000 //1秒关闭（如果不配置，默认是3秒）
                }, function(){
                    flag = true;
                });
                return false;
            }
            else {
                // //检验18位身份证的校验码是否正确。
                // //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
                // var valnum;
                // var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
                // var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
                // var nTemp = 0, i;
                // for (i = 0; i < 17; i++) {
                //     nTemp += num.substr(i, 1) * arrInt[i];
                // }
                // valnum = arrCh[nTemp % 11];
                // if (valnum != num.substr(17, 1)) {
                //     alert('18位身份证的校验码不正确！'); //应该为： + valnum
                //     return false;
                // }

                return true;
            }
        }
        return false;
    }

    function checkAccount(){
        if(IDCardCheck()) {
            //账号
            var idCard = $('#idCard').val();

            $.ajax({
                url: "/user/checkAccount",
                type: "post",
                data: {
                    "idCard": idCard
                },
                success: function (data) {
                    if (data.data === true) {
                        layer.msg('账号已存在', {
                            icon: 5,
                            time: 2000 //1秒关闭（如果不配置，默认是3秒）
                        });
                        return false;
                    } else {
                        layer.msg('账号可用', {
                            icon: 6,
                            time: 2000 //1秒关闭（如果不配置，默认是3秒）
                        });
                        return true;

                    }
                }
            });
        }else return false
    }

    //JS使用正则表达式校验电话号码
    function checkModbile() {
        var phone = $('#phone').val();
        var re = /^1[3,4,5,6,7,8,9][0-9]{9}$/;
        var result = re.test(phone);
        if(!result) {
            layer.msg('手机号码格式不正确！', {
                icon: 5,
                time: 2000 //1秒关闭（如果不配置，默认是3秒）
            });
            return false;//若手机号码格式不正确则返回false
        }
        return true;
    }


    $('#btnLogin').click(function () {
        //账号
        var idCard = $('#idCard').val();
        var phone = $('#phone').val();
        var sex = $('#sex  option:selected').val();
        var type = $('#type  option:selected').val();

        if (idCard == null || idCard.length < 1 ) {
            layer.msg('身份证不能为空!');
            return;
        }

        if (phone == null || phone.length < 1 ) {
            layer.msg('电话号码不能为空!');
            return;
        }

        if(!checkModbile() || !IDCardCheck() )return false;
        else
        $.ajax({
            url: "/userRegister?"+"&idCard="+idCard+"&phone="+phone+"&sex="+sex+"&type="+type,
            type: "get",
            async: false,
            success: function (data) {
                if(data.data === true){
                    layer.msg('注册成功', {
                        icon: 6,
                        time: 2000 //1秒关闭（如果不配置，默认是3秒）
                    }, function(){
                        location.reload();
                    });
                }else{
                    layer.msg('注册失败', {
                        icon: 5,
                        time: 2000 //1秒关闭（如果不配置，默认是3秒）
                    });
                }
            }
        });
    });
</script>

</body>

</html>
