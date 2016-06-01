define(function(require, exports, module) {

	var $ = require("jquery");
	require("formSubmit");
	require("formSelect");
	require("bootstrap-datetimepicker");
	require("bootstrap");
	var DT_bootstrap = require("DT_bootstrap");

	var dataTable$ = require("dataTables");

	var Map = require("map");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	
	//------------------加载DataTable-----------Start--------------
	var Map = require("map");
	require("confirm_dialog");
	require("uic/message_dialog");
	//表格列数
	 var lentr=0;
	
	
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
		 $("#_sino_eoss_pay_end").unbind('click').click(function () {
			 _end();
		 })
		 //删除产品
		 $("._remove_product").unbind('click').click(function(){
   			 _removeProducts($(this).attr("num"));
		 });
		 getCoursesType();//科目类型下拉
		 getTaxRateType();//加载税率下拉
		 getTime();
		 getAmount();
		 $(".amount").blur(function(){
				var num = $(this).attr("num");
				var payAmount = $("#payAmount"+num).attr("tdValue");
				var amount = $("#amount"+num).val();
				var num = Number(payAmount-amount);
				if(num<0){
					UicDialog.Error("报销金额超过可报销金额！");
						return;
				}
				getAmount();
		});

	}
    /**
     *  时间插件
     */
      function  getTime(){
     	  $('.date').datetimepicker({ 
     	        pickTime: false
     	 
     	      }); 
      }

      function _removeProducts(serialNum){
   		UicDialog.Confirm("确认删除该条设备吗？",function () {
   			$("#tr_"+serialNum).remove();
   			getAmount();
   		});
      }
    //得到产品总价
  	function getAmount(){
  		var sum=0;
  		    var len=$("#_order_reim tr").length;
  		    if(lentr<len){
  				 lentr=len;
  			 }
  			for(var i = 1; i < lentr; i++){
  				if($("#amount"+i).val()!=null&&$("#amount"+i).val()!=""){
  					sum+=Number($("#amount"+i).val());
  				}
  			}
  			$("#tatol").text("￥"+fmoney(sum,2));
  			$("#invoiceAmount").val(sum);
  	}
  	
      function getCoursesType(){
     	 $("div[id*='coursesType']").each(function(){
              var $fieldUserTypes = $(this);
              $fieldUserTypes.addClass("li_form");
  			 var optionUserTypes = {
  				inputValue : $(this).attr("value"),
  				writeType : 'show',
  				showLabel : false,
  				code : 'coursesType',
  				width : "160",
  				onSelect :function(){
  					var type= $(this).formSelect("getValue")[0];
  			}
  			};
  			$fieldUserTypes.formSelect(optionUserTypes);
          });   
 			
 	}
      
      function getTaxRateType(){
      	 $("div[id*='taxRate']").each(function(){
               var $fieldUserTypes = $(this);
               $fieldUserTypes.addClass("li_form");
   			 var optionUserTypes = {
   				inputValue : $(this).attr("value"),
   				writeType : 'show',
   				showLabel : false,
   				code : 'taxrateType',
   				width : "120",
   				onSelect :function(){
   					var type= $(this).formSelect("getValue")[0];
   			}
   			};
   			$fieldUserTypes.formSelect(optionUserTypes);
           });   
  			
  	}
	function _back(){
		//刷新父级窗口
		window.opener.reload();
		metaUtil.closeWindow();  
		//关闭当前子窗口
	}
	function _add(){
		 var obj = new Object();
		 var tableGridData = "";
		 $("#_order_reim").find("tr").each(function(i, trEle){
			if(i != 0){
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
					if(j==8){
						str+="column8="+$(tdEle).attr('tdValue')+","
					}
					if(j==9){
						str+="column9="+$(tdEle).attr('tdValue')+","
					}
					if(j==10){
						str+="column10="+$(tdEle).attr('tdValue')
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
	     var datas=$("#_sino_eoss_cuotomer_addform").serialize();
	     var url =  ctx+'/business/rbmApply/doUpdate?tmp='+Math.random();
	   //启动遮盖层
	    	 $('#_progress1').show();
	    	 $('#_progress2').show();
	     $.post(url,datas,
	            function(data,status){
	            	if(status="success"){
	                	  UicDialog.Success("重新提交成功!",function(){
	                	  _back();
	                	  });
	                  }else{
	                  	  UicDialog.Error("重新提交失败！");
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
	 function _end(){
   	  var datas=$("#_sino_eoss_cuotomer_addform").serialize();
	      var url =  ctx+'/business/rbmApply/endreim?tmp='+Math.random();
	      //启动遮盖层
	    	 $('#_progress1').show();
	    	 $('#_progress2').show();
	     $.post(url,datas,
	            function(data,status){
	            	if(status=="success"){
	                	  UicDialog.Success("报销申请放弃提交成功!",function(){
	                		  _back(); 
	                	  });
	                  }else{
	                  	  UicDialog.Error("报销申请放弃提交失败！",function(){
	                  		  _back();
	                  	  });
	                  	  
	                  }
	                });
     }
	
	//金额格式化
	function fmoney(s, n) { 
		n = n > 0 && n <= 20 ? n : 2; 
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
		t = ""; 
		for (i = 0; i < l.length; i++) { 
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		} 
		return t.split("").reverse().join("") + "." + r; 
	} 

	exports.init = function(){
		loadgrid();  
	}
});