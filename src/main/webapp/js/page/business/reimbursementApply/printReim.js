define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("uic/message_dialog");

	var reimDetail = {
			config: {
				module: 'reimDetail',
				uicTable : 'uicTable',
            	namespace:'/business/reimbursement'
	        },
	        methods :{
	        	initDocument : function(){
	        		reimDetail.doExecute('getFormSelect');
	        		$("#_order_reim tbody").find("tr").each(function(i,ele){
	        			var serviceTypeId=$('#coursesType'+i).text();
	        			reimDetail.methods.getCourseTypes(serviceTypeId,i);
	        		});
		        },
		        getCourseTypes : function(ids,j){
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=coursesType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == ids){
		    						$('#coursesType'+j).html(msg[i].name);
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
				var method =reimDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		reimDetail.doExecute('initDocument');
	}
});