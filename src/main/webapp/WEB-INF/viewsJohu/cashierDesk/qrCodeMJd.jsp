<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>京东扫码支付</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
</script>
</head>

<body class="bgf5">

<div class="zfndiv">
	<div class="zflog">
		<img src="${ctx }/johu/images/jdlog.png" />
	</div>
	<div class="zfnr">
		<div class="zftit">
			<span>二维码有效时间为<i>30分钟</i>，请尽快完成支付！</span>
		</div>
		<div class="zfje">
			<span>支付<i>${payMoney }</i>元</span>
		</div>
		<div class="zfewmm">
			<img src="${ctx }/common/getQrCode?qrCode=${qrCode}" />
		</div>
		<div class="zfsm">
			<img src="${ctx }/johu/images/sm.png" />
			<span>打开手机京东<br/>扫一扫完成付款</span>
		</div>
	</div>
</div>

</body>
</html>