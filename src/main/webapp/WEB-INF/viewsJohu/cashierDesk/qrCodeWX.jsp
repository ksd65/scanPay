<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>微信扫码支付</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
</script>
</head>

<body>

<div class="sytdiv">
	<div class="zfnrtit">
		<dd class="zfnrtleft">
			<h6>订单已提交成功，请您尽快完成付款！</h6>
			<span>支付二维码的有效时间为<i>30分钟</i>，如过期请返回重新获取。</span>
		</dd>
		<dd class="zfnrtright">
			<span>支付<b>${payMoney}</b>元</span>
		</dd>
	</div>
	<div class="zfnrcon">
		<img src="${ctx }/johu/images/log_wx.png" class="mlog" />
		<div class="zfewm">
			<div class="zfmdiv">
				<img src="${ctx }/common/getQrCode?qrCode=${qrCode}" />
				<p>打开手机微信<br/>扫一扫继续付款</p>
			</div>
			<img src="${ctx }/johu/images/wxp.png" class="zfyd"/>
		</div>
	</div>
</div>
</body>
</html>