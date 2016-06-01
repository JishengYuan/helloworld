define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("uic/message_dialog");

	var paymentDetail = {
			config: {
				module: 'paymentDetail',
				uicTable : 'uicTable',
            	namespace:'/business/payment'
	        },
	        methods :{
	        	initDocument : function(){
	        		paymentDetail.doExecute('getFormSelect');
	        		$("#_payment_detail tbody").find("tr").each(function(i,ele){
	        			var serviceTypeId=$('#type'+i).text();
	        			paymentDetail.methods.getCourseTypes(serviceTypeId,i);
	        		});
		        },

		        getCourseTypes:function(ids,j){
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=coursesType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == ids){
		    						$('#type'+j).html(msg[i].name);
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
		    			url : ctx + "/sysDomain/findDomainValue?code=coursesType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == $('#_coursesTypeId').val()){
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
		        	
		        	
		        }

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