<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<%@ include file="/common/include.jsp"%>
<%
	
	String param1 = new String(request.getParameter("login_name").getBytes("iso-8859-1") ,"utf-8");
	String param2 = new String(request.getParameter("user_name").getBytes("iso-8859-1") ,"utf-8");
	String param3 = new String(request.getParameter("fromurl").getBytes("iso-8859-1") ,"utf-8");
	String flag = request.getParameter("flag").toString(); 
	System.out.println(param1 + " , " + param2 + " , " + param3+" , " + flag);
%>
<head>
<title>修改密码</title>
<script type="text/javascript" src="js/baseInterface.js"></script>
</head>
<style>
#changePwd {
	
	width: 100%;
	
}

#changePwdTitle {
	position: relative;
	width: 100%;
	height: 50px;
	border-bottom: 1px solid #e2e2e2;
	font-family: "微软雅黑";
	text-align: center;
	margin-bottom: 20px;
	background: #f6f6f6;
}

#changePwdTitle i {
	
}

#changePwdTitle label {
	font-size: 18px;
	font-weight: 600;
	line-height: 50px;
	color: #555555;
}

#changePwdContent {
	width: 100%;
	position: relative;
}

#changePwdContent .input {
	width: 70%;
	margin-left: 15%;
	margin-top: 15px;
}

#operateDiv {
	width: 100%;
	text-align:center;
	overflow:hidden;
	margin-top:25px;
}

#sureBtn , #resetBtn {
	width: 130px;
	height: 35px;
}
 #resetBtn{
 	margin-left:15px;
 }

</style>
<body>
	<div style="position: absolute; width: 100%;">
		<div id="changePwd">
			<div id="changePwdTitle">
				<i class="edit big grey icon"></i> <label>修改密码</label>
			</div>
			<div id="changePwdContent">
				<div class="ui input">
					<input type="password" placeholder="原始密码">
				</div>
				<div class="ui input">
					<input type="password" placeholder="新密码">
				</div>
				<div class="ui input ">
					<input type="password" placeholder="确认新密码">
				</div>
				
				<div class="ui negative message errorDiv" style = "display:none;margin-bottom:10px;width:70%;margin-left:15%">
				  <i class="close icon"></i>
				  <div class="header">错误提示</div>
				  <p>输入密码不同</p>
				 </div>
				 
				 <div class="ui positive message tipDiv" style = "display:none;margin-bottom:10px;width:70%;margin-left:15%">
				  <i class="close icon"></i>
				  <div class="header">消息提示 </div>
				  <p></p>
				</div>
				 
				<div id="operateDiv">
					<div id="sureBtn" class="ui button green">确认</div>
					<div id="resetBtn" class="ui button blue">请求重置</div>
				</div>
				
			</div>
		</div>
	</div>
</body>
<script>
	$(function() {
		var config_login_name = '<%=param1%>'; 
		var config_user_name = '<%=param2%>';
		var config_fromurl = '<%=param3%>';
		console.log(123)
		$('#sureBtn').unbind('click').click(function(){
			var flag = true;
			$('.errorDiv , .tipDiv').hide();
			$('#changePwdContent input').each(function(index , value){
				var value = $('#changePwdContent input').eq(index).val();
				if(value == '' || value == null){
					flag = false;
					$(this).parent().addClass('error');
				}else{
					$(this).parent().removeClass('error');
				}
			})
			if(!flag){
				return;
			}
			if($('#changePwdContent input').eq(1).val() != $('#changePwdContent input').eq(2).val() ){
				$('.errorDiv').show();
				
			}else{
				new $.getPort({
					
					config:{
						tourl:"changePwd.interface", 
						login_name:config_login_name,
						login_pwd:$('#changePwdContent input').eq(0).val(),
						new_login_pwd:$('#changePwdContent input').eq(1).val()
					},
					callback:function(rep){
						console.log(rep)
						if(rep.result == '0'){
							$('.errorDiv').find('p').text(rep.message);
							$('.errorDiv').show();
						}else{
							$('.tipDiv').find('p').text(rep.message);
							$('.tipDiv').show();
						}
					}
				});
			}
			
		})
		$('.message .close').unbind('click').click(function(){
			$(this).parent().hide();
		})
		$('#resetBtn').unbind('click').click(function(){
			//获得系统管理员信息
			new $.getPort({
				
				config:{
					tourl:"getSystemAdmin.interface", 
					flag:'<%=flag%>'
				},
				callback:function(rep){
					if(rep.result == '0'){
						alert('获取当前系统管理员信息失败');
					}else{
						var data = rep.data[0];
						postInf(data.admin_login_name , data.user_name);
					}
				}
			});
			
		})
		function postInf(admin_login_name , admin_user_name){
			var urlBuilder = config_fromurl.split('/');
			var returnurl = '';
			if(urlBuilder.length > 0){
				for(var i = 0 ; i < urlBuilder.length ; i++){
					if(i == urlBuilder.length - 1){
						returnurl+='userManage.jsp?module=15&menu=48';
					}else{
						returnurl +=urlBuilder[i] + '/';
					}
				}
			}
			
			new $.getPort({
				config:{
					tourl:"postInfData.interface", 
					inf_type:"修改密码",
					inf_importance:"0",
					inf_content:config_user_name+"("+config_login_name+")" + "请求重置密码",
					inf_post:config_login_name,
					inf_get_name:admin_user_name,
					inf_get:admin_login_name,
					inf_system:'<%=flag%>',
					inf_return_url:returnurl, 
				},
				callback:function(rep){
					if(rep.result == '0'){
						$('.errorDiv').find('p').text("重置请求发送失败！");
						$('.errorDiv').show();
					}else{
						$('.tipDiv').find('p').text("重置请求已发送！");
						$('.tipDiv').show();
					}
				}
			});
		}
	})
</script>
</html>