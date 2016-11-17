<%--
  Created by IntelliJ IDEA.
  User: aning
  Date: 16/5/28
  Time: 下午7:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="static/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <script src="static/bootstrap3/js/jquery-1.11.1.min.js"></script>
    <script src="static/bootstrap3/js/bootstrap.min.js"></script>
    <style type="text/css">
        html,body,table{background-color:#f5f5f5;width:100%;text-align:center;}.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:20px;color:#0663a2;}
        .form-signin{position:relative;text-align:left;width:300px;padding:25px 29px 29px;margin:0 auto 20px;background-color:#fff;border:1px solid #e5e5e5;
            -webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);}
        .form-signin .checkbox{margin-bottom:10px;color:#0663a2;} .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
        .form-signin .input-block-level{font-size:16px;height:auto;margin-bottom:15px;padding:7px;*width:283px;*padding-bottom:0;_padding:7px 7px 9px 7px;}
        .form-signin .btn.btn-large{font-size:16px;} .form-signin #themeSwitch{position:absolute;right:15px;bottom:10px;}
        .form-signin div.validateCode {padding-bottom:15px;} .mid{vertical-align:middle;}
        .header{height:80px;padding-top:20px;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
        label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}
    </style>
</head>
<body>
<h1 class="form-signin-heading">登录</h1>
<form id="loginForm" class="form-signin" action="user/login" method="post">
    <label class="input-label" for="logInName">Username</label>
    <input type="text" id="logInName" name="logInName" class="input-block-level required" value="admin">
    <label class="input-label" for="password">Password</label>
    <input type="password" id="password" name="password" class="input-block-level required">
    <input class="btn btn-large btn-primary" type="submit" value="登 录"/>&nbsp;&nbsp;


</form>
</body>
</html>
