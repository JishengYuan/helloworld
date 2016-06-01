define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("formSelect");
	require("formUser");
	$= require("easyui");
	require("internetTable");
	require("confirm_dialog");
	require("uic/message_dialog");
	//var StringBuffer = require("stringBuffer");
	require("changTag");
	require("json2");
	require("js/common/form/data_grid");
	require("uic_Romerselect");
	
	var contract = {
			config: {
				module: 'contract',
				uicTable : 'uicTable', 
	        },
	        methods :{
	        	initDocument : function(){
	        		 $('.date').datetimepicker({
	        		    	pickTime: false
	        		 });
	        		$("#add").unbind('click').click(function () {
	        			$.ajax({
			    			type : "POST",
			    			//async : false,
			    			dataType : "json",
			    			data:$('#addForm').serialize(),
			    			url : ctx + "/sales/funds/budgetFunds/addBudget?tmp="+ Math.random(),
			    			success : function(data) {
			    				alert(data);
			    			}
			    		});
	        			$("#edit_list").empty();
	        			$("#edit_list").load(ctx+"/sales/funds/budgetFunds/manage");
	        		});
	        		$("#back").unbind('click').click(function () {//返回
	        			$("#edit_list").empty();
	        			$("#edit_list").load(ctx+"/sales/funds/budgetFunds/manage");
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
		//收款人，控件
		$("#_customerManager").empty();
		var $fieldStaff = $("#_customerManager");
		var optionss = {
			inputName : "staffValues",
			showLabel : false,
			width : "280",
			name : "code",
			value : "root",
			selectUser : false,
			radioStructure : true
		}
		optionss.addparams = [ {
			name : "code",
			value : "root"
		} ];
		optionss.resIds = $("#userId").attr("value");
		optionss.inputValue = $("#userName").attr("value");
		$fieldStaff.formUser(optionss);
		
		//行业名称
		getContractName();
	}
	
	function getContractName(){ 
		//行业名称
		$("#contractName").empty();
		var contractName = $("#contractName");
		contractName.addClass("li_form");
		var optionCompPosType = {
				writeType : 'show',
				showLabel : false,
				required:true,
				url : ctx + "/sales/funds/budgetFunds/searchContract?tmp="+ Math.random(),
				onSelect:function(node){
					var id = $("#contractName").formSelect("getValue")[0];
					$('#contractName').val(id);
					getContractCode(id);//获得合同编码
				},
				width : "280"
		};
		contractName.formSelect(optionCompPosType);
		contractName.formSelect('setValue',$('#contractName').val());
		$(".uicSelectData").height(300);
	}
	
	//获得合同编码
	function getContractCode(id){ 
		if(id!=null && id!=""){
			$.ajax({
    			type : "POST",
    			//async : false,
    			dataType : "json",
    			data:'',
    			url : ctx + "/sales/funds/budgetFunds/getSaleContractById?id="+id+"&tmp="+ Math.random(),
    			success : function(data) {
    				$("#contractCode").attr("value",data.contractCode);
    			}
    		});
		}
	}

  
});