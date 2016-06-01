define(function(require, exports, module) {
	var $ = require("jquery");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	var interPurchasDetail = {
			config: {
				module: 'interPurchasDetail',
            	namespace:'/business/interPurchas'
	        },
	        methods :{
	        	initDocument : function(){
	        		//interPurchasDetail.doExecute('getFormSelect');
		        	$("#_eoss_business_inter_back").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/manage";
		    			interPurchasDetail.doExecute("goBack",opts);
		        	});
		        	$("#_eoss_business_inter_print").unbind('click').click(function () {
		    			interPurchasDetail.doExecute("print");
		        	});
		        	
	        	},
		        	//getFormSelect : function(){},
		        goBack : function(opts){
		        	//刷新父级窗口
		    		//window.opener.childColseRefresh();
		    		//关闭当前子窗口
		        	metaUtil.closeWindow();   
		        },
	        	print : function(){
	        		id=$("#interID").val();
	        		var url=ctx+'/business/interPurchas/printInter?id='+id+'&tmp='+Math.random();
	    			$.ajax({url:url,async:false,success:function(data){
	        			window.open(url,'_blank','status=no,scrollbars=yes,top=150,left=150,width=800,height=500');
	        		  }});
	        	}
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =interPurchasDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		interPurchasDetail.doExecute('initDocument');
	}
});