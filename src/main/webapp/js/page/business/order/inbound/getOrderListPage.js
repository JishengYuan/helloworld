define(function(require, exports, module) {
	var $ = require("jquery");
	var DT_bootstrap = require("DT_bootstrap");
	var dataTable$ = require("dataTables");
	
	exports.init = function() {
		load();
	};

	function load() {
    	//列表
		var url = ctx+"/business/order/getProductOrderList"
		table=dataTable$('#orderTaskTable').dataTable({
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
		              { "mData": "orderCode","mRender": function (data,row,obj) { 
		            	  var str='';
		            	  str+="<a href='#' id='"+obj.id+"' name='_orderCode'>"+data+"</a>";
		            	  return str;
	          		  }},
		              { "mData":"orderName"}
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
			},fnDrawCallback:function(){
				
				$('#orderTaskTable tbody').find("tr").each(function(i,ele){
					$(ele).find("td").each(function(j,elem){
						$(elem).find("a").each(function(m,el){
							$(el).popover({
								title:'产品信息',
						 		trigger :'hover',
						 		placement:'right',
						 		content:showProductDetail(el),
						 		html:true	
						 	}).click(function(e) {
						 		e.preventDefault();
						 	});
						});
					});
				});
				
			}
		});	
		$('.dataTables_length').hide();
	}
	
	function showProductDetail(obj){
		var id = obj.id;
		var str = "";
		if(id != null&&id != ""){
			var url = ctx+"/business/order/getProductDetailByOrderId?id="+id;
			
			$.ajax({
				 url:url,
				 async : false,
				 dataType : "json", 
				 success : function(result){
					 str+="<ul>"
					 for(var i = 0;i < result.length;i++){
						 str+="<li>产品型号:"+result[i].productNo+" - - 产品数量:"+result[i].productNum+"</li>";
					 }
					 str+="</ul>"
				 }
			 });
			
		}
		return str;
	}

});