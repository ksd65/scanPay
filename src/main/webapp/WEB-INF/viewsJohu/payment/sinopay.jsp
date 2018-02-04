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
    
    <script type="text/javascript" lang="javascript">
	
	
	function toPay(){alert($("#payMoney").val())
		 if($.trim($("#payMoney").val()) == "") {
		      alert("请输入金额!");
		      return;
		 }
		 
		
		var payMoney=$.trim($("#payMoney").val());
		
		if(payMoney=="0"){
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

<form name="pay" method="post" action="${ctx}/payment/bankConfirm" id="pay">
    <input type="hidden" id="memberCode" name="memberCode" value="9010000988">  
	<!--<input type="hidden" id="memberCode" name="memberCode" value="9010000002">-->
	<input type="hidden" id="orderNum" name="orderNum" value="${orderNum }">
	<input type="hidden" id="ip" name="ip" value="${ip }">
	<input type="hidden" id="callbackUrl" name="callbackUrl" value="http://www.johutech.com:8682/johuPosp/cashierDesk/testCallBack">

    <table bordercolordark="#FFFFFF" bordercolorlight="#333333" align="center" bgcolor="#F0F0FF" border="1"
           cellpadding="3" cellspacing="0" width="400">
        <tbody>
        <tr bgcolor="#8070FF">
            <td colspan="2">
                <div align="center">
                    <font color="#FFFF00"><b>网银支付</b></font></div>
            </td>
        </tr>
    <!--    <tr>
            <td width="25%">商户号(*)</td>
            <td width="75%"><input id="Mer_code" name="Mer_code" size="16" type="text" value="218337"></td>
        </tr>
        <tr>
            <td width="25%">商户名称(*)</td>
            <td width="75%"><input name="Mer_Name" size="16" value="测试商户" type="text"></td>
        </tr>
        <tr>
            <td width="25%">商户账户号(*)</td>
            <td width="75%"><input id="Mer_acccode" name="Mer_acccode" size="16" type="text" value="2183370010"></td>
        </tr>
        <tr>
            <td>版本号</td>
            <td><input name="version" type="text" id="version" value="v1.0.0"/></td>
        </tr>  -->
        <tr>
            <td>商户订单号(*)</td>
            <td><input id="orderNum" name="orderNum" size="16" type="text" value="${orderNum }" readonly="readonly"></td>
        </tr>
        <tr>
            <td>金额(*)</td>
            <td><input id="payMoney" name="payMoney" size="16" value="" type="text"></td>
        </tr>
     <!--    <tr>
            <td>订单日期(*)</td>
            <td><input name="Date" size="16" value="20160818" type="text"></td>
        </tr> 
        <tr>
            <td>支付币种(*)</td>
            <td>
                <select name="Currency_Type" style="width: 115px">
                    <option value="156" selected="selected">人民币</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>支持方式(*)</td>
            <td>
                <select name="gateWayType" style="width: 115px">
                	<option value="01" <c:if test="${gateWayType=='01' }">selected="selected"</c:if>>借记卡</option>
                    <option value="02" <c:if test="${gateWayType=='02' }">selected="selected"</c:if>>信用卡</option>
                </select>
            </td>
        </tr>
       <tr>
            <td>语言</td>
            <td>
                <select name="Lang" style="width: 115px">
                    <option selected="selected" value="GB">GB中文</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>支付结果成功返回地址</td>
            <td><input name="Merchanturl" size="70" type="text"
                       value="http://192.168.12.110:8080/payment-demo/merchant/success.html"></td>
        </tr>
        <tr>
            <td>支付结果失败返回地址</td>
            <td><input name="FailUrl" size="70" type="text"
                       value="https://www.ips.com.cn/ipay/Default.aspx"></td>
        </tr>
        <tr>
            <td>商户数据包</td>
            <td><input name="Attach" size="70" type="text" value="this is merchant attach!"></td>
        </tr>
        <tr>
            <td>订单支付加密方式(*)</td>
            <td>
                <select name="OrderEncodeType" style="width: 115px">
                    <option value="5" selected="selected">md5摘要</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>交易返回加密方式(*)</td>
            <td>
                <select name="RetEncodeType" style="width: 115px">
                    <option selected="selected" value="17">md5摘要</option>
                    <option value="16">md5withRsa</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>是否提供Server返回方式(*)</td>
            <td>
                <select name="Rettype">
                    <option value="1" selected="selected">Server to Server</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>商户S2S返回地址(*)</td>
            <td><input name="ServerUrl" size="70" value="http://192.168.12.110:8080/payment-demo/merchant/s2surl.html"
                       type="text"></td>
        </tr>
        <tr>
            <td>直连支付方式(*)</td>
            <td>
                <select name="DoCredit" style="width: 115px">
                    <option value="1">银行直连</option>
                    <option value="" selected="selected">非直连</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>直连个/企选择</td>
            <td>
                <select name="PrdType" style="width: 115px">
                    <option value="1" selected="selected">个人网银</option>
                    <option value="2">企业网银</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>直连银行号（网关号）</td>
            <td><input name="Bankco" size="16" value="1100" type="text"></td>
        </tr>--> 
        <tr>
            <td>商品名称</td>
            <td><input id="goodsName" name="goodsName" size="16" value="" type="text"></td>
        </tr>
     <!--   <tr>
            <td>订单有效期(小时为单位)</td>
            <td><input name="BillEXP" size="16" type="text" value="96"></td>
        </tr> --> 
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

