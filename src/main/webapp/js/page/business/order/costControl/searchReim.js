define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap-datetimepicker");
	
	var searchReim = {
			config: {
				module: 'searchReim'
	        },
	        methods :{
	        	initDocument : function(){
	        		 $('.date').datetimepicker({
	        		    	pickTime: false
        		    });
		        },
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =searchReim.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		searchReim.doExecute('initDocument');
	}
});