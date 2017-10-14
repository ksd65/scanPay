<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>注册信息</title>
<link href="css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="js/ajaxfileupload.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/tools.js"></script>
<style type="text/css">
	.mysearchbtn{
		width: 18%;
		display: inline-block;
		margin: 7px 0;
		float: right;
		height: 1.7rem;
		border: none;
		border-radius: 0.3rem;
		background: #46aaf0;
		color: #fff;
		font: 0.8rem/1.7rem;
	}
</style>
</head>
<body>
<form id="registerForm" enctype="multipart/form-data">
<div class="zcimsdiv">
	<span class="zcimsts">因央行政策规定，互联网支付必须进行实名认证，请填写您的真实信息！</span>
	<div class="zcimscon">
		<dl>
			<span>真实姓名</span>
			<input type="text" name="memberName" class="zcimstxt" placeholder="请输入您的真实姓名">
		</dl>
		<dl>
			<span>身份证号</span>
			<input type="text" name="certNbr" class="zcimstxt" placeholder="请输入您的身份证号" >
		</dl>
		<dl>
			<span>联系人类型</span>
			<div class="setsb">
				<select name="select_c">
				<option value="">请选择联系人类型</option>
				<option value="01">法人</option>
				<option value="02">实际控制人</option>
				<option value="03">代理人</option>
				<option value="00">其他</option>
				</select>
			</div>
		</dl>
		<!-- 
		<dl>
			<span>营业执照</span>
			<input type="text" name="busLicenceNbr" class="zcimstxt" placeholder="请输入您的营业执照编号" />
		</dl>
		 -->
		<dl>
				<span>所在省市</span>
				<div class="setsa">
					<select name="addr_province" onchange="addr_provinceChange(this.value)">
					<option value="">省</option>
					</select>
				</div>
				<em></em>
				<div class="setsa">
					<select name="addr_city" onchange="addr_cityChange(this.value)">
					<option value="">市</option>
					</select>
				</div>
			</dl>
 
			<dl>
				<span>所在市县</span>
				<div class="setsb">
					<select name="addr_county" onchange="fillAddr()">
					<option value="">市/县</option>
					</select>
				</div>				
			</dl>
		<dl>
			<span>详细地址</span>
			<input type="text" name="addr" class="zcimstxt" onfocus="show('dz');" onblur="hide('dz');" placeholder="请输入您的详细地址">
		</dl>
		<div class="shxxtc" id="dz" style="display:none;">
			<p>
				地址填写规则：<br/>
				xx 路 xx 号<br/>
				如：五一路001号<br/>
				请按规则填写，否则将导致审核失败、无法交易！
			</p>
		</div>
		<dl>
			<span>商户名称</span>
			<input type="text" name="merchantName" class="zcimstxt" onfocus="show('mc');" onblur="hide('mc');" placeholder="请输入您的商户名称">
		</dl>
		<div class="shxxtc" id="mc" style="display:none;">
			<p>
				商户命名规则：<br/>
				1、公司名称：行政规划+字号+行业+组织形式；<br/>
				如：厦门开心贸易有限公司；<br/>
				2、商店名称：行政规划+字号+行业+组织形式；<br/>
				如：厦门市思明区开心小吃店；<br/>
				3、个体户：个体户+法人姓名；<br/>
				如：个体户张三。<br/>
				请按规则填写，否则将导致审核失败、无法交易！
			</p>
		</div>
		<dl>
			<span>商户简称</span>
			<input type="text" name="shortName" class="zcimstxt" onfocus="show('jc');" onblur="hide('jc');" placeholder="请输入您的商户简称">
		</dl>
		<div class="shxxtc" id="jc" style="display:none;">
			<p>
				商户简称命名规则：<br/>
				1、公司关键字，如：开心贸易；<br/>
				2、实体店名，如：开心小吃店；<br/>
				3、法人姓名+销售产品，如：张三电器。<br/>
				请按规则填写，否则将导致审核失败、无法交易！
			</p>
		</div>
		<div class="gll"></div>
		<div class="zcimscon">
			<dl>
				<span>手机号码</span>
				<input type="text" name="mobilePhone" class="zcimstxt" placeholder="请输入您的手机号码">
			</dl>
			<dl>
				<span>验证码</span>
				<input type="text" name="verifyCode" class="yzmtxt" placeholder="请输入验证码">
				<em></em>
				<dd>
					<a id="xsx1" href="javascript:;" onclick="getMessageCode();" class="get">获取验证码</a>	
					<a id="xsx2" class="gets" style="display:none;">重新发送(<i id="mias">60</i>)</a>			
				</dd>
			</dl>
		</div>
	</div>
	<input type="hidden" name="epayCode" value="${epayCode }">
	<input type="hidden" name="agentType" value="${agentType }">
	<input type="hidden" name="agentCode" value="${agentCode }">
	<input type="hidden" name="oemName" value="${oemName }">
	<input type="button" class="zcimsbut" value="下一步" onclick="stepChange(1)">
