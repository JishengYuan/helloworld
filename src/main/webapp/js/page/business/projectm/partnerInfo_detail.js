define(function(require, exports, module) {
	var $ = require("jquery");
			require("easyui");
			require("bootstrap");
			require("coverage");
			require("formTree");
			require("formSubmit");
			require("jModuleGrid");
			
			require("formSelect");
			/*
		    * 上传图片用
		    */
		   require("swfupload");
		   require("queue");
		   require("fileprogress");
		   require("callbacks");
		   require("fileTypeMaps");
		   require("tangram");
		   require("uic/message_dialog");
	function load(){
		
		$.ajax({
			type : "GET",
			async : false,
			dataType : "json",
			url : ctx + "/sysDomain/findDomainValue?code=currency&tmp="+ Math.random(),
			success : function(msg) {
				for(var i in msg){
					if(msg[i].id == $('#_model_currency').val()){
						$('#_sino_product_currency').text(msg[i].name);
					}
				}
			}
		});
		$("#_sino_partner_logo").bind('click',function(){
			upload();
       });
		$("#_sino_partner_add_back").bind('click',function(){
			//openurl();
			$.openurl({'murl':'business/projectm/partnerInfo/partnerInfo_main?tmp='+Math.random()});
       });
		var ptype = $("#partnerType").val().split(",");
		var parterType = "";
		for(var i = 0; i < ptype.length - 1;i++){
			if(ptype[i]==1000){
				parterType+="设备制造商"+"，";
			}
			if(ptype[i]==2000){
				parterType+="设备供应商"+"，";
			}
			if(ptype[i]==3000){
				parterType+="服务供应商";
			}
		}
		$("#dmValueMeaning").html(parterType);
		
	}
	
	function brandType(value,row){
		if(value == 1000){
			return '国际品牌';
		}
		if(value == 2000){
			return '国家品牌';
		}
		if(value == 3000){
			return '地区品牌';
		}
	}
	function brandLevel(value,row){
		if(value == 1000){
			return '一等品';
		}
		if(value == 2000){
			return '二等品';
		}
		if(value == 3000){
			return '三等品';
		}
	}
	
	function brandLogo(value,row){
		if(value != ""&&value != null){
			return '<img height="10px;" src="'+ctx+value+'">';
		} else {
			return '';
		}
	}
	var editIndex = undefined;
	function endEditing(){
		if (editIndex == undefined){return true;}
		if ($('#_sino_partnerBrand_datagrid').datagrid('validateRow', editIndex)){
			$('#_sino_partnerBrand_datagrid').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	
	function onClickRow(index){
		if (editIndex != index){
			if (endEditing()){
				$('#_sino_partnerBrand_datagrid').datagrid('selectRow', index)
						.datagrid('beginEdit', index);
				editIndex = index;
			} else {
				$('#_sino_partnerBrand_datagrid').datagrid('selectRow', editIndex);
			}
		}
	}
	
	function accept(){
//		$('#_sino_partnerBrand_datagrid').datagrid('reload');
		var rows = $('#_sino_partnerBrand_datagrid').datagrid('getRows');
//		var row = rows[rows.length - 1];
//		alert(editIndex);
		 var row = $('#_sino_partnerBrand_datagrid').datagrid('getSelected');
		 if(row == null){
				return;
			}
		 var id = row.id;
		 
		 if(id == undefined||id == ""||id == null){
			 if (endEditing()){
					$('#_sino_partnerBrand_datagrid').datagrid('acceptChanges');
				}
			var model = rows[rows.length-1];
			var queryString = $.param({
	        	"brandCode": model.brandCode,
	        	"brandName": model.brandName,
	        	"brandType": model.brandType,
	        	"brandLevel": model.brandLevel,
	        	"partnerId":$('#_partner_id').val(),
	        	
	        	"brandLogo":$('#_sino_brandLogo').val(),
	        	"brandDesc":model.brandDesc,
	        	"brandOid": model.brandOid
	    		}, true);
				var href = ctx + "/business/projectm/partnerBrand/save";
				$.post(href, queryString, function(data){
					$('#_sino_brandLogo').val("");
					$('#_sino_partnerBrand_datagrid').datagrid('reload');
				});
		 } else {
			 var model = row;
			 if (endEditing()){
					$('#_sino_partnerBrand_datagrid').datagrid('acceptChanges');
				}
			var queryString = $.param({
				"id":id,
	        	"brandCode": model.brandCode,
	        	"brandName": model.brandName,
	        	"brandType": model.brandType,
	        	"brandLevel": model.brandLevel,
	        	"partnerId":$('#_partner_id').val(),
	        	
	        	"brandLogo":$('#_sino_brandLogo').val(),
	        	"brandDesc":model.brandDesc,
	        	"brandOid": model.brandOid
	    		}, true);
				var href = ctx + "/business/projectm/partnerBrand/update";
				$.post(href, queryString, function(data){
					$('#_sino_brandLogo').val("");
					$('#_sino_partnerBrand_datagrid').datagrid('reload');
				});
		 }

	}
	
	
	function _reload(){
		editIndex = undefined;
		$('#_sino_partnerBrand_datagrid').datagrid('reload');
	}
	function openurl(){
		editIndex = undefined;
		var options = {};
		options.murl = 'business/projectm/partnerInfo/partnerInfo_main';
		$.openurl(options);
	}
	
	function typeCheck(){
}
	
	function upload(){
		$('#_sino_partner_brand_logo_page').empty();
		var href = ctx+'/business/projectm/baseUpload/baseUploadPic?tmp='+Math.random();
		var dialogStr = '<div id="_sino_partner_brand_logo_page" name="_sino_partner_brand_logo_page" class="modal hide fade" tabindex="-1"  aria-labelledby="_sino_partner_brand_logo_page" aria-hidden="true"></div>';
		$('#_sino_partner_add_form').append(dialogStr);
		$('#_sino_partner_brand_logo_page').load(href,function(){
			$("#pic_btnConfirm").bind('click',function(){
				_window_close();
				$("#_sino_partner_brand_logo_page").modal('hide');
	       });
		});
	}
	
	function isNew(id,obj){
		var b = true;
		var oldIds = obj.split(",");
		for(var i = 0;i< oldIds.length;i++){
			if(id == oldIds[i]){
				b = false;
			}
		}
		return b;
	} 
	
	function isOld(id,obj){
		var b = true;
		var oldIds = obj.split(",");
		for(var i = 0;i< oldIds.length;i++){
			if(id == oldIds[i]){
				b = false;
			}
		}
		return b;
	}
	
	exports.init = function(){
		load();
	}
});




	
