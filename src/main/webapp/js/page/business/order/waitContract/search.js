define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("formSelect");
	require("formUser");
	require("bootstrap-datetimepicker");
	var searchManage = {
			config: {
				module: 'searchManage',
				creator:'creator',
				contractType:'contractType',
				contractState:'contractState',
	        },
	        methods :{
	        	initDocument : function(){
	        		 $('.date').datetimepicker({
	        		    	pickTime: false
     		    });
	        		//$('#search_creator').empty();
		        	var $fieldStaff = $("#search_creator");
	        		// 选人组件
	        		var optionss = {
	        			inputName : "staffValues",
	        			// labelName : "选择用户",
	        			showLabel : false,
	        			width : "224",
	        			name : "code",
	        			value : "root",
	        			tree_url:ctx+'/staff/getStaffByOrgs?random=1',
	        			selectUser : false,
	        			radioStructure : true
	        		}
	        		optionss.addparams = [ {
	        			name : "orgs",
	        			//三个销售部门ID
	        			value : "3,4,9"
	        		} ];
	        		$fieldStaff.formUser(optionss);
	        		if($('#flowStep').val() != 'show1'){
		        		$('#_userName_ul').hide();
		        	}
	        		
	        		searchManage.doExecute('getcontractState');
	        		searchManage.doExecute('getcontractType');
	        		 //加载行业下拉框
	        		searchManage.doExecute('getCustomerInfo');
		        },
	        	//客户选择下拉框
	        	getCustomerInfo:function(){
	        		var url = ctx+"/base/customermanage/customerInfo/getCustomerInfo?tmp="+Math.random();
	        		var customerInfo = $("#customerInfo");
	        		customerInfo.addClass("li_form");
	        		var optionCustomerInfo = {
	        				inputName : "customer",
	        				writeType : 'show',
	        				showLabel : false,
	        				url : url,
	        				onSelect:function(node){
	        					var id=$("#customerInfo").formSelect("getValue")[0];
	        					$("#search_customerInfo").val(id);
	        				},
	        				width : "222"
	        		};
	        		customerInfo.formSelect(optionCustomerInfo);
	        		$('.uicSelectData').height(100);
	        	},
		        
		        getcontractState : function(){
		        	var contractState = $("#"+searchManage.config.contractState);
					contractState.addClass("li_form");
					var optionCompPosType = {
						writeType : 'show',
						showLabel : false,
						code : searchManage.config.contractState,
						onSelect:function(node){
							$('#search_contractState').val($("#"+searchManage.config.contractState).formSelect("getValue")[0]);
						},
						width : "222"
					};
					contractState.formSelect(optionCompPosType);
					$('.uicSelectData').height(100);
		        },
					
		        getcontractType : function(){
		        	var contractType = $("#"+searchManage.config.contractType);
					contractType.addClass("li_form");
					var optionCompPosType = {
						writeType : 'show',
						showLabel : false,
						code : searchManage.config.contractType,
						onSelect:function(node){
							$('#search_contractType').val($("#"+searchManage.config.contractType).formSelect("getValue")[0]);
						},
						width : "222"
					};
					contractType.formSelect(optionCompPosType);
					$('.uicSelectData').height(100);
		        },
					
		        goBack : function(m){
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =searchManage.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		searchManage.doExecute('initDocument');
	}
});