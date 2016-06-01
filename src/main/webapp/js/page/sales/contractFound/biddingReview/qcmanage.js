define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	function initqc(){
	  $(".searchqc").unbind('click').click(function () {
		  var val= $("#search-queryqc").val();
		  $("#uicTable").empty();
		  $("#uicTable").load(ctx+"/sales/contractFoundOpt/qcListInfo",{"val":val});
	  });
		$("#uicTable").load(ctx+"/sales/contractFoundOpt/qcListInfo");
	}
	
	exports.init = function() {
		initqc();
	}
});