define(function(require, exports, module) {
	var $ = require("jquery");
	require("coverage");
	require("internetTable");
	require("uic/message_dialog");
	reload = function(){
		$("#uicTable").tableOptions({
    		pageNo : '1',
    		addparams:[]
    	});
    	$("#uicTable").tableReload();
    	$("#uicTable2").tableOptions({
    		pageNo : '1',
    		addparams:[]
    	});
    	$("#uicTable2").tableReload();
	}
	var costTaskManage = {
			config: {
				module: 'costTaskManage',
	        },
	        methods :{
	        	initDocument : function(){
	        		costTaskManage.doExecute("initTable");
	        		 $("#payment").bind('click',function(){
		        			$("#reimburse").removeClass("sel");
		        			$("#payment").addClass("sel");
	        			 $('#uicTable2').tableOptions({
								pageNo : '1',
								addparams :[]
							}).tableReload();
	        			 costTaskManage.doExecute('initTable');
			         });
	        		 
	        		$("#reimburse").bind('click',function(){
	        			 $("#payment").removeClass("sel");
	        			 $("#reimburse").addClass("sel");
	        			$('#uicTable').tableOptions({
	        				pageNo : '1',
	        				addparams : []
	        			}).tableReload();
	        			costTaskManage.doExecute('initTable2');
	        		});
	    			/*
        			 *键盘点击事件
        			 */
        			document.onkeydown = function(event) {
        				var e = event || window.event || arguments.callee.caller.arguments[0];
        				if (e && e.keyCode == 13) { // enter 键
        					if($("#uicTable").is(":visible")==true){
        						costTaskManage.doExecute('doAdvancedFun');
        					}else{
        						costTaskManage.doExecute('doAdvancedFun2');
        					}
        		  			
        				}

        			}
		        },
		        initTable : function(){
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	$("#uicTable2").hide();
		        	$("#uicTable").show();
		        	var grid=$("#uicTable").uicTable({
		        		title : "",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		buttons : [
		        			],
	    			    columns:[
	    			        { code: 'payApplyName',name : '商务合同付款名称',width:'20%',process :costTaskManage.methods.links},
		        			{ code:'PayAmonut', name :'<span class="icon-money" style="font-size:20px;float:left;margin-top:5px;"></span>付款金额',width:'15%',process :costTaskManage.methods.totalAmount},
		        			{ code: 'creator',name : '创建人',width:'10%'},
		        			{ code: 'createTime',name : '创建时间',width:'15%',sort : true},
		        			{code : 'prev_handler_',name : '最后处理',width : '16%',process:costTaskManage.methods.dealinfo}
		        			
			        ],
			        url: ctx+'/business/payOrder/costTaskList?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:13,
			        moreCellShow : false,
			        htmlSort : false,
			        showcheckbox : false,
			        sortname : 'createTime',
					sortorder : 'desc',
			        onLoadFinish:function(){
			        	$('a[name="_supplierName"]').unbind('click').click(function () {
							var id =this.id;
							var opts = {};
			    			opts.id = id;
			    			opts.url = "/detail";
			    			costTaskManage.doExecute("openurl",opts);
						});
			        	$(".advancedSearch").show();
			        },
			        searchFun:function(){
			        	$("#uicTable").tableOptions({
			        		pageNo : '1',
			        		addparams:[{
			        			name:"test",
			        			value:$('.searchs').find('input').first().val()
			        		}]
			        	});
			        	$("#"+costTaskManage.config.uicTable).tableReload();
			        },
			        advancedFun:costTaskManage.methods.advancedFunction
			        });
		        	$(".advancedSearch").show();

		        	$(".advancedSearch",$("#uicTable")).load(ctx+'/business/payOrder/taskSearch?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			costTaskManage.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			costTaskManage.doExecute('reloadTable');
		        		});
		        	});
		        	$(".advancedSearch",$("#uicTable")).css("height","auto");
		        },
		        initTable2 : function(){
		        	$("#uicTable").hide();
		        	$("#uicTable2").show();
		        //	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#uicTable2").uicTable({
		        		title : "",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		buttons : [
		        			],
	    			    columns:[
	    			        [{ code: 'ReimbursementName',name : '商务合同报销名称',width:'15%',process :costTaskManage.methods.links2}],
		        			[{ code:'amount', name :'<span class="icon-money" style="font-size:20px;float:left;margin-top:5px;"></span>报销金额',width:'15%',process :costTaskManage.methods.totalAmount}],
		        			[{ code: 'creator',name : '创建人',width:'10%'}],
		        			[{ code: 'createTime',name : '创建时间',width:'15%',sort : true}],
		        			[{code : 'prev_handler_',name : '最后处理',width : '16%',process:costTaskManage.methods.dealinfo2}]
					       	 
	    			    
		        			
			        ],
			        url: ctx+'/business/rbmApply/costTaskList?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:13,
			        moreCellShow : true,
			        htmlSort : false,
			        showcheckbox : false,
			        sortname : 'createTime',
					sortorder : 'desc',
			        onLoadFinish:function(){/*
			        	$('a[name="_supplierName"]').unbind('click').click(function () {
							var id =this.id;
							var opts = {};
			    			opts.id = id;
			    			opts.url = "/detail";
			    			costTaskManage.doExecute("openurl",opts);
						});
			        	$(".advancedSearch").show();
			        */},
			        searchFun:function(){
			        	$("#"+config.uicTable).tableOptions({
			        		pageNo : '1',
			        		addparams:[{
			        			name:"test",
			        			value:$('.searchs').find('input').first().val()
			        		}]
			        	});
			        	$("#"+pay.config.uicTable).tableReload();
			        },
			        advancedFun:costTaskManage.methods.advancedFunction1
			        });
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	$('.doAdvancedSearch').hide();
		        	$('.doAdvancedSearch').click();
		        	$('.operation').hide();
		        	$(".advancedSearch",$("#uicTable2")).load(ctx+'/business/rbmApply/reimSearch?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn2").unbind('click').click(function () {
		        			costTaskManage.doExecute('doAdvancedFun2');
			        	});
		        		$("#reset_btn2").unbind('click').click(function () {
		        			costTaskManage.doExecute('reloadTable2');
		        		});
		        	});
		        	$(".advancedSearch",$("#uicTable2")).css("height","auto");
		        },

		        totalAmount:function(divInner, trid, row, count){
		        	if(divInner == null||divInner == ""){
		        		divInner = 0;
		        	}
		        	
		        	if(row.currency=='usd'){
			        	return "$"+costTaskManage.methods.fmoney(divInner,2);
		        	}else{
			        	return "￥"+costTaskManage.methods.fmoney(divInner,2);
		        	}

		        },
		    		
		        links : function(divInner, trid, row, count) {
					var title = null;
					if (row.payApplyName == undefined) {
						title = '无标题';
					} else {
						title = row.payApplyName;
					}
					return '<a href="flow/procinst/view?procInstId=' + row.procInstId + '&taskId=' + row.taskId + '" target="_blank"><font class="blue02"><b>'
							+ title + '</b></font></a>';							
				},
				links2 : function(divInner, trid, row, count) {
					var title = null;
					if (row.ReimbursementName == undefined) {
						title = '无标题';
					} else {
						title = row.ReimbursementName;
					}
					return '<a href="flow/procinst/view?procInstId=' + row.procInstId + '&taskId=' + row.taskId + '" target="_blank"><font class="blue02"><b>'
							+ title + '</b></font></a>';							
				},
				dealinfo:function(divInner, trid, row, count){
					 var showinfo = "";
					 showinfo = row.prev_handler_+'('+row.taskCreateTime+')';
					 return showinfo;
				},
				dealinfo2:function(divInner, trid, row, count){
					 var showinfo = "";
					 showinfo = row.prev_handler_+'('+row.taskCreateTime+')';
					 return showinfo;
				},
				advancedFunction1:function(type) {
		        	if(type){
		        		$(".advancedSearch",$("#"+pay.config.uicTable)).css("height","auto");
		        		//绑定“查询”按钮
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			pay.methods.doAdvancedFun();
			        	});
		        		//绑定“重置”按钮
		        		$("#reset_btn").unbind('click').click(function () {
		        			pay.doExecute('reload1');
		        		});
		        	} else {
		        	}
		        },
		        advancedFunction2:function(type) {
		        	if(type){
		        		$(".advancedSearch",$("#"+pay.config.uicTable)).css("height","auto");
		        		//绑定“查询”按钮
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			pay.methods.doAdvancedFun2();
			        	});
		        		//绑定“重置”按钮
		        		$("#reset_btn").unbind('click').click(function () {
		        			pay.doExecute('reload2');
		        		});
		        	} else {
		        	}
		        },
				doAdvancedFun:function(){
					var payApplyName = $("#search_payApplyName").val();
					var payAmount = $("#search_payAmount").val();
					var planPayDate = $("#search_planPayDate").val();
					
					$("#uicTable").tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"payApplyName",
		        			value:payApplyName
		        		},{
		        			name:"payAmount",
		        			value:payAmount
		        		},{
		        			name:"planPayDate",
		        			value:planPayDate
		        		}]
		        	});
		        	$("#uicTable").tableReload();
					
				},
				doAdvancedFun2:function(){
					var reimbursementName = $("#search_reimbursementName").val();
					var amount = $("#search_amount").val();
					
					$("#uicTable2").tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"reimbursementName",
		        			value:reimbursementName
		        		},{
		        			name:"amount",
		        			value:amount
		        		}]
		        	});
		        	$("#uicTable2").tableReload();
					
				},
				reloadTable:function(){
					$("#search_payApplyName").val("");
					$("#search_payAmount").val("");
					$("#search_planPayDate").val("");
					
					$("#uicTable").tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#uicTable").tableReload();
				},
				reloadTable2:function(){
					$("#search_reimbursementName").val("");
					$("#search_amount").val("");
					
					$("#uicTable2").tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#uicTable2").tableReload();
				},
				fmoney:function (s, n) {
					n = n > 0 && n <= 20 ? n : 2; 
					s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
					var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
					t = ""; 
					for (i = 0; i < l.length; i++) { 
					t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
					} 
					return t.split("").reverse().join("") + "." + r; 
				},
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = "business/payOrder/costTaskManage" + opts.url;
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
				var method =costTaskManage.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		costTaskManage.doExecute('initDocument');
	}
	
});