define(function(require, exports, module) {
	var $ = require("jquery");
	require("uic/message_dialog");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	
	//------------------加载DataTable-----------Start--------------
	var salesContractDetail = {
			config: {
				module: 'salesContractDetail',
            	namespace:'/sales/contractFound'
	        },
	        methods :{
	        	initDocument : function(){
		        	//审批按钮
		        	$("#ok_Contract_dailogs").unbind('click').click(function () {
		        		salesContractDetail.methods.approveToFlow();
		        	});
		        	$("#table_size tbody").find("tr").each(function(i,ele){
	        			 var payTypeId=$('#payType_'+i).text();
	        			 salesContractDetail.methods.serviceType(payTypeId,i);
	        			 var payDescId=$('#payDesc_'+i).text();
	        			 salesContractDetail.methods.payDesc(payDescId,i);
	        			 
	        		 });
		        	
		        },
		        payDesc : function(payDescId,j){
		        	$.ajax({
	        			type : "GET",
	        			async : false,
	        			dataType : "json",
	        			url : ctx + "/sysDomain/findDomainValue?code=currentMoneyUses&tmp="+ Math.random(),
	        			success : function(msg) {
	        				for(var i in msg){
	        					if(msg[i].id == payDescId){
	        						$('#payDesc_'+j).text(msg[i].name);
	        					}
	        				}
	        			}
	        		});
		        },
		        serviceType : function(payTypeId,j){
	        		$.ajax({
	        			type : "GET",
	        			async : false,
	        			dataType : "json",
	        			url : ctx + "/sysDomain/findDomainValue?code=paymentMode&tmp="+ Math.random(),
	        			success : function(msg) {
	        				for(var i in msg){
	        					if(msg[i].id == payTypeId){
	        						$('#payType_'+j).text(msg[i].name);
	        					}
	        				}
	        			}
	        		});
	        	},
		        
		        approveToFlow : function(){
		        	var id = $("#size_id").val();
		        	var amount = $("#size_amount").val();
		        	 var num = $("#num").val();
		        	$("#amount_"+num).html(amount);
		        	$.ajax({
		                 url: ctx+"/sales/contractFound/doSizeAmount?id="+id+"&amount="+amount,  // 提交的页面
		                 //data: $('#addForm').serialize(), // 从表单中获取数据
		                 type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
		                 dataType:"json",       
		                 success: function(data) {
		                 }
		        	 });
		        	 $('#dailogs1').modal('hide');
		        	 
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