define(function(require, exports, module) {
	var $ = require("jquery");
	var DT_bootstrap = require("DT_bootstrap");
	var dataTable$ = require("dataTables");
	require("formSelect");
	require("select2");
	require("select2_locale_zh-CN");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	var table;
    //表格列数
	 var lentr=0;
	
	exports.init = function() {

		 //加载供应商类型下拉框
		 getSupplierType();
		  load('1');
			$("#ok_Order_Add").unbind('click').click(function(){
				ok_order();
			});
	};
	var orderids= new Array();
	checkbutton=function(id){
		if(orderids.indexOf(id)!=-1){
			orderids.splice(orderids.indexOf(id), 1);
		}else{
			orderids.push(id);
		}
	
	}
	function load(supplierId) {
		orderids.splice(0,orderids.length);
		if(table!=null){
			table.fnDestroy();
		}

    	var url = ctx+"/business/rbmApply/getOrders?supplierId="+supplierId;
		table=dataTable$('#taskTable').dataTable({
			"bProcessing": true,
			"bServerSide": true,
			"sAjaxSource":url,
			"iDisplayLength": 5,
			"bRetrieve": true,
			"bFilter": true,
			"sServerMethod": "POST",
			"aoColumns": [
			              {"mData":"id" , "mRender": function (data,row,obj) { 
			            	  var rstatus='';
			            	  var id = data;      
			            	  if(orderids.indexOf(id)!=-1){
			            		  rstatus="<input type='checkbox' id='myCheckbox' name='myCheckbox'  onclick='checkbutton("+id+")'  value='"+id+"' checked/>";
			            	  }else{
			            		  rstatus="<input type='checkbox' id='myCheckbox' name='myCheckbox'  onclick='checkbutton("+id+")'  value='"+id+"'/>";
			            	  }
			            	  return rstatus;
			            	  }	
			              },
			              {"mData":"orderCode"},
			              {"mData":"orderName"},
			              {"mData":"orderAmount", "mRender": function (data,row,obj){
			            	  return "￥"+fmoney(data,2);
			              	}
			              },
						],
			"sDom": "<'row'<'bt5left'l><'bt5right partnersel'>r>t<'row'<'bt5left'i><'bt5right'p>>",
			"sPaginationType": "bootstrap",
			"oLanguage": {
				"sLengthMenu": "",
				"sInfo":"从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
				"sSearch":"检索:",
				"sEmptyTable":"没有数据",
				"sInfoEmpty": "显示0条数据",
				"oPaginate":{
					"sPrevious": "",
					"sNext":""
				}
			},
		});	
	}


    function searchTable(supplierId) {
		var param = "searchCode_searchName_searchAmount";
		var orderCode = $("#_orderCode").val();
		var orderName = $("#_orderName").val();
		var orderAmount = $("#_orderAmount").val();
		if (orderCode != null && orderCode != "") {
			param = param.replace("searchCode", orderCode);
		}
		if (orderName != null && orderName != "") {
			param = param.replace("searchName", orderName);
		}
		if (orderAmount != null && orderAmount != "") {
			param = param.replace("searchAmount", orderAmount);
		}
		
		table.fnFilter(param, 0);
	}
	
	function ok_order(){
		var orderId = "";
		orderId = orderids+",";
	     $.ajax({
             url: ctx+"/business/rbmApply/getOrderData?orderIds="+orderId,  // 提交的页面
             //data: $('#addForm').serialize(), // 从表单中获取数据
             type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
             dataType:"json",       
             success: function(data) {
            	 var dataMap = data.dataList;
            	 if(dataMap!=0){
 	           		for (var i = 0; i < dataMap.length; i++) {
 	           			var _serial_num=$("#pay_order tr").length;
 	           			var saleslist = "";
 	           			if(dataMap[i].sales!=null){
 	           				saleslist = dataMap[i].sales;
 	           			}
 	           			if(dataMap[i].neicai!=null){
 	           				saleslist = dataMap[i].neicai;
 	           			}
 	           			var str ="";
 	                    str ="<tr id='tr_"+_serial_num+"' class='orderinfo'>"
 	                    	+"<td  rowspan='"+Number(saleslist.length+1)+"' style='vertical-align : middle;' id ='orderCode"+_serial_num+"' name='orderCode' StrpayAmount='"+dataMap[i].orderCode+"' tdValue='"+dataMap[i].orderCode+"'>"+dataMap[i].orderCode+"</td>"
 	                    	+"<td id ='supplierShortName"+_serial_num+"' name='supplierShortName' tdValue='"+dataMap[i].supplierShortName+"'>"+dataMap[i].supplierShortName+"</td>"
 	                    	+"<td id ='unplanAmount"+_serial_num+"' name='unplanAmount' tdValue='"+dataMap[i].unplanAmount+"'>"+dataMap[i].unplanAmount+"</td>"
 	   						
 	                    	+"<td><span num='"+_serial_num+"' id='amount"+_serial_num+"' style='width:90%' name='amount'></span><input type='hidden' id='hiamount"+_serial_num+"' ></td>"
 	   						+"<td><input num='"+_serial_num+"' id='credit"+_serial_num+"' style='width:40px;' name='credit' type='text' value=''/></td>"
 	   						+"<td><div style='position: relative;' class='input-append date'><input type='text' data-format='yyyy-MM-dd' id='serviceEndTime"+_serial_num+"' style='width:75px' value=''/><span class='add-on'><i data-time-icon='icon-time' data-date-icon='icon-calendar'></i></span></div></td>"
 	   					     +"<td><div id='taxRate"+_serial_num+"'value='"+dataMap[i].taxRateType+"' style='width:90%'><span id='_taxRate"+_serial_num+"'></span></div></td>"
 	   						+"<td><div id='coursesType"+_serial_num+"'value='"+dataMap[i].coursesType+"' style='width:90%'><span id='_coursesType"+_serial_num+"'></span></div></td>"
 	   						
 	   						+"<td id ='orderId"+_serial_num+"' style='display:none'  name='orderId' tdValue='"+dataMap[i].orderId+"'></td>"
 	   					    +"<td id ='supplierId"+_serial_num+"' style='display:none'  name='supplierId'  tdValue='"+dataMap[i].supplierId+"'></td>"
 	   						+"<td><a serial_num='"+_serial_num+"' id='_remove_product_"+_serial_num+"' href='#'  class='_remove_product'><img src='"+ctx+"/images/state/delete.png' style='width:24px;height:24px;'/></a></td>"
 	   						+"</tr>";
 	                    if(dataMap[i].sales!=null){
 	                    	for(var j=0;j<saleslist.length;j++){
 	                    		str+="<tr class='children"+_serial_num+"'><td colspan=8><div style='float:left;width:350px;'>"+saleslist[j].sales.contractName+"<br/>"+saleslist[j].sales.contractCode+" </div>"
 	                    		+"<div style='float:left;margin-left:10px;'>￥"+fmoney(saleslist[j].sales.contractAmount,2)+"<br/>"+saleslist[j].sales.creatorName+"</div>"
 	                    		+"<div style='float:right;margin-left:10px;'>已报金额："+saleslist[j].sales.busiReimbursement+"<br/>本次合同发票金额：<input type='text'  style='width:100px;'name='reimbursement' class='_salesrbm reimbursement"+_serial_num+"' value='"+saleslist[j].amount+"' id='"+_serial_num+"' salesId='"+saleslist[j].sales.id+"' orderId='"+dataMap[i].orderId+"'></div></td></tr>";
 	                    	}
 	                    }
 	                    if(dataMap[i].neicai!=null){
 	                    	for(var j=0;j<saleslist.length;j++){
	 	   	       				str+="<tr class='children"+_serial_num+"'><td colspan=8><div style='float:left;width:350px;'>"+saleslist[j].neicai.purchasName+"<br/></div>"
	 	   	       				+"<div style='float:left;margin-left:10px;'>"+saleslist[j].neicai.creator+"</div>"
	 	   	       				+"<div style='float:right;margin-left:10px;'>本次合同发票金额：<input type='text'  style='width:100px;'name='reimbursement' class='_salesrbm reimbursement"+_serial_num+"' value='"+saleslist[j].amount+"' id='"+_serial_num+"' salesId='"+saleslist[j].neicai.id+"' orderId='"+dataMap[i].orderId+"'></div></td></tr>";
 	   	       				}
 	                    }
	 	          
 	           			$("#pay_order").append(str);
 	           		subTotal(_serial_num);
 	           		$('.date').datetimepicker({ 
 	        	        pickTime: false
 	        	 
 	        	      }); 
 	           		
 	           		  $(".reimbursement"+_serial_num).blur(function(){
 	           			  var str=subTotal(this.id);
 	           			  if(str==0){
 	           				 $(this).val("");
 	           			  }
 	           		  });
 	           			$("#amount"+_serial_num).blur(function(){
 	           				var amount = $("#amount"+_serial_num).val();
 	           				var unplanAmount = $("#unplanAmount"+_serial_num).attr("tdValue");
 	           				//var num=Number(unplanAmount-amount);
 	           				var num =metaUtil.accSub(unplanAmount,amount);
	           				if(num<0){
	           					UicDialog.Error("报销金额超过可报销金额！");
	           						return;
	           				}
 	           				getAmount();
 	           			});
 	           			
 	           		getTaxRateType(_serial_num);//加载税率下拉
 	           		getCoursesType(_serial_num);//加载科目下拉
 	           		}
 	           	}else {
 	           		alert("没有可以付款的订单！");
 	           	}
 	           		 $("._remove_product").unbind('click').click(function(){
 	           			 _removeProducts($(this).attr("serial_num"));
 	 				 });
 	            }
 	          });
 	 	     $('#dailogs1').modal('hide');
     }
	//税率下拉
   function getTaxRateType(_serial_num){
		var $fieldUserTypes = $("#taxRate"+_serial_num);
		$fieldUserTypes.addClass("li_form");
		var optionUserTypes = {
			writeType : 'show',
			showLabel : false,
			code : 'taxrateType',
			width : '120',
			onSelect :function(){
				var type= $("#taxRate"+_serial_num).formSelect("getValue")[0];
				$('#_taxRate'+_serial_num).val(type);
		}
		};
		$fieldUserTypes.formSelect(optionUserTypes);
		if($("#taxRate"+_serial_num).val() == null||$("#taxRate"+_serial_num).val() == ""){
			var roomId = "";
			$fieldUserTypes.find("ul li").each(function(i,ele){
    			if(i == 0){
    				var obj = $(ele);
					roomId = obj.attr("infinityid");
    			}
			});
    		$fieldUserTypes.formSelect('setValue',roomId);
    		$("#taxRate"+_serial_num).val(roomId);
		} else {
			$fieldUserTypes.formSelect('setValue',$('#_taxRate'+_serial_num).val());
		}
		
   }
	//科目下拉
	function getCoursesType(_serial_num){
		var $fieldUserTypes = $("#coursesType"+_serial_num);
		$fieldUserTypes.addClass("li_form");
		var optionUserTypes = {
			writeType : 'show',
			showLabel : false,
			code : 'coursesType',
			width : '160',
			onSelect :function(){
				var type= $("#coursesType"+_serial_num).formSelect("getValue")[0];
				$('#_coursesType'+_serial_num).val(type);
				if(type=="2"){ //如果是税费,设置为100% 
					var $taxRateTypes = $("#taxRate"+_serial_num);
					$taxRateTypes.formSelect('setValue',"100"); 
				}
		}
		};
		$fieldUserTypes.formSelect(optionUserTypes);
		if($("#coursesType"+_serial_num).val() == null||$("#coursesType"+_serial_num).val() == ""){
			var roomId = "";
			$fieldUserTypes.find("ul li").each(function(i,ele){
    			if(i == 5){
    				var obj = $(ele);
					roomId = obj.attr("infinityid");
    			}
			});
    		$fieldUserTypes.formSelect('setValue',roomId);
    		$("#coursesType"+_serial_num).val(roomId);
		} else {
			$fieldUserTypes.formSelect('setValue',$('#_coursesType'+_serial_num).val());
		}
	}
	
	function getSupplierType(){
		var $fieldUserTypes= $("#_supplierType");
		$fieldUserTypes.addClass("li_form");
		var optionSupplierName = {
				writeType : 'show',
				showLabel : false,
				width:"254", 
				required:true,
				code:'supplierType',
				onSelect :function(node){
					var id = $("#_supplierType").formSelect("getValue")[0];
					//getSupplierName(id);
					supplierInfoSelect(id);
				}
		};
		$fieldUserTypes.formSelect(optionSupplierName);
		$fieldUserTypes.formSelect('setValue',$('#_supplierType').val());
		//getSupplierName("");
		supplierInfoSelect("");
	
	}
	   function supplierInfoSelect(id){
			$("#supplierInfoSelect").select2({
				ajax: {
					url:ctx+'/business/order/findSupplierInfoValue?id='+id+'&tmp='+Math.random(),
					dataType: 'json',
					quietMillis: 100,
					data: function (term, page) { // page is the one-based page number tracked by Select2
						return {
							q: term, //search term
							page_limit: 10, // page size
							page: page, // page number
						};
					},
					results: function (data, page) {
						var more = (page * 10) < data.total; // whether or not there are more results available
						// notice we return the value of more so Select2 knows if more results can be loaded
						return {results: data.rows, more: more};
					}
				},
				formatResult: format, // omitted for brevity, see the source of this page
				formatSelection: format1, // omitted for brevity, see the source of this page
				dropdownCssClass: "bigdrop", // apply css that makes the dropdown taller
				escapeMarkup: function (m) { return m; } // we do not want to escape markup since we are displaying html in results
				});
				function format(item) { 
					 return item.shortName; 
				};
				
				function format1(item) {
					load(item.id);
					return item.shortName; 
				};
			}
	
		function _removeProducts(serialNum){
 			$("#tr_"+serialNum).remove();
 			$(".children"+serialNum).remove();
 			getAmount();
		}	
		//得到产品总价
		function getAmount(){
			var sum=0;
			    var len=$("#pay_order tr").length;
			    if(lentr<len){
					 lentr=len;
				 }
				for(var i = 1; i < lentr; i++){
					if($("#amount"+i).html()!=null&&$("#amount"+i).html()!=""){
						sum=metaUtil.accAdd( Number(sum),Number($("#amount"+i).html()));
					}
				}
			  $("#invoiceAmount").val(sum);
				var show_amount=fmoney(sum,2);
				$("#tatol").text("￥"+show_amount);
		}
		function subTotal(_serial_num){
   			var num=Number(0);
   			 $(".reimbursement"+_serial_num).each(function(index){
   				num=metaUtil.accAdd($(this).val(),num);
   				//num = Number($(this).val())+num;
   			 });
   			var amount =num.toFixed(2);
    		var unplanAmount = $("#unplanAmount"+_serial_num).attr("tdValue");
			//var nums=Number(unplanAmount-amount);
			var t =metaUtil.accSub(unplanAmount,amount);
			if(t<0){
				UicDialog.Error("报销金额超过可报销金额！");
				return 0;
			}
			$("#amount"+_serial_num).html(amount);
   			$("#hiamount"+_serial_num).val(amount);
			getAmount();
			return 1;
		}
		//金额格式化
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
});