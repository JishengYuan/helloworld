define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("formUser");
	//------------------加载DataTable-----------Start--------------
	var Map = require("map");
	require("confirm_dialog");
	require("uic/message_dialog");
	require("easyui");
	gridStore = new Map();//
	gridTypeStore=new Map();//表格行列类型
	gridFieldStore = new Map();
	require("json2");
	require("js/common/form/data_grid");
	var timeObj = require("js/plugins/meta/js/timeObjectUtil");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	
	var StringBuffer = require("stringBuffer");
	//------------------加载DataTable-------------End------------
	String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
	}
	var changeSalesContract = {
			config: {
				module: 'changeSalesContract',
            	namespace:'/sales/contract'
	        },
	        methods :{
	        	initDocument : function(){
	        		
	        		//产品总额
	       		 var totalCount = 0;
	       		 if($('#_eoss_sales_contractTypeId').val() == '3000'){
	       			 $('#_sino_eoss_sales_contract_maproducts_table tbody').find("tr").each(function(i,ele){
	       				 $(ele).find("td").each(function(j,elem){
	       					 if(j == 9){
	       						 //totalCount+=parseInt($(elem).find("input").first().val());
	       						totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
	       					 }
	       				 });
	       			 });
	       		 } else {
	       			 $('#_sino_eoss_sales_contract_products_table tbody').find("tr").each(function(i,ele){
	       				 $(ele).find("td").each(function(j,elem){
	       					 if(j == 8){
	       						totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
	       					 }
	       				 });
	       			 });
	       		 }
	       		 $('#_product_total').html(totalCount);
	        		
	        		changeSalesContract.methods.getDataTable();
	        		changeSalesContract.methods.getContractType();
	        		changeSalesContract.methods.getInvoiceType();
	        		changeSalesContract.methods.getAccountCurrency();
	        		changeSalesContract.methods.getReceiveWay();
	        		changeSalesContract.methods.getIndustryAndCustomerIdt();
	        		 //加载附件组件
	        		changeSalesContract.methods.showFileField();
		        	//绑定审核确认提交到流程引擎按钮
		        	$("#_sino_eoss_sales_contract_change_submit").unbind('click').click(function () {
		    			changeSalesContract.methods.doContractChange();
		        	});
	        		 $("#_sino_eoss_sales_contract_change_cancel").bind('click',function(){
	        			 changeSalesContract.methods._back();
	                 });
	        		 
	        		//设备添加
	        		 $("#_sino_eoss_sales_products_add").bind('click').click(function(){
	        				_addProducts();
	        		 });
	        		//导入设备
	        		 $("#_sino_eoss_sales_products_import").bind('click').click(function(){
	        			 _exportProducts();
	        		 });
	        		 //设备修改
	        		 $("._sino_eoss_sales_contract_update_product").unbind('click').click(function(){
	        			 _updateProducts($(this).attr("serial_num"));
	        		 });
	        		 //设备删除
	        		 $("._sino_eoss_sales_contract_update_remove_product").unbind('click').click(function(){
//	        			 _removeProducts($(this).attr("serial_num"));
	        			 _removeProductsRelate($(this).attr("serial_num"),this);
	        		 });
	        		 
	        		 $('.date').datetimepicker({
	        		    	pickTime: false
	        	    });
	        		 changeSalesContract.methods.typeSelectResult($('#_eoss_sales_contractTypeId').val());
	        		//关联备货合同
	        		 $("#_sino_eoss_sales_products_relate").bind('click').click(function(){
	        			 _relateSalesContracts();
	        		 });
	        		 
		        },
		        getDataTable : function(){
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
		        },
	        	showFileField:function(){
	        	    require.async("formFileField", function() {
		        		var attr = {};
		        		attr.salesContractId = $('#id').val();
		        		attr.fileUploadFlag = "update";
	        			$.ajax({
	        				url:ctx+"/sales/contract/fileUploadData",
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
		        getContractType : function(){
		        	var contractTypeId=$("#_eoss_sales_contractTypeId").val();
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=contractType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == contractTypeId){
		    						$('#_eoss_sales_contractType').text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
//		        	var $fieldUserTypes = $("#_eoss_sales_contractType");
//		    		$fieldUserTypes.addClass("li_form");
//		    		var optionUserTypes = {
//		    			inputValue : $('#_eoss_sales_contractTypeId').val(),
//		    			writeType : 'show',
//		    			showLabel : false,
//		    			required:true,
//		    			code : 'contractType',
//		    			width : "282",
//		    			onSelect :function(){
//		    				var type= $("#_eoss_sales_contractType").formSelect("getValue")[0];
//		    				$('#_eoss_sales_contractTypeId').val(type);
//		    				changeSalesContract.methods.typeSelectResult(type);
//		    		}
//		    		};
//		    		$fieldUserTypes.formSelect(optionUserTypes);
		        },
		        typeSelectResult:function(id){
		    		$('.product_handler').show();
		    		$('#serviceStartDate_div').show();
		    		$('#serviceEndDate_div').show();
		    		$('.collection_plan').show();
//		    		$('#contractAmount').val("");
		    		$('#type_th').html("产品类型");
		    		$('#partner_th').html("产品厂商");
		    		$('#product_th').html("产品型号");
		    		$("#contractAmount").removeAttr("readonly");
		    		$("#_sales_import_excel").attr("href",ctx+"/sales/doc/projectProductTemplate.xls");
		    		$("#_sino_eoss_sales_contract_products_table").show();
		    		$("#_sino_eoss_sales_contract_maproducts_table").hide();
		    		
		    		$("#_sino_eoss_sales_products_relate").show();
		    		//产品合同
		    		if(id == 1000){
		    		}
		    		//临时采购
		    		if(id == 2000){
		    			$('.collection_plan').hide();
		    			$("#contractAmount").val(0);
		    			$("#contractAmount").attr({readonly: 'true'});
		    		}
		    		//MA续保合同
		    		if(id == 3000){
		    			$("#_sales_import_excel").attr("href",ctx+"/sales/doc/projectMAProductTemplate.xls");
		    			$("#_sino_eoss_sales_products_add").hide();
		    			$("#_sino_eoss_sales_products_relate").hide();
		    			$("#_sino_eoss_sales_contract_maproducts_table").show();
		    			$("#_sino_eoss_sales_contract_products_table").hide();
		    			$("#_sino_eoss_sales_contract_products_table tbody").remove();
		    		}
		    		//技术服务合同
		    		if(id == 4000){
		    			//$('.product_handler').hide();
	        			$('#type_th').html("服务类型");
	        			$('#partner_th').html("服务厂商");
	        			$('#product_th').html("服务型号");
	        			$("#_sino_eoss_sales_products_relate").hide();
		    		}
		    		//采购确认函
		    		if(id == 5000){
		    			$('#serviceStartDate_div').hide();
		    			$('#serviceEndDate_div').hide();
		    			$("#_sino_eoss_sales_products_relate").hide();
		    		}
		    		//软件开发合同
		    		if(id == 6000){
		    			$('#type_th').html("软件类型");
		    			$('#partner_th').html("软件厂商");
		    			$('#product_th').html("软件版本");
		    			$("#_sino_eoss_sales_products_relate").hide();
		    		}
		    		//公司备件
		    		if(id == 7000){
		    			$('.collection_plan').hide();
		    			$('#serviceStartDate_div').hide();
		    			$('#serviceEndDate_div').hide();
		    			$('#contractAmount').val(0);
		    			$("#contractAmount").attr({readonly: 'true'});
		    			$("#_sino_eoss_sales_products_relate").hide();
		    		}
		    		//客户配件
		    		if(id == 8000){
		    			$('.collection_plan').hide();
		    			$('#serviceStartDate_div').hide();
		    			$('#serviceEndDate_div').hide();
		    			$("#_sino_eoss_sales_products_relate").hide();
		    			
		    			$("#contractAmount").val(0);
		    			$("#contractAmount").attr({readonly: 'true'});
		    		}
		    		//备货合同
		    		if(id == 9000){
		    			$('.collection_plan').hide();
		    			$("#contractAmount").val(0);
		    			$("#contractAmount").attr({readonly: 'true'});
		    			$("#_sino_eoss_sales_products_relate").hide();
		    		}
		    		//其他合同类型
		    		if(id == 0000){
		    		}
		    		if($('.product_handler').is(":hidden")){
		    			$("#_hopeArriveTime").attr("required",false);
		    		} else {
		    			$("#_hopeArriveTime").attr("required",true);
		    		}
		    		if($('#serviceStartDate_div').is(":hidden")){
		    			$("#serviceStartDate_input").attr("required",false);
		    			$("#serviceEndDate_input").attr("required",false);
		    		} else {
		    			$("#serviceStartDate_input").attr("required",true);
		    			$("#serviceEndDate_input").attr("required",true);
		    		}
		    	},
		        getInvoiceType : function(){
//		        	var invoiceTypeId=$("#_eoss_sales_invoiceTypeId").val();
//		        	$.ajax({
//		    			type : "GET",
//		    			async : false,
//		    			dataType : "json",
//		    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
//		    			success : function(msg) {
//		    				for(var i in msg){
//		    					if(msg[i].id == invoiceTypeId){
//		    						$('#invoiceType').text(msg[i].name);
//		    					}
//		    				}
//		    			}
//		    		});
		        	var $fieldUserTypes = $("#_eoss_sales_invoiceType");
		    		$fieldUserTypes.addClass("li_form");
		    		var optionUserTypes = {
		    			inputValue : $('#_eoss_sales_invoiceTypeId').val(),
		    			writeType : 'show',
		    			showLabel : false,
		    			required:true,
		    			code : 'invoiceType',
		    			width : "282",
		    			onSelect :function(){
		    				var type= $("#_eoss_sales_invoiceType").formSelect("getValue")[0];
		    				$('#_eoss_sales_invoiceTypeId').val(type);
		    		}
		    		};
		    		$fieldUserTypes.formSelect(optionUserTypes);
		        },
		        getAccountCurrency : function(){
//		        	var accountCurrencyId=$("#_eoss_sales_accountCurrencyId").val();
//		        	$.ajax({
//		    			type : "GET",
//		    			async : false,
//		    			dataType : "json",
//		    			url : ctx + "/sysDomain/findDomainValue?code=accountCurrency&tmp="+ Math.random(),
//		    			success : function(msg) {
//		    				for(var i in msg){
//		    					if(msg[i].id == accountCurrencyId){
//		    						$('#accountCurrency').text(msg[i].name);
//		    					}
//		    				}
//		    			}
//		    		});
		        	var $fieldUserTypes = $("#_eoss_sales_accountCurrency");
		    		$fieldUserTypes.addClass("li_form");
		    		var optionUserTypes = {
		    			inputValue : $('#_eoss_sales_accountCurrencyId').val(),
		    			writeType : 'show',
		    			showLabel : false,
		    			code : 'accountCurrency',
		    			width : "282",
		    			onSelect :function(){
		    				var type= $("#_eoss_sales_accountCurrency").formSelect("getValue")[0];
		    				$('#_eoss_sales_accountCurrencyId').val(type);
		    		}
		    		};
		    		$fieldUserTypes.formSelect(optionUserTypes);
		        },
		        getReceiveWay : function(){
//		        	var receiveWayId=$("#_eoss_sales_receiveWayId").val();
//		        	$.ajax({
//		    			type : "GET",
//		    			async : false,
//		    			dataType : "json",
//		    			url : ctx + "/sysDomain/findDomainValue?code=receiveWay&tmp="+ Math.random(),
//		    			success : function(msg) {
//		    				for(var i in msg){
//		    					if(msg[i].id == receiveWayId){
//		    						$('#receiveWay').text(msg[i].name);
//		    					}
//		    				}
//		    			}
//		    		});
		        	var $fieldUserTypes = $("#_eoss_sales_receiveWay");
		    		$fieldUserTypes.addClass("li_form");
		    		var optionUserTypes = {
		    			inputValue : $('#_eoss_sales_receiveWayId').val(),
		    			writeType : 'show',
		    			showLabel : false,
		    			code : 'receiveWay',
		    			width : "282",
		    			onSelect :function(){
		    				var type= $("#_eoss_sales_receiveWay").formSelect("getValue")[0];
		    				$('#_eoss_sales_receiveWayId').val(type);
		    		}
		    		};
		    		$fieldUserTypes.formSelect(optionUserTypes);
		        },
		    	//根据客户ID得到要回显的行业ID,行业客户ID以及直接把联系人，联系人手机号查出来
		        getIndustryAndCustomerIdt:function (){
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
		    	_back: function(){
		    		//刷新父级窗口
		    		window.opener.childColseRefresh();
		    		//关闭当前子窗口
		    		metaUtil.closeWindow();  
		        },
		        doContractChange:function(){
		        	
		        	var serviceTimeB = false;;
		    		$("#_sino_eoss_sales_contract_products_table").find("input[name='serviceEndDates']").each(function(i,ele){
		    			var val = $(ele).val();
		    			if(!timeObj.compareEndTimeAndtartTime(val,$("#serviceEndDate_input").val())){
		    				serviceTimeB = true;
		    			}
		    		});
		    		if(serviceTimeB){
		    			UicDialog.Error("产品服务结束时间不能大于合同服务结束,请重新填写");
		    			return;
		    		}
		    		$("#attachIds").val($("#uplaodfile").fileField("getValue"));
		    		if($("#isSubmit").val() != 'CG'){
		    			
		    			var startTime = $("#serviceStartDate_input").val();
		    			var endTime = $("#serviceEndDate_input").val();
		    			
		    			if(!timeObj.compareEndTimeAndtartTime(startTime,endTime)){
		    				UicDialog.Error("合同终止期不能小于合同生效期");
		        			return;
		    			}
		    			
		    			var totalAmount = 0;
		    			$("#editTable").find("table tbody tr").each(function(i,ele){
		    				$(ele).find("td").each(function(j,elem){
		    					if(j == 1){
		    						//totalAmount+=parseInt($(elem).find("input").first().val());
		    						totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
		    					}
		    				});
		    			});
		    			
		    			if($("#contractAmount").attr("readonly") == null){
		    				var contractAmount = parseInt($('#contractAmount').val());
		        			if(contractAmount != totalAmount){
		        				if(!$('.collection_plan').is(':hidden')){
		        					 UicDialog.Error("收款金额与合同金额不等不能提交！");
		    	    				return;
		        				}
		        			}
		        			
		        			var totalProductCount = $('#_product_total').html();
		        			if(totalProductCount != contractAmount){
		        				if(!$('.product_handler').is(':hidden')){
		        					UicDialog.Error("产品金额与合同金额不等不能提交！");
		            				return;
		        				}
		        			}
		    			}
		    			
		    			var attachIds = $("#attachIds").val();
		    	   	     if(attachIds == ""||attachIds == null){
		    	   	    	 UicDialog.Error("请上传合同附件!");
		    	   	    	 return;
		    	   	     } else {
		    	   	    	 var attachs = attachIds.split(",");
		    	   	    	 if(attachs.length < 2){
		    	   	    		 UicDialog.Error("请上传合同副本和毛利预估表!");
		    	       	    	 return;
		    	   	    	 }
		    	   	     }
		    		}
		    		
		    		//计划收款时间 验证
		    		var b = false;
		    		$("#editGridName .hasDatepicker").each(function(i,ele){
		    			var val = $(ele).val();
		    			$("input[name^='planedReceiveAmount']").each(function(j,elem){
		    				var inputValue = $(elem).val();
		    				if(i == j&&inputValue != null&&inputValue != ""){
		    					if(val == ""||val == null){
		    						b = true;
		        				}
		    				}
		    			});
		    		});
		    		if(b){
		    			UicDialog.Error("计划收款时间不能为空！");
		    			return;
		    		}
		    		 var obj = new Object();
		    		 var grid = gridStore.get(form.name);// 获取表格行列name
		    		 var gridType=gridTypeStore.get(form.name);
		    		 var gridStoreData =  $('#' + form.name + ' tbody tr').data_grid('getValue',grid, form.name,gridType);
		    		 obj[form.name]=gridStoreData;
		    		 var json_data = JSON.stringify(obj);
		    	     $("#tableData").val(json_data);
		    	     $("#attachIds").val($("#uplaodfile").fileField("getValue"));
		       	     var applyAmount=0;
		    		 $("#editGridName tbody").find("tr").each(function(i,ele){ 
		    			 var index;
		    			 if(i==0){
		    				 index=i+1;
		    			 }else if(i>0){
		    				 index=1+i*3;
		    			 }
		    			 if($("#planedReceiveAmount_"+index).val()!=""&&$("#planedReceiveAmount_"+index).val()!=null){
		    				 //applyAmount+=parseFloat($("#planedReceiveAmount_"+index).val());
		    				 applyAmount=metaUtil.accAdd(applyAmount,$("#planedReceiveAmount_"+index).val());
		    			 }
		    		});
		    		 //与剩余可开发票金额进行比较如果大于剩余金额，则提示不让提交
		    		 var contractAmount=$("#contractAmount").val();
		    		 if(applyAmount>contractAmount){
		    			 UicDialog.Error("收款金额超过了合同金额！");
		    			 return;
		    		 }
		        	
	        		var datas=$("#_sino_eoss_sales_contract_change_addform").serialize();
	        		var url = ctx+'/sales/contract/doContractChange?tmp='+Math.random()+'&changeType='+$("#changeType").val();
	        		$("#_sino_eoss_sales_contract_change_submit").unbind('click');
	        		$.post(url,datas,
	    	            function(data,status){
	    	            	if(data=="success"){
	    	                	  UicDialog.Success("保存数据成功!",function(){
	    	                		  changeSalesContract.methods._back();
	    	                	  });
	    	                  }else{
	    	                  	  UicDialog.Error("保存数据失败！");
	    	                  	  changeSalesContract.methods._back();
	    	                  }
	    	        }); 
		        }
	        }
	}
	//----------------------------设备添加---------------------------------------
	function _addProducts(){
		var frameSrc = ctx+"/sales/contract/toAddOrUpdateProductsView"; 
		$('#dailogs1').on('show', function () {
			$('#dtitle').html("添加清单");
		     $('#dialogbody').load(frameSrc); 
			     $("#dsave").unbind('click');
			     $('#dsave').click(function () {
			    	 var _productType_=$("#_productType").val();
			    	 var _productTypeName_=$("#_sino_productTypeName").val();
			    	 var _productPartner_=$("#_partnerId").val();
			    	 var _productPartnerName_=$("#_sino_productPartnerName").val();
			    	 var _productNo_=$("#_productModelId").val();
			    	 var _productNoName_=$("#_sino_productModelName").val();

			    	 var _startTime_=$("#startTime").val();
			    	 var _endTime_=$("#endTime").val();
			    	 
			    	 
			    	 var _quantity_=$("#_products_add_count").val();
			    	 var _unitPrice_=$("#_product_add_unitPrice").val();
			    	 var show_unitPrice_= fmoney(_unitPrice_,2);
			    	 var _totalPrice_=$("#_product_add_totalPrice").val();
			    	 var show_totalPrice_= fmoney(_totalPrice_,2);
			    	 
			    	 var _productRemark_ =  $("#_product_add_remark").val();
			    	 
			    	 var _serial_num=$("#_sino_eoss_sales_contract_products_table tr").length;
			    	 
			    	 if(_productType_ == ""||_productPartner_ == ""||_productNo_ == ""||_unitPrice_ == ""){
			    		 $('#_product_alertMsg').empty();
			 			 $('#_product_alertMsg').append('<div class="alert alert-error"><strong>错误：</strong>请把数据填写完整！<button type="button" class="close _cancleHandler" data-dismiss="alert">&times;</button></div>');
			 			 $(".alert").delay(2000).hide(0);
			 			 $("._cancleHandler").click(function() {
			 				$(".alert").hide();
			 			 });
			    		 return;
			    	 }
			    	 if(_endTime_ != ""){
			    		 var endDate = $("#serviceEndDate_input").val();
			    		 if(endDate == ""){
			    			 UicDialog.Error("请先填写合同服务结束!");
				    		 return;
			    		 }
			    		 if(!timeObj.compareEndTimeAndtartTime(_endTime_,$("#serviceEndDate_input").val())){
				    		 $('#_product_alertMsg').empty();
				 			 $('#_product_alertMsg').append('<div class="alert alert-error"><strong>错误：服务结束时间不能大于合同服务结束!</strong>！<button type="button" class="close _cancleHandler" data-dismiss="alert">&times;</button></div>');
				 			 $(".alert").delay(2000).hide(0);
				 			 $("._cancleHandler").click(function() {
				 				$(".alert").hide();
				 			 });
				    		 return;
				    	 } 
			    	 }
			    	 
			    	 var tr=$("<tr style='text-algin:center'id='tr_"+_serial_num+"'>" +
			    			 	"<td style='text-algin:center'>"+_serial_num+"<input type='hidden' name='relateDeliveryProductId' /><input type='hidden' name='relateContractProductId' value='0'/></td>" +
								"<td><span>"+_productTypeName_+"</span><input id='_product_Type_"+_serial_num+"' name='productTypes' type='hidden' value='"+_productType_+"'><input id='_product_Type_Name_"+_serial_num+"' name='productTypeNames' type='hidden' value='"+_productTypeName_+"'></td>" +
								"<td><span>"+_productPartnerName_+"</span><input id='_product_Partner_"+_serial_num+"' name='productPartners' type='hidden' value='"+_productPartner_+"'><input id='_product_Partner_Name_"+_serial_num+"' name='productPartnerNames' type='hidden' value='"+_productPartnerName_+"'></td>" +
								"<td  title='"+_productRemark_+"'><span>"+_productNoName_+"</span><input id='_product_No_"+_serial_num+"' name='productNos' type='hidden' value='"+_productNo_+"'><input id='_product_Name_"+_serial_num+"' name='productNames' type='hidden' value='"+_productNoName_+"'></td>" +
								"<td><span>"+_startTime_+"</span><input id='_product_startTime_"+_serial_num+"' name='serviceStartDates' type='hidden' value='"+_startTime_+"'></td>" +
								"<td><span>"+_endTime_+"</span><input id='_product_endTime_"+_serial_num+"' name='serviceEndDates' type='hidden' value='"+_endTime_+"'></td>" +
								"<td><span>"+_quantity_+"</span><input id='_product_quantity_"+_serial_num+"' name='quantitys' type='hidden' value='"+_quantity_+"'></td>" +
								"<td><span>￥"+show_unitPrice_+"</span><input id='_product_unitPrice_"+_serial_num+"' name='unitPrices' type='hidden' value='"+_unitPrice_+"'></td>" +
								"<td><span>￥"+show_totalPrice_+"</span><input id='_product_totalPrice_"+_serial_num+"' name='totalPrices' type='hidden' value='"+_totalPrice_+"'><input id='_product_remark_"+_serial_num+"' name='productRemarks' type='hidden' value='"+_productRemark_+"'></td>" +
								"<td style='text-algin:center'><a serial_num='"+_serial_num+"'id='_sino_eoss_sales_contract_update_product_"+_serial_num+"'  class='btn btn-primary _sino_eoss_sales_contract_update_product' style='width:40px;font-size:12px;padding:2px;'><i class='icon-pencil'></i>修改</a>&nbsp;&nbsp;&nbsp;<a serial_num='"+_serial_num+"' id='_sino_eoss_sales_contract_remove_product_"+_serial_num+"'  class='btn btn-danger _sino_eoss_sales_contract_remove_product' style='padding:2px;'><i class='icon-remove'></i>删除</a></td>" +
								"</tr>");  
					$("#_sino_eoss_sales_contract_products_table").append(tr);
					 //设备修改
					 $("._sino_eoss_sales_contract_update_product").unbind('click').click(function(){
						 _updateProducts($(this).attr("serial_num"));
					 });
					 //设备删除
					 $("._sino_eoss_sales_contract_remove_product").unbind('click').click(function(){
						 _removeProducts($(this).attr("serial_num"));
					 });
					 
					 var totalCount = 0;
					 $('#_sino_eoss_sales_contract_products_table tbody').find("tr").each(function(i,ele){
						 $(ele).find("td").each(function(j,elem){
							 if(j == 8){
								 //totalCount+=parseInt($(elem).find("input").first().val());
								 totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
							 }
						 });
					 });
					 $('#_product_total').html(totalCount);
					 
			     });
			
		 });
			    $('#dailogs1').on('hidden', function () {$('#dailogs1').unbind("show");});
				$('#dailogs1').modal({show:true});
				$('#dailogs1').off('show');
	}	

	function _exportProducts(){
		var serviceEndTime = $("#serviceEndDate_input").val();
		if(!$('#serviceStartDate_div').is(":hidden")){
			if(serviceEndTime == ""){
				UicDialog.Error("请先填写合同服务结束!");
	    		return;
			}
		}
		$("#_sino_eoss_sales_products_import_div").empty();
		var path = ctx;
		var buffer = new StringBuffer();
		buffer.append('<div id="_sino_eoss_sales_products_import_page" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="_sino_eoss_sales_products_import_page" aria-hidden="true">');
		buffer.append('<div class="modal-header">');
		buffer.append('<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button >');
		buffer.append('<h3 id="myModalLabel">请选择Excel</h3 >');
		buffer.append('</div >');
		buffer.append('<div class="modal-body" >');
		
		buffer.append('<form id="_sino_eoss_article_advice" method="post" enctype="multipart/form-data">');
		buffer.append('<table class="table table-hover">');
		buffer.append('<tr><td>选择Excel文件：<input type="file" id="_salary_import_input" name="attachment"></td></tr>');
		buffer.append('</table>');
		buffer.append('</form>');
		
		buffer.append("</div>");	
		buffer.append('<div class="modal-footer" >');
		buffer.append('<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button >');
		buffer.append('<button class="btn btn-primary"  id="btnConfirmq" data-dismiss="modal">确定</button >');
		buffer.append('</div >');
		buffer.append('</div>');
		
		$("#_sino_eoss_sales_products_import_div").append(buffer.toString());
		var datas=$("#_sino_eoss_article_advice").serialize();
		var contractType = $("#_eoss_sales_contractType").formSelect("getValue")[0];
		if(contractType == 3000){
			var url = ctx+"/sales/contract/uploadProducts?state=1";
			$('#btnConfirmq').unbind('click').click(function () {
				
				$("#_sino_eoss_article_advice").ajaxSubmit({
					url:url,
					dataType:'json',
				    success: function(data) {
				    	var result = data;
	                	var lineNo = result.lineNo;
	                	if(lineNo != null){
	                		var type = result.productName;
	                		var str = "";
	                		if(type != null){
	                			str = "产品型号";
	                		} else {
	                			type = result.partnerName;
	                			if(partnerName != null){
	                				str = "产品厂商";
	                			}
	                		}
	                		UicDialog.Error("第"+lineNo+"行,"+str+type+"有误");
	                		return;
	                	} else {
	                		var jsonData = result.rows;
	                		
	                		if(!$('#serviceStartDate_div').is(":hidden")){
	                			for(var i = 0;i < jsonData.length;i++){
	                				var _endTime_=jsonData[i].serviceEndDate;
	                				if(!timeObj.compareEndTimeAndtartTime(_endTime_,serviceEndTime)){
	                					UicDialog.Error("第"+(i+1)+"行,服务结束时间不能大于合同服务结束");
	                					return;
	                				}
	                			}
	                		}
	                		
	                    	if(jsonData.length > 0){
	                    		var totalCount = $('#_product_total').html();
	                    		for(var i in jsonData){
	                    			if(jsonData[i].partnerName != ""||jsonData[i].partnerName != null){
	    	   	           		    	 var _productPartner_=jsonData[i].partnerId;
	    	   	           		    	 var _productPartnerName_=jsonData[i].partnerName;
	    	   	           		    	 var _productNo_=jsonData[i].productId;
	    	   	           		    	 var _productNoName_=jsonData[i].productName;

	    	   	           		    	 var _serialNumber_=jsonData[i].serialNumber;
	    	   	           		    	 var _servicePeriod_=jsonData[i].servicePeriod;
	    	   	           		    	 var _serviceStartDate_=jsonData[i].serviceStartDate;
	    	   	           		    	 var _serviceEndDate_=jsonData[i].serviceEndDate;
	    	   	           		    	 
	    	   	           		    	 var _quantity_=jsonData[i].quantity;
	    	   	           		    	 var _unitPrice_=jsonData[i].unitPrice;
	    	   	           		    	 var show_unitPrice_ = fmoney(_unitPrice_,2);
	    	   	           		    	 var _totalPrice_=jsonData[i].totalPrice;
	    	   	           		    	 
	    	   	           		    	 var _productRemark_=jsonData[i].productRemark;
	  	   	           		         
	    	   	           		    	 if(_productRemark_ == null){
	    	   	           		    		 _productRemark_ = "";
	    	   	           		    	 }
	    	   	           		    	 
	    	   	           		         var show_totalPrice_ = fmoney(_totalPrice_,2);

	    	   	           		         var _equipmentSplace_=jsonData[i].equipmentSplace;
	    	   	           		          
	    	   	           		    	 var _serial_num=$("#_sino_eoss_sales_contract_maproducts_table tr").length;
	    	   	           		    	 var tr=$("<tr style='text-algin:center'id='tr_"+_serial_num+"'>" +
	    	              							"<td style='text-algin:center'>"+_serial_num+"</td>" +
	    	              							"<td><span>"+_productPartnerName_+"</span><input id='_product_Partner_"+_serial_num+"' name='productPartners' type='hidden' value='"+_productPartner_+"'><input id='_product_Partner_Name_"+_serial_num+"' name='productPartnerNames' type='hidden' value='"+_productPartnerName_+"'></td>" +
	    	              							"<td title='"+_productRemark_+"'><span>"+_productNoName_+"</span><input id='_product_No_"+_serial_num+"' name='productNos' type='hidden' value='"+_productNo_+"'><input id='_product_Name_"+_serial_num+"' name='productNames' type='hidden' value='"+_productNoName_+"'></td>" +
	    	              							"<td><span>"+_serialNumber_+"</span><input id='_product_serialNumber_"+_serial_num+"' name='serialNumber' type='hidden' value='"+_serialNumber_+"'></td>" +
	    	              							"<td><span>"+_servicePeriod_+"</span><input id='_product_servicePeriod_"+_serial_num+"' name='servicePeriod' type='hidden' value='"+_servicePeriod_+"'></td>" +
	    	              							"<td><span>"+_serviceStartDate_+"</span><input id='_product_serviceStartDate_"+_serial_num+"' name='serviceStartDates' type='hidden' value='"+_serviceStartDate_+"'></td>" +
	    	              							"<td><span>"+_serviceEndDate_+"</span><input id='_product_serviceEndDate_"+_serial_num+"' name='serviceEndDates' type='hidden' value='"+_serviceEndDate_+"'></td>" +
	    	              							"<td><span>￥"+show_unitPrice_+"</span><input id='_product_unitPrice_"+_serial_num+"' name='unitPrices' type='hidden' value='"+_unitPrice_+"'></td>" +
	    	              							"<td><span>"+_quantity_+"</span><input id='_product_quantity_"+_serial_num+"' name='quantitys' type='hidden' value='"+_quantity_+"'></td>" +
	    	              							"<td><span>￥"+show_totalPrice_+"</span><input id='_product_totalPrice_"+_serial_num+"' name='totalPrices' type='hidden' value='"+_totalPrice_+"'><input id='_product_remark_"+_serial_num+"' name='productRemarks' type='hidden' value='"+_productRemark_+"'></td>" +
	    	              							"<td><span>"+_equipmentSplace_+"</span><input id='_product_equipmentSplace_"+_serial_num+"' name='equipmentSplace' type='hidden' value='"+_equipmentSplace_+"'></td>" +
	    	              							"</tr>");  
	    	   	           				$("#_sino_eoss_sales_contract_maproducts_table").append(tr);
//	    	   	           				 //设备修改
//	    	   	           				 $("._sino_eoss_sales_contract_update_product").unbind('click').click(function(){
//	    	   	           					 _updateProducts($(this).attr("serial_num"));
//	    	   	           				 });
//	    	   	           				 //设备删除
//	    	   	           				 $("._sino_eoss_sales_contract_remove_product").unbind('click').click(function(){
//	    	   	           					 _removeProducts($(this).attr("serial_num"));
//	    	   	           				 });
//	    	   	           				 totalCount+=parseInt(_totalPrice_);
	    	   	           				 totalCount=metaUtil.accAdd(totalCount,_totalPrice_);
	    	   	           				 $('#_product_total').html(totalCount);
	    	                   		}
	                			}
	                    	}
	                	}
				    }
				});
			});
		} else {
			var url = ctx+"/sales/contract/uploadProducts";
			$('#btnConfirmq').unbind('click').click(function () {
				$("#_sino_eoss_article_advice").ajaxSubmit({
					url:url,
					dataType:'json',
				    success: function(data) {
				    	var result = data;
	                	var lineNo = result.lineNo;
	                	if(lineNo != null){
	                		var type = result.productName;
	                		var str = "";
	                		if(type != null){
	                			str = "产品型号";
	                		} else {
	                			type = result.typeName;
	                			if(type != null){
	                				str = "产品类型";
	                			} else {
	                				type = result.partnerName;
	                				str = "产品厂商";
	                			}
	                		}
	                		UicDialog.Error("第"+lineNo+"行,"+str+type+"有误");
	                		return;
	                	} else {
	                		var jsonData = result.rows;
	                		
	                		if(!$('#serviceStartDate_div').is(":hidden")){
	                			for(var i = 0;i < jsonData.length;i++){
	                				var _endTime_=jsonData[i].endTime;
	                				if(!timeObj.compareEndTimeAndtartTime(_endTime_,serviceEndTime)){
	                					UicDialog.Error("第"+(i+1)+"行,服务结束时间不能大于合同服务结束");
	                					return;
	                				}
	                			}
	                		}
	                		
	                    	if(jsonData.length > 0){
	                    		var totalCount = $('#_product_total').html();
	                    		for(var i = 0;i < jsonData.length;i++){
	                    			if(jsonData[i].productTypeName != ""||jsonData[i].productTypeName != null){
	                    				var _productType_=jsonData[i].typeId;
	    	   	           		    	 var _productTypeName_=jsonData[i].typeName;
	    	   	           		    	 var _productPartner_=jsonData[i].partnerId;
	    	   	           		    	 var _productPartnerName_=jsonData[i].partnerName;
	    	   	           		    	 var _productNo_=jsonData[i].productId;
	    	   	           		    	 var _productNoName_=jsonData[i].productName;
	    	   	           		    	 var _startTime_=jsonData[i].startTime;
	    	   	           		    	 var _endTime_=jsonData[i].endTime;
	    	   	           		    	 var _quantity_=jsonData[i].quantity;
	    	   	           		    	 var _unitPrice_=jsonData[i].unitPrice;
	    	   	           		    	 var show_unitPrice_ = fmoney(_unitPrice_,2);
	    	   	           		    	 var _totalPrice_=jsonData[i].totalPrice;
	    	   	           		          var show_totalPrice_ = fmoney(_totalPrice_,2);
	    	   	           		    	 var _serial_num=$("#_sino_eoss_sales_contract_products_table tr").length;
	    	   	           		    	 var tr=$("<tr style='text-algin:center'id='tr_"+_serial_num+"'>" +
	    	              							"<td style='text-algin:center'>"+_serial_num+"<input type='hidden' name='relateDeliveryProductId' /><input type='hidden' name='relateContractProductId' value='0'/></td>" +
	    	              							"<td><span>"+_productTypeName_+"</span><input id='_product_Type_"+_serial_num+"' name='productTypes' type='hidden' value='"+_productType_+"'><input id='_product_Type_Name_"+_serial_num+"' name='productTypeNames' type='hidden' value='"+_productTypeName_+"'></td>" +
	    	              							"<td><span>"+_productPartnerName_+"</span><input id='_product_Partner_"+_serial_num+"' name='productPartners' type='hidden' value='"+_productPartner_+"'><input id='_product_Partner_Name_"+_serial_num+"' name='productPartnerNames' type='hidden' value='"+_productPartnerName_+"'></td>" +
	    	              							"<td><span>"+_productNoName_+"</span><input id='_product_No_"+_serial_num+"' name='productNos' type='hidden' value='"+_productNo_+"'><input id='_product_Name_"+_serial_num+"' name='productNames' type='hidden' value='"+_productNoName_+"'></td>" +
	    	              							"<td><span>"+_startTime_+"</span><input id='_product_startTime_"+_serial_num+"' name='serviceStartDates' type='hidden' value='"+_quantity_+"'></td>" +
	    	              							"<td><span>"+_endTime_+"</span><input id='_product_endTime_"+_serial_num+"' name='serviceEndDates' type='hidden' value='"+_quantity_+"'></td>" +
	    	              							"<td><span>"+_quantity_+"</span><input id='_product_quantity_"+_serial_num+"' name='quantitys' type='hidden' value='"+_quantity_+"'></td>" +
	    	              							"<td><span>￥"+show_unitPrice_+"</span><input id='_product_unitPrice_"+_serial_num+"' name='unitPrices' type='hidden' value='"+_unitPrice_+"'></td>" +
	    	              							"<td><span>￥"+show_totalPrice_+"</span><input id='_product_totalPrice_"+_serial_num+"' name='totalPrices' type='hidden' value='"+_totalPrice_+"'></td>" +
	    	              							"<td style='text-algin:center'><a serial_num='"+_serial_num+"'id='_sino_eoss_sales_contract_update_product_"+_serial_num+"'  class='btn btn-primary _sino_eoss_sales_contract_update_product' style='width:40px;font-size:12px;padding:2px;'><i class='icon-pencil'></i>修改</a>&nbsp;&nbsp;&nbsp;<a serial_num='"+_serial_num+"' id='_sino_eoss_sales_contract_remove_product_"+_serial_num+"'  class='btn btn-danger _sino_eoss_sales_contract_remove_product' style='padding:2px;'><i class='icon-remove'></i>删除</a></td>" +
	    	              							"</tr>");  
	    	   	           				$("#_sino_eoss_sales_contract_products_table").append(tr);
	    	   	           				 //设备修改
	    	   	           				 $("._sino_eoss_sales_contract_update_product").unbind('click').click(function(){
	    	   	           					 _updateProducts($(this).attr("serial_num"));
	    	   	           				 });
	    	   	           				 //设备删除
	    	   	           				 $("._sino_eoss_sales_contract_remove_product").unbind('click').click(function(){
	    	   	           					 _removeProducts($(this).attr("serial_num"));
	    	   	           				 });
//	    	   	           				 totalCount+=parseInt(_totalPrice_);
	    	   	           				 totalCount=metaUtil.accAdd(totalCount,_totalPrice_);
	    	   	           				 $('#_product_total').html(totalCount);
	    	                   		}
	                			}
	                    	}
	                	}
					}
				});
				
			});
		}
	}
	
	function _relateSalesContracts(){
		$("#_sino_eoss_sales_products_import_div").empty();
		var buffer = new StringBuffer();
		buffer.append('<div id="_sino_eoss_sales_products_import_page" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="_sino_eoss_sales_products_import_page" aria-hidden="true">');
		$("#_sino_eoss_sales_products_import_div").append(buffer.toString());
		var url = ctx+"/sales/contract/getSalesContractListPage";
		$("#_sino_eoss_sales_products_import_page").load(url,function(){
			$("#_sales_contracts_relate").bind('click').click(function(){
				
				var productMap = new Map();
				
				//判断此种类型是否已被关联
				$("#_sino_eoss_sales_contract_products_table tbody").find("tr").each(function(m,ele){
	    			 $(ele).find("td").each(function(j,elem){
	    				 var ke = "";
	    				 var val = "";
	    				 if(j == 0){
	    					 ke = $(elem).find("input[name='relateDeliveryProductId']").first().val();
	    				 }
	    				 if(j == 3){
	    					 val = $(elem).find("span").first().val();
	    				 }
	    				 productMap.put(ke,val);
	    			 });
		    	 });
				
				var url = ctx+"/sales/contract/getRelateDeliveryProductList?salesId="+$('input[name="radio_product"]:checked').val();
				$.ajax({
					url:url,
					async : false,
					dataType : "json", 
					success : function(result){
						var strResult = "";
						for(var i = 0;i < result.length;i++){
							 var _quantity_=result[i].quantity - result[i].surplusNum;
							 if(_quantity_ != 0){
								 var _productType_=result[i].productType;
						    	 var _productTypeName_=result[i].productTypeName;
						    	 var _productPartner_=result[i].productPartner;
						    	 var _productPartnerName_=result[i].productPartnerName;
						    	 var _productNo_=result[i].productNo;
						    	 var _productNoName_=result[i].productName;
						    	 
						    	 var _startTime_=result[i].serviceStartDate;
						    	 var _endTime_=result[i].serviceEndDate;
						    	 
						    	 if(_startTime_ == ""||_startTime_ == null){
						    		 _startTime_ = "";
						    	 }
						    	 if(_endTime_ == ""||_endTime_ == null){
						    		 _endTime_ = "";
						    	 }
						    	 
						    	 var _productRemark_ = result[i].remark;
						    	 if(_productRemark_ == ""||_productRemark_ == null){
						    		 _productRemark_ = "";
						    	 }
						    	 
						    	 var _unitPrice_=result[i].unitPrice;
						    	 var _totalPrice_=result[i].totalPrice;
						    	 var _serial_num=$("#_sino_eoss_sales_contract_products_table tr").length;
						    	 
						    	 if(productMap.containsKey(result[i].id)){
						    		 strResult+=_productNoName_+"  ";
						    	 } else {
						    		 var tr=$("<tr style='text-algin:center'id='tr_"+_serial_num+"'>" +
							    			 	"<td style='text-algin:center'>"+_serial_num+"<input type='hidden' name='relateDeliveryProductId' value='"+result[i].id+"'/><input type='hidden' name='relateContractProductId' value='0'/></td>" +
												"<td><span>"+_productTypeName_+"</span><input id='_product_Type_"+_serial_num+"' name='productTypes' type='hidden' value='"+_productType_+"'><input id='_product_Type_Name_"+_serial_num+"' name='productTypeNames' type='hidden' value='"+_productTypeName_+"'></td>" +
												"<td><span>"+_productPartnerName_+"</span><input id='_product_Partner_"+_serial_num+"' name='productPartners' type='hidden' value='"+_productPartner_+"'><input id='_product_Partner_Name_"+_serial_num+"' name='productPartnerNames' type='hidden' value='"+_productPartnerName_+"'></td>" +
												"<td><span>"+_productNoName_+"</span><input id='_product_No_"+_serial_num+"' name='productNos' type='hidden' value='"+_productNo_+"'><input id='_product_Name_"+_serial_num+"' name='productNames' type='hidden' value='"+_productNoName_+"'></td>" +
												"<td><span>"+_startTime_+"</span><input id='_product_startTime_"+_serial_num+"' name='serviceStartDates' type='hidden' value='"+_startTime_+"'></td>" +
												'<td><div class="input-append date"><input data-format="yyyy-MM-dd" style="width:80px;" type="text" name="serviceEndDates" value="'+_endTime_+'"></input><span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span></div>' +
												"<td><input id='_product_quantity_"+_serial_num+"' name='quantitys' type='text' value='"+_quantity_+"' style='width:40px;' inputCount='"+_quantity_+"' /></td>" +
												"<td><input id='_product_unitPrice_"+_serial_num+"' name='unitPrices' type='text' value='"+_unitPrice_+"' style='width:40px;' /></td>" +
												"<td><span>"+_totalPrice_+"</span><input id='_product_totalPrice_"+_serial_num+"' name='totalPrices' type='hidden' value='"+_totalPrice_+"'><input id='_product_remark_"+_serial_num+"' name='productRemarks' type='hidden' value='"+_productRemark_+"'></td>" +
												"<td style='text-algin:center'>&nbsp;&nbsp;&nbsp;<a serial_num='"+_serial_num+"' id='_sino_eoss_sales_contract_remove_product_"+_serial_num+"'  class='btn btn-danger _sino_eoss_sales_contract_remove_product'><i class='icon-remove'></i>删除</a></td>" +
												"</tr>");  
									$("#_sino_eoss_sales_contract_products_table").append(tr);
									
									var totalCount = 0;
									 $('#_sino_eoss_sales_contract_products_table tbody').find("tr").each(function(i,ele){
										 $(ele).find("td").each(function(j,elem){
											 if(j == 8){
												 //totalCount+=parseInt($(elem).find("input").first().val());
												 totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
											 }
										 });
									 });
									 $('#_product_total').html(totalCount);
									
									//设备删除
									 $("._sino_eoss_sales_contract_remove_product").unbind('click').click(function(){
										 _removeProducts($(this).attr("serial_num"));
									 });
									 //单价计算总价
									 $("#_product_unitPrice_"+_serial_num).blur(function(){
										 var id = $(this).attr("id");
										 var num = id.split("_")[3];
										 var qId = "_product_quantity_"+num;
										 var totalId = "_product_totalPrice_"+num;
										 var totalPrice = $(this).val()*$("#"+qId).val();
										 $("#"+totalId).prev("span").html(totalPrice);
										 $("#"+totalId).val(totalPrice);
										 
										 var totalCount = 0;
										 $('#_sino_eoss_sales_contract_products_table tbody').find("tr").each(function(i,ele){
											 $(ele).find("td").each(function(j,elem){
												 if(j == 8){
//													 totalCount+=parseInt($(elem).find("input").first().val());
													 totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
												 }
											 });
										 });
										 $('#_product_total').html(totalCount);
							         });
									 //数量验证
									 $("#_product_quantity_"+_serial_num).blur(function(){
										 var inputCount = $(this).attr("inputCount");
										 var value = $(this).val();
										 if(parseInt(inputCount) < parseInt(value)){
											 UicDialog.Error("此型号还剩"+inputCount+"台");
											 $("#_is_submit_product").val(0);
											 return;
										 } else {
											 $("#_is_submit_product").val(1);
										 }
										 var id = $(this).attr("id");
										 var num = id.split("_")[3];
										 var qId = "_product_unitPrice_"+num;
										 var totalId = "_product_totalPrice_"+num;
										 var totalPrice = $(this).val()*$("#"+qId).val();
										 $("#"+totalId).prev("span").html(totalPrice);
										 $("#"+totalId).val(totalPrice);
										 
										 var totalCount = 0;
										 $('#_sino_eoss_sales_contract_products_table tbody').find("tr").each(function(i,ele){
											 $(ele).find("td").each(function(j,elem){
												 if(j == 8){
//													 totalCount+=parseInt($(elem).find("input").first().val());
													 totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
												 }
											 });
										 });
										 $('#_product_total').html(totalCount);
									 });
						    	 }
					    	 }
						}
						$('.date').datetimepicker({
	        		    	pickTime: false
	        		    }).on('changeDate', function(ev){
	            			var selectdate = ev.date;
	            			 if(!$('#serviceStartDate_div').is(":hidden")){
	            				 if(!timeObj.compareEndTimeAndtartTime(timeObj.sFormatterDateTime(selectdate),serviceEndTime)){
	            					 $("#_is_submit_product").val(0);
	            				 } else {
	            					 $("#_is_submit_product").val(1)
	            				 }
	            			 }
//	            			$('#selectDate').datetimepicker('hide');
	    	        	});
						if(strResult != ""){
							UicDialog.Error(strResult+"已被关联,不能再次关联,请删除后再关联!",function(){
			    			 });
						}
				    }  
				});
			});
		});
	}
	//----------------------------设备修改---------------------------------------
	function _updateProducts(serialNum){
		var oldOptions={};
		oldOptions.productType=$("#_product_Type_"+serialNum).val();
		oldOptions.productTypeName=$("#_product_Type_Name_"+serialNum).val();
		oldOptions.productPartner=$("#_product_Partner_"+serialNum).val();
		oldOptions.productPartnerName=$("#_product_Partner_Name_"+serialNum).val();
		oldOptions.productNo=$("#_product_No_"+serialNum).val();
		oldOptions.productName=$("#_product_Name_"+serialNum).val();
		oldOptions.startTime=$("#_product_startTime_"+serialNum).val();
		oldOptions.endTime=$("#_product_endTime_"+serialNum).val();
		oldOptions.quantity=$("#_product_quantity_"+serialNum).val();
		oldOptions.unitPrice=$("#_product_unitPrice_"+serialNum).val();
		oldOptions.totalPrice=$("#_product_totalPrice_"+serialNum).val();
		
		oldOptions.remark=$("#_product_remark_"+serialNum).val();
		
		var frameSrc = ctx+"/sales/contract/toAddOrUpdateProductsView?startTime="+oldOptions.startTime+"&endTime="+oldOptions.endTime;
		var data=oldOptions;
		$('#dailogs1').on('show', function () {
			$('#dtitle').html("修改清单");
		     $('#dialogbody').load(frameSrc,data); 
			     $("#dsave").unbind('click');
			     $('#dsave').click(function () {
			    	 var newOptions={};
			    	 newOptions.productType=$("#_productType").val();
			    	 newOptions.productTypeName=$("#_sino_productTypeName").val();
			    	 newOptions.productPartner=$("#_partnerId").val();
			    	 newOptions.productPartnerName=$("#_sino_productPartnerName").val();
			    	 newOptions.productNo=$("#_productModelId").val();
			    	 newOptions.productNoName=$("#_sino_productModelName").val();
			    	 newOptions.startTime=$("#startTime").val();
			    	 newOptions.endTime=$("#endTime").val();
			    	 newOptions.quantity=$("#_products_add_count").val();
			    	 newOptions.unitPrice=$("#_product_add_unitPrice").val();
			    	 newOptions.totalPrice=$("#_product_add_totalPrice").val();
			    	 var  show_unitPrice=fmoney(newOptions.unitPrice,2);
			    	 var show_totalPrice=fmoney(newOptions.totalPrice,2);
			    	 newOptions.serial_num=serialNum;
			    	 
			    	 if(newOptions.endTime != ""){
			    		 var endDate = $("#serviceEndDate_input").val();
			    		 if(endDate == ""){
			    			 UicDialog.Error("请先填写合同服务结束!");
				    		 return;
			    		 }
			    		 if(!timeObj.compareEndTimeAndtartTime(newOptions.endTime,$("#serviceEndDate_input").val())){
				    		 $('#_product_alertMsg').empty();
				 			 $('#_product_alertMsg').append('<div class="alert alert-error"><strong>错误：服务结束时间不能大于合同服务结束!</strong>！<button type="button" class="close _cancleHandler" data-dismiss="alert">&times;</button></div>');
				 			 $(".alert").delay(2000).hide(0);
				 			 $("._cancleHandler").click(function() {
				 				$(".alert").hide();
				 			 });
				    		 return;
				    	 }
			    	 }
			    	 
			    	 var tr=$("<tr style='text-algin:center' id='tr_"+newOptions.serial_num+"'>" +
								"<td>"+newOptions.serial_num+"<input type='hidden' name='relateDeliveryProductId' /><input type='hidden' name='relateContractProductId' value='0'/></td>" +
								"<td><span>"+newOptions.productTypeName+"</span><input id='_product_Type_"+newOptions.serial_num+"' name='productTypes' type='hidden' value='"+newOptions.productType+"'><input id='_product_Type_Name_"+newOptions.serial_num+"' name='productTypeNames' type='hidden' value='"+newOptions.productTypeName+"'></td>" +
								"<td><span>"+newOptions.productPartnerName+"</span><input id='_product_Partner_"+newOptions.serial_num+"' name='productPartners' type='hidden' value='"+newOptions.productPartner+"'><input id='_product_Partner_Name_"+newOptions.serial_num+"' name='productPartnerNames' type='hidden' value='"+newOptions.productPartnerName+"'></td>" +
								"<td title='"+newOptions.productRemark+"'><span>"+newOptions.productNoName+"</span><input id='_product_No_"+newOptions.serial_num+"' name='productNos' type='hidden' value='"+newOptions.productNo+"'><input id='_product_Name_"+newOptions.serial_num+"' name='productNames' type='hidden' value='"+newOptions.productNoName+"'></td>" +
								"<td><span>"+newOptions.startTime+"</span><input id='_product_startTime_"+newOptions.serial_num+"' name='serviceStartDates' type='hidden' value='"+newOptions.startTime+"'></td>" +
								"<td><span>"+newOptions.endTime+"</span><input id='_product_endTime_"+newOptions.serial_num+"' name='serviceEndDates' type='hidden' value='"+newOptions.endTime+"'></td>" +
								"<td><span>"+newOptions.quantity+"</span><input id='_product_quantity_"+newOptions.serial_num+"' name='quantitys' type='hidden' value='"+newOptions.quantity+"'></td>" +
								"<td><span>￥"+show_unitPrice+"</span><input id='_product_unitPrice_"+newOptions.serial_num+"' name='unitPrices' type='hidden' value='"+newOptions.unitPrice+"'></td>" +
								"<td><span>￥"+show_totalPrice+"</span><input id='_product_totalPrice_"+newOptions.serial_num+"' name='totalPrices' type='hidden' value='"+newOptions.totalPrice+"'><input id='_product_remark_"+newOptions.serial_num+"' name='productRemarks' type='hidden' value='"+newOptions.productRemark+"'></td>" +
								"<td style='text-algin:center'><a serial_num='"+newOptions.serial_num+"' id='_sino_eoss_sales_contract_update_product_"+newOptions.serial_num+"'  class='btn btn-primary _sino_eoss_sales_contract_update_product' style='width:40px;font-size:12px;padding:2px;'><i class='icon-pencil'></i>修改</a>&nbsp;&nbsp;&nbsp;<a serial_num='"+newOptions.serial_num+"' id='_sino_eoss_sales_contract_remove_product_"+newOptions.serial_num+"'  class='btn btn-danger _sino_eoss_sales_contract_remove_product' style='padding:2px;'><i class='icon-remove'></i>删除</a></td>" +
								"</tr>");
			    	 var downer=parseInt(serialNum)+1;
			    	 if($("#_sino_eoss_sales_contract_products_table tr").length > downer){
			    		 $("#tr_"+downer).before(tr);
			    	 }else {
			    		 $("#_sino_eoss_sales_contract_products_table").append(tr);
			    	 }
			    	 $("#tr_"+serialNum).remove();
					 //设备修改
					 $("._sino_eoss_sales_contract_update_product").unbind('click').click(function(){
						 _updateProducts($(this).attr("serial_num"));
					 });
					 //设备删除
					 $("._sino_eoss_sales_contract_remove_product").unbind('click').click(function(){
						 _removeProducts($(this).attr("serial_num"));
					 });
					 var totalCount = 0;
					 $('#_sino_eoss_sales_contract_products_table tbody').find("tr").each(function(i,ele){
						 $(ele).find("td").each(function(j,elem){
							 if(j == 8){
//								 totalCount+=parseInt($(elem).find("input").first().val());
								 totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
							 }
						 });
					 });
					 $('#_product_total').html(totalCount);
					
			     });
			
		 });
			    $('#dailogs1').on('hidden', function () {$('#dailogs1').unbind("show");});
				$('#dailogs1').modal({show:true});
				$('#dailogs1').off('show');
	}
	//----------------------------设备修改---------------------------------------
	function _removeProducts(serialNum){
		UicDialog.Confirm("确认删除该条设备吗？",function () {
			
			var totalCount = $('#_product_total').html();
			$("#tr_"+serialNum).find("td").each(function(j,elem){
				 if(j == 6){
					 totalCount = totalCount-$(elem).find("input").first().val();
				 }
			 });
			if(isNaN(totalCount)){
				totalCount = 0;
			}
			 $('#_product_total').html(totalCount);
			
			$("#tr_"+serialNum).remove();
		});
	}
	function _removeProductsRelate(serialNum,obj){
		UicDialog.Confirm("确认删除该条设备吗？",function () {
			
			var totalCount = $('#_product_total').html();
			$("#tr_"+serialNum).find("td").each(function(j,elem){
				 if(j == 8){
					 totalCount = totalCount-$(elem).find("input").first().val();
				 }
			 });
			if(isNaN(totalCount)){
				totalCount = 0;
			}
			 $('#_product_total').html(totalCount);
			
			$("#tr_"+serialNum).remove();
			var id1 = $(obj).attr("id1");
			var id2 = $(obj).attr("id2");
			if(id1 != null&&id1 != ""){
				$.ajax({
					url:ctx+"/sales/contract/deleteSalesProduct",
					data:{"id1":id1,"id2":id2},
					dataType : "json", 
					success : function(result){  
				    }  
				});
				
			}
		});
	}
	exports.init = function() {
		changeSalesContract.methods.initDocument();
	}
	function fmoney(s, n) { 
		n = n > 0 && n <= 20 ? n : 2; 
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
		t = ""; 
		for (i = 0; i < l.length; i++) { 
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		} 
		return t.split("").reverse().join("") + "." + r; 
		} 
});