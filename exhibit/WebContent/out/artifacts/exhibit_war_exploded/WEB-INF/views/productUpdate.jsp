<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加产品</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="${ctx}/static/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/bootstrap3/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="${ctx}/static/bootstrap3/css/bootstrapValidator.css" rel="stylesheet"/>
    <link href="${ctx}/static/css/uploadBtn.css" rel="stylesheet"/>
    <link href="${ctx}/static/css/style.css" rel="stylesheet"/>
    <script src="${ctx}/static/bootstrap3/js/jquery-1.11.1.min.js"></script>
    <script src="${ctx}/static/bootstrap3/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/bootstrap3/js/bootstrap-datetimepicker.js"
            charset="UTF-8"></script>
    <script type="text/javascript" src="${ctx}/static/bootstrap3/js/locale/bootstrap-datetimepicker.zh-CN.js"
            charset="UTF-8"></script>
    <script type="text/javascript" src="${ctx}/static/bootstrap3/js/bootstrapValidator.js"></script>

</head>
<body>
<h2>产品基本信息</h2>

<form:form id="productForm" class="form-horizontal" role="form" action="${ctx}/product/update" method="post"
           enctype="multipart/form-data" modelAttribute="product">
    <form:hidden id="productId" path="id"/>
    <div id="leftContent" style="width: 50%;">
        <legend>产品基本信息</legend>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productType">产品类型</label>
            <div class="col-xs-6">
                <select class="form-control input-sm col-xs-6" id="productType" name="productType"
                        onchange="addProductType()">
                    <c:if test="${!empty requestScope.directorys}">
                        <c:forEach items="${requestScope.directorys}" var="directorys">
                            <c:if test="${directorys.directoryType == 1}">
                                <c:if test="${directorys.directoryName == product.productType}">
                                    <option id="${directoryName.id}"
                                            selected="selected">${directorys.directoryName}</option>
                                </c:if>
                                <c:if test="${directorys.directoryName != product.productType}">
                                    <option id="${directoryName.id}">${directorys.directoryName}</option>
                                </c:if>
                            </c:if>
                        </c:forEach>
                        <option>其他</option>
                    </c:if>
                </select>
                <input id="otherType" name="otherType" type="text" class="orm-control input-sm col-xs-6" hidden/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productBarcode">产品条码</label>
            <div class="col-xs-6">
                <form:input type="text" class="form-control input-sm" id="productBarcode"
                            placeholder="产品条码" readonly="true" name="productBarcode" path="productBarcode"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productCode">产品编号</label>
            <div class="col-xs-6">
                <form:input type="text" class="form-control input-sm" id="productCode"
                            placeholder="请输入产品编号" name="productCode" path="productCode"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productSizeIn">尺寸(内)</label>
            <div class="col-xs-6 form-inline">
                <form:input type="text" class="form-control input-sm col-xs-6" id="productSizeIn"
                            placeholder="请输入尺寸(内)" name="productSizeIn" path="productSizeIn"/>
                <select class="form-control input-sm col-xs-6" id="etalonIn" name="sizeInUnit">
                    <c:if test="${!empty requestScope.directorys}">
                        <c:forEach items="${requestScope.directorys}" var="directorys">
                            <c:if test="${directorys.directoryType == 2}">
                                <c:if test="${directorys.directoryName == product.sizeInUnit}">
                                    <option id="${directoryName.id}"
                                            selected="selected">${directorys.directoryName}</option>
                                </c:if>
                                <c:if test="${directorys.directoryName != product.sizeInUnit}">
                                    <option id="${directoryName.id}">${directorys.directoryName}</option>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productSizeOut">尺寸(外)</label>
            <div class="col-xs-6 form-inline">
                <form:input type="text" class="form-control input-sm col-xs-6" id="productSizeOut"
                            placeholder="请输入尺寸(内)" name="productSizeOut" path="productSizeOut"/>
                <select class="form-control input-sm col-xs-6" id="etalonOut" name="sizeOutUnit">
                    <c:if test="${!empty requestScope.directorys}">
                        <c:forEach items="${requestScope.directorys}" var="directorys">
                            <c:if test="${directorys.directoryType == 2}">
                                <c:if test="${directorys.directoryName == product.sizeOutUnit}">
                                    <option id="${directoryName.id}"
                                            selected="selected">${directorys.directoryName}</option>
                                </c:if>
                                <c:if test="${directorys.directoryName != product.sizeOutUnit}">
                                    <option id="${directoryName.id}">${directorys.directoryName}</option>
                                </c:if>

                            </c:if>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productSize">框条尺寸</label>
            <div class="col-xs-6 form-inline">
                <form:input type="text" class="form-control input-sm col-xs-6" id="productSize"
                            placeholder="框条尺寸" name="productSize" path="productSize"/>
                <select class="form-control input-sm col-xs-6" id="etalonBorder" name="sizeUnit">
                    <c:if test="${!empty requestScope.directorys}">
                        <c:forEach items="${requestScope.directorys}" var="directorys">
                            <c:if test="${directorys.directoryType == 2}">
                                <c:if test="${directorys.directoryName == product.sizeUnit}">
                                    <option id="${directoryName.id}"
                                            selected="selected">${directorys.directoryName}</option>
                                </c:if>
                                <c:if test="${directorys.directoryName != product.sizeUnit}">
                                    <option id="${directoryName.id}">${directorys.directoryName}</option>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productMaterial">框条材质</label>
            <div class="col-xs-6">
                <select class="form-control input-sm col-xs-6" id="productMaterial" name="productMaterial"
                        onchange="addProductMaterial()">
                    <c:if test="${!empty requestScope.directorys}">
                        <c:forEach items="${requestScope.directorys}" var="directorys">
                            <c:if test="${directorys.directoryType == 3}">
                                <c:if test="${directorys.directoryName == product.productMaterial}">
                                    <option id="${directoryName.id}"
                                            selected="selected">${directorys.directoryName}</option>
                                </c:if>
                                <c:if test="${directorys.directoryName != product.productMaterial}">
                                    <option id="${directoryName.id}">${directorys.directoryName}</option>
                                </c:if>
                            </c:if>
                        </c:forEach>
                        <option>其他</option>
                    </c:if>
                </select>
                <input id="otherMaterial" name="otherMaterial" type="text" class="orm-control input-sm col-xs-6"
                       hidden/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="outframeCode">外框编号</label>
            <div class="col-xs-6">
                <form:input type="text" class="form-control input-sm" id="outframeCode"
                            placeholder="请输入外框编号" name="outframeCode" path="outframeCode"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="generateTimer">生成日期</label>
            <div class="col-xs-6">
                <form:input id="generateTimer" size="16" type="text" value="2012-6-15" readonly="true"
                            class="form-control input-sm form_datetime" name="generateTimer" path=""/>
            </div>
            <form:hidden id="creatTime" path="creatTime"/>

        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label" for="unitPrice">单价</label>
            <div class="col-xs-6 form-inline">
                <form:input type="text" class="form-control input-sm col-xs-6" id="unitPrice"
                            placeholder="请输入单价" name="unitPrice" path="unitPrice"/>
                <select class="form-control input-sm col-xs-6" id="etalonprice" name="priceUnit">
                    <c:if test="${!empty requestScope.directorys}">
                        <c:forEach items="${requestScope.directorys}" var="directorys">
                            <c:if test="${directorys.directoryType == 4}">
                                <c:if test="${directorys.directoryName == product.priceUnit}">
                                    <option id="${directoryName.id}"
                                            selected="selected">${directorys.directoryName}</option>
                                </c:if>
                                <c:if test="${directorys.directoryName != product.priceUnit}">
                                    <option id="${directoryName.id}">${directorys.directoryName}</option>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productNumber">装量</label>
            <div class="col-xs-6">
                <form:input type="text" class="form-control input-sm" id="productNumber"
                            placeholder="请输入装量" name="productNumber" path="productNumber"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productVolume">体积</label>
            <div class="col-xs-6 form-inline">
                <form:input type="text" class="form-control input-sm  col-xs-6" id="productVolume"
                            placeholder="请输入体积" name="productVolume" path="productVolume"/>
                <span class="form-control input-sm  col-xs-6">M<sup>3</sup></span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label" for="productPackage">包装方式</label>
            <div class="col-xs-6">
                <select class="form-control input-sm" id="productPackage" name="productPackage">
                    <c:if test="${!empty requestScope.directorys}">
                        <c:forEach items="${requestScope.directorys}" var="directorys">
                            <c:if test="${directorys.directoryType == 5}">
                                <c:if test="${directorys.directoryName == product.productPackage}">
                                    <option id="${directoryName.id}"
                                            selected="selected">${directorys.directoryName}</option>
                                </c:if>
                                <c:if test="${directorys.directoryName != product.productPackage}">
                                    <option id="${directoryName.id}">${directorys.directoryName}</option>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productDescribe">产品描述</label>
            <div class="col-xs-6">
                <form:textarea class="form-control input-sm" rows="3" id="productDescribe" placeholder="请输入产品描述"
                               name="productDescribe" path="productDescribe"></form:textarea>
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-9">
                <button id="generateBtn" type="submit" class="btn btn-success">生成</button>
                <button id="resetBtn" type="button" class="btn btn-primary" name="reset">清空</button>
                <a href="${ctx}/product/list" class="btn btn-primary" role="button">
                    返回
                </a>
            </div>
        </div>
    </div>

    <div id="rightContent">
        <legend>产品图片</legend>
        <div id="imageContainer">
            <img id="imagePre" src="" class="img-rounded">
        </div>
        <div id="bottomContainer">
            <div class="fileupload fileupload-new" data-provides="fileupload">
            <span class="btn btn-primary btn-file"><span class="fileupload-new">装载图片</span>
            <span class="fileupload-exists">Change</span>
                <input id="imageUp" onchange='show()' type="file" name="imageUp"/></span>
                <input id="isImgUp" type="hidden" name="isImgUp" value="NO"/>
                <span class="fileupload-preview"></span>
                <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none">×</a>
            </div>
        </div>
    </div>
