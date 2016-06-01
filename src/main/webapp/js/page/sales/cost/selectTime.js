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
		getTime();
	}
	 /**
     *  时间插件
     */
      function  getTime(){
     	  $('.date').datetimepicker({ 
     	        pickTime: false
     	 
     	      }); 
      }
      function ok_contract(){
    	  var time = $("#add_time").val();
    	  
    	  $.ajax({
				url: ctx+"/sales/cost/getInsertSalesCost?time="+time+"&tmp="+Math.random(),  // 提交的页面
	            data: "", // 从表单中获取数据
	            async : false,
	            type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
	            error: function(request) {     // 设置表单提交出错
	            },
	            success: function(data) {
	            	var msg=data;
	            	if(msg.time!=null){
	            		UicDialog.Error(msg.time+"月已添加，不可重复添加！");
	            	}else if(msg.newSaleCost=="true"&&msg.saleCostBH=="true"){
	            		UicDialog.Success("添加完成！");
	            	}else{
	            		UicDialog.Error("添加失败！");
	            	}
	            	
	            }
	            
			});
    	  $('#dailogs1').modal('hide');
      }
});