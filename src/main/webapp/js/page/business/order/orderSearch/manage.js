define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
	require("confirm_dialog");
	require("uic/message_dialog");
	var StringBuffer = require("stringBuffer");
	//子页面关闭刷新该父页面
	childColseRefresh = function(){
			$("#uicTable").tableOptions({
    			pageNo : '1',
    			addparams:[]
    			});
    			$("#uicTable").tableReload();
		}
	var orderTypeJson = {};
	var statusTypeJson = {};
	var order = {
			config: {
				module: 'order',
				uicTable : 'uicTable',
				orderStatus:'orderStatus',
				orderType:'orderType',
	            namespace: '/business/order'
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
		    				statusTypeJson = msg;
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
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#"+order.config.uicTable).uicTable({
		        		title : "商务采购-->订单查询",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		/*buttons : [
		                    { name: '添加', butclass: 'doAdd',onpress: order.methods.toolbarItem},
		                    {  name: '删除', butclass: 'doDel',onpress: order.methods.toolbarItem},
		        			],*/
		        	searchItems:'name',
	    			    columns:[
	    			             [
	    			              { code:'orderName',name : '订单名称',process:order.methods.handler,width:'20%'},	
	    			              { code:'orderCode',name : '订单编码',width:'20%'}],
	    			             [
	    			              { code:'supplierShortName', name :'供应商',process:order.methods.greyStyle,width:'20%'},
	    			              { code:'creator',name : '采购员',process:order.methods.greyStyle,width:'20%'}],
	    			              
	    			             [{ code:'orderType', name :'订单类型',process:order.methods.orderType,width:'20%'},
	    			              { code:'payAmountStr', name :'已付金额',process:order.methods.payAmountStyle,width:'20%'}],
	    			              
	    			             [{ code:'orderAmountStr', name :'订单金额',process:order.methods.orderAmountStyle,width:'20%'},
	    			             { code:'reimAmountStr', name :'已报销金额',process:order.methods.reimAountStyle,width:'20%'}],
	    			             
	    			             [{code:'orderStatus', name :'订单状态',process:order.methods.orderStatus,width:'20%'},
	    			              {code:'arrivalStatus', name :'到货状态',process:order.methods.greyStyle,width:'20%'}]														
			        ],
			        url: ctx+order.config.namespace+'/getOrderList?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:8,
			        moreCellShow : true,
			        onLoadFinish:function(){
			        	var totallAmount = $("#"+order.config.uicTable).getJsonData().totallAmount;
			        	var pageAmount = $("#"+order.config.uicTable).getJsonData().pageAmount;
			        	$('#totallAmount').html("￥"+fmoney(totallAmount,2));
			        	$('#pageAmount').html("￥"+fmoney(pageAmount,2));
			        /*	var pageAmount=0;
			        	$("#uicTable").find("table").each(function(i, taEle){
			        		$(taEle).find("tbody").each(function(j, tbEle){
			        				$(tbEle).find("tr").each(function(m, trEle){
			        						$(trEle).find("td").each(function(u, tdEle){
			        							if(u==3){
			        								$(tdEle).find("div").each(function(t, diEle){
			        									if(t==1){
			        										pageAmount+=Number($(diEle).find("font").text());
			        									}
			        								});
			        							}
			        						});
			        				 });
			        		});
			        	});*/
			        	//$("#pageAmount").text(pageAmount);
			        	//var str = '<span style="margin-left:10px;"><a href="#" class="_edit"><i class="icon-edit"></i></a></span>&nbsp;<span style="margin-left:10px;margin-right:10px;"><a href="#" class="_payment"><i class="icon-list-alt"></i>付款计划</a></span> ';
				        $('.order_handler').popover({
			        		trigger :'click',
			        		placement:'right',
			        		html:true,
			        		//content:str
			        	}).click(function(e) {
					        e.preventDefault();
					        $('._payment').unbind('click').click(function () {
					        	var orderId=$(this).parent().parent().parent().parent().attr("spanId");
					        	var options = {};
					    		options.murl = 'business/payment/saveOrUpdate';
					    		options.keyName = 'id';
					    		options.keyValue =orderId;
					    		$.openurl(options);
					        });
					      
					    });
			        },
			       // searchTitle:'订单编号',
			       // searchTip:'请输入订单编号',
			       /* searchFun:function(){
			        	$("#"+config.uicTable).tableOptions({
			        		pageNo : '1',
			        		addparams:[{
			        			name:"test",
			        			value:$('.searchs').find('input').first().val()
			        		}]
			        	});
			        	$("#"+order.config.uicTable).tableReload();
			        },*/
			        advancedFun:order.methods.advancedFun
		         });
		        	
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	$('.doAdvancedSearch').click();
		        	$('.doAdvancedSearch').hide();
		        	$(".advancedSearch",$("#"+order.config.uicTable)).load(ctx+order.config.namespace+'/orderSearch/allSearch?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			order.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			order.doExecute('reload');
		        		});
		        	});
		        	$(".advancedSearch",$("#"+order.config.uicTable)).css("height","auto");
		        	
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
		        orderStatus : function(divInner, trid, row, count){
		        	var orderStatus = "";
					for(var i in statusTypeJson){
						if(row.orderStatus == statusTypeJson[i].id){
							orderStatus += statusTypeJson[i].name;
						}
					}
					return orderStatus;
		        },
		        
		        handsHandler : function(divInner, trid, row, count){
		        	var str = "";
		        	var dataContent="";
		        	str+=row.orderName;
		        	if(row.orderStatus=='SH'){
		        		dataContent="<span style='margin-left:10px;'><a href='#' class='_payment'><i class='icon-list-alt'></i>付款计划</a></span> ";
		        	}else{
		        		dataContent="<span style='margin-left:10px;'><a href='"+ctx+"/business/order/saveOrUpdate?id="+row.id+"' target='_blank' class='_edit'><i class='icon-edit'></i>修改</a></span>&nbsp;&nbsp;<span style='margin-left:10px;margin-right:10px;'><a href='#' class='_payment'><i class='icon-list-alt'></i>付款计划</a></span> ";
		        	}
		        	str+='<span spanId="'+row.id+'" style="margin-left:5px"><a href="#" name="order_handler" data-content="'+dataContent+'" id="'+row.id+'" class="order_handler"><i class="icon-hand-down"></i></a></span>';
		        	return str;
		        },
		        handler:function(divInner,trid,row,count){
		        	var str="";
		        	str+='<a style="color:#005EA7;" href="'+ctx+'/business/order/detail?id='+row.id+'&flat=search" target="_blank" name="_orderCode">'+row.orderName+'</a>';
		        	return str;
		        },
		       
		        advancedFun:function(type) {

		        	if(type){
		        		$(".advancedSearch",$("#"+order.config.uicTable)).css("height","auto");
		        		
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			order.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			order.doExecute('reload');
		        		});
		        	} else {
		        	}
		        },
		        doAdvancedFun:function(){
		        	$('#pageAmount').html("￥"+0);
		        	$('#totallAmount').html("￥"+0);
		        	var orderCode=$('#search_orderCode').val();
		        	var orderName=$('#search_orderName').val();
		        	var orderAmount=$('#search_orderAmount').val();
		        	var orderType = $('#search_orderType').val();
		        	var creator=$('#search_creator').val();
		        	var purchaseType=$('#search_purchaseType').val();
		        	var supplierShortName=$('#search_supplierShortName').val();
		        	var payStatus=$('#search_payStatus').val();
		        	var reimStatus=$('#search_reimStatus').val();
		        	var startTime=$('#startTime').val();
		        	var endTime=$('#endTime').val();
		        	
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
		        			name:"creator",
		        			value:creator
		        		},{
		        			name:"purchaseType",
		        			value:purchaseType
		        		},{
		        			name:"supplierShortName",
		        			value:supplierShortName
		        		},{
		        			name:"payStatus",
		        			value:payStatus
		        		},{
		        			name:"startTime",
		        			value:startTime
		        		},{
		        			name:"endTime",
		        			value:endTime
		        		},{
		        			name:"reimStatus",
		        			value:reimStatus
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
		        	$('#search_creator').val("");
		        	$('#creator').formSelect('setValue',"");
		        	$('#search_purchaseType').val("");
		        	$('#purchaseType').formSelect('setValue',"");
		        	$('#search_supplierShortName').val("");
		        	$('#supplierShortName').formSelect('setValue',"");
		        	$('#search_payStatus').val("");
		        	$('#payStatus').formSelect('setValue',"");
		        	$('#search_reimStatus').val("");
		        	$('#reimStatus').formSelect('setValue',"");
		        	$('#endTime').val("");
		        	$('#startTime').val("");
		        	
		        	$("#"+order.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+order.config.uicTable).tableReload();
		        },
		        greyStyle:function(divInner, trid, row, count){
					return '<font class="grey03">'+divInner+'</font>';
				},
				orderAmountStyle:function(divInner, trid, row, count){
					return "￥"+fmoney(divInner,2);
				},
				payAmountStyle:function(divInner, trid, row, count){
					return '<font class="grey03">￥'+fmoney(divInner,2)+'</font>';
				},
				reimAountStyle:function(divInner, trid, row, count){
					return '<font class="grey03">￥'+fmoney(divInner,2)+'</font>';
				},
		     /*   goBack : function(m){
		        	var options = {};
//		    		options.murl = vendor.config.namespace + m;
//		    		$.openurl(options);
		        },*/
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
/*	//子页面关闭刷新该父页面
	childColseRefresh=function(){
		order.doExecute('initDocument');
    }*/

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