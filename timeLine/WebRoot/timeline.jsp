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
		<title>年华</title>
		<meta name="description" content="TimelineJS example">

		<!-- CSS -->
		<link href="<%=basePath%>js/css/timeline.css" rel="stylesheet">
		<link rel="stylesheet" href="<%=basePath%>js/css/style.css" type="text/css" media="screen" />
  		<link rel="stylesheet" href="<%=basePath%>js/css/fx.slide.css" type="text/css" media="screen" />
  		<link rel="stylesheet" href="<%=basePath%>js/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="<%=basePath%>js/bootstrap/css/bootstrap-responsive.min.css">
		
		
		<!-- JavaScript -->
		<script type="text/javascript" src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/timeline.js"></script>
		<script src="<%=basePath%>js/audiojs/audio.min.js" type="text/javascript"></script>

		<script>
			audiojs.events.ready(function() { //audiojs,initialize the music.
				var as = audiojs.createAll();
			});
			$().ready(function() {
				var timeline = new VMM.Timeline();
				timeline.init("/init/timeline_init"); //AJAX异步调用初始化，不要在这个ACTION做的时候加入interceptor返回到这个页面，否则会有不同步的问题

			$("a#register").click(function() {
				$("form#register-window").fadeIn(200);
			});
			$(".close").click(function() {
				$("form#register-window").fadeOut(200);
			});

			$("#hidden").mouseenter(function() {
				$("#hidden").css("opacity", "100");
			});

			$("#hidden").mouseleave(function() {
				$("#hidden").css("opacity", "0");
			});

		});
</script>


	</head>
	<body>
<!--导航栏-->
<div id="hidden" style="position: absolute;top: 0;left: 0;z-index: 200;width:100%;opacity:0;height:40px;">
<%if(user==null) {%>

<div class="navbar" >
	<div class="navbar-inner" style="padding: 0;">
		<div class="container-fluid">
			<a class="brand " href="#"><img src="<%=basePath%>js/bootstrap/img/nianhua-logo2.png" alt=""/ style="margin-top: -6px;margin-right: 10px;">年华</a>
			<ul class="nav">
				<li class="divider-vertical"></li>
				<audio id="player" src="<%=basePath%>/music/Drenched.mp3" preload="auto" autoplay="autoplay" loop="loop"></audio>
			</ul>
			<form class="navbar-form pull-right form-inline" action="/user/user_login" method="post">
				<input type="text" class="input-small" style="margin-top: 0px;" placeholder="账户名" name="username">
				<input type="password" class="input-small" style="margin-top: 0px;" placeholder="密码"  name="password">
				<label class="checkbox"><input type="checkbox" name="rememberme" value="true">记住我</label>
				<button class="btn btn-primary" type="submit"><i class="icon-ok icon-white"></i> 登陆</button>
				<a id="register" class="btn btn-min" href="#"><i class="icon-plus"></i> 注册</a>	
			</form>
		</div>
	</div>
</div>
<%}else { %>

	<div class="navbar">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a class="brand " href="#"><img src="<%=basePath%>js/bootstrap/img/nianhua-logo2.png" alt=""/ style="margin-top: -6px;margin-right: 10px;">年华</a>
			<ul class="nav">
				<li class="divider-vertical"></li>
				<audio id="player" src="<%=basePath%>/music/Drenched.mp3" preload="auto" autoplay="autoplay" loop="loop"></audio>				
			</ul>
		
			<ul class="nav pull-right">
			<li class="divider-vertical"></li>
			<li><a href="/myprofile.jsp"><i class="icon-user icon-white"></i> <%=user.getUserName() %></a></li>
			<li class="divider-vertical"></li>
			<li><a href="#"><i class="icon-plus icon-white"></i> 创建</a></li>
			<li class="divider-vertical"></li>
			<li><a href="#"><i class="icon-list icon-white"></i> 管理</a></li>
			<li class="divider-vertical"></li>
			<li><a href="#"><i class="icon-cog icon-white"></i> 设置</a></li>
			<li class="divider-vertical"></li>
			<li><a href="/user/user_logout"><span class="label label-important">登出</span></a></li>
			</ul>
		</div>
	</div>
</div>

<%} %>
</div>




<!--_注册弹出页面_-->
	<form id="register-window" action="/user/user_register" method="post" class="well" style="width: 230px;margin-left: -130px;position: absolute;left: 50%;margin-top: 100px;display: none; z-index:999;box-shadow: 0px 3px 20px #000000;">
	<a class="close">&times;</a>
	<legend>注册年华	<small>相信我，这很快</small></legend>
	<input type="text" name="username" placeholder="账户" />
	<input type="password" name="password" placeholder="密码" />
	<input type="password" name="password2" placeholder="重复密码" />
	<input type="email" name="email" placeholder="邮箱" />
	<label for="" class="checkbox">
	<input type="checkbox" name="" value="" />同意<a href="#">条款</a>
	</label>
	<button  class="btn-primary btn btn-large" style="width: 220px;" type="submit"><i class="icon-plus icon-white"></i> 注册</button>	
	</form>




<!-- timeline块 -->
<div id="timeline">
</div>
	
		

	</body>
</html>
