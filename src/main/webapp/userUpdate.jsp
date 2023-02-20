<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>修改页面</title>
    <meta name="keywords" content="修改页面">
    <meta name="description" content="修改页面">

    <link rel="shortcut icon" href="../favicon.ico"> <link href="./static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="./static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="./static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="./static/css/animate.css" rel="stylesheet">
    <link href="./static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="./static/js/layui/css/layui.css" rel="stylesheet">
<%--    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>--%>

</head>

<body class="gray-bg">

    <div class="middle-box text-center loginscreen   animated fadeInDown" id="vue">
        <div>
<!--            <div>-->

<!--                <h1 class="logo-name">签</h1>-->

<!--            </div>-->
            <h3>修改一个账户</h3>
            <form class="form-horizontal" role="form" action="##">
                <div class="form-group" style="display: none">
                    <input type="text" id="userId" class="form-control" placeholder="" required="" value="" v-model="user.userId">
                </div>
                <div class="form-group">
                    <input type="text" id="userName" class="form-control" placeholder="请输入用户名" required="" onBlur="checkAccount()" v-model="user.userName">
                </div>
                <div class="form-group">
                    <input type="text" id="nickname" class="form-control" placeholder="请输入昵称" required="" v-model="user.nickname">
                </div>
                <div class="form-group">
                    <input type="password" id="password" class="form-control" placeholder="请输入密码" required="" v-model="user.password">
                </div>
                <div class="form-group">
                    <input type="password" id="rePassword"   class="form-control" placeholder="请再次输入密码" required="" v-model="user.rePassword">
                </div>
                <div class="form-group">
                    <input type="text" id="phone" class="form-control" placeholder="请输入手机号(非必填)" required="" v-model="user.phone">
                </div>
                <div class="form-group">
                    <input type="text" id="qq" class="form-control" placeholder="请输入QQ(非必填)" required="" v-model="user.qq">
                </div>
                <div class="form-group">
                    <input type="text" id="wxchat" class="form-control" placeholder="请输入微信号(非必填)" required="" v-model="user.wxchat">
                </div>
                <div class="form-group">
                    <input type="text" id="email" class="form-control" placeholder="请输入邮箱(非必填)" required="" v-model="user.email">
                </div>
<%--                <div class="form-group" style="margin-bottom: 0px;">--%>
<%--                    <label class="col-sm-2 control-label">Select</label>--%>

<%--                    <div class="col-sm-10">--%>
<%--                        <select class="form-control m-b" name="type" id="type">--%>
<%--                            <option value="0">普通用户</option>--%>
<%--<!--                            <option value="1">管理员</option>-->--%>
<%--                        </select>--%>
<%--                    </div>--%>
<%--                </div>--%>
                <div class="form-group text-left">
                    <div class="checkbox i-checks">
                        <label class="no-padding">
                            <input id="agreement" type="checkbox"><i></i> 我同意修改协议</label>
                    </div>
                </div>
                <button id="btnLogin" type="button" class="btn btn-primary block full-width m-b">修改</button>

<%--                <p class="text-muted text-center"><small>已经有账户了？</small><a href="login.jsp">点此登录</a>--%>
<%--                </p>--%>

            </form>
        </div>
    </div>

    <!-- 全局js -->
    <script src="./static/js/jquery.min.js?v=2.1.4"></script>
    <script src="./static/js/bootstrap.min.js?v=3.3.6"></script>
    <!-- iCheck -->
    <script src="./static/js/plugins/iCheck/icheck.min.js"></script>
    <script src="./static/js/layui/layui.all.js"></script>
    <script src="./static/js/vue.2.6.11.js"></script>
    <script src="./static/js/axios.js"></script>

    <script>
        var vue = new Vue({
            el: '#vue',
            data: {
                user: [],
                msg: ""
            },
            methods: {
                getInfo() {

                    axios.get('/userInfo').then(response => {
                        console.log(response)
                        this.user = eval(response.data)
                    })
                }

            },
            mounted() {
                // thigetInfo()
                this.getInfo()
            },
        })
    </script>
    <script>
        $(document).ready(function () {
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
        });
    </script>

    <script>
        window.onkeypress = function () {
            if (event.keyCode == 13) {
                $("#btnLogin").click();
            }
        };

        layui.use(['layer', 'form'], function(){
            var layer = layui.layer
                ,form = layui.form;
        });

        var flag = false;

        function checkAccount(){
            //账号
            var account = $('#account').val();

            $.ajax({
                url: "user/checkAccount.do",
                type: "post",
                data: {
                    "account": account
                },
                success: function (data) {
                    if(data === true){
                        layer.msg('账号已存在', {
                            icon: 5,
                            time: 2000 //1秒关闭（如果不配置，默认是3秒）
                        }, function(){
                            flag = true;
                        });
                    }else{
                        layer.msg('账号可用', {
                            icon: 6,
                            time: 2000 //1秒关闭（如果不配置，默认是3秒）
                        }, function(){
                            flag = false;
                        });
                    }
                }
            });
        }

        $('#btnLogin').click(function () {
            //账号
            var userId = $('#userId').val();
            var userName = $('#userName').val();
            var nickname = $('#nickname').val();
            var password = $('#password').val();
            var rePassword = $('#rePassword').val();
            var phone = $('#phone').val();
            var qq = $('#qq').val();
            var wxchat = $('#wxchat').val();
            var email = $('#email').val();
            var sex = $('#sex  option:selected').val();
            // var agreement = $('#agreement').is(':checked');


            if (userName.length < 1 || password.length < 1 || rePassword.length < 1) {
                layer.msg('用户名或密码不能为空!');
                return;
            }

            if (password !== rePassword) {
                layer.msg('两次输入密码不一致!');
                return;
            }

            // if (!agreement) {
            //     layer.msg('注册协议未同意!');
            //     return;
            // }

            // if (flag) {
            //     layer.msg('账号已存在!');
            //     return;
            // }

            $.ajax({
                url: "/userUpdate?userId="+userId+"&userName="+userName+"&nickname="+nickname+"&pwd="+password+"&phone="+phone+"&qq="+qq
                    +"&wxchat="+wxchat+"&email="+email,
                type: "get",
                success: function (data) {
                    if(data === "true"){
                        layer.msg('修改成功', {
                            icon: 6,
                            time: 2000 //1秒关闭（如果不配置，默认是3秒）
                        }, function(){
                            window.location.href = "login.jsp";
                        });
                    }else{
                        layer.msg('修改失败', {
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
