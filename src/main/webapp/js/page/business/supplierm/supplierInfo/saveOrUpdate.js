define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("formSelect");
	require("formSubmit");
	require("easyui");
	var Map = require("map");
	require("uic/message_dialog");
	gridStore = new Map();//
	gridTypeStore=new Map();//表格行列类型
	gridFieldStore = new Map();
	require("json2");
	require("js/common/form/data_grid");
	
	require("uic_Romerselect");
	var supplierInfoSaveOrUpdate = {
			config: {
				module: 'supplierInfoSaveOrUpdate',
				//供应商类型
            	supplierType:'supplierType',
            	//供应商评级
            	supplierGrade:'supplierGrade',
            	//合作关系
            	relationShip:'relationShip',
            	//认证级别
            	relationGrade:'relationGrade',
            	//商务负责人
            	bizOwner:'bizOwner',
            	namespace:'/business/supplierm/supplierInfo'
	        },
	        methods :{
	        	initDocument : function(){
		        	supplierInfoSaveOrUpdate.doExecute('getFormSelect');
		        	supplierInfoSaveOrUpdate.doExecute('getRomerselect');
		        	supplierInfoSaveOrUpdate.doExecute('editgrid');
		        	
		        	//提交按钮的点击事件
		        	$("#_eoss_business_supplieradd").unbind('click').click(function () {
		        		var params = {};
		        		params.url = ctx + supplierInfoSaveOrUpdate.config.namespace+"/doSaveOrUpdate?tmp="+Math.random();
		        		supplierInfoSaveOrUpdate.doExecute('saveOrUpdate', params);
		        	});
		        	
		        	//返回按钮
		        	$("#_eoss_business_supplierback").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/manage";
		    			supplierInfoSaveOrUpdate.doExecute("goBack",opts);
		        	});
		        	
		        	//注册"保存为草稿"按钮点击事件
		        	$("#_eoss_business_supplierdraft").unbind('click').click(function(){
		        		var params = {};
		        		params.url = ctx + supplierInfoSaveOrUpdate.config.namespace
		        			+ "/saveAsDraft?tmp="+Math.random();
		        		supplierInfoSaveOrUpdate.doExecute("saveOrUpdate", params);
		        	});
		        	
		        	$("#shortName").blur(function(){
		        		var shortName = $("#shortName").val();
		        		supplierInfoSaveOrUpdate.doExecute("getShortName",shortName);
		        	});
		        	$("#supplierCode").blur(function(){
		        		var supplierCode = $("#supplierCode").val();
		        		supplierInfoSaveOrUpdate.doExecute("getSupplierCode",supplierCode);
		        	});
		        	
		        },
		        getSupplierCode : function(supplierCode){
		        	var url=ctx+"/business/supplierm/supplierInfo/checkSupplierCode?supplierCode="+supplierCode+"&tmp="+Math.random();
		        	$.post(url,function(data,status){
		        		if(status=="success"){
		        			if(data==0){
		        				UicDialog.Error("此编号已被用过，请修改编号！");
		        			}
		        		}
		        		
		        	});
		        	
		        },
		        getShortName : function(shortName){
		        	var url=ctx+"/business/supplierm/supplierInfo/checkShortName?name="+shortName+"&tmp="+Math.random();
		        	$.post(url,function(data,status){
		        		if(status=="success"){
		        			if(data==0){
		        				UicDialog.Error("此简称已被用过，请修改简称！");
		        			}
		        		}
		        		
		        	});
		        	
		        },
		        getFormSelect : function(){
		        	var supplierType = $("#"+supplierInfoSaveOrUpdate.config.supplierType);
		        	supplierType.addClass("li_form");
					var optionCompPosType = {
						writeType : 'show',
						showLabel : false,
						required:true,
						code : supplierInfoSaveOrUpdate.config.supplierType,
						onSelect:function(){
							$('#_supplierType').val($("#"+supplierInfoSaveOrUpdate.config.supplierType).formSelect("getValue")[0]);
						},
						width : "224"
					};
					supplierType.formSelect(optionCompPosType);
					supplierType.formSelect('setValue',$('#_supplierType').val());
					
					var supplierGrade = $("#"+supplierInfoSaveOrUpdate.config.supplierGrade);
					supplierGrade.addClass("li_form");
					var optionCompPosType = {
							writeType : 'show',
							showLabel : false,
							required:true,
							code : supplierInfoSaveOrUpdate.config.supplierGrade,
							onSelect:function(node){
								$('#_grade').val($("#"+supplierInfoSaveOrUpdate.config.supplierGrade).formSelect("getValue")[0]);
							},
							width : "224"
					};
					supplierGrade.formSelect(optionCompPosType);
					supplierGrade.formSelect('setValue',$('#_grade').val());
					
					var relationShip = $("#"+supplierInfoSaveOrUpdate.config.relationShip);
					relationShip.addClass("li_form");
					var optionCompPosType = {
							writeType : 'show',
							showLabel : false,
							required:true,
							code : supplierInfoSaveOrUpdate.config.relationShip,
							onSelect:function(node){
								$('#_relationShip').val($("#"+supplierInfoSaveOrUpdate.config.relationShip).formSelect("getValue")[0]);
							},
							width : "224"
					};
					relationShip.formSelect(optionCompPosType);
					relationShip.formSelect('setValue',$('#_relationShip').val());
					
					var relationGrade = $("#"+supplierInfoSaveOrUpdate.config.relationGrade);
					relationGrade.addClass("li_form");
					var optionCompPosType = {
							writeType : 'show',
							showLabel : false,
							required:true,
							code : supplierInfoSaveOrUpdate.config.relationGrade,
							onSelect:function(node){
								$('#_relationGrade').val($("#"+supplierInfoSaveOrUpdate.config.relationGrade).formSelect("getValue")[0]);
							},
							width : "224"
					};
					relationGrade.formSelect(optionCompPosType);
					relationGrade.formSelect('setValue',$('#_relationGrade').val());
					
					var bizOwner = $("#"+supplierInfoSaveOrUpdate.config.bizOwner);
					bizOwner.addClass("li_form");
					var optionCompPosType = {
							writeType : 'show',
							showLabel : false,
							required:true,
							url : ctx+"/business/supplierm/supplierInfo/getBizOwner?tmp="+Math.random(),
							onSelect:function(node){
								$('#_bizOwner').val($("#"+supplierInfoSaveOrUpdate.config.bizOwner).formSelect("getValue")[0]);
							},
							width : "224"
					};
					bizOwner.formSelect(optionCompPosType);
					bizOwner.formSelect('setValue',$('#_bizOwner').val());
		        },
		        getRomerselect:function(){
		        	$("#_country").uic_Romerselect({
						height:"auto",// 宽度
						width:"365", //高度
						countryurl:ctx+"/base/basedata/country/getUICRomerSelectJson?tmp="+Math.random(),	 //国家url数据格式请参照静态数据源,(根据国家查询城市和根据城市查询终端均使用id为条件)
						cityurl:ctx+"/base/basedata/province/getUICRomerSelectJson?tmp="+Math.random(),//城市url数据格式请参照静态数据源(数据中relationid为关联的国家id)
						modelurl:ctx+"/base/basedata/city/getUICRomerSelectJson?tmp="+Math.random(),//终端url数据格式请参照静态数据源(数据中relationid为关联的城市id)
						pagingShow:20,//查询时显示多少条数据默认10条
						searchTip:"请输入要选择的城市",
						searchText:"searchText",
						onSelect:function(node){
							$.ajax({
								url:ctx+'/base/basedata/area/getModelJson?tmp='+Math.random()+"&provinceId="+node.parent().attr('cityid'), 
								async:false,
								dataType:'json', 
								type:'post',         
								success: function(data) {
									$('#region').val(data.areaName);
								}
							});
						},
						onClickSearch:function(obj){
							var selectId = $("#_country").attr('selectid');
							var provinceId = selectId.split("-");
							$.ajax({
								url:ctx+'/base/basedata/area/getModelJson?tmp='+Math.random()+"&provinceId="+provinceId[1], 
								async:false,
								dataType:'json', 
								type:'post',         
								success: function(data) {
									$('#region').val(data.areaName);
								}
							});
						},
						searchurl:ctx+"/base/basedata/city/getSearchUICRomerSelectJson"//查询数据url(查询条件为searchText)
					})
		        },
		        
		        editgrid : function(){
		        	var $editgrid = $("#editTable");   //前台jsp中的div的ID
					var $row = $("<ul>").attr("class", "clearfix");
		
					var grid = new Array();
					var gridType=new Array();
					
					var field = form.fieldList;// 列数组
					for (var i = 0; i < field.length; i++) {
						//alert(field[i].name + "---" + field[i].value);
						grid.push(field[i].name);
						gridType.push(field[i].type);
					}
					gridStore.put(form.name, grid);// 表格行
					gridTypeStore.put(form.name,gridType)//类型
					$editgrid.append($row.data_grid(form));
		        },
		        saveOrUpdate : function(params){
		        	var url = params.url;//ctx+supplierInfoSaveOrUpdate.config.namespace+"/doSaveOrUpdate?tmp="+Math.random();
//		        	var obj = new Object();
					var grid = gridStore.get(form.name);// 获取表格行列name
					var gridType=gridTypeStore.get(form.name);
					var gridStoreData =  $('#' + form.name + ' tbody tr').data_grid('getValue',grid, form.name,gridType);
					gridStoreData+="";
					var objs = gridStoreData.split("},");

					var str = "";
					for(var i = 0;i < objs.length;i++){
						if(i != objs.length - 1){
							str += objs[i]+'}","';
						} else {
							str+=objs[i];
						}
					}
					
					//将这个object类型转成string类型   没有效果时  可能是浏览器缓存导致的
					objs += "";
					objs = objs.substring(1,objs.length-1);
					var objArr = objs.split(",");//分割后得到一个数组
					var key, value;
					for(var x=0; x<objArr.length; x++){
						var pair = objArr[x];  //contactName=ddd
						value = pair.substring(pair.indexOf("=") + 1);
						if(value == ""){
							UicDialog.Error("联系人信息不完整，请填写完整后再提交！");
							return;  
						}
					}
//					obj[form.name]=gridStoreData;
//					var datas = new Object();
//			    	var json_data = JSON.stringify(obj);
//			        datas.tableData = json_data;   //表单中的一个可编辑表格数据
		        	$('#_tableData').val('{"editGridName":["'+str+'"]}');
		        	
		        	$("#_eoss_business_supplier").form('submit',{
		    	    	url:url,
		    	    	onSubmit:function(){
		    				var options = {};
		    				options.formId = "_eoss_business_supplier";
		    				//在这里判断
		    				/*var grid = gridStore.get(form.name);// 获取表格行列name
							var gridType=gridTypeStore.get(form.name);
							var gridStoreData =  $('#' + form.name + ' tbody tr').data_grid('getValue',grid, form.name,gridType);
							gridStoreData += "";
							var objs = gridStoreData.split("},");
							alert(objs);*/
		    				//alert(objs[0].name + "=" + objs[objs[0].name]);
		    				return $.formSubmit.doHandler(options);
		    	    	},
		                success: function(data) {
		                       if(data != "fail"){
		                    	   var opts = {};
			   		    		   opts.url = "/manage";
			   		    		   supplierInfoSaveOrUpdate.doExecute("goBack",opts);
		                        }
		                    }
		    	    });
		        },
		        goBack : function(opts){
		        	var options = {};
		    		options.murl = "business/supplierm/supplierInfo" + opts.url;
		    		if(opts.id){
		    			options.keyName = 'id';
		    			options.keyValue = id;
		    		}
		    		$.openurl(options);
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =supplierInfoSaveOrUpdate.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		supplierInfoSaveOrUpdate.doExecute('initDocument');
	}
});