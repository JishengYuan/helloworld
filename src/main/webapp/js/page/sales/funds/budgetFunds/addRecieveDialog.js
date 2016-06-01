define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap-datetimepicker");
	require("formSelect");	
	require("bootstrap");
	require("formSubmit");
	require("js/plugins/meta/js/metaDropdowmSelect");
	var addRecieveDialog = {
			config: {
				module: 'addRecieveDialog'
	        },
	        methods :{
	        	initDocument : function(){
	        		var dateTime=new Date($("#dateTime").val());
	        		 $('.date').datetimepicker({
	        		    	pickTime: false,
	        		    	startDate:dateTime,
        		    });
	        		 var id=$("#_Recieve_id").val();
	        		 addRecieveDialog.methods.getProject(id);
			        },
			        
			        getProject : function(id){
			        	$("#_project_div").empty();
			        	if(id==null||id==""){
			        		$("#_project_div").metaDropdownSelect({
			       				 url:ctx+"/sales/funds/budgetFunds/getSalesContractByCode",
			        			 searchUrl:ctx+"/sales/funds/budgetFunds/getSalesContract",
			        			 inputShowValueId:"",
			        			 placeholder:"请输入要搜索的合同名称或客户名称,按回车键",
			        			 width:"230",
			       				 height:"13",
			        			 onSelect:function(id,obj){
			        				 $("#_Recieve_id").val(id);
			        				 addRecieveDialog.methods.getSalesInfo(id);
									},
							 });
			        	}else{
			        		$("#_project_div").metaDropdownSelect({
			       				 url:ctx+"/sales/funds/budgetFunds/getSalesContractByCode",
			        			 searchUrl:ctx+"/sales/funds/budgetFunds/getSalesContract",
			        			 inputShowValueId:id,
			        			 placeholder:"请输入要搜索的合同名称或客户名称,按回车键",
			        			 width:"230",
			       				 height:"13",
			        			 onSelect:function(ids,obj){
			        				 if(ids!=null){
			        					 $("#_Recieve_id").val(ids);
			        					 addRecieveDialog.methods.getSalesInfo(ids);
			        				 }
									},
							 });
			        		addRecieveDialog.methods.getSalesInfo(id);
			        	}
			        	
			        },
			        
			        getSalesInfo:function(id){
			        	$("#recieveList").empty();
			        	 $.ajax({
				        		type : "GET",
				        		async : false,
				        		dataType : "json",
				        		url : ctx + "/sales/funds/budgetFunds/getSalesInfo?id="+id+"&tmp="+ Math.random(),
				        		success : function(date) {
				        			$("#contractAmount").html(date.contractAmount);
				        			$("#receive").html(date.receive);
				        			$("#customer").html(date.customer);
				        			var recieveList=date.recieveList;
				        			if(recieveList!=null){
				        				var str='<h5>收款计划</h5><table id="_sales_feeincome" width="100%" cellspacing="0" border="0" id="editGridName"  class="table table-striped table-bordered table-hover">';
					        			str+="<thead><tr>";
					        			str+="<td>计划收款金额</td>";
					        			str+="<td>计划收款日期</td>";
					        			str+="</tr></thead>";
					        			str+="<tbody>";
					        			for(var i=0;i<recieveList.length;i++){
					        				str+='</tr><td class="sino_table_label" >'+recieveList[i].planedReceiveAmount+'</td>';
					        				str+='<td class="sino_table_label" >'+recieveList[i].planedReceiveDate+'</td></tr>';
					        			}
					        			str+='</tbody></table>';
					        			$("#recieveList").append(str);
				        			}
				        			
				        		}
				        	});
			        },
			        
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =addRecieveDialog.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	        
	exports.init = function() {
		addRecieveDialog.doExecute('initDocument');
	}
});