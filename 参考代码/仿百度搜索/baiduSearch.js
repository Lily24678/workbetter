$(function(){
	$("#word").keyup(function(){
		var word = $(this).val();
		if(word != ""){
//			$.post("/demo_AJAX/BaiduSearchServlet",{"word":word},function(data){
//				$("#d1").show().html(data);
//			});
			$("#d1").load("/demo_AJAX/BaiduSearchServlet", {"word":word}, function(data){
				$("#d1").show().html(data);
			});
		}else{
			$("#d1").hide();
		}
		
	});
});