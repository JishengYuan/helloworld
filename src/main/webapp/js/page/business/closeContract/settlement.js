define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("formUser");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	//------------------加载DataTable-----------Start--------------
	var Map = require("map");
	require("confirm_dialog");
	require("uic/message_dialog");
	gridStore = new Map();//
	gridTypeStore=new Map();//表格行列类型
	gridFieldStore = new Map();
	require("json2");
	require("js/common/form/data_grid");
	//------------------加载DataTable-------------End------------
	String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
	}
	var salesContractDetail = {
			config: {
				module: 'salesContractDetail',
            	namespace:'/business/closeContract'
	        },
	        methods :{
	        	initDocument : function(){
	        		salesContractDetail.doExecute('getContractType');
	        		salesContractDetail.doExecute('getInvoiceType');
	        		salesContractDetail.doExecute('getAccountCurrency');
	        		salesContractDetail.doExecute('getReceiveWay');
	        		salesContractDetail.doExecute('getIndustryAndCustomerIdt');
	        		salesContractDetail.doExecute('getOrderStatus');
	        		//通过审批
		        	$("#dsave").unbind('click').click(function () {
		        		$("#dsave").unbind('click');
		        		var opts = {};
		    			opts.url = "/close";
		    			salesContractDetail.doExecute("closeCon",opts);
		        	});
		        	 $("#_do_cancel").bind('click',function(){
		        		 salesContractDetail.doExecute("_back");
			         });
		        	 $("#_sino_eoss_order_products_table tbody").find("tr").each(function(i,ele){
	        			 var serviceTypeId=$('#serviceType'+i).text();
	        			 salesContractDetail.methods.getServiceType(serviceTypeId,i);
	        		 });
		        	 salesContractDetail.methods.getContractName();
		        },
		        getContractName:function(){
		        	if($("td[name = 'relateDeliveryProduct_contract']").length > 0){
		        		$("#_contract_show").hide();
		        		$("#_contract_hidden").show();
		        		if($("td[name = 'relateDeliveryProduct_contract_']").length > 0){
		        			$("td[name = 'relateDeliveryProduct_contract_']").after("<td></td>");
		        		}
		        	} else {
		        		$("#_contract_show").show();
		        		$("#_contract_hidden").hide();
		        	}
		        	$("td[name = 'relateDeliveryProduct_contract']").each(function(i,ele){
		        		var id = $(ele).attr("tid");
		        		$.ajax({
			        		type : "GET",
			        		url : ctx+"/sales/contract/getContract?pruductId="+id+"&tmp="+ Math.random(),
			        		success : function(msg) {
			        			var str = $(ele).html();
			        			str+=msg.contractName;
			        			$(ele).html(str);
			        		}
			        	});
		        	});
		        },
		        closeCon:function(opts){
		        	var options = {};
		        	options.murl = ctx+salesContractDetail.config.namespace + opts.url;
		    		var datas=$("#_sino_eoss_sales_contract_approveForm").serialize();
		    		 $.post(options.murl,datas,
			    	          function(data,status){
			    	            if(status=="success"){
			    	                UicDialog.Success("审批成功!",function(){
			    	                	salesContractDetail.doExecute("closeWindow",opts);
			    	                });
			    	            }else{
			    	                UicDialog.Error("审批失败！",function(){
			    	                	salesContractDetail.doExecute("closeWindow",opts);
			    	                });
			    	           }
			    	    });
		        },
		        getServiceType : function(serviceTypeId,j){
		        	$.ajax({
	        			type : "GET",
	        			async : false,
	        			dataType : "json",
	        			url : ctx + "/sysDomain/findDomainValue?code=serviceType&tmp="+ Math.random(),
	        			success : function(msg) {
	        				for(var i in msg){
	        					if(msg[i].id == serviceTypeId){
	        						$('#serviceType'+j).text(msg[i].name);
	        					}
	        				}
	        				/*$('._order_table tbody').find("tr").each(function(m,ele){
	        					$(this).find("td").each(function(j,elem){
	        						if(j == 4){
		        						var typeId = $(elem).attr("tdId");
		        						for(var i in msg){
		    	        					if(msg[i].id == typeId){
		    	        						$(elem).html(msg[i].name);
		    	        					}
		    	        				}
		        					}
	        					});
	        				});*/
	        			}
	        		});
		        },
		        
		        getContractType : function(){
		        	var contractTypeId=$("#contractTypeId").val();
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=contractType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == contractTypeId){
		    						$('#contractType').text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        },
		        getOrderStatus : function(){
		        	$.ajax({
	        			type : "GET",
	        			async : false,
	        			dataType : "json",
	        			url : ctx + "/sysDomain/findDomainValue?code=orderStatus&tmp="+ Math.random(),
	        			success : function(msg) {
	        				for(var i in msg){
	        					if(msg[i].id == $('#_orderStatus').val()){
	        						$('#orderStatus').html(msg[i].name);
	        					}
	        				}
	        			}
	        		});
		        },
		        getInvoiceType : function(){
		        	var invoiceTypeId=$("#_eoss_sales_invoiceTypeId").val();
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == invoiceTypeId){
		    						$('#invoiceType').text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        },
		        getAccountCurrency : function(){
		        	var accountCurrencyId=$("#_eoss_sales_accountCurrencyId").val();
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=accountCurrency&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == accountCurrencyId){
		    						$('#accountCurrency').text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        },
		        getReceiveWay : function(){
		        	var receiveWayId=$("#_eoss_sales_receiveWayId").val();
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=receiveWay&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == receiveWayId){
		    						$('#receiveWay').text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        },
		    	//根据客户ID得到要回显的行业ID,行业客户ID以及直接把联系人，联系人手机号查出来
		        getIndustryAndCustomerIdt:function (){
		    		var customerInfoId=$('#_eoss_sales_customerId').val();
		    	    var url = ctx+"/base/customermanage/customerInfo/getIndustryAndCustomerIdtByCustomerInfo?id="+customerInfoId+"&tmp="+Math.random();
		    	     $.post(url,function(data ,status){
		             	if(status="success"){
		             		$('#_industryName').text(data.industryName);
		             		$('#_customerIdtCustomerName').text(data.idtCustomerName);
		             		$('#_customerInfoName').text(data.customerInfoName);
		            		//初始化客户下拉菜单
		             		 $('#_customerContacts').text(data.contactsName);
		             		//初始化客户下拉菜单
		             		 if(data.contactsTel==null){
		             			$('#_customerContactsPhone').text('');
		             		 }else{
		             			 $('#_customerContactsPhone').text(data.contactsTel);
		             		 }
		                 }
		             });
		    	},
		        closeWindow: function(){
		        	window.opener.childColseRefresh();
		        	metaUtil.closeWindow();  
		        },
		        _back:function(){
		        	metaUtil.closeWindow();  
		        },
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =salesContractDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		salesContractDetail.doExecute('initDocument');
	}
});