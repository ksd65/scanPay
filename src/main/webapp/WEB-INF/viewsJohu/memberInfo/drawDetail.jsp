<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>提现详情</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">

</head>

<body>

<div class="zcimsdiv">
	<div class="zcimscon">
		<dl>
			<span>商户名称</span>
			<span class="grxxsp">${resData.memberInfo.name }</span>
		</dl>
		<dl>
			<span>商户编号</span>
			<span class="grxxsp">${resData.routewayDraw.memberCode }</span>
		</dl>
		<dl>
			<span>提现时间</span>
			<span class="grxxsp">${resData.routewayDraw.createDate }</span>
		</dl>
		<dl>
			<span>交易金额</span>
			<span class="grxxsp red">${resData.routewayDraw.money }元</span>
		</dl>
		<dl>
			<span>实际提现金额</span>
			<span class="grxxsp red">${resData.routewayDraw.drawamount }元</span>
		</dl>
		<dl>
			<span>提现手续费</span>
			<span class="grxxsp red">${resData.routewayDraw.drawfee }元</span>
		</dl>
		<dl>
			<span>审核状态</span>
			<span class="grxxsp blue">
				<c:if test="${resData.routewayDraw.auditStatus=='1' }">待审核</c:if>
				<c:if test="${resData.routewayDraw.auditStatus=='2' }">审核通过</c:if>
				<c:if test="${resData.routewayDraw.auditStatus=='3' }">审核不通过</c:if>
			</span>
		</dl>
		<dl>
			<span>提现状态</span>
			<span class="grxxsp blue">
				<c:if test="${resData.routewayDraw.respType=='S' }">提现成功</c:if>
				<c:if test="${resData.routewayDraw.respType=='E' }">提现失败</c:if>
				<c:if test="${resData.routewayDraw.respType=='R' }">提现中</c:if>
			</span>
		</dl>
		<dl>
			<span>结果说明</span>
			<span class="grxxsp">${resData.routewayDraw.respMsg }</span>
		</dl>
		<!--<c:if test="${resData.routewayDraw.respType=='S' }">
			<dl>
				<span>实际提现金额</span>
				<span class="grxxsp red">${resData.routewayDraw.drawamount }元</span>
			</dl>
			<dl>
				<span>提现手续费</span>
				<span class="grxxsp red">${resData.routewayDraw.drawfee }元</span>
			</dl>
			<dl>
				<span>交易手续费</span>
				<span class="grxxsp red">${resData.routewayDraw.tradefee }元</span>
			</dl>
		</c:if>
		<c:if test="${resData.routewayDraw.respType=='E' }">
			<dl>
				<span>交易金额</span>
				<span class="grxxsp red">${resData.routewayDraw.money }元</span>
			</dl>
		</c:if>-->
		<dl>
			<span>备注</span>
			<span class="grxxsp">${resData.routewayDraw.remarks }</span>
		</dl>
		<!--以下是提现失败
		<dl>
			<span>商户名称</span>
			<span class="grxxsp">李某某扫码收款</span>
		</dl>
		<dl>
			<span>商户编号</span>
			<span class="grxxsp">104251356210254</span>
		</dl>
		<dl>
			<span>提现时间</span>
			<span class="grxxsp">2016-11-01 10:25:43</span>
		</dl>
		<dl>
			<span>提现状态</span>
			<span class="grxxsp red">提现失败</span>
		</dl>
		<dl>
			<span>交易金额</span>
			<span class="grxxsp red">4299.72元</span>
		</dl>
		<dl>
			<span>备注</span>
			<span class="grxxsp">提现失败，失败原因</span>
		</dl>
		-->
	</div>
</div>

</body>
</html>