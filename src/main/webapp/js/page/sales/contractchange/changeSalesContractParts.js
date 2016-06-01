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
	gridStore = new Map();//
	gridTypeStore=new Map();//表格行列类型
	gridFieldStore = new Map();
	require("json2");
	require("js/common/form/data_grid");
	require("js/plugins/meta/js/metaDropdowmSelect");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	var timeObj = require("js/plugins/meta/js/timeObjectUtil");
	
	//------------------加载DataTable-------------End------------
	String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
	}
	var salesContractParts = {
			config: {
				module: 'salesContractParts',
            	namespace:'/sales/contract'
	        },
	        methods :{
	        	initDocument : function(){
	        		
	        		//采购员
	        		var buyers = $("#_sales_contracts_buyer").html();
	        		if(buyers != null&&buyers != ""){
	        			$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx + "/sales/contract/getSaleContractBuyers?buyers="+buyers+"&tmp="+ Math.random(),
			    			success : function(msg) {
			    				$("#_sales_contracts_buyer").html(msg.buyers);
			    			}
			    		});
	        		}
	        		
	        	
	        		
	        		$("a[name='hrefReload']").unbind('click').click(function () {
	        			var url = $(this).attr("hrefA");
	        			window.location.href = url;
	        		});
	        		
	        		salesContractParts.doExecute('getDataTable');
	        		salesContractParts.doExecute('getContractType');
	        		salesContractParts.doExecute('getInvoiceType');
	        		salesContractParts.doExecute('getAccountCurrency');
	        		salesContractParts.doExecute('getReceiveWay');
	        		salesContractParts.doExecute('getIndustryAndCustomerIdt');
	        		salesContractParts.doExecute('staffSelect');
	        		 //加载附件组件
	        		var typeJson = {};
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				typeJson = msg;
		    			}
		    		});
	        		
	        		salesContractParts.methods.showFileField();
	        		$("#_invoice_table tbody").find("tr").each(function(i,ele){
	        			 var invoiceTypeId=$('#invoiceType'+i).text();
	        			 salesContractParts.methods.getInvoice(invoiceTypeId,i,typeJson);
	 	        		var invoiceStatusId=$('#invoiceStatus'+i).text();
	 	        		salesContractParts.methods.getInvoiceStatus(invoiceStatusId,i);
	        		 });

		        	//详情返回按钮
		        	$("#_sino_eoss_sales_contract_detailBack").unbind('click').click(function () {
		        		salesContractParts.methods.closeWindow('detail');
		        	});
		        	salesContractParts.methods.typeSelectResult($('#contractTypeId').val());
		        	salesContractParts.methods.getContractName();
		        	if($("#contractTypeId").val() == 9000){
		        		var totalCount = 0;
			       		 $('#_sino_eoss_sales_contract_readyproducts_table tbody').find("tr").each(function(i,ele){
			       			 $(ele).find("td").each(function(j,elem){
			       				 if(j == 8){
			       					 totalCount+=parseInt($(elem).attr("tdPrice"));
			       				 }
			       			 });
			       		 });
			       		 $('#_product_total').html('￥'+fmoney(totalCount,2));
		        	} else if($("#_contract_hidden").is(":hidden")){
	        			var totalCount = 0;
			       		 $('#_sino_eoss_sales_contract_products_table tbody').find("tr").each(function(i,ele){
			       			 $(ele).find("td").each(function(j,elem){
			       				 if(j == 8){
			       					 totalCount+=parseInt($(elem).attr("tdPrice"));
			       				 }
			       			 });
			       		 });
			       		 $('#_product_total').html('￥'+fmoney(totalCount,2));
	        		} else {
	        			var totalCount = 0;
			       		 $('#_sino_eoss_sales_contract_products_table tbody').find("tr").each(function(i,ele){
			       			 $(ele).find("td").each(function(j,elem){
			       				 if(j == 9){
			       					 totalCount+=parseInt($(elem).attr("tdPrice"));
			       				 }
			       			 });
			       		 });
			       		 $('#_product_total').html('￥'+fmoney(totalCount,2));
	        		}
	
		        	//修改单价金额变更
