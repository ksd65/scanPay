
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<title>扫码支付</title>
	<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
    <script src="${ctx }/js/jquery-2.2.3.min.js"></script>
   
	
	
	
</script>
    
    
</head>
<body>


<form id="form" action="${ctx }/payment/toH5" method="post">
	<input type="hidden" id="payType" name="payType" value="${payType }">
	<input type="hidden" id="memberCode" name="memberCode" value="${memberCode }">
	<input type="hidden" id="orderNum" name="orderNum" value="${orderNum }">
	<input type="hidden" id="payMoney" name="payMoney" value="${payMoney }">
	<input type="hidden" id="ip" name="ip" value="${ip }">
	<input type="hidden" id="callbackUrl" name="callbackUrl" value="${callbackUrl }">
	<input type="hidden" id="signStr" name="signStr" value="${signStr }">
</form>
<div id="blankCommonBlock" style="display: none;">
<script type="text/javascript">
    
function toPay(){
	
   var orderNum = $("input[name='orderNum']").val();
   var memberCode = $("input[name='memberCode']").val();
   var callbackUrl = $("input[name='callbackUrl']").val();
   var payMoney = $("input[name='payMoney']").val();
   var payType = $("input[name='payType']").val();
   var ip = $("input[name='ip']").val();
   var signStr = $("input[name='signStr']").val();
   var paramData = {payMoney: payMoney,
		   payType: payType,
		   orderNum: orderNum,
		   memberCode: memberCode,
		   callbackUrl: callbackUrl,
		   ip: ip,
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
					//location.href = "${ctx}/cashierDesk/getQrCodeM?qrCode=" + data.resData.qrCode;
				var submitUrl = "${ctx}/cashierDesk/getQrCodeM";
				var htmlStr = "<form id='scanpaysubmit' action='"+ submitUrl + "' method='post'> " 
						  + " <input type='hidden' name='qrCodeDetail' value='"+data.qrCode+"'> "
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
}


$(document).ready(function(){
	toPay();  
});
 
</script>

</body>
</html>
