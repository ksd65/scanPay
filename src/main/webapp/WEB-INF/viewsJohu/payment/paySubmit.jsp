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
    <title>订单支付</title>
    
</head>
<body>

<form id="form" action="${ctx}/payment/tothird" method="post">
    <input name="msg" type="hidden" value="${msg}" />
</form>

<script type="text/javascript">
	document.getElementById("form").submit();
   // $(document).ready(function(){
   // 	$("#form").submit();
   // });
</script>

</body>
</html>
