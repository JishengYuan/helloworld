define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("jqBootstrapValidation");
	require("confirm_dialog");
	require("uic/message_dialog");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	var interPurchasSP = {
			config: {
				module: 'interPurchasSP',
				
            	namespace:ctx+'/business/interPurchas'
	        },
	        methods :{
	        	initDocument : function(){
	        		interPurchasSP.doExecute('getFormSelect');
	        		//确认审批结果
	        		$("#_order_OK").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/handleFlow";
		    			interPurchasSP.doExecute("approveToFlow",opts);
		        	});
	        		$("#_eoss_business_back").unbind('click').click(function () {
		        		var opts = {};
		        		opts.url = "/manage";
		        		interPurchasSP.doExecute("closeWindow",opts);
		        	});
	        		
	        	},
	        	 getFormSelect : function(){
			        	
	        	 },
	        	 closeWindow: function(){
	        			//刷新父级窗口
			        	window.opener.reload();
			        	//关闭当前子窗口
			        	metaUtil.closeWindow();   
	        	 },
	        	 approveToFlow : function(opts){
			        	var options = {};
			    		options.murl = interPurchasSP.config.namespace + opts.url;
			    		var datas=$("#_eoss_business_interPurchas").serialize();
			    		 //启动遮盖层
		       	    	 $('#_progress1').show();
		       	    	 $('#_progress2').show();
			    		 $.post(options.murl,datas,
				    	          function(data,status){
				    	            if(status=="success"){
				    	                interPurchasSP.doExecute("closeWindow",opts);
				    	            }else{
				    	                UicDialog.Error("审批失败！",function(){
				    	                	interPurchasSP.doExecute("closeWindow",opts);
				    	                });
				    	           }
				    	    });
			    		
			        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =interPurchasSP.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		interPurchasSP.doExecute('initDocument');
	}
});