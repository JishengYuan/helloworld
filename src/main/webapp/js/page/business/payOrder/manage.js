define(function(require, exports, module) {
	var $ = require("jquery");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
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
	
	var paymentTypeJson = {};
	var statusTypeJson = {};
	var pay = {
			config: {
				module: 'pay',
				uicTable : 'uicTable',
	            namespace: '/business/payOrder'
	        },
	        
	        methods :{
	        	initDocument : function(){
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=coursesType&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				paymentTypeJson = msg;
		    			}
		    		});
	        		
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=planStatus&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				statusTypeJson = msg;
		    			}
		    		});
	        		pay.doExecute('initTable');
	        		/*
        			 *键盘点击事件
        			 */
        			document.onkeydown = function(event) {
        				var e = event || window.event || arguments.callee.caller.arguments[0];
        				if (e && e.keyCode == 13) { // enter 键
        					pay.doExecute('doAdvancedFun');
        				}

        			}
		        },
		       
		        initTable : function(){
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#"+pay.config.uicTable).uicTable({
		        		title : "商务管理-->付款申请",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		buttons : [
		                  /*  { name: '添加', butclass: 'doAdd',onpress: payment.methods.toolbarItem},
		                    {  name: '删除', butclass: 'doDel',onpress: payment.methods.toolbarItem},*/
		        			],searchItems:'name',
	    			    columns:[
	    			        { code:'PayApplyName',name : '付款申请名称',process:pay.methods.handsHandler,width:'16%'},
		        			{ code:'CoursesType', name :'科目类型',process:pay.methods.coursesType,width:'7%'},												
		        			{ code:'PlanPayDate', name :'计划付款时间',width:'10%'},
		        			{ code:'PayAmonut', name :'付款金额',process:pay.methods.payAmountStr,width:'11%'},	
		        			{ code:'RealPayDate', name :'实际付款时间',width:'10%'},	
		        			{ code:'ShortName', name :'供应商简称',width:'10%'},
		        			{ code:'PlanStatus', name :'状态',process:pay.methods.paytatus,width:'10%'}	
			        ],
			        
			        url: ctx+pay.config.namespace+'/getTableGrid?&tmp='+Math.random(),
			        pageNo:1,
			        pageSize:10,
			        onLoadFinish:function(){
			        	$(".showbody").css("overflow","visible");
			        },
			       
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
			        advancedFun:pay.methods.advancedFunction
		         });
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	$('.doAdvancedSearch').hide();
		        	$('.doAdvancedSearch').click();
		        	$('.operation').hide();
		        	$(".advancedSearch",$("#"+pay.config.uicTable)).load(ctx+pay.config.namespace+'/search?tmp='+Math.random(),function(){
		        		$(".advancedSearch",$("#"+pay.config.uicTable)).css("height","auto");
		        		//绑定“查询”按钮
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			pay.methods.doAdvancedFun();
			        	});
		        		//绑定“重置”按钮
		        		$("#reset_btn").unbind('click').click(function () {
		        			pay.doExecute('reload');
		        		});
			        	//绑定“添加”按钮
		        		$("#addOrder_btn").unbind('click').click(function () {
		        			pay.methods.toAddPayPage();
			        	});
		        		//绑定“删除”按钮
		        		$("#removeOrder_btn").unbind('click').click(function () {
		        			pay.methods.removePay();
			        	});
		        	});
		        	
		        },
		        
		        removePay:function(){/*
		        	var ids=$("#"+pay.config.uicTable).getTableCheckedRows();
		    		if(ids.length==0){
	    				UicDialog.Error("请选择删除条目！");
	    				return;
		    		}
	        		var orderId ='';
		    		for(var i=0;i<ids.length;i++){
		    			var name=ids[i].orderName;
		    			var orderState=ids[i].payStatus;
		    			if(orderState!='CG'){
		    				UicDialog.Error(name+"<br/>已提交审批，无法删除！");
		    				return;
		    			}else{
		    				orderId +=ids[i].id+",";
		    			}
		    		}
		    		var datas={'id':orderId};
				    var url = ctx+'/business/payment/remove?tmp='+Math.random();
				    $.post(url,datas,
				    		function(data,status){
				            	if(status="success"){
				                	  UicDialog.Success("删除付款申请成功!",function(){
				                	  $("#"+pay.config.uicTable).tableReload();
				                	  });
				                  }else{
				                  	  UicDialog.Error("删除付款申请失败！");
				                  	  $("#"+pay.config.uicTable).tableReload();
				                  }
				   });   */
		    	},
		        coursesType : function(divInner, trid, row, count){
		        	var coursesType = "";
					for(var i in paymentTypeJson){
						if(row.CoursesType == paymentTypeJson[i].id){
							coursesType += paymentTypeJson[i].name;
						}
					}
					return coursesType;
		        },
		        payAmountStr: function(divInner, trid, row, count){
		        	if(row.currency=='usd'){
		        		return "$"+fmoney(divInner,2);
		        	}else{
		        		return "￥"+fmoney(divInner,2);
		        	}
		        	
		        },
		        paytatus : function(divInner, trid, row, count){
		        	var paytatus = "";
					for(var i in statusTypeJson){
						if(row.PlanStatus == statusTypeJson[i].id){
							paytatus += statusTypeJson[i].name;
						}
					}
					if(row.NAME_!=null){
						paytatus += "("+row.NAME_+")"
					}
					return paytatus;
		        },
		        handsHandler : function(divInner, trid, row, count){
		        	var str = "";
		        		str+='<a style="color:#005EA7;" href="'+ctx+'/business/payOrder/payDetail?id=' + row.id +'" target="_blank" >'+row.PayApplyName+'<font class="blue02"></a>';							
		        	return str;
		        },
		        advancedFunction:function(type) {
		        	if(type){
		        		$(".advancedSearch",$("#"+pay.config.uicTable)).css("height","auto");
		        		//绑定“查询”按钮
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			pay.methods.doAdvancedFun();
			        	});
		        		//绑定“重置”按钮
		        		$("#reset_btn").unbind('click').click(function () {
		        			pay.doExecute('reload');
		        		});
			        	//绑定“添加合同”按钮
		        		$("#addOrder_btn").unbind('click').click(function () {
		        			pay.doExecute('toAddPayPage');
			        	});
		        		//绑定“删除合同”按钮
		        		$("#removeOrder_btn").unbind('click').click(function () {
		        			pay.methods.removeOrder();
			        	});
		        	} else {
		        	}
		        },
		        toAddPayPage:function(){
	    			var url=ctx+'/business/payOrder/addPay?tmp='+Math.random();
	    			window.open(url,'_blank');
		        },
		        doAdvancedFun:function(){
		        	var closeTime=$('#search_closeTime').val();
		        	var amount=$('#search_amount').val();
	        		var orderId = "";
	        		var myCheckbox = $("input[name='isAgree']:checked");
	        		var supplierShortName = $('#search_supplierShortName').val();
	        		var amountPart = $("#search_amountPart").val();
	        		myCheckbox.each(function(i,ele){//循环拼装被选中项的值
	        			if($(ele).val() != 'null'){
	        				orderId = orderId+$(this).val()+",";
	        			}
	        		 });
		        	$("#"+pay.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"closeTime",
		        			value:closeTime
		        		},{
		        			name:"amount",
		        			value:amount
		        		},{
		        			name:"supplierShortName",
		        			value:supplierShortName
		        		},{
		        			name:"amountPart",
		        			value:amountPart
		        		},{
		        			name:"orderId",
		        			value:orderId
		        			
		        		}]
		        	});
		        	$("#"+pay.config.uicTable).tableReload();
		        },
		        reload : function(){
		        	$("#"+pay.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+pay.config.uicTable).tableReload();
		        },
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = pay.config.namespace + opts.url;
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
	        	var method =pay.methods[flag];
	        	if( typeof method === 'function') {
	        		return method(param);
	        	} else {
	        		alert('操作 ' + flag + ' 暂未实现！');
	        	}
	        }
	}
	exports.init = function() {
		pay.doExecute('initDocument');
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