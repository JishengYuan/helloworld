define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
	require("changTag");
	var contractTypeJson={};

	var contract = {
			config: {
				module: 'contract',
				uicTable : 'uicTable',
	            namespace: '/sales/contract/relevance'
	        },
	        methods :{
	        	initDocument : function(){
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=contractType&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				contractTypeJson = msg;
		    			}
		    		});
	        		contract.doExecute('initTable');
		        },
		        initTable : function(){
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#"+contract.config.uicTable).uicTable({
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
						title : "合同管理-->合同订单关联查询",
		        		searchItems:'name',
	    			    columns:[

	    			            [{ code:'CreatorName',name : '客户经理',width:'15%'},
		    			          { code:'ContractType',name : '合同类型',process:contract.methods.contractTypeStr,width:'15%'}],
	    			            [{ code:'ContractCode',name : '合同编号',process:contract.methods.link1,width:'18%'},
	    			             { code:'OrderCode', name :'对应订单',process:contract.methods.link2,width:'18%'}],
	    			     		[{ code:'productNum', name :'合同下单数',width:'18%'},
	    			     		 { code:'orderNum',name : '对应订单数',width:'18%'}],
	    			     		[{ code:'salesOrder',name : '合同状态', width:'15%'},
	    						 { code:'ContractAmount', name :'合同总金额',width:'15%',process:contract.methods.contractAmount}],
	    						[{ code:'orderProNum', name :'订单下单数',width:'18%'},
	    						 { code:'orderProductAmount', name :'订单分项金额',width:'18%',process:contract.methods.orderProductAmountStr}],
	    						[{ code:'BeginTime', name :'订单下单日期',width:'12%'}]
	    			    ],
			        url: ctx+contract.config.namespace+'/getListRelevance?&tmp='+Math.random(),
			    	addparams:[{name:"sales_startTime",value:"2015-01-01"}],
			        pageNo:1,
			        pageSize:10,
			        moreCellShow : true,
			        onLoadFinish:function(){
			        },
			        searchFun:function(){
			        	$("#"+contract.config.uicTable).tableOptions({
			        		pageNo : '1',
			        		addparams:[{
			        			name:"test",
			        			value:$('.searchs').find('input').first().val()
			        		}]
			        	});
			        	$("#"+contract.config.uicTable).tableReload();
			        },
			        advancedFun:contract.methods.advancedFun
			        });
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doAdvancedSearch').hide();
		        	$('.doSearch').hide();
		        	$(".advancedSearch",$("#"+contract.config.uicTable)).load(ctx+contract.config.namespace+'/search?&tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			contract.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			contract.doExecute('reload');
		        		});
		        		$("#advanced_export").unbind('click').click(function () {
		        			contract.doExecute('salesOrder');
		        		});
		        	});
		        	$(".advancedSearch",$("#"+contract.config.uicTable)).css("height","auto");
		        	$('.doAdvancedSearch').click();

		        },
		        advancedFun:function(type) {
		        	if(type){
		        		$(".advancedSearch",$("#"+contract.config.uicTable)).css("height","auto");
		        		
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			//contract.doExecute('doAdvancedFun');
		        			contract.methods.doAdvancedFun();
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			contract.doExecute('reload');
		        		});
		        		
		        	} else {
		        	}
		        },
		        doAdvancedFun:function(){
		        	/*$("#"+contract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"test",
		        			value:1
		        		}]
		        	});
		        	$("#"+contract.config.uicTable).tableReload();*/
		        	
		        	var contractCode = $('#search_contractCode').val();
		        	var contractAmount = $('#search_contractAmount').val();
		        	var sales_startTime = $('#sales_startTime').val();
		        	var sales_endTime = $('#sales_endTime').val();
		        	var order_startTime = $('#order_startTime').val();
		        	var order_endTime = $('#order_endTime').val();
		        	var creator = $("#search_creator").formUser("getValue");
		        	//var contractType=$('#search_contractType').val();
		        	var orgId = $('#orgTreeInput').val();
		        	
		        	$("#"+contract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[ {name:"contractCode",value:contractCode},
		        		           {name:"contractAmount",value:contractAmount},
		        		           {name:"sales_startTime",value:sales_startTime},
		        		           {name:"sales_endTime",value:sales_endTime},
		        		           {name:"order_startTime",value:order_startTime},
		        		           {name:"order_endTime",value:order_endTime},
		        		           {name:"creator",value:creator},
		        		           {name:"orgId",value:orgId},
		        		           /*{name:"contractType",value:contractType},*/
		        		          ]
		        	});
		        	$("#"+contract.config.uicTable).tableReload();
		        },
		        salesOrder:function(){
		        	var contractCode = $('#search_contractCode').val();
		        	var contractAmount = $('#search_contractAmount').val();
		        	var sales_startTime = $('#sales_startTime').val();
		        	var sales_endTime = $('#sales_endTime').val();
		        	var order_startTime = $('#order_startTime').val();
		        	var order_endTime = $('#order_endTime').val();
		        	var creator = $("#search_creator").formUser("getValue");
		        	//var contractType=$('#search_contractType').val();
		        	var orgId = $('#orgTreeInput').val();
		        	
		        	var url = ctx+"/sales/contract/relevance/exportSales?contractCode="+contractCode;
		        		url+="&contractAmount="+contractAmount;
		        		url+="&sales_startTime="+sales_startTime;
		        		url+="&sales_endTime="+sales_endTime;
		        		url+="&order_startTime="+order_startTime;
		        		url+="&order_endTime="+order_endTime;
		        		url+="&creator="+creator;
		        		//url+="&contractType="+contractType;
		        		url+="&orgId="+orgId;
		        	window.location.href= url;
		        	
		        },
		        creatorName:function(divInner, trid, row, count){
		        	var str = "";
		        	if(row.dealUserName == ""||row.dealUserName == null){
		        		str+=divInner;
		        	} else {
		        		str+=divInner+"["+row.dealUserName+"]";
		        	}
		        	return str;
		        },
		        contractAmount:function(divInner, trid, row, count){
		        	if(divInner==""||divInner==null){
		        		return "￥0.00";
		        	}else{
		        		return "￥"+fmoney(divInner,2);
		        	}
				},
				orderProductAmountStr:function(divInner, trid, row, count){
					if(divInner==""||divInner==null){
		        		return "￥0.00";
		        	}else{
		        		return "￥"+fmoney(divInner,2);
		        	}
				},
				contractTypeStr:function(divInner, trid, row, count){
					var contractType = "";
					for(var i in contractTypeJson){
						if(row.ContractType == contractTypeJson[i].id){
							contractType += contractTypeJson[i].name;
						}
					}
					return contractType;
				},
				link1:function(divInner, trid, row, count){
					var str = "";
					str+='<a style="color:#005EA7;" href="'+ctx+'/sales/contract/detail?id=' + row.sales_contract +'" target="_blank" >'+row.ContractCode+'</a>';	
					return str;
				},
					
				link2:function(divInner, trid, row, count){
					var str="";
					str+='<a style="color:#005EA7;" href="'+ctx+'/business/order/detail?id=' + row.business_order + '" target="_blank" >'+row.OrderCode+'</a>';
					return str;
				},
		        reload : function(){
		        	$("#search_contractCode").attr("value","");
		        	$("#search_contractAmount").attr("value","");
		        	
		        	$('#contractType').formSelect('setValue',"");
		        	$('#search_contractType').val("");
		        	
		        	$('#sales_startTime').val("");
		        	$('#sales_endTime').val("");
		        	$('#order_startTime').val("");
		        	$('#order_endTime').val("");
		        	/*var sd=$("#"+contract.config.uicTable).getTableCheckedRows();
		    		for(var i=0;i<sd.length;i++){
		    			alert(sd[i].id);
		    		}*/
		        	$("#"+contract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[ ]
		        	});
		        	$("#"+contract.config.uicTable).tableReload();
		        	
		        },
		        
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = ctx+contract.config.namespace + opts.url;
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
				var method =contract.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		contract.doExecute('initDocument');
	}
	//子页面关闭刷新该父页面
	childColseRefresh=function(){
		//contract.doExecute('initDocument');
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