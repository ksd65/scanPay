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
	
    
    	function sendSms(){
    	   var payMoney = $.trim($("input[name='payMoney']").val());
    	   var accountType = $("#accountType").val();
    	   var bankAccount = $.trim($("input[name='bankAccount']").val());
    	   var accountName = $.trim($("input[name='accountName']").val());
    	   var bankCvv = $.trim($("input[name='bankCvv']").val());
    	   var bankYxq = $.trim($("input[name='bankYxq']").val());
    	   var tel = $.trim($("input[name='tel']").val());
    	   
    	    if(payMoney==""){
	   			alert("请输入正确的金额!");
	   		    return;
	   		}
	   		var exp = /^([1-9][\d]{0,7}|0)(\.(([0-9]?[1-9])|([1-9][0-9]?)))?$/;
	   	    if(exp.test(payMoney)==false){
	   	    	alert("请输入正确的金额!");
	   		    return;
	   	    }
    	    if(bankAccount==""){
    	    	alert("请输入银行卡号!");
	   		    return;
    	    }
    	    if(accountName==""){
    	    	alert("请输入账户名称!");
	   		    return;
    	    }
    	    if(tel==""){
    	    	alert("请输入手机号!");
	   		    return;
    	    }
    	    
    	    if(accountType=="2"){
    	    	if(bankCvv==""){
        	    	alert("请输入卡背面后三位!");
    	   		    return;
        	    }
    	    	if(bankYxq==""){
        	    	alert("请输入卡有效期!");
    	   		    return;
        	    }
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

<form name="pay" method="post" action="${ctx}/payment/quickPaySms" id="pay">
   <!-- <input type="hidden" id="memberCode" name="memberCode" value="9010000988"> --> 
	<input type="hidden" id="memberCode" name="memberCode" value="9010000002">
	<input type="hidden" id="orderNum" name="orderNum" value="${orderNum }">
	<input type="hidden" id="callbackUrl" name="callbackUrl" value="http://www.johutech.com:8682/johuPosp/cashierDesk/testCallBack">

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
            <td>商品名称</td>
            <td><input id="goodsName" name="goodsName" size="16" value="" type="text"></td>
        </tr>
        <tr>
            <td>金额(*)</td>
            <td><input id="payMoney" name="payMoney" size="16" value="" type="text"></td>
        </tr>
        <tr>
            <td>账户类型(*)</td>
            <td>
                <select id="accountType" name="accountType" style="width: 115px">
                	<option value="1">借记卡</option>
                    <option value="2">信用卡</option>
                </select>
            </td>
        </tr>
        <tr>
            <td width="25%">银行卡号(*)</td>
            <td width="75%"><input id="bankAccount" name="bankAccount" size="16" type="text" value=""></td>
        </tr>
        <tr>
            <td width="25%">账户名称(*)</td>
            <td width="75%"><input id="accountName" name="accountName" size="16" type="text" value=""></td>
        </tr>
        
         <tr>
            <td width="25%">卡背面末三位</td>
            <td width="75%"><input id="bankCvv" name="bankCvv" size="16" type="text" value=""></td>
        </tr>
         <tr>
            <td width="25%">卡有效期</td>
            <td width="75%"><input id="bankYxq" name="bankYxq" size="16" type="text" value=""></td>
        </tr>
     
        <tr>
            <td width="25%">绑定手机号(*)</td>
            <td width="75%"><input id="tel" name="tel" size="16" type="text" value=""></td>
        </tr>
        
        <tr>
            <td colspan="2">
                <div align="center"><input value="发送验证码" name="B1" type="button" onclick="sendSms();"></div>
            </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>

