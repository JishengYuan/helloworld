define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("confirm_dialog");
	require("uic/message_dialog");
	require("changTag");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	//------------------加载DataTable-----------Start--------------
	var Map = require("map");
	require("confirm_dialog");
	require("uic/message_dialog");
	gridStore = new Map();//
	gridTypeStore=new Map();//表格行列类型
	gridFieldStore = new Map();
	require("json2");
	require("js/common/form/data_grid");
	//------------------加载DataTable-------------End------------
	//子页面关闭刷新该父页面
	var salesInvoice={
			config: {
            	namespace:ctx+'/sales/invoice'
	        },
	        methods :{
	        	initDocument:function(){
	        		
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
					$("#editGridName tbody").find("tr").each(function(i,ele){
						$(ele).find("td").each(function(j,elem){
							if(j == 3){
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
										width : "260"
											
								};
								_invoiceType.formSelect(optionCompPosType);
								_invoiceType.formSelect('setValue',$('#_eoss_sales_invoiceTypeId').val());
								$('#'+inputName).val($('#_eoss_sales_invoiceTypeId').val());
							}
						});
					});
					
					$("#_sino_eoss_sales_invoice_add").bind('click',function(){
	        			 salesInvoice.methods._add();
	        	     });
	        		
	        		/*
	        		 * 绑定触发事件
	        		 */
	        		 $("#_sino_eoss_sales_invoice_back").bind('click',function(){
	        			 salesInvoice.methods._back("detail");
	                 });
	        		 
	        		 if(parseInt($('#surplusAmount').val()) == 0){
	        			 $("#_sino_eoss_sales_invoicePlans_add").hide();
	        		 }

	        		 $("input[name='_invoice_checkbox']").removeAttr("disabled");
	        		 //发票变更
	        		 $("#_sino_eoss_sales_invoicePlans_change").bind('click',function(){
	        			 salesInvoice.methods.invoiceChange();
		             });
	        		 
	        		 //继续添加按钮
	        		 $("#_sino_eoss_sales_invoicePlans_add").bind('click',function(){
	        			/* $("#_sino_eoss_sales_invoicePlans_add").unbind('click');
			        	 var opts = {};
			        	 opts.url = "/apply";
			        	 opts.id=$("#id").val();
			        	 salesInvoice.methods.toContinueAdd(opts);*/
	        			 $("#_invoice_add").show();
	        			 $("#_sino_eoss_sales_invoice_add").show();
		             });
	        		 $("#_sino_eoss_sales_invoice_submit").bind('click',function(){
	        			 $("#_sino_eoss_sales_invoice_submit").unbind('click');
			        	 var opts = {};
			        	 opts.url = "/handleFlow";
			        	 salesInvoice.methods.approve(opts);
	        	     });
	        		 //回显合同类型
	        		 salesInvoice.methods.getContractType();
	        		 //回显客户，联系人，联系人手机号
	        		 salesInvoice.methods.getCustomerInfoContactsPhone();
	        		 
	        		 var invoiceTypeJson = {};
	        		 $.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
			    			success : function(msg) {
			    				invoiceTypeJson = msg;
			    			}
			    		});
	        		 
	        		 $("td[id^=invoiceTypeId_]").each(function(i,ele){
	        			 for(var j = 0;j < invoiceTypeJson.length;j++){
	        				 if($(ele).text() == invoiceTypeJson[j].id){
	        					 $(ele).html(invoiceTypeJson[j].name);
	        				 }
	        			 }
	        		 });
	        		 
	        		 //绑定“审核详情”按钮数据
	        		 $("#_sino_eoss_sales_contract_invoiceApplys_table tbody").find("tr").each(function(i,ele){
	        			 	//回显发票类型

	        			 	//绑定“查看审查日志”按钮
	        			 	var porId=$('#porId_'+i).attr("porId");
	        			 	$('#porId_'+i).popover({
	        			 		trigger :'focus',
	        			 		placement:'left',
	        			 		content:salesInvoice.methods.onloadCheckedData(porId),
	        			 		html:true	
	        			 	}).click(function(e) {
	        			 		e.preventDefault();
	        			 	});
					});
	        		 $("a[name='contract_print']").bind('click',function(){
	        			 salesInvoice.methods.print($(this).attr('contractId'));
	                 });
	        		 
