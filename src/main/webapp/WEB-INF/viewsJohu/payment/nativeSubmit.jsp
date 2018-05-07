<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>公众号支付</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
<script src="${ctx }/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript" lang="javascript">
	
	
	function toPay(){
		 
	    
	   	$.ajax({
			url:"${ctx }/debitNote/nativePay",
			data:{},
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
									location.href = "${ctx}/debitNote/payCallBack?orderCode="
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
 									//alert(res.err_msg);
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
	    
	   	return ;
	    
	}
	
	
	
</script>
</head>

<body>

<div class="zcimsdiv">
	<div class="zcimscon">
		<dl>
			<span>订单号</span>
			<span class="grxxsp">${orderNum }</span>
		</dl>
		<dl>
			<span>支付金额</span>
			<span class="grxxsp"><font color="red"><strong>￥${payMoney }</strong> </font> </span>
		</dl>
		
	</div>
	
	<a class="xgxxa" href="javascript:toPay();">付款</a>
	
</div>


</body>
</html>