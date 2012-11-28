<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>Register</title>

		<link rel="stylesheet" type="text/css" href="../js/css/ui.css">


	</head>

	<body>

		<div class="middle">
			<h2>
				欢迎注册Timeline
			</h2>

			<form action="/RegisterUserServlet" method="post" name="userRegForm">

				<input type="text" placeholder="Username" name="username">

				<input type="password" placeholder="Password" name="password">
				<input type="password" placeholder="RePassword" name="repassword">

				<input type="text" placeholder="Email" name="email">

				<input type="submit" class="register" value="  注册  ">
			</form>

		</div>
	</body>
</html>
