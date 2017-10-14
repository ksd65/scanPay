<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/myeltag" prefix="m"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>选择支付卡</title>
<link href="${ctx }/johu/${oemName}/css/main_style.css" rel="stylesheet" type="text/css">
<script src="${ctx }/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript">
function nextStep(){
	var acc = $("input[name='zffs']:checked").val();
	if(!acc){
		alert("请选择银行卡类型");
		return ;
	}
	var d = new Date();
	var current = (d.getHours()>9?d.getHours():"0"+d.getHours())+":"+(d.getMinutes()>9?d.getMinutes():"0"+d.getMinutes());
	if(current<"09:00" || current >"22:00"){
		alert("请在早上9点到晚上10期间操作！");
		return ;
	}
	$('#submitForm').submit();
}
</script>
</head>

<body>

<div class="xzkdiv">
	<span class="zcimsts">请选择要交易的信用卡</span>
<form id="submitForm" action="${ctx }/debitNote/miPayPage">
<input type="hidden" name="oemName" value="${oemName }"/>
<c:forEach var="bank" items="${resData.memberBindAccList}" varStatus="st">
	<dl>
		<div class="zffsxz">
            <input type="radio" id="radioFourInput${st.count}" value="${bank.acc }" name="zffs" />
            <label for="radioFourInput${st.count}"></label>
        </div>
        <c:if test="${bank.bankName=='中国工商银行' }">
	        <b class="yh001">工商银行</b>
        </c:if>
        <c:if test="${bank.bankName=='中国农业银行' }">
	        <b class="yh002">农业银行</b>
        </c:if>
        <c:if test="${bank.bankName=='中国银行' }">
	        <b class="yh003">中国银行</b>
        </c:if>
        <c:if test="${bank.bankName=='中国建设银行' }">
	        <b class="yh004">建设银行</b>
        </c:if>
        <c:if test="${bank.bankName=='交通银行' }">
	        <b class="yh005">交通银行</b>
        </c:if>
        <c:if test="${bank.bankName=='中信银行' }">
	        <b class="yh006">中信银行</b>
        </c:if>
        <c:if test="${bank.bankName=='中国光大银行' }">
	        <b class="yh007">光大银行</b>
        </c:if>
        <c:if test="${bank.bankName=='华夏银行' }">
	        <b class="yh008">华夏银行</b>
        </c:if>
        <c:if test="${bank.bankName=='中国民生银行' }">
	        <b class="yh009">民生银行</b>
        </c:if>
        <c:if test="${bank.bankName=='平安银行' }">
	        <b class="yh010">平安银行</b>
        </c:if>
        <c:if test="${bank.bankName=='招商银行' }">
	        <b class="yh011">招商银行</b>
        </c:if>
        <c:if test="${bank.bankName=='兴业银行' }">
	        <b class="yh012">兴业银行</b>
        </c:if>
        <c:if test="${bank.bankName=='上海浦东发展银行' }">
	        <b class="yh013">浦发银行</b>
        </c:if>
        <c:if test="${bank.bankName=='广发银行' }">
	        <b class="yh014">广发银行</b>
        </c:if>
        <c:if test="${bank.bankName=='中国邮政储蓄银行' }">
	        <b class="yh015">中国邮政储蓄银行</b>
        </c:if>
        <c:if test="${bank.bankName=='花旗银行' }">
	        <b class="yh016">花旗银行</b>
        </c:if>
        <span>${m:getEncryptCard(bank.acc)}</span>
	</dl>

</c:forEach>
	<a class="tjyhk" href="${ctx }/debitNote/bindCard?oemName=${oemName}">+ 添加银行卡</a>
	<input type="button" class="sbut" value="下一步" onclick="nextStep()">
    <a class="jbkp" href="tel:4007037777">解绑卡片，联系客服</a>
</form>
</div>

</body>
</html>

<!--
class 对应 银行图标
yh001  中国工商银行
yh002  中国农业银行
yh003  中国银行
yh004  中国建设银行
yh005  交通银行
yh006  中信银行
yh007  中国光大银行
yh008  华夏银行
yh009  中国民生银行
yh010  平安银行
yh011  招商银行
yh012  兴业银行
yh013  浦发银行
yh014  广发银行
yh015  中国邮政储蓄银行
yh016  花旗银行
-->