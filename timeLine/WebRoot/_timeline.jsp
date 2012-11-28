<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hywang.timeline.entity.User" %>
<% 
	User user =null;
	Object userObject =request.getSession().getAttribute("user");
	if(userObject!=null){
	    user=(User)userObject;
	}
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE html>

<html>
	<style>
      body {
        padding-top: ; /* 60px to make the container go all the way to the bottom of the topbar */
      }
	</style>
	<head>
		<meta charset="utf-8">
		<title>timeline,where amazing happen!</title>
		<meta name="description" content="TimelineJS example">

		<!-- CSS -->
		<link href="js/css/timeline.css" rel="stylesheet">
		<link rel="stylesheet" href="js/css/style.css" type="text/css" media="screen" />
  		<link rel="stylesheet" href="js/css/fx.slide.css" type="text/css" media="screen" />
  		<link rel="stylesheet" href="js/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="js/bootstrap/css/bootstrap-responsive.min.css">
		
		<!-- JavaScript -->
		<script src="js/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="js/bootstrap/js/jquery.hotkeys-0.7.9.min.js" type="text/javascript"></script>
<!--		<script type="text/javascript" src="js/jquery-string-object.js"></script>-->
		<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="js/timeline-min.js"></script>

		<script>
	$(document).ready(function() {
		var timeline = new VMM.Timeline();
		timeline.init("/timeLine/TimeLineInitServlet");

		$("a#register").click(function() {
			$("form#register-window").fadeIn(200);
		});
		$(".close").click(function() {
			$("form#register-window").fadeOut(200);
		});

		$("#toggle").toggle(function() {
			$("#hidden").animate({
				height : 40
			}, 320);

		}, function() {
			$("#hidden").animate({
				height : 0
			}, 320);
		});
		//键盘展开关闭navbar事件，通过hotkeys.js实现
//		$(document).bind("keydown", "down", function(ev) {
//			$("#hidden").animate({
//				height : 40
//			}, 320); //其它处理事件;    
//			return false;
//		});

//		$(document).bind("keydown", "up", function(ev) {
//			$("#hidden").animate({
//				height : 0
//			}, 320); //其它处理事件;    
//			return false;
//		});
	
	});
</script>


	</head>
	<body>
		<!-- <form id="CategoryForm">  -->
		<!--			<div id="CategoryDiv">-->
		<!--				<input type="checkbox" name="Category" value="Sports">-->
		<!--				Sports-->
		<!--				<br>-->
		<!--				<input type="checkbox" name="Category" value="Events">-->
		<!--				Events-->
		<!--				<br>-->
		<!--				<input type="checkbox" name="Category" value="Entertainment">-->
		<!--				Entertainment-->
		<!--				<br>-->
		<!--				<input type="checkbox" name="Remeber">	Remember my choice  -->
		<!--				<button id="refresh">-->
		<!--					refresh-->
		<!--				</button>-->
		<!--			</div>-->
		<!--  </form>  -->
<!--		<div id="userDiv">-->
<!--			<button id="regBtn" onclick="location.href='user/register.jsp'">-->
<!--				register-->
<!--			</button>-->
<!--			<button id="loginBtn" onclick="location.href='user/login.jsp'">-->
<!--				login-->
<!--			</button>-->
<!--		</div>-->
<!--		<iframe src="titleBar2.jsp" name="titleBar" id="titleBar"></iframe>-->
	<!--导航栏-->

