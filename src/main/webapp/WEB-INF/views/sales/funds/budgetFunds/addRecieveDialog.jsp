<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<html>
	<head>
		<title>合同管理</title>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>

</head>
<style type="text/css">
	.row-input{
		width: 180px;
		height:28px"
	}

.row-input-oneColumn {
    width: 480px;
}
.select2-container .select2-choice {
    border-radius: 4px;
    color: #555555;
    font-size: 14px;
    height: 20px;
    padding: 4px 6px;
    width: 180px;
    background-color: #ffffff;
    border: 1px solid #cccccc;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
    transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
    display: inline-block;
    margin-bottom: 0;
    vertical-align: middle;
    background-image: none;
}
.handprocess_order li.li_form {
    display: inline-block;
    float: left;
    font-size: 14px;
    line-height: 25px;
    padding: 3px 0;
    width: 300px;
}
body {
    font-size: 12px;
}
.table {
    margin-bottom: 20px;
    width: 100%;
    margin-left: 0px;
}

.handprocess_order {
    padding: 0px;
    border-bottom: 0px solid #d7d7d7;
}
.handprocess_order li.li_form label {
    color: #888;
    display: inline-block;
    float: left;
    text-align: right;
    width: 98px;
}
.handprocess_order .li_form label.editableLabel {
    color: #eeb422;
}

.handprocess_order .li_form label.editableLabel2 {
    color: #000000;
    margin-bottom: 0;
    margin-top: 3px;
}
.meta_on_search {
    margin-left: 100px;
}
</style>

<body>       
	<div class="portlet-body form">
	<input type="hidden" id="_Recieve_id" value="${saleId }" name="id"/>
	<input type="hidden" id="_eoss_funds_id" value="${funds.id }" name="fundsid"/>
	<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal' method="post"/>
	
	<!--预测时间最近的为7天后  -->
	<input type="hidden" id='dateTime' value="${startTime}"/>
	<div class="handprocess_order" id="SheetDiv">
		<ul class="clearfix" >
		    <li id="field_userName" class="li_form" style="width:400px">
				<label for="orderType" class="editableLabel">合同名称：</label>
				<div id="_project_div" style="width:400px;">
				</div>
			</li>
			 <li id="field_userName" class="li_form" style="width:300px">
				<label for="orderType" class="editableLabel">合同金额：</label>
				<span id='contractAmount'></span>
			</li>
		</ul>	
		<ul class="clearfix" >
		    <li id="field_userName" class="li_form" style="width:400px">
				<label for="orderType" class="editableLabel">客户名称：</label>
				<span id='customer'></span>
			</li>
			<li id="field_userName" class="li_form" style="width:300px">
				<label class="editableLabel">已回款金额：</label>
				<span id='receive'></span>
			</li>
		</ul>	
		<ul class="clearfix" >
		    <li id="field_userName" class="li_form" style="width:350px">
				<label for="orderType" class="editableLabel">预测回款金额：</label>
				<input type="text" id="recieve_amount" name='recieveAmount' value="${funds.budgetReceive}" placeholder='请输入预测回款金额' required data-content="请输入预测回款金额"/>
			</li>
			<li class="advancedSearch_li" style="width:350px;">
			<label class="editableLabel">预测回款时间：</label>
				<div class="input-append date">
				<c:if test="${funds.budgetDate!=null}">
					 <input data-format="yyyy-MM-dd"  name="recieveTime" id="add_budgetDate" type="text" value="${funds.budgetDate}" style="width:177px" ></input>
				</c:if>
				<c:if test="${funds.budgetDate==null}">
					 <input data-format="yyyy-MM-dd"  name="recieveTime" id="add_budgetDate" type="text" value="${startTime}" style="width:177px" ></input>
				</c:if>
					    <span class="add-on">
					      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
					      </i>
						</span>
				  </div> 
			</li>
		</ul>	
		</form>
    		<%-- <table id="_sales_feeincome" width="100%" cellspacing="0" border="0" id="editGridName"  class="table table-striped table-bordered table-hover">
							<thead>
							<tr>
							<td>计划收款金额</td>
							<td>计划收款日期</td>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="recieve" items="${recieve}" varStatus="status">
							<tr>
								<td class="sino_table_label" >${recieve.planedReceiveAmount }</td>
								<td class="sino_table_label" >${recieve.planedReceiveDate }</td>
							</tr>
							</c:forEach>
							</tbody>
			</table> --%>
			<div id="recieveList"></div>
    		</div>
    	</div>
 <script language="javascript">
  seajs.use('js/page/sales/funds/budgetFunds/addRecieveDialog',function(addRecieveDialog){
	  addRecieveDialog.init();
    }); 
</script>
</body>
</html>