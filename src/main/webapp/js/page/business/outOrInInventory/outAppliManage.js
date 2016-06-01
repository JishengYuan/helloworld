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
	var outApp = {
			config: {
				module: 'outApp',
				uicTable : 'uicTable',
	            namespace: '/business/outOrInInventory'
	        },
	        methods :{
	        	initDocument : function(){
	        		outApp.doExecute('initTable');
		        	/*$("#_sino_eoss_inventory_import").unbind('click').click(function () {
		        		inventory.doExecute('importProduct');
		        	});*/
		        },
		        initTable : function(){
		        	
		        	var grid=$("#"+outApp.config.uicTable).uicTable({
		        		title : "公司备件->出库申请",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		buttons : [
		        			{name: '申请出库', butclass: 'doAdd',onpress: outApp.methods.toolbarItem}
		        			],
		        		searchItems:'name',
	    			    columns:[
	    			        { code: 'Partner',name:'厂商',width:'30%'},
		        			{ code: 'ProductNo',name:'型号',width:'40%',process:outApp.methods.link},
		        			{ code: 'totalNum',name:'总数量',width:'15%'},
		        			{ code: 'unNum',name:'可借数量',width:'15%',process:outApp.methods.UnNum}
		        			
				        ],
				        url: ctx+outApp.config.namespace+'/getOutAppliList?tmp='+Math.random(),
				        pageNo:1,
				        showcheckbox:true,
				        pageSize:100,
				        onLoadFinish:function(){
				        },
			        });
		        	$(".doAdvancedSearch").hide();
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	//$('.operation').hide();
		        },
		        toolbarItem:function(com, trGrid){
		        	var ids=$("#"+outApp.config.uicTable).getTableCheckedRows();
		    		if(ids.length==0){
	    				UicDialog.Error("请选择申请产品！");
	    				return;
		    		}
		    		var productNo="";
		    		for(var i=0;i<ids.length;i++){
		    			var name=ids[i].ProductNo;
		    			var unNum=ids[i].unNum;
		    			if(unNum=='0'||unNum==''||unNum==null){
		    				UicDialog.Error(name+"<br/>暂时无货，请延后申请！");
		    				return;
		    			}else{
		    				productNo +=name+",";
		    			}
		    		}
				    var url = ctx+'/business/outOrInInventory/outStorageAdd?product='+productNo+'&tmp='+Math.random();
				    window.open(url,'_blank');
		        },
		        UnNum:function(divInner, trid, row, count){
		        	if(divInner==''||divInner==null){
		        		return '0';
		        	}else{
		        		return divInner;
		        	}
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
				var method =outApp.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		outApp.doExecute('initDocument');
	}
	//子页面关闭刷新该父页面
	childColseRefresh=function(){
		$("#uicTable").tableOptions({
			pageNo : '1',
			addparams:[]
			});
			$("#uicTable").tableReload();
    }
});