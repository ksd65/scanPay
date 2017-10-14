<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>费率</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="xefldiv">
	<div class="xefltit">
		<img src="${ctx }/johu/images/yes_jh.png" />
		<span>您的费率是多少</span>
	</div>
	<div class="xeflcon">
		<div class="wdfltit">
			<span>结算周期</span>
			<span>服务费率</span>
			<span>手续费</span>
		</div>
		<div class="wdflcon">
			<dl><span>D+0</span><span><fmt:formatNumber type="number" value="${resData.memberInfo.t0TradeRate*100 }" pattern="0.00" maxFractionDigits="2"/>%</span><span>每笔${resData.memberInfo.t0DrawFee }元</span></dl>
			<dl><span>T+1</span><span><fmt:formatNumber type="number" value="${resData.memberInfo.t1TradeRate*100 }" pattern="0.00" maxFractionDigits="2"/>%</span><span>每笔${resData.memberInfo.t1DrawFee }元</span></dl>
		</div>
	</div>
</div>
<%--<div class="grfldiv">
	<div class="flzong">
		<h6>遵循微信、支付宝的限制</h6>
		<span>微信单笔最高：5000元</span>
		<span>支付宝单笔最高：30000元</span>
	</div>
	<div class="fwpt">
		<h6>服务平台</h6>
		<dd><img src="../images/wx.png" /><span>微信扫码</span></dd>
		<dd><img src="../images/zfb.png" /><span>支付宝扫码</span></dd>
	</div>
	<div class="flge">
		<h6>您的费率信息</h6>
		<div class="grfl">
			<dd class="dyh"><span>结算周期：实时到账</span><span>服务费率：<fmt:formatNumber type="number" value="${resData.memberInfo.t0TradeRate*100 }" pattern="0.00" maxFractionDigits="2"/>%</span></dd>
			<dd><span>结算周期：T+1</span><span>服务费率：<fmt:formatNumber type="number" value="${resData.memberInfo.t1TradeRate*100 }" pattern="0.00" maxFractionDigits="2"/>%</span></dd>
		</div>
	</div>
</div>

--%></body>
</html>