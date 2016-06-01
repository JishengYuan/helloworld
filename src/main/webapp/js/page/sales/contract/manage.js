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
	require("changTag");
	require("formSelect");
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

	var contract = {
			config: {
				module: 'contract',
				uicTable : 'uicTable',
	            namespace: '/sales/contract'
	        },
	        methods :{
	        	initDocument : function(){
	        		contract.doExecute('initTable');
		        },
		        initTable : function(){
		        //	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#"+contract.config.uicTable).uicTable({
		        		title : "合同管理-->我的合同",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		searchItems:'name',
	    			    columns:[[{ code: 'contractName',name : '合同名称',process:contract.methods.links,width:'38%'},
	    			              { code: 'contractCode',name : '编码',process:contract.methods.handsHandler ,width:'38%'}],
			        			[ { code:'contractAmount', name :'合同金额',width:'20%',process:contract.methods.contractAmount},
			        			 { code: 'reciveStatus',name : '收款',width:'20%',process:contract.methods.greyStyle}],
			        			[{ code: 'orderStatus',name : '订单状态',width:'15%',process:contract.methods.orderState},
			        			 { code: 'cachetStatus',name : '盖章',width:'15%',process:contract.methods.showCachetStatus}],
			        			[{ code:'contractState', name :'合同状态',width:'15%'}	,
			        			 { code: 'invoiceStatus',name : '发票',width:'15%',process:contract.methods.showInvoiceStatus}],
			        			 [{ code:'currentNode', name :'合同审批状态',width:'12%'}	,
				        			 { code: 'dealUserName',name : '',width:'12%',process:contract.methods.showContractAduit}]
	    			    ],
	    			    url: ctx+contract.config.namespace+'/getList?tmp='+Math.random(),
	    			    pageNo:1,
	    			    pageSize:10,
	    			    moreCellShow : true,
	    			    id : 'salesContractId',
//				        onLoadFinish:function(){
//				        	$('.contract_handler').popover({
//				        		trigger :'focus',
//				        		placement:'right',
//				        		html:true	
//				        	}).click(function(e) {
//						        e.preventDefault();
//						    });
//				        },
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
				        //advancedFun:contract.methods.advancedFunction
				        });
			        	$('.searchs').find('input').first().hide();
			        	$('.searchs').find('label').first().hide();
			        	$('.doSearch').hide();
			        	$('.doAdvancedSearch').hide();
			        	//默认触发了搜索
			        	$('.doAdvancedSearch').click();
			        	//加载查询
			        	$(".advancedSearch",$("#"+contract.config.uicTable)).load(ctx+contract.config.namespace+'/search?tmp='+Math.random(),function(){
			        		$(".advancedSearch",$("#"+contract.config.uicTable)).css("height","auto");
			        		//绑定“查询”按钮
			        		$("#advancedSearch_btn").unbind('click').click(function () {
			        			contract.methods.doAdvancedFun();
				        	});
			        		//绑定“重置”按钮
//			        		$("#reset_btn").unbind('click').click(function () {
//			        			contract.methods.cleanSearchInput();
//			        		});
				        	//绑定“添加合同”按钮
			        		$("#addContract_btn").unbind('click').click(function () {
			        			contract.methods.toAddContractPage();
				        	});
			        		//绑定“删除合同”按钮
			        		$("#removeContract_btn").unbind('click').click(function () {
			        			contract.methods.removeContracts();
				        	});
			        		//绑定“导出”按钮
			        		$("#advanced_export").unbind('click').click(function () {
			        			contract.doExecute('salesExport');
			        		});
			        		//绑定“更多”按钮
			        		$("#_contract_moreSearch_btn").unbind('click').click(function () {
			        			$("#_contract_moreSearch").toggle(1000);
			        		});
							
							var contractState = $("#contractState");
							contractState.addClass("li_form");
							var optionCompPosType = {
								writeType : 'show',
								showLabel : false,
								code : "contractState",
								onSelect:function(node){
									$('#search_contractState').val($("#contractState").formSelect("getValue")[0]);
								},
								width : "222"
							};
							contractState.formSelect(optionCompPosType);
							$('.uicSelectData').height(100);
			        		
							$('.date').datetimepicker({
		        		    	pickTime: false
							});
			        	});
		        },
		        handsHandler : function(divInner, trid, row, count){
		        	var str = "";
		        	var dataContent="";
		        	str+=row.contractCode;
	/*	        	if(row.contractState =='草稿'){
		        		dataContent = "<span style='margin-left:10px;'><a href='"+ctx+"/sales/contract/toUpdate?id=" + row.salesContractId + "' target='_blank' class='_edit'><i class='icon-edit'></i>修改</a></span>&nbsp;";
		        	}else if(row.contractState =='审核通过'){
		        		//变更申请审核通过后不能在申请发票
		        		if(row.invoiceStatus =='发票状态：未申请'&&row.changeStatus !='变更申请通过'){
		        			dataContent=dataContent+"<span style='margin-left:10px;'><a href='"+ctx+"/sales/invoice/apply?colseType=addNew&id=" + row.salesContractId + "' target='_blank' class='_invoicePlan'><i class='icon-list-alt'></i>发票申请</a></span>&nbsp;";
		        		}
		        		//变更申请审核通过后不能在申请盖章
		        		if((row.cachetStatus =='盖章状态：未申请'&&row.changeStatus !='变更申请通过')||row.cachetStatus =='盖章状态：待重新申请'){
		        			dataContent = dataContent+"<span style='margin-left:10px;'><a href='"+ctx+"/sales/cachet/apply?id=" + row.salesContractId + "' target='_blank' class='_cachetApply'><i class='icon-screenshot'></i>盖章申请</a></span>&nbsp;";
		        		}
		        		if(row.changeStatus =='未申请'&&row.cachetStatus !='盖章状态：审核通过' && row.invoiceStatus !='发票状态：审核通过'&&row.orderStatus =='合同订单状态：未采购'){
		        			dataContent=dataContent+"<span style='margin-left:10px;'><a href='"+ctx+"/sales/contractchange/apply?id=" + row.salesContractId + "' target='_blank' class='_changeApply'><i class='icon-edit'></i>变更申请</a></span>&nbsp;"
		        		}
		        		//变更申请通过后有变更合同内容的功能
		        		if(row.changeStatus =='变更申请通过'){
		        			dataContent=dataContent+"<span style='margin-left:10px;'><a href='"+ctx+"/sales/contract/toContractChange?id=" + row.salesContractId + "' target='_blank' class='_contractChange'><i class='icon-pencil'></i>变更</a></span>&nbsp;"
		        		}
		        		//变更申请中或者是变更申请通过后有变更申请详情展示功能
		        		if(row.changeStatus =='变更申请中'||row.changeStatus =='变更申请通过'){
		        			dataContent=dataContent+"<span style='margin-left:10px;'><a href='"+ctx+"/sales/contractchange/applyDetail?id=" + row.salesContractId + "' target='_blank' class='_contractChangeDetail'><i class='icon-screenshot'></i>变更申请详情</a></span>&nbsp;"
		        		}
		        	}
		        	//合同为“审核中”，“废弃”两个状态不需要有小手图标，同时如果是“审核通过”后发票申请状态和盖章申请状态只有为“未申请”状态才有小手。
		        	//“草稿”状态下有小手
		        	//if((row.contractState !='审核中'&&row.contractState !='废弃'&&(row.invoiceStatus =='发票申请状态：未申请'||row.cachetStatus =='盖章申请状态：未申请'))||(row.contractState =='草稿')){
		        	if((row.contractState !='审核中'&&row.contractState !='废弃')||(row.contractState =='草稿')){
		        		str+='<span spanId="'+row.salesContractId+'" style="margin-left:5px"><a href="#" data-content="'+dataContent+'" name="contract_handler"  id="'+row.salesContractId+'" class="contract_handler"><i class="icon-hand-down"></i></a></span>';
		        	}*/
		        	return str;
		        },
		        toAddContractPage:function(){
	        		var opts = {};
	    			opts.url = "/toSave";
	    			contract.methods.openurl(opts);
		        },
		        removeContracts:function(){
	        		var ids=$("#"+contract.config.uicTable).getTableCheckedRows();
		    		if(ids.length==0){
	    				UicDialog.Error("请选择删除条目！");
	    				return;
		    		}
	        		var id ='';
		    		for(var i=0;i<ids.length;i++){
//		    			var contractName=$('#row'+ids[i].salesContractId+' > td:eq(2) a').text();
//		    			var contractState=$('#row'+ids[i].contractState+' > .tdLast').text().replace(/(^\s*)|(\s*$)/g, "");
		    			var contractName=ids[i].contractName;
		    			var contractState=ids[i].contractState;
		    			if(contractState!='草稿'){
//		    				UicDialog.Error(contractName+"<br/>已提交审批，无法删除！");
		    				UicDialog.Error("只能选择草稿数据删除!");
		    				return;
		    			}else{
		    				id +=ids[i].salesContractId+",";
		    			}
		    		}
		    		var datas={'id':id};
				    var url = ctx+'/sales/contract/remove?tmp='+Math.random();
				    $.post(url,datas,
				    		function(data,status){
				            	if(data=="success"){
				                	  UicDialog.Success("删除数据成功!",function(){
				                	  $("#"+contract.config.uicTable).tableReload();
				                	  });
				                  }else{
				                  	  UicDialog.Error("删除数据失败！");
				                  	  $("#"+contract.config.uicTable).tableReload();
				                  }
				   });
		        },
		        
		        advancedFunction:function(type) {
		        	if(type){
		        		$(".advancedSearch",$("#"+contract.config.uicTable)).css("height","auto");
		        		//绑定“查询”按钮
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			contract.methods.doAdvancedFun();
			        	});
		        		//绑定“重置”按钮
