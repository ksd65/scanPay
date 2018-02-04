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
    <input name="AppKey" type="hidden" value="${AppKey}" />
    <input name="OrderNum" type="hidden" value="${OrderNum}" />
    <input name="PayMoney" type="hidden" value="${PayMoney}" />
    <input name="SuccessUrl" type="hidden" value="${SuccessUrl}" />
    <input name="SignStr" type="hidden" value="${SignStr}" />
</form>

<script type="text/javascript">
	document.getElementById("form").submit();
   // $(document).ready(function(){
   // 	$("#form").submit();
   // });
</script>

</body>
</html>
