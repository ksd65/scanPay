<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>提现</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx }/js/jquery-2.2.3.min.js"></script>
</head>
<script type="text/javascript">
function toDraw(){
	//$("#submitBtn").css("background","gray");
	//$("#submitBtn").attr("disabled",true);
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
	var drawFee = $("#drawFee").val();
	var flag = window.confirm("提现手续费为"+drawFee+"元，您确定要提现吗？");
	if(flag){
		$.ajax({
			url:"${ctx }/memberInfo/drawCommit",
			data:{drawMoney:payMoney},
			type:'post',
			cache:false,
			async:false,
			dataType:'json',
			success:function(data) {
				if(data.returnCode=="0000"){//请求成功
					$('#msgTrue').show();
				//	window.location.href = "${ctx }/memberInfo/toDraw";
				}else{
					//$('#msgFalse').show();
					//$("#submitBtn").attr("disabled",false);
			        //$("#submitBtn").css("background","#46aaf0");
					alert(data.returnMsg);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {    
		        alert("请求出错");
		    }
		});
	}
	
}
function hideMsg(num){
	if(num==1){
		$('#msgTrue').hide();
	}else{
		$('#msgFalse').hide();
	}
	window.location.reload();
}
</script>
<body>

<div class="txbig">
	<div class="txbtit">
		<span>${resData.memberInfo.name}</span>
	</div>
	<div class="txbcon">
		<div class="txbctop">
			<input type="hidden" id="drawFee" value="${resData.drawFee }">
			<!-- <dd><span>当日交易金额</span><b class="black">￥${resData.tradeMoneyCountToday}</b></dd>
			<dd><span>当日已提现金额</span><b class="blue">￥${resData.drawMoneyCount}</b></dd>
			<dd><span>当日未提现金额</span><b class="red">￥${resData.unDrawMoneyCount}</b></dd>
			 -->
			<dd><span>账户余额</span><b class="black">￥${resData.balance}</b></dd>
			<dd><span>已提现金额</span><b class="blue">￥${resData.drawMoneyCountAll}</b></dd>
			<dd><span>可提现金额</span><b class="red">￥${resData.canDrawMoneyCount}</b></dd>
			
			<div class="jybz">
				<span>提现金额</span>
				<input type="text" class="bzxx" id="jytext"/>
			</div>
		</div>
		
	</div>
	<%--<input id="submitBtn" type="button" onClick="toDraw()" class="butblue" value="提 现">
--%>
	<a class="butblue"  href="javascript:void(0);" onclick="toDraw()" >提 现</a>
	</div>


<!--成功提示-->
<div id="msgTrue" class="txbfkd" style="display:none;">
	<div class="txbfkdcon">
		<img src="${ctx }/johu/images/cgi.png" />
		<span>提现申请提交成功</span>
		<p>后台审核需要一两个工作日<br/>请留意您的银行账户</p>
		<a class="cgx" href="javascript:;" onClick="hideMsg(1)"></a>
	</div>
</div>


<!--失败提示-->
<div id="msgFalse" class="txbfkd" style="display:none;">
	<div class="txbfkdcon">
		<img src="${ctx }/johu/images/sbi.png" />
		<span>提现申请失败</span>
		<p>由于当前申请提现的人数太多<br/>请您20分钟后再次尝试</p>
		<a class="sbx" href="javascript:;" onClick="hideMsg(2)"></a>
	</div>
</div>

</body>
</html>