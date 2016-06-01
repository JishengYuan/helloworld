define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("internetTable");
	require("formSelect");
	require("uic/message_dialog");
	require("formSubmit");
	require("js/plugins/meta/js/metaDropdowmSelect");
	var supplierTypeJson = {};
	var count = 1;
	var contractMergeSaveOrUpdate = {
			config: {
				module: 'contractMergeSaveOrUpdate',
				uicTable : 'uicTable',
	            namespace: '/sales/contractMerge'
	        },
	        methods :{
	        	initDocument : function(){
	        		$("#add_selectSales_btn").bind('click').click(function(){
	        			var divId = "_select_sales_";
	        			var aId = "_select_sales_a_";
//	        			var len = $("span[name='_select_sales_div']").length;
	        			if(count){
	        				divId+=count;
	        				aId+=count;
	        				count++;
	        			}
	        			
	        			var str = '<li class="advancedSearch_li"  style="height: 30px;width:888px;">';
	        			str+='<label class="editableLabel"  style="margin-top:5px;float:left;width: 140px;">请选择被合并的合同：</label>';
	        			str+='<span style="float:left;" name="_select_sales_div" id="'+divId+'"></span>';
	        			str+='<a href="javascript:void(0)" style="margin-top:5px;float:left;color:#389ae2;" id="'+aId+'">删除</a>';
	        			str+='</li>';
	        			
	        			$("#_select_sales_ul").append(str);
	        			contractMergeSaveOrUpdate.doExecute("selectSales",divId);
	        			$("#"+aId).bind('click').click(function(){
		        			$(this).parent().remove();
		        		});
	        		 });
	        		contractMergeSaveOrUpdate.doExecute("selectSales",'_select_sales_0');
	        		
	        		/*$("#_select_sales_a_0").bind('click').click(function(){
	        			$(this).parent().remove();
	        		 });*/
	        		$("#_sales_merge_back").bind('click').click(function(){
	        			var options = {};
			    		options.murl = "sales/contractMerge/manage";
			    		$.openurl(options);
	        		});
	        		
	        		$("#_sales_merge_submit").bind('click').click(function(){
	        			contractMergeSaveOrUpdate.doExecute("doSave");
	        		});
	        		
	        	},
		        selectSales:function(divId){
		        	$("#"+divId).empty();
	        		$("#"+divId).metaDropdownSelect({
//       				 url:ctx+"/base/customermanage/customerInfo/getInfoModelById",
        			 searchUrl:ctx+"/sales/contractMerge/getContractMergeList",
//        			 inputShowValueId:id,
        			 required:true,
        			 placeholder:"请输入要搜索的合同名称或编码",
        			 width:"700",
       				 height:"20",
        			 onSelect:function(id,obj){
        			 }
	        		 });
		        },
		        doSave:function(){
		        	var contractIds = "";
		        	$("span[name='_select_sales_div']").each(function(i,ele){
		        		var spanId = $(this).attr("id");
		        		var contractId = $("#"+spanId).metaDropdownSelect("getValue");
		        		contractIds+=contractId+",";
		        	});
		        	$("#_contractIds_input").val(contractIds);
		        	$("#_contractIds_input_").val($("#_select_sales_0").metaDropdownSelect("getValue"));
		        	
		        	var datas=$("#_sino_eoss_sales_merge_addform").serialize();
		       	    var url = ctx+'/sales/contractMerge/doSave?tmp='+Math.random();
		       	    $.post(url,datas, function(data,status){
		        		if(data == 'success'){
		        			UicDialog.Success("保存成功！");
		        			var options = {};
				    		options.murl = "sales/contractMerge/manage";
				    		$.openurl(options);
		        		} else {
		        			UicDialog.Error("保存失败！");
		        		}
		        	});
		        },
		        
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = "sales/contractMerge" + opts.url;
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
				var method =contractMergeSaveOrUpdate.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	
	exports.init = function() {
		contractMergeSaveOrUpdate.doExecute('initDocument');
	}
	
});