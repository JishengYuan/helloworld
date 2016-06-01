package com.sinobridge.eoss.business.interPurchas.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Ehcache;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.bpm.common.constants.ProcessConstants;
import com.sinobridge.eoss.bpm.service.ProcInstAppr;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.business.interPurchas.model.InterPurchasModel;
import com.sinobridge.eoss.business.interPurchas.model.InterPurchasProductModel;
import com.sinobridge.eoss.business.interPurchas.service.InterPurchasService;
import com.sinobridge.eoss.business.interPurchas.utils.CodeUtils;
import com.sinobridge.eoss.business.interPurchas.utils.DateUtils;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.systemmanage.model.SysOrgnization;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.model.SysStaffOrg;
import com.sinobridge.systemmanage.service.SysStaffService;
import com.sinobridge.systemmanage.util.Constants;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.vo.SystemUser;

/**
 * 
 * <p>
 * Description: 内部采购
 * </p>
 *
 * @author tigq
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年1月8日 下午3:16:34          tigq        1.0         To create
 * </p>
 *
 * @since 
 * @see
 */
@Controller
@RequestMapping(value = "business/interPurchas")
public class InterPurchasController extends DefaultBaseController<InterPurchasModel, InterPurchasService> {
    @Autowired
    private ProcessService processService;
    @Autowired
    private SysStaffService staffService;

    private final static Ehcache systemUserCache = Constants.CACHE_MANAGER.getEhcache("systemUser");

