define(function(require, exports, module) {
	var $ = require("jquery");
	var Map = require("map");
	require("formSelect");
	require("formTree");
	require("bootstrap");

	require("js/plugins/uic/components/form/formPartnerInfo");
	require("js/plugins/meta/js/metaDropdowmSelect");
	exports.init = function() {
		load();
		if($("#startTime").val() != ""||$("#endTime").val() != ""){
			$("#_server_time1").attr("checked","checked");
			$("#_server_time0").attr("checked",false);
			$("._service_time_tr").show();
		}
	};

	function load() {
		getPartnerInfo("");
		//类型
		$("#_sino_product_type").formTree({	
			inputName : '_sino_product_type',
			inputValue : $('#_sino_productTypeName').val(),
			Checkbox : false,
			animate : true,
			searchTree : true,
			required : true,
			asyncUrl:ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),
			tree_url : ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),// 顶层
			search_url : ctx+"/business/projectm/productType/getTree",// 搜索
			url : '',
			asyncParam : ["id"],
			onSelect:function(node){
				$('#_partnerId').val('');
//				$('#_brandCode').val('');
//				$('#_productLine').val('');
				getPartnerInfo(node.id);
				getProductModel('',"");
				$('#_productType').val(node.id);
				$('#_sino_productTypeName').val(node.text);
			},
			async : true
		});
		//因为是添加所以直接根据产品系列加载选项即可
		var productModelUrl='';
		//因为是修改所以直接根据产品系列加载选项即可
		if($('#_partnerId').val() != ""&&$('#_partnerId').val() != null){
			productModelUrl=ctx+'/business/projectm/productModel/getProductModelList?partnerId='+$('#_partnerId').val()+"&tmp="+Math.random();
		}
		/*
		 * 绑定触发事件
		 */
		 $("#_product_add_unitPrice").blur(function(){
			 _toSubTotalPrice();
         });
		 $("#_products_add_count").blur(function(){
			 _toSubTotalPrice();
         });
		 if($("#_productType").val() != "0"&&$("#_productType").val() != ""){
			 getPartnerInfo($("#_productType").val());
		 }
		 $('#startTime_div').datetimepicker({
	    	pickTime: false
	    });
		 $('#endTime_div').datetimepicker({
	    	pickTime: false
	    });
		 
		 $("input[name='_server_time']").unbind('click').click(function(){
 			var val = $("input[name='_server_time']:checked").attr("value");
 			if(val == '0'){
 				$("#startTime").val("");
 				$("#endTime").val("");
 				$("._service_time_tr").hide();
 			} else {
 				var salesStartTime = $("#serviceStartDate_input").val(); 
 				var salesEndTime = $("#serviceEndDate_input").val();
 				$("#startTime").val(salesStartTime);
 				$("#endTime").val(salesEndTime);
 				$("._service_time_tr").show();
 			}
 		});
		 getProductModel($('#_productModelId').val());
	}
	
	function getPartnerInfo(productTypeId){
		
		$("#_sino_partner_div").empty();
		var $fieldStaff = $("#_sino_partner_div");
		var optionss = {
			showLabel : false,
			radioStructure : true,
			selectUser : false,
			onClickSearchFun:function(id,name){
				var typeId = $('#_productType').val();
				var str = typeId+","+id;
				getProductModel("",id);
				$('#_partnerId').val(id);
				$('#_sino_productPartnerName').val(name);
			},
			processFun:function(id,name){
				var typeId = $('#_productType').val();
				var str = typeId+","+id;
				getProductModel("",id);
				$('#_partnerId').val(id);
				$('#_sino_productPartnerName').val(name);
			},
			submitFunP:function(id,name){
				var typeId = $('#_productType').val();
				var str = typeId+","+id;
				getProductModel("",id);
				$('#_partnerId').val(id);
				$('#_sino_productPartnerName').val(name);
			},
			deleteFun:function(){
				$('#_partnerId').val("");
				$('#_sino_productPartnerName').val("");
				getProductModel("","");
			},
			width : "281"
		}
		optionss.addparams = [ {
			name : "code",
			value : "root"
		} ];
		var partnerId = $("#_partnerId").attr("value");
		if(partnerId == "0"){
			partnerId = "";
		}
		optionss.resIds = partnerId;
		optionss.inputValue = $("#_sino_productPartnerName").attr("value");
		$fieldStaff.formPartnerInfo(optionss);
		
		/*$("#_sino_partner_div").empty();
		$("#_sino_partner_div").append('<input id="_sino_partner" type="text" class="ultra-select-input3" data="0" data-content="请选择厂商" required />');
		//加载厂商
		$(".ultra-select-input3").uic_Dropdown({
			height:"auto",// 宽度
			title: "厂商",
			selecttitle:"从下列选择", //标题
			url:ctx+"/business/projectm/partnerInfo/getAll?productTypeId="+productTypeId,	 //型号分组数据
			checkbox:false,
			branchtype:true,
			search:false,
			width:'280px',
			onSelect:function(id,value){
				$('#_brandCode').val('');
				$('#_productLine').val('');
				getProductModel(ctx+'/business/projectm/productModel/getProductModelList?partnerId='+id+"&tmp="+Math.random());
				$('#_partnerId').val(id);
				$('#_sino_productPartnerName').val(value);
			}
		});*/
	}
	function getProductModel(id,str){
		if(str == null){
			str = "";
		}
		//型号
		$("#_sino_product_model").empty();
		$("#_sino_product_model").metaDropdownSelect({
		 searchUrl:ctx+"/business/projectm/productModel/getProductInfosByName?partnerId="+str,
		 url:ctx+"/business/projectm/productModel/getProductoModelById",
		 inputShowValueId:id,
		 required:true,
		 placeholder:"请输入要搜索的型号,按回车键",
		 width:"210",
		 height:"20",
		 onSelect:function(id,obj){
				var customerInfoCode=$(obj).attr("datacode");
				$('#_productModelId').val(id);
				$('#_sino_productModelName').val($(obj).text());
				
				var url = ctx+"/business/projectm/productModel/getParentModelById?id="+id;
				$.ajax({
					url:url,
					async : false,
					dataType : "json", 
					success : function(result){
						$('#_productType').val(result.productType);
						$('#_sino_productTypeName').val(result.typeName);
						$('#_partnerId').val(result.partnerId);
						$('#_sino_productPartnerName').val(result.partnerFullName);
						
						$("#_sino_product_type").empty();
						$("#_sino_product_type").formTree({
							inputName : '_sino_product_type',
							inputValue : $('#_sino_productTypeName').val(),
							Checkbox : false,
							animate : true,
							searchTree : true,
							required : true,
							asyncUrl:ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),
							tree_url : ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),// 顶层
							search_url : ctx+"/business/projectm/productType/getTree",// 搜索
							url : '',
							asyncParam : ["id"],
							onSelect:function(node){
								$('#_partnerId').val('');
//								$('#_brandCode').val('');
//								$('#_productLine').val('');
								getPartnerInfo(node.id);
								getProductModel('',"");
								$('#_productType').val(node.id);
								$('#_sino_productTypeName').val(node.text);
							},
							async : true
						});
						getPartnerInfo(result.productType);
						
				    }  
				});
		 }
		 });
