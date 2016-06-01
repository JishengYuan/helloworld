define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("jqBootstrapValidation");
	require("formSubmit");
	
	var uic$ = require("uic_Dropdown");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	
	//------------------加载DataTable-----------Start--------------
	var Map = require("map");
	require("uic/message_dialog");
	require("json2");
	
	var addOrderContract={
			config: {
				//namespace:'/business/supplierm/supplierInfo'
	        },
	        methods :{
	        	initDocument:function(){
						/*
						 * 绑定触发事件
						 */
						 $("#no_back").bind('click',function(){
							 _back();
				         });
						//直接提交审批
						 $("#sp_Add").unbind('click').click(function () {
							 $("#orderStatus").val('SH');
							 _add();
					       });
			   },

				},
				/**
				 * 执行方法操作
				 */
				doExecute : function(flag, param) {
					var method =addOrderContract.methods[flag];
					if( typeof method === 'function') {
						return method(param);
					} else {
						alert('操作 ' + flag + ' 暂未实现！');
					}
				}
	}

	function _back(){
		//刷新父级窗口
		window.opener.childColseRefresh();
		metaUtil.closeWindow();
		//关闭当前子窗口
	}
	function _add(){
	     var datas=$("#_sino_eoss_cuotomer_addform").serialize();
	     var options = {};
	     options.formId = "_sino_eoss_cuotomer_addform";
	     var url = ctx+'/business/order/doChange?tmp='+Math.random();
	     if($.formSubmit.doHandler(options)){
	    	//启动遮盖层
   	    	 $('#_progress1').show();
   	    	 $('#_progress2').show();
   	    	$.post(url,datas,
	 	            function(data,status){
	 	            	if(data.result=="success"){
	 	            			UicDialog.Success("变更申请提交成功!",function(){
	 	            				_back();
	 	            			});
	 	                  }else{
	 	                		 UicDialog.Error(data.message);
	 	                	  $('#_progress1').hide();
	 	                	  $('#_progress2').hide();
	 	                  }
	 	                },"json"); 
	     }
	}

	exports.init = function(){
		addOrderContract.doExecute('initDocument');  
	}
});
