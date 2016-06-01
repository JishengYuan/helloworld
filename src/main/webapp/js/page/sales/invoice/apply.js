define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("confirm_dialog");
	require("uic/message_dialog");
	require("changTag");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	//------------------加载DataTable-----------Start--------------
	var Map = require("map");
	require("confirm_dialog");
	require("uic/message_dialog");
	gridStore = new Map();//
	gridTypeStore=new Map();//表格行列类型
	gridFieldStore = new Map();
	require("json2");
	require("js/common/form/data_grid");
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
							if((j*i+3)%3==0){
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
					//點擊條目添加行按鈕，設置每行下拉菜單的樣式
	        		 $(".add").bind('click',function(){
	        			 var tr_id=($("#editGridName tbody").find("tr").length)*4-1;
	        			 $("#invoiceType_"+tr_id+"_div").children().find(".uicSelectInp").css("width","206");
	        			 $("#invoiceType_"+tr_id+"_div").children().find(".uicSelectData").css("width","216"); 
	                 });
	        		 $("#_sino_eoss_sales_invoice_cancel").bind('click',function(){
	        			 metaUtil.closeWindow();
	        			 //salesInvoice.methods._back("update");
	                 });
	        		 $("#_sino_eoss_sales_invoice_add").bind('click',function(){
	        			 salesInvoice.methods._add();
	        	     });
	        		 //回显合同类型
	        		 salesInvoice.methods.getContractType();
	        		 //回显客户，联系人，联系人手机号
	        		 salesInvoice.methods.getCustomerInfoContactsPhone();
	        	},
	        	buttonDelay:function(obj){
                    $("#_sino_eoss_sales_invoice_add").attr("class","btn");
                    $("#_sino_eoss_sales_invoice_add").unbind("click");
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
	        		//alert(colseType);
	        		//刷新父级窗口
	        		var close=$("#colseType").val();
	        		
	        		if(close=='prod'){//详情页面添加后刷新父页面
	            		window.opener.location.reload();
	            	}
	            	/*if(colseType=='prod'){//详情页面添加后刷新父页面
	            		window.opener.location.reload();
	            	}*/
	            	if(close=='addNew'){//第一次添加后刷新父页面
	            		window.opener.childColseRefresh();
	            	}
	            	//关闭当前子窗口
	            	metaUtil.closeWindow();  
	        	},
	        	_add:function(){
		       		 var applyAmount=0;
	        		 $("#editTable tbody").find("tr").each(function(i,ele){ 
	        			 var index;
	        			 if(i==0){
	        				 index=i+1;
	        			 }else if(i>0){
	        				 index=1+i*4;
	        			 }
	        			 var invoiceAmount_ = $("#invoiceAmount_"+index).val();
	        			 invoiceAmount_ = invoiceAmount_.replace(/\,/g,"");
        				 $("#invoiceAmount_"+index).val(invoiceAmount_)
	        			 if(invoiceAmount_!=""&&invoiceAmount_!=null){
	        				 applyAmount=metaUtil.accAdd(applyAmount,invoiceAmount_);
	        			 }
					});
	        		 var obj = new Object();
		       		 var grid = gridStore.get(form.name);// 获取表格行列name
		       		 var gridType=gridTypeStore.get(form.name);
		       		 var gridStoreData =  $('#' + form.name + ' tbody tr').data_grid('getSelectValue',grid, form.name,gridType);
		       		 obj[form.name]=gridStoreData;
		       		if(gridStoreData == '}'||gridStoreData == ''){
		       			UicDialog.Error("请选择要提交的发票明细!");
	        			return;
		       		 }
		       		 var json_data = JSON.stringify(obj);
		       	     $("#tableData").val(json_data);
	        		 //与剩余可开发票金额进行比较如果大于剩余金额，则提示不让提交
	        		 var surplusAmount=$("#surplusAmount").val();
	        		 if(applyAmount>surplusAmount){
	        			 UicDialog.Error("此次申请的发票金额超过了剩余可开发票金额！");
	        			 return;
	        		 }
	        		var datas=$("#_sino_eoss_sales_invoice_addform").serialize();
	        		var url = ctx+'/sales/invoice/doSave?tmp='+Math.random();
	        		salesInvoice.methods.buttonDelay();
	        		$.post(url,datas,
	    	            function(data,status){
	    	            	if(data=="success"){
	    	                	  UicDialog.Success("发票申请成功!",function(){
	    	                		//关闭当前子窗口
	    	      	        		window.opener.childColseRefresh();
	    	                		  metaUtil.closeWindow();
//	    	                		  salesInvoice.methods._back();
	    	                	  });
	    	                  }else{
	    	                  	  UicDialog.Error("发票申请失败！");
	    	                  	  	salesInvoice.methods._back();
	    	                  }
	    	        });
	        	}
	        }
		}
	exports.init = function(){
		salesInvoice.methods.initDocument();  
	}
});