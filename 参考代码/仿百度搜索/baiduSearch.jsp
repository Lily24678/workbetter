<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/jq-Ajax_baiduSearch/baiduSearch.js"></script>
</head>
<body>
<center><h1>百度</h1>
<input type="text" id="word" name="word" style="width:400px;height:25px;"><input type="button" value="百度一下"></center>
<div id="d1" style="display:none;position:absolute;top:120px;left:440px;border:1px solid gray;width:400px;height:200px;"></div>
</body>
</html>