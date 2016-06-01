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
	
	var paymentDetail = {
			config: {
				module: 'paymentDetail',
            	namespace:'/business/payOrder'
	        },
	        methods :{
	        	initDocument : function(){
					/*
	        		 * 绑定触发事件
	        		 */
		        	//绑定审核确认提交到流程引擎按钮
		        	$("#_eoss_business_pay_back").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/manage";
		    			paymentDetail.doExecute("goBack",opts);
		        	});
		        	paymentDetail.doExecute("getFormSelect");
		        	paymentDetail.doExecute("getTime");
		        
			        	
		        },
		        accountPersion : function(){
		        	var supplierType = $("#accountPersion");
		        	supplierType.addClass("li_form");
		        	var optionCompPosType = {
		        			writeType : 'show',
		        			url:ctx+"/"+paymentDetail.config.namespace+"/getAccountPersion?tmp="+Math.random(),
		        			showLabel : false,
		        			required:true,
		        			onSelect:function(id,obj){
		        				$('#payUser').val($("#accountPersion").formSelect("getValue")[1]);
		        			},
		        			width : "284"
		        	};
		        	supplierType.formSelect(optionCompPosType);
		        	$(".uicSelectData").height(160);
		        },
		        getTime:function(){
		       	  $('.date').datetimepicker({ 
		       	        pickTime: false,
		       	  		todayBtn: true
		       	      }); 
		        },
		        getFormSelect : function(){
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=coursesType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == $('#_coursesTypeId').val()){
		    						$('#coursesType').html(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == $('#_eoss_business_invoiceTypeId').val()){
		    						$('#_eoss_business_invoiceType').html(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=paymentMode&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == $('#_eoss_business_paymentModeId').val()){
		    						$('#_eoss_business_paymentMode').html(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        },

		        closeWindow: function(){
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
				var method =paymentDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		paymentDetail.doExecute('initDocument');
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
	
	function GetCookieVal(offset) {
		var endstr = document.cookie.indexOf(";", offset);
		if (endstr == -1) {
			endstr = document.cookie.length;
		}
		return unescape(document.cookie.substring(offset, endstr));
	}
	function SetCookie(name, value) {
		var expdate = new Date();
		var argv = SetCookie.arguments;
		var argc = SetCookie.arguments.length;
		var expires = (argc > 2) ? argv[2] : null;			/* expires 周期单位:秒 */
		var path = (argc > 3) ? argv[3] : null;
		var domain = (argc > 4) ? argv[4] : null;
		var secure = (argc > 5) ? argv[5] : false;
		if (expires != null) {
			expdate.setTime(expdate.getTime() + (expires*1000));
		}
		document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + expdate.toGMTString())) + ((path == null) ? "" : ("; path=" + path)) + ((domain == null) ? "" : ("; domain=" + domain)) + ((secure == true) ? "; secure" : "");
	}
	function DelCookie(name) {
		var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval = GetCookie(name);
		document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();
	}
	function GetCookie(name) {
		var arg = name + "=";
		var alen = arg.length;
		var clen = document.cookie.length;
		var i = 0;
		while (i < clen) {
			var j = i + alen;
			if (document.cookie.substring(i, j) == arg) {
				return GetCookieVal(j);
			}
			i = document.cookie.indexOf(" ", i) + 1;
			if (i == 0) {
				break;
			}
		}
		return null;
	}
});