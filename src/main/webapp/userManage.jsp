<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
		<html>
<%@ include file="/common/include.jsp"%> 
		<head>

				<!--这里引用其他样式-->
				<title>校内教学质量监控运行平台</title>
				<style>
					#add {
						margin-left: 60%;
					}
					
					#user_xq {
						margin-top: 60px;
					}
					
					#recomposepwd {
						margin-top: 4%;
						margin-left: 38%;
						width: 230px;
					}
				</style>
		</head>

		<body>
				<div id="userManageDiv">
					<h3 class="ui header">
							<i class="user icon"></i>
                            <div class="content">用户管理 </div>
                        </h3>
					<div class="ui action input">
						<input type="text" placeholder="请先输入需要查询的用户" id="search">
						<button class="ui button" id="search_button">搜索</button>
					</div>
					<div class="ui blue basic button" id="add_user" style="float: right;">
						<i class="add black user icon"></i>添加新用户
					</div>

					<div class="ui  horizontal blue divider" id="user_xq">
						<h3 class="ui blue header"><i class="translate icon"></i>用户详情</h3></div>

					<table class="ui compact blue table" id="form_table">
						<thead>
							<tr>
								<th>用户登录名</th>
								<th>用户名称</th>
								<th>创建时间</th>
								<th>用户类型</th>
								<th>用户权限</th>
								<th>用户状态</th>
								<th>管理操作</th>
							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>
					<div id="planmod" class="ui modal">
						<i class="close icon"></i>
						<div class="header" id="modheader">
							修改用户信息
						</div>
						<div class="content">
							<div class="description">
								<div>
									用户名称：
									<div class="ui  input">
										<input type="text" placeholder="" style="width: 417px;" id="menu_name" value="">
									</div>
								</div>
								<br>
								<div>
									用户类型:&nbsp;&nbsp;&nbsp;
									<select class="ui one dropdown" id="type_one">
									</select>
									用户状态：
									<select class="ui two dropdown" id="status_one">
										<option value="1" selected="selected">启用</option>
										<option value="0">不启用</option>
									</select>
								</div>
								<br>
								<div>
									用户权限：
									<div class="ui input">
										<select class="ui dropdown" id="dep_name">
											<option value="">选择权限</option>
											<option value="ALL">全局</option>
										</select>
									</div>
								</div>
								
							</div>
						</div>
						<div class="actions">
							<div class="ui positive right labeled icon button" id="submit_two">
								提&nbsp;&nbsp;&nbsp;&nbsp;交
								<i class="checkmark icon"></i>
							</div>
						</div>
					</div>

					<div id="add_users" class="ui modal">
						<i class="close icon"></i>
						<div class="header" id="modheader">
							添加新用户
						</div>
						<div class="content">
							<div class="description">
								<div>
									用户登陆名：
									<div class="ui  input">
										<input type="text" placeholder="" id="dl_name" value="">
									</div>

									用户名称：
									<div class="ui  input">
										<input type="text" placeholder="" id="user_name" value="">
									</div>
								</div>
								<br>
								<div>
									用户类型:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<select class="ui one dropdown" id="type_two">
									</select>
									用户状态：
									<select class="ui two dropdown" id="status_two">
										<option value="1" >启用</option>
										<option value="0">不启用</option>
									</select>
								</div>
								<br>
								<div>
									用户权限:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<div class="ui input">
										<select class="ui dropdown" id="dep_name_">
											<option value="">可以选择权限哦</option>
											<option value="ALL">全局</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div>
							<div class="actions">
								<div class="ui positive right labeled icon button" id="addsubmit">
									提&nbsp;&nbsp;&nbsp;&nbsp;交
									<i class="checkmark icon"></i>
								</div>
							</div>
						</div>
					</div>

				</div>
		</body>
		<script>
			$(function() {
				var add_select = "学生";
				var change_select = "";
				var type = "1";
				var name_no;
				var user_loginname;
				var user_type;
				var name;
				$('#status_two').dropdown();
				$('#status_one').dropdown();
				$("#submit_two").click(function() {
					change_table();
				});
				$("#search_button").click(function() {
					loadstu_search();
				});
				$("#add_user").click(function() {
					
					$('#dl_name').val("");
					$('#user_name').val("");
					$('#limit_two').val("");
					$("#type_two").text("");
					$("#status_two").val("");
					load_type(type);
					$('#add_users').modal('show');
					
				});
				$("#addsubmit").click(function() {
					save_table();
				});

				function loadstu_search() {
					name_no = $('#search').val();
					$.ajax({
						url: "get_search.do",
						type: 'POST',
						dataType: 'json',
						data: {
							name_no: name_no
						},
						success: function(rep) {
							if (rep.result == 0) {
								$.alert("抱歉！还没有用户哦，赶紧添加吧!");
							} else {
								$('#form_table tr:gt(0)').remove();
								table = rep.data;
								console.log(table) 
							
								for (var i = 0; i < table.length; i++) {
									if (table[i].user_status == 1) {
										var dom_stu = '<tr>' +
											'<td>' + table[i].user_loginname + '</td>' +
											'<td>' + table[i].user_name + '</td>' +
											'<td>' + table[i].user_createtime + '</td>' +
											'<td>' + table[i].user_type + '</td>' +
											'<td>' + table[i].user_purview + '</td>' +
											'<td>' + '启用' + '</td>' +
											'<td>' +
											'<div class="btnupdate circular mini ui blue basic icon button"  id="' + table[i].user_loginname + '"name="' + table[i].user_name + '"  qx="' + table[i].user_purview + '" lx="' + table[i].user_type + '">' +
											'<i class="edit blue large icon"></i>' +
											'</div>' +
											'<div class="btndelete circular mini ui red basic icon button" id="' + table[i].user_loginname + '">' +
											'<i class="trash red large icon"></i>' +
											'</div>' +
											'<div class="circular mini ui orange basic icon button resetpwd"  jgh="' + table[i].user_loginname + '"name="' + table[i].user_name + '"  qx="' + table[i].user_purview + '" lx="' + table[i].user_type + '">' +
											'<i class="undo orange large icon"></i>' +
											'</div>' +
											'</td>' +
											'</tr>'; 
										$('#form_table').append($(dom_stu));
									} else {
										var dom_stu = '<tr>' +
											'<td>' + table[i].user_loginname + '</td>' +
											'<td>' + table[i].user_name + '</td>' +
											'<td>' + table[i].user_createtime + '</td>' +
											'<td>' + table[i].user_type + '</td>' +
											'<td>' + table[i].user_purview + '</td>' +
											'<td>' + '停用' + '</td>' +
											'<td>' +
											'<div class="btnupdate circular mini ui blue basic icon button"  id="' + table[i].user_loginname + '"name="' + table[i].user_name + '"  qx="' + table[i].user_purview + '" lx="' + table[i].user_type + '">' +
											'<i class="edit blue large icon"></i>' +
											'</div>' +
											'<div class="btndelete circular mini ui red basic icon button" id="' + table[i].user_loginname + '"  name="' + table[i].user_name + '">' +
											'<i class="trash red large icon"></i>' +
											'</div>' +
											'<div class="circular mini ui orange basic icon button resetpwd"  jgh="' + table[i].user_loginname + '"name="' + table[i].user_name + '"  qx="' + table[i].user_purview + '" lx="' + table[i].user_type + '">' +
											'<i class="undo orange large icon"></i>' +
											'</div>' +
											'</td>' +
											'</tr>';
										$('#form_table').append($(dom_stu));
										//
									}
								}
								drawnHeight();
								$('.btndelete').click(function() {
									var user_loginname = $(this).attr("id");
									console.log(user_loginname);
									$.confirm({
										msg: '您确定要删除这个用户吗?',
										btnSure: '确认删除 ',
										btnCancel: '重新选择',
										sureDo: function() {
											load_delete(user_loginname);
										}
									});
								});
								$('.btnupdate').click(function() {
									user_loginname = $(this).attr("id");
									var name = $(this).attr("name");
									var purview = $(this).attr("qx");
									type = $(this).attr("lx");
								
									change_select = type;
									console.log(change_select) 
									if(change_select.trim() == '学生'){  
										console.log('xuesheng')
										$('#type_one').parents('.dropdown').find('.text').text('学生');
										
									}else{
										console.log('jiaoshi')
										$('#type_one').parents('.dropdown').find('.text').text('教师'); 
										
									}
									$('#menu_name').val(name);
									$('#limit_one').val(purview);
									$('#planmod').modal('show');
								});
								$(".resetpwd").click(function(){
									var uloginname=$(this).attr("jgh");
									var utype=$(this).attr("lx")
									$.confirm({
										msg: '您确定重置该用户密码吗?',
										btnSure: '确认重置 ',
										btnCancel: '取消',
										sureDo: function() {
											changepwd(uloginname,utype);
										}
									});
								});
							}
						}
					});
				}

				function load_delete(user_loginname) {
					$.ajax({
						url: "load_delete.do",
						type: 'POST',
						dataType: 'json',
						data: {
							user_loginname: user_loginname
						},
						success: function(rep) {
							$('#form_table tr:gt(0)').remove();
							/* loadstu_all(); */
							$.alert("", rep.message);
						}
					});
				}
				load_type();

				function load_type() {
					
					var dom_two = '<option data-value = "学生" class = "xuesheng">学生 </option>'; 
						dom_two+='<option data-value = "教师" class = "jiaoshi" >教师 </option>';
					$('#type_two').html($(dom_two)); 
					$('#type_one').html($(dom_two)); 
					$('#type_two').dropdown({
						onChange:function(value, text, $choice){
							add_select = value;
						}
					});
					$('#type_one').dropdown({
						onChange:function(value, text, $choice){
							change_select = value;
						}
					});
					
				}

				function save_table() {
					var user_loginname = $('#dl_name').val();
					var user_name = $('#user_name').val();
					var user_purview = $('#dep_name_ > option:selected').val();
					var user_type = add_select;
					var user_status = $("#status_two > option:selected").val();
					if (user_type == "请选择类型") {
						$('#planmod').modal('show');
					} else {
						var user_pwd = '';
						if(user_type =='学生'){
							user_pwd = '123456';
						}else{
							user_pwd = '111111';
						}
						$.ajax({
							url: "save_table.do",
							type: 'POST',
							dataType: 'json',
							data: {
								user_loginname: user_loginname,
								user_pwd: user_pwd,
								user_name: user_name,
								user_purview: user_purview,
								user_type: user_type,
								user_status: user_status
							},
							success: function(rep) {
								$.alert("", rep.message);
								$('#form_table tr:gt(0)').remove();
								$('#type_two').dropdown();
								$('#type_one').dropdown();
								/* loadstu_all(); */
							}
						});
					}
				}

				function change_table() {
					var user_name = $('#menu_name').val();
					var user_purview = $('#dep_name > option:selected').val();
					var user_type = change_select;
					var user_status = $("#status_one > option:selected").val();
						$.ajax({
							url: "change_table.do",
							type: 'POST',
							dataType: 'json',
							data: {
								user_loginname: user_loginname,
								user_name: user_name,
								user_purview: user_purview,
								user_type: user_type,
								user_status: user_status
							},
							success: function(rep) {
								$.alert("", rep.message);
								$('#form_table tr:gt(0)').remove();
								$("#type_one").dropdown("set selected", "5");
								loadstu_search();
							}
						});
				}
				loaddepname();

				function loaddepname() {
					$.ajax({
						url:"basicdata/department/getdepartments.do",
						type:"POST",
						dataType:"json",
						success:function(rep){
							console.log(rep)
							if(rep.result == '0'){
								$.alert('读取部门失败');
							}else{
								var depInfo = rep.data;
								for (var i = 0; i < depInfo.length; i++) {
									var dom_college = '<option value="' + depInfo[i].dep_no + '">' + depInfo[i].dep_name + '</option>';
									$('#dep_name').append($(dom_college));  
									$('#dep_name_').append($(dom_college));
								}
								$('#dep_name').dropdown();
								$('#dep_name_').dropdown();
							}
						}
					});
					
					
				}

				function changepwd(loginname,utype) {
					$.ajax({
						url: "changepwd.do",
						type: 'POST',
						dataType: 'json',
						data: {
							user_loginname: loginname,
							user_type:utype
						},
						success: function(rep) {
							$.alert("", rep.message);
						}
					});
				}
				 
			});
			drawnHeight()
			function drawnHeight(){ 
				var parentContent = $(parent.document.getElementById("container"));
				
				var contentHeight = $('#userManageDiv').height();
				if(contentHeight < 500){
					contentHeight = 500;
				}
				parentContent.height(contentHeight)  
			}
		</script> 
		<!--这里引用其他JS-->

		</html>

		<!-- 					/* loadstu_all(); 获取所有的用户信息
 				function loadstu_all() {
					$.ajax({ 
						url: "do?invoke=menuManageOpera@get_user",
						type: 'POST',
						dataType: 'json',

						success: function(rep) {
							if (rep.result == 0) {
								$.alert("抱歉！还没有用户哦，赶紧添加吧!");
							} else {
								 $('#form_table tr:gt(0)').remove();
								table = rep.data;
								console.log(rep);
								for (var i = 0; i < table.length; i++) {
									if(table[i].user_status==1)
										{
										var dom_stu = '<tr>' +
										'<td>' + table[i].user_loginname + '</td>' +
										'<td>' + table[i].user_name + '</td>' +
										'<td>' + table[i].user_createtime.substring(0,10) + '</td>' +
										'<td>' + table[i].user_type + '</td>' +
										'<td>' + table[i].user_purview + '</td>' +
										'<td>' + '启用' + '</td>' +
									      '<td>'+
									      '<div class="btnupdate circular mini ui blue basic icon button" id="'+table[i].user_loginname+'" name="'+table[i].user_name+'" qx="'+table[i].user_purview+'" lx="'+table[i].user_type+'">'+
												'<i class="edit blue large icon"></i>'+
							                 '</div>'+ 
							                 '<div class="btndelete circular mini ui red basic icon button" id="'+table[i].user_loginname+'">'+
												'<i class="trash red large icon"></i>'+
							                 '</div>'+
						                 '</td>'+
										'</tr>';
										$('#form_table').append($(dom_stu));

									}
									else
										{
										var dom_stu = '<tr>' +
										'<td>' + table[i].user_loginname + '</td>' +
										'<td>' + table[i].user_name + '</td>' +
										'<td>' + table[i].user_createtime.substring(0,10) + '</td>' +
										'<td>' + table[i].user_type + '</td>' +
										'<td>' + table[i].user_purview + '</td>' +
										'<td>' + '停用' + '</td>' +
									      '<td>'+
									      '<div class="btnupdate circular mini ui blue basic icon button"  id="'+table[i].user_loginname+'"name="'+table[i].user_name+'"  qx="'+table[i].user_purview+'" lx="'+table[i].user_type+'">'+                           
											'<i class="edit blue large icon"></i>'+
						                 '</div>'+ 
						                 '<div class="btndelete circular mini ui red basic icon button" id="'+table[i].user_loginname+'"  name="'+table[i].user_name+'">'+
											'<i class="trash red large icon"></i>'+
						                 '</div>'+
						                 '</td>'+
										'</tr>';
									$('#form_table').append($(dom_stu));

										}	
								}
								$('.btndelete').click(function(){
									var user_loginname=$(this).attr("id");
									console.log(user_loginname);
									$.confirm({
										msg: '您确定要删除这个用户吗?',
										btnSure: '确认删除 ',
										btnCancel: '重新选择',
										sureDo: function() {
											 load_delete(user_loginname);
										}
									});
								});
								
								$('.btnupdate').click(function(){
									user_loginname=$(this).attr("id");
									var name=$(this).attr("name");
									var purview=$(this).attr("qx");
									 type=$(this).attr("lx");
									 console.log(type);
									$('#menu_name').val(name);
									$('#limit_one').val(purview);
									$('#planmod').modal('show');
									load_type(type);
								});
							}
						}
					});
				}  -->