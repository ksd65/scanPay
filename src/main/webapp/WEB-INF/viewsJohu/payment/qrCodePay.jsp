<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>扫码支付</title>
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
    	   var payMoney = $.trim($("input[name='payMoney']").val());
    	   if(payMoney==""){
	   			alert("请输入正确的金额!");
	   		    return;
	   		}
	   		var exp = /^([1-9][\d]{0,7}|0)(\.(([0-9]?[1-9])|([1-9][0-9]?)))?$/;
	   	    if(exp.test(payMoney)==false){
	   	    	alert("请输入正确的金额!");
	   		    return;
	   	    }
    	    
    	    $("#pay").submit();
    	}
    
	
	
	
	
	
</script>

    <%
        java.util.Date currentTime = new java.util.Date();//得到当前系统时间
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "yyyyMMddHHmmssSSS");
        java.lang.String sdfStr = sdf.format(currentTime);
    %>
</head>
<body bgcolor="#FFFFFF">

<form name="pay" method="post" action="${ctx}/payment/qrCodeConfirm" id="pay">
   <!-- <input type="hidden" id="memberCode" name="memberCode" value="9010000988"> --> 
	<input type="hidden" id="memberCode" name="memberCode" value="9010000002">
	<input type="hidden" id="orderNum" name="orderNum" value="${orderNum }">
	<input type="hidden" id="ip" name="ip" value="${ip }">
	<input type="hidden" id="callbackUrl" name="callbackUrl" value="http://www.johutech.com:8682/johuPosp/cashierDesk/testCallBack">

    <table bordercolordark="#FFFFFF" bordercolorlight="#333333" align="center" bgcolor="#F0F0FF" border="1"
           cellpadding="3" cellspacing="0" width="400">
        <tbody>
        <tr bgcolor="#8070FF">
            <td colspan="2">
                <div align="center">
                    <font color="#FFFF00"><b>扫码支付</b></font></div>
            </td>
        </tr>
   		<tr>
            <td>商户订单号(*)</td>
            <td><input id="orderNum1" name="orderNum1" size="16" type="text" value="${orderNum }" readonly="readonly"></td>
        </tr>
        <tr>
            <td>金额(*)</td>
            <td><input id="payMoney" name="payMoney" size="16" value="" type="text"></td>
        </tr>
        <tr>
            <td>支付类型(*)</td>
            <td>
                <select id="payType" name="payType" style="width: 115px">
                	<option value="1">微信</option>
                    <option value="2">支付宝</option>
                    <option value="3">QQ</option>
                </select>
            </td>
        </tr>
        
        
        <tr>
            <td colspan="2">
                <div align="center"><input value="支付" name="B1" type="button" onclick="toPay();"></div>
            </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>

