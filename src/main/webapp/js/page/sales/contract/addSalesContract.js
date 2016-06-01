define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("select2");
	require("select2_locale_zh-CN");
	require("formSelect");
	require("formSubmit");
	var StringBuffer = require("stringBuffer");
	require("easyui");
	require("jquery.form");
	
	require("js/plugins/meta/js/metaDropdowmSelect");
	var timeObj = require("js/plugins/meta/js/timeObjectUtil");
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
	String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
	}
	var addSalesContract={
			config: {
            	namespace:ctx+'/sales/contract'
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
	        		//------------------加载DataTable-------------End------------
	        		/*
	        		 * 绑定触发事件
	        		 */
	        		 $("#_sino_eoss_sales_contract_back").bind('click',function(){
	        			 addSalesContract.methods._back();
	                 });
	        		 //保存为草稿
	        		 $("#_sino_eoss_sales_contract_add").bind('click',function(){
	        			 $("#isSubmit").val('CG');
	        			 addSalesContract.methods._add();
	        	       });
	        		//直接提交审批
	        		 $("#_sino_eoss_sales_contract_submit").bind('click',function(){
	        			 $("#isSubmit").val('SH');
	        			 addSalesContract.methods._add();
	        	       });
	        		 //设备添加
	        		 $("#_sino_eoss_sales_products_add").bind('click').click(function(){
	        			 _addProducts();
	        		 });
	        		 //选择客户
	        		 $("#_sino_eoss_sales_customerInfo_select").bind('click').click(function(){
	        			 selectCustomerInfo();
	        		 });
	        		 //加载合同类型下拉框
	        		 addSalesContract.methods.getContractType();
	        		 //加载结算币种下拉框
	        		 addSalesContract.methods.getAccountCurrency();
	        		 //加载发票类型下拉框
	        		 addSalesContract.methods.getInvoiceType();
	        		 //加载收款方式下拉框
	        		 addSalesContract.methods.getReceiveWay();
	        		 //加载行业下拉框
	        		 addSalesContract.methods.getIndustryName();
	        		 //加载附件组件
	        		 addSalesContract.methods.showFileField();
	        		 addSalesContract.methods.customerDropSelect("");
	        		 $('.date').datetimepicker({
	        		    	pickTime: false
	        		    });
	        		 //导入设备
	        		 $("#_sino_eoss_sales_products_import").bind('click').click(function(){
	        			 _exportProducts();
	        		 });
	        		 //关联备货合同
	        		 $("#_sino_eoss_sales_products_relate").bind('click').click(function(){
	        			 _relateSalesContracts();
	        		 });
	        		 
	        		 //备品备件选择
	        		 $("input[name='needother']").unbind('click').click(function () {
	        			 var val = $(this).val();
	        			 var typeId = $('#_eoss_sales_contractTypeId').val();
	        			 if(val == '1'){
	        				 var _serial_num=$("#_sino_eoss_sales_contract_maproducts_table tr").length;
	        				 if(typeId == 3000){
	        					 var tr=$("<tr style='text-algin:center'id='tr_needother'>" +
	              							"<td style='text-algin:center'>"+_serial_num+"</td>" +
	              							"<td><span>北京神州新桥科技有限公司</span><input id='_product_Partner_"+_serial_num+"' name='productPartners' type='hidden' value='8'><input id='_product_Partner_Name_"+_serial_num+"' name='productPartnerNames' type='hidden' value='北京神州新桥科技有限公司'></td>" +
	              							"<td><span>备品备件保修服务</span><input id='_product_No_"+_serial_num+"' name='productNos' type='hidden' value='1899'><input id='_product_Name_"+_serial_num+"' name='productNames' type='hidden' value='备品备件保修服务'></td>" +
	              							"<td><span></span><input id='_product_serialNumber_"+_serial_num+"' name='serialNumber' type='hidden' value=''></td>" +
	              							"<td><input id='_product_servicePeriod_"+_serial_num+"' name='servicePeriod' type='hidden' value='0'></td>" +
	              							"<td><input id='_product_serviceStartDate_"+_serial_num+"' name='serviceStartDates' type='hidden' value=''></td>" +
	              							"<td><input id='_product_serviceEndDate_"+_serial_num+"' name='serviceEndDates' type='hidden' value=''></td>" +
	              							"<td>0<input id='_product_unitPrice_"+_serial_num+"' name='unitPrices' type='hidden' value='0'></td>" +
	              							"<td><input id='_product_quantity_"+_serial_num+"' style='width:30px;' name='quantitys' type='text' value='1'></td>" +
	              							"<td><span>0</span><input id='_product_totalPrice_"+_serial_num+"' name='totalPrices' type='hidden' value='0'></td>" +
	              							"<td><span></span><input id='_product_equipmentSplace_"+_serial_num+"' name='equipmentSplace' type='hidden' value=''></td>" +
	              							"</tr>");  
	        					 $("#_sino_eoss_sales_contract_maproducts_table").append(tr);
	        				 } else {
	        					 var tr=$("<tr style='text-algin:center'id='tr_needother'>" +
	              							"<td style='text-algin:center'>"+_serial_num+"<input type='hidden' name='relateDeliveryProductId' /><input type='hidden' name='relateContractProductId' value='0'/></td>" +
	              							"<td><span>服务</span><input id='_product_Type_"+_serial_num+"' name='productTypes' type='hidden' value='3'><input id='_product_Type_Name_"+_serial_num+"' name='productTypeNames' type='hidden' value='服务'></td>" +
	              							"<td><span>北京神州新桥科技有限公司</span><input id='_product_Partner_"+_serial_num+"' name='productPartners' type='hidden' value='8'><input id='_product_Partner_Name_"+_serial_num+"' name='productPartnerNames' type='hidden' value='北京神州新桥科技有限公司'></td>" +
	              							"<td title=''><span>备品备件保修服务</span><input id='_product_No_"+_serial_num+"' name='productNos' type='hidden' value='1899'><input id='_product_Name_"+_serial_num+"' name='productNames' type='hidden' value='备品备件保修服务'></td>" +
	              							"<td><span></span><input id='_product_startTime_"+_serial_num+"' name='serviceStartDates' type='hidden' value=''></td>" +
	              							"<td><span></span><input id='_product_endTime_"+_serial_num+"' name='serviceEndDates' type='hidden' value=''></td>" +
	              							"<td><input id='_product_quantity_"+_serial_num+"' name='quantitys' type='text' style='width:30px;' value='1'></td>" +
	              							"<td><span>0</span><input id='_product_unitPrice_"+_serial_num+"' name='unitPrices' type='hidden' value='0'></td>" +
	              							"<td><span>0</span><input id='_product_totalPrice_"+_serial_num+"' name='totalPrices' type='hidden' value='0'><input id='_product_remark_"+_serial_num+"' name='productRemarks' type='hidden' value=''></td>" +
	              							"<td style='text-algin:center'></td>" +
	              							"</tr>");  
	   	           				$("#_sino_eoss_sales_contract_products_table").append(tr);
	        				 }
	        			 } else if(val == '0'){
	        				 $("#tr_needother").remove();
	        			 }
	        		 });
	        	},
	        	customerDropSelect:function(id){
	        		$("#_select_customer_div").empty();
	        		$("#_select_customer_div").metaDropdownSelect({
       				 url:ctx+"/base/customermanage/customerInfo/getInfoModelById",
        			 searchUrl:ctx+"/base/customermanage/customerInfo/getCustomerInfosByName",
        			 inputShowValueId:id,
        			 required:true,
        			 placeholder:"请输入要搜索的客户名称,按回车键",
        			 width:"736",
       				 height:"20",
        			 onSelect:function(id,obj){
        				 $('#_eoss_sales_customerId').val(id);
        				 addSalesContract.methods.getCustomerContactsInfo(id);
	   					//获取到客户简称放入隐藏input标签供creatContractCode()使用
	   					var customerInfoCode=$(obj).attr("datacode");
	   					$('#_eoss_sales_customerInfoCode').val(customerInfoCode);
	   					//调取创建合同编码方法
	   					addSalesContract.methods.creatContractCode();
        			 }
	        		 });
	        	},
	        	buttonDelay:function(obj){
                    $("#_sino_eoss_sales_invoice_add").attr("class","btn");
                    $("#_sino_eoss_sales_invoice_add").unbind("click");
	        	},
	        	showFileField:function(){
	        	    require.async("formFileField", function() {
		        		var attr = {};
		        		attr.fileUploadFlag = "add";
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
	        	_back:function (){
	        		//关闭当前子窗口
	        		window.opener.childColseRefresh();
	        		metaUtil.closeWindow(); 
	        		//刷新父级窗口
	        	},
	        	//合同类型下拉框
	        	getContractType:function(){
	        		var $fieldUserTypes = $("#_eoss_sales_contractType");
	        		$fieldUserTypes.addClass("li_form");
	        		var optionUserTypes = {
	        			writeType : 'show',
	        			showLabel : false,
	        			required:true,
	        			code : 'contractType',
	        			width : "282",
	        			onSelect :function(){
	        				var type= $("#_eoss_sales_contractType").formSelect("getValue")[0];
	        				$('#_eoss_sales_contractTypeId').val(type);
	        				addSalesContract.methods.typeSelectResult(type);
	        			}
	        		};
	        		$fieldUserTypes.formSelect(optionUserTypes);
	        		if($('#_eoss_sales_contractTypeId').val() == null||$('#_eoss_sales_contractTypeId').val() == ""){
	        			var roomId = "";
		        		$fieldUserTypes.find("ul li").each(function(i,ele){
		        			if(i == 0){
		        				var obj = $(ele);
		    					roomId = obj.attr("infinityid");
		        			}
		    			});
		        		$fieldUserTypes.formSelect('setValue',roomId);
		        		$('#_eoss_sales_contractTypeId').val(roomId);
	        		} else {
	        			$fieldUserTypes.formSelect('setValue',$('#_eoss_sales_contractTypeId').val());
	        		}
	        	},
	        	
	        	typeSelectResult:function(id){
	        		$('.product_handler').show();
	        		$('#serviceStartDate_div').show();
        			$('#serviceEndDate_div').show();
        			$('.collection_plan').show();
        			$('#contractAmount').val("");
        			$('#type_th').html("产品类型");
        			$('#partner_th').html("产品厂商");
        			$('#product_th').html("产品型号");
        			$("#contractAmount").removeAttr("readonly");
        			$("#_sino_eoss_sales_products_add").show();
        			$("#_sino_eoss_sales_products_relate").show();
        			$("#_sales_import_excel").attr("href",ctx+"/sales/doc/projectProductTemplate.xls");
        			$("#_sino_eoss_sales_contract_products_table").show();
	        		$("#_sino_eoss_sales_contract_maproducts_table").hide();
	        		
	        		$("#_product_total").html("0");
	        		$("#_sino_eoss_sales_contract_products_table tbody").empty();
	        		$("#_sino_eoss_sales_contract_maproducts_table tbody").empty();
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
	        	
	        	//结算币种下拉框
	        	getAccountCurrency:function(){
	        		var $fieldUserTypes = $("#_eoss_sales_accountCurrency");
	        		$fieldUserTypes.addClass("li_form");
	        		var optionUserTypes = {
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
	        		
	        		if($('#_eoss_sales_accountCurrencyId').val() == null||$('#_eoss_sales_accountCurrencyId').val() == ""){
	        			var roomId = "";
		        		$fieldUserTypes.find("ul li").each(function(i,ele){
		        			if(i == 0){
		        				var obj = $(ele);
		    					roomId = obj.attr("infinityid");
		        			}
		    			});
		        		$fieldUserTypes.formSelect('setValue',roomId);
		        		$('#_eoss_sales_accountCurrencyId').val(roomId);
	        		} else {
	        			$fieldUserTypes.formSelect('setValue',$('#_eoss_sales_accountCurrencyId').val());
	        		}
	        		
	        	},
	        	//发票类型下拉框
	        	getInvoiceType:function(){
	        		var $fieldUserTypes = $("#_eoss_sales_invoiceType");
	        		$fieldUserTypes.addClass("li_form");
	        		var optionUserTypes = {
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
	        		
	        		if($('#_eoss_sales_invoiceTypeId').val() == null||$('#_eoss_sales_invoiceTypeId').val() == ""){
	        			var roomId = "";
		        		$fieldUserTypes.find("ul li").each(function(i,ele){
		        			if(i == 0){
		        				var obj = $(ele);
		    					roomId = obj.attr("infinityid");
		        			}
		    			});
		        		$fieldUserTypes.formSelect('setValue',roomId);
		        		$('#_eoss_sales_invoiceTypeId').val(roomId);
	        		} else {
	        			$fieldUserTypes.formSelect('setValue',$('#_eoss_sales_invoiceTypeId').val());
	        		}
	        	},
	        	//收款方式下拉框
	        	getReceiveWay:function (){
	        		var $fieldUserTypes = $("#_eoss_sales_receiveWay");
	        		$fieldUserTypes.addClass("li_form");
	        		var optionUserTypes = {
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
	        		
	        		if($('#_eoss_sales_receiveWayId').val() == null||$('#_eoss_sales_receiveWayId').val() == ""){
	        			var roomId = "";
		        		$fieldUserTypes.find("ul li").each(function(i,ele){
		        			if(i == 0){
		        				var obj = $(ele);
		    					roomId = obj.attr("infinityid");
		        			}
		    			});
		        		$fieldUserTypes.formSelect('setValue',roomId);
		        		$('#_eoss_sales_receiveWayId').val(roomId);
	        		} else {
	        			$fieldUserTypes.formSelect('setValue',$('#_eoss_sales_receiveWayId').val());
	        		}
	        		
	        	},
	        	//行业选择下拉框
	        	getIndustryName:function (){
	        		var industryUrl=ctx+'/base/customermanage/customerIndustry/getTreeList?tmp='+Math.random();
	        		var $industryName= $("#_industryName");
	        		$industryName.addClass("li_form");
	        		var optionIndustryName = {
	        				inputName : "industry",
	        				writeType : 'show',
	        				showLabel : false,
	        				required:true,
	        				width:"280", 
	        				url : industryUrl,
	        				onSelect :function(node){
	        					var id = $("#_industryName").formSelect("getValue")[0];
	        					 addSalesContract.methods.getCustomerIdtCustomer(id);
	        				}
	        		};
	        		$industryName.formSelect(optionIndustryName);
	        		 addSalesContract.methods.getCustomerIdtCustomer("");
	        		 $('.uicSelectData').height(200);
	        	},
	        	//行业客户选择下拉框
	        	getCustomerIdtCustomer:function(id){
	        		var url = ctx+"/base/customermanage/customerIndustry/getTreeListByIndustry?id="+id+"&tmp="+Math.random();
	        		//行业名称
	        		$("#_customerIdtCustomer").empty();
	        		var customerIdtCustomer = $("#_customerIdtCustomer");
	        		customerIdtCustomer.addClass("li_form");
	        		var optionCustomerIdtCustomer = {
	        				writeType : 'show',
	        				showLabel : false,
	        				required:true,
	        				url : url,
	        				height: "100",//高度
	        				onSelect:function(node,obj){
	        					var id = $("#_customerIdtCustomer").formSelect("getValue")[0];
	        					addSalesContract.methods.getCustomerInfo(id);
	        				},
	        				width : "590"
	        		};
	        		customerIdtCustomer.formSelect(optionCustomerIdtCustomer);
	        		addSalesContract.methods.getCustomerInfo("");
	        		$('.uicSelectData').height(200);
	        	},
	        	//客户选择下拉框
	        	getCustomerInfo:function(id){
	        		var url = ctx+"/base/customermanage/customerIdtCustomer/getTreeListByCustomerIdtCustomer?id="+id+"&tmp="+Math.random();
	        		$("#_customerInfo").empty();
	        		var customerInfo = $("#_customerInfo");
	        		customerInfo.addClass("li_form");
	        		var optionCustomerInfo = {
	        				writeType : 'show',
	        				showLabel : false,
	        				required:true,
	        				url : url,
	        				onSelect:function(node,obj){
	        					var id = $("#_customerInfo").formSelect("getValue")[0];
	        					$('#_eoss_sales_customerId').val(id);
	        					addSalesContract.methods.getCustomerContactsInfo(id);
	        					//获取到客户简称放入隐藏input标签供creatContractCode()使用
	        					var customerInfoCode=$(obj).attr("infinitycode");
	        					$('#_eoss_sales_customerInfoCode').val(customerInfoCode);
	        					//调取创建合同编码方法
	        					addSalesContract.methods.creatContractCode();
	        				},
	        				width : "430"
	        		};
	        		customerInfo.formSelect(optionCustomerInfo);
	        		customerInfo.formSelect('setValue',$('#_eoss_sales_customerId').val());
	        		$('.uicSelectData').height(200);
	        	},
	        	//联系人/手机号码默认值
	        	getCustomerContactsInfo:function (id){
//		       	     $.post(url,function(data ,status){
//		                	if(status="success"){
//		                		$('#_customerContacts').html(data.name);
//		                		$('#_customerContactsPhone').html(data.telphone);
//		                    }
//		                });
	        		var url = ctx+"/sales/contract/getContactsByCustomer?customerId="+id+"&tmp="+Math.random();
	       	        $("#_customerContacts").empty();
		       	    $('#_customerContactsPhone').html("");
	        		var $fieldUserTypes = $("#_customerContacts");
	        		$fieldUserTypes.addClass("li_form");
	        		var optionUserTypes = {
	        			writeType : 'show',
	        			showLabel : false,
	        			required:true,
	        			url:url,
	        			width : "160",
	        			onSelect :function(id,node){
	        				$("#customerContact").val(id);
	        				$('#_customerContactsPhone').html($(node).attr("infinitycode"));
	        			}
	        		};
	        		$fieldUserTypes.formSelect(optionUserTypes);
	        	},
	        	//创建合同编码方法
	        	creatContractCode:function (){
	        		var customerInfoCode=$('#_eoss_sales_customerInfoCode').val();
	        	     var url = ctx+"/sales/contract/creatContractCode?customerInfoCode="+customerInfoCode+"&tmp="+Math.random();
	        	     $.post(url,function(data ,status){
	                 	if(status="success"){
	                 		$('#contractCode').val(data);
	                     }
	                 });
	        	},
	        	_add:function(){
	        		
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
	        		if($("#_is_submit_product").val() != 1){
	        			UicDialog.Error("产品数量或服务结束时间填写有误,请重新填写");
	        			return;
	        		}
	        		$("#attachIds").val($("#uplaodfile").fileField("getValue"));
	        		//不是草稿的话判断收款金额和合同金额是否相等
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
	        						totalAmount=metaUtil.accAdd(totalAmount,$(elem).find("input").first().val());
	        					}
	        				});
	        			});
	        			var contractAmount = $('#contractAmount').val();
	        			if($("#contractAmount").attr("readonly") == null){
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
				       	    	 UicDialog.Error("请上传合同副本和毛利预估表!");
				       	    	 return;
				       	     } else {
				       	    	 var attachs = attachIds.split(",");
				       	    	 if(attachs.length < 2){
				       	    		 UicDialog.Error("请上传合同副本或毛利预估表!");
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
	        		
	        		//验证备品备件
	        	//	alert($("input[name='needother']:checked").val());
	        
	        		if($("input[name='needother']:checked").val()==1){
	        			var ischeckother=false;
	        			$("input[name='productNames']").each(function(i,elem){
		        		//	alert(elem.value);
		        			if(elem.value=="备品备件"){
		        				ischeckother=true;
		        			}
		        		});
	        			if(!ischeckother&&$("#tr_needother") == null){
	        				UicDialog.Error("该合同需要备品备件，请在合同清单中增加！");
	        				return;
	        			}
	        		}
	        	
	          	     //收款计划验证
		       		 var obj = new Object();
		       		 var grid = gridStore.get(form.name);// 获取表格行列name
		       		 var gridType=gridTypeStore.get(form.name);
		       		 var gridStoreData =  $('#' + form.name + ' tbody tr').data_grid('getValue',grid, form.name,gridType);
		       		 obj[form.name]=gridStoreData;
		       		 var json_data = JSON.stringify(obj);
		       	     $("#tableData").val(json_data);
		       	     
		       	     var applyAmount=0;
		
	        		 $("#editGridName tbody").find("tr").each(function(i,ele){ 
	        			 var index;
	        			 if(i==0){
	        				 index=i+1;
	        			 }else if(i>0){
	        				 index=1+i*3;
	        			 }
	        			 if($("#planedReceiveAmount_"+index).val()!=""&&$("#planedReceiveAmount_"+index).val()!=null){
	        				 applyAmount=metaUtil.accAdd(applyAmount,$("#planedReceiveAmount_"+index).val());
	        			 }
					});
	        		 //与剩余可开发票金额进行比较如果大于剩余金额，则提示不让提交
	        		 var contractAmount=$("#contractAmount").val();
	        		 if(!$('.collection_plan').is(':hidden')){
	        			 if(applyAmount>contractAmount){
		        			 UicDialog.Error("收款金额超过了合同金额！");
		        			 return;
		        		 }
    				}
		       	     var datas=$("#_sino_eoss_sales_contract_addform").serialize();
		       	     var url = ctx+'/sales/contract/doSave?tmp='+Math.random();
		       	     var options = {};
		       	     options.formId = "_sino_eoss_sales_contract_addform";
		       	     if($.formSubmit.doHandler(options)){
		       	    	 //启动遮盖层
		       	    	 $('#_progress1').show();
		       	    	 $('#_progress2').show();
		       	    	 //按钮不可用
		       	    	 addSalesContract.methods.buttonDelay();
		       	    	 $.post(url,datas,
		       	 	            function(data,status){
		       	 	            	if(data=="success"){
		       	 	            		var isSubmit = $("#isSubmit").val();
		       	 	            		if(isSubmit == 'CG'){
		       	 	            			UicDialog.Success("保存合同草稿成功!",function(){
			       	 	                		addSalesContract.methods._back();
			       	 	                	});
		       	 	            		} else {
			       	 	            		UicDialog.Success("合同提交成功!",function(){
			       	 	                		addSalesContract.methods._back();
			       	 	                	});
		       	 	            		}
		       	 	                  }else{
		       	 	                	var isSubmit = $("#isSubmit").val();
		       	 	                	if(isSubmit == 'CG'){
		       	 	                		UicDialog.Error("保存合同草稿失败!",function(){
		       	 	                		 $('#_progress1').hide();
		       	 	                		 $('#_progress2').hide();
			       	 	                	});
		       	 	            		} else {
		       	 	            			UicDialog.Error("合同提交失败!",function(){
			       	 	            			 $('#_progress1').hide();
			       	 	                		 $('#_progress2').hide();
			       	 	                	});
		       	 	            		}
		       	 	                  	  	//addSalesContract.methods._back();
		       	 	                  }
		       	 	                }); 
		       	     }
	        	}
	        }
	}

	//----------------------------设备添加---------------------------------------
	function _addProducts(){
		var frameSrc = ctx+"/sales/contract/toAddOrUpdateProductsView"; 
		$('#dailogs1').on('show', function () {
			$('#dtitle').html("增加合同清单");
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
			    	 if(_productType_ == ""||_productType_ == '0'||_productPartner_ == ""||_productPartner_ == '0'||_productNo_ == ""||_productNo_ == '0'||_unitPrice_ == ""){
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
			    			 $('#dailogs1').modal('hide');
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
								"<td title='"+_productRemark_+"'><span>"+_productNoName_+"</span><input id='_product_No_"+_serial_num+"' name='productNos' type='hidden' value='"+_productNo_+"'><input id='_product_Name_"+_serial_num+"' name='productNames' type='hidden' value='"+_productNoName_+"'></td>" +
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
								 totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
							 }
						 });
					 });
					 $('#_product_total').html(totalCount);
					 $('#dailogs1').modal('hide');
			     });
			
		 });
			    $('#dailogs1').on('hidden', function () {$('#dailogs1').unbind("show");});
				$('#dailogs1').modal({show:true});
				$('#dailogs1').off('show');
	}
	
	function _exportProducts(){
		var contractType = $("#_eoss_sales_contractType").formSelect("getValue")[0];
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
		
		if(contractType == 3000){
			buffer.append('<tr><td><input style="margin-top:-2px;margin-right:10px;" type="checkbox" id="_input_export" value="1" checked/>删除己有设备 打勾表示先删除己有设备后导入，否则以追加方式导入）**</td></tr>');
		}
		
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
		if(contractType == 3000){
			var url = ctx+"/sales/contract/uploadProducts?state=1";
			$('#btnConfirmq').unbind('click').click(function () {
				
				var ischecked=$('#_input_export').is(':checked');
				if(ischecked){
					$('#_product_total').html("0");
					$("#_sino_eoss_sales_contract_maproducts_table tbody").empty();
				}
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
	                			if(result.partnerName != null){
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
	                    		for(var i = 0;i < jsonData.length;i++){
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
	    	   	           		         var show_totalPrice_ = fmoney(_totalPrice_,2);

	    	   	           		         var _equipmentSplace_=jsonData[i].equipmentSplace;
	    	   	           		          
	    	   	           		    	 var _serial_num=$("#_sino_eoss_sales_contract_maproducts_table tr").length;
	    	   	           		    	 var tr=$("<tr style='text-algin:center'id='tr_"+_serial_num+"'>" +
	    	              							"<td style='text-algin:center'>"+_serial_num+"</td>" +
	    	              							"<td><span>"+_productPartnerName_+"</span><input id='_product_Partner_"+_serial_num+"' name='productPartners' type='hidden' value='"+_productPartner_+"'><input id='_product_Partner_Name_"+_serial_num+"' name='productPartnerNames' type='hidden' value='"+_productPartnerName_+"'></td>" +
	    	              							"<td><span>"+_productNoName_+"</span><input id='_product_No_"+_serial_num+"' name='productNos' type='hidden' value='"+_productNo_+"'><input id='_product_Name_"+_serial_num+"' name='productNames' type='hidden' value='"+_productNoName_+"'></td>" +
	    	              							"<td><span>"+_serialNumber_+"</span><input id='_product_serialNumber_"+_serial_num+"' name='serialNumber' type='hidden' value='"+_serialNumber_+"'></td>" +
	    	              							"<td><span>"+_servicePeriod_+"</span><input id='_product_servicePeriod_"+_serial_num+"' name='servicePeriod' type='hidden' value='"+_servicePeriod_+"'></td>" +
	    	              							"<td><span>"+_serviceStartDate_+"</span><input id='_product_serviceStartDate_"+_serial_num+"' name='serviceStartDates' type='hidden' value='"+_serviceStartDate_+"'></td>" +
	    	              							"<td><span>"+_serviceEndDate_+"</span><input id='_product_serviceEndDate_"+_serial_num+"' name='serviceEndDates' type='hidden' value='"+_serviceEndDate_+"'></td>" +
	    	              							"<td><span>￥"+show_unitPrice_+"</span><input id='_product_unitPrice_"+_serial_num+"' name='unitPrices' type='hidden' value='"+_unitPrice_+"'></td>" +
	    	              							"<td><span>"+_quantity_+"</span><input id='_product_quantity_"+_serial_num+"' name='quantitys' type='hidden' value='"+_quantity_+"'></td>" +
	    	              							"<td><span>￥"+show_totalPrice_+"</span><input id='_product_totalPrice_"+_serial_num+"' name='totalPrices' type='hidden' value='"+_totalPrice_+"'></td>" +
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
	    	   	           				 
	    	                   		}
	                			}
	                    		$("input[name='totalPrices']").each(function(i,ele){
	                    			totalCount=metaUtil.accAdd(totalCount,$(ele).val());
	                    		});
	                    		$('#_product_total').html(totalCount);
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

	    	   	           		    	 var _productRemark_=jsonData[i].productRemark;
	    	   	           		         
	    	   	           		    	 if(_productRemark_ == null){
	    	   	           		    		 _productRemark_ = "";
	    	   	           		    	 }
	    	   	           		    	 
	    	   	           		    	 var show_totalPrice_ = fmoney(_totalPrice_,2);
	    	   	           		    	 var _serial_num=$("#_sino_eoss_sales_contract_products_table tr").length;
	    	   	           		    	 var tr=$("<tr style='text-algin:center'id='tr_"+_serial_num+"'>" +
	    	              							"<td style='text-algin:center'>"+_serial_num+"<input type='hidden' name='relateDeliveryProductId' /><input type='hidden' name='relateContractProductId' value='0'/></td>" +
	    	              							"<td><span>"+_productTypeName_+"</span><input id='_product_Type_"+_serial_num+"' name='productTypes' type='hidden' value='"+_productType_+"'><input id='_product_Type_Name_"+_serial_num+"' name='productTypeNames' type='hidden' value='"+_productTypeName_+"'></td>" +
	    	              							"<td><span>"+_productPartnerName_+"</span><input id='_product_Partner_"+_serial_num+"' name='productPartners' type='hidden' value='"+_productPartner_+"'><input id='_product_Partner_Name_"+_serial_num+"' name='productPartnerNames' type='hidden' value='"+_productPartnerName_+"'></td>" +
	    	              							"<td title='"+_productRemark_+"'><span>"+_productNoName_+"</span><input id='_product_No_"+_serial_num+"' name='productNos' type='hidden' value='"+_productNo_+"'><input id='_product_Name_"+_serial_num+"' name='productNames' type='hidden' value='"+_productNoName_+"'></td>" +
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
		
		var serviceEndTime = $("#serviceEndDate_input").val();
		if(!$('#serviceStartDate_div').is(":hidden")){
			if(serviceEndTime == ""){
				UicDialog.Error("请先填写合同服务结束!");
	    		return;
			}
		}
		
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
										 var totalPrice = metaUtil.accMul($(this).val(),$("#"+qId).val());
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
										 var totalPrice = metaUtil.accMul($(this).val(),$("#"+qId).val());
										 $("#"+totalId).prev("span").html(totalPrice);
										 $("#"+totalId).val(totalPrice);
										 
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
									 
						    	 }
					    	 }
						}
						$('.date').datetimepicker({
	        		    	pickTime: false
	        		    }).on('changeDate', function(ev){
	            			/*var selectdate = ev.date;
	            			alert(selectdate);
	            			 if(!$('#serviceStartDate_div').is(":hidden")){
	            				 if(!timeObj.compareEndTimeAndtartTime(timeObj.sFormatterDateTime(selectdate),serviceEndTime)){
	            					 $("#_is_submit_product").val(0);
	            				 } else {
	            					 $("#_is_submit_product").val(1)
	            				 }
	            			 }*/
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
	
	function selectCustomerInfo(){
		$("#_sino_eoss_sales_products_import_div").empty();
		var path = ctx;
		var buffer = new StringBuffer();
		buffer.append('<div style="width:800px;" id="_sino_eoss_sales_products_import_page" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="_sino_eoss_sales_products_import_page" aria-hidden="true">');
		buffer.append('<div class="modal-header">');
		buffer.append('<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button >');
		buffer.append('<h3 id="myModalLabel">请选择</h3 >');
		buffer.append('</div >');
		buffer.append('<div class="modal-body" >');
		buffer.append('<div class="modal-body-a" ><div class="mddbod"><div style="float:left;width:50px;font-size:15px;font-weight: bold;">行业:</div><div style="float:left;width:720px;margin-top:-20px;margin-left:60px;">');
		var industryUrl=ctx+'/base/customermanage/customerIndustry/getTreeList?tmp='+Math.random();
		$.ajax({
			url:industryUrl,
			async : false,
			dataType : "json", 
			success : function(result){
				for(var i = 0;i < result.length;i++){
					buffer.append('<a class="_customerIndustry_select_a" name="_customerIndustry_select" id="'+result[i].id+'" href="#">'+result[i].name+'</a>');
				}
		    }  
		});
		buffer.append('</div></div><hr size=1  style="float:left;border-style: dashed;margin:5px 0;width:760px;"></div>');
		buffer.append('<div class="modal-body-b" id="_div_customerIdt"></div>');
		buffer.append('<div class="modal-body-c" id="_div_customerContract"></div>');
		buffer.append("</div>");	
		buffer.append('<div class="modal-footer" >');
		buffer.append('<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button >');
		buffer.append('<button class="btn btn-primary"  id="btnConfirmq" data-dismiss="modal">确定</button >');
		buffer.append('</div >');
		buffer.append('</div>');
		
		$("#_sino_eoss_sales_products_import_div").append(buffer.toString());
		
		 $("a[name='_customerIndustry_select']").unbind('click').click(function(){
			 $("._customerIndustry_select_a").removeAttr("style");
			 $(this).css("color","#0044cc");
			 var id = $(this).attr("id");
			 var url = ctx+"/base/customermanage/customerIndustry/getTreeListByIndustry?id="+id+"&tmp="+Math.random();
			 $.ajax({
					url:url,
					async : false,
					dataType : "json", 
					success : function(result){
						$('#_div_customerIdt').empty();
						var buffera = new StringBuffer();
						buffera.append('<div><div style="float:left;width:80px;font-size:15px;font-weight: bold;">行业客户:</div><div style="float:left;width:690px;margin-left:20px;margin-top:0px;">');
						buffera.append('<div class="brand-attr"><div class="a-values">');
						buffera.append('<div class="v-tabs"><ul style="display: block;" class="tab hide">');
						buffera.append('<li class="curr" rel="a">A<b></b></li>');
						buffera.append('<li class="" rel="b">B<b></b></li>');
						buffera.append('<li class="" rel="c">C<b></b></li>');
						buffera.append('<li class="" rel="d">D<b></b></li>');
						buffera.append('<li class="" rel="e">E<b></b></li>');
						buffera.append('<li class="" rel="f">F<b></b></li>');
						buffera.append('<li class="" rel="g">G<b></b></li>');
						buffera.append('<li class="" rel="h">H<b></b></li>');
						buffera.append('<li class="" rel="i">I<b></b></li>');
						buffera.append('<li class="" rel="j">J<b></b></li>');
						buffera.append('<li class="" rel="k">K<b></b></li>');
						buffera.append('<li class="" rel="l">L<b></b></li>');
						buffera.append('<li class="" rel="m">M<b></b></li>');
						buffera.append('<li class="" rel="n">N<b></b></li>');
						buffera.append('<li class="" rel="o">O<b></b></li>');
						buffera.append('<li class="" rel="p">P<b></b></li>');
						buffera.append('<li class="" rel="q">Q<b></b></li>');
						buffera.append('<li class="" rel="r">R<b></b></li>');
						buffera.append('<li class="" rel="s">S<b></b></li>');
						buffera.append('<li class="" rel="t">T<b></b></li>');
						buffera.append('<li class="" rel="u">U<b></b></li>');
						buffera.append('<li class="" rel="v">V<b></b></li>');
						buffera.append('<li class="" rel="w">W<b></b></li>');
						buffera.append('<li class="" rel="x">X<b></b></li>');
						buffera.append('<li class="" rel="y">Y<b></b></li>');
						buffera.append('<li class="" rel="z">Z<b></b></li>');
						buffera.append('</ul><div class="tabcon show-logo tabcon-multi height185">');
						for(var i = 0;i < result.length;i++){
//							buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
							var py = makePy(result[i].name);
							var pya = py[0];
							switch(pya){
								case 'A':
									buffera.append('<div style="" class="" rel="a" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'B':
									buffera.append('<div style="display: none;" class="" rel="b" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'C':
									buffera.append('<div style="display: none;" class="" rel="c" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'D':
									buffera.append('<div style="display: none;" class="" rel="d" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'E':
									buffera.append('<div style="display: none;" class="" rel="e" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'F':
									buffera.append('<div style="display: none;" class="" rel="f" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'G':
									buffera.append('<div style="display: none;" class="" rel="g" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'H':
									buffera.append('<div style="display: none;" class="" rel="h" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'I':
									buffera.append('<div style="display: none;" class="" rel="i" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'J':
									buffera.append('<div style="display: none;" class="" rel="j" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'K':
									buffera.append('<div style="display: none;" class="" rel="k" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'L':
									buffera.append('<div style="display: none;" class="" rel="l" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'M':
									buffera.append('<div style="display: none;" class="" rel="m" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'N':
									buffera.append('<div style="display: none;" class="" rel="n" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'O':
									buffera.append('<div style="display: none;" class="" rel="o" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'P':
									buffera.append('<div style="display: none;" class="" rel="p" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'Q':
									buffera.append('<div style="display: none;" class="" rel="q" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'R':
									buffera.append('<div style="display: none;" class="" rel="r" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'S':
									buffera.append('<div style="display: none;" class="" rel="s" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'T':
									buffera.append('<div style="display: none;" class="" rel="t" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'U':
									buffera.append('<div style="display: none;" class="" rel="u" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'V':
									buffera.append('<div style="display: none;" class="" rel="v" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'W':
									buffera.append('<div style="display: none;" class="" rel="w" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'X':
									buffera.append('<div style="display: none;" class="" rel="x" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'Y':
									buffera.append('<div style="display: none;" class="" rel="y" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'Z':
									buffera.append('<div style="display: none;" class="" rel="z" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
							}
						}
						buffera.append('</div></div>');
						buffera.append('</div></div></div><hr size=1  style="float:left;border-style: dashed;margin:5px 0;width:760px;"></div>');
						$('#_div_customerIdt').append(buffera.toString());
						
						$('li[rel]').bind('mouseover',function(){
						    var rel = $(this).attr('rel');
						    $(this).addClass('curr');
						    $('li:not([rel='+rel+'])').removeClass('curr');
						
						    if( rel==0 ) {
						        $('div[rel]').show();
						    } else {
						        $('div[rel='+rel+']').show();
						        $('div[rel]:not([rel='+rel+'])').hide();
						
						    }
						});
						
						$("a[name='_customerIdt_select']").unbind('click').click(function(){
							$("._customerIdt_select_a").css("color","");
							$(this).css("color","#0044cc"); 
							var id = $(this).attr("id");
							 var url = ctx+"/base/customermanage/customerIdtCustomer/getTreeListByCustomerIdtCustomer?id="+id+"&tmp="+Math.random();;
							 $.ajax({
								 url:url,
								 async : false,
								 dataType : "json", 
								 success : function(result){
									 $('#_div_customerContract').empty();
									 $('#_div_customerContract').append("<h5><span href='#' name='_select_spot'>客户选择:</span><input type='text' id='_customerInfo_select_input' placeholder='输入搜索内容,按回车键' style='margin-top:8px;margin-left:15px;'></h5>");
									 for(var i in result){
										 $('#_div_customerContract').append('<div class="_c_div"><a class="_customerInfo_select_a" name="_customerInfo_select" style="" code="'+result[i].code+'" id="'+result[i].id+'" href="#">'+result[i].name+'</a></div>');
									 }
									 
									 $("a[name='_customerInfo_select']").unbind('click').click(function(){
										 $("._customerInfo_select_a").css("color","");
										 $(this).css("color","#0044cc");
										 var id = $(this).attr("id");
										 
										 addSalesContract.methods.customerDropSelect(id);

										 $('#_eoss_sales_customerId').val(id);
										 var customerInfoName = $(this).html();
										 $('#_customerInfoName').html(customerInfoName);
			        					 addSalesContract.methods.getCustomerContactsInfo(id);
			        					 //获取到客户简称放入隐藏input标签供creatContractCode()使用
			        					 var customerInfoCode=$(this).attr("code");
			        					 $('#_eoss_sales_customerInfoCode').val(customerInfoCode);
										 //调取创建合同编码方法
			        					 addSalesContract.methods.creatContractCode();
			        					 $("#_sino_eoss_sales_products_import_page").modal('hide');
									 });
									 
									 $('#_customerInfo_select_input').keyup(function(event){
											var myEvent = event || window.event; 
											var keyCode = myEvent.keyCode;
											var val = $(this).val();
											if(keyCode == 13||keyCode==32||keyCode == 8){
													$(this).parent().parent().find("a").each(function(i,ele){
														$(ele).parent().css("display","block");
														if(val != ""){
															var infinityName = $(ele).html();
															var pos = infinityName.indexOf(val,0);
															if(pos == -1){
																$(ele).parent().css("display","none");
															}
														}
													});
												}
										});
								 }  
							 });
						 });
						$("._customerIdt_select_a").first().click();
				    }  
				});
		 });
		$("._customerIndustry_select_a").first().click();
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
			$('#dtitle').html("修改设备");
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
			    	 
			    	 newOptions.productRemark=$("#_product_add_remark").val();
			    	 
			    	 if(newOptions.productType == ""||newOptions.productType == '0'||newOptions.productPartner == ""||newOptions.productPartner == '0'){
			    		 $('#_product_alertMsg').empty();
			 			 $('#_product_alertMsg').append('<div class="alert alert-error"><strong>错误：</strong>请把数据填写完整！<button type="button" class="close _cancleHandler" data-dismiss="alert">&times;</button></div>');
			 			 $(".alert").delay(2000).hide(0);
			 			 $("._cancleHandler").click(function() {
			 				$(".alert").hide();
			 			 });
			    		 return;
			    	 }
			    	 
			    	 var  show_unitPrice=fmoney(newOptions.unitPrice,2);
			    	 var show_totalPrice=fmoney(newOptions.totalPrice,2);
			    	 newOptions.serial_num=serialNum;
			    	 
			    	 if(newOptions.endTime != ""){
			    		 var endDate = $("#serviceEndDate_input").val();
			    		 if(endDate == ""){
			    			 UicDialog.Error("请先填写合同服务结束!");
			    			 $('#dailogs1').modal('hide');
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
								 totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
							 }
						 });
					 });
					 $('#_product_total').html(totalCount);
					 $('#dailogs1').modal('hide');
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
				 if(j == 8){
					 totalCount = metaUtil.accSub(totalCount,$(elem).find("input").first().val()); 
				 }
			 });
		//	totalCount ＝ fmoney(totalCount,2);
			 $('#_product_total').html(totalCount);
			 $("#tr_"+serialNum).remove();
			
		});
	}
	//------------------------------------------------------------------------
	exports.init = function(){
		addSalesContract.methods.initDocument();  
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