package com.sinobridge.eoss.business.stock.controller;

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
import com.sinobridge.eoss.business.stock.dao.ExcelDao;
import com.sinobridge.eoss.business.stock.model.ExcelModel;
import com.sinobridge.eoss.business.stock.service.ExcelService;

/**
 * excel导入
 * @author Stone
 *
 */
@Controller
@RequestMapping(value = "/inboundexcel")
public class InboundExcelController extends DefaultBaseController<ExcelDao, ExcelService> {

    /**
     * 初始化excel表格导入页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/inbound")
    public ModelAndView excel(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/stock/excel");
        return mav;
    }

    /**
     * 得到表格列表
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getExcelInfo")
    public Map<String, Object> getExcelInfo(HttpServletRequest request, HttpServletResponse response) {
        String params = request.getParameter("sEcho");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength = request.getParameter("iDisplayLength");
        //System.out.println("iDisplayStart="+iDisplayStart+";iDisplayLength="+iDisplayLength);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ExcelModel.class);
        PaginationSupport paginationSupport = this.getService().findPageByCriteria(detachedCriteria, Integer.parseInt(iDisplayStart), Integer.parseInt(iDisplayLength));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("aaData", paginationSupport.getItems());
        map.put("sEcho", params);
        map.put("iTotalDisplayRecords", paginationSupport.getTotalCount());
        map.put("iTotalRecords", paginationSupport.getTotalCount());
        return map;
    }

    /**
     * 上传Excel操作
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uploadFile")
    public String uploadFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            getService().uploadFile(request);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

    /**
     * 一键入库
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/dealExcel")
    public String dealExcel() {
        try {
            this.getService().dealExcel();
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

}