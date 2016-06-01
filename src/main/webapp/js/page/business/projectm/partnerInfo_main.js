define(function(require, exports, module) {
	var $ = require("easyui");
	
	require("jModuleGrid");
	require("coverage");
	require("uic/message_dialog");
	function loadDatagrid(){
	
		$("#_sino_partner_datagrid").datagrid({
			url:ctx+"/business/projectm/partnerInfo/getList?tmp="+Math.random(),
			title:"厂商信息表",
			loadMsg : "数据加载中，请稍后......",
			singleSelect : false,
			pagination:true,
			rownumbers: true,
			fitColumns:true,
//			width:$(document.body).width()*0.79-20,
			width:'100%',
			onBeforeLoad:function(param){
				_search();
//				$("#_sino_partner_datagrid").resizeDataGrid(0, $(this).width()*0.2-10, 0, $(this).width()*0.79-10);
//				var w = $(".datagrid").width();
//				$(".panel-header").css('width',w-30);
//				$(".datagrid-wrap").css('width',w-20);
			},
			onLoadSuccess : function(data){
//				$(window).bind('resize', function () {
//					$("#_sino_partner_datagrid").resizeDataGrid(0, $(this).width()*0.2-10, 0, $(this).width()*0.79-10);
//					var w = $(".datagrid").width();
//					$(".panel-header").css('width',w-30);
//					$(".datagrid-wrap").css('width',w-20);
//				});
				if($("a[id='_sino_partner_add']").length == 0){
					$("#_sino_device_remove").hide();
				}
				$('a[name="modibutton"]').unbind('click').click(function () {
			    	_edit(this.id);
				});
			},
			columns: [[{
	            field: 'id',
	            checkbox:true,
	            sortable: false
	        },{
	            field: 'partnerCode',
	            title: '厂商编码',
	            width: 90,
	            sortable: false,
	            formatter : partnerCode
	        }, {
	            field: 'partnerFullName',
	            title: '厂商名称',
	            width: 100,
	            sortable: false
	        }, {
	            field: 'partnerEnCode',
	            title: '英文名称',
	            width: 200,
	            sortable: false
	        },{
	            field: 'webUrl',
	            title: '厂商网址',
	            width: 250,
	            sortable: false
	        },{
	            field: 'address',
	            title: '联系地址',
	            width: 150,
	            sortable: false
	        },{
	            field: 'hotLine',
	            title: '服务热线',
	            width: 150,
	            sortable: false
	        }]],
	        toolbar :[{
				id : '_remove',
				text : ''
			}]
		});
		$('#_remove').remove();
	/*	var p = $("#_sino_partner_datagrid").datagrid('getPager');  
	    $(p).pagination({  
	        pageSize: 10,//每页显示的记录条数，默认为10  
	        pageList: [10,20,30,40,50],//可以设置每页记录条数的列表  
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
	      
	    }); */
	    
	    $('#_sino_partner_add').unbind('click').click(function () {
	    	_add();
	    });

	}
	function partnerCode(value,row){
		return "<a href='#' id='"+row.id+"' name='modibutton'>"+value+"</a>";
	}
	function _add(){
		//$('.edit_list').load(ctx+'/business/projectm/partnerInfo/add?tmp='+Math.random());
		$.openurl({'murl':'business/projectm/partnerInfo/add?tmp='+Math.random()});
	}
	function _edit(id){
		if($("a[id='_sino_partner_add']").length == 0){
			var options = {};
			options.murl = 'business/projectm/partnerInfo/detail';
			options.keyName = 'id';
			options.keyValue = id;
			$.openurl(options);
		}else{
			var options = {};
			options.murl = 'business/projectm/partnerInfo/edit';
			options.keyName = 'id';
			options.keyValue = id;
			$.openurl(options);
		}
		//$('.edit_list').load(ctx+'/business/projectm/partnerInfo/edit?id='+id+'&tmp='+Math.random());
	}
	function _search(){
		var toolbar = $("#_sino_partner_datagrid").datagrid('getToolbar');
		var toolbarSearch = toolbar.find("div.datagrid-toolbar-search");
		if(toolbarSearch.size() == 0){
			$("#_sino_partner_datagrid").datagrid('toggleSearchbar',{
	    		height : 10,
	    		url : ctx+"/business/projectm/partnerInfo/partnerInfo_Search?tmp="+Math.random()
	    	});
		}
    }
	function _remove(){
		var selections = $("#_sino_partner_datagrid").datagrid('getSelections');
		if(selections.length <= 0){
			UicDialog.Error("请选择数据!");
			return;
		}
		var id = '';
		for(var i = 0;i < selections.length;i++){
			id+=selections[i].id+",";
		}
		UicDialog.Confirm("确定要删除这条数据吗？",function(){
			$.ajax({
				url: ctx+"/business/projectm/partnerInfo/remove?id="+id,  // 提交的页面
	            data: "", // 从表单中获取数据
	            type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
	            error: function(request) {     // 设置表单提交出错
	            },
	            success: function(data) {
	            	//$.messager.alert('提示','删除成功!');
	            	$('#_sino_partner_datagrid').datagrid('reload');
	            	
	            }
			});
			
        },function(){
        });
	}
	
	exports.init = function(){
		loadDatagrid();
	}

	
});





	