</form:form>


<script type="text/javascript">
    var genTime = $("#creatTime").val() * 1000;
    var dateObj = new Date(genTime);
    var unixTimeToDate = dateObj.getUTCFullYear() + '-' + (dateObj.getUTCMonth() + 1 ) + '-' + dateObj.getUTCDate();
    $("#generateTimer").val(unixTimeToDate);

    $("#imagePre").attr('src', "${ctx}/product/getpic?productId=" + $("#productId").val());

    $(".form_datetime").datetimepicker({
        format: 'yyyy-mm-dd', language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    }).on('changeDate', function (ev) {
        genTime = ev.date.valueOf();
        console.log(genTime);
    });

    $('#productForm').bootstrapValidator({
        fields: {
            productCode: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '产品编号不能为空'
                    }
                }
            },
            productSizeIn: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '尺寸(内)不能为空'
                    }
                }
            },
            productSizeOut: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '尺寸(外)不能为空'
                    }
                }
            },
            productSize: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '框条尺寸不能为空'
                    }
                }
            },
            outframeCode: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '外框编号不能为空'
                    }
                }
            },
            unitPrice: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '单价不能为空'
                    },
                    regexp: {
                        regexp: /^\d+(\.\d+)?$/,
                        message: '单价只能为数字'
                    }
                }
            },
            productVolume: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '体积不能为空'
                    },
                    regexp: {
                        regexp: /^\d+(\.\d+)?$/,
                        message: '体积只能为数字'
                    }
                }
            },
            productNumber: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '装量不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '装量只能为数字'
                    }
                }
            },
            productDescribe: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '产品描述不能为空'
                    }
                }
            },
            otherType: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '产品类型不能为空'
                    }
                }
            },
            otherMaterial: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '框条材质不能为空'
                    }
                }
            }
        }
    });

    $('#generateBtn').click(function () {
        $('#creatTime').val(genTime);
    });

    $('#resetBtn').click(function () {
        $('#productForm').data('bootstrapValidator').resetForm(true);
    });

    function getPath(obj, fileQuery, transImg) {

        var imgSrc = '', imgArr = [], strSrc = '';

        if (window.navigator.userAgent.indexOf("MSIE") >= 1) { // IE浏览器判断
            if (obj.select) {
                obj.select();
                var path = document.selection.createRange().text;
                obj.removeAttribute("src");
                imgSrc = fileQuery.value;
                imgArr = imgSrc.split('.');
                strSrc = imgArr[imgArr.length - 1].toLowerCase();
                if (strSrc.localeCompare('jpg') === 0 || strSrc.localeCompare('jpeg') === 0 || strSrc.localeCompare('gif') === 0 || strSrc.localeCompare('png') === 0) {
                    obj.setAttribute("src", transImg);
                    obj.style.filter =
                            "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + path + "', sizingMethod='scale');"; // IE通过滤镜的方式实现图片显示
                } else {
                    throw new Error('File type Error! please image file upload..');
                }
            } else {
                // alert(fileQuery.value) ;
                imgSrc = fileQuery.value;
                imgArr = imgSrc.split('.');
                strSrc = imgArr[imgArr.length - 1].toLowerCase();
                if (strSrc.localeCompare('jpg') === 0 || strSrc.localeCompare('jpeg') === 0 || strSrc.localeCompare('gif') === 0 || strSrc.localeCompare('png') === 0) {
                    obj.src = fileQuery.value;
                } else {
                    throw new Error('File type Error! please image file upload..');
                }

            }

        } else {
            var file = fileQuery.files[0];
            var reader = new FileReader();
            reader.onload = function (e) {

                imgSrc = fileQuery.value;
                imgArr = imgSrc.split('.');
                strSrc = imgArr[imgArr.length - 1].toLowerCase();
                if (strSrc.localeCompare('jpg') === 0 || strSrc.localeCompare('jpeg') === 0 || strSrc.localeCompare('gif') === 0 || strSrc.localeCompare('png') === 0) {
                    obj.setAttribute("src", e.target.result);
                } else {
                    throw new Error('File type Error! please image file upload..');
                }
            }
            reader.readAsDataURL(file);
        }
    }

    function show() {
        $("#isImgUp").val("YES");
        //以下即为完整客户端路径
        $('#imageContainer').css("background-color", "white");
        var file_img = document.getElementById("imagePre"),
                imageUp = document.getElementById('imageUp');
        getPath(file_img, imageUp, file_img);
    }

    function addProductType() {
        if ($("#productType").find("option:selected").text() == "其他") {
            $("#otherType").show();
        } else {
            $("#otherType").hide();

        }
    }
    function addProductMaterial() {
        if ($("#productMaterial").find("option:selected").text() == "其他") {
            $("#otherMaterial").show();
        } else {
            $("#otherMaterial").hide();

        }
    }

</script>

</body>
</html>