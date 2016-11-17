<%--
  Created by IntelliJ IDEA.
  User: aning
  Date: 16/6/1
  Time: 下午3:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="${ctx}/static/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <script src="${ctx}/static/bootstrap3/js/jquery-1.11.1.min.js"></script>
    <script src="${ctx}/static/bootstrap3/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse"
                data-target="#example-navbar-collapse">
            <span class="sr-only">切换导航</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">画之都</a>
    </div>
    <div class="collapse navbar-collapse" id="example-navbar-collapse">
        <ul class="nav navbar-nav">
            <li><a href="${ctx}/product/list">产品信息管理</a></li>
            <li class="active"><a href="${ctx}/user/list">用户信息管理</a></li>
            <li><a href="${ctx}/qrcode/list">扫码信息管理</a></li>
        </ul>
    </div>
</nav>
<c:if test="${empty requestScope.users}">
    没有任何用户信息
</c:if>
<c:if test="${!empty requestScope.users}">
    <table class="table table-hover">
        <caption>用户信息列表</caption>
        <thead>
        <tr>
            <th>ID</th>
            <th>用户名</th>
            <th>部门</th>
            <th>用户类型</th>
            <th>登录名</th>
            <th>登录密码</th>
            <th>状态</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${requestScope.users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.userName}</td>
                <td>${user.userDepartment}</td>
                <td>${user.userType}</td>
                <td>${user.logInName}</td>
                <td>${user.userPassword}</td>
                <td>${user.status}</td>
                <td>
                    <a href="${ctx}/user/toupdate?userId=${user.id}" class="btn btn-info btn-xs" role="button">
                        更改
                    </a>
                </td>
                <td>
                    <a href="${ctx}/user/del?userId=${user.id}" class="btn btn-danger btn-xs" role="button">
                        删除
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table><br/>

    <a href="${ctx}/user/input" class="btn btn-primary btn-lg" role="button">
        添加用户
    </a>

</c:if>
</body>
</html>