//		$('#_sino_product_model').empty();
//		var $fieldProductModel = $("#_sino_product_model");
//		$fieldProductModel.addClass("li_form");
//		var optionProductModel = {
//			inputName : "productModel",
//			writeType : 'show',
//			width:"280", //高度
//			showLabel : false,
//			url : url,
//			inputChange : true,
//			onSelect :function(){
//				var str = $("#_sino_product_model").formSelect("getValue")+"";
//				var idAndName = str.split(",");
//				$('#_productModelId').val(idAndName[0]);
//				$('#_sino_productModelName').val(idAndName[1]);
//			}
//		};
//		$fieldProductModel.formSelect(optionProductModel);
//		var infinityid = $('#_sino_product_model').find("li").first().attr("infinityid");
//		var infinityname= $('#_sino_product_model').find("li").first().attr("infinityname");
//		if(infinityid){
//			$("#_sino_product_model").formSelect('setValue',infinityid);
//			$('#_productModelId').val(infinityid);
//			$('#_sino_productModelName').val(infinityname);
//		}
//		$("#_sino_product_model .uicSelectData").height("160");
	}	 
	function  _toSubTotalPrice(){
		var count=$("#_products_add_count").val();
		var unitPrice=$("#_product_add_unitPrice").val();
		var subTotalPrice=count*unitPrice;
		$("#_product_add_totalPrice").val(toDecimal2(subTotalPrice));
	}
	//强制保留2位小数，如：2，会在2后面补上00.即2.00  
	function toDecimal2(x) {              
		var f = parseFloat(x);              
		if (isNaN(f)) {
			alert("请按格式输入正确数字");
		   return;              
		}              
		var f = Math.round(x*100)/100;              
		var s = f.toString();              
		var rs = s.indexOf('.');              
		if (rs < 0) {  
		   rs = s.length;                  
		 　　s += '.';  
		}              
		while (s.length <= rs + 2) {  
		   s += '0';              
		}              
		return s;   
	}    

});