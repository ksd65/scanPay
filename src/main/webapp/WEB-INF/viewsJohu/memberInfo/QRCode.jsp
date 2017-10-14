<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>我的二维码</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx }/js/jquery-2.2.3.min.js"></script>
</head>

<body>

<div class="wdewmdiv" style="display:${returnCode=='0000'?'block':'none'}">
	<img src="${ctx }/file_epay_qrcode${resData.qrCodePath}"/>
</div>
<div style="display:${returnCode!='0000'?'block':'none'};text-align: center;padding-top: 30%;font-size: 25px;">
	<span>${returnMsg }</span>
</div>

</body>
</html>