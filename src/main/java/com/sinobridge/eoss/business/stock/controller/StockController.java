package com.sinobridge.eoss.business.stock.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.stock.model.StockModel;
import com.sinobridge.eoss.business.stock.service.StockService;
import com.sinobridge.eoss.sales.contract.utils.DateUtils;
import com.sinobridge.systemmanage.util.StringUtil;

@Controller
@RequestMapping(value = "/storage")
public class StockController extends DefaultBaseController<StockModel, StockService> {

    /**
     * 初始化到库存信息页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/storageManage")
    public ModelAndView storageManage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/stock/storageQuery");
        return mav;
    }

    /**
     * 初始化库存产品信息列表
     * @param request
     * @param response
     * @return
     */
    /*@RequestMapping(value = "/stockInfo")
    @ResponseBody
    public Map<String, Object> stockInfo(HttpServletRequest request, HttpServletResponse response) {
        String params = request.getParameter("sEcho");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength = request.getParameter("iDisplayLength");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StockModel.class);
        buildSearch_Project(request, detachedCriteria);
        PaginationSupport paginationSupport = this.getService().findPageByCriteria(detachedCriteria, Integer.parseInt(iDisplayStart), Integer.parseInt(iDisplayLength));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("aaData", paginationSupport.getItems());
        map.put("sEcho", params);
        map.put("iTotalDisplayRecords", paginationSupport.getTotalCount());
        map.put("iTotalRecords", paginationSupport.getTotalCount());
        return map;
    }*/
    
    /**
     * <code>getList</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年10月13日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/stockInfo")
    public Map<String, Object> getList(HttpServletRequest request, HttpServletResponse response) {
        
        String typeId = request.getParameter("typeId");
        String partnerId = request.getParameter("partnerId");
        String productCode = request.getParameter("productCode");
        Map<String,Object> params = new HashMap<String,Object>();
        
        if(!StringUtil.isEmpty(typeId)){
            params.put("typeId", typeId);
        } 
        if(!StringUtil.isEmpty(partnerId)){
            params.put("partnerId", partnerId);
        } 
        if(!StringUtil.isEmpty(productCode)){
            params.put("productCode", productCode);
        } 
        
        Map<String, Object> map = new HashMap<String, Object>();
        String iDisplayStart = request.getParameter("pageNo");
        String iDisplayLength = request.getParameter("pageSize");
        if (iDisplayStart != null) {
            this.pageNo = Integer.parseInt(iDisplayStart) - 1;
        }
        if (iDisplayLength != null) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }
//        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StockModel.class);
//        buildSearch_Project(request, detachedCriteria);
        
//        PaginationSupport paginationSupport = this.getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);
        PaginationSupport paginationSupport = this.getService().getStockPageList(params, this.pageNo * this.pageSize, this.pageSize);
        
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", iDisplayStart);
        return map;
    }
    
    /**
     * <code>search</code>
     * 库存搜索页面
     * @param request
     * @param response
     * @return
     * @since   2014年10月13日    guokemenng
     */
    @RequestMapping(value = "/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/stock/search");
        return mav;
    }

    /**
     * 按条件查询相应信息
     * @param request
     * @param detachedCriteria
     */
    public void buildSearch_Project(HttpServletRequest request, DetachedCriteria detachedCriteria) {
        
        detachedCriteria.addOrder(Order.desc("id"));
        String orderCode = request.getParameter("orderCode");
        String contractCode = request.getParameter("contractCode");
        
        String selectValue = request.getParameter("selectValue");
        
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        if(!StringUtil.isEmpty(orderCode)){
            detachedCriteria.add(Restrictions.like(StockModel.ORDERCODE, "%"+orderCode+"%"));
        }
        if(!StringUtil.isEmpty(contractCode)){
            detachedCriteria.add(Restrictions.like(StockModel.CONTRACTCODE, "%"+contractCode+"%"));
        }
        if(!StringUtil.isEmpty(selectValue)){
            if(selectValue.equals("1")){
                if(!StringUtil.isEmpty(startTime)){
                    detachedCriteria.add(Restrictions.ge(StockModel.INBOUNDTIME, DateUtils.convertStringToDate(startTime, "yyyy-MM-dd")));
                }
                if(!StringUtil.isEmpty(endTime)){
                    detachedCriteria.add(Restrictions.le(StockModel.INBOUNDTIME, DateUtils.convertStringToDate(endTime, "yyyy-MM-dd")));
                }
            } else {
                if(!StringUtil.isEmpty(startTime)){
                    detachedCriteria.add(Restrictions.ge(StockModel.OUTBOUNDTIME, DateUtils.convertStringToDate(startTime, "yyyy-MM-dd")));
                }
                if(!StringUtil.isEmpty(endTime)){
                    detachedCriteria.add(Restrictions.le(StockModel.OUTBOUNDTIME, DateUtils.convertStringToDate(endTime, "yyyy-MM-dd")));
                }
            }
        }
//        String sSearch_0 = request.getParameter("sSearch_0");
//        if (!StringUtil.isEmpty(sSearch_0)) {
//            String[] ss = sSearch_0.split("_");
//            if (!ss[0].equals("vendor")) {
//                detachedCriteria.add(Restrictions.eq("vendor", ss[0]));
//            }
//            if (!ss[1].equals("orderCode")) {
//                detachedCriteria.add(Restrictions.eq("orderCode", ss[1]));
//            }
//            if (!ss[2].equals("contractCode")) {
//                detachedCriteria.add(Restrictions.eq("contractCode", ss[2]));
//            }
//            if (!ss[3].equals("stockState")) {
//                detachedCriteria.add(Restrictions.eq("stockState", Integer.parseInt(ss[3])));
//            }
//            if (!ss[4].equals("inboundCode")) {
//                detachedCriteria.add(Restrictions.eq("inboundCode", ss[4]));
//            }
//            if (!ss[5].equals("outboundCode")) {
//                detachedCriteria.add(Restrictions.eq("outboundCode", ss[5]));
//            }
//        }
    }

    /**
     * 查看详细信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sotckDetail")
    public ModelAndView addCase(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/stock/storageDetail");
        String stockId = request.getParameter("stockId");
        if (stockId != null && stockId != "") {
            this.model = this.getService().get(Long.parseLong(stockId));
            mav.addObject("model", this.model);
        }
        return mav;
    }

}