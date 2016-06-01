define(function(require, exports, module) {
	var $ = require("jquery");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
	}
	var orderDetail = {
			config: {
				module: 'orderDetail',
            	namespace:'/business/order'
	        },
	        methods :{
	        	initDocument : function(){
	        		orderDetail.methods.showFileField();
		        	$("#_eoss_business_order_back").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/manage";
		    			orderDetail.doExecute("goBack",opts);
		        	});
	        	},
		        	showFileField:function(){
		        	    require.async("formFileField", function() {
			        		var attr = {};
			        		attr.id = $('#id').val();
			        		attr.fileUploadFlag = "detail";
		        			$.ajax({
		        				url:ctx+"/business/order/fileUploadData",
		        				data:attr,
		        				dataType : "json", 
		        				success : function(result){  
		        			    	var $field2 = $("#uplaodfile");
		        			    	var $field1 = $("<li>").attr("id", "field_" + result.name);
		        			    	$field1.css("width", "900");
		        			    	$field2.append($field1);
		        			    	var tmp = $field1.fileField(result);
		        			    	$field2.append(tmp);
		        			    }  
		        			});
		        	    });
		        	},
		        goBack : function(opts){
		        	metaUtil.closeWindow();  
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =orderDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		orderDetail.doExecute('initDocument');
	}
});