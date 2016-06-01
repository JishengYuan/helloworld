define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
	require("confirm_dialog");
	require("uic/message_dialog");
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
	            uicTable : 'uicTable',
	        },
	        
	        methods :{
	        	initDocument : function(){
	        		 $('.date').datetimepicker({
	        		    	pickTime: false
	        		 });
	        		fund.doExecute('getStatist','week');
	        		 $("#week").bind('click',function(){
	        			 $(".statist").removeClass("sel");
	        			 $("#week").addClass("sel");
	        			 fund.doExecute('getStatist','week');
			         });
	        		 $("#month").bind('click',function(){
	        			 $(".statist").removeClass("sel");
	        			 $("#month").addClass("sel");
	        			 fund.doExecute('getStatist','month');
			         });
	        		 $("#total").bind('click',function(){
	        			 $(".statist").removeClass("sel");
	        			 $("#total").addClass("sel");
	        			 fund.doExecute('getStatist','total');
			         });
	        		//fund.doExecute('initTable'); 
	        		fund.doExecute('hisRecieveTable');
	        		fund.doExecute('hisInvoiceTable');
	        		$("#addContract_btn").bind('click',function(){//跳转到添加预算界面
	        			var frameSrc = ctx+"/sales/funds/budgetFunds/addRecieveDialog"; 
	    	    		$('#dailogs1').on('show', function () {
	    	    			$('#dtitle').html("添加回款预测");
	    	    		     $('#dialogbody').load(frameSrc); 
	    	    			     $("#save_recieve").unbind('click');
	    	    			     $('#save_recieve').click(function () {
	    	    			    	 var id=$("#_Recieve_id").val();
	    	    			        	var recieve=$("#recieve_amount").val();
	    	    			        	var time=$("#add_budgetDate").val();
	    	    			       	     var url = ctx+'/sales/funds/budgetFunds/doRecieve?id='+id+'&recieveAmount='+recieve+'&recieveTime='+time+'&tmp='+Math.random();
	    	    			       	     var options = {};
	    	    			       	     options.formId = "_sino_eoss_cuotomer_addform";
	    	    			       	     if($.formSubmit.doHandler(options)){
	    	    			       	    	 //按钮不可用
	    	    			       	    	 $.post(url,function(data,status){
	    	    	       	 	            	if(data.date=="success"){
	    	    	       	 	            		$('#dailogs1').modal('hide');
	    	    	       	 	            	$("#hisRecieveTable").tableOptions({
	    	    	       			        		pageNo : '1',
	    	    	       			        		addparams:[{
	    	    	       			        		}]
	    	    	       			        	});
	    	    	       	 	            	$("#hisRecieveTable").tableReload();
	    	    	       	 	                }else{
	    	    	       	 	                	UicDialog.Error(data.msg);
	    	    	       	 	                }
	    	    			       	 	     }); 
	    	    			       	     }
	    	    			     });
	    	    					
	    	   		 });
	    	    		$('#dailogs1').on('hidden', function () {$('#dailogs1').unbind("show");});
    	   				$('#dailogs1').modal({show:true});
    	   				$('#dailogs1').off('show');
   	 	            	
	        		 });
	        		$("#removeContract_btn").bind('click',function(){//删除
	        			var frameSrc = ctx+"/sales/funds/budgetFunds/addInvoiceDialog"; 
	    	    		$('#dailogs2').on('show', function () {
	    	    			$('#dtitle_invoice').html("添加发票预测");
	    	    		     $('#dialogbody_invoice').load(frameSrc); 
	    	    			     $("#save_invoice").unbind('click');
	    	    			     $('#save_invoice').click(function () {
	    	    			    	 var id=$("#_invoice_id").val();
	    	    			        	var recieve=$("#invoice_amount").val();
	    	    			        	var time=$("#add_invoiceDate").val();
	    	    			       	     var url = ctx+'/sales/funds/budgetFunds/doInvoice?id='+id+'&invoiceAmount='+recieve+'&invoiceTime='+time+'&tmp='+Math.random();
	    	    			       	     var options = {};
	    	    			       	     options.formId = "_sino_eoss_invoice_addform";
	    	    			       	     if($.formSubmit.doHandler(options)){
	    	    			       	    	 //按钮不可用
	    	    			       	    	 $.post(url,function(data,status){
	    	    	       	 	            	if(data.date=="success"){
	    	    	       	 	            		$('#dailogs2').modal('hide');
	    	    	       	 	            	$("#hisInvoiceTable").tableOptions({
	    	    	       			        		pageNo : '1',
	    	    	       			        		addparams:[{
	    	    	       			        		}]
	    	    	       			        	});
	    	    	       	 	            	$("#hisInvoiceTable").tableReload();
	    	    	       	 	                }else{
	    	    	       	 	                	UicDialog.Error(data.msg);
	    	    	       	 	                }
	    	    			       	 	     }); 
	    	    			       	     }
	    	    			     });
	    	    					
	    	   		 });
	    	    		$('#dailogs2').on('hidden', function () {$('#dailogs2').unbind("show");});
		   				$('#dailogs2').modal({show:true});
		   				$('#dailogs2').off('show');
		 	            	
	        		 });
	        		
		        },
		        getStatist:function(str){
		        	var url=ctx+"/sales/funds/budgetFunds/getStatist?time="+str+"&tmp="+Math.random();
				     $.post(url,
				 	            function(data,status){
				 	            	if(status=="success"){
				 	            		var str="";
				 	            		str="<div><div style='float: left; width: 500px;font-size: 14px;'>"+data.time+"实际回款总金额："+data.realRecive+"</div><div style='font-size: 14px;'>"+data.time+"预测回款总金额："+data.budRecive+"</div></div> ";
				 	            		str+="<div><div style='float: left; width: 500px;font-size: 14px;'>"+data.time+"实际开票总金额："+data.realInvioce+"</div><div style='font-size: 14px;'>"+data.time+"预测开票总金额："+data.budInvioce+"</div></div> ";
				 	            		$("#week_statistics").html(str);
				 	                  }else{
				 	                		// UicDialog.Error("错误!"+data.message);
				 	                  }
				 	                },"json"); 
		        },
		        
		        initTable : function(){
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#uicTable").uicTable({
		        		title : "",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
	    			    columns:[
	    			        [{ code:'contractName',name :'合同名称',width:'18%'},
	    			         {code:'contractCode',name :'合同编号',width:'18%'}],
		        			[{ code:'contractAmount', name :'合同金额',width:'10%',process:fund.methods.contractStr}],												
		        			[{ code:'shortName', name :'客户',width:'15%'}],	
		        			[{ code:'receive', name :'已回款金额',width:'10%',process:fund.methods.receiveStr}],
		        			[{ code:'invoice', name :'已开票金额',width:'15%',process:fund.methods.invoiceStr}]
			        ],
			        url: ctx+'/sales/funds/budgetFunds/getAll?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:8,
			        moreCellShow : true,
		        	});
		        	$(".advancedSearch").show();
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	$('.doAdvancedSearch').hide();
		        	$('.operation').hide();
		        	$(".advancedSearch",$("#"+fund.config.uicTable)).load(ctx+'/sales/funds/budgetFunds/searchBudgetFunds?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn1").unbind('click').click(function () {
		        			fund.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			$("#contractName").val("");
		        			$('#search_customerInfo').val("");
				        	$('#customerInfo_input').val("");
							$("#"+fund.config.uicTable).tableOptions({
				        		pageNo : '1',
				        		addparams:[]
				        	});
				        	$("#"+fund.config.uicTable).tableReload();
		        		});
		        		$(".advancedSearch",$("#"+fund.config.uicTable)).css("height","auto");
		        		$("#addContract_btn").bind('click',function(){//跳转到添加预算界面
		        			var ids=$("#"+fund.config.uicTable).getTableCheckedRows();
				    		if(ids.length==0||ids.length>1){
			    				UicDialog.Error("请选择一个合同！");
			    				return;
				    		}
		        			var frameSrc = ctx+"/sales/funds/budgetFunds/addRecieveDialog?salesId="+ids[0].id; 
		    	    		$('#dailogs1').on('show', function () {
		    	    			$('#dtitle').html("添加回款预测");
		    	    		     $('#dialogbody').load(frameSrc); 
		    	    			     $("#save_recieve").unbind('click');
		    	    			     $('#save_recieve').click(function () {
		    	    			    	 var id=$("#_eoss_customer_id").val();
		    	    			        	var recieve=$("#recieve_amount").val();
		    	    			        	var time=$("#add_budgetDate").val();
		    	    			       	     var url = ctx+'/sales/funds/budgetFunds/doRecieve?id='+id+'&recieveAmount='+recieve+'&recieveTime='+time+'&tmp='+Math.random();
		    	    			       	     var options = {};
		    	    			       	     options.formId = "_sino_eoss_cuotomer_addform";
		    	    			       	     if($.formSubmit.doHandler(options)){
		    	    			       	    	 //按钮不可用
		    	    			       	    	 $.post(url,function(data,status){
		    	    	       	 	            	if(data.date=="success"){
		    	    	       	 	            		$('#dailogs1').modal('hide');
		    	    	       	 	                }else{
		    	    	       	 	                	UicDialog.Error(data.msg);
		    	    	       	 	                }
		    	    			       	 	     }); 
		    	    			       	     }
		    	    			     });
		    	    					
		    	   		 });
		    	    		$('#dailogs1').on('hidden', function () {$('#dailogs1').unbind("show");});
	    	   				$('#dailogs1').modal({show:true});
	    	   				$('#dailogs1').off('show');
       	 	            	
		        		 }); 
		        		$("#removeContract_btn").bind('click',function(){//删除
		        			fund.methods.removeOrder();
		        		 });
		        	});
		        },
		        contractStr:function(divInner, trid, row, count){
		        	if(divInner!=null||divInner!=''){
		        		return "￥"+fmoney(divInner,2);
		        	}else{
		        		return "￥0.00";
		        	}
		        },
		        receiveStr:function(divInner, trid, row, count){
		        	if(divInner!=null||divInner!=''){
		        		return "￥"+fmoney(divInner,2);
		        	}else{
		        		return "￥0.00";
		        	}
		        },
		        invoiceStr:function(divInner, trid, row, count){
		        	if(divInner!=null||divInner!=''){
		        		return "￥"+fmoney(divInner,2);
		        	}else{
		        		return "￥0.00";
		        	}
		        },
		        removeOrder:function(){
		        	var ids=$("#"+fund.config.uicTable).getTableCheckedRows();
		    		if(ids.length==0||ids.length>1){
	    				UicDialog.Error("请选择一个合同！");
	    				return;
		    		}
        			var frameSrc = ctx+"/sales/funds/budgetFunds/addInvoiceDialog?salesId="+ids[0].id+"&invoice="+ids[0].invoice; 
    	    		$('#dailogs2').on('show', function () {
    	    			$('#dtitle_invoice').html("添加回款预测");
    	    		     $('#dialogbody_invoice').load(frameSrc); 
    	    			     $("#save_invoice").unbind('click');
    	    			     $('#save_invoice').click(function () {
    	    			    	 var id=$("#_invoice_id").val();
    	    			        	var recieve=$("#invoice_amount").val();
    	    			        	var time=$("#add_invoiceDate").val();
    	    			       	     var url = ctx+'/sales/funds/budgetFunds/doInvoice?id='+id+'&invoiceAmount='+recieve+'&invoiceTime='+time+'&tmp='+Math.random();
    	    			       	     var options = {};
    	    			       	     options.formId = "_sino_eoss_invoice_addform";
    	    			       	     if($.formSubmit.doHandler(options)){
    	    			       	    	 //按钮不可用
    	    			       	    	 $.post(url,function(data,status){
    	    	       	 	            	if(data.date=="success"){
    	    	       	 	            		$('#dailogs2').modal('hide');
    	    	       	 	                }else{
    	    	       	 	                	UicDialog.Error(data.msg);
    	    	       	 	                }
    	    			       	 	     }); 
    	    			       	     }
    	    			     });
    	    					
    	   		 });
    	    		$('#dailogs2').on('hidden', function () {$('#dailogs2').unbind("show");});
	   				$('#dailogs2').modal({show:true});
	   				$('#dailogs2').off('show');
	 	            	
		    	},
		        doAdvancedFun:function(){
					var contractName=$("#contractName").val();
					var customerInfo = $('#search_customerInfo').val();
					$("#"+fund.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"contractName",
		        			value:contractName
		        		},{
		        			name:"customerInfo",
		        			value:customerInfo
		        		}]
		        	});
		        	$("#"+fund.config.uicTable).tableReload();
		        },
		        
		        hisRecieveTable:function(){
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#hisRecieveTable").uicTable({
		        		title : "",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
	    			    columns:[
	    			        { code:'contractName',name :'合同名称',width:'18%',process:fund.methods.receiveUpdate},
		        			{ code:'budgetReceive', name :'预收回款',width:'10%',process:fund.methods.budgetReceiveStr},												
		        			{ code:'budgetDate', name :'预计时间',width:'10%'},
		        			{ code:'createDate', name :'创建时间',width:'15%'}
			        ],
			        url: ctx+'/sales/funds/budgetFunds/getHisReceive?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:5,
			        onLoadFinish:function(){
			        	$("a[name=receiveUpdate]").bind('click',function(){
		        			var fundid=$(this).attr("fundid");
		        			var salesId=$(this).attr("salesid");
		        			var frameSrc = ctx+"/sales/funds/budgetFunds/addRecieveDialog?fundid="+fundid+"&salesId="+salesId
				        	$('#dailogs1').on('show', function () {
		    	    			$('#dtitle').html("修改回款预测");
		    	    		     $('#dialogbody').load(frameSrc); 
		    	    			     $("#save_recieve").unbind('click');
		    	    			     $('#save_recieve').click(function () {
		    	    			    	 	var id=$("#_Recieve_id").val();
		    	    			        	var recieve=$("#recieve_amount").val();
		    	    			        	var time=$("#add_budgetDate").val();
		    	    			        	var fundsid=$("#_eoss_funds_id").val();
		    	    			       	     var url = ctx+'/sales/funds/budgetFunds/doRecieve?id='+id+'&recieveAmount='+recieve+'&recieveTime='+time+'&fundsid='+fundsid+'&tmp='+Math.random();
		    	    			       	     var options = {};
		    	    			       	     options.formId = "_sino_eoss_cuotomer_addform";
		    	    			       	     if($.formSubmit.doHandler(options)){
		    	    			       	    	 //按钮不可用
		    	    			       	    	 $.post(url,function(data,status){
		    	    	       	 	            	if(data.date=="success"){
		    	    	       	 	            		$('#dailogs1').modal('hide');
			    	    	       	 	            	$("#hisRecieveTable").tableOptions({
			    	    	       			        		pageNo : '1',
			    	    	       			        		addparams:[{
			    	    	       			        		}]
			    	    	       			        	});
			    	    	       	 	            	$("#hisRecieveTable").tableReload();
		    	    	       	 	                }else{
		    	    	       	 	                	UicDialog.Error(data.msg);
		    	    	       	 	                }
		    	    			       	 	     }); 
		    	    			       	     }
		    	    			     });
		    	   		 });
		    	    		$('#dailogs1').on('hidden', function () {$('#dailogs1').unbind("show");});
			   				$('#dailogs1').modal({show:true});
			   				$('#dailogs1').off('show');
		        		});
			        },
		        	});
		        	$(".advancedSearch").show();
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	$('.doAdvancedSearch').hide();
		        	$('.operation').hide();
		        	/*$(".advancedSearch",$("#hisRecieveTable")).load(ctx+'/sales/funds/budgetFunds/searchHisReceive?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn1").unbind('click').click(function () {
		        			fund.doExecute('doAdvancedReceive');
			        	});
		        		$(".advancedSearch",$("#hisRecieveTable")).css("height","auto");
		        	});*/
		        	
		        },
		        budgetReceiveStr:function(divInner, trid, row, count){
		        	if(divInner!=null||divInner!=''){
		        		return "￥"+fmoney(divInner,2);
		        	}else{
		        		return "￥0.00";
		        	}
		        },
		        receiveUpdate:function(divInner, trid, row, count){
		        	var id=row.id;
		        	var contractId = row.contractId;
		        	var str="<a href='javascript:void(0)' style='color:#005EA7;' name='receiveUpdate' fundid='"+id+"' salesId='"+contractId+"'>"+divInner+"</a>";
		        	return str;
		        },
		        hisInvoiceTable:function(){
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#hisInvoiceTable").uicTable({
		        		title : "",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
	    			    columns:[
	    			        { code:'contractName',name :'合同名称',width:'18%',process:fund.methods.updateInvoice},
		        			{ code:'budgetInvoice', name :'预开发票',width:'10%',process:fund.methods.budgetInvoiceStr},												
		        			{ code:'budgetDate', name :'预计时间',width:'10%'},
		        			{ code:'createDate', name :'创建时间',width:'15%'}
			        ],
			        url: ctx+'/sales/funds/budgetFunds/getHisInvoice?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:5,
			        onLoadFinish:function(){
			        	$("a[name=updateInvoice]").bind('click',function(){
		        			var fundid=$(this).attr("fundid");
		        			var salesId=$(this).attr("salesId");
		        			var frameSrc = ctx+"/sales/funds/budgetFunds/addInvoiceDialog?salesId="+salesId+"&fundid="+fundid; 
		    	    		$('#dailogs2').on('show', function () {
		    	    			$('#dtitle_invoice').html("修改发票预测");
		    	    		     $('#dialogbody_invoice').load(frameSrc); 
		    	    			     $("#save_invoice").unbind('click');
		    	    			     $('#save_invoice').click(function () {
		    	    			    	 var id=$("#_invoice_id").val();
		    	    			        	var recieve=$("#invoice_amount").val();
		    	    			        	var time=$("#add_invoiceDate").val();
		    	    			        	var fundid=$("#_invoice_funds_id").val();
		    	    			       	     var url = ctx+'/sales/funds/budgetFunds/doInvoice?id='+id+'&invoiceAmount='+recieve+'&invoiceTime='+time+'&fundid='+fundid+'&tmp='+Math.random();
		    	    			       	     var options = {};
		    	    			       	     options.formId = "_sino_eoss_invoice_addform";
		    	    			       	     if($.formSubmit.doHandler(options)){
		    	    			       	    	 //按钮不可用
		    	    			       	    	 $.post(url,function(data,status){
		    	    	       	 	            	if(data.date=="success"){
		    	    	       	 	            		$('#dailogs2').modal('hide');
			    	    	       	 	            	$("#hisInvoiceTable").tableOptions({
			    	    	       			        		pageNo : '1',
			    	    	       			        		addparams:[{
			    	    	       			        		}]
			    	    	       			        	});
			    	    	       	 	            	$("#hisInvoiceTable").tableReload();
		    	    	       	 	                }else{
		    	    	       	 	                	UicDialog.Error(data.msg);
		    	    	       	 	                }
		    	    			       	 	     }); 
		    	    			       	     }
		    	    			     });
		    	    					
		    	   		 });
		    	    		$('#dailogs2').on('hidden', function () {$('#dailogs2').unbind("show");});
			   				$('#dailogs2').modal({show:true});
			   				$('#dailogs2').off('show');
		        		});
			        },
		        	});
		        	$(".advancedSearch").show();
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	$('.doAdvancedSearch').hide();
		        	$('.operation').hide();
		        	/*$(".advancedSearch",$("#hisRecieveTable")).load(ctx+'/sales/funds/budgetFunds/searchHisReceive?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn1").unbind('click').click(function () {
		        			fund.doExecute('doAdvancedReceive');
			        	});
		        		$(".advancedSearch",$("#hisRecieveTable")).css("height","auto");
		        	});*/
		        },
		        budgetInvoiceStr:function(divInner, trid, row, count){
		        	if(divInner!=null||divInner!=''){
		        		return "￥"+fmoney(divInner,2);
		        	}else{
		        		return "￥0.00";
		        	}
		        },
		        updateInvoice:function(divInner, trid, row, count){
		        	var id=row.id;
		        	var contractId = row.contractId;
		        	var str="<a href='javascript:void(0)' style='color:#005EA7;' name='updateInvoice' fundid='"+id+"' salesId='"+contractId+"'>"+divInner+"</a>";
		        	return str;
		        },
	},
	        /**
	         * 执行方法操作
	         */
	        doExecute:function(flag, param) {
	        	var method =fund.methods[flag];
	        	if( typeof method === 'function') {
	        		return method(param);
	        	} else {
	        		alert('操作 ' + flag + ' 暂未实现！');
	        	}
	        }
	}
	exports.init = function() {
		fund.doExecute('initDocument');
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
	
	/*//子页面关闭刷新该父页面
	childColseRefresh=function(){
		fund.doExecute('initDocument');
    }
*/

});