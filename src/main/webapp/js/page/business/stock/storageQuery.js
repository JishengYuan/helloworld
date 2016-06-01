define(function(require, exports, module) {
	var $ = require("jquery");	//声明全局jquery对象
	require("bootstrap");
	
	require("bootstrap-datetimepicker");
	require("internetTable");
	
	require("formSelect");
	require("formTree");
	require("js/plugins/uic/components/form/formPartnerInfo");
	exports.init = function() {
		load();
	};
	
	function load() {
		var url = ctx+"/storage/stockInfo";
		require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
    	var grid=$("#uicTable").uicTable({
    		title : "库存管理管理-->库存查询",
    		width : 'auto',
			height : 'auto',
			gridClass : "bbit-grid",
    		searchItems:'name',
    		showcheckbox : false,
		    columns:[
		             { code: 'TypeName',name : '产品类型',width:'25%'},
		             { code: 'PartnerFullName',name : '厂商名称',width:'25%'},
		             { code: 'ProductCode',name : '产品型号',width:'25%'},
		             { code: 'productNum',name : '数量',width:'25%'}
		    ],
		    url: url,
		    pageNo:1,
		    pageSize:10
//		    moreCellShow : true
//	        onLoadFinish:function(){
//	        	$('.contract_handler').popover({
//	        		trigger :'focus',
//	        		placement:'right',
//	        		html:true	
//	        	}).click(function(e) {
//			        e.preventDefault();
//			    });
//	        },
	        });
        	$('.searchs').find('input').first().hide();
        	$('.searchs').find('label').first().hide();
        	$('.doSearch').hide();
        	$('.doAdvancedSearch').hide();
        	//默认触发了搜索
        	$('.doAdvancedSearch').click();
        	//加载查询
        	$(".advancedSearch",$("#uicTable")).load(ctx+'/storage/search?tmp='+Math.random(),function(){
        		getProductType();
        		getPartnerInfo("");
        		$("#advancedSearch_btn").unbind('click').click(function () {
        			searchTable();
	        	});
        	});
		
	}
	
	function getProductType(){
		//类型
		$("#_sino_product_type").formTree({	
			inputName : '_sino_product_type',
			inputValue : $('#_sino_productTypeName').val(),
			Checkbox : false,
			animate : true,
			searchTree : true,
			required : true,
			width:"200",
			tree_url : ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),// 顶层
			search_url : ctx+"/business/projectm/productType/getTree",// 搜索
			url : '',
			asyncParam : ["id"],
			onSelect:function(node){
//				getProductModel('');
			},
			async : true
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
			},
			processFun:function(id,name){
			},
			submitFunP:function(id,name){
			},
			width : "220"
		}
		optionss.addparams = [ {
			name : "code",
			value : "root"
		} ];
//		optionss.resIds = $("#_partnerId").attr("value");
//		optionss.inputValue = $("#_partner_name").attr("value");
		$fieldStaff.formPartnerInfo(optionss);
	}
	function contractCodeFun(divInner, trid, row, count){
		var contractCode = row.contractCode;
		if(contractCode == null||contractCode == ''){
			return "<span style='color:#ccc'>无</span>";
		} else {
			return divInner;
		}
	}
	function outboundTimeFun(divInner, trid, row, count){
		var outboundTime = row.outboundTime;
		if(outboundTime == null||outboundTime == ''){
			return "<span style='color:#ccc'>无</span>";
		} else {
			return divInner;
		}
	}
	
	function storePlaceFun(divInner, trid, row, count){
		var storePlace = row.storePlace;
		var str = '';
		if(storePlace == '1'){
			str = '中电库房';
		} else if(storePlace == '2'){
			str = '机房库房';
		} else if(storePlace == '3'){
			str = '公司库房';
		}
		return str;
	}
	//创建表单提交方法
	function searchTable() {

		var typeId = $("#_sino_product_type").formTree('getValue');
		var partnerId = $("#_sino_partner_div").formPartnerInfo("getValue");
		var productCode = $("#search_orderCode").val();
		
		$("#uicTable").tableOptions({
    		pageNo : '1',
    		addparams:[{
    			name:"typeId",
    			value:typeId
    		},{
    			name:"partnerId",
    			value:partnerId
    		},{
    			name:"productCode",
    			value:productCode
    		}]
    	});
    	$("#uicTable").tableReload();
	}
	
});		