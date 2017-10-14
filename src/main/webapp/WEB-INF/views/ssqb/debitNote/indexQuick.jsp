<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head> 
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>银联快捷</title>
<link href="${ctx }/${oemName}/css/main_style.css" rel="stylesheet" type="text/css">
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
		$(".delChars").on("touchstart", function() {
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
	
	function toPay(isJf){
		 if($.trim($("#jytext").val()) == "") {
		      alert("请输入金额!");
		      return false;
		    }
		
		var payMoney=$.trim($("#jytext").val());
		
		if(payMoney=="0"){
			alert("请输入正确的金额!");
		      return false;
		}
		if(isJf==1){
			if(parseFloat(payMoney)<10){
				alert("金额不能小于10元!");
			      return false;
			}
		}
		if(isJf==0){
			if(parseFloat(payMoney)<500){
				alert("金额不能小于500元!");
			      return false;
			}
		}
			var exp = /^([1-9][\d]{0,7}|0)(\.(([0-9]?[0-9])|([0-9][0-9]?)))?$/;
	    if(exp.test(payMoney)==false){
	    	alert("请输入正确的金额!");
		      return false;
	    }
	    
	    var remark = $.trim($("input.bzxx").val());
	    if(remark != "" && remark.length > 10){
	    	alert("备注信息不超过10个字符");
		      return false;
	    }
	    
	    $('#remark').val(remark);
	    $('#money').val(payMoney);
	    $('#isJf').val(isJf);
	    $('#submitForm').submit();
	   	return false;
	}
	
	
	
</script>
</head>

<body>

<div id="container">
<form action="${ctx }/debitNote/miPay?oemName=${oemName}" id="submitForm" name="computer">
<input  type="hidden" id="money" name="money" />
<input  type="hidden" id="isJf" name="isJf" />
<input  type="hidden" id="acc" name="acc" value="${acc}" />
<input  type="hidden" id="userId" name="userId" value="${userId}" />
<input  type="hidden" id="oemName" name="oemName" value="${oemName}" />
<input  type="hidden" id="remark" name="remark" value="" />
	<div class="jydivtop">
		<h1>${memberName}收款</h1>
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
			<button class="jybutbs" onClick="return toPay(0);">付款-无积分</button>
			<button class="jybutbs" onClick="return toPay(1);">付款-有积分</button>
			<%--<button value="付款" class="jybutb" id="getup" onClick="return toPay();">付款</button>
		--%></div>
	</div>
</form>
</div>

</body>
</html>