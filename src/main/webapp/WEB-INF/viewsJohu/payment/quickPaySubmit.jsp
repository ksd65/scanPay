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
    <title>快捷支付</title>
    
</head>
<body>

<form id="form" action="${action}" method="post">
    <input name="v_version" type="hidden" value="${version}" />
    <input name="v_mid" type="hidden" value="${merchantCode}" />
    <input name="v_oid" type="hidden" value="${orderCode}" />
    <input name="v_smsCode" type="hidden" value="${smsCode}" />
    <input name="v_time" type="hidden" value="${time}">
    <input name="v_type" type="hidden" value="${type}">
    <input name="v_sign" type="hidden" value="${signStr}" />
</form>

<script type="text/javascript">
	document.getElementById("form").submit();
   // $(document).ready(function(){
   // 	$("#form").submit();
   // });
</script>

</body>
</html>
