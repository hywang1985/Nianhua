<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Login</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/css/ui.css">

	<script type="text/javascript" src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery.md5.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#btn").click(function(){
			txt=$("#code").val();
			result=$.md5(txt);
			$("#r").text(result);		
		})
		});
</script>
  </head>
  
  	<body>
	
	<div class="middle">
	<h2>欢迎来到Timeline</h2>
	<form action="/LoginServlet" method="post">
		        <input type="text" placeholder="Username" name="username">
		      
		        <input type="password" placeholder="Password" name="password">
<%--		        <input type="submit" class="register" value="  注册  ">--%>
		        <a href="user/register.jsp">注册</a>
		        <input type="submit" class="login" value="登陆        »">
		        
	</form>	        		        
	</div>
	</body>
</html>
