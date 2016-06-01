define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("formSelect");
	var supplierInfoSearch = {
			config: {
				module: 'supplierInfoSearch',
            	supplierType:'supplierType',
            	supplierGrade:'supplierGrade',
            	relationShip:'relationShip',
            	bizOwner:'bizOwner',
            	relationGrade:'relationGrade'
	        },
	        methods :{
	        	initDocument : function(){
		        	supplierInfoSearch.doExecute('getSupplierType');
		        },
		        getSupplierType : function(){
		        	var $fieldCompPosType = $("#"+supplierInfoSearch.config.supplierType);
					$fieldCompPosType.addClass("li_form");
					var optionCompPosType = {
						writeType : 'show',
						showLabel : false,
						code : supplierInfoSearch.config.supplierType,
						onSelect:function(node){
							$('#search_supplierType').val($("#"+supplierInfoSearch.config.supplierType).formSelect("getValue")[0]);
						},
						width : "280"
					};
					$fieldCompPosType.formSelect(optionCompPosType);
					
					var bizOwner = $("#"+supplierInfoSearch.config.bizOwner);
					bizOwner.addClass("li_form");
					var optionCompPosType = {
							writeType : 'show',
							showLabel : false,
							url : ctx+"/business/supplierm/supplierInfo/getBizOwner?tmp="+Math.random(),
							onSelect:function(node){
								$('#search_bizOwner').val($("#"+supplierInfoSearch.config.bizOwner).formSelect("getValue")[0]);
							},
							width : "280"
					};
					bizOwner.formSelect(optionCompPosType);
		        },
		        goBack : function(m){
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =supplierInfoSearch.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		supplierInfoSearch.doExecute('initDocument');
	}
});