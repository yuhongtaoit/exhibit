<%--
  Created by IntelliJ IDEA.
  User: aning
  Date: 16/5/25
  Time: 下午7:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
    <title>test</title>
    <title>Exhibition</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="${ctx}/static/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/bootstrap3/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="${ctx}/static/bootstrap3/js/jquery-1.11.1.min.js"></script>
    <script src="${ctx}/static/bootstrap3/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/bootstrap3/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${ctx}/static/bootstrap3/js/locale/bootstrap-datetimepicker.zh-CN.js"
            charset="UTF-8"></script>
</head>
<body>
<div class="alert alert-success" role="alert" style="text-align: center">用户名或密码错误, 3秒后跳转!</div>
<script type="application/javascript">
    setTimeout(function(){
        window.location.href = '${ctx}';
    }, 3000);
</script>
</body>
</html>
