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
	
	var contractTypeJson = {};
	var contractStateJson = {};
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
	            namespace: '/stockup/contractCost'
	        },
	        methods :{
	        	initDocument : function(){
	        		contract.doExecute('initTable');
	        		$("#busicostid").unbind('click').click(function () {
	        			$("#edit_list").load(ctx+"/sales/fundsSalesBusiCost/busiCostList?planState=1,2,");
	        		});
	        		$("#busicoststock").unbind('click').click(function () {
	        			$("#edit_list").load(ctx+"/stockup/contractCost/manage");
	        		});
		        },
		        initTable : function(){
		        	//合同状态
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=contractState&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				contractStateJson = msg;
		    			}
		    		});
		        	//合同类型
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=contractType&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				contractTypeJson = msg;
		    			}
		    		});

		        	var grid=$("#"+contract.config.uicTable).uicTable({
		        		title : "",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		searchItems:'name',
		        		showcheckbox : true,
	    			    columns:[
	    			              { code:'creatorName',name : '客户经理',width:'8%'},
	    			              { code:'contractName',name : '合同名称',width:'27%',process:contract.methods.links},
	    			              { code:'contractCode',name : '合同编号',width:'23%'},
	    			              { code:'contractAmount', name :'合同金额',width:'14%',process:contract.methods.contractAmount},
	    			              //{ code:'contractType', name :'合同类型 ',width:'10%',process:contract.methods.contractType},
	    			              //{ code:'contractState', name :'合同状态',width:'10%',process:contract.methods.contractState},
	    			    		  { code:'doState', name :'确认状态',width:'8%',process:contract.methods.doState}
	    			    ],
	    			    
	    		
	    			    url: ctx+contract.config.namespace+'/getList?tmp='+Math.random(),
	    			    pageNo:1,
	    			    pageSize:10,
	    			    //moreCellShow : true,
	    			    //id : 'salesContractId',
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
			        		//绑定“删除合同”按钮
			        		$("#removeContract_btn").unbind('click').click(function () {
			        			contract.methods.removeContracts();
				        	});

			        	});
		        },
		        
		       //合同类型
		        /*contractType : function(divInner, trid, row, count){  	       	
		        	var contractType = "";
					for(var i in contractTypeJson){
						if(row.contractType == contractTypeJson[i].id){
							contractType += contractTypeJson[i].name;
						}
					}
					return contractType;
		        },
		        //合同状态
		        contractState : function(divInner, trid, row, count){  	       	
		        	var contractState = "";
					for(var i in contractStateJson){
						if(row.contractState == contractStateJson[i].id){
							contractState += contractStateJson[i].name;
						}
					}
					return contractState;
		        },*/
		        doState : function(divInner, trid, row, count){  	       	
		        	var doState = row.doState;
		        	var doStateStr = "";
					if(doState == 1){
						doStateStr = "已确认";
					}else{
						doStateStr = "未确认";
					}
					return doStateStr;
		        },
		        
		        handsHandler : function(divInner, trid, row, count){
		        	var str = "";
		        	var dataContent="";
		        	str+=row.contractCode;
		        	return str;
		        },

		        advancedFunction:function(type) {
		        	if(type){
		        		$(".advancedSearch",$("#"+contract.config.uicTable)).css("height","auto");
		        		//绑定“查询”按钮
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			contract.methods.doAdvancedFun();
			        	});
		        	}
		        },
		        doAdvancedFun:function(){
		        	var contractName = $('#search_contractName').val();
		        	var contractCode = $('#search_contractCode').val();
		        	var contractAmount = $('#search_contractAmount').val();
		        	var contractAmountb = $('#search_contractAmountb').val();
		        	var myCheckbox = $("input[name='doState']:checked");
		        	var doState = "";
		        	
		        	myCheckbox.each(function(i,ele){//循环拼装被选中项的值
	        			if($(ele).val() != 'null'){
	        				doState = doState+$(this).val()+",";
	        			}
	        		 });
		        	$("#"+contract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{name:"contractName",value:contractName},
		        		           {name:"contractCode",value:contractCode},
		        		           {name:"contractAmountb",value:contractAmountb},
		        		           {name:"contractAmount",value:contractAmount},
		        		           {name:"doState",value:doState}]
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
					}
					//转换字符中的html标签
					var t=title.changeHtml();
		        	var dataContent="";
		        	
		        		var str = "";
		        		var orderId = row.orderId;
		        		var orderProcessor = row.orderProcessor;
		        		var bhSaleContractId = row.bhSaleContractId;
		        	
			        	//str+='<a style="color:#005EA7;" title="'+title+'" href="'+ctx+'/stockup/contractCost/toDetail?id=' + row.salesContractId +'&orderId='+orderId+'&orderProcessor='+orderProcessor+'&bhSaleContractId='+bhSaleContractId+'" target="_blank" ><span style="color:orange;"></span>'+title+'<font class="blue02"></a>';
			        	str+='<a style="color:#005EA7;" title="'+title+'" href="'+ctx+'/stockup/contractCost/toDetail?id=' + row.id +'" target="_blank" ><span style="color:orange;"></span>'+title+'<font class="blue02"></a>';
			        	return str;
		        	
				},
				contractAmount:function(divInner, trid, row, count){
					return "￥"+fmoney(divInner,2);
				},
				orderAmount:function(divInner, trid, row, count){
					return "￥"+fmoney(divInner,2);
				},
				removeContracts:function(){
	        		var ids=$("#"+contract.config.uicTable).getTableCheckedRows();
		    		if(ids.length==0){
	    				UicDialog.Error("请选择删除条目！");
	    				return;
		    		}
	        		var id ='';
		    		for(var i=0;i<ids.length;i++){
		    			var doState=ids[i].doState;
		    			if(doState==1){
		    				UicDialog.Error("只能选择未确认的合同删除!");
		    				return;
		    			}else{
		    				id +=ids[i].id+",";
		    			}
		    		}
		    		var datas={'id':id};
				    var url = ctx+'/stockup/contractCost/delContract?tmp='+Math.random();
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