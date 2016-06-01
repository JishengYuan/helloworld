<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>

<html>
<head>
<title>员工管理</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link rel="stylesheet"
	href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css"
	type="text/css" />
<style type="text/css">
.sino_table_div table {
	border-collapse:separate;
	border-spacing:2px 2px;
	clear:both;
}
</style>

</head>

<body>
	<div class="modal-header">
    	<a type="button" class="close" data-dismiss="modal" aria-hidden="true">×</a>
        <h3 id="myModalLabel">增加返点信息</h3>
    </div>
	<div class="modal-body" style="overflow:auto;height:220px;">
    	<form id="_eoss_business_returnspot_add" novalidate="novalidate" style="" method="post">
    		<input type="hidden" id="supplierId" name="supplierId" value="${param.id }">
    		<input type="hidden" id="_returnAmount" value="${dto.returnAmount }">
    		<input type="hidden" id="returnSpotId" name="returnSpotId" value="${dto.id }">
    		<table border="0" style="width: 100%;border-collapse:separate;border-spacing:2px 2px;" class="sino_table_body">
				<thead>
					<tr>
						<td class="sino_table_label" style="text-align:left;padding-left:28px;" colspan="2">
							<label>供应商：${dto.shortName } &nbsp;</label>
							<label> &nbsp;余额：<c:if test="${dto.returnAmount == null||dto.returnAmount == ''}">0</c:if>
							<c:if test="${dto.returnAmount != null&&dto.returnAmount != ''}">${dto.returnAmount}</c:if>
							</label>
						</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="sino_table_label"><label>返点类型:</label></td>
						<td>
							<input type="radio" name="returnType" value="1" checked />收入
							<input type="radio" name="returnType" value="2" />支出
						</td>
					</tr>
					<tr>
						<td class="sino_table_label"><label>返点数:</label></td>
						<td><input type="text" style="width: 110px;"
							data-content="请填写,数字格式" id="returnAmount" name="returnAmount" required pattern="number"/><span class="STYLE1">*</span>
						</td>
					</tr>
					<tr>
						<td class="sino_table_label"><label>备注:</label></td>
						<td colspan="3"><textarea id="remark" name="remark" style="width: 230px; height: 60px"
								 data-content="请填写" required></textarea> <span
							class="STYLE1">*</span></td>
					</tr>
				</tbody>
			</table>
    	</form>
    </div>
	<div class="modal-footer">
		<a id="_eoss_business_supplier_spotAdd" role="button" data-toggle="modal" class="btn btn-primary">确定</a>
        <a class="btn" id="_eoss_business_supplier_spotCancel" data-dismiss="modal" aria-hidden="true">取消</a>
    </div >
   <script>
    </script>
</body>
</html>