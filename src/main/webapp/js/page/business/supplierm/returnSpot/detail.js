define(function(require, exports, module) {
	var $ = require("jquery");
	var returnSpotDetail = {
			config: {
				module: 'returnSpotDetail',
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
            	namespace:'/business/supplierm/returnSpot'
	        },
	        methods :{
	        	initDocument : function(){
		        	returnSpotDetail.doExecute('getFormSelect');
		        	$("#_eoss_business_supplierback").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/manage";
		    			returnSpotDetail.doExecute("goBack",opts);
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
		        
		        },
		        goBack : function(opts){
		        	var options = {};
		    		options.murl = "business/supplierm/returnSpot" + opts.url;
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
				var method =returnSpotDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		returnSpotDetail.doExecute('initDocument');
	}
});