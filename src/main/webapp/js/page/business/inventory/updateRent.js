define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	
	exports.init = function() {
		load();
	};
	
	function load() {
		$("#ok_Contract_Add").unbind('click').click(function(){
			$("#ok_Contract_Add").unbind('click');
			ok_contract();
		});
	}
      function ok_contract(){
    	  var id = $("#id").val();
    	  var rent = $("#rent").val();
    	  $.ajax({
				url: ctx+"/business/inventory/getNewRent?id="+id+"&rent="+rent+"&tmp="+Math.random(),  // 提交的页面
	            data: "", // 从表单中获取数据
	            async : false,
	            type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
	            error: function(request) {     // 设置表单提交出错
	            },
	            success: function(data) {
	            	var msg=data;
	            	if(msg=='true'){
	            		//UicDialog.Success("完成修改！");
	            	}else{
	            		UicDialog.Success("修改失败！");
	            	}
	            }
			});
    	  $("#uicTable").tableOptions({
  			pageNo : '1',
  			addparams:[]
  			});
  			$("#uicTable").tableReload();
    	  $('#dailogs1').modal('hide');
    	  
      }
});