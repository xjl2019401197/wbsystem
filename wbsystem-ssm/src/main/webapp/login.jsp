<%@ page import="com.example.wbsystem_ssm.redis.JedisUtil" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="com.example.wbsystem_ssm.entity.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>登录页面</title>
    <meta name="keywords" content="登录页面">
    <meta name="description" content="登录页面">

    <link rel="shortcut icon" href="../favicon.ico">
    <link href="./static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="./static/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="./static/css/animate.css" rel="stylesheet">
    <link href="./static/css/style.css?v=4.1.0" rel="stylesheet">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->
    <link href="./static/js/layui/css/layui.css" rel="stylesheet">
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>
</head>

<body class="gray-bg">

<div class="middle-box text-center loginscreen  animated fadeInDown">
    <div>
        <!--            <div>-->

        <!--                <h1 class="logo-name">H+</h1>-->

        <!--            </div>-->
        <h3>欢迎登陆 网吧会员管理系统</h3>

        <form class="m-t" role="form" action="##">
            <div class="form-group">
                <input type="text" id="account" class="form-control" placeholder="账号" required="">
            </div>
            <div class="form-group">
                <input type="password" id="password" class="form-control" placeholder="密码" required="">
            </div>
            <button id="btnLogin" type="button" class="btn btn-primary block full-width m-b">登 录</button>


            <p class="text-muted text-center">
                <a href="rePassword.html">
                    <small>忘记密码了？</small>
                </a> | <a href="register.jsp">注册一个新账号</a>
            </p>

        </form>
    </div>
</div>

<!-- 全局js -->
<script src="./static/js/jquery.min.js?v=2.1.4"></script>
<script src="./static/js/bootstrap.min.js?v=3.3.6"></script>

<script src="./static/js/layui/layui.all.js"></script>

<script>
    window.onkeypress = function () {
        if (event.keyCode == 13) {
            $("#btnLogin").click();
        }
    };

    layui.use(['layer', 'form'], function () {
        var layer = layui.layer
            , form = layui.form;
    });

    var flag = false;

    $('#btnLogin').click(function () {
        //账号
        var account = $('#account').val().trim();
        var pwd = $('#password').val().trim();

        if (account.length < 1) {
            layer.msg('用户名不能为空!');
            return;
        }
        if (password.length < 1) {
            layer.msg('密码不能为空!');
            return;
        }
        $.ajax({
            url: "userLogin?account=" + account + "&pwd=" + pwd,
            type: "get",
            success: function (data) {
                if (data.data === "0") {
                    layer.msg('账户不存在', {
                        icon: 5,
                        time: 1000 //1秒关闭（如果不配置，默认是3秒）
                    });
                } else if (data.data === "1") {
                    layer.msg('密码错误', {
                        icon: 5,
                        time: 1000 //1秒关闭（如果不配置，默认是3秒）
                    });
                } else if (data.data === "2") {
                    layer.msg('登陆成功', {
                        icon: 6,
                        time: 1000 //1秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        var type = null;
                        $.ajax({
                            url: "user/getUserBySession",
                            type: "get",
                            async: false,
                            success: function (data) {
                                type = data['userType']
                            }
                        });
                        if (type == 0) location.href = "manager.html"
                        else if (type == 1) location.href = "index.html"
                        else location.href = "index.jsp";


                    });
                } else {
                    layer.msg('系统出错', {
                        icon: 5,
                        time: 1000 //1秒关闭（如果不配置，默认是3秒）
                    });
                }
            }
        });
    });
</script>
</body>

</html>
