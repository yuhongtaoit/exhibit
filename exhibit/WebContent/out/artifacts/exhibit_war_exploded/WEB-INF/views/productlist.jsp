<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %><%--
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
    <title>产品管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="${ctx}/static/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <script src="${ctx}/static/bootstrap3/js/jquery-1.11.1.min.js"></script>
    <script src="${ctx}/static/bootstrap3/js/bootstrap.min.js"></script>
    <script type="text/css">
        .imgbox img {
            width: 300px;
        }
    </script>
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
            <li class="active"><a href="${ctx}/product/list">产品信息管理</a></li>
            <li><a href="${ctx}/user/list">用户信息管理</a></li>
            <li><a href="${ctx}/qrcode/list">扫码信息管理</a></li>
        </ul>
    </div>
</nav>
<c:if test="${empty requestScope.products}">
    没有任何产品信息<br />
    <a href="${ctx}/product/input" class="btn btn-primary btn-lg" role="button">
        添加产品
    </a>
</c:if>
<c:if test="${!empty requestScope.products}">
    <table class="table table-hover" id="prodtb" style="white-space: nowrap;">
        <caption>产品信息列表</caption>
        <thead>
        <tr>
            <th><input type="checkbox" id="masterCheck"/></th>
            <th>产品类型</th>
            <th>产品条码</th>
            <th>产品编号</th>
            <th>尺寸(内)</th>
            <th>尺寸(外)</th>
            <th>框条尺寸</th>
            <th>框条材质</th>
            <th>外框编号</th>
            <th>生成日期</th>
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
        <%
            int id = 1;
        %>
        <c:forEach items="${requestScope.products}" var="prod">

            <tr>
                <td><input id="<%=id%>" type="checkbox" name="num"></td>
                <td>${prod.productType}</td>
                <td name="productBarcode">${prod.productBarcode}</td>
                <td>${prod.productCode}</td>
                <td>${prod.productSizeIn} ${prod.sizeInUnit}</td>
                <td>${prod.productSizeOut} ${prod.sizeOutUnit}</td>
                <td>${prod.productSize} ${prod.sizeUnit}</td>
                <td>${prod.productMaterial}</td>
                <td>${prod.outframeCode}</td>
                <td>
                    <script type="text/javascript">
                        var unixTi =
                        ${prod.creatTime} *
                        1000;
                        var dateObj = new Date(unixTi);
                        var UnixTimeToDate = dateObj.getUTCFullYear() + '年' + (dateObj.getUTCMonth() + 1 ) + '月' + (dateObj.getUTCDate() + 1) + '日';
                        document.write(UnixTimeToDate);
                    </script>
                </td>
                <td>${prod.unitPrice} ${prod.priceUnit}</td>
                <td>${prod.productNumber}</td>
                <td>${prod.productVolume}</td>
                <td>${prod.productPackage}</td>
                <td>${prod.productDescribe}</td>
                <td>
                    <a href="${ctx}/product/toupdate?productId=${prod.id}" class="btn btn-info btn-xs" role="button">
                        更改
                    </a>
                </td>
                <td>
                    <a href="${ctx}/product/del?productId=${prod.id}" class="btn btn-danger btn-xs" role="button">
                        删除
                    </a>
                </td>
                <td>
                    <button id="${prod.id}" type="button" class="btn btn-primary btn-xs" onclick="preview(this.id)">预览
                    </button>
                </td>
            </tr>
            <%
                id++;
            %>
        </c:forEach>
        </tbody>
    </table>
    <br/>

    <a href="${ctx}/product/input" class="btn btn-primary btn-lg" role="button">
        添加产品
    </a>
    <button type="button" class="btn btn-primary btn-lg" onclick="print()">
        打印产品二维码
    </button>
    <div id="info" style="display: none;" class="alert alert-success">
        成功打印二维码!
    </div>
    <div id="danger" style="display: none;" class="alert alert-danger">
        无可用打印机,请检查您的打印机驱动程序是否正确安装!
    </div>
    <div id="warn" style="display: none;" class="alert alert-warning">请选择您要打印的产品信息!</div>

    </div>
    <div id="imgContent" class="imgbox">
        <img id="image" src="" style="width:500px;height: auto;display: none"/>
    </div>
</c:if>
<script type="text/javascript">
    $("#image").addClass("carousel-inner img-responsive img-rounded");
    function print() {
        var prodtb = document.getElementById('prodtb');
        var productBarcodes = [];
        var flag = -1;
        $(":checkbox[name=num]:checked").each(function (key, value) {
            flag = 1;
            var i = value.id;
            $("#prodtb tr:nth-child(" + i + ") td:nth-child(3)").each(function () {
                productBarcodes.push($(this).text());
            });
        });
        if (1 == flag) {
            window.location.href = "download?productBarcodes=" + productBarcodes.join(',');
            $("#warn").hide();
            $("#danger").hide();
            $("#info").show();
        }
        /*if (1 == flag) {
         $.ajax({
         url: "download",
         timeout : 10000,
         data: "productBarcodes=" + productBarcodes.join(','),
         type: "get",
         dataType: "json",
         success: function (data) {
         if (data.status == 'success') {
         $("#warn").hide();
         $("#danger").hide();
         $("#info").show();
         } else {
         $("#warn").hide();
         $("#info").hide();
         $("#danger").show();
         window.location.href = "download?productBarcodes="+productBarcodes.join(',');
         }

         }
         });
         }*/

        if (-1 == flag) {
            $("#danger").hide();
            $("#info").hide();
            $("#warn").show();
        }
    }

    function preview(id) {
        $("#image").attr('src', "${ctx}/product/getpic?productId=" + id);
        $("#image").show();

    }

    $('#masterCheck').click(function () {
        $('INPUT[name=num]').prop('checked', $('#masterCheck').prop('checked'));
    });
</script>
</body>
</html>