define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap-datetimepicker");
	require("formSelect");	
	var searchPay = {
			config: {
				module: 'searchPay'
	        },
	        methods :{
	        	initDocument : function(){
	        		 $('.date').datetimepicker({
	        		    	pickTime: false
        		    });
	        		 searchPay.doExecute('selectSupplier');
		        },
		        selectSupplier:function(){
		        	var supplierShortName=$("#supplierShortName");
					supplierShortName.addClass("li_form");
					var optionCompPosType = {
							writeType : 'show',
							showLabel : false,
							url : ctx+"/business/order/getSupplierShortName?tmp="+Math.random(),
							onSelect:function(node){
								$('#search_supplierShortName').val(node);
							},
							width : "250"
					};
					supplierShortName.formSelect(optionCompPosType);
					$('.uicSelectData').height(200);
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =searchPay.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		searchPay.doExecute('initDocument');
	}
});