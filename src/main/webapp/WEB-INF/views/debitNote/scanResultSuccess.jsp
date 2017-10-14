<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>收款</title>
<link href="${ctx }/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function closeWin(){
	window.history.go(-1);
}
</script>
</head>

<body class="bgeee">

<div class="zccgdiv">
	<div class="zfcgmsg">
		<span>支付金额：<b>￥${totalAmount}</b></span>
		<p></p>
		<span>支付状态：成功</span>
	</div>
	<div class="zfcgimg">
		<a href="${Href}">
			<img src="${ImageUrl}" />
		</a>
	</div>
</div>

</body>
<%--<body class="bgfff">

<div class="zccgdiv">
	<img class="zckk" src="${ctx }/images/zfok.png" />
	<div class="zcokcon">
		<h6>${resultMessage}</h6>
	</div>
	<div class="zfcgdiv">
		<span>金额：</span>
		<b>￥${totalAmount}</b>
		<!--  
		<a href="#" onclick="closeWin()">关闭</a>
		-->
	</div>
	<div class="ggdiv">
		<a href="${Href}">
			<img src="${ImageUrl}" />
		</a>
	</div>
</div>

</body>
--%></html>