define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSubmit");
	require("formSelect");
	require("formTree");

	require("bootstrap-datetimepicker");
	require("bootstrap");
	require("jqBootstrapValidation");
	var Map = require("map");
	require("confirm_dialog");
	require("uic/message_dialog");
	
	var dataTable$ = require("dataTables");
	var uic$ = require("uic_Dropdown");
	var DT_bootstrap = require("DT_bootstrap");
	
	
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
	}
	var orderDetail = {
			config: {
				module: 'orderDetail',
				supplierName:'supplierName',
				invoiceType:'invoiceType',
				purchaseType:'purchaseType',
				orderType:'orderType',
            	namespace:'/business/order'
	        },
	        methods :{
	        	initDocument : function(){
	        		$("a[name='hrefReload']").unbind('click').click(function () {
	        			var url = $(this).attr("hrefA");
	        			window.location.href = url;
	        		});
	        		orderDetail.doExecute('getFormSelect');
	        		 //加载附件组件
	        		orderDetail.methods.showFileField();
	        		 $("#_reim tbody").find("tr").each(function(i,ele){
	        			 var invoiceTypeId=$('#invoiceTypeReim'+i).text();
	 	        		orderDetail.methods.reimInvoiceType(invoiceTypeId,i);
	        		 });
	        		 $("#_payment tbody").find("tr").each(function(i,ele){
	        			 var invoiceTypePay=$('#invoiceTypePay'+i).text();
	 	        		orderDetail.methods.payInvoiceType(invoiceTypePay,i);

	        		 });

	        		 
	        
		        	$("#_eoss_business_order_back").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/manage";
		    			orderDetail.doExecute("goBack",opts);
		        	});
		        	
		        	$("#_ok_Save").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/manage";
		        		orderDetail.doExecute("_save",opts);
		        		//_save();
		   	       });

		        		var productAmount2=0;
		        		$("#tbody_products").find("tr").each(function(j,trEle){
		        			 $(trEle).find("td").each(function(j,elem){
			   					 if(j == 6){
			   						//alert($(elem).find("input").first().val());
			   						productAmount2=metaUtil.accAdd(productAmount2,$(elem).find("input").first().val());
			   					 }
			   				 });
		        		});
		        		$("#totallProducts").html("￥"+fmoney(productAmount2,2));
		        		
		        		
		        		//修改单价，计算采购成本
		        		$(".unitPricesClass").blur(function(){
		        			 var num = $(this).attr("num");
		        			 var quantity = Number($('#quantitys'+num).val());
     	           			 var unitPrice = Number($('#unitPrices'+num).val());
    
     	           			 var subPrice = 0;
     	           			 if(!isNaN(unitPrice) && !isNaN(quantity)){
     	           				subPrice = metaUtil.accMul(quantity,unitPrice);
     	           				$('#subTotals'+num).val(subPrice);
     	           			 }else{
     	           				UicDialog.Error("单价 请输入正确格式的数字！");
     	           				$('#unitPrices'+num).val(0);
     	           				$('#subTotals'+num).val(0);
     	           			 }

		        			var totalCount = 0;
				   			 $("#tbody_products").find("tr").each(function(i,ele){ 
				   				 $(ele).find("td").each(function(j,elem){
				   					 if(j == 6){
				   						 totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
				   					 }
				   				 });
				   			  });
				   			 $("#totallProducts").html('￥'+fmoney(totalCount,2));
				   			 $('#orderAmountSpan').html('￥'+fmoney(totalCount,2));
				   			 $('#_orderAmount').val(totalCount);
				   			 
				   			
		        		});
		        		
		        		//修改总价，计算采购成本 
		        		$(".subTotalsClass").blur(function(){
		        			 var num = $(this).attr("num");
		        			 var quantity = Number($('#quantitys'+num).val());
     	           			 var subTotals = Number($('#subTotals'+num).val());
     	           			 
     	           			 var unitPrice2 = 0;
     	           			 if(!isNaN(subTotals) && !isNaN(quantity)){
     	           				unitPrice2 = subTotals/quantity;
     	           				$('#unitPrices'+num).val(unitPrice2);
     	           			 }else{
     	           				UicDialog.Error("总价 请输入正确格式的数字！");
     	           				$('#subTotals'+num).val(0);
     	           				$('#unitPrices'+num).val(0);
     	           			 }
		        			
		        			var totalCount = 0;
				   			 $("#tbody_products").find("tr").each(function(i,ele){ 
				   				 $(ele).find("td").each(function(j,elem){
				   					 if(j == 6){
				   						 //alert($(elem).find("input").first().val());
				   						 totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
				   					 }
				   				 });
				   			  });
				   			 $("#totallProducts").html('￥'+fmoney(totalCount,2));
				   			 $('#orderAmountSpan').html('￥'+fmoney(totalCount,2));
				   			 $('#_orderAmount').val(totalCount);
		        		});
	            		 
		        	
		       
		        	$("#_eoss_business_order_cachet_sub").unbind('click').click(function () {
		        		var id = $("#id").val();
		        		var advice = $("#_order_cachet_advice").val();
		        		$.ajax({
			    			type : "POST",
			    			data: "&cachetAdvice="+advice,
			    			url : ctx + "/business/order/cachet/cachetApprove?id="+id+"&?tmp="+ Math.random(),
			    			error:function(){
			    				UicDialog.Error("操作失败!");
			    			},
			    			success : function(msg) {
			    				if(msg == 'success'){
			    					window.opener.childColseRefresh();
			    		    		metaUtil.closeWindow();
			    				} else {
			    					UicDialog.Error("操作失败!");
			    				}
			    			}
			    		});
		        	});
	        	},
	        	_reimbursement:function(){
	        		
	        	},
	        	_payment:function(){
	        		
	        	},
	        	//保存修改
	        	_save:function(){
	        		 var datas=$("#_eoss_business_order").serialize();
		       	     var options = {};
		       	     options.formId = "_eoss_business_order";
		       	     var url =  ctx+'/business/order/doSaveOrUpdateUsd?tmp='+Math.random();
		       	     
		       	     if($.formSubmit.doHandler(options)){
	       		    	//启动遮盖层
	       	   	    	 $('#_progress1').show();
	       	   	    	 $('#_progress2').show();
	       	   	    	 $.post(url,datas,
	       	 	 	            function(data,status){
	       	   	    		        var status= $("#isSubmit").val();
	       	 	 	            	if(data.result=="success"){
	       		 	            			UicDialog.Success("订单修改成功!",function(){
	       		 	            			var opts = {};
	       		 	            			opts.url = "/manage";
	       		 	            			orderDetail.doExecute("goBack",opts);
	       		 	            			});

	       		 	                  }else{
	       		 	                		 UicDialog.Error("订单修改失败!"+data.message);
	       		 	                	  $('#_progress1').hide();
	       		 	                	  $('#_progress2').hide();
	       		 	                  }
	       	 	 	                },"json"); 
	       		     }
	        	},
	        	
	        	
	        	planStatus:function(planStatusId,j){
	        		$.ajax({
	        			type : "GET",
	        			async : false,
	        			dataType : "json",
	        			url : ctx + "/sysDomain/findDomainValue?code=planStatus&tmp="+ Math.random(),
	        			success : function(msg) {
	        				for(var i in msg){
	        					if(msg[i].id == planStatusId){
	        						$('#planStatus'+j).html(msg[i].name);
	        					}
	        				}
	        			}
	        		});
	        	},
	        	reimBursStatus:function(reimBursStatusId,j){
	        		$.ajax({
	        			type : "GET",
	        			async : false,
	        			dataType : "json",
	        			url : ctx + "/sysDomain/findDomainValue?code=reimBursStatus&tmp="+ Math.random(),
	        			success : function(msg) {
	        				for(var i in msg){
	        					if(msg[i].id == reimBursStatusId){
	        						$('#reimBursStatus'+j).text(msg[i].name);
	        					}
	        				}
	        			}
	        		});
	        	},
	        	serviceType : function(serviceTypeId,j){
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
	        			}
	        		});
	        		
	        	},
	        	payInvoiceType:function(invoiceTypePay,j){
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=taxType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == invoiceTypePay){
		    						$('#invoiceTypePay'+j).text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
	        		
	        	},
	        	reimInvoiceType:function(invoiceTypeId,j){
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == invoiceTypeId){
		    						$('#invoiceTypeReim'+j).text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
	        	},
		        	getFormSelect : function(){
		        		/*$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx + "/sysDomain/findDomainValue?code=orderProfits&tmp="+ Math.random(),
			    			success : function(msg) {
			    				for(var i in msg){
			    					if(msg[i].id == $('#_orderProfits').val()){
			    						$('#_eoss_orderProfits').html(msg[i].name);
			    					}
			    				}
			    			}
			    		});*/
		        		//结算币种
		        		$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx + "/sysDomain/findDomainValue?code=accountCurrency&?tmp="+ Math.random(),
			    			success : function(msg) {
			    				for(var i in msg){
			    					if(msg[i].id == $('#accountCurrency').val()){
			    						$('#currency').html(msg[i].name);
			    					}
			    				}
			    			}
			    		});
		        		
		        		$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx + "/sysDomain/findDomainValue?code=paymentMode&tmp="+ Math.random(),
			    			success : function(msg) {
			    				for(var i in msg){
			    					if(msg[i].id == $('#_eoss_business_paymentModeId').val()){
			    						$('#_eoss_business_paymentMode').html(msg[i].name);
			    					}
			    				}
			    			}
			    		});
		        		$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx + "/sysDomain/findDomainValue?code=deliveryAddress&tmp="+ Math.random(),
			    			success : function(msg) {
			    				for(var i in msg){
			    					if(msg[i].id == $('#_business_deliveryAddressId').val()){
			    						$('#_business_deliveryAddress').html(msg[i].name);
			    					}
			    				}
			    			}
			    		});
		        		$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx + "/sysDomain/findDomainValue?code=supplierType&tmp="+ Math.random(),
			    			success : function(msg) {
			    				for(var i in msg){
			    					if(msg[i].id == $('#supplierType').val()){
			    						$('#_supplierType').html(msg[i].name);
			    					}
			    				}
			    			}
			    		});
		        		$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx + "/sysDomain/findDomainValue?code=orderStatus&?tmp="+ Math.random(),
			    			success : function(msg) {
			    				for(var i in msg){
			    					if(msg[i].id == $('#orderStatus').val()){
			    						$('#status').html(msg[i].name);
			    					}
			    				}
			    			}
			    		});
		        		$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
			    			success : function(msg) {
			    				for(var i in msg){
			    					if(msg[i].id == $('#_invoiceType').val()){
			    						$('#invoiceType').html(msg[i].name);
			    					}
			    				}
			    			}
			    		});
		        		
		        		$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx + "/sysDomain/findDomainValue?code=purchaseType&tmp="+ Math.random(),
			    			success : function(msg) {
			    				for(var i in msg){
			    					if(msg[i].id == $('#_purchaseType').val()){
			    						$('#purchaseType').html(msg[i].name);
			    					}
			    				}
			    			}
			    		});
		        		$.ajax({
		        			type : "GET",
		        			async : false,
		        			dataType : "json",
		        			url : ctx + "/sysDomain/findDomainValue?code=orderType&tmp="+ Math.random(),
		        			success : function(msg) {
		        				for(var i in msg){
		        					if(msg[i].id == $('#_orderType').val()){
		        						$('#orderType').html(msg[i].name);
		        					}
		        				}
		        			}
		        		});
		        		
		        	},
		        	showFileField:function(){
		        	    require.async("formFileField", function() {
			        		var attr = {};
			        		attr.id = $('#id').val();
			        		attr.fileUploadFlag = "detail";
		        			$.ajax({
		        				url:ctx+"/business/order/fileUploadData",
		        				data:attr,
		        				dataType : "json", 
		        				success : function(result){  
		        			    	var $field2 = $("#uplaodfile");
		        			    	var $field1 = $("<li>").attr("id", "field_" + result.name);
		        			    	$field1.css("width", "900");
		        			    	$field2.append($field1);
		        			    	var tmp = $field1.fileField(result);
		        			    	$field2.append(tmp);
		        			    }  
		        			});
		        	    });
		        	},
		        goBack : function(opts){
		        	window.opener.childColseRefresh();
		        	metaUtil.closeWindow();  
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =orderDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
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
	exports.init = function() {
		orderDetail.doExecute('initDocument');
	}
});