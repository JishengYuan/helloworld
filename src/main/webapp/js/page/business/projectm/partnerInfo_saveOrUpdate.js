define(function(require, exports, module) {
	var $ = require("jquery");
			require("easyui");
			require("bootstrap");
			require("coverage");
			require("formTree");
			require("formSubmit");
			require("jModuleGrid");
			var StringBuffer = require("stringBuffer");
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
			require("js/plugins/meta/js/metaSelect");
	function load(){
		
		var type = $('#_model_partnerType').val();
		if(type == null||type == ''){
			$(":checkbox").each(function(i,el){
				if(i == 0){
					$(el).attr("checked",true);
				}
			});
		} else {
			var types =  type.split(",");
			for(var i = 0; i < types.length - 1;i++){
				$("#"+types[i]).attr("checked",true);
				typeCheck();
			}
		}
//		$("#_sino_product_type").formTree({	
//			
//			inputName : '_sino_product_type_name',
//			inputValue : $('#_sino_productType_names').val(),
//			//labelName : 'formTree',
//			Checkbox : true,
//			// onlyLeafCheck : true,
//			animate : true,
//			searchTree : true,
//		
//			tree_url : ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),// 顶层
//			asyncUrl : ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),// 异步
//			search_url : ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),// 搜索
//			find_url :ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),// 精确定位
//			url : '',
//			asyncParam : ["id"],
//			addparams : [{
//						name : "productTypeId",
//						value : "root"
//					}],
//			async : true
//		});
		$("#sysRole").html($('#_sino_productType_names').val());
    	$("#modi_roles11").unbind("click").click(function(){
    		//alert(1);
    		$("#modi_roles_div").empty();
    		var sb = new StringBuffer();
    		sb.append('<div id="modi_roles" name="modi_roles" class="modal hide fade" tabindex="-1" role="modi_roles" aria-labelledby="modi_rolesbody" aria-hidden="true">');
    		sb.append('<div class="modal-header">');
    		sb.append('<button type="button" class="close _pos_roles_close">×</button >');
    		sb.append('<h3 id="modi_rolesbody">支持产品类型</h3 >');
    		sb.append('</div>');
    		sb.append('<div class="modal-body" style="height:360px;" >');
    		sb.append('<div id="_meta_select"></div>');
    		sb.append('</div >');
    		sb.append('<div class="modal-footer" >');
    		sb.append('<button class="btn _pos_roles_close">取消</button >');
    		sb.append('<button class="btn btn-primary" id="doHandlerRoles" >确定</button >');
    		sb.append('</div >');
    		sb.append('</div>');

    		$("#modi_roles_div").append(sb.toString());
    		
    		$("._pos_roles_close").unbind("click").click(function(){
    			 $('#modi_roles').modal('hide');
    		});
    		$("#_meta_select").meta_select({
                lefturl:ctx+"/business/projectm/partnerInfo/getAlltype",
                righturl:ctx + "/business/projectm/partnerInfo/getPartnertype?id="+$("#_partner_id").val()+"&tmp="+ Math.random(),
                buttons:[{right:"go-right",
                          left:"back-left",
                          allRight:"all-go-right",
                          allLeft:"all-back-left",
                          Ascending:"on",
                          Descending:"off"
                          }],
                
                OKbutton:"submit"
                });
    		
    		$("#doHandlerRoles").unbind("click").click(function(){
        		var objList = $("#_meta_select").meta_select("getValue");
        		var rolesId = "";
        		var rolesName = "";
        		for(var i　=　0;i < objList.length;i++){
        			if(i == objList.length - 1){
        				rolesId+=objList[i].id;
        				rolesName+=objList[i].name;
        			} else {
        				rolesId+=objList[i].id+",";
        				rolesName+=objList[i].name+",";
        			}
        		}
        		$("#_sino_productType").val(rolesId);
        		$("#sysRole").html(rolesName);
        		$('#modi_roles').modal('hide');
        	});
    		$("#sysRole").html($('#_sino_productType_names').val());
    	});
		var supplierType = $("#_sino_product_currency");
    	supplierType.addClass("li_form");
		var optionCompPosType = {
			writeType : 'show',
			showLabel : false,
			required:true,
			code : "currency",
			onSelect:function(){
				$('#_model_currency').val($("#_sino_product_currency").formSelect("getValue")[0]);
			},
			width : "284"
		};
		supplierType.formSelect(optionCompPosType);
		supplierType.formSelect('setValue',$('#_model_currency').val());
		
		/*var partnerId = $('#_partner_id').val();
		if(partnerId != null&&partnerId != ""){
			$("#_sino_partnerBrand_datagrid").datagrid({
				url:ctx+"/business/projectm/partnerBrand/getDataGridList?sSearch_0="+$('#_partner_id').val()+"&tmp="+Math.random(),
				title:"品牌信息表",
				loadMsg : "数据加载中，请稍后......",
				singleSelect : true,
				pagination:false,
				rownumbers: false,
				fitColumns:true,
				width:$(document.body).width()*0.7,
				onDblClickRow : onClickRow,
				columns: [[{
		            field: 'id',
		            checkbox:true,
		            sortable: false
		        },{
		            field: 'brandCode',
		            title: '品牌编码',
		            width: 90,
		            sortable: false,
		            editor:{
		            	type:'text'
		            }
		        }, {
		            field: 'brandName',
		            title: '品牌名称',
		            width: 100,
		            sortable: false,
		            editor:{
		            	type:'text'
		            }
		        }, {
		            field: 'brandType',
		            title: '品牌类型',
		            width: 100,
		            sortable: false,
		            formatter : brandType,
		            editor:{
		            	type:'combobox',
						options:{
							valueField:'id',
							textField:'name',
							url:ctx+"/sysDomain/findDomainValue?code=partnerBrandType&tmp="+Math.random()
						}
		            }
		        },{
		            field: 'brandLevel',
		            title: '品牌等级',
		            width: 100,
		            sortable: false,
		            formatter : brandLevel,
		            editor:{
		            	type:'combobox',
						options:{
							valueField:'id',
							textField:'name',
							url:ctx+"/sysDomain/findDomainValue?code=partnerBandLevel&tmp="+Math.random()
						}
		            }
		        },{
		            field: 'brandOid',
		            title: '品牌Oid',
		            width: 100,
		            sortable: false,
		            editor:{
		            	type:'text'
		            }
		        },{
		            field: 'brandLogo',
		            title: '品牌Logo',
		            width: 100,
		            height:"auto",
		            sortable: false,
		            formatter : brandLogo,
		            editor:{
		            	type:'uploadPic'
		            }
		        },{
		            field: 'brandDesc',
		            title: '品牌描述',
		            width: 100,
		            sortable: false,
		            editor:{
		            	type:'text'
		            }
		        }]],
		        toolbar :[{
					id : '_add',
					text : '增加',
					handler : function() {
						append();
					}
				},{
					id : '_save',
					text : '|  保存',
					handler : function() {
						accept();
					}
				},{
					id : '_remove',
					text : '|  删除',
					handler : function() {
						deleteBrand();
					}
				},{
					id : '_reload',
					text : '|  刷新',
					handler : function() {
						_reload();
					}
				}]
			});
			
		    $.extend($.fn.datagrid.defaults.editors, {  
		    	uploadPic : {  
		        init: function(container, options)  
		        {  
		            var editorContainer = $('<div/>');  
		      
		            var button = '<a id="_sino_brand_logo" role="button" class="btn">点击上传</a>';  
		            editorContainer.append(button);  
		            editorContainer.appendTo(container);  
		            return button;  
		        },  
		        getValue: function(target)  
		        {  
		            return $(target).text();  
		        },  
		        setValue: function(target, value)  
		        {  
		            $(target).text(value);  
		        },  
		        resize: function(target, width)  
		        {  
		            var span = $(target);  
		            if ($.boxModel == true){  
		                span.width(width - (span.outerWidth() - span.width()) - 10);  
		            } else {  
		                span.width(width - 10);  
		            }
		            $("#_sino_brand_logo").bind('click',function(){
		    			//openurl();
	    				var dialogStr = '<div id = "_sino_partner_uploadPic"></div>';
	    				$(dialogStr).appendTo('#_sino_partner_add_form');
	    				$('#_sino_partner_uploadPic').dialog({
	    				    title: '上传图片',
	    				    width: 593,
	    				    height: 456,
	    				    closable: false,
	    				    href: ctx+'/business/projectm/baseUpload/baseUploadPic?brandLogo=brandLogo&tmp='+Math.random(),
	    				    buttons:[{
	    						text:'关闭',
	    						handler:function(){
	    				    	_window_close();
	    				    		$(this).parents('.panel-body').dialog('destroy');
	    						}
	    					}]
	    				});
		           });
		        }  
		        }  
		    });  
		}*/
		
		$("#_sino_partner_add_back").bind('click',function(){
			//openurl();
			$.openurl({'murl':'business/projectm/partnerInfo/partnerInfo_main?tmp='+Math.random()});
       });
		$("#_sino_partner_add_partner").bind('click',function(){
			addPartner();
       });
		$("#_sino_partner_logo").bind('click',function(){
			upload();
       });
		$('input:checkbox[name="partnerType"]').bind('click',function(){
			typeCheck();
       });
		$('#_sino_partner_product').bind('click',function(){
			addPartnerProduct();
       });
		
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
	function append(){
		if(editIndex == undefined){
			if (endEditing()){
				$('#_sino_partnerBrand_datagrid').datagrid('appendRow',{status:'P'});
				editIndex = $('#_sino_partnerBrand_datagrid').datagrid('getRows').length -1;
				$('#_sino_partnerBrand_datagrid').datagrid('selectRow', editIndex)
						.datagrid('beginEdit', editIndex);
			}
		} else {
			UicDialog.Error("请先保存,在增加!");
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
	
	function deleteBrand(){
		var row = $('#_sino_partnerBrand_datagrid').datagrid('getSelected');
		if(row == null){
			UicDialog.Error("请选择数据!");
			return;
		}
		var id = row.id;
		UicDialog.Confirm("确定要删除这条数据吗？",function(){
			if(id == null||id == ""){
				var rows = $('#_sino_partnerBrand_datagrid').datagrid('getRows');
				$('#_sino_partnerBrand_datagrid').datagrid('deleteRow', rows.length-1);
			} else {
				$.ajax({
					url: ctx+"/business/projectm/partnerBrand/remove?id="+id,  // 提交的页面
		            data: "", // 从表单中获取数据
		            type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
		            error: function(request) {     // 设置表单提交出错
		            },
		            success: function(data) {
		            	_reload();
		            	
		            }
				});
			}
			
        },function(){
        });
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
		var checkboxValues =[];
		$(":checkbox:checked").each(function(){
		    var  checkboxValue = $(this).val();
		    checkboxValues.push(checkboxValue);
		});
		var b = false; 
		for(var i = 0;i < checkboxValues.length;i++){
			if(checkboxValues[i] == 3000){
				b = true;
			}
		}
	    if(b){
	    	$("#_sino_device_serviceType").show();
	    	$("input[name='_sino_product_type_name']").attr("required",false);
	    } else {
	    	$("#_sino_device_serviceType").hide();
	    	$("input[name='_sino_product_type_name']").attr("required",true);
	    }

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
		
//		var dialogStr = '<div id = "_sino_partner_uploadPic"></div>';
//		$(dialogStr).appendTo('#_sino_partner_add_form');
//		$('#_sino_partner_uploadPic').dialog({
//		    title: '上传图片',
//		    width: 593,
//		    height: 456,
//		    closable: false,
//		    href: ctx+'/business/projectm/baseUpload/baseUploadPic?tmp='+Math.random(),
//		    buttons:[{
//				text:'关闭',
//				handler:function(){
//		    	_window_close();
//		    		$(this).parents('.panel-body').dialog('destroy');
//				}
//			}]
//		});
	}
	
	function addPartner(){
		var url = ctx+'/business/projectm/partnerInfo/saveOrUpdate?tmp='+Math.random();
		$("#_sino_partner_add_form").form('submit',{
	    	url:url,
	    	onSubmit:function(){
	
				var options = {};
				options.formId = "_sino_partner_add_form";
				return $.formSubmit.doHandler(options);
	    	},
            success: function(data) {
                // 设置表单提交完成使用方法
                   if(data != "fail"){
                	   openurl();
                	   //$('#_partner_id').val(data);
                    }
                }
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




	
