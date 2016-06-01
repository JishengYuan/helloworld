<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <%@ page import="com.sinobridge.systemmanage.util.Global"%> 
<head>
    <title>内部采购</title>
	<%@ include file="/common/include-base-boostrap-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/sinobridge/pink/sino_form_grid.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/dialog.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/tip.css" type="text/css" ></link>
	<link rel="stylesheet" href="${ctx}/skin/default/editgrid/editgrid.css" type="text/css" />
	<link type="text/css" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/jquery-ui-1.8.17.custom.css" rel="stylesheet">
	
	<script src="${ctx}/js/plugins/seajs/sea.js" id="seajsnode" type="text/javascript"></script>
	<script src="${ctx}/js/plugins/seajs/config.js" type="text/javascript"></script>
	<script src="${ctx}/js/page/sales/contract/makePy.js" type="text/javascript"></script>
	<link href="${ctx}/skin/default/sales/sale.css" rel="stylesheet"/>
<style type="text/css">

	.row-input{
		padding: 0 5px;
		width: 180px;
		height:28px"
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
    color: #000000;
}
.handprocess_btngroup input {
    cursor: pointer;
    height: auto;
    margin-right: 15px;
}

</style>
</head>
<body>
<!-- 遮盖层div -->
	<div id="_progress1" style="display: none; top: 0px; left: 0px; width: 100%; margin-left: 0px; height: 100%; margin-top: 0px; z-index: 1000; opacity: 0.7; text-align: center; background-color: rgb(204, 204, 204); position: fixed;">
	</div>
	<div id="_progress2" style="display: none; width: 233px; height: 89px; top: 45%; left: 45%; position: absolute; z-index: 1001; line-height: 23px; text-align: center;">
		<div style="top: 50%;position:fixed;">
			<div style="display: block;">数据加载中，请稍等...</div>
		</div>
	</div>
	
	
<div class="salecontent">
		<div class="top">
			<div class="caption">
				<i class="icon-reorder"></i>&nbsp;&nbsp;&nbsp;添加内部采购信息
			    <!-- <span class="tright" >内部采购编号：</span> -->
			</div>
			<div class="tools">
				<!--a href="javascript:;" class="reload"><i class="icon-repeat"></i>返回</a-->
			</div>
		</div>
	</div>	
	<!--表单、底部按钮 -->
	<div class="portlet-body form">
	<form  id="_sino_eoss_cuotomer_addform" class='form-horizontal'  method="post">
	<!--  隐藏ID-->
	<input type="hidden" id="_eoss_customer_id" value="${model.id }" name="id"/>
	<!--  保存为草稿还是提交的标志位-->
	<input type="hidden" id="purchasCode" name="purchasCode" value="" />
	<input type='hidden' name='purchasStatus' id='purchasStatus' value="${model.purchasStatus }"/>
	<div class="handprocess_order" id="SheetDiv">
	<br>
		<h3 class="form-section-title">采购信息</h3>
		<ul class="clearfix" >
		<%-- 	<li id="field_userName" class="li_form" style="width: 320px;">
				<label  class="editableLabel">编号：</label>
				<input type="text" id="purchasCode" name="purchasCode" class="row-input" style="border: none; disabled="readonly" value="${model.purchasCode }"  placeholder='编号自动生成'/>
			</li> --%>
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label class="editableLabel">申请单名称：</label>
				<input type="text" id="purchasName" name="purchasName" class="row-input" value="${model.purchasName }"  placeholder='请输入名称' required data-content="请输入申请单名称"/>
			</li>
			<li id="field_userName" class="li_form" style="width: 400px;">
				<label for="expectedDeliveryTime" class="editableLabel">期望到货时间：</label>
				    <div style="position: relative;" class="input-append date">
						 <input data-format="yyyy-MM-dd"  name="expectedDeliveryTime"  type="text"  style="width:150px"></input>
						    <span class="add-on">
						      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
						      </i>
							</span>
					  </div>
			</li>
		</ul>
			<li id="field_userName" class="li_form" style="width: 670px;margin-left:24px" >
				<label  class="editableLabel">用途：</label>
		        <textarea name="remark" id="remark" rows="3" cols="30" style="width:500px" value="${model.remark }" placeholder='请输入用途' required data-content="请输入用途"></textarea>
			</li>
		<div style="clear:both;"></div>
		
		<div class=" row-inline" >
				<hr size=1 class="dashed-inline">
		</div>
		
		<div class=" row-inline" >
				<h3 class="form-section-title">设备添加</h3>
		
				<div Style="margin-left: 863px;width: 88px;margin-bottom: 5px">
					<a id='products_add' role="button" class="btn btn-success"><i class="icon-plus"></i>添加</a>
				</div>
			</div>
			<!-- 小标题end -->
			<!-- 9行start -->	
			<div class=" row-inline" >
				<div class='table-tile-width'>
					<table id="products_table"class="table  table-bordered table-striped">
					  <thead>
					    <tr>
					      <th width="6%">序号</th>
					      <th width="15%">产品类型</th>
					      <th width="15%">产品厂商</th>
					      <th width="20%">产品型号</th>
					      <th width="15%">数量</th>
					      <th width="16%">操作</th>
					    </tr>
					  </thead>
					  <tbody>
					  </tbody>
					</table>
				</div>
			</div>
</div>
<div style="clear:both;"></div>
</form>
			<!--设备添加弹出对话框 -------start-->
			 <div id="dailogs1" class="modal hide fade" role="dialog" tabIndex="-1" style="width:800px;height:460px">
		   		<div class="modal-header">
				   <h3 id="dtitle"></h3>
				   <div id="_product_alertMsg"></div>
		  		</div>
				<div class="modal-body" style="height:70%">
					<div id="dialogbody" ></div>
				</div>
				<div class="modal-footer">
					<button id="_do_cancel"  class="btn" data-dismiss="modal" aria-hidden="true" >取消</button >
					<a id="dsave"  class="btn btn-primary" >保存</a>
				</div>
			</div>
			<!--设备添加弹出对话框 -------end-->
	<!--按钮组-->
    		<!-- <div id="bottom_button" class="handprocess_btngroup">
        		<input id="ok_Add" type="button" value="保存"  class="btn btn-success">
        		<input id="sp_Add" type="button" value="提交"  class="btn btn-success">
        		<input id="no_back" type="button" value="取消" class="btn ">
    		</div> -->
    		<div class="form-actions">
				<div style='text-align:center;'>
					<a id='ok_Add' role="button" class="btn btn-success">保存草稿</a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a id='sp_Add' role="button" class="btn btn-success"><i class="icon-ok"></i>提交</a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<button id='no_back' type="button" class="btn">取消</button>
				</div>		
			</div>  
     </div>
</div>

 <script language="javascript">
 seajs.use('js/page/business/interPurchas/addNote',function(addNote){
	 addNote.init();
    });
</script>
</body>
</html>