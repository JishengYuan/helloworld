define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
	require("changTag");

	var salecontractDate="Sales_contractdate";
	var salecontractDateValue="";

	var contract = {
			config: {
				module: 'contract',
				uicTable : 'uicTable',
	            namespace: '/sales/contract'
	        },
	        methods :{
	        	initDocument : function(){
	        		salecontractDateValue=GetCookie(salecontractDate);
	        		if(salecontractDateValue==null||salecontractDateValue=="null"){
	        			salecontractDateValue='2016-01-01';
	        		}
	        		
	        		contract.doExecute('initTable');
	        		/*
        			 *键盘点击事件
        			 */
        			document.onkeydown = function(event) {
        				var e = event || window.event || arguments.callee.caller.arguments[0];
        				if (e && e.keyCode == 13) { // enter 键
        					contract.methods.doAdvancedFun();
        				}

        			}
	        		
		        },
		        initTable : function(){
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#"+contract.config.uicTable).uicTable({
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
						title : "合同管理-->合同查询",
		        		searchItems:'name',
	    			    columns:[
	    			            [{ code:'contractName',name : '合同名称',process:contract.methods.links, width:'30%'},
	    			             { code:'contractCode',name : '合同编码',process:contract.methods.handsHandler, width:'30%'}],
	    			     		[{ code:'contractAmount', name :'合同金额',width:'11%',process:contract.methods.contractAmount}],
	    			     		[{ code:'customerName',name : '客户名称',width:'15%'},
	    						 { code:'contractTypeName', name :'合同类型',width:'15%'}],
	    						[{ code:'contractState', name :'合同状态',width:'15%',process:contract.methods.creatorName},
	    						 { code:'orderStatus', name :'订单状态',width:'15%',process:contract.methods.orderState}],
	    						[{ code:'creatorName', name :'客户经理',width:'14%'},
	    						 { code:'createTime', name :'创建时间',width:'14%'}]
	    			    ],
			        url: ctx+contract.config.namespace+'/getSearchManageList?tmp='+Math.random(),
			    	addparams:[  {name:"startTime",value:salecontractDateValue}],
			        pageNo:1,
			        pageSize:25,
			        moreCellShow : true,
			        onLoadFinish:function(){
			        	var totallAmount = $("#"+contract.config.uicTable).getJsonData().totallAmount;
			        	var pageAmount = $("#"+contract.config.uicTable).getJsonData().pageAmount;
			        	$('#totallAmount').html("￥"+fmoney(totallAmount,2));
			        	$('#pageAmount').html("￥"+fmoney(pageAmount,2));
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
		        	$(".advancedSearch",$("#"+contract.config.uicTable)).load(ctx+contract.config.namespace+'/multipleSearch?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			contract.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			contract.doExecute('reload');
		        		});
		        		$("#advanced_export").unbind('click').click(function () {
		        			contract.doExecute('salesExport');
		        		});
		        		$('#startTime').val(salecontractDateValue);
		        		
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
		        	SetCookie(salecontractDate,$('#startTime').val());
		
		        	var contractName = $('#search_contractName').val();
		        	var contractCode = $('#search_contractCode').val();
		        	var contractAmount = $('#search_contractAmount').val();
		        	var creator = $("#search_creator").formUser("getValue");
		        	var contractType = $('#search_contractType').val();
		        	var contractState = $('#search_contractState').val();
		        	var customerIdtCustomer = $('#search_customerIdtCustomer').val();
		        	var customerIndustry = $('#search_customerIndustry').val();
		        	var startTime = $('#startTime').val();
		        	var endTime = $('#endTime').val();
		        	var orgId = $('#orgTreeInput').val();
		        	var customerInfo = $('#search_customerInfo').val();
		        	$("#"+contract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{name:"contractName",value:contractName},
		        		           {name:"contractCode",value:contractCode},
		        		           {name:"contractAmount",value:contractAmount},
		        		           {name:"customerIndustry",value:customerIndustry},
		        		           {name:"customerIdtCustomer",value:customerIdtCustomer},
		        		           {name:"creator",value:creator},
		        		           {name:"contractType",value:contractType},
		        		           {name:"contractState",value:contractState},
		        		           {name:"startTime",value:startTime},
		        		           {name:"endTime",value:endTime},
		        		           {name:"orgId",value:orgId},
		        		           {name:"customerInfo",value:customerInfo}
		        		          ]
		        	});
		        	
		        	$('#totallAmount').html("￥0.00");
		        	$('#pageAmount').html("￥0.00");
		        	$("#"+contract.config.uicTable).tableReload();
		        },
		        salesExport:function(){
		        	var contractName = $('#search_contractName').val();
		        	var contractCode = $('#search_contractCode').val();
		        	var contractAmount = $('#search_contractAmount').val();
		        	var creator = $("#search_creator").formUser("getValue");
		        	var customerIdtCustomer = $('#search_customerIdtCustomer').val();
		        	var customerIndustry = $('#search_customerIndustry').val();
		        	var contractType = $('#search_contractType').val();
		        	var contractState = $('#search_contractState').val();
		        	var startTime = $('#startTime').val();
		        	var endTime = $('#endTime').val();
		        	var orgId = $('#orgTreeInput').val();
		        	var customerInfo = $('#search_customerInfo').val();
		        	/*var url = ctx+"/sales/contract/exportSales?contractName="+contractName;*/
		        	var url = ctx+"/sales/export/exportSales?contractName="+contractName;
		        		url+="&contractCode="+contractCode;
		        		url+="&contractAmount="+contractAmount;
		        		url+="&creator="+creator;
		        		url+="&contractType="+contractType;
		        		url+="&contractState="+contractState;
		        		url+="&startTime="+startTime;
		        		url+="&endTime="+endTime;
		        		url+="&customerInfo="+customerInfo;
		        		url+="&orgId="+orgId;
		        		url+="&customerIdtCustomer="+customerIdtCustomer;
		        		url+="&customerIndustry="+customerIndustry;
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
					return "￥"+fmoney(divInner,2);
				},
		        reload : function(){
		        	$("#search_contractName").attr("value","");
		        	$("#search_creator").formUser("setValue","");
		        	$("#search_contractCode").attr("value","");
		        	$("#search_contractAmount").attr("value","");
		        	$('#orgTreeInput').attr("value","");
		        	$('#orgTree').formTree('setValue',"");
//		        	contract.doExecute("getUserName");
		        	$('#contractType').formSelect('setValue',"");
		        	$('#contractState').formSelect('setValue',"");
		        	$('#search_contractState').val("");
		        	$('#search_contractType').val("");
		        	$('#search_customerInfo').val("");
		        	$('#_customerIdtCustomer').formUser('setValue',"");
		        	$('#_customerIndustry').formUser('setValue',"");
		        	$('#search_customerIdtCustomer').val("");
		        	$('#search_customerIndustry').val("");
		        	$('#customerInfo_input').val("");
		        	
		        	$('#startTime').val("2015-01-01");
		        	$('#endTime').val("");
		        	/*var sd=$("#"+contract.config.uicTable).getTableCheckedRows();
		    		for(var i=0;i<sd.length;i++){
		    			alert(sd[i].id);
		    		}*/
		        	$("#"+contract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[  {name:"startTime",value:"2015-01-01"}]
		        	});
		        	$("#"+contract.config.uicTable).tableReload();
		        	
		        },
		        
				links : function(divInner, trid, row, count) {
					var title;
					var isoldt="";
					if (row.contractName == undefined) {
						title = '无标题';
					} else {
						if (row.isChanged == 1) {
							if(row.changeStatus != "未申请"){
								title =row.contractName+ '【'+row.changeStatus +'】';
							} else {
								title =row.contractName+ '【已变更】';
							}
						} else {
							if (row.changeStatus == "变更申请中") {
								title =row.contractName+ '【'+row.changeStatus +'】';
							} else if(row.changeStatus == "变更申请不通过") {
								title =row.contractName+ '【'+row.changeStatus +'】';
							} else if(row.changeStatus == "变更申请通过"){
								title =row.contractName+ '【'+row.changeStatus +'】';
							} else {
								title = row.contractName;
							}
							if(row.isold!=""){
								isoldt="["+row.isold+"]";
								/*title = "["+row.isold+"]"+row.contractName;*/
							}
							
						}
						
						if(row.contractTypeName == '备货合同'&&row.contractState != '草稿'){
							if(row.qu != ""&&row.qu == row.sur){
								title += "【已关联完毕】";
							} else {
								title += "【未关联完毕】";
							}
						}
					}
					//转换字符中的html标签
					var t=title.changeHtml();
			      	var str = "";
		        	str+='<a style="color:#005EA7;" href="'+ctx+'/sales/contract/detail?id=' + row.salesContractId + '" target="_blank" title="'+title+'"><span style="color:orange;">'+isoldt+"</span>"+title+'<font class="blue02"></a>';							
		        	return str;
				},
				orderState:function(divInner, trid, row, count){
					var orderStatus = row.orderStatus;
					if(orderStatus != '合同订单状态：未采购'&&orderStatus != ""){
						return '<a target="_blank" href="'+ctx+'/sales/contract/contractOrderAllDetail_link?id=' + row.salesContractId + '" style="color:#005EA7;">'+orderStatus+'</a>'
					} else {
						return orderStatus;
					}
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
	
	function GetCookieVal(offset) {
		var endstr = document.cookie.indexOf(";", offset);
		if (endstr == -1) {
			endstr = document.cookie.length;
		}
		return unescape(document.cookie.substring(offset, endstr));
	}
	function SetCookie(name, value) {
		var expdate = new Date();
		var argv = SetCookie.arguments;
		var argc = SetCookie.arguments.length;
		var expires = (argc > 2) ? argv[2] : null;			/* expires 周期单位:秒 */
		var path = (argc > 3) ? argv[3] : null;
		var domain = (argc > 4) ? argv[4] : null;
		var secure = (argc > 5) ? argv[5] : false;
		if (expires != null) {
			expdate.setTime(expdate.getTime() + (expires*1000));
		}
		document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + expdate.toGMTString())) + ((path == null) ? "" : ("; path=" + path)) + ((domain == null) ? "" : ("; domain=" + domain)) + ((secure == true) ? "; secure" : "");
	}
	
	function GetCookie(name) {
		var arg = name + "=";
		var alen = arg.length;
		var clen = document.cookie.length;
		var i = 0;
		while (i < clen) {
			var j = i + alen;
			if (document.cookie.substring(i, j) == arg) {
				return GetCookieVal(j);
			}
			i = document.cookie.indexOf(" ", i) + 1;
			if (i == 0) {
				break;
			}
		}
		return null;
	}
});