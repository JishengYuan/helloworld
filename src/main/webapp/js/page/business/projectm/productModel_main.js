define(function(require, exports, module) {
	var $ = require("jquery");
	require("easyui");
	var dataTable$ = require("dataTables");
	require("formTree");
	require("formSelect");
//	var uic$ = require("uic_Dropdown");
	require("DT_bootstrap");
	require("bootstrap");
	require("coverage");
	require("uic/message_dialog");
	require("js/plugins/uic/components/form/formPartnerInfo");
	function loadgrid(){
		//型号列表
		table=dataTable$('#taskTable').dataTable({
			"bProcessing": true,
			"bServerSide": true,
			"sAjaxSource":ctx+"/business/projectm/productModel/getList?tmp="+Math.random(), 
			"bRetrieve": true,
			"bSort": false,
			"bFilter": true,
			"sServerMethod": "POST",
			"aoColumns": [
						    { "mData": "productModel","mRender":function(data,row,obj){
				          		  rstatus="<div style='float:left;' >" +
				          		  				"<div style='display:block;float:left;'><a href='#' id ='"+obj.id+"' name='_sino_productModel_id'>"+data+"</a>&nbsp&nbsp&nbsp&nbsp&nbsp</div> " +
				          		  				"<div style='display:none;float:right;'>" +
				          		  					"<a  id ='"+obj.id+"'  href='#'  data-toggle='modal'  name='_sino_partner_brand_edit'><button title='修改' class='room-command-button room-edit-command-button roommodi'></button></a>" +
				          		  					"<a  id = '"+obj.id+"' href='#' name='_sino_partner_brand_del'>&nbsp<button title='删除' class='room-command-button room-delete-command-button roomdel'></button></a> " +
				          		  				"</div>" +
				          		  		 "</div>" ; 
				          		 if(typeof(optcustomer)!='undefined'&&optcustomer){
				          			 return rstatus;
				          		 }else{
				          			 return data;
				          		 }
				          		 
						    } },
							{ "mData": "productModelName" },
							{ "mData": "productPdlife" },
							{ "mData": "StopDate" },
							{ "mData": "stopSevDate" },
							{ "mData": "typeName" },
							{ "mData": "partnerFullName" }
						],
			"sDom": "<'row'<'bt5left'l><'bt5right partnersel'>r>t<'row'<'bt5left'i><'bt5right'p>>",
			"sPaginationType": "bootstrap",
			"oLanguage": {
				"sLengthMenu": "页显示_MENU_ ",
				"sInfo":"从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
				"sSearch":"检索:",
				"sEmptyTable":"没有数据",
				"sInfoEmpty": "显示0条数据",
				"oPaginate":{
					"sPrevious": "",
					"sNext":''
				}

				
			},
			fnDrawCallback:function(){
				$('.partnersel').empty();
				
				$('a[name="_sino_productModel_id"]').unbind('click').click(function () {
					var id =this.id;
					detail(id);
				});
				$('a[name="_sino_partner_brand_edit"]').unbind('click').click(function () {
					var id =this.id;
					_createOrEdit(id);
				});
				$('a[name="_sino_partner_brand_del"]').unbind('click').click(function () {
					var id =this.id;
					remove(id);
				});
				
				$('#taskTable tbody tr').each(function(){
					var tdd=$(this.childNodes[0]);
					var sss=$(tdd.children()[0]);
				    tdd.bind("mouseover",function(){
				    	$(sss.children()[1]).css('display','block'); 
				    });
				    
				    tdd.bind("mouseout",function(){
				    	$(sss.children()[1]).css('display','none'); 
				    });
				  
				})
			} 
		
		});
		
		getPartnerInfo("root");
		$("#_sino_product_type").formTree({	
			
			inputName : '_sino_product_type',
			//labelName : 'formTree',
			Checkbox : false,
			// onlyLeafCheck : true,
			animate : true,
			searchTree : true,
		
			tree_url : ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),// 顶层
			asyncUrl : ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),// 异步
			search_url : ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),// 搜索
			find_url :ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),// 精确定位
			url : '',
			asyncParam : ["id"],
			onSelect:function(node){
			//	getPartnerInfo(node.id);
				//getPartnerBrand('');
			},
			addparams : [{
						name : "productTypeId",
						value : "root"
					}],
			async : true
		});
		
		//加载厂商

		
		/*
		 * 绑定触发事件
		 */
		 
		 $("#_sino_partner_productmodel_create").bind('click',function(){
			 _createOrEdit('');
         });
		 
		 $("#_sino_partner_model_search").bind('click',function(){
			 searchModelTable();
         });
		 $("#_sino_partner_model_reload").bind('click',function(){
			 var options = {};
			options.murl = 'business/projectm/productModel/productModel_main';
			$.openurl(options);
         });

	}
	
	function detail(id){
		//$('.edit_list').load(ctx+'/business/projectm/productModel/productModel_saveOrUpdate?id='+id+'&tmp='+Math.random());
		var options = {};
		options.murl = 'business/projectm/productModel/productModel_detail';
		options.keyName = 'detailId';
		options.keyValue = id;
		$.openurl(options);
	}
	
	function _createOrEdit(id){
		//$('.edit_list').load(ctx+'/business/projectm/productModel/productModel_saveOrUpdate?id='+id+'&tmp='+Math.random());
		var options = {};
		options.murl = 'business/projectm/productModel/productModel_saveOrUpdate';
		options.keyName = 'id';
		options.keyValue = id;
		$.openurl(options);
	}
	function remove(id){
		UicDialog.Confirm("确定要删除这条数据吗？",function(){
			$.ajax({
				url: ctx+"/business/projectm/productModel/remove?id="+id,  // 提交的页面
	            type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
	            error: function(request) {     // 设置表单提交出错
	            },
	            success: function(data) {
	            	searchTable('');
	            	$('#alertMsg').empty();
         			$('#alertMsg').append('<div class="alert alert-success"><strong>提示：</strong>删除成功！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
         		    $(".alert").delay(2000).hide(0);
         		    $(".close").click(function(){
         		    	$(".alert").hide();  
         		    	
         		    });
	            	
	            }
			});
			
        },function(){
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
				$('#_sino_partner_partnerId').val(id);
			},
			processFun:function(id,name){
				$('#_sino_partner_partnerId').val(id);
			},
			submitFunP:function(id,name){
				$('#_sino_partner_partnerId').val(id);
			},
			deleteFun:function(){
				$('#_sino_partner_partnerId').val("");

			},
			width : "281"
		}
		optionss.addparams = [ {
			name : "code",
			value : productTypeId
		} ];
		$fieldStaff.formPartnerInfo(optionss);
	}
	
	function searchTable(param){
		table.fnFilter(param,0 );
		//table.fnSort( [ [1,param] ] );
	}
	
	function searchModelTable(){
//		var param =  "";
//		var productType = $("#_sino_product_type").formTree('getValue');
//		var partner = $(".ultra-select-input3").uic_Dropdown('getValue').id;
//		if(partner!= null&&partner != ""){
//			param = partner+"_"+"partnerId";
//		} else if(productType!= null&&productType != ""){
//			param = productType+"_"+"productType";
//		}
		var param  = "";
		var productType = $("#_sino_product_type").formTree('getValue');
		var partner = $("#_sino_partner_partnerId").val();
		var productName = $('#_sino_partner_product_name_i').val();
		if(productType != null&&productType != ""){
			param += productType+"_"+"productType";
		}
		if(partner != null&&partner != ""){
			if(param == ""){
				param += partner+"_"+"partnerId";
			} else {
				param += ","+ partner+"_"+"partnerId";
			}
		}
		if(productName != null&&productName != ""){
			if(param == ""){
				param += productName+"_"+"productModelName";
			} else {
				param += ","+ productName+"_"+"productModelName";
			}
		}
		table.fnFilter(param,0 );
		//table.fnSort( [ [1,param] ] );
	}
	
	
	exports.init = function(){
		loadgrid();  
	}
});