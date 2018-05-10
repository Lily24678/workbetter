$(function(){
	// 为省份下拉列表绑定事件:
	$("#province").change(function(){
		// 获得选中的省份的id:
		var pid = $(this).val();
		// alert(pid);
		$.post("/day15/ServletDemo6",{"pid":pid},function(data){
			// alert(data);
			var $city = $("#city");
			$city.html("<option>-请选择-</option>");
			$(data).find("city").each(function(){
				var cid = $(this).children("cid").text();
				var cname = $(this).children("cname").text();
				
				$city.append("<option value='"+cid+"'>"+cname+"</option>");
			});
		});
	});
});