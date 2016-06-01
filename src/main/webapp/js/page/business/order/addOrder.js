define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap-datetimepicker");
	require("bootstrap");
	require("jqBootstrapValidation");
	require("select2");
	require("select2_locale_zh-CN");
	require("formSubmit");
	
	var uic$ = require("uic_Dropdown");
	var DT_bootstrap = require("DT_bootstrap");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	
	//------------------加载DataTable-----------Start--------------
	var Map = require("map");
	require("uic/message_dialog");
	gridStore = new Map();//
	gridTypeStore=new Map();//表格行列类型
	gridFieldStore = new Map();
	require("json2");
	require("js/common/form/data_grid");
	//------------------加载DataTable-------------End------------
	
	var addOrderContract={
			config: {
				//namespace:'/business/supplierm/supplierInfo'
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
						 $("#no_back").bind('click',function(){
							 _back();
				         });
						//直接提交审批
						 $("#sp_Add").unbind('click').click(function () {
							 $("#orderStatus").val('SH');
							 _add();
					       });
						//保存为草稿
						 $("#ok_Add").unbind('click').click(function () {
							 $("#orderStatus").val('CG');
								_add();
					       });
						 $("#selectContract").bind('click',function(){
							 selectContract();
					       });
						 $("input:radio[name='spotChange']").unbind('click').click(function () {
			        			var radioValue = $('input:radio[name="spotChange"]:checked').val();
			        			if(radioValue == 0){
			        				$('._is_hidden').hide();
			        			} else {
			        				$('._is_hidden').show();
			        				getSpotNum();
			        			}
			        		});
						 $('#orderCode').blur(function(){
							 _toBlur();
				     		});
						
						 $("#checkall").click(function() {
				                $('input[name="myCheckbox"]').attr("checked",this.checked);
				            });
						 $("#remove_product").unbind('click').click(function(){
	 	           			 _removeProducts();
	 	           			getAmount();
	 	 				 });
						 $("#remove_product_else").unbind('click').click(function(){
	 	           			 _removeProductsElse();
	 	           			getAmount();
	 	 				 });
						 //加载订单类型下拉框
						 getOrderType();
						 //加载发票类型下拉框
						 getInvoiceType();
						 //加载采购分类下拉框
						 getPurchaseType();
						 //加载付款方式下拉框
						 getPaymentMode();
						 //加载利润类型下拉框
						 //getOrderProfits();
						 //加载发货地点
						 getDeliveryAddress();
						 getTime();
						 var suId="";
						 supplierInfoSelect(suId);
		        		 //加载供应商类型下拉框
		        		 getSupplierType();
		        		 //加载结算币种
		        		 getAccountCurrency();
						 
			   },

				},
				/**
				 * 执行方法操作
				 */
				doExecute : function(flag, param) {
					var method =addOrderContract.methods[flag];
					if( typeof method === 'function') {
						return method(param);
					} else {
						alert('操作 ' + flag + ' 暂未实现！');
					}
				}
	}
    /**
     *  时间插件
     */
      function  getTime(){
     	  $('.date').datetimepicker({ 
     	        pickTime: false
     	 
     	      }); 
      }
	
	//结算币种
	function getAccountCurrency(){
 		var $accountCurrency = $("#_accountCurrency");
 		$accountCurrency.addClass("li_form");
 		var optionUserState = {
 			inputName : "accountCurrency",
 			inputValue: $("#accountCurrency").attr("value"),
 			writeType : 'show',
 			showLabel : false,
 			code : 'accountCurrency',
 			width : "255",
 			required : true,
 			onSelect :function(){
				var type= $("#_accountCurrency").formSelect("getValue")[0];
				$('#_eoss_business_accountCurrency').val(type);
		}
 		};
 		$accountCurrency.formSelect(optionUserState);	
 		if($('#_eoss_business_accountCurrency').val() == null||$('#_eoss_business_accountCurrency').val() == ""){
			var roomId = "";
    		$accountCurrency.find("ul li").each(function(i,ele){
    			if(i == 0){
    				var obj = $(ele);
					roomId = obj.attr("infinityid");
    			}
			});
    		$accountCurrency.formSelect('setValue',roomId);
    		$('#_eoss_business_accountCurrency').val(roomId);
		} else {
			$accountCurrency.formSelect('setValue',$('#_eoss_business_accountCurrency').val());
		}
	}
	
	
	//删除勾选中的产品
		function _removeProducts(){
			var myCheckbox = $("input[name='myCheckbox']:checked");
			myCheckbox.each(function(i,ele){//循环拼装被选中项的值
				if($(ele).val() != 'null'){
					var trId =$(this).val();
						$("#tr_"+trId).remove();
				}
			 });
			
		}
		//反选产品
		function _removeProductsElse(){
			 var arrSon = $("input[name='myCheckbox']");
			 for(i=0;i<arrSon.length;i++) {
			  arrSon[i].click();
			 }
			 _removeProducts();
	     }	
		
		//得到产品总价
		function getAmount(){
			var sum=0;
			    $("[name='trName']").each(function(i,ele){
			    	var num=$(this).attr("num");
			    	if($("#sub"+num).val()!=null&&$("#sub"+num).val()!=""){
			    		sum = metaUtil.accAdd(Number(sum),Number($("#sub"+num).val()));
					//	sum+=Number($("#sub"+num).val());
					}
			    });
				$("#orderAmount").val(sum);
				var show_amount=fmoney(sum,2);
				$("#tatol").text("￥"+show_amount);
		}
		
	function _toBlur(){
		var orderCode = $('#orderCode').val();
		var url=ctx+"/business/order/checkOrderCode?orderCode="+orderCode+"&tmp="+Math.random();
		     $.post(url,
		 	            function(data,status){
		 	            	if(data.result=="success"){
		 	                  }else{
		 	                		 UicDialog.Error("错误!"+data.message);
		 	                  }
		 	                },"json"); 
	}
	function getOrderType(){
		var $fieldUserTypes = $("#_eoss_business_ordertype");
		$fieldUserTypes.addClass("li_form");
		var optionUserTypes = {
			writeType : 'show',
			showLabel : false,
			required:true,
			code : 'orderType',
			width : "254",
			onSelect :function(){
				var type= $("#_eoss_business_ordertype").formSelect("getValue")[0];
				$('#_eoss_business_ordertypeId').val(type);
			 	$('#product_add tbody').html("");
				$('#orderAmount').val(0);
				$('#tatol').html('0');
		}
		};
		$fieldUserTypes.formSelect(optionUserTypes);
		if($('#_eoss_business_ordertypeId').val() == null||$('#_eoss_business_ordertypeId').val() == ""){
			var roomId = "";
    		$fieldUserTypes.find("ul li").each(function(i,ele){
    			if(i == 0){
    				var obj = $(ele);
					roomId = obj.attr("infinityid");
    			}
			});
    		$fieldUserTypes.formSelect('setValue',roomId);
    		$('#_eoss_business_ordertypeId').val(roomId);
		} else {
			$fieldUserTypes.formSelect('setValue',$('#_eoss_business_ordertypeId').val());
		}
	}
	
	function getDeliveryAddress(){
		var $fieldUserTypes = $("#_business_deliveryAddress");
		$fieldUserTypes.addClass("li_form");
		var optionUserTypes = {
			writeType : 'show',
			showLabel : false,
			required:true,
			code : 'deliveryAddress',
			width : "254",
			onSelect :function(){
				var type= $("#_business_deliveryAddress").formSelect("getValue")[0];
				$('#_business_deliveryAddressId').val(type);
		}
		};
		$fieldUserTypes.formSelect(optionUserTypes);
		if($('#_business_deliveryAddressId').val() == null||$('#_business_deliveryAddressId').val() == ""){
			var roomId = "";
    		$fieldUserTypes.find("ul li").each(function(i,ele){
    			if(i == 0){
    				var obj = $(ele);
					roomId = obj.attr("infinityid");
    			}
			});
    		$fieldUserTypes.formSelect('setValue',roomId);
    		$('#_business_deliveryAddressId').val(roomId);
		} else {
			$fieldUserTypes.formSelect('setValue',$('#_business_deliveryAddressId').val());
		}
		
	}
	
	function getInvoiceType(){
		var $fieldUserTypes = $("#_eoss_business_invoiceType");
		$fieldUserTypes.addClass("li_form");
		var optionUserTypes = {
			writeType : 'show',
			showLabel : false,
			code : 'invoiceType',
			width : "254",
			onSelect :function(){
				var type= $("#_eoss_business_invoiceType").formSelect("getValue")[0];
				$('#_eoss_business_invoiceTypeId').val(type);
		}
		};
		$fieldUserTypes.formSelect(optionUserTypes);
		if($('#_eoss_business_invoiceTypeId').val() == null||$('#_eoss_business_invoiceTypeId').val() == ""){
			var roomId = "";
    		$fieldUserTypes.find("ul li").each(function(i,ele){
    			if(i == 1){
    				var obj = $(ele);
					roomId = obj.attr("infinityid");
    			}
			});
    		$fieldUserTypes.formSelect('setValue',roomId);
    		$('#_eoss_business_invoiceTypeId').val(roomId);
		} else {
			$fieldUserTypes.formSelect('setValue',$('#_eoss_business_invoiceTypeId').val());
		}
	}
	
	function getPurchaseType(){
		var $fieldUserTypes = $("#_eoss_business_purchaseType");
		$fieldUserTypes.addClass("li_form");
		var optionUserTypes = {
			writeType : 'show',
			showLabel : false,
			code : 'purchaseType',
			width : "254",
			onSelect :function(){
				var type= $("#_eoss_business_purchaseType").formSelect("getValue")[0];
				$('#_eoss_business_purchaseTypeId').val(type);
		}
		};
		$fieldUserTypes.formSelect(optionUserTypes);
		if($('#_eoss_business_purchaseTypeId').val() == null||$('#_eoss_business_purchaseTypeId').val() == ""){
			var roomId = "";
    		$fieldUserTypes.find("ul li").each(function(i,ele){
    			if(i == 0){
    				var obj = $(ele);
					roomId = obj.attr("infinityid");
    			}
			});
    		$fieldUserTypes.formSelect('setValue',roomId);
    		$('#_eoss_business_purchaseTypeId').val(roomId);
		} else {
			$fieldUserTypes.formSelect('setValue',$('#_eoss_business_purchaseTypeId').val());
		}
	}
	
	function getPaymentMode(){
		var $fieldUserTypes = $("#_eoss_business_paymentMode");
		$fieldUserTypes.addClass("li_form");
		var optionUserTypes = {
			writeType : 'show',
			showLabel : false,
			required:true,
			code : 'paymentMode',
			width : "254",
			onSelect :function(){
				var type= $("#_eoss_business_paymentMode").formSelect("getValue")[0];
				$('#_eoss_business_paymentModeId').val(type);
		}
		};
		$fieldUserTypes.formSelect(optionUserTypes);
		if($('#_eoss_business_paymentModeId').val() == null||$('#_eoss_business_paymentModeId').val() == ""){
			var roomId = "";
    		$fieldUserTypes.find("ul li").each(function(i,ele){
    			if(i == 0){
    				var obj = $(ele);
					roomId = obj.attr("infinityid");
    			}
			});
    		$fieldUserTypes.formSelect('setValue',roomId);
    		$('#_eoss_business_paymentModeId').val(roomId);
		} else {
			$fieldUserTypes.formSelect('setValue',$('#_eoss_business_paymentModeId').val());
		}
	}
	function getOrderProfits(){
		var $fieldUserTypes = $("#_eoss_orderProfits");
		$fieldUserTypes.addClass("li_form");
		var optionUserTypes = {
			writeType : 'show',
			showLabel : false,
			required:true,
			code : 'orderProfits',
			width : "254",
			onSelect :function(){
				var type= $("#_eoss_orderProfits").formSelect("getValue")[0];
				$('#orderProfits').val(type);
		}
		};
		$fieldUserTypes.formSelect(optionUserTypes);
		if($('#orderProfits').val() == null||$('#orderProfits').val() == ""){
			var roomId = "";
    		$fieldUserTypes.find("ul li").each(function(i,ele){
    			if(i == 0){
    				var obj = $(ele);
					roomId = obj.attr("infinityid");
    			}
			});
    		$fieldUserTypes.formSelect('setValue',roomId);
    		$('#orderProfits').val(roomId);
		} else {
			$fieldUserTypes.formSelect('setValue',$('#orderProfits').val());
		}
	}
	function getSupplierType(){
		var $fieldUserTypes= $("#_supplierType");
		$fieldUserTypes.addClass("li_form");
		var optionSupplierName = {
				writeType : 'show',
				showLabel : false,
				width:"254", 
				code:'supplierType',
				onSelect :function(node){
					var id = $("#_supplierType").formSelect("getValue")[0];
					//getSupplierName(id);
					supplierInfoSelect(id);
				}
		};
		$fieldUserTypes.formSelect(optionSupplierName);
		$fieldUserTypes.formSelect('setValue',$('#_supplierType').val());
		//getSupplierName("");
		supplierInfoSelect("");
		contactsInfoSelect("");
	
	}
	   function supplierInfoSelect(id){
			$("#supplierInfoSelect").select2({
				ajax: {
					url:ctx+'/business/order/findSupplierInfoValue?id='+id+'&tmp='+Math.random(),
					dataType: 'json',
					quietMillis: 100,
					data: function (term, page) { // page is the one-based page number tracked by Select2
						return {
							q: term, //search term
							page_limit: 10, // page size
							page: page, // page number
						};
					},
					results: function (data, page) {
						var more = (page * 10) < data.total; // whether or not there are more results available
						// notice we return the value of more so Select2 knows if more results can be loaded
						return {results: data.rows, more: more};
					}
				},
				formatResult: format, // omitted for brevity, see the source of this page
				formatSelection: format1, // omitted for brevity, see the source of this page
				dropdownCssClass: "bigdrop", // apply css that makes the dropdown taller
				required:true,
				escapeMarkup: function (m) { return m; } // we do not want to escape markup since we are displaying html in results
				});
				function format(item) { 
					 return item.shortName; 
				};
				
				function format1(item) {
					$('#_supplierInfoSelect').val(item.id);
					//$('#supplierInfoSelect').val(item.shortName);
					$("#supplierCode").text(item.supplierCode);
					$("#supplierCode").val(item.supplierCode);
					$("#fax").text(item.fax);
					$("#address").text(item.address);
					$("#zipCode").text(item.zipCode);
					$("#BankAccount").text(item.bankAccount);
					$("#bankNameSupplier").text(item.bankName);
					$("#contactName").text(item.bankName);
					var supplierId=item.id;
					contactsInfoSelect(supplierId);
					creatOrderCode();
					return item.shortName; 
				};
			}
	   function contactsInfoSelect(id){
		   $("#_contactName").select2({
				ajax: {
					url:ctx+'/business/order/findContactsValue?id='+id+'&tmp='+Math.random(),
					dataType: 'json',
					quietMillis: 100,
					data: function (term, page) { // page is the one-based page number tracked by Select2
						return {
							q: term, //search term
							page_limit: 10, // page size
							page: page, // page number
						};
					},
					results: function (data, page) {
						var more = (page * 10) < data.total; // whether or not there are more results available
						// notice we return the value of more so Select2 knows if more results can be loaded
						return {results: data, more: more};
					}
				},
				formatResult: format, // omitted for brevity, see the source of this page
				formatSelection: format1, // omitted for brevity, see the source of this page
				dropdownCssClass: "bigdrop", // apply css that makes the dropdown taller
				escapeMarkup: function (m) { return m; } // we do not want to escape markup since we are displaying html in results
				});
				function format(item) { return item.ContactName; };
				function format1(item) {
					$("#contactId").val(item.ContactName);
					$("#_contactTelPhone").text(item.ContactTelPhone);
					$("#_contactPhone").text(item.ContactPhone);
					return item.ContactName; 
				};
				
	   }
	   //创建编码
	   function creatOrderCode(){
		   var supplierCode=$('#supplierCode').val();
		   var url = ctx+"/business/order/creatOrderCode?supplierCode="+supplierCode+"&tmp="+Math.random();
		     $.post(url,function(data ,status){
	         	if(status=="success"){
	         		$('#orderCode').val(data);
	         		//$('#Code').text(data);
	             }
	         });
	   }
	   //查询供应商可用返点数
	   function getSpotNum(){
		   $("#spot_order tbody").html("");
		   var url = ctx + "/business/order/findSpotNum?&tmp="+Math.random();
		   var trStr="";
		   $.post(url,function(data,status){
			   if(status=="success"){
				   /*$('#nowSpotNum').text(data);*/
				   var spot = data.spotModel;
				   trStr+='<tbody>'
				   if(spot.length>0){
					   for(var i=0;i<spot.length;i++){
							trStr+='<tr>'+
							'<td class="sino_table_label"><input type="radio" name="spotRadio" supplierId="'+spot[i].id+'" id="supplierId'+i+'" value="'+i+'"/></td>'+
							'<td class="sino_table_label" id="supplierName_'+i+'" tdValue="'+spot[i].SupplierName+'">'+spot[i].SupplierName+'</td>'+
							'<td class="sino_table_label" id="returnAmount'+i+'" amount="'+spot[i].ReturnAmount+'">'+spot[i].ReturnAmount+'</td>'+
							'<td class="sino_table_label"><input type="text" name="spotNums" id="spotNums_'+i+'" value=""/></td>'+
							'</tr>';
						}
				   }else{
					   trStr+='<tr>'+
						'<td class="sino_table_label" colspan="5">无可用返点数</td>'+
						'</tr>';
				   }
				   trStr+='</tbody>'
			   }
			   $("#spot_order").find("thead").after(trStr);
		   })
		   
	   }

	function _back(){
		//刷新父级窗口
		window.opener.childColseRefresh();
		metaUtil.closeWindow();
		//关闭当前子窗口
	}
	function _add(){
		var radioValue = $('input:radio[name="spotChange"]:checked').val();
		if(radioValue==1){
			var spotValue=$("input[name='spotRadio']:checked").val();
			if(spotValue==''){
				UicDialog.Error("请选择返点厂商！");
				return
			}else{
				var num=$("input[name='spotRadio']:checked").val();
				var spot=$("#spotNums_"+num).val();
				var supplier=$("#supplierName_"+num).attr("tdValue");
				var mont=$("#returnAmount"+num).attr("amount");
				var supplierId=$("#supplierId"+num).attr("supplierId");
				if(spot==""||spot==null){
					UicDialog.Error("使用的返点数不能为空！");
					return;
				}
				var submount=metaUtil.accSub(mont,spot);
				if(submount<0){
					UicDialog.Error("使用的返点超过可用返点数！");
					return;
				}
				$("#spotNum").val(spot);
				$("#spotSupplier").val(supplier);
				$("#spotId").val(supplierId);
			}
		}
		if( $("#orderStatus").val()=='SH'){
			if($("#_is_submit_product").val() != 1){
				UicDialog.Error("产品数量填写有误,请重新填写！");
				return;
			}
			if($('#product_add').find('tbody').find('tr').length<1){
				UicDialog.Error("请添加产品！");
				return;
			}
			var partnerPV=$("#partnerPV").val();
			var profitsValue=$("#profitsValue").val();
			var customerPV=$("#customerPV").val();
			if(partnerPV=="" && profitsValue=="" && customerPV==""){
				UicDialog.Error("请填写至少一个利润值！");
				return;
			}
		}
		
		var div$ = $('#s2id_supplierInfoSelect');
		var input$ = div$.find("input").first();
		input$.attr('value',$('#supplierInfoSelect').val());
		
		var obj = new Object();
		 var tableGridData = "";
		 var productIds = "";
		 var mark="";
		 $("#product_add").find("tr").each(function(i, trEle){
			if(i != 0){
				var str = "{";
				var column0 = "";
				var column1 = "";
				var column2 = "";
				var column3 = "";
				var column4 = "";
				var column5 = "";
				var column6 = "";
				var column7 = "";
				var column8 = "";
				var column9 = "";
				var column10 = "";
				var column11 = "";
				var column12 = "";
				var tdValue = $(trEle).attr("trValue");
				str+="column6="+$(trEle).attr('tdValue')+","
				 if (productIds.indexOf($(trEle).attr('tdValue'))!=-1) {
				      //continue;
				 }else{
					 productIds+=$(trEle).attr('tdValue')+","
				 }
				$(trEle).find("td").each(function(j, tdEle){
					//+="column6="+$(tdEle).attr('tdValue')+","
					if(j == 0){
						str+="column0="+$(tdEle).attr('tdValue').trim()+","
					}
					if(j == 1){
						str+="column10="+$(tdEle).attr('tdValue')+","
					}
					if(j == 2){
						str+="column2="+$(tdEle).attr('tdValue')+","
					}
					if(j == 3){
						str+="column1="+$(tdEle).attr('tdValue')+","
					}
					if(j == 4){
						$(tdEle).find("input").each(function(m, iEle){
							str+="column3="+$(iEle).attr('value')+","
						});
					}
					if(j == 5){
						$(tdEle).find("input").each(function(m, iEle){
							str+="column4="+$(iEle).attr('value')+","
						});
					}
					if(j == 6){
						$(tdEle).find("input").each(function(m, iEle){
							str+="column5="+$(iEle).attr('value')+","
						});
					}
				/*	if(j == 7){
						$(tdEle).find("input").each(function(m, iEle){
							str+="column9="+$(iEle).attr('infinityid')+","
						});
					}
					if(j == 8){
						$(tdEle).find("input").each(function(m, iEle){
							str+="column11="+$(iEle).attr('value')+","
						});
					}
					if(j == 9){
						$(tdEle).find("input").each(function(m, iEle){
							str+="column12="+$(iEle).attr('value')+","
						});
					}*/
					if(j == 7){
						str+="column7="+$(tdEle).attr('tdValue')+","
					}
					if(j == 8){
						str+="column8="+$(tdEle).attr('tdValue')+"}"
						mark=$(tdEle).attr('tdValue');
					}
				});
				if(i == 1){
					tableGridData+=str;
				} else {
					tableGridData+=","+str;
				}
			} 
		});
		if(mark=='HT'){
			$('#_contract').val(productIds);
		}else{
			$('#_interPurchas').val(productIds);
		}
		 tableGridData+="";
			var objs = tableGridData.split("},");
			var strs = "";
			for(var i = 0;i < objs.length;i++){
				if(i != objs.length - 1){
					strs+=objs[i]+'}","';
				} else {
					strs+=objs[i];
				}
			}
    	 $('#_tableGridData').val('{"editGridName":["'+strs+'"]}');
	     var datas=$("#_sino_eoss_cuotomer_addform").serialize();
	     var options = {};
	     options.formId = "_sino_eoss_cuotomer_addform";
	     var url = ctx+'/business/order/doAddOrder?tmp='+Math.random();
	     if($.formSubmit.doHandler(options)){
	    	 
	    	//启动遮盖层
   	    	 $('#_progress1').show();
   	    	 $('#_progress2').show();
	    	
	    	 $.post(url,datas,
	 	            function(data,status){
	    		        var status=$("#orderStatus").val();
	 	            	if(data.result=="success"){
	 	            		if(status=='CG'){
	 	            			UicDialog.Success("保存订单草稿成功!",function(){
	 	            				_back();
	 	            			});
	 	            		}
	 	            		else{
	 	            			UicDialog.Success("订单提交成功!",function(){
	 	            				_back();
	 	            			});
	 	            		}
	 	                  }else{
	 	                	  if(status=='CG'){
	 	                		 UicDialog.Error("保存订单草稿失败!"+data.message);
	 	                	  }else{
	 	                		 UicDialog.Error("订单提交失败!"+data.message);
	 	                	  }
	 	                	  $('#_progress1').hide();
	 	                	  $('#_progress2').hide();
	 	                  }
	 	                },"json"); 
	     }
	}
	function selectContract(){
		if($('#_eoss_business_ordertypeId').val()!=4){
			var frameSrc = ctx+"/business/order/selectContractView";
			$('#dailogs1').on('show', function () {
				$('#dtitle').html("选择合同");
				$('#dialogbody').load(frameSrc,function(){
		    	  
				}); 
			       $("#dsave").unbind('click');
			       $('#dsave').click(function () {
			    	 $('#editForm').submit();
				   });
				});
		}else{
			var frameSrc = ctx+"/business/order/selectInterPurchasView";
			$('#dailogs1').on('show', function () {
				$('#dtitle').html("选择内部采购");
				$('#dialogbody').load(frameSrc,function(){
		    	  
				}); 
			       $("#dsave").unbind('click');
			       $('#dsave').click(function () {
			    	 $('#editForm').submit();
				   });
				});
		}
		    $('#dailogs1').on('hidden', function () {$('#dailogs1').unbind("show");});
			$('#dailogs1').modal({show:true});
			$('#dailogs1').off('show');
	}

	exports.init = function(){
		addOrderContract.doExecute('initDocument');  
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
	//强制保留2位小数，如：2，会在2后面补上00.即2.00  
	function changeTwoDecimal_f(x){
		var f_x = parseFloat(x);
		if (isNaN(f_x)){
			alert('请输入正确格式的数字！');
			return false;
		}
		f_x = Math.round(f_x*100)/100;
		var s_x = f_x.toString();
		var pos_decimal = s_x.indexOf('.');
		if (pos_decimal < 0){
			pos_decimal = s_x.length;
			s_x += '.';
		}
		while (s_x.length <= pos_decimal + 2){
			s_x += '0';
		}
		return s_x;
	} 
	
});
