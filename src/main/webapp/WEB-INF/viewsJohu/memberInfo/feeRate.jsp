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
<title>我的费率</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
<script type="application/javascript" src="${ctx }/js/jquery-2.2.3.min.js"></script>
<script>


</script>
</head>

<body>
<div class="zflxdiv">
	<div class="shztbt">
		<span>我的费率</span>
	</div>
	<div class="shztfl">
		<h6>扫码收款</h6>
		<div class="shztfltit">
			<span>结算周期</span>
			<span>服务费率</span>
			<span>手续费</span>
		</div>
		<div class="shztflcon">
			<dl><span>D+0</span><span><fmt:formatNumber type="number" value="${resData.memberInfo.t0TradeRate*100 }" pattern="0.00" maxFractionDigits="2"/>%</span><span>每笔${resData.memberInfo.t0DrawFee }元</span></dl>
			<dl><span>T+1</span><span><fmt:formatNumber type="number" value="${resData.memberInfo.t1TradeRate*100 }" pattern="0.00" maxFractionDigits="2"/>%</span><span>每笔${resData.memberInfo.t1DrawFee }元</span></dl>
		</div>
		<h6>银联快捷</h6>
		<div class="shztfltit">
			<span>积分</span>
			<span>服务费率</span>
			<span>手续费</span>
		</div>
		<div class="shztflcon">
			<dl><span>无积分</span><span><fmt:formatNumber type="number" value="${resData.memberInfo.mlWjfRate*100 }" pattern="0.00" maxFractionDigits="2"/>%</span><span>每笔${resData.memberInfo.mlWjfFee }元</span></dl>
			<dl><span>有积分</span><span><fmt:formatNumber type="number" value="${resData.memberInfo.mlJfRate*100 }" pattern="0.00" maxFractionDigits="2"/>%</span><span>每笔${resData.memberInfo.mlJfFee }元</span></dl>
		</div>
	</div>
</div>
</body>
</html>