define(function(require, exports, module) {
	var $ = require("jquery");
	var dataTable = require("dataTables");
	require("DT_bootstrap");
	var StringBuffer = require("stringBuffer");
	require("uic/message_dialog");
	require("coverage");
	
	var confirmInbound = {
			config: {
				module: 'Excel',
				formId: '_excel_import_form',
	            inputId: '_excel_import_input',
	            importId: '_excel_import_action',
	            //当前操作的类型：增删改查等
	            namespace: '/business/order'
	        },
	        initDocument : function(){
	        	confirmInbound.initTable();
	        },
	        initTable : function(){
	        	var table=$('#taskTable').dataTable({
	        		 "columnDefs": [
	        		                { "visible": false, "targets": 0 }
	        		            ],
	    			"bProcessing": true,
	    			"bServerSide": true,
	    			"sAjaxSource":ctx +"/business/order/inboundTableList?tmp="+Math.random(), 
	    			"bRetrieve": true,
	    			"bFilter": true,
	    			"bSort": false,
	    			"sServerMethod": "POST",
	    			"aoColumns": [
	    						  { "mData": "inboundCode"},
	    						  { "mData": "productCode"},
	    						  { "mData": "productNum"},
	    						  { "mData": "storePlace","mRender":function(data,row,obj){
	    							  var storePlace = obj.storePlace;
	    							  var str = '';
	    							  if(storePlace == '1'){
	    								  str = '中电库房';
	    							  } else if(storePlace == '2'){
	    								  str = '机房库房';
	    							  } else if(storePlace == '3'){
	    								  str = '公司库房';
	    							  }
					          		  return str;
						  		  }},
	    						  { "mData": "inboundTime"},
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
	    			              "drawCallback":function(settings){
	    			            	  var api = this.api();
	    			                  var rows = api.rows( {page:'current'} ).nodes();
	    			                  var last=null;
	    			       
	    			                  api.column(0, {page:'current'} ).data().each( function ( group, i ) {
	    			                	  if ( last !== group ) {
	    			                          $(rows).eq( i ).before(
	    			                              '<tr class="group"><td colspan="5">入库单号:'+group+'<a name="_inboundCode_a" inboundCode="'+group+'" role="button" href="#_sino_eoss_order_products_relate_page" role="button" target="_self"  data-toggle="modal" aria-hidden="true"  style="float:right;" class="btn btn-primary">确认</a></td></tr>'
	    			                          );
	    			                          last = group;
	    			                      }
	    			                  } );
	    			                  
	    			                  $('a[name="_inboundCode_a"]').unbind('click').click(function () {
		    			  					var inboundCode = $(this).attr("inboundCode");
		    			  					confirmInbound.relateOrder(inboundCode);
		    			  				});
	    			              },
	        	});
	    		
	        },
	        relateOrder:function(inboundCode){
	        	$("#_sino_eoss_order_products_relate_page_div").empty();
	    		var buffer = new StringBuffer();
	    		buffer.append('<div id="_sino_eoss_order_products_relate_page" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="_sino_eoss_order_products_relate_page" aria-hidden="true">');
	    		$("#_sino_eoss_order_products_relate_page_div").append(buffer.toString());
	    		var url = ctx+"/business/order/getOrderListPage";
	    		$("#_sino_eoss_order_products_relate_page").load(url,function(){
	    			$("#_sales_contracts_relate").bind('click').click(function(){
	    				var url = ctx+"/business/order/relateOrderAndProduct?orderId="+$('input[name="radio_product"]:checked').val()+"&inboundCode="+inboundCode;
	    				 $.ajax({
							 url:url,
							 success : function(result){
								 if(result == 'success'){
									 UicDialog.Success("操作成功！",function(){
										 var options = {};
								    		options.murl = "business/order/confirmInbound";
								    		$.openurl(options);
				                  	  });
								 } else {
									 UicDialog.Error("保存数据失败！",function(){
				                  	  });
								 }
							 }
	    				 });
	    			});
	    		});
	        }
	}
	
	exports.init = function() {
    	confirmInbound.initDocument();
	}
});