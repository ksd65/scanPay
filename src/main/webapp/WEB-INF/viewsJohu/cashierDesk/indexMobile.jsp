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
	
	var payMoney=$.trim($(".zfjediv input").val());
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
					//location.href = "${ctx}/cashierDesk/getQrCodeM?qrCode=" + data.resData.qrCode;
				var submitUrl = "${ctx}/cashierDesk/getQrCodeM";
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

<form action="#">
	<div class="syggdiv">
		<a href="#">
			<img src="${ctx }/johu/images/gg.png" />
		</a>
	</div>
	<div class="zfjediv">
		<dd>
			<span>订单号：</span>
			<label>${orderNum }</label>
		</dd>
		<dd>
		<span>支付金额：</span>
		<input type="text" ${isMoneyFilled=='1'?"readonly='readonly'":'' } value="${isMoneyFilled=='1'?payMoney:'' }" placeholder="请输入支付金额">
		<i>元</i>
		</dd>
	</div>
	<div class="zffsdiv">
		<dl>
			<img src="${ctx }/johu/images/wx.png" />
			<dd>
				<span>微信扫码</span>
				<p>推荐安装微信5.0及以上版本的用户使用</p>
			</dd>
			<div class="zffsxz">
	            <input type="radio" id="radioFourInput1" value="1" name="zffs" checked="checked"/>
	            <label for="radioFourInput1"></label>
	        </div>
		</dl>
		<dl>
			<img src="${ctx }/johu/images/zfb.png" />
			<dd>
				<span>支付宝扫码</span>
				<p>建议使用9.0及以上版本的支付宝</p>
			</dd>
			<div class="zffsxz">
	            <input type="radio" id="radioFourInput2" value="2" name="zffs" />
	            <label for="radioFourInput2"></label>
	        </div>
		</dl>
	</div>
	<input type="button" class="butyel" value="确认支付" onclick="return toPay()">
	
	<input type="hidden" value="${orderNum }" name="orderNum">
	<input type="hidden" value="${memberCode }" name="memberCode">
	<input type="hidden" value="${callbackUrl }" name="callbackUrl">
	<input type="hidden" value="${isMoneyFilled }" name="isMoneyFilled">
	<input type="hidden" value="${platformType }" name="platformType">
	<input type="hidden" value="${signStr }" name="signStr">
</form>
<div id="blankCommonBlock" style="display: none;">
</div>
</body>
</html>