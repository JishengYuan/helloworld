define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("stringHelper");
	require("internetTable");
	require("confirm_dialog");
	require("uic/message_dialog");
	var StringBuffer = require("stringBuffer");

	var contractInvoice = {
			config: {
				module: 'contractInvoice',
				uicTable : 'uicTable',
	            namespace: '/sales/invoice'
	        },
	        methods :{
	        	initDocument : function(){
	        		contractInvoice.doExecute('initTable');
	        		
	        		document.onkeydown = function(event) {
        				var e = event || window.event || arguments.callee.caller.arguments[0];
        				if (e && e.keyCode == 13) { // enter 键
        					contractInvoice.methods.doAdvancedFun();
        				}

        			}
	        		
		        },
		        initTable : function(){
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#"+contractInvoice.config.uicTable).uicTable({
		        		title : "合同管理-->发票查询",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		searchItems:'name',
	    			    columns:[
    			             	{ code: 'CreatorName',name:'客户经理',width:'8%'},
    			             	{ code: 'ContractName',name:'合同名称',process:contractInvoice.methods.ContractNameStr,width:'35%'},
    			             	{ code: 'ContractAmount',name:'合同金额',process:contractInvoice.methods.amount,width:'14%'},
    			             	{ code: 'InvoiceAmount',name:'发票金额',process:contractInvoice.methods.amount,width:'14%'},
    			             	{ code: 'FinalInspection',name:'通过时间',width:'10%',sort : true},
    			             	{ code: 'InvoiceStatus',name:'状态',process:contractInvoice.methods.state,width:'15%'},
    			             	{ code: '',name:'操作',process:contractInvoice.methods.handsHandler,width:'5%'},
			             ],
	    			    url: ctx+contractInvoice.config.namespace+'/getList?tmp='+Math.random(),
	    			    pageNo:1,
	    			    showcheckbox : false,
	    			    pageSize:25,
	    			    moreCellShow : false,
	    			    sortorder : 'desc',
	    			    sortname : 'FinalInspection',
	    			    htmlSort : false,
	    			    id : 'id',
				        onLoadFinish:function(){
				        	$(".operation").hide();
				        	$("._invoice_handler").unbind('click').click(function () {
			        			var id = $(this).attr("id");
			        			contractInvoice.methods.print(id);
				        	});
				        },
				        searchFun:function(){}
				        });
			        	$('.searchs').find('input').first().hide();
			        	$('.searchs').find('label').first().hide();
			        	$('.doSearch').hide();
			        	$('.doAdvancedSearch').hide();
			        	$('.operation').hide();
			        	//默认触发了搜索
			        	$('.doAdvancedSearch').click();
			        	//加载查询
			        	$(".advancedSearch",$("#"+contractInvoice.config.uicTable)).load(ctx+contractInvoice.config.namespace+'/search?tmp='+Math.random(),function(){
			        		$(".advancedSearch",$("#"+contractInvoice.config.uicTable)).css("height","auto");
			        		$('.date').datetimepicker({
		        		    	pickTime: false
			        		});
			        		//绑定“查询”按钮
			        		$("#advancedSearch_btn").unbind('click').click(function () {
			        			contractInvoice.methods.doAdvancedFun();
				        	});
			        		//绑定"重置按钮"点击事件
			        		$("#advancedSearch_reset").unbind('click').click(function () {
			        			contractInvoice.methods.reload();
			        		});
			        		
			        		//绑定"导出按钮"点击事件
			        		$("#advancedSearch_export").unbind('click').click(function(){
			        			contractInvoice.methods.invoiceExport();
			        		});
			        	});
		        },
		        doAdvancedFun:function(){
		        	var startTime = $("#startTime").val();
		        	var endTime = $("#endTime").val();
		        	var minAmount = $("#search_minAmount").val();
		        	var creatorUse=$("#search_creatorUser").formUser("getValue");
		        	var contractName=$("#search_contract").val();
		        	var orgId = $('#orgTreeInput').val();
		        	$("#"+contractInvoice.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[
		        		           {name:"startTime",value:startTime},
		        		           {name:"endTime",value:endTime},
		        		           {name:"minAmount",value:minAmount},
		        		           {name:"creatorUse",value:creatorUse},
		        		           {name:"contractName",value:contractName},
		        		           {name:"orgId",value:orgId},
		        		           ]
		        	});
		        	$("#"+contractInvoice.config.uicTable).tableReload();
		        },
		        print:function(contractId){
	        		var url = ctx+"/sales/invoice/getPrint?contractId="+contractId+"&tmp="+Math.random();
	        		$.ajax({url:url,async:false,success:function(data){
	        			window.open(url,'_blank','status=no,scrollbars=yes,top=150,left=150,width=800,height=500');
	        		  }});
	        	},
		        state : function(divInner, trid, row, count){
		        	var str = "";
		        	if(divInner == "TGSH"){
		        		str = "审批通过";
		        	} else if(divInner == "BG"){
		        		str = "变更中";
		        	} else if(divInner == "CXTG"){
		        		str = "重新提交";
		        	} else {
		        		if(row.last_ != null&&row.last_ != ""){
		        			str = row.NAME_+"("+row.last_+")";
		        		} else {
		        			str = row.NAME_;
		        		}
		        	}
		        	return str;
		        },
		        handsHandler : function(divInner, trid, row, count){
		        	var str = null;
		        	if(row.InvoiceStatus == 'TGSH'||row.NAME_ == '财务复核'){
		        		str = "<a href='#' class='_invoice_handler' style='color:blue;' id='"+row.id+"'>打印</a>"
		        	} else {
		        		str = "";
		        	}
		        	return str;
		        },
		        amount:function(divInner, trid, row, count){
		        	return "￥"+contractInvoice.methods.fmoney(divInner,2);
				},
				ContractNameStr:function(divInner, trid, row, count){
					var str='<a style="color:#005EA7;" href="'+ctx+'/sales/contract/detail?id=' + row.salesContractId + '&flowStep=SHOW" target="_blank" >'+divInner+'</a>';	
					return str;
				},
		        reload : function(){
		        	//重置时将部门名称也给重置掉
		        	$('#orgTreeInput').attr("value","");
		        	$('#orgTree').formTree('setValue',"");
		        	$("#startTime").val("");
		        	$("#endTime").val("");
		        	$("#search_minAmount").val("");
		        	$("#search_creatorUser").val("");
		        	$("#search_creatorUser").formUser("setValue","");
		        	$("#search_contract").val("");
		        	$("#"+contractInvoice.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+contractInvoice.config.uicTable).tableReload();
		        },
		        
		        //导出发票信息到excel文件中
		        invoiceExport : function(){
		        	var startTime = $('#startTime').val();
		        	var endTime = $('#endTime').val();
		        	var minAmount = $('#search_minAmount').val();
		        	var creator = $('#search_creatorUser').formUser("getValue");
		        	var contractName = $('#search_contract').val();
		        	var orgId = $('#orgTreeInput').val();
		        	var url = ctx + "/sales/invoice/exportInvoice?startTime=" 
		        	+ startTime + "&endTime=" + endTime + "&minAmount=" + minAmount
		        	+ "&creatorUse=" + creator + "&contractName=" + contractName 
		        	+ "&orgId=" + orgId;
		        	//alert(url);
		        	window.location.href = url;
		        },
		        
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = ctx+contractInvoice.config.namespace + opts.url;
		    		if(opts.id){
		    			options.keyName = 'id';
		    			options.keyValue = opts.id;
		    		}
		    		window.open(options.murl);
		        },
		        fmoney:function(s,n){
		        	n = n > 0 && n <= 20 ? n : 2; 
		    		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		    		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
		    		t = ""; 
		    		for (i = 0; i < l.length; i++) { 
		    		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		    		} 
		    		return t.split("").reverse().join("") + "." + r; 
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =contractInvoice.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		contractInvoice.doExecute('initDocument');
	}
  
});