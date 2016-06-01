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
	var paymentTypeJson = {};
	var payment = {
			config: {
				module: 'payment',
				uicTable : 'uicTable',
	            namespace: '/business/payment'
	        },
	        
	        methods :{
	        	initDocument : function(){
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=coursesType&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				paymentTypeJson = msg;
		    			}
		    		});
	        		payment.doExecute('initTable');
		        },
		       
		        initTable : function(){
		        	var grid=$("#"+payment.config.uicTable).uicTable({
		        		title : "",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		buttons : [
		                    { name: '添加', butclass: 'doAdd',onpress: payment.methods.toolbarItem},
		                    {  name: '删除', butclass: 'doDel',onpress: payment.methods.toolbarItem},
		        			],searchItems:'name',
	    			    columns:[
	    			        { code:'orderCode',name : '订单编码',process:payment.methods.handsHandler,width:'20%'},
		        			{ code:'supplierShortName',name : '供方简称',process:payment.methods.handler,width:'13%'},
		        			{ code:'coursesType', name :'科目类型',process:payment.methods.coursesType,width:'12%'},												
		        			{ code:'planTime', name :'付款时间',width:'15%'},
		        			{ code:'amount', name :'付款金额',width:'13%'},	
		        			{ code:'remark', name :'用途',width:'15%'},	
		        			{ code:'planStatus', name :'状态',process:payment.methods.paymentStatus,width:'12%'}	
		        			
		        			
			        ],
			        
			        url: ctx+payment.config.namespace+'/getTableGrid?&tmp='+Math.random(),
			        pageNo:1,
			        pageSize:10,
			        onLoadFinish:function(){
			        	
				        $('.payment_handler').popover({
			        		trigger :'focus',
			        		placement:'right',
			        		html:true,
			        	}).click(function(e) {
			        		 e.preventDefault();
					    });
			        },
		         });
		        	$('.searchs').find('input').first().hide();
		        	$('.searchs').find('label').first().hide();
		        	$('.doSearch').hide();
		        	$('.doAdvancedSearch').hide();
		        },
		        coursesType : function(divInner, trid, row, count){
		        	var coursesType = "";
					for(var i in paymentTypeJson){
						if(row.coursesType == paymentTypeJson[i].id){
							coursesType += paymentTypeJson[i].name;
						}
					}
					return coursesType;
		        },
		        paymentStatus : function(divInner, trid, row, count){
		        	var planStatus = "";
		        	if(row.planStatus == 1){
		        		planStatus = "草稿";
		        	} else if(row.planStatus == 2){
		        		planStatus = "审批中";
		        	} else {
		        		planStatus = "通过审批";
		        	}
		        	return planStatus;
		        },
		        handsHandler : function(divInner, trid, row, count){
		        	var str = "";
		        	var dataContent="";
		        	str+=row.orderCode;
		        	if(row.planStatus=='1'){
		        		dataContent="<span style='margin-left:10px;'><a href='"+ctx+"/business/payment/saveOrUpdate?id="+row.id+"' target='_blank' class='_edit'><i class='icon-edit'></i>修改</a></span> ";
		        	}
		        	str+='<span spanId="'+row.id+'" style="margin-left:5px"><a href="#" name="payment_handler" data-content="'+dataContent+'" id="'+row.id+'" class="payment_handler"><i class="icon-hand-down"></i></a></span>';
		        	return str;
		        },
		        handler:function(divInner,trid,row,count){
		        	var str="";
		        	str+='<a style="color:#005EA7;" href="'+ctx+'/business/payment/detail?id='+row.id+'" target="_blank" name="_supplierShortName">'+row.supplierShortName+'</a>';
		        	return str;
		        },
		        toolbarItem:function(com, trGrid){

		        	if (com=='doAdd'){
		        		var order=$("#order").val();
		    			var url=ctx+'/business/payment/addPayment?order='+order+'&tmp='+Math.random();
		    			window.open(url,'_blank');
		    		}
		        	if (com=='doDel'){
		        		var ids=$("#"+payment.config.uicTable).getTableCheckedRows();
		        		var id ='';
			    		for(var i=0;i<ids.length;i++){
			    			var orderCode=$('#row'+ids[i].id+' > td').eq(1).text();
			    			var planStatus=$('#row'+ids[i].id+' > .tdLast').text().replace(/(^\s*)|(\s*$)/g, "");
			    			if(planStatus!="草稿"){
			    				UicDialog.Error(orderCode+"无法删除！");
			    				return;
			    			}else{
			    				id+=ids[i].id+",";
			    			}
			    		}
				   	    var datas={'id':id};
					    var url = ctx+'/business/payment/remove?tmp='+Math.random();
					    $.post(url,datas,
					    		function(data,status){
					            	if(status="success"){
					                	  UicDialog.Success("删除数据成功!",function(){
					                	  $("#"+payment.config.uicTable).tableReload();
					                	  });
					                  }else{
					                  	  UicDialog.Error("删除数据失败！");
					                  	  $("#"+payment.config.uicTable).tableReload();
					                  }
					   });
		        	}
		        },
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = payment.config.namespace + opts.url;
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
	        	var method =payment.methods[flag];
	        	if( typeof method === 'function') {
	        		return method(param);
	        	} else {
	        		alert('操作 ' + flag + ' 暂未实现！');
	        	}
	        }
	}
	exports.init = function() {
		payment.doExecute('initDocument');
	}
	/*//子页面关闭刷新该父页面
	childColseRefresh=function(){
		payment.doExecute('initDocument');
    }*/

});