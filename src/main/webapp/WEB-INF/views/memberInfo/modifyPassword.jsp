<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>修改密码</title>
<link href="${ctx }/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx }/js/jquery-2.2.3.min.js"></script>
<style type="text/css">
*{font-family: "微软雅黑";}
input[type=button]{-webkit-appearance:none;outline:none}
input[type=submit]{-webkit-appearance:none;outline:none}
input, a{-webkit-tap-highlight-color:rgba(255,255,255,0);}
html{width:100%;height:100%;}
body{overflow-x:hidden;padding:0;margin:0;-webkit-overflow-scrolling:touch;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none;}
img{border:none;}
ul,li{padding:0;margin:0; list-style:none;}
a{ text-decoration:none;outline:none;}
h1,h2,h3,h4,h5,h6{margin:0;padding:0;}
p,dd,dl{margin:0; padding:0;}
input{margin:0;padding:0;}
.xgmmdiv{width:100%;margin:0.5rem 0 0;background: #fff;overflow: hidden;}
.xgmmdiv dl{width:88%;padding:0 6%;min-height:2.5rem;border-bottom: 1px solid #eee;overflow: hidden;}
.xgmmdiv dl span{width:22%;float:left;font:0.8rem/2.5rem "微软雅黑";color:#222;}
.xgmmdiv dl .zcimstxt{float:left;border:none;height:2rem;margin:0.25rem 0;width:77%;font-size: 0.8rem;color:#222;}

.bgeee{background: #eee;}
.zcimsdiv{width:88%;margin:0 auto 3rem;overflow: hidden;}
.zcimsdiv dl{width:100%;border-bottom: 1px solid #eee;height:3rem;}
.zcimsdiv dl span{float:left;display:block;font:1rem/3rem "微软雅黑";color:#222;width:20%;}
.zcimsdiv dl .zcimstxt{float:left;border:none;height:2rem;margin:0.5rem 0;width:79%;font-size: 1rem;color:#222;}
.zcimsdiv dl .yzmtxt{float:left;border:none;height:2rem;margin:0.5rem 0;width:45%;font-size: 1rem;color:#222;}
.zcimsdiv dl dd{float:left;width:35%;overflow: hidden;}
.zcimsdiv dl dd a{display:block;font:1rem/3rem "微软雅黑";text-align: center}
.zcimsdiv dl dd a i{font-style: normal;}
.zcimsdiv dl dd a.get{color:#1d8fe1;}
.zcimsdiv dl dd a.gets{color:#888;}
.butblue{display:block;width:88%;background: #1d8fe1;height:2.2rem;border:none;border-radius: 0.3rem;font:1rem/2.2rem "微软雅黑";color:#fff;margin:1.5rem auto 0;text-align: center;}

</style>
</head>

<body class="bgeee">

<form action="#">
	<div class="xgmmdiv">
		<dl>
			<span>旧密码</span>
			<input id="oldPassword" type="password" class="zcimstxt" placeholder="请输入您的旧密码">
		</dl>
		<dl>
			<span>新密码</span>
			<input id="newPassword" type="password" class="zcimstxt" placeholder="请输入您的新密码">
		</dl>
		<dl>
			<span>确认密码</span>
			<input id="confirmPassword" type="password" class="zcimstxt" placeholder="请确认您的新密码">
		</dl>
	</div>
	<input type="button" class="butblue" onClick="modify()" value="确认修改">
</form>
<script type="text/javascript">
	function modify(){
		var oldPassword = $.trim($("#oldPassword").val());
		var newPassword = $.trim($("#newPassword").val());
		var confirmPassword = $.trim($("#confirmPassword").val());
		if(""==oldPassword){
			alert("请输入旧密码");
			return;
		}
		if(""==newPassword){
			alert("请输入新密码");
			return;
		}
		if(""==confirmPassword){
			alert("请确认密码");
			return;
		}
		if(newPassword!=confirmPassword){
			alert("新密码输入不一致");
			return;
		}
		$.ajax({
			url:"${ctx }/memberInfo/modifyPwd",
			data:{oldPassword:oldPassword,newPassword:newPassword,confirmPassword:confirmPassword},
			type:'post',
			cache:false, 
			async:false,
			dataType:'json',
			success:function(data) {
				if(data.returnCode=="0000"){//请求成功
					//var epayCode = data.resData.epayCode;
					//window.location.href = "${ctx }/debitNote/index?epayCode="+epayCode;
					alert("密码修改成功");
					window.location.href = "${ctx }/login";
				}else{
					alert(data.returnMsg);
				}
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {    
		        alert("请求出错");
		    }
		});
	}

</script>
</body>
</html>