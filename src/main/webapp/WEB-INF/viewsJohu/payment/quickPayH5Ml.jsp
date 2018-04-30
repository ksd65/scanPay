<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>订单支付</title>
    <script src="${ctx }/js/jquery-2.2.3.min.js"></script>
</head>
<body>

<form id="form" action="${action}" method="post">
    <input name="ORDER_ID" type="hidden" value="${ORDER_ID}" />
    <input name="ORDER_TIME" type="hidden" value="${ORDER_TIME}" />
    <input name="ORDER_AMT" type="hidden" value="${ORDER_AMT}" />
    <input name="USER_TYPE" type="hidden" value="${USER_TYPE}" />
    <input name="USER_ID" type="hidden" value="${USER_ID}" />
    <input name="SIGN_TYPE" type="hidden" value="${SIGN_TYPE}" />
    <input name="BUS_CODE" type="hidden" value="${BUS_CODE}" />
    <input name="PAY_TYPE" type="hidden" value="${PAY_TYPE}" />
    <input name="CCT" type="hidden" value="${CCT}" />
    <input name="ADD1" type="hidden" value="${ADD1}" />
    <input name="ID_NO" type="hidden" value="${ID_NO}" />
    <input name="PHONE_NO" type="hidden" value="${PHONE_NO}" />
    <input name="BG_URL" type="hidden" value="${BG_URL}" />
    <input name="PAGE_URL" type="hidden" value="${PAGE_URL}" />
    <input name="SETT_ACCT_NO" type="hidden" value="${SETT_ACCT_NO}" />
    <input name="CARD_INST_NAME" type="hidden" value="${CARD_INST_NAME}" />
    <input name="NAME" type="hidden" value="${NAME}" />
    <input name="SIGN" type="hidden" value="${SIGN}" />
    
</form>

<script type="text/javascript">
    $(document).ready(function(){
        $("#form").submit();
    });
</script>

</body>
</html>
