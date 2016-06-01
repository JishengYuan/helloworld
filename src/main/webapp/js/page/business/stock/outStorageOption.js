define(function(require, exports, module) {
	var css = require('js/plugins/ztree/zTreeStyle.css');
	var $ = require("jquery");
	var Map = require("map");
	var DT_bootstrap = require("DT_bootstrap");
	var zTree = require("zTree_core");
	var zTree_excheck = require("zTree_excheck");
	var dataTable$ = require("dataTables");
	
	exports.init = function() {
		load();
	};

	function load() {
    	//列表
		var url = ctx+"/outStorage/getOutboundContractList?tmp="+Math.random();
		table=dataTable$('#taskTable').dataTable({
			"bProcessing": true,
			"bServerSide": true,
			"sAjaxSource":url,
			"bSort":false,
			"iDisplayLength": 5,
			"bRetrieve": true,
			"bFilter": true,
			"sServerMethod": "POST",
			"aoColumns": [
					  { "mData":"id" ,"mRender": function (data,row,obj) { 
		            	  var rstatus='';
		            	  var id = data;           	  
		            	  rstatus="<input type='radio' name='radio_product' value='"+id+"'/>";

	          		  return rstatus;
		              } },
		              { "mData":"ContractCode"},	    
		              { "mData": "ContractName"}
						],
			"sDom": "<'row'<'bt5left'l><'bt5right partnersel'>r>t<'row'<'bt5left'i><'bt5right'p>>",
			"sPaginationType": "bootstrap",
			"oLanguage": {
				"sLengthMenu": "页显示_MENU_ ",
				"sInfo":"从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
				"sSearch":"检索:",
				"sEmptyTable":"没有数据",
				"sInfoEmpty": "显示0条数据",
				"oPaginate":{
					"sPrevious": "",
					"sNext":''
				}
			},fnDrawCallback:function(){
//				$("input[name=radio_product]").click(function(){
//					  showProduct($(this).attr("value"));
//				 });
				$("#_sales_contracts_relate").unbind('click').click(function () {
					var contractId = $("input[name='radio_product']:checked").val();
					if(contractId == null||contractId == ""){
						return;
					}
        			showProduct(contractId);
        		});
            } 
		});	
		$('.dataTables_length').hide();
	}
	
	function showProduct(contractId){
		var url = ctx+"/outStorage/getOutboundSalesProduct?salesId="+contractId;
		$.ajax({
			url:url,
			async : false,
			success : function(result){
				var obj = $("#_product_table").find("tbody");
				$(obj).empty();
				for(var i = 0;i < result.length;i++){
					var str = "";
					str+="<tr>";
					str+="<td>"+result[i].productNo+"</td>";
					str+="<td>"+result[i].salesProductNum+"</td>";
					str+="<td>"+result[i].productNum+"</td>";
					str+="<td><input style='width:20px;height:10px;' num1='"+result[i].salesProductNum+"' num2='"+result[i].productNum+"' type='text' name='productNum' />";
					str+="<input type='hidden' value='"+result[i].contractCode+"' name='contractCode' />";
					str+="<input type='hidden' value='"+result[i].productNo+"' name='productCode' />";
					str+="</td>";
					str+='<td><input style="float:left;" type="checkbox" name="_del_product" /> <a style="font-size: 13px; float: right; padding: 2px;" class="btn btn-danger _a_del_product" class="_a_del_product"><i class="icon-remove"></i>删除</a></td>';
					str+="</tr>";
					$(obj).append(str);
				}
				$("._a_del_product").unbind('click').click(function() {
					$(this).parent().parent().remove();
				});
				$(obj).append("<tr><td colspan='5'><a id='_sino_eoss_sales_products_relate_add' style='' class='btn btn-success'>确定</a></td></tr>");
				$("#_sino_eoss_sales_products_relate_add").unbind('click').click(function() {
					
					if($("#storePlace").val() == null||$("#storePlace").val() == ""){
						UicDialog.Error("到货地点不能为空!", function() {
						});
						return;
					}
					
					var b = true;
					$('input[name="productNum"]').each(function(i,ele){
						var v = $(ele).attr("value");
						var num1 = $(ele).attr("num1");
						var num2 = $(ele).attr("num2");
						if(v <=0||v > num1||v > num1 - num2){
							b = false;
						}
					});
					if(b){
						var url = ctx + "/outStorage/doSalesOutboundProduct";
					    var datas=$("#_out_bound_form").serialize();
						$.post(url, datas, function(data, status) {
							if (data == "success") {
								UicDialog.Success("保存数据成功!", function() {
									var options = {};
						    		options.murl = "outStorage/outStorage";
						    		$.openurl(options);
								});
							} else {
								UicDialog.Error("保存数据失败!", function() {
									var options = {};
						    		options.murl = "outStorage/outStorage";
						    		$.openurl(options);
								});

							}
						});
					} else {
						UicDialog.Error("需要出库数量有误!", function() {
						});
					}
				});
			}  
		});
	}

});