define(function(require, exports, module) {
	var $ = require("jquery");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
	require("uic/message_dialog");
	var StringBuffer = require("stringBuffer");
	//子页面关闭刷新该父页面
	childColseRefresh=function(){
		$("#uicTable").tableOptions({
			pageNo : '1',
			addparams:[]
			});
			$("#uicTable").tableReload();
    }
	
	function formatDate(now) { 
		var year=now.getYear(); 
		var month=now.getMonth()+1; 
		var date=now.getDate(); 
		var hour=now.getHours(); 
		var minute=now.getMinutes(); 
		var second=now.getSeconds(); 
		return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
		} 
	var statusTypeJson = {};
	var reimbursement = {
			config: {
				module: 'reimbursement',
				uicTable : 'uicTable',
	            namespace: '/business/rbmApply'
	        },
	        methods :{
	        	initDocument : function(){
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=reimBursStatus&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				statusTypeJson = msg;
		    			}
		    		});
	        		reimbursement.doExecute('initTable');
	        		/*
        			 *键盘点击事件
        			 */
        			document.onkeydown = function(event) {
        				var e = event || window.event || arguments.callee.caller.arguments[0];
        				if (e && e.keyCode == 13) { // enter 键
        					reimbursement.doExecute('doAdvancedFun');
        				}

        			}
		        },   
		        initTable : function(){
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#"+reimbursement.config.uicTable).uicTable({
		        		title : "商务成本-->订单发票报销",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		searchItems:'name',
	    			    columns:[ { code:'reimbursementName', name :'发票报销名称',process:reimbursement.methods.handsHandler,width:'18%'},												
	    			        			{ code:'amount', name :'报销金额',process:reimbursement.methods.amountStr,width:'12%'},												
	    			        			{ code:'reimbursementUser', name :'报销人',width:'10%'},
	    			        			{ code:'createTime', name :'申请时间',width:'10%'},
	    			        			{ code:'closeTime', name :'报销时间',width:'10%'},
	    			        			{ code:'supplierShortName', name :'供应商简称',width:'10%'},
	    			        			{ code:'reimBursStatus', name :'状态',process:reimbursement.methods.reimBursStatusStr,width:'12%'}												
	    			    ],
	    			    url: ctx+reimbursement.config.namespace+'/getList?tmp='+Math.random(),
	    			    pageNo:1,
	    			    pageSize:10,
	    			  //  moreCellShow : true,
	    			    onLoadFinish:function(){
				        	$(".showbody").css("overflow","visible");
				        },
	    			    id : 'salesreimbursementId',
				        searchFun:function(){
				        	$("#"+reimbursement.config.uicTable).tableOptions({
				        		pageNo : '1',
				        		addparams:[{
				        			name:"test",
				        			value:$('.searchs').find('input').first().val()
				        		}]
				        	});
				        	$("#"+reimbursement.config.uicTable).tableReload();
				        },
				        advancedFun:reimbursement.methods.advancedFunction
				        });
			        	$('.searchs').find('input').first().hide();
			        	$('.searchs').find('label').first().hide();
			        	$('.doSearch').hide();
			        	$('.doAdvancedSearch').hide();
			        	//默认触发了搜索
			        	$('.doAdvancedSearch').click();
			        	//加载查询
			        	$(".advancedSearch",$("#"+reimbursement.config.uicTable)).load(ctx+reimbursement.config.namespace+'/search?tmp='+Math.random(),function(){
			        		$(".advancedSearch",$("#"+reimbursement.config.uicTable)).css("height","auto");
			        		//绑定“查询”按钮
			        		$("#advancedSearch_btn").unbind('click').click(function () {
			        			reimbursement.methods.doAdvancedFun();
				        	});

				        	//绑定“添加合同”按钮
			        		$("#addApply_btn").unbind('click').click(function () {
			        			reimbursement.methods.toAddreimbursementPage();
				        	});
			
			        	});
		        },
		      
		        toAddreimbursementPage:function(){
	        		var opts = {};
	    			opts.url = "/add";
	    			reimbursement.methods.openurl(opts);
		        },
		    
		        handsHandler : function(divInner, trid, row, count){
		        	var str = "";
		        		str+='<a style="color:#005EA7;" href="'+ctx+'/business/rbmApply/detail?id=' + row.id +'" target="_blank" >'+row.reimbursementName+'<font class="blue02"></a>';							
		        	return str;
		        },
		        advancedFunction:function(type) {
		        	if(type){
		        		$(".advancedSearch",$("#"+reimbursement.config.uicTable)).css("height","auto");
		        		//绑定“查询”按钮
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			reimbursement.methods.doAdvancedFun();
			        	});

			        	//绑定“添加”按钮
		        		$("#addApply_btn").unbind('click').click(function () {
		        			reimbursement.methods.toAddreimbursementPage();
			        	});
	
		        	}
		        },
		        amountStr: function(divInner, trid, row, count){
		        	return "￥"+fmoney(divInner,2);
		        },
		        doAdvancedFun:function(){
		        	var reimbursementName = $('#search_reimbursementName').val();
		        	var reimbursementAmount = $('#search_reimbursementAmount').val();
		        	var myCheckbox = $("input[name='isAgree']:checked");
		        	var orderId="";
		        	myCheckbox.each(function(i,ele){//循环拼装被选中项的值
		        			if($(ele).val() != 'null'){
		        				orderId = orderId+$(this).val()+",";
		        			}
		        		 });
		        	var supplierShortName = $('#search_supplierShortName').val();
	        		var amountPart = $("#search_amountPart").val();
	        		
		        	$("#"+reimbursement.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{name:"reimbursementName",value:reimbursementName},
		        		           {name:"supplierShortName",value:supplierShortName},
		        		           {name:"amountPart",value:amountPart},
		        		           {name:"orderId",value:orderId},
		        		           {name:"reimbursementAmount",value:reimbursementAmount}]
		        	});
		        	$("#"+reimbursement.config.uicTable).tableReload();
		        },
		        reload : function(){
		        	var sd=$("#"+reimbursement.config.uicTable).getTableCheckedRows();
		        	$("#"+reimbursement.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+reimbursement.config.uicTable).tableReload();
		        },
		        cleanSearchInput : function(){
		        	$("#search_reimbursementName").attr("value","");
		        	$("#search_reimbursementAmount").attr("value","");
		        },
				links : function(divInner, trid, row, count) {
					return divInner;
					
				},
				greyStyle:function(divInner, trid, row, count){
					var m =  /^\d+(\.\d+)?$/;
					var amount = row.reciveStatus.split("：");
					if(m.test(amount[1])){
						return '<font class="grey03">'+amount[0]+'：收'+amount[1]+'</font>';
					} else {
						return '<font class="grey03">'+divInner+'</font>';
					}
					
				},
				reimbursementAmount:function(divInner, trid, row, count){
					return "￥"+fmoney(divInner,2);
				},

				reimBursStatusStr : function(divInner, trid, row, count){
					var reimBursStatusStr = "";
					for(var i in statusTypeJson){
						if(row.reimBursStatus == statusTypeJson[i].id){
							reimBursStatusStr += statusTypeJson[i].name;
						}
					}
					if(row.NAME_!=null){
						reimBursStatusStr += "("+row.NAME_+")";
					}
					return reimBursStatusStr;
				},
	
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = ctx+reimbursement.config.namespace + opts.url;
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
				var method =reimbursement.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		reimbursement.doExecute('initDocument');
	}
	
	openreimbursement = function(tp,reimbursementId,ops){
		var url="";
		if(tp=="1"){
			url=ctx+'/sales/reimbursement/toUpdate?id=' + reimbursementId + '&opts='+ops;
		}else{
			url=ctx+'/sales/reimbursement/detail?id=' +reimbursementId + '&opts='+ops;
		}
		//window.open(url);
		var pnames = ['id','opts'];
		var args = ops.split(",");
		 $.post(ctx+'/sales/reimbursement/detail',{ id: reimbursementId,opts:ops },function(str_response){
			 
		     var obj = window.open("about:blank");   
		        obj.document.write(str_response);   
		 });
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