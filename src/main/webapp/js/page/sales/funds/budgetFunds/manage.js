define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
	require("confirm_dialog");
	require("uic/message_dialog");
	//var StringBuffer = require("stringBuffer");
	require("changTag");
	var paymentTypeJson = {};
	reload = function(){
		$("#"+fund.config.uicTable).tableOptions({
    		pageNo : '1',
    		addparams:[]
    	});
    	$("#"+fund.config.uicTable).tableReload();
	}
	var fund = {
			config: {
				module: 'fund',
	            //namespace: '/business/order',
	            uicTable : 'processDiv',
	        },
	        
	        methods :{
	        	initDocument : function(){
	        		fund.doExecute('initTable'); 
	        		 /*
	        			 *键盘点击事件
	        			 */
	        			document.onkeydown = function(event) {
	        				var e = event || window.event || arguments.callee.caller.arguments[0];
	        				if (e && e.keyCode == 13) { // enter 键
	        					if($("#processDiv").is(":visible")==true){
	        						fund.doExecute('doAdvancedFun');
	        					}else{
	        						fund.doExecute('doAdvancedFun2');
	        					}
	        		  			
	        				}

	        			}
		        },
		       
		        initTable : function(){
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#processDiv").uicTable({
		        		title : "",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
						searchItems:'name',
	    			    columns:[
	    			        [{ code:'contractName',name :'合同名称',width:'18%'},
	    			         {code:'contractCode',name :'合同编号',width:'18%'}],
		        			[{ code:'contractAmount', name :'合同金额',width:'10%'}],												
		        			[{ code:'supplier', name :'客户',width:'15%'},	
		        			{ code:'contractType', name :'合同类型',width:'10%'}],
		        			[{ code:'createDate', name :'已回款金额',width:'10%']},
		        			[{ code:'remark', name :'已开票金额',width:'15%'}]//,process:payment.methods.paytatus 调用方法
			        ],
			        
			        url: ctx+'/sales/funds/budgetFunds/getAll?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:10,
			        onLoadFinish:function(){
			        	$(".showbody").css("overflow","visible");
			        	$(".advancedSearch").show();
			        },
		         });
		        	$(".advancedSearch").show();
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	$('.doAdvancedSearch').hide();
		        	$('.operation').hide();
		        	$(".advancedSearch",$("#"+payment.config.uicTable)).load(ctx+'/sales/funds/budgetFunds/searchBudgetFunds?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn1").unbind('click').click(function () {
		        			payment.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			$("#contractName").val("");
							$("#budgetDate").val("");
							$("#search_supplierShortName").val("");
							$("#"+payment.config.uicTable).tableOptions({
				        		pageNo : '1',
				        		addparams:[]
				        	});
				        	$("#"+payment.config.uicTable).tableReload();
		        		});
		        		$(".advancedSearch",$("#"+payment.config.uicTable)).css("height","auto");
		        		$("#addContract_btn").bind('click',function(){//跳转到添加预算界面
		        			var options = {};
				    		options.murl = "sales/funds/budgetFunds/add";
				    		$.openurl(options);
		        		 }); 
		        		$("#removeContract_btn").bind('click',function(){//删除
		        			payment.methods.removeOrder();
		        		 });
		        	});
		        },
		        
		        initTable2 : function(){
		        	$("#processDiv").hide();
		        	$("#processDiv3").hide();
		        	$("#processDiv2").show();
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#processDiv2").uicTable({
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
						searchItems:'name',
	    			    columns:[
	    			        { code:'contractName',name : '合同名称',process:payment.methods.handler2,width:'30%'},
		        			{ code:'contractAmount', name :'合同金额',process:payment.methods.payAmountStr2,width:'10%'},	
		        			{ code:'businessCost', name :'商务成本',width:'10%'},
		        			{ code:'receiveAmount', name :'合同回款',width:'10%'},
		        			{ code:'incomeInvoice', name :'进项发票',width:'10%'},	
		        			{ code:'outInvoice', name :'出项发票',width:'10%'},	
		        			{ code:'finalAmount', name :'售后成本',width:'10%'},	
		        			/*{ code:'reimStatus', name :'状态',process:payment.methods.reimStatus,width:'10%'}	*/
			        ],
			        url: ctx+'/sales/fundsSalesContract/getList?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:10,
			        onLoadFinish:function(){
			        	$(".advancedSearch").show();
			        	$(".showbody").css("overflow","visible");
			        },
		         });
		        	$(".advancedSearch").show();
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	$('.doAdvancedSearch').hide();
		        	$('.operation').hide();
		        	$(".advancedSearch",$("#processDiv2")).load(ctx+'/sales/fundsSalesContract/search?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			payment.doExecute('doAdvancedFun2');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			$("#contractName").val("");
							$("#budgetDate").val("");
							$("#search_supplierShortName").val("");
							$("#"+payment.config.uicTable).tableOptions({
				        		pageNo : '1',
				        		addparams:[]
				        	});
				        	$("#"+payment.config.uicTable).tableReload();
		        		});
		        		$(".advancedSearch",$("#"+payment.config.uicTable)).css("height","auto");
		        		
		        		//$(".advancedSearch",$("#processDiv2")).css("height","auto");
		        	});
		        },
		        initTable3 : function(){
		        	$("#processDiv").hide();
		        	$("#processDiv2").hide();
		        	$("#processDiv3").show();
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#processDiv3").uicTable({
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
						searchItems:'name',
	    			    columns:[
	    			        { code:'contractName',name : '来源名称',process:payment.methods.handler2,width:'18%'},
		        			{ code:'budgetReceive', name :'产品类型',process:payment.methods.payAmountStr2,width:'15%'},	
		        			{ code:'budgetInvoice', name :'产品型号',width:'15%'},
		        			{ code:'budgetDate', name :'产品数量',width:'15%'}	
		        			/*{ code:'reimStatus', name :'状态',process:payment.methods.reimStatus,width:'10%'}	*/
			        ],
			        url: ctx+'/sales/fundsSalesContract/getList?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:10,
			        onLoadFinish:function(){
			        	$(".advancedSearch").show();
			        	$(".showbody").css("overflow","visible");
			        },
		         });
		        	$(".advancedSearch").show();
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	$('.doAdvancedSearch').hide();
		        	$('.operation').hide();
		        	$(".advancedSearch",$("#processDiv3")).load(ctx+'/sales/fundsSalesContract/search?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			payment.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			$("#contractName").val("");
							$("#budgetDate").val("");
							$("#search_supplierShortName").val("");
							$("#"+payment.config.uicTable).tableOptions({
				        		pageNo : '1',
				        		addparams:[]
				        	});
				        	$("#"+payment.config.uicTable).tableReload();
		        		});
		        		$(".advancedSearch",$("#"+payment.config.uicTable)).css("height","auto");
		        		/*$("#addContract_btn").bind('click',function(){//跳转到添加预算界面
		        			var options = {};
				    		options.murl = "sales/fundsSalesContract/add";
				    		$.openurl(options);
		        		 }); 
		        		$("#removeContract_btn").bind('click',function(){//删除
		        			payment.methods.removeOrder();
		        		 });*/
		        	});
		        },
		        removeOrder:function(){
		        	var ids=$("#"+payment.config.uicTable).getTableCheckedRows();
		    		if(ids.length==0){
	    				UicDialog.Error("请选择删除条目！");
	    				return;
		    		}
	        		var orderId ='';
		    		for(var i=0;i<ids.length;i++){
		    				orderId +=ids[i].id+",";
		    		}
		    		var datas={'id':orderId};
				    var url = ctx + '/sales/funds/budgetFunds/delBudget?tmp='+Math.random();
				    $.post(url,datas,
				    		function(data,status){
				            	if(status="success"){
				                	  UicDialog.Success("删除合同预算成功!",function(){
				                		  $("#"+payment.config.uicTable).tableReload();
				                	  });
				                  }else{
				                  	  UicDialog.Error("删除合同预算失败！",function(){
				                  		$("#"+payment.config.uicTable).tableReload();
				                  	  });
				                  }
				   });   
		    	},
		        doAdvancedFun:function(){
					var budgetDate = $("#budgetDate").val();//budgetDate
					var contractName=$("#contractName").val();
					
					$("#"+payment.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"contractName",
		        			value:contractName
		        		},{
		        			name:"budgetDate",
		        			value:budgetDate
		        		}]
		        	});
		        	$("#"+payment.config.uicTable).tableReload();
		        },
		        doAdvancedFun2 : function(){
		        	var contractName = $("#search_contractName").val();
					var contractCode = $("#search_contractCode").val();
					var contractAmount = $("#search_contractAmount").val();
					var contractAmountb = $("#search_contractAmountb").val();
					$("#processDiv2").tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"contractName",
		        			value:contractName
		        		},{
		        			name:"contractCode",
		        			value:contractCode
		        		},{
		        			name:"contractAmount",
		        			value:contractAmount
		        		},{
		        			name:"contractAmountb",
		        			value:contractAmountb
		        		}]
		        	});
		        	$("#processDiv2").tableReload();
		        },
	},
	        /**
	         * 执行方法操作
	         */
	        doExecute:function(flag, param) {
	        	var method =payment.methods[flag];
	        	if( typeof method === 'function') {
	        		return method(param);
	        	} else {
	        		alert('操作 ' + flag + ' 暂未实现！');
	        	}
	        }
	}
	exports.init = function() {
		payment.doExecute('initDocument');
	}
	/*//子页面关闭刷新该父页面
	childColseRefresh=function(){
		payment.doExecute('initDocument');
    }
*/

});