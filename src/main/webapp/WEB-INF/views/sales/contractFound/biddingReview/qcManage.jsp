<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>投标章资质信息主页</title>
<style type="text/css">
.form-search .input-append .btn {
    border-radius: 0 14px 14px 0;
}
</style>
</head>
<body>
<div style="height:30px;position:absolute;right:20px;top:5px;">
    <form class="form-search">
      <div class="input-append" style="float:right;">
        <input type="text" class="span2 search-query" id="search-queryqc" >
        <button type="submit" class="btn searchqc">检索</button>
      </div>
    </form>
</div>
	<div id="uicTable" style="margin-top:0px;"></div>
	<script language="javascript">
		seajs.use('js/page/sales/contractFound/biddingReview/qcmanage', function(qcmanage) {
			qcmanage.init();
		});
	</script>
</body>
</html>