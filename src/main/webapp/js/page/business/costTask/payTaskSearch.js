define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap-datetimepicker");
	var addOrderContract={
			config: {
	        },
	        methods :{
	        	initDocument:function(){
					//	 getTime();
			   },

			},
				doExecute : function(flag, param) {
					var method =addOrderContract.methods[flag];
					if( typeof method === 'function') {
						return method(param);
					} else {
						alert('操作 ' + flag + ' 暂未实现！');
					}
				}
	}
    /**
     *  时间插件
     */
      function  getTime(){
     	  $('.date').datetimepicker({ 
     	        pickTime: false
     	 
     	      }); 
      }

	exports.init = function(){
		addOrderContract.doExecute('initDocument');  
	}
});
