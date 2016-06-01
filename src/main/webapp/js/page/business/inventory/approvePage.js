define(function(require, exports, module) {
	var css = require('js/plugins/ztree/zTreeStyle.css');
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("internetTable");
	require("uic/message_dialog");
	require("jquery.form");
	require("formSelect");
	require("formTree");
	require("formUser");
	require("formSubmit");
	
	var StringBuffer = require("stringBuffer");
	var approvePage = {
			config: {
				module: 'approvePage',
				uicTable : 'uicTable',
	            namespace: '/business/approvePage'
	        },
	        methods :{
	        	initDocument : function(){
	        		var outId = $("#outId").val();
	        		var approveState = Number($("#approveState").val());
	        		//alert(approveState);
	        		//var projectName = $("#projectName").val();
	        		//var projectNameStr = projectName.split(",");
	        		//alert(projectName);
	        		//$("#projectNameSpan").text(projectNameStr[1]);

	        		$.ajax({
			             url: ctx+"/business/inventory/getProductData?id="+outId,  // 提交的页面
			             //data: $('#addForm').serialize(), // 从表单中获取数据
			             type: "POST",                   // 设置请求类型为"POST"，默认为"GET"
			             dataType:"json",       
			             success: function(data) {
			            	 var dataMap = data;//.dataList;
			            	 //alert(dataMap);
			            	 var str ="";  	 
			            	 if(dataMap!=0){
			            		 if(approveState==1){
			            			 for(var i=0;i<data.length;i++){
		            					 str +="<tr id='tr_"+i+"'>"
				 	                    	+"<td>"+data[i].partner+"</td>"
				 	                    	+"<td>"+data[i].productNo+"</td>"
				 	                    	+"<td>"+data[i].quantity+"</td>"
				 	 						+"</tr>";	
				     		        }
			            		 }
			            		 
			            		 if(approveState==2){
			            			 for(var i=0;i<data.length;i++){
			            				 var quantity = data[i].quantity;
			            				 for(var j=0;j<quantity;j++){
			            					 str +="<tr id='tr_"+i+"'>"
					 	                    	+"<td><input type='hidden' name='partners' value='"+data[i].partner+"'/><input type='hidden' num='"+(""+i+j)+"' name='borrowingIds' value='"+data[i].borrowingId+"'/>"+data[i].partner+"</td>"
					 	                    	+"<td><input type='hidden' name='productNos' id='productNos' value='"+data[i].productNo+"'/>"+data[i].productNo+"</td>"
					 	                    	+"<td>"+1+"</td>"
					 	                    	+"<td><input type='hidden' name='inventoryIds' id='inventoryIds"+(""+i+j)+"' value=''/><div id='_seriesNo"+(""+i+j)+"'></div></td>"
					 	 						+"</tr>";
			            				 }		            						
				     		        }
			            		 }
			            		$("#taskTbody").append(str);
			            		
			            		if(approveState==2){
			            			 for(var i=0;i<data.length;i++){
			            				 var quantity = data[i].quantity;
			            				 for(var j=0;j<quantity;j++){
			            					 var productNo = data[i].productNo;
			            					 var borrowingId = data[i].borrowingId;
			            					 var serial_num = ""+i+j;
			            					 var valuesNum = productNo+"_"+borrowingId+"_"+serial_num;
			            					 approvePage.doExecute("getSeriesNo",valuesNum);

			            				 }		            						
				     		        }
			            		 }	
			 	           	}
			 	            }
			 	          });
	        		
	        		
		        	$("#_sino_eoss_sales_contract_submit").unbind('click').click(function () {
		        		approvePage.doExecute('approveSubmit');
		        	});
		        	$("#_sino_eoss_sales_contract_back").unbind('click').click(function () {
		        		approvePage.doExecute('_back');
		        	});
		        },
		        
		      //选择序列号
		        getSeriesNo:function(valuesNum){
		        	var valuesNumStr = valuesNum.split("_");
		        	//alert(valuesNumStr[0]+"--"+valuesNumStr[1]+"--"+valuesNumStr[2]);
 	        		var seriesNo = $("#_seriesNo"+valuesNumStr[2]);
 	        		seriesNo.addClass("li_form");
 	        		var options = {
 	        			url : ctx + "/business/inventory/getSeriesByProductNo?productNo="+valuesNumStr[0]+"&borrowingId="+valuesNumStr[1],
 	        			inputName : "seriesNo",
	 	     			writeType : 'show',
	 	     			showLabel : false,
	 	     			width : "220",
	 	     			required:true,
 	        			onSelect:function(id,obj){
 	      				//alert(id+"--"+$(obj).text());
 	        			$("#inventoryIds"+valuesNumStr[2]).val(id);
 	      			}
 	        		};
 	        		//options.writeType = 'show';
 	        		seriesNo.formSelect(options);
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
		        reload:function(){
		        	$("#"+approvePage.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+approvePage.config.uicTable).tableReload();
		        },
		        approveSubmit:function(){
		        	var url = ctx+"/business/inventory/outApproveSubmit"
		        	var datas=$("#_sino_eoss_inventory_form").serialize();
		        	$.post(url,datas,function(data,status){
		        		if(data=="success"){
		        			UicDialog.Success("审批成功！");
		        			approvePage.doExecute('_back');
		        		} else {
		        			UicDialog.Error("审批失败！");
	   	    				return;
		        		}
 	                });
		        },
		        _back:function(){
		        	var options = {};
		    		options.murl = "business/inventory/approveManage";
		    		$.openurl(options);
		        },
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = "business/inventory" + opts.url;
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
				var method =approvePage.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		approvePage.doExecute('initDocument');
	}
});