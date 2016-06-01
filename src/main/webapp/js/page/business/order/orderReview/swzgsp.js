define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("jqBootstrapValidation");
	require("confirm_dialog");
	require("uic/message_dialog");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	var swzgSp = {
			config: {
				module: 'spOrderDetail',
				supplierName:'supplierName',
				orderType:'orderType',
				invoiceType:'invoiceType',
				purchaseType:'purchaseType',
				paymentMode:'paymentMode',
            	namespace:'/business/order'
	        },
	        methods :{
	        	initDocument : function(){
	        		swzgSp.doExecute('getFormSelect');
	        		//确认审批结果
	        		$("#_order_OK").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/handleFlow";
		    			swzgSp.doExecute("approveToFlow",opts);
		        	});
	        		$("#_eoss_business_back").unbind('click').click(function () {
		        		var opts = {};
		        		opts.url = "/manage";
		        		swzgSp.doExecute("closeWindow",opts);
		        	});
	        		/*$("#product_table tbody").find("tr").each(function(i,ele){
	        			 var serviceTypeId=$('#serviceType'+i).text();
	        			 swzgSp.methods.serviceType(serviceTypeId,i);
	        		 });*/
	        		
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
			    			url : ctx + "/sysDomain/findDomainValue?code=orderType&tmp="+ Math.random(),
			    			success : function(msg) {
			    				for(var i in msg){
			    					if(msg[i].id == $('#_orderType').val()){
			    						$('#orderType').html(msg[i].name);
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
	        	 },
	        	 closeWindow: function(){
	        		   //刷新父级窗口
	        			window.opener.reload();
	        			//关闭当前子窗口
	        			metaUtil.closeWindow();  
	        	 },
	        	 approveToFlow : function(opts){
			        	var options = {};
			    		options.murl = ctx+swzgSp.config.namespace + opts.url;
			    		var datas=$("#_eoss_business_order").serialize();
			    		 //启动遮盖层
				    	 $('#_progress1').show();
				    	 $('#_progress2').show();
			    		 $.post(options.murl,datas,
				    	          function(data,status){
				    	            if(status=="success"){
				    	                swzgSp.doExecute("closeWindow",opts);
				    	            }else{
				    	                UicDialog.Error("审批失败！",function(){
				    	                	swzgSp.doExecute("closeWindow",opts);
				    	                });
				    	           }
				    	    });
			    		
			        }

	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =swzgSp.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		swzgSp.doExecute('initDocument');
	}
});