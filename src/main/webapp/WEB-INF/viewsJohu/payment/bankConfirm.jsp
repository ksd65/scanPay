<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>网银支付</title>
    <script src="${ctx }/js/jquery-2.2.3.min.js"></script>
    <style type="text/css">
        <!--
        TD {
            FONT-SIZE: 9pt
        }

        SELECT {
            FONT-SIZE: 9pt
        }

        OPTION {
            COLOR: #5040aa;
            FONT-SIZE: 9pt
        }

        INPUT {
            FONT-SIZE: 9pt
        }

        -->
    </style>

   
</head>
<body bgcolor="#FFFFFF">

<form name="pay" method="post" action="${ctx}/payment/toPayment" id="pay">
    <input type="hidden" id="memberCode" name="memberCode" value="${memberCode }"/>
    <input type="hidden" id="callbackUrl" name="callbackUrl" value="${callbackUrl }"/>
    <input type="hidden" id="bankCode" name="payMoney" value="${payMoney }"/>
    <input type="hidden" id="bankCode" name="goodsName" value="${goodsName }"/>
    <input type="hidden" id="bankCode" name="orderNum" value="${orderNum }"/>
    <input type="hidden" id="bankCode" name="bankCode" value="${bankCode }"/>
    <input type="hidden" id="signStr" name="signStr" value="${signStr }"/>
    
</form>
<script type="text/javascript">
    
    $("#pay").submit();
   
</script>
</body>
</html>

