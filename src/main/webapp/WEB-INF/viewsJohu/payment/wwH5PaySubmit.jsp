
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<title>H5支付</title>
	
	
	
</script>
    
    
</head>
<body>


<form id="form" action="${payUrl }" method="post">
	<input type="hidden" id="payType" name="payType" value="${payType }">
	<input type="hidden" id="memberCode" name="memberCode" value="${memberCode }">
	<input type="hidden" id="orderNum" name="orderNum" value="${orderNum }">
	<input type="hidden" id="payMoney" name="payMoney" value="${payMoney }">
	<input type="hidden" id="sceneInfo" name="sceneInfo" value="${sceneInfo }">
	<input type="hidden" id="ip" name="ip" value="${ip }">
	<input type="hidden" id="callbackUrl" name="callbackUrl" value="${callbackUrl }">
	<input type="hidden" id="signStr" name="signStr" value="${signStr }">
</form>

<script type="text/javascript">
	document.getElementById("form").submit();
</script>


</body>
</html>
