define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap-datetimepicker");
	require("formSelect");	
	require("bootstrap");
	require("formSubmit");
	require("js/plugins/meta/js/metaDropdowmSelect");
	var addInvoiceDialog = {
			config: {
				module: 'addInvoiceDialog'
	        },
	        methods :{
	        	initDocument : function(){
	        		var dateTime=new Date($("#dateTime").val());
	        		 $('.date').datetimepicker({
	        		    	pickTime: false,
	        		    	startDate:dateTime,
       		    });
	        		 var id=$("#_invoice_id").val();
	        		 addInvoiceDialog.methods.getProject(id);
		        },
		        getProject : function(id){
		        	$("#_project_Invoice").empty();
		        	if(id==""||id==null){
		        		$("#_project_Invoice").metaDropdownSelect({
		       				 url:ctx+"/sales/funds/budgetFunds/getSalesContractByCode",
		        			 searchUrl:ctx+"/sales/funds/budgetFunds/getSalesContract",
		        			 inputShowValueId:"",
		        			 placeholder:"请输入要搜索的合同名称或客户名称,按回车键",
		        			 width:"230",
		       				 height:"13",
		        			 onSelect:function(id,obj){
		        				 $("#_invoice_id").val(id);
		        				 addInvoiceDialog.methods.getSalesInfo(id);
								},
						 });
		        	}else{
		        		$("#_project_Invoice").metaDropdownSelect({
		       				 url:ctx+"/sales/funds/budgetFunds/getSalesContractByCode",
		        			 searchUrl:ctx+"/sales/funds/budgetFunds/getSalesContract",
		        			 inputShowValueId:id,
		        			 placeholder:"请输入要搜索的合同名称或客户名称,按回车键",
		        			 width:"230",
		       				 height:"13",
		        			 onSelect:function(ids,obj){
		        				 $("#_invoice_id").val(ids);
		        					 addInvoiceDialog.methods.getSalesInfo(ids);
								},
						 });
		        		addInvoiceDialog.methods.getSalesInfo(id);
		        	}
		        	
		        },
		        
		        getSalesInfo:function(id){
		        	$("#invoiceList").empty();
		        	 $.ajax({
			        		type : "GET",
			        		async : false,
			        		dataType : "json",
			        		url : ctx + "/sales/funds/budgetFunds/getSalesInfo?id="+id+"&tmp="+ Math.random(),
			        		success : function(date) {
			        			$("#contractAmount_a").html(date.contractAmount);
			        			$("#invoiceAmount").html(date.invoice);
			        			$("#customer_a").html(date.customer);
			        			var invoiceList=date.invoiceList;
			        			if(invoiceList!=null){
			        				var str='<h5>发票详情</h5><table id="_sales_feeincome" width="100%" cellspacing="0" border="0" id="editGridName"  class="table table-striped table-bordered table-hover">';
				        			str+="<thead><tr>";
				        			str+="<td>发票金额</td>";
				        			str+="<td>开票日期</td>";
				        			str+="</tr></thead>";
				        			str+="<tbody>";
				        			for(var i=0;i<invoiceList.length;i++){
				        				str+='</tr><td class="sino_table_label" >'+invoiceList[i].InvoiceAmount+'</td>';
				        				str+='<td class="sino_table_label" >'+invoiceList[i].InvoiceTime+'</td></tr>';
				        			}
				        			str+='</tbody></table>';
				        			$("#invoiceList").append(str);
			        			}
			        			
			        		}
			        	});
		        },
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =addInvoiceDialog.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	        
	exports.init = function() {
		addInvoiceDialog.doExecute('initDocument');
	}
});