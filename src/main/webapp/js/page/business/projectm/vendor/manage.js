define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
	var StringBuffer = require("stringBuffer");
	var vendor = {
			config: {
				module: 'vendor',
				uicTable : 'uicTable',
	            namespace: '/business/projectm/vendor'
	        },
	        methods :{
	        	initDocument : function(){
		        	vendor.doExecute('initTable');
		        },
		        initTable : function(){
		        	var grid=$("#"+vendor.config.uicTable).uicTable({
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		buttons : [
		        			{  name: '新增', butclass: 'doAdd',onpress: vendor.methods.toolbarItem},
		                    {  name: '修改', butclass: 'doMod',onpress: vendor.methods.toolbarItem},
		                    {  name: '删除', butclass: 'doDel'},
		                    {   name: '导入模块', butclass: 'add1',butChildren:true},
		                    {   name: '导出模块', butclass: 'modify2',butChildren:true}
		        			],searchItems:'name',
	    			    columns:[
	    			        { code: 'vendorCode',name : '厂商编号',process:vendor.methods.handler,width:'20%'},
		        			{ code: 'vendorName',name : '厂商中文名称',width:'30%'},
		        			{ code:'vendorEnName', name :'厂商英文名称',width:'30%'},												
		        			{ code:'currency', name :'币种',width:'20%'}													
		        			
			        ],
			        url: ctx+vendor.config.namespace+'/getList?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:13,
			        onLoadFinish:function(){
			        	var str = '<span style="margin-left:10px;"><a href="#" class="_edit"><i class="icon-edit"></i></a></span><span style="margin-left:10px;margin-right:10px;"><a href="#" class="_remove"><i class="icon-remove"></i></a></span>';
			        	
			        	$('.vendor_handler').popover({
			        		trigger :'click',
			        		placement:'right',
			        		html:true,
			        		content:str
			        	}).click(function(e) {
					        e.preventDefault();
					        $('._edit').unbind('click').click(function () {
					        	alert("edit操作ID="+$(this).parent().parent().parent().parent().attr("spanId"));
					        });
					        $('._remove').unbind('click').click(function () {
					        	alert("remove操作ID="+$(this).parent().parent().parent().parent().attr("spanId"));
					        });
					    });
			        },
			        searchTitle:'厂商编号',
			        searchTip:'请输入厂商编号',
			        searchFun:function(){
			        	$("#"+vendor.config.uicTable).tableOptions({
			        		pageNo : '1',
			        		addparams:[{
			        			name:"test",
			        			value:$('.searchs').find('input').first().val()
			        		}]
			        	});
			        	$("#"+vendor.config.uicTable).tableReload();
			        },
			        advancedFun:vendor.methods.advancedFun
			        });
		        	$(".advancedSearch",$("#"+vendor.config.uicTable)).load(ctx+vendor.config.namespace+'/search?tmp='+Math.random());

		        },
		        handler : function(divInner, trid, row, count){
		        	var str = "";
		        	str+=row.vendorCode;
		        	str+='<span spanId="'+row.id+'" style="margin-left:5px"><a href="#" name="vendor_handler" id="'+row.id+'" class="vendor_handler"><i class="icon-hand-down"></i></a></span>';
		        	return str;
		        },
		        
		        toolbarItem:function(com, trGrid){
		        	if (com=='doAdd'){
		    			alert("增加方法");
		    		}
		        	if (com=='doMod'){
		        		alert("修改方法");
		        	}
		        },
		        advancedFun:function(type) {
		        	if(type){
		        		$(".advancedSearch",$("#"+vendor.config.uicTable)).css("height","auto");
		        		
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			vendor.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			vendor.doExecute('reload');
		        		});
		        		
		        	} else {
		        	}
		        },
		        doAdvancedFun:function(){
		        	$("#"+vendor.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"test",
		        			value:1
		        		}]
		        	});
		        	$("#"+vendor.config.uicTable).tableReload();
		        },
		        reload : function(){
		        	var sd=$("#"+vendor.config.uicTable).getTableCheckedRows();
		    		for(var i=0;i<sd.length;i++){
		    			alert(sd[i].vendorCode);
		    		}
		        	$("#"+vendor.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+vendor.config.uicTable).tableReload();
		        },
		        goBack : function(m){
//		        	var options = {};
//		    		options.murl = vendor.config.namespace + m;
//		    		$.openurl(options);
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =vendor.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		vendor.doExecute('initDocument');
	}
});