define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
	require("formSelect");
	require("uic/message_dialog");
	require("jquery.form");
	require("formUser");
	var StringBuffer = require("stringBuffer");
	var approveManage = {
			config: {
				module: 'approveManage',
				uicTable : 'uicTable',
	            namespace: '/business/inventory'
	        },
	        methods :{
	        	initDocument : function(){
		        	approveManage.doExecute('customerManage');
		        	approveManage.doExecute('initTable');
		        	$("#advancedSearch_btn").unbind('click').click(function () {
		        		approveManage.doExecute('reload');
		        	});
		        	$("#_sino_eoss_approveManage_import").unbind('click').click(function () {
		        		approveManage.doExecute('importProduct');
		        	});
		        },
		        initTable : function(){
		        	var grid=$("#"+approveManage.config.uicTable).uicTable({
//		        		title : "公司备件->出库入库审批",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		buttons : [
//		        			{name: '导入设备', butclass: 'doAdd',onpress: approveManage.methods.toolbarItem}
		        			],
	    			    columns:[
	    			        { code: 'userName',name:'申请人',width:'16%'},
		        			{ code: 'num',name:'申请编码',width:'16%'},
		        			{ code:'ti', name:'申请时间',width:'16%'},
		        			{ code: 'state',name:'状态',width:'16%',process:approveManage.methods.state},
		        			{ code:'ss', name:'类别',width:'16%',process:approveManage.methods.type},				
		        			{ code:'id',name:'操作',width:'16%',process:approveManage.methods.handler}		        			
			        ],
			        url: ctx+approveManage.config.namespace+'/getApproveList?tmp='+Math.random(),
			        pageNo:1,
			        showcheckbox:false,
			        pageSize:13,
			        onLoadFinish:function(){
			        	$('a[name="_inventory_approve"]').unbind('click').click(function () {
							var id =this.id+"_"+$(this).attr("aid");
							var opts = {};
			    			opts.id = id;
			    			opts.url = "/approvePage";
			    			approveManage.doExecute("openurl",opts);
						});
			        },
			        searchFun:function(){
			        	var name = $("input[class='text']").first().val();
			        	$("#"+approveManage.config.uicTable).tableOptions({
			        		pageNo : '1',
			        		addparams:[{
			        			name:"name",
			        			value:name
			        		}]
			        	});
			        	$("#"+approveManage.config.uicTable).tableReload();
			        }
			        });
		        	$(".doAdvancedSearch").hide();
		        },
		        state:function(divInner, trid, row, count){
		        	if(row.ss == '0'){
		        		if(row.state == 0){
			        		return "在库";
			        	} else if(row.state == 1){
			        		return "待审批";
			        	} else if(divInner == 2){
			        		return "已借出";
			        	} else if(row.state == 3){
			        		return "待归还";
			        	}
		        	} else {
		        		if(row.state == 0){
			        		return "在库";
			        	} else if(row.state == 2){
			        		return "待审批";
			        	} else if(divInner == 3){
			        		return "已归还";
			        	}
		        	}
		        },
		        type:function(divInner, trid, row, count){
		        	if(row.ss == '1'){
		        		return "入库申请";
		        	}
		        	if(row.ss == '0'){
		        		return "出库申请";
		        	}
		        },
		        handler:function(divInner, trid, row, count){
		        	var str = "<a href='#' id='"+row.id+"' name='_inventory_approve' aid='"+row.ss+"'>审批<i class='icon-edit'></i></a> ";
		        	return str;
		        },
		        costFmt:function(divInner, trid, row, count){
		        	var cost = row.cost;
		        	if(cost == ""||cost == null){
						cost = 0;
					}
		        	return approveManage.methods.fmoney(cost, 2);
		        },
		        rentFmt:function(divInner, trid, row, count){
		        	var rent = row.rent;
		        	if(rent == ""||rent == null){
		        		rent = 0;
					}
		        	return approveManage.methods.fmoney(rent, 2);
		        },
		        fmoney:function(s,n){
		        	n = n > 0 && n <= 20 ? n : 2; 
		    		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		    		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
		    		t = ""; 
		    		for (i = 0; i < l.length; i++) { 
		    			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		    		} 
		    		return t.split("").reverse().join("") + "." + r;
		        },
		        toolbarItem:function(com, trGrid){
		        	if (com=='doAdd'){
		        		$("#_sino_eoss_approveManage_import").click();
		    		}
		        },
		        reload:function(){
	        		var userName = $("#search_creator").formUser("getValue");
		        	$("#"+approveManage.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{name:"userName",value:userName}]
		        	});
		        	$("#"+approveManage.config.uicTable).tableReload();
		        },
		        customerManage:function(){
		        	$("#search_creator").empty();
		        	var $fieldStaff = $("#search_creator");
		        	var orgId = "3,4,9,52";
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
	        			value : orgId
	        		} ];
	        		$fieldStaff.formUser(optionss);
		        },
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = "business/inventory" + opts.url;
		    		if(opts.id){
		    			options.keyName = 'id';
		    			options.keyValue = opts.id;
		    		}
		    		$.openurl(options);
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =approveManage.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		approveManage.doExecute('initDocument');
	}
});