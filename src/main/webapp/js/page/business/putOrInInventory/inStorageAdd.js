
define(function(require, exports, module) {
	var css  = require('js/plugins/ztree/zTreeStyle.css');
	var $ = require("jquery");
	var Map = require("map");
	require("jqBootstrapValidation");
	require("bootstrap");
	var uic$ = require("uic_Dropdown");
	require("DT_bootstrap");
	require("formUser");
	require("formSelect");
	require("formSubmit");
	require("uic/message_dialog");
	var zTree = require("zTree_core");
	var zTree_excheck = require("zTree_excheck");
	require("easyui");
	require("bootstrap-datetimepicker");
	
	var StringBuffer = require("stringBuffer");
	    exports.init = function(){
	    	getTime();
	    	//editgrid();
	    	selectProject();
	   
			var $fieldStaff = $("#formStaff");
			// 选人组件
			var optionss = {
				inputName : "_projectManager",
				// labelName : "选择用户",
				showLabel : false,
				width : "282",
				name : "code",
				value : "root",
				selectUser : false,
				radioStructure : true
			    //radioStructure : field.single
			}
			optionss.resIds = $("#hideUserName").attr("value");
	 		optionss.inputValue = $("#hideStaffName").attr("value");
			optionss.addparams = [ {
				name : "code",
				value : "root"
			} ];

			$fieldStaff.formUser(optionss);

	    	/*
			 * 绑定触发事件
			 */
			 $("#ok_Save").bind('click',function(){
				 //$("#serviceArea").attr("value",$("#pmsArea").formSelect("getValue"));
				 _save();
	         });
			 $("#no_back").bind('click',function(){
				 _back2();
	         });
			 //全选行
 			 $("#chk_all").bind('click',function(){
 			 	$("input[name='chk_list']").attr("checked", this.checked);
		     });
	   }

//	    function editgrid(id){
//	    	alert(id);
//	      	var $editgrid = $("#_editTable_");   //前台jsp中的div的ID
//	      	//$editgrid="";
//	      	
//				var $row = $("<ul>").attr("class", "clearfix");
//
//				var grid = new Array();
//				var gridType=new Array();
//				var field = form.fieldList;// 列数组
//				for (var i = 0; i < field.length; i++) {
//					grid.push(field[i].name);
//					gridType.push(field[i].type);
//				}
//				gridStore.put(form.name, grid);// 表格行
//				gridTypeStore.put(form.name,gridType)//类型
//				$editgrid.append($row.data_grid(form));
//				$("#editGridName tbody").find("tr").each(function(i,ele){
//					$(ele).find("td").each(function(j,elem){
//						//if(j == 1){
//							var divId = $(elem).find("div").attr("id");
//							var inputName = $(elem).find("input").attr("name");
////							var _subjectType = $("#"+divId);
////							_subjectType.addClass("li_form");
//							var optionCompPosType = {
//									writeType : 'show',
//									showLabel : false,
//									required:true,
//									//code : "subjectType",
//									url : ctx + "/business/putOrInInventory/getDatetable?productId="+id,
//									onSelect:function(node){
//										$('#'+inputName).val($("#"+divId).formSelect("getValue")[0]);
//									},
//									width : "224"
//							};
////							_subjectType.formSelect(optionCompPosType);
////							_subjectType.formSelect('setValue',$('#'+inputName).val());
//						//}
//					});
//				});	
//	      }
				
					
		//保存或提交审批
		function _save(){ 
			$("#jhSerialNum").attr("value",$("#_serialNum_div").formSelect("getValue"));

			 var id = document.getElementsByName('chk_list');
			   var idsStr = "";		      
				   for(var i = id.length-1; i >=0; i--){
			    	   if(id[i].checked){
			    		   if(id[i].value != 'on'){
			    			   idsStr += "_"+id[i].value;	
			    		   }
			         	}	 
			        }

			 var returnUser2 = $("#formStaff").formUser("getValue");
	         $("#returnUser").val($("#formStaff").formUser("getValue"));

	    	var options = {};
        	options.murl = ctx+'/business/putOrInInventory/doSave?tmp='+Math.random()+"&ids="+idsStr;
		    options.formId = "_sino_eoss_inventory_addform2";
		    var datas=$("#_sino_eoss_inventory_addform2").serialize();
	    	if($.formSubmit.doHandler(options)){
	    		if(returnUser2.length==0){
					UicDialog.Error("请选择归还人！");
	    		}else{
	    			//启动遮盖层
		   	    	 $('#_progress1').show();
		   	    	 $('#_progress2').show();
		   	    	 
		   	    	$.post(options.murl,datas,
			            function(data,status){
			            	if(status="success"){
			                	  UicDialog.Success("入库申请成功!",function(){
			                	  _back();
			                	  });
			                  }else{
			                  	  UicDialog.Error("入库申请失败！");
			                  	  _back();
			                  }
			                });
	    			}
	    		}
		}
		
		function selectProject(){
			$("#_serialNum_div").empty();
			//项目名称
			var pmsArea = $("#_serialNum_div");
			pmsArea.addClass("li_form");
			var options = {
				inputName : "serviceArea",
				url : ctx + "/business/putOrInInventory/getAllOutOrInInventory",
				//inputValue: $("#hideServiceArea").attr("value"),
				showLabel : false,
				//inputChange : false,
				writeType : 'show',
				width : "280",
				required:true,
				onSelect:function(id,obj){
					getData(id);
					//editgrid(id);
				}
			};
			pmsArea.formSelect(options);
			//$("#pmsArea").formSelect('setValue',$("#hidePmsArea").val());
		}
		
		   /**
	     *  时间插件
	     */
	      function  getTime(object){
	     	  $('.date').datetimepicker({ 
	     	        pickTime: false
	     	      }); 
	      }
	      
	   	function _back2(){
	  		window.close();
	  	}
	  	
		function getData(id){
			  $("#_editTable tbody").empty();
		     $.ajax({
	              url: ctx+"/business/putOrInInventory/getProductData?productId="+id,  // 提交的页面
	              //data: $('#addForm').serialize(), // 从表单中获取数据
	              type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
	                      
	              success: function(data) {
	            	  if(data.length > 0){
	            		  for(var i = 0;i < data.length;i++){
	            	      var _serial_num=$("#_editTable tr").length;
	            	      
	            	      var _id = data[i].id;
	            	      var _productNo = data[i].productNo;
	            	      var _number = data[i].number;
	            	      var _cost = data[i].cost;
	            	      var _seriesNo = data[i].seriesNo;
	            	      var _partner = data[i].partner;
	            	      
	            		  var tr=$("<tr style='text-algin:center'id='tr_"+_serial_num+"'>" +
        							"<td style='text-algin:center'><input name='chk_list' type='checkbox' value='"+_id+"'/></td>" +
        							"<td><span>"+_partner+"</span><input id='_partner"+_serial_num+"' name='partners' type='hidden' value='"+_partner+"'></td>" +
        							"<td><span>"+_productNo+"</span><input id='_productNo"+_serial_num+"' name='ids' type='hidden' value='"+_id+"'></td>" +
        							"<td><span>"+_seriesNo+"</span><input id='_seriesNo"+_serial_num+"' name='seriesNos' type='hidden' value='"+_seriesNo+"'></td>" +
        							"<td><span>"+_number+"</span><input id='_number"+_serial_num+"' name='numbers' type='hidden' value='"+_number+"'></td>" +
        							"<td><span>"+_cost+"</span><input id='_cost"+_serial_num+"' name='costs' type='hidden' value='"+_cost+"'></td>" +
        							
        							"</tr>");  
	            		  $("#_editTable").append(tr);
	            		  }
	            	  }  
	              }
	          });
		}
		
		
	      
	  	function _back(){
	  		//刷新父级窗口
    		window.opener.childColseRefresh();
    		//关闭当前子窗口
    		window.close(); 
		}
					          
});		