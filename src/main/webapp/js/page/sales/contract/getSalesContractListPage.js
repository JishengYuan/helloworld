define(function(require, exports, module) {
	var css = require('js/plugins/ztree/zTreeStyle.css');
	var $ = require("jquery");
	var Map = require("map");
	var DT_bootstrap = require("DT_bootstrap");
	var zTree = require("zTree_core");
	var zTree_excheck = require("zTree_excheck");
	var dataTable$ = require("dataTables");
	require("formUser");
	
	exports.init = function() {
		load();
	};

	function load() {
    	//列表
		var url = ctx+"/sales/contract/getSalesContractList?contractType=9000"
		table=dataTable$('#taskTable').dataTable({
			"bProcessing": true,
			"bServerSide": true,
			"sAjaxSource":url,
			"bSort":false,
			"iDisplayLength": 5,
			"bRetrieve": true,
			"bFilter": true,
			"sServerMethod": "POST",
			"aoColumns": [
					  { "mData":"id" ,"mRender": function (data,row,obj) { 
		            	  var rstatus='';
		            	  var id = data;           	  
		            	  rstatus="<input type='radio' name='radio_product' value='"+id+"'/>";

	          		  return rstatus;
		              } },
		              { "mData":"ContractCode"},	    
		              { "mData": "ContractName"}
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
		});	
		$('.dataTables_length').hide();
	}

});