//	        		 $("#_sino_eoss_sales_contract_invoiceApplys_table tbody").find("tr").each(function(i,ele){
//	        			 var contractId=$('#contractId_'+i).attr("contractId");
//	        			 	$('#contractId_'+i).popover({
//	        			 		trigger :'focus',
//	        			 		placement:'left',
//	        			 		content:salesInvoice.methods.print(contractId),
//	        			 		html:true	
//	        			 	}).click(function(e) {
//	        			 		e.preventDefault();
//	        			 	});
//	        		 });
	        		 if(form.dataList.length > 0){
	        			 $("#_invoice_add").show();
	        			 $("#_sino_eoss_sales_invoice_add").show();
	        			 $("#_sino_eoss_sales_invoicePlans_add").hide();
	        			 $("#_sino_eoss_sales_invoicePlans_change").hide();
	        		 }
	        	},
	        	invoiceChange:function(){
	        		
	        		var tdChage = $("td[tdChange='1']").length;
	        		if(tdChage != 0){
	        			UicDialog.Error("变更正在进行中,不能变更!");
	        			return;
	        		}
	        		
	        		var $obj = $("input[name='_invoice_checkbox']:checked");
	        		var ids = "";
	        		var b = true;
	        		$obj.each(function(i,ele){
	        			if($("#_td_"+$(ele).val()).attr("tdstate") != "TGSH"){
	        				b = false;
        				}
	        			if(i == $obj.length - 1){
        					ids+=$(ele).val();
	        			} else {
	        				ids+=$(ele).val()+",";
	        			}
	        		});
	        		if(!b){
	        			UicDialog.Error("所选发票必须都是审批通过的!");
	        			$("#_invoice_add").hide();
	        			return;
	        		}
	        		if(ids == ""){
	        			 UicDialog.Error("请选择要变更的发票明细!");
	        			 $("#_invoice_add").hide();
	        			 return;
	        		} else {
	        			$("#_sino_eoss_sales_invoicePlans_change").hide();
        				$("input[name='_invoice_checkbox']").attr("disabled","disabled");
	        			$("#invoiceIds").val(ids);
        				$("#_invoice_add").show();
        				$("#_sino_eoss_sales_invoice_add").show();
	        		}
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
		        getCustomerInfoContactsPhone: function(){
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
	        	_back:function(colseType){
		    		//刷新父级窗口
		        	if(colseType=='proc'){//如果是“待办”审核后刷新“待办事项”这个父页面
		        		window.opener.reload();
		        	}
		        	/*if(colseType=='detail'){//如果是“待办”审核后刷新“合同管理”这个父页面
		        		window.opener.childColseRefresh();
		        	}*/
		        	//关闭当前子窗口
		        	metaUtil.closeWindow();  
	        	},
	        	onloadInvoiceType:function(invoiceTypeId,j){
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == invoiceTypeId){
		    						$('#invoiceTypeId_'+j).text(msg[i].name)
		    					}
		    				}
		    			}
		    		});
	        		
	        	},
	        	print:function(contractId){
	        		var url = ctx+"/sales/invoice/getPrint?contractId="+contractId+"&tmp="+Math.random();
	        		$.ajax({url:url,async:false,success:function(data){
	        			window.open(url,'_blank','status=no,scrollbars=yes,top=150,left=150,width=800,height=500');
	        		  }});
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
	        	approve:function(opts){
		        	var options = {};
		    		options.murl = salesInvoice.config.namespace + opts.url;
		    		var datas=$("#_sino_eoss_sales_invoice_approveForm").serialize();
		    	    $.post(options.murl,datas,
		    	          function(data,status){
		    	            if(status=="success"){
		    	            	salesInvoice.methods._back("proc");
		    	              /*  UicDialog.Success("审批成功!",function(){
		    	                });*/
		    	            }else{
		    	                UicDialog.Error("审批失败！",function(){
		    	                	salesInvoice.methods._back("proc");
		    	                });
		    	           }
		    	    });
	        	},
	        	toContinueAdd:function(opts){
	        		var url = salesInvoice.config.namespace + opts.url+"?colseType=prod&id="+opts.id+"&tmp="+Math.random();;
	        		window.open(url);
	        	},
	        	_add:function(){
	        		
	        		var changeInvoiceIds = $("#invoiceIds").val();
	        		var changeInvoiceAmount = 0;
	        		var changeInvoiceIdss = changeInvoiceIds.split(",");
	        		for(var i = 0;i < changeInvoiceIdss.length;i++){
	        			changeInvoiceAmount=metaUtil.accAdd(changeInvoiceAmount,$("#_td_count_"+changeInvoiceIdss[i]).attr("tdamount"));
	        		}
	        		
	        		var tdChage = $("td[tdChange='1']").length;
	        		if(tdChage != 0&&changeInvoiceIds != ""){
	        			UicDialog.Error("变更正在进行中,不能变更,请不要选择条目!");
	        			return;
	        		}
	        		
		       		 var obj = new Object();
		       		 var grid = gridStore.get(form.name);// 获取表格行列name
		       		 var gridType=gridTypeStore.get(form.name);
		       		 var gridStoreData =  $('#' + form.name + ' tbody tr').data_grid('getSelectValue',grid, form.name,gridType);
		       		 obj[form.name]=gridStoreData;
		       		 if(gridStoreData == ''){
		       			UicDialog.Error("请选择要提交的发票明细!");
	        			return;
		       		 }
		       		 var json_data = JSON.stringify(obj);
		       	     $("#tableData").val(json_data);
		       		 var applyAmount=0;
	        		 $("#editTable tbody").find("tr").each(function(i,ele){ 
	        			 var index;
	        			 if(i==0){
	        				 index=i+1;
	        			 }else if(i>0){
	        				 index=1+i*4;
	        			 }
	        			 if($("#invoiceAmount_"+index).val()!=""&&$("#invoiceAmount_"+index).val()!=null){
	        				 applyAmount=metaUtil.accAdd(applyAmount,$("#invoiceAmount_"+index).val());
	        			 }
					});
	        		 
	        		 var surplusAmount=$("#surplusAmount").val();
	        		 if(changeInvoiceIds == ""||changeInvoiceIds == null){
	        			//与剩余可开发票金额进行比较如果大于剩余金额，则提示不让提交
		        		 if(applyAmount>surplusAmount){
		        			 UicDialog.Error("此次申请的发票金额超过了剩余可开发票金额！");
		        			 return;
		        		 }
	        		 } else {
	        			 if(applyAmount  > (metaUtil.accAdd(surplusAmount,changeInvoiceAmount))){
	        				 UicDialog.Error("此次申请的发票金额和要作废的发票金额之和超过了剩余可开发票金额！");
		        			 return;
	        			 }
	        		 }
	        		 $("#_sino_eoss_sales_invoice_add").unbind('click');
	        		var datas=$("#_sino_eoss_sales_invoice_approveForm").serialize();
	        		var url = ctx+'/sales/invoice/doSave?tmp='+Math.random();
	        		$.post(url,datas,
	    	            function(data,status){
	    	            	if(data=="success"){
	    	                	  UicDialog.Success("保存数据成功!",function(){
	    	                		  window.location.reload();
//	    	                		  salesInvoice.methods._back();
	    	                	  });
	    	                  }else{
	    	                  	  UicDialog.Error("保存数据失败！");
	    	                  	  window.location.reload();
	    	                  }
	    	        });
	        	}
	        }
		}
	exports.init = function(){
		salesInvoice.methods.initDocument();  
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