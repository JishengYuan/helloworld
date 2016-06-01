define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
	require("formSelect");
	require("uic/message_dialog");
	require("jquery.form");
	var StringBuffer = require("stringBuffer");
	var putApprovePage = {
			config: {
				module: 'putApprovePage',
				uicTable : 'uicTable',
	            namespace: '/business/putApprovePage'
	        },
	        methods :{
	        	initDocument : function(){
		        	$("#_sino_eoss_sales_contract_submit").unbind('click').click(function () {
		        		putApprovePage.doExecute('approveSubmit');
		        	});
		        	$("#_sino_eoss_sales_contract_back").unbind('click').click(function () {
		        		putApprovePage.doExecute('_back');
		        	});
		        },
		        fmoney:function(s,n){
		        	n = n > 0 && n <= 20 ? n : 2; 
		    		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		    		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
		    		t = ""; 
		    		for (i = 0; i < l.length; i++) { 
		    			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		    		} 
		    		return t.split("").reverse().join("") + "." + r;
		        },
		        reload:function(){
		        	$("#"+putApprovePage.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+putApprovePage.config.uicTable).tableReload();
		        },
		        approveSubmit:function(){
		        	var url = ctx+"/business/inventory/putApproveSubmit"
		        	var datas=$("#_sino_eoss_inventory_form").serialize();
		        	$.post(url,datas,function(data,status){
		        		if(data=="success"){
		        			UicDialog.Success("审批成功！");
		        			putApprovePage.doExecute('_back');
		        		} else {
		        			UicDialog.Error("审批失败！");
	   	    				return;
		        		}
 	                });
		        },
		        _back:function(){
		        	var options = {};
		    		options.murl = "business/inventory/approveManage";
		    		$.openurl(options);
		        },
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = "business/inventory" + opts.url;
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
			doExecute : function(flag, param) {
				var method =putApprovePage.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		putApprovePage.doExecute('initDocument');
	}
});