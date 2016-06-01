<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/global.jsp" %>
 <html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${ctx}/skin/default/room.css" type="text/css" />
    <link rel="stylesheet" href="${ctx}/js/plugins/jquery-easyui-1.3.3/themes/icon.css"/>
    <link rel="stylesheet" href="${ctx}/js/plugins/jquery-easyui-1.3.3/themes/bootstrap/easyui.css"/>  
    
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/ultra_select.css" type="text/css"></link>
    
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formSelect.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formTree.css" type="text/css"></link>
    <link rel="stylesheet" href="${ctx}/js/plugins/uic/style/excellenceblue/uic/css/formstyle.css" type="text/css"></link>
    <title>产品品牌管理</title>
	<style type="text/css">
	</style>
  </head>
  <body>
   	  	<div class="pull-right">
		     <button id="_sino_partner_product_back" type="button" class="btn btn-default btn-lg">
			  <span class="icon-repeat"></span>
			  返回
			</button>
		  </div>
   	   <div  class="page-header">
   		<h2>型号详情</h2>
   	  </div>
   	  <div class="" style='float:left;text-alagin:center;'>
			<form id="_sino_partner_productModel_add_form" method="post" style="text-align: left;width:770px;">
			<input type="hidden" id="_productModelId" name="id" value="${model.id }">
			<input type="hidden" id="_partnerId" name="partnerId" value="${model.partnerId }">
			<input type="hidden" id="_productType" name="productType" value="${model.productType }">
			<input type="hidden" id="_brandCode" name="brandCode" value="${model.brandCode }">
			<input type="hidden" id="_productLine" name="productLine" value="${model.productLine }">
			<input type="hidden" id="_sino_base_picUrl" name="productIcon" value="${model.productIcon }">
			<input type="hidden" id="_sino_base_bigUrl" name="productBgImage" value="${model.productBgImage }">
			
			<input type="hidden" id="_sino_productTypeName" value="${productType.typeName }">
			<input type="hidden" id="_sino_productLineName" value="${productLine.productLine }">
		    	<table style="margin-top:10px;">
		    		<tr>
		    			<td class="_sino_td_one">&nbsp型&nbsp号&nbsp&nbsp名&nbsp称:</td>
		    		    <td  class="_sino_td_two">
		    			${model.productModelName }</td>
		    			<td class="_sino_td_one one">型&nbsp号&nbsp&nbsp编&nbsp码:</td>
		    		    <td  class="_sino_td_two">${model.productModel }</td>
		    		</tr>
		    		<tr>
		    			<td class="_sino_td_one">产&nbsp品&nbsp&nbsp类&nbsp别:</td>
		    			<td class="_sino_td_two">${productType.typeName }</div></td>
		    			<td class="_sino_td_one  one">&nbsp生&nbsp产&nbsp&nbsp厂&nbsp商:</td>
		    		    <td class="_sino_td_two">
			    		    <div class="controls" id="_sino_partner_div" >
				      		  ${partnerInfo.partnerName }
				      		</div>
						</td>
		    		</tr>
		    		<tr>
		    			<td class="_sino_td_one">公&nbsp开&nbsp&nbsp报&nbsp价:</td>
		    			<td class="_sino_td_two">${model.listPrice }</div></td>
		    			<td class="_sino_td_one  one">币&nbsp&nbsp种:</td>
		    		    <td class="_sino_td_two">
		    		    	<c:if test="${model.listPrice == 'RMB'}">人民币</c:if>
		    		    	<c:if test="${model.listPrice == 'USD'}">美元</c:if>
						</td>
		    		</tr>
		    		<%-- <tr>
		    			<td class="_sino_td_one">&nbsp产&nbsp品&nbsp&nbsp系&nbsp列:</td>
		    		    <td class="_sino_td_two">
			    		    ${productLine.productLine }
						</td>
		    			<td class="_sino_td_one one">产&nbsp品&nbsp&nbsp品&nbsp牌:</td>
		    			<td class="_sino_td_two">
		    				<c:forEach var="brand" items="${partnerInfo.basePartnerBrands }" varStatus="status">
		    					<c:if test="${brand.id ==  model.brandCode}">
		    						${brand.brandName }
		    					</c:if>
		    				</c:forEach>
		    			
		    			</td>
		    		</tr> --%>
		    		<tr>
		    			<td class="_sino_td_one _sino_td_top">设计寿命(月):</td>
		    		    <td  class="_sino_td_two _sino_td_top">
		    				${model.productPdlife }
		    			</td>
		    			<td class="_sino_td_one _sino_td_top one">重	量(kg):</td>
		    		    <td  class="_sino_td_two _sino_td_top">
		    		    ${model.productWeight }</td>
		    		</tr>
		    		<tr>
		    			<td class="_sino_td_one">外观尺寸(mm):</td>
		    		    <td  class="_sino_td_two">
		    			${model.productOutWard }</td>
		    		    <td class="_sino_td_one one">产&nbsp品&nbsp&nbsp&nbsp&nbspOID:</td>
		    		    <td  class="_sino_td_two">
		    		    ${model.productOid }</td>
		    		</tr>
		    		<tr>
		    			<td class="_sino_td_one _sino_td_top">产&nbsp品&nbsp&nbsp描&nbsp述:</td>
		    			<td class="_sino_td_two _sino_td_top">
		    				${model.productDesc }
		    			</td>
		    		</tr>
		    		<tr>
		    		    
		    			<td class="_sino_td_one">停产日期:</td>
		    		    <td  class="_sino_td_two">
		    		    
		    		     	${model.stopDate }
					     </td>
					     <td class="_sino_td_one  one">停止服务日期:</td>
		    		    <td  class="_sino_td_two">
		    				
		    				${model.stopSevDate }
		    			</td>
		    		</tr>
		    		<tr>
		    			<td class="_sino_td_one _sino_td_top">工作环境要求:</td>
		    			<td class="_sino_td_two _sino_td_top" colspan="3">
		    				${model.productWorkDesc }
		    			</td>
		    		</tr>
		    		<tr>
		    			<td colspan="4" class="_sino_td_one">图&nbsp&nbsp标:</td>
		    			<td class="_sino_td_two" colspan="3">
		    				
		    				<c:if test="${empty model.productIcon}">
			    				<img id="_sino_base_picPath" alt="预览" src="${ctx }/images/state/noUpLoad.jpg" style="height: 60px;  border: 1px solid #ccc" />
			    			</c:if>
			    			<c:if test="${model.productIcon != null&&model.productIcon != ''}">
			    				<img id="_sino_base_picPath" alt="预览" src="${ctx }${model.productIcon }" style="height: 60px; background-color: #ccc; border: 1px solid #ccc" />
			    			</c:if>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td colspan="4" class="_sino_td_one">背&nbsp景&nbsp&nbsp图&nbsp片:</td>
		    			<td class="_sino_td_two" colspan="3">
		    				<c:if test="${empty model.productBgImage}">
			    				<img id="_sino_base_picPath" alt="预览" src="${ctx }/images/state/noUpLoad.jpg" style="height: 60px;  border: 1px solid #ccc" />
			    			</c:if>
			    			<c:if test="${model.productBgImage != null&&model.productBgImage != ''}">
			    				<img id="_sino_base_bigPath" alt="预览" src="${ctx }${model.productBgImage }" style="height: 60px; background-color: #ccc; border: 1px solid #ccc" />
			    			</c:if>
		    			</td>
		    		</tr>
		    	</table>
	    	</form>
				<div id="_sino_productModel_addExpand_div"></div>
					<form id="_sino_partner_productModelData_add_form" method="post" style="text-align: left;width:800px;">
						<input type="hidden" id="_productModelDataId" name="id" value="${model.id }">
						<c:forEach var="dto" items="${dtoList}" varStatus="status">
						<c:if test="${dto.indicatorCategoryId == null||dto.indicatorCategoryId == ''}">
							<c:if test="${status.index%2 == 0}">
								<div class="form-horizontal" style="float: left;height:10px;margin-bottom: 28px">
									<div class="control-group">
										<label class="control-label" for="partnerCode">${dto.attrName }:</label>
									    <div class="controls">
									    	<c:if test="${dto.catalogy == 10}">
									    		${dto.attrValue }
									    	</c:if>
									    	<c:if test="${dto.catalogy == 5||dto.catalogy == 6}">
									    		${dto.attrValue }
									    	</c:if>
									    	<c:if test="${dto.catalogy == 4}">
									    		<div style="display:none;">
													    		<div class="_domainCode" name="${dto.domainCode }" inputid="${dto.id }" id="${dto.id+2013}"></div>
													    	</div>
									    		<input type="hidden" value="${dto.attrValue }" name="${dto.att }" id="${dto.id }" />
									    		<div id="${dto.id+2014}"></div>
									    	</c:if>
									    </div>
									</div>
								</div>
							</c:if>
							<c:if test="${status.index%2 != 0}">
								<div class="form-horizontal" style="float: left;">
									<div class="control-group">
										<label class="control-label" for="partnerCode">${dto.attrName }:</label>
									    <div class="controls">
									    	<c:if test="${dto.catalogy == 10}">
									    		${dto.attrValue }
									    	</c:if>
									    	<c:if test="${dto.catalogy == 5||dto.catalogy == 6}">
									    		${dto.attrValue }
									    	</c:if>
									    	<c:if test="${dto.catalogy == 4}">
									    		<div style="display:none;">
													    		<div class="_domainCode" name="${dto.domainCode }" inputid="${dto.id }" id="${dto.id+2013}"></div>
													    	</div>
									    		<input type="hidden" value="${dto.attrValue }" name="${dto.att }" id="${dto.id }" />
									    		<div id="${dto.id+2014}"></div>
									    		<div>
									    	</c:if>
									    </div>
									</div>
								</div>
							</c:if>
							</c:if>
						</c:forEach>
						<div class="row"></div>
						<div class="row">
							<c:forEach var="dtoCty" items="${dtoCtyList}" varStatus="statusCty">
								<div class="col-lg-4">
								<h4>${dtoCty.attrName }</h4>
								<c:forEach var="dto" items="${dtoList}" varStatus="status">
									<c:if test="${dto.indicatorCategoryId == dtoCty.indicatorCategoryId}">
											<div class="form-horizontal" style="float: left;">
												<div class="control-group">
													<label class="control-label" for="partnerCode">${dto.attrName }:</label>
												    <div class="controls">
												    	<c:if test="${dto.catalogy == 10}">
												    		${dto.attrValue }
												    	</c:if>
												    	<c:if test="${dto.catalogy == 5||dto.catalogy == 6}">
												    		${dto.attrValue }
												    	</c:if>
												    	<c:if test="${dto.catalogy == 4}">
													    	<div style="display:none;">
													    		<div class="_domainCode" name="${dto.domainCode }" inputid="${dto.id }" id="${dto.id+2013}"></div>
													    	</div>
												    		<input type="hidden" value="${dto.attrValue }" name="${dto.att }" id="${dto.id }" />
												    		<div id="${dto.id+2014}"></div>
												    	</c:if>
												    </div>
												</div>
											</div>
									</c:if>
									</c:forEach>
								</div>
							<div class="row"></div>
							</c:forEach>
						</div>
						
						<%-- <c:if test="${fn:length(dtoList) <= 0&&fn:length(dtoCtyList) <= 0}">
							<div class="row">
							<div class="form-horizontal" style="width:800px;">
								<div class="control-group">
									<h4>没有配置参数,请点击编辑页,操作配置参数</h4>
								</div>
							</div>
						</div>
						</c:if> --%>
				 </form>
				<div class="modal-footer" style="margin-top:40px;">
					<div class="pull-right" id="_sino_partner_productModel_handler">
				  		<a id="_sino_partner_productModel_addbg" role="button" data-toggle="modal" class="btn btn-primary">确定</a>
					</div>
				</div>
			</div>
	   <script language="javascript">
		  	seajs.use('js/page/business/projectm/productModel_detail',function(productModel_detail){
		  		productModel_detail.init();
			 });    
		</script>

</body>
</html>