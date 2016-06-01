define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
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
	        			 salesCachet.methods._back("proc");
	                 });
	        		 $("#_sino_eoss_sales_contract_cachet_submit").bind('click',function(){
	        			 $("#_sino_eoss_sales_contract_cachet_submit").unbind('click');
			        	 var opts = {};
			        	 opts.url = "/handleFlow";
	        			 salesCachet.methods.approve(opts);
	        			 //setTimeout("_sino_eoss_sales_contract_cachet_submit.disabled=false;",3000);  
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
		        		attr.fileUploadFlag = "detail";
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
	        	_back:function(colseType){
		    		//刷新父级窗口
		        	if(colseType=='proc'){//如果是“待办”审核后刷新“待办事项”这个父页面
		        		window.opener.reload();
		        	}
		        	if(colseType=='detail'){//如果是“待办”审核后刷新“合同管理”这个父页面
		        		window.opener.childColseRefresh();
		        	}
		        	//关闭当前子窗口
		        	metaUtil.closeWindow();  
	        	},
	        	approve:function(opts){
		        	var options = {};
		    		options.murl = salesCachet.config.namespace + opts.url;
		    		var datas=$("#_sino_eoss_sales_cachet_approveForm").serialize();
		    	    $.post(options.murl,datas,
		    	          function(data,status){
		    	            if(status=="success"){
		    	            	salesCachet.methods._back("proc");
		    	               /* UicDialog.Success("审批成功!",function(){
		    	                });*/
		    	            }else{
		    	                UicDialog.Error("审批失败！",function(){
		    	                	salesCachet.methods._back("proc");
		    	                });
		    	           }
		    	    });
	        	}
	        }
		}
	exports.init = function(){
		salesCachet.methods.initDocument();  
	}
});