define(function(require, exports, module) {
	var $ = require("jquery");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	var Map = require("map");
	require("confirm_dialog");
	require("uic/message_dialog");
	require("json2");
	require("js/common/form/data_grid");
	String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
	}
	var orderDetail = {
			config: {
				module: 'orderDetail',
				namespace: '/business/changeOrder',
	        },
	        methods :{
	        	initDocument : function(){
	        		orderDetail.doExecute('getFormSelect');
	        		 //加载附件组件
	        		 $("#_reim tbody").find("tr").each(function(i,ele){
	        			 var invoiceTypeId=$('#invoiceTypeReim'+i).text();
	 	        		orderDetail.methods.reimInvoiceType(invoiceTypeId,i);
	        		 });
	        		 $("#_payment tbody").find("tr").each(function(i,ele){
	        			 var invoiceTypePay=$('#invoiceTypePay'+i).text();
	 	        		orderDetail.methods.payInvoiceType(invoiceTypePay,i);
	        		 });
	        		 $("#dsave").unbind('click').click(function () {
			        		var opts = {};
			    			opts.url = "/close";
			    			orderDetail.doExecute("closeCon",opts);
			        	});
		        	$("#_do_cancel").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/manage";
		    			orderDetail.doExecute("goBack",opts);
		        	});
	        	},
	        	  closeCon:function(opts){
			        	var options = {};
			        	options.murl = ctx+orderDetail.config.namespace + opts.url;
			    		var datas=$("#_eoss_business_order").serialize();
			    		 $.post(options.murl,datas,
				    	          function(data,status){
				    	            if(status=="success"){
				    	                UicDialog.Success("审批成功!",function(){
				    	                	orderDetail.doExecute("closeWindow",opts);
				    	                });
				    	            }else{
				    	                UicDialog.Error("审批失败！",function(){
				    	                	orderDetail.doExecute("closeWindow",opts);
				    	                });
				    	           }
				    	    });
			        },
	        	payInvoiceType:function(invoiceTypePay,j){
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
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
		        goBack : function(opts){
		        	metaUtil.closeWindow();  
		        },
        	 closeWindow: function(){
		        	window.opener.childColseRefresh();
		        	metaUtil.closeWindow();  
		        },
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
	exports.init = function() {
		orderDetail.doExecute('initDocument');
	}
});