<%if(user==null) {%>
<div id="hidden" style="height: 0px; overflow:hidden;">
<div class="navbar" >
	<div class="navbar-inner">
		<div class="container-fluid">
			<a class="brand " href="#" style="color: gray;">年华</a>
			<ul class="nav">
				<li class="divider-vertical"></li>
				<!--<li><a href="#"></a></li>
				<li class="divider-vertical"></li>
				<li><a href="#">Link Text</a></li>
				<li class="divider-vertical"></li>
				<li><a href="#">Link Text</a></li>
				<li class="divider-vertical"></li>-->
			</ul>
		
			<form class="navbar-form pull-right form-inline" action="/LoginServlet" method="post">
				<input type="text" class="input-small" style="margin-top: 0px;" placeholder="账户名" name="username">
				<input type="password" class="input-small" style="margin-top: 0px;" placeholder="密码"  name="password">
				<label class="checkbox"><input type="checkbox" name="rememberme" value="true">记住我</label>
				<button class="btn btn-success" type="submit"><i class="icon-ok icon-white"></i> 登陆</button>
				<a id="register" class="btn btn-min btn-info" href="#"><i class="icon-plus icon-white"></i> 注册</a>	
			</form>
		</div>
	</div>
</div>
<%}else { %>
<div id="hidden" style="height: 40px; overflow:hidden;">
	<div class="navbar">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a class="brand " href="#" style="color: white;">年华</a>
			<ul class="nav">
				<li class="divider-vertical"></li>
				<li><a href="#">Link Text</a></li>
				<li class="divider-vertical"></li>
				<li><a href="#">Link Text</a></li>
				<li class="divider-vertical"></li>
				<li><a href="#">Link Text</a></li>
				<li class="divider-vertical"></li>
			</ul>
		
			<ul class="nav pull-right">
			<li class="divider-vertical"></li>
			<li><a href="/UserProfileServlet"><i class="icon-user icon-white"></i> <%=user.getUserName() %></a></li>
			<li class="divider-vertical"></li>
			<li><a href="#"><i class="icon-plus icon-white"></i> 创建</a></li>
			<li class="divider-vertical"></li>
			<li><a href="#"><i class="icon-list icon-white"></i> 管理</a></li>
			<li class="divider-vertical"></li>
			<li><a href="#"><i class="icon-cog icon-white"></i> 设置</a></li>
			<li class="divider-vertical"></li>
			<li><a href="/LogoutServlet"><span class="label label-important">登出</span></a></li>
			</ul>
		</div>
	</div>
</div>

<%} %>
</div>

<!--
	<form class="span3 well" style="margin-top: 200px;height:1500px">
	<input type="text" name="" placeholder="账户" />
	<input type="password" name="" placeholder="密码" />
	<label for="" class="checkbox">
		<input type="checkbox" name="" value="" />记住我
	</label>
	<button class="btn-success btn btn-large" style="width: 140px;" type="submit"><i class="icon-ok icon-white"></i> 登陆</button>
	<button class="btn btn-large" type="submit"><i class="icon-plus"></i> 注册</button>
</form>
-->

<!--拉帘按钮-->
		<div id="loginTop" style="height: 30px;">
		<!-- login -->
		<div id="toggle" style="background-color: black;border-radius:0 0 8px 8px ;height: 20px;width:60px;margin: 0px auto;text-align: center;padding-top: 5px;z-index: ;"><i style="width: ;60px"><i class="icon-chevron-up icon-white"></i></i>
		</div>
		</div>



<!--_注册弹出页面_-->
	<form id="register-window" action="/RegisterUserServlet" method="post" class="well" style="width: 230px;margin-left: -115px;position: absolute;left: 50%;margin-top: 100px;display: none; z-index:999;">
	<a class="close">&times;</a>
	<legend>注册年华	<small>相信我，这很快</small></legend>
	<input type="text" name="username" placeholder="账户" />
	<input type="password" name="password" placeholder="密码" />
	<input type="password" name="password2" placeholder="重复密码" />
	<input type="email" name="email" placeholder="邮箱" />
	<label for="" class="checkbox">
	<input type="checkbox" name="" value="" />同意<a href="#">条款</a>
	</label>
	<button  class="btn-info btn btn-large" style="width: 220px;" type="submit"><i class="icon-plus icon-white"></i> 注册</button>	
	</form>

 	<div id="">
	<div id="timeline"></div>
	</div>
	
		

	</body>
</html>
