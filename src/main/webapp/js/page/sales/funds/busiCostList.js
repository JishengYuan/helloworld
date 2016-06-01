define(function(require, exports, module) {
	var $ = require("jquery");
	var contract = {
			config: {
				module: 'contract',
				uicTable : 'uicTable',
	            namespace: '/sales/fundsSalesContract'
	        },
	        methods :{
	        	initDocument : function(){
	        		$("#busicostid").unbind('click').click(function () {
	        			$("#edit_list").load(ctx+"/sales/fundsSalesBusiCost/busiCostList");
	        		});
	        		$("#busicoststock").unbind('click').click(function () {
	        			$("#edit_list").load(ctx+"/stockup/contractCost/manage");
	        		});
	        		
	        		$(".confirmcost").unbind('click').click(function () {
	        			window.open(ctx+"/sales/fundsSalesBusiCost/busiCostInfo?payAppid="+this.id);
	        		});
	   
		        }
		      
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =contract.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		contract.doExecute('initDocument');
	}
	
	
	
	

  
});