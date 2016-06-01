define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("confirm_dialog");
	require("formSubmit");
	require("changTag");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	var paymentDetail = {
			config: {
				module: 'paymentDetail',
            	namespace:'/business/payment'
	        },
	        methods :{
	        	initDocument : function(){
					/*
	        		 * 绑定触发事件
	        		 */
		        	$("#_eoss_business_payment_back").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/manage";
		    			paymentDetail.doExecute("goBack",opts);
		        	});
		        	/* $("#_order_payment tbody").find("tr").each(function(i,ele){
		        		//回显科目
	        			 var coursesType=$('#coursesType'+i).text();
	        			 paymentDetail.methods.payCoursesType(coursesType,i);
	        			 //回显发票类型
	 	        		var invoiceTypePay=$('#invoiceTypePay'+i).text();
	 	        		paymentDetail.methods.payInvoiceType(invoiceTypePay,i);
	        		 });*/
		        	paymentDetail.methods.payInvoiceType();
		        	paymentDetail.methods.payCoursesType();
		        },
		        payCoursesType:function(){
		        	
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=coursesType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == $('#_ctype').val()){
		    						$('#coursesType').html(msg[i].name);
		    					}
		    				}
		    			}
		    		});
	        	},
	        	payInvoiceType:function(){
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == $('#_itype').val()){
		    						$('#invoiceTypePay').html(msg[i].name);
		    					}
		    				}
		    			}
		    		});
	        	},

		        goBack : function(opts){
		        	metaUtil.closeWindow();   
		        },
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =paymentDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		paymentDetail.doExecute('initDocument');
	}
});