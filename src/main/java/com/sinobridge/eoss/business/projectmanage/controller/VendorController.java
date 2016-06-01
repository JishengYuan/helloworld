package com.sinobridge.eoss.business.projectmanage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.projectmanage.model.VendorModel;
import com.sinobridge.eoss.business.projectmanage.service.VendorService;

@Controller
@RequestMapping(value = "/business/projectm/vendor")
public class VendorController extends DefaultBaseController<VendorModel, VendorService>{

    /**
     * <code>managePublish</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年5月12日    guokemenng
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/projectm/vendor/manage");
        return mav;
    }
    
    /**
     * <code>getList</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年5月12日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getList")
    public Map<String, Object> getList(HttpServletRequest request, HttpServletResponse response) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        String iDisplayStart = request.getParameter("pageNo");
        String iDisplayLength = request.getParameter("pageSize");
        if (iDisplayStart != null) {
            this.pageNo = Integer.parseInt(iDisplayStart);
        }
        if (iDisplayLength != null) {
            this.pageSize = Integer.parseInt(iDisplayLength);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getModel().getClass());
        buildSearchStaff(request, detachedCriteria);
        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo, this.pageSize);
   
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }
    
    
    /**
     * <code>buildSearch</code>
     * 创建查询条件
     * @param detachedCriteria
     * @since   2013-11-13    guokemenng
     */
    public void buildSearchStaff(HttpServletRequest request, DetachedCriteria detachedCriteria) {
        
    }
    
    /**
     * <code>search</code>
     * 搜索页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月14日    guokemenng
     */
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("business/projectm/vendor/search");
        return mav;
    }
    
    
    @ResponseBody
    @RequestMapping(value="/add")
    public String addModel(){
        for(int i = 0;i < 100;i++){
            VendorModel vendor = new VendorModel();
            vendor.setCurrency("hhh"+i);
            vendor.setVendorCode(i+i+i+"");
            vendor.setVendorName("郭克蒙");
            vendor.setVendorEnName("guokemeng");
            getService().saveOrUpdate(vendor);
        }
        return SUCCESS;
    }
}