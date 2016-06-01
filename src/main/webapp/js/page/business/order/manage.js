define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
	require("confirm_dialog");
	require("uic/message_dialog");
	var StringBuffer = require("stringBuffer");
	require("changTag");
	require("stringHelper");
	var orderTypeJson = {};
	var OrderstatusTypeJson = {};
	var paystatusTypeJson = {};
	var reimTypeJson = {};
	var order = {
			config: {
					module: 'order',
	            	uicTable : 'uicTable',
	            	namespace: '/business/order',
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
	        		
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=orderStatus&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				OrderstatusTypeJson = msg;
		    			}
		    		});
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=payStatus&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				paystatusTypeJson = msg;
		    			}
		    		});
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=reimStatus&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				reimTypeJson = msg;
		    			}
		    		});
	        		order.doExecute('initTable');
	        		/*
        			 *键盘点击事件
        			 */
        			document.onkeydown = function(event) {
        				var e = event || window.event || arguments.callee.caller.arguments[0];
        				if (e && e.keyCode == 13) { // enter 键
        						order.doExecute('doAdvancedFun');
        				}

        			}
		        },
		       
		        initTable : function(){
		        	var grid=$("#"+order.config.uicTable).uicTable({
		        		title : "商务采购-->我的订单",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		/*buttons : [
		                    { name: '添加', butclass: 'doAdd',onpress: order.methods.toolbarItem},
		                    {  name: '删除', butclass: 'doDel',onpress: order.methods.toolbarItem},
		        			],*/
		        			searchItems:'name',
	    			    columns:[
	    			             [{code:'orderName',name : '订单名称',process:order.methods.handler,width:'25%'},
	    			              {code:'orderCode',name : '订单编号',width:'25%'}
	    			             ],
	    			              
	    			             [ {code:'orderType', name :'订单类型',process:order.methods.orderType,width:'15%'},
	    			               {code:'orderStatus', name :'订单状态',process:order.methods.Ostatus,width:'15%'}
	    			              ],
	    			              
	    			             [{code:'orderAmountStr', name :'订单金额',width:'20%',process:order.methods.orderAmountStyle},
	    			              {code:'payStatus', name :'付款状态',width:'20%',process:order.methods.showPayAmount}],
	    			              
	    			             [{code:'supplierShortName', name :'供应商',width:'23%'},
	    			             {code:'reimStatus', name :'发票计划',width:'23%',process:order.methods.showReimamount}],
	    			             
	    			             [{code:'creator',name : '采购员',width:'17%'},
	    			              {code:'arrivalStatus', name :'到货状态',width:'17%',process:order.methods.arriveStates}]														
			        ],
			        url: ctx+order.config.namespace+'/getTableGrid?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:9,
			        moreCellShow : true,
			        onLoadFinish:function(){
			        	$(".showbody").css("overflow","visible");
			        },
			        advancedFun:order.methods.advancedFunction
		         });
		        	
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	$('.doAdvancedSearch').click();
		        	$('.doAdvancedSearch').hide();
		        	$('.operation').hide();
		        	$(".advancedSearch",$("#"+order.config.uicTable)).load(ctx+order.config.namespace+'/search?tmp='+Math.random(),function(){
		        		$(".advancedSearch",$("#"+order.config.uicTable)).css("height","auto");
		        		//绑定“查询”按钮
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			order.methods.doAdvancedFun();
			        	});
		        		//绑定“重置”按钮
		        		$("#reset_btn").unbind('click').click(function () {
		        			//order.methods.cleanSearchInput();
		        			order.doExecute('reload');
		        		});
			        	//绑定“添加”按钮
		        		$("#addOrder_btn").unbind('click').click(function () {
		        			order.methods.toAddOrderPage();
			        	});
		        		//绑定“删除”按钮
		        		$("#removeOrder_btn").unbind('click').click(function () {
		        			order.methods.removeOrder();
			        	});
		        		//绑定“修改”按钮
		        		$("#updateOrder_btn").unbind('click').click(function () {
		        			order.methods.toUpdateOrderPage();
			        	});
		        		//绑定“结算币种”按钮
		        		$(".accountCurrency").click(function () {
				        	order.methods.doAdvancedFun();
		        		});
		        	});
		        	
		        },
		        	
		        orderType : function(divInner, trid, row, count){
		        	var orderType = "";
					for(var i in orderTypeJson){
						if(row.orderType == orderTypeJson[i].id){
							orderType += orderTypeJson[i].name;
						}
					}
					return orderType;
		        },
		        
		        handsHandler : function(divInner, trid, row, count){
		        	var str = "";
		        	var dataContent="";
		        	str+=row.orderName;
		        	if(row.orderStatus =='CG'){
		        		dataContent="<span style='margin-left:10px;'><a href='"+ctx+"/business/order/saveOrUpdate?id="+row.id+"' target='_blank' class='_edit'><i class='icon-edit'></i>修改</a></span>";
		        	}
		        	if(row.orderStatus =='TGSP'){
		        		dataContent="<span style='margin-left:10px;'><a href='#' class='_payment'><i class='icon-list-alt'></i>付款计划</a></span>&nbsp;<span style='margin-left:10px;'><a href='#' class='_reim'><i class='icon-list-alt'></i>发票计划</a></span>&nbsp;<span style='margin-left:10px;margin-right:10px;'><a href='"+ctx+"/business/order/fileUpdate?id="+row.id+"' target='_blank'  class='_fileUpdate'><i class='icon-screenshot'></i>上传客户签收单</a></span> ";
		        	}
		        	/*else{
		        		dataContent="<span style='margin-left:10px;'><a href='"+ctx+"/business/order/saveOrUpdate?id="+row.id+"' target='_blank' class='_edit'><i class='icon-edit'></i>修改</a></span>&nbsp;<span style='margin-left:10px;margin-right:10px;'><a href='#' class='_payment'><i class='icon-list-alt'></i>付款计划</a></span>&nbsp;<span style='margin-left:10px;'><a href='#' class='_reim'><i class='icon-list-alt'></i>发票计划</a></span>&nbsp;<span style='margin-left:10px;margin-right:10px;'><a href='"+ctx+"/business/order/fileUpdate?id="+row.id+"' target='_blank'  class='_fileUpdate'><i class='icon-screenshot'></i>上传客户签收单</a></span>  ";
		        	}*/
		        	str+='<span spanId="'+row.id+'" style="margin-left:5px"><a href="#" name="order_handler" data-content="'+dataContent+'" id="'+row.id+'" class="order_handler"><i class="icon-hand-down"></i></a></span>';
		        	return str;
		        },
		        toAddOrderPage:function(){
	        		/*var opts = {};
	    			opts.url = "/addOrder";
	    			order.methods.openurl(opts);*/
	    			var url=ctx+'/business/order/addOrder?tmp='+Math.random();
	    			window.open(url,'_blank');
		        },
		        
		        //美元修改
		        toUpdateOrderPage:function(){
		        	var ids=$("#"+order.config.uicTable).getTableCheckedRows();
		    		if(ids.length==0){
	    				UicDialog.Error("请选择修改条目！");
	    				return;
		    		}
		    		if(ids.length>1){
		    			UicDialog.Error("修改只能选择一个条目！");
	    				return;
		    		}else{
			    		for(var i=0;i<ids.length;i++){
			    			var accountCurrency=ids[i].accountCurrency;
			    			var id=ids[i].id;
			    			if(accountCurrency==2){
			    				var url=ctx+'/business/order/saveOrUpdateUsd?id='+id;
				    			window.open(url,'_blank');
			    			}else{
			    				UicDialog.Error("只能选择美元结算订单修改！");
			    				return;
			    			}
			    		}
		    		}
		        	
		        },
		        
		        
		        handler:function(divInner,trid,row,count){
		        		var str="";
			        	if(row.orderStatus =='CG'){
			        		var str = "";
				        	str+='<a style="color:#005EA7;" href="'+ctx+'/business/order/saveOrUpdate?id=' + row.id +'" target="_blank" >'+row.orderName+'<font class="blue02"></a>';							
				        	return str;
			        	} else if(row.isChange=='CTGSP'){
			        		var str = "";
				        	str+='<a style="color:#005EA7;" href="'+ctx+'/business/order/changeUpdate?id='+row.id+'" target="_blank" >'+row.orderName+'</a>';
				        	return str;
			        	}else{
			        		var str = "";
				        	str+='<a style="color:#005EA7;" href="'+ctx+'/business/order/detail?id='+row.id+'" target="_blank" >'+row.orderName+'</a>';
				        	return str;
			        	}
		        	
		        	/*
		        	str+='<a style="color:#005EA7;" href="#" onclick="openContract(\'1\',\''+row.id+'\',\''+dataContent+'\')" >'+row.orderName+'<font class="blue02"></a>';	*/
		        	return str;
		        },
		        orderAmountStyle : function(divInner, trid, row, count){
		        	if(divInner!=""){
			        	return "￥"+fmoney(divInner,2);
		        	}else{
		        		return "无";
		        	}

		        },
		        removeOrder:function(){
		        	var ids=$("#"+order.config.uicTable).getTableCheckedRows();
		    		if(ids.length==0){
	    				UicDialog.Error("请选择删除条目！");
	    				return;
		    		}
	        		var orderId ='';
		    		for(var i=0;i<ids.length;i++){
		    			/*var orderName=$('#row'+ids[i].id+' > td').eq(2).text();
		    			var orderState=$('#row'+ids[i].id+' > td').eq(2).text().replace(/(^\s*)|(\s*$)/g, "");*/
		    			var name=ids[i].orderName;
		    			var orderState=ids[i].orderStatus;
		    			if(orderState!='CG'){
		    				UicDialog.Error(name+"<br/>已提交审批，无法删除！");
		    				return;
		    			}else{
		    				orderId +=ids[i].id+",";
		    			}
		    		}
		    		var datas={'id':orderId};
				    var url = ctx+'/business/order/remove?tmp='+Math.random();
				    $.post(url,datas,
				    		function(data,status){
				            	if(status="success"){
				                	  UicDialog.Success("删除订单成功!",function(){
				                		  $("#"+order.config.uicTable).tableReload();
				                	  });
				                  }else{
				                  	  UicDialog.Error("删除订单失败！",function(){
				                  		$("#"+order.config.uicTable).tableReload();
				                  	  });
				                  }
				   });   
		    	},
		        
		        /*toolbarItem:function(com, trGrid){

		        	if (com=='doAdd'){
		        		//var opts = {};
		    			//opts.url = "/addOrder";
		    			//order.doExecute("openurl",opts);
		    			var url=ctx+'/business/order/addOrder?tmp='+Math.random();
		    			window.open(url,'_blank');
		    		}
		        	if (com=='doDel'){
		        		var ids=$("#"+order.config.uicTable).getTableCheckedRows();
		        		var id ='';
			    		for(var i=0;i<ids.length;i++){
			    			id +=ids[i].id+",";
			    		}
				   	    var datas={'id':id};
					    var url = ctx+'/business/order/remove?tmp='+Math.random();
					    $.post(url,datas,
					    		function(data,status){
					            	if(status="success"){
					                	  UicDialog.Success("删除数据成功!",function(){
					                	  $("#"+order.config.uicTable).tableReload();
					                	  });
					                  }else{
					                  	  UicDialog.Error("删除数据失败！");
					                  	  $("#"+order.config.uicTable).tableReload();
					                  }
					   });
		        	}
		        },*/
		        advancedFunction:function(type) {

		        	if(type){

		        		$(".advancedSearch",$("#"+order.config.uicTable)).css("height","auto");
		        		//绑定“查询”按钮
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			order.methods.doAdvancedFun();
			        	});
		        		//绑定“重置”按钮
		        		$("#reset_btn").unbind('click').click(function () {
		        			order.doExecute('reload');
		        		});
			        	//绑定“添加合同”按钮
		        		$("#addOrder_btn").unbind('click').click(function () {
		        			order.methods.toAddOrderPage();
			        	});
		        		//绑定“删除合同”按钮
		        		$("#removeOrder_btn").unbind('click').click(function () {
		        			order.methods.removeOrder();
			        	});
		        	/*
		        		$(".advancedSearch",$("#"+order.config.uicTable)).css("height","auto");
		        		
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			order.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			order.doExecute('reload');
		        		});*/
		        	} else {
		        	}
		        },
		        doAdvancedFun:function(){
		        	var orderCode=$('#search_orderCode').val();
		        	var orderName=$('#search_orderName').val();
		        	var orderAmount=$('#search_orderAmount').val();
		        	var orderType = $('#search_orderType').val();
		        	var purchaseType=$('#search_purchaseType').val();
		        	var supplierShortName=$('#search_supplierShortName').val();
		        	
		        	var myCheckbox = $("input[name='accountCurrency']:checked");
		        	var accountCurrency = "";
		        	
		        	myCheckbox.each(function(i,ele){//循环拼装被选中项的值
	        			if($(ele).val() != 'null'){
	        				accountCurrency = accountCurrency+$(this).val()+",";	        				
	        			}
	        		 });
		        	
		        	$("#"+order.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"orderCode",
		        			value:orderCode
		        		},{
		        			name:"orderName",
		        			value:orderName
		        		},{
		        			name:"orderAmount",
		        			value:orderAmount
		        		},{
		        			name:"orderType",
		        			value:orderType
		        		},{
		        			name:"supplierShortName",
		        			value:supplierShortName
		        		},{
		        			name:"accountCurrency",
		        			value:accountCurrency
		        		},{
		        			name:"purchaseType",
		        			value:purchaseType
		        		}]
		        	});
		        	$("#"+order.config.uicTable).tableReload();
		        },
		        reload : function(){
		        	$('#search_orderType').val("");
		        	$('#orderType').formSelect('setValue',"");
		        	$('#search_orderName').val("");
		        	$('#search_orderCode').val("");
		        	$('#search_orderAmount').val("");
		        	$('#search_purchaseType').val("");
		        	$('#purchaseType').formSelect('setValue',"");
		        	$('#search_supplierShortName').val("");
		        	$('#supplierShortName').formSelect('setValue',"");
		        	
		        	$("#"+order.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+order.config.uicTable).tableReload();
		        },
		        cleanSearchInput : function(){
		        	$("#orderType").attr("value"," ");
		        	$("#purchaseType").attr("value"," ");
		        	$("#supplierShortName").attr("value"," ");
		        	
		        },
				greyStyle:function(divInner, trid, row, count){
					return '<font class="grey03">'+divInner+'</font>';
				},
				arriveStates:function(divInner, trid, row, count){
					//此处到货确认这块流程存在问题，需要确认
					var str ="";
					if(row.deliveryAddress=="1"){
						if(row.wareHouseStatus=="A"){
							 str ='<font class="grey03">全部到库房</font>';
						}else if(row.wareHouseStatus=="S"){
							str ='<font class="grey03">部分到库房</font>';
						}else if(row.wareHouseStatus=="N"){
							str ='<font class="grey03">未到库房</font>';
						}						
					}else{
						str =divInner;
					}

					return '<font class="grey03">'+str+'</font>';
				},
				Ostatus : function(divInner, trid, row, count){
					var status = "";
					for(var i in OrderstatusTypeJson){
	        			if(row.orderStatus == OrderstatusTypeJson[i].id){
	        				status += OrderstatusTypeJson[i].name;
	        			}
	        		}
		        	if(row.isChange=="CSP"){
		        		status ="变更申请中";
		        	}
		        	if(row.isChange=="CTGSP"){
		        		status ="可变更";
		        	}
					return '<font class="grey03">'+status+'</font>';
		        },
				showPayAmount:function(divInner, trid, row, count){
					var payStatus = "";
					for(var i in paystatusTypeJson){
						if(row.payStatus == paystatusTypeJson[i].id){
							payStatus += paystatusTypeJson[i].name;
						}
					}
	        		if(row.payStatus!='N'){
				      	var str = "";
				      	if(row.payAmount!=0){
				      		str+='<a style="color:#005EA7;" href="'+ctx+'/business/payOrder/detail?id=' + row.id + '" target="_blank">已付：￥'+fmoney(row.payAmountStr,2)+'<font class="blue02"></a>';							
				      	}else{
				      		str+='<a style="color:#005EA7;" href="'+ctx+'/business/payOrder/detail?id=' + row.id + '" target="_blank">付款状态：'+payStatus+'<font class="blue02"></a>';	
				      	}
			        	return str;
	        		}else{
	        			return '<font class="grey03">付款状态：'+payStatus+'</font>';
	        		}
				},
				showReimamount:function(divInner, trid, row, count){
					var reimStatus = "";
					for(var j in reimTypeJson){
						if(row.reimStatus == reimTypeJson[j].id){
							reimStatus += reimTypeJson[j].name;
						}
					}
	        		if(row.reimStatus!='N'){
				      	var str = "";
				      	if(row.reimAmount!=0){
				      		str+='<a style="color:#005EA7;" href="'+ctx+'/business/rbmApply/finish?id=' + row.id + '" target="_blank">发票状态：已报'+fmoney(row.reimAmountStr,2)+'<font class="blue02"></a>';							
				      	}else{
				      		str+='<a style="color:#005EA7;" href="'+ctx+'/business/rbmApply/finish?id=' + row.id + '" target="_blank">发票状态：'+reimStatus+'<font class="blue02"></a>';							
				      	}
			        	return str;
	        		}else{
	        			return '<font class="grey03">发票状态：'+reimStatus+'</font>';
	        		}
				},
				
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = order.config.namespace + opts.url;
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
	        doExecute:function(flag, param) {
	        	var method =order.methods[flag];
	        	if( typeof method === 'function') {
	        		return method(param);
	        	} else {
	        		alert('操作 ' + flag + ' 暂未实现！');
	        	}
	        }
	}
	exports.init = function() {
		order.doExecute('initDocument');
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