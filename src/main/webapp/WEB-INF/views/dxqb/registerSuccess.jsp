<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>注册成功</title>
<link href="${ctx }/${oemName}/css/main_style.css" rel="stylesheet" type="text/css">
</head>

<body class="bgfff">

<div class="zccgdiv">
	<img class="zckk" src="${ctx }/${oemName}/images/zcok.png" />
	<div class="zcokcon">
		<h6>恭喜您！注册成功！</h6>
		<span>初始登录密码为您的手机号后6位！</span>
		<b>微信、支付宝需要审核1-2个工作日<br/>其他平台注册后即可使用</b>
	</div>
	<div class="zcyddiv">
		<span>请关注微信公众号“大喜钱包”进行登录哦！</span>
		<!-- 
		<dd>
			<img src="${ctx }/${oemName}/images/gzh.jpg" />
			<i>长按二维码关注公众号</i>
		</dd>
		-->
	</div>
</div>

</body>
</html>