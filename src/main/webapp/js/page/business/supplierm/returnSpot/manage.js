define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("internetTable");
	require("formSelect");
	require("uic/message_dialog");
	require("formSubmit");
	var supplierTypeJson = {};
	var returnSpot = {
			config: {
				module: 'returnSpot',
				uicTable : 'uicTable',
	            namespace: '/business/supplierm/returnSpot',
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
	        		$("#_eoss_add_returnSpot_a").unbind('click').click(function () {
	        			returnSpot.doExecute('doAdd');
	        		});
		        	returnSpot.doExecute('initTable');
		        },
		        initTable : function(){
		        	
		        	var grid=$("#"+returnSpot.config.uicTable).uicTable({
		        		title : "供方返点列表页",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		buttons : [
		        			{  name: '新增', butclass: 'doAdd',onpress: returnSpot.methods.toolbarItem},
		                    {  name: '详情', butclass: 'doMod',onpress: returnSpot.methods.toolbarItem},
		                    {  name: '删除', butclass: 'doDel',onpress: returnSpot.methods.toolbarItem},
		        			],searchItems:'name',
	    			    columns:[
	    			        { code: 'supplierType',name : '供应商类型',process:returnSpot.methods.supplierType,width:'10%'},
		        			{ code: 'supplierCode',name : '供应商编号',width:'15%'},
		        			{ code:'shortName', name :'公司简称',width:'15%'},												
		        			{ code:'returnAmount', name :'返点数量',width:'15%'}									
		        			
			        ],
			        url: ctx+returnSpot.config.namespace+'/getList?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:13,
			        onLoadFinish:function(){
			        	$('a[name="_supplierName"]').unbind('click').click(function () {
							var id =this.id;
							var opts = {};
			    			opts.id = id;
			    			opts.url = "/detail";
			    			returnSpot.doExecute("openurl",opts);
						});
			        },
			        searchFun:function(){
			        	$("#"+returnSpot.config.uicTable).tableOptions({
			        		pageNo : '1',
			        		addparams:[{
			        			name:"test",
			        			value:$('.searchs').find('input').first().val()
			        		}]
			        	});
			        	$("#"+returnSpot.config.uicTable).tableReload();
			        },
			        advancedFun:returnSpot.methods.advancedFun
			        });
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	$(".advancedSearch",$("#"+returnSpot.config.uicTable)).load(ctx+returnSpot.config.namespace+'/search?tmp='+Math.random());

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
		        toolbarItem:function(com, trGrid){
		        	if (com=='doAdd'){
		        		var sd=$("#"+returnSpot.config.uicTable).getTableCheckedRows();
			        	if(sd.length == 0){
			    			UicDialog.Error("请选择数据！");
				    		return ;
			    		} else if(sd.length > 1){
			    			UicDialog.Error("只能选择一条数据！");
				    		return ;
			    		} else {
			    			var supplierId = sd[0].id;
			    			$("#_eoss_add_returnSpot_a").attr("supplierId",supplierId);
			    			$("#_eoss_add_returnSpot_a").attr("returnAmount",sd[0].ReturnAmount);
			    		}
		        		$("#_eoss_add_returnSpot_a").click();
//		    			returnSpot.doExecute("doAdd");
		    		}
		        	if (com=='doMod'){
		        		returnSpot.doExecute("doMod");
		        	}
		        	if (com=='doDel'){
		        		returnSpot.doExecute("doDel");
		        	}
		        },
		        advancedFun:function(type) {
		        	if(type){
		        		$(".advancedSearch",$("#"+returnSpot.config.uicTable)).css("height","auto");
		        		
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			returnSpot.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			returnSpot.doExecute('reload');
		        		});
		        		
		        	} else {
		        	}
		        },
		        doAdd:function(){
		        	$("#_eoss_add_returnSpot_div").empty();
		        	var str = '<div id="_eoss_add_returnSpot_page" name="_eoss_add_returnSpot_page" class="modal hide fade" tabindex="-1"  aria-labelledby="_eoss_add_returnSpot_page" aria-hidden="true" style="height:300px;"></div>';				    
		    		$("#_eoss_add_returnSpot_div").append(str);
		    		var spotPageUrl = ctx+returnSpot.config.namespace+"/addPage?tmp="+Math.random();
		    	    $("#_eoss_add_returnSpot_page").load(spotPageUrl,{id:$("#_eoss_add_returnSpot_a").attr("supplierId"),returnAmount:$("#_eoss_add_returnSpot_a").attr("returnAmount")},function(){
		    	    	$('#_eoss_business_supplier_spotAdd').unbind('click').click(function(){
		    	    		var supplierId = $('#supplierId').val();
		    	    		var returnSpotId = $('#returnSpotId').val();
		    	    		var returnType = $("input[name='returnType']:checked").val();
		    	    		var returnAmount = $('#returnAmount').val();
		    	    		var remark = $('#remark').val();
		    	    		
		    	    		var options = {};
		    				options.formId = "_eoss_business_returnspot_add";
		    				if(!$.formSubmit.doHandler(options)){
		    					return;
		    				}
		    				if(returnType == 2&&($('#_returnAmount').val() - returnAmount) < 0){
		    					UicDialog.Error("支出大于剩余填写有误！");
		    					return;
		    				}
		    	    		$.ajax({
		    					url: ctx+returnSpot.config.namespace+"/addReturnSpot?tmp="+Math.random(),  // 提交的页面
		    		            data: "supplierId="+supplierId+"&returnSpotId="+returnSpotId+"&returnType="+returnType+"&returnAmount="+returnAmount+"&remark="+remark, // 从表单中获取数据
		    		            type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
		    		            error: function(request) {     // 设置表单提交出错
		    		            	var opts = {};
		    		    			opts.url = "/manage";
		    		    			returnSpot.doExecute("openurl",opts);
		    		            },
		    		            success: function(data) {
		    		            	$('#_eoss_business_supplier_spotCancel').click();
		    		            	returnSpot.doExecute('reload');
//		    		            	var opts = {};
//		    		    			opts.url = "/manage";
//		    		    			returnSpot.doExecute("openurl",opts);
		    		            }
		    				});
		    	    	});
		    	    });
		        },
		        doMod:function(){
		        	var sd=$("#"+returnSpot.config.uicTable).getTableCheckedRows();
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
		    			returnSpot.doExecute("openurl",opts);
		    		}
		        },
		        doDel:function(){
		        	var sd=$("#"+returnSpot.config.uicTable).getTableCheckedRows();
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
		    					url: ctx+returnSpot.config.namespace+"/delReturnSpot?tmp="+Math.random(),  // 提交的页面
		    		            data: "ids="+ids, // 从表单中获取数据
		    		            type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
		    		            error: function(request) {     // 设置表单提交出错
		    		            	var opts = {};
		    		    			opts.url = "/manage";
		    		    			returnSpot.doExecute("openurl",opts);
		    		            },
		    		            success: function(data) {
		    		            	var opts = {};
		    		    			opts.url = "/manage";
		    		    			returnSpot.doExecute("openurl",opts);
		    		            }
		    				});
		    				
			            },function(){
			            });
		    		}
		        },
		        
		        doAdvancedFun:function(){
		        	var supplierType = $('#search_supplierType').val();
		        	var supplierCode = $('#search_supplierCode').val();
		        	var shortName = $('#search_shortName').val();
		        	var smallReturn = $('#search_smallReturn').val();
		        	var bigReturn = $('#search_bigReturn').val();
		        	
		        	var searchParam = "";
		        	if(supplierType　!= ""&&supplierType != null){
		        		searchParam+=supplierType+"_supplierType"
		        	}
		        	if(supplierCode　!= ""&&supplierCode != null){
		        		if(searchParam == ""){
		        			searchParam+=supplierCode+"_supplierCode"
		        		} else {
		        			searchParam+=","+supplierCode+"_supplierCode"
		        		}
		        	}
		        	if(shortName　!= ""&&shortName != null){
		        		if(searchParam == ""){
		        			searchParam+=shortName+"_shortName"
		        		} else {
		        			searchParam+=","+shortName+"_shortName"
		        		}
		        	}
		        	if(smallReturn　!= ""&&smallReturn != null){
		        		if(searchParam == ""){
		        			searchParam+=smallReturn+"_smallReturn"
		        		} else {
		        			searchParam+=","+smallReturn+"_smallReturn"
		        		}
		        	}
		        	if(bigReturn　!= ""&&bigReturn != null){
		        		if(searchParam == ""){
		        			searchParam+=bigReturn+"_bigReturn"
		        		} else {
		        			searchParam+=","+bigReturn+"_bigReturn"
		        		}
		        	}
		        	$("#"+returnSpot.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"searchParam",
		        			value:searchParam
		        		}]
		        	});
		        	$("#"+returnSpot.config.uicTable).tableReload();
		        },
		        reload : function(){
		        	$('#search_supplierType').val("");
		        	$('#supplierType').formSelect('setValue',"");
		        	$('#search_supplierCode').val("");
		        	$('#search_shortName').val("");
		        	$('#search_smallReturn').val("");
		        	$('#search_bigReturn').val("");
		        	$("#"+returnSpot.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+returnSpot.config.uicTable).tableReload();
		        },
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = "business/supplierm/returnSpot" + opts.url;
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
				var method =returnSpot.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	
	exports.init = function() {
		returnSpot.doExecute('initDocument');
	}
	
});