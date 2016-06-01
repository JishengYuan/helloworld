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
	
	var contractTypeJson = {};
	//子页面关闭刷新该父页面
	childColseRefresh = function(){
			$("#uicTable").tableOptions({
    			pageNo : '1',
    			addparams:[]
    			});
    			$("#uicTable").tableReload();
		}
	var contract = {
			config: {
				module: 'contract',
				uicTable : 'uicTable',
	            namespace: '/business/closeContract'
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
		        		buttons : [
		        			],searchItems:'name',
	    			    columns:[
	    			        { code: 'contractCreator',name : '客户经理',process:contract.methods.handsHandler,width:'20%'},
		        			{ code: 'contractShortName',name : '合同简称'/*,process:contract.methods.links*/,width:'20%'},
		        			{ code:'contractType', name :'合同类型',process:contract.methods.contractType,width:'20%'},
		        			{ code:'contractAmount', name :'合同金额',process:contract.methods.contractAmountStr,width:'10%'}	
			        ],
			        url: ctx+'/business/closeContract/getTableGrid?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:10,
			        onLoadFinish:function(){
			        	$('.contract_handler').popover({
			        		trigger :'focus',
			        		placement:'right',
			        		html:true	
			        	}).click(function(e) {
					        e.preventDefault();
					    });
			        },
			        searchTitle:'合同编号',
			        searchTip:'请输入合同编号',
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
		        	$('.doSearch').hide();
		        	/*$(".advancedSearch",$("#"+contract.config.uicTable)).load(ctx+contract.config.namespace+'/search?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			contract.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			contract.doExecute('reload');
		        		});
		        	});*/
		        	$(".advancedSearch",$("#"+contract.config.uicTable)).css("height","auto");
		        	$('.doAdvancedSearch').hide();

		        },
		     
		        handsHandler : function(divInner, trid, row, count){
		        	var str = "";
		        	str+=row.contractCreator;
		        	str+="<span style='margin-left:10px;'><a href='"+ctx+"/business/closeContract/settlement?id="+row.id+"' name='order_handler'  target='_blank' class='_edit'><i class='icon-edit'></i>审批</a></span>";
		        	return str;
		        },
		        advancedFun:function(type) {
		        	if(type){
		        		$(".advancedSearch",$("#"+contract.config.uicTable)).css("height","auto");
		        		
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			contract.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			contract.doExecute('reload');
		        		});
		        		
		        	} else {
		        	}
		        },
		        doAdvancedFun:function(){
		        	$("#"+contract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"test",
		        			value:1
		        		}]
		        	});
		        	$("#"+contract.config.uicTable).tableReload();
		        },
		        reload : function(){
		        	var sd=$("#"+contract.config.uicTable).getTableCheckedRows();
		    		for(var i=0;i<sd.length;i++){
		    			alert(sd[i].id);
		    		}
		        	$("#"+contract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+contract.config.uicTable).tableReload();
		        },
				links : function(divInner, trid, row, count) {
					var title;
					if (row.procInstTitle == undefined) {
						title = '无标题';
					} else {
						title = row.procInstTitle;
					}
					//转换字符中的html标签
					var t=title.changeHtml();
			      	var str = "";
		        	str+='<a style="color:#005EA7;" href="'+ctx+'/sales/contract/detail?id=' + row.id + '" target="_blank">'+row.contractShortName+'<font class="blue02"></a>';							
		        	return str;
				},
				 contractType : function(divInner, trid, row, count){
			        	var contractType = "";
						for(var i in contractTypeJson){
							if(row.contractType == contractTypeJson[i].id){
								contractType += contractTypeJson[i].name;
							}
						}
						return contractType;
			        },
			     contractAmountStr : function(divInner, trid, row, count){
			    	 return "￥"+fmoney(divInner,2);
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
	/*//子页面关闭刷新该父页面
	childColseRefresh=function(){
		contract.doExecute('initDocument');
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