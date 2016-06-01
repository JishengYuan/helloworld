package com.sinobridge.eoss.business.stock.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.sinobridge.eoss.business.stock.model.InboundModel;
import com.sinobridge.eoss.business.stock.model.StateUtil;
import com.sinobridge.eoss.business.stock.model.StockModel;
import com.sinobridge.eoss.business.stock.service.InboundService;
import com.sinobridge.eoss.business.stock.service.StockService;
import com.sinobridge.systemmanage.util.StringUtil;

@Controller
@RequestMapping(value = "/intoStorage")
public class InboundController extends DefaultBaseController<InboundModel, InboundService>{
	
	@Autowired
	InboundService inboundService;
	@Autowired
	StockService stockService;
	
	/**
	 * 初始化到入库信息页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/inbound")
	public ModelAndView inbound(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("business/stock/intoStorageManage");
		return mav;
	}
	
	/**
	 * 初始化入库产品信息列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/inboundInfo")
	@ResponseBody
    public Map<String, Object> inboundInfo(HttpServletRequest request, HttpServletResponse response) {
		String params = request.getParameter("sEcho");
		String iDisplayStart = request.getParameter("iDisplayStart");
		String iDisplayLength = request.getParameter("iDisplayLength");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StockModel.class);
		detachedCriteria.add(Restrictions.and(Restrictions.ne("stockState",StateUtil.YRK),Restrictions.ne("stockState",StateUtil.YCK)));
		//detachedCriteria.add(Restrictions.or(Restrictions.ne("stockState",StateUtil.YRK),Restrictions.ne("stockState",StateUtil.YCK)));
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
	 * 按条件查询入库信息
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
			if (!ss[2].equals("timeA")&&!ss[3].equals("timeB")) {
				detachedCriteria.add(Restrictions.between("inboundTime",ss[2],ss[3]));
			}
		}
	}
	
	/**
	 * 产品入库（实则修改库存状态）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/upInbound")
	@ResponseBody
	public String upInbound(HttpServletRequest request,HttpServletResponse response) {
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String inboundTime = sdf.format(new Date());
			String inboundId = request.getParameter("stockId");
			if(!StringUtil.isEmpty(inboundId)){
				stockService.upInProject(StateUtil.YRK,inboundTime,Long.parseLong(inboundId));
			} else {
				Assert.isNull(inboundId);
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("入库失败");
			return FAIL;
		}
	}
	
	/**
	 * 逻辑删除入库信息（实则修改库存状态）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delInbound")
	@ResponseBody
	public String delInbound(HttpServletRequest request,HttpServletResponse response) {
		try {
			String inboundId = request.getParameter("inboundId");
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String str = sdf.format(new Date());
			if(!StringUtil.isEmpty(inboundId)){
				stockService.upInProject(StateUtil.YDR,null,Long.parseLong(inboundId));
			} else {
				Assert.isNull(inboundId);
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("删除失败");
			return FAIL;
		}
	}
	
}