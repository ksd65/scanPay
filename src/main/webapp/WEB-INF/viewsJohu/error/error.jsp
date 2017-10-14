<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8"/>
<title>错误页面</title>
<meta name="viewport" content="width=device-width,maximum-scale=1.0,initial-scale=1.0,user-scalable=no"/>
<meta content="telephone=no" name="format-detection" />
<meta content="email=no" name="format-detection" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/iconfont.css">
<link type="text/css" rel="stylesheet" href="${ctx}/css/css.css">
<script src="${ctx}/js/all.js"></script>


</head>
<body class="f-arial pt-r bg-fff">
<div class="null tc">
<i class="iconfont icon-kulian tc"></i>
<p class="f45m cr-666 lh50 disb">对不起，页面出错啦！</p>
<div class="mt75m">
<a onclick="javascript:history.back();" class=" bg-2386c6 cr-fff f65m bor-rad-10">返回上一页</a>
</div>
</div>
</body>
</html>