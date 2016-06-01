define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("bootstrap-datetimepicker");
	require("stringHelper");
	require("internetTable");
	require("confirm_dialog");
	require("uic/message_dialog");
	var StringBuffer = require("stringBuffer");
	require("changTag");
	require("formSelect");
	require("formUser");
	require("jquery.form");
	//子页面关闭刷新该父页面
	childColseRefresh=function(){
		$("#uicTable").tableOptions({
			pageNo : '1',
			addparams:[]
			});
			$("#uicTable").tableReload();
    }
	
	function formatDate(now) { 
		var year=now.getYear(); 
		var month=now.getMonth()+1; 
		var date=now.getDate(); 
		var hour=now.getHours(); 
		var minute=now.getMinutes(); 
		var second=now.getSeconds(); 
		return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
		} 

	var precontract = {
			config: {
				module: 'contract',
				uicTable : 'uicTable',
	            namespace: '/sales/precontract'
	        },
	        methods :{
	        	initDocument : function(){
	        		precontract.doExecute('initTable');
		        },
		        initTable : function(){
		        	var grid=$("#"+precontract.config.uicTable).uicTable({
		        		title : "合同管理-->合同智能检索",
		        		width : 'auto',
						height : 'auto',
						gridClass : "bbit-grid",
		        		searchItems:'name',
	    			    columns:[[{ code: 'CreatorName',name : '客户经理',width:'7%'}],
	    			             [{ code: 'ContractName',name : '合同名称',width:'22%',process:precontract.methods.links},
	    			              { code: 'ContractCode',name : '合同编号',width:'22%'}],
	    			             [{ code: 'ContractAmount',name : '合同金额',width:'18%',process:precontract.methods.contractAmount}],
	    			             [{ code: 'Name',name : '客户名称',width:'20%'},
	    			              { code: 'customerType',name : '客户类型',width:'20%'}],
	    			             [{ code: 'vendor',name : '厂商',width:'13%'},
	    			              { code: 'projectSite',name : '项目地点',width:'13%'}],
	    			             [{ code: 'proTechnology',name : '产品技术',width:'20%'},
	    			             { code: 'preContractCode',name : '内部合同编号',width:'20%'}]],
	    			    url: ctx+precontract.config.namespace+'/getList?tmp='+Math.random(),
	    			    pageNo:1,
	    			    pageSize:20,
	    			    moreCellShow : true,
	    			    showcheckbox : false,
	    			    id : 'id',
	    			    onLoadFinish:function(){
				        	var totallAmount = $("#"+precontract.config.uicTable).getJsonData().totallAmount;
				        	var pageAmount = $("#"+precontract.config.uicTable).getJsonData().pageAmount;
				        	$('#totallAmount').html("￥"+fmoney(totallAmount,2));
				        	$('#pageAmount').html("￥"+fmoney(pageAmount,2));
				        	$(".selectDIV_person").height(30);
				        },
				        });
			        	$('.searchs').find('input').first().hide();
			        	$('.searchs').find('label').first().hide();
			        	$('.doSearch').hide();
			        	$('.doAdvancedSearch').hide();
			        	//默认触发了搜索
			        	$('.doAdvancedSearch').click();
			        	//加载查询
			        	$(".advancedSearch",$("#"+precontract.config.uicTable)).load(ctx+precontract.config.namespace+'/search?tmp='+Math.random(),function(){
			        		$(".advancedSearch",$("#"+precontract.config.uicTable)).css("height","auto");
			        		precontract.methods.customerManage();
			        		
			        		//绑定“查询”按钮
			        		$("#advancedSearch_btn").unbind('click').click(function () {
			        			precontract.methods.doAdvancedFun();
				        	});
				        	//绑定“导入合同”按钮
			        		$("#_importcontract_btn").unbind('click').click(function () {
			        			precontract.methods.importContract(1);
				        	});
			        		
			        		//绑定“导入厂商”按钮
			        		$("#_importventor_btn").unbind('click').click(function () {
			        			precontract.methods.importContract(2);
				        	});
			        		//绑定“导入产品技术”按钮
			        		$("#_importpretechnology_btn").unbind('click').click(function () {
			        			precontract.methods.importContract(3);
				        	});
			        		
			        		//绑定“同步信息”按钮
			        		$("#_update_search_type").unbind('click').click(function () {
			        			$.ajax({
					    			type : "GET",
					    			url : ctx+precontract.config.namespace+"/updatePresaleType?tmp="+ Math.random(),
					    			success : function(msg) {
					    				if(msg == "success"){
								    		UicDialog.Success('同步成功!');
//								    		precontract.methods.doAdvancedFun();
								    		$("a[name='sales/precontract/manage']").click();
								    	} else {
								    		UicDialog.Error('同步失败!');
								    	}
					    			}
					    		});
			        			
			        		});
							
			        		
			        		//厂商
							var _vendor_div = $("#_vendor_div");
							_vendor_div.addClass("li_form");
							var _vendor_divType = {
								writeType : 'show',
								showLabel : false,
								url : ctx+"/sales/precontract/getTypeListByState?state=1&tmp="+Math.random(),
								onSelect:function(node){
									$('#search_vendor').val($("#_vendor_div").formSelect("getValue")[1]);
								},
								width : "282"
							};
							_vendor_div.formSelect(_vendor_divType);
							
							//产品技术
							var _proTechnology_div = $("#_proTechnology_div");
							_proTechnology_div.addClass("li_form");
							var _proTechnology_divType = {
								writeType : 'show',
								showLabel : false,
								url : ctx+"/sales/precontract/getTypeListByState?state=2&tmp="+Math.random(),
								onSelect:function(node){
									$('#search_proTechnology').val($("#_proTechnology_div").formSelect("getValue")[1]);
								},
								width : "282"
							};
							_proTechnology_div.formSelect(_proTechnology_divType);
			        		
			        		//客户类型
							var contractState = $("#_customerType_div");
							contractState.addClass("li_form");
							var optionCompPosType = {
								writeType : 'show',
								showLabel : false,
								url : ctx+"/sales/precontract/getTypeListByState?state=3&tmp="+Math.random(),
								onSelect:function(node){
									$('#search_customerType').val($("#_customerType_div").formSelect("getValue")[1]);
								},
								width : "282"
							};
							contractState.formSelect(optionCompPosType);
							$('.uicSelectData').height(200);
			        		
							$('.date').datetimepicker({
		        		    	pickTime: false
							});
			        	});
		        },
		        
		        links : function(divInner, trid, row, count) {
					var title=row.ContractName;
			      	var str = "";
		        	str+='<a style="color:#005EA7;" href="'+ctx+'/sales/contract/detail?id=' + row.id + '" target="_blank" title="'+title+'">'+title+'</a>';							
		        	return str;
				},
		        
		        importContract:function(catagory){
		        	$("#_sino_eoss_contract_import_div").empty();
		        	var path = ctx;
		    		var buffer = new StringBuffer();
		    		buffer.append('<div id="_sino_eoss_contract_import_page" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="_sino_eoss_contract_import_page" aria-hidden="true">');
		    		buffer.append('<div class="modal-header">');
		    		buffer.append('<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button >');
		    		buffer.append('<h3 id="myModalLabel">请选择Excel</h3 >');
		    		buffer.append('<div id="_alertMsg"></div>');
		    		buffer.append('</div >');
		    		buffer.append('<div class="modal-body" >');
		    		
		    		buffer.append('<form id="_sino_eoss_article_advice" method="post" enctype="multipart/form-data">');
		    		buffer.append('<table class="table table-hover">');
		    		buffer.append('<tr><td>选择Excel文件：<input type="file" id="_salary_import_input" name="attachment"></td></tr>');
		    		buffer.append('</table>');
		    		buffer.append('</form>');
		    		
		    		buffer.append("</div>");	
		    		buffer.append('<div class="modal-footer" >');
		    		buffer.append('<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button >');
		    		buffer.append('<button class="btn btn-primary"  id="btnConfirmq">确定</button >');
		    		buffer.append('</div >');
		    		buffer.append('</div>');
		    		$("#_sino_eoss_contract_import_div").append(buffer.toString());
		    		
		    		$("#btnConfirmq").unbind('click').click(function () {
		    			var url =  ctx+precontract.config.namespace+"/uploadExcel?category="+catagory;
		    			var fileName = $("#_salary_import_input").val();
			        	if(fileName == ""){
			        		$('#_alertMsg').empty();
				 			$('#_alertMsg').append('<div class="alert alert-error"><strong>错误：</strong>请选择要上传的Excel！<button type="button" class="close _cancleHandler" data-dismiss="alert">&times;</button></div>');
				 			$(".alert").delay(2000).hide(0);
				 			$("._cancleHandler").click(function() {
				 				$(".alert").hide();
				 			});
				    		return;
			        	}
		    			$("#_sino_eoss_article_advice").ajaxSubmit({
							url:url,
							dataType:'json',
						    success: function(data) {
						    	var msg = data.msg;
						    	if(msg == "success"){
						    		UicDialog.Success('上传成功!');
						    		$("a[name='sales/precontract/manage']").click();
						    	} else {
						    		UicDialog.Error('上传失败!');
						    	}
						    }
						});
			        	$("#_sino_eoss_contract_import_page").modal('hide');
		        	});
		        },
		        customerManage:function(){
		        	$("#search_creator").empty();
		        	var $fieldStaff = $("#search_creator");
		        	var orgId = "3,4,9";
		        	var orgTreeInput = $("#orgTreeInput").val();
		        	if(orgTreeInput != ""&&orgTreeInput != null){
		        		orgId = orgTreeInput;
		        	}
	        		// 选人组件
	        		var optionss = {
	        			inputName : "staffValues",
	        			// labelName : "选择用户",
	        			showLabel : false,
	        			width : "284",
	        			name : "code",
	        			value : "root",
	        			tree_url:ctx+'/staff/getAllStaffByOrgs?random=1',
	        			search_url:ctx+'/staff/searchAllStaff',
	        			selectUser : false,
	        			radioStructure : true
	        		}
	        		optionss.addparams = [ {
	        			name : "orgs",
	        			//三个销售部门ID
	        			value : orgId
	        		} ];
	        		$fieldStaff.formUser(optionss);
		        },
		        
		        doAdvancedFun:function(){
		        	var creator = $("#search_creator").formUser("getValue");
		        	var contractAmount = $('#search_contractAmount').val();
		        	var contractAmountb = $('#search_contractAmountb').val();
		        	var vendor = $('#search_vendor').val();
		        	var customerName = $('#search_customerName').val();
		        	var startTime = $('#startTime').val();
		        	var endTime = $('#endTime').val();

		        	var proTechnology = $('#search_proTechnology').val();
		        	var customerType = $('#search_customerType').val();
		        	var contractCode = $('#search_contractCode').val();
		        	var projectSite = $('#search_projectSite').val();
		        	var preContractCode = $('#search_preContractCode').val();
		        	
		        	
		        	$("#"+precontract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[{name:"creator",value:creator},
		        		           {name:"contractAmount",value:contractAmount},
		        		           {name:"contractAmountb",value:contractAmountb},
		        		           {name:"vendor",value:vendor},
		        		           {name:"customerName",value:customerName},
		        		           {name:"startTime",value:startTime},
		        		           {name:"endTime",value:endTime},
		        		           {name:"proTechnology",value:proTechnology},
		        		           {name:"customerType",value:customerType},
		        		           {name:"contractCode",value:contractCode},
		        		           {name:"projectSite",value:projectSite},
		        				   {name:"preContractCode",value:preContractCode}]
		        	});
		        	$("#"+precontract.config.uicTable).tableReload();
		        },
		        reload : function(){
		        	var sd=$("#"+precontract.config.uicTable).getTableCheckedRows();
		        	$("#"+precontract.config.uicTable).tableOptions({
		        		pageNo : '1',
		        		addparams:[]
		        	});
		        	$("#"+precontract.config.uicTable).tableReload();
		        },
		        cleanSearchInput : function(){
		        	$("#search_contractName").attr("value","");
		        	$("#search_contractCode").attr("value","");
		        	$("#search_contractAmount").attr("value","");
		        },
				greyStyle:function(divInner, trid, row, count){
					var m =  /^\d+(\.\d+)?$/;
					var amount = row.reciveStatus.split("：");
					if(m.test(amount[1])){
						return '<font class="grey03">已收款：'+"￥"+fmoney(amount[1],2)+'</font>';
					} else {
						return '<font class="grey03">'+divInner+'</font>';
					}
					
				},
				contractAmount:function(divInner, trid, row, count){
					return "￥"+fmoney(row.ContractAmount,2);
				},
		        openurl : function(opts){
		        	var options = {};
		    		options.murl = ctx+precontract.config.namespace + opts.url;
		    		if(opts.id){
		    			options.keyName = 'id';
		    			options.keyValue = opts.id;
		    		}
		    		window.open(options.murl);
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =precontract.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		precontract.doExecute('initDocument');
	}
	
	openContract = function(tp,contractId,ops){
		var url="";
		if(tp=="1"){
			url=ctx+'/sales/contract/toUpdate?id=' + contractId + '&opts='+ops;
		}else{
			url=ctx+'/sales/contract/detail?id=' +contractId + '&opts='+ops;
		}
		//window.open(url);
		var pnames = ['id','opts'];
		var args = ops.split(",");
		 $.post(ctx+'/sales/contract/detail',{ id: contractId,opts:ops },function(str_response){
			 
		     var obj = window.open("about:blank");   
		        obj.document.write(str_response);   
		 });
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