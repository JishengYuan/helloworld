define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("stringHelper");
	require("internetTable");
	require("confirm_dialog");
	require("uic/message_dialog");
	require("jqBootstrapValidation");
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
	            namespace: '/sales/fundsSalesContract'
	        },
	        methods :{
	        	initDocument : function(){
	        		contract.doExecute('initTable');
		        },
		        initTable : function(){
		      

		        	var grid=$("#"+contract.config.uicTable).uicTable({
		        		title : "合同管理-->合同资金核算",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		searchItems:'name',
		        		showcheckbox : true,
	    			    columns:[
	    			              [{ code:'contractName',name : '合同名称',width:'30%',process:contract.methods.links},
	    			              { code:'contractCode',name : '合同编号',width:'30%'}],
	    			              [{ code:'contractAmount', name :'合同金额',width:'14%',process:contract.methods.contractAmountStr}],
	    			              [{ code:'businessCost', name :'商务成本 ',width:'14%',process:contract.methods.businessCostStr}],
	    			              [{ code:'incomeInvoice', name :'进向发票',width:'14%',process:contract.methods.incomeInvoiceStr}],
	    			              [{ code:'outInvoice', name :'销向发票',width:'14%',process:contract.methods.outInvoiceStr}],
	    			              [{ code:'receiveAmount', name :'回款金额',width:'14%',process:contract.methods.receiveAmountStr}]
	    			    ],
	    				buttons : [
	    				      /*{ code: 'modify', name: '修改', butclass: 'modify',isDimmed:false, onpress: toolbarItem }*/
	    					],
	    			    url: ctx+contract.config.namespace+'/getList?tmp='+Math.random(),
	    			    pageNo:1,
	    			    pageSize:10,
	    			    moreCellShow : true,
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
			        //	$('.searchs').find('input').first().hide();
			        //	$('.searchs').find('label').first().hide();
			        	//$('.doSearch').hide();
			        	//$('.doAdvancedSearch').hide();
			        	//默认触发了搜索
			        //	$('.doAdvancedSearch').click();
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
		        
		      /* //合同类型
		        contractType : function(divInner, trid, row, count){  	       	
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
		        contractAmountStr : function(divInner, trid, row, count){
		        	if(divInner!=""){
		        		return "￥"+fmoney(divInner,2);
		        	}else{
		        		return "￥0.00";
		        	}
		        	
		        },
		        businessCostStr: function(divInner, trid, row, count){
		        	if(divInner!=""){
		        		return "￥"+fmoney(divInner,2);
		        	}else{
		        		return "￥0.00";
		        	}
		        	
		        },
		        incomeInvoiceStr: function(divInner, trid, row, count){
		        	if(divInner!=""){
		        		return "￥"+fmoney(divInner,2);
		        	}else{
		        		return "￥0.00";
		        	}
		        	
		        },
		        outInvoiceStr: function(divInner, trid, row, count){
		        	if(divInner!=""){
		        		return "￥"+fmoney(divInner,2);
		        	}else{
		        		return "￥0.00";
		        	}
		        	
		        },
		        receiveAmountStr: function(divInner, trid, row, count){
		        	if(divInner!=""){
		        		return "￥"+fmoney(divInner,2);
		        	}else{
		        		return "￥0.00";
		        	}
		        	
		        },
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
		        	$("#"+contract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{name:"contractName",value:contractName},
		        		           {name:"contractCode",value:contractCode},
		        		           {name:"contractAmountb",value:contractAmountb},
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
					}
					//转换字符中的html标签
					var t=title.changeHtml();
		        	var dataContent="";
		        	
		        		var str = "";
		        		var orderId = row.orderId;
		        		var orderProcessor = row.orderProcessor;
		        		var bhSaleContractId = row.bhSaleContractId;
		        	
			        	//str+='<a style="color:#005EA7;" title="'+title+'" href="'+ctx+'/stockup/contractCost/toDetail?id=' + row.salesContractId +'&orderId='+orderId+'&orderProcessor='+orderProcessor+'&bhSaleContractId='+bhSaleContractId+'" target="_blank" ><span style="color:orange;"></span>'+title+'<font class="blue02"></a>';
			        	str+='<a style="color:#005EA7;" title="'+title+'" href="'+ctx+'/sales/fundsSalesContract/detail?contractCode=' + row.contractCode +'" target="_blank" ><span style="color:orange;"></span>'+title+'<font class="blue02"></a>';
			        	return str;
		        	
				},
				contractAmount:function(divInner, trid, row, count){
					return "￥"+fmoney(divInner,2);
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
	function toolbarItem() {
		var ids=$("#uicTable").getTableCheckedRows();		
		if(ids.length>2||ids.length==0){
			alert("请选择一个项目！");
			return;
		}
		var doState="";
		for(var i=0;i<ids.length;i++){
			doState=ids[i].id;
		}

    	var frameSrc = ctx+"/sales/fundsSalesContract/modi?fundsId="+doState; 
    	
    	$('#dailogs1').on('show', function () {
		     $('#dialogbody').load(frameSrc,function(){
		    	 $("input,select,textarea").not("[type=submit]").jqBootstrapValidation({

	    		     submitSuccess: function (form, event) {
	    		         event.preventDefault();
	    		         $.ajax({
	 		                url:  ctx+"/sales/fundsSalesContract/modifunds", 
	 		                data: $('#editForm').serialize(), // 从表单中获取数据
	 		                type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
	 		                error: function(request) {      // 设置表单提交出错
	 		                	 $('#alertMsg').empty();
	 			         			$('#alertMsg').append('<div class="alert alert-error"><strong>错误：</strong>修改失败，请稍后再试！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
	 			         		    $(".alert").delay(2000).hide(0);
	 			         		    $(".close").click(function(){
	 			         		    	$(".alert").hide();
	 			         		    });
	 		                },
	 		                success: function(data) {
	 		                // 设置表单提交完成使用方法
	 		                   if(data=="success"){
	 		                	   $('.edit_list').load(ctx + '/sales/fundsSalesContract/manage?tmp=' + Math.random(),{},function(){
	 		                			$('#alertMsg').empty();
	 				         			$('#alertMsg').append('<div class="alert alert-success"><strong>提示：</strong>修改成功！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
	 				         		    $(".alert").delay(2000).hide(0);
	 				         		    $(".close").click(function(){
	 				         		    	$("#"+contract.config.uicTable).tableOptions({
	 							        		pageNo : '1',
	 							        		addparams:[{
	 							        		}]
	 							        	});
	 							        	$("#"+contract.config.uicTable).tableReload();
	 				         		    	$(".alert").hide();
	 				         		    });
	 		                	   });
	 		                   }else{
	 		                	   $('#alertMsg').empty();
	 			         			$('#alertMsg').append('<div class="alert alert-error"><strong>错误：</strong>修改失败，请稍后再试！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
	 			         		    $(".alert").delay(2000).hide(0);
	 			         		    $(".close").click(function(){
	 			         		    	$(".alert").hide();
	 			         		    });
	 		                   }
	 		                  $('#dailogs1').modal('hide');
	 		                }
	 		            });
	    		   
	    		     },
	    		     submitError: function (form, event, errors) {
	    		         event.preventDefault();
	    		         }
	    	 	});
		     }); 
			     $("#dsave").unbind('click');
			     $('#dsave').click(function () {
					$('#editForm').submit();
			     });
			
		 });
			    $('#dailogs1').on('hidden', function () {$('#dailogs1').unbind("show");});
				$('#dailogs1').modal({show:true});
				$('#dailogs1').off('show');
				
	
                   
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