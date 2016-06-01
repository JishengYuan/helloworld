define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("confirm_dialog");
	require("uic/message_dialog");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	// 批量替换字符
	String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
	}
	var salesCachet={
			config: {
            	namespace:ctx+'/sales/cachet'
	        },
	        methods :{
	        	initDocument:function(){
	        		/*
	        		 * 绑定触发事件
	        		 */
	        		 $("#_sino_eoss_sales_cachet_back").bind('click',function(){
	        			 salesCachet.methods._back();
	                 });
	        		 $("#_sino_eoss_sales_cachet_add").bind('click',function(){
	        			 salesCachet.methods._add();
	        	     });
	        		 //初始化附件上传组件
	        		 salesCachet.methods.showFileField();
	        		 //回显合同类型
	        		 salesCachet.methods.getContractType();
	        		 //回显客户，联系人，联系人手机号
	        		 salesCachet.methods.getCustomerInfoContactsPhone();
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
	        	showFileField:function(){
	        	    require.async("formFileField", function() {
		        		var attr = {};
		        		attr.salesContractId = $('#salesContractId').val();
		        		attr.fileUploadFlag = "add";
	        			$.ajax({
	        				url:ctx+"/sales/cachet/fileUploadData",
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
	        	_back:function(){
	        		//刷新父级窗口
	        		window.opener.childColseRefresh();
	        		//关闭当前子窗口
	        		metaUtil.closeWindow(); 
	        	},
	        	_add:function(){
	        		$("#attachIds").val($("#uplaodfile").fileField("getValue"));
	        		var datas=$("#_sino_eoss_sales_cachet_addform").serialize();
	        		
	        		var attachIds = $("#attachIds").val();
		       	    if(attachIds == ""||attachIds == null){
		       	    	UicDialog.Error("请上传附件!");
		       	    	return;
		       	     }
	        		$("#_sino_eoss_sales_cachet_add").unbind('click');
	        		
	        		var url = ctx+'/sales/cachet/doSave?tmp='+Math.random();
	        		$.post(url,datas,
	    	            function(data,status){
	    	            	if(data=="success"){
	    	                	  UicDialog.Success("提交盖章申请成功!",function(){
	    	                		  salesCachet.methods._back();
	    	                	  });
	    	                  }else{
	    	                  	  UicDialog.Error("提交盖章申请失败！");
	    	                  	  salesCachet.methods._back();
	    	                  }
	    	        });
	        	}
	        }
		}
	exports.init = function(){
		salesCachet.methods.initDocument();  
	}
});