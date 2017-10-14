<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>快捷支付</title>
<link href="${ctx }/css/main_style.css" rel="stylesheet" type="text/css">
</head>

<body>

<div id="container" style="text-align: center;">
<br/>
<br/>
<br/>
<h3 style="color: red;">交易失败!</h3>
<h3 style="color: red;">应答码:${returnCode}</h3>
<h3 style="color: red;">应答描述:${returnMsg}</h3>
</div>

</body>
</html>