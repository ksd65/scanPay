<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>个人信息</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx }/js/jquery-2.2.3.min.js"></script>
<style type="text/css">
.butred{display:block;width:88%;background: #f15a4a;height:2.2rem;border:none;border-radius: 0.3rem;font:1rem/2.2rem "微软雅黑";color:#fff;margin:1.5rem auto 0;text-align: center;}
</style>
</head>

<body>

<div class="zcimsdiv">
	<div class="zcimscon">
		<dl>
			<span>商户编号</span>
			<span class="grxxsp">${resData.memberInfoMore.code}</span>
		</dl>
		<dl>
			<span>商户名称</span>
			<span class="grxxsp">${resData.memberInfoMore.name}</span>
		</dl>
		<dl>
			<span>身份证号</span>
			<span class="grxxsp">${resData.memberInfoMore.certNbr}</span>
		</dl>
		<dl>
			<span>手机号码</span>
			<span class="grxxsp">${resData.memberInfoMore.mobilePhone}</span>
		</dl>
	</div>
	<div class="zcimscon">
		<dl>
			<span>收款姓名</span>
			<span class="grxxsp">${resData.memberInfoMore.contact}</span>
		</dl>
		<dl>
			<span>收款账号</span>
			<span class="grxxsp">${resData.memberInfoMore.cardNbr}</span>
		</dl>
		<dl>
			<span>开户银行${resData.memberInfoMore.createDate}55</span>
			<span class="grxxsp">${resData.memberInfoMore.bankOpen}</span>
		</dl>
	</div>
	<%--<a style="display: none;" class="xgxxa" href="#">修改信息</a>
	<a href="${ctx}/memberInfo/modifyPassword" class="butred">修改密码</a>--%>
	<a href="${ctx}/loginOut" class="butred">安全退出</a>
</div>

</body>
</html>