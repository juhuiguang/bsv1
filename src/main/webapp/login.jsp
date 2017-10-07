<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<%@ include file="/common/include.jsp"%> 

<style>
		#leftcontent{
			text-align: left; 
		}
		#leftpanel{
			width:100%;
			margin:0 auto;
		}
		#rightcontent{
		 	border: 1px solid #d6d6cf;
    		box-shadow: #D6D6cf 0px 0px 50px 20px;
    		border-radius:10px;
    		padding:10px 5px;
    	}
		#logobanner{
			width:80%;
		}
		#logobanner img{
			width:100%;
		}
		
		#rightpanel{
			width:85%;
			margin:0 auto;
			padding:15px 10px;
		}
		#rightpanel .userimg{
			width:100%;
			text-align:center;
		}
		#btnlogin{
			width:100%;
			background: #f15852;
			text-align:center;
			cursor:pointer;
		}
		#rightpanel .input{
			width:100%;
			line-height:50px;
			height:50px;
			border:1px solid #f15852;
			padding-left:35px;
			font-weight:500;
		}
		#rightpanel .input.error{
			border:2px solid #ff0000;
			background-color:rgba(200,0,0,0.2);
		}
		#rightpanel .iconinput{
			position:relative;
		}
		#rightpanel .iconinput i{
			position:absolute;
			left:0;
			top:0.5em;
			color:#898989;
		}
		#rightpanel .row{
			margin-top:30px;
		}
		#divremember{
			font-weight: 500;
			vertical-align: middle;
			line-height:20px;
			position:relative;
		}
		#remeberme{
		}
		#remberme{
			position:relative;
			width:50%;
			text-align: left;
		}
		#checktext{
			margin-left:30px;
			color:#db2828;
		}
		#findback{
			position:absolute;
			right:0;
		}
		
		.schoollog{
			position:fixed;
			left:15px;
			top:10;
		}
	</style>
 
	<body>
		
			<div id="rightcontent" class="five wide column">
				<div id="rightpanel" >
					
					<div id="divloginname" class="iconinput row">
					
						<input class="input font20" type="text" id="input_loginname" placeholder="请输入教工号登录" />
					</div>
					<div id="divloginpwd" class="iconinput row">
						
						<input class="input font20"  type="password" id="input_loginpwd" placeholder="请输入登陆密码" />
					</div>
					<div id="divremember" class="row">
						<span id="remberme" >
							<input type="checkbox" id="remeberme" />
							<span id="checktext" class="font16 stick">记住我</span>
						</span>
					</div>
					<div id="btnlogin" class="ui button row font20 stick white">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</div>
				</div>
			</div>
		
	</body>
	<script>
		$(function(){
		
			//回车登陆
			$(document).keypress(function(e){
				if(e.charCode==13){
					$("#btnlogin").trigger("click");
				}
			});
			
			var loginstatus=1;
			$("#btnlogin").click(function(){
				if(!validate()) return;
			
				dologin();
			});
			
			function validate(){
				$(".input.error").removeClass("error");
				var loginname=$("#input_loginname").val();
				var loginpwd=$("#input_loginpwd").val();
				if(loginname==""){
					$("#input_loginname").addClass("error");
					return false;
				}
				if(loginpwd==""){
					$("#input_loginpwd").addClass("error");
					return false;
				}
				return true;
			}
			
			function dologin(){
				if(loginstatus==0){
					return;
				}
				loginstatus==0;
				
				new $.getPort({
					
					config:{
						tourl:"login.interface", 
						login_name:$("#input_loginname").val(),
						login_pwd:$("#input_loginpwd").val()
					},
					callback:function(rep){
						alert("请求成功") 
					console.log(rep)
					}
				});
				
				/* if(rep.data !=null){ 
					//打印参数 
					alert('登陆成功')
					rememberMe();
				}else{
					alert("您输入的用户名密码错误！");
					console.log(rep) 
					$("#input_loginpwd").addClass("error");
					$("#input_loginname").addClass("error");
				} */
			}
			function rememberMe(){
				if($("#remeberme")[0].checked){
					$.cookie('rememberme', true, { expires: 365 });
					$.cookie("base_loginname",$("#input_loginname").val(),{ expires: 365 });
					$.cookie("base_loginpwd",$("#input_loginpwd").val(),{ expires: 365 });
				}else{
					$.cookie('rememberme', false, { expires: 365 });
					$.cookie("base_loginname","");
					$.cookie("base_loginpwd","");
				} 
			}
			function getRemember(){
				var rememberme=$.cookie("rememberme");
				if(rememberme!=null&&rememberme=="true"){
					
					$("#remeberme")[0].checked=true; 
					$("#input_loginname").val($.cookie("base_loginname"));
					$("#input_loginpwd").val($.cookie("base_loginpwd"));  
				}else{
				
					$("#remeberme")[0].checked=false; 
					$("#input_loginname").val("");
					$("#input_loginpwd").val("");
				}
				 
			}
			getRemember();
		});
	</script>
</html>