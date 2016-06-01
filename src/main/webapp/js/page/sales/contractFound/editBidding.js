define(function(require, exports, module) {
	var $ = require("jquery");
	require("formSelect");
	require("bootstrap-datetimepicker");
	require("bootstrap");
	require("jqBootstrapValidation");
	require("select2");
	require("select2_locale_zh-CN");
	require("formSubmit");
	var StringBuffer = require("stringBuffer");
	var uic$ = require("uic_Dropdown");
	var DT_bootstrap = require("DT_bootstrap");
	var metaUtil = require("js/plugins/meta/js/metaUtil");
	require("js/plugins/meta/js/metaDropdowmSelect");
	require("easyui");
	require("jquery.form");
	//------------------加载DataTable-----------Start--------------
	var Map = require("map");
	require("uic/message_dialog");
	gridStore = new Map();//
	gridTypeStore=new Map();//表格行列类型
	gridFieldStore = new Map();
	require("json2");
	require("js/common/form/data_grid");
	//------------------加载DataTable-------------End------------
	
	var addOrderContract={
			config: {
	        },
	        methods :{
	        	initDocument:function(){
	        		addOrderContract.doExecute('editgrid1');
	        		addOrderContract.doExecute('editgrid2');
	        		addOrderContract.doExecute('editgrid3');
	        		addOrderContract.doExecute('editgrid4');
	        		var cusId=$("#cusCustomerId").val();
	        		addOrderContract.doExecute('customerDropSelect',cusId);
						/*
						 * 绑定触发事件
						 */
					//直接提交审批
					 $("#ok_Add").unbind('click').click(function () {
						 _add();
				       });
					 //选择客户
	        		 $("#_sino_eoss_sales_customerInfo_select").bind('click').click(function(){
	        			 selectCustomerInfo();
	        		 });	 
			   },
			   
			   customerDropSelect:function(id){
	        		$("#_select_customer_div").empty();
	        		$("#_select_customer_div").metaDropdownSelect({
      				 url:ctx+"/base/customermanage/customerInfo/getInfoModelById",
       			 searchUrl:ctx+"/base/customermanage/customerInfo/getCustomerInfosByName",
       			 inputShowValueId:id,
       			 required:true,
       			 placeholder:"请输入要搜索的客户名称,按回车键",
       			 width:"580",
      				 height:"20",
       			 onSelect:function(id,obj){
       				 $('#_eoss_sales_customerId').val(id);
       				 $('#cusCustomerId').val(id);
       				//addOrderContract.methods.getCustomerContactsInfo(id);
	   					//获取到客户简称放入隐藏input标签供creatContractCode()使用
	   					var customerInfoCode=$(obj).attr("datacode");
	   					$('#_eoss_sales_customerInfoCode').val(customerInfoCode);
       			 }
	        		 });
	        	},
			   
			   
			   editgrid1:function(){
					var $editgrid = $("#editTable1");   //前台jsp中的div的ID
					var $row = $("<ul>").attr("class", "clearfix");

					var grid = new Array();
					var gridType=new Array();
					var field = form_size.fieldList;// 列数组
					for (var i = 0; i < field.length; i++) {
						grid.push(field[i].name);
						gridType.push(field[i].type);
					}
					gridStore.put(form_size.name, grid);// 表格行
					gridTypeStore.put(form_size.name,gridType)//类型
					$editgrid.append($row.data_grid(form_size));
//					var divId = "";
//					var inputName = "";
//					$("#editGridName tbody").find("tr").first().find("td").each(function(i,ele) {
//						if(i == 1){
//							divId = $(ele).find("div").attr("id");
//							inputName = $(ele).find("input").attr("name");
//						}
//					});
					
					$("#editGridName_size tbody").find("tr").each(function(i,ele){
						$(ele).find("td").each(function(j,elem){
							if(j == 2){
								var divId = $(elem).find("div").attr("id");
								var inputName = $(elem).find("input").attr("name");
								var _paymentMode = $("#"+divId);
								_paymentMode.addClass("li_form");
								var optionCompPosType = {
										writeType : 'show',
										showLabel : false,
										required:true,
										code : "paymentMode",
										onSelect:function(node){
											$('#'+inputName).val($("#"+divId).formSelect("getValue")[0]);
										},
										width : "224"
								};
								_paymentMode.formSelect(optionCompPosType);
								_paymentMode.formSelect('setValue',$('#'+inputName).val());
							}
						});
					});
					$("#editGridName_size tbody").find("tr").each(function(i,ele){
						$(ele).find("td").each(function(j,elem){
							if(j == 3){
								var divId = $(elem).find("div").attr("id");
								var inputName = $(elem).find("input").attr("name");
								var _currentMoneyUses = $("#"+divId);
								_currentMoneyUses.addClass("li_form");
								var optionCompPosType = {
										writeType : 'show',
										showLabel : false,
										required:true,
										code : "currentMoneyUses",
										onSelect:function(node){
											$('#'+inputName).val($("#"+divId).formSelect("getValue")[0]);
										},
										width : "224"
								};
								_currentMoneyUses.formSelect(optionCompPosType);
								_currentMoneyUses.formSelect('setValue',$('#'+inputName).val());
							}
						});
					});
			    },
			    
				editgrid2:function(){
					var $editgrid = $("#editTable2");   //前台jsp中的div的ID
					var $row = $("<ul>").attr("class", "clearfix");

					var grid = new Array();
					var gridType=new Array();
					var field = form_chapter.fieldList;// 列数组
					for (var i = 0; i < field.length; i++) {
						grid.push(field[i].name);
						gridType.push(field[i].type);
					}
					gridStore.put(form_chapter.name, grid);// 表格行
					gridTypeStore.put(form_chapter.name,gridType)//类型
					$editgrid.append($row.data_grid(form_chapter));
					
					$("#editGridName_chapter tbody").find("tr").each(function(i,ele){
						$(ele).find("td").each(function(j,elem){
							if(j == 1){
								var divId = $(elem).find("div").attr("id");
								var inputName = $(elem).find("input").attr("name");
								var _chopType = $("#"+divId);
								_chopType.addClass("li_form");
								var optionCompPosType = {
										writeType : 'show',
										showLabel : false,
										required:true,
										url : ctx + "/sales/contractFound/findSalesType?type=1&tmp="+ Math.random(),
										onSelect:function(node){
											$('#'+inputName).val($("#"+divId).formSelect("getValue")[0]);
										},
										width : "224"
								};
								_chopType.formSelect(optionCompPosType);
								_chopType.formSelect('setValue',$('#'+inputName).val());
							}
						});
					});
			    },
			    
				
				 editgrid3:function(){
					var $editgrid = $("#editTable3");   //前台jsp中的div的ID
					var $row = $("<ul>").attr("class", "clearfix");

					var grid = new Array();
					var gridType=new Array();
					var field = form_fication.fieldList;// 列数组
					for (var i = 0; i < field.length; i++) {
						grid.push(field[i].name);
						gridType.push(field[i].type);
					}
					gridStore.put(form_fication.name, grid);// 表格行
					gridTypeStore.put(form_fication.name,gridType)//类型
					$editgrid.append($row.data_grid(form_fication));
					
					/*$("#editGridName_fication tbody").find("tr").each(function(i,ele){
						$(ele).find("td").each(function(j,elem){
							if(j == 2){
								var divId = $(elem).find("div").attr("id");
								var inputName = $(elem).find("input").attr("name");
								var _aptitudeType = $("#"+divId);
								_aptitudeType.addClass("li_form");
								var optionCompPosType = {
										writeType : 'show',
										showLabel : false,
										required:true,
										code : "aptitudeType",
										onSelect:function(node){
											$('#'+inputName).val($("#"+divId).formSelect("getValue")[0]);
										},
										width : "224"
								};
								_aptitudeType.formSelect(optionCompPosType);
								_aptitudeType.formSelect('setValue',$('#'+inputName).val());
							}
						});
					});*/
					$("#editGridName_fication tbody").find("tr").each(function(i,ele){
						$(ele).find("td").each(function(j,elem){
							if(j == 1){
								var divId = $(elem).find("div").attr("id");
								var inputName = $(elem).find("input").attr("name");
								var _aptitudeName = $("#"+divId);
								_aptitudeName.addClass("li_form");
								var optionCompPosType = {
										writeType : 'show',
										showLabel : false,
										required:true,
										url : ctx + "/sales/contractFound/findSalesType?type=2&tmp="+ Math.random(),
										onSelect:function(node){
											$('#'+inputName).val($("#"+divId).formSelect("getValue")[0]);
										},
										width : "224"
								};
								_aptitudeName.formSelect(optionCompPosType);
								_aptitudeName.formSelect('setValue',$('#'+inputName).val());
							}
						});
					});
			    },
			    
				
				
				 editgrid4:function(){
					var $editgrid = $("#editTable4");   //前台jsp中的div的ID
					var $row = $("<ul>").attr("class", "clearfix");
					var grid = new Array();
					var gridType=new Array();
					var field = form_certificate.fieldList;// 列数组
					for (var i = 0; i < field.length; i++) {
						grid.push(field[i].name);
						gridType.push(field[i].type);
					}
					gridStore.put(form_certificate.name, grid);// 表格行
					gridTypeStore.put(form_certificate.name,gridType)//类型
					$editgrid.append($row.data_grid(form_certificate));
					
					$("#editGridName_certificate tbody").find("tr").each(function(i,ele){
						$(ele).find("td").each(function(j,elem){
							if(j == 1){
								var divId = $(elem).find("div").attr("id");
								var inputName = $(elem).find("input").attr("name");
								var _certificatesType = $("#"+divId);
								_certificatesType.addClass("li_form");
								var optionCompPosType = {
										writeType : 'show',
										showLabel : false,
										required:true,
										url : ctx + "/sales/contractFound/findSalesType?type=3&tmp="+ Math.random(),
										onSelect:function(node){
											$('#'+inputName).val($("#"+divId).formSelect("getValue")[0]);
										},
										width : "224"
								};
								_certificatesType.formSelect(optionCompPosType);
								_certificatesType.formSelect('setValue',$('#'+inputName).val());
							}
						});
					});
			    }
			    
			   
			},
				/**
				 * 执行方法操作
				 */
				doExecute : function(flag, param) {
					var method =addOrderContract.methods[flag];
					if( typeof method === 'function') {
						return method(param);
					} else {
						alert('操作 ' + flag + ' 暂未实现！');
					}
				}
	}
	
	function selectCustomerInfo(){
		$("#_sino_eoss_sales_products_import_div").empty();
		var path = ctx;
		var buffer = new StringBuffer();
		buffer.append('<div style="width:800px;" id="_sino_eoss_sales_products_import_page" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="_sino_eoss_sales_products_import_page" aria-hidden="true">');
		buffer.append('<div class="modal-header">');
		buffer.append('<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button >');
		buffer.append('<h3 id="myModalLabel">请选择</h3 >');
		buffer.append('</div >');
		buffer.append('<div class="modal-body" >');
		buffer.append('<div class="modal-body-a" ><div class="mddbod"><div style="float:left;width:50px;font-size:15px;font-weight: bold;">行业:</div><div style="float:left;width:720px;margin-top:-20px;margin-left:60px;">');
		var industryUrl=ctx+'/base/customermanage/customerIndustry/getTreeList?tmp='+Math.random();
		$.ajax({
			url:industryUrl,
			async : false,
			dataType : "json", 
			success : function(result){
				for(var i = 0;i < result.length;i++){
					buffer.append('<a class="_customerIndustry_select_a" name="_customerIndustry_select" id="'+result[i].id+'" href="#">'+result[i].name+'</a>');
				}
		    }  
		});
		buffer.append('</div></div><hr size=1  style="float:left;border-style: dashed;margin:5px 0;width:760px;"></div>');
		buffer.append('<div class="modal-body-b" id="_div_customerIdt"></div>');
		buffer.append('<div class="modal-body-c" id="_div_customerContract"></div>');
		buffer.append("</div>");	
		buffer.append('<div class="modal-footer" >');
		buffer.append('<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button >');
		buffer.append('<button class="btn btn-primary"  id="btnConfirmq" data-dismiss="modal">确定</button >');
		buffer.append('</div >');
		buffer.append('</div>');
		
		$("#_sino_eoss_sales_products_import_div").append(buffer.toString());
		
		 $("a[name='_customerIndustry_select']").unbind('click').click(function(){
			 $("._customerIndustry_select_a").removeAttr("style");
			 $(this).css("color","#0044cc");
			 var id = $(this).attr("id");
			 var url = ctx+"/base/customermanage/customerIndustry/getTreeListByIndustry?id="+id+"&tmp="+Math.random();
			 $.ajax({
					url:url,
					async : false,
					dataType : "json", 
					success : function(result){
						$('#_div_customerIdt').empty();
						var buffera = new StringBuffer();
						buffera.append('<div><div style="float:left;width:80px;font-size:15px;font-weight: bold;">行业客户:</div><div style="float:left;width:690px;margin-left:20px;margin-top:0px;">');
						buffera.append('<div class="brand-attr"><div class="a-values">');
						buffera.append('<div class="v-tabs"><ul style="display: block;" class="tab hide">');
						buffera.append('<li class="curr" rel="a">A<b></b></li>');
						buffera.append('<li class="" rel="b">B<b></b></li>');
						buffera.append('<li class="" rel="c">C<b></b></li>');
						buffera.append('<li class="" rel="d">D<b></b></li>');
						buffera.append('<li class="" rel="e">E<b></b></li>');
						buffera.append('<li class="" rel="f">F<b></b></li>');
						buffera.append('<li class="" rel="g">G<b></b></li>');
						buffera.append('<li class="" rel="h">H<b></b></li>');
						buffera.append('<li class="" rel="i">I<b></b></li>');
						buffera.append('<li class="" rel="j">J<b></b></li>');
						buffera.append('<li class="" rel="k">K<b></b></li>');
						buffera.append('<li class="" rel="l">L<b></b></li>');
						buffera.append('<li class="" rel="m">M<b></b></li>');
						buffera.append('<li class="" rel="n">N<b></b></li>');
						buffera.append('<li class="" rel="o">O<b></b></li>');
						buffera.append('<li class="" rel="p">P<b></b></li>');
						buffera.append('<li class="" rel="q">Q<b></b></li>');
						buffera.append('<li class="" rel="r">R<b></b></li>');
						buffera.append('<li class="" rel="s">S<b></b></li>');
						buffera.append('<li class="" rel="t">T<b></b></li>');
						buffera.append('<li class="" rel="u">U<b></b></li>');
						buffera.append('<li class="" rel="v">V<b></b></li>');
						buffera.append('<li class="" rel="w">W<b></b></li>');
						buffera.append('<li class="" rel="x">X<b></b></li>');
						buffera.append('<li class="" rel="y">Y<b></b></li>');
						buffera.append('<li class="" rel="z">Z<b></b></li>');
						buffera.append('</ul><div class="tabcon show-logo tabcon-multi height185">');
						for(var i = 0;i < result.length;i++){
//							buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
							var py = makePy(result[i].name);
							var pya = py[0];
							switch(pya){
								case 'A':
									buffera.append('<div style="" class="" rel="a" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'B':
									buffera.append('<div style="display: none;" class="" rel="b" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'C':
									buffera.append('<div style="display: none;" class="" rel="c" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'D':
									buffera.append('<div style="display: none;" class="" rel="d" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'E':
									buffera.append('<div style="display: none;" class="" rel="e" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'F':
									buffera.append('<div style="display: none;" class="" rel="f" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'G':
									buffera.append('<div style="display: none;" class="" rel="g" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'H':
									buffera.append('<div style="display: none;" class="" rel="h" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'I':
									buffera.append('<div style="display: none;" class="" rel="i" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'J':
									buffera.append('<div style="display: none;" class="" rel="j" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'K':
									buffera.append('<div style="display: none;" class="" rel="k" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'L':
									buffera.append('<div style="display: none;" class="" rel="l" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'M':
									buffera.append('<div style="display: none;" class="" rel="m" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'N':
									buffera.append('<div style="display: none;" class="" rel="n" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'O':
									buffera.append('<div style="display: none;" class="" rel="o" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'P':
									buffera.append('<div style="display: none;" class="" rel="p" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'Q':
									buffera.append('<div style="display: none;" class="" rel="q" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'R':
									buffera.append('<div style="display: none;" class="" rel="r" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'S':
									buffera.append('<div style="display: none;" class="" rel="s" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'T':
									buffera.append('<div style="display: none;" class="" rel="t" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'U':
									buffera.append('<div style="display: none;" class="" rel="u" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'V':
									buffera.append('<div style="display: none;" class="" rel="v" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'W':
									buffera.append('<div style="display: none;" class="" rel="w" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'X':
									buffera.append('<div style="display: none;" class="" rel="x" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'Y':
									buffera.append('<div style="display: none;" class="" rel="y" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
								case 'Z':
									buffera.append('<div style="display: none;" class="" rel="z" more="false">');
									buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
									buffera.append('</div>');
									break;
							}
						}
						buffera.append('</div></div>');
						buffera.append('</div></div></div><hr size=1  style="float:left;border-style: dashed;margin:5px 0;width:760px;"></div>');
						$('#_div_customerIdt').append(buffera.toString());
						
						$('li[rel]').bind('mouseover',function(){
						    var rel = $(this).attr('rel');
						    $(this).addClass('curr');
						    $('li:not([rel='+rel+'])').removeClass('curr');
						
						    if( rel==0 ) {
						        $('div[rel]').show();
						    } else {
						        $('div[rel='+rel+']').show();
						        $('div[rel]:not([rel='+rel+'])').hide();
						
						    }
						});
						
						$("a[name='_customerIdt_select']").unbind('click').click(function(){
							$("._customerIdt_select_a").css("color","");
							$(this).css("color","#0044cc"); 
							var id = $(this).attr("id");
							 var url = ctx+"/base/customermanage/customerIdtCustomer/getTreeListByCustomerIdtCustomer?id="+id+"&tmp="+Math.random();;
							 $.ajax({
								 url:url,
								 async : false,
								 dataType : "json", 
								 success : function(result){
									 $('#_div_customerContract').empty();
									 $('#_div_customerContract').append("<h5><span href='#' name='_select_spot'>客户选择:</span><input type='text' id='_customerInfo_select_input' placeholder='输入搜索内容,按回车键' style='margin-top:8px;margin-left:15px;'></h5>");
									 for(var i in result){
										 $('#_div_customerContract').append('<div class="_c_div"><a class="_customerInfo_select_a" name="_customerInfo_select" style="" code="'+result[i].code+'" id="'+result[i].id+'" href="#">'+result[i].name+'</a></div>');
									 }
									 
									 $("a[name='_customerInfo_select']").unbind('click').click(function(){
										 $("._customerInfo_select_a").css("color","");
										 $(this).css("color","#0044cc");
										 var id = $(this).attr("id");
										 
										 addOrderContract.methods.customerDropSelect(id);

										 $('#_eoss_sales_customerId').val(id);
										 $('#cusCustomerId').val(id);
										 var customerInfoName = $(this).html();
										 $('#_customerInfoName').html(customerInfoName);
										 //addOrderContract.methods.getCustomerContactsInfo(id);
			        					 //获取到客户简称放入隐藏input标签供creatContractCode()使用
			        					 var customerInfoCode=$(this).attr("code");
			        					 $('#_eoss_sales_customerInfoCode').val(customerInfoCode);
										 //调取创建合同编码方法
			        					 //addSalesContract.methods.creatContractCode();
			        					 $("#_sino_eoss_sales_products_import_page").modal('hide');
									 });
									 
									 $('#_customerInfo_select_input').keyup(function(event){
											var myEvent = event || window.event; 
											var keyCode = myEvent.keyCode;
											var val = $(this).val();
											if(keyCode == 13||keyCode==32||keyCode == 8){
													$(this).parent().parent().find("a").each(function(i,ele){
														$(ele).parent().css("display","block");
														if(val != ""){
															var infinityName = $(ele).html();
															var pos = infinityName.indexOf(val,0);
															if(pos == -1){
																$(ele).parent().css("display","none");
															}
														}
													});
												}
										});
								 }  
							 });
						 });
						$("._customerIdt_select_a").first().click();
				    }  
				});
		 });
		$("._customerIndustry_select_a").first().click();
	}
	function getSaveTableSize(){
		 var obj = new Object();
  		 var grid = gridStore.get(form_size.name);// 获取表格行列name
  		 var gridType=gridTypeStore.get(form_size.name);
  		 var gridStoreData =  $('#' + form_size.name + ' tbody tr').data_grid('getValue',grid, form_size.name,gridType);
  		 obj[form_size.name]=gridStoreData;
  		 var json_data = JSON.stringify(obj);
  	     $("#tableDataSize").val(json_data);
	}
	function getSaveTableChapter(){
		 var obj = new Object();
 		 var grid = gridStore.get(form_chapter.name);// 获取表格行列name
 		 var gridType=gridTypeStore.get(form_chapter.name);
 		 var gridStoreData =  $('#' + form_chapter.name + ' tbody tr').data_grid('getValue',grid, form_chapter.name,gridType);
 		 obj[form_chapter.name]=gridStoreData;
 		 var json_data = JSON.stringify(obj);
 	     $("#tableDataChapter").val(json_data);
	}
	function getSaveTableFication(){
		 var obj = new Object();
 		 var grid = gridStore.get(form_fication.name);// 获取表格行列name
 		 var gridType=gridTypeStore.get(form_fication.name);
 		 var gridStoreData =  $('#' + form_fication.name + ' tbody tr').data_grid('getValue',grid, form_fication.name,gridType);
 		 obj[form_fication.name]=gridStoreData;
 		 var json_data = JSON.stringify(obj);
 	     $("#tableDataQualification").val(json_data);
	}
	function getSaveTableCertificate(){
		 var obj = new Object();
 		 var grid = gridStore.get(form_certificate.name);// 获取表格行列name
 		 var gridType=gridTypeStore.get(form_certificate.name);
 		 var gridStoreData =  $('#' + form_certificate.name + ' tbody tr').data_grid('getValue',grid, form_certificate.name,gridType);
 		 obj[form_certificate.name]=gridStoreData;
 		 var json_data = JSON.stringify(obj);
 	     $("#tableDataCertificate").val(json_data);
	}
	function _add(){
		//保存4个表格----------------
		getSaveTableSize();
		getSaveTableChapter();
		getSaveTableFication();
		getSaveTableCertificate();
		//数据验证-----------------
		/*//金额 验证
 		var b = false;
 		var borrowingPayType = false;
 		var currentMoneyUses = false;
		$("#editGridName_size .hasDatepicker").each(function(i,ele){
			var val = $(ele).val();
			$("input[name^='applyPrices']").each(function(j,elem){
				var inputValue = $(elem).val();
				if(i == j&&inputValue != null&&inputValue != ""){
					if(val == ""||val == null){
						b = true;
    				}
				}
			});
			$("input[name^='borrowingPayType']").each(function(j,elem){
				var inputValue = $(elem).val();
				if(i == j&&inputValue != null&&inputValue != ""){
					if(val == ""||val == null){
						b = true;
    				}
				}
			});
			$("input[name^='currentMoneyUses']").each(function(j,elem){
				var inputValue = $(elem).val();
				if(i == j&&inputValue != null&&inputValue != ""){
					if(val == ""||val == null){
						b = true;
    				}
				}
			});
		});
		if(b){
			UicDialog.Error("来往款金额不能为空！");
			return;
		}
		if(borrowingPayType){
			UicDialog.Error("支付方式不能为空！");
			return;
		}
		if(currentMoneyUses){
			UicDialog.Error("用途不能为空！");
			return;
		}*/
		
		
        	var options = {};
    		options.murl = ctx+"/sales/contractFound/doSave?doFlag=update";
    		var datas=$("#_sino_eoss_cuotomer_addform").serialize();
    		//启动遮盖层
   	    	 $('#_progress1').show();
   	    	 $('#_progress2').show();
    	    $.post(options.murl,datas,
    	          function(data,status){
    	            if(status=="success"){
    	            		closeWindow();
    	            		//addOrderContract.doExecute("closeWindow","proc");
    	                	UicDialog.Success("重新提交成功!",function(){
    	                });
    	            }else{
    	                UicDialog.Error("重新提交失败！",function(){
    	                	//addOrderContract.doExecute("closeWindow","proc");
    	                	closeWindow();
    	                });
    	           }
    	    });
	}
	function closeWindow(){
	    //刷新父级窗口
		window.opener.reload();
		//关闭当前子窗口
		window.close();
    }

	exports.init = function(){
		addOrderContract.doExecute('initDocument');  
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
	//强制保留2位小数，如：2，会在2后面补上00.即2.00  
	function changeTwoDecimal_f(x){
		var f_x = parseFloat(x);
		if (isNaN(f_x)){
			alert('请输入正确格式的数字！');
			return false;
		}
		f_x = Math.round(f_x*100)/100;
		var s_x = f_x.toString();
		var pos_decimal = s_x.indexOf('.');
		if (pos_decimal < 0){
			pos_decimal = s_x.length;
			s_x += '.';
		}
		while (s_x.length <= pos_decimal + 2){
			s_x += '0';
		}
		return s_x;
	} 
	
});
