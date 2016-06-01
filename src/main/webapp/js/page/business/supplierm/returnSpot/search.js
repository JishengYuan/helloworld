define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("formSelect");
	var returnSpotSearch = {
			config: {
				module: 'returnSpotSearch',
            	supplierType:'supplierType',
            	supplierGrade:'supplierGrade',
            	relationShip:'relationShip',
            	bizOwner:'bizOwner',
            	relationGrade:'relationGrade'
	        },
	        methods :{
	        	initDocument : function(){
		        	returnSpotSearch.doExecute('getSupplierType');
		        },
		        getSupplierType : function(){
		        	var $fieldCompPosType = $("#"+returnSpotSearch.config.supplierType);
					$fieldCompPosType.addClass("li_form");
					var optionCompPosType = {
						writeType : 'show',
						showLabel : false,
						code : returnSpotSearch.config.supplierType,
						onSelect:function(node){
							$('#search_supplierType').val($("#"+returnSpotSearch.config.supplierType).formSelect("getValue")[0]);
						},
						width : "280"
					};
					$fieldCompPosType.formSelect(optionCompPosType);
					
		        },
		        goBack : function(m){
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =returnSpotSearch.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		returnSpotSearch.doExecute('initDocument');
	}
});