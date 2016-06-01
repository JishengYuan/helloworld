define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("formSelect");
	require("bootstrap-datetimepicker");
	var orderSearch = {
			config: {
				module: 'orderSearch',
				orderType:'orderType',
            	supplierShortName:'supplierShortName',
            	
	        },
	        methods :{
	        	initDocument : function(){
	        		orderSearch.doExecute('getorderType');
	        		$('.date').datetimepicker({
        		    	pickTime: false
	        		});
		        },
		        getorderType : function(){
					var supplierShortName=$("#"+orderSearch.config.supplierShortName);
					supplierShortName.addClass("li_form");
					var optionCompPosType = {
							writeType : 'show',
							showLabel : false,
							url : ctx+"/business/order/getSupplierShortName?tmp="+Math.random(),
							onSelect:function(node){
								$('#search_supplierShortName').val($("#"+orderSearch.config.supplierShortName).formSelect("getValue")[0]);
							},
							width : "244"
					};
					supplierShortName.formSelect(optionCompPosType);
					$('.uicSelectData').height(180);
		        },
		        goBack : function(m){
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =orderSearch.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		orderSearch.doExecute('initDocument');
	}
});