<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0);    
%>
<html>
<head>
    <meta http-equiv="pragma" content="no-cache"> 
    <meta http-equiv="cache-control" content="no-cache"> 
    <meta http-equiv="expires" content="0"> 
    <title>支付</title>
    
</head>
<body>

<form id="form" action="${action}" method="post">
    <input name="requestTime" type="hidden" value="${requestTime}" />
    <input name="externalTraceNo" type="hidden" value="${externalTraceNo}" />
    <input name="merchantCode" type="hidden" value="${merchantCode}" />
    <input name="merchantId" type="hidden" value="${merchantId}" />
    <input name="terminalId" type="hidden" value="${terminalId}" />
    <input name="amt" type="hidden" value="${amt}" />
    <input name="productInfo" type="hidden" value="${productInfo}" />
    <input name="returnUrl" type="hidden" value="${returnUrl}" />
    <input name="secretInfo" type="hidden" value="${sign}" />
    
</form>

<script type="text/javascript">
//	document.getElementById("form").submit();
   // $(document).ready(function(){
   // 	$("#form").submit();
   // });
   var url = '${action}';
	window.location.href = url;
</script>

</body>
</html>
