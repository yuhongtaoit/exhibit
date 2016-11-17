<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <title>更改产品</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="${ctx}/static/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/bootstrap3/css/bootstrapValidator.css" rel="stylesheet"/>
    <script src="${ctx}/static/bootstrap3/js/jquery-1.11.1.min.js"></script>
    <script src="${ctx}/static/bootstrap3/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/bootstrap3/js/bootstrapValidator.js"></script>
</head>
<body>

<div id="mainContent" style="">
    <form:form id="userForm" class="form-horizontal" role="form" action="${ctx}/user/update"
    method="post" modelAttribute="user">
        <legend>用户基本信息</legend>
        <form:hidden path="Id" id="Id"/>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="UserName">用户名</label>
            <div class="col-xs-2">
                <form:input type="text" class="form-control input-sm" id="UserName"
                       placeholder="请输入用户名" name="UserName" path="UserName"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="UserDepartment">部门</label>
            <div class="col-xs-2">
                <form:input type="text" class="form-control input-sm" id="UserDepartment"
                       placeholder="请输入部门" name="UserDepartment" path="UserDepartment"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="UserType">用户类型</label>
            <div class="col-xs-2">
                <form:input type="text" class="form-control input-sm" id="UserType"
                       placeholder="请输入产品编号" name="UserType" path="UserType"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="LogInName">登录名</label>
            <div class="col-xs-2">
                <form:input type="text" class="form-control input-sm" id="LogInName"
                       placeholder="请输入登录名" name="LogInName" path="LogInName" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="UserPassword">登录密码</label>
            <div class="col-xs-2">
                <form:input type="text" class="form-control input-sm" id="UserPassword"
                       placeholder="请输入密码" name="UserPassword" path="UserPassword"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="Status">状态</label>
            <div class="col-xs-2">
                <form:input type="text" class="form-control input-sm" id="Status"
                       placeholder="状态" name="Status" path="Status"/>
            </div>
        </div>
        <div class="form-group" style="margin-left: 200px;">
            <div class="col-lg-9">
                <button id="generateBtn" type="submit" class="btn btn-success">保存</button>
                <button id="resetBtn" type="button" class="btn btn-primary" name="reset">清空</button>
                <a href="${ctx}/user/list" class="btn btn-primary" role="button">
                    返回
                </a>
            </div>
        </div>

    </form:form>

</div>

<script type="text/javascript">

    $('#userForm').bootstrapValidator({
        fields: {
            UserName: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '用户名不能为空'
                    }
                }
            },
            UserDepartment: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '部门不能为空'
                    }
                }
            },
            UserType: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '用户类型不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '用户类型只能为数字'
                    }
                }
            },
            LogInName: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '登录名不能为空'
                    }
                }
            },
            UserPassword: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '密码不能为空'
                    }
                }
            },
            Status: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '状态不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '状态只能为数字'
                    }
                }
            }
        }
    });


    $('#resetBtn').click(function () {
        $('#userForm').data('bootstrapValidator').resetForm(true);
    });

</script>

</body>
</html>