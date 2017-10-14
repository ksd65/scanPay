<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>审核中</title>
<link href="${ctx }/${oemName}/css/main_style.css" rel="stylesheet" type="text/css">
<style>
	body{background:#ededed;}
</style>
<script type="text/javascript">
</script>
</head>

<body>
	<div class="shzdiv">
		<img class="shtb" src="${ctx }/${oemName}/images/shz.png" />
		<span>正在审核中</span>
		<p>请到“商户状态”查询各支付渠道的状态</p>
		<img class="shbz" src="${ctx }/${oemName}/images/cksh.png" />
	</div>
</body>
</html>