//		        	$(".unitPrices").blur(function(){
//		        		var num=$(this).parent().prev().find("input").val();
//		        		var amount=num*this.value;
//		        		$(this).parent().next().find("span").text('￥'+fmoney(amount,2));
//		        		$(this).parent().next().find("input:first").val(amount);
//		        		$(this).parent().next().attr("tdPrice",amount);
//		        		var totalCount=0;
//			       		 $('#_sino_eoss_sales_contract_products_table tbody').find("tr").each(function(i,ele){
//			       			 $(ele).find("td").each(function(j,elem){
//			       				 if(j == 8){
//			       					 totalCount+=parseInt($(elem).attr("tdPrice"));
//			       				 }
//			       			 });
//			       		 });
//			       		 $('#_product_total').html('￥'+fmoney(totalCount,2));
//			       		 
//			       		 $('#contractAmountshow').text(totalCount);
//			       		 
//			       		 $('#contractAmount').val(totalCount);
//		        	});
		        	
			       	 //设备添加
			   		 $("#_sino_eoss_sales_products_add").bind('click').click(function(){
			   				_addProducts();
			   		 });
			   		 //设备修改
					 $("._sino_eoss_sales_contract_update_product").unbind('click').click(function(){
						 _updateProducts($(this).attr("serial_num"));
					 });
					 //设备删除
					 $("._sino_eoss_sales_contract_update_remove_product").unbind('click').click(function(){
						 _removeProductsRelate($(this).attr("serial_num"),this);
					 });
		        	$("#_sino_eoss_sales_contract_change_submit").unbind('click').click(function () {
		        		salesContractParts.methods.doContractChange();
		        	});
		        	
		        },
		        doContractChange:function(){
					var contractAmount = parseInt($('#contractAmount').val());
	        		
		
	    			 var totalCount = 0;
					 $('#_sino_eoss_sales_contract_products_table tbody').find("tr").each(function(i,ele){
						 $(ele).find("td").each(function(j,elem){
							 if(j == 8){
								 totalCount+=parseInt($(elem).find("input").first().val());
							 }
						 });
					 });
	    	
	        			if(contractAmount != totalCount){
	        			
	        					 UicDialog.Error("合同金额和合同清单金额不等，不能提交！");
	    	    				return;
	        			
	        			}
	        			
			    		
			    		 var obj = new Object();
			    		 var grid = gridStore.get(form.name);// 获取表格行列name
			    		 var gridType=gridTypeStore.get(form.name);
			    		 var gridStoreData =  $('#' + form.name + ' tbody tr').data_grid('getValue',grid, form.name,gridType);
			    		 obj[form.name]=gridStoreData;
			    		 var json_data = JSON.stringify(obj);
			    	     $("#tableData").val(json_data);
			    	     
			        		var datas=$("#_sino_eoss_sales_contract_change_addform").serialize();
			        		var url = ctx+'/sales/contract/doContractProductChange?tmp='+Math.random()+'&changeType='+$("#changeType").val();
			        		$("#_sino_eoss_sales_contract_change_submit").unbind('click');
			        		$.post(url,datas,
			    	            function(data,status){
			    	            	if(data=="success"){
			    	                	  UicDialog.Success("保存数据成功!",function(){
			    	                		  salesContractParts.methods._back();
			    	                	  });
			    	                  }else{
			    	                  	  UicDialog.Error("保存数据失败！");
			    	                  	salesContractParts.methods._back();
			    	                  }
			    	        }); 
		        },
		    	_back: function(){
		    		//刷新父级窗口
		    		window.opener.childColseRefresh();
		    		//关闭当前子窗口
		    		metaUtil.closeWindow();  
		        },
		        getContractName:function(){
		        	if($("td[name = 'relateDeliveryProduct_contract']").length > 0){
		        		$("#_contract_show").hide();
		        		$("#_contract_hidden").show();
		        		if($("td[name = 'relateDeliveryProduct_contract_']").length > 0){
		        			$("td[name = 'relateDeliveryProduct_contract_']").after("<td></td>");
		        		}
		        	} else {
		        		$("#_contract_show").show();
		        		$("#_contract_hidden").hide();
		        	}
		        	$("td[name = 'relateDeliveryProduct_contract']").each(function(i,ele){
		        		var id = $(ele).attr("tid");
		        		$.ajax({
			        		type : "GET",
			        		url : ctx+"/sales/contract/getContract?pruductId="+id+"&tmp="+ Math.random(),
			        		success : function(msg) {
			        			var str = $(ele).html();
			        			str+=msg.contractName;
			        			$(ele).html(str);
			        		}
			        	});
		        	});
		        },
		        
		        getInvoice: function(invoiceTypeId,m,msg){
		        	for(var i in msg){
    					if(msg[i].id == invoiceTypeId){
    						$('#invoiceType'+m).text(msg[i].name);
    					}
    				}
		        },
		        typeSelectResult:function(id){
	        		$('.product_handler').show();
	        		$('#serviceStartDate_div').show();
        			$('#serviceEndDate_div').show();
        			$('.collection_plan').show();
        			$('#type_th').html("产品类型");
        			$('#partner_th').html("产品厂商");
        			$('#product_th').html("产品型号");
	        		//产品合同
	        		if(id == 1000){
	        		}
	        		//临时采购
	        		if(id == 2000){
	        			$('.collection_plan').hide();
	        		}
	        		//MA续保合同
	        		if(id == 3000){
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
	        		}
	        		//软件开发合同
	        		if(id == 6000){
	        			$('#type_th').html("软件类型");
	        			$('#partner_th').html("软件厂商");
	        			$('#product_th').html("软件版本");
	        		}
	        		//公司备件
	        		if(id == 7000){
	        			$('.collection_plan').hide();
	        			$('#serviceStartDate_div').hide();
	        			$('#serviceEndDate_div').hide();
	        			$('#contractAmount').val(0);
	        		}
	        		//客户配件
	        		if(id == 8000){
	        			$('.collection_plan').hide();
	        			$('#serviceStartDate_div').hide();
	        			$('#serviceEndDate_div').hide();
	        		}
	        		//备货合同
	        		if(id == 9000){
	        			$('.collection_plan').hide();
	        		}
	        		//其他合同类型
	        		if(id == 0000){
	        		}
	        	},
		        getInvoiceStatus :function(invoiceStatusId,i){
		        	/*$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=invoiceStatus&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == invoiceStatusId){
		    						$('#invoiceStatus'+i).text(msg[i].name);
		    					}
		    				}
		    			}
		    		});*/
		        },
		        getDataTable : function(){
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
		        		attr.fileUploadFlag = "detail";
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
		        getInvoiceType : function(){
		        	var invoiceTypeId=$("#_eoss_sales_invoiceTypeId").val();
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == invoiceTypeId){
		    						$('#invoiceType').text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        },
		        
		        getAccountCurrency : function(){
		        	var accountCurrencyId=$("#_eoss_sales_accountCurrencyId").val();
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=accountCurrency&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == accountCurrencyId){
		    						$('#accountCurrency').text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        },
		        getReceiveWay : function(){
		        	var receiveWayId=$("#_eoss_sales_receiveWayId").val();
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=receiveWay&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == receiveWayId){
		    						$('#receiveWay').text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
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
		        closeWindow: function(colseType){//审核后刷新父页面
		    		//刷新父级窗口
		        	if(colseType=='proc'){//如果是“待办”审核后刷新“待办事项”这个父页面
		        		window.opener.reload();
		        	}
		        	if(colseType=='detail'){//如果是“待办”审核后刷新“合同管理”这个父页面
//		        		window.opener.childColseRefresh();
		        	}
		        	//关闭当前子窗口
		        	metaUtil.closeWindow();  
		        },
		        staffSelect:function(){
		    		var $fieldStaff = $("#formStaff");
		    		// 选人组件
		    		var optionss = {
		    			inputName : "staffValues",
		    			showLabel : false,
		    			width : "580",
		    			name : "code",
		    			value : "root",
		    			level:2,
		    			tree_url:ctx+'/staff/getStaffByOrgs?random=1',
		    			selectUser : false,
		    			radioStructure : false
		    		}
		    		optionss.addparams = [ {
		    			name : "orgs",
		    			//三个销售部门ID
	        			value : "52"
		    		} ];

		    		$fieldStaff.formUser(optionss);
		    		if($('#flowStep').val() != 'show1'){
		        		$('#_userName_ul').hide();
		        	}
		        },
		        approveToFlow : function(opts){
		        	if(flowFlag=='SWJLSP'){
		        		var isAgreen =$('input[name="isAgree"]:checked').val()
		        		var orderProcessorId =$("#formStaff").formUser("getValue");
		        		//审批校验，必须选择订单处理人
		        		if(orderProcessorId==""&&isAgreen!=0){
		        			alert("请选择订单处理人");
		        			return;
		        		}
		        	}
		    		$("#orderProcessorId").val(orderProcessorId);
		        	var options = {};
		    		options.murl = ctx+salesContractParts.config.namespace + opts.url;
		    		var datas=$("#_sino_eoss_sales_contract_approveForm").serialize();
		    		//启动遮盖层
	       	    	 $('#_progress1').show();
	       	    	 $('#_progress2').show();
		    	    $.post(options.murl,datas,
		    	          function(data,status){
		    	            if(status=="success"){
		    	                UicDialog.Success("审批成功!",function(){
		    	                	salesContractParts.doExecute("closeWindow","proc");
		    	                });
		    	            }else{
		    	                UicDialog.Error("审批失败！",function(){
		    	                	salesContractParts.doExecute("closeWindow","proc");
		    	                });
		    	           }
		    	    });
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =salesContractParts.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

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
								 totalCount+=parseInt($(elem).find("input").first().val());
							 }
						 });
					 });
					 $('#_product_total').html(totalCount);
				//	 $('#contractAmountshow').text(totalCount);
	       		 
     	       //		 $('#contractAmount').val(totalCount);
					 
			     });
			
		 });
			    $('#dailogs1').on('hidden', function () {$('#dailogs1').unbind("show");});
				$('#dailogs1').modal({show:true});
				$('#dailogs1').off('show');
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
			    	 
			    	 newOptions.productRemark=$("#_product_add_remark").val();
			    	 
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
								 totalCount+=parseInt($(elem).find("input").first().val());
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
			
			var totalCount = parseInt($('#_product_total').html());
			$("#tr_"+serialNum).find("td").each(function(j,elem){
				 if(j == 8){
					 totalCount = totalCount-parseInt($(elem).find("input").first().val());
				 }
			 });
		//	totalCount ＝ fmoney(totalCount,2);
			 $('#_product_total').html(totalCount);
			 
			 $("#tr_"+serialNum).remove();
		});
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
	exports.init = function() {
		salesContractParts.doExecute('initDocument');
	}
});