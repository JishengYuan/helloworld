define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	var Map = require("map");
	var DT_bootstrap = require("DT_bootstrap");
	var StringBuffer = require("stringBuffer");
	require("formSubmit");
	require("formSelect");
	require("formUser");
	require("formTree");
	require("easyui");
	require("uic/message_dialog");
	
	
	
	var supplierInfoDetail = {
			config: {
				module: 'supplierInfoDetail',
				//供应商类型
            	supplierType:'supplierType',
            	//供应商评级
            	supplierGrade:'supplierGrade',
            	//合作关系
            	relationShip:'relationShip',
            	//认证级别
            	relationGrade:'relationGrade',
            	//商务负责人
            	bizOwner:'bizOwner',
            	namespace:'/business/supplierm/supplierInfo'
	        },
	        methods :{
	        	initDocument : function(){
		        	supplierInfoDetail.doExecute('getFormSelect');
		        	$("#_eoss_business_submit").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/doApprove";
		    			supplierInfoDetail.doExecute("doApprove",opts);
		        	});
		        	$("#_eoss_business_back").unbind('click').click(function () {
		        		//projectApprove.methods.closeWindow();
		        		//supplierInfoDetail.doExecute("goBack",opts);
		        		supplierInfoDetail.methods.goBack();
		        	});
		        	
		        },
		        getFormSelect : function(){
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=supplierType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == $('#_supplierType').val()){
		    						$('#supplierType').html(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        	$.ajax({
		        		type : "GET",
		        		async : false,
		        		dataType : "json",
		        		url : ctx + "/sysDomain/findDomainValue?code=supplierGrade&tmp="+ Math.random(),
		        		success : function(msg) {
		        			for(var i in msg){
		        				if(msg[i].id == $('#_grade').val()){
		        					$('#supplierGrade').html(msg[i].name);
		        				}
		        			}
		        		}
		        	});
		        	$.ajax({
		        		type : "GET",
		        		async : false,
		        		dataType : "json",
		        		url : ctx + "/sysDomain/findDomainValue?code=relationShip&tmp="+ Math.random(),
		        		success : function(msg) {
		        			for(var i in msg){
		        				if(msg[i].id == $('#_relationShip').val()){
		        					$('#relationShip').html(msg[i].name);
		        				}
		        			}
		        		}
		        	});
		        	$.ajax({
		        		type : "GET",
		        		async : false,
		        		dataType : "json",
		        		url : ctx + "/sysDomain/findDomainValue?code=relationGrade&tmp="+ Math.random(),
		        		success : function(msg) {
		        			for(var i in msg){
		        				if(msg[i].id == $('#_relationGrade').val()){
		        					$('#relationGrade').html(msg[i].name);
		        				}
		        			}
		        		}
		        	});
		        	$.ajax({
		        		type : "GET",
		        		async : false,
		        		dataType : "json",
		        		url : ctx + "/business/supplierm/supplierInfo/getBizOwner?tmp="+ Math.random(),
		        		success : function(msg) {
		        			for(var i in msg){
		        				if(msg[i].id == $('#_bizOwner').val()){
		        					$('#bizOwner').html(msg[i].name);
		        				}
		        			}
		        		}
		        	});
		        },
		        
		        doApprove : function(opts){
		        	var options = {};
		        	options.murl = ctx+"/business/supplierm/supplierInfo/doApprove?tmp="+Math.random();
				    options.formId = "_eoss_business_supplier";
		    		var datas=$("#_eoss_business_supplier").serialize();

					if($.formSubmit.doHandler(options)){
	    					//启动遮盖层
				   	    	 $('#_progress1').show();
				   	    	 $('#_progress2').show();
	    					 $.post(options.murl,datas,
					    	          function(data,status){
					    	            if(status="success"){
					    	                UicDialog.Success("审批成功!",function(){
					    	                	supplierInfoDetail.doExecute("goBack",opts);
					    	                });
					    	            }else{
					    	                UicDialog.Error("审批失败！",function(){
					    	                	supplierInfoDetail.doExecute("goBack",opts);
					    	                });
					    	           }
					    	    });

		    		}
		        }, 
		        
		        
//		        goBack : function(opts){
//		        	var options = {};
//		    		options.murl = "business/supplierm/supplierInfo" + opts.url;
//		    		if(opts.id){
//		    			options.keyName = 'id';
//		    			options.keyValue = id;
//		    		}
//		    		$.openurl(options);
//		        }
		        
		        goBack: function(){
        		    //刷新父级窗口
        			window.opener.reload();  //重新加载一下，使审批通过的数据不再显示
		        	window.opener.childColseRefresh(2);
		        	
        			//关闭当前子窗口
        			window.close();
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =supplierInfoDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		supplierInfoDetail.doExecute('initDocument');
	}
});