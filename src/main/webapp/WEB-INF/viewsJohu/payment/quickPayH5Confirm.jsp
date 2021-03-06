
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<title>快捷支付</title>
	<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
    <script src="${ctx }/js/jquery-2.2.3.min.js"></script>
   
	
	
	
</script>
    
    
</head>
<body>


<form id="form" action="${ctx }/payment/toQuickPayH5" method="post">
	<input type="hidden" id="memberCode" name="memberCode" value="${memberCode }"/>
    <input type="hidden" id="callbackUrl" name="callbackUrl" value="${callbackUrl }"/>
    <input type="hidden" id="payMoney" name="payMoney" value="${payMoney }"/>
    <input type="hidden" id="goodsName" name="goodsName" value="${goodsName }"/>
    <input type="hidden" id="orderNum" name="orderNum" value="${orderNum }"/>
    <input type="hidden" id="bankAccount" name="bankAccount" value="${bankAccount }">
	<input type="hidden" id="tel" name="tel" value="${tel }">
    <input type="hidden" id="signStr" name="signStr" value="${signStr }"/>
</form>
<script type="text/javascript">
    
    $("#form").submit();
   
</script>

</body>
</html>
