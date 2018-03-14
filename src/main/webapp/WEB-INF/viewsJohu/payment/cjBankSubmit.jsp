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
    <title>网银支付</title>
    
</head>
<body>

<form id="form" action="${action}" method="post">
    <input name="v_version" type="hidden" value="1.0.0.0" />
    <input name="v_mid" type="hidden" value="${v_mid}" />
    <input name="v_oid" type="hidden" value="${v_oid}" />
    <input name="v_time" type="hidden" value="${v_time}" />
    <input name="v_txnAmt" type="hidden" value="${v_txnAmt}" />
    <input name="v_bankAddr" type="hidden" value="${v_bankAddr}" />
    <input name="v_productName" type="hidden" value="${v_productName}" />
    <input name="v_productNum" type="hidden" value="1" />
    <input name="v_productDesc" type="hidden" value="${v_productDesc}" />
    <input name="v_type" type="hidden" value="0" />
    <input name="v_cardType" type="hidden" value="${v_cardType}" />
    <input name="v_currency" type="hidden" value="1" />
    <input name="v_expire_time" type="hidden" value="0" />
    <input name="v_notify_url" type="hidden" value="${v_notify_url}" />
    <input name="v_url" type="hidden" value="${v_url}" />
    <input name="v_channel" type="hidden" value="1" />
    <input name="v_attach" type="hidden" value="1" />
    <input name="v_sign" type="hidden" value="${v_sign}" />
</form>

<script type="text/javascript">
	document.getElementById("form").submit();
   // $(document).ready(function(){
   // 	$("#form").submit();
   // });
</script>

</body>
</html>
