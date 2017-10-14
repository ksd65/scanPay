<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>收银台</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
<script src="${ctx }/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript">
function toPay(){
	
	var payMoney=$.trim($("#payMoneyBlock").val());
	if(payMoney == "") {
	    alert("请输入金额!");
	    return false;
	}
	if(payMoney=="0"){
		alert("请输入正确的金额!");
	    return false;
	}
	var exp = /^([1-9][\d]{0,7}|0)(\.(([0-9]?[1-9])|([1-9][0-9]?)))?$/;
    if(exp.test(payMoney)==false){
   	alert("请输入正确的金额!");
	      return false;
   }
   var payType = $("input[name='zffs']:checked").val();
   if(null == payType || payType == ""){
	   alert("请选择支付方式");
	   return false;
   }
   var orderNum = $("input[name='orderNum']").val();
   var memberCode = $("input[name='memberCode']").val();
   var callbackUrl = $("input[name='callbackUrl']").val();
   var isMoneyFilled = $("input[name='isMoneyFilled']").val();
   var platformType = $("input[name='platformType']").val();
   var signStr = $("input[name='signStr']").val();
   var paramData = {payMoney: payMoney,
		   payType: payType,
		   orderNum: orderNum,
		   memberCode: memberCode,
		   callbackUrl: callbackUrl,
		   isMoneyFilled: isMoneyFilled,
		   platformType: platformType,
		   signStr: signStr
   };
  	$.ajax({
		url:"${ctx }/cashierDesk/pay",
		data:paramData,
		type:'post',
		cache:false, 
		async:false,
		success:function(data) {
			if(data.returnCode=="0000"){//请求成功
				var submitUrl = "${ctx}/cashierDesk/getQrCodeP";
				var htmlStr = "<form id='scanpaysubmit' action='"+ submitUrl + "' method='post'> " 
						  + " <input type='hidden' name='qrCodeDetail' value='"+data.resData.qrCode+"'> "
						  + " <input type='hidden' name='payTypeDetail' value='"+payType+"'>"
						  + " <input type='hidden' name='payMoneyDetail' value='"+payMoney+"'></form>";
						  $("#blankCommonBlock").html(htmlStr);
				$("#scanpaysubmit").submit();
			}else{
				alert(data.returnMsg);
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {    
	        alert("请求出错");
	    }
	});
   
  	return false;
   
}

</script>
</head>

<body class="bgf5">
<div class="sytdiv">
<form action="#">
	<div class="fkfsdiv">
		<h6>选择付款方式</h6>
		<div class="ddxxdiv">
			<dd><span>订单号：</span><label>${orderNum }</label></dd>
			<dd>
				<span>支付金额：</span>
				<input type="text" id="payMoneyBlock" ${isMoneyFilled=='1'?"readonly='readonly'":'' } value="${isMoneyFilled=='1'?payMoney:'' }" class="jetxt" placeholder="请输入支付金额"/><i>元</i>
			</dd>
		</div>	
	</div>
	<div class="fkfsson">
		<div class="fkstit">
			<span>支付方式</span>
		</div>
		<div class="fkscon">
			<dd>
				<div class="radioxx">
		            <input type="radio" id="radioFourInput" name="zffs" value="2"/>
		            <label for="radioFourInput"></label>    
		        </div>
		        <img src="${ctx }/johu/images/log_zfb.png" />
		        <span>—— 支付宝扫码支付</span>
		    </dd>
		    <dd>
				<div class="radioxx">
		            <input type="radio" id="radioFourInput2" name="zffs" value="1"/>
		            <label for="radioFourInput2"></label>    
		        </div>
		        <img src="${ctx }/johu/images/log_wx.png" />
		        <span>—— 微信扫码支付</span>
		    </dd>
		</div>
	</div>
	<input type="button" value="立即付款" class="fsbut" onclick="return toPay()"/> 
	
	<input type="hidden" value="${orderNum }" name="orderNum">
	<input type="hidden" value="${memberCode }" name="memberCode">
	<input type="hidden" value="${callbackUrl }" name="callbackUrl">
	<input type="hidden" value="${isMoneyFilled }" name="isMoneyFilled">
	<input type="hidden" value="${platformType }" name="platformType">
	<input type="hidden" value="${signStr }" name="signStr">
</form>
<div id="blankCommonBlock" style="display: none;">
	
</div>
</div>
</body>
</html>