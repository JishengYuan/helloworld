define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("internetTable");
	require("formSelect");
	require("uic/message_dialog");
	require("formSubmit");
	var supplierTypeJson = {};
	var contractMerge = {
			config: {
				module: 'contractMerge',
				uicTable : 'uicTable',
	            namespace: '/sales/contractMerge'
	        },
	        methods :{
	        	initDocument : function(){
	        		$("#_eoss_add_contractMerge_a").unbind('click').click(function () {
	        			contractMerge.doExecute('doAdd');
	        		});
		        	contractMerge.doExecute('initTable');
		        },
		        initTable : function(){
		        	
		        	var grid=$("#"+contractMerge.config.uicTable).uicTable({
		        		title : "合同管理-->合同合并",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		buttons : [
		        			{  name: '新增', butclass: 'doAdd',onpress: contractMerge.methods.toolbarItem},
		                    {  name: '删除', butclass: 'doDel',onpress: contractMerge.methods.toolbarItem},
		        			],
	    			    columns:[
	    			        { code: 'name',name : '名称',process:contractMerge.methods.name,width:'30%'},
		        			{ code: 'applyTime',name : '申请时间',width:'15%'},
		        			{ code:'state', name :'状态',width:'15%',process:contractMerge.methods.state},												
		        			{ code:'remark', name :'备注',width:'40%'}									
		        			
			        ],
			        url: ctx+contractMerge.config.namespace+'/getList?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:13,
			        onLoadFinish:function(){
			        	$('a[name="_apply_name"]').unbind('click').click(function () {
							var id =this.id;
							var opts = {};
			    			opts.id = id;
			    			opts.url = "/detail";
			    			contractMerge.doExecute("openurl",opts);
						});
			        }
			        });

		        },
		        name : function(divInner, trid, row, count){
					var str = "<a style='color:#005EA7;' href='#' id='"+row.id+"' name='_apply_name'>"+divInner+"</a>";
		        	return str;
		        },
		        state : function(divInner, trid, row, count){
		        	if(row.state == '0'){
		        		return "审批未通过";
		        	}
		        	if(row.state == '1'){
		        		return "提交审批";
		        	}
		        	if(row.state == '2'){
		        		return "审批通过";
		        	}
		        },
		        toolbarItem:function(com, trGrid){
		        	if (com=='doAdd'){
		        		contractMerge.doExecute("saveOrUpdate");
		    		}
		        	if (com=='doMod'){
		        		contractMerge.doExecute("doMod");
		        	}
		        	if (com=='doDel'){
		        		contractMerge.doExecute("doDel");
		        	}
		        },
		        saveOrUpdate:function(){
		        	var opts = {};
		        	opts.url = "/saveOrUpdate"
		        	contractMerge.doExecute("openurl",opts);
		        },
		        doMod:function(){
		        	var sd=$("#"+contractMerge.config.uicTable).getTableCheckedRows();
		        	if(sd.length == 0){
		    			UicDialog.Error("请选择数据！");
			    		return ;
		    		} else if(sd.length > 1){
		    			UicDialog.Error("只能选择修改一条数据！");
			    		return ;
		    		} else {
		    			var opts = {};
		    			opts.id = sd[0].id;
		    			opts.url = "/detail";
		    			contractMerge.doExecute("openurl",opts);
		    		}
		        },
		        doDel:function(){
		        	var sd=$("#"+contractMerge.config.uicTable).getTableCheckedRows();
		    		var ids = "";
		        	if(sd.length == 0){
		    			UicDialog.Success("请选择数据！");
			    		return ;
		    		} else if(sd.length > 1){
		    			UicDialog.Error("只能选择删除一条数据！");
			    		return ;
		    		} else {
		    			var state = sd[0].state;
		    			if(state != '0'){
		    				UicDialog.Error("只有审批不通过的才能删除！");
				    		return ;
		    			}
		    			UicDialog.Confirm("确定要删除这"+sd.length+"条数据吗？",function(){
		    				for(var i=0;i<sd.length;i++){
		    					if(i == sd.length-1){
		    						ids+=sd[i].id
		    					} else {
		    						ids+=sd[i].id+","
		    					}
				    		}
		    				$.ajax({
		    					url: ctx+contractMerge.config.namespace+"/delete?tmp="+Math.random(),  // 提交的页面
		    		            data: "id="+ids, // 从表单中获取数据
		    		            type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
		    		            error: function(request) {     // 设置表单提交出错
		    		            	var opts = {};
		    		    			opts.url = "/manage";
		    		    			contractMerge.doExecute("openurl",opts);
		    		            },
		    		            success: function(data) {
		    		            	var opts = {};
		    		    			opts.url = "/manage";
		    		    			contractMerge.doExecute("openurl",opts);
		    		            }
		    				});
		    				
			            },function(){
			            });
		    		}
		        },
		        
		        reload : function(){
		        	$('#search_supplierType').val("");
		        	$('#supplierType').formSelect('setValue',"");
		        	$('#search_supplierCode').val("");
		        	$('#search_shortName').val("");
		        	$('#search_smallReturn').val("");
		        	$('#search_bigReturn').val("");
		        	$("#"+contractMerge.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+contractMerge.config.uicTable).tableReload();
		        },
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = "sales/contractMerge" + opts.url;
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
				var method =contractMerge.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	
	exports.init = function() {
		contractMerge.doExecute('initDocument');
	}
	
});