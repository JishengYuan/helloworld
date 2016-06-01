define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("confirm_dialog");
	require("uic/message_dialog");
	require("formSubmit");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	var salesContractChangeApply={
			config: {
            	namespace:ctx+'/sales/contractchange'
	        },
	        methods :{
	        	initDocument:function(){
	        		/*
	        		 * 绑定触发事件
	        		 */
	        		 $("#_sino_eoss_sales_contract_change_back").bind('click',function(){
	        			 salesContractChangeApply.methods._back();
	                 });
	        		 $("#_sino_eoss_sales_contract_change_add").bind('click',function(){
	        			 salesContractChangeApply.methods._add();
	        	     });
	        		 //回显合同类型
	        		 salesContractChangeApply.methods.getContractType();
	        		 //回显客户，联系人，联系人手机号
	        		 salesContractChangeApply.methods.getCustomerInfoContactsPhone();
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
	        		window.opener.childColseRefresh();
	        		//关闭当前子窗口
	        		metaUtil.closeWindow(); 
	        	},
	        	_add:function(){
	        		var salesContractId=$('#salesContractId').val();
	        		if($(":radio:checked").val()==5){
	        			var url = ctx+"/sales/contractchange/findSalesContractId?salesContractId="+salesContractId+"&tmp="+Math.random();
	        			$.post(url,function(data ,status){
	        				if(data=="fail"){
	        					$("#flat").val("YGL");
	        				}else{
	        					$("#flat").val("WGL");
	        				}
	        				
	        			});
	        		}
	        		if($("#flat").val()=="YGL"){
        				UicDialog.Error("合同变更申请提交失败！此合同已被订单关联，请联系采购员删除关联订单。");
    					return;
        			}else{
        				var datas=$("#_sino_eoss_sales_contract_change_addform").serialize();
    	        		var url = ctx+'/sales/contractchange/doSave?tmp='+Math.random();
    	        		
//    	        		if($("input[name='changeType']:checked").attr("value") == 2){
//    	        			UicDialog.Error("此功能暂未实现");
//    	        			return;
//    	        		}
    	        		
    	        		var options = {};
    	       	        options.formId = "_sino_eoss_sales_contract_change_addform";
    	       	        if($.formSubmit.doHandler(options)){
    	       	        	$("#_sino_eoss_sales_contract_change_add").unbind('click');
    	       	        	$.post(url,datas,
    	    	    	            function(data,status){
    	    	    	            	if(data=="success"){
    	    	    	                	  UicDialog.Success("合同变更申请已经提交!",function(){
    	    	    	                		  salesContractChangeApply.methods._back();
    	    	    	                	  });
    	    	    	                  }else{
    	    	    	                  	  UicDialog.Error("合同变更申请提交失败！");
    	    	    	                  	salesContractChangeApply.methods._back();
    	    	    	                  }
    	    	    	        });
    	       	        }
        			}
	        	}
	        }
		}
	exports.init = function(){
		salesContractChangeApply.methods.initDocument();  
	}
});