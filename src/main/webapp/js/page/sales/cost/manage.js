define(function (require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("stringHelper");
	require("internetTable");
	require("confirm_dialog");
	require("uic/message_dialog");
	var StringBuffer = require("stringBuffer");
	var DT_bootstrap = require("DT_bootstrap");
	
	var cost={
			config:{
				module: 'cost',
				uicTable : 'uicTable',
	            namespace: '/sales/cost'
			},
			methods:{
				initDocument:function(){
					/*$("#insertCost").unbind('click').click(function(){
						cost.methods.getInsertCost();
					});*/
					
					cost.methods.initTable();
					$("#normalContract").bind('click',function(){
						$("#normalContract").addClass("sel");
						$("#relationContract").removeClass("sel");
						 $("#uicTable").tableOptions({
	        		    		pageNo : '1',
	        		    		addparams:[]
	        		    	});
	        		    	$("#uicTable").tableReload();
	        		    	cost.methods.initTable();
					});
					$("#relationContract").bind('click',function(){
						$("#relationContract").addClass("sel");
						$("#normalContract").removeClass("sel");
						 $("#uicTable2").tableOptions({
	        		    		pageNo : '1',
	        		    		addparams:[]
	        		    	});
	        		    	$("#uicTable2").tableReload();
	        		    	cost.methods.initTable2();
					});
				},
				initTable:function(){
					$("#uicTable2").hide();
		        	$("#uicTable").show();
					var grid=$("#"+cost.config.uicTable).uicTable({
		        		title : "",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		searchItems:'name',
		        		buttons : [
		 		                  /*  { name: '添加', butclass: 'doAdd',onpress: payment.methods.toolbarItem},
		 		                    {  name: '删除', butclass: 'doDel',onpress: payment.methods.toolbarItem},*/
		 		        			],
	    			    columns:[
	    			             [{ code: 'ContractName',name : '合同名称',width:'28%'},
	    			             { code: 'ContractCode',name : '合同编号',process:cost.methods.links,width:'28%'}],
	    			             [{ code: 'ContractAmount',name : '合同金额',process:cost.methods.conAmountStr,width:'12%'}],
	    			             [{ code: 'CreatorName',name : '销售',width:'10%',sort : true}],
	    			             [{ code: 'orderPay',name : '付款金额',process:cost.methods.orderPayStr,width:'15%'},
	    			             { code: 'salesReceive',name : '回款金额',process:cost.methods.salesRecStr,width:'15%',sort : true}],
	    			             [{ code: 'cost',name : '资金占用成本',width:'12%',process:cost.methods.costStr,sort : true}],
	    			             [{ code: 'CreateTime',name : '计算日期',width:'10%',sort : true}],
	    			             [{ code: '',name : '操作',width:'6%',process:cost.methods.choice}],
	    			             ],
	    			    url: ctx+cost.config.namespace+'/getList?tmp='+Math.random(),
	    			    pageNo:1,
	    			    pageSize:20,
	    			    showcheckbox : false,
	    			    moreCellShow : true,
	    			    htmlSort : false,
	    			    sortname : 'SalesStartDate',
	    			    sortorder : 'desc',
	    			    searchFun:function(){
				        	$("#"+cost.config.uicTable).tableOptions({
				        		pageNo : '1',
				        		addparams:[{
				        			name:"test",
				        			value:$('.searchs').find('input').first().val()
				        		}]
				        	});
				        	$("#"+cost.config.uicTable).tableReload();
				        },
				        });
			        	$('.searchs').find('input').first().hide();
			        	$('.searchs').find('label').first().hide();
			        	$('.doSearch').hide();
			        	$('.doAdvancedSearch').hide();
			        	$('.operation').hide();
			        	//默认触发了搜索
			        	/*$('.doAdvancedSearch').click();*/
			        	$(".advancedSearch").show();
			        	//加载查询
			        	$(".advancedSearch",$("#"+cost.config.uicTable)).load(ctx+cost.config.namespace+'/search?tmp='+Math.random(),function(){
			        		$(".advancedSearch",$("#"+cost.config.uicTable)).css("height","auto");
			        		//绑定“查询”按钮
			        		$("#advancedSearch_btn").unbind('click').click(function () {
			        			cost.methods.doAdvancedFun();
				        	});
			        		//绑定“查询”按钮
			        		$("#insertCost").unbind('click').click(function () {
			        			cost.methods.getInsertCost();
				        	});
			        		
			        	});
				},
				initTable2:function(){
					$("#uicTable").hide();
		        	$("#uicTable2").show();
					var grid=$("#uicTable2").uicTable({
		        		title : "",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		searchItems:'name',
		        		buttons : [
		 		                  /*  { name: '添加', butclass: 'doAdd',onpress: payment.methods.toolbarItem},
		 		                    {  name: '删除', butclass: 'doDel',onpress: payment.methods.toolbarItem},*/
		 		        			],
	    			    columns:[
	    			             	 [{ code: 'ContractName',name : '合同名称',width:'28%'},
		    			             { code: 'ContractCode',name : '合同编号',process:cost.methods.links,width:'28%'}],
		    			             [{ code: 'ContractAmount',name : '合同金额',process:cost.methods.conAmountStr,width:'12%'}],
		    			             [{ code: 'CreatorName',name : '销售',width:'10%',sort : true}],
		    			             [{ code: 'orderPay',name : '付款金额',process:cost.methods.orderPayStr,width:'15%'},
		    			             { code: 'salesReceive',name : '回款金额',process:cost.methods.salesRecStr,width:'15%',sort : true}],
		    			             [{ code: 'cost',name : '资金占用成本',width:'12%',process:cost.methods.costStr,sort : true}],
		    			             [{ code: 'CreateTime',name : '计算日期',width:'10%',sort : true}],
		    			             [{ code: '',name : '操作',width:'6%',process:cost.methods.choice}],
	    			             
	    			             ],
	    			    url: ctx+cost.config.namespace+'/getSpecialList?tmp='+Math.random(),
	    			    pageNo:1,
	    			    pageSize:20,
	    			    showcheckbox : true,
	    			    moreCellShow : true,
	    			    sortname : 'SalesStartDate',
	    			    sortorder : 'desc',
	    			    htmlSort : false,
	    			    searchFun:function(){
				        	$("#uicTable2").tableOptions({
				        		pageNo : '1',
				        		addparams:[{
				        			name:"test",
				        			value:$('.searchs').find('input').first().val()
				        		}]
				        	});
				        	$("#uicTable2").tableReload();
				        },
				        });
			        	$('.searchs').find('input').first().hide();
			        	$('.searchs').find('label').first().hide();
			        	$('.doSearch').hide();
			        	$('.doAdvancedSearch').hide();
			        	$('.operation').hide();
			        	//默认触发了搜索
			        	/*$('.doAdvancedSearch').click();*/
			        	$(".advancedSearch").show();
			        	//加载查询
			        	$(".advancedSearch",$("#uicTable2")).load(ctx+cost.config.namespace+'/specilSearch?tmp='+Math.random(),function(){
			        		$(".advancedSearch",$("#uicTable2")).css("height","auto");
			        		//绑定“查询”按钮
			        		$("#advancedSearch_btn2").unbind('click').click(function () {
			        			cost.methods.doAdvancedFun2();
				        	});
			        	});
				},
				links : function(divInner, trid, row, count) {
			      	var str = "";
		        	str+='<a style="color:#005EA7;" href="'+ctx+'/sales/cost/detail?id='+row.id+'&time='+row.CreateTime+'" target="_blank" ">'+divInner+'</a>';							
		        	return str;
				},
				links2 : function(divInner, trid, row, count) {
			      	var str = "";
		        	str+='<a style="color:#005EA7;" href="'+ctx+'/sales/cost/detail?id='+row.id+'&time='+row.CreateTime+'" target="_blank" ">'+divInner+'</a>';							
		        	return str;
				},
				conAmountStr: function(divInner, trid, row, count) {
					var amount = divInner;
					if(amount!=""){
						return "￥"+ fmoney(divInner,2);
					}else{
						return "￥0.00";
					}
				},
				conAmountStr2: function(divInner, trid, row, count) {
					var amount = divInner;
					if(amount!=""){
						return "￥"+ fmoney(divInner,2);
					}else{
						return "￥0.00";
					}
				},
				choice: function(divInner, trid, row, count) {
					var str = "";
					str+='<a style="color:#005EA7;" href="'+ctx+'/sales/cost/update?id='+row.id+'&time='+row.CreateTime+'" target="_blank" ">修改</a>';
					return str;
				},
				choice2: function(divInner, trid, row, count) {
					var str = "";
					str+='<a style="color:#005EA7;" href="'+ctx+'/sales/cost/update?id='+row.id+'&time='+row.CreateTime+'" target="_blank" ">修改</a>';
					return str;
				},
				orderPayStr: function(divInner, trid, row, count) {
					return "￥"+ fmoney(divInner,2);
				},
				orderPayStr2: function(divInner, trid, row, count) {
					return "￥"+ fmoney(divInner,2);
				},
				salesRecStr: function(divInner, trid, row, count) {
					var amount = divInner;
					if(amount!=""){
						return "￥"+ fmoney(divInner,2);
					}else{
						return "￥0.00";
					}
					//return divInner;
				},
				salesRecStr2: function(divInner, trid, row, count) {
					var amount = divInner;
					if(amount!=""){
						return "￥"+ fmoney(divInner,2);
					}else{
						return "￥0.00";
					}
					//return divInner;
				},
				costStr: function(divInner, trid, row, count) {
					return "￥"+ fmoney(divInner,2);
				},
				costStr2: function(divInner, trid, row, count) {
					return "￥"+ fmoney(divInner,2);
				},
				
					doAdvancedFun:function(){
			        	var time = $('#search_time').val();
			        	var timeEnd = $('#search_timeEnd').val();
			        	var salesCode = $('#search_salesCode').val();
			        	var salesName = $('#search_salesName').val();
			        	var salesAmount = $('#search_salesAmount').val();
			        	var salesCreator = $("#search_creator").formUser("getValue")

			        	$("#"+cost.config.uicTable).tableOptions({
			        		pageNo : '1',
			        		addparams:[{name:"time",value:time},
			        		           {name:"timeEnd",value:timeEnd},
			        		           {name:"salesCode",value:salesCode},
			        		           {name:"salesName",value:salesName},
			        		           {name:"salesAmount",value:salesAmount},
			        		           {name:"salesCreator",value:salesCreator},
			        		           ],
			        	});
			        	$("#"+cost.config.uicTable).tableReload();
			        },
			        doAdvancedFun2:function(){
			        	var time2 = $('#search_time2').val();
			        	var timeEnd2 = $('#search_timeEnd2').val();
			        	var salesCode2 = $('#search_salesCode2').val();
			        	var salesName2 = $('#search_salesName2').val();
			        	var salesAmount2 = $('#search_salesAmount2').val();
			        	var salesCreator2 = $("#search_creator2").formUser("getValue")
			        	
			        	
			        	
			        	$("#uicTable2").tableOptions({
			        		pageNo : '1',
			        		addparams:[{name:"time",value:time2},
			        		           {name:"timeEnd",value:timeEnd2},
			        		           {name:"salesCode",value:salesCode2},
			        		           {name:"salesName",value:salesName2},
			        		           {name:"salesAmount",value:salesAmount2},
			        		           {name:"salesCreator",value:salesCreator2},
			        		           ],
			        	});
			        	$("#uicTable2").tableReload();
			        },
			        getInsertCost:function(){
			        	var frameSrc = ctx+"/sales/cost/selectTime";
						$('#dailogs1').on('show', function () {
							$('#dtitle').html("选择时间");
							$('#dialogbody').load(frameSrc,function(){
							}); 
						       $("#dsave").unbind('click');
						       $('#dsave').click(function () {
						    	 $('#editForm').submit();
							});
						});
						 	$('#dailogs1').on('hidden', function () {
						 		$("#"+cost.config.uicTable).tableOptions({
					        		pageNo : '1',
					        		addparams:[
					        		           ],
					        	});
					        	$("#"+cost.config.uicTable).tableReload();
						 		$('#dailogs1').unbind("show");});
							$('#dailogs1').modal({show:true});
							$('#dailogs1').off('show');
						/*$.ajax({
	    					url: ctx+"/sales/cost/getInsertSalesCost?tmp="+Math.random(),  // 提交的页面
	    		            data: "", // 从表单中获取数据
	    		            async : false,
	    		            type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
	    		            error: function(request) {     // 设置表单提交出错
	    		            },
	    		            success: function(data) {
	    		            	UicDialog.Success("添加完成！");
	        		            $("#"+cost.config.uicTable).tableOptions({
	        		        		pageNo : '1',
	        		        		addparams:[]
	        		        	});
	        		        	$("#"+cost.config.uicTable).tableReload();
	    		            }
	    		            
						});*/
					},
			},
	}
	exports.init = function() {
		cost.methods.initDocument();  
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
	
