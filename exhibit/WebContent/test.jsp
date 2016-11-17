<%--
  Created by IntelliJ IDEA.
  User: aning
  Date: 16/5/28
  Time: 下午7:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>
    <%--<a href="custom/save?customName=haha&productBarcodes=2121,1212,1,212s1">test</a>--%>
    <button onclick="test()">test</button>
<script type="application/javascript">

    function test() {
        var productBarcodes = [];
        //productBarcodes.push("weweeewewewe");
        //productBarcodes.push("weweeewewewe2");
        productBarcodes.push("weweeewewewe3");
        var url = encodeURI("${ctx}/custom/save?customName=" + "饭" +"&productBarcodes=" + productBarcodes);
        var x = decodeURI(url.split("?")[1].split("=")[1].split("&")[0]);
        window.location.href = "${ctx}/custom/search?loginName=sale1";
        <%--window.location.href = "${ctx}/custom/del?loginName=sale1&qrcodeId=117,118,136";--%>
        <%--window.location.href = "${ctx}/custom/save?customName=nini&loginName=sale1&comment=com1,com2,com3&productBarcodes=A389203098,A1380890286,A1413021915";--%>

    }
</script>
<form action="${ctx}/custom/save">

    <input type="text" name="customName" /><br/>
    <input type="text" name="loginName" /><br/>
    <input type="text" name="qrcodes[0].productBarcode" /><br/>
    <input type="text" name="qrcodes[0].comment" /><br/>
    <input type="text" name="qrcodes[1].productBarcode" /><br/>
    <input type="text" name="qrcodes[1].comment" /><br/>
    <input type="submit" value="ok" />

</form>
</body>
</html>
