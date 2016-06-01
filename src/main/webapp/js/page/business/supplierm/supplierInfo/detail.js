define(function(require, exports, module) {
	var $ = require("jquery");
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
		        	$("#_eoss_business_supplierback").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/manage";
		    			supplierInfoDetail.doExecute("goBack",opts);
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
		        goBack : function(opts){
		        	var options = {};
		    		options.murl = "business/supplierm/supplierInfo" + opts.url;
		    		if(opts.id){
		    			options.keyName = 'id';
		    			options.keyValue = id;
		    		}
		    		$.openurl(options);
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