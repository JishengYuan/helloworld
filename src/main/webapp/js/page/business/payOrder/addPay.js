define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap-datetimepicker");
	require("bootstrap");
	require("jqBootstrapValidation");
	require("select2");
	require("select2_locale_zh-CN");
	require("formSubmit");
	
	
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	require("confirm_dialog");
	require("uic/message_dialog");
	
	var addOrderContract={
			config: {
				//namespace:'/business/supplierm/supplierInfo'
	        },
	        methods :{
	        	initDocument:function(){
	        		 $(".selectCurrency").unbind('click').click(function () {
	        			 $(".nav-pills").find("li").each(function(i,ele){
	        				 $(ele).removeClass("active");
	        			 });
						 $(this).parent().addClass("active");
						 $("#selectCurrency").val(this.id);
						 if(this.id=="cny"){
							 $(".chgcurrency").empty();
							 $(".chgcurrency").html("(人民币)");
							 $("#currentRate").css("display","none");
						 }
						 if(this.id=="usd"){
							 $(".chgcurrency").empty();
							 $(".chgcurrency").html("(美元)");
							 $("#currentRate").css("display","");
						 }
						 
				       });
						/*
						 * 绑定触发事件
						 */
						 $("#no_back").bind('click',function(){
							 _back();
				         });
						//直接提交审批
						 $("#sp_Add").unbind('click').click(function () {
							 _add();
					       });
						 $("#selectOrder").bind('click',function(){
							 selectOrder();
					       });
						 //加载发票类型下拉框
						// getInvoiceType();
						//加载税率下拉框
						 getTaxType();
						 //加载付款方式下拉框
						 getPaymentMode();
						 getTime();
		        		 //加载供应商类型下拉框
		        		 getSupplierType();
		        		 getCoursesType();
		        		 var suId='';
		        		 supplierInfoSelect(suId);
						 
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
	function getCoursesType(){
		var $fieldUserTypes = $("#_coursesType");
		$fieldUserTypes.addClass("li_form");
		var optionUserTypes = {
			writeType : 'show',
			showLabel : false,
			code : 'coursesType',
			width : "254",
			onSelect :function(){
				var type= $("#_coursesType").formSelect("getValue")[0];
				$('#_coursesTypeId').val(type);
		}
		};
		$fieldUserTypes.formSelect(optionUserTypes);
		if($('#_coursesTypeId').val() == null||$('#_coursesTypeId').val() == ""){
			var roomId = "";
    		$fieldUserTypes.find("ul li").each(function(i,ele){
    			if(i == 5){
    				var obj = $(ele);
					roomId = obj.attr("infinityid");
    			}
			});
    		$fieldUserTypes.formSelect('setValue',roomId);
    		$('#_coursesTypeId').val(roomId);
		} else {
			$fieldUserTypes.formSelect('setValue',$('#_coursesTypeId').val());
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
	function getTaxType(){
		var $fieldUserTypes = $("#_eoss_taxType");
		$fieldUserTypes.addClass("li_form");
		var optionUserTypes = {
			writeType : 'show',
			showLabel : false,
			code : 'taxType',
			width : "254",
			onSelect :function(){
				var type= $("#_eoss_taxType").formSelect("getValue")[0];
				$('#_eoss_business_taxTypeIds').val(type);
		}
		};
		$fieldUserTypes.formSelect(optionUserTypes);
		if($('#_eoss_business_taxTypeIds').val() == null||$('#_eoss_business_taxTypeIds').val() == ""){
			var roomId = "";
    		$fieldUserTypes.find("ul li").each(function(i,ele){
    			if(i == 3){
    				var obj = $(ele);
					roomId = obj.attr("infinityid");
    			}
			});
    		$fieldUserTypes.formSelect('setValue',roomId);
    		$('#_eoss_business_taxTypeIds').val(roomId);
		} else {
			$fieldUserTypes.formSelect('setValue',$('#_eoss_business_taxTypeIds').val());
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
    			if(i == 1){
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
				escapeMarkup: function (m) { return m; } // we do not want to escape markup since we are displaying html in results
				});
				function format(item) { 
					 return item.shortName; 
				};
				
				function format1(item) {
					$('#_supplierInfoSelect').val(item.id);
					$("#BankAccount").text(item.bankAccount);
					$("#bankNameSupplier").text(item.bankName);
					var supplierId=item.id;
					return item.shortName; 
				};
			}

	function _back(){
		//刷新父级窗口
		window.opener.childColseRefresh();
		metaUtil.closeWindow();
		//关闭当前子窗口
	}
	function _add(){
		var applyName = $("#payName").html();
		$("#payApplyName").val(applyName);
		
		if($("#payAmount").val()==""||$("#payAmount").val()==0){
			UicDialog.Error("请填写付款金额！");
			return;
		}
		var obj = new Object();
		 var tableGridData = "";
		 var orderId = "";
		 var mark="";
		 $("#pay_order").find("tr").each(function(i, trEle){
			if(i != 0){
				var str = "{";
				var column0 = "";
				var column1 = "";
				var column2 = "";
				var column3 = "";
				var column4 = "";
				var column5 = "";
				var column6 = "";
				var tdValue = $(trEle).attr("trValue");
				$(trEle).find("td").each(function(j, tdEle){
					if(j == 0){
						str+="column0="+$(tdEle).attr('tdValue')+","
					}
					if(j == 1){
						str+="column1="+$(tdEle).attr('tdValue')+","
					}
					if(j == 2){
						str+="column2="+$(tdEle).attr('tdValue')+","
					}
					if(j == 3){
						str+="column3="+$(tdEle).attr('tdValue')+","
					}
					if(j == 4){
						$(tdEle).find("input").each(function(m, iEle){
							str+="column4="+$(iEle).attr('value')+","
						});
					}
					if(j == 5){
						$(tdEle).find("input").each(function(m, iEle){
							str+="column5="+$(iEle).attr('value')+","
						});
					}
					if(j == 6){
						str+="column6="+$(tdEle).attr('tdValue')+"}"
					}
					
				});
				if(i == 1){
					tableGridData+=str;
				} else {
					tableGridData+=","+str;
				}
			} 
		});
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
	     var url = ctx+'/business/payOrder/doAddPay?tmp='+Math.random();
	     if($.formSubmit.doHandler(options)){
	    	 
	    	//启动遮盖层
   	    	 $('#_progress1').show();
   	    	 $('#_progress2').show();
	    	
	    	 $.post(url,datas,
	 	            function(data,status){
	 	            	if(data.result=="success"){
	 	            			UicDialog.Success("付款申请提交成功!",function(){
	 	            				_back();
	 	            			});
	 	                  }else{
	 	                		 UicDialog.Error("付款申请提交失败!"+data.message);
	 	                	  $('#_progress1').hide();
	 	                	  $('#_progress2').hide();
	 	                  }
	 	                },"json"); 
	     }
	}
	function selectOrder(){
		var supplierId=$("#_supplierInfoSelect").val();
		if(supplierId==null||supplierId==""){
			UicDialog.Error("请先选择供应商！");
			return;
		}
		var subject = $("#_coursesTypeId").val();
			var frameSrc = ctx+"/business/payOrder/selectOrderView?supplierId="+supplierId+"&subject="+subject;
			$('#dailogs1').on('show', function () {
				$('#dtitle').html("选择订单");
				$('#dialogbody').load(frameSrc,function(){
		    	  
				}); 
			       $("#dsave").unbind('click');
			       $('#dsave').click(function () {
			    	 $('#editForm').submit();
				   });
				});
		    $('#dailogs1').on('hidden', function () {$('#dailogs1').unbind("show");});
			$('#dailogs1').modal({show:true});
			$('#dailogs1').off('show');
	}

	exports.init = function(){
		addOrderContract.doExecute('initDocument');  
	}
});
