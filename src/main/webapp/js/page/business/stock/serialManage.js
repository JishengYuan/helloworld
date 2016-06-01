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
	require("formSubmit");
	require("formSelect");
	
	exports.init = function() {
		load();
	};
	
	function load() {
		//列表
		table=dataTable$("#taskTable").dataTable({
			"bProcessing": true,
			"bServerSide": true,
			"sAjaxSource":ctx+"/serial/serialInfo", 
			"bRetrieve": true,
			"bFilter": true,
			"sServerMethod": "POST",
			"aoColumns": [
			          { "mData": "orderCode"},
		              { "mData":"contractCode"},
		              { "mData": "inboundCode"},
		              { "mData": "serial" },
		              { "mData": "id","mRender": function (data,row,obj) {
		            	  var rstatus="";
		            	  var id = data;
		            	  //rstatus="<a name='editSerial' href='#' id='"+id+"'>修改</a>&nbsp;"+
		            	  rstatus="<a name='delSerial' href='#' id='"+id+"'>删除</a>";
		            	  return rstatus;
		              } }],
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
		            		  "sNext":""
		            	  }
		              },
		              fnDrawCallback:function(){
		            	  $(".partnersel").empty();
		            	  $("a[name='editSerial']").unbind("click").bind("click",editSerial);
		            	  $("a[name='delSerial']").unbind("click").bind("click",delSerial);
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
		
		function editSerial(){
			//alert("序列号是否可以进行修改操作？！");
		}
		
		function delSerial(){
			if(confirm("确定要删除吗？")){
				$.ajax({
					url: ctx+"/serial/delSerial?serialId="+$(this).attr("id"),		//提交的页面
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
	                	 $(".edit_list").load(ctx + "/serial/serialManage?tmp=" + Math.random());
	                },success: function(data) {
	                	//设置表单提交完成使用方法
	                	//RefreshTable();
	                	$("#alertMsg").empty();
	                	$("#alertMsg").append('<div class="alert alert-success"><strong>提示：</strong>删除成功！<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
	                	$(".alert").delay(2000).hide(0);
	                	$(".close").click(function(){
	                		$(".alert").hide();
	                	});
	                	 $(".edit_list").load(ctx + "/serial/serialManage?tmp=" + Math.random());
	                }
	               
				});
			}
		}
		//选中查询按钮控件并绑定事件
		$("#querySerial").unbind("click").click(function() {
			searchTable();
		});
		function searchTable() {
			var param = "orderCode_contractCode_inboundCode";
			var orderCode = $("#orderCode").val();			//订单编号
			var contractCode = $("#contractCode").val();	//合同编号
			var inboundCode = $("#inboundCode").val();	//入库单号
			
			if (orderCode != null && orderCode != "") {
				param = param.replace("orderCode", orderCode);
			}
			if (contractCode != null && contractCode != "") {
				param = param.replace("contractCode", contractCode);
			}
			if (inboundCode != null && inboundCode != "") {
				param = param.replace("inboundCode", inboundCode);
			}
			table.fnFilter(param, 0);
		}
	}
});		