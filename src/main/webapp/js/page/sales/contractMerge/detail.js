define(function(require, exports, module) {
	var $ = require("jquery");
	require("uic/message_dialog");
	var supplierTypeJson = {};
	var count = 1;
	var contractMergeDetail = {
			config: {
				module: 'contractMergeDetail',
				uicTable : 'uicTable',
	            namespace: '/sales/contractMerge'
	        },
	        methods :{
	        	initDocument : function(){
	        		
	        		$("#_sales_merge_back").bind('click').click(function(){
	        			var options = {};
			    		options.murl = "sales/contractMerge/manage";
			    		$.openurl(options);
	        		});
	        		
	        		$("#_sales_merge_submit").bind('click').click(function(){
	        			contractMergeDetail.doExecute("doSave");
	        		});
	        		
	        	},
		        doSave:function(){
		        	$("#_sales_merge_submit").unbind();
		        	var datas=$("#_sino_eoss_sales_merge_detailform").serialize();
		       	    var url = ctx+'/sales/contractMerge/appproveSubmit?tmp='+Math.random();
		       	    $.post(url,datas, function(data,status){
		       	    	if(data == 'success'){
		        			UicDialog.Success("审批成功！");
		        			var options = {};
				    		options.murl = "sales/contractMerge/manage";
				    		$.openurl(options);
		        		} else {
		        			UicDialog.Error("审批失败！");
		        		}
		        	});
		        },
		        
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = "sales/contractMerge" + opts.url;
		    		if(opts.id){
		    			options.keyName = 'id';
		    			options.keyValue = opts.id;
		    		}
		    		$.openurl(options);
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =contractMergeDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	
	exports.init = function() {
		contractMergeDetail.doExecute('initDocument');
	}
	
});