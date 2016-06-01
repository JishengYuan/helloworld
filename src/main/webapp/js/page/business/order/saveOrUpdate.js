define(function(require, exports, module) {
	//var css = require('js/plugins/ztree/zTreeStyle.css');
	var $ = require("jquery");
	require("formSelect");
	require("formSubmit");
	require("formTree");

	require("bootstrap-datetimepicker");
	require("bootstrap");
	require("jqBootstrapValidation");
	
	var dataTable$ = require("dataTables");
	var uic$ = require("uic_Dropdown");
	var DT_bootstrap = require("DT_bootstrap");
	
	require("select2");
	require("select2_locale_zh-CN");
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
	 
	function loadgrid(){
		
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
 		 //返回
		 $("#no_back").bind('click',function(){
			 _back();
         });
		 //提交审批
		 $("#sp_Add").unbind('click').click(function () {
			 $("#isSubmit").val('SH');
			 _add();
		 })
		 //保存修改
		 $("#ok_Add").unbind('click').click(function () {
			 $("#isSubmit").val('CG');	
			 _add();
	       });
		 //查询添加合同
		 $("#selectContract").bind('click',function(){
			 selectContract();
	       });
		 //删除产品
		 $("#remove_product").unbind('click').click(function(){
			 _removeProducts();
			 getAmount();
		 });
		 //删除未勾选的产品
		 $("#remove_product_else").unbind('click').click(function(){
			 _removeProductsElse();
			 getAmount();
		 });
		
		 $(".unitPriceStyle").blur(function(){
			 var num = $(this).attr("num");
			 _toBlur(num);
     		});
		 $(".sumStyle").blur(function(){
			 var num = $(this).attr("num");
			 _toBlur2(num);
     		});
		 $(".quantityStyle").blur(function(){
			 var num = $(this).attr("num");
			 _findproduct(num);
			 _toBlur(num);
     		});
		 $("#checkall").click(function() {
             $('input[name="myCheckbox"]').attr("checked",this.checked);
         });
		 $("input:radio[name='spotChange']").unbind('click').click(function () {
 			var radioValue = $('input:radio[name="spotChange"]:checked').val();
 			if(radioValue == 0){
 				$('._is_hidden').hide();
 			} else {
 				$('._is_hidden').show();
 				var orderId=$('#_eoss_customer_id').val(); 
 				getSpotNum(orderId);
 			}
 		});
		 var numSpot = $('#spotNum').val();
		 if(+numSpot>0){
			 $('._is_hidden').show();
			 var orderId=$('#_eoss_customer_id').val(); 
				getSpotNum(orderId);
		 }
		 getInvoiceType();
		 getPurchaseType();
		 getOrderType();
		 getTime();
		 getPaymentMode();
		 //getOrderProfits();
		 //加载供应商类型下拉框
		 getSupplierType();
		// getServiceType();
		 //结算币种
		 getAccountCurrency();
		 getDeliveryAddress();
		 var typeId = $('#_supplierTypeId').val();
		 if(typeId != ""&&typeId != null){
			 supplierInfoSelect(typeId);
			 var name = $('#suName').val();
			 $('#select2-chosen-1').html(name);
		 }
		 var infocontacts = $('#supplierId').val();
		 if(infocontacts != ""&&infocontacts != null){
			 contactsInfoSelect(infocontacts)
			 var name = $('#coName').val();
			 $('#s2id__contactName').find('.select2-chosen').html(name);
		 }
		 
	}
    /**
     *  时间插件
     */
      function  getTime(object){
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
      
      
      //查询供应商可用返点数
	   function getSpotNum(orderId){
		   $("#spot_order tbody").html("");
		   var url = ctx + "/business/order/findSpotNum?orderId="+orderId+"&tmp="+Math.random();
		   var trStr="";
		   var spotAmount = $("#spotNum").val();
		   $.post(url,function(data,status){
			   if(status=="success"){
				   /*$('#nowSpotNum').text(data);*/
				   var spot = data.spotModel;
				   trStr+='<tbody>'
				   if(spot.length>0){
					   for(var i=0;i<spot.length;i++){
							trStr+='<tr>';
							if(data.spotId==i){
								trStr+='<td class="sino_table_label"><input type="radio" name="spotRadio" supplierId="'+spot[i].id+'" id="supplierId'+i+'" value="'+i+'" checked/></td>'+
								'<td class="sino_table_label" id="supplierName_'+i+'" tdValue="'+spot[i].SupplierName+'">'+spot[i].SupplierName+'</td>'+
								'<td class="sino_table_label" id="returnAmount'+i+'" amount="'+spot[i].ReturnAmount+'">'+spot[i].ReturnAmount+'</td>'+
								'<td class="sino_table_label"><input type="text" name="spotNums" id="spotNums_'+i+'" value="'+spotAmount+'"/></td>'+
								'</tr>';
							}else{
								trStr+='<td class="sino_table_label"><input type="radio" name="spotRadio" supplierId="'+spot[i].id+'" id="supplierId'+i+'" value="'+i+'"/></td>'+
								'<td class="sino_table_label" id="supplierName_'+i+'" tdValue="'+spot[i].SupplierName+'">'+spot[i].SupplierName+'</td>'+
								'<td class="sino_table_label" id="returnAmount'+i+'" amount="'+spot[i].ReturnAmount+'">'+spot[i].ReturnAmount+'</td>'+
								'<td class="sino_table_label"><input type="text" name="spotNums" id="spotNums_'+i+'" value=""/></td>'+
								'</tr>';
							}
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

      function _findproduct(num){
    	  var mark=$("#mark"+num).attr("tdValue");
    	  var id=$("#productId"+num).attr("tdValue");
    	  var pronum=$("#quantity"+num).attr("inputCount");
    	  var url = ctx+"/business/order/findproduct?id="+id+"&mark="+mark+"&pronum="+pronum+"&tmp="+Math.random();
		     $.post(url,function(data ,status){
	         	if(status=="success"){
	         		if($('#quantity'+num).val()>data){
	         			UicDialog.Error("产品数量填写有误,请重新填写！");
	         			$("#productNum"+num).val("1");
	         		}else{
	         			$("#productNum"+num).val("0");
	         		}
	             }
	         });
      }
      
      function _toBlur(num){
			var quantity = $('#quantity'+num).val();
			var unitPrice = $('#unitPrice'+num).val();
			var sum =quantity*unitPrice;
			$("#sub"+num).val(changeTwoDecimal_f(sum));
		    getAmount();
	}
      function _toBlur2(num){
    	  var sum=$('#sub'+num).val();
    	  var quantity = $('#quantity'+num).val();
    	  var price = sum/quantity;
		  $("#unitPrice"+num).val(changeTwoDecimal_f(price));
		    getAmount();
      }
      
     /* function getServiceType(){
    	 $("div[id*='inter_serviceType']").each(function(){
             var $fieldUserTypes = $(this);
             $fieldUserTypes.addClass("li_form");
 			 var optionUserTypes = {
 				inputValue : $(this).attr("value"),
 				writeType : 'show',
 				showLabel : false,
 				code : 'serviceType',
 				width : "130",
 				onSelect :function(){
 					var type= $(this).formSelect("getValue")[0];
 			}
 			};
 			$fieldUserTypes.formSelect(optionUserTypes);
         });   
			//var $fieldUserTypes = $("#inter_serviceType"+num);
			
	}*/
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
  		$fieldUserTypes.formSelect('setValue',$('#_business_deliveryAddressId').val());
  	}
      function getPaymentMode(){
  		var $fieldUserTypes = $("#_eoss_business_paymentMode");
  		$fieldUserTypes.addClass("li_form");
  		var optionUserTypes = {
  			writeType : 'show',
  			showLabel : false,
  			code : 'paymentMode',
  			width : "254",
  			onSelect :function(){
  				var type= $("#_eoss_business_paymentMode").formSelect("getValue")[0];
  				$('#_eoss_business_paymentModeId').val(type);
  		}
  		};
  		$fieldUserTypes.formSelect(optionUserTypes);
  		$fieldUserTypes.formSelect('setValue',$('#_eoss_business_paymentModeId').val());
  	}
     function getOrderProfits(){
    	 var $fieldUserTypes = $("#_eoss_orderProfits");
   		$fieldUserTypes.addClass("li_form");
   		var optionUserTypes = {
   			writeType : 'show',
   			showLabel : false,
   			code : 'orderProfits',
   			width : "254",
   			onSelect :function(){
   				var type= $("#_eoss_orderProfits").formSelect("getValue")[0];
   				$('#_orderProfits').val(type);
   		}
   		};
   		$fieldUserTypes.formSelect(optionUserTypes);
   		$fieldUserTypes.formSelect('setValue',$('#_orderProfits').val());
     }
	function getOrderType(){
		/*var $fieldUserTypes = $("#_eoss_business_ordertype");
		$fieldUserTypes.addClass("li_form");
		var optionUserTypes = {
			writeType : 'show',
			showLabel : false,
			code : 'orderType',
			width : "254",
			onSelect :function(){
				var type= $("#_eoss_business_ordertype").formSelect("getValue")[0];
				$('#_eoss_business_ordertypeId').val(type);
		}
		};
		$fieldUserTypes.formSelect(optionUserTypes);
		$fieldUserTypes.formSelect('setValue',$('#_eoss_business_ordertypeId').val());*/
		$.ajax({
			type : "GET",
			async : false,
			dataType : "json",
			url : ctx + "/sysDomain/findDomainValue?code=orderType&tmp="+ Math.random(),
			success : function(msg) {
				for(var i in msg){
					if(msg[i].id == $('#orderTypeId').val()){
						$('#ordertypeString').html(msg[i].name);
					}
				}
			}
		});
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
		$fieldUserTypes.formSelect('setValue',$('#_eoss_business_invoiceTypeId').val());
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
		$fieldUserTypes.formSelect('setValue',$('#_eoss_business_purchaseTypeId').val());
	}
	
	function getSupplierType(){
		var $fieldUserTypes= $("#_supplierType");
		$fieldUserTypes.addClass("li_form");
		var optionSupplierType = {
				writeType : 'show',
				showLabel : false,
				width:"254", //高度
				code:'supplierType',
				onSelect :function(node){
					var id = $("#_supplierType").formSelect("getValue")[0];
					$('#_supplierTypeId').val(id);
					supplierInfoSelect(id);
				}
		};
		$fieldUserTypes.formSelect(optionSupplierType);
		$fieldUserTypes.formSelect('setValue',$('#_supplierTypeId').val());
	}
	
	 function supplierInfoSelect(id){
		
			$("#supplierInfoSelect").select2({
				ajax: {
					url:ctx+'/business/order/findSupplierInfoValue?id='+id+'&tmp='+Math.random(),
					dataType: 'json',
					width: '180px',
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
				escapeMarkup: function (m) { return m; } // we do not want to escape markup since we are displaying html in results
				});
				function format(item) { 
					$("#supplierId").val(item.id); 
					return item.shortName; 
				};
				
				function format1(item) {
					$("#supplierId").val(item.id); 
					$("#supplierCode").text(item.supplierCode);
					$("#supplierCode").val(item.supplierCode);
					$("#fax").text(item.fax);
					$("#address").text(item.address);
					$("#zipCode").text(item.zipCode);
					$("#BankAccount").text(item.bankAccount);
					$("#bankNameSupplier").text(item.bankName);
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
				function format(item) {$("#contactId").val(item.id);  return item.ContactName; };
				function format1(item) {
					$("#_contactTelPhone").val(item.ContactTelPhone);
					$("#_contactPhone").val(item.ContactPhone);
					return item.ContactName; 
				};
	   }
	
	function _back(){
		/*//刷新父级窗口
		window.opener.childColseRefresh();
		//关闭当前子窗口
		window.close(); */
		window.opener.childColseRefresh();
		metaUtil.closeWindow();   
	}
	
	function _removeProducts(serialNum){
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
	
	
	function _add(){
		var strProduct="";
		$("input[name='productNum']").each(function(i,ele){
			if($(this).val()=="1"){
				strProduct+="第"+(i+1)+"行产品数量有误"+"\n";
			}
		});
		if(strProduct!=""){
			UicDialog.Error(strProduct);
			return;
		}
		 var obj = new Object();
		 var tableGridData = "";
		 var productIds = "";
		 var mark="";
		 var radioValue = $('input:radio[name="spotChange"]:checked').val();
			if(radioValue==1){
				var spotValue=$("input[name='spotRadio']:checked").val();
				if(spotValue=='undefind'){
					UicDialog.Error("请选择返点厂商！");
					return;
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
		 if( $("#isSubmit").val()=='SH'){
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
						/*if(j == 7){
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
    	 
	     var datas=$("#_sino_eoss_cuotomer_updateform").serialize();
	     var options = {};
	     options.formId = "_sino_eoss_cuotomer_updateform";
	     var url =  ctx+'/business/order/doSaveOrUpdate?tmp='+Math.random();
	     
	     if($.formSubmit.doHandler(options)){
		    	//启动遮盖层
	   	    	 $('#_progress1').show();
	   	    	 $('#_progress2').show();
	   	    	 $.post(url,datas,
	 	 	            function(data,status){
	   	    		        var status= $("#isSubmit").val();
	 	 	            	if(data.result=="success"){
	 	 	            		if(status=='CG'){
		 	            			UicDialog.Success("订单修改成功!",function(){
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
		 	                		 UicDialog.Error("订单修改失败!"+data.message);
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

	//得到产品总价
	function getAmount(){
		var sum=0;
	    $("[name='trName']").each(function(i,ele){
	    	var num=$(this).attr("num");
	    	if($("#sub"+num).val()!=null&&$("#sub"+num).val()!=""){
				sum=sum+Number($("#sub"+num).val());
			}
	    });
		$("#orderAmount").val(sum);
		var sumt=changeTwoDecimal_f(sum);
		var show_amount=fmoney(sumt,2);
		$("#tatol").text("￥"+show_amount);
	}

	exports.init = function(){
		loadgrid();  
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