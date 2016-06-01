package com.sinobridge.eoss.business.stock.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.stock.model.SerialModel;
import com.sinobridge.eoss.business.stock.service.OutboundService;
import com.sinobridge.eoss.business.stock.service.SerialService;
import com.sinobridge.systemmanage.util.StringUtil;

@Controller
@RequestMapping(value = "/serial")
public class SerialController extends DefaultBaseController<SerialModel, SerialService>{
	
	@Autowired
	OutboundService outboundService;
	
	/**
	 * 初始化到序列号管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/serialManage")
	public ModelAndView inbound(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("business/stock/serialQuery");
		return mav;
	}
	
	/**
	 * 初始化序列号信息列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/serialInfo")
	@ResponseBody
    public Map<String, Object> serialInfo(HttpServletRequest request, HttpServletResponse response) {
		String params = request.getParameter("sEcho");
		String iDisplayStart = request.getParameter("iDisplayStart");
		String iDisplayLength = request.getParameter("iDisplayLength");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SerialModel.class);
		buildSearch_Project(request, detachedCriteria);
		PaginationSupport paginationSupport =  this.getService().findPageByCriteria(detachedCriteria,Integer.parseInt(iDisplayStart),Integer.parseInt(iDisplayLength));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", paginationSupport.getItems());
		map.put("sEcho", params);
		map.put("iTotalDisplayRecords", paginationSupport.getTotalCount());
		map.put("iTotalRecords", paginationSupport.getTotalCount());
		return map;
    }
	
	/**
	 * 按条件查询序列号信息
	 * @param request
	 * @param detachedCriteria
	 */
	public void buildSearch_Project(HttpServletRequest request,DetachedCriteria detachedCriteria) {
		String sSearch_0 = request.getParameter("sSearch_0");
		if (!StringUtil.isEmpty(sSearch_0)) {
			String[] ss = sSearch_0.split("_");
			if (!ss[0].equals("orderCode")) {
				detachedCriteria.add(Restrictions.eq("orderCode", ss[0]));
			}
			if (!ss[1].equals("contractCode")) {
				detachedCriteria.add(Restrictions.eq("contractCode", ss[1]));
			}
			if (!ss[2].equals("inboundCode")) {
				detachedCriteria.add(Restrictions.eq("inboundCode",ss[2]));
			}
		}
	}
	
	/**
	 * 删除序列号信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delSerial")
	@ResponseBody
	public String delSerial(HttpServletRequest request,HttpServletResponse response) {
		try {
			Long serialId = Long.parseLong(request.getParameter("serialId"));
			if(serialId!=null){
				this.getService().delete(serialId);
			} else {
				Assert.isNull(serialId);
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("删除失败");
			return FAIL;
		}
	}
	
}