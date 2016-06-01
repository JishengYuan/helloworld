define(function(require, exports, module) {
	var $ = require("easyui");
	require("uic/message_dialog");
    var scrWidth = screen.width;
	   
	function loadSearch(){
		$('input:radio[name="type"]').bind('click',function(){
			typeCheck(this);
       });
		$('#_sino_device_search').bind('click',function(){
			search();
       });
		$('#_sino_device_remove').bind('click',function(){
			_remove();
		});
	}
	function typeCheck(obj){
		var param = {};
		var val=$('input:radio[name="type"]:checked').val();
		if(val == 3000){
			$("#_sino_device_serviceType").show();
		} else {
			param.partnerType = "%"+val+"%";
			$("#_sino_partner_datagrid").datagrid('reload', param);
			$("#_sino_device_serviceType").hide();
		}
	}
	function search(){
		var param1 = {};
		var partnerName=$("#partnername").val();
		param1.partnerName = "%"+partnerName+"%";
		$("#_sino_partner_datagrid").datagrid('reload', param1);
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
	
	function _edit(id){
		$('.edit_list').load(ctx+'/confPartner/edit?id='+id+'&tmp='+Math.random());
	}
	
	exports.init = function(){
		loadSearch();
	}
});




	
