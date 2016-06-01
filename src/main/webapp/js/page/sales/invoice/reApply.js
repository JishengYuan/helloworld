define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("formSubmit");
	//------------------加载DataTable-----------Start--------------
	var Map = require("map");
	require("confirm_dialog");
	require("uic/message_dialog");
	gridStore = new Map();//
	gridTypeStore=new Map();//表格行列类型
	gridFieldStore = new Map();
	require("json2");
	require("js/common/form/data_grid");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	//------------------加载DataTable-------------End------------
	var salesInvoice={
			config: {
            	namespace:ctx+'/sales/invoice'
	        },
	        methods :{
	        	initDocument:function(){
	        		//------------------加载DataTable-----------Start--------------
	        		var $editgrid = $("#editTable");   //前台jsp中的div的ID
	        		var $row = $("<ul>").attr("class", "clearfix");
	        		var grid = new Array();
	        		var gridType=new Array();
	        		var field = form.fieldList;// 列数组
	        		for (var i = 0; i < field.length; i++) {
	        			grid.push(field[i].name);
	        			gridType.push(field[i].type);
	        		}
	        		gridStore.put(form.name, grid);// 表格行
	        		gridTypeStore.put(form.name,gridType)//类型
	        		$editgrid.append($row.data_grid(form));
	        		
					$("#editGridName tbody").find("tr").each(function(i,ele){
						$(ele).find("td").each(function(j,elem){
							if(j == 3){
								var divId = $(elem).find("div").attr("id");
								var inputName = $(elem).find("input").attr("name");
								var _invoiceType = $("#"+divId);
								_invoiceType.addClass("li_form");
								var optionCompPosType = {
										writeType : 'show',
										showLabel : false,
										required:true,
										code : "invoiceType",
										onSelect:function(node){
											$('#'+inputName).val($("#"+divId).formSelect("getValue")[0]);
										},
										width : "280"
											
								};
								_invoiceType.formSelect(optionCompPosType);
								_invoiceType.formSelect('setValue',$('#'+inputName).val());
							}
						});
					});
	        		//------------------加载DataTable-------------End------------
	        		/*
	        		 * 绑定触发事件
	        		 */
	        		 $("#_sino_eoss_sales_contract_invoice_cancel").bind('click',function(){
	        			 salesInvoice.methods._back("proc");
	                 });
	        		 
	        		 $("#_sino_eoss_sales_contract_invoice_reSubmit").bind('click',function(){
	        			 $("#_sino_eoss_sales_contract_invoice_reSubmit").unbind('click');
	        			 $("#invoiceState").val("CXTJ");
	        			 salesInvoice.methods._reSubmit();
	        	     });
	        		 $("#_sino_eoss_sales_contract_invoice_NotReSubmit").bind('click',function(){
	        			 $("#_sino_eoss_sales_contract_invoice_NotReSubmit").unbind('click');
	     				//将该合同状态置为废弃
	     				$("#invoiceState").val("FQ");
	     				salesInvoice.methods._abolish();
	        	     });
	        		 //回显合同类型
	        		 salesInvoice.methods.getContractType();
	        		 //回显客户，联系人，联系人手机号
	        		 salesInvoice.methods.getCustomerInfoContactsPhone();
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
	        	_back:function(colseType){
		    		//刷新父级窗口
		        	if(colseType=="proc"){//如果是“待办”审核后刷新“待办事项”这个父页面
		        		window.opener.reload();
		        	}
		        	if(colseType=="detail"){//如果是“待办”审核后刷新“合同管理”这个父页面
		        		window.opener.childColseRefresh();
		        	}
		        	//关闭当前子窗口
		        	metaUtil.closeWindow();  
	        	},
	        	_reSubmit:function(){
		       		 var obj = new Object();
		       		 var grid = gridStore.get(form.name);// 获取表格行列name
		       		 var gridType=gridTypeStore.get(form.name);
		       		 var gridStoreData =  $('#' + form.name + ' tbody tr').data_grid('getValue',grid, form.name,gridType);
		       		 obj[form.name]=gridStoreData;
		       		 var json_data = JSON.stringify(obj);
		       	     $("#tableData").val(json_data);
		       		 var applyAmount=0;
	        		 $("#editTable tbody").find("tr").each(function(i,ele){ 
	        			 var index;
	        			 if(i==0){
	        				 index=i+1;
	        			 }else if(i>0){
	        				 index=1+i*4;
	        			 }
	        			 if($("#invoiceAmount_"+index).val()!=""&&$("#invoiceAmount_"+index).val()!=null){
	        				 applyAmount=metaUtil.accAdd(applyAmount,$("#invoiceAmount_"+index).val());
	        			 }
					});
	        		 //与剩余可开发票金额进行比较如果大于剩余金额，则提示不让提交
	        		 var surplusAmount=$("#surplusAmount").val();
	        		 if(applyAmount>surplusAmount){
	        			 UicDialog.Error("此次申请的发票金额超过了剩余可开发票金额！");
	        			 return;
	        		 }
		       	     var datas=$("#_sino_eoss_sales_invoice_reApplyForm").serialize();
		       	     var url = ctx+'/sales/invoice/handleFlow?tmp='+Math.random();
		       	     var options = {};
		       	     options.formId = "_sino_eoss_sales_invoice_reApplyForm";
		       	     if($.formSubmit.doHandler(options)){
		       	    	 $.post(url,datas,
		       	    			 function(data,status){
		       	    		 if(data=="success"){
		       	    			 UicDialog.Success("重新提交该合同发票申请成功!",function(){
		       	    				salesInvoice.methods._back("proc");
		       	    			 });
		       	    		 }else{
		       	    			 UicDialog.Error("重新提交该合同发票申请失败！",function(){
		       	    				salesInvoice.methods._back("proc");
		       	    			 });
		       	    		 }
		       	    	 });
		       	     }
		        },
	        	_abolish:function(){
		       		 var obj = new Object();
		       		 var grid = gridStore.get(form.name);// 获取表格行列name
		       		 var gridType=gridTypeStore.get(form.name);
		       		 var gridStoreData =  $('#' + form.name + ' tbody tr').data_grid('getValue',grid, form.name,gridType);
		       		 obj[form.name]=gridStoreData;
		       		 var json_data = JSON.stringify(obj);
		       	     $("#tableData").val(json_data);
//		       	     $("#attachIds").val($("#uplaodfile").fileField("getValue"));
		       	     var datas=$("#_sino_eoss_sales_invoice_reApplyForm").serialize();
		       	     var url = ctx+'/sales/invoice/handleFlow?tmp='+Math.random();
		       	     var options = {};
		       	     options.formId = "_sino_eoss_sales_invoice_reApplyForm";
		       	     if($.formSubmit.doHandler(options)){
		       	    	 $.post(url,datas,
		       	    			 function(data,status){
		       	    		 if(data=="success"){
		       	    			 UicDialog.Success("废弃该合同发票申请成功!",function(){
		       	    				salesInvoice.methods._back("proc");
		       	    			 });
		       	    		 }else{
		       	    			 UicDialog.Error("废弃该合同发票申请失败！",function(){
		       	    				salesInvoice.methods._back("proc");
		       	    			 });
		       	    		 }
		       	    	 });
		       	     }
	        	}
	        }
		}
	exports.init = function(){
		salesInvoice.methods.initDocument();  
	}
});