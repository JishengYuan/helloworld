define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("uic/message_dialog");
	var supplierTypeJson = {};
	var bizOwnerJson = {};
	var borrowingPurpose = {};
	var simplePage = {
			config: {
				module: 'simplePage',
				uicTable : 'uicTable',
	            namespace: '/business/feemanage/simplePage'
	        },
	        methods :{
	        	initDocument : function(){
	        		$('._day_date').datetimepicker({
        		    	pickTime: false
        		    });
	        		$('._month_date').datetimepicker({
	        			viewMode:1,
			    		minViewMode:1,
				    	pickTime: false
	        		});
	        		$('._year_date').datetimepicker({
	        			viewMode:2,
	        			minViewMode:2,
	        			pickTime: false
	        		});
	        		$("input[name='_input_raido']").unbind('click').click(function(){
	        			simplePage.doExecute("selectInputRadio",$("input[name='_input_raido']:checked").attr("value"));
	        		});
	        		simplePage.doExecute('renderFusionCharts');
		        },
		        selectInputRadio:function(val){
		        	if(val == "1"){
		        		$('._day_date').show();
		        		$('._month_date').hide();
		        		$('._quarter_span').hide();
		        		$('._year_date').hide();
		        	} else if(val == "2"){
		        		$('._day_date').hide();
		        		$('._month_date').show();
		        		$('._quarter_span').hide();
		        		$('._year_date').hide();
		        	} else if(val == "3"){
		        		$('._day_date').hide();
		        		$('._month_date').hide();
		        		$('._quarter_span').show();
		        		$('._year_date').hide();
		        	} else {
		        		$('._day_date').hide();
		        		$('._month_date').hide();
		        		$('._quarter_span').hide();
		        		$('._year_date').show();
		        	}
		        },
		        renderFusionCharts:function(){
		        	var url = ctx+"/sales/statistic/simpleDataXml";
		    		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "text",
		    			url :url,
		    			success : function(msg) {
		    			    var chart = new FusionCharts(ctx+"/js/plugins/FusionCharts_XTV3.2/swf/Column3D.swf",  Math.random(), "800", "600","0","0");
		    		        chart.setXMLData(msg);				   
		    			    chart.render("chartContainer");
		    			}
		    		});
		        },
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = "business/feemanage/simplePage" + opts.url;
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
				var method =simplePage.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		simplePage.doExecute('initDocument');
	}
});