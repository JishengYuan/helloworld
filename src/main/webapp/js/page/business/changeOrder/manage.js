//待下单合同js（普通版）
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
	
	var supplierTypeJson = {};
	var bizOwnerJson = {};
	//子页面关闭刷新该父页面
	childColseRefresh = function(num){
		if(num==2){
			$("#uicTable").hide();
			$("#uicTable2").tableOptions({
			pageNo : '1',
			addparams:[]
			});
			$("#uicTable2").tableReload();
		}else{
			$("#uicTable2").hide();
			$("#uicTable").tableOptions({
			pageNo : '1',
			addparams:[]
			});
			$("#uicTable").tableReload();
		}
	}
	var changeOrder = {
			config: {
				module: 'changeOrder',
				uicTable : 'uicTable',
	            namespace: '/business/changeOrder'
	        },
	        methods :{
	        	initDocument : function(){
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=supplierType&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				supplierTypeJson = msg;
		    				//alert(supplierTypeJson);
		    			}
		    		});
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx+"/business/supplierm/supplierInfo/getBizOwner?tmp="+Math.random(),
		    			success : function(msg) {
		    				bizOwnerJson = msg;
		    			}
		    		});
	        		
	        		
	        		changeOrder.doExecute('initTable');
	        		
	        		$("#normalContract").bind('click',function(){
						$("#normalContract").addClass("sel");
						$("#relationContract").removeClass("sel");
						 $("#uicTable").tableOptions({
	        		    		pageNo : '1',
	        		    		addparams:[]
	        		    	});
	        		    	$("#uicTable").tableReload();
	        		    	changeOrder.methods.initTable();
					});
					$("#relationContract").bind('click',function(){
						$("#relationContract").addClass("sel");
						$("#normalContract").removeClass("sel");
						 $("#uicTable2").tableOptions({
	        		    		pageNo : '1',
	        		    		addparams:[]
	        		    	});
	        		    	$("#uicTable2").tableReload();
	        		    	changeOrder.methods.initTable2();
					});
	        		
		        },
		        initTable : function(){
		        	$("#uicTable2").hide();
		        	$("#uicTable").show();
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#"+changeOrder.config.uicTable).uicTable({
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		buttons : [
		        			],
		        		searchItems:'name',
	    			    columns:[
	    			        { code:'creator',name : '申请人',process:changeOrder.methods.handsHandler,width:'20%'},
		        			{ code:'orderName',name : '订单名称'/*,process:changeOrder.methods.links*/,width:'20%'},
		        			{ code:'creatTime', name :'申请时间',width:'20%'},
			        ],
			        url: ctx+'/business/changeOrder/getTableGrid?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:10,
			        onLoadFinish:function(){
			        	/*$('.changeOrder_handler').popover({
			        		trigger :'focus',
			        		placement:'right',
			        		html:true	
			        	}).click(function(e) {
					        e.preventDefault();
					    });*/
			        },
			        searchTitle:'合同编号',
			        searchTip:'请输入合同编号',
			        searchFun:function(){
			        	$("#"+changeOrder.config.uicTable).tableOptions({
			        		pageNo : '1',
			        		addparams:[{
			        			name:"test",
			        			value:$('.searchs').find('input').first().val()
			        		}]
			        	});
			        	$("#"+changeOrder.config.uicTable).tableReload();
			        },
			        advancedFun:changeOrder.methods.advancedFun
			        });
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	/*$(".advancedSearch",$("#"+changeOrder.config.uicTable)).load(ctx+changeOrder.config.namespace+'/search?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			changeOrder.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			changeOrder.doExecute('reload');
		        		});
		        	});*/
		        	$(".advancedSearch",$("#"+changeOrder.config.uicTable)).css("height","auto");
		        	$('.doAdvancedSearch').hide();

		        },
		        initTable2:function(){
					$("#uicTable").hide();
		        	$("#uicTable2").show();
					var grid=$("#uicTable2").uicTable({
		        		title : "",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		//searchItems:'name',
		        		buttons : [
		 		        		],
	    			    columns:[
	    			        { code:'bizOwner', name :' 商务负责人',process:changeOrder.methods.bizOwner,width:'15%'},
	    			        { code: 'supplierType',name : '供应商类型',process:changeOrder.methods.supplierTypeStr,width:'10%'},
	 		        		{ code: 'supplierCode',name : '供应商编号',width:'10%'},
	 		        		{ code:'shortName', name :'公司简称',process:changeOrder.methods.handler,width:'20%'},												
	 		        		{ code:'phone', name :'电话',width:'13%'},										
	 		        		{ code:'fax', name :' 传真',width:'12%'}	,																							
	 		        		{ code:'web', name :' 网址',width:'20%'}		
	    			    ],
	    			    url: ctx+'/business/supplierm/supplierInfo/getListApprove?tmp='+Math.random(),
	    			    pageNo:1,
	    			    pageSize:20,
	    			   /* showcheckbox : true,
	    			    moreCellShow : true,
	    			    sortname : 'SalesStartDate',*/
	    			    sortorder : 'desc',
	    			    htmlSort : false,
	    			    /*searchFun:function(){
				        	$("#uicTable2").tableOptions({
				        		pageNo : '1',
				        		addparams:[{
				        			name:"test",
				        			value:$('.searchs').find('input').first().val()
				        		}]
				        	});
				        	$("#uicTable2").tableReload();
				        },*/
				        });
			        	$('.searchs').find('input').first().hide();
			        	$('.searchs').find('label').first().hide();
			        	$('.doSearch').hide();
			        	$('.doAdvancedSearch').hide();
			        	$('.operation').hide();
			        	//默认触发了搜索
			        	/*$('.doAdvancedSearch').click();*/
			        	//$(".advancedSearch").show();
			        	//加载查询
//			        	$(".advancedSearch",$("#uicTable2")).load(ctx+cost.config.namespace+'/specilSearch?tmp='+Math.random(),function(){
//			        		$(".advancedSearch",$("#uicTable2")).css("height","auto");
//			        		//绑定“查询”按钮
//			        		$("#advancedSearch_btn2").unbind('click').click(function () {
//			        			cost.methods.doAdvancedFun2();
//				        	});
//			        	});
				},
		     
		        handsHandler : function(divInner, trid, row, count){
		        	var str = "";
		        	str+=row.creator;
		        	str+="<span style='margin-left:10px;'><a href='"+ctx+"/business/changeOrder/settlement?id="+row.id+"' name='order_handler'  target='_blank' class='_edit'><i class='icon-edit'></i>审批</a></span>";
		        	return str;
		        },
		    
		        advancedFun:function(type) {
		        	if(type){
		        		$(".advancedSearch",$("#"+changeOrder.config.uicTable)).css("height","auto");
		        		
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			changeOrder.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			changeOrder.doExecute('reload');
		        		});
		        		
		        	} else {
		        	}
		        },
		        doAdvancedFun:function(){
		        	$("#"+changeOrder.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"test",
		        			value:1
		        		}]
		        	});
		        	$("#"+changeOrder.config.uicTable).tableReload();
		        },
		        
		        supplierTypeStr : function(divInner, trid, row, count){
		        	var supplierType = "";
					for(var i in supplierTypeJson){
						if(row.supplierType == supplierTypeJson[i].id){
							supplierType += supplierTypeJson[i].name;
						}
					}
					return supplierType;
			
		        },
		        
		        bizOwner : function(divInner, trid, row, count){
		        	var bizOwner = "";
		        	var spstr="<span style='margin-left:10px;'><a href='"+ctx+"/business/supplierm/supplierInfo/supplierApprove?id="+row.id+"' name='order_handler'  target='_blank' class='_edit'><i class='icon-edit'></i>审批</a></span>";
		        	for(var i in bizOwnerJson){
						if(row.bizOwner == bizOwnerJson[i].id){
							bizOwner += bizOwnerJson[i].name;
						}
					}
					return bizOwner+=spstr;
		        },
		        
		        
		        reload : function(){
		        	var sd=$("#"+changeOrder.config.uicTable).getTableCheckedRows();
		    		for(var i=0;i<sd.length;i++){
		    		}
		        	$("#"+changeOrder.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+changeOrder.config.uicTable).tableReload();
		        },
				links : function(divInner, trid, row, count) {
			      	var str = "";
		        	str+='<a style="color:#005EA7;" href="'+ctx+'/business/order/detail?id=' + row.orderId + '" target="_blank">'+row.orderName+'<font class="blue02"></a>';							
		        	return str;
				},
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = changeOrder.config.namespace + opts.url;
		    		if(opts.id){
		    			options.keyName = 'id';
		    			options.keyValue = opts.id;
		    		}
		    		window.open(options);
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =changeOrder.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		changeOrder.doExecute('initDocument');
	}
	/*//子页面关闭刷新该父页面
	childColseRefresh=function(){
		changeOrder.doExecute('initDocument');
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