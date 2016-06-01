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
	require("json2");
	var companyId = 'Sys_companyId';
	var companyBankId = 'Sys_companyBankId';
	var accountPersionId = 'Sys_accountPersionId';
	
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
		        	$("#_sino_eoss_pay_submit").unbind('click').click(function () {

		        		var opts = {};
		    			opts.url = "/handleFlow";
		    			paymentDetail.doExecute("approveToFlow",opts);
		        	});
		        	$("#_eoss_business_pay_back").unbind('click').click(function () {
//		        		var opts = {};
//		    			opts.url = "/manage";
//		    			paymentDetail.doExecute("goBack",opts);
		        		metaUtil.closeWindow();
		        	});
		        	$("#_print").unbind('click').click(function () {
		        		paymentDetail.doExecute("getPrint1");
		        	});
		        	/* //绑定“签收单”按钮数据
	        		 $("#_order_payment tbody").find("tr").each(function(i,ele){
	        			 	var porId=$('#porId_'+i).attr("porId");
	        			 	$('#porId_'+i).unbind('click').click(function () {
	    		        		paymentDetail.doExecute("fileDetail",porId);
	    		        	});
					});*/
		        	paymentDetail.doExecute("getFormSelect");
		        	paymentDetail.doExecute("company");
		        //	paymentDetail.doExecute("_companyBank",null);
		        	paymentDetail.doExecute("getTime");
		        	paymentDetail.doExecute("getPaymentMode");
		        	paymentDetail.doExecute("accountPersion");
		        	paymentDetail.doExecute("initbankinfo");
			        	
		        },
		        fileDetail :function(orderId){
		        	var url=ctx+'/business/order/fileDetail?id='+orderId+'&tmp='+Math.random();
		        	window.open(url,'_blank');
		        },
		        getPrint1 :function(){
		        	var paymentId = $("#_eoss_customer_id").val();
		        	var url=ctx+'/business/payOrder/printPay?id='+paymentId+'&tmp='+Math.random();
	    			$.ajax({url:url,async:false,success:function(data){
	        			window.open(url,'_blank','status=no,scrollbars=yes,top=150,left=150,width=800,height=500');
	        		  }});
		        },
		        accountPersion : function(){
		        	var supplierType = $("#accountPersion");
		        	supplierType.addClass("li_form");
		        	var optionCompPosType = {
		        			writeType : 'show',
		        			url:ctx+paymentDetail.config.namespace+"/getAccountPersion?tmp="+Math.random(),
		        			showLabel : false,
		        			required:true,
		        			onSelect:function(id,obj){
		        				SetCookie(accountPersionId,id);
		        				$('#payUser').val($("#accountPersion").formSelect("getValue")[1]);
		        			},
		        			width : "284"
		        	};
		        	supplierType.formSelect(optionCompPosType);
		        	$(".uicSelectData").height(160);
		        },
		        getPaymentMode:function(){
		    		var $fieldUserTypes = $("#_eoss_business_paymentMode");
		    		$fieldUserTypes.addClass("li_form");
		    		var optionUserTypes = {
		    			writeType : 'show',
		    			showLabel : false,
		    			required:true,
		    			code : 'paymentMode',
		    			width : "284",
		    			onSelect :function(){
		    				var type= $("#_eoss_business_paymentMode").formSelect("getValue")[0];
		    				$('#_eoss_business_paymentModeId').val(type);
		    		}
		    		};
		    		$fieldUserTypes.formSelect(optionUserTypes);
		    		if($('#_eoss_business_paymentModeId').val() == null||$('#_eoss_business_paymentModeId').val() == ""){
		    			var roomId = "";
		        		$fieldUserTypes.find("ul li").each(function(i,ele){
		        			if(i == 0){
		        				var obj = $(ele);
		    					roomId = obj.attr("infinityid");
		        			}
		    			});
		        		$fieldUserTypes.formSelect('setValue',roomId);
		        		$('#_eoss_business_paymentModeId').val(roomId);
		    		} else {
		    			$fieldUserTypes.formSelect('setValue',$('#_eoss_business_paymentModeId').val());
		    		}
		    	},
		        getTime:function(){
		       	  $('.date').datetimepicker({ 
		       	        pickTime: false,
		       	  		todayBtn: true
		       	      }); 
		        },
		        company:function(){
		        	var supplierType = $("#_company");
		        	supplierType.addClass("li_form");
					var optionCompPosType = {
						url:ctx+"/business/payOrder/getCompany?tmp="+Math.random(),
						writeType : 'show',
						showLabel : false,
						required:true,
						onSelect:function(id,obj){
							SetCookie(companyId,id);
							var type= $("#_company").formSelect("getValue")[0];
							$("#payCompany").val(type);
							paymentDetail.doExecute("_companyBank",{"id":id});
						},
						width : "284"
					};
					supplierType.formSelect(optionCompPosType);
					
					$(".uicSelectData").height(160);
		        },
		        _companyBank:function(opts){
		        	$("#_companyBank").empty();
		        	var url = ctx+paymentDetail.config.namespace+"/getBankAccountByCmp?companyId=1111&tmp="+Math.random();
		        	if(opts != null){
		        		url = ctx+paymentDetail.config.namespace+"/getBankAccountByCmp?companyId="+opts.id+"&tmp="+Math.random();
		        	}
		        	var supplierType = $("#_companyBank");
		        	supplierType.addClass("li_form");
					var optionCompPosType = {
						url:url,
						writeType : 'show',
						showLabel : false,
						required:true,
						onSelect:function(id,obj){
							SetCookie(companyBankId,id);
							$("#payCompanyBank").val(id);
							$.ajax({
				    			type : "GET",
				    			async : false,
				    			dataType : "json",
				    			url : ctx+paymentDetail.config.namespace+"/getBankAccountModel?id="+id+"&tmp="+Math.random(),
				    			success : function(msg) {
				    				$('#_bankAccount').html(msg[0].BankAccount);
				    				$('#_banlance').html(msg[0].Balance);
				    				$('#banlance').val(msg[0].Balance);
				    				
				    			}
				    		});
						},
						width : "284"
					};
					supplierType.formSelect(optionCompPosType);
					$(".uicSelectData").height(160);
		        },
		        initbankinfo:function(){
		        	//初始化公司
		        	var companyIdval = GetCookie(companyId);
		    		var companyBankIdval = GetCookie(companyBankId);
		    		var accountPersionval = GetCookie(accountPersionId);
		    		
		    		if(companyIdval==null){
			        	var supplierType = $("#_company");
			        	var roomId="";
			        	supplierType.find("ul li").each(function(i,ele){
		        			if(i == 0){
		        				var obj = $(ele);
		    					roomId = obj.attr("infinityid");
		        			}
		    			});
			           supplierType.formSelect('setValue',roomId);
			       
						$("#payCompany").val(roomId);
						paymentDetail.doExecute("_companyBank",{"id":roomId});
		    		}else{
		    			var supplierType = $("#_company");
		    			supplierType.formSelect('setValue',companyIdval);
		    			$("#payCompany").val(companyIdval);
						paymentDetail.doExecute("_companyBank",{"id":companyIdval});
		    		}

		    	 	//初始化公司银行
		    		if(companyBankIdval==null){
		    			var bankid="";
			        	var  supplierType1 = $("#_companyBank");
		        		supplierType1.find("ul li").each(function(i,ele){
		        			if(i == 0){
		        				var obj = $(ele);
		        				bankid = obj.attr("infinityid");
		        			}
		    			});
			        	supplierType1.formSelect('setValue',bankid);
			        	
		        	$("#payCompanyBank").val(bankid);
		        	if(bankid != ""&&bankid != null){
		        		$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx+paymentDetail.config.namespace+"/getBankAccountModel?id="+bankid+"&tmp="+Math.random(),
			    			success : function(msg) {
			    				$('#_bankAccount').html(msg[0].BankAccount);
			    				$('#_banlance').html(msg[0].Balance);
			    				$('#banlance').val(msg[0].Balance);
			    			}
			    		});
		        	}
		    		}else{
		    			var  supplierType1 = $("#_companyBank");
		    			supplierType1.formSelect('setValue',companyBankIdval);
		    	     	$("#payCompanyBank").val(companyBankIdval);
						$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx+paymentDetail.config.namespace+"/getBankAccountModel?id="+companyBankIdval+"&tmp="+Math.random(),
			    			success : function(msg) {
			    				$('#_bankAccount').html(msg[0].BankAccount);
			    				$('#_banlance').html(msg[0].Balance);
			    				$('#banlance').html(msg[0].Balance);
			    			}
			    		});
		    		}
		    		
		    		//财务人员
		    		
		    		if(accountPersionval==null){
		    			var supplierType3 = $("#accountPersion");
		    			var persionId="";
		    			var persionName="";
		    			supplierType3.find("ul li").each(function(i,ele){
		        			if(i == 0){
		        				var obj = $(ele);
		        				persionId = obj.attr("infinityid");
		        				persionName = obj.attr("infinityname");
		        			}
		    			});
		    			supplierType3.formSelect('setValue',persionId);
		    			$('#payUser').val(persionName);
		    		}else{
		    			var supplierType3 = $("#accountPersion");
		    			supplierType3.formSelect('setValue',accountPersionval);
		    			$('#payUser').val($("#accountPersion").formSelect("getValue")[1]);
		    		}
		    	

		        },
		        getFormSelect : function(){
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=taxType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == $('#_eoss_business_taxTypeId').val()){
		    						$('#_eoss_taxType').html(msg[i].name);
		    					}
		    				}
		    			}
		    		});
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
		        approveToFlow : function(opts){
		        	var amount = $('#_banlance').html();
		        	var pay = $('#payAmonut').val();
		        	$('#banlance').val(amount);
		        	var num=Number(amount-pay);
		        	if(num<0&&$('input:radio[name="isAgree"]:checked').val()==1){
		        		 UicDialog.Error("银行剩余金额不足！");
		        		 return;
		        	}
		        	var paycurrency=$("#paycurrency").val();
		        	if(paycurrency=='usd'){
		        		var realPayAmount = $("#realPayAmount").val();
		        		if($('input:radio[name="isAgree"]:checked').val()==1&&(realPayAmount==''||realPayAmount=="0")){
		        			UicDialog.Error("请输入实际付款人民币金额！");
		        			return;
		        		}
		        	}
		        	var options = {};
		    		options.murl = ctx+paymentDetail.config.namespace + opts.url;
		    		var datas=$("#_sino_eoss_cuotomer_addform").serialize();
	        		$("#_sino_eoss_pay_submit").unbind('click');
		    		//启动遮盖层
	       	    	 $('#_progress1').show();
	       	    	 $('#_progress2').show();
		    	    $.post(options.murl,datas,
		    	          function(data,status){
		    	            if(status=="success"){
		    	                	paymentDetail.doExecute("closeWindow",opts);
		    	            }else{
		    	                UicDialog.Error("审批失败！",function(){
		    	                	paymentDetail.doExecute("closeWindow",opts);;
		    	                });
		    	           }
		    	    });
		        }
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