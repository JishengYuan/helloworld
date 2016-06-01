define(function(require, exports, module) {
	var $ = require("jquery");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	var contract = {
			config: {
				module: 'contract',
				uicTable : 'uicTable',
	            namespace: '/sales/fundsSalesBusiCost'
	        },
	        methods :{
	        	initDocument : function(){
	        		$(".order_show").unbind('click').click(function () {
	        			var salesInfo = "#order_"+this.id;
	        			$(salesInfo).toggle(1000);
	        		});
	        		$(".sales_show").unbind('click').click(function () {
	        			var salesInfo = "#_sales_"+this.id;
	        			$(salesInfo).toggle(1000);
	        		});
	        		
	               	$("#_eoss_business_order_back").unbind('click').click(function () {
    		    		metaUtil.closeWindow();
		        	});
	             	$(".reloadback").unbind('click').click(function () {
    		    		metaUtil.closeWindow();
		        	});
	               	
	             	$("table[id^='_sales_']").each(function(i,ele){
	             		var productAmount=0;
	             		var idval = $(ele).attr("id").substring(7);
		        		$(ele).find("tr[id]").each(function(j,trEle){
		        			$(trEle).find("td[name]").each(function(m,tdEle){
		        					productAmount+=Number($(tdEle).attr("tdvalue"));
		        			});
		        		});
		        		if(	$("#salesCost_"+idval).attr("busicost")==""){ //如果该合同已经划分商务成本，则第二次划分时，置为0
			        		$("#salesCost_"+idval).val(productAmount);
		        		}else{
			        		$("#salesCost_"+idval).val(0);
		        		}

		        		$("#totallProduct_"+idval).html("￥"+fmoney(productAmount,2));
	             	});

     		    $("#_eoss_business_cost").unbind('click').click(function () {
     		    	var postModel="["; 
     		    	var planid="";
     		    	var payAmount=$("#noAmount").val();
     		    	var sumval=0;
     		    	$(":input[type=text]").each(function(){
     		    		postModel+='{ "name":"'+ this.name+'", "value":"'+this.value+'","contractid":"'+$(this).attr("contractid")+'","planid":"'+$(this).attr("planid")+'"},'; 
     		    		sumval+=parseFloat(this.value);
     		    		//planid += $(this).attr("planid")+",";
     		       });
     		    	for(var i=0;i<$("input[name*=cost]").length;i++){
     		    		
     		    		$("input[name*=cost]").each(function(){
         		    		var num
         		    	});
     		    	}
     		    	
     		    	postModel+="]";
     		    	if(sumval!=payAmount){
     		    		alert("划分的商务成本与付款金额不一致！");
     		    		return;
     		    	}
     		    	$.ajax({
     		    		type: "post",
     		    		url: ctx+'/sales/fundsSalesBusiCost/dobusiCost',
     		    		dataType : 'json',
     		    		data : {"aid":postModel},
     		    		success: function(data,textStatus){
     		    		alert("操作成功");
    		    		metaUtil.closeWindow();
    		    		window.opener.location.reload();
     		    		},
     		    		error: function(xhr,status,errMsg){
     		    		alert("操作失败!");
     		    		}
     		    		});
	        		});
		        }
		      
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =contract.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		contract.doExecute('initDocument');
	}
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
  
});