</div>
<div id="stepChangeBlock" style="display: none;position: fixed;top:0;height: 100%;width: 100%;background-color: #f8f8f8;">
	<div class="zcimsdiv">
		<div class="zcimscon">
			<%--<dl>
				<span>结算方式</span>
				<div class="setsb">
					<select name="settleType" onchange="settleTypeChange(this.value)">
					<option value="">请选择结算方式</option>
					<option value="0">D+0</option>
					<option value="1">T+1</option>
					</select>
				</div>
			</dl>
			<dl>
				<span>开户区域</span>
				<div class="setsa">
					<select name="province" onchange="provinceChange(this.value)">
					<option value="">省/区</option>
					</select>
				</div>
				<em></em>
				<div class="setsa">
					<select name="city" onchange="cityChange(this.value)">
					<option value="">市/县</option>
					</select>
				</div>
			</dl>--%>
			<dl>
				<span>开户银行</span>
				<div class="setsb">
					<select name="bankId">
						<option value="">请选择开户银行</option>
					</select>
				</div>
			</dl>
			<dl>
				<span>收款卡号</span>
				<input type="text" name="cardNbr" class="zcimstxt" placeholder="请输入您的收款卡号">
				<select name="settleType" hidden="true">
					<option value="0" selected="selected">D+0</option>
				</select>
			</dl>
			<%--<dl>
				<span>搜索支行</span>
				<div>
					<input id="searchTextBank" type="text" class="zcimstxt" style="width:55%;" placeholder="搜索开户支行">
					<input id="subBankSearchBtn" onclick="searchBank()" type="button" class="mysearchbtn" value="搜索"/>
				</div>
			</dl>
			<dl>
				<span>开户支行</span>
				<div class="setsb">
					<select name="subId">
					<option value="">请选择开户支行</option>
					</select>
				</div>
			</dl>
		--%></div>
	</div>
	<div class="sczpdiv">
		<div class="tyxydiv">
			<div class="checkboxFour">
		        <div class="chedxx">
		            <input type="checkbox" id="checkboxFourInput" value="1"/>
		            <label for="checkboxFourInput"></label>    
		        </div>
		    	<a href="#" class="ckb">同意《用户服务协议》</a>
		    </div>
		</div>
		<div class="scan">
			<a href="#" onclick="stepChange(2)">上一步</a>
			<input class="sczpbut" onclick="submitRegister()" type="button" style=" text-align: center;" value="提交审核">
		</div>
	</div>
	<%--<div class="sczpdiv">
		<div class="ttsccon">
			<div id="img">
				<div class="imgbox">
				    <div class="imgnum">
				        <input id="uploadCertImg" type="file"  accept="image/*" class="filepath" name="certImg"/>
				        <img src="images/sfz.png" class="img1" />
				        <img id="uploadCertImg_Path" src="" class="img2" />
				    </div>
				</div>
			</div>
			<span>身份证正面照</span>
		</div>
		<div class="ttsccon">
			<div id="img">
				<div class="imgbox">
				    <div class="imgnum">
				        <input id="uploadCardImg" type="file"  accept="image/*" class="filepath" name="cardImg"/>
				        <img src="images/yhk.png" class="img1" />
				        <img id="uploadCardImg_Path" src="" class="img2" />
				    </div>
				</div>
			</div>
			<span>银行卡正面照</span>
		</div>
		<div class="tyxydiv">
			<div class="checkboxFour">
		        <div class="chedxx">
		            <input type="checkbox" id="checkboxFourInput" value="1"/>
		            <label for="checkboxFourInput"></label>    
		        </div>
		    	<a href="#" class="ckb">同意《用户服务协议》</a>
		    </div>
		</div>
		<div class="scan">
			<a href="#" onclick="stepChange(2)">上一步</a>
			<input class="sczpbut" onclick="submitRegister()" type="button" style=" text-align: center;" value="提交审核">
		</div>
	</div>
