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
		        	//绑定审核确认提交到流程引擎按钮
		        	$("#_sino_eoss_pay_submit").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/handleFlow";
		    			rbmApply.doExecute("approveToFlow",opts);
		        	});
		        	$("#_eoss_business_pay_back").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/manage";
		    			rbmApply.doExecute("goBack",opts);
		        	});
		        	$("#_order_reim tbody").find("tr").each(function(i,ele){
	        			 var serviceTypeId=$('#coursesType'+i).text();
	        			 rbmApply.methods.coursesType(serviceTypeId,i);
	        			 var taxRateTypeId=$('#taxRate'+i).text();
	        			 rbmApply.methods.taxRateType(taxRateTypeId,i);
	        		 });
		        	$("#_print").unbind('click').click(function () {
		        		rbmApply.doExecute("getPrint1");
		        	});
		        	rbmApply.doExecute("getTime");
		        },
		        getTime:function(){
		       	  $('.date').datetimepicker({ 
		       	        pickTime: false
		       	      }); 
		        },
		        getPrint1 :function(){
		        	var paymentId = $("#_eoss_customer_id").val();
		        	var url=ctx+'/business/rbmApply/printReim?id='+paymentId+'&tmp='+Math.random();
	    			$.ajax({url:url,async:false,success:function(data){
	        			window.open(url,'_blank','status=no,scrollbars=yes,top=150,left=150,width=800,height=500');
	        		  }});
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

		        taxRateType: function(cid,j){
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=taxrateType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == cid){
		    						$('#taxRate'+j).html(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        },
		        closeWindow: function(){
		        	//刷新父级窗口
		        	 if(window.top!=window.self){
			        		window.opener.reload();
			        		//window.opener.childColseRefresh();
			        	}
			        	 if(window.opener){
				        		window.opener.reload();
				        	}
		        		metaUtil.closeWindow();  
		        },
		  
		        goBack : function(opts){
		        	//刷新父级窗口
		    		window.opener.childColseRefresh();
		    		//关闭当前子窗口
		        	metaUtil.closeWindow();  
		        },
		        
		        approveToFlow : function(opts){
		        	var options = {};
		    		options.murl = ctx+rbmApply.config.namespace + opts.url;
		    		var datas=$("#_sino_eoss_cuotomer_addform").serialize();
		    		//启动遮盖层
	       	    	 $('#_progress1').show();
	       	    	 $('#_progress2').show();
		    	    $.post(options.murl,datas,
		    	          function(data,status){
		    	            if(status=="success"){
		    	                	rbmApply.doExecute("closeWindow",opts);
		    	            }else{
		    	                UicDialog.Error("审批失败！",function(){
		    	                	rbmApply.doExecute("closeWindow",opts);
		    	                });
		    	           }
		    	    });
		        }
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