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
	var paymentTypeJson = {};
	reload = function(){
		$("#"+payment.config.uicTable).tableOptions({
    		pageNo : '1',
    		addparams:[]
    	});
    	$("#"+payment.config.uicTable).tableReload();
    	$("#uicTable2").tableOptions({
    		pageNo : '1',
    		addparams:[]
    	});
    	$("#uicTable2").tableReload();
	}
	var payment = {
			config: {
				module: 'payment',
	            //namespace: '/business/order',
	            uicTable : 'processDiv',
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
	        		payment.doExecute('initTable');
	        	
	        		 $("#reim").bind('click',function(){
	        			 $("#payment").removeClass("sel");
	        			 $("#amount").hide();
	        			 $("#reim").addClass("sel");
	        			 $("#processDiv2").tableOptions({
	        		    		pageNo : '1',
	        		    		addparams:[]
	        		    	});
	        		    	$("#processDiv2").tableReload();
	        			 payment.doExecute('initTable2');
			         });
	        		 $("#payment").bind('click',function(){
	        			 $("#reim").removeClass("sel");
	        			 $("#payment").addClass("sel");
	        			 $("#amount").show();
	        			 $("#processDiv").tableOptions({
	        		    		pageNo : '1',
	        		    		addparams:[]
	        		    	});
	        		    	$("#processDiv").tableReload();
	        			 payment.doExecute('initTable');
			         });
	        		 /*
	        			 *键盘点击事件
	        			 */
	        			document.onkeydown = function(event) {
	        				var e = event || window.event || arguments.callee.caller.arguments[0];
	        				if (e && e.keyCode == 13) { // enter 键
	        					if($("#processDiv").is(":visible")==true){
	        						payment.doExecute('doAdvancedFun');
	        					}else{
	        						payment.doExecute('doAdvancedFun2');
	        					}
	        		  			
	        				}

	        			}
		        },
		       
		        initTable : function(){
		        	$("#processDiv2").hide();
		        	$("#processDiv").show();
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#processDiv").uicTable({
		        		title : "",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
						searchItems:'name',
	    			    columns:[
	    			        { code:'PayApplyName',name :'付款单名称',process:payment.methods.handler,width:'18%'},
		        			{ code:'CoursesType', name :'科目类型',process:payment.methods.coursesType,width:'10%'},												
		        			{ code:'PayAmonut', name :'付款金额',process:payment.methods.payAmountStr,width:'15%'},	
		        			{ code:'PlanPayDate', name :'计划付款时间',width:'10%'},
		        			{ code:'RealPayDate', name :'实际付款时间',width:'10%'},
		        			{ code:'PlanStatus', name :'付款状态',process:payment.methods.paytatus,width:'15%'}
			        ],
			        
			        url: ctx+'/business/payOrder/getAllPay?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:10,
			        onLoadFinish:function(){
			        	var totallAmount = $("#"+payment.config.uicTable).getJsonData().totallamount;
			        	$('#totallAmount').html("￥"+fmoney(totallAmount,2));
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
		        	$(".advancedSearch",$("#"+payment.config.uicTable)).load(ctx+'/business/payOrder/searchCostPay?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			payment.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			$("#_pay_name").val("");
		        			$("#search_endTime").val("");
							$("#search_startTime").val("");
							$("#_pay_min").val("");
							$("#_pay_max").val("");
							
							$("#supplierShortName").formSelect('setValue',"");
							$("#search_supplierShortName").val("");
		        			
							
							$("#"+payment.config.uicTable).tableOptions({
				        		pageNo : '1',
				        		addparams:[]
				        	});
				        	$("#"+payment.config.uicTable).tableReload();
		        		});
		        		$(".advancedSearch",$("#"+payment.config.uicTable)).css("height","auto");
		        	});
		        },
		        
		        initTable2 : function(){
		        	$("#processDiv").hide();
		        	$("#processDiv2").show();
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#processDiv2").uicTable({
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
						searchItems:'name',
	    			    columns:[
	    			        { code:'reimbursementName',name : '报销单名称',process:payment.methods.handler2,width:'18%'},
		        			{ code:'amount', name :'报销金额',process:payment.methods.payAmountStr2,width:'15%'},	
		        			{ code:'createTime', name :'计划报销时间',width:'15%'},
		        			{ code:'closeTime', name :'实际报销时间',width:'15%'}	
		        			/*{ code:'reimStatus', name :'状态',process:payment.methods.reimStatus,width:'10%'}	*/
			        ],
			        url: ctx+'/business/rbmApply/getAllReim?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:13,
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
		        	$(".advancedSearch",$("#processDiv2")).load(ctx+'/business/rbmApply/searchCostReim?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn2").unbind('click').click(function () {
		        			payment.doExecute('doAdvancedFun2');
		        		});
		        		$("#reset_btn2").unbind('click').click(function () {
		        			$("#_reim_max").val("");
		        			$("#_reim_min").val("");
		        			$("#search_startdate").val("");
							$("#search_enddate").val("");
							$("#processDiv2").tableOptions({
				        		pageNo : '1',
				        		addparams:[]
				        	});
				        	$("#processDiv2").tableReload();
		        		});
		        		$(".advancedSearch",$("#processDiv2")).css("height","auto");
		        	});
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
		        handler:function(divInner,trid,row,count){
		        	var str="";
		        	str+='<a style="color:#005EA7;" href="'+ctx+'/business/payOrder/payDetail?id='+row.id+'" target="_blank" name="_payApplyName">'+row.PayApplyName+'</a>';
		        	return str;
		        },
		        handler2:function(divInner,trid,row,count){
		        	var str="";
		        	str+='<a style="color:#005EA7;" href="'+ctx+'/business/rbmApply/detail?id='+row.id+'" target="_blank" name="_reimbursementName">'+row.reimbursementName+'</a>';
		        	return str;
		        },
		        payAmountStr :function(divInner,trid,row,count){
		        	var str = "$";
		        	if(row.Currency=='cny'){
		        		str = "￥"
		        	}
		        	return str+fmoney(divInner,2);
		        },
		        payAmountStr2 :function(divInner,trid,row,count){
		        	return "￥"+fmoney(divInner,2);
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
		        doAdvancedFun:function(){
		        	$('#totallAmount').html("￥"+0);
        			var supplierShortName = $("#search_supplierShortName").val();
					var startTime = $("#search_startTime").val();
					var endTime = $("#search_endTime").val();
					var minAmount = $("#_pay_min").val();
					var maxAmount = $("#_pay_max").val();
					var payName=$("#_pay_name").val();
					
					$("#"+payment.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"supplierShortName",
		        			value:supplierShortName
		        		},{
		        			name:"startTime",
		        			value:startTime
		        		},{
		        			name:"endTime",
		        			value:endTime
		        		},{
		        			name:"payName",
		        			value:payName
		        		},{
		        			name:"minAmount",
		        			value:minAmount
		        		},{
		        			name:"maxAmount",
		        			value:maxAmount
		        		}]
		        	});
		        	$("#"+payment.config.uicTable).tableReload();
		        },
		        doAdvancedFun2 : function(){
		        	var startdate = $("#search_startdate").val();
					var enddate = $("#search_enddate").val();
					var reimminAmount = $("#_reim_min").val();
					var reimmaxAmount = $("#_reim_max").val();
					$("#processDiv2").tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"startdate",
		        			value:startdate
		        		},{
		        			name:"enddate",
		        			value:enddate
		        		},{
		        			name:"reimminAmount",
		        			value:reimminAmount
		        		},{
		        			name:"reimmaxAmount",
		        			value:reimmaxAmount
		        		}]
		        	});
		        	$("#processDiv2").tableReload();
		        },
		        openurl : function(opts){
		        	var options = {};
		    		options.murl ="business/order/costControl/manage" +  opts.url;
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