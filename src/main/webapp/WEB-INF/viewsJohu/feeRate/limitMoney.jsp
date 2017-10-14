<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>限额</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
</head>

<body>

<div class="xefldiv">
	<div class="xefltit">
		<img src="${ctx }/johu/images/yes_jh.png" />
		<span>扫码收款的限额是多少</span>
	</div>
	<div class="xeflcon">
		<div class="wdxetit">
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为了给用户提供更好的服务，以及遵循微信、支付宝的限制。<br/>
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我们的限额设置如下：</span>
		</div>
		<div class="wdxecon">
			<dl><img src="${ctx }/johu/images/wx.png" /><span>单笔最高5000元</span></dl>
			<dl><img src="${ctx }/johu/images/zfb.png" /><span>单笔最高30000元</span></dl>
		</div>
	</div>
</div>

</body>
</html>