//		        		$("#reset_btn").unbind('click').click(function () {
//		        			contract.doExecute('reload');
//		        		});
			        	//绑定“添加合同”按钮
		        		$("#addContract_btn").unbind('click').click(function () {
		        			contract.methods.toAddContractPage();
			        	});
		        		//绑定“删除合同”按钮
		        		$("#removeContract_btn").unbind('click').click(function () {
		        			contract.methods.removeContracts();
			        	});
		        	}
		        },
		        salesExport:function(){
		        	var contractName = $('#search_contractName').val();
		        	var contractCode = $('#search_contractCode').val();
		        	var contractAmount = $('#search_contractAmount').val();
		        	var contractAmountb = $('#search_contractAmountb').val();
		        	var url = ctx+"/sales/export/exportSales?contractName="+contractName;
		        		url+="&contractCode="+contractCode;
		        		url+="&contractAmount="+contractAmount;
		        		url+="&userName=ture";
		        	window.location.href= url;
		        	
		        },
		        doAdvancedFun:function(){
		        	var contractName = $('#search_contractName').val();
		        	var contractCode = $('#search_contractCode').val();
		        	var contractAmount = $('#search_contractAmount').val();
		        	var contractAmountb = $('#search_contractAmountb').val();
		        	
		        	var contractState = $('#search_contractState').val();
		        	var startTime = $('#startTime').val();
		        	var endTime = $('#endTime').val();
		        	$("#"+contract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{name:"contractName",value:contractName},
		        		           {name:"contractCode",value:contractCode},
		        		           {name:"contractAmountb",value:contractAmountb},
		        		           {name:"contractState",value:contractState},
		        		           {name:"startTime",value:startTime},
		        		           {name:"endTime",value:endTime},
		        		           {name:"contractAmount",value:contractAmount}]
		        	});
		        	$("#"+contract.config.uicTable).tableReload();
		        },
		        reload : function(){
		        	var sd=$("#"+contract.config.uicTable).getTableCheckedRows();
		        	$("#"+contract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+contract.config.uicTable).tableReload();
		        },
		        cleanSearchInput : function(){
		        	$("#search_contractName").attr("value","");
		        	$("#search_contractCode").attr("value","");
		        	$("#search_contractAmount").attr("value","");
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

						if(row.contractType == '9000'&&row.contractState != '草稿'){
							if(row.qu != ""&&row.qu == row.sur){
								title += "【已关联完毕】";
							} else {
								title += "【未关联完毕】";
							}
						}
					}
					//转换字符中的html标签
					var t=title.changeHtml();
		        	var dataContent="";
		        	if(row.contractState =='草稿'){
		        		dataContent="draft,";
		        	}else if(row.contractState =='审核通过'){
		        		//变更申请审核通过后不能在申请发票
		        		if(row.invoiceStatus =='发票状态：未申请'&&row.changeStatus !='变更申请通过'){
		        			dataContent=dataContent+"Invoice,";
		        		}
		        		//变更申请审核通过后不能在申请盖章
		        		if((row.cachetStatus =='盖章状态：未申请'&&row.changeStatus !='变更申请通过')||row.cachetStatus =='盖章状态：待重新申请'){
		        			dataContent=dataContent+"Cachet,";
		        		}
		        		if(row.changeStatus =='未申请'&&row.cachetStatus !='盖章状态：审核通过' && row.invoiceStatus !='发票状态：审核通过'&&row.orderStatus =='合同订单状态：未采购'){
		        			dataContent=dataContent+"ChangeApply,";
		        		}
		        		//变更申请通过后有变更合同内容的功能
		        		if(row.changeStatus =='变更申请通过'){
		        			dataContent=dataContent+"Change,";
		        		}
		        		//变更申请中或者是变更申请通过后有变更申请详情展示功能
		        		if(row.changeStatus =='变更申请中'||row.changeStatus =='变更申请通过'){
		        			dataContent=dataContent+"ChangeDetail,";
		        		}
		        		if(row.changeStatus =='变更申请不通过'){
		        			dataContent=dataContent+"ChangeApply,";
		        			dataContent=dataContent+"ChangeDetail,";
		        		}
		        	}
		        	if(row.contractState =='草稿'){
		        		var str = "";
			        	str+='<a style="color:#005EA7;" title="'+title+'" href="'+ctx+'/sales/contract/toUpdate?id=' + row.salesContractId +'" target="_blank" >'+t+'<font class="blue02"></a>';							
			        	return str;
		        	} else {
		        		var str = "";
			        	str+='<a style="color:#005EA7;" title="'+title+'" href="'+ctx+'/sales/contract/detail?id=' + row.salesContractId +'" target="_blank" ><span style="color:orange;">'+isoldt+'</span>'+title+'<font class="blue02"></a>';							
			        	return str;
		        	}
				},
				greyStyle:function(divInner, trid, row, count){
					var m =  /^\d+(\.\d+)?$/;
					var amount = row.reciveStatus.split("：");
					if(m.test(amount[1])){
						return '<font class="grey03">已收款：'+"￥"+fmoney(amount[1],2)+'</font>';
					} else {
						return '<font class="grey03">'+divInner+'</font>';
					}
					
				},
				contractAmount:function(divInner, trid, row, count){
					return "￥"+fmoney(divInner,2);
				},
				orderState:function(divInner, trid, row, count){
					var orderStatus = row.orderStatus;
					if(orderStatus != '订单状态：未采购'&&orderStatus != ""){
						return '<a target="_blank" href="'+ctx+'/sales/contract/contractOrderAllDetail?id=' + row.salesContractId + '" style="color:#005EA7;">'+orderStatus+'</a>'
					} else {
						return orderStatus;
					}
				},
				showContractAduit:function(divInner, trid, row, count){
					var rs="";
					var d;
//					if(row.taskReachTime!=null){
//						 d=new Date(row.taskReachTime); 
//						 d=formatDate(d);
//					}else{
//						d="";
//					}
					rs = row.dealUserName;
					return rs;
				},
				showInvoiceStatus:function(divInner, trid, row, count){
//					if(row.isold == '原ERP合同'){
//						return '<font class="grey03">'+divInner+'</font>';
//					} else 
					if(row.invoiceStatus!='发票状态：未申请'){
	        			var title=row.invoiceStatus;
						//转换字符中的html标签
						var t=title.changeHtml();
				      	var str = "";
			        	str+='<a style="color:#005EA7;" href="'+ctx+'/sales/invoice/applyDetail?id=' + row.salesContractId + '" target="_blank">'+t+'<font class="blue02"></a>';							
			        	return str;
	        		}else{
	        			return '<font class="grey03">'+divInner+'</font>';
	        		}
				},
				showCachetStatus:function(divInner, trid, row, count){
	        		if(row.cachetStatus!='盖章状态：未申请'){
						var title=row.cachetStatus;
						//转换字符中的html标签
						var t=title.changeHtml();
				      	var str = "";
			        	str+='<a style="color:#005EA7;" href="'+ctx+'/sales/cachet/applyDetail?id=' + row.salesContractId + '" target="_blank">'+t+'<font class="blue02"></a>';							
			        	return str;
	        		}else{
	        			return '<font class="grey03">'+divInner+'</font>';
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
	
	openContract = function(tp,contractId,ops){
		var url="";
		if(tp=="1"){
			url=ctx+'/sales/contract/toUpdate?id=' + contractId + '&opts='+ops;
		}else{
			url=ctx+'/sales/contract/detail?id=' +contractId + '&opts='+ops;
		}
		//window.open(url);
		var pnames = ['id','opts'];
		var args = ops.split(",");
		 $.post(ctx+'/sales/contract/detail',{ id: contractId,opts:ops },function(str_response){
			 
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