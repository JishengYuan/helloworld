define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("uic/message_dialog");

	var applyDetail = {
			config: {
				module: 'reimDetail',
				uicTable : 'uicTable',
            	namespace:'/sales/invoice'
	        },
	        methods :{
	        	initDocument : function(){
	        		applyDetail.doExecute('getFormSelect');
	        		applyDetail.methods.getCustomerInfoContactsPhone();
		        },

		        getFormSelect : function(){
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == $('#_eoss_sales_invoiceTypeId').val()){
		    						$('#invoiceType').text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=coursesType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == $('#_coursesType').val()){
		    						$('#coursesType').html(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        	$("#_print_button").unbind('click').click(function () {
		        		window.print();
//	        			try{
//	        				WebBrowser.ExecWB(6,6);
//        				} catch(e){
//        					alert("您的浏览器不支持此功能")
//    					}
	        		});
		        	
		        	
		        },
		        getCustomerInfoContactsPhone: function(){
		    		var customerInfoId=$('#_eoss_sales_customerId').val();
		    	    var url = ctx+"/base/customermanage/customerInfo/getIndustryAndCustomerIdtByCustomerInfo?id="+customerInfoId+"&tmp="+Math.random();
		    	     $.post(url,function(data ,status){
		             	if(status=="success"){
		             		$('#_customerInfoName').text(data.customerInfoName);
		                 }
		             });
		        },

	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =applyDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		applyDetail.doExecute('initDocument');
	}
});