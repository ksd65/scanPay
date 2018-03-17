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
    <input name="sp_id" type="hidden" value="${sp_id}" />
    <input name="mch_id" type="hidden" value="${mch_id}" />
    <input name="out_trade_no" type="hidden" value="${orderCode}" />
    <input name="swpaccid" type="hidden" value="${swpaccid}" />
    <input name="total_amt" type="hidden" value="${total_amt}">
    <input name="notifyurl" type="hidden" value="${callBack}">
    <input name="timestamp" type="hidden" value="${timestamp}">
    <input name="sign" type="hidden" value="${signStr}" />
</form>

<script type="text/javascript">
	document.getElementById("form").submit();
   // $(document).ready(function(){
   // 	$("#form").submit();
   // });
</script>

</body>
</html>
