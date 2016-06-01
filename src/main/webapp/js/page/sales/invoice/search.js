define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("formSelect");
	require("formUser");
	require("formTree");
	var StringBuffer = require("stringBuffer");
	
	require("js/plugins/meta/js/metaDropdowmSelect");
	var searchManage = {
			config: {
				module: 'searchManage',
				creator:'creator',
	        },
	        methods :{
	        	initDocument : function(){
	        		if($('#flowStep').val() != 'show1'){
		        		$('#_userName_ul').hide();
		        	}
	        		searchManage.doExecute('customerManage');
	        		searchManage.doExecute('getOrgTree');
		        },
		        customerManage:function(){
		        	//$("#search_creatorUser").empty();
		        	var $fieldStaff = $("#search_creatorUser");
		        	var orgId = "3,4,9";
		        	var orgTreeInput = $("#orgTreeInput").val();
		        	if(orgTreeInput != "" && orgTreeInput != null){
		        		orgId = orgTreeInput;
		        	}
	        		// 选人组件
	        		var optionss = {
	        			inputName : "staffValues",
	        			// labelName : "选择用户",
	        			showLabel : false,
	        			width : "224",
	        			name : "code",
	        			value : "root",
	        			tree_url:ctx+'/staff/getAllStaffByOrgs?random=1',
	        			search_url:ctx+'/staff/searchAllStaff',
	        			selectUser : false,
	        			radioStructure : true
	        		}
	        		optionss.addparams = [ {
	        			name : "orgs",
	        			//三个销售部门ID
	        			value : orgId
	        		} ];
	        		$fieldStaff.formUser(optionss);
	        		
		        },
		        
		        getOrgTree:function(){
		        	$("#orgTree").empty();
		        	var orgTree = $("#orgTree");
		     		var optionsOrg = {
		     				animate : true,
		     				width:"220",
		     				searchTree : true,
		     				tree_url : ctx+'/formTree/getTreeOrg?random=1',// 顶层
		     				asyncUrl : ctx+'/formTree/getTreeOrg?random=1',// 异步
		     				search_url : ctx+'/formTree/searchTreeOrg?random=1',// 搜索
		     				find_url : ctx+'/formTree/getTreeOrg?random=1',// 精确定位
		     				url : '',
		     				asyncParam : ["id"],
		     				addparams : [{
		     							name : "id",
		     							value : "4,9,3"
		     						}],
		     				async : true,
		                    inputChange:function(){
		     					var orgId = $("#orgTree").formTree("getValue");
		     					$("#orgTreeInput").val(orgId);
		     					
		     					//searchManage.doExecute('customerManage');
		     				},
		     				clearFunction:function(){
		     					var orgId = $("#orgTree").formTree("getValue");
		     					$("#orgTreeInput").val(orgId);
		     					//searchManage.doExecute('customerManage');
		     				}
		     			};
		     		
		     	 	orgTree.formTree(optionsOrg);
		     	 	$(".uicSelectDataTree").css("z-index","300");
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