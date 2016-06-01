
define(function(require, exports, module) {
	var css  = require('js/plugins/ztree/zTreeStyle.css');
	var $ = require("jquery");
	var Map = require("map");
	require("jqBootstrapValidation");
	require("bootstrap");
	var dataTable$ = require("dataTables");
	var uic$ = require("uic_Dropdown");
	require("DT_bootstrap");
	require("formUser");
	require("formSelect");
	require("formSubmit");
	require("uic/message_dialog");
	require("bootstrap-datetimepicker");
	
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	require("js/plugins/meta/js/metaDropdowmSelect");
	
	
	String.prototype.replaceAll = function(s1, s2) {
		return this.replace(new RegExp(s1, "gm"), s2);
	}


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
				width : "283",
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
	    	
			
			var $fieldStaff2 = $("#formStaff2");
			// 选人组件
			var optionss = {
				inputName : "_projectManager2",
				// labelName : "选择用户",
				showLabel : false,
				width : "283",
				name : "code",
				value : "root",
				selectUser : false,
				radioStructure : true
			    //radioStructure : field.single
			}
			optionss.addparams = [ {
				name : "code",
				value : "root"
			} ];

			$fieldStaff2.formUser(optionss);
			
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
			 $(".productNum").blur(function(){
				 var num = $(this).attr("num");
				 _toBlur(num);
			 });
			/*//添加行
 			$("#addRow").bind('click',function(){
 				_addRow();
		    });
 			$("#delRow").bind('click',function(){
				_delRow();
		    });*/
 		/*	//全选行
 			$("#chk_all").bind('click',function(){
 				$("input[name='chk_list']").attr("checked", this.checked);
		    });*/
		
	   }
/*
	    function editgrid(){
	      	var $editgrid = $("#_editTable_");   //前台jsp中的div的ID
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
							var _subjectType = $("#"+divId);
							_subjectType.addClass("li_form");
							var optionCompPosType = {
									writeType : 'show',
									showLabel : false,
									required:true,
									code : "subjectType",
									url : ctx + "/business/outOrInInventory/findAllProduct",
									onSelect:function(node){
										$('#'+inputName).val($("#"+divId).formSelect("getValue")[0]);
									},
									width : "224"
							};
							_subjectType.formSelect(optionCompPosType);
							_subjectType.formSelect('setValue',$('#'+inputName).val());
						}
					});
				});	
	      }*/
	    
	   /* *//**
         * 添加行
         *//*
           function _addRow(){
        	//添加行
        	var newTr = editTable.insertRow();
        	//添加列
        	var newTd0 = newTr.insertCell();
        	var newTd1 = newTr.insertCell();
        	var newTd2 = newTr.insertCell();
        	var newTd3 = newTr.insertCell();

        	var id = document.getElementsByName('chk_list');

        	//设置列内容和属性
        	newTd0.style.width = "30"; 
        	newTd0.innerHTML = "<input name='chk_list' type='checkbox' />"; 
        	newTd1.innerHTML = "<div id='_sino_product_model"+id.length+"'></div><input type='hidden' name='ids' id='_id"+id.length+"'/>";
        	newTd2.innerHTML = "<input name='_numbers' id='_number"+id.length+"' readonly='readonly'/>";
        	newTd3.innerHTML = "<input name='_costs' id='_cost"+id.length+"' readonly='readonly'/>";
        	
        	getProductModel('',"",id.length);
	
        }
        *//**
         * 删除行
         *//*
		   function _delRow(){
		   var id = document.getElementsByName('chk_list');
		   var idsStr = "";
		   //alert(id.length+"==id.length");
		   //var datas=$("#_sino_eoss_members_editForm").serialize();
	      
		   UicDialog.Confirm("确定要删除吗？",function () {
			   for(var i = id.length-1; i >=0; i--){
		    	   if(id[i].checked){
		    		   if(id[i].value != 'on'){
		    			   idsStr += "_"+id[i].value;	
		    		   }
		    		   editTable.deleteRow(i+2);  
		         	}	 
		        }
			   var url = ctx+'/projectMembers/delRowData?tmp='+Math.random()+"&ids="+idsStr;
			   $.post(url,datas,
			            function(data,status){
			            	if(status="success"){
//			                	  UicDialog.Success("保存数据成功!",function(){
//			                	  project.methods._back();
//			                	  });
			                  }
			           });
		   });
		}*/
		   
		/*function getProductModel(id,str,count){
			if(str == null){
				str = "";
			}
			//型号
			$("#_sino_product_model"+count).empty();
			$("#_sino_product_model"+count).metaDropdownSelect({
			 searchUrl:ctx+"/business/outOrInInventory/getProductInfosByName",
			 url:ctx+"/business/outOrInInventory/getProductoModelById",
			 inputShowValueId:id,
			 required:true,
			 placeholder:"请输入要搜索的型号,按回车键",
			 width:"210",
			 height:"20",
			 onSelect:function(id,obj){
					//var customerInfoCode=$(obj).attr("datacode");
//					$('#_productModelId').val(id);
//					$('#_sino_productModelName').val($(obj).text());
					
					var url = ctx+"/business/outOrInInventory/getParentModelById?id="+id;
					$.ajax({
						url:url,
						async : false,
						dataType : "json", 
						success : function(result){
							$("#_number"+count).val("1");
							$("#_cost"+count).val(result.cost);
							$("#_id"+count).val(result.id);
							
							
//							$("#_sino_product_type").empty();
//							$("#_sino_product_type").formTree({
//								inputName : '_sino_product_type',
//								inputValue : $('#_sino_productTypeName').val(),
//								Checkbox : false,
//								animate : true,
//								searchTree : true,
//								required : true,
//								asyncUrl:ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),
//								tree_url : ctx+"/business/projectm/productType/getTree?tmp="+Math.random(),// 顶层
//								search_url : ctx+"/business/projectm/productType/getTree",// 搜索
//								url : '',
//								asyncParam : ["id"],
//								onSelect:function(node){
//									$('#_partnerId').val('');
////										$('#_brandCode').val('');
////										$('#_productLine').val('');
//									getPartnerInfo(node.id);
//									getProductModel('',"");
//									$('#_productType').val(node.id);
//									$('#_sino_productTypeName').val(node.text);
//								},
//								async : true
//							});
//							getPartnerInfo(result.productType);
							
					    }  
					});
			 }
			 });
		}*/
	    
	    
		function _toBlur(num){
			var nums = Number($("#num_"+num).val());
			var oldnum = Number($("#num_"+num).attr("tdValue"));
			if(nums>oldnum){
				UicDialog.Error("超过库存量！");
				$("#num_"+num).val(oldnum);
			}
		}
			
		//保存或提交审批
		function _save(){ 
	         $("#customerManage").val($("#formStaff").formUser("getValue"));
	         $("#engineers").val($("#formStaff2").formUser("getValue"));
	         var customerManage= $("#formStaff").formUser("getValue");
	         var engineers = $("#formStaff2").formUser("getValue");
			
	         var tableGridData = "";
	         $("#editTableName").find("tr").each(function(i, trEle){
	        	 if(i!=0){
	        		var str = "{";
					var column0 = "";
					var column1 = "";
					var column2 = "";
					
					$(trEle).find("td").each(function(j, tdEle){
						if(j == 0){
							str+="column0="+$(tdEle).attr('tdValue')+","
						}
						if(j == 1){
							str+="column1="+$(tdEle).attr('tdValue')+","
						}
						if(j == 2){
							$(tdEle).find("input").each(function(m, iEle){
								str+="column2="+$(iEle).attr('value')+"}"
							});
						}
					});
					if(i == 1){
						tableGridData+=str;
					} else {
						tableGridData+=","+str;
					}
	        	 }
	         });
	         tableGridData+="";
			var objs = tableGridData.split("},");
			var strs = "";
			for(var i = 0;i < objs.length;i++){
				if(i != objs.length - 1){
					strs+=objs[i]+'}","';
				} else {
					strs+=objs[i];
				}
			}
	    	 $('#_tableGridData').val('{"editGridName":["'+strs+'"]}');
	    	var options = {};
        	options.murl = ctx+'/business/outOrInInventory/doSave?tmp='+Math.random();
		    options.formId = "_sino_eoss_inventory_addform";
		    var datas=$("#_sino_eoss_inventory_addform").serialize();
	    	if($.formSubmit.doHandler(options)){
	    		if(customerManage.length==0){
					UicDialog.Error("请选择项目经理！");
	    		}else if(engineers.length==0){
	    			UicDialog.Error("请选择驻点工程师！");
	    		}else{
	    			//启动遮盖层
		   	    	 $('#_progress1').show();
		   	    	 $('#_progress2').show();
		   	    	 
		   	    	$.post(options.murl,datas,
			            function(data,status){
			            	if(status="success"){
			                	  UicDialog.Success("出库申请成功!",function(){
			                	  _back();
			                	  });
			                  }else{
			                  	  UicDialog.Error("出库申请失败！");
			                  	  _back();
			                  }
			                });
	    		}
	    	}
		}
		
		function selectProject(){
			//项目名称
			var pmsArea = $("#_project_div");
			pmsArea.addClass("li_form");
			var options = {
				//inputName : "serviceArea",
				writeType : 'show',
				showLabel : false,
				url : ctx + "/business/outOrInInventory/findProjectAll",
				//inputValue: $("#hideServiceArea").attr("value"),
				//inputChange : false,
				width : "684",
				//height:"100",
				required:true,
				onSelect:function(id,obj){
					$("#projectId").val(id);
					//$("#projectName").val(obj);
					 $("#projectName").attr("value",$("#_project_div").formSelect("getValue")[1]);
					//alert(obj);
				}
			};
			pmsArea.formSelect(options);
			$('.uicSelectData').height(220);
			//$("#pmsArea").formSelect('setValue',$("#hidePmsArea").val());
			//pmsArea.formSelect('setValue',$('#projectName').val());
		}
		
		   /**
	     *  时间插件
	     */
	      function  getTime(object){
	     	  $('.date').datetimepicker({ 
	     	        pickTime: false
	     	      }); 
	      }
	      
	  	function _back(){
	  		//刷新父级窗口
    		//window.opener.childColseRefresh();
    		//关闭当前子窗口
    		window.close(); 
		}
	  	
	  	function _back2(){
	  		window.close();
	  	}

					          
});		