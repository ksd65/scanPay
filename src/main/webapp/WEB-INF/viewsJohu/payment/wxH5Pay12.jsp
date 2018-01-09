
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<title>微信H5支付</title>
	<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
    <script src="${ctx }/js/jquery-2.2.3.min.js"></script>
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
		      return;
		 }
		 
		
		var payMoney=$.trim($("#jytext").val());
		
		if(payMoney=="0"){
			alert("请输入正确的金额!");
		    return;
		}
			var exp = /^([1-9][\d]{0,7}|0)(\.(([0-9]?[1-9])|([1-9][0-9]?)))?$/;
	    if(exp.test(payMoney)==false){
	    	alert("请输入正确的金额!");
		    return;
	    }
	    
	    $("#payMoney").val(payMoney);
	    $("#form").submit();
	}
	
	
	
</script>
    
    
</head>
<body>

<div id="container">

	<div class="jydivtop">
		<h1>天悦网络7(84377714)</h1>
		<div class="xsqdiv">
			<span>￥</span>
			<input type="text" id="jytext" class="jytext" name="result" disabled="disabled" placeholder="0">
		</div>
	<!-- 	<div class="jybz">
			<span>通道</span>
			<select id="routeCode" name="routeCode" style="width: 80px">
				<option value="">请选择</option>
				<option value="1002">杉德</option>
				<option value="1007">随乐付</option>
			</select>
		</div>
		<div class="jybz">
			<span>前台回调地址</span>
			<input type="text" class="bzxx" id="frontUrl" name="frontUrl"/>
		</div> -->
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
			<button value="付款" class="jybutb" id="getup" onClick="toPay();">付款</button>
		</div>
	</div>

</div>










<form id="form" action="${ctx }/payment/h5Confirm" method="post">
	<input type="hidden" id="memberCode" name="memberCode" value="9010001068">
	<input type="hidden" id="orderNum" name="orderNum" value="${orderNum }">
	<input type="hidden" id="payMoney" name="payMoney" value="">
	<input type="hidden" id="sceneInfo" name="sceneInfo" value="测试">
	<input type="hidden" id="ip" name="ip" value="${ip }">
	<input type="hidden" id="callbackUrl" name="callbackUrl" value="http://www.johutech.com:8682/johuPosp/cashierDesk/testCallBack">
</form>


</body>
</html>
