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
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	require("select2");
	require("select2_locale_zh-CN");
	require("uic/message_dialog");
/*	//------------------加载DataTable-----------Start--------------
	var Map = require("map");
	require("confirm_dialog");
	require("uic/message_dialog");
	gridStore = new Map();//
	gridTypeStore=new Map();//表格行列类型
	gridFieldStore = new Map();
	require("json2");
	require("js/common/form/data_grid");
	//------------------加载DataTable-------------End------------
*/	
	//表格列数
	 var lentr=0;
	
	function loadgrid(){
		/*
		 * 绑定触发事件
		 */
 		 //返回
		 $("#no_back").bind('click',function(){
			 _back();
         });
		 //从新提交
		 $("#_sino_eoss_pay_reSubmit").unbind('click').click(function () {
			 _add();
		 })
		 $("#_sino_eoss_pay_end").unbind('click').click(function () {
			 _end();
		 })
		 //查询添加订单
		 $("#selectOrder").unbind('click').click(function () {
			 selectOrder();
	       });
		 //删除产品
		 $("._remove_product").unbind('click').click(function(){
   			 _removeProducts($(this).attr("num"));
			 });
		 getAmount();
		
		 $(".selectCurrency").unbind('click').click(function () {
			 $(".nav-pills").find("li").each(function(i,ele){
				 $(ele).removeClass("active");
			 });
			 $(this).parent().addClass("active");
			 $("#selectCurrency").val(this.id);
			 if(this.id=="cny"){
				 $(".chgcurrency").empty();
				 $(".chgcurrency").html("(人民币)");
			 }
			 if(this.id=="usd"){
				 $(".chgcurrency").empty();
				 $(".chgcurrency").html("(美元)");
			 }
	       });
		 
		 var currency=$("#currency").val();
		 if(currency=='usd'){
			 $(".nav-pills").find("li").each(function(i,ele){
				 $(ele).removeClass("active");
			 });
			 $("#usd").parent().addClass("active");
			 $("#selectCurrency").val('usd');
			 $(".chgcurrency").empty();
			 $(".chgcurrency").html("(美元)");
		 }else{
			 $(".nav-pills").find("li").each(function(i,ele){
				 $(ele).removeClass("active");
			 });
			 $("#cny").parent().addClass("active");
			 $("#selectCurrency").val('cny');
			 $(".chgcurrency").empty();
			 $(".chgcurrency").html("(人民币)");
		 }
		 
		 $(".amount").blur(function(){
 				var num = $(this).attr("num");
 				var payAmount = $("#payAmount"+num).attr("tdValue");
				var amount = $("#amount"+num).val();
				var num=Number(payAmount-amount);
				if(num<0){
					UicDialog.Error("付款金额超过可付金额！");
						return;
				}
 				getAmount();
 			});
		 //getInvoiceType();
		 getTime();
		 getCoursesType();
		 getTaxType();
		 //加载供应商类型下拉框
		 getSupplierType();
		 var typeId = $('#_supplierTypeId').val();
		 if(typeId != ""&&typeId != null){
			 supplierInfoSelect(typeId);
			 var name = $('#suName').val();
			 $('#select2-chosen-1').html(name);
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

      
      function _end(){
    	  var datas=$("#_sino_eoss_cuotomer_addform").serialize();
 	      var url =  ctx+'/business/payOrder/endpay?tmp='+Math.random();
 	     //启动遮盖层
	    	 $('#_progress1').show();
	    	 $('#_progress2').show();
 	     $.post(url,datas,
 	            function(data,status){
 	            	if(status=="success"){
 	                	  UicDialog.Success("付款申请放弃提交成功!",function(){
 	                		  _back(); 
 	                	  });
 	                  }else{
 	                  	  UicDialog.Error("付款申请放弃提交失败！",function(){
 	                  		  _back();
 	                  	  });
 	                  	  
 	                  }
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
      			if(i == 1){
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
		$fieldUserTypes.formSelect('setValue',$('#_eoss_business_invoiceTypeId').val());
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
				function format(item) { $("#supplierId").val(item.id); return item.shortName; };
				
				function format1(item) {
					$("#supplierCode").text(item.supplierCode);
					$("#BankAccount").text(item.bankAccount);
					$("#bankNameSupplier").text(item.bankName);
					$("#_supplierInfoSelect").val(item.id); 
					var supplierId=item.id;
					return item.shortName; 
				};
			}

		function _back(){
			//刷新父级窗口
			window.opener.reload();
			metaUtil.closeWindow();  
		}
		
	function _removeProducts(serialNum){
 		UicDialog.Confirm("确认删除该条设备吗？",function () {
 			$("#tr_"+serialNum).remove();
 			getAmount();
 		});
    }
	
	//得到产品总价
	function getAmount(){
		var sum=0;
		    var len=$("#pay_order tr").length;
		    
		    if(lentr<len){
				 lentr=len;
			 }
			for(var i = 1; i < lentr; i++){
				if($("#amount"+i).val()!=null&&$("#amount"+i).val()!=""){
					sum+=Number($("#amount"+i).val());
				}
			}
				$("#tatol").text("￥"+fmoney(sum,2));
			$("#payAmount").val(sum);
	}
	
	function _add(){
		 var obj = new Object();
		 var tableGridData = "";
		 var productIds = "";
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
							str+="column6="+$(tdEle).attr('tdValue')+","
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
	     var url =  ctx+'/business/payOrder/doUpdateOrder?tmp='+Math.random();
	     if($.formSubmit.doHandler(options)){
		    	//启动遮盖层
	   	    	 $('#_progress1').show();
	   	    	 $('#_progress2').show();
		    	 $.post(url,datas, function(data,status){
		 	            	if(data.result=="success"){
		 	                	  UicDialog.Success("重新提交成功!",function(){
		 	                	  _back();
		 	                	  });
		 	                  }else{
		 	                  	  UicDialog.Error("重新提交失败！",function(){
		 	                  	  });
		 	                  	 $('#_progress1').hide();
		 	                	 $('#_progress2').hide();
		 	                  }
		 	                }); 
		     }
	}
	
	//金额格式化
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
	exports.init = function(){
		loadgrid();  
	}
});
