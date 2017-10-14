<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>忘记密码</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx }/js/jquery-2.2.3.min.js"></script>

</head>

<body>

<form action="#">
	<div class="xgmmdiv">
		<dl>
			<span>真实姓名</span>
			<input type="text" name="contactName" class="zcimstxt" placeholder="注册时所填写的姓名">
		</dl>
		<dl>
			<span>手机号码</span>
			<input type="text" name="mobilePhone" class="zcimstxt" placeholder="注册时所填写的手机号码">
		</dl>
		<dl>
			<span>身份证号</span>
			<input type="text" name="certNbr" class="zcimstxt" placeholder="注册时所填写的身份证号码">
		</dl>
	</div>
	<div class="xgmmdiv">
		<dl>
			<span>新密码</span>
			<input type="password" name="password" class="zcimstxt" placeholder="请输入您的新密码">
		</dl>
		<dl>
			<span>确认密码</span>
			<input type="password" name="confirmPassword" class="zcimstxt" placeholder="请确认您的新密码">
		</dl>
	</div>
	<input type="button" onclick="forget()" class="butblue" value="提交修改" />
</form>
<script type="text/javascript">
	function forget(){
		var contactName = $.trim($("input[name='contactName']").val());
		var mobilePhone = $.trim($("input[name='mobilePhone']").val());
		var certNbr = $.trim($("input[name='certNbr']").val());
		var password = $("input[name='password']").val();
		var confirmPassword = $("input[name='confirmPassword']").val();
		
		if(contactName == ""){
			alert("真是姓名不能为空");
			return;
		}
		var re = /^1[34578]\d{9}$/;
        if (!re.test(mobilePhone)) {
			alert("手机号码格式不正确");
			return;
		}
		if(certNbr == ""){
			alert("身份证号码不能为空");
			return;
		}
		if(password == ""){
			alert("密码不能为空");
			return;
		}
		if(confirmPassword == ""){
			alert("确认密码不能为空");
			return;
		}
		if(password != confirmPassword){
			alert("两次输入的密码不一致");
			return;
		}
		var params = {contactName:contactName,
				mobilePhone:mobilePhone,
				certNbr:certNbr,
				password:password,
				confirmPassword:confirmPassword};
		$.ajax({
			url:"${ctx }/resetPassword",
			data:params,
			type:'post',
			cache:false, 
			async:false,
			dataType:'json',
			success:function(data) {
				if(data.returnCode=="0000"){//请求成功
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