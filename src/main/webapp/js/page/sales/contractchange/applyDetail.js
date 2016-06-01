define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("confirm_dialog");
	require("uic/message_dialog");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	var salesContractChangeApplyCheck={
			config: {
            	namespace:ctx+'/sales/contractchange'
	        },
	        methods :{
	        	initDocument:function(){
	        		/*
	        		 * 绑定触发事件
	        		 */
	        		 $("#_sino_eoss_sales_contract_change_back").bind('click',function(){
	        			 window.history.go(-1);
	        			 //salesContractChangeApplyCheck.methods._back();
	                 });
	        		 $("#_sino_eoss_sales_contract_change_check").bind('click',function(){
	        			 $("#_sino_eoss_sales_contract_change_check").unbind('click');
	        			 salesContractChangeApplyCheck.methods._add();
	        	     });
	        		 //回显合同类型
	        		 salesContractChangeApplyCheck.methods.getContractType();
	        		 //回显客户，联系人，联系人手机号
	        		 salesContractChangeApplyCheck.methods.getCustomerInfoContactsPhone();
	        	
	        		 var statusTypeJson = {};
	        		 $.ajax({
	 					type : "GET",
	 					async : false,
	 					dataType : "json",
	 					url : ctx+ "/sysDomain/findDomainValue?code=orderStatus&?tmp="+ Math.random(),
	 					success : function(msg) {
	 						statusTypeJson = msg;
	 					}
	 				});
	        		 
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
	        	_back:function(){
	        		//刷新父级窗口
	        		window.opener.reload();
	        		//关闭当前子窗口
	        		metaUtil.closeWindow(); 
	        	},
	        	_add:function(){
	        		var datas=$("#_sino_eoss_sales_contract_change_checkform").serialize();
	        		var url = ctx+'/sales/contractchange/handleFlow?tmp='+Math.random();
	        		$.post(url,datas,
	    	            function(data,status){
	    	            	if(data=="success"){
//	    	                	  UicDialog.Success("保存数据成功!",function(){
	    	                		  salesContractChangeApplyCheck.methods._back();
//	    	                	  });
	    	                  }else{
	    	                  	  UicDialog.Error("保存数据失败！");
	    	                  	  salesContractChangeApplyCheck.methods._back();
	    	                  }
	    	        });
	        	}
	        }
		}
	exports.init = function(){
		salesContractChangeApplyCheck.methods.initDocument();  
	}
});