$(function(){
	$("#username").blur(function(){
//		var $username = $(this).val();
		var $username = $("#username").val();
		//默认使用 GET 方式 - 传递附加参数时自动转换为 POST 方式
//		$("#s1").load("/demo_AJAX/JQAjaxRegistServlet", {"username":$username});//post
//		$("#s1").load("/demo_AJAX/JQAjaxRegistServlet?username="+$username);//get
		$.get("/demo_AJAX/JQAjaxRegistServlet",{"username":$username},function(data){
			if(data==1){
				$("#s1").html("<font color='red'>用户名已经存在</font>");
			}else if(data==0){
				$("#s1").html("<font color='green'>用户名可用</font>");
			}
		});	
	});
});