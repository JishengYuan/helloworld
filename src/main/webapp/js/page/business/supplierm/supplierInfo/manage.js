define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
	require("formSelect");
	require("uic/message_dialog");
	var supplierTypeJson = {};
	var bizOwnerJson = {};
	var supplierInfo = {
			config: {
				module: 'supplierInfo',
				uicTable : 'uicTable',
	            namespace: '/business/supplierm/supplierInfo',
            	supplierType:'supplierType',
            	supplierGrade:'supplierGrade',
            	relationShip:'relationShip',
            	relationGrade:'relationGrade'
	        },
	        methods :{
	        	initDocument : function(){
		    		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=supplierType&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				supplierTypeJson = msg;
		    			}
		    		});
		    		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx+"/business/supplierm/supplierInfo/getBizOwner?tmp="+Math.random(),
		    			success : function(msg) {
		    				bizOwnerJson = msg;
		    			}
		    		});
		        	supplierInfo.doExecute('initTable');
		        },
		        initTable : function(){
		        	
		        	var grid=$("#"+supplierInfo.config.uicTable).uicTable({
		        		title : "供方信息列表页",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		buttons : [
		        			{  name: '新增', butclass: 'doAdd',onpress: supplierInfo.methods.toolbarItem},
		                    {  name: '修改', butclass: 'doMod',onpress: supplierInfo.methods.toolbarItem},
		                    {  name: '删除', butclass: 'doDel',onpress: supplierInfo.methods.toolbarItem},
		        			],searchItems:'name',
	    			    columns:[
	    			        { code: 'supplierType',name : '供应商类型',process:supplierInfo.methods.supplierType,width:'10%'},
		        			{ code: 'supplierCode',name : '供应商编号',width:'10%'},
		        			{ code:'shortName', name :'公司简称',process:supplierInfo.methods.handler,width:'20%'},												
		        			{ code:'phone', name :'电话',width:'15%'},										
		        			{ code:'fax', name :' 传真',width:'15%'}	,												
		        			{ code:'bizOwner', name :' 商务负责人',process:supplierInfo.methods.bizOwner,width:'15%'},													
		        			{ code:'web', name :' 网址',width:'15%'}													
		        			
			        ],
			        url: ctx+supplierInfo.config.namespace+'/getList?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:13,
			        onLoadFinish:function(){
			        	$('a[name="_supplierName"]').unbind('click').click(function () {
							var id =this.id;
							var opts = {};
			    			opts.id = id;
			    			opts.url = "/detail";
			    			supplierInfo.doExecute("openurl",opts);
						});
			        },
			        searchTitle:'公司简称',
			        searchTip:'请输入公司简称',
			        searchFun:function(){
			        	$("#"+supplierInfo.config.uicTable).tableOptions({
			        		pageNo : '1',
			        		addparams:[{
			        			name:"test",
			        			value:$('.searchs').find('input').first().val()
			        		}]
			        	});
			        	$("#"+supplierInfo.config.uicTable).tableReload();
			        },
			        advancedFun:supplierInfo.methods.advancedFun
			        });
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	$(".advancedSearch",$("#"+supplierInfo.config.uicTable)).load(ctx+supplierInfo.config.namespace+'/search?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			supplierInfo.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			supplierInfo.doExecute('reload');
		        		});
		        	});
		        	$(".advancedSearch",$("#"+supplierInfo.config.uicTable)).css("height","auto");
		        	$('.doAdvancedSearch').click();
		        },
		        supplierType : function(divInner, trid, row, count){
		        	var supplierType = "";
					for(var i in supplierTypeJson){
						if(row.supplierType == supplierTypeJson[i].id){
							supplierType += supplierTypeJson[i].name;
						}
					}
					return supplierType;
		        },
		        bizOwner : function(divInner, trid, row, count){
		        	var bizOwner = "";
		        	for(var i in bizOwnerJson){
						if(row.bizOwner == bizOwnerJson[i].id){
							bizOwner += bizOwnerJson[i].name;
						}
					}
					return bizOwner;
		        },
		        handler : function(divInner, trid, row, count){
		        	var str = "";
		        	str+='<a style="color:#005EA7;" href="#" id="'+row.id+'" name="_supplierName">'+row.shortName+'</a>';
		        	return str;
		        },
		        
		        toolbarItem:function(com, trGrid){
		        	if (com=='doAdd'){
		        		var opts = {};
		    			opts.url = "/saveOrUpdate";
		    			supplierInfo.doExecute("openurl",opts);
		    		}
		        	if (com=='doMod'){
		        		supplierInfo.doExecute("doMod");
		        	}
		        	if (com=='doDel'){
		        		supplierInfo.doExecute("doDel");
		        	}
		        },
		        advancedFun:function(type) {
		        	if(type){
		        		$(".advancedSearch",$("#"+supplierInfo.config.uicTable)).css("height","auto");
		        		
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			supplierInfo.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			supplierInfo.doExecute('reload');
		        		});
		        		
		        	} else {
		        	}
		        },
		        doMod:function(){
		        	
		        	var sd=$("#"+supplierInfo.config.uicTable).getTableCheckedRows();
		        	if(sd.length == 0){
		    			UicDialog.Error("请选择数据！");
			    		return ;
		    		} else if(sd.length > 1){
		    			UicDialog.Error("只能选择修改一条数据！");
			    		return ;
		    		} else {
		    			var opts = {};
		    			opts.id = sd[0].id;
		    			opts.url = "/saveOrUpdate";
		    			supplierInfo.doExecute("openurl",opts);
		    		}
		        },
		        doDel:function(){
		        	var sd=$("#"+supplierInfo.config.uicTable).getTableCheckedRows();
		    		var ids = "";
		        	if(sd.length == 0){
		    			UicDialog.Success("请选择数据！");
			    		return ;
		    		} else {
		    			UicDialog.Confirm("确定要删除这"+sd.length+"条数据吗？",function(){
		    				for(var i=0;i<sd.length;i++){
		    					if(i == sd.length-1){
		    						ids+=sd[i].id
		    					} else {
		    						ids+=sd[i].id+","
		    					}
				    		}
		    				$.ajax({
		    					url: ctx+supplierInfo.config.namespace+"/detele?tmp="+Math.random(),  // 提交的页面
		    		            data: "ids="+ids, // 从表单中获取数据
		    		            type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
		    		            error: function(request) {     // 设置表单提交出错
		    		            	var opts = {};
		    		    			opts.url = "/manage";
		    		    			supplierInfo.doExecute("openurl",opts);
		    		            },
		    		            success: function(data) {
		    		            	var opts = {};
		    		    			opts.url = "/manage";
		    		    			//alert(data);
		    		    			if(data == "no"){
		    		    				UicDialog.Error("对不起，该条数据不能删除！");
		    		    			}
		    		    			supplierInfo.doExecute("openurl",opts);
		    		            }
		    				});
		    				
			            },function(){
			            });
		    		}
		        },
		        
		        doAdvancedFun:function(){
		        	var bizOwner = $('#search_bizOwner').val();
		        	var supplierType = $('#search_supplierType').val();
		        	var supplierCode = $('#search_supplierCode').val();
		        	var shortName = $('#search_shortName').val();
		        	$("#"+supplierInfo.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"bizOwner",
		        			value:bizOwner
		        		},{
		        			name:"supplierType",
		        			value:supplierType
		        		},{
		        			name:"supplierCode",
		        			value:supplierCode
		        		},{
		        			name:"shortName",
		        			value:shortName
		        		}]
		        	});
		        	$("#"+supplierInfo.config.uicTable).tableReload();
		        },
		        reload : function(){
		        	$('#search_bizOwner').val("");
		        	$('#bizOwner').formSelect('setValue',"");
		        	$('#search_supplierType').val("");
		        	$('#supplierType').formSelect('setValue',"");
		        	$('#search_supplierCode').val("");
		        	$('#search_shortName').val("");
		        	$("#"+supplierInfo.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+supplierInfo.config.uicTable).tableReload();
		        },
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = "business/supplierm/supplierInfo" + opts.url;
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
				var method =supplierInfo.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		supplierInfo.doExecute('initDocument');
	}
});