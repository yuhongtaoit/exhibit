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
    <title>扫码管理</title>
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
            <li><a href="${ctx}/user/list">用户信息管理</a></li>
            <li class="active"><a href="${ctx}/qrcode/list">扫码信息管理</a></li>
        </ul>
    </div>
</nav>
<ul id="qrTab" class="nav nav-tabs">
    <li class="active" id="preli">
        <a href="#pre" data-toggle="tab">
            用户扫码查询
        </a>
    </li>
    <li id="lasli"><a href="#las" data-toggle="tab" onclick="getQrList()">扫码列表</a></li>

</ul>
<div id="tabContent" class="tab-content">
    <div class="tab-pane fade in active" id="pre">
        <div style="padding: 30px 30px 10px;">
            <div class="row">
                <div class="col-lg-4">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="请输入客户名进行检索" id="customName">
                        <span class="input-group-btn">
                            <button id="searchBtn" class="btn btn-default" type="button" onclick="searchQrForUser()">
                                查询
                            </button>
                        </span>
                        <span class="input-group-btn">
                            <button id="exportBtn" class="btn btn-default" type="button" onclick="exportQrcode()">
                                导出
                            </button>
                        </span>
                        <span class="input-group-btn">
                            <button id="delAllBtn" class="btn btn-danger" type="button" onclick="delQrcode()">
                                删除
                            </button>
                        </span>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${!empty requestScope.qrcode}">
            <table id="userQrTb" class="table table-hover" style="white-space: nowrap;">
                <caption>用户扫码信息</caption>
                <thead>
                <tr>
                    <th><input type="checkbox" id="masterCheck"/></th>
                    <th>客户</th>
                    <th>产品类型</th>
                    <th>产品条码</th>
                    <th>产品编码</th>
                    <th>尺寸(内)</th>
                    <th>尺寸(外)</th>
                    <th>框条尺寸</th>
                    <th>框条材质</th>
                    <th>外框编号</th>
                    <th>单价</th>
                    <th>装量</th>
                    <th>体积</th>
                    <th>包装方式</th>
                    <th>产品描述</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${requestScope.qrcode}" var="qrcode">
                    <tr>
                        <td><input id="${qrcode.qrcodeId}" type="checkbox" name="num"></td>
                        <td>${qrcode.customName}</td>
                        <td>${qrcode.productType}</td>
                        <td>${qrcode.productBarcode}</td>
                        <td>${qrcode.productCode}</td>
                        <td>${qrcode.productSizeIn} ${qrcode.sizeInUnit}</td>
                        <td>${qrcode.productSizeOut} ${qrcode.sizeOutUnit}</td>
                        <td>${qrcode.productSize} ${qrcode.sizeUnit}</td>
                        <td>${qrcode.productMaterial}</td>
                        <td>${qrcode.outframeCode}</td>
                        <td>${qrcode.unitPrice} ${qrcode.priceUnit}</td>
                        <td>${qrcode.productNumber}</td>
                        <td>${qrcode.productVolume}</td>
                        <td>${qrcode.productPackage}</td>
                        <td>${qrcode.productDescribe}</td>
                        <td>
                            <button id="${qrcode.productId}" type="button" class="btn btn-primary btn-xs"
                                    onclick="preview(this.id)">预览
                            </button>
                        </td>
                        <td>
                            <a href="${ctx}/qrcode/del2?qrcodeId=${qrcode.qrcodeId}&customName=${qrcode.customName}"
                               class="btn btn-danger btn-xs"
                               role="button">
                                删除
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="17">客户备注: ${qrcode.comment}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br/>
        </c:if>
    </div>
    <div class="tab-pane fade" id="las">
        <c:if test="${empty requestScope.qrcodes}">
            没有任何用户信息
        </c:if>
        <c:if test="${!empty requestScope.qrcodes}">
            <table class="table table-hover" style="white-space: nowrap;">
                <caption>扫码信息</caption>
                <thead>
                <tr>
                    <th>客户</th>
                    <th>产品类型</th>
                    <th>产品条码</th>
                    <th>产品编码</th>
                    <th>尺寸(内)</th>
                    <th>尺寸(外)</th>
                    <th>框条尺寸</th>
                    <th>框条材质</th>
                    <th>外框编号</th>
                    <th>单价</th>
                    <th>装量</th>
                    <th>体积</th>
                    <th>包装方式</th>
                    <th>产品描述</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${requestScope.qrcodes}" var="qrcode">
                    <tr>
                        <td>${qrcode.customName}</td>
                        <td>${qrcode.productType}</td>
                        <td>${qrcode.productBarcode}</td>
                        <td>${qrcode.productCode}</td>
                        <td>${qrcode.productSizeIn} ${qrcode.sizeInUnit}</td>
                        <td>${qrcode.productSizeOut} ${qrcode.sizeOutUnit}</td>
                        <td>${qrcode.productSize} ${qrcode.sizeUnit}</td>
                        <td>${qrcode.productMaterial}</td>
                        <td>${qrcode.outframeCode}</td>
                        <td>${qrcode.unitPrice} ${qrcode.priceUnit}</td>
                        <td>${qrcode.productNumber}</td>
                        <td>${qrcode.productVolume}</td>
                        <td>${qrcode.productPackage}</td>
                        <td>${qrcode.productDescribe}</td>
                        <td>
                            <a href="${ctx}/qrcode/del?qrcodeId=${qrcode.qrcodeId}" class="btn btn-danger btn-xs"
                               role="button">
                                删除
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="17">客户备注: ${qrcode.comment}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br/>

        </c:if>
    </div>

