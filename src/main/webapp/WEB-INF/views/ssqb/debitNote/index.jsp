<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>${memberName }收款</title>
<link href="${ctx }/${oemName}/css/main_style.css" rel="stylesheet" type="text/css">
<script src="${ctx }/js/jquery-2.2.3.min.js"></script>
<script src="http://open.mobile.qq.com/sdk/qqapi.js?_bid=152"></script>
<script src="https://open.mobile.qq.com/sdk/qqapi.js?_bid=152"></script>
<script type="text/javascript" lang="javascript">
	$(function() {
		//输入金额
		$(".inputChar").on("touchstart", function() {
			var char = $(this).text();
			var re = /^(0|([1-9]\d{0,5}))(\.\d{0,2})?$/g;
			var num = $("input[name=result]").val() + char;
			if (re.test(num)) {
				$("input[name=result]").val(num);
			}
		});
		//撤销
		$(".delChar").on("touchstart", function() {
			var num = $("input[name=result]").val();
			num = num.substring(0, num.length - 1);
			$("input[name=result]").val(num);
		});
		
		function fmoney(s, n) {	
		    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";		 
		    return s;  
	    }

		function strlen(str) {
			var realLength = 0, len = str.length, charCode = -1;
			for ( var i = 0; i < len; i++) {
				charCode = str.charCodeAt(i);
				if (charCode >= 0 && charCode <= 128)
					realLength += 1;
				else
					realLength += 2;
			}
			return realLength;
		}
		
	});
	
	function toPay(){
		 if($.trim($("#jytext").val()) == "") {
		      alert("请输入金额!");
		      return false;
		    }
		 
		
		var payMoney=$.trim($("#jytext").val());
		
		if(payMoney=="0"){
			alert("请输入正确的金额!");
		      return false;
		}
			var exp = /^([1-9][\d]{0,7}|0)(\.(([0-9]?[1-9])|([1-9][0-9]?)))?$/;
	    if(exp.test(payMoney)==false){
	    	alert("请输入正确的金额!");
		      return false;
	    }
	    
	    var remark = $.trim($("input.bzxx").val());
	    if(remark != "" && remark.length > 10){
	    	alert("备注信息不超过10个字符");
		      return false;
	    }
	    
	   	$.ajax({
			url:"${ctx }/debitNote/pay",
			data:{money:payMoney,epayCode:"${epayCode}",userId:"${userId}",remark:remark},
			type:'post',
			cache:false, 
			async:false,
			success:function(data) {
				if(data.returnCode=="0000"){//请求成功
					
					if("${userAgentType}"=="alipay"){
						AlipayJSBridge.call("tradePay",
								{tradeNO:data.resData.channelNo},
								function(result){
// 									var str = JSON.stringify(result);
// 									alert(str);
									location.href=location.href = "${ctx}/debitNote/payCallBack?orderCode="
										+ data.resData.orderCode;
								});
					}
				
//					if("${userAgentType}"=="micromessenger"){
//						location.href = "${ctx}/debitNote/getQrCode?qrCode="
//												+ data.resData.qrCode;
//					}

 					if("${userAgentType}"=="micromessenger"){
 						WeixinJSBridge
 						.invoke(
 								'getBrandWCPayRequest',
 								{
 									"appId" : data.resData.wxjsapiStr.appId, //公众号名称，由商户传入     
 									"timeStamp" : data.resData.wxjsapiStr.timeStamp, //时间戳，自1970年以来的秒数     
 									"nonceStr" : data.resData.wxjsapiStr.nonceStr, //随机串     
 									"package" : data.resData.wxjsapiStr.package_str,
 									"signType" : data.resData.wxjsapiStr.signType, //微信签名方式:     
 									"paySign" : data.resData.wxjsapiStr.paySign //微信签名
 								},
 								function(res) {
 									if (res.err_msg == "get_brand_wcpay_request:ok") {
 										location.href = "${ctx}/debitNote/payCallBack?orderCode="
 												+ data.resData.orderCode;
 									}else{// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
 										alert('支付失败');
 									} 
 								});
 					}
 					if("${userAgentType}"=="mqqbrowser"){
						location.href = "${ctx}/debitNote/getQrCode?payType=3&qrCode=" + data.resData.qrCode;
					}else if("${userAgentType}"=="baiduwallet"){
						location.href = "${ctx}/debitNote/getQrCode?payType=4&qrCode=" + data.resData.qrCode;
					}else if("${userAgentType}"=="jdjrpay"){
						location.href = "${ctx}/debitNote/getQrCode?payType=5&qrCode=" + data.resData.qrCode;
					}
					
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

<body>

<div id="container">
<form action="#" name="computer">
	<div class="jydivtop">
		<h1>${memberName }收款</h1>
		<div class="xsqdiv">
			<span>￥</span>
			<input type="text" id="jytext" class="jytext" name="result" disabled="disabled" placeholder="0">
		</div>
		<div class="jybz">
			<span>备注信息</span>
			<input type="text" class="bzxx" />
		</div>
		<p>谨防网店刷单、强行开通花呗/借呗/微粒贷等电子信贷产品、钓鱼网站等诈骗行为。请谨慎付款！</p>
	</div>
	<div class="buttons">
		<div class="butleft">
			<span class="inputChar">1</span>
			<span class="inputChar">2</span>
			<span class="inputChar">3</span>
			<span class="inputChar">4</span>
			<span class="inputChar">5</span>
			<span class="inputChar">6</span>
			<span class="inputChar">7</span>
			<span class="inputChar">8</span>
			<span class="inputChar">9</span>
			<span class="inputChar">0</span>
			<span class="inputChar">.</span>
			<span class="delChar">←</span>
		</div>
		<div class="butright">
			<button value="付款" class="jybutb" id="getup" onClick="return toPay();">付款</button>
		</div>
	</div>
</form>
</div>

</body>
</html>