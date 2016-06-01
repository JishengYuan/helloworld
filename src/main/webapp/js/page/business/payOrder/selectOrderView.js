define(function(require, exports, module) {
	var $ = require("jquery");

	var DT_bootstrap = require("DT_bootstrap");
	var dataTable$ = require("dataTables");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	//------------------加载DataTable-----------Start--------------
	var Map = require("map");
	gridStore = new Map();//
	gridTypeStore=new Map();//表格行列类型
	gridFieldStore = new Map();
	require("json2");
	//------------------加载DataTable-------------End------------
	
    //表格列数
	 var lentr=0;
	 var table;
	exports.init = function() {
		load();
	};
	var orderId = new Array();
	checkOut=function(id){
		if(orderId.indexOf(id)!=-1){
			orderId.splice(orderId.indexOf(id), 1);
		}else{
			orderId.push(id);
		}
	};
	function load() {
		orderId.splice(0,orderId.length);
		if(table!=null){
			table.fnDestroy();
		}

    	var url = ctx+"/business/payOrder/getOrders?supplierId="+supplierId+"&subject="+subject;
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
			            	  if(orderId.indexOf(id)!=-1){
			            		  rstatus="<input type='checkbox' id='myCheckbox' name='myCheckbox' checked onclick='checkOut("+id+")' value='"+id+"'/>";
			            	  }else{
			            		  rstatus="<input type='checkbox' id='myCheckbox' name='myCheckbox' onclick='checkOut("+id+")' value='"+id+"'/>";
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
		$("#_sino_eoss_pay_searchOrder").unbind('click').click(function() {
			searchTable();
		});
		$("#ok_Order_Add").unbind('click').click(function(){
			ok_order();
		});
		getTime();
	}

	 /**
     *  时间插件
     */
      function  getTime(){
     	  $('.date').datetimepicker({ 
     	        pickTime: false
     	 
     	      }); 
      }
    function searchTable() {
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
		var orderIds = "";
		orderIds = orderId+",";
		/*var myCheckbox = $("input[name='myCheckbox']:checked");
		myCheckbox.each(function(i,ele){//循环拼装被选中项的值
			if($(ele).val() != 'null'){
				orderId = orderId+$(this).val()+',';
			}
		 });*/
		// orderId= +$('input[name="myCheckbox"]:checked').val()+",";
	     $.ajax({
             url: ctx+"/business/payOrder/getOrderData?orderIds="+orderIds,  // 提交的页面
             //data: $('#addForm').serialize(), // 从表单中获取数据
             type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
             dataType:"json",       
             success: function(data) {
            	 var dataMap = data.dataList;
            	 if(dataMap!=0){
 	           		for (var i = 0; i < dataMap.length; i++) {
 	           			var _serial_num=$("#pay_order tr").length;
 	           			var str ="";
 	                    str =$("<tr id='tr_"+_serial_num+"'>"
 	                    	+"<td id ='orderCode"+_serial_num+"' name='orderCode' StrpayAmount='"+dataMap[i].orderCode+"' tdValue='"+dataMap[i].orderCode+"'>"+dataMap[i].orderCode+"</td>"
 	                    	+"<td id ='supplierShortName"+_serial_num+"' name='supplierShortName' tdValue='"+dataMap[i].supplierShortName+"'>"+dataMap[i].supplierShortName+"</td>"
 	                    	+"<td id ='orderAmount"+_serial_num+"' name='orderAmount'  tdValue='"+dataMap[i].orderAmount+"'>"+dataMap[i].orderAmount+"</td>"
 	                    	+"<td id ='payAmount"+_serial_num+"' name='payAmount' tdValue='"+dataMap[i].payAmount.toFixed(2)+"'>"+dataMap[i].payAmount.toFixed(2)+"</td>"
 	   						+"<td><input num='"+_serial_num+"' id='amount"+_serial_num+"' style='width:90%' name='amount' type='text' value='"+dataMap[i].amount+"'/></td>"
 	   						+"<td><input num='"+_serial_num+"' id='credit"+_serial_num+"' style='width:90%' name='credit' type='text' value='"+dataMap[i].credit+"'/></td>"
 	   						+"<td id ='orderId"+_serial_num+"' style='display:none'  name='orderId' tdValue='"+dataMap[i].orderId+"'></td>"
 	   						+"<td><a serial_num='"+_serial_num+"' id='_remove_product_"+_serial_num+"' style='width:60%' class='btn btn-danger _remove_product'><i style='width:100%'>删除</i></a></td>"
 	   						+"</tr>");
 	           			$("#pay_order").append(str);
 	           			$("#amount"+_serial_num).blur(function(){
 	           				var payAmount = $("#payAmount"+_serial_num).attr("tdValue");
 	           				var amount = $("#amount"+_serial_num).val();
 	           				//var num=Number(payAmount-amount);
 	           				var num=metaUtil.accSub(payAmount,amount);
 	           				if(num<0){
 	           					UicDialog.Error("付款金额超过可付金额！");
 	           						return;
 	           				}
 	           				getAmount();
 	           			});
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

	function _removeProducts(serialNum){
	 			$("#tr_"+serialNum).remove();
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
				if($("#amount"+i).val()!=null&&$("#amount"+i).val()!=""){
					sum=metaUtil.accAdd(Number(sum),Number($("#amount"+i).val()));
				}
			}
			$("#payAmount").val(sum.toFixed(2));
			var show_amount=fmoney(sum,2);
			if($("#selectCurrency").val()=="usd"){
				$("#tatol").text("$"+show_amount);
			}else{
				$("#tatol").text("￥"+show_amount);
			}
			
			
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
	//强制保留2位小数，如：2，会在2后面补上00.即2.00  
	function toDecimal2(x) {              
		var f = parseFloat(x);              
		if (isNaN(f)) {
			alert("请按格式输入正确数字");
		   return;              
		}              
		var f = Math.round(x*100)/100;              
		var s = f.toString();              
		var rs = s.indexOf('.');              
		if (rs < 0) {  
		   rs = s.length;                  
		 　　s += '.';  
		}              
		while (s.length <= rs + 2) {  
		   s += '0';              
		}              
		return s;   
	}   
});