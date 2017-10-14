<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>绑定信用卡</title>
<link href="${ctx }/johu/${oemName}/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx }/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="${ctx }/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx }/js/tools.js"></script>
<script>
	function txsb(){
		$('#txsb').hide();
	}
	function submitForm(){
		var name = $.trim($('#name').val());
		var bankId = $.trim($('#bankId').val());
		var cardNbr = $.trim($('#cardNbr').val());
		var phone = $.trim($('#phone').val());
		if("" == name){
			alert("持卡人姓名不能为空");
			return;
		}
		if("" == bankId){
			alert("开户银行不能为空");
			return;
		}
		if("" == cardNbr){
			alert("银行卡号不能为空");
			return;
		}
		if("" == phone){
			alert("预留电话不能为空");
			return;
		}
		ajaxSubmitForm();
	}
	
	function ajaxSubmitForm(){
		$("#submitForm").ajaxSubmit({
            url : "${ctx }/debitNote/toBindCard",  
            type : "post",  
            dataType : 'json',  
            success : function(data) {  
            	if(data.returnCode=="0000"){//请求成功
					window.location.href = "${ctx}/debitNote/chooseCard?oemName=${oemName}";
				}else{
					$('#txsb').show();
				} 
            },  
            error : function(data) {  
            	alert("请求出错"); 
            }  
        });  
	}
	
	function getBankList(bankId,selectName,settleType){
		var url = "";
		var data = {};
		if(selectName == "bankId"){
			url = "${ctx }/common/getBankList";
		}else{
			return;
		}
		$.ajax({
			url:url,
			data:data,
			type:'post',
			cache:false, 
			async:false,
			dataType:'json',
			success:function(data) {
				if(data.returnCode=="0000"){//请求成功
					var bankHtml = "";
					if(selectName == "bankId"){
						var bankList = data.resData.bankList;
						bankHtml = "<option value=''>请选择开户银行</option>";
						for(var i=0;i<bankList.length;i++){
							if("1" == settleType && bankList[i].kbinId == ""){
								continue;
							}else{
								bankHtml = bankHtml + "<option value='"+bankList[i].id+"'>"+bankList[i].name+"</option>";
							}
						}
					}else if(selectName == "subId"){
						bankHtml = "<option value=''>请选择开户支行</option>";
						var bankList = data.resData.subBankList;
						for(var i=0;i<bankList.length;i++){
							bankHtml = bankHtml + "<option value='"+bankList[i].subId+"'>"+bankList[i].subName+"</option>";
						}
					}
					$("select[name='"+selectName+"']").html(bankHtml);
				}else{
					alert(data.returnMsg);
				}
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {    
		        alert("请求出错");
		    }
		});
	}
	$(function(){
		getBankList(null,"bankId",null);
	});
</script>
</head>

<body>

<div class="zcimsdiv">
	<span class="zcimsts">请绑定持卡人本人的信用卡</span>
<form id="submitForm" action="#">
	<div class="zcimscon">
		<dl>
			<span>持卡人</span>
			<input id="name" name="name" type="text" class="zcimstxt" placeholder="请输入持卡人姓名" >
		</dl>
		<dl>
			<span>开户银行</span>
			<div class="setsb">
				<select id="bankId" name="bankId">
					<option value="">请选择开户银行</option>
				</select>
			</div>
		</dl>
		<dl>
			<span>卡号</span>
			<input id="cardNbr" name="cardNbr" type="text" class="zcimstxt" placeholder="请输入信用卡卡号">
		</dl>
		<dl>
			<span>手机号</span>
			<input id="phone" name="phone" type="text" class="zcimstxt" placeholder="请输入银行卡预留手机号">
		</dl>
	</div>
	<input type="button" class="zcimsbut" value="确认绑定" onclick="submitForm();">
</form>
</div>

<!--失败提示-->
<div id="txsb" class="txbfkd" style="display:none;">
	<div class="txbfkdcon">
		<img src="${ctx }/johu/${oemName}/images/sbi.png" />
		<span>信用卡绑定失败！</span>
		<p>持卡人与结算账户户名不一致<br/>请绑定同户名信用卡</p>
		<a class="sbx" href="javascript:;" onclick="txsb(0)"></a>
	</div>
</div>

</body>
</html>