define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("formSelect");
	var reimSearch = {
			config: {
				module: 'reimSearch',
				supplierShortName:'supplierShortName',
	        },
	        methods :{
	        	initDocument : function(){
	        		reimSearch.doExecute('getSupplier');
		        },
		        
		        getSupplier:function(){
		        	var supplierShortName=$("#"+reimSearch.config.supplierShortName);
 					supplierShortName.addClass("li_form");
 					var optionCompPosType = {
 							writeType : 'show',
 							showLabel : false,
 							url : ctx+"/business/order/getSupplierShortName?tmp="+Math.random(),
 							onSelect:function(node){
 								$('#search_supplierShortName').val($("#"+reimSearch.config.supplierShortName).formSelect("getValue")[0]);
 							},
 							width : "244"
 					};
 					supplierShortName.formSelect(optionCompPosType);
 					$('.uicSelectData').height(150);
		        },
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =reimSearch.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		reimSearch.doExecute('initDocument');
	}
});