<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>扫码支付</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
</script>
</head>

<body>
<br>
<br>
<br>
<div id="container" style="text-align: center;">
<img style="max-width: 80%;" alt="" src="${ctx }/common/getQrCode?qrCode=${qrCode}">
</div>

</body>
</html>