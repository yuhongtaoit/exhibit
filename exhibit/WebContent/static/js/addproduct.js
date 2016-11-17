$(document).ready(function() {
    var genTime;
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
    });

    $('#productForm').bootstrapValidator({
        fields: {
            barcode: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '产品条码不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '产品条码只能为数字'
                    }
                }
            },
            number: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '产品编号不能为空'
                    }
                }
            },
            sizeIn: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '尺寸(内)不能为空'
                    }
                }
            },
            sizeOut: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '尺寸(外)不能为空'
                    }
                }
            },
            boderSize: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '框条尺寸不能为空'
                    }
                }
            },

            boderNumber: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '外框编号不能为空'
                    }
                }
            },
            price: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '单价不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '产品条码只能为数字'
                    }
                }
            },
            volume: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '体积不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9.]+$/,
                        message: '体积只能为数字'
                    }
                }
            },
            measure: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '装量不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '产品条码只能为数字'
                    }
                }
            },
            productDescibe: {
                validators: {
                    notEmpty: {
                        group: '.col-lg-4',
                        message: '产品描述不能为空'
                    }
                }
            }
        }
    });

    $('#generateBtn').click(function () {
        $('#generateTime').val(genTime);
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
                alert(path);
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
        //以下即为完整客户端路径
        $('#imageContainer').css("background-color", "white");
        var file_img = document.getElementById("imagePre"),
            imageUp = document.getElementById('imageUp');
        getPath(file_img, imageUp, file_img);
    }
});