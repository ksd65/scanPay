<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>扫码支付</title>
<link href="${ctx }/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
</script>
</head>

<body>
<div class="zfndiv">
	<div class="zflog">
		<img src="${ctx }/images/qqlog.png" />
	</div>
	<div class="zfnr">
		<div class="zfewm">
			<img src="${ctx }/common/getQrCode?qrCode=${qrCode}" />
		</div>

		<dd class="zfts">
			<span>长按二维码进行支付</span>
			<span>或保存二维码使用手机QQ扫描支付</span>
		</dd>
	</div>
</div>
</body>
</html>