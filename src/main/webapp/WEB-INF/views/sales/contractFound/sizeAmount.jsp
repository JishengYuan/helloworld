<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<html>
	<head>
		<title>投标信息</title>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/user-selection.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSearchTip.css" type="text/css" ></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
		<link rel="stylesheet" href="${ctx}/js/plugins/bootstrap/css/bootstrap-datetimepicker.min.css" type="text/css"></link>

</head>
 <style type="text/css">
		._sino_td_one{
			float:left;
			margin-right:3px;
			width:100px;
			height: 30px;
		}
		.one{
			margin-left:30px;
		}
		._sino_td_two{
			float:left;
			height: 30px;
		}
		._sino_table tr {
		    height: 50px;
		}
		.handprocess_order li.li_form {
		    display: inline-block;
		    float: left;
		    font-size: 12px;
		    line-height: 19px;
		    padding: 0px 0;
		    width: 300px;
		}
		.table th, .table td {
		    border-top: 1px solid #dddddd;
		    line-height: 12px;
		    padding: 7px;
		    text-align: left;
		    vertical-align: top;
		}
		

	</style>
<body>    
	 <form name="_sino_eoss_products_addform" id ="_sino_eoss_products_addform" class='form-horizontal'>
	 	<input type="hidden" name="id" id="size_id" value="${model.id}"/>
	 	<input type="hidden" name="num" id="num" value="${num}"/>
	 		<table id='table_size' width="400px;" cellspacing="0" border="0" class="">
					<tr>
						<td>金额</td>
						<td ><input type="text" id="size_amount" name="amount" value="${model.applyPrices}"/></td>
					</tr>
			</table>
	 </form>
	
			
 <script language="javascript">
    seajs.use('js/page/sales/contractFound/sizeAmount',function(sizeAmount){
    	sizeAmount.init();
    });
</script>
</body>
</html>