define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("uic/message_dialog");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	var reimDetail = {
			config: {
				module: 'reimDetail',
            	namespace:'/business/reimbursement'
	        },
	        methods :{
	        	initDocument : function(){
	        		$("#editGridName tbody").find("tr").each(function(i,ele){
						$(ele).find("td").each(function(j,elem){
							if(j == 1){
								var divId = $(elem).find("div").attr("id");
								var inputName = $(elem).find("input").attr("name");
								var _coursesType = $("#"+divId);
								_coursesType.addClass("li_form");
								var optioncoursesType = {
										writeType : 'show',
										showLabel : false,
										required:true,
										code : "coursesType",
										onSelect:function(node){
											$('#'+inputName).val($("#"+divId).formSelect("getValue")[0]);
										},
										width : "180"
											
								};
								_coursesType.formSelect(optioncoursesType);
								_coursesType.formSelect('setValue',$('#'+inputName).val());
							}
						});
					});
					$("#editGridName tbody").find("tr").each(function(i,ele){
						$(ele).find("td").each(function(j,elem){
							if(j == 5){
								var divId = $(elem).find("div").attr("id");
								var inputName = $(elem).find("input").attr("name");
								var _invoiceType = $("#"+divId);
								_invoiceType.addClass("li_form");
								var optionCompPosType = {
										writeType : 'show',
										showLabel : false,
										required:true,
										code : "invoiceType",
										onSelect:function(node){
											$('#'+inputName).val($("#"+divId).formSelect("getValue")[0]);
										},
										width : "200"
											
								};
								_invoiceType.formSelect(optionCompPosType);
								_invoiceType.formSelect('setValue',$('#invoiceType_Id').val());
								$('#'+inputName).val($('#invoiceType_Id').val());
							}
						});
					});
					
		        	 $("#_business_order_reim").bind('click',function(){
		        			 $("#_reim_add_tabledate").show();
			             });
		        	$("#_eoss_business_reim_back").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/manage";
		    			reimDetail.doExecute("goBack",opts);
		        	});
		        	 $("#_order_reim tbody").find("tr").each(function(i,ele){
			        		//回显科目
		        			 var coursesType=$('#coursesType'+i).text();
		        			 reimDetail.methods.payCoursesType(coursesType,i);
		        			 //回显发票类型
		 	        		var invoiceTypePay=$('#invoiceTypePay'+i).text();
		 	        		reimDetail.methods.payInvoiceType(invoiceTypePay,i);
		        	 });
		        },
	        	 payCoursesType:function(coursesType,j){
		        		$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx + "/sysDomain/findDomainValue?code=coursesType&tmp="+ Math.random(),
			    			success : function(msg) {
			    				for(var i in msg){
			    					if(msg[i].id == coursesType){
			    						$('#coursesType'+j).text(msg[i].name);
			    					}
			    				}
			    			}
			    		});
		        		
		        	},
		        	payInvoiceType:function(invoiceTypePay,j){
		        		$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
			    			success : function(msg) {
			    				for(var i in msg){
			    					if(msg[i].id == invoiceTypePay){
			    						$('#invoiceTypePay'+j).text(msg[i].name);
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
		    		window.opener.childColseRefresh();
		        	metaUtil.closeWindow();  
		        },
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =reimDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		reimDetail.doExecute('initDocument');
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