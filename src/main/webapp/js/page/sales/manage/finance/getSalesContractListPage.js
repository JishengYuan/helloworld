define(function(require, exports, module) {
	var css = require('js/plugins/ztree/zTreeStyle.css');
	var $ = require("jquery");
	var Map = require("map");
	var DT_bootstrap = require("DT_bootstrap");
	var dataTable$ = require("dataTables");
	require("formUser");
	require("js/plugins/meta/js/metaDropdowmSelect");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	require("uic/message_dialog");
	
	var salesContractDetail = {
			config: {
				module: 'salesContractDetail',
            	namespace:'/sales/manage/finance'
	        },
	        methods :{
	        	initDocument : function(){
	        		//通过审批
		        	$("#_sales_contracts_OK").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/saveOrUpdate";
		    			salesContractDetail.doExecute("closeCon",opts);
		        	});
		        	 $("#backUp").bind('click',function(){
		        		 salesContractDetail.doExecute("_back");
			         });
		        },
		        closeCon:function(opts){
		        	var options = {};
		        	var datas=$("#_eoss_sales_finance_contracts").serialize();
		        	options.murl = ctx+salesContractDetail.config.namespace + opts.url;
		    		 $.post(options.murl,datas,
			    	          function(data,status){
			    	            if(status=="success"){
			    	                UicDialog.Success("修改成功!",function(){
			    	                	salesContractDetail.doExecute("closeWindow",opts);
			    	                });
			    	            }else{
			    	                UicDialog.Error("修改失败！",function(){
			    	                	salesContractDetail.doExecute("closeWindow",opts);
			    	                });
			    	           }
			    	    });
		        },
		        
		        closeWindow: function(){
		        	window.opener.childColseRefresh();
		        	metaUtil.closeWindow();  
		        },
		        _back:function(){
		        	metaUtil.closeWindow();  
		        },
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =salesContractDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		salesContractDetail.doExecute('initDocument');
	}
});