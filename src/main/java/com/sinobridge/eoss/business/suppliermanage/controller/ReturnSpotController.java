package com.sinobridge.eoss.business.suppliermanage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.eoss.business.suppliermanage.dto.SupplierReturnDto;
import com.sinobridge.eoss.business.suppliermanage.model.ReturnSpotModel;
import com.sinobridge.eoss.business.suppliermanage.service.ReturnSpotService;
import com.sinobridge.systemmanage.util.StringUtil;

@Controller
@RequestMapping(value = "/business/supplierm/returnSpot")
public class ReturnSpotController extends DefaultBaseController<ReturnSpotModel, ReturnSpotService> {

    /**
     * <code>manage</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年5月20日    guokemenng
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/supplierm/returnSpot/manage");
        return mav;
    }

    /**
     * <code>getList</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年5月20日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getList")
    public Map<String, Object> getList(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo) - 1;
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }

        map.put("rows", getService().getMapList(request,this.pageNo*this.pageSize,this.pageSize));
        map.put("total", getService().getCountByQuery(request));
        map.put("page", pageNo);
        return map;
    }
    
    /**
     * <code>addPage</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年5月26日    guokemenng
     */
    @RequestMapping(value = "/addPage")
    public ModelAndView addPage(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("business/supplierm/returnSpot/addPage");
        String id = request.getParameter("id");
        SupplierReturnDto dto = getService().getSupplierInfo(id);
        mav.addObject("dto", dto);
        return mav;
    }
    
    @ResponseBody
    @RequestMapping(value = "/addReturnSpot")
    public String addReturnSpot(HttpServletRequest request, HttpServletResponse response){
        try {
            String returnSpotId = request.getParameter("returnSpotId");
            if(StringUtil.isEmpty(returnSpotId)){
                getService().addReturnSpot(request);
            } else {
                getService().updateReturnSpot(request);
            }
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

    /**
     * <code>detail</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年5月26日    guokemenng
     */
    @RequestMapping(value = "/detail")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/supplierm/returnSpot/detail");
        
        String id = request.getParameter("id");
        ReturnSpotModel model = getService().detail(id);
        mav.addObject("model",model);
        
        return mav;
    }
    /**
     * <code>delReturnSpot</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年5月26日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/delReturnSpot")
    public String delReturnSpot(HttpServletRequest request, HttpServletResponse response){
        try {
            String id = request.getParameter("ids");
            getService().delReturnSpot(id);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }
    
    /**
     * <code>search</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年5月27日    guokemenng
     */
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("business/supplierm/returnSpot/search");
        return mav;
    }
    
}