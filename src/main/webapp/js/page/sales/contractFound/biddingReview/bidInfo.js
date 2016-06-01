define(function(require, exports, module) {
	var $ = require("jquery");
	require("uic/message_dialog");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	require("js/plugins/meta/js/metaDropdowmSelect");
	//------------------加载DataTable-----------Start--------------
	var salesContractDetail = {
			config: {
				module: 'salesContractDetail',
            	namespace:'/sales/contractFound/biddingReview'
	        },
	        methods :{
	        	initDocument : function(){
	        		salesContractDetail.doExecute('getIndustryAndCustomerIdt');
		        	//关闭按钮
		        	$("#back_page").unbind('click').click(function () {
		        		salesContractDetail.methods.closeWindow('detail');
		        	});
		        	//往来款付款操作
		        	$(".payfounds").unbind('click').click(function () {
		        		salesContractDetail.methods.execPayPrice(this.id,$('#realpayprice').val());
		        	});
		        	//章借出操作
		        	$(".stampborrow").unbind('click').click(function () {
		        		
		       	    	 var datas={"stampid":this.id};
		       	    	 var url = ctx+"/sales/contractFoundOpt/stampborrow";
			    	    $.post(url,datas,
			    	          function(data,status){
			    	            if(status=="success"){
			    	                UicDialog.Success("章已借出!",function(){
			    	                	salesContractDetail.doExecute("refreshWindow");
			    	                });
			    	            }else{
			    	                UicDialog.Error("操作失败！",function(){
			    	              
			    	                });
			    	           }
			    	    },"json");
		        	});
		        	
		        	
		        	//章归还操作
		        	$(".stampreturn").unbind('click').click(function () {
		        		
		       	    	 var datas={"stampid":this.id};
		       	    	 var url = ctx+"/sales/contractFoundOpt/stampreturn";
			    	    $.post(url,datas,
			    	          function(data,status){
			    	            if(status=="success"){
			    	                UicDialog.Success("章已归还!",function(){
			    	                	salesContractDetail.doExecute("refreshWindow");
			    	                });
			    	            }else{
			    	                UicDialog.Error("操作失败！",function(){
			    	              
			    	                });
			    	           }
			    	    },"json");
		        	});
		        	
		        	//资质借出操作
		        	$(".qualificationborrow").unbind('click').click(function () {
		       	    	 var datas={"qualificationid":this.id};
		       	    	 var url = ctx+"/sales/contractFoundOpt/qualificationborrow";
			    	    $.post(url,datas,
			    	          function(data,status){
			    	            if(status=="success"){
			    	                UicDialog.Success("资质已借出!",function(){
			    	                	salesContractDetail.doExecute("refreshWindow");
			    	                });
			    	            }else{
			    	                UicDialog.Error("操作失败！",function(){
			    	              
			    	                });
			    	           }
			    	    },"json");
		        	});
		        	
		        	//资质归还操作
		        	$(".qualificationreturn").unbind('click').click(function () {
		       	    	 var datas={"qualificationid":this.id};
		       	    	 var url = ctx+"/sales/contractFoundOpt/qualificationreturn";
			    	    $.post(url,datas,
			    	          function(data,status){
			    	            if(status=="success"){
			    	                UicDialog.Success("资质已归还!",function(){
			    	                	salesContractDetail.doExecute("refreshWindow");
			    	                });
			    	            }else{
			    	                UicDialog.Error("操作失败！",function(){
			    	              
			    	                });
			    	           }
			    	    },"json");
		        	});
		        	
		        	//保证金项目划归
		        	$(".projectcontractPrice").unbind('click').click(function () {
		        		var mergercontractPrice = $("#mergercontractPrice").val();
		        		var contractCode = $("#contractCode").val();
		        		var foundId = $("#foundId").val();
		       	    	 var datas={"foundId":foundId,"contractCode":contractCode,"mergercontractPrice":mergercontractPrice};
		       	    	 var url = ctx+"/sales/contractFoundOpt/mergercontractPrice";
			    	    $.post(url,datas,
			    	          function(data,status){
			    	            if(status=="success"){
			    	                UicDialog.Success("投标保证金项目划归完成!",function(){
			    	                	salesContractDetail.doExecute("refreshWindow");
			    	                });
			    	            }else{
			    	                UicDialog.Error("操作失败！",function(){
			    	              
			    	                });
			    	           }
			    	    },"json");
		        	});
		        	//返还保证金
		        	$(".returncontractPrice").unbind('click').click(function () {
		        		var returnPrice = $("#returnPrice").val();
		        		var foundId = $("#foundId").val();
		       	    	 var datas={"foundId":foundId,"returnPrice":returnPrice};
		       	    	 var url = ctx+"/sales/contractFoundOpt/returncontractPrice";
			    	    $.post(url,datas,
			    	          function(data,status){
			    	            if(status=="success"){
			    	                UicDialog.Success("投标保证金项目划归完成!",function(){
			    	                	salesContractDetail.doExecute("refreshWindow");
			    	                });
			    	            }else{
			    	                UicDialog.Error("操作失败！",function(){
			    	              
			    	                });
			    	           }
			    	    },"json");
		        	});
		        	
		        	$("#table_size tbody").find("tr").each(function(i,ele){
	        			 var payTypeId=$('#payType_'+i).text();
	        			 salesContractDetail.methods.serviceType(payTypeId,i);
	        			 var payDescId=$('#payDesc_'+i).text();
	        			 salesContractDetail.methods.payDesc(payDescId,i);
	        		 });
		        	
		        	salesContractDetail.methods.getContract();
		        	$("input:radio[name='isAgree']").unbind('click').click(function () {
	        			var radioValue = $('input:radio[name="isAgree"]:checked').val();
	        			if(radioValue == 0){
	        				$('._contract_hidden').hide();
	        				$("#contractCode").val("");
	        				$("#_select_contractName_div_input").val("");
	        				$('._return_hidden').show();
	        			} else {
	        				$('._return_hidden').hide();
	        				$("#returnPrice").val("");
	        				$('._contract_hidden').show();
	        			}
	        		});
		        },
		        execPayPrice:function(payId,payPrice){
	       	    	 var datas={"payid":payId,"payPrice":payPrice};
	       	    	 var url = ctx+"/sales/contractFoundOpt/realpay";
		    	    $.post(url,datas,
		    	          function(data,status){
		    	            if(status=="success"){
		    	                UicDialog.Success("付款操作成功!",function(){
		    	                	salesContractDetail.doExecute("refreshWindow");
		    	                });
		    	            }else{
		    	                UicDialog.Error("审批失败！",function(){
		    	              
		    	                	//salesContractDetail.doExecute("closeWindow","proc");
		    	                });
		    	           }
		    	    },"json");
		        },
		        getContract:function(){
		        	var name = $("#_select_contractName_div").val();
		        	$("#_select_contractName_div").empty();
					$("#_select_contractName_div").metaDropdownSelect({
	       				 url:ctx+"/sales/contractFound/biddingReview/getSalesContractByCode",
	        			 searchUrl:ctx+"/sales/contractFound/biddingReview/getSalesContract",
	        			 inputShowValueId:name,
	        			 required:true,
	        			 placeholder:"请输入要搜索的合同名称,按回车键",
	        			 width:"230",
	       				 height:"21",
	        			 onSelect:function(id,obj){
	        				 $('#contractCode').val(id);
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
		    	},
		        closeWindow: function(colseType){//审核后刷新父页面
		    		//刷新父级窗口
		        	window.opener.childColseRefresh();
		        	//关闭当前子窗口
		        	metaUtil.closeWindow();  
		        },
		        refreshWindow:function(){
		        	window.location.reload();
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