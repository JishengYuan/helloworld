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
	var paymentAdd={
			config: {
            	namespace:ctx+'/business/payment'
	        },
	        methods :{
	        	initDocument:function(){
	        		//------------------加载DataTable-----------Start--------------
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
							if(j == 1){
								var divId = $(elem).find("div").attr("id");
								var inputName = $(elem).find("input").attr("name");
								var _coursesType = $("#"+divId);
								_coursesType.addClass("li_form");
								var optioncoursesType = {
										writeType : 'show',
										showLabel : false,
										required:true,
										code : "coursesType",
										onSelect:function(node){
											$('#'+inputName).val($("#"+divId).formSelect("getValue")[0]);
										},
										width : "180"
											
								};
								_coursesType.formSelect(optioncoursesType);
								_coursesType.formSelect('setValue',$('#'+inputName).val());
							}
						});
					});
					$("#editGridName tbody").find("tr").each(function(i,ele){
						$(ele).find("td").each(function(j,elem){
							if(j == 5){
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
										width : "200"
											
								};
								_invoiceType.formSelect(optionCompPosType);
								_invoiceType.formSelect('setValue',$('#invoiceType_Id').val());
									$('#'+inputName).val($('#invoiceType_Id').val());
							}
						});
					});
					
	        		//------------------加载DataTable-------------End------------
					/*
	        		 * 绑定触发事件
	        		 */
					//點擊條目添加行按鈕，設置每行下拉菜單的樣式
	        		 $(".add").bind('click',function(){
	        			 var tr_id=($("#editGridName tbody").find("tr").length)*7-6;
	        			 $("#coursesType_"+tr_id+"_div").children().find(".uicSelectInp").css("width","105");
	        			 $("#coursesType_"+tr_id+"_div").children().find(".uicSelectData").css("width","180"); 
	        			 var tr_id=($("#editGridName tbody").find("tr").length)*7-2;
	        			 $("#invoiceType_"+tr_id+"_div").children().find(".uicSelectInp").css("width","126");
	        			 $("#invoiceType_"+tr_id+"_div").children().find(".uicSelectData").css("width","200"); 
	                 });
	        		 $("#no_back").bind('click',function(){
	        			 paymentAdd.methods._back();
	                 });
	        		 $("#sp_Add").unbind('click').click(function () {
	        			 $("#planStatus").val("2");
	        			 paymentAdd.methods._add();
	        			 
	        	     });
	        		 //getCoursesType();
	        	}, 
	        		 
	        	buttonDelay:function(){
                    $("#sp_Add").attr("class","btn");
                    $("#sp_Add").unbind("click");
	        	},  
	        	
	
	        	 _add : function(){
	        		 var obj = new Object();
	           		 var grid = gridStore.get(form.name);// 获取表格行列name
	           		 var gridType=gridTypeStore.get(form.name);
	           		 var gridStoreData =  $('#' + form.name + ' tbody tr').data_grid('getValue',grid, form.name,gridType);
	           		 obj[form.name]=gridStoreData;
	           		 var json_data = JSON.stringify(obj);
	           	     $("#tableData").val(json_data);
	           	     //与剩余可开发票金额进行比较如果大于剩余金额，则提示不让提交
	        		 var orderDueAmnount=$("#orderDueAmnount").val();
	        		 var amounttatol=0;
	           	  if(gridStoreData.length<1){
	           			UicDialog.Error("付款计划为空不能提交!");
	        			 return;
	           		}else{
	           			for(var i = 0;i < gridStoreData.length;i++){
		        			var str = gridStoreData[i];
		        			str = str.replace(/{/g,"");
		        			str = str.replace(/}/g,"");
		        			var strs = str.split(",");
		        			var subjectType = "";
		        			var purpose = "";
		        			for(var j = 0;j < strs.length;j++){
		        				var keyStrs = strs[j].split("=");
		        				var keyStr = keyStrs[0];
		        				var valueStr = keyStrs[1];
		        				if(keyStr == "coursesType"){
		        					coursesType = valueStr;
		        				}
		        				if(keyStr == "planTime"){
		        					planTime = valueStr;
		        				}
		        				if(keyStr == "amount"){
		        					amount = valueStr;
		        					amounttatol = amounttatol + Number(valueStr);
		        				}
		        			}
		        			if(coursesType == ""||coursesType == null){
		        				UicDialog.Error("科目类型不能为空!");
	    						return;
		        			} 
		        			if(planTime == ""||planTime == null){
        						UicDialog.Error("付款时间不能为空!");
	    						return;
		        			} 
	        				 if(amount==0||amount==null){
	    	        			 UicDialog.Error("付款金额不能为0或空！");
	    	        			 return;
	    	        		 }
		        		}
	           		}   
	        		 if(amounttatol>orderDueAmnount){
	        			 UicDialog.Error("付款金额超过了剩余可付款金额！");
	        			 return;
	        		 }

	           	     
	           	   /*  var applyAmount=0;
	           		 var payTime=0;
	           		 if(gridStoreData.length<1){
	           			UicDialog.Error("付款计划为空不能提交!");
	        			 return;
	           		 }
	        		 $("#editTable tbody").find("tr").each(function(i,ele){ 
	        			 var index=6+i*7;
	        			 if($("#amount_"+index).val()!=""&&$("#amount_"+index).val()!=null){
	        				 applyAmount+=parseFloat($("#amount_"+index).val());
	        			 }
	        		});
	        		 //与剩余可开发票金额进行比较如果大于剩余金额，则提示不让提交
	        		 var orderDueAmnount=$("#orderDueAmnount").val();
	        		 
	        		 if(applyAmount>orderDueAmnount){
	        			 UicDialog.Error("此次申请的付款金额超过了剩余可付款金额！");
	        			 return;
	        		 }
	        		 if(applyAmount==0){
	        			 UicDialog.Error("付款金额为0，无法提交！");
	        			 return;
	        		 }*/
	        		 paymentAdd.methods.buttonDelay();
	        		var datas=$("#_sino_eoss_cuotomer_addform").serialize();
	        		 var url = ctx+'/business/payment/doAddPayment?tmp='+Math.random();
	        		 //启动遮盖层
	       	    	 $('#_progress1').show();
	       	    	 $('#_progress2').show();
	        		$.post(url,datas,
	                    function(data,status){
	                    	if(data=="success"){
	                        	  UicDialog.Success("订单付款计划提交成功!",function(){
	                        		  paymentAdd.methods._back();
	                        	  });
	                          }else{
	                          	  UicDialog.Error("订单付款计划提交失败！",function(){
	                          		  paymentAdd.methods._back();
	                          	  });
	                          }
	                });
	        	},
	        	 _back : function(){
	        		window.opener.childColseRefresh();
	        		 metaUtil.closeWindow();  
	        	}
	        }
	}
	exports.init = function(){
		paymentAdd.methods.initDocument();   
	}
	
});