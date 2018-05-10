<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>异步请求用户注册验证</h1><br/>
用户名<input type="text" name = "username" value="" id="username"/><span id="sp1"></span>
<br/>
密码<input type="password" name="password" value="" id="password"/>
<br/>
<input type="button" value="提交" onclick="ajax_post()"/>
<script>
function ajax_post(){
	//创建异步对象
	var xmlHttp = createXMLHttp();
	//设置监听
	xmlHttp.onreadystatechange = function(){
		if(xmlHttp.readyState==4&&xmlHttp.status==200){
			var data = xmlHttp.responseText;
			document.getElementById("sp1").innerHTML=data;
		}
	}
	//设置打开路径
	xmlHttp.open("post","${pageContext.request.contextPath }/AjaxRegistServlet",true);
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	//发送数据
	var username = document.getElementById("username").value;
	xmlHttp.send("username="+username);
}
//创建异步对象的方法
function createXMLHttp() {
	var xmlHttp;
	try { // Firefox, Opera 8.0+, Safari
		xmlHttp = new XMLHttpRequest();
	} catch (e) {
		try {// Internet Explorer
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
			}
		}
	}

	return xmlHttp;
}

</script>



</body>
</html>