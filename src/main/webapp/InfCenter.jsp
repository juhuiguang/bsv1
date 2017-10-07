<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!DOCTYPE html">
	<html>
	<%@ include file="/common/include.jsp"%>
		<%
	String params =  new String(request.getParameter("config").getBytes("iso-8859-1") ,"utf-8");
	String flag = request.getParameter("flag");
%>

			<head>
				<title>消息盒子</title>
				<style>
					body {
						font-family: "Arial", "Microsoft YaHei", "黑体", "宋体", sans-serif;
					}
					
					.information_container {
						width: 100%;
						height: auto;
						overflow-y: auto;
					}
					
					.information_header {
						max-width: 96%;
						margin-left: 2%;
						margin-right: 2%;
						position:fixed;
						top:5px;
						left:0;
						z-index:100;
					}
					
					.information_text {
						width: 100%;
						padding:30px 1% 10px 1%;
						

					}
					
					.information_title_word {
						color: grey !important;
					}
					
					.information_text_word {
						margin-top: 5px;
						margin-left: 5%;
						color: gray;
					}
					
					.information_star {
						margin-left: 7%;
					}
					
					.time_word {
						float: right;
					}
					
					.inf_content_ {
						margin-top: -20px;
						margin-left: 40px;
					}
					
					.fields .trash_field {
						margin-left: 90%;
					}
					
					#show_inf .message{
						width:100% important;
						background:#fff;
					}
				</style>
			</head>

			<body>

				<div class="information_container">
					<!-- //最外边 -->
					<div class="information_header">
						<!-- //标题的头 -->
						<div class="ui menu">
							<div class="header item">消息工作台</div>
							<a class="item active inf_item " menu_value="inf_all">全部</a>
							<a class="item inf_item " menu_value="inf_no">未读 </a>
							<a class="item inf_item " menu_value="inf_week">本周 </a>
							<a class="item inf_item" menu_value="inf_month">本月</a>
						</div>
					</div>
					<div class="information_text">
						<!-- 消息的内容 -->
						<!-- 今日消息开始 -->

						<!-- 今日消息结束 -->

						<div class="ui raised segment">
							<div class="information_text" id="show_inf">

								<div class="ui icon message" id="no_data">
									<i class="inbox icon"></i>
									<div class="content">
										<div class="header">您还没有任何消息。。。。 </div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</body>
			<script>
				$(function() {
					var menu_value = 'inf_all';
					$('.inf_item').click(function() {
						$('.inf_item').removeClass('active');
						$(this).addClass('active');
						menu_value = $(this).attr("menu_value");
						getAllInfNo();
					});
					var config = JSON.parse('<%=params%>');
					var flag = '<%=flag%>';
					//根据当当天日期获取本周的开始和结束时间
					oToday = new Date();
					currentDay = oToday.getDay();
					if (currentDay == 0) {
						currentDay = 7
					}
					mondayTime = oToday.getTime() - (currentDay - 1) * 24 * 60 * 60 * 1000;
					sundayTime = oToday.getTime() + (7 - currentDay) * 24 * 60 * 60 * 1000;

					function date2str(x, y) {
						var z = {
							y: x.getFullYear(),
							M: x.getMonth() + 1,
							d: x.getDate(),
							h: x.getHours(),
							m: x.getMinutes(),
							s: x.getSeconds()
						};
						return y.replace(/(y+|M+|d+|h+|m+|s+)/g, function(v) {
							return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1))).slice(-(v.length > 2 ? v.length : 2))
						});
					}
					var start_data = date2str(new Date(mondayTime), "yyyy-MM-dd");
					var end_data = date2str(new Date(sundayTime), "yyyy-MM-dd");
					var now = new Date(); //当前日期     
					var nowDayOfWeek = now.getDay(); //今天本周的第几天     
					var nowDay = now.getDate(); //当前日     
					var nowMonth = now.getMonth(); //当前月     
					var nowYear = now.getYear(); //当前年     
					nowYear += (nowYear < 2000) ? 1900 : 0; //    
					//格式化日期：yyyy-MM-dd     
					function formatDate(date) {
						var myyear = date.getFullYear();
						var mymonth = date.getMonth() + 1;
						var myweekday = date.getDate();
						if (mymonth < 10) {
							mymonth = "0" + mymonth;
						}
						if (myweekday < 10) {
							myweekday = "0" + myweekday;
						}
						return (myyear + "-" + mymonth + "-" + myweekday);
					}
					//获得某月的天数     
					function getMonthDays(myMonth) {
						var monthStartDate = new Date(nowYear, myMonth, 1);
						var monthEndDate = new Date(nowYear, myMonth + 1, 1);
						var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
						return days;
					}
					//获得本月的开始日期     
					function getMonthStartDate() {
						var monthStartDate = new Date(nowYear, nowMonth, 1);
						return formatDate(monthStartDate);
					}
					//获得本月的结束日期     
					function getMonthEndDate() {
						var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
						return formatDate(monthEndDate);
					}
					//月的开始和结束时间
					var month_start = getMonthStartDate();
					var month_finish = getMonthEndDate();
					getAllInfNo();

					function getAllInfNo() {
						new $.getPort({
							config: {
								tourl: "getAllInf.interface",
								teacher_no: config.login_name,
								start_time: start_data,
								end_time: end_data,
								month_start: month_start,
								month_finish: month_finish,
								inf_system: flag,
								menu_value: menu_value,
							},
							callback: function(rep) {
								if (rep.result == 0) {
									$('#show_inf .yesmessage').remove();
									$('#no_data').show();
								} else {
									var inf_data = rep.data;
									var inf_data_len = inf_data.length;
									var dom = '';
									$('#no_data').hide();
									$('#show_inf .yesmessage').remove();
									for (var i = 0; i < inf_data_len; i++) {
										dom += '<div class="ui vertical yesmessage message">';
										if(inf_data[i].inf_status == 0){
											dom +='<div class="information_text_title"><i class="mail inf_look red large outline icon"  data-content="消息未读"></i>&nbsp;&nbsp;&nbsp;&nbsp;';
										}else{
											dom +='<div class="information_text_title"><i class="mail inf_look teal large outline icon"  data-content="消息已读"></i>&nbsp;&nbsp;&nbsp;&nbsp;';
										}
											
										if (inf_data[i].inf_return_url == null || inf_data[i].inf_return_url == '') {
											dom += '<div class="inf_content_">' + inf_data[i].inf_content + '</div>';
										} else {
											dom += '<div class="inf_content_">' + inf_data[i].inf_content + '<i  inf_no="' + inf_data[i].inf_no + '"   src_url="' + inf_data[i].inf_return_url + '" class="inf_class  arrow link right icon"></i></div>';
										}
										dom += '</div>' +
											'<div class="information_text_word">' +
											'<div class="ui equal width form">' +
											'<div class="three fields">' +
											'<div class="field">' +
											'<div>发件人：' + inf_data[i].inf_get_name + '</div>' +
											'</div>' +
											'<div class="field">' +
											'<div class="time_word">发件时间：' + inf_data[i].inf_post_time + '</div>' +
											'</div>' +
											'<div class=" field">' +
											'<i  inf_no="' + inf_data[i].inf_no + '" class="trash_field link red trash  icon"></i>' +
											'</div>' +
											'</div>' +
											'</div>' +
											'</div>' +
											'</div>';
									}
									$('#show_inf').append(dom);
									if($(window).parent()&&$(window).parent().setMsgBoxHeight){
										$(window).parent().setMsgBoxHeight($(".information_container").height());
									}
									$('.inf_class').click(function() {
										var inf_no = $(this).attr("inf_no");
										var src_url = $(this).attr("src_url");
										alert(inf_no);
										alert(src_url);
										changeStatus(inf_no);
										new $.getJSP({
											type: "open",
											config: {
												simple_url: false,
												fromurl: "InfCenter.jsp",
												tourl: src_url
											}
										});
									});
									$('.trash_field').click(function() {
										var inf_no = $(this).attr("inf_no");
										deleteInf(inf_no);
									});
									
									$('.inf_look').popup();
								}
							}
						});
					}
					//点击之后，改变已读未读的状态
					function changeStatus(inf_no) {
						new $.getPort({
							config: {
								tourl: "changeStatus.interface",
								inf_no: inf_no,
							},
							callback: function(rep) {
								getAllInfNo();
							}
						});
					}

					function deleteInf(inf_no) {
						new $.getPort({
							config: {
								tourl: "deleteInf.interface",
								inf_no: inf_no,
							},
							callback: function(rep) {
								getAllInfNo();
							}
						});
					}
				});
			</script>

	</html>