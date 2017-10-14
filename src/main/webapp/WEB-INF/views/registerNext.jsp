<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>上传身份证照片</title>
<link href="css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="js/ajaxfileupload.js"></script>
<script type="text/javascript">
    /*
    $(function() {
        $(".filepath").on("change",function() {
            var srcs = getObjectURL(this.files[0]);   //获取路径
            $(this).nextAll(".img1").hide();   //this指的是input
            $(this).nextAll(".img2").show();  //fireBUg查看第二次换图片不起做用
            $(this).nextAll(".img2").attr("src",srcs);    //this指的是input
            $(this).val('');    //必须制空
        })
    })

    function getObjectURL(file) {
        var url = null;
        if (window.createObjectURL != undefined) {
            url = window.createObjectURL(file)
        } else if (window.URL != undefined) {
            url = window.URL.createObjectURL(file)
        } else if (window.webkitURL != undefined) {
            url = window.webkitURL.createObjectURL(file)
        }
        return url
    };
    */
</script>
</head>

<body>
<div>
<form action="#">
	<div class="sczpdiv">
		<div class="ttsccon">
			<div id="img">
				<div class="imgbox">
				    <div class="imgnum">
				        <input id="uploadCenterImg" type="file" onchange="fileUploadOnChange(this)" accept="image/*" class="filepath" name="centerImg"/>
				        <img src="images/sfz.png" class="img1" />
				        <img id="uploadCenterImg_Path" src="" class="img2" />
				    </div>
				</div>
			</div>
			<span>身份证正面照</span>
		</div>
		<div class="ttsccon">
			<div id="img">
				<div class="imgbox">
				    <div class="imgnum">
				        <input id="uploadCardImg" type="file" onchange="fileUploadOnChange(this)" accept="image/*" class="filepath" name="cardImg"/>
				        <img src="images/yhk.png" class="img1" />
				        <img id="uploadCardImg_Path" src="" class="img2" />
				    </div>
				</div>
			</div>
			<span>银行卡正面照</span>
		</div>
		<div class="tyxydiv">
			<div class="checkboxFour">
		        <div class="chedxx">
		            <input type="checkbox" id="checkboxFourInput" value="1"/>
		            <label for="checkboxFourInput"></label>    
		        </div>
		    	<a href="#" class="ckb">同意《用户服务协议》</a>
		    </div>
		</div>
		<div class="scan">
			<a href="#">上一步</a>
			<input type="submit" class="sczpbut" value="提交审核">
		</div>
	</div>
</form>
</div>
<script type="text/javascript">
/* $(".filepath").on("change",function() {
	fileUploadOnChange(this);
	console.log("${ctx }")
}); */

function fileUploadOnChange(obj) {
	var inputFileID = $(obj).attr("id");
	var uploadUrl = "${ctx}/common/uploadFileImg?fileDir="+"/certImg";
	ajaxFileUpload(uploadUrl,inputFileID);
}

function ajaxFileUpload (uploadUrl,fileInputID){ 
	//loading();//动态加载小图标 
	$.ajaxFileUpload ({ 
    	url :uploadUrl, 
    	secureuri :false, 
    	fileElementId :fileInputID, 
    	dataType : 'json', 
    	success: function (data, status){ 
    		console.log(data);
		    	if(data.returnCode == "0000"){ 
		    		console.log(data.resData.filePath); 
		    		var filePath = data.resData.filePath;
		    		$("#"+fileInputID+"_Path").attr("src","${ctx}"+filePath);
		    		$("#"+fileInputID+"_Path").prev("img").hide();
		    		$("#"+fileInputID+"_Path").show();
		    	}else{ 
		    		alert(data.returnMsg); 
		    	} 
    	}, 
    	error: function (data, status, e){ 
    		console.log(e); 
    	} 
	}); 
	//return false; 
	} 
	function loading (){ 
	$("#loading ").ajaxStart(function(){ 
		$(this).show(); 
	}).ajaxComplete(function(){ 
		$(this).hide(); 
	}); 
	} 

</script>
</body>
</html>