<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>修改密码</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx }/js/jquery-2.2.3.min.js"></script>
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