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
	        		salesContractDetail.doExecute('getIndustryAndCustomerIdt');
		        	//审批按钮
		        	$("#ok_Contract_Add").unbind('click').click(function () {
		        		salesContractDetail.methods.approveToFlow();
		        	});
		        	//详情返回按钮
		        	$("#back_page").unbind('click').click(function () {
		        		salesContractDetail.methods.closeWindow('detail');
		        	});
		        	$("#table_size tbody").find("tr").each(function(i,ele){
	        			 var payTypeId=$('#payType_'+i).text();
	        			 salesContractDetail.methods.serviceType(payTypeId,i);
	        			 var payDescId=$('#payDesc_'+i).text();
	        			 salesContractDetail.methods.payDesc(payDescId,i);
	        			 
	        		 });
		        	$("#table_chapter tbody").find("tr").each(function(i,ele){
	        			 var chapterTypeId=$('#chapterType_'+i).text();
	        			 salesContractDetail.methods.chapterType(chapterTypeId,i);
	        		 });
		        	$("#table_fication tbody").find("tr").each(function(i,ele){
	        			 var qualificationsId=$('#qualifications_'+i).text();
	        			 salesContractDetail.methods.qualifications(qualificationsId,i);
	        			/* var qualificationsTypeId=$('#qualificationsType_'+i).text();
	        			 salesContractDetail.methods.qualificationsType(qualificationsTypeId,i);*/
	        		 });
		        	$("#table_certificate tbody").find("tr").each(function(i,ele){
	        			 var certificateTypeId=$('#certificateType_'+i).text();
	        			 salesContractDetail.methods.certificateType(certificateTypeId,i);
	        		 });
		        	
		        },
		        certificateType : function(certificateTypeId,j){
		        	$.ajax({
	        			type : "GET",
	        			async : false,
	        			dataType : "json",
	        			url : ctx + "/sales/contractFound/findSalesType?type=3&tmp="+ Math.random(),
	        			success : function(msg) {
	        				for(var i in msg){
	        					if(msg[i].id == certificateTypeId){
	        						$('#certificateType_'+j).text(msg[i].name);
	        					}
	        				}
	        			}
	        		});
		        },
		      /*  qualificationsType : function(qualificationsTypeId,j){
		        	$.ajax({
	        			type : "GET",
	        			async : false,
	        			dataType : "json",
	        			url : ctx + "/sales/contractFound/findSalesType?code=2&tmp="+ Math.random(),
	        			success : function(msg) {
	        				for(var i in msg){
	        					if(msg[i].id == qualificationsTypeId){
	        						$('#qualificationsType_'+j).text(msg[i].name);
	        					}
	        				}
	        			}
	        		});
		        },*/
		        qualifications : function(qualificationsId,j){
		        	$.ajax({
	        			type : "GET",
	        			async : false,
	        			dataType : "json",
	        			url : ctx + "/sales/contractFound/findSalesType?type=2&tmp="+ Math.random(),
	        			success : function(msg) {
	        				for(var i in msg){
	        					if(msg[i].id == qualificationsId){
	        						$('#qualifications_'+j).text(msg[i].name);
	        					}
	        				}
	        			}
	        		});
		        },
		        chapterType : function(chapterTypeId,j){
		        	$.ajax({
	        			type : "GET",
	        			async : false,
	        			dataType : "json",
	        			url : ctx + "/sales/contractFound/findSalesType?type=1&tmp="+ Math.random(),
	        			success : function(msg) {
	        				for(var i in msg){
	        					if(msg[i].id == chapterTypeId){
	        						$('#chapterType_'+j).text(msg[i].name);
	        					}
	        				}
	        			}
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
		        
		    	//根据客户ID得到要回显的行业ID,行业客户ID以及直接把联系人，联系人手机号查出来
		        getIndustryAndCustomerIdt:function (){
		        	var customerId=$('#_eoss_sales_customerId').val();
		    	    var url = ctx+"/base/customermanage/customerInfo/getIndustryAndCustomerIdtByCustomerInfo?id="+customerId+"&tmp="+Math.random();
		    	     $.post(url,function(data ,status){
		             	if(status="success"){
		             		$('#_customerInfoName').text(data.customerInfoName);
		                 }
		             });
		    		//var contactId=$('#customerContact').val();
		    	   /* var url1 = ctx+"/sales/contract/getCustomerContactById?customerId="+customerId+"&contactId="+contactId+"&tmp="+Math.random();
		    	     $.post(url1,function(data ,status){
		             	if(status="success"){
		             		 $('#_customerContacts').html(data.name);
		             		$('#_customerContactsPhone').html(data.telphone);
		                 }
		             });*/
		    	},
		        closeWindow: function(colseType){//审核后刷新父页面
		        	//关闭当前子窗口
		        	window.opener.childColseRefresh();
		        	metaUtil.closeWindow();  
		        },
		        approveToFlow : function(){
		        	var options = {};
		    		options.murl = ctx+"/sales/contractFound/doSave";
		    		var datas=$("#_sino_eoss_cuotomer_addform").serialize();
		    		//启动遮盖层
	       	    	 $('#_progress1').show();
	       	    	 $('#_progress2').show();
	       	    	 $('#dailogs1').hide();
		    	    $.post(options.murl,datas,
		    	          function(data,status){
		    	            if(status=="success"){
		    	            	UicDialog.Success("提交成功!",function(){
		    	            		salesContractDetail.doExecute("closeWindow","proc");
		    	                });
		    	            }else{
		    	                UicDialog.Error("提交失败！",function(){
		    	                	salesContractDetail.doExecute("closeWindow","proc");
		    	                });
		    	           }
		    	    });
		        }
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