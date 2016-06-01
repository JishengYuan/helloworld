define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("formSelect");
	require("formSubmit");
	require("uic/message_dialog");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
	}
	var saveFile={
			config: {
            	namespace:ctx+'/business/order'
	        },
	        methods :{
	        	initDocument:function(){
	        		/*
	        		 * 绑定触发事件
	        		 */
	        		 $("#_eoss_business_order_back").bind('click',function(){
	        			 _back();
	                 });
	        		 $("#_eoss_business_order_ok").unbind('click').click(function () {
	        			 $("#_eoss_business_order_ok").unbind('click');
	        			 _add();
	        	       });
	        		 //加载附件组件
	        		 saveFile.methods.showFileField();
	        		 saveFile.methods.getTime();
	        	},
	        	getTime:function(){
	        		 $('.date').datetimepicker({ 
	          	        pickTime: false
	          	 
	          	      }); 
	        	},
	        	showFileField:function(){
	        	    require.async("formFileField", function() {
		        		var attr = {};
		        		attr.id = $('#id').val();
		        		attr.fileUploadFlag = "fileUpdate";
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
	        	}
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =saveFile.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	function _back(){
		window.opener.childColseRefresh();
		metaUtil.closeWindow();  
	}

	function _add(){
	     $("#attachIds").val($("#uplaodfile").fileField("getValue"));
	     var datas=$("#_eoss_business_order").serialize();
	     var url = ctx+'/business/order/doSaveFile?tmp='+Math.random();
	     var options = {};
	     options.formId = "_eoss_business_order";
	     if($.formSubmit.doHandler(options)){
	    	 $.post(url,datas,
	 	            function(data,status){
	 	            	if(data=="success"){
	 	                	  UicDialog.Success("保存成功!",function(){
	 	                	  _back();
	 	                	  });
	 	                  }else{
	 	                  	  UicDialog.Error("保存失败！",function(){
	 	                  		  _back();
	 	                  	  });
	 	                  }
	 	                }); 
	     }
	}
	exports.init = function(){
		saveFile.doExecute('initDocument');  
	}

});