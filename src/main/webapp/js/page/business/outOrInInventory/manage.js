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
	var outOrInInventory = {
			config: {
				module: 'outOrInInventory',
				uicTable : 'uicTable',
	            namespace: '/business/outOrInInventory'
	        },
	        methods :{
	        	initDocument : function(){
		        	outOrInInventory.doExecute('initTable');
		        	$("#_sino_eoss_outOrInInventory_import").unbind('click').click(function () {
		        		outOrInInventory.doExecute('importProduct');
		        	});
		        	
		        	outOrInInventory.doExecute("initTable");
		        	outOrInInventory.doExecute("initTable1");
        			$("#_outInfo").unbind('click').click(function () {
        				$("#_inInfo").parent().addClass("button");
        				$("#_inInfo").parent().removeClass("sel");
        				$(this).parent().addClass("sel");
        				$(this).parent().removeClass("button");
        				$("#uicTable").show();
        				$("#uicTable1").hide();
        			});
        			$("#_inInfo").unbind('click').click(function () {
        				$("#_outInfo").parent().addClass("button");
        				$("#_outInfo").parent().removeClass("sel");
        				$(this).parent().addClass("sel");
        				$(this).parent().removeClass("button");
        				$("#uicTable1").show();
        				$("#uicTable").hide();
        			});
        			$("#_outInfo").click();
        			
        			
        			$('#_outStorage').unbind('click').click(function () {
						var id =this.id;
						var url=ctx+'/business/outOrInInventory/outStorageView';
						window.open(url,'_blank');
					});
        			$('#_inStorage').unbind('click').click(function () {
						var id =this.id;
						var url=ctx+'/business/putOrInInventory/putStorageView';
						window.open(url,'_blank');
					});
		        	
		        },
		        importProduct:function(){

		        	$("#_sino_eoss_outOrInInventory_products_import_div").empty();
		    		var buffer = new StringBuffer();
		    		buffer.append('<div id="_sino_eoss_outOrInInventory_import_page" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="_sino_eoss_outOrInInventory_import_page" aria-hidden="true">');
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
		    		
		    		$("#_sino_eoss_outOrInInventory_products_import_div").append(buffer.toString());
		    		
		    		$('#btnConfirmq').unbind('click').click(function () {
		    			var url = ctx+"/business/outOrInInventory/uploadProducts"
			    		$("#_sino_eoss_article_advice").ajaxSubmit({
							url:url,
							dataType:'json',
						    success: function(data) {
						    	alert(data);
						    	if(data == 'success'){
						    		UicDialog.Success("导入成功!");
						    		outOrInInventory.doExecute('reload');
						    	} else {
						    		UicDialog.Error("导入失败!");
						    	}
						    },
						    error:function(data){
						    	UicDialog.Success("导入成功!");
					    		outOrInInventory.doExecute('reload');
						    }
						});
		    		});
		        
		        },
		        initTable : function(){
		        	var grid=$("#"+outOrInInventory.config.uicTable).uicTable({
		        		//title : "公司资产->资产查询",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
//		        		buttons : [
//		        			{name: '导入设备', butclass: 'doAdd',onpress: outOrInInventory.methods.toolbarItem}
//		        			],searchItems:'name',
	    			    columns:[
	    			        { code: 'serialNum',name:'借货编号',width:'20%',process:outOrInInventory.methods.detail1},
		        			{ code: 'borrowTime',name:'借货时间',width:'15%'},
		        			{ code:'customerManageName', name:'借货人',width:'10%'},										
		        			{ code: 'userDept',name:'用户单位',width:'30%'},								
		        			{ code:'state',name:'状态',process:outOrInInventory.methods.state,width:'10%'}															
			        ],
			        url: ctx+outOrInInventory.config.namespace+'/getList?tmp='+Math.random(),
			        pageNo:1,
			        showcheckbox:false,
			        pageSize:13,
			        onLoadFinish:function(){
			        	$('#_outStorage').unbind('click').click(function () {
							var id =this.id;
							var url=ctx+'/business/outOrInInventory/outStorageView';
							window.open(url,'_blank');
						});
			        	$('#_inStorage').unbind('click').click(function () {
							var id =this.id;
							var url=ctx+'/business/putOrInInventory/putStorageView';
							window.open(url,'_blank');
						});
			        },
			        searchFun:function(){
			        	var name = $("input[class='text']").first().val();
			        	$("#"+outOrInInventory.config.uicTable).tableOptions({
			        		pageNo : '1',
			        		addparams:[{
			        			name:"name",
			        			value:name
			        		}]
			        	});
			        	$("#"+outOrInInventory.config.uicTable).tableReload();
			        }
			        });
		        	$(".doAdvancedSearch").hide();
		        },
		        
		        
		        initTable1 : function(){
		        	var grid=$("#uicTable1").uicTable({
		        		//title : "公司资产->资产查询",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
//		        		buttons : [
//		        			{name: '导入设备', butclass: 'doAdd',onpress: outOrInInventory.methods.toolbarItem}
//		        			],searchItems:'name',
	    			    columns:[
	    			        { code:'serialNum',name:'归还编号',width:'20%',process:outOrInInventory.methods.detail2},
		        			{ code:'returnDate',name:'归还时间',width:'15%'},
		        			{ code:'returnUserName', name:'归还人',width:'10%'},													
		        			{ code:'state',name:'状态',process:outOrInInventory.methods.state2,width:'10%'}															
			        ],
			        url: ctx+outOrInInventory.config.namespace+'/getInInfoList?tmp='+Math.random(),
			        pageNo:1,
			        showcheckbox:false,
			        pageSize:13,
			        onLoadFinish:function(){
			        	$('#_outStorage').unbind('click').click(function () {
							var id =this.id;
							var url=ctx+'/business/outOrInInventory/outStorageView';
							window.open(url,'_blank');
						});
			        	$('#_inStorage').unbind('click').click(function () {
							var id =this.id;
							var url=ctx+'/business/putOrInInventory/putStorageView';
							window.open(url,'_blank');
						});
			        },
			        searchFun:function(){
			        	var name = $("input[class='text']").first().val();
			        	$("#"+outOrInInventory.config.uicTable).tableOptions({
			        		pageNo : '1',
			        		addparams:[{
			        			name:"name",
			        			value:name
			        		}]
			        	});
			        	$("#"+outOrInInventory.config.uicTable).tableReload();
			        }
			        });
		        	$(".doAdvancedSearch").hide();
		        },
		        
		        
		        detail1:function(divInner, trid, row, count){
		        	var url = ctx + "/business/outOrInInventory/detail?id="+row.id;
		        	return '<a target="_blank" style="color:#005EA7;" href="'+url+'" >'+row.serialNum+'</a>';
		        },
		        detail2:function(divInner, trid, row, count){
		        	var url = ctx + "/business/putOrInInventory/detail?id="+row.id;
		        	return '<a target="_blank" style="color:#005EA7;" href="'+url+'" >'+row.serialNum+'</a>';
		        },
		        
		        costFmt:function(divInner, trid, row, count){
		        	var cost = row.cost;
		        	if(cost == ""||cost == null){
						cost = 0;
					}
		        	return outOrInInventory.methods.fmoney(cost, 2);
		        },
		        rentFmt:function(divInner, trid, row, count){
		        	var rent = row.rent;
		        	if(rent == ""||rent == null){
		        		rent = 0;
					}
		        	return outOrInInventory.methods.fmoney(rent, 2);
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
		        		return "草稿";
		        	} else if(row.state == 1){
		        		return "待审批";
		        	} else if(divInner == 2){
		        		return "已借出";
		        	} else if(row.state == 3){
		        		return "待归还";
		        	}else if(row.state == 4){
		        		return "已归还";
		        	}
		        },
		        state2 : function(divInner, trid, row, count){
		        	if(row.state == 0){
		        		return "草稿";
		        	} else if(row.state == 1){
		        		return "待审批";
		        	} else if(divInner == 2){
		        		return "待审批";
		        	} else if(row.state == 3){
		        		return "已归还";
		        	}
		        },
		        handler : function(divInner, trid, row, count){
		        	var str = "";
		        	str+='<a style="color:#005EA7;" href="#" id="'+row.id+'" name="_supplierName">'+row.shortName+'</a>';
		        	return str;
		        },
		        toolbarItem:function(com, trGrid){
		        	if (com=='doAdd'){
		        		$("#_sino_eoss_outOrInInventory_import").click();
		    		}
		        },
		        reload:function(){
		        	$("#"+outOrInInventory.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+outOrInInventory.config.uicTable).tableReload();
		        },
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = "business/supplierm/outOrInInventory" + opts.url;
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
				var method =outOrInInventory.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	
	exports.init = function() {
		outOrInInventory.doExecute('initDocument');
	}
	//子页面关闭刷新该父页面
	childColseRefresh=function(){
		$("#"+outOrInInventory.config.uicTable).tableReload(); 
		$("#uicTable1").tableReload();
    }
});