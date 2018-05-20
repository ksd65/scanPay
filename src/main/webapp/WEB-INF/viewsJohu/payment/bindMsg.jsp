<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>绑卡结果</title>
<link href="${ctx }/css/main_style.css" rel="stylesheet" type="text/css">
</head>
<body>

<body class="bgeee">
<div class="zccgdiv">
	<div class="zfcgmsg">
		<span>绑卡结果：<b>${returnMsg}</b></span>
	</div>
	<div class="zfcgimg">
		<img src="${ctx }/images/ggimg.png" />
	</div>
</div>
</body>



</html>
