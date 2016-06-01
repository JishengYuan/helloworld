define(function(require, exports, module) {

	var $ = require("jquery");
	require("formSubmit");

	require("bootstrap-datetimepicker");
	require("bootstrap");
	var DT_bootstrap = require("DT_bootstrap");

	var dataTable$ = require("dataTables");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	var Map = require("map");

	
	//------------------加载DataTable-----------Start--------------
	require("confirm_dialog");
	require("uic/message_dialog");

	
	
	function loadgrid(){

		/*
		 * 绑定触发事件
		 */
		 $("#no_back").bind('click',function(){
			 _back();
         });
		 $("#sp_Add").bind('click',function(){
				_add();
	       });
		 $("#selectOrder").bind('click',function(){
			 selectContract();
	       });

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
		window.opener.childColseRefresh();
		metaUtil.closeWindow();
		//关闭当前子窗口
	}
	function _add(){
		
		if($("#invoiceAmount").val()==""||$("#invoiceAmount").val()==0){
			UicDialog.Error("请填写报销金额！");
			return;
		}
		var credit=1;
		$("input[name='credit']").each(function(i,ele){
			if($(this).val()==""){
				credit=0;
			}
		});
		
		$("input[id*='serviceEndTime']").each(function(i,ele){
			if($(this).val()==""){
				credit=0;
			}
		});
		if(credit==0){
			UicDialog.Error("请填写发票数量或发生日期！");
			return;
		}
		var reim=1;
		$("input[name='reimbursement']").each(function(i,ele){
			if($(this).val()==""){
				reim=0;
			}
		});
		if(reim==0){
			UicDialog.Error("请填写发票金额！");
			return;
		}
		 var obj = new Object();
		 var tableGridData = "";
		 $("#pay_order").find("tr").each(function(i, trEle){
			 if(this.id!=""){
				var str = "{";
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
							str+="column5="+$(iEle).attr('value')+","
						});
					}
					if(j == 6){
						$(tdEle).find("input").each(function(m, iEle){
							str+="column6="+$(iEle).attr('infinityid')+","
						});
					}
					if(j == 7){
						$(tdEle).find("input").each(function(m, iEle){
							str+="column7="+$(iEle).attr('infinityid')+","
						});
					}
					if(j == 8){
						str+="column8="+$(tdEle).attr('tdValue')+","
					}
					if(j==9){
						str+="column9="+$(tdEle).attr('tdValue')+"}"
					}
				});
				/*str=str.substring(0, str.length-1);
				str+="}";*/
				if(i == 1){
					tableGridData+=str;
				} else {
					tableGridData+=","+str;
				}
			 }
		});
		 tableGridData+="";
		// alert(tableGridData);
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
    	 
		 
		 var salerbmdata = "[";
		 $("._salesrbm").each(function(){
			 salerbmdata+='{"value":"'+this.value+'","salesid":"'+$(this).attr("salesid")+'","orderid":"'+$(this).attr("orderid")+'"},';
		 });
		 salerbmdata+="]";
		// alert(salerbmdata);
		 $('#_salesRbmData').val(salerbmdata);
	    var datas=$("#_sino_eoss_cuotomer_addform").serialize();
	     var url =  ctx+'/business/rbmApply/save?tmp='+Math.random();
	   //启动遮盖层
	    	 $('#_progress1').show();
	    	 $('#_progress2').show();
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
		var frameSrc = ctx+"/business/rbmApply/selectOrderView";
			$('#dailogs1').on('show', function () {
			$('#dtitle').html("选择订单");
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