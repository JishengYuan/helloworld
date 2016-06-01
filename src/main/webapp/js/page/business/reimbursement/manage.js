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
	var reimTypeJson = {};
	var reim = {
			config: {
				module: 'reim',
				uicTable : 'uicTable',
	            namespace: '/business/reimbursement'
	        },
	        
	        methods :{
	        	initDocument : function(){
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=coursesType&?tmp="+ Math.random(),
		    			success : function(msg) {
		    				reimTypeJson = msg;
		    			}
		    		});
	        		reim.doExecute('initTable');
		        },
		       
		        initTable : function(){
		        	var grid=$("#"+reim.config.uicTable).uicTable({
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		buttons : [
		               
		                    { name: '添加', butclass: 'doAdd',onpress: reim.methods.toolbarItem},
		                    {  name: '删除', butclass: 'doDel',onpress: reim.methods.toolbarItem},
		        			],searchItems:'name',
	    			    columns:[
	    			        { code:'orderCode',name : '订单编码',process:reim.methods.handsHandler,width:'20%'},
		        			{ code:'supplierShortName',name : '供方简称',process:reim.methods.handler,width:'13%'},
		        			{ code:'coursesType', name :'科目类型',process:reim.methods.coursesType,width:'12%'},												
		        			{ code:'planTime', name :'发票时间',width:'15%'},
		        			{ code:'amount', name :'报销金额',width:'13%'},	
		        			{ code:'remark', name :'用途',width:'15%'},	
		        			{ code:'reimBursStatus', name :'状态',process:reim.methods.reimBursStatus,width:'12%'}	
			        ],
			        
			        url: ctx+reim.config.namespace+'/getTableGrid?orderId='+orderId+'&tmp='+Math.random(),
			        pageNo:1,
			        pageSize:10,
			        onLoadFinish:function(){
			        	
				        $('.reim_handler').popover({
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
					for(var i in reimTypeJson){
						if(row.coursesType == reimTypeJson[i].id){
							coursesType += reimTypeJson[i].name;
						}
					}
					return coursesType;
		        },
		        reimBursStatus : function(divInner, trid, row, count){
		        	var reimBursStatus = "";
		        	if(row.reimBursStatus == 1){
		        		reimBursStatus = "草稿";
		        	} else if(row.reimBursStatus == 2){
		        		reimBursStatus = "审批中";
		        	} else {
		        		reimBursStatus = "通过审批";
		        	}
		        	return reimBursStatus;
		        },
		        handsHandler : function(divInner, trid, row, count){
		        	var str = "";
		        	var dataContent="";
		        	str+=row.orderCode;
		        	if(row.reimBursStatus=='1'){
		        		dataContent="<span style='margin-left:10px;'><a href='"+ctx+"/business/reimbursement/saveOrUpdate?id="+row.id+"' target='_blank' class='_edit'><i class='icon-edit'></i>修改</a></span> ";
		        	}
		        	str+='<span spanId="'+row.id+'" style="margin-left:5px"><a href="#" name="reim_handler" data-content="'+dataContent+'" id="'+row.id+'" class="reim_handler"><i class="icon-hand-down"></i></a></span>';
		        	return str;
		        },
		        handler:function(divInner,trid,row,count){
		        	var str="";
		        	str+='<a style="color:#005EA7;" href="'+ctx+'/business/reimbursement/detail?id='+row.id+'" target="_blank" name="_supplierShortName">'+row.supplierShortName+'</a>';
		        	return str;
		        },
		        toolbarItem:function(com, trGrid){

		        	if (com=='doAdd'){
		        		var id=$("#id").val();
		    			var url=ctx+'/business/reimbursement/addReim?id='+id+'&tmp='+Math.random();
		    			window.open(url,'_blank');
		    		}
		        	if (com=='doDel'){
		        		var ids=$("#"+reim.config.uicTable).getTableCheckedRows();
		        		var id ='';
			    		for(var i=0;i<ids.length;i++){
			    			var orderCode=$('#row'+ids[i].id+' > td').eq(1).text();
			    			var reimBursStatus=$('#row'+ids[i].id+' > .tdLast').text().replace(/(^\s*)|(\s*$)/g, "");
			    			if(reimBursStatus!="草稿"){
			    				UicDialog.Error(orderCode+"无法删除！");
			    				return;
			    			}else{
			    				id+=ids[i].id+",";
			    			}
			    		}
				   	    var datas={'id':id};
					    var url = ctx+'/business/reimbursement/remove?tmp='+Math.random();
					    $.post(url,datas,
					    		function(data,status){
					            	if(status="success"){
					                	  UicDialog.Success("删除数据成功!",function(){
					                	  $("#"+reim.config.uicTable).tableReload();
					                	  });
					                  }else{
					                  	  UicDialog.Error("删除数据失败！");
					                  	  $("#"+reim.config.uicTable).tableReload();
					                  }
					   });
		        	}
		        },
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = reim.config.namespace + opts.url;
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
	        	var method =reim.methods[flag];
	        	if( typeof method === 'function') {
	        		return method(param);
	        	} else {
	        		alert('操作 ' + flag + ' 暂未实现！');
	        	}
	        }
	}
	exports.init = function() {
		reim.doExecute('initDocument');
	}
/*	//子页面关闭刷新该父页面
	childColseRefresh=function(){
		reim.doExecute('initDocument');
    }*/

});