<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>提现</title>
<link href="${ctx }/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx }/js/jquery-2.2.3.min.js"></script>
</head>
<script type="text/javascript">
function toDraw(){
	//$("#submitBtn").css("background","gray");
	//$("#submitBtn").attr("disabled",true);
	$.ajax({
		url:"${ctx }/memberInfo/checkToDraw",
		data:{},
		type:'post',
		cache:false,
		async:false,
		dataType:'json',
		success:function(data) {
			if(data.returnCode=="0000"){//请求成功
				//$('#msgTrue').show();
				window.location.href = "${ctx }/memberInfo/toDraw";
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
			<dd><span>当日交易金额</span><b class="black">￥${resData.tradeMoneyCountToday}</b></dd>
			<dd><span>当日已提现金额</span><b class="blue">￥${resData.drawMoneyCount}</b></dd>
			<dd><span>当日未提现金额</span><b class="red">￥${resData.unDrawMoneyCount}</b></dd>
		</div>
	</div>
	<%--<input id="submitBtn" type="button" onClick="toDraw()" class="butblue" value="提 现">
--%>
	<a class="butblue"  href="javascript:void(0);" onclick="toDraw()" >提 现</a>
	</div>


<!--成功提示-->
<div id="msgTrue" class="txbfkd" style="display:none;">
	<div class="txbfkdcon">
		<img src="${ctx }/images/cgi.png" />
		<span>提现申请成功</span>
		<p>款项到账可能需要几分钟时间<br/>请留意您的银行账户</p>
		<a class="cgx" href="javascript:;" onClick="hideMsg(1)"></a>
	</div>
</div>


<!--失败提示-->
<div id="msgFalse" class="txbfkd" style="display:none;">
	<div class="txbfkdcon">
		<img src="${ctx }/images/sbi.png" />
		<span>提现申请失败</span>
		<p>由于当前申请提现的人数太多<br/>请您20分钟后再次尝试</p>
		<a class="sbx" href="javascript:;" onClick="hideMsg(2)"></a>
	</div>
</div>

</body>
</html>