<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>米连快捷支付</title>
<style type="text/css">
#all {
	margin: 0px auto;
	width: 500px;
	height: 200px;
}

#string {
	margin: 0px auto;
	width: 400px;
	height: 100px;
	text-align: center;
	font-size: 100px;
	color: green;
}
</style>
<script type="text/javascript">
	window.onload = function() {
		setTimeout(abc, 0);
	}
	function abc() {
		document.getElementById('myForm').submit();
	}
</script>
</head>
<BODY>
	<div style='margin:200px auto;text-align:center;'>
		  	<form name="form1" id="myForm" action="http://npsapi.mylandpay.com/MyLandQuickPay/servlet/QuickPay" method="post">		 
			<input type="hidden" name="ORDER_ID" value="${resData.memberQuickPay.ORDER_ID }">
			<input type="hidden" name="ORDER_AMT" value="${resData.memberQuickPay.ORDER_AMT }">
			<input type="hidden" name="ORDER_TIME" value="${resData.memberQuickPay.ORDER_TIME }">
			<input type="hidden" name="PAY_TYPE" value="${resData.memberQuickPay.PAY_TYPE }">
			<input type="hidden" name="USER_TYPE" value="${resData.memberQuickPay.USER_TYPE }">
			<input type="hidden" name="PAGE_URL" value="${resData.memberQuickPay.PAGE_URL }">
			<input type="hidden" name="BG_URL" value="${resData.memberQuickPay.BG_URL }">
			<input type="hidden" name="USER_ID" value="${resData.memberQuickPay.USER_ID }">
			<input type="hidden" name="SIGN" value="${resData.memberQuickPay.SIGN }">
		    <input type="hidden" name="SIGN_TYPE" value="${resData.memberQuickPay.SIGN_TYPE }" >
			<input type="hidden" name="BUS_CODE" value="${resData.memberQuickPay.BUS_CODE }">
			<input type="hidden" name="GOODS_NAME" value="${resData.memberQuickPay.GOODS_NAME }">
			<input type="hidden" name="GOODS_DESC" value="${resData.memberQuickPay.GOODS_DESC }">
			<input type="hidden" name="CCT" value="${resData.memberQuickPay.CCT }">
			<input type="hidden" name="AGEN_NO" value="${resData.memberQuickPay.AGEN_NO }">
			<input type="hidden" name="ID_NO" value="${resData.memberQuickPay.ID_NO }">
			<input type="hidden" name="SETT_ACCT_NO" value="${resData.memberQuickPay.SETT_ACCT_NO }">
			<input type="hidden" name="CARD_INST_NAME" value="${resData.memberQuickPay.CARD_INST_NAME }">
			<input type="hidden" name="NAME" value="${resData.memberQuickPay.NAME }">
			<input type="hidden" name="SETT_AMT" value="${resData.memberQuickPay.SETT_AMT }">
		    <input type="hidden" name="ADD1" value="${resData.memberQuickPay.ADD1 }">
			<input type="hidden" name="ADD2" value="${resData.memberQuickPay.ADD2 }">
		    <input type="hidden" name="ADD3" value="${resData.memberQuickPay.ADD3 }">
			<input type="hidden" name="ADD4" value="${resData.memberQuickPay.ADD4 }">
		    <input type="hidden" name="ADD5" value="${resData.memberQuickPay.ADD5 }">
		</form>
	</div>
</BODY>
</html>