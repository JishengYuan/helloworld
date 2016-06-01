package com.sinobridge.eoss.business.stock.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.stock.model.OutboundModel;
import com.sinobridge.eoss.business.stock.model.StateUtil;
import com.sinobridge.eoss.business.stock.service.OutboundService;
import com.sinobridge.eoss.business.stock.service.StockService;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contract.utils.DateUtils;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.vo.SystemUser;

@Controller
@RequestMapping(value = "/outStorage")
public class OutboundController extends DefaultBaseController<OutboundModel, OutboundService> {

    @Autowired
    StockService stockService;
    
    @Autowired
    SalesContractService contractService;

    /**
     * 初始化到出库信息页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/outStorage")
    public ModelAndView inbound(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/stock/outStorageManage");
        return mav;
    }

    /**
     * 初始化出库产品信息列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/outStorageInfo")
    @ResponseBody
    public Map<String, Object> outStorageInfo(HttpServletRequest request, HttpServletResponse response) {
//        String params = request.getParameter("sEcho");
//        String iDisplayStart = request.getParameter("iDisplayStart");
//        String iDisplayLength = request.getParameter("iDisplayLength");
//        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StockModel.class);
//        detachedCriteria.add(Restrictions.eq("stockState", StateUtil.YRK));
//        buildSearch_Project(request, detachedCriteria);
//        PaginationSupport paginationSupport = this.getService().findPageByCriteria(detachedCriteria, Integer.parseInt(iDisplayStart), Integer.parseInt(iDisplayLength));
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("aaData", paginationSupport.getItems());
//        map.put("sEcho", params);
//        map.put("iTotalDisplayRecords", paginationSupport.getTotalCount());
//        map.put("iTotalRecords", paginationSupport.getTotalCount());
        
        Map<String, Object> map = new HashMap<String, Object>();
        String iDisplayStart = request.getParameter("pageNo");
        String iDisplayLength = request.getParameter("pageSize");
        if (iDisplayStart != null) {
            this.pageNo = Integer.parseInt(iDisplayStart) - 1;
        }
        if (iDisplayLength != null) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OutboundModel.class);
        buildSearch_Project(request, detachedCriteria);
        
        PaginationSupport paginationSupport = this.getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);
        
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        return map;
    }

    /**
     * 按条件查询出库信息
     * @param request
     * @param detachedCriteria
     */
    public void buildSearch_Project(HttpServletRequest request, DetachedCriteria detachedCriteria) {

        detachedCriteria.addOrder(Order.desc(OutboundModel.ID));
        String contractCode = request.getParameter("contractCode");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        if(!StringUtil.isEmpty(contractCode)){
            detachedCriteria.add(Restrictions.like(OutboundModel.CONTRACTCODE, "%"+contractCode+"%"));
        }
        if(!StringUtil.isEmpty(startTime)){
            detachedCriteria.add(Restrictions.ge(OutboundModel.OUTBOUNDTIME, DateUtils.convertStringToDate(startTime, "yyyy-MM-dd")));
        }
        if(!StringUtil.isEmpty(endTime)){
            detachedCriteria.add(Restrictions.le(OutboundModel.OUTBOUNDTIME, DateUtils.convertStringToDate(endTime, "yyyy-MM-dd")));
        }
        
    }
    
    @RequestMapping(value = "/outStockSearch")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/stock/outStockSearch");
        return mav;
    }

    /**
     * 弹出出库选项页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/outboundView")
    public ModelAndView addAreaView(HttpServletRequest request, HttpServletResponse response) {
        String outboundId = request.getParameter("outboundId");
        ModelAndView mav = new ModelAndView("business/stock/outStorageOption");
        mav.addObject("outboundId", outboundId);
        return mav;
    }
    
    @ResponseBody
    @RequestMapping(value = "/getOutboundContractList")
    public Map<String, Object> getOutboundContractList(HttpServletRequest request, HttpServletResponse response) {
        String params = request.getParameter("sEcho");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength = request.getParameter("iDisplayLength");
        if (!StringUtil.isEmpty(iDisplayLength)) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }
        PaginationSupport paginationSupport = contractService.getOutboundSalesContractList(request, Integer.parseInt(iDisplayStart), this.pageSize);
        Map<String, Object> pg = new HashMap<String, Object>();
        pg.put("aaData", paginationSupport.getItems());
        pg.put("sEcho", params);
        pg.put("iTotalDisplayRecords", paginationSupport.getTotalCount());
        pg.put("iTotalRecords", paginationSupport.getTotalCount());
        return pg;
    }
    
    

    /**
     * 保存设备出库状态
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/saveOutState")
    @ResponseBody
    public String saveOutState(HttpServletRequest request, HttpServletResponse response) {
        try {
            String outboundCode = request.getParameter("outboundCode");
            String outState = request.getParameter("stockState");
            String outboundId = request.getParameter("outboundId");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String outboundTime = sdf.format(new Date());
            if (!StringUtil.isEmpty(outboundId)) {
                if (Integer.parseInt(outState) == StateUtil.YCK) {
                    stockService.upProject(Integer.parseInt(outState), outboundCode, outboundTime, Long.parseLong(outboundId));
                } else if (Integer.parseInt(outState) == StateUtil.JC) {
                    stockService.upProject(Integer.parseInt(outState), outboundCode, outboundTime, Long.parseLong(outboundId));
                } else if (Integer.parseInt(outState) == StateUtil.GH) {
                    stockService.upProject(Integer.parseInt(outState), outboundCode, outboundTime, Long.parseLong(outboundId));
                }
            } else {
                Assert.isNull(outboundId);
            }
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("出库失败");
            return FAIL;
        }
    }

    /**
     * 逻辑删除出库信息（实则修改库存状态）
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/delOutbound")
    @ResponseBody
    public String delOutbound(HttpServletRequest request, HttpServletResponse response) {
        try {
            String outboundId = request.getParameter("outboundId");
            //			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //			String str = sdf.format(new Date());
            if (!StringUtil.isEmpty(outboundId)) {
                stockService.upOutProject(StateUtil.YDR, null, Long.parseLong(outboundId));
            } else {
                Assert.isNull(outboundId);
            }
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("删除失败");
            return FAIL;
        }
    }
    
    /**
     * <code>getOutboundSalesProduct</code>
     * 得到合同要出库的产品
     * @return
     * @since   2014年10月17日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getOutboundSalesProduct")
    public List<Map<String,Object>> getOutboundSalesProduct(HttpServletRequest request, HttpServletResponse response){
        
        List<Map<String,Object>> mapList = getService().getOutboundSalesProduct(request);
        
        return mapList;
    }
    
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/doSalesOutboundProduct")
    public String doSalesOutboundProduct(HttpServletRequest request, HttpServletResponse response){
        Map<String, String[]> parameterMap = request.getParameterMap();
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        String storePlace = request.getParameter("storePlace");
        try {
            getService().doSalesOutboundProduct(parameterMap,systemUser,storePlace);
            return SUCCESS;
        } catch (Exception e) {
            return FAIL;
        }
        
    }
    
    /**
     * <code>addOutStoragePage</code>
     * 新增出库页面
     * @param request
     * @param response
     * @return
     * @since   2014年12月28日    guokemenng
     */
    @RequestMapping(value = "/addOutStoragePage")
    public ModelAndView appOutStoragePage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/stock/addOutStoragePage");
        return mav;
    }

}