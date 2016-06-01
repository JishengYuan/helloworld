define(function(require, exports, module) {
	//var css = require('js/plugins/ztree/zTreeStyle.css');
	var $ = require("jquery");

	var DT_bootstrap = require("DT_bootstrap");
	var zTree = require("zTree_core");
	var zTree_excheck = require("zTree_excheck");
	var dataTable$ = require("dataTables");
	require("formSelect");
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
	require("formUser");
	
    //表格列数
	 var lentr=0;
	
	exports.init = function() {
		load();
	};

	function load() {
		var isSingle = 'false';
		var accordion = null;
		var userForm = null;
		var userId = null;
		
		var $fieldStaff = $("#formStaff");
		// 选人组件
		var optionss = {
			inputName : "staffValues",
			// labelName : "选择用户",
			showLabel : false,
			width : "220",
			name : "code",
			value : "root",
			selectUser : false,
			radioStructure : true
		// radioStructure : field.single
		}
		optionss.addparams = [ {
			name : "code",
			value : "root"
		} ];

		$fieldStaff.formUser(optionss);
		
		var orgFullNameTypeJson = {};
		$.ajax({
			type : "GET",
			async : false,
			dataType : "json",
			url : ctx + "/business/order/getOrgFullName?tmp="+ Math.random(), 
			success : function(msg) {
				orgFullNameTypeJson = msg;
			}
		});
		
    	var url = ctx+"/business/order/getInterPurchas";
    	//列表
		table=dataTable$('#taskTable').dataTable({
			"bProcessing": true,
			"bServerSide": true,
			"sAjaxSource":url, 
			"bRetrieve": true,
			"bFilter": true,
			"sServerMethod": "POST",
			"aoColumns": [
			              {"mData":"id" , "mRender": function (data,row,obj) { 
			            	  var rstatus='';
			            	  var id = data;           	  
			            	  rstatus="<input type='radio' name='myRadio' value='"+id+"'/>";
			            	  return rstatus;
			            	  }	
			              },
			              {"mData":"creator"},
			              {"mData":"purchasName"},
			              {"mData":"expectedDeliveryTime"},
			              
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
					"sNext":''
				}
			},
		});	
		
		$("#_sino_eoss_order_searchInterPurchas").unbind('click').click(function() {
			searchTable();
		});
		$("#ok_Contract_Add").unbind('click').click(function(){
			$("#ok_Contract_Add").unbind('click');
			ok_InterPurchas();
		});
		getOrgFullName();
		
	}

	function getOrgFullName(){
		var $fieldCompPosType = $("#orgFullName");
		$fieldCompPosType.addClass("li_form");
		var optionCompPosType = {
			writeType : 'show',
			showLabel : false,
			url: ctx + "/business/order/getOrgFullName?tmp="+ Math.random(), 
			onSelect:function(node){
				$('#search_orgFullName').val($("#orgFullName").formSelect("getValue")[0]);
			},
			width : "244"
		};
		$fieldCompPosType.formSelect(optionCompPosType);
	}
	
    function searchTable() {
		var param = "searchCreator_searchTime";
		var creator = $("#formStaff").formUser("getValue");
		var time = $("#_time").val();
		/*var orgFullName = $("#orgFullName").formSelect('setValue',"");
		if (orgFullName != null && orgFullName != "") {
			param = param.replace("searchOrgFullName", orgFullName);
		}*/
		if (creator != null && creator != "") {
			param = param.replace("searchCreator", creator);
		}
		if (time != null && time != "") {
			param = param.replace("searchTime", time);
		}
		table.fnFilter(param, 0);
	}
	
	function ok_InterPurchas(){
		var interPurchasId = $('input[name="myRadio"]:checked').val();
		/*var interpurchasIds = $('#_interPurchas').val();
		interpurchasIds+=interPurchasId+",";
		$('#_interPurchas').val(interPurchasId);*/
	     $.ajax({
             url: ctx+"/business/order/getInterPurchasDate?interPurchasId="+interPurchasId,  // 提交的页面
             //data: $('#addForm').serialize(), // 从表单中获取数据
             type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
             dataType:"json",       
             success: function(data) {
            	 var dataMap = data.dataList;
            	
            	 if(dataMap!=0){
 	           		for (var i = 0; i < dataMap.length; i++) {
 	           			var _serial_num=$("#product_add tr").length;
 	           			var str ="";
 	                    str =$("<tr id='tr_"+_serial_num+"' tdValue='"+interPurchasId+"' >"
 	                    	+"<td id ='contractName"+_serial_num+"' name='contractName' tdValue='"+dataMap[i].contractName+"'>"+dataMap[i].contractName+"</td>"
 	                    	+"<td id ='productType"+_serial_num+"' name='productType' tdValue='"+dataMap[i].productType+"'>"+dataMap[i].productType+"</td>"
 	                    	+"<td id ='vendorCode"+_serial_num+"' name='vendorCode' tdValue='"+dataMap[i].vendorCode+"'>"+dataMap[i].vendorCode+"</td>"
 	                    	+"<td id='productNo"+_serial_num+"' name='productNo' tdValue='"+dataMap[i].productNo+"'>"+dataMap[i].productNo+"</td>"
 	                    	+"<td><input num='"+_serial_num+"' id='quantity"+_serial_num+"' style='width:90%' name='quantity' type='text' min='0' inputCount='"+dataMap[i].quantity+"' value='"+dataMap[i].quantity+"'/></td>"
 	   						+"<td><input num='"+_serial_num+"' id='unitPrice"+_serial_num+"' style='width:90%' name='unitPrice' type='text' value='"+dataMap[i].unitPrice+"'/></td>"
 	 						+"<td><input num='"+_serial_num+"' id='sub"+_serial_num+"' name='sub' type='text' style='width:90%' value='"+dataMap[i].sub+"'/></td>"
 	 						//+"<td><div id='inter_serviceType"+_serial_num+"'><input id='serviceType"+_serial_num+"' name='serviceType' type='hidden' style='width:60%' value='"+dataMap[i].serviceType+"'/></div></td>"
 	 						/*+"<td><div id='inter_serviceType"+_serial_num+"' value='"+dataMap[i].serviceType+"'><span id='serviceType"+_serial_num+"'><span></div></td>"
 	 						+"<td><div style='position: relative;' class='input-append date'><input type='text' data-format='yyyy-MM-dd' id='serviceStartTime"+_serial_num+"' style='width:55px' value='"+dataMap[i].serviceStartTime+"'/><span class='add-on'><i data-time-icon='icon-time' data-date-icon='icon-calendar'></i></span></div></td>"
 	 						+"<td><div style='position: relative;' class='input-append date'><input type='text' data-format='yyyy-MM-dd' id='serviceEndTime"+_serial_num+"' style='width:55px' value='"+dataMap[i].serviceEndTime+"'/><span class='add-on'><i data-time-icon='icon-time' data-date-icon='icon-calendar'></i></span></div></td>"
 	 						*/
 	 						+"<td id ='productId"+_serial_num+"' style='display:none'  name='productId' tdValue='"+dataMap[i].productId+"'></td>"
 	 						+"<td id ='mark "+_serial_num+"' style='display:none'  name='mark ' tdValue='"+dataMap[i].mark+"'></td>"
 	 						/*+"<td><a serial_num='"+_serial_num+"' id='_remove_product_"+_serial_num+"' style='width:60%' class='btn btn-danger _remove_product'><i style='width:100%'>删除</i></a></td>"*/
 	 						+"<td><input type='checkbox' name='myCheckbox' value="+_serial_num+" /></td>"
 	 						+"</tr>");
 	           			$("#product_add").append(str);
	 	           		/*$('.date').datetimepicker({ 
	 	        	        pickTime: false
	 	        	     }); */
 	           			$("#unitPrice"+_serial_num).blur(function(){
     	           			var num = $(this).attr("num");
     	           			var quantity = Number($('#quantity'+num).val());
     	           			var unitPrice = Number($('#unitPrice'+num).val());
	     	           		var subPrice = metaUtil.accMul(quantity,unitPrice);
	 	           			$("#sub"+num).val(changeTwoDecimal_f(subPrice));
     	           		    getAmount();
     	           		});
	 	           		$("#sub"+_serial_num).blur(function(){
	 	           			var num = $(this).attr("num");
	 	           			var subPrice = Number($('#sub'+num).val());
	 	           			var quantity = Number($('#quantity'+num).val());
	 	           			var unitPrice = metaUtil.accDiv(subPrice,quantity);
	 	           			$('#unitPrice'+num).val(changeTwoDecimal_f(unitPrice));
	 	           		    getAmount();
	 	           		});
 	           			$("#quantity"+_serial_num).blur(function(){
 	           				var num= $(this).attr("num");
	 	           			var inputCount = $(this).attr("inputCount");
							 var value = $(this).val();
							 if(parseInt(inputCount) < parseInt(value)){
								 UicDialog.Error("此型号还剩"+inputCount+"台");
								 $('#quantity'+num).val(inputCount);
							 } 
 	           				var quantity = $('#quantity'+num).val();
     	           			var unitPrice = $('#unitPrice'+num).val();
	     	           		var subPrice = quantity*unitPrice;
	 	           			$("#sub"+num).val(changeTwoDecimal_f(subPrice));
     	           		    getAmount();
 	           			});
 	           			$("#sub"+_serial_num).blur(function(){
 	           				getAmount();
 	           			});
 	             	    //getServiceType(_serial_num);
 	           		}
 	           	getAmount();
 	           	}else {
 	           		alert("没有可以添加的产品了！");
 	           	}
 	           		/* $("._remove_product").unbind('click').click(function(){
 	           			 _removeProducts($(this).attr("serial_num"));
 	 				 });*/
 	            }
 	          });
 	 	     $('#dailogs1').modal('hide');
     }
	//得到产品总价
	function getAmount(){
		var sum=0;
		    var len=$("#product_add tr").length;
		    if(lentr<len){
				 lentr=len;
			 }
			for(var i = 1; i < lentr; i++){
				if($("#sub"+i).val()!=null&&$("#sub"+i).val()!=""){
					sum=metaUtil.accAdd(Number(sum),Number($("#sub"+i).val()));
				}
			}
			$("#orderAmount").val(sum);
			var sumt=changeTwoDecimal_f(sum);
			var show_amount=fmoney(sumt,2);
			$("#tatol").text("￥"+show_amount);

	}
	/*function getServiceType(_serial_num){
			var $fieldUserTypes = $("#inter_serviceType"+_serial_num);
			$fieldUserTypes.addClass("li_form");
			var optionUserTypes = {
				writeType : 'show',
				showLabel : false,
				code : 'serviceType',
				width : "254",
				onSelect :function(){
					var type= $("#inter_serviceType"+_serial_num).formSelect("getValue")[0];
					$('#serviceType'+_serial_num).val(type);
			}
			};
			$fieldUserTypes.formSelect(optionUserTypes);
			if($("#inter_serviceType"+_serial_num).val() == null||$("#inter_serviceType"+_serial_num).val() == ""){
				var roomId = "";
				if($('#serviceEndTime'+_serial_num).val()!=null&&$('#serviceEndTime'+_serial_num).val()!=""){
					$fieldUserTypes.find("ul li").each(function(i,ele){
		    			if(i == 6){
		    				var obj = $(ele);
							roomId = obj.attr("infinityid");
		    			}
					});
		    		$fieldUserTypes.formSelect('setValue',roomId);
		    		$("#inter_serviceType"+_serial_num).val(roomId);
				}else{
					$fieldUserTypes.find("ul li").each(function(i,ele){
		    			if(i == 1){
		    				var obj = $(ele);
							roomId = obj.attr("infinityid");
		    			}
					});
		    		$fieldUserTypes.formSelect('setValue',roomId);
		    		$("#inter_serviceType"+_serial_num).val(roomId);
				}
			} else {
				$fieldUserTypes.formSelect('setValue',$('#serviceType'+_serial_num).val());
			}
	}*/
	
	function _removeProducts(serialNum){
		$("#tr_"+serialNum).remove();
		getAmount();
	 		/*UicDialog.Confirm("确认删除该条设备吗？",function () {
	 		});*/
	 		
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
	//强制保留2位小数，如：2，会在2后面补上00.即2.00  
	function changeTwoDecimal_f(x){
		var f_x = parseFloat(x);
		if (isNaN(f_x)){
			alert('请输入正确格式的数字！');
			return false;
		}
		f_x = Math.round(f_x*100)/100;
		var s_x = f_x.toString();
		var pos_decimal = s_x.indexOf('.');
		if (pos_decimal < 0){
			pos_decimal = s_x.length;
			s_x += '.';
		}
		while (s_x.length <= pos_decimal + 2){
			s_x += '0';
		}
		return s_x;
	}   
	
});