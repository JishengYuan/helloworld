define(function(require, exports, module) {
	var $ = require("jquery");
	var StringBuffer = require("stringBuffer");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	var Map = require("map");
	
	pMap = new Map();
	pNMap = new Map();
	trMap = new Map();
	
//	var metaUtil = require("js/plugins/meta/js/metaUtil");

	var contractOrderTypeJson = {};
	var statusTypeJson = {};

	var contractAllOrder = {
		config : {
			module : 'contractAllOrder'
		},

		methods : {
			initDocument : function() {
				// $.ajax({
				// type : "GET",
				// async : false,
				// dataType : "json",
				// url : ctx +
				// "/sysDomain/findDomainValue?code=contractOrderType&?tmp="+
				// Math.random(),
				// success : function(msg) {
				// contractOrderTypeJson = msg;
				// }
				// });

				$.ajax({
					type : "GET",
					async : false,
					dataType : "json",
					url : ctx+ "/sysDomain/findDomainValue?code=orderStatus&?tmp="+ Math.random(),
					success : function(msg) {
						statusTypeJson = msg;
					}
				});
				contractAllOrder.doExecute('contractOrderStatus');
				contractAllOrder.doExecute('getContractType');
				contractAllOrder.doExecute('getCustomerInfoContactsPhone');
				contractAllOrder.doExecute('getContractName');
				
				$("#_sino_eoss_sales_contract_order_back").bind('click',function(){
					metaUtil.closeWindow(); 
                });
				$("#_contract_order_product tbody").find("tr").each(function(i,ele){
					$(ele).find("td").each(function(j,elem){
						if(j == 2){
							pMap.put(i,$(elem).html());
						}
					});
				});
				for(var n = 0;n < pMap.keys().length;n++){
					var m = 0;
					var ke = pMap.keys()[n];
					var val = pMap.get(ke);
					$("#_contract_order_product tbody").find("tr").each(function(i,ele){
						$(ele).find("td").each(function(j,elem){
							if(j == 2){
								if(val == $(elem).html()){
									pNMap.put(n,m++);
								}
							}
						});
					});
					
				}
				for(var i = 0;i < pNMap.keys().length;i++){
					var ke = pNMap.keys()[i];
					var val = pNMap.get(ke);
					if(val >= 1){
						$("#tr_"+ke).find("td").each(function(j,elem){
							if(j < 4){
								$(elem).attr("rowspan",val+1);
								$(elem).attr("td_index",1);
							}
						});
					}
				}
				
				for(var i = 0;i <= $("tr td[td_index='1']").length;i++){
					var obj = $("tr td[td_index='1']")[i];
					if(obj != null){
						trMap.put($(obj).parent().attr("id"),i);
					}
				}
				for(var i = 0;i < trMap.keys().length;i++){
					var ke = trMap.keys()[i];
					if(i != 0){
						$("#"+ke).find("td").each(function(i,elem){
							if(i < 4){
								$(elem).remove();
							}
						});
					}
				}
				
			},
			
			getContractName:function(){
	        	if($("td[name = 'relateDeliveryProduct_contract']").length > 0){
	        		$("#_contract_show").hide();
	        		$("#_contract_hidden").show();
	        		$("._order_hidden").show();
	        		if($("td[name = 'relateDeliveryProduct_contract_']").length > 0){
	        			$("td[name = 'relateDeliveryProduct_contract_']").after("<td></td>");
	        		}
	        	} else {
	        		$("#_contract_show").show();
	        		$("#_contract_hidden").hide();
	        		$("._order_hidden").hide();
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
			getCustomerInfoContactsPhone: function(){
	    		var customerInfoId=$('#_eoss_sales_customerId').val();
	    	    var url = ctx+"/base/customermanage/customerInfo/getIndustryAndCustomerIdtByCustomerInfo?id="+customerInfoId+"&tmp="+Math.random();
	    	     $.post(url,function(data ,status){
	             	if(status="success"){
	             		$('#_industryName').text(data.industryName);
	             		$('#_customerIdtCustomerName').text(data.idtCustomerName);
	             		$('#_customerInfoName').text(data.customerInfoName);
	             		 $('#_customerContacts').text(data.contactsName);
	             		 if(data.contactsTel=='null'){
	             			$('#_customerContactsPhone').text('无');
	             		 }else{
	             			 $('#_customerContactsPhone').text(data.contactsTel);
	             		 }
	                 }
	             });
	        },
			contractOrderStatus : function() {
				$('#_contract_order tbody').find("tr").each(function(i,ele){
	        			$(ele).find("td").each(function(j,elem){
	        				if(j == 3){
	        					var $td = $(elem);
	        					var contractOrderStatus = $td.attr("tdId");
	        					for(var m in statusTypeJson){
	    	        				if(statusTypeJson[m].id == contractOrderStatus){
	    	        					$td.html(statusTypeJson[m].name);
	    	        				}
	    	        			}
	        				}
	        			});
	        		});
			}

		},
		/**
		 * 执行方法操作
		 */
		doExecute : function(flag, param) {
			var method = contractAllOrder.methods[flag];
			if (typeof method === 'function') {
				return method(param);
			} else {
				alert('操作 ' + flag + ' 暂未实现！');
			}
		}
	}
	exports.init = function() {
		contractAllOrder.doExecute('initDocument');
	}
});