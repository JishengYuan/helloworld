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
		// 列表
		var url = ctx + "/outStorage/outStorageInfo";
		require
				.async(ctx
						+ "/js/plugins/uic/style/excellenceblue/uic/css/processList.css");
		var grid = $("#uicTable").uicTable({
			title : "库存管理管理-->出库管理",
			width : 'auto',
			height : 'auto',
			gridClass : "bbit-grid",
			searchItems : 'name',
			showcheckbox : false,
			columns : [ [ {
				code : 'outboundCode',
				name : '出库单号',
				width : '20%'
			} ], [ {
				code : 'contractCode',
				name : '合同编号',
				width : '20%'
			} ], [ {
				code : 'productCode',
				name : '产品型号',
				width : '20%'
			} ], [ {
				code : 'productNum',
				name : '产品数量',
				width : '20%'
			} ],[ {
				code : 'outboundTime',
				name : '出库时间',
				width : '20%'
			} ] ],
			url : url,
			pageNo : 1,
			pageSize : 10,
			moreCellShow : true,
			advancedFun : advancedFun
		});
		$('.searchs').find('input').first().hide();
		$('.searchs').find('label').first().hide();
		$('.doSearch').hide();
		$('.doAdvancedSearch').hide();
		// 默认触发了搜索
		$('.doAdvancedSearch').click();
		// 加载查询
		$(".advancedSearch", $("#uicTable")).load(ctx + '/outStorage/outStockSearch?tmp=' + Math.random(),
				function() {
					$("#allQuery_search_div").parent().removeClass().addClass("advancedSearcha");

					$('.date').datetimepicker({
						pickTime : false
					});
					$("#advancedSearch_btn").unbind('click').click(function() {
						searchTable();
					});
					$("#advancedSearch_btn_add").unbind('click').click(function() {
						//$("#_sino_eoss_sales_products_relate").click();
						var options = {};
			    		options.murl = "outStorage/addOutStoragePage";
			    		$.openurl(options);
					});
				});
//				$("#_sino_eoss_sales_products_relate").bind('click').click(function() {
//					_relateSalesContracts();
//				});
	}

	function advancedFun(type) {
		if (true) {
			$(".advancedSearch", $("#uicTable")).css("height", "auto");
		} else {
		}
	}

	function _relateSalesContracts() {
		$("#_sino_eoss_sales_products_import_div").empty();
		var buffer = new StringBuffer();
		buffer.append('<div id="_sino_eoss_sales_products_import_page" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="_sino_eoss_sales_products_import_page" aria-hidden="true">');
		$("#_sino_eoss_sales_products_import_div").append(buffer.toString());
		var url = ctx + "/outStorage/outboundView";
		$("#_sino_eoss_sales_products_import_page").load(url, function() {
			$("#_sales_contracts_relate").bind('click').click(function() {
				var b = true;
				$('input[name="productNum"]').each(function(i,ele){
					var v = $(ele).attr("value");
					var num1 = $(ele).attr("num1");
					var num2 = $(ele).attr("num2");
					if(v <=0||v > num1||v > num2){
						b = false;
					}
				});
				if(b){
					var url = ctx + "/outStorage/doSalesOutboundProduct";
				    var datas=$("#_out_bound_form").serialize();
					$.post(url, datas, function(data, status) {
						if (data == "success") {
							UicDialog.Success("保存数据成功!", function() {
								openurl();
							});
						} else {
							UicDialog.Error("保存数据失败!", function() {
								openurl();
							});

						}
					});
				} else {
					UicDialog.Error("需要出库数量有误!", function() {
					});
				}
			});
		});
	}
	
	function openurl(){
		//$('.edit_list').load(ctx+'/business/projectm/productModel/productModel_main?tmp='+Math.random());
		var options = {};
		options.murl = 'outStorage/outStorage';
		$.openurl(options);
	}

	// 产品出库操作
	function outboundView(com, trGrid) {
		if (com == 'doCreate') {
			$("#_sino_eoss_sales_products_relate").click();
		}
	}
	// 创建表单提交方法
	function searchTable() {
		var contractCode = $("#search_contractCode").val();		//合同编号
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		$("#uicTable").tableOptions({
    		pageNo : '1',
    		addparams:[{
    			name:"orderCode",
    			value:orderCode
    		},{
    			name:"startTime",
    			value:startTime
    		},{
    			name:"endTime",
    			value:endTime
    		}]
    	});
    	$("#uicTable").tableReload();
	}
});