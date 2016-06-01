define(function(require, exports, module) {
	var $ = require("jquery");
	require("easyui");
	require("coverage");
	require("jModuleGrid");
	require("jquery.pop");
	require("jqBootstrapValidation");
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
	var jsonData = [];
	function loadTreegrid(){
		
		$("#_sino_devicetype_treegrid").treegrid({
	        	title:"设备分类表",
	        	loadMsg : "数据加载中，请稍后......",
	        	method:"post",
	        	url:ctx+"/business/projectm/productType/getTreegrid",
	        	rownumbers:true,
	        	idField:"id",
	        	treeField:"name",
	        	fitColumns:true,
	        	width:'100%',
	        	height:'auto',
	        	onBeforeLoad:function(row,param){
//					$("#_sino_devicetype_treegrid").resizeTreeGrid(0, $(this).width()*0.2-10, 0, $(this).width()*0.79-10);
//					var w = $(".datagrid").width();
//					$(".panel-header").css('width',w-30);
//					$(".datagrid-wrap").css('width',w-20);
				},	
	        	onLoadSuccess:function(row){
	        		jsonData = $('#_sino_devicetype_treegrid').treegrid('getData');
//					$(window).bind('resize', function () {
//						$("#_sino_devicetype_treegrid").resizeTreeGrid(0, $(this).width()*0.2-10, 0, $(this).width()*0.79-10);
//						var w = $(".datagrid").width();
//						$(".panel-header").css('width',w-30);
//						$(".datagrid-wrap").css('width',w-20);
//					});
					$('#_sino_devicetype_treegrid').treegrid('options').jsonData = row;
				},
	        	columns:[[
	        	  {
	        		 field:"name",
	        		 title:"名称",
	        		 width:$(this).width() * 0.13,
	        		 height:50
	        	  },{
	        		  field:"icon",
	        		  title:"图标信息",
	        		  width:$(this).width() * 0.1,
	        		  height:"30px",
	        		  formatter:formatProgress
	        	  },{
	        		  field:"isModule",
	        		  title:"部件标志",
	        		  width:$(this).width() * 0.1,
	        		  formatter:module
	        	  },{
	        		  field:"isLogic",
	        		  title:"是否逻辑设备",
	        		  width:$(this).width() * 0.1,
	        		  formatter:logic
	        	  },{
	        		  field:"bgImage",
	        		  title:"背景图片",
	        		  width:$(this).width() * 0.11,
	        		  height:"30px",
	        		  formatter:bgImage
	        	  }
	        	 ]]
		});
		function formatProgress(value){
			if (value != null&&value != ""){
				return '<img width="20px" height="20px;" src="'+ctx+value+'">';
	    	} else {
		    	return '';
	    	}
		}
		function bgImage(value){
			if (value != null&&value != ""){
				return '<img width="20px" height="20px;" src="'+ctx+value+'">';
	    	} else {
		    	return '';
	    	}
		}
		
		function module(value){
			if(value == 1){
				return "是";
			} else {
				return "否";
			}
		}
		
		function logic(value){
			if(value == 1){
				return "逻辑";
			} else {
				return "物理";
			}
		}
		
		/*
		 * 绑定触发事件
		 */
		 $("#_sino_devicetype_add").bind('click',function(){
              create();
         });

		 $("#_sino_devicetype_edit").bind('click',function(){
    	      update();
		 });
      
		 $("#_sino_devicetype_del").bind('click',function(){
	         del();
		 });
	}
	
	function create(){
		$('#alertMsg').empty();
		var row = $("#_sino_devicetype_treegrid").treegrid("getSelected"); 
		$("#_sino_devicetype_loadurl").empty();
		$(".page-header").empty();
		var titleStr = '<h2 id="myModalLabel">新建类别'
			+'<div class="pull-right"><button id="cancle" type="button" class="btn btn-default btn-lg">'
			+' <span class="icon-repeat"></span>'
			+'返回'
			+'</button></div>'
			+'</h2>'
		$(".page-header").append(titleStr);
		
		var parentId,parentName,typeCode;
		if(row){				
		   parentId = row.id;			   
		   parentName= "<strong>父菜单为："+row.name+"</strong>";
		   typeCode = row.typeCode;
		} else {
			parentId = "";
			parentName = "空";
			typeCode = "";
		}
		
		var str =  ""
			+"<div class='padding:10px 0 10px 60px;text-align: center;'>"		
			+"<form id='_sino_devicetype_create' name='_sino_devicetype_create' method='post' class='form-horizontal'>"
			+"<input type='hidden' name='parentId' value='"+parentId+"'>"
			+"<input type='hidden' id='_sino_base_picUrl' name='icon'>"
			+"<input type='hidden' id='_sino_base_bigUrl' name='bgImage'>"
			
			+"<div class='control-group' style='margin-bottom:0px;border: 2px solid #F3F3F3;width: 800px;'>"
			+"<div class='alert' style='margin-top:1px'><strong style='margin-left: 300px;'>提示：</strong>当前父节点为："+parentName+"</div>"
			+"<label class='control-label' for='TypeName'>类别名称:</label><div class='controls'><input type='text' name='typeName' data-validation-required-message='请输入菜单名称！' required /> <p class='help-block'></p></div>"
			+"<div class='control-group' style='margin-bottom:0px;margin-top:15px;'><label class='control-label' for='TypeName'>分类编码:</label><div class='input-prepend'><span class='add-on' style='margin-left:20px;'>"+typeCode+"</span><input typeCode ='"+typeCode+"' style='width: 180px;' type='text' name='typeCode' id='_typeCode' data-validation-required-message='请输入分类编码！' data-content='编码已存在！' required /> <p class='help-block' style='font-size:12px;margin-top:-20px;margin-left:260px;color:red;'>注：编码需要唯一</p></div></div>"
			//+"<div class='control-group'><label class='control-label' for='IsModule'>部件标志</label><div class='controls'><label class='radio inline'>是<input type='radio' name='isModule' value='1' checked/></label>"
			//+"<label class='radio inline'>否<input type='radio' name='isModule' value='2'/></label></div></div>"
			+"<input type='hidden' name='isModule' id='isModule'  value='2'/>"
			+"<input type='hidden' name='isLogic' id='isLogic' value='2'/>"
			//+"<div class='control-group'><label class='control-label' for='IsLogic'>是否逻辑设备</label><div class='controls'><label class='radio inline'>逻辑<input type='radio' name='isLogic' value='1' checked/></label>"
			//+"<label class='radio inline'>物理<input type='radio' name='isLogic' value='2'/></label></div></div>"
			
			//+"<div class='control-group'><label class='control-label' for='Icon'>图标信息</label><div class='controls'><img id='_sino_base_picPath' src='"+ctx+"/images/state/noUpLoad.jpg' alt='预览' style='height: 60px; background-color: #ccc; border: 1px solid #333' /><a href='#_sino_partner_brand_logo_page' data-toggle='modal' role='button' data-toggle='modal' class='btn btn-primary' id='_sino_base_icon'>点击上传</a><p class='help-block'></p></div></div>"
			//+"<div class='control-group'><label class='control-label' for='Icon'>背景图片</label><div class='controls'><img id='_sino_base_bigPath' src='"+ctx+"/images/state/noUpLoad.jpg' alt='预览' style='height: 60px; background-color: #ccc; border: 1px solid #333' /><a href='#_sino_partner_brand_logo_page' data-toggle='modal' role='button' data-toggle='modal' class='btn btn-primary' class='btn btn-primary' id='_sino_base_big'>点击上传</a><p class='help-block'></p></div></div>"
			+"<div ><label class='control-label' for='Icon' style='margin-top: 25px;'>排序字段:</label><div ><input type='number' style='margin-top: 25px;margin-left: 21px;' name='orderValue' data-validation-number-message='请输入排序字段整数！'/><p class='help-block'></p></div></div></div>"				
			+"</form>"
			+"</div>"
			+"<div class='modal-footer' style='margin-top: 10px;width: 776px;'>"				
			+"<button class='btn btn-primary' id='btnConfirm'>保存分类</button ></div>"
						
			;

		    $("#_sino_devicetype_loadurl").append(str);
            
		    $("input,select,textarea").not("[type=submit]").jqBootstrapValidation({
				submitSuccess: function (form, event){
					event.preventDefault();
	    				$.ajax({
		    					url: ctx+"/business/projectm/productType/add?tmp="+Math.random(),  // 提交的页面

		    					data: $('#_sino_devicetype_create').serialize(), // 从表单中获取数据
		    					type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
		    					beforeSend: function()          // 设置表单提交前方法
		    					{
		    						var typeCodes = [];
		    						var _typeCode = $('#_typeCode').val();
		    						_typeCode+=$('#_typeCode').attr('typeCode');
		    						for(var i in jsonData){
		    							var code = jsonData[i].typeCode;
		    							typeCodes.push(code);
		    							addTypeCode(jsonData[i],typeCodes);
		    						}
		    						for(var j in typeCodes){
		    							if(_typeCode == typeCodes[j]){
		    								$('#_typeCode').popover({trigger:'focus',animation:true,placement:'left',delay:{ show: 100, hide: 100 }});
		    								$('#_typeCode').focus();
		    								return false;
		    							}
		    						}
		    					},
		    					error: function(request) {      // 设置表单提交出错
		    					},
		    					success: function(data) {
		    						openurl('business/projectm/productType/productType_main');
		    					}
	    				});
					
				},submitError: function (form, event, errors) {
					event.preventDefault();
				}
			});
		    
		    /*
		     * 绑定上传时间
		     */
//		    $("#_sino_devicetype_pic").unbind('click').click(function(){
//	        	upload();
//        	  	        	
//	        });
		    $("#_sino_base_icon").bind('click',function(){
		    	upload(this);
			 });
		    $("#_sino_base_big").bind('click',function(){
		    	upload(this);
			 });
	        $("#btnConfirm").unbind('click').click(function(){
	        	$('#_sino_devicetype_create').submit();
	        	    //url = ctx+"/business/projectm/productType/add?tmp="+Math.random();
	        	    //save();
	          });
	        $("#cancle").unbind('click').click(function(){
	        	openurl('business/projectm/productType/productType_main');
        	  	        	
	        });
		}
	
	function isChildren(obj){
		if(obj.children){
			if(obj.children.length > 0){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	function addTypeCode(obj,typeCodes){
		if(isChildren(obj)){
			for(var j in obj.children){
				var code = obj.children[j].typeCode;
				typeCodes.push(code);
				if(isChildren(obj.children[j])){
					addTypeCode(obj.children[j],typeCodes);
				}
			}
		}
	}
	
	function upload(obj){
		var href = ctx+'/business/projectm/baseUpload/baseUploadPic?tmp='+Math.random();
		if(obj.id == '_sino_base_big'){
			href+='&big=t';
		}
		
		$('#_sino_partner_brand_logo_page').empty();
		var dialogStr = '<div id="_sino_partner_brand_logo_page" name="_sino_partner_brand_logo_page" class="modal hide fade" tabindex="-1"  aria-labelledby="_sino_partner_brand_logo_page" aria-hidden="true"></div>';
		$('#_sino_devicetype_loadurl').append(dialogStr);
		$('#_sino_partner_brand_logo_page').load(href,function(){
			$("#pic_btnConfirm").bind('click',function(){
				_window_close();
				$("#_sino_partner_brand_logo_page").modal('hide');
	       });
		});
		
//		var href = ctx+'/business/projectm/baseUpload/baseUploadPic?tmp='+Math.random();
//		if(obj.id == '_sino_base_big'){
//			href+='&big=t';
//		}
//		var id = obj.id + "_uploadPic";
//		var dialogStr = '<div id = "'+id+'"></div>';
//		$(dialogStr).appendTo('#_sino_devicetype_loadurl');
//		$('#'+id).dialog({
//		    title: '上传图片',
//		    width: 593,
//		    height: 456,
//		    href: href,
//		    closable: false,
//		    buttons:[{
//				text:'关闭',
//				handler:function(){
//		    	_window_close();
//		    		$(this).parents('.panel-body').dialog('destroy');
//				}
//			}]
//		});
	}
	
	function save(){
		$("#_sino_devicetype_create").form('submit',{
	    	url:'',
	    	onSubmit:function(){
	    		return false;
	    	},
            success: function(data) {
                // 设置表单提交完成使用方法
                   if(data=="success"){
                	  //  alert("ss3ss");
                		$('#alertMsg').empty();
	         			$('#alertMsg').append('<div class="alert alert-success"><strong>提示：</strong>操作成功！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
	         		    $(".alert").delay(2000).hide(0);
	         		    $(".close").click(function(){
	         		       $(".alert").hide();
	         		    });	
	         		   openurl('business/projectm/productType/productType_main');
                    }else{
                	   $('#alertMsg').empty();
	         			$('#alertMsg').append('<div class="alert alert-error"><strong>错误：</strong>操作失败，请稍后再试！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
	         		    $(".alert").delay(2000).hide(0);
	         		    $(".close").click(function(){
	         		    	$(".alert").hide();
	         		    });
                   }  
  
                    
                }
	    });
	}
	
	function update(){
		$('#alertMsg').empty();
		var row = $("#_sino_devicetype_treegrid").treegrid("getSelected"); 
		
		if(row == null){
			$('#alertMsg').append('<div class="alert alert-error"><strong>错误：</strong>请选择要修改的数据！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
			$(".alert").delay(2000).hide(0);
 		    $(".close").click(function(){
 		    	$(".alert").hide();
 		    });
			return;
		}
		var isModule = row.isModule;
		var isLogic = row.isLogic;
		var orderValue = '';
		if(row.orderValue == null||row.orderValue == ''){
		} else {
			orderValue = row.orderValue;
		}
		$("#_sino_devicetype_loadurl").empty();
		$(".page-header").empty();
		var titleStr = '<h2 id="myModalLabel">修改类别'
			+'<div class="pull-right"><button id="cancle" type="button" class="btn btn-default btn-lg">'
			+' <span class="icon-repeat"></span>'
			+'返回'
			+'</button></div>'
			+'</h2>'
		$(".page-header").append(titleStr);
		
		var typeCode = row.typeCode;
		if(typeCode == null){
			typeCode = "";
		}
		var str =  ""
			+"<div class='padding:10px 0 10px 60px;text-align: center;'>"		
			+"<form id='_sino_devicetype_editform' name='_sino_devicetype_editform' method='post' class='form-horizontal'>"
			+"<input type='hidden' name='id' value='"+row.id+"'>"
			+"<input type='hidden' id='_sino_base_picUrl' name='icon' value='"+row.icon+"'>"
			+"<input type='hidden' id='_sino_base_bigUrl' name='bgImage' value='"+row.bgImage+"'>"
			+"<div class='control-group' style='margin-bottom:30px;border: 2px solid #F3F3F3;width: 800px;'><label class='control-label' for='TypeName' style='margin-top: 30px;'>类别名称:</label><div class='controls'><input type='text' style='margin-top: 30px;' name='typeName' value='"+row.name+"' required data-validation-required-message='请输入菜单名称！'/> <p class='help-block'></p></div>"
			+"<div class='control-group' style='margin-bottom:30px;margin-top:20px'><label class='control-label' for='TypeName'>分类编码:</label><div class='controls'><input type='text' name='typeCode' value='"+typeCode+"' required data-validation-required-message='请输入分编代码！'/> <p class='help-block' style='font-size:12px;margin-top:-20px;margin-left:235px;color:red;'>注：编码需要唯一</p></div></div>"
			
			//+"<div class='control-group'><label class='control-label' for='IsModule'>部件标志</label><div class='controls'><label class='radio inline'>是<input type='radio' id='isModule_true' name='isModule' value='1'/></label>"
			//+"<label class='radio inline'>否<input type='radio' id='isModule_false' name='isModule' value='2'/></label></div></div>"
			//+"<div class='control-group'><label class='control-label' for='IsLogic'>是否逻辑设备</label><div class='controls'><label class='radio inline'>逻辑<input type='radio' id='isLogic_true' name='isLogic' value='1'/></label>"
			//+"<label class='radio inline'>物理<input type='radio' name='isLogic' id='isLogic_false' value='2'/></label></div></div>"
			+"<input type='hidden' name='isModule' id='isModule'  value='2'/>"
			+"<input type='hidden' name='isLogic' id='isLogic' value='2'/>"
			//+"<div class='control-group'><label class='control-label' for='Icon'>图标信息</label><div class='controls' id='_sino_devicetype_picmanage'><img id='_sino_base_picPath' src='"+ctx+row.icon+"' alt='预览' style='height: 60px; background-color: #ccc; border: 1px solid #333' /><a href='#_sino_partner_brand_logo_page' data-toggle='modal' role='button' data-toggle='modal' class='btn btn-primary'  id='_sino_base_icon'>点击上传</a><p class='help-block'></p></div></div>"
			//+"<div class='control-group'><label class='control-label' for='Icon'>背景图片</label><div class='controls'><img id='_sino_base_bigPath' src='"+ctx+row.bgImage+"' alt='预览' style='height: 60px; background-color: #ccc; border: 1px solid #333' /><a href='#_sino_partner_brand_logo_page' data-toggle='modal' role='button' data-toggle='modal' class='btn btn-primary'  id='_sino_base_big'>点击上传</a><p class='help-block'></p></div></div>"
			+"<div class='' style='margin-bottom:20px;'><label class='control-label' for='Icon'>排序字段:</label><div class='controls'><input type='number' name='orderValue' value='"+orderValue+"' data-validation-number-message='请输入排序字段整数！'/><p class='help-block'></p></div></div></div>" 				
						
			+"</form>"
			+"</div>"
			+"<div class='modal-footer' style='margin-top: 10px;width: 776px;'>"				
			+"<button class='btn btn-primary' id='btnConfirm'>保存分类</button >"
						
			;

		    $("#_sino_devicetype_loadurl").append(str);
            
		    if(isModule == 1){
				$("#isModule_true").attr("checked","checked");
			} else {
				$("#isModule_false").attr("checked","checked");
			}
		    if(isLogic == 1){
				$("#isLogic_true").attr("checked","checked");
			} else {
				$("#isLogic_false").attr("checked","checked");
			}

		    $("#_sino_base_icon").bind('click',function(){
		    	upload(this);
			 });
		    $("#_sino_base_big").bind('click',function(){
		    	upload(this);
			 });
	        $("#btnConfirm").unbind('click').click(function(){
	        	    url = ctx+"/business/projectm/productType/edit?tmp="+Math.random();
	        	    edit();
	          });
	        $("#cancle").unbind('click').click(function(){
	        	openurl('business/projectm/productType/productType_main');
        	  	        	
	        });
		}
	
	function edit(){
		$("#_sino_devicetype_editform").form('submit',{
	    	url:url,
	    	onSubmit:function(){
	    		return $(this).form('validate');
	    	},
            success: function(data) {
                // 设置表单提交完成使用方法
                   if(data=="success"){
                	  //  alert("ss3ss");
                		$('#alertMsg').empty();
	         			$('#alertMsg').append('<div class="alert alert-success"><strong>提示：</strong>操作成功！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
	         		    $(".alert").delay(2000).hide(0);
	         		    $(".close").click(function(){
	         		       $(".alert").hide();
	         		    });	
	         		   openurl('business/projectm/productType/productType_main');
                    }else{
                	   $('#alertMsg').empty();
	         			$('#alertMsg').append('<div class="alert alert-error"><strong>错误：</strong>操作失败，请稍后再试！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
	         		    $(".alert").delay(2000).hide(0);
	         		    $(".close").click(function(){
	         		    	$(".alert").hide();
	         		    });
                   }  
  
                    
                }
	    });
	}
	
	function del(){
		$('#alertMsg').empty();
		var row = $("#_sino_devicetype_treegrid").treegrid("getSelected"); 
		if(row == null){
			$('#alertMsg').append('<div class="alert alert-error"><strong>错误：</strong>请选择要删除的数据！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
			$(".alert").delay(2000).hide(0);
 		    $(".close").click(function(){
 		    	$(".alert").hide();
 		    });
			return;
		}
		if(row.children.length != 0){
			$('#alertMsg').append('<div class="alert alert-error"><strong>错误：</strong>不能直接删除父节点信息！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
			$(".alert").delay(2000).hide(0);
 		    $(".close").click(function(){
 		    	$(".alert").hide();
 		    });
			return;
		}
		UicDialog.Confirm("确定要删除这条数据吗？",function(){
			$.ajax({
				url: ctx+"/business/projectm/productType/del?devicetypeId="+row.id,  // 提交的页面
	            data: "", // 从表单中获取数据
	            type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
	            error: function(request) {     // 设置表单提交出错
	            	 $('#alertMsg').empty();
	         			$('#alertMsg').append('<div class="alert alert-error"><strong>错误：</strong>删除失败，请稍后再试！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
	         		    $(".alert").delay(2000).hide(0);
	         		    $(".close").click(function(){
	         		    	$(".alert").hide();
	         		    });
	            },
	            success: function(data) {
	            		$('#alertMsg').empty();
	         			$('#alertMsg').append('<div class="alert alert-success"><strong>提示：</strong>删除成功！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
	         		    $(".alert").delay(2000).hide(0);
	         		    $(".close").click(function(){
	         		    	$(".alert").hide();  
	         		    });
	         		   $('#_sino_devicetype_treegrid').treegrid('reload',row.target);
	            }
			});
			
        },function(){
        });
	}
	function openurl(murl){
		//$('.edit_list').load(ctx+'/'+murl+'?tmp='+Math.random());
		var options = {};
		options.murl = murl;
		$.openurl(options);
	}
	
	exports.init = function(){
		loadTreegrid();  
	}
});





	
