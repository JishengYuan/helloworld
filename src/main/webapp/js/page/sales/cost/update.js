define(function(require, exports, module){
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("formSubmit");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	require("uic/message_dialog");
	var update={
			config:{
				module:'update',
				namespace:'/sales/cost',
			},
			methods:{
				initDocument:function(){
					$("#_eoss_business_OK").bind('click',function(){
						update.methods.updates();
					});
					$("#_eoss_business_back").bind('click',function(){
						update.methods.close();
					});
				},
				updates:function(){
					var dates = $("#_sino_eoss_sales_cost_Form").serialize();
					var url=ctx+update.config.namespace+"/doUpdate?tmp="+Math.random();
					 var options = {};
		       	     options.formId = "_sino_eoss_sales_cost_Form";
					$.post(url,dates,
							function(date,status){
							if(status="success"){
			                	  UicDialog.Success("资金占用成本修改成功!",function(){
			                	    update.methods.back();
			                	  });
			                  }else{
			                  	  UicDialog.Error("资金占用成本修改失败！",function(){
			                  		  update.methods.close();
			                  	  });
			                  }
			            });
				},
				back:function(){
					window.opener.childColseRefresh();
					metaUtil.closeWindow();   
				},
				close(){
					metaUtil.closeWindow();   
				},
			},
	}
	exports.init=function(){
		update.methods.initDocument();
	}
})