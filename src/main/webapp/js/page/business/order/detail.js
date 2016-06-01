define(function(require, exports, module) {
	var $ = require("jquery");
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
	        			 var invoiceTypeId=$('#invoiceTypeReim_'+i).text();
	 	        		orderDetail.methods.reimInvoiceType(invoiceTypeId,i);
	 	        		/*var  reimBursStatusId=$('#reimBursStatus'+i).text();
	 	        		orderDetail.methods.reimBursStatus(reimBursStatusId,i);*/
	        		 });
	        		 $("#_payment tbody").find("tr").each(function(i,ele){
	        			 var invoiceTypePay=$('#invoiceTypePay'+i).text();
	 	        		orderDetail.methods.payInvoiceType(invoiceTypePay,i);
	 	        		/*var planStatusId=$('#planStatus'+i).text();
	 	        		orderDetail.methods.planStatus(planStatusId,i);*/
	        		 });
	        		 /*$("#product_table tbody").find("tr").each(function(i,ele){
	        			 var serviceTypeId=$('#serviceType'+i).text();
	 	        		orderDetail.methods.serviceType(serviceTypeId,i);
	        		 });*/
	        		 
	        		/* $("#_reimbursement").unbind('click').click(function () {
	        			   orderDetail.doExecute("_reimbursement");
			        	});
	        		 $("#_payment").unbind('click').click(function () {
	        			   orderDetail.doExecute("_payment");
			        	});*/
		        	$("#_eoss_business_order_back").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/manage";
		    			orderDetail.doExecute("goBack",opts);
		        	});
		        	
		        	
		        	for(var i=0;i<$("table[id^='_sales_']").length;i++){
		        		var productAmount=0;
		        		$("#_sales_"+i).find("tr[id]").each(function(j,trEle){
		        			$(trEle).find("td[name]").each(function(m,tdEle){
		        					productAmount+=Number($(tdEle).attr("tdvalue"));
		        			});
		        		});
		        		$("#totallProduct_"+i).html("￥"+fmoney(productAmount,2));
		        	}
		        	
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
		    			url : ctx + "/sysDomain/findDomainValue?code=taxrateType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == invoiceTypeId){
		    						$('#invoiceTypeReim_'+j).text(msg[i].name);
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