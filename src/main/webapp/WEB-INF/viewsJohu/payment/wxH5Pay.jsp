<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>H5支付</title>
    <script src="${ctx }/js/jquery-2.2.3.min.js"></script>
</head>
<body>

<form id="form" action="${ctx }/payment/toWxH5" method="post">
	<input type="hidden" id="memberCode" name="memberCode" value="9010000988">
	<input type="hidden" id="orderNum" name="orderNum" value="${orderNum }">
	<input type="hidden" id="payMoney" name="payMoney" value="0.01">
	<input type="hidden" id="sceneInfo" name="sceneInfo" value="测试">
	<input type="hidden" id="ip" name="ip" value="${ip }">
	<input type="hidden" id="callbackUrl" name="callbackUrl" value="www.baidu.com">
	<input type="hidden" id="signStr" name="signStr" value="${signStr }">
</form>
<input type="button" id="btn" value="提交" onclick="tijiao();">
<script type="text/javascript">
    function tijiao(){
    	$("#form").submit();
    }
</script>

</body>
</html>
