define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("formUser");
	require("formTree");
	var specilSearch={
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
	        		 
	        		 var $fieldStaff = $("#search_creator2");
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
		specilSearch.methods.initDocument();  
	}
});