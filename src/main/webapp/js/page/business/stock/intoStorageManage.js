define(function(require, exports, module) {
	var css  = require("js/plugins/ztree/zTreeStyle.css");
	var $ = require("jquery");	//声明全局jquery对象
	var Map = require("map");
	require("jqBootstrapValidation");
	require("bootstrap");
	var dataTable$ = require("dataTables");
	var uic$ = require("uic_Dropdown");
	require("DT_bootstrap");
	require("formUser");
	require("formSelect");
	require("bootstrap-datetimepicker");
	var inboundStateJson = {};
	
	exports.init = function() {
		load();
	};
	
	function load() {
		$(".date").datetimepicker({
	    	pickTime: false
	    });
		var url = ctx+"/intoStorage/inboundInfo";
		//加载数据字典项“入库状态”到下拉框
		var $fieldUserState = $("#_inboundState");	//页面元素id
		$fieldUserState.addClass("li_form");
		var optionUserState = {
			inputName : "inboundState",	//数据源名称
			writeType : "show",
			showLabel : false,
			code : "inboundState",				//数据源代码
			width : "280"
		};
		$fieldUserState.formSelect(optionUserState);
		//获得转译用到的字典状态
		$.ajax({
			type : "GET",
			async : false,
			dataType : "json",
			url : ctx + "/sysDomain/findDomainValue?code=stockState&?tmp="+ Math.random(),
			success : function(msg) {
				inboundStateJson = msg;
			}
		});
		//列表
		table=dataTable$("#taskTable").dataTable({
			"bProcessing": true,
			"bServerSide": true,
			"sAjaxSource":url, 
			"bRetrieve": true,
			"bFilter": true,
			"sServerMethod": "POST",
			"aoColumns": [
		              { "mData":"inboundCode"},
		              { "mData": "orderCode"},
		              { "mData": "contractCode"},
		              { "mData": "productCode" },
		              { "mData": "model" },
		              { "mData": "stockNum" },
		              { "mData": "stockState","mRender": function (data,row,obj) {
		            	  var str = "";
		            	  for(var i in inboundStateJson){
		            		  if(inboundStateJson[i].id == data){
		            			  str = inboundStateJson[i].name;
		            		  }
		            	  }
		            	  return str;
		              }},
		              { "mData": "inboundTime" },
		              { "mData": "recipientName" },
		              { "mData": "id","mRender": function (data,row,obj) {
		            	  var rstatus="";
		            	  var id = data;
		            	  rstatus="<a name='dealInbound' href='#' id='"+id+"'>入库</a><br>" +
		            	  		        "<a name='delInbound' href='#' id='"+id+"'>删除</a>";
		            	  return rstatus;
		              }}],
		              "sDom": "<'row'<'bt5left'l><'bt5right partnersel'>r>t<'row'<'bt5left'i><'bt5right'p>>",
		              "sPaginationType": "bootstrap",
		              "oLanguage": {
		            	  "sInfo":"从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
		            	  "sSearch":"检索:",
		            	  "sEmptyTable":"没有数据",
		            	  "sInfoEmpty": "显示0条数据",
		            	  "sLengthMenu": "页显示_MENU_ ",
		            	  "oPaginate":{
		            		  "sPrevious": "",
		            		  "sNext":''
		            	  }
		              },
		              fnDrawCallback:function(){
		            	  $(".partnersel").empty();
		            	  $("a[name='dealInbound']").unbind("click").bind("click",dealInbound);
		            	  $("a[name='delInbound']").unbind("click").bind("click",delInbound);
		            	  $("#taskTable tbody tr").each(function(){
		            		  var tdd=$(this.childNodes[0]);
		            		  var sss=$(tdd.children()[0]);
		            		  tdd.bind("mouseover",function(){
		            			  $(sss.children()[1]).css("display","block");
		            		  });
		            		  tdd.bind("mouseout",function(){
		            			  $(sss.children()[1]).css("display","none");
		            		  });
		            	  })
		              }
			});
		//产品入库操作
		function dealInbound(){
			if(confirm("确定要入库吗？")){
				$.ajax({
					url: ctx+"/intoStorage/upInbound?stockId="+$(this).attr("id"),
					data: "",
					type: "POST",
					beforeSend: function(){
						//new screenClass().lock();
	                },error: function(request){
	                	$("#alertMsg").empty();
	                	$("#alertMsg").append('<div class="alert alert-error"><strong>错误：</strong>入库失败，请稍后再试！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
	                	$(".alert").delay(2000).hide(0);
	                	$(".close").click(function(){
	                		$(".alert").hide();
	                	});
	                	 $(".edit_list").load(ctx + "/intoStorage/inbound?tmp=" + Math.random());
	                },success: function(data) {
	                	//设置表单提交完成使用方法
	                	//RefreshTable();
	                	$("#alertMsg").empty();
	                	$("#alertMsg").append('<div class="alert alert-success"><strong>提示：</strong>入库成功！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
	                	$(".alert").delay(2000).hide(0);
	                	$(".close").click(function(){
	                		$(".alert").hide();
	                	});
	                	 $(".edit_list").load(ctx + "/intoStorage/inbound?tmp=" + Math.random());
	                }
				});
			}
		}
		function delInbound(){
			if(confirm("确定要删除吗？")){
				$.ajax({
					url: ctx+"/intoStorage/delInbound?inboundId="+$(this).attr("id"),		//提交的页面
					data: "",				// 从表单中获取数据
					type: "POST",		// 设置请求类型为"POST"，默认为"GET"
					beforeSend: function(){		// 设置表单提交前方法
						//new screenClass().lock();
	                },error: function(request){	// 设置表单提交出错
	                	$("#alertMsg").empty();
	                	$("#alertMsg").append('<div class="alert alert-error"><strong>错误：</strong>删除失败，请稍后再试！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
	                	$(".alert").delay(2000).hide(0);
	                	$(".close").click(function(){
	                		$(".alert").hide();
	                	});
	                	 $(".edit_list").load(ctx + "/intoStorage/inbound?tmp=" + Math.random());
	                },success: function(data) {
	                	//设置表单提交完成使用方法
	                	//RefreshTable();
	                	$("#alertMsg").empty();
	                	$("#alertMsg").append('<div class="alert alert-success"><strong>提示：</strong>删除成功！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
	                	$(".alert").delay(2000).hide(0);
	                	$(".close").click(function(){
	                		$(".alert").hide();
	                	});
	                	 $(".edit_list").load(ctx + "/intoStorage/inbound?tmp=" + Math.random());
	                }
				});
			}
		}
		//选中查询按钮控件并绑定事件
		$("#queryInbound").unbind("click").click(function() {
			searchTable();
		});
	}
	//创建表单提交方法
	function searchTable() {
		var param = "orderCode_contractCode_timeA_timeB";
		var orderCode = $("#orderCode").val();			//订单编号
		var contractCode = $("#contractCode").val();	//合同编号
		var timeA = $("#timeA").val();		//开始时间
		var timeB = $("#timeB").val();		//结束时间
		if (orderCode != null && orderCode != "") {
			param = param.replace("orderCode", orderCode);
		}
		if (contractCode != null && contractCode != "") {
			param = param.replace("contractCode", contractCode);
		}
		if (timeA != null && timeA != "" && timeB != null && timeB != "") {
			param = param.replace("timeA", timeA);
			param = param.replace("timeB", timeB);
		}
		table.fnFilter(param, 0);
	}
});		