--%>
</div>
</form>
</body>
<script type="text/javascript">
$(function(){
	//getAreaList(1,"province");
	getKBankList();
	//register();
});
//获取地址归属省--》市---》县
$(function(){
	getAddrAreaList(1,"addr_province");
});
function getAddrAreaList(areacode,selectName){
	$.ajax({
		url:"${ctx }/common/getAddrAreaList",
		data:{areacode:areacode},
		type:'post',
		cache:false, 
		async:false,
		dataType:'json',
		success:function(data) {
			if(data.returnCode=="0000"){//请求成功
				var areaList = data.resData.areaList;
				var provinceCityHtml = "";
				if("addr_province" == selectName){
					provinceCityHtml = "<option value=''>省</option>";
				}else if("addr_city" == selectName){
					provinceCityHtml = "<option value=''>市</option>";
				}else if("addr_county"==selectName){
					provinceCityHtml = "<option value=''>市/县</option>";
				}
				for(var i=0;i<areaList.length;i++){
					provinceCityHtml = provinceCityHtml+ "<option value='"+areaList[i].areacode+"'>"+areaList[i].areaname+"</option>";	
				}
				$("select[name='"+selectName+"']").html(provinceCityHtml);
			}else{
				alert(data.returnMsg);
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {    
	        alert("请求出错");
	    }
	});

}
function fillAddr(){}
/**不用了，先改个名字，暂时保留**/
function fillAddr2(){
	var addr = "";
	var province = $.trim($("select[name='addr_province']").val());
	var city = $.trim($("select[name='addr_city']").val());
	var county = $.trim($("select[name='addr_county']").val());
	if(province){
		addr += $("select[name='addr_province'] option:selected").text();
	}
	if(city){
		addr += $("select[name='addr_city'] option:selected").text();
	}
	if(county){
		addr += $("select[name='addr_county'] option:selected").text();
	}
	$("input[name='addr']").val(addr);
}
function addr_provinceChange(provinceVal){ 
	if("" == provinceVal){
		$("select[name='addr_city']").html("<option value=''>市</option>");
		$("select[name='addr_county']").html("<option value=''>市/县</option>");
	}else{
		getAddrAreaList(provinceVal,"addr_city");
		$("select[name='addr_county']").html("<option value=''>市/县</option>");
	}
	fillAddr();
}

function addr_cityChange(cityVal){
	$("select[name='addr_county']").html("<option value=''>市/县</option>");
	if("" != cityVal){
		getAddrAreaList(cityVal,"addr_county");
	} else{
		
	}
	fillAddr();
 }
	function getAreaList(parentId,selectName){
		$.ajax({
			url:"${ctx }/common/getAreaList",
			data:{parentId:parentId},
			type:'post',
			cache:false, 
			async:false,
			dataType:'json',
			success:function(data) {
				if(data.returnCode=="0000"){//请求成功
					var areaList = data.resData.areaList;
					var provinceCityHtml = "";
					if("province" == selectName){
						provinceCityHtml = "<option value=''>省/区</option>";
					}else if("city" == selectName){
						provinceCityHtml = "<option value=''>市/县</option>";
					}
					for(var i=0;i<areaList.length;i++){
						provinceCityHtml = provinceCityHtml+ "<option value='"+areaList[i].id+"'>"+areaList[i].name+"</option>";	
					}
					$("select[name='"+selectName+"']").html(provinceCityHtml);
				}else{
					alert(data.returnMsg);
				}
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {    
		        alert("请求出错");
		    }
		});
	
	}
	
	function provinceChange(provinceVal){
		$("select[name='bankId']").html("<option value=''>请选择开户银行</option>");
		$("select[name='subId']").html("<option value=''>请选择开户支行</option>");
		if("" == provinceVal){
			$("select[name='city']").html("<option value=''>市/县</option>");
		}else{
			getAreaList(provinceVal,"city");
		}
	}
	
	function cityChange(cityVal){
		$("select[name='subId']").html("<option value=''>请选择开户支行</option>");
		if("" != cityVal){
			var settleType = $("select[name=settleType]").val();
			if(settleType == ""){
				getBankList(null,"bankId",null);
			}else{
				getBankList(null,"bankId",settleType);
			}
		}else{
			$("select[name='bankId']").html("<option value=''>请选择开户银行</option>");
		}
	}
	
	function getKBankList(){
		var selectName = "bankId";
		var url = "${ctx }/common/getKBankList";
		var data = {};
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
							bankHtml = bankHtml + "<option value='"+bankList[i].bankCode+"'>"+bankList[i].bankName+"</option>";
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
	function getBankList(bankId,selectName,settleType){
		var url = "";
		var data = {};
		if(selectName == "bankId"){
			url = "${ctx }/common/getBankList";
		}else if(selectName == "subId"){
			url = "${ctx }/common/getSubBankList";
			var cityId = $("select[name='city']").val();
			data = {bankId:bankId,cityId:cityId};
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
	
	function bankChange(bankId){
		var settleType = $("select[name=settleType]").val();
		if(""==settleType){
			alert("请先选择结算方式");
			$("select[name='bankId']").val("");
			return;
		}
		if("" == bankId){
			$("select[name='subId']").html("<option value=''>请选择开户支行</option>");
		}else{
			getBankList(bankId,"subId",null);
		}
	}
	
	function settleTypeChange(settleTypeVal){
		getBankList(null,"bankId",settleTypeVal);
		$("select[name='subId']").html("<option value=''>请选择开户支行</option>");
	}
	
	var x = 60;
	var isAbleSendMsg = true;
	var mobilePhone = "";
	function misx(){
		var a = document.getElementById("xsx1");
		var b = document.getElementById("xsx2");
		var s = document.getElementById("mias");
		a.style.display="none";
		b.style.display="block";
		s.innerHTML = x;
		x--;
		if(x<=0){
			a.style.display="block";
			b.style.display="none";
			x=60;
			isAbleSendMsg =true;
			return;
		}
		setTimeout("misx();",1000);
	}
	
	function getMessageCode(){
		if(!isAbleSendMsg){
			return;
		}
		isAbleSendMsg = false;
		var mobilePhone = $("input[name='mobilePhone']").val();
		if(mobilePhone == ""){
			isAbleSendMsg = true;
			alert("请输入手机号");
			return;
		}else{
			$.ajax({
				url:"${ctx }/common/sendSms",
				data:{mobilePhone:mobilePhone,type:1},
				type:'post',
				cache:false, 
				async:false,
				dataType:'json',
				success:function(data) {
					if(data.returnCode=="0000"){//请求成功
						misx();
					}else{
						isAbleSendMsg = true;
						alert(data.returnMsg);
					}
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {    
			        alert("请求出错");
					isAbleSendMsg = true;
			    }
			});
		}
		
	}

	function register(){
		var memberName = $.trim($("input[name='memberName']").val());
		if("" == memberName){
			alert("请填写真实姓名");
			return ;
		}
		
		var certNbr = $.trim($("input[name='certNbr']").val());
		if("" == certNbr){
			alert("请填写身份证号码");
			return ;
		}
		if(!checkCard(certNbr)){
			return;
		}
		/**暂时屏蔽营业执照号码
		var busLicenceNbr = $.trim($("input[name='busLicenceNbr']").val());
		if("" == busLicenceNbr){
			alert("请填写营业执照编号");
			return ;
		}
		if(busLicenceNbr.length != 15 && busLicenceNbr.length != 18){
			alert("营业执照号码不正确");
			return ;
		}
		*/
		var merchantName = $.trim($("input[name='merchantName']").val());
		if("" == merchantName){
			alert("请填写商户名称");
			return ;
		}
		if(!validateChinese(merchantName) || merchantName.length<=4){
			alert("商户名称请填写大于4个汉字的名称");
			return;
		}
		var shortName = $.trim($("input[name='shortName']").val());
		if("" == shortName){
			alert("请填写商户简称");
			return ;
		}
		if(!validateChinese(shortName) || shortName.length<=4){
			alert("商户简称请填写大于4个汉字的名称");
			return;
		}
		var select_c = $.trim($("select[name='select_c']").val());
		if(""==select_c){
			alert("请选择联系人类型");
			return;
		}
		var addr_province = $.trim($("select[name='addr_province']").val());
		if(""==addr_province){
			alert("请选择所在省市");
			return;
		}
		var addr_city = $.trim($("select[name='addr_city']").val());
		if(""==addr_city){
			alert("请选择所在省市");
			return;
		}
		var addr_county = $.trim($("select[name='addr_county']").val());
		if(""==addr_county){
			alert("请选择所在市县");
			return;
		}
		
		var addr = $.trim($("input[name='addr']").val());
		if("" == addr){
			alert("请填写详细地址");
			return ;
		}
		var cardNbr = $.trim($("input[name='cardNbr']").val());
		if("" == cardNbr){
			alert("请填写收款卡号");
			return ;
		}
		/*var province = $.trim($("select[name='province']").val());
		if("" == province){
			alert("请选择开户区域省份信息");
			return ;
		}
		var city = $.trim($("select[name='city']").val());
		if("" == city){
			alert("请选择开户区域市县信息");
			return ;
		}*/
		var settleType = $.trim($("select[name='settleType']").val());
		if("" == settleType){
			alert("请选择结算方式");
			return ;
		}
		var bankId = $.trim($("select[name='bankId']").val());
		if("" == bankId){
			alert("请选择开户银行");
			return ;
		}
		/*var subId = $.trim($("select[name='subId']").val());
		if("" == subId){
			alert("请选择开户支行");
			return ;
		}*/
		var mobilePhone = $.trim($("input[name='mobilePhone']").val());
		if("" == mobilePhone){
			alert("请填写手机号码");
			return ;
		}
		var verifyCode = $.trim($("input[name='verifyCode']").val());
		if("" == verifyCode){
			alert("请填写验证码");
			return ;
		} 
		/* var certFilePath = $("#uploadCertImg").val();
		if(certFilePath.length == 0){
			alert("请上传身份证正面照片");
			return ;
		}
		var cardFilePath = $("#uploadCardImg").val();
		if(cardFilePath.length == 0){
			alert("请上传银行卡正面照片");
			return ;
		}  */
		if(!$("#checkboxFourInput").is(":checked")){
			alert("请勾选同意用户服务协议");
			return ;
		}
		ajaxSubmitForm();
	}
</script>

<script type="text/javascript">
/* $(".filepath").on("change",function() {
	fileUploadOnChange(this);
	console.log("${ctx }")
}); */

$(function() {
    $(".filepath").on("change",function() {
        var srcs = getObjectURL(this.files[0]);   //获取路径
        $(this).nextAll(".img1").hide();   //this指的是input
        $(this).nextAll(".img2").show();  //fireBUg查看第二次换图片不起做用
        $(this).nextAll(".img2").attr("src",srcs);    //this指的是input
//        $(this).val('');    //必须制空
    })
})

function show(obj) {
   	var s = document.getElementById(obj);
   	s.style.display = "block";
}

function hide(obj) {
   	var s = document.getElementById(obj);
   	s.style.display = "none";
}

function getObjectURL(file) {
    var url = null;
    if (window.createObjectURL != undefined) {
        url = window.createObjectURL(file)
    } else if (window.URL != undefined) {
        url = window.URL.createObjectURL(file)
    } else if (window.webkitURL != undefined) {
        url = window.webkitURL.createObjectURL(file)
    }
    return url
};

function stepChange(type){
	if(type == 1){
		$("#stepChangeBlock").show();
	}else{
		$("#stepChangeBlock").hide();
	}
}

function submitRegister(){
	register();
}


function fileUploadOnChange(obj) {
	var inputFileID = $(obj).attr("id");
	var uploadUrl = "${ctx}/common/uploadFileImg?fileDir="+"/certImg";
	ajaxFileUpload(uploadUrl,inputFileID);
}

function ajaxFileUpload (uploadUrl,fileInputID){ 
	//loading();//动态加载小图标 
	$.ajaxFileUpload ({ 
    	url :uploadUrl, 
    	secureuri :false, 
    	fileElementId :fileInputID, 
    	dataType : 'json', 
    	success: function (data, status){ 
		    	if(data.returnCode == "0000"){ 
		    		var filePath = data.resData.filePath;
		    		$("#"+fileInputID+"_Path").attr("src","${ctx}"+filePath);
		    		$("#"+fileInputID+"_pathvalue").val(filePath);
		    		$("#"+fileInputID+"_Path").prev("img").hide();
		    		$("#"+fileInputID+"_Path").show();
		    	}else{ 
		    		alert(data.returnMsg); 
		    	} 
    	}, 
    	error: function (data, status, e){ 
    		console.log(e); 
    	} 
	}); 
	//return false; 
	} 
	function loading (){ 
	$("#loading ").ajaxStart(function(){ 
		$(this).show(); 
	}).ajaxComplete(function(){ 
		$(this).hide(); 
	}); 
	} 
	
	var isSubmitAble = true;
	function ajaxSubmitForm(){
		if(!isSubmitAble){
			return;
		}
		isSubmitAble = false;
		$("#registerForm").ajaxSubmit({  
            url : "${ctx }/registerMem",  
            type : "post",  
            dataType : 'json',  
            success : function(data) {
            	isSubmitAble = true;
            	if(data.returnCode=="0000"){//请求成功
					window.location.href = "${ctx}/registerSuccess";
					//alert("成功");
				}else{
					alert(data.returnMsg);
				} 
            },  
            error : function(data) { 
            	isSubmitAble = true;
            	alert("请求出错"); 
            }  
        });  
	}
	
	function searchBank(){
		var bankId = $.trim($("select[name='bankId']").val());
		if("" == bankId){
			alert("请先选择开户银行");
			return ;
		}
		var searchBankText = $.trim($("#searchTextBank").val());
		if(searchBankText == ""){
			alert("请输入搜索关键字");
			return;
		}
		var city = $.trim($("select[name='city']").val());
		$.ajax({
			url:"${ctx }/common/searchSubBank",
			data:{bankId:bankId,subBankName:searchBankText,cityId:city},
			type:'post',
			cache:false, 
			async:false,
			dataType:'json',
			success:function(data) {
				if(data.returnCode=="0000"){//请求成功
					var bankHtml = "<option value=''>请选择开户支行</option>";
					var bankList = data.resData.subBankList;
					for(var i=0;i<bankList.length;i++){
						bankHtml = bankHtml + "<option value='"+bankList[i].subId+"'>"+bankList[i].subName+"</option>";
					}
					$("select[name='subId']").html(bankHtml);
				}else{
					alert(data.returnMsg);
				}
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {    
		        alert("请求出错");
				isAbleSendMsg = true;
		    }
		});
	}
</script>
</html>