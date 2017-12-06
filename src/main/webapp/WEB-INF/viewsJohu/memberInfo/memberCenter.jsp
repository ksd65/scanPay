<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>商户中心</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
</head>

<body class="bgeee">

<div class="usercen">
	<div class="userxx">
	<a href="${ctx }/memberInfo/memberInfo">
		<img src="${ctx }/johu/images/slog.png" />
		<dd>
			<span>${resData.memberInfo.name}</span>
			<p>收款姓名&nbsp;&nbsp;&nbsp;${resData.memberInfo.contact}</p>	
			<p>绑定手机&nbsp;&nbsp;&nbsp;${resData.memberInfo.mobilePhone}</p>		
		</dd>
	</a>
	</div>
	<div class="userjy">
		<dd>
			<span>${resData.tradeMoneyDayCount}</span>
			<p>当日收款总额</p>
		</dd>
<!-- 		<dd>
			<span>${resData.unDrawMoneyCount}</span>
			<p>当日未提现金额</p>
		</dd> -->
	</div>
	<div class="usergj">
		<ul>
			<li><a href="${ctx }/memberInfo/transactionLog">
				<img src="${ctx }/johu/images/sh01.png" />
				<span>交易明细</span>
			</a></li>
		<!-- 	<li><a href="${ctx }/memberInfo/drawList">
				<img src="${ctx }/johu/images/sh02.png" />
				<span>提现明细</span>
			</a></li>
			<li><a href="${ctx }/memberInfo/draw">
				<img src="${ctx }/johu/images/sh06.png" />
				<span>我要提现</span>
			</a></li> -->
			<%--<li><a href="${ctx }/feeRate/index">
				<img src="${ctx }/johu/images/sh05.png" />
				<span>我的费率</span>
			</a></li>
			--%>
			<li><a href="${ctx }/memberInfo/showChannel">
				<img src="${ctx }/johu/images/sh04.png" />
				<span>我的权限</span>
			</a></li>
			<li><a href="${ctx }/memberInfo/feeRate">
				<img src="${ctx }/johu/images/sh07.png" />
				<span>我的费率</span>
			</a></li>
			<li><a href="${ctx }/memberInfo/modifyPassword">
				<img src="${ctx }/johu/images/sh03.png" />
				<span>修改密码</span>
			</a></li>
		
			<!--<li><a href="javascript:alert('暂未开放！');">
				<img src="images/sh04.png" />
				<span>实名认证</span>
			</a></li>-->
		</ul>
	</div>
</div>

</body>
</html>