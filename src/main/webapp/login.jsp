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
        <h3>欢迎登陆 网吧会员管理系统</h3>

        <form class="m-t" role="form" action="/user/login" method="post">
            <div class="form-group">
                <input type="text" name="username" id="account" class="form-control" placeholder="账号" required="">
            </div>
            <div class="form-group">
                <input type="password" name="password" id="password" class="form-control" placeholder="密码" required="">
            </div>
            <button id="btnLogin" type="submit" class="btn btn-primary block full-width m-b">登 录</button>


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

</body>

</html>
