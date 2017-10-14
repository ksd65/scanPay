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
<script type="application/javascript" src="${ctx }/js/jquery-2.2.3.min.js"></script>
<script>
var isClick=false;
function doDraw(txnType){
	if(isClick){
		return;
	}else{
		isClick = true;
		$("a").css("color","#887e77");
	}
	var id="";
	var money;
	if(txnType==1){
		id = "wxDraw";
		money = ${resData.unDrawMoneyCountWx};
	}else if(txnType==2){
		id = "zfbDraw";
		money = ${resData.unDrawMoneyCountZfb};
	}else if(txnType==3){
		id = "qqDraw";
		money = ${resData.unDrawMoneyCountQq};
	}else if(txnType==4){
		id = "bdDraw";
		money = ${resData.unDrawMoneyCountBd};
	}else if(txnType==5){
		id = "jdDraw";
		money = ${resData.unDrawMoneyCountJd};
	}
	var d = new Date();
	var current = (d.getHours()>9?d.getHours():"0"+d.getHours())+":"+(d.getMinutes()>9?d.getMinutes():"0"+d.getMinutes());
	if(money<10){
		$('#msg3').show();
	}else if(current<"08:00" || current >"21:55"){
		$('#msg4').show();
	}
	else{
		$.ajax({
			url:"${ctx }/memberInfo/doDraw?txnType="+txnType,
			data:{},
			type:'post',
			cache:false,
			async:false,
			dataType:'json',
			success:function(data) {
				if(data.returnCode=="0000"){//请求成功
					$('#msg1').show();
				}else if(data.returnCode=="4002"){
					$('#msg2').show();
					//$("#submitBtn").attr("disabled",false);
			        //$("#submitBtn").css("background","#46aaf0");
				}else{
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
	$('#msg'+num).hide();
	window.location.reload();
}
</script>
</head>

<body>

<div class="txbig">
	<%--<div class="txbtit">
		<span>${resData.memberInfo.name}</span>
	</div>
	--%><div class="txfltop">
		<span>提现时间：早上8:00 至 晚上21:55</span>
	</div>
	<ul class="txflul">
		<li>
			<dd><img src="${ctx }/images/wx.png" /><span>微信</span></dd>
			<dd>
				<b>￥${resData.unDrawMoneyCountWx}</b>
				<p>未提现金额</p>
			</dd>
			<a href="javascript:;" id="wxDraw" onclick="doDraw(1)">立即提现</a>
		</li>
		<li>
			<dd><img src="${ctx }/images/zfb.png" /><span>支付宝</span></dd>
			<dd>
				<b>￥${resData.unDrawMoneyCountZfb}</b>
				<p>未提现金额</p>
			</dd>
			<a href="javascript:;" id="zfbDraw" onclick="doDraw(2)">立即提现</a>
		</li>
		<li>
			<dd><img src="${ctx }/images/qq.png" /><span>QQ钱包</span></dd>
			<dd>
				<b>￥${resData.unDrawMoneyCountQq}</b>
				<p>未提现金额</p>
			</dd>
			<a href="javascript:;" id="qqDraw" onclick="doDraw(3)">立即提现</a>
		</li>
		<li>
			<dd><img src="${ctx }/images/bd.png" /><span>百度钱包</span></dd>
			<dd>
				<b>￥${resData.unDrawMoneyCountBd}</b>
				<p>未提现金额</p>
			</dd>
			<a href="javascript:;" id="bdDraw" onclick="doDraw(4)">立即提现</a>
		</li>
		<li>
			<dd><img src="${ctx }/images/jd.png" /><span>京东钱包</span></dd>
			<dd>
				<b>￥${resData.unDrawMoneyCountJd}</b>
				<p>未提现金额</p>
			</dd>
			<a href="javascript:;" id="jdDraw" onclick="doDraw(5)">立即提现</a>
		</li>
	</ul>
	<span class="txflts">温馨提示：当日未提现金额，默认T+1到账。<br/>以上所显示金额为交易金额，未扣除费率和手续费</span>
</div>


<!--成功提示-->
<div id="msg1" class="txbfkd" style="display:none;">
	<div class="txbfkdcon">
		<img src="${ctx }/images/cgi.png" />
		<span>提现申请成功</span>
		<p>款项到账可能需要几分钟时间<br/>请留意您的银行账户</p>
		<a class="cgx" href="javascript:;" onClick="hideMsg(1)"></a>
	</div>
</div>
<div id="msg2" class="txbfkd" style="display:none;">
	<div class="txbfkdcon">
		<img src="${ctx }/images/cgi.png" />
		<span>提现申请已提交</span>
		<p>您的提现申请已提交<br/>稍后请到提现明细查询提现结果</p>
		<a class="cgx" href="javascript:;" onClick="hideMsg(2)"></a>
	</div>
</div>


<!--失败提示-->
<div id="msg3" class="txbfkd" style="display:none;">
	<div class="txbfkdcon">
		<img src="${ctx }/images/sbi.png" />
		<span>提现申请失败</span>
		<p>当前可提现金额不足￥10元<br/>不建议手动提现</p>
		<a class="sbx" href="javascript:;" onClick="hideMsg(3)"></a>
	</div>
</div>

<!--失败提示-->
<div id="msg4" class="txbfkd" style="display:none;">
	<div class="txbfkdcon">
		<img src="${ctx }/images/sbi.png" />
		<span>提现申请失败</span>
		<p>当前不在提现时间段内<br/>请您在8:00至21:55进行提现</p>
		<a class="sbx" href="javascript:;" onClick="hideMsg(4)"></a>
	</div>
</div>

</body>
</html>