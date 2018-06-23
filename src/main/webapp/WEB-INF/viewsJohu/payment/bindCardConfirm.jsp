<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>快捷支付绑卡</title>
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

<form name="pay" method="post" action="${ctx}/quickPay/bindCard" id="pay">
    <input type="hidden" id="memberCode" name="memberCode" value="${memberCode }"/>
    <input type="hidden" id="orderNum" name="orderNum" value="${orderNum }"/>
    <input type="hidden" id="accountType" name="accountType" value="${accountType }"/>
    <input type="hidden" id="bankCode" name="bankCode" value="${bankCode }"/>
    <input type="hidden" id="accountName" name="accountName" value="${accountName }"/>
    <input type="hidden" id="bankAccount" name="bankAccount" value="${bankAccount }"/>
    <input type="hidden" id="bankCvv" name="bankCvv" value="${bankCvv }"/>
    <input type="hidden" id="bankYxq" name="bankYxq" value="${bankYxq }"/>
    <input type="hidden" id="tel" name="tel" value="${tel }"/>
    <input type="hidden" id="certNbr" name="certNbr" value="${certNbr }"/>
    <input type="hidden" id="callbackUrl" name="callbackUrl" value="${callbackUrl }"/>
  <!--   <input type="hidden" id="frontUrl" name="frontUrl" value="${frontUrl }"/> -->
    <input type="hidden" id="signStr" name="signStr" value="${signStr }"/>
    
</form>
<script type="text/javascript">
    
    $("#pay").submit();
   
</script>
</body>
</html>

