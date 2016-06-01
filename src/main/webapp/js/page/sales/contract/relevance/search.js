define(function(require, exports, module) {
	var $ = require("jquery");
	require("bootstrap");
	require("coverage");
	require("formSelect");
	require("formUser");
	require("formTree");
	require("bootstrap-datetimepicker");
	var StringBuffer = require("stringBuffer");
	var zTree = require("zTree_core");
	var zTree_excheck = require("zTree_excheck");
	
	require("js/plugins/meta/js/metaDropdowmSelect");
	var searchManage = {
			config: {
				module: 'searchManage',
	        },
	        methods :{
	        	initDocument : function(){
	        		 $('.date').datetimepicker({
	        		    	pickTime: false
     		    });
	        		//$('#search_creator').empty();
	        		if($('#flowStep').val() != 'show1'){
		        		$('#_userName_ul').hide();
		        	}
	        		
	        		//searchManage.doExecute('getcontractType');
	        		searchManage.doExecute('customerManage');
	        		 //加载行业下拉框
	        		//searchManage.doExecute('getCustomerInfo',"");
	        		//选择客户
	        		/* $("#_sino_eoss_sales_customerInfo_select").bind('click').click(function(){
	        			 searchManage.doExecute('selectCustomerInfo');
	        		 });*/
	        		 searchManage.doExecute("getOrgTree");
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
	        			width : "224",
	        			name : "code",
	        			value : "root",
	        			tree_url:ctx+'/staff/getStaffByOrgs?random=1',
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
		        selectCustomerInfo:function(){
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
//		    							buffera.append('<a class="_customerIdt_select_a" style="" name="_customerIdt_select" id="'+result[i].id+'" href="#_select_spot">'+result[i].name+'</a>');
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
		    										 if(result[i].id != null){
		    											 $('#_div_customerContract').append('<div class="_c_div"><a class="_customerInfo_select_a" name="_customerInfo_select" style="" code="'+result[i].code+'" id="'+result[i].id+'" href="#">'+result[i].name+'</a></div>');
		    										 }
		    									 }
		    									 
		    									 $("a[name='_customerInfo_select']").unbind('click').click(function(){
		    										 $("._customerInfo_select_a").css("color","");
		    										 $(this).css("color","#0044cc");
		    										 var id = $(this).attr("id");
		    										 
		    										 searchManage.doExecute('getCustomerInfo',id);

		    										 $('#search_customerInfo').val(id);
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
		        },
	        	/*//客户选择下拉框
	        	getCustomerInfo:function(id){
	        		
	        		$("#customerInfo").empty();
	        		$("#customerInfo").metaDropdownSelect({
	   				 url:ctx+"/base/customermanage/customerInfo/getInfoModelById",
	    			 searchUrl:ctx+"/base/customermanage/customerInfo/getCustomerInfosByName",
	    			 inputShowValueId:id,
	    			 width:"190",
	   				 height:"20",
	    			 onSelect:function(id,obj){
	    				 $("#search_customerInfo").val(id);
	    			 }
        		    });
	        		var url = ctx+"/base/customermanage/customerInfo/getCustomerInfo?tmp="+Math.random();
	        		var customerInfo = $("#customerInfo");
	        		customerInfo.addClass("li_form");
	        		var optionCustomerInfo = {
	        				inputName : "customer",
	        				writeType : 'show',
	        				showLabel : false,
	        				url : url,
	        				onSelect:function(node){
	        					var id=$("#customerInfo").formSelect("getValue")[0];
	        					$("#search_customerInfo").val(id);
	        				},
	        				width : "222"
	        		};
	        		customerInfo.formSelect(optionCustomerInfo);
	        		$('.uicSelectData').height(100);
	        	},*/
	            getOrgTree:function(){
		        	$("#orgTree").empty();
		        	var orgTree = $("#orgTree");
		     		var optionsOrg = {
		     				animate : true,
		     				width:"220",
		     				searchTree : true,
		     				tree_url : ctx+'/formTree/getTreeOrg?random=1',// 顶层
		     				asyncUrl : ctx+'/formTree/getTreeOrg?random=1',// 异步
		     				search_url : ctx+'/formTree/searchTreeOrg?random=1',// 搜索
		     				find_url : ctx+'/formTree/getTreeOrg?random=1',// 精确定位
		     				url : '',
		     				asyncParam : ["id"],
		     				addparams : [{
		     							name : "id",
		     							value : "4,9,3"
		     						}],
		     				async : true,
		                    inputChange:function(){
		     					var orgId = $("#orgTree").formTree("getValue");
		     					$("#orgTreeInput").val(orgId);
		     					
		     					searchManage.doExecute('customerManage');
		     				},
		     				clearFunction:function(){
		     					var orgId = $("#orgTree").formTree("getValue");
		     					$("#orgTreeInput").val(orgId);
		     					searchManage.doExecute('customerManage');
		     				}
		     			};
		     		
		     	 	orgTree.formTree(optionsOrg);
		     	 	$(".uicSelectDataTree").css("z-index","300");
		        },
					
		        getcontractType : function(){
		        	var contractType = $("#"+searchManage.config.contractType);
					contractType.addClass("li_form");
					var optionCompPosType = {
						writeType : 'show',
						showLabel : false,
						code : searchManage.config.contractType,
						onSelect:function(node){
							$('#search_contractType').val($("#"+searchManage.config.contractType).formSelect("getValue")[0]);
						},
						width : "222"
					};
					contractType.formSelect(optionCompPosType);
					$('.uicSelectData').height(100);
		        },
					
		        goBack : function(m){
		        }
	        },
	        /**
			 * 执行方法操作
			 */
			doExecute : function(flag, param) {
				var method =searchManage.methods[flag];
				if( typeof method === 'function') {
					return method(param);
				} else {
					alert('操作 ' + flag + ' 暂未实现！');

				}
			}
	}
	exports.init = function() {
		searchManage.doExecute('initDocument');
	}
});