define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap");
	require("bootstrap-datetimepicker");
	require("uic/message_dialog");
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
	
	var reimDetail = {
			config: {
				module: 'reimDetail',
            	namespace:'/business/reimbursement'
	        },
	        methods :{
	        	initDocument : function(){
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
					//點擊條目添加行按鈕，設置每行下拉菜單的樣式
	        		 $(".add").bind('click',function(){
	        			 var tr_id=($("#editGridName tbody").find("tr").length)*7-6;
	        			 $("#coursesType_"+tr_id+"_div").children().find(".uicSelectInp").css("width","105");
	        			 $("#coursesType_"+tr_id+"_div").children().find(".uicSelectData").css("width","180"); 
	        			 var tr_id=($("#editGridName tbody").find("tr").length)*7-2;
	        			 $("#invoiceType_"+tr_id+"_div").children().find(".uicSelectInp").css("width","126");
	        			 $("#invoiceType_"+tr_id+"_div").children().find(".uicSelectData").css("width","200"); 
	                 });
		        	//绑定审核确认提交到流程引擎按钮
		        	$("#_sino_eoss_reim_submit").unbind('click').click(function () {
		        		$("#_sino_eoss_reim_submit").unbind('click');
		        		var opts = {};
		    			opts.url = "/handleFlow";
		    			reimDetail.doExecute("approveToFlow",opts);
		        	});
		        	 $("#_business_order_reim").bind('click',function(){
		        			 $("#_reim_add_tabledate").show();
			             });
		        	$("#_eoss_business_reim_back").unbind('click').click(function () {
		        		var opts = {};
		    			opts.url = "/manage";
		    			reimDetail.doExecute("goBack",opts);
		        	});
		        	$("#_eoss_business_reim_print").unbind('click').click(function () {
		        		reimDetail.doExecute("getPrint");
	        		});
		        	 $("#_order_reim tbody").find("tr").each(function(i,ele){
			        		//回显科目
		        			 var coursesType=$('#coursesType'+i).text();
		        			 reimDetail.methods.payCoursesType(coursesType,i);
		        			 //回显发票类型
		 	        		var invoiceTypePay=$('#invoiceTypePay'+i).text();
		 	        		reimDetail.methods.payInvoiceType(invoiceTypePay,i);
		 	        		//绑定“查看审查日志”按钮
	        			 	var porId=$('#porId_'+i).attr("porId");
	        			 	$('#porId_'+i).popover({
	        			 		trigger :'focus',
	        			 		placement:'left',
	        			 		content:reimDetail.methods.onloadCheckedData(porId),
	        			 		html:true	
	        			 	}).click(function(e) {
	        			 		e.preventDefault();
	        			 	});
		        	 });
		        	 $("#_reim_add").bind('click',function(){
	        			 //salesInvoice.methods.buttonDelay();
		        		 reimDetail.methods._add();
	        	     });
		        	 //打印按钮
		        	 $("a[name='order_print']").bind('click',function(){
		        		 reimDetail.methods.getPrint($(this).attr('reimId'));
	                 });
		        	 $("#_order_reim tbody").find("tr").each(function(i,ele){
		        		 var status=$("#reimBursStatus"+i).text();
		        		 reimDetail.methods.getReimStatus(i,status);
		        	 });
		        },
		        getReimStatus : function(j,status){
		        	$.ajax({
		    			type : "GET",
		    			async : false,
		    			dataType : "json",
		    			url : ctx + "/sysDomain/findDomainValue?code=reimBursStatus&tmp="+ Math.random(),
		    			success : function(msg) {
		    				for(var i in msg){
		    					if(msg[i].id == status){
		    						$('#reimBursStatus'+j).text(msg[i].name);
		    					}
		    				}
		    			}
		    		});
		        },
		        onloadCheckedData:function(porId){
		    	    var url = ctx+"/business/reimbursement/getProInstLogList?procInstId="+porId+"&tmp="+Math.random();
		    	    var trStr="";
		    	    var name="";
		    	    $.ajax({url:url,async:false,success:function(data){
		    	    	var pro=data.edit;
		    	    	if(pro.length>0){
							for(var i=0;i<pro.length;i++){
								var dataTime=new Date(pro[i].time);
								trStr+='<tr>'+
								'<td class="sino_table_label">'+pro[i].user+'</td>'+
								'<td class="sino_table_label">'+pro[i].taskName+'</td>'+
								'<td class="sino_table_label">'+pro[i].apprResultDesc+'</td>'+
								'<td class="sino_table_label">'+dataTime.toString("yyyy-MM-dd")+'</td>'+
								'<td class="sino_table_label">'+pro[i].apprDesc+'</td>'+
								'</tr>';
							}
	    	    		}else{
							trStr +='<tr>'+
							'<td class="sino_table_label" colspan="5">申请中，暂无审核记录</td>'+
							'</tr>';
	    	    		}
	    	    		if(data.approName!=null){
	    	    		    name=data.approName;
	    	    		}else{
	    	    			name="无";
	    	    		}
	             		
	    	    }});
					var finalOut ='<div style="height:100px;width:730px">'+
					'<table border="0" style="width:92%" class="sino_table_body" id="_fee_travel">'+
					'<thead>'+
						'<tr>'+
							'<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">操作人</td>'+
							'<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">审批阶段</td>'+
							'<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">审批结果</td>'+
							'<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">操作时间</td>'+
							'<td class="sino_table_label" style="background:none repeat scroll 0 0 #F3F3F3;width:20%;">详情</td>'+
						'</tr>'+
					'</thead>'+
					'<tbody>'+trStr+
					'</tbody>'+
						'</div>'+
					'<span id="approName">当前审批人：'+name+'</span>';
					return finalOut;
	        	},
	        	 payCoursesType:function(coursesType,j){
		        		$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx + "/sysDomain/findDomainValue?code=coursesType&tmp="+ Math.random(),
			    			success : function(msg) {
			    				for(var i in msg){
			    					if(msg[i].id == coursesType){
			    						$('#coursesType'+j).text(msg[i].name);
			    					}
			    				}
			    			}
			    		});
		        		
		        	},
		        	payInvoiceType:function(invoiceTypePay,j){
		        		$.ajax({
			    			type : "GET",
			    			async : false,
			    			dataType : "json",
			    			url : ctx + "/sysDomain/findDomainValue?code=invoiceType&tmp="+ Math.random(),
			    			success : function(msg) {
			    				for(var i in msg){
			    					if(msg[i].id == invoiceTypePay){
			    						$('#invoiceTypePay'+j).text(msg[i].name);
			    					}
			    				}
			    			}
			    		});
		        		
		        	},
	        	_add:function(){
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
		    					UicDialog.Error("发票时间不能为空!");
		    					return;
		        			} 
		    				 if(amount==0||amount==null){
		            			 UicDialog.Error("发票金额不能为0或空！");
		            			 return;
		            		 }
		        		}
		       		}
		       	 if(amounttatol>orderDueAmnount){
        			 UicDialog.Error("付款金额超过了剩余可付款金额！");
        			 return;
        		 }
	        		var datas=$("#_sino_eoss_cuotomer_addform").serialize();
	        		var url = ctx+'/business/reimbursement/doAddReim?tmp='+Math.random();
	        		 //启动遮盖层
	       	    	 $('#_progress1').show();
	       	    	 $('#_progress2').show();
	        		$.post(url,datas,
	    	            function(data,status){
	    	            	if(data=="success"){
	    	                	  UicDialog.Success("发票计划提交成功!",function(){
	    	                		  window.location.reload();
	    	                	  });
	    	                  }else{
	    	                  	  UicDialog.Error("发票计划提交失败！",function(){
	    	                  		window.location.reload();
	    	                  	  });
	    	                  }
	    	        });
	        	},
	        	
		        closeWindow: function(){
		        	//刷新父级窗口
		        		window.opener.reload();
		        		metaUtil.closeWindow();  
		        },
		        getPrint : function(reimId){
		        	var url=ctx+'/business/reimbursement/printReim?id='+reimId+'&tmp='+Math.random();
	        		$.ajax({url:url,async:false,success:function(data){
	        			window.open(url,'_blank','status=no,scrollbars=yes,top=150,left=150,width=800,height=500');
	        		  }});
		        },
		        goBack : function(opts){
		    		window.opener.childColseRefresh();
		        	metaUtil.closeWindow();  
		        },
		        approveToFlow : function(opts){
		        	var options = {};
		    		options.murl = ctx+reimDetail.config.namespace + opts.url;
		    		var datas=$("#_sino_eoss_cuotomer_addform").serialize();
		    		//启动遮盖层
	       	    	 $('#_progress1').show();
	       	    	 $('#_progress2').show();
		    	    $.post(options.murl,datas,
		    	          function(data,status){
		    	            if(status="success"){
		    	                UicDialog.Success("审批成功!",function(){
		    	                	reimDetail.doExecute("closeWindow");
		    	                });
		    	            }else{
		    	                UicDialog.Error("审批失败！",function(){
		    	                	reimDetail.doExecute("closeWindow");
		    	                });
		    	           }
		    	    });
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =reimDetail.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		reimDetail.doExecute('initDocument');
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
});