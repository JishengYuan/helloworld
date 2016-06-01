define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("confirm_dialog");
	require("uic/message_dialog");
	require("formSubmit");
	require("changTag");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	var Map = require("map");
	gridStore = new Map();//
	gridTypeStore=new Map();//表格行列类型
	gridFieldStore = new Map();
	require("json2");
	require("js/common/form/data_grid");
	
	var rbmApply = {
			config: {
				module: 'rbmApply',
            	namespace:'/business/rbmApply'
	        },
	        methods :{
	        	initDocument : function(){
					/*
	        		 * 绑定触发事件
	        		 */
		        	$("#_eoss_business_pay_back").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/manage";
		    			rbmApply.doExecute("goBack",opts);
		        	});
		        	$("#_order_reim tbody").find("tr").each(function(i,ele){
	        			 var serviceTypeId=$('#coursesType'+i).text();
	        			 rbmApply.methods.coursesType(serviceTypeId,i);
	        		 });
		        	rbmApply.doExecute("getTime");
		        },
		        getTime:function(){
		       	  $('.date').datetimepicker({ 
		       	        pickTime: false
		       	      }); 
		        },
		        coursesType : function(cid,j){
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=coursesType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == cid){
		    						$('#coursesType'+j).html(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        },

		        closeWindow: function(){
		        	//刷新父级窗口
		        		window.opener.reload();
		        		metaUtil.closeWindow();  
		        },
		  
		        goBack : function(opts){
		    		//关闭当前子窗口
		        	metaUtil.closeWindow();  
		        },
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =rbmApply.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		rbmApply.doExecute('initDocument');
	}
	//时间整形小工具
	Date.prototype.toString = function(){
		if(arguments.length>0){
			var formatStr = arguments[0];
			var str = formatStr;
		
			str=str.replace(/yyyy|YYYY/,this.getFullYear());
			str=str.replace(/yy|YY/,(this.getFullYear() % 100)>9?(this.getFullYear() % 100).toString():"0" + (this.getFullYear() % 100));
		
			str=str.replace(/MM/,this.getMonth()+1>9?(this.getMonth()+1).toString():"0" + (this.getMonth()+1));
			str=str.replace(/M/g,(this.getMonth()+1).toString());
		
			str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():"0" + this.getDate());
			str=str.replace(/d|D/g,this.getDate());
		
			str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():"0" + this.getHours());
			str=str.replace(/h|H/g,this.getHours());
		
			str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():"0" + this.getMinutes());
			str=str.replace(/m/g,this.getMinutes());
		
			str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():"0" + this.getSeconds());
			str=str.replace(/s|S/g,this.getSeconds());
		
			return str;
		}else{
			return this.toLocaleString();
		}
	}
});