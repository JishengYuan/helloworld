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
	var salesContractDetail = {
			config: {
				module: 'salesContractDetail',
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
	        		
	        		//是否能再次申请发票(发票金额不为0且小于合同金额且不能变更的时候)
	        		var invoiceTotal = 0;
	        		$("._invoice_input").each(function(i,ele){
	        			invoiceTotal+=parseInt($(ele).val());
	        		});
	        		if(invoiceTotal < parseInt($("#contractAmount").html())&&invoiceTotal != 0&&$("#_Change_input").val() != 1){
//	        			$("#_invoice_a").show();
	        		}
	        		
	        		$("a[name='hrefReload']").unbind('click').click(function () {
	        			var url = $(this).attr("hrefA");
	        			window.location.href = url;
	        		});
	        		
	        		salesContractDetail.doExecute('getDataTable');
	        		salesContractDetail.doExecute('getContractType');
	        		salesContractDetail.doExecute('getInvoiceType');
	        		salesContractDetail.doExecute('getAccountCurrency');
	        		salesContractDetail.doExecute('getReceiveWay');
	        		salesContractDetail.doExecute('getIndustryAndCustomerIdt');
	        		salesContractDetail.doExecute('staffSelect');

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
	        		
	        		salesContractDetail.methods.showFileField();
	        		$("#_invoice_table tbody").find("tr").each(function(i,ele){
	        			 var invoiceTypeId=$('#invoiceType'+i).text();
	        			 salesContractDetail.methods.getInvoice(invoiceTypeId,i,typeJson);
	 	        		var invoiceStatusId=$('#invoiceStatus'+i).text();
	 	        		salesContractDetail.methods.getInvoiceStatus(invoiceStatusId,i);
	        		 });
	        		
	        		//开发票信息
	        		salesContractDetail.methods.getInvoiceInfo(typeJson);
	        		
		        	//绑定审核确认提交到流程引擎按钮
		        	$("#_sino_eoss_sales_contract_submit").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/handleFlow";
		    			//opts.flowStep=
		    			salesContractDetail.doExecute("approveToFlow",opts);
		        	});
		        	//审批返回按钮
		        	$("#_sino_eoss_sales_contract_checkBack").unbind('click').click(function () {
		        		salesContractDetail.methods.closeWindow('proc');
		        	});
		        	//详情返回按钮
		        	$("#_sino_eoss_sales_contract_detailBack").unbind('click').click(function () {
		        		salesContractDetail.methods.closeWindow('detail');
		        	});
		        	salesContractDetail.methods.typeSelectResult($('#contractTypeId').val());
		        	salesContractDetail.methods.getContractName();
		        	
			   		 if($('#contractTypeId').val() == '3000'){
			   			 var totalCount = 0;
			   			 $('#_sino_eoss_sales_contract_maproducts_table tbody').find("tr").each(function(i,ele){
			   				 $(ele).find("td").each(function(j,elem){
			   					 if(j == 9){
			   						 totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
			   					 }
			   				 });
			   			 });
			   			$('#_product_total').html('￥'+fmoney(totalCount,2));
			   		 } else if($("#contractTypeId").val() == 9000){
		        		var totalCount = 0;
			       		 $('#_sino_eoss_sales_contract_readyproducts_table tbody').find("tr").each(function(i,ele){
			       			 $(ele).find("td").each(function(j,elem){
			       				 if(j == 8){
			       					 //totalCount+=parseInt($(elem).attr("tdPrice"));
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
			       					 //totalCount+=parseInt($(elem).attr("tdPrice"));
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
			       					 //totalCount+=parseInt($(elem).attr("tdPrice"));
			       					totalCount=metaUtil.accAdd(totalCount,$(elem).attr("tdPrice"));
			       				 }
			       			 });
			       		 });
			       		 $('#_product_total').html('￥'+fmoney(totalCount,2));
	        		}
	        		$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sales/contract/getFeeIncomeBySalesId?salesId="+$('#id').val()+"&tmp="+ Math.random(),
		    			success : function(msg) {
		    				var str = "";
		    				var amout = 0;
		    				for(var i = 0;i < msg.length;i++){
		    					str+="<tr><td>￥"+fmoney(msg[i].Amount,2)+"</td><td>"+timeObj.sFormatterDateTime(new Date(msg[i].ReceiveTime))+"</td><td>"+msg[i].Creator+"</td><td>"+msg[i].Remark+"</td></tr>"
		    					amout= metaUtil.accAdd(msg[i].Amount,amout);
		    				}
		    				var contractAmount = $("#contractAmount").html();
		    				if(contractAmount == amout){
		    					$("#_sales_feeincome_amount").html("已收款");
		    				} else {
		    					var loAmount=metaUtil.accSub(contractAmount,amout);
		    					$("#_sales_feeincome_amount").html("已收:"+amout+"剩余:"+loAmount);
		    				}
		    				$("#_sales_feeincome tbody").append(str);
		    			}
		    		});
		        	
	        		$("a[name='contract_invoice_handler']").each(function(i,ele){
        			 	//回显发票类型

        			 	//绑定“查看审查日志”按钮
        			 	var porId=$('#porId_'+i).attr("porId");
        			 	$('#porId_'+i).popover({
        			 		trigger :'focus',
        			 		placement:'left',
        			 		content:salesContractDetail.methods.onloadCheckedData(porId),
        			 		html:true	
        			 	}).click(function(e) {
        			 		e.preventDefault();
        			 	});
				});
	        		
		        },
		        
		        onloadCheckedData:function(porId){
		    	    var url = ctx+"/sales/invoice/getProInstLogList?procInstId="+porId+"&tmp="+Math.random();
		    	    var trStr="";
		    	    var name="";
		    	    $.ajax({url:url,async:false,success:function(data){
		    	    	var pro=data.edit;
		    	    		if(pro&&pro.length>0){
								for(var i=0;i<pro.length;i++){
									var dataTime=new Date(pro[i].time);
									trStr+='<tr>'+
									'<td class="sino_table_label">'+pro[i].user+'</td>'+
									'<td class="sino_table_label">'+pro[i].taskName+'</td>'+
									'<td class="sino_table_label">'+pro[i].apprResultDesc+'</td>'+
									'<td class="sino_table_label">'+dataTime.toString("yyyy-MM-dd")+'</td>'+
									'<td class="sino_table_label">'+pro[i].apprDesc+'</td>'+
									'</tr>';
								}
		    	    		}else{
								trStr+='<tr>'+
								'<td class="sino_table_label" colspan="5">申请中，暂无审核记录</td>'+
								'</tr>';
		    	    		}
		    	    		if(data.approName!=null){
		    	    		    name=data.approName;
		    	    		}else{
		    	    			name="无";
		    	    		}
		             		
		    	    }});
						var finalOut ='<div style="height:100px;width:730px">'+
						'<table border="0" style="width:92%" class="sino_table_body" id="_fee_travel">'+
						'<thead>'+
							'<tr>'+
								'<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">操作人</td>'+
								'<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">审批阶段</td>'+
								'<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">审批结果</td>'+
								'<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">操作时间</td>'+
								'<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">详情</td>'+
							'</tr>'+
						'</thead>'+
						'<tbody>'+trStr+
						'</tbody>'+
							'</div>'+
						'<span id="approName">当前审批人：'+name+'</span>';
						return finalOut;
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
		        getInvoiceInfo: function(typeJson){
		        	var _invoiceAmount = 0;
		        	var m = new Map();
		        	$("._invoiceAmount_td").each(function(i,ele){
		        		var $obj = $(ele);
		        		var invoiceAmount = $obj.attr("invoiceAmount");
		        		var invoiceType = $obj.attr("invoiceType");
		        		var invoiceFinalInspection = $obj.attr("invoiceFinalInspection");
		        		if(invoiceFinalInspection != null&&invoiceFinalInspection != ""){
		        			_invoiceAmount=metaUtil.accAdd(_invoiceAmount,invoiceAmount);
		        		}
		        		if(!m.containsKey(invoiceType)){
		        			m.put(invoiceType,invoiceAmount);
		        		} else {
		        			var im = m.get(invoiceType);
		        			m.remove(invoiceType);
		        			m.put(invoiceType,metaUtil.accAdd(im,invoiceAmount))
		        		}
		        	});
		        	
		        	$("#_yes_invoice_span").html(fmoney(_invoiceAmount,2));
		        	$("#_not_invoice_span").html(fmoney(metaUtil.accSub($("#contractAmount").html(),_invoiceAmount),2));
		        	
		        	for(var i=0;i<m.keys().length;i++){
		        		var str = '<label style="margin-left:20px;" class="row-label">';
		        		var mkey = m.keys()[i];
		        		var mval = m.get(mkey);
		        		for(var j in typeJson){
	    					if(typeJson[j].id == mkey){
	    						str += typeJson[j].name;
	    					}
	    				}
		        		    str += '：'+fmoney(mval,2);
		        		    str += '</label>';
		        		$("#_invoiceinfo_div").append(str);
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
		    		//form对象数据是从 controller中获取
		    		$editgrid.append($row.data_grid(form));
		        },
	        	showFileField:function(){
	        	    require.async("formFileField", function() {
		        		var attr = {};
		        		attr.salesContractId = $('#id').val();
		        		attr.fileUploadFlag = "detail";
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
//		    		var customerInfoId=$('#_eoss_sales_customerId').val();
		        	var customerId=$('#_eoss_sales_customerId').val();
		    	    var url = ctx+"/base/customermanage/customerInfo/getIndustryAndCustomerIdtByCustomerInfo?id="+customerId+"&tmp="+Math.random();
		    	     $.post(url,function(data ,status){
		             	if(status="success"){
		             		$('#_customerInfoName').text(data.customerInfoName);
		                 }
		             });
		    		var contactId=$('#customerContact').val();
		    	    var url1 = ctx+"/sales/contract/getCustomerContactById?customerId="+customerId+"&contactId="+contactId+"&tmp="+Math.random();
		    	     $.post(url1,function(data ,status){
		             	if(status="success"){
		             		 $('#_customerContacts').html(data.name);
		             		$('#_customerContactsPhone').html(data.telphone);
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
		        	if($("#formStaff").length == 0){
		        		$("#orderProcessorId").val("");
		        	} else {
		        		$("#orderProcessorId").val(orderProcessorId);
		        	}
		        	var options = {};
		    		options.murl = ctx+salesContractDetail.config.namespace + opts.url;
		    		var datas=$("#_sino_eoss_sales_contract_approveForm").serialize();
		    		//启动遮盖层
	       	    	 $('#_progress1').show();
	       	    	 $('#_progress2').show();
		    	    $.post(options.murl,datas,
		    	          function(data,status){
		    	            if(status=="success"){
		    	            	salesContractDetail.doExecute("closeWindow","proc");
		    	                /*UicDialog.Success("审批成功!",function(){
		    	                });*/
		    	            }else{
		    	                UicDialog.Error("审批失败！",function(){
		    	                	salesContractDetail.doExecute("closeWindow","proc");
		    	                });
		    	           }
		    	    });
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =salesContractDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
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
		salesContractDetail.doExecute('initDocument');
	}
});