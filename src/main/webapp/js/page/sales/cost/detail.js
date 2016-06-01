define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	var costDetail = {
			config: {
	        },
	        methods :{
	        	initDocument : function(){
	        		
	        		$("#_eoss_business_back").bind('click',function(){
	        			costDetail.methods.closeWindow();
	        		});
		        },
		        closeWindow: function(){
		        	//关闭当前子窗口
		        	metaUtil.closeWindow();  
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =costDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		costDetail.doExecute('initDocument');
	}
});