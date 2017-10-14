<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>修改信息</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx }/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="${ctx }/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx }/js/tools.js"></script>
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
			<span>详细地址</span>
			<input type="text" name="addr" class="zcimstxt" placeholder="请输入您的详细地址">
		</dl>
		<dl>
			<span>商户名称</span>
			<input type="text" name="merchantName" class="zcimstxt" placeholder="请输入您的商铺名称">
		</dl>
		<dl>
			<span>商户简称</span>
			<input type="text" name="shortName" class="zcimstxt" placeholder="请输入您的商铺简称">
		</dl>
	</div>
	<div class="zcimscon">
		<dl>
			<span>收款卡号</span>
			<input type="text" name="cardNbr" class="zcimstxt" placeholder="请输入您的收款卡号">
		</dl>
		<dl>
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
		</dl>
		<dl>
			<span>开户银行</span>
			<div class="setsb">
				<select name="bankId" onchange="bankChange(this.value)">
					<option value="">请选择开户银行</option>
				</select>
			</div>
		</dl>
		<dl>
			<div>
				<input id="searchTextBank" type="text" class="zcimstxt" placeholder="搜索开户支行">
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
	</div>
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
	<input type="hidden" name="epayCode" value="${epayCode }">
	<input type="button" class="zcimsbut" value="下一步" onclick="stepChange(1)">
</div>
<div id="stepChangeBlock" style="display: none;position: fixed;top:0;height: 100%;width: 100%;background-color: #f8f8f8;">
	<div class="sczpdiv">
		<div class="ttsccon">
			<div id="img">
				<div class="imgbox">
				    <div class="imgnum">
				        <input id="uploadCertImg" type="file"  accept="image/*" class="filepath" name="certImg"/>
				        <img src="johu/images/sfz.png" class="img1" />
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
				        <img src="johu/images/yhk.png" class="img1" />
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
			<input class="sczpbut" onclick="submitModify()" type="button" style=" text-align: center;" value="提交审核">
		</div>
	</div>
</div>
</form>
</body>
<script type="text/javascript">
$(function(){
	//getAreaList(1,"province");
	//register();
});
	
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
							if("0" == settleType && bankList[i].kbinId == ""){
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

	function modify(){
		ajaxSubmitForm();
	}
	/**有些身份证号码校验有误，弃用**/
	function IdentityCodeValid(code) {
         var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",
             31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",
             43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",
             61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};

         var pass= true;

         if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
             alert('身份证号错误');
             pass = false;

         } else if(!city[code.substr(0,2)]){
        	 alert('身份证号错误');
             pass = false;
         } else{
             //18位身份证需要验证最后一位校验位
             if(code.length == 18){
                 code = code.split('');
                 //∑(ai×Wi)(mod 11)
                 //加权因子
                 var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
                 //校验位
                 var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
                 var sum = 0;
                 var ai = 0;
                 var wi = 0;
                 for (var i = 0; i < 17; i++)
                 {
                     ai = code[i];
                     wi = factor[i];
                     sum += ai * wi;
                 }
                 var last = parity[sum % 11];
                 if(parity[sum % 11] != code[17]){
                	 alert('身份证号错误');
                     pass =false;
                 }
             }
         }
         if(!pass) ;
         return pass;
     }
	/**身份证校验入口**/
	checkCard = function(card)
	{
		//是否为空
		if(card === '')
		{
			alert('请输入身份证号，身份证号不能为空');
			return false;
		}
		//校验长度，类型
		if(isCardNo(card) === false)
		{
			alert('您输入的身份证号码不正确，请重新输入');
			return false;
		}
		//检查省份
		if(checkProvince(card) === false)
		{
			alert('您输入的身份证号码不正确,请重新输入');
			return false;
		}
		//校验生日
		if(checkBirthday(card) === false)
		{
			alert('您输入的身份证号码生日不正确,请重新输入');
			return false;
		}
		//检验位的检测
		if(checkParity(card) === false)
		{
			alert('您的身份证校验位不正确,请重新输入');
			return false;
		}
		return true;
	};


	//检查号码是否符合规范，包括长度，类型
	isCardNo = function(card)
	{
		//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
		var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
		if(reg.test(card) === false)
		{
			return false;
		}

		return true;
	};

	//取身份证前两位,校验省份
	checkProvince = function(card)
	{
		var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",
	             31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",
	             43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",
	             61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
		var province = card.substr(0,2);
		if(city[province] == undefined)
		{
			return false;
		}
		return true;
	};

	//检查生日是否正确
	checkBirthday = function(card)
	{
		var len = card.length;
		//身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
		if(len == '15')
		{
			var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/; 
			var arr_data = card.match(re_fifteen);
			var year = arr_data[2];
			var month = arr_data[3];
			var day = arr_data[4];
			var birthday = new Date('19'+year+'/'+month+'/'+day);
			return verifyBirthday('19'+year,month,day,birthday);
		}
		//身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
		if(len == '18')
		{
			var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
			var arr_data = card.match(re_eighteen);
			var year = arr_data[2];
			var month = arr_data[3];
			var day = arr_data[4];
			var birthday = new Date(year+'/'+month+'/'+day);
			return verifyBirthday(year,month,day,birthday);
		}
		return false;
	};

	//校验日期
	verifyBirthday = function(year,month,day,birthday)
	{
		var now = new Date();
		var now_year = now.getFullYear();
		//年月日是否合理
		if(birthday.getFullYear() == year && (birthday.getMonth() + 1) == month && birthday.getDate() == day)
		{
			//判断年份的范围（3岁到100岁之间)
			var time = now_year - year;
			if(time >= 3 && time <= 100)
			{
				return true;
			}
			return false;
		}
		return false;
	};

	//校验位的检测
	checkParity = function(card)
	{
		//15位转18位
		card = changeFivteenToEighteen(card);
		var len = card.length;
		if(len == '18')
		{
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
			var cardTemp = 0, i, valnum; 
			for(i = 0; i < 17; i ++) 
			{ 
				cardTemp += card.substr(i, 1) * arrInt[i]; 
			} 
			valnum = arrCh[cardTemp % 11]; 
			if (valnum == card.substr(17, 1)) 
			{
				return true;
			}
			return false;
		}
		return false;
	};

	//15位转18位身份证号
	changeFivteenToEighteen = function(card)
	{
		if(card.length == '15')
		{
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
			var cardTemp = 0, i;   
			card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
			for(i = 0; i < 17; i ++) 
			{ 
				cardTemp += card.substr(i, 1) * arrInt[i]; 
			} 
			card += arrCh[cardTemp % 11]; 
			return card;
		}
		return card;
	};
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

function submitModify(){
	modify();
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
	
	
	function ajaxSubmitForm(){
		$("#registerForm").ajaxSubmit({  
            url : "${ctx }/modifyMemTest",  
            type : "post",  
            dataType : 'json',  
            success : function(data) {  
            	if(data.returnCode=="0000"){//请求成功
					window.location.href = "${ctx}/login";
				}else{
					alert(data.returnMsg);
				} 
            },  
            error : function(data) {  
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