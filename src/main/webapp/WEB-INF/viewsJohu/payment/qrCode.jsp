<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>扫码支付</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
</script>
<style>
	body{background:#fff;}
</style>
</head>

<body>

<div class="ddewmdiv">
	<div class="smewm">
		<img src="${ctx }/common/getQrCode?qrCode=${qrCode}" />
	</div>
	
	<span>订单号${orderNum }，支付<b>${payMoney }</b>元<br/>打开
	<c:if test="${payType=='1' }">手机微信，</c:if>
	<c:if test="${payType=='2' }">手机支付宝，</c:if>
	<c:if test="${payType=='3' }">QQ钱包，</c:if>
	扫一扫继续付款</span>
</div>

</body>
</html>