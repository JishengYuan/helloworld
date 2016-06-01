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
	var closingSales = {};
	reload = function(){
		$("#"+contract.config.uicTable).tableOptions({
    		pageNo : '1',
    		addparams:[]
    	});
    	$("#"+contract.config.uicTable).tableReload();
    	$("#uicTable2").tableOptions({
    		pageNo : '1',
    		addparams:[]
    	});
    	$("#uicTable2").tableReload();
	}
	var type=null;
	var contract = {
			config: {
				module: 'contract',
	            namespace: '/business/order'
	        },
	        methods :{
	        	initDocument : function(){
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/business/order/getClosingSales?tmp="+ Math.random(),
		    			success : function(msg) {
		    				closingSales = msg;
		    			}
		    		});
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=payStatus&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				payStatusJson = msg;
		    			}
		    		});
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=reimStatus&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				reimStatusJson = msg;
		    			}
		    		});
	        		contract.doExecute('initTable');
	        		 $("#orderEnd").bind('click',function(){
	        			 $("#orderStart").removeClass("sel");
	        			 $("#orderEnd").addClass("sel");
	        			 
	        			 $('#uicTable2').tableOptions({
								pageNo : '1',
								addparams :[]
							}).tableReload();
	        			 contract.doExecute('initTable2');
			         });
	        		 $("#orderStart").bind('click',function(){
	        			 type='start';
	        			 $("#orderEnd").removeClass("sel");
	        			 $("#orderStart").addClass("sel");
	        			 $('#uicTable').tableOptions({
								pageNo : '1',
								addparams : []
							}).tableReload();
	        			 contract.doExecute('initTable');
			         });
	        		 /*
        			 *键盘点击事件
        			 */
        			document.onkeydown = function(event) {
        				var e = event || window.event || arguments.callee.caller.arguments[0];
        				if (e && e.keyCode == 13) { // enter 键
        					if($("#uicTable").is(":visible")==true){
        						contract.doExecute('doAdvancedFun');
        					}else{
        						contract.doExecute('doAdvancedFun2');
        					}
        		  			
        				}

        			}
	        		 
	        	},
		        initTable : function(){
		        	$("#uicTable2").hide();
		        	$("#uicTable").show();
		        	var grid=$("#uicTable").uicTable({
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
						title : "",
		        		buttons : [
		        			],
		        			//searchItems:'name',
	    			    columns:[
	    			        { code:'contractCode',name : '合同编码',width:'23%'},
		        			{ code:'contractName',name : '合同名称',process:contract.methods.links1,width:'19%'},
		        			{ code:'orderName',name : '订单名称',process:contract.methods.orderNameLink,width:'18%'},
		        			{ code:'reimStatus', name :'订单报销',process:contract.methods.reimStatus,width:'10%'},
		        			{ code:'paymentStatus', name :'订单付款',process:contract.methods.payStatus,width:'10%'},	
		        			{ code:'arrivalStatus', name :'订单到货',width:'10%'}												
			        ],
			        url: ctx+'/business/order/getContractsList?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:13,
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
		        	$(".advancedSearch",$("#uicTable")).load(ctx+contract.config.namespace+'/waitContract/search?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			contract.methods.doAdvancedFun();
			        	});

		        		$("#reset_btn").unbind('click').click(function () {
		        			$("#search_contractName").attr("value","");
				        	$("#search_contractCode").attr("value","");
				        	$("#search_contractAmount").attr("value","");
				        	$("#uicTable").tableOptions({
				        		pageNo : '1',
				        		addparams:[]
				        	});
				        	$("#uicTable").tableReload();
		        		});
		        	});
		        	$(".advancedSearch",$("#uicTable")).css("height","auto");
		        },
		        initTable2 : function(){
		        	$("#uicTable").hide();
		        	$("#uicTable2").show();
		        	var grid=$("#uicTable2").uicTable({
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
						title : "",
		        		buttons : [
		        			],
		        			//searchItems:'name',
	    			    columns:[
	    			            { code:'contractCode',name : '合同编码',width:'28%',process:contract.methods.apply},
	 		        			{ code:'contractName',name : '合同名称',process:contract.methods.links1,width:'18%'},
	 		        			{ code:'orderName',name : '订单名称',width:'18%'},
	 		        			{ code:'paymentStatus', name :'订单付款',process:contract.methods.payStatus,width:'9%'},
	 		        			{ code:'reimStatus', name :'订单报销', process:contract.methods.reimStatus,width:'9%'},
	 		        			{ code:'arrivalStatus', name :'订单到货',width:'9%'},	
	 		        			{ code:'closeStatuse', name :'申请状态',width:'8%'}
			        ],
			        
			        url: ctx+'/business/order/getContractsList2?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:13,
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
		        	$(".advancedSearch",$("#uicTable2")).load(ctx+contract.config.namespace+'/waitContract/closeSearch?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn2").unbind('click').click(function () {
		        			contract.methods.doAdvancedFun2();
			        	});
		        		$("#reset_btn2").unbind('click').click(function () {
		        			$("#search_Name").attr("value","");
				        	$("#search_Code").attr("value","");
				        	$("#search_Amounts").attr("value","");
				        	$("#uicTable2").tableOptions({
				        		pageNo : '1',
				        		addparams:[]
				        	});
				        	$("#uicTable2").tableReload();
		        		});
		        	});
		        	$(".advancedSearch",$("#uicTable2")).css("height","auto");
		        },
		        apply : function(divInner, trid, row, count){
		        	var str = "";
		        	str+=row.contractCode;
		        	if(row.closeStatuse!='申请中'){
		        		str+="<span style='margin-left:10px;'><a href='"+ctx+"/business/order/Contractdetail?id="+row.id+"&close=yes' name='order_handler'  target='_blank' class='_edit'><i class='icon-edit'></i>申请关闭</a></span>";
		        	}
		        	return str;
		        },
				links1 : function(divInner, trid, row, count) {
					var title;
					if (row.procInstTitle == undefined) {
						title = '无标题';
					} else {
						title = row.procInstTitle;
					}
					//转换字符中的html标签
					var t=title.changeHtml();
			      	var str = "";
		        	str+='<a style="color:#005EA7;" href="'+ctx+'/business/order/Contractdetail?id=' + row.id + '&close=No " target="_blank">'+row.contractName+'<font class="blue02"></a>';							
		        	return str;
				},
				links2 : function(divInner, trid, row, count) {
					var title;
					if (row.procInstTitle == undefined) {
						title = '无标题';
					} else {
						title = row.procInstTitle;
					}
					//转换字符中的html标签
					var t=title.changeHtml();
			      	var str = "";
		        	str+='<a style="color:#005EA7;" href="'+ctx+'/business/order/Contractdetail?id=' + row.id + '&close=No " target="_blank">'+row.contractName+'<font class="blue02"></a>';							
		        	return str;
				},
				orderNameLink : function(divInner, trid, row, count){
					var str = "";
					if(row.orderName!='创建订单'){
			        	str+='<a style="color:#005EA7;" href="'+ctx+'/business/order/detail?code=' + row.orderCode + ' " target="_blank">'+row.orderName+'<font class="blue02"></a>';							
					}else{
						str+='<a style="color:#005EA7;" href="'+ctx+'/business/order/addOrder? " target="_blank">'+row.orderName+'<font class="blue02"></a>';	
					}
					return str;
				},
				contractState : function(divInner, trid, row, count){
					var contractState = "通过审批";
					for(var i in closingSales){
						if(closingSales[i].salesContractId == row.id){
							contractState = "已申请关闭"
						}
					}
		        	return contractState;
				},
				closeStatuse :function(divInner, trid, row, count){
					/*if(row.closeStatuse!='申请中'){
						var title=row.closeStatuse;
						//转换字符中的html标签
						var t=title.changeHtml();
				      	var str = "";
			        	//str+='<a style="color:#005EA7;" href="'+ctx+'/business/order/closeContractStatus?id=' + row.id + '">'+t+'<font class="blue02"></a>';	
			        	//str+='<a style="color:#005EA7;"role="button" id="close" class="aClose">'+t+'<font class="blue02"></a>';	
			        	str+='<a style="color:#005EA7;" href="#" onclick="closeContract('+row.id+')">'+t+'<font class="blue02"></a>';	
			        	//str+="<input type='submit' style='color:#005EA7;'  name='Submit' value='"+row.closeStatuse +"' onclick='closeContract()'>";
			        	return str;
					}else{
	        			return divInner;
	        		}*/
				},
				 payStatus : function(divInner, trid, row, count){
			        	var payStatus = "";
						for(var i in payStatusJson){
							if(row.paymentStatus == payStatusJson[i].id){
								payStatus += payStatusJson[i].name;
							}
						}
						return payStatus;
			        },
		        reimStatus : function(divInner, trid, row, count){
		        	var reimStatus = "";
					for(var i in reimStatusJson){
						if(row.reimStatus == reimStatusJson[i].id){
							reimStatus += reimStatusJson[i].name;
						}
					}
					return reimStatus;
		        },
		        doAdvancedFun : function(){
		        	var contractName = $('#search_contractName').val();
		        	var contractCode = $('#search_contractCode').val();
		        	var contractAmount = $('#search_contractAmount').val();
		        	
		        	$("#uicTable").tableOptions({
		        		pageNo : '1',
		        		addparams:[{name:"contractName",value:contractName},
		        		           {name:"contractCode",value:contractCode},
		        		           {name:"contractAmount",value:contractAmount}
		        		          ]
		        	});
		        	$("#uicTable").tableReload();
		        },
		        doAdvancedFun2 : function(){
		        	var contractName = $('#search_Name').val();
		        	var contractCode = $('#search_Code').val();
		        	var contractAmount = $('#search_Amounts').val();
		        	
		        	$("#uicTable2").tableOptions({
		        		pageNo : '1',
		        		addparams:[{name:"contractName",value:contractName},
		        		           {name:"contractCode",value:contractCode},
		        		           {name:"contractAmount",value:contractAmount}
		        		          ]
		        	});
		        	$("#uicTable2").tableReload();
		        },
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = contract.config.namespace + opts.url;
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
});