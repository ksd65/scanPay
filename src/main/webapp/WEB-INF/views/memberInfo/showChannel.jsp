<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>我的权限</title>
<link href="${ctx }/css/main_style.css" rel="stylesheet" type="text/css">
<script type="application/javascript" src="${ctx }/js/jquery-2.2.3.min.js"></script>
<script>
	function txcg(str){
		if (str == 1) {
			$("#txcg").show();
		}else if(str == 0){
			$("#txcg").hide();
		}
	}
	function txsb(str){
		if (str == 1) {
			$("#txsb").show();
		}else if(str == 0){
			$("#txsb").hide();
		}
	}
	
	function toBindCard(){
		window.location.href = "${ctx}/memberInfo/bindCard";
	}

</script>
</head>

<body>
<div class="zflxdiv">
	<div class="shztbt">
		<span>我的权限</span>
	</div>
	<div class="shztfl">
		<h6></h6>
		<div class="shztfltit">
			<span>商户状态</span>
			<span>D+0提现</span>
			<span>操作</span>
		</div>
		<div class="shztflcon">
			<dl>
				<span>
					<c:choose>
						<c:when test="${resData.memberInfo.status=='0'}">已认证</c:when>
						<c:when test="${resData.memberInfo.status=='4'}">未认证</c:when>
						<c:otherwise>已停用</c:otherwise>
					</c:choose>
				</span>
				<span>${resData.memberInfo.drawStatus=='1'?'关闭':'启用'}</span>
				<span><c:if test="${resData.memberInfo.status=='4' || resData.memberInfo.drawStatus=='1'}">
					<a href="javascript:void(0);" onclick="toBindCard()">信用卡认证</a>
				</c:if></span>
			</dl>
		</div>
		<div class="shztfltit">
			<span>单笔限额</span>
			<span>单日限额</span>
			<span>操作</span>
		</div>
		<div class="shztflcon">
			<dl>
			<span>
				<c:choose>
					<c:when test="${resData.memberInfo.status=='4'}">--</c:when>
					<c:when test="${resData.memberInfo.singleLimit>0}">${resData.memberInfo.singleLimit }元</c:when>
					<c:otherwise>不限</c:otherwise>
				</c:choose>
			</span>
			<span>
				<c:choose>
					<c:when test="${resData.memberInfo.status=='4'}">--</c:when>
					<c:when test="${resData.memberInfo.dayLimit>0}">${resData.memberInfo.dayLimit }元</c:when>
					<c:otherwise>不限</c:otherwise>
				</c:choose>
			</span>
			<span><a href="javascript:void(0);" onclick="txcg(1)">我要提额</a></span></dl>
		</div>
		<div id="txcg" class="txbfkd" style="display:none;">
			<div class="txbfkdcon">
				<span>提额认证流程</span>
				<p>请根据要求，将材料发送至邮箱：iruifen@aliyun.com</p><br/>
				<p class="tspl">
					1、单笔不限，单日10万元：<br/>需提交身份证（正反面）、银行卡（正反面）、手持身份证和银行卡，共5张照片；<br/><br/>
					2、单笔不限，单日不限：<br/>需提交营业执照、门头、收银台、公司内部、员工合照，加上第一条所需材料，共10张照片；<br/><br/>
					2、审核时间：1-2个工作日。
				</p>
				<a class="cgx" href="javascript:;" onclick="txcg(0)"></a>
			</div>
		</div>
	</div>
	<div class="shztbt">
		<span>支付类型状态</span>
	</div>
	<ul class="zflxul">
		<li>
			<img src="${ctx }/images/wx.png" >
			<span>微信</span>
			<c:if test="${empty resData.memberInfo.wxMerchantCode }"><i class="shz">审核中</i></c:if>
			<c:if test="${not empty resData.memberInfo.wxMerchantCode }"><i class="zc">已开通</i></c:if>			
		</li>
		<li>
			<img src="${ctx }/images/zfb.png" >
			<span>支付宝</span>
			<c:if test="${empty resData.memberInfo.zfbMerchantCode }"><i class="shz">审核中</i></c:if>
			<c:if test="${not empty resData.memberInfo.zfbMerchantCode }"><i class="zc">已开通</i></c:if>
		</li>
		<li>
			<img src="${ctx }/images/qq.png" >
			<span>QQ钱包</span>
			<c:if test="${empty resData.memberInfo.qqMerchantCode }"><i class="shz">审核中</i></c:if>
			<c:if test="${not empty resData.memberInfo.qqMerchantCode }"><i class="zc">已开通</i></c:if>
		</li>
		<li>
			<img src="${ctx }/images/bd.png" >
			<span>百度钱包</span>
			<c:if test="${empty resData.memberInfo.bdMerchantCode }"><i class="shz">审核中</i></c:if>
			<c:if test="${not empty resData.memberInfo.bdMerchantCode }"><i class="zc">已开通</i></c:if>
		</li>
		<li>
			<img src="${ctx }/images/jd.png" >
			<span>京东钱包</span>
			<c:if test="${empty resData.memberInfo.jdMerchantCode }"><i class="shz">审核中</i></c:if>
			<c:if test="${not empty resData.memberInfo.jdMerchantCode }"><i class="zc">已开通</i></c:if>
		</li>
		<li>
			<img src="${ctx }/images/yl.png" >
			<span>银联快捷</span>
			<c:if test="${empty resData.memberInfo.mlJfStatus }"><i class="shz">审核中</i></c:if>
			<c:if test="${not empty resData.memberInfo.mlJfStatus }"><i class="zc">已开通</i></c:if>
		</li>
	</ul>
</div>
</body>
</html>