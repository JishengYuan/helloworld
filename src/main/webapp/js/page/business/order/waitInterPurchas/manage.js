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
	            namespace: '/business/order'
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
	        		/*
        			 *键盘点击事件
        			 */
        			document.onkeydown = function(event) {
        				var e = event || window.event || arguments.callee.caller.arguments[0];
        				if (e && e.keyCode == 13) { // enter 键
        					interPurchas.doExecute('doAdvancedFun');
        				}

        			}
		        },
		       
		        initTable : function(){
		        	require.async(ctx+"/js/plugins/uic/style/excellenceblue/uic/css/processList.css");	
		        	var grid=$("#"+interPurchas.config.uicTable).uicTable({
		        		title : "商务采购-->待下单的内部采购",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		buttons : [
		               
		        			],searchItems:'name',
	    			    columns:[
	    			        { code:'creator',name : '申请人',width:'15%'},
		        			{ code:'purchasCode',name : '编号',process:interPurchas.methods.handler,width:'15%'},
		        			{ code:'purchasName', name :'申请单名称',width:'25%'},												
		        			{ code:'expectedDeliveryTime', name :'期望到货日期',width:'20%'},
		        			{ code:'purchasStatus', name :'审批状态',process:interPurchas.methods.purchasStates,width:'10%'}														
		        			
			        ],
			        url: ctx+'/business/order/getInterPurchasList?tmp='+Math.random(),
			        pageNo:1,
			        pageSize:13,
			        onLoadFinish:function(){
			        	
			        	//var str = '<span style="margin-left:10px;"><a href="#" class="_edit"><i class="icon-edit"></i></a></span>&nbsp;<span style="margin-left:10px;margin-right:10px;"><a href="#" class="_payment"><i class="icon-list-alt"></i>付款计划</a></span> ';
				        $('.order_handler').popover({
			        		trigger :'click',
			        		placement:'right',
			        		html:true,
			        		//content:str
			        	}).click(function(e) {
					        e.preventDefault();
					        /*$('._edit').unbind('click').click(function () {
					        	var updateOrderId=$(this).parent().parent().parent().parent().attr("spanId");
					        	var options = {};
					    		options.murl = 'business/order/saveOrUpdate';
					    		options.keyName = 'id';
					    		options.keyValue = updateOrderId;
					    		$.openurl(options);
					        });*/
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
		        	$(".advancedSearch",$("#"+interPurchas.config.uicTable)).load(ctx+interPurchas.config.namespace+'/waitInterPurchas/search?tmp='+Math.random(),function(){
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			interPurchas.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			interPurchas.doExecute('reload');
		        		});
		        	});
		        	$(".advancedSearch",$("#"+interPurchas.config.uicTable)).css("height","auto");
		        	$('.doAdvancedSearch').click().hide();

		        },
		        purchasStates : function(divInner, trid, row, count){
		        	var purchasStates = "";
					for(var i in interPurchasTypeJson){
						if(row.purchasStatus == interPurchasTypeJson[i].id){
							purchasStates += interPurchasTypeJson[i].name;
						}
					}
					return purchasStates;
		        },
		        handsHandler : function(divInner, trid, row, count){
		        	var str = "";
		        	var dataContent="";
		        	str+=row.creator;
		        	if(row.orderStatus=='CG'){
		        		dataContent="<span style='margin-left:10px;'><a href='"+ctx+"/business/interPurchas/saveOrUpdate?id="+row.id+"' target='_blank' class='_edit'><i class='icon-edit'></i>修改</a></span>";
		        	}
		        	str+='<span spanId="'+row.id+'" style="margin-left:5px"><a href="#" name="order_handler" data-content="'+dataContent+'" id="'+row.id+'" class="order_handler"><i class="icon-hand-down"></i></a></span>';
		        	return str;
		        },
		        handler:function(divInner,trid,row,count){
		        	var str="";
		        	str+='<a style="color:#005EA7;" href="'+ctx+'/business/interPurchas/detail?id='+row.id+'" target="_blank" name="_purchasCode">'+row.purchasCode+'</a>';
		        	return str;
		        },
		        toolbarItem:function(com, trGrid){

		        	/*if (com=='doAdd'){
		        		//var opts = {};
		    			//opts.url = "/addOrder";
		    			//order.doExecute("openurl",opts);
		    			var url=ctx+'/business/interPurchas/addNote?tmp='+Math.random();
		    			window.open(url,'_blank');
		    		}
		        	if (com=='doDel'){
		        		var ids=$("#"+interPurchas.config.uicTable).getTableCheckedRows();
		        		var id ='';
			    		for(var i=0;i<ids.length;i++){
			    			id +=ids[i].id+",";
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
		        	}*/
		        },
		        advancedFun:function(type) {

		        	if(type){
		        		$(".advancedSearch",$("#"+interPurchas.config.uicTable)).css("height","auto");
		        		
		        		$("#advancedSearch_btn").unbind('click').click(function () {
		        			interPurchas.doExecute('doAdvancedFun');
			        	});
		        		$("#reset_btn").unbind('click').click(function () {
		        			interPurchas.doExecute('reload');
		        		});
		        	} else {
		        	}
		        },
		        doAdvancedFun:function(){
		        	var creator = $("#creator").formUser("getValue");
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
		    		options.murl = order.config.namespace + opts.url;
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