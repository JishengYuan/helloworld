define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("internetTable");
	require("uic/message_dialog");
	var StringBuffer = require("stringBuffer");
	var orderTypeJson = {};
	var FinanceContract = {
			config: {
				module: 'FinanceContract',
				uicTable : 'uicTable',
	            namespace: 'sales/manage/finance'
	        },
	        methods :{
	        	initDocument : function(){
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=orderType&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				orderTypeJson = msg;
		    			}
		    		});
	        		FinanceContract.doExecute('initTable');
	        		
	        		/*
        			 *键盘点击事件
        			 */
	        		
        			document.onkeydown = function(event) {
        				var e = event || window.event || arguments.callee.caller.arguments[0];
        				if (e && e.keyCode == 13) { // enter 键
        					FinanceContract.doExecute('reloadTable');
        				}

        			}
	        		
		        },
		        initTable : function(){
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#"+FinanceContract.config.uicTable).uicTable({
		        		title : "财务管理-->往来帐款统计",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
	    			    columns:[
	    			        [{ code: 'supplier',name : '供应商',process:FinanceContract.methods.handsHandler,width:'25%'},
		        			{ code: 'OrderCode',name : '合同号',width:'25%'}],
		        			[{ code: 'OrderType',name : '内容',process:FinanceContract.methods.OrderType,width:'15%'},
		        			 { code: 'OrderAmount',name : '合同金额',process:FinanceContract.methods.orderAmount,width:'15%'}],
		        			[{ code: 'ReceiveAmount',name : '已付款金额',width:'18%'},
		        			 { code:'UnreceiveAmount', name :'未付款金额',width:'18%'}],										
		        			[{ code: 'InvoiceAmount',name : '认证金额',width:'15%'},
		        			 { code:'UninvoiceAmount', name :'未认证金额',width:'15%'}],
		        			 [{ code: 'customer',name : '客户',width:'20%'}]
			        ],
			        url: ctx+"/"+FinanceContract.config.namespace+'/getList?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:13,
			        moreCellShow : true,
			        onLoadFinish:function(){
			        	$('a[name="_contractName"]').unbind('click').click(function () {
			        		$("#selectContract").attr("aid",$(this).attr("aid"));
			        		$("#selectContract").click();
			        	});
			        },
			        searchFun:function(){
			        	$("#"+supplierInfo.config.uicTable).tableOptions({
			        		pageNo : '1',
			        		addparams:[{
			        			name:"test",
			        			value:$('.searchs').find('input').first().val()
			        		}]
			        	});
			        	$("#"+supplierInfo.config.uicTable).tableReload();
			        }
			        });
		        	$(".advancedSearch").show();
		        	$(".advancedSearch",$("#"+FinanceContract.config.uicTable)).load(ctx+"/"+FinanceContract.config.namespace+'/search?tmp='+Math.random(),function(){
		        		$("#selectContract").unbind('click').click(function () {
		        			FinanceContract.doExecute('addFinanceContract');
		        		});
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			FinanceContract.doExecute('reloadTable');
		        		});
		        		$("#removeContract_btn").unbind('click').click(function () {
		        			FinanceContract.doExecute('removeContracts');
		        		});
		        	});
		        	$('.doAdvancedSearch').click();
		        },
		        handsHandler:function(divInner, trid, row, count){
		        	var str = "";
		        	str+='<a style="color:#005EA7;" href="'+ctx+'/sales/manage/finance/getSalesContractListPage?id='+row.id+'" target="_blank" >'+divInner+'</a>';
		        	return str;
		        },
		        removeContracts:function(){
	        		var ids=$("#"+FinanceContract.config.uicTable).getTableCheckedRows();
		    		if(ids.length==0){
	    				UicDialog.Error("请选择删除条目！");
	    				return;
		    		}
	        		var id ='';
		    		for(var i=0;i<ids.length;i++){
		    			id +=ids[i].id+",";
		    		}
		    		var datas={'id':id};
				    var url = ctx+'/sales/manage/finance/remove?tmp='+Math.random();
				    $.post(url,datas,function(data,status){
		            	if(data=="success"){
		                	  UicDialog.Success("删除数据成功!",function(){});
	                		  FinanceContract.doExecute('reloadTable');
		                  }else{
		                  	  UicDialog.Error("删除数据失败！");
		                  	FinanceContract.doExecute('reloadTable');
		                  }
				   });
		        },
		        addFinanceContract:function(){
		        	var id = $("#selectContract").attr("aid");
		        	$("#selectContract").attr("aid","");
		        	$("#_sino_eoss_sales_products_import_div").empty();
					var buffer = new StringBuffer();
					buffer.append('<div id="_sino_eoss_sales_products_import_page" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="_sino_eoss_sales_products_import_page" aria-hidden="true">');
					$("#_sino_eoss_sales_products_import_div").append(buffer.toString());
					var url = ctx+"/"+FinanceContract.config.namespace+"/getSalesContractListPage?id="+id;
					$("#_sino_eoss_sales_products_import_page").load(url,function(){
						$("#_sales_contracts_relate").bind('click').click(function(){
							var datas=$("#_eoss_sales_finance_contracts").serialize();
							var url1 = ctx+"/"+FinanceContract.config.namespace+"/saveOrUpdate";
							$.post(url1,datas,function(data,status){
								if(data=="success"){
									FinanceContract.doExecute('reloadTable');
								}else{
									FinanceContract.doExecute('reloadTable');
								}
       	 	                });
						});
					});
		        },
		        reloadTable:function(){
		        	var contractCode = $('#search_contractCode').val();
		        	var contractAmount = $('#search_contractAmount').val();
		        	var startTime=$('#startTime').val();
		        	var endTime=$('#endTime').val();
		        	var supplierInfo=$('#search_supplierShortName').val();
		        	var orderId = "";
	        		var myCheckbox = $("input[name='isAgree']:checked");
	        		myCheckbox.each(function(i,ele){//循环拼装被选中项的值
	        			if($(ele).val() != 'null'){
	        				orderId = orderId+$(this).val()+",";
	        			}
	        		 });
		        	
		        	$("#"+FinanceContract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[
		        		           {name:"startTime",value:startTime},
		        		           {name:"endTime",value:endTime},
		        		           {name:"contractCode",value:contractCode},
		        		           {name:"supplierInfo",value:supplierInfo},
		        		           {name:"orderId",value:orderId},
		        		           {name:"contractAmount",value:contractAmount}]
		        	});
		        	$("#"+FinanceContract.config.uicTable).tableReload();
		        },
		        toolbarItem:function(com, trGrid){
		        	if (com=='doAdd'){
		    		}
		        	if (com=='doMod'){
		        	}
		        	if (com=='doDel'){
		        	}
		        },
		      	
		        OrderType : function(divInner, trid, row, count){
		        	var orderType = "";
					for(var i in orderTypeJson){
						if(row.OrderType == orderTypeJson[i].id){
							orderType += orderTypeJson[i].name;
						}
					}
					return orderType;
		        },
		        orderAmount:function(divInner, trid, row, count){
		        	if(divInner!=""){
			        	return "￥"+fmoney(divInner,2);
		        	}else{
		        		return "无";
		        	}
		        },
		        
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = ctx+FinanceContract.config.namespace + opts.url;
		    		if(opts.id){
		    			options.keyName = 'id';
		    			options.keyValue = opts.id;
		    		}
		    		window.open(options.murl);
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =FinanceContract.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		FinanceContract.doExecute('initDocument');
	}
	//子页面关闭刷新该父页面
	childColseRefresh=function(){
		//order.doExecute('initDocument');
		$("#uicTable").tableOptions({
			pageNo : '1',
			addparams:[]
			});
			$("#uicTable").tableReload();
    }
	function fmoney(s, n) { 
		n = n > 0 && n <= 20 ? n : 2; 
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
		t = ""; 
		for (i = 0; i < l.length; i++) { 
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		} 
		return t.split("").reverse().join("") + "." + r; 
	} 
});