define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("jqBootstrapValidation");
	require("formSubmit");
	require("confirm_dialog");
	require("uic/message_dialog");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	
	function loadgrid(){
		
		/*
		 * 绑定触发事件
		 */
 		 //返回
		 $("#no_back").bind('click',function(){
			 _back();
         });
		 //从新提交
		 $("#sp_Add").bind('click',function(){
			 $("#sp_Add").unbind('click');
			 $("#isSubmit").val('CXTJ');
			 _update();
		 })
		 $("#no_Add").bind('click',function(){
			 $("#no_Add").unbind('click');
			 $("#isSubmit").val('END');
			 _end();
		 })
		 
		 //设备添加
		 $("#products_add").bind('click').click(function(){
				_addProducts();
		 });
		 //设备修改
		 $(".update_product").unbind('click').click(function(){
			 _updateProducts($(this).attr("serial_num"));
		 });
		 //设备删除
		 $(".remove_product").unbind('click').click(function(){
			 _removeProducts($(this).attr("serial_num"));
		 });
		 getTime();
	}
    /**
     *  时间插件
     */
    function  getTime(){
    	$('.date').datetimepicker({ 
 	        pickTime: false
 	      }); 
    }
    function _end(){
  	  var datas=$("#_sino_eoss_cuotomer_addform").serialize();
	      var url =  ctx+'/business/interPurchas/endInterPurchas?tmp='+Math.random();
	      //启动遮盖层
	    	 $('#_progress1').show();
	    	 $('#_progress2').show();
	     $.post(url,datas,
	            function(data,status){
	    	 		
	            	if(status=="success"){
	                	  UicDialog.Success("成功放弃内部采购提交!",function(){
	                		  _back(); 
	                	  });
	                  }else{
	                  	  UicDialog.Error("内部采购放弃失败！",function(){
	                  		  _back();
	                  	  });
	                  }
	                });
    }
	function _back(){
		//刷新父级窗口
		window.opener.reload();
		//关闭当前子窗口
		metaUtil.closeWindow();  
	}
	

	function _update(){
	    /* var datas=$("#_sino_eoss_cuotomer_addform").serialize();
	     var url = ctx+'/business/interPurchas/doSaveOrUpdate?tmp='+Math.random();
	     $.post(url,datas,
	            function(data,status){
	            	if(status="success"){
	                	  UicDialog.Success("修改数据成功!",function(){
	                	  _back();
	                	  });
	                  }else{
	                  	  UicDialog.Error("修改数据失败！");
	                  	  _back();
	                  }
	                });*/
	     var datas=$("#_sino_eoss_cuotomer_addform").serialize();
	     var url = ctx+'/business/interPurchas/doReviseNote?tmp='+Math.random();
	     var options = {};
	     options.formId = "_sino_eoss_cuotomer_addform";
	     $.post(url,datas,
		            function(data,status){
		            	if(status=="success"){
		                	  UicDialog.Success("重新提交成功!",function(){
		                	  _back();
		                	  });
		                  }else{
		                  	  UicDialog.Error("重新提交失败！",function(){
		                  		  _back();
		                  	  });
		                  }
		                });

	}


	//----------------------------设备添加---------------------------------------
	function _addProducts(){
		var frameSrc = ctx+"/business/interPurchas/addOrUpdateProductsView"; 
		$('#dailogs1').on('show', function () {
			$('#dtitle').html("添加设备");
		     $('#dialogbody').load(frameSrc); 
			     $("#dsave").unbind('click');
			     $('#dsave').click(function () {
			    	 var _contractProductId_=$("#_contract_productId").val();
			    	 var _productType_=$("#_productType").val();
			    	 var _productTypeName_=$("#_sino_productTypeName").val();
			    	 var _productPartner_=$("#_partnerId").val();
			    	 var _productPartnerName_=$("#_sino_productPartnerName").val();
			    	 var _productNo_=$("#_productModelId").val();
			    	 var _productNoName_=$("#_sino_productModelName").val();
			    	 var _quantity_=$("#_products_add_count").val();
			    	 var _serial_num=$("#products_table tr").length;
			    	 if(_productType_ == ""||_productType_ == '0'||_productPartner_ == ""||_productPartner_ == '0'||_productNo_ == ""||_productNo_ == '0'){
			    		 $('#_product_alertMsg').empty();
			 			 $('#_product_alertMsg').append('<div class="alert alert-error"><strong>错误：</strong>产品数据不完整或信息填写不完整，请到基础信息中补充完整或填写完所有内容！<button type="button" class="close _cancleHandler" data-dismiss="alert">&times;</button></div>');
			 			 $(".alert").delay(2000).hide(0);
			 			 $("._cancleHandler").click(function() {
			 				$(".alert").hide();
			 			 });
			    		 return;
			    	 }
			    	 var tr=$("<tr style='text-algin:center'id='tr_"+_serial_num+"'>" +
								"<td>"+_serial_num+"<input id='_contract_product_id_"+_serial_num+"' name='contractProductIds' type='hidden' value='"+_contractProductId_+"'></td>" +
								"<td><span>"+_productTypeName_+"</span><input id='_product_Type_"+_serial_num+"' name='productTypes' type='hidden' value='"+_productType_+"'><input id='_product_Type_Name_"+_serial_num+"' name='productTypeNames' type='hidden' value='"+_productTypeName_+"'></td>" +
								"<td><span>"+_productPartnerName_+"</span><input id='_product_Partner_"+_serial_num+"' name='productPartners' type='hidden' value='"+_productPartner_+"'><input id='_product_Partner_Name_"+_serial_num+"' name='productPartnerNames' type='hidden' value='"+_productPartnerName_+"'></td>" +
								"<td><span>"+_productNoName_+"</span><input id='_product_No_"+_serial_num+"' name='productNos' type='hidden' value='"+_productNo_+"'><input id='_product_Name_"+_serial_num+"' name='productNames' type='hidden' value='"+_productNoName_+"'></td>" +
								"<td><span>"+_quantity_+"</span><input id='_product_quantity_"+_serial_num+"' name='quantitys' type='hidden' value='"+_quantity_+"'></td>" +
								"<td style='text-algin:center'><a serial_num='"+_serial_num+"'id='update_product_"+_serial_num+"'  class='btn btn-primary update_product'><i class='icon-pencil'></i>修改</a>&nbsp;&nbsp;&nbsp;<a serial_num='"+_serial_num+"' id='remove_product_"+_serial_num+"'  class='btn btn-danger remove_product'><i class='icon-remove'></i>删除</a></td>" +
								"</tr>");  
					$("#products_table").append(tr);
					 //设备修改
					 $(".update_product").unbind('click').click(function(){
						 _updateProducts($(this).attr("serial_num"));
					 });
					 //设备删除
					 $(".remove_product").unbind('click').click(function(){
						 _removeProducts($(this).attr("serial_num"));
					 });
					 $('#dailogs1').modal('hide');
			     });
			
		 });
			    $('#dailogs1').on('hidden', function () {$('#dailogs1').unbind("show");});
				$('#dailogs1').modal({show:true});
				$('#dailogs1').off('show');
	}	
	//----------------------------设备修改---------------------------------------
	function _updateProducts(serialNum){
		var oldOptions={};
		oldOptions.id=$("#_contract_product_id_"+serialNum).val();
		oldOptions.productType=$("#_product_Type_"+serialNum).val();
		oldOptions.productTypeName=$("#_product_Type_Name_"+serialNum).val();
		oldOptions.productPartner=$("#_product_Partner_"+serialNum).val();
		oldOptions.productPartnerName=$("#_product_Partner_Name_"+serialNum).val();
		oldOptions.productNo=$("#_product_No_"+serialNum).val();
		oldOptions.productName=$("#_product_Name_"+serialNum).val();
		oldOptions.quantity=$("#_product_quantity_"+serialNum).val();
		var frameSrc = ctx+"/business/interPurchas/addOrUpdateProductsView";
		var data=oldOptions;
		$('#dailogs1').on('show', function () {
			$('#dtitle').html("修改设备");
		     $('#dialogbody').load(frameSrc,data); 
			     $("#dsave").unbind('click');
			     $('#dsave').click(function () {
			    	 var newOptions={};
			    	 newOptions.id=$("#_contract_productId").val();
			    	 newOptions.productType=$("#_productType").val();
			    	 newOptions.productTypeName=$("#_sino_productTypeName").val();
			    	 newOptions.productPartner=$("#_partnerId").val();
			    	 newOptions.productPartnerName=$("#_sino_productPartnerName").val();
			    	 newOptions.productNo=$("#_productModelId").val();
			    	 newOptions.productNoName=$("#_sino_productModelName").val();
			    	 newOptions.quantity=$("#_products_add_count").val();
			    	 newOptions.serial_num=serialNum;
			    	 if(newOptions.productType == ""||newOptions.productType == '0'||newOptions.productPartner == ""||newOptions.productPartner == '0'||newOptions.productNo == ""||newOptions.productNo == '0'){
			    		 $('#_product_alertMsg').empty();
			 			 $('#_product_alertMsg').append('<div class="alert alert-error"><strong>错误：</strong>产品数据不完整或信息填写不完整，请到基础信息中补充完整或填写完所有内容！<button type="button" class="close _cancleHandler" data-dismiss="alert">&times;</button></div>');
			 			 $(".alert").delay(2000).hide(0);
			 			 $("._cancleHandler").click(function() {
			 				$(".alert").hide();
			 			 });
			    		 return;
			    	 }
			    	 var tr=$("<tr style='text-algin:center' id='tr_"+newOptions.serial_num+"'>" +
								"<td>"+newOptions.serial_num+"<input id='_contract_product_id_"+newOptions.serial_num+"' name='contractProductIds' type='hidden' value='"+newOptions.id+"'></td>" +
								"<td><span>"+newOptions.productTypeName+"</span><input id='_product_Type_"+newOptions.serial_num+"' name='productTypes' type='hidden' value='"+newOptions.productType+"'><input id='_product_Type_Name_"+newOptions.serial_num+"' name='productTypeNames' type='hidden' value='"+newOptions.productTypeName+"'></td>" +
								"<td><span>"+newOptions.productPartnerName+"</span><input id='_product_Partner_"+newOptions.serial_num+"' name='productPartners' type='hidden' value='"+newOptions.productPartner+"'><input id='_product_Partner_Name_"+newOptions.serial_num+"' name='productPartnerNames' type='hidden' value='"+newOptions.productPartnerName+"'></td>" +
								"<td><span>"+newOptions.productNoName+"</span><input id='_product_No_"+newOptions.serial_num+"' name='productNos' type='hidden' value='"+newOptions.productNo+"'><input id='_product_Name_"+newOptions.serial_num+"' name='productNames' type='hidden' value='"+newOptions.productNoName+"'></td>" +
								"<td><span>"+newOptions.quantity+"</span><input id='_product_quantity_"+newOptions.serial_num+"' name='quantitys' type='hidden' value='"+newOptions.quantity+"'></td>" +
								"<td style='text-algin:center'><a serial_num='"+newOptions.serial_num+"' id='update_product_"+newOptions.serial_num+"'  class='btn btn-primary update_product'><i class='icon-pencil'></i>修改</a>&nbsp;&nbsp;&nbsp;<a serial_num='"+newOptions.serial_num+"' id='remove_product_"+newOptions.serial_num+"'  class='btn btn-danger remove_product'><i class='icon-remove'></i>删除</a></td>" +
								"</tr>");
			    	 var downer=parseInt(serialNum)+1;
			    	 if($("#products_table tr").length > downer){
			    		 $("#tr_"+downer).before(tr);
			    	 }else {
			    		 $("#products_table").append(tr);
			    	 }
			    	 $("#tr_"+serialNum).remove();
					 //设备修改
					 $(".update_product").unbind('click').click(function(){
						 _updateProducts($(this).attr("serial_num"));
					 });
					 //设备删除
					 $(".remove_product").unbind('click').click(function(){
						 _removeProducts($(this).attr("serial_num"));
					 });
					 $('#dailogs1').modal('hide');
			     });
			
		 });
			    $('#dailogs1').on('hidden', function () {$('#dailogs1').unbind("show");});
				$('#dailogs1').modal({show:true});
				$('#dailogs1').off('show');
	}
	//----------------------------设备修改---------------------------------------
	function _removeProducts(serialNum){
		UicDialog.Confirm("确认删除该条设备吗？",function () {$("#tr_"+serialNum).remove();});
	}
	//-------------------------------------------------------------------
	exports.init = function(){
		loadgrid();  
	}
});