    /**
     * <code>manage</code>
     * 內采页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月5日    wangya
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/interPurchas/manage");
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
        buildSearch(request, detachedCriteria);
        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);

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
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        detachedCriteria.addOrder(Order.desc(InterPurchasModel.ID));
        String creator = request.getParameter("creator");
        String expectedDeliveryTime = request.getParameter("expectedDeliveryTime");

        if (!StringUtil.isEmpty(creator)) {
            SysStaff staff = staffService.getStaffByAccount(creator);
            detachedCriteria.add(Restrictions.eq(InterPurchasModel.CREATOR, staff.getStaffName()));
        } else {
            detachedCriteria.add(Restrictions.eq(InterPurchasModel.CREATOR, systemUser.getStaffName()));
        }
        if (!StringUtil.isEmpty(expectedDeliveryTime)) {
            Date time = DateUtils.convertStringToDate(expectedDeliveryTime, "yyyy-MM-dd");
            detachedCriteria.add(Restrictions.eq(InterPurchasModel.EXPECTEDTIME, time));
        }

    }

    /**
     * <code>search</code>
     * 搜索页面
     * @param request
     * @param response
     * @return
     * @since   2014年5月20日    wangya
     */
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/interPurchas/search");
        return mav;
    }

    /**
     * <code>addNote</code>
     * 添加页面
     * @param request
     * @param response
     * @return
     * @since 2014年7月19日     wangay
     */
    @RequestMapping(value = "/addNote")
    public ModelAndView addNote(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/interPurchas/addNote");
        return mav;
    }

    /**
     * <code>doAddNote</code>
     * 保存更新实体
     * @param request
     * @param response
     * @param interPurchas
     * @return
     * @since   2014年7月5日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/doAddNote")
    public String doAddNote(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_sino_eoss_cuotomer_addform") InterPurchasModel interPurchas) {
        try {

            //得到产品
            List<InterPurchasProductModel> interPurchasProduct = interPurchasProductsDataAnalyst(interPurchas);
            getService().doAddOrders(request, interPurchas, interPurchasProduct);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>saveOrUpdate</code>
     * 跳转到修改页面
     * @param request
     * @param response
     * @return
     * @since 2014年7月3日     wangya
     */
    @RequestMapping(value = "/saveOrUpdate")
    public ModelAndView saveOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/interPurchas/UpdateNote");
        String id = request.getParameter("id");
        InterPurchasModel interPurchasModel = null;
        if (id != null && !"".equals(id)) {
            interPurchasModel = getService().get(Long.parseLong(id));
            //判断是否能修改
            Object nameObj = getService().getInterById(interPurchasModel.getId()).get("NAME_");
            String name = "";
            if (nameObj != null) {
                name = nameObj.toString();
            }
            if (!(interPurchasModel.getPurchasStatus().equals(BusinessOrderContant.NFF_CG) || name.equals("重新提交")) || interPurchasModel.getPurchasStatus().equals(BusinessOrderContant.INTERPURCHAS_OK)) {
                mav = new ModelAndView("business/interPurchas/UpdateNote1");
                return mav;
            }
            mav.addObject("model", interPurchasModel);

        }
        return mav;
    }

    /**
     * <code>doSaveOrUpdate</code>
     * 更新实体
     * @param request
     * @param response
     * @param manageModel
     * @return String
     * @since 2014年7月3日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/doSaveOrUpdate")
    public String doSaveOrUpdate(HttpServletRequest request, HttpServletResponse response, InterPurchasModel interPurchas) {
        try {
            //得到产品
            List<InterPurchasProductModel> interPurchasProduct = interPurchasProductsDataAnalyst(interPurchas);
            //创建者
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            getService().update(interPurchas, interPurchasProduct, systemUser);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>endInterPurchas</code>
     * 放弃提交
     * @param request
     * @param response
     * @param manageModel
     * @return String
     * @since 2014年7月3日    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/endInterPurchas")
    public String endInterPurchas(HttpServletRequest request, HttpServletResponse response, InterPurchasModel interPurchas) {
        try {
            getService().endInterPurchas(request, interPurchas);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>detail</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年5月6日    wangya
     */
    @RequestMapping(value = "/detail")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/interPurchas/detail");

        String id = request.getParameter("id");
        String proId = request.getParameter("procInstId");
        InterPurchasModel purchasModel = new InterPurchasModel();
        if (id != null) {
            purchasModel = getService().get(Long.parseLong(id));
        }
        //参与事项页面
        if (proId != null) {
            purchasModel = getService().findProId(proId);
        }
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(purchasModel.getProcessInstanceId());//审批日志
        Set<String> userSet = processService.getUserIdSetByProcInst(purchasModel.getProcessInstanceId(), ProcessConstants.ROLE_ASSIGNEE);
        String name = null;
        Iterator<String> it = userSet.iterator();
        while (it.hasNext()) {
            String str = it.next();
            name = ((SysStaff) systemUserCache.get(str).getObjectValue()).getStaffName();
        }
        //Map<String, Object> logs = new HashMap<String, Object>();
        mav.addObject("approName", name);
        request.setAttribute("display", purchasModel.getPurchasStatus());
        mav.addObject("proInstLogList", proInstLogList);
        mav.addObject("model", purchasModel);

        return mav;
    }

    /**
     * <code>toAddOrUpdateProductsView</code>
     * 弹出toAddOrUpdateProductsView页面
     * @param request
     * @param response
     * @return ModelAndView
     * @since 2014年7月05日    wangya
     */
    @RequestMapping(value = "/addOrUpdateProductsView")
    public ModelAndView addOrUpdateProductsView(HttpServletRequest request, HttpServletResponse response, InterPurchasProductModel interPurchasProductModel) {
        ModelAndView mav = new ModelAndView("business/interPurchas/addOrUpdateProducts");
        mav.addObject("model", interPurchasProductModel);
        return mav;
    }

    /**
     * <code>interPurchasProductsDataAnalyst</code>
     * 办公设备|数据的整理
     * @param interPurchas
     * @return List<SalesContractProductModel>
     * @since   2014年7月3日    wangya
     */
    private List<InterPurchasProductModel> interPurchasProductsDataAnalyst(InterPurchasModel interPurchas) {
        List<InterPurchasProductModel> interPurchasProduct = new ArrayList<InterPurchasProductModel>();
        long[] productPartners = interPurchas.getProductPartners();
        // String[] productNames = interPurchas.getProductNames();
        int[] quantitys = interPurchas.getQuantitys();
        long[] productNos = interPurchas.getProductNos();
        String[] productTypeName = interPurchas.getProductTypeNames();
        long[] productTypes = interPurchas.getProductTypes();
        String[] productPartnerNames = interPurchas.getProductPartnerNames();
        String[] productName = interPurchas.getProductNames();
        if (quantitys != null) {
            for (int i = 0; i < quantitys.length; i++) {
                InterPurchasProductModel interProduct = new InterPurchasProductModel();
                interProduct.setProductPartner(productPartners[i]);
                interProduct.setQuantity(quantitys[i]);
                //interProduct.setProductName(productNames[i]);
                interProduct.setProductType(productTypes[i]);
                interProduct.setProductTypeName(productTypeName[i]);
                interProduct.setProductPartnerName(productPartnerNames[i]);
                interProduct.setProductNo(productNos[i]);
                interProduct.setProductName(productName[i]);
                interProduct.setInterPurchas(interPurchas);
                interPurchasProduct.add(interProduct);
            }
        }
        return interPurchasProduct;
    }

    /**
     * <code>creatInterPurchasCode</code>
     * 生成编号
     * @param request
     * @param response
     * @return
     * @since 2014年7月5日    wangya
     */
    @RequestMapping(value = "/creatInterPurchasCode")
    @ResponseBody
    public String creatInterPurchasCode(HttpServletRequest request, HttpServletResponse response) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getModel().getClass());
        long startTime = getStartTime();
        long endTime = getEndTime();
        detachedCriteria.add(Restrictions.ge(SalesContractModel.CREATETIME, new Date(startTime)));
        detachedCriteria.add(Restrictions.le(SalesContractModel.CREATETIME, new Date(endTime)));
        List<InterPurchasModel> interPurchasModels = this.getService().findInterPurchasByCriteria(detachedCriteria);
        int serialNum = interPurchasModels.size();
        String contractCode = CodeUtils.creatCode(serialNum);
        return contractCode;
    }

    private long getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    private long getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime().getTime();
    }

    /**
     * <code>managerApprovalDetail</code>
     * 部门经理审批页面
     * @param request
     * @param response
     * @return
     * @since   2014年7月6日    wangya
     */
    @RequestMapping(value = "/interPurchasReview/managerApproval")
    public ModelAndView manageApprovalDetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/interPurchas/interPurchasReview/managerApproval");
        String taskId = request.getParameter("taskId");
        long id = (Long) processService.getVariable(taskId, "interPurshasId");
        InterPurchasModel interPurchas = getService().get(id);
        request.setAttribute("taskId", taskId);
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(interPurchas.getProcessInstanceId());//审批日志
        mav.addObject("proInstLogList", proInstLogList);
        mav.addObject("model", interPurchas);
        return mav;
    }

    /**
     * <code>businessSupervisorDetail</code>
     * 审批页面
     * @param request
     * @param response
     * @return
     * @since   2014年7月6日    wangya
     */
    @RequestMapping(value = "/interPurchasReview/businessSupervisor")
    public ModelAndView businessSupervisorDetail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/interPurchas/interPurchasReview/businessSupervisor");
        String taskId = request.getParameter("taskId");
        long id = (Long) processService.getVariable(taskId, "interPurshasId");
        InterPurchasModel interPurchas = getService().get(id);
        request.setAttribute("taskId", taskId);
        request.setAttribute("mark", "END");
        List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(interPurchas.getProcessInstanceId());
        mav.addObject("proInstLogList", proInstLogList);
        mav.addObject("model", interPurchas);
        return mav;
    }

    /**
     * <code>handleFlow</code>
     * 处理流程
     * @param systemUser 操作人实体
     * @param taskId 任务ID
     * @param isAgree 是否同意
     * @param remark 审批意见
     * @since   2014年7月6日    wangya
     */
    @RequestMapping(value = "/handleFlow")
    public ModelAndView handleFlow(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/interPurchas/manage");
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        String userId = systemUser.getUserName();
        String id = request.getParameter("id");
        InterPurchasModel interPurchas = getService().get(Long.parseLong(id));
        String taskId = request.getParameter("taskId");
        int isAgree = Integer.parseInt(request.getParameter("isAgree"));
        String mark = request.getParameter("mark");
        System.out.println("isagree" + isAgree);
        String remark = request.getParameter("remark");
        if (mark != null) {
            if (mark.equals("END") && isAgree == 1) {
                getService().updateStatus(interPurchas);
            }
        }
        /*   if(isAgree==0){
               getService().getNewWork(interPurchas);
           }*/
        processService.handle(taskId, userId, isAgree, remark, null, null, null);
        return mav;
    }

    /**
     * <code>reviseNote</code>
     * 跳转到从新提交
     * @param request
     * @param response
     * @return
     * @since 2014年5月19日     3unshine
     */
    @RequestMapping(value = "/interPurchasReview/reviseNote")
    public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/interPurchas/interPurchasReview/reviseNote");
        String taskId = request.getParameter("taskId");
        long id = (Long) processService.getVariable(taskId, "interPurshasId");
        InterPurchasModel interPurchasModel = getService().get(id);
        request.setAttribute("taskId", taskId);
        mav.addObject("model", interPurchasModel);
        return mav;
    }

    /**
     * <code>doReviseNote</code>
     * 更新实体
     * @param request
     * @param response
     * @param manageModel
     * @return String
     * @since 2014年5月19日    3unshine
     */
    @ResponseBody
    @RequestMapping(value = "/doReviseNote")
    public String doUpdate(HttpServletRequest request, HttpServletResponse response, InterPurchasModel interPurchasModel) {
        try {
            List<InterPurchasProductModel> interPurchasProductModel = interPurchasProductsDataAnalyst(interPurchasModel);
            //创建者
            String taskId = request.getParameter("taskId");
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            String status = request.getParameter("isSubmit");
            //String prcId = request.getParameter("processInstanceId");
            getService().reviseNote(interPurchasModel, interPurchasProductModel, systemUser, taskId, status);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>remove</code>
     * 删除內采草稿
     * @param request
     * @param response
     * @param id
     * @return
     * @since   2014-07-22    wangya
     */
    @ResponseBody
    @RequestMapping(value = "/remove")
    public String remove(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String ids) {
        try {
            String[] id = ids.substring(0, ids.lastIndexOf(",")).split(",");
            getService().deletes(id);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>printInter</code>
     * 跳转到printInter页面
     * @param request
     * @param response
     * @return
     * @since 2014年7月26日    wangya
     */
    @RequestMapping(value = "/printInter")
    public ModelAndView printInter(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/interPurchas/printInter");
        Long id = Long.parseLong(request.getParameter("id"));
        InterPurchasModel inter = getService().get(id);
        /* List<ProcInstAppr> proInstLogList = processService.findProcInstApprLog(inter.getProcessInstanceId());
         StringBuilder sb = new StringBuilder();
         for(ProcInstAppr appr : proInstLogList){
             sb.append(appr.getUser() +" ");
         }*/

        //申请部门
        String useId = inter.getCreatorId();
        SysStaff use = staffService.get(useId);
        List<SysStaffOrg> org = use.getSysStaffOrgs();
        SysOrgnization o = org.get(0).getSysOrgnization();
        String orgName = o.getOrgFullName();
        mav.addObject("orgName", orgName);
        mav.addObject("model", inter);
        return mav;
    }
}
