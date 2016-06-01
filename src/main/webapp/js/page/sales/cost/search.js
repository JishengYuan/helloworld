define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("formSelect");
	require("formUser");
	require("formTree");
	require("bootstrap-datetimepicker");
	var StringBuffer = require("stringBuffer");
	
	require("js/plugins/meta/js/metaDropdowmSelect");
	var search={
			config: {
	        },
	        methods :{
	        	initDocument:function(){
	        		 $('.date').datetimepicker({
	        				viewMode:1,
	    	        		minViewMode:0,
	    	    	    	pickTime: false,
		        			//format: "yyyy-MM", 
		        			autoclose:1, //选择日期后自动关闭 
		        	});
	        		 
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
		        			value : "3,4,9"
		        		} ];
		        		$fieldStaff.formUser(optionss);
	        		 
	        		 
	        	}
	        }
	        
	}
	exports.init = function(){
		search.methods.initDocument();  
	}
});