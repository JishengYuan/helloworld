define(function(require, exports, module) {
	var $ = require("jquery");
	var dataTable$ = require("dataTables");
	require("DT_bootstrap");
	require("formSelect");
	require("formTree");
	var uic$ = require("uic_Dropdown");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("easyui");
	require("coverage");
	require("formSubmit");
	require("uic/message_dialog");
	
	/*
	    * 上传图片用
	    */
	   require("swfupload");
	   require("queue");
	   require("fileprogress");
	   require("callbacks");
	   require("fileTypeMaps");
	   require("tangram");
	function load(){
		//类型
		$("#_sino_product_type").formTree({	
			
			inputName : '_sino_product_type',
			inputValue : $('#_sino_productTypeName').val(),
			//labelName : 'formTree',
			Checkbox : false,
			// onlyLeafCheck : true,
			animate : true,
			searchTree : true,
			required : true,
		
			tree_url : ctx+"/business/projectm/productModel/getTree?tmp="+Math.random(),// 顶层
			search_url : ctx+"/business/projectm/productModel/getTree",// 搜索
			url : '',
			asyncParam : ["id"],
			onSelect:function(node){
				getPartnerInfo(node.id);
				getPartnerBrand('');
				getProductLine('');
				$('#_productType').val(node.id);
				$('#_partnerId').val('');
				$('#_brandCode').val('');
				$('#_productLine').val('');
			},
			async : true
		});
		var brandUrl = '';
		if($('#_productModelId').val() != ""&&$('#_productModelId').val() != null){
			brandUrl = ctx+'/business/projectm/partnerBrand/getSelectListByPartner?partnerId='+$('#_partnerId').val();
		}
		//品牌
		var $fieldCompDevType = $("#_sino_partner_brand");
		$fieldCompDevType.addClass("li_form");
		var optionCompDevTypes = {
			inputName : "partnerBrand",
			writeType : 'show',
			showLabel : false,
			width:"280", //高度
			url : brandUrl,
			onSelect :function(){
				var str = $("#_sino_partner_brand").formSelect("getValue")+"";
				var idAndName = str.split(",");
				//getProductLine(ctx+'/base/productline/getTree?partnerBrandId='+idAndName[0]+"&tmp="+Math.random());;
				$('#_brandCode').val(idAndName[0]);
				//$('#_productLine').val('');
			}
			//checkbox : true
		};
		$fieldCompDevType.formSelect(optionCompDevTypes);
		$("#_sino_partner_brand").formSelect('setValue',$('#_brandCode').val());
		
		
		var productUrl = '';
		if($('#_productModelId').val() != ""&&$('#_productModelId').val() != null){
			if($('#_brandCode').val() != ""&&$('#_brandCode').val() != null){
				productUrl = ctx+'/business/projectm/productline/getTree?partnerBrandId='+$('#_brandCode').val();
			} else {
				productUrl = ctx+'/business/projectm/productline/getTree?partnerId='+$('#_partnerId').val();
			}
		} 
		//系列
		$("#_sino_product_line").formTree({	
			inputName : '_sino_product_line',
			inputValue : $('#_sino_productLineName').val(),
			Checkbox : false,
			animate : true,
			searchTree : true,
		
			tree_url : productUrl,// 顶层
			search_url : productUrl,// 搜索
			asyncParam : ["id"],
			onSelect:function(node){
				$('#_productLine').val(node.id);
			},
			async : true
		});	
		
		 $('.date').datetimepicker({
	    	pickTime: false
	    });
		
		 
		 var b = true;
		 $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
			 b = true;
			 if($('#_productModelId').val() != null&&$('#_productModelId').val() != ""){
				 $(this).tab('show');
			 } else {
				 $('.nav-tabs a:first').tab('show'); 
				 if(b){
					 UicDialog.Error("请点击下一页查看!");
					 b = false;
				 }
			 }
			})
		 
//		$(document).on('click.tab.data-api', '[data-toggle="tab"], [data-toggle="pill"]', function (e) {
//			 if($('#_productModelId').val() != null&&$('#_productModelId').val() != ""){
//				 $(this).tab('show');
//			 } else {
//				 $('.nav-tabs a:first').tab('show'); 
//				 if(b){
//					 b = false;
//				 }
//			 }
//		  });
		 
		/*
		 * 绑定触发事件
		 */
		 
		 $("#_sino_partner_product_back").bind('click',function(){
			 openurl();
         });
		 $("#_sino_partner_productModel_add").bind('click',function(){
			 saveOrUpdate();
         });
		 $("#_sino_base_icon").bind('click',function(){
	    	upload(this);
		 });
	    $("#_sino_base_big").bind('click',function(){
	    	upload(this);
		 });
	    $("#_sino_partner_product_addExpand").bind('click',function(){
	    	productModelAddExpand();
		 });
	    
	    $("#_sino_partner_productModel_addb").bind('click',function(){
	    	addProductModelData();
		 });
	    
	    var divList = $("._domainCode");
	    for(var i = 0;i < divList.length;i++){
	    	var obj = divList[i];
	    	var codeName = $(obj).attr("name");
	    	var divIdA = $(obj).attr("id");
	    	var inputIdA = parseInt(divIdA) - 2013;
	    	var $fieldDataTypes = $(obj);
			$fieldDataTypes.addClass("li_form");
			
			var optionDataTypes = {
				writeType : 'show',
				showLabel : false,
				code : codeName,
				width : "282",
				onSelect :function(id,obj){
					var divIdB = $(obj).parent().parent().parent().parent().attr("id");
					var inputIdB = parseInt(divIdB) - 2013;
					$('#'+inputIdB).val(id);
				}
			};
			$fieldDataTypes.formSelect(optionDataTypes);
			$fieldDataTypes.formSelect('setValue',$('#'+inputIdA).val());
			if($fieldDataTypes.formSelect('getValue') != null&&$fieldDataTypes.formSelect('getValue') != ""){
				var idAndName = $fieldDataTypes.formSelect('getValue')+"";
				var opt = idAndName.split(",");
				var divTextId = parseInt(divIdA) + 1;
				$("#"+divTextId).html(opt[1]);
			}
	    	
	    }
	    
	    $("#_sino_partner_productModel_addg").bind('click',function(){
	    	$('.nav-tabs a:last').tab('show');
		 });
	    $("#_sino_partner_productModel_addbg").bind('click',function(){
	    	openurl();
		 });

	}
	
	function getPartnerInfo(productTypeId){
		$("#_sino_partner_div").empty();
		$("#_sino_partner_div").append('<input id="_sino_partner" type="text" class="ultra-select-input3" data="0" data-content="请选择厂商" required />');
		//加载厂商
		$(".ultra-select-input3").uic_Dropdown({
			height:"auto",// 宽度
			title: "厂商",
			selecttitle:"", //标题
			url:ctx+"/business/projectm/partnerInfo/getAll?productTypeId="+productTypeId,	 //型号分组数据
			checkbox:false,
			branchtype:true,
			search:false,
			width:'280px',
			onSelect:function(id,value){
				getPartnerBrand(ctx+"/business/projectm/partnerBrand/getSelectListByPartner?partnerId="+id+"&tmp="+Math.random());
				getProductLine(ctx+'/business/projectm/productline/getTree?partnerId='+id+"&tmp="+Math.random());
				$('#_partnerId').val(id);
				$('#_brandCode').val('');
				$('#_productLine').val('');
				//reloadTree("partnerId",id);
			}
		});
	}
	function getPartnerBrand(url){
		$('#_sino_partner_brand').empty();
		var $fieldCompDevType = $("#_sino_partner_brand");
		$fieldCompDevType.addClass("li_form");
		var optionCompDevTypes = {
			inputName : "partnerBrand",
			writeType : 'show',
			width:"280", //高度
			showLabel : false,
			url : url,
			inputChange : true,
			onSelect :function(){
				var str = $("#_sino_partner_brand").formSelect("getValue")+"";
				var idAndName = str.split(",");
				getProductLine(ctx+'/business/projectm/productline/getTree?partnerBrandId='+idAndName[0]+"&tmp="+Math.random());;
				$('#_brandCode').val(idAndName[0]);
				//$('#_productLine').val('');
		}
		};
		$fieldCompDevType.formSelect(optionCompDevTypes);
		
		var index1 = url.indexOf("partnerId");
		if(index1 != -1){
			var infinityid = $('#_sino_partner_brand').find("li").first().attr("infinityid");
			if(infinityid){
				$("#_sino_partner_brand").formSelect('setValue',infinityid);
				$('#_brandCode').val(infinityid);
			}
		}
	}
	function getProductLine(url){
		$("#_sino_product_line").empty();
		var index1 = url.indexOf("partnerId");
		if(index1 != -1){
			$.ajax({
	            type: "GET",
	            url: url,
	            data: "{}",
	            contentType: "application/json; charset=utf-8",
	            dataType: "json",
	            success: function (data) {
					if(data){
						$('#_sino_product_line').find("input").first().attr("value",data[0].text);
					}
	            },
	            error: function (msg) {
	            }
	        });
		}
		$("#_sino_product_line").formTree({	
			inputName : '_sino_product_line',
			//labelName : 'formTree',
			Checkbox : false,
			// onlyLeafCheck : true,
			animate : true,
			searchTree : true,
		
			tree_url : url,// 顶层
			search_url : url,// 搜索
			asyncParam : ["id"],
			onSelect:function(node){
				$('#_productLine').val(node.id);
			},
			async : true
		});	
	}
	
	function upload(obj){
		var href = ctx+'/baseUpload/baseUploadPic?tmp='+Math.random();
		if(obj.id == '_sino_base_big'){
			href+='&big=t';
		}
		var id = obj.id + "_uploadPic";
		var dialogStr = '<div id = "'+id+'"></div>';
		$(dialogStr).appendTo('#basic');
		$('#'+id).dialog({
		    title: '上传图片',
		    width: 593,
		    height: 456,
		    href: href,
		    closable: false,
		    buttons:[{
				text:'关闭',
				handler:function(){
		    	_window_close();
		    		$(this).parents('.panel-body').dialog('destroy');
				}
			}]
		});
	}
	
	function addProductModelData(){
		var url = ctx+'/base/productModelData/saveOrUpdate?tmp='+Math.random();
		$("#_sino_partner_productModelData_add_form").form('submit',{
	    	url:url,
	    	onSubmit:function(){
	    		return $(this).form('validate');
	    	},
            success: function(data) {
                // 设置表单提交完成使用方法
                   if(data != "fail"){
                	   openurl();
                    }
                }
	    });
	}
	
	function openurl(){
		//$('.edit_list').load(ctx+'/base/productModel/productModel_main?tmp='+Math.random());
		var options = {};
		options.murl = 'business/projectm/productModel/productModel_main';
		$.openurl(options);
	}
	exports.init = function(){
		load();  
	}
});