<%--
  Created by IntelliJ IDEA.
  User: aning
  Date: 16/6/3
  Time: 上午8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>产品信息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="${ctx}/static/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <script src="${ctx}/static/bootstrap3/js/jquery-1.11.1.min.js"></script>
    <script src="${ctx}/static/bootstrap3/js/bootstrap.min.js"></script>
    <style type="text/css">
        #imgContent {
            display: -webkit-flex;
            display: flex;
            -webkit-align-items: center;
            align-items: center;
            -webkit-justify-content: center;
            justify-content: center;
        }

    </style>
</head>
<body>

<c:if test="${empty requestScope.product}">
    该产品已下架
</c:if>
<c:if test="${!empty requestScope.product}">
    <table class="table table-hover" id="prodtb"  style="white-space: nowrap;">
        <caption>产品信息列表</caption>
        <thead>
        <tr>
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
        </tr>
        </thead>
        <tbody>
        <c:set var="prod" value="${requestScope.product}"/>

        <tr>
            <td>${prod.productType}</td>
            <td>${prod.productBarcode}</td>
            <td>${prod.productCode}</td>
            <td>${prod.productSizeIn}</td>
            <td>${prod.productSizeOut}</td>
            <td>${prod.productSize}</td>
            <td>${prod.productMaterial}</td>
            <td>${prod.outframeCode}</td>
            <td>
                <script type="text/javascript">
                    var unixTi =
                    ${prod.creatTime} *
                    1000;
                    var dateObj = new Date(unixTi);
                    var UnixTimeToDate = dateObj.getUTCFullYear() + '年' + (dateObj.getUTCMonth() + 1 ) + '月' + dateObj.getUTCDate() + '日';
                    document.write(UnixTimeToDate);
                </script>
            </td>
            <td>${prod.unitPrice}</td>
            <td>${prod.productNumber}</td>
            <td>${prod.productVolume}</td>
            <td>${prod.productPackage}</td>
            <td>${prod.productDescribe}</td>
        </tr>

        </tbody>
    </table>
    <br/>
    <c:if test="${!empty requestScope.status}">
        <div id="warn" style="display: block;" class="alert alert-warning">该产品已扫码</div>
    </c:if>
    <div id="imgContent"/>
    <img id="image" src="${ctx}/product/getpic?productId=${prod.id}"/>
    </div>

</c:if>
<script type="application/javascript">
    $("#image").addClass("carousel-inner img-responsive img-rounded");
</script>
</body>
</html>