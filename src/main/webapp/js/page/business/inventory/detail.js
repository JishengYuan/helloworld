define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
	require("formSelect");
	require("uic/message_dialog");
	require("jquery.form");
	var timeObj = require("js/plugins/meta/js/timeObjectUtil");
	var StringBuffer = require("stringBuffer");
	var inventoryDetail = {
			config: {
				module: 'inventoryDetail',
				uicTable : 'uicTable',
	            namespace: '/business/inventory'
	        },
	        methods :{
	        	initDocument : function(){
		        	inventoryDetail.doExecute('initTable');
		        	$("#_sino_eoss_inventoryDetail_import").unbind('click').click(function () {
		        		inventoryDetail.doExecute('importProduct');
		        	});
		        },
		        initTable : function(){
		        	
		        	var grid=$("#"+inventoryDetail.config.uicTable).uicTable({
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		buttons : [
		        			],
	    			    columns:[
	    			        { code: 'CustomerManageName',name:'借出人',width:'15%'},
		        			{ code: 'BorrwTime',name:'借出时间',width:'15%',process:inventoryDetail.methods.dateFmt},
		        			{ code:'ReturnUserName', name:'归还人',width:'15%'},										
		        			{ code: 'ReturnDate',name:'归还时间',width:'15%',process:inventoryDetail.methods.dateFmt},
		        			{ code:'Rent',name:'租金',width:'20%',process:inventoryDetail.methods.rentFmt}
			        ],
			        url: ctx+inventoryDetail.config.namespace+'/getDetailList?id='+$("#_productId").val()+'&tmp='+Math.random(),
			        pageNo:1,
			        showcheckbox:false,
			        pageSize:13,
			        onLoadFinish:function(){
			        	/*$('a[name="_supplierName"]').unbind('click').click(function () {
							var id =this.id;
							var opts = {};
			    			opts.id = id;
			    			opts.url = "/detail";
			    			inventoryDetail.doExecute("openurl",opts);
						});*/
			        	$(".rentEdit").unbind('click').click(function () {
			        		var id =this.id;
			        		inventoryDetail.doExecute('upRent',id);
			        	});
			        },
			        searchFun:function(){
			        	var name = $("input[class='text']").first().val();
			        	$("#"+inventoryDetail.config.uicTable).tableOptions({
			        		pageNo : '1',
			        		addparams:[{
			        			name:"name",
			        			value:name
			        		}]
			        	});
			        	$("#"+inventoryDetail.config.uicTable).tableReload();
			        }
			        });
		        	$(".doAdvancedSearch").hide();
		        },
		        link:function(divInner, trid, row, count){
		        	var url = ctx + "/business/inventoryDetail/detail?id="+row.id;
		        	return '<a target="_blank" style="color:#005EA7;" href="'+url+'" >'+row.productNo+'</a>';
		        },
		        costFmt:function(divInner, trid, row, count){
		        	var cost = row.cost;
		        	if(cost == ""||cost == null){
						cost = 0;
					}
		        	return inventoryDetail.methods.fmoney(cost, 2);
		        },
		        dateFmt:function(divInner, trid, row, count){
		        	if(divInner != null&&divInner != ""){
		        		return timeObj.sFormatterDateTime(new Date(divInner));
		        	} else {
		        		return "";
		        	}
		        },
		        rentFmt:function(divInner, trid, row, count){
		        	var str = "";
		        	var rent = divInner;
		        	if(rent == ""||rent == null){
		        		rent = 0;
					}
		        	var r = inventoryDetail.methods.fmoney(rent, 2);
		        	str+=r+"<span style='margin-left:10px;'><a href='#' id='"+row.id+"' class='rentEdit'><i class='icon-edit'></i>修改</a></span>";
		        	//str+=r+"<input type='button' id='"+row.id+"' class='rentEdit icon-edit' value='修改'>";
		        	return str;
		        },
		        upRent:function(id){
		        	var frameSrc = ctx+"/business/inventory/updateRent?id="+id;
					$('#dailogs1').on('show', function () {
						$('#dtitle').html("修改租金");
						 $('#dialogbody').load(frameSrc,id); 
					       $("#dsave").unbind('click');
					       $('#dsave').click(function () {
					    	 $('#editForm').submit();
						});
					});
					$('#dailogs1').modal({show:true});
					$('#dailogs1').off('show');
				
		        },
		        fmoney:function(s,n){
		        	n = n > 0 && n <= 20 ? n : 2; 
		    		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		    		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
		    		t = ""; 
		    		for (i = 0; i < l.length; i++) { 
		    			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		    		} 
		    		return t.split("").reverse().join("") + "." + r;
		        },
		        state : function(divInner, trid, row, count){
		        	if(row.state == 0){
		        		return "在库";
		        	} else if(row.state == 1){
		        		return "待审批";
		        	} else if(divInner == 2){
		        		return "已借出";
		        	} else if(row.state == 3){
		        		return "待归还";
		        	}
		        },
		        toolbarItem:function(com, trGrid){
		        	if (com=='doAdd'){
		        		$("#_sino_eoss_inventoryDetail_import").click();
		    		}
		        },
		        reload:function(){
		        	$("#"+inventoryDetail.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+inventoryDetail.config.uicTable).tableReload();
		        },
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = "business/supplierm/inventoryDetail" + opts.url;
		    		if(opts.id){
		    			options.keyName = 'id';
		    			options.keyValue = opts.id;
		    		}
		    		$.openurl(options);
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =inventoryDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		inventoryDetail.doExecute('initDocument');
	}
});