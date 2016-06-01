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
	require("formSubmit");
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
	        		salesContractDetail.methods.getContractType();

			   		 var relateDeliveryContractId = $("#relateDeliveryContractId").val();
			   		 //var cpSalesContractId = $("#cpSalesContractId").val();
			   		 var id = $("#salesContractId").val();
			   		 var doState = $("#doState").val();
			   		 var doFlag = 0;
			   		 var isNaNFlag = 0;
			   		 //未确认清单 
			   		 if(doState==0){
			   		 $.ajax({
			             url: ctx+"/stockup/contractCost/getProductData?id="+id,  // 提交的页面
			             //data: $('#addForm').serialize(), // 从表单中获取数据
			             type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
			             dataType:"json",       
			             success: function(data) {
			            	 var dataMap = data;//.dataList;
			            	 var str ="";  	
			            	 if(dataMap!=0){
			            		 for(var i=0;i<data.length;i++){
			            			 var productSize = data[i].products.length;
			            			 var orderAmountNum = data[i].orderAmount;
			            			 if(productSize>0){
			            			 str ="<table num='"+i+"' id='_sino_eoss_sales_contract_products_table"+i+"' class='table table-bordered' style='margin-bottom:20px !important;'>"
			            			 +"<tr><td colspan='8' style='padding:17px 10px 10px;font-size:14px;'>"
			            			 		 +"<div style='height:35px;'><div style='float:left;width:270px;'>合同编号:"
			            			 		 +"<a href='"+ctx+"/sales/contract/contractOrderAllDetail_link?id="+data[i].bhsaleContractId+"&flat=search' target='_blank'><span style='color: #0000dd;'>"+data[i].bhSaleContractCode+"</span></a></div>"
			            			 		 +"<div style='float:left;width:180px;'>总成本:<span style=''>￥"+fmoney(data[i].bhqrCost+data[i].bhbusinessCost,2)+"</span></div>"
			            			 		 +"<input num='"+i+"' id='orderAmount"+i+"'  type='hidden' style='width:80%' value='"+data[i].orderAmount+"'/>"
			            			 		 +"<div style='float:right;width:220px;'>&nbsp;&nbsp;&nbsp;未确认:<span style='' class='moneyfomat' >￥"+fmoney(data[i].bhbusinessCost,2)+"</span></div>"
			            			 		 +"<div style='float:right;width:200px;'>已确认:<span style='' >￥"+fmoney(data[i].bhqrCost,2)+"</span></div></div>"
			            			 		 +"<input type='hidden' id='notAmount_"+i+"' num='"+i+"' value='"+data[i].bhwqrCost+"' />";
			            			 		 //第二行
			            			 		 +"<div style='height:30px;'><div style='float:left;width:380px;'>订单编号:<a href='"+ctx+"/business/order/detail?id="+data[i].orderId+"&flat=search' target='_blank'><span style='color: #0000dd;'>"+data[i].orderCode+"</span></a></div>"
			            			 		 +"<div style='float:left;width:180px;'>订单金额:<span style=''>￥"+fmoney(data[i].orderAmount,2)+"</span></div>"
			            			 		 +"<input num='"+i+"' id='orderAmount"+i+"' type='hidden' style='width:80%' value='"+data[i].orderAmount+"'/>"
			            			 		 +"<div style='float:right;width:180px;'>本次商务成本:<span style='font-weight:bold;color: #1076BE;' id='_buyCount"+i+"'></span></div></div></td></tr>"
			            			 		 
			            			     str+="<tr id='_contract_show' style='background-color: #f3f3f3;color: #888888;'>"
			   					      		 +"<th style='width:5%;'>序号</th>"
			   					      		 +"<th style='width:25%;' id='type_th'>备货合同名称</th>"
			   					      		 +"<th style='width:13%;' id='type_th'>产品类型</th>"
			   					      		 +"<th style='width:12%;' id='partner_th'>产品厂商</th>"
			   					      		 +"<th style='width:16%;' id='product_th'>产品型号</th>"
					            			 +"<th style='width:7%;'>数量</th>"
					            			 +"<th style='width:10%;'>单价</th>"
					            			 +"<th style='width:11%;'>合计</th>"
			            			 		+"</tr>"
			            			 		//+"</thead>"
			            			 		+"<tbody class='tbodyClass'>";
			            			 		var _serial_num=$("#_sino_eoss_sales_contract_products_table"+i+" tr").length;
			            				 for(var j=0; j<productSize; j++){
					     		        		var number = ""+i+j;
					     		        		var cpQuantity = data[i].products[j][0];
					     		        		var syQuantity = data[i].products[j][14];
					     	           			var unitPriceNum = data[i].products[j][3];
					     	           			var okQuantity = 0;
					     	           			if(syQuantity > 0){
					     	           				if(cpQuantity<=syQuantity){
					     	           					okQuantity = cpQuantity;
					     	           				}else{
					     	           					okQuantity = syQuantity;
					     	           				}
					     	           			
					     	           			var totalNum = metaUtil.accMul(okQuantity,unitPriceNum);
					     	           		       			
				     		        			str +="<tr id='tr_"+number+"' tdValue='"+relateDeliveryContractId+"' >"
						 	                    	+"<td>"+(j+1)+"<input num='"+_serial_num+j+"' id='id"+_serial_num+j+"' name='ids' type='hidden' value='"+data[i].products[j][10]+"'/>"
						 	                    	+"<input id='id"+number+"' name='bhSaleContractIds' type='hidden' value='"+data[i].products[j][7]+"'/>" 
						 	                    	+"<input id='id"+number+"' name='bhProductIds' type='hidden' value='"+data[i].products[j][8]+"'/>"
						 	                    	+"<input id='id"+number+"' name='orderIds' type='hidden' value='"+data[i].products[j][9]+"'/></td>"
						 	                    	+"<td><input type='hidden' name='bhSaleContractNames' value='"+data[i].products[j][15]+"'/>"+data[i].products[j][15]+"</td>"
						 	                    	+"<td><input type='hidden' name='productTypeNames' value='"+data[i].products[j][6]+"'/>"+data[i].products[j][6]+"</td>"
						 	                    	+"<td><input type='hidden' name='productPartnerNames' value='"+data[i].products[j][5]+"'/>"+data[i].products[j][5]+"</td>"
						 	                    	+"<td><input type='hidden' name='productNames' value='"+data[i].products[j][2]+"'/>"+data[i].products[j][2]+"</td>"
						 	   						+"<td>"+okQuantity+""
						 	   						+"<input num='"+number+"' id='quantity1"+number+"' name='quantitys' type='hidden' style='width:80%' value='"+okQuantity+"'/></td>"
						 	 						+"<td><input num='"+number+"' class='unitPriceCalss' id='unitPrice"+number+"' name='unitPrices' type='text' style='width:85%' value='"+data[i].products[j][3]+"'/></td>"
						 	 						+"<td><span id='sub"+number+"'>"+totalNum+"</span><input num='"+number+"' id='totalPrice"+number+"' name='totalPrices' type='hidden' value='"+totalNum+"'/></td>"
						 	 						//+"<td><span id='sub"+_serial_num+j+"'>"+data[i].products[j][4]+"</span><input num='"+_serial_num+j+"' id='totalPrice"+_serial_num+j+"' name='totalPrices' type='hidden' value='"+data[i].products[j][4]+"'/></td>"
						 	 						+"</tr>";	
					     	           			}
					     		        	}
			     		        				str +="</tbody>"
			     		        				+"</table>";
			     		        				$("#product_table").append(str);

			           
			            			//采购成本
			            			 var buyCount = 0;
						   			 $('#_sino_eoss_sales_contract_products_table'+i+' tbody').find("tr").each(function(i,ele){
						   				 $(ele).find("td").each(function(j,elem){
						   					 if(j == 7){
						   						buyCount=metaUtil.accAdd(buyCount,$(elem).find("input").first().val());
						   					 }
						   				 });
						   			 });
						   			$('#_buyCount'+i).html('￥'+fmoney(buyCount,2)); 
						   			if(buyCount>orderAmountNum){
						   				doFlag = 1;
						   			}else{
						   				doFlag = 0;
						   			}
			            			}					   			
			     		        }
			            		 
		            			 
		            			 //修改单价，自动计算总价
		            			 $(".unitPriceCalss").blur(function(){
		            				 var num = $(this).attr("num");
		            				 var tableId = $(this).parent().parent().parent().parent().attr("num");
		     	           			 var quantity = Number($('#quantity1'+num).val());
		     	           			 var unitPrice = Number($('#unitPrice'+num).val());
		     	           			 var orderAmount = Number($('#orderAmount'+tableId).val());
		     	           			 
		     	           			 if(!isNaN(unitPrice) && !isNaN(quantity)){	
		     	           				 isNaNFlag = 0;
		     	           				 $('#unitPrice'+num).css("background-color","#FFFFFF");
			     	           			 var subPrice = metaUtil.accMul(quantity,unitPrice);
				 	           			 $("#sub"+num).html(changeTwoDecimal_f(subPrice));
				 	           			 $("#totalPrice"+num).val(changeTwoDecimal_f(subPrice));
					 	           		 //采购成本
					 	           		 var buyCount = 0;
							   			 $('#_sino_eoss_sales_contract_products_table'+tableId+' tbody').find("tr").each(function(i,ele){
							   				 $(ele).find("td").each(function(j,elem){
							   					 if(j == 7){
							   						buyCount=metaUtil.accAdd(buyCount,$(elem).find("input").first().val());
							   					 }
							   				 });
							   			 });
							   			$('#_buyCount'+tableId).html('￥'+fmoney(buyCount,2));							   			
					 	           			
							   			//采购成本合计
					 	           		var totalCount = 0;
					 	           		$(".tbodyClass").find("tr").each(function(i,ele){
							   				 $(ele).find("td").each(function(j,elem){
							   					 if(j == 7){
							   						 totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
							   					 }
							   				 });
							   			 });
							   			$('#_product_total').html('￥'+fmoney(totalCount,2));
							   			$('#productTotal').val(totalCount);
							   			
							   			if(buyCount>orderAmount){
							   				doFlag = 1;
							   				UicDialog.Error("采购成本不能大于订单金额！采购成本："+buyCount+"，订单金额："+orderAmount);
							   				return;
							   			}else{
							   				doFlag = 0;
							   			}
		     	           			}else{
		     	           				isNaNFlag = 1;
		     	           				if(isNaN(unitPrice) && isNaN(quantity)){
		     	           					$('#unitPrice'+num).css("background-color","#FF0000");
		     	           					
		     	           				}else{
			     	           				if(!isNaN(unitPrice)){
					     	           			$('#unitPrice'+num).css("background-color","#FFFFFF");
			     	           				}else{
			     	           					UicDialog.Error("单价 请输入正确格式的数字！");
			     	           					$('#unitPrice'+num).css("background-color","#FF0000");
			     	           					//isNaNFlag = 1;
			     	           				}
		     	           				}
			     	           			return;
		     	           			}
		            	     	}); 
			            		 
			            		 
			            		 //采购成本合计 
			            		 var totalCount = 0;
					   			 $(".tbodyClass").find("tr").each(function(i,ele){
					   				 $(ele).find("td").each(function(j,elem){
					   					 if(j == 7){
					   						 totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
					   					 }
					   				 });
					   			  });
					   			 $('#_product_total').html('￥'+fmoney(totalCount,2));
					   			 $('#productTotal').val(totalCount);
			            		 
			 	
			 	           	}
			 	            }
			 	          });
	        	}else{  //已确认清单 
	        		 $.ajax({
	        			 //alert(id+"--"+cpSalesContractId);
			             url: ctx+"/stockup/contractCost/getProductData?id="+id,  // 提交的页面
			             //data: $('#addForm').serialize(), // 从表单中获取数据
			             type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
			             dataType:"json",       
			             success: function(data) {
			            	 var dataMap = data;//.dataList;
			            	 var str ="";  	
			            	 if(dataMap!=0){
			            		 for(var i=0;i<data.length;i++){
			            			 var productSize = data[i].products.length;
			            			 var orderAmountNum = data[i].orderAmount;
			            			 if(productSize>0){
				            			 str ="<table num='"+i+"' id='_sino_eoss_sales_contract_products_table"+i+"' class='table table-bordered' style='margin-bottom:20px !important;'>"
				            			 +"<tr><td colspan='8' style='padding:17px 10px 10px;font-size:14px;'>"
				            			 		 +"<div style='height:35px;'><div style='float:left;width:270px;'>合同编号:"
				            			 		 +"<a href='"+ctx+"/sales/contract/detail?id="+data[i].bhsaleContractId+"&flat=search' target='_blank'><span style='color: #0000dd;'>"+data[i].bhSaleContractCode+"</span></a></div>"
				            			 		 +"<div style='float:left;width:180px;'>总成本:<span style=''>￥"+fmoney(+data[i].bhbusinessCost,2)+"</span></div>"
				            			 		 +"<input num='"+i+"' id='orderAmount"+i+"'  type='hidden' style='width:80%' value='"+data[i].orderAmount+"'/>"
				            			 		 +"<div style='float:right;width:220px;'>&nbsp;&nbsp;&nbsp;未确认:<span style='' class='moneyfomat' >￥"+fmoney(data[i].bhwqrCost,2)+"</span></div>"
				            			 		 +"<div style='float:right;width:200px;'>已确认:<span style='' >￥"+fmoney(data[i].bhqrCost,2)+"</span></div></div>"
			            			 		    //第二行
				            			 		 +"<div style='height:30px;'><div style='float:left;width:380px;'>订单编号:<a href='"+ctx+"/business/order/detail?id="+data[i].orderId+"&flat=search' target='_blank'><span style='color: #0000dd;'>"+data[i].orderCode+"</span></a></div>"
				            			 		 +"<div style='float:left;width:180px;'>订单金额:<span style=''>￥"+fmoney(data[i].orderAmount,2)+"</span></div>"
				            			 		 +"<input num='"+i+"' id='orderAmount"+i+"' type='hidden' style='width:80%' value='"+data[i].orderAmount+"'/>"
				            			 		 +"<div style='float:right;width:180px;'>本次商务成本:<span style='font-weight:bold;color: #1076BE;' id='_buyCount"+i+"'></span></div></div></td></tr>"
				            			 		 
				            			     str+="<tr id='_contract_show' style='background-color: #f3f3f3;color: #888888;'>"
				   					      		 +"<th style='width:5%;'>序号</th>"
				   					      		 +"<th style='width:25%;' id='type_th'>备货合同名称</th>"
				   					      		 +"<th style='width:13%;' id='type_th'>产品类型</th>"
				   					      		 +"<th style='width:12%;' id='partner_th'>产品厂商</th>"
				   					      		 +"<th style='width:16%;' id='product_th'>产品型号</th>"
						            			 +"<th style='width:7%;'>数量</th>"
						            			 +"<th style='width:10%;'>单价</th>"
						            			 +"<th style='width:11%;'>合计</th>"
				            			 		+"</tr>"
				            			 		//+"</thead>"
				            			 		+"<tbody class='tbodyClass'>";
				            			 		var _serial_num=$("#_sino_eoss_sales_contract_products_table"+i+" tr").length;
				            				 for(var j=0; j<productSize; j++){
						     		        		var number = ""+i+j;
						     		        		var cpQuantity = data[i].products[j][0];
						     		        		var syQuantity = data[i].products[j][14];
						     	           			var unitPriceNum = data[i].products[j][3];
						     	           			var okQuantity = 0;
						     	           			if(syQuantity > 0){
						     	           				if(cpQuantity<=syQuantity){
						     	           					okQuantity = cpQuantity;
						     	           				}else{
						     	           					okQuantity = syQuantity;
						     	           				}
						     	           			
						     	           			var totalNum = metaUtil.accMul(okQuantity,unitPriceNum);
						     	           		       			
					     		        			str +="<tr id='tr_"+number+"' tdValue='"+relateDeliveryContractId+"' >"
							 	                    	+"<td>"+(j+1)+"<input num='"+_serial_num+j+"' id='id"+_serial_num+j+"' name='ids' type='hidden' value='"+data[i].products[j][10]+"'/>"
							 	                    	+"<input id='id"+number+"' name='bhSaleContractIds' type='hidden' value='"+data[i].products[j][7]+"'/>" 
							 	                    	+"<input id='id"+number+"' name='bhProductIds' type='hidden' value='"+data[i].products[j][8]+"'/>"
							 	                    	+"<input id='id"+number+"' name='orderIds' type='hidden' value='"+data[i].products[j][9]+"'/></td>"
							 	                    	+"<td><input type='hidden' name='bhSaleContractNames' value='"+data[i].products[j][15]+"'/>"+data[i].products[j][15]+"</td>"
							 	                    	+"<td><input type='hidden' name='productTypeNames' value='"+data[i].products[j][6]+"'/>"+data[i].products[j][6]+"</td>"
							 	                    	+"<td><input type='hidden' name='productPartnerNames' value='"+data[i].products[j][5]+"'/>"+data[i].products[j][5]+"</td>"
							 	                    	+"<td><input type='hidden' name='productNames' value='"+data[i].products[j][2]+"'/>"+data[i].products[j][2]+"</td>"
							 	   						+"<td>"+okQuantity+""
							 	   						+"<input num='"+number+"' id='quantity1"+number+" 'name='quantitys' type='hidden' style='width:80%' value='"+okQuantity+"'/></td>"
							 	 						+"<td>"+data[i].products[j][3]+"</td>"
							 	 						+"<td><span id='sub"+number+"'>"+totalNum+"</span><input num='"+number+"' id='totalPrice"+number+"' name='totalPrices' type='hidden' value='"+totalNum+"'/></td>"
							 	 						//+"<td><span id='sub"+_serial_num+j+"'>"+data[i].products[j][4]+"</span><input num='"+_serial_num+j+"' id='totalPrice"+_serial_num+j+"' name='totalPrices' type='hidden' value='"+data[i].products[j][4]+"'/></td>"
							 	 						+"</tr>";	
						     	           			}
						     		        	}
				     		        				str +="</tbody>"
				     		        				+"</table>";
				     		        				$("#product_table").append(str);

				           
				            			//采购成本
				            			 var buyCount = 0;
							   			 $('#_sino_eoss_sales_contract_products_table'+i+' tbody').find("tr").each(function(i,ele){
							   				 $(ele).find("td").each(function(j,elem){
							   					 if(j == 7){
							   						buyCount=metaUtil.accAdd(buyCount,$(elem).find("input").first().val());
							   					 }
							   				 });
							   			 });
							   			$('#_buyCount'+i).html('￥'+fmoney(buyCount,2)); 
							   			if(buyCount>orderAmountNum){
							   				doFlag = 1;
							   			}else{
							   				doFlag = 0;
							   			}
				            			}					   			
			     		        }
			            		 
		            			 
		            			 //修改单价，自动计算总价
		            			 $(".unitPriceCalss").blur(function(){
		            				 var num = $(this).attr("num");
		            				 var tableId = $(this).parent().parent().parent().parent().attr("num");
		     	           			 var quantity = Number($('#quantity1'+num).val());
		     	           			 var unitPrice = Number($('#unitPrice'+num).val());
		     	           			 var orderAmount = Number($('#orderAmount'+tableId).val());
		     	           			
		     	           			 if(!isNaN(unitPrice) && !isNaN(quantity)){	
		     	           				 isNaNFlag = 0;
		     	           				 $('#unitPrice'+num).css("background-color","#FFFFFF");
			     	           			 var subPrice = metaUtil.accMul(quantity,unitPrice);
				 	           			 $("#sub"+num).html(changeTwoDecimal_f(subPrice));
				 	           			 $("#totalPrice"+num).val(changeTwoDecimal_f(subPrice));
					 	           		 //采购成本
					 	           		 var buyCount = 0;
							   			 $('#_sino_eoss_sales_contract_products_table'+tableId+' tbody').find("tr").each(function(i,ele){
							   				 $(ele).find("td").each(function(j,elem){
							   					 if(j == 7){
							   						buyCount=metaUtil.accAdd(buyCount,$(elem).find("input").first().val());
							   					 }
							   				 });
							   			 });
							   			$('#_buyCount'+tableId).html('￥'+fmoney(buyCount,2));							   			
					 	           			
							   			//采购成本合计
					 	           		var totalCount = 0;
					 	           		$(".tbodyClass").find("tr").each(function(i,ele){
							   				 $(ele).find("td").each(function(j,elem){
							   					 if(j == 7){
							   						 totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
							   					 }
							   				 });
							   			 });
							   			$('#_product_total').html('￥'+fmoney(totalCount,2));
							   			$('#productTotal').val(totalCount);
							   			
							   			if(buyCount>orderAmount){
							   				doFlag = 1;
							   				UicDialog.Error("采购成本不能大于订单金额！采购成本："+buyCount+"，订单金额："+orderAmount);
							   				return;
							   			}else{
							   				doFlag = 0;
							   			}
		     	           			}else{
		     	           				isNaNFlag = 1;
		     	           				if(isNaN(unitPrice) && isNaN(quantity)){
		     	           					$('#unitPrice'+num).css("background-color","#FF0000");
		     	           					
		     	           				}else{
			     	           				if(!isNaN(unitPrice)){
					     	           			$('#unitPrice'+num).css("background-color","#FFFFFF");
			     	           				}else{
			     	           					UicDialog.Error("单价 请输入正确格式的数字！");
			     	           					$('#unitPrice'+num).css("background-color","#FF0000");
			     	           					//isNaNFlag = 1;
			     	           				}
		     	           				}
			     	           			return;
		     	           			}
		            	     	}); 
			            		 
			            		 
			            		 //采购成本合计 
			            		 var totalCount = 0;
					   			 $(".tbodyClass").find("tr").each(function(i,ele){
					   				 $(ele).find("td").each(function(j,elem){
					   					 if(j == 7){
					   						 totalCount=metaUtil.accAdd(totalCount,$(elem).find("input").first().val());
					   					 }
					   				 });
					   			  });
					   			 $('#_product_total').html('￥'+fmoney(totalCount,2));
					   			 $('#productTotal').val(totalCount);
			            		 
			 	
			 	           	}
			 	            }
			 	          });
	        	}
			   		 
			   	//提交按钮
	        	$("#_sino_eoss_sales_contract_submit").unbind('click').click(function () {
	        		var flat=1;
	        		$("table").each(function(i,ele){
	        			var num=$(this).attr("num");
	        			var totalPrice=0;
	        			$("input[id*='totalPrice"+num+"']").each(function(j,plp){
	        				totalPrice=totalPrice+Number($(this).val());
	        			});
	        			var notAmount=Number($("#notAmount_"+num).val());
	        			if(notAmount<totalPrice){
	        				flat=0;
	        			}
	        		});
	        		if(flat==0){
	        			UicDialog.Error("未确认金额小于合计金额，请检查并修改！若未确认金额为0元，请先进行商务成本确认！");
	        			return;
	        		}
	        		if(isNaNFlag==1){
	        			UicDialog.Error("单价 请输入正确格式的数字！");
	        			return;
	        		}else if(doFlag==1){
	        			UicDialog.Error("采购成本不能大于订单金额！请检查并修改！");
	        			return;
	        		}else{
	        			//UicDialog.Error("正确，可以提交！");
	        			salesContractDetail.methods._save();	
	        		}
	        	});
	        	//取消按钮
	        	$("#_sino_eoss_sales_contract_back").unbind('click').click(function () {
	        		salesContractDetail.methods.closeWindow('detail');
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
		        
	        	_save:function(){
		       	     var datas=$("#_sino_eoss_sales_contract_addform").serialize();
		       	     var url = ctx+'/stockup/contractCost/doSave?tmp='+Math.random();
		       	     var options = {};
		       	     options.formId = "_sino_eoss_sales_contract_addform";
		       	     if($.formSubmit.doHandler(options)){
		       	    	 //启动遮盖层
		       	    	 $('#_progress1').show();
		       	    	 $('#_progress2').show();
		       	    	 //按钮不可用
		       	    	 //addSalesContract.methods.buttonDelay();
		       	    	 $.post(url,datas,
		       	 	            function(data,status){
		       	 	            	if(data=="success"){
		       	 	            		var isSubmit = $("#isSubmit").val();
			       	 	            		UicDialog.Success("关联备货成本提交成功!",function(){
			       	 	                		//addSalesContract.methods._back();
			       	 	                		salesContractDetail.methods.closeWindow('detail');
			       	 	                	});         	
		       	 	                  }else{
		       	 	            			UicDialog.Error("关联备货成本提交失败!",function(){
		       	 	                			//metaUtil.closeWindow(); 
		       	 	                			salesContractDetail.methods.closeWindow('detail');
			       	 	                	});
		       	 	            		}
		       	 	                }); 
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
		        closeWindow: function(colseType){//确认后刷新父页面
		    		//刷新父级窗口
		        	if(colseType=='proc'){
		        		window.opener.reload();
		        	}
		        	if(colseType=='detail'){
		        		window.opener.childColseRefresh();   
		        	}
		        	//关闭当前子窗口
		        	metaUtil.closeWindow();  
		        },

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
	
	
	function fmoney(s, n) { 
		if(s==null||s=="null"||s=="undefined"){
			return 0.00;
		}
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