</div>
<div id="imgContent" class="imgbox">
    <img id="image" src="" style="width: 500px;height: auto;display: none;"/>
</div>
<script type="application/javascript">

    <c:if test="${!empty requestScope.qrcodes}">
    $("#preli").removeClass("active");
    $("#lasli").addClass("active");

    $("#pre").removeClass("active");
    $("#pre").removeClass("in");

    $("#las").addClass("active");
    $("#las").addClass("in");
    </c:if>
    <c:if test="${empty requestScope.qrcodes}">
    $("#lasli").removeClass("active");
    $("preli").addClass("active");
    </c:if>

    <c:if test="${!empty requestScope.qrcode}">
    $("#lasli").removeClass("active");
    $("#preli").addClass("active");

    $("#las").removeClass("active");
    $("#las").removeClass("in");

    $("#pre").addClass("active");
    $("#pre").addClass("in");
    </c:if>

    function getQrList() {
        window.location.href = "${ctx}/qrcode/tolist";
    }

    function searchQrForUser() {
        window.location.href = "${ctx}/qrcode/search?customName=" + $("#customName").val();
    }

    function exportQrcode() {
        var customName = $("#userQrTb tr td:eq(1)").text();
        if (customName == "") {
            return;
        }
        window.location.href = "${ctx}/qrcode/download?customName=" + customName;
    }

    function preview(id) {
        $("#image").attr('src', "${ctx}/product/getpic?productId=" + id);
        $("#image").show();

    }

    function delAllQrcode() {
    var customName = $("#userQrTb tr td:first").text();
    if (customName == "") {
    return;
    }
    window.location.href = "${ctx}/qrcode/delAll?customName=" + customName;
    }

    function delQrcode() {
        var qrcodeId = [];
        var flag = -1;
        $(":checkbox[name=num]:checked").each(function (key, value) {
            flag = 1;
            var i = value.id;
            qrcodeId.push(i);
        });

        if (1 == flag) {
            var customName = $("#userQrTb tr td:eq(1)").text();
            if (customName == "") {
                return;
            }
            window.location.href = "${ctx}/qrcode/delByIds?qrcodeIds=" + qrcodeId +"&customName=" + customName;
        }
    }

    $('#masterCheck').click(function(){
        $('INPUT[name=num]').prop('checked',$('#masterCheck').prop('checked'));
    });

</script>
</body>
</html>