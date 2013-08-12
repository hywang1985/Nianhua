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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<style>
body {
	padding-top: 60px;
	/* 60px to make the container go all the way to the bottom of the topbar */
}
</style>
	<head>
		<meta charset="utf-8">
		<title>年华</title>
		<link type="text/css"
			href="<%=basePath%>js/css/ui-lightness/jquery-ui-1.8.21.custom.css"
			rel="Stylesheet" />
		<link rel="stylesheet"
			href="<%=basePath%>js/bootstrap/css/bootstrap-responsive.min.css">
		<link rel="stylesheet" href="<%=basePath%>js/bootstrap/css/bootstrap.css">
		<link href="<%=basePath%>js/jtable/themes/standard/blue/jtable_blue.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jquery-ui-1.8.21.custom.min.js"></script>
		<script src="<%=basePath%>js/jtable/jquery.jtable.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/bootstrap/js/bootstrap-tab.js" type="text/javascript"></script>
	<script>
	$(function() {
		$("#startdate").datepicker();
		$("#enddate").datepicker();
		
		
		$("#manage_tab").click(
				function() {
					$.getJSON("/article/article_load", function(data) {
						$("#ArticlesContainer").jtable(data);
						$("#ArticlesContainer").jtable('load');
					});
				});
	});
</script>
	</head>
	<body>
		<div class="navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container-fluid">
					<a class="brand " href="/timeline.jsp" style="color: white;">年华</a>

					<ul class="nav pull-right">
						<li class="divider-vertical"></li>
						<li>
							<a href="#"><i class="icon-user icon-white"></i>  <%=user.getUserName() %></a>
						</li>
						<li class="divider-vertical"></li>
						<li>
							<a href="#"><i class="icon-plus icon-white"></i> 创建</a>
						</li>
						<li class="divider-vertical"></li>
						<li>
							<a href="#"><i class="icon-list icon-white"></i> 管理</a>
						</li>
						<li class="divider-vertical"></li>
						<li>
							<a href="#"><i class="icon-cog icon-white"></i> 设置</a>
						</li>
						<li class="divider-vertical"></li>
						<li>
							<a href="/user/user_logout"><span class="label label-important">登出</span>
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<div class="container-fluid" >
			<div class="tabbable tabs-left">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#1" data-toggle="tab"><i class="icon-plus"></i> 创建</a>
					</li>
					<li>
						<a href="#2" data-toggle="tab" id="manage_tab"><i class="icon-list"></i> 管理</a>
					</li>
					<li>
						<a href="#3" style="color: gray;"><i class="icon-cog"></i>
							设置-暂不可用</a>
				</ul>
				<div class="tab-content well">
					<div class="tab-pane active" id="1">

						<form class="form-horizontal" action="/article/article_create" method="post" >
							<fieldset>
								<legend>
									撰写新文章
								</legend>
								<div class="control-group">
									<label class="control-label" for="input01">
										正文
									</label>
									<div class="controls">
										<textarea class="input-xlarge span6"
											style="height: 150px;" name="header" id="input01"></textarea>
										<p class="help-block">
											写出你想说的，或者憋在心里的。
										</p>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="input01">
										背景描述
									</label>
									<div class="controls">
										<textarea class="input-xlarge span6" id="textare" name="article"
											style="height: 100px"></textarea>
										<p class="help-block">
											可以对正文做出解释，或者描述事件背景。
										</p>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label" for="input01">
										起始时间
									</label>
									<div class="controls">
										<input type="text" value="01/01/2012" id="startdate" name="startdate">
										<p class="help-block">
											请记录下开始时间。
										</p>
									</div>
									<label class="control-label" for="input01">
										结束时间
									</label>
									<div class="controls">
										<input type="text" value="01/01/2012" id="enddate" name="enddate">
										<p class="help-block">
											请记录下结束时间。
										</p>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label" for="input01">
										媒体
									</label>
									<div class="controls">
										<input type="url" name="media" value="" placeholder="url://" />
										<p class="help-block">
											请选择媒体
										</p>
									</div>
								</div>
									<div class="control-group">
									<label class="control-label" for="input01">
										背景图
									</label>
									<div class="controls">
										<input type="url" name="bgrimg" value="" placeholder="url://" />
										<p class="help-block">
											请选择背景图
										</p>
									</div>
								</div>

								<button type="submit" class="btn pull-right btn-info"
									style="width: 100px; margin-left: 10px;">
									提交
								</button>
								<button type="submit" class="btn pull-right" style="width: 80px">
									预览
								</button>


							</fieldset>
						</form>
					</div>


					<div class="tab-pane" id="2">
						<div id="ArticlesContainer"></div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
