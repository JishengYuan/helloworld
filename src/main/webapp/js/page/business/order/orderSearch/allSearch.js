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
				creator:'creator',
            	relationShip:'relationShip',
            	purchaseType:'purchaseType',
            	orderStatus:'orderStatus',
            	supplierShortName:'supplierShortName',
            	payStatus:'payStatus',
            	reimStatus:'reimStatus',
            	
	        },
	        methods :{
	        	initDocument : function(){
	        		orderSearch.doExecute('getorderType');
	        		$('.date').datetimepicker({
        		    	pickTime: false
	        		});
		        },
		        getorderType : function(){
		        	var $fieldCompPosType = $("#"+orderSearch.config.orderType);
					$fieldCompPosType.addClass("li_form");
					var optionCompPosType = {
						writeType : 'show',
						showLabel : false,
						code : orderSearch.config.orderType,
						onSelect:function(node){
							$('#search_orderType').val($("#"+orderSearch.config.orderType).formSelect("getValue")[0]);
						},
						width : "244"
					};
					$fieldCompPosType.formSelect(optionCompPosType);
					
					var $fieldCompPosType = $("#"+orderSearch.config.purchaseType);
					$fieldCompPosType.addClass("li_form");
					var optionCompPosType = {
						writeType : 'show',
						showLabel : false,
						code : orderSearch.config.purchaseType,
						onSelect:function(node){
							$('#search_purchaseType').val($("#"+orderSearch.config.purchaseType).formSelect("getValue")[0]);
						},
						width : "244"
					};
					$fieldCompPosType.formSelect(optionCompPosType);
					
					var $fieldCompPosType = $("#"+orderSearch.config.reimStatus);
					$fieldCompPosType.addClass("li_form");
					var optionCompPosType = {
						writeType : 'show',
						showLabel : false,
						code : orderSearch.config.reimStatus,
						onSelect:function(node){
							$('#search_reimStatus').val($("#"+orderSearch.config.reimStatus).formSelect("getValue")[0]);
						},
						width : "244"
					};
					$fieldCompPosType.formSelect(optionCompPosType);
					$('.uicSelectData').height(100);
					
					var $fieldCompPosType = $("#"+orderSearch.config.payStatus);
					$fieldCompPosType.addClass("li_form");
					var optionCompPosType = {
						writeType : 'show',
						showLabel : false,
						code : orderSearch.config.payStatus,
						onSelect:function(node){
							$('#search_payStatus').val($("#"+orderSearch.config.payStatus).formSelect("getValue")[0]);
						},
						width : "244"
					};
					$fieldCompPosType.formSelect(optionCompPosType);
					$('.uicSelectData').height(100);
					
					var creator = $("#"+orderSearch.config.creator);
					creator.addClass("li_form");
					var optionCompPosType = {
							writeType : 'show',
							showLabel : false,
							url : ctx+"/business/order/getOrderCreator?tmp="+Math.random(),
							onSelect:function(node){
								$('#search_creator').val($("#"+orderSearch.config.creator).formSelect("getValue")[0]);
							},
							width : "244"
					};
					creator.formSelect(optionCompPosType);
					$('.uicSelectData').height(150);
					
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
					$('.uicSelectData').height(100);
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