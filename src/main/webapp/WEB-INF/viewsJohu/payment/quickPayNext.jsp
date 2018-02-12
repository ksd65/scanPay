<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>快捷支付</title>
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
    
    <script type="text/javascript" lang="javascript">
	
    function toPay(){
		var smsCode=$.trim($("#smsCode").val());
		
		if(smsCode==""){
			alert("请输入短信验证码!");
		    return;
		}
		$("#pay").submit();
	}
	
	
</script>

   
</head>
<body bgcolor="#FFFFFF">

<form name="pay" method="post" action="${ctx}/payment/quickPayConfirm" id="pay">
   <!-- <input type="hidden" id="memberCode" name="memberCode" value="9010000988"> --> 
	<input type="hidden" id="memberCode" name="memberCode" value="${memberCode }">
	<input type="hidden" id="orderNum" name="orderNum" value="${orderNum }">
	
    <table bordercolordark="#FFFFFF" bordercolorlight="#333333" align="center" bgcolor="#F0F0FF" border="1"
           cellpadding="3" cellspacing="0" width="400">
        <tbody>
        <tr bgcolor="#8070FF">
            <td colspan="2">
                <div align="center">
                    <font color="#FFFF00"><b>快捷支付</b></font></div>
            </td>
        </tr>
   		<tr>
            <td>商户订单号(*)</td>
            <td><input id="orderNum1" name="orderNum1" size="16" type="text" value="${orderNum }" readonly="readonly"></td>
        </tr>
        <tr>
            <td>短信验证码(*)</td>
            <td><input id="smsCode" name="smsCode" size="16" value="" type="text"></td>
        </tr>
        
        
        <tr>
            <td colspan="2">
                <div align="center"><input value="提交" name="B1" type="button" onclick="toPay();"></div>
            </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>

