define(function(require, exports, module) {
	var css = require('js/plugins/ztree/zTreeStyle.css');
	var $ = require("jquery");
	//require("formSelect");
	require("formSubmit");
	require("formTree");

	require("bootstrap-datetimepicker");
	require("bootstrap");
	require("jqBootstrapValidation");

	var dataTable$ = require("dataTables");
	var uic$ = require("uic_Dropdown");
	var Map = require("map");
	var DT_bootstrap = require("DT_bootstrap");
	var zTree = require("zTree_core");
	var zTree_excheck = require("zTree_excheck");
	
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
	
	
	
	function loadgrid(){
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
 		//------------------加载DataTable-------------End------------
		/*
		 * 绑定触发事件
		 */
		 $("#no_back").bind('click',function(){
			 _back();
         });
		 $("#ok_Add").bind('click',function(){
				_add();
	       });
		 $("#selectContract").bind('click',function(){
			 selectContract();
	       });
		 getOrderType();
		 getTime();
	}
    /**
     *  时间插件
     */
      function  getTime(object){
     	  $('.date').datetimepicker({ 
     	        pickTime: false
     	 
     	      }); 
      }
	function getOrderType(){
		var $fieldUserTypes = $("#_eoss_business_ordertype");
		$fieldUserTypes.addClass("li_form");
		var optionUserTypes = {
			writeType : 'show',
			showLabel : false,
			code : 'orderType',
			width : "282",
			onSelect :function(){
				var type= $("#_eoss_business_ordertype").formSelect("getValue")[0];
				$('#_eoss_business_ordertypeId').val(type);
		}
		};
		$fieldUserTypes.formSelect(optionUserTypes);
		$fieldUserTypes.formSelect('setValue',$('#_eoss_business_ordertypeId').val());
	}
	
	function _back(){
		var options = {};
		options.murl = 'business/order/manage';
		$.openurl(options);
	}
	function _add(){
		 var obj = new Object();
//		 var grid = gridStore.get(form.name);// 获取表格行列name
//		 var gridType=gridTypeStore.get(form.name);
//		 var gridStoreData =  $('#' + form.name + ' tbody tr').data_grid('getValue',grid, form.name,gridType);
//		 obj[form.name]=gridStoreData;
//		 var json_data = JSON.stringify(obj);
		 var tableGridData = "";
		 $("#product_add").find("tr").each(function(i, trEle){
			if(i != 0){
				var str = "{";
				var column0 = "";
				var column1 = "";
				var column2 = "";
				var column3 = "";
				var column4 = "";
				var tdValue = $(trEle).attr("trValue");
				str+="column6="+$(trEle).attr('tdValue')+","
			
				
				$(trEle).find("td").each(function(j, tdEle){
					//+="column6="+$(tdEle).attr('tdValue')+","
					if(j == 0){
						str+="column0="+$(tdEle).attr('tdValue')+","
					}
					if(j == 1){
						str+="column1="+$(tdEle).attr('tdValue')+","
					}
					if(j == 2){
						str+="column2="+$(tdEle).attr('tdValue')+","
					}
					if(j == 3){
						$(tdEle).find("input").each(function(m, iEle){
							str+="column3="+$(iEle).attr('value')+","
						});
					}
					if(j == 4){
						$(tdEle).find("input").each(function(m, iEle){
							str+="column4="+$(iEle).attr('value')+","
						});
					}
					if(j == 5){
						$(tdEle).find("input").each(function(m, iEle){
							str+="column5="+$(iEle).attr('value')+"}"
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
    	// alert('{"editGridName":["'+strs+'"]}');
    	 $('#_tableGridData').val('{"editGridName":["'+strs+'"]}');
    	 
	     var datas=$("#_sino_eoss_cuotomer_addform").serialize();
	     var url =  ctx+'/business/order/doAddOrder?tmp='+Math.random();
	     
	     $.post(url,datas,
	            function(data,status){
	            	if(status="success"){
	                	  UicDialog.Success("保存数据成功!",function(){
	                	  _back();
	                	  });
	                  }else{
	                  	  UicDialog.Error("保存数据失败！");
	                  	  _back();
	                  }
	                });
	}
	function selectContract(){
		var frameSrc = ctx+"/business/order/selectContractView";
			$('#dailogs1').on('show', function () {
			$('#dtitle').html("选择合同");
			$('#dialogbody').load(frameSrc,function(){
	    	  
			}); 
		       $("#dsave").unbind('click');
		       $('#dsave').click(function () {
		    	 $('#editForm').submit();
			   });
			});
		    $('#dailogs1').on('hidden', function () {$('#dailogs1').unbind("show");});
			$('#dailogs1').modal({show:true});
			$('#dailogs1').off('show');
		
	}

	exports.init = function(){
		loadgrid();  
	}
});