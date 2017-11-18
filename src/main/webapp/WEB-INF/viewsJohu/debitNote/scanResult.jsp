<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>收款</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
</head>

<body>

<div id="container" style="text-align: center;">
<br/>
<br/>
<br/>
<h3 style="color: red;">${resultMessage}</h3>
</div>

<script type="text/javascript" lang="javascript">
function refresh(){
	location.href = "${ctx}/debitNote/payCallBack?orderCode=${orderCode}&refresh=1";
}
var oriRespType = '${oriRespType}';
var flag = '${refresh}';
if(oriRespType=='R'&&(flag!='1')){
	var t1 = window.setTimeout("refresh()",3000);
}


</script>
</body>
</html>