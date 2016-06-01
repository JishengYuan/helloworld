define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
	require("formSelect");
	require("uic/message_dialog");
	require("jquery.form");
	var StringBuffer = require("stringBuffer");
	var inventory = {
			config: {
				module: 'inventory',
				uicTable : 'uicTable',
	            namespace: '/business/inventory'
	        },
	        methods :{
	        	initDocument : function(){
		        	inventory.doExecute('initTable');
		        	$("#_sino_eoss_inventory_import").unbind('click').click(function () {
		        		inventory.doExecute('importProduct');
		        	});
		        },
		        importProduct:function(){

		        	$("#_sino_eoss_inventory_products_import_div").empty();
		    		var buffer = new StringBuffer();
		    		buffer.append('<div id="_sino_eoss_inventory_import_page" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="_sino_eoss_inventory_import_page" aria-hidden="true">');
		    		buffer.append('<div class="modal-header">');
		    		buffer.append('<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button >');
		    		buffer.append('<h3 id="myModalLabel">请选择Excel</h3 >');
		    		buffer.append('</div >');
		    		buffer.append('<div class="modal-body" >');
		    		
		    		buffer.append('<form id="_sino_eoss_article_advice" method="post" enctype="multipart/form-data">');
		    		buffer.append('<table class="table table-hover">');
		    		buffer.append('<tr><td>选择Excel文件：<input type="file" id="_salary_import_input" name="attachment"></td></tr>');
		    		
		    		buffer.append('</table>');
		    		buffer.append('</form>');
		    		
		    		buffer.append("</div>");	
		    		buffer.append('<div class="modal-footer" >');
		    		buffer.append('<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button >');
		    		buffer.append('<button class="btn btn-primary"  id="btnConfirmq" data-dismiss="modal">确定</button >');
		    		buffer.append('</div >');
		    		buffer.append('</div>');
		    		
		    		$("#_sino_eoss_inventory_products_import_div").append(buffer.toString());
		    		
		    		$('#btnConfirmq').unbind('click').click(function () {
		    			var url = ctx+"/business/inventory/uploadProducts"
			    		$("#_sino_eoss_article_advice").ajaxSubmit({
							url:url,
							dataType:'json',
						    success: function(data) {
						    	alert(data);
						    	if(data == 'success'){
						    		UicDialog.Success("导入成功!");
						    		inventory.doExecute('reload');
						    	} else {
						    		UicDialog.Error("导入失败!");
						    	}
						    },
						    error:function(data){
						    	UicDialog.Success("导入成功!");
					    		inventory.doExecute('reload');
						    }
						});
		    		});
		        
		        },
		        initTable : function(){
		        	
		        	var grid=$("#"+inventory.config.uicTable).uicTable({
		        		title : "公司备件->备件查询",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		buttons : [
		        			{name: '导入设备', butclass: 'doAdd',onpress: inventory.methods.toolbarItem}
		        			],searchItems:'name',
	    			    columns:[
	    			        { code: 'partner',name:'厂商',width:'10%'},
		        			{ code: 'productNo',name:'型号',width:'20%',process:inventory.methods.link},
		        			{ code:'seriesNo', name:'序列号',width:'18%'},										
		        			{ code: 'appearance',name:'新旧',width:'8%'},
		        			{ code:'location', name:'位置',width:'12%'}	,												
		        			{ code:'cost',name:'成本',width:'12%',process:inventory.methods.costFmt},													
		        			{ code:'rent',name:'租金/月',width:'10%',process:inventory.methods.rentFmt},												
		        			{ code:'state',name:'状态',process:inventory.methods.state,width:'10%'}													
		        			
			        ],
			        url: ctx+inventory.config.namespace+'/getList?tmp='+Math.random(),
			        pageNo:1,
			        searchTitle:'型号序列号搜索:',
			        showcheckbox:false,
			        pageSize:20,
			        onLoadFinish:function(){
			        	$('a[name="_supplierName"]').unbind('click').click(function () {
							var id =this.id;
							var opts = {};
			    			opts.id = id;
			    			opts.url = "/detail";
			    			inventory.doExecute("openurl",opts);
						});
			        },
			        searchFun:function(){
			        	var name = $("input[class='text']").first().val();
			        	$("#"+inventory.config.uicTable).tableOptions({
			        		pageNo : '1',
			        		addparams:[{
			        			name:"name",
			        			value:name
			        		}]
			        	});
			        	$("#"+inventory.config.uicTable).tableReload();
			        }
			        });
		        	$(".doAdvancedSearch").hide();
		        },
		        link:function(divInner, trid, row, count){
		        	var url = ctx + "/business/inventory/detail?id="+row.id;
		        	return '<a target="_blank" style="color:#005EA7;" href="'+url+'" >'+row.productNo+'</a>';
		        },
		        costFmt:function(divInner, trid, row, count){
		        	var cost = row.cost;
		        	if(cost == ""||cost == null){
						cost = 0;
					}
		        	return inventory.methods.fmoney(cost, 2);
		        },
		        rentFmt:function(divInner, trid, row, count){
		        	var rent = row.rent;
		        	if(rent == ""||rent == null){
		        		rent = 0;
					}
		        	return inventory.methods.fmoney(rent, 2);
		        },
		        fmoney:function(s,n){
		        	n = n > 0 && n <= 20 ? n : 2; 
		    		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		    		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
		    		t = ""; 
		    		for (i = 0; i < l.length; i++) { 
		    			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		    		} 
		    		return t.split("").reverse().join("") + "." + r;
		        },
		        state : function(divInner, trid, row, count){
		        	if(row.state == 0){
		        		return "在库";
		        	} else if(row.state == 1){
		        		return "待审批";
		        	} else if(divInner == 2){
		        		return "已借出";
		        	} else if(row.state == 3){
		        		return "待归还";
		        	}
		        },
		        toolbarItem:function(com, trGrid){
		        	if (com=='doAdd'){
		        		$("#_sino_eoss_inventory_import").click();
		    		}
		        },
		        reload:function(){
		        	$("#"+inventory.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+inventory.config.uicTable).tableReload();
		        },
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = "business/supplierm/inventory" + opts.url;
		    		if(opts.id){
		    			options.keyName = 'id';
		    			options.keyValue = opts.id;
		    		}
		    		$.openurl(options);
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =inventory.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		inventory.doExecute('initDocument');
	}
});