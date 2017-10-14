<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>登录</title>
<link href="${ctx }/${oemName}/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx }/js/jquery-2.2.3.min.js"></script>
<style>
	/** 该页面所有图片链接必须加上ctx参数前缀，防止图片加载出错(该页面可由多个控制器跳转到时会导致图片相对路径地址错误)  */
	body{background:#f8f8f8 url(${ctx }/${oemName}/images/dlbg.png) left bottom no-repeat;background-size: 100%;}
</style>
</head>

<body>

<div class="logindiv">
	<img src="${ctx }/${oemName}/images/dllogo.png" class="logo"/>
	<form action="#">
		<div class="login">
			<dl class="fstdl">
				<span class="zh"></span>
				<input id="textPhone" type="text" class="logtxt" placeholder="请输入手机号">
			</dl>
			<dl>
				<span class="mm"></span>
				<input id="textPassword" type="password" class="logtxt" placeholder="请输入密码">
			</dl>
		</div>
		<input type="button" class="logbut" onclick="login()" value="登 录">
		<input type="hidden" id="userOpenID" value="${openid }"/>
	</form>
	<a href="${ctx }/forget" class="wjmm">忘记密码？点这里</a>
	<%--<ul class="logintit">
		<li><a href="${ctx }/register">快速注册</a></li>
		<li><a class="cur" href="#">找回密码</a></li>
	</ul>
--%></div>
<script type="text/javascript">
	function login(){
		var textPhone = $.trim($("#textPhone").val());
		var textPassword = $.trim($("#textPassword").val());
		if(textPhone == ""){
			alert("账号不能为空");
			return;
		}
		var re = /^1[34578]\d{9}$/;
        if (!re.test(textPhone)) {
			alert("账号格式不正确");
			return;
		}
		
		if(textPassword == ""){
			alert("密码不能为空");
			return;
		}
		
		$.ajax({
			url:"${ctx }/loginIn",
			data:{loginCode:textPhone,password:textPassword,userOpenID:$("#userOpenID").val()},
			type:'post',
			cache:false, 
			async:false,
			dataType:'json',
			success:function(data) {
				if(data.returnCode=="0000"){//请求成功
					var epayCode = data.resData.epayCode;
					//window.location.href = "${ctx }/debitNote/index?epayCode="+epayCode;
					window.location.href = "${ctx }/memberInfo/memberCenter";
				}else{
					alert(data.returnMsg);
				}
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {    
		        alert("请求出错");
		    }
		});
	}





</script>
</body>
</html>