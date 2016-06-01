package com.sinobridge.eoss.business.order.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.order.model.BusinessOrderProductModel;
import com.sinobridge.eoss.business.order.model.CloseContractModel;
import com.sinobridge.eoss.business.order.service.BusinessOrderProductService;
import com.sinobridge.eoss.business.order.service.CloseContractService;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;

@Controller
@RequestMapping(value = "business/closeContract")
public class CloseContractController  extends DefaultBaseController<CloseContractModel,CloseContractService>{

    @Autowired
    private BusinessOrderProductService businessOrderProductService;
    @Autowired
    private SalesContractService salesContractServive;
    
    /**
     * <code>manage</code>
     * 结算销售合同页面
     * @param request
     * @param response
     * @return
     * @since   2014年7月25日    wangya
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/closeContract/manage");
        return mav;
    }
    
    /**
     * <code>getList</code>
     * 得到表格列表
     * @param request
     * @param response
     * @return
     * @since   2014年5月5日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getTableGrid")
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
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getModel().getClass());
       // buildSearch(request, detachedCriteria);
        detachedCriteria.add(Restrictions.eq("statuse", "1"));
        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize , this.pageSize);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }
    
    /**
     * <code>buildSearchStaff</code>
     * 
     * @param request
     * @param detachedCriteria
     * @since   2014年5月20日    wangya
     */
    public void buildSearch(HttpServletRequest request, DetachedCriteria detachedCriteria) {
        detachedCriteria.add(Restrictions.eq("statuse", "1"));
    }
    
    
    /**
     * <code>settlement</code>
     * 显示结算的合同
     * @param request
     * @param response
     * @return
     * @since   2014年7月25日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/settlement")
    public ModelAndView settlement(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/closeContract/settlement");
        String id = request.getParameter("id");
        Set<BusinessOrderProductModel> orderproduct=new HashSet<BusinessOrderProductModel>(0);
        CloseContractModel closeContract = new CloseContractModel();
        //取出合同实体发送到前台页面
        if (id != null && !"".equals(id)) {
            closeContract=getService().get(Long.parseLong(id));
            SalesContractModel salesContract=closeContract.getSalesContract();
            Long salesId=salesContract.getId();
            List<BusinessOrderProductModel> products=businessOrderProductService.getOrderProduct(salesId); 
            if (products != null) {
                for (int i = 0; i < products.size(); i++) {
                    BusinessOrderProductModel orderProduct = products.get(i);
                    orderproduct.add(orderProduct);
                }
            }
            mav.addObject("productmodel", orderproduct);
            mav.addObject("model", closeContract);
        }
        return mav;
    }
    

    /**
     * <code>detail</code>
     * 详情页面
     * @param request
     * @param response
     * @return
     * @since   2014年7月25日    wangya
     */
    @RequestMapping(value = "/detail")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/closeContract/detail");
        Long id=Long.parseLong(request.getParameter("id"));
        CloseContractModel closeContract=getService().get(id);
        mav.addObject("model", closeContract);
        return mav;
    }
    
    /**
     * <code>close</code>
     * 结算确认
     * @param request
     * @param response
     * @param manageModel
     * @return
     * @since   2014年7月25日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/close")
    public String close(HttpServletRequest request,@ModelAttribute("_sino_eoss_sales_contract_approveForm") CloseContractModel closeContract){
        try {
            int isAgree=Integer.parseInt(request.getParameter("isAgree"));
            Long salesId=Long.parseLong(request.getParameter("salesId"));
            getService().close(closeContract,isAgree,salesId);
            return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                return FAIL;
            }
       
    }
}
