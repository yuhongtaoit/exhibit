<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

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

<form id="productForm" class="form-horizontal" role="form" action="${ctx}/product/add" method="post"
      enctype="multipart/form-data">
    <div id="leftContent" style="width: 50%;">
        <legend>产品基本信息</legend>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productType">产品类型</label>
            <div class="col-xs-6">
                <select class="form-control input-sm col-xs-6" id="productType" name="productType" onchange="addProductType()">
                    <c:if test="${!empty requestScope.directorys}">
                        <c:forEach items="${requestScope.directorys}" var="directorys">
                            <c:if test="${directorys.directoryType == 1}">
                                <option id="${directoryName.id}">${directorys.directoryName}</option>
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
                <input type="text" class="form-control input-sm" id="productBarcode"
                       placeholder="产品条码" readonly="readonly" name="productBarcode"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productCode">产品编号</label>
            <div class="col-xs-6">
                <input type="text" class="form-control input-sm" id="productCode"
                       placeholder="请输入产品编号" name="productCode">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productSizeIn">尺寸(内)</label>
            <div class="col-xs-6 form-inline">
                <input type="text" class="form-control input-sm col-xs-6" id="productSizeIn"
                       placeholder="请输入尺寸(内)" name="productSizeIn">
                <select class="form-control input-sm col-xs-6" id="etalonIn" name="sizeInUnit">
                    <c:if test="${!empty requestScope.directorys}">
                        <c:forEach items="${requestScope.directorys}" var="directorys">
                            <c:if test="${directorys.directoryType == 2}">
                                <option id="${directoryName.id}">${directorys.directoryName}</option>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productSizeOut">尺寸(外)</label>
            <div class="col-xs-6 form-inline">
                <input type="text" class="form-control input-sm col-xs-6" id="productSizeOut"
                       placeholder="请输入尺寸(内)" name="productSizeOut">
                <select class="form-control input-sm col-xs-6" id="etalonOut" name="sizeOutUnit">
                    <c:if test="${!empty requestScope.directorys}">
                        <c:forEach items="${requestScope.directorys}" var="directorys">
                            <c:if test="${directorys.directoryType == 2}">
                                <option id="${directoryName.id}">${directorys.directoryName}</option>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productSize">框条尺寸</label>
            <div class="col-xs-6 form-inline">
                <input type="text" class="form-control input-sm col-xs-6" id="productSize"
                       placeholder="框条尺寸" name="productSize">
                <select class="form-control input-sm col-xs-6" id="etalonBorder" name="sizeUnit">
                    <c:if test="${!empty requestScope.directorys}">
                        <c:forEach items="${requestScope.directorys}" var="directorys">
                            <c:if test="${directorys.directoryType == 2}">
                                <option id="${directoryName.id}">${directorys.directoryName}</option>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productMaterial">框条材质</label>
            <div class="col-xs-6 form-inline">
                <select class="form-control input-sm col-xs-6" id="productMaterial" name="productMaterial" onchange="addProductMaterial()">
                    <c:if test="${!empty requestScope.directorys}">
                        <c:forEach items="${requestScope.directorys}" var="directorys">
                            <c:if test="${directorys.directoryType == 3}">
                                <option id="${directoryName.id}">${directorys.directoryName}</option>
                            </c:if>
                        </c:forEach>
                        <option>其他</option>
                    </c:if>
                </select>
                <input id="otherMaterial" name="otherMaterial" type="text" class="orm-control input-sm col-xs-6" hidden/>

            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="outframeCode">外框编号</label>
            <div class="col-xs-6">
                <input type="text" class="form-control input-sm" id="outframeCode"
                       placeholder="请输入外框编号" name="outframeCode">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="generateTimer">生成日期</label>
            <div class="col-xs-6">
                <input id="generateTimer" size="16" type="text" value="" readonly
                       class="form-control input-sm form_datetime" name="generateTimer">
            </div>
            <input type="hidden" id="creatTime" name="creatTime">
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="unitPrice">单价</label>
            <div class="col-xs-6 form-inline">
                <input type="text" class="form-control input-sm col-xs-6" id="unitPrice"
                       placeholder="请输入单价" name="unitPrice">
                <select class="form-control input-sm col-xs-6" id="etalonprice" name="priceUnit">
                    <c:if test="${!empty requestScope.directorys}">
                        <c:forEach items="${requestScope.directorys}" var="directorys">
                            <c:if test="${directorys.directoryType == 4}">
                                <option id="${directoryName.id}">${directorys.directoryName}</option>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productNumber">装量</label>
            <div class="col-xs-6">
                <input type="text" class="form-control input-sm" id="productNumber"
                       placeholder="请输入装量" name="productNumber">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productVolume">体积</label>
            <div class="col-xs-6 form-inline">
                <input type="text" class="form-control input-sm  col-xs-6" id="productVolume"
                       placeholder="请输入体积" name="productVolume">
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
                                <option id="${directoryName.id}">${directorys.directoryName}</option>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="productDescribe">产品描述</label>
            <div class="col-xs-6">
                <textarea class="form-control input-sm" rows="3" id="productDescribe" placeholder="请输入产品描述"
                          name="productDescribe"></textarea>
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
            <img id="imagePre" src="" class="img-rounded" width="400" style="background-image: url('${ctx}/static/image/bgimg.jpg');">
        </div>
        <div id="bottomContainer">
            <div class="fileupload fileupload-new" data-provides="fileupload">
            <span class="btn btn-primary btn-file"><span class="fileupload-new">装载图片</span>
            <span class="fileupload-exists">Change</span><input id="imageUp" onchange='show()' type="file"
                                                                name="imageUp"/></span>
                <span class="fileupload-preview"></span>
                <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none"></a>
            </div>
        </div>
    </div>
</form>


<script type="text/javascript">
    var date = new Date();
    var now = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();    //获取完整的年份(4位,1970-????)
    $("#generateTimer").val(now);
    var genTime = Math.round(new Date().getTime());
    var ran = parseInt(genTime * Math.random() / 1000);
    $("#productBarcode").val("A" + ran);
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
        var ran = parseInt(genTime * Math.random() / 1000);
        $("#productBarcode").val("A" + ran);
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
            otherType:{
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '产品类型不能为空'
                    }
                }
            },
            otherMaterial:{
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
        //以下即为完整客户端路径
        $('#imageContainer').css("background-color", "white");
        var file_img = document.getElementById("imagePre"),
                imageUp = document.getElementById('imageUp');
        getPath(file_img, imageUp, file_img);
    }

    function addProductType() {
       if($("#productType").find("option:selected").text() == "其他") {
           $("#otherType").show();
       } else {
           $("#otherType").hide();

       }
    }
    function addProductMaterial() {
       if($("#productMaterial").find("option:selected").text() == "其他") {
           $("#otherMaterial").show();
       } else {
           $("#otherMaterial").hide();

       }
    }



</script>

</body>
</html>