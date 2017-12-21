<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>H5支付跳转</title>
    <script src="${ctx }/js/jquery-2.2.3.min.js"></script>
</head>
<body>

<form id="form" action="${payUrl}" method="post">
</form>

<script type="text/javascript">
    $(document).ready(function(){
        $("#form").submit();
    });
</script>

</body>
</html>
