<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>
<script>
$(function(){
	//查询所有的省
	$.post("${pageContext.request.contextPath }/DataServlet",{method:"queryProvince"},function(data){
		$(data).each(function(){
			//this -> {"pid":1,"pname":"陕西省"}
			var el = $("<option></option>");//创建一个元素<option></option>
			el.text(this.pname);//设置结点文本<option>文本</option>
			el.attr("value",this.pid);//设置结点属性
			$("#province").append(el);//将option节点添加到下拉框
			//$("#province").append("<option>"+this.pname+"</option>");//将option节点添加到下拉框
		});
		
	},"json");
	
	//选择不同的省份,市的下拉列表也跟着改变
	
	$("#province").change(function(){
		//当触发了省份的选择改变，那么就必须要清空城市下拉框原有的数据
		$("#city option:gt(0)").remove();//找city下的所有的option元素，并且索引值大于0，将这些option节点删除
		var pid = $(this).val();
		$.post("${pageContext.request.contextPath }/DataServlet",{method:"queryCity",pid:pid},function(data){
			$(data).each(function(){
				var el = $("<option></option>");//创建一个元素<option></option>
				el.text(this.cname);//设置结点文本<option>文本</option>
				el.attr("value",this.cid);//设置结点属性
				$("#city").append(el);//将option节点添加到下拉框
			});
		},"json");
		
	});
	
	
});
</script>
省:
<select name="province" id="province">
	<option value="">---请选择---</option>		
</select>
&nbsp;&nbsp;&nbsp;&nbsp;
市:
<select name="city" id="city">
	<option value="">---请选择---</option>
</select>

</body>
</html>