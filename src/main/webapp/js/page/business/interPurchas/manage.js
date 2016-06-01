define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
	require("confirm_dialog");
	require("uic/message_dialog");
	var StringBuffer = require("stringBuffer");
	//子页面关闭刷新该父页面
	childColseRefresh = function(){
			$("#uicTable").tableOptions({
    			pageNo : '1',
    			addparams:[]
    			});
    			$("#uicTable").tableReload();
		}
	var interPurchasTypeJson = {};
	var interPurchas = {
			config: {
				module: 'interPurchas',
				uicTable : 'uicTable',
	            namespace: '/business/interPurchas'
	        },
	        
	        methods :{
	        	initDocument : function(){
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=purchasStatus&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				interPurchasTypeJson = msg;
		    			}
		    		});
	        		interPurchas.doExecute('initTable');
		        },
		       
		        initTable : function(){
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#"+interPurchas.config.uicTable).uicTable({
		        		title : "商务采购-->办公设备采购",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		/*buttons : [
		               
		                    { name: '添加', butclass: 'doAdd',onpress: interPurchas.methods.toolbarItem},
		                    {  name: '删除', butclass: 'doDel',onpress: interPurchas.methods.toolbarItem},
		        			],*/
		        			searchItems:'name',
	    			    columns:[
	    			        { code:'creator',name : '申请人',/*process:interPurchas.methods.handsHandler,*/width:'15%'},
		        			{ code:'purchasCode',name : '编号',process:interPurchas.methods.handler,width:'20%'},
		        			{ code:'purchasName', name :'申请单名称',width:'20%'},												
		        			{ code:'expectedDeliveryTime', name :'期望到货日期',width:'15%'},
		        			{ code:'interOrderStatus', name :'处理状态',width:'15%'},
		        			{ code:'purchasStatus', name :'审批状态',process:interPurchas.methods.purchasStatus,width:'15%'}														
		        			
			        ],
			        url: ctx+interPurchas.config.namespace+'/getTableGrid?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:10,
			        onLoadFinish:function(){
			        	
			        	//var str = '<span style="margin-left:10px;"><a href="#" class="_edit"><i class="icon-edit"></i></a></span>&nbsp;<span style="margin-left:10px;margin-right:10px;"><a href="#" class="_payment"><i class="icon-list-alt"></i>付款计划</a></span> ';
				        $('.interPurchas_handler').popover({
				        	trigger :'focus',
			        		placement:'right',
			        		html:true,
			        		//content:str
			        	}).click(function(e) {
					        e.preventDefault();
					       
					    });
			        },
			        searchTitle:'',
			        searchTip:'',
			        searchFun:function(){
			        	$("#"+config.uicTable).tableOptions({
			        		pageNo : '1',
			        		addparams:[{
			        			name:"test",
			        			value:$('.searchs').find('input').first().val()
			        		}]
			        	});
			        	$("#"+interPurchas.config.uicTable).tableReload();
			        },
			        advancedFun:interPurchas.methods.advancedFun
		         });
		        	
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	$('.doAdvancedSearch').click();
		        	$('.doAdvancedSearch').hide();
		        	$(".advancedSearch",$("#"+interPurchas.config.uicTable)).load(ctx+interPurchas.config.namespace+'/search?tmp='+Math.random(),function(){
		        		$(".advancedSearch",$("#"+interPurchas.config.uicTable)).css("height","auto");
		        		//绑定“查询”按钮
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			interPurchas.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			interPurchas.doExecute('reload');
		        		});
		        		$(".advancedSearch",$("#"+interPurchas.config.uicTable)).css("height","auto");
			        	//绑定“添加”按钮
		        		$("#addInter_btn").unbind('click').click(function () {
		        			interPurchas.methods.toAddInter();
			        	});
		        		//绑定“删除”按钮
		        		$("#removeInter_btn").unbind('click').click(function () {
		        			interPurchas.methods.removeInter();
			        	});
		        	});
		        	//$(".advancedSearch",$("#"+interPurchas.config.uicTable)).css("height","auto");

		        },
		        purchasStatus : function(divInner, trid, row, count){
		        	var purchasStatus = "";
					for(var i in interPurchasTypeJson){
						if(row.purchasStatus == interPurchasTypeJson[i].id){
							purchasStatus += interPurchasTypeJson[i].name;
						}
					}
					return purchasStatus;
		        },
		       /* handsHandler : function(divInner, trid, row, count){
		        	var str = "";
		        	var dataContent="";
		        	str+=row.creator;
		        	if(row.purchasStatus=='1'){
		        		dataContent="<span style='margin-left:10px;'><a href='"+ctx+"/business/interPurchas/saveOrUpdate?id="+row.id+"' target='_blank' class='_edit'><i class='icon-edit'></i>修改</a></span>";
		        	}
		        	str+='<span spanId="'+row.id+'" style="margin-left:5px"><a href="#" name="interPurchas_handler" data-content="'+dataContent+'" id="'+row.id+'" class="interPurchas_handler"><i class="icon-hand-down"></i></a></span>';
		        	return str;
		        },*/
		        handler:function(divInner,trid,row,count){
		        	var str="";
		        	if(row.purchasStatus=='1'){
		        		str+='<a style="color:#005EA7;" href="'+ctx+'/business/interPurchas/saveOrUpdate?id='+row.id+'" target="_blank" name="_purchasCode">'+row.purchasCode+'</a>';
		        	}else{
		        		str+='<a style="color:#005EA7;" href="'+ctx+'/business/interPurchas/detail?id='+row.id+'" target="_blank" name="_purchasCode">'+row.purchasCode+'</a>';
		        	}
		        	return str;
		        },
		        toAddInter:function(){
	        		/*var opts = {};
	    			opts.url = "/addOrder";
	    			order.methods.openurl(opts);*/
		        	var url=ctx+'/business/interPurchas/addNote?tmp='+Math.random();
	    			window.open(url,'_blank');
		        },
		        removeInter:function(){
		        	var ids=$("#"+interPurchas.config.uicTable).getTableCheckedRows();
	        		var id ='';
		    		for(var i=0;i<ids.length;i++){
		    			var purchasName=$('#row'+ids[i].id+' > td').eq(3).text();
		    			var purchasStatus=$('#row'+ids[i].id+' > .tdLast').text().replace(/(^\s*)|(\s*$)/g, "");
		    			if(purchasStatus!="草稿状态"){
		    				UicDialog.Error(purchasName+"无法删除！");
		    				return;
		    			}else{
		    				id+=ids[i].id+",";
		    			}
		    		}
			   	    var datas={'id':id};
				    var url = ctx+'/business/interPurchas/remove?tmp='+Math.random();
				    $.post(url,datas,
				    		function(data,status){
				            	if(status="success"){
				                	  UicDialog.Success("删除数据成功!",function(){
				                	  $("#"+interPurchas.config.uicTable).tableReload();
				                	  });
				                  }else{
				                  	  UicDialog.Error("删除数据失败！");
				                  	  $("#"+interPurchas.config.uicTable).tableReload();
				                  }
				   });
		        },
		        /*toolbarItem:function(com, trGrid){
		        	if (com=='doAdd'){
		    			var url=ctx+'/business/interPurchas/addNote?tmp='+Math.random();
		    			window.open(url,'_blank');
		    		}
		        	if (com=='doDel'){
		        		var ids=$("#"+interPurchas.config.uicTable).getTableCheckedRows();
		        		var id ='';
			    		for(var i=0;i<ids.length;i++){
			    			var purchasName=$('#row'+ids[i].id+' > td').eq(3).text();
			    			var purchasStatus=$('#row'+ids[i].id+' > .tdLast').text().replace(/(^\s*)|(\s*$)/g, "");
			    			if(purchasStatus!="草稿状态"){
			    				UicDialog.Error(purchasName+"无法删除！");
			    				return;
			    			}else{
			    				id+=ids[i].id+",";
			    			}
			    		}
				   	    var datas={'id':id};
					    var url = ctx+'/business/interPurchas/remove?tmp='+Math.random();
					    $.post(url,datas,
					    		function(data,status){
					            	if(status="success"){
					                	  UicDialog.Success("删除数据成功!",function(){
					                	  $("#"+interPurchas.config.uicTable).tableReload();
					                	  });
					                  }else{
					                  	  UicDialog.Error("删除数据失败！");
					                  	  $("#"+interPurchas.config.uicTable).tableReload();
					                  }
					   });
		        	}
		        },*/
		        advancedFun:function(type) {

		        	if(type){
		        		$(".advancedSearch",$("#"+interPurchas.config.uicTable)).css("height","auto");
		        		
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			interPurchas.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			interPurchas.doExecute('reload');
		        		});
		        		//绑定“添加”按钮
		        		$("#addInter_btn").unbind('click').click(function () {
		        			interPurchas.methods.toAddInter();
			        	});
		        		//绑定“删除”按钮
		        		$("#removeInter_btn").unbind('click').click(function () {
		        			interPurchas.methods.removeInter();
			        	});
		        	} else {
		        	}
		        },
		        doAdvancedFun:function(){
		        	var creator=$('#creator').formUser("getValue");
		        	var expectedDeliveryTime=$('#expectedDeliveryTime').val();
		        	
		        	$("#"+interPurchas.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{
		        			name:"creator",
		        			value:creator
		        		},{
		        			name:"expectedDeliveryTime",
		        			value:expectedDeliveryTime
		        		},]
		        	});
		        	$("#"+interPurchas.config.uicTable).tableReload();
		        },
		        getUserName:function(){
		        	$('#creator').empty();
		        	var $fieldStaff = $("#creator");
	        		var optionss = {
	        			inputName : "staffValues",
	        			showLabel : false,
	        			width : "280",
	        			name : "code",
	        			value : "root",
	        			selectUser : false,
	        			radioStructure : true
	        		}
	        		optionss.addparams = [ {
	        			name : "code",
	        			value : "root"
	        		} ];
	        		$fieldStaff.formUser(optionss);
	        		if($('#flowStep').val() != 'show1'){
		        		$('#_userName_ul').hide();
		        	}
		        },
		        reload : function(){
		        	interPurchas.doExecute("getUserName");
		        	$('#expectedDeliveryTime').val("");
		        	$("#"+interPurchas.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+interPurchas.config.uicTable).tableReload();
		        },
		     /*   goBack : function(m){
		        	var options = {};
//		    		options.murl = vendor.config.namespace + m;
//		    		$.openurl(options);
		        },*/
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = interPurchas.config.namespace + opts.url;
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
	        doExecute:function(flag, param) {
	        	var method =interPurchas.methods[flag];
	        	if( typeof method === 'function') {
	        		return method(param);
	        	} else {
	        		alert('操作 ' + flag + ' 暂未实现！');
	        	}
	        }
	}
	exports.init = function() {
		interPurchas.doExecute('initDocument');
	}
	/*//子页面关闭刷新该父页面
	childColseRefresh=function(){
		interPurchas.doExecute('initDocument');
    }*/

});