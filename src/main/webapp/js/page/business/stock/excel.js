define(function(require, exports, module) {
	var $ = require("jquery");
	var dataTable$ = require("dataTables");
	require("DT_bootstrap");
	require("bootstrap");
	require("formSubmit");
	require("easyui");
	require("coverage");
	require("uic/message_dialog");
	
	var excelImport = {
			config: {
				module: 'inboundBill',
				formId: '_excel_import_form',
	            inputId: '_excel_import_input',
	            importId: '_excel_sumit',
	            //当前操作的类型：增删改查等
	            namespace: '/inboundBill'
	        },
	        initDocument : function(){
	        	$("#"+excelImport.config.importId).unbind('click').click(function () {
	        		var btn = $(this);
	    		    btn.button('loading');
	    		    excelImport.excel(btn);
	    		});
	        	excelImport.initTable();
	        },
	        excel : function(btn){
	        	var fileName = $("#"+excelImport.config.inputId).val();
	        	if(fileName == ""){
	        		UicDialog.Error('请选择要上传的Excel!');
	        		btn.button('reset');
	        		return ;
	        	}
	        	var url = ctx + "/inboundBill/uploadExcel?storePlace="+$('#_storePlace_select').val()+"&tmp="+Math.random();
	        	$("#_excel_import_form").form('submit',{
	    	    	url:url,
	    	    	onSubmit:function(){
	    	    	},
	                success: function(data) {
	                	btn.button('reset');
	                	if(data == 'success'){
	                		excelImport.goBack("/inbound");
	                	} else {
	                		UicDialog.Error('Excel填写有误');
	                	}
                    }
	    	    });
	        },
	        initTable : function(){
	        	table=dataTable$('#taskTable').dataTable({
	    			"bProcessing": true,
	    			"bServerSide": true,
	    			"sAjaxSource":ctx +　excelImport.config.namespace+"/getInboundInfo?tmp="+Math.random(), 
	    			"bRetrieve": true,
	    			"bFilter": true,
	    			"bSort": false,
	    			"sServerMethod": "POST",
	    			"aoColumns": [
	    						  { "mData": "inboundCode"},
	    						  { "mData": "productCode"},
	    						  { "mData": "productNum"},
	    						  { "mData": "storePlace" ,"mRender":function(data,row,obj){
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
	    						  { "mData": "recipientName"},
	    						  { "mData": "inboundTime"}],
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
	    			              } 
	        	});
	        },
	        goBack : function(m){
	        	var options = {};
	    		options.murl = excelImport.config.namespace + m;
	    		$.openurl(options);
	        }
	}
	exports.init = function() {
		excelImport.initDocument();
	}
});