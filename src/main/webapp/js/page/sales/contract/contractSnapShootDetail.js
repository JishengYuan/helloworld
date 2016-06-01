define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("formUser");
	//------------------加载DataTable-----------Start--------------
	var Map = require("map");
	require("confirm_dialog");
	require("uic/message_dialog");
	gridStore = new Map();//
	gridTypeStore=new Map();//表格行列类型
	gridFieldStore = new Map();
	require("json2");
	require("js/common/form/data_grid");
	var metaUtil = require("js/plugins/meta/js/metaUtil");	
	//------------------加载DataTable-------------End------------
	String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
	}
	var contractSnapShootDetail = {
			config: {
				module: 'contractSnapShootDetail',
            	namespace:'/sales/contract'
	        },
	        methods :{
	        	initDocument : function(){
	        		contractSnapShootDetail.doExecute('getDataTable');
	        		contractSnapShootDetail.doExecute('getContractType');
	        		contractSnapShootDetail.doExecute('getInvoiceType');
	        		contractSnapShootDetail.doExecute('getAccountCurrency');
	        		contractSnapShootDetail.doExecute('getReceiveWay');
	        		contractSnapShootDetail.doExecute('getIndustryAndCustomerIdt');
	        		contractSnapShootDetail.doExecute('staffSelect');
	        		contractSnapShootDetail.doExecute('getContractName');
	        		 //加载附件组件
	        		contractSnapShootDetail.methods.showFileField();
		        	//绑定审核确认提交到流程引擎按钮
		        	$("#_sino_eoss_sales_contract_submit").unbind('click').click(function () {
		        		$("#_sino_eoss_sales_contract_submit").unbind('click');
		        		var opts = {};
		    			opts.url = "/handleFlow";
		    			contractSnapShootDetail.doExecute("approveToFlow",opts);
		        	});
		        	
		        	if($("#contractTypeId").val() == 9000){
		        		var totalCount = 0;
			       		 $('#_sino_eoss_sales_contract_readyproducts_table tbody').find("tr").each(function(i,ele){
			       			 $(ele).find("td").each(function(j,elem){
			       				 if(j == 8){
//			       					 totalCount+=parseInt($(elem).attr("tdPrice"));
			       					totalCount=metaUtil.accAdd(totalCount,$(elem).attr("tdPrice"));
			       				 }
			       			 });
			       		 });
			       		 $('#_product_total').html('￥'+fmoney(totalCount,2));
		        	} else if($("#_contract_hidden").is(":hidden")){
	        			var totalCount = 0;
			       		 $('#_sino_eoss_sales_contract_products_table tbody').find("tr").each(function(i,ele){
			       			 $(ele).find("td").each(function(j,elem){
			       				 if(j == 8){
			       					totalCount=metaUtil.accAdd(totalCount,$(elem).attr("tdPrice"));
			       					//totalCount+=parseInt($(elem).attr("tdPrice"));
			       				 }
			       			 });
			       		 });
			       		 $('#_product_total').html('￥'+fmoney(totalCount,2));
	        		} else {
	        			var totalCount = 0;
			       		 $('#_sino_eoss_sales_contract_products_table tbody').find("tr").each(function(i,ele){
			       			 $(ele).find("td").each(function(j,elem){
			       				 if(j == 9){
			       					 //totalCount+=parseInt($(elem).attr("tdPrice"));
			       					 totalCount=metaUtil.accAdd(totalCount,$(elem).attr("tdPrice"));
			       				 }
			       			 });
			       		 });
			       		 $('#_product_total').html('￥'+fmoney(totalCount,2));
	        		}
		        	
		        },
		        getDataTable : function(){
		    		var $editgrid = $("#editTable");   //前台jsp中的div的ID
		    		var $row = $("<ul>").attr("class", "clearfix");
		    		var grid = new Array();
		    		var gridType=new Array();
		    		var field = form.fieldList;// 列数组
		    		for (var i = 0; i < field.length; i++) {
		    			grid.push(field[i].name);
		    			gridType.push(field[i].type);
		    		}
		    		gridStore.put(form.name, grid);// 表格行
		    		gridTypeStore.put(form.name,gridType)//类型
		    		$editgrid.append($row.data_grid(form));
		        },
	        	showFileField:function(){
	        	    require.async("formFileField", function() {
		        		var attr = {};
		        		attr.salesContractId = $('#id').val();
		        		attr.fileUploadFlag = "detail";
	        			$.ajax({
	        				url:ctx+"/sales/contractSnapShoot/fileUploadData",
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
		        getContractType : function(){
		        	var contractTypeId=$("#contractTypeId").val();
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=contractType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == contractTypeId){
		    						$('#contractType').text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        },
		        getContractName:function(){
		        	if($("td[name = 'relateDeliveryProduct_contract']").length > 0){
		        		$("#_contract_show").hide();
		        		$("#_contract_hidden").show();
		        		if($("td[name = 'relateDeliveryProduct_contract_']").length > 0){
		        			$("td[name = 'relateDeliveryProduct_contract_']").after("<td></td>");
		        		}
		        	} else {
		        		$("#_contract_show").show();
		        		$("#_contract_hidden").hide();
		        	}
		        	$("td[name = 'relateDeliveryProduct_contract']").each(function(i,ele){
		        		var id = $(ele).attr("tid");
		        		$.ajax({
			        		type : "GET",
			        		url : ctx+"/sales/contract/getContract?pruductId="+id+"&tmp="+ Math.random(),
			        		success : function(msg) {
			        			var str = $(ele).html();
			        			str+=msg.contractName;
			        			$(ele).html(str);
			        		}
			        	});
		        	});
		        },
		        getInvoiceType : function(){
		        	var invoiceTypeId=$("#_eoss_sales_invoiceTypeId").val();
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == invoiceTypeId){
		    						$('#invoiceType').text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        },
		        getAccountCurrency : function(){
		        	var accountCurrencyId=$("#_eoss_sales_accountCurrencyId").val();
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=accountCurrency&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == accountCurrencyId){
		    						$('#accountCurrency').text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        },
		        getReceiveWay : function(){
		        	var receiveWayId=$("#_eoss_sales_receiveWayId").val();
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=receiveWay&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == receiveWayId){
		    						$('#receiveWay').text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        },
		    	//根据客户ID得到要回显的行业ID,行业客户ID以及直接把联系人，联系人手机号查出来
		        getIndustryAndCustomerIdt:function (){
		    		var customerInfoId=$('#_eoss_sales_customerId').val();
		    	    var url = ctx+"/base/customermanage/customerInfo/getIndustryAndCustomerIdtByCustomerInfo?id="+customerInfoId+"&tmp="+Math.random();
		    	     $.post(url,function(data ,status){
		             	if(status="success"){
		             		$('#_industryName').text(data.industryName);
		             		$('#_customerIdtCustomerName').text(data.idtCustomerName);
		             		$('#_customerInfoName').text(data.customerInfoName);
		             		 $('#_customerContacts').text(data.contactsName);
		             		 if(data.contactsTel=='null'){
		             			$('#_customerContactsPhone').text('无');
		             		 }else{
		             			 $('#_customerContactsPhone').text(data.contactsTel);
		             		 }
		                 }
		             });
		    	},
		        closeWindow: function(){
		        	//关闭当前子窗口
		        	metaUtil.closeWindow();  
		    		//刷新父级窗口
		    		window.opener.childColseRefresh();
		        },
		        staffSelect:function(){
		    		var $fieldStaff = $("#formStaff");
		    		// 选人组件
		    		var optionss = {
		    			inputName : "staffValues",
		    			showLabel : false,
		    			width : "220",
		    			name : "code",
		    			value : "root",
		    			selectUser : false,
		    			radioStructure : true
		    		}
		    		optionss.addparams = [ {
		    			name : "code",
		    			value : "root"
		    		} ];

		    		$fieldStaff.formUser(optionss);
		        },
		        approveToFlow : function(opts){
		    		var orderProcessorId =$("#formStaff").formUser("getValue");
		    		$("#orderProcessorId").val(orderProcessorId);
		        	var options = {};
		    		options.murl = ctx+contractSnapShootDetail.config.namespace + opts.url;
		    		var datas=$("#_sino_eoss_sales_contract_approveForm").serialize();
		    	    $.post(options.murl,datas,
		    	          function(data,status){
		    	            if(data=="success"){
		    	                UicDialog.Success("审批成功!",function(){
		    	                	contractSnapShootDetail.doExecute("closeWindow");
		    	                });
		    	            }else{
		    	                UicDialog.Error("审批失败！",function(){
		    	                	contractSnapShootDetail.doExecute("closeWindow");
		    	                });
		    	           }
		    	    });
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =contractSnapShootDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	function fmoney(s, n) { 
		n = n > 0 && n <= 20 ? n : 2; 
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
		t = ""; 
		for (i = 0; i < l.length; i++) { 
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		} 
		return t.split("").reverse().join("") + "." + r; 
		} 
	exports.init = function() {
		contractSnapShootDetail.doExecute('initDocument');
	}
});