<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<br />

	    	<form name="editForm"  id ="editForm" class='form-horizontal' action="">

	    	<div class='control-group'>
			<label class='control-label' for='menuName'>合同名称：</label><span style="float: left;margin-top: 10px;">${model.contractName}</span>
			</div>
    		<div class='control-group'>
	    	<label class='control-label' for='menuName'> 合同金额：</label>
	    	<input type='text' name='contractAmount' id='contractAmount' value='${model.contractAmount}' />
			</div>	    	
			<div class='control-group'>
	    	<label class='control-label' for='menuName'> 商务成本：</label>
	    	<input type='text' name='businessCost' id='businessCost' value='${model.businessCost}' />
			</div>
			
	    	<div class='control-group'>
	    	<label class='control-label' for='menuName'>进向发票：</label>
	    	<input type='text' name='incomeInvoice' value='${model.incomeInvoice}'  />
			</div>
			
			<div class='control-group'>
	    	<label class='control-label' for='menuName'>销向发票：</label>
	    	<input type='text' name='outInvoice' value='${model.outInvoice}'  />
			</div>
			
			<div class='control-group'>
	    	<label class='control-label' for='menuName'>合同回款：</label>
	    	<input type='text' name='receiveAmount' value='${model.receiveAmount}'  />
			</div>
	

		</div>
		


<input type="hidden" id="id" name="id" value="${model.id}"/>

</form>
 <script language="javascript">
	
</script>
</body>
</html>