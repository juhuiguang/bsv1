<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% 
    String PATH = request.getContextPath();
	String BASE_URL  = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String BASE_PATH = BASE_URL + PATH +"/";
	String BASE_HOME = BASE_PATH +"BaseService";
	request.setAttribute("BASE_PATH", BASE_PATH);
    %>
<head>

<base href="<%=BASE_PATH%>"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0 , user-scalable=no"/>		
<meta http-equiv="cache-control" content="no-cache">
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta name="format-detection" content="telephone=no">
<meta http-equiv="x-rim-auto-match" content="none">  
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible"/>
<meta name="description" content="major" />
<meta name="author" content="alienlab" />
<title></title>
<link href="js/semantic/semantic.min.css" rel="stylesheet" type="text/css" />
<link href="js/alert/jquery.alert.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-2.1.0.js"></script>
<script src="js/jquery.cookie.js" type="text/javascript"></script> 
<script src="js/semantic/semantic.min.js"></script>
<script type="text/javascript" src="js/alert/jquery.alert.js"></script>

</head>