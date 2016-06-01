define(function(require, exports, module) {
	var css = require("js/plugins/ztree/zTreeStyle.css");
	var $ = require("jquery"); // 声明全局jquery对象
	var Map = require("map");
	require("bootstrap");
	require("formUser");
	require("bootstrap-datetimepicker");
	require("internetTable");
	var StringBuffer = require("stringBuffer");
	require("coverage");
	require("uic/message_dialog");

	var outboundStateJson = {};

	exports.init = function() {
		load();
	};

	function load() {
		$("#_sino_eoss_sales_products_relate").unbind('click').click(function() {
			_relateSalesContracts();
		});
		$("#_input_del_product").unbind('click').click(function() {
			var b = $(this).attr("checked");
			if(b){
				$("input[name='_del_product']").attr("checked",true);
			} else {
				$("input[name='_del_product']").attr("checked",false);
			}
		});
		$("#_a_del_product").unbind('click').click(function() {
			$("input[name='_del_product']").each(function(i,ele){
				if($(this).attr("checked")&&$(this).val() != '0'){
					$(this).parent().parent().remove();
				}
			});
		});
	}

	function _relateSalesContracts() {
		$("#_sino_eoss_sales_products_import_div").empty();
		var buffer = new StringBuffer();
		buffer.append('<div id="_sino_eoss_sales_products_import_page" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="_sino_eoss_sales_products_import_page" aria-hidden="true">');
		$("#_sino_eoss_sales_products_import_div").append(buffer.toString());
		var url = ctx + "/outStorage/outboundView";
		$("#_sino_eoss_sales_products_import_page").load(url, function() {
			
		});
	}
	
	function openurl(){
		//$('.edit_list').load(ctx+'/business/projectm/productModel/productModel_main?tmp='+Math.random());
		var options = {};
		options.murl = 'outStorage/outStorage';
		$.openurl(options);
	}
});