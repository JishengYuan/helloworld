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
	var timeObj = require("js/plugins/meta/js/timeObjectUtil");
	//------------------加载DataTable-------------End------------
	String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
	}
	var salesContractAmount = {
			config: {
				module: 'salesContractAmount',
            	namespace:'/sales/contract'
	        },
	        methods :{
	        	initDocument : function(){
	        		
	        		//采购员
	        		var buyers = $("#_sales_contracts_buyer").html();
	        		if(buyers != null&&buyers != ""){
	        			$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx + "/sales/contract/getSaleContractBuyers?buyers="+buyers+"&tmp="+ Math.random(),
			    			success : function(msg) {
			    				$("#_sales_contracts_buyer").html(msg.buyers);
			    			}
			    		});
	        		}
	        		
	        	
	        		
	        		$("a[name='hrefReload']").unbind('click').click(function () {
	        			var url = $(this).attr("hrefA");
	        			window.location.href = url;
	        		});
	        		
	        		salesContractAmount.doExecute('getDataTable');
	        		salesContractAmount.doExecute('getContractType');
	        		salesContractAmount.doExecute('getInvoiceType');
	        		salesContractAmount.doExecute('getAccountCurrency');
	        		salesContractAmount.doExecute('getReceiveWay');
	        		salesContractAmount.doExecute('getIndustryAndCustomerIdt');
	        		salesContractAmount.doExecute('staffSelect');
	        		 //加载附件组件
	        		var typeJson = {};
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				typeJson = msg;
		    			}
		    		});
	        		
	        		salesContractAmount.methods.showFileField();
	        		$("#_invoice_table tbody").find("tr").each(function(i,ele){
	        			 var invoiceTypeId=$('#invoiceType'+i).text();
	        			 salesContractAmount.methods.getInvoice(invoiceTypeId,i,typeJson);
	 	        		var invoiceStatusId=$('#invoiceStatus'+i).text();
	 	        		salesContractAmount.methods.getInvoiceStatus(invoiceStatusId,i);
	        		 });

		        	//详情返回按钮
		        	$("#_sino_eoss_sales_contract_detailBack").unbind('click').click(function () {
		        		salesContractAmount.methods.closeWindow('detail');
		        	});
		        	salesContractAmount.methods.typeSelectResult($('#contractTypeId').val());
		        	salesContractAmount.methods.getContractName();
		        	if($("#contractTypeId").val() == 3000){//MA续保合同
		        		var totalCount = 0;
			       		 $('#_sino_eoss_sales_contract_maproducts_table tbody').find("tr").each(function(i,ele){
			       			 $(ele).find("td").each(function(j,elem){
			       				 if(j == 9){
//			       					 totalCount+=parseInt($(elem).attr("tdPrice"));
			       					 totalCount=metaUtil.accAdd(totalCount,$(elem).attr("tdPrice"));
			       				 }
			       			 });
			       		 });
			       		 $('#_product_total').html('￥'+fmoney(totalCount,2));
		        	} else if($("#_contract_hidden").is(":hidden")){//没关联备货的合同
	        			var totalCount = 0;
			       		 $('#_sino_eoss_sales_contract_products_table tbody').find("tr").each(function(i,ele){
			       			 $(ele).find("td").each(function(j,elem){
			       				 if(j == 8){
			       					totalCount=metaUtil.accAdd(totalCount,$(elem).attr("tdPrice"));
			       				 }
			       			 });
			       		 });
			       		 $('#_product_total').html('￥'+fmoney(totalCount,2));
	        		} else {
	        			var totalCount = 0;
			       		 $('#_sino_eoss_sales_contract_products_table tbody').find("tr").each(function(i,ele){
			       			 $(ele).find("td").each(function(j,elem){
			       				 if(j == 9){
			       					totalCount=metaUtil.accAdd(totalCount,$(elem).attr("tdPrice"));
			       				 }
			       			 });
			       		 });
			       		 $('#_product_total').html('￥'+fmoney(totalCount,2));
	        		}
		        	
		        	//修改单价金额变更
		        	$(".unitPrices").blur(function(){
		        		//var num=$(this).parent().prev().find("input").val();
		        		var num=$(this).parent().prev().find("input").val();
		        		var amount=metaUtil.accMul(num,this.value);
		        		$(this).parent().next().find("span").text('￥'+fmoney(amount,2));
		        		$(this).parent().next().find("input:first").val(amount);
		        		$(this).parent().next().attr("tdPrice",amount);
		        		var totalCount=0;
		        		if($("#contractTypeId").val() == 3000){//MA续保合同
		        			$('#_sino_eoss_sales_contract_maproducts_table tbody').find("tr").each(function(i,ele){
	        					$(ele).find("td").each(function(j,elem){
	        						if(j == 9){
	        							totalCount=metaUtil.accAdd(totalCount,$(elem).attr("tdPrice"));
	        						}
	        					});
		        			});
		        		}else{//其他合同（除备货的）
		        			$('#_sino_eoss_sales_contract_products_table tbody').find("tr").each(function(i,ele){
		        				if($("td[name = 'relateDeliveryProduct_contract']").length > 0){//有备货的合同
		        					$(ele).find("td").each(function(j,elem){
		        						if(j == 9){
		        							totalCount=metaUtil.accAdd(totalCount,$(elem).attr("tdPrice"));
		        						}
		        					});
		        				}else{
		        					$(ele).find("td").each(function(j,elem){
		        						if(j == 8){
		        							totalCount=metaUtil.accAdd(totalCount,$(elem).attr("tdPrice"));
		        						}
		        					});
		        				}
		        			});
		        		}
		        		alert(totalCount);
			       		 $('#_product_total').html('￥'+fmoney(totalCount,2));
			       		 
			       		 $('#contractAmountshow').text(totalCount);
			       		 
			       		 $('#contractAmount').val(totalCount);
		        	});
		        	$("#_sino_eoss_sales_contract_change_submit").unbind('click').click(function () {
		        		salesContractAmount.methods.doContractChange();
		        	});
		        	
		        },
		        doContractChange:function(){
		        	
		  			var totalAmount = 0;
	    			$("#editTable").find("table tbody tr").each(function(i,ele){
	    				$(ele).find("td").each(function(j,elem){
	    					if(j == 1){
//	    						totalAmount+=parseInt($(elem).find("input").first().val());
	    						totalAmount=metaUtil.accAdd(totalAmount,$(elem).find("input").first().val());
	    					}
	    				});
	    			});
	    			
	    				var contractAmount = $('#contractAmount').val();
	        			if(contractAmount != totalAmount){
	        				if(!$('.collection_plan').is(':hidden')){
	        					 UicDialog.Error("收款金额与合同金额不等不能提交！");
	    	    				return;
	        				}
	        			}
	        			
	        			//计划收款时间 验证
			    		var b = false;
			    		$("#editGridName .hasDatepicker").each(function(i,ele){
			    			var val = $(ele).val();
			    			$("input[name^='planedReceiveAmount']").each(function(j,elem){
			    				var inputValue = $(elem).val();
			    				if(i == j&&inputValue != null&&inputValue != ""){
			    					if(val == ""||val == null){
			    						b = true;
			        				}
			    				}
			    			});
			    		});
			    		if(b){
			    			UicDialog.Error("计划收款时间不能为空！");
			    			return;
			    		}
			    		
			    		 var obj = new Object();
			    		 var grid = gridStore.get(form.name);// 获取表格行列name
			    		 var gridType=gridTypeStore.get(form.name);
			    		 var gridStoreData =  $('#' + form.name + ' tbody tr').data_grid('getValue',grid, form.name,gridType);
			    		 obj[form.name]=gridStoreData;
			    		 var json_data = JSON.stringify(obj);
			    	     $("#tableData").val(json_data);
			    	     
			    	     $("#attachIds").val($("#uplaodfile").fileField("getValue"));
				    		
				    		var attachIds = $("#attachIds").val();
					   	     if(attachIds == ""||attachIds == null){
					   	    	 UicDialog.Error("请上传合同附件!");
					   	    	 return;
					   	     } else {
					   	    	 var attachs = attachIds.split(",");
					   	    	 if(attachs.length < 2){
					   	    		 UicDialog.Error("请上传合同副本和毛利预估表!");
					       	    	 return;
					   	    	 }
					   	     }
			    	     
			        		var datas=$("#_sino_eoss_sales_contract_change_addform").serialize();
			        		var url = ctx+'/sales/contract/doContractAmountChange?tmp='+Math.random()+'&changeType='+$("#changeType").val();
			        		$("#_sino_eoss_sales_contract_change_submit").unbind('click');
			        		$.post(url,datas,
			    	            function(data,status){
			    	            	if(data=="success"){
			    	                	  UicDialog.Success("保存数据成功!",function(){
			    	                		  salesContractAmount.methods._back();
			    	                	  });
			    	                  }else{
			    	                  	  UicDialog.Error("保存数据失败！");
			    	                  	salesContractAmount.methods._back();
			    	                  }
			    	        }); 
		        },
		    	_back: function(){
		    		//刷新父级窗口
		    		window.opener.childColseRefresh();
		    		//关闭当前子窗口
		    		metaUtil.closeWindow();  
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
		        
		        getInvoice: function(invoiceTypeId,m,msg){
		        	for(var i in msg){
    					if(msg[i].id == invoiceTypeId){
    						$('#invoiceType'+m).text(msg[i].name);
    					}
    				}
		        },
		        typeSelectResult:function(id){
	        		$('.product_handler').show();
	        		$('#serviceStartDate_div').show();
        			$('#serviceEndDate_div').show();
        			$('.collection_plan').show();
        			$('#type_th').html("产品类型");
        			$('#partner_th').html("产品厂商");
        			$('#product_th').html("产品型号");
	        		//产品合同
	        		if(id == 1000){
	        		}
	        		//临时采购
	        		if(id == 2000){
	        			$('.collection_plan').hide();
	        		}
	        		//MA续保合同
	        		if(id == 3000){
	        		}
	        		//技术服务合同
	        		if(id == 4000){
	        			//$('.product_handler').hide();
	        			$('#type_th').html("服务类型");
	        			$('#partner_th').html("服务厂商");
	        			$('#product_th').html("服务型号");
	        			$("#_sino_eoss_sales_products_relate").hide();
	        		}
	        		//采购确认函
	        		if(id == 5000){
	        			$('#serviceStartDate_div').hide();
	        			$('#serviceEndDate_div').hide();
	        		}
	        		//软件开发合同
	        		if(id == 6000){
	        			$('#type_th').html("软件类型");
	        			$('#partner_th').html("软件厂商");
	        			$('#product_th').html("软件版本");
	        		}
	        		//公司备件
	        		if(id == 7000){
	        			$('.collection_plan').hide();
	        			$('#serviceStartDate_div').hide();
	        			$('#serviceEndDate_div').hide();
	        			$('#contractAmount').val(0);
	        		}
	        		//客户配件
	        		if(id == 8000){
	        			$('.collection_plan').hide();
	        			$('#serviceStartDate_div').hide();
	        			$('#serviceEndDate_div').hide();
	        		}
	        		//备货合同
	        		if(id == 9000){
	        			$('.collection_plan').hide();
	        		}
	        		//其他合同类型
	        		if(id == 0000){
	        		}
	        	},
		        getInvoiceStatus :function(invoiceStatusId,i){
		        	/*$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=invoiceStatus&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == invoiceStatusId){
		    						$('#invoiceStatus'+i).text(msg[i].name);
		    					}
		    				}
		    			}
		    		});*/
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
		        		attr.fileUploadFlag = "update";
	        			$.ajax({
	        				url:ctx+"/sales/contract/fileUploadData",
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
		        closeWindow: function(colseType){//审核后刷新父页面
		    		//刷新父级窗口
		        	if(colseType=='proc'){//如果是“待办”审核后刷新“待办事项”这个父页面
		        		window.opener.reload();
		        	}
		        	if(colseType=='detail'){//如果是“待办”审核后刷新“合同管理”这个父页面
//		        		window.opener.childColseRefresh();
		        	}
		        	//关闭当前子窗口
		        	metaUtil.closeWindow();  
		        },
		        staffSelect:function(){
		    		var $fieldStaff = $("#formStaff");
		    		// 选人组件
		    		var optionss = {
		    			inputName : "staffValues",
		    			showLabel : false,
		    			width : "580",
		    			name : "code",
		    			value : "root",
		    			level:2,
		    			tree_url:ctx+'/staff/getStaffByOrgs?random=1',
		    			selectUser : false,
		    			radioStructure : false
		    		}
		    		optionss.addparams = [ {
		    			name : "orgs",
		    			//三个销售部门ID
	        			value : "52"
		    		} ];

		    		$fieldStaff.formUser(optionss);
		    		if($('#flowStep').val() != 'show1'){
		        		$('#_userName_ul').hide();
		        	}
		        },
		        approveToFlow : function(opts){
		        	if(flowFlag=='SWJLSP'){
		        		var isAgreen =$('input[name="isAgree"]:checked').val()
		        		var orderProcessorId =$("#formStaff").formUser("getValue");
		        		//审批校验，必须选择订单处理人
		        		if(orderProcessorId==""&&isAgreen!=0){
		        			alert("请选择订单处理人");
		        			return;
		        		}
		        	}
		    		$("#orderProcessorId").val(orderProcessorId);
		        	var options = {};
		    		options.murl = ctx+salesContractAmount.config.namespace + opts.url;
		    		var datas=$("#_sino_eoss_sales_contract_approveForm").serialize();
		    		//启动遮盖层
	       	    	 $('#_progress1').show();
	       	    	 $('#_progress2').show();
		    	    $.post(options.murl,datas,
		    	          function(data,status){
		    	            if(status=="success"){
		    	                UicDialog.Success("审批成功!",function(){
		    	                	salesContractAmount.doExecute("closeWindow","proc");
		    	                });
		    	            }else{
		    	                UicDialog.Error("审批失败！",function(){
		    	                	salesContractAmount.doExecute("closeWindow","proc");
		    	                });
		    	           }
		    	    });
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =salesContractAmount.methods[flag];
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
		salesContractAmount.doExecute('initDocument');
	}
});