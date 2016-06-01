define(function(require, exports, module) {
	var $ = require("jquery");
	require("dataTables");
	require("DT_bootstrap");
	require("formSelect");
	require("formTree");
	require("uic_Dropdown");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("easyui");
	require("coverage");
	require("formSubmit");
	require("js/plugins/uic/components/form/formPartnerInfo");
	
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
		getPartnerInfo('root');
		//类型
		getType('');
		var brandUrl = '';
		if($('#_productModelId').val() != ""&&$('#_productModelId').val() != null){
			brandUrl = ctx+'/business/projectm/partnerBrand/getSelectListByPartner?partnerId='+$('#_partnerId').val();
		}
	
		
		var productUrl = '';
		if($('#_productModelId').val() != ""&&$('#_productModelId').val() != null){
			if($('#_brandCode').val() != ""&&$('#_brandCode').val() != null){
				productUrl = ctx+'/business/projectm/productline/getTree?partnerBrandId='+$('#_brandCode').val();
			} else {
				productUrl = ctx+'/business/projectm/productline/getTree?partnerId='+$('#_partnerId').val();
			}
		} 
		
		var supplierType = $("#_sino_product_currency");
    	supplierType.addClass("li_form");
		var optionCompPosType = {
			writeType : 'show',
			showLabel : false,
			required:true,
			code : "currency",
			onSelect:function(){
				$('#_currency').val($("#_sino_product_currency").formSelect("getValue")[0]);
			},
			width : "280"
		};
		supplierType.formSelect(optionCompPosType);
		if($('#_currency').val()!=""){
			supplierType.formSelect('setValue',$('#_currency').val());
		}else{
			supplierType.formSelect('setValue','RMB');
		}

		
		 $('.date').datetimepicker({
	    	pickTime: false
	    });
		 

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
	    	
	    }
	    
	   /* $("#_sino_partner_productModel_addg").bind('click',function(){
	    	$('.nav-tabs a:last').tab('show');
		 });*/
	    $("#_sino_partner_productModel_addbg").bind('click',function(){
	    	openurl();
		 });

	}
	
	function getPartnerInfo(productTypeId){
		$("#_sino_partner_div").empty();
		var $fieldStaff = $("#_sino_partner_div");
		var optionss = {
			showLabel : false,
			radioStructure : true,
			selectUser : false,
			onClickSearchFun:function(id,name){
				$('#_partnerId').val(id);
				//getType(id);
			},
			processFun:function(id,name){
				$('#_partnerId').val(id);
				//getType(id);
			},
			submitFunP:function(id,name){
				$('#_partnerId').val(id);
				//getType(id);
			},
			deleteFun:function(){
				$('#_partnerId').val("");

			},
			width : "281"
		}
		optionss.addparams = [ {
			name : "code",
			value : "root"
		} ];
		optionss.resIds = $("#_partnerId").attr("value");
		optionss.inputValue = $("#_partner_name").attr("value");
		$fieldStaff.formPartnerInfo(optionss);
		/*if( $("#_partnerId").attr("value")!=null){
			getType($("#_partnerId").val());
		}*/

	}
	
	function getType(partnerId){
		$("#_sino_product_type").empty();
		$("#_sino_product_type").formTree({	
			
			inputName : '_sino_product_type',
			inputValue : $('#_sino_productTypeName').val(),
			//labelName : 'formTree',
			folderChoose:false,
			Checkbox : false,
			// onlyLeafCheck : true,
			animate : true,
			searchTree : true,
			required : true,
		
			asyncUrl:ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),
			//tree_url : ctx+"/business/projectm/productType/getTreeBypartner?tmp="+Math.random(),// 顶层
			tree_url : ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),// 顶层
			search_url : ctx+"/business/projectm/productType/getTree",// 搜索
			find_url :ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),// 精确定位
			url : '',
			asyncParam : ["id"],
			addparams : [{name:"partnerId",value:partnerId}],
			onSelect:function(node){
				getPartnerInfo(node.id);
				$('#_productType').val(node.id);
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
	
	function saveOrUpdate(){
		var url = ctx+'/business/projectm/productModel/saveOrUpdate?tmp='+Math.random();
		var _partner_name=$("#_partnerId").attr("value");//抓取生产厂商信息
		if(_partner_name==''||_partner_name==null){
			UicDialog.Error("生产厂商不能为空！");
			return;
		}
		$("#_sino_partner_productModel_add_form").form('submit',{
	    	url:url,
	    	onSubmit:function(){
			var options = {};
			options.formId = "_sino_partner_productModel_add_form";
			return $.formSubmit.doHandler(options);
	    	},
            success: function(data) {
                // 设置表单提交完成使用方法
                   if(data != "fail"){
                	   openurl();
                    }
                }
	    });
	}
	
	function addProductModelData(){
		var url = ctx+'/business/projectm/productModelData/saveOrUpdate?tmp='+Math.random();
		$("#_sino_partner_productModelData_add_form").form('submit',{
	    	url:url,
	    	onSubmit:function(){
	    		var options = {};
				options.formId = "_sino_partner_productModelData_add_form";
				return $.formSubmit.doHandler(options);
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
		//$('.edit_list').load(ctx+'/business/projectm/productModel/productModel_main?tmp='+Math.random());
		var options = {};
		options.murl = 'business/projectm/productModel/productModel_main';
		$.openurl(options);
	}
	exports.init = function(){
		load();  
	};
});