<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>费率</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
</head>

<body>

<div class="feilcon">
	<div class="flcleft">
		<img src="${ctx }/johu/images/wx.png" />
		<span>微信</span>
	</div>
	<div class="flcright">
		<dl><span class="left">单日额度</span><span class="right">100000元/日</span></dl>
		<dl><span class="left">单笔额度</span><span class="right">3000元/笔</span></dl>
		<dl><span class="left">服务费率</span><span class="right">${resData.rate }%</span></dl>
		<dl class="lst"><span class="left">结算周期</span><span class="right">实时到账</span></dl>
	</div>
</div>
<div class="feilcon">
	<div class="flcleft">
		<img src="${ctx }/johu/images/zfb.png" />
		<span>支付宝</span>
	</div>
	<div class="flcright">
		<dl><span class="left">单日额度</span><span class="right">100000元/日</span></dl>
		<dl><span class="left">单笔额度</span><span class="right">3000元/笔</span></dl>
		<dl><span class="left">服务费率</span><span class="right">${resData.rate }%</span></dl>
		<dl class="lst"><span class="left">结算周期</span><span class="right">实时到账</span></dl>
	</div>
</div>

</body>
</html>