<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>神州新桥运营支撑管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/skin/default/room.css"	type="text/css" />
<script src="${ctx}/js/plugins/FusionCharts_XTV3.2/js/FusionCharts.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/internetTable.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/processList.css" type="text/css"></link>
</head>

<body>
	<div class="ultrapower-table-box">
    	<span class="title" style="font-size:14px; font-weight: normal;font-family:宋体;">合同报表->简单测试</span>
     </div>
     <div class="" style="border:1px solid #eee;width:auto;margin-top:10px;height:100px;">
		<div style="margin-top: 20px; margin-left: 20px;height:20px">
			<input type="radio" name="_input_raido" value="1" checked/>日报
			<input type="radio" name="_input_raido" value="2" />月报
			<input type="radio" name="_input_raido" value="3" />季报
			<input type="radio" name="_input_raido" value="4" />年报
		</div>
		<div style="margin-left:20px;margin-top:20px;height:20px">
			<div class="input-append date _day_date">
	  		    <input data-format="yyyy-MM-dd" style="width:150px;" type="text" name="serviceStartDate"></input>
			    <span class="add-on">
			      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
			      </i>
				</span>
		   </div>
		   <div class="input-append date _month_date" style="display:none;">
	  		    <input data-format="yyyy-MM" style="width:150px;" type="text" name="serviceStartDate"></input>
			    <span class="add-on">
			      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
			      </i>
				</span>
		    </div>
		   <div class="_quarter_span" style="display:none;margin-left:-1px;">
		   	<input type="radio" name="_input_raido_quarter" value="1" checked/>第一季度
			<input type="radio" name="_input_raido_quarter" value="2" />第二季度
			<input type="radio" name="_input_raido_quarter" value="3" />第三季度
			<input type="radio" name="_input_raido_quarter" value="4" />第四季度
			</div>
		    <div class="input-append date _year_date" style="display:none;">
	  		    <input data-format="yyyy" style="width:150px;" type="text" name="serviceStartDate"></input>
			    <span class="add-on">
			      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
			      </i>
				</span>
		    </div>
		</div>
		<div style="float:right;margin-top:-10px;">
              <a style="font-size:12px;" id="advancedSearch_btn" class="btn btn-primary" href="#"><i class="icon-search"></i>&nbsp;查询</a>
		</div>
	</div>
	<div class="" style="border:1px solid #eee;width:auto;margin-top:10px;">
		<div id="chartContainer" style="margin:0 auto;"></div>
	</div>
	<script>
	/* $(function() {
		var url = ctx+"/sales/statistic/simpleDataXml";
		$.ajax({
			type : "GET",
			async : false,
			dataType : "text",
			url :url,
			success : function(msg) {
			    var chart = new FusionCharts(ctx+"/js/plugins/FusionCharts_XTV3.2/swf/Column3D.swf",  Math.random(), "800", "600","0","0");
		        chart.setXMLData(msg);				   
			    chart.render("chartContainer");
			}
		});
	}); */
	seajs.use('js/page/sales/statistic/simplePage', function(simplePage) {
		simplePage.init();
	});
	</script>
</body>
</html>
