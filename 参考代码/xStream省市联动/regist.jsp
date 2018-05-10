<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>注册页面</title>
		<style>
			div{
				border:1px solid blue;
			}
			
			.bodyDiv{
				width:90%;
			}
			
			.bodyDiv > div{
				width:100%;
			}
			
			.logoDiv{
				width:33%;
				height:50px;
				float:left;
			}
			
			.clear{
				clear:both;
			}
			
			ul li{
				display:inline;
			}
			
			a:link{
				color:blue;
			}
			
			a:visited{
				color:#FFFF00;
			}
			
			a:hover{
				color:green;
				font-size: 20px;
			}
			
			a:active{
				color:red;
				font-size:30px;
			}
		</style>
		
		<script type="text/javascript" src="${ pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${ pageContext.request.contextPath }/06_jq_province/regist.js"></script>
	</head>
	<body>
		<!--整体DIV-->
		<div class="bodyDiv">
			<div>
				<div class="logoDiv">
					<img src="${pageContext.request.contextPath}/img/logo2.png" height="50"/>
				</div>
				<div class="logoDiv">
					<img src="${pageContext.request.contextPath}/img/header.png" height="50"/>
				</div>
				<div class="logoDiv" style="margin-top:0px;padding-top:10px;height:40px;">
					<a href="#">登录</a>
					<a href="#" id="reg">注册</a>
					<a href="#">购物车</a>
				</div>
				<div class="clear"></div>
			</div>
			<div style="height:50px;background-color: black;color:white;font-size: 20px;">
				<ul >
					<li>首页</li>
					<li>首页</li>
					<li>首页</li>
					<li>首页</li>
				</ul>
			</div>
			<div style="height:500px;background-image: url(${pageContext.request.contextPath}/img/regist_bg.jpg);">
				<div style="border:5px solid gray;background-color:white;position:absolute;left:350px;top:160px;width:600px;">
					<form action="" method="post" >
					<table border="0" width="100%" cellspacing="15">
						<tr>
							<td>用户名</td>
							<td><input type="text" id="username" name="username"><span id="s1"></span></td>
						</tr>
						<tr>
							<td>密码</td>
							<td><input type="password" id="password" name="password" ></td>
						</tr>
						<tr>
							<td>确认密码</td>
							<td><input type="password" id="repassword" name="repassword"></td>
						</tr>
						<tr>
							<td>性别</td>
							<td><input type="radio" name="sex" value="男">男<input type="radio" name="sex" value="女">女</td>
						</tr>
						<tr>
							<td>籍贯</td>
							<td>
								<select id="province" name="province" >
									<option value="">-请选择-</option>
									<c:forEach var="p" items="${ list }">
										<option value="${ p.pid }">${ p.pname }</option>
									</c:forEach>
								</select>
								<select id="city" name="city">
									<option>-请选择-</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>爱好</td>
							<td>
								<input type="checkbox" name="hobby" value="篮球" />篮球
								<input type="checkbox" name="hobby" value="足球" />足球
								<input type="checkbox" name="hobby" value="排球" />排球
								<input type="checkbox" name="hobby" value="羽毛球" />羽毛球
							</td>
						</tr>
						<tr>
							<td>邮箱</td>
							<td><input type="text" id="email" name="email"></td>
						</tr>
						<tr>
							<td colspan="2"><input id="regBut" type="submit" value="注册"></td>
						</tr>
					</table>
					</form>
				</div>
			</div>
			<div>
				<img src="${pageContext.request.contextPath}/img/footer.jpg" width="100%"/>
			</div>
			<div>
				<center>
					

    关于我们 联系我们 招贤纳士 法律声明 友情链接 支付方式 配送方式 服务声明 广告声明 <br/>

Copyright © 2005-2016 传智商城 版权所有 
				</center>
			</div>
		</div>
	</body>
</html>
