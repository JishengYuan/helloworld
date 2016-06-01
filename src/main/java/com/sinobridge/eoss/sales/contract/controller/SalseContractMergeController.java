/*
 * FileName: SalseContractMergeController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.exception.SinoRuntimeException;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.model.SalseContractMergeModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.contract.service.SalseContractMergeService;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.vo.SystemUser;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author guo_kemeng
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年3月23日 下午2:03:19          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/sales/contractMerge")
public class SalseContractMergeController extends DefaultBaseController<SalseContractMergeModel, SalseContractMergeService> {

    @Autowired
    SalesContractService contractService;

    /**
     * <code>search</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2015年3月23日    guokemenng
     */
    @RequestMapping("/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractMerge/manage");
        return mav;
    }

    /**
     * <code>getList</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2015年3月23日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getList")
    public Map<String, Object> getList(HttpServletRequest request, HttpServletResponse response) {

        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);

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
        //        buildSearch(request, detachedCriteria);

        if (systemUser.getUserName().equals("zhangjq")) {
            detachedCriteria.add(Restrictions.eq("state", (short) 1));
        } else {
            detachedCriteria.add(Restrictions.eq("empoyeeId", systemUser.getUserId()));
        }

        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);

        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    /**
     * <code>saveOrUpdate</code>
     * 新增修改页面
     * @param request
     * @param response
     * @return
     * @since   2015年3月24日    guokemenng
     */
    @RequestMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractMerge/saveOrUpdate");
        return mav;
    }

    /**
     * <code>getContractMergeList</code>
     * 得到可以合并的合同列表
     * @param request
     * @param response
     * @return
     * @since   2015年3月23日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getContractMergeList")
    public Map<String, Object> getContractMergeList(HttpServletRequest request, HttpServletResponse response) {

        //当前登录用户
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);

        String staffId = systemUser.getUserId();
        String customerId = request.getParameter("customerId");
        String name = request.getParameter("name");
        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo);
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        PaginationSupport paginationSupport = contractService.getContractMergeList(staffId, name, customerId, Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        map.put("datas", paginationSupport.getItems());
        map.put("pageNo", this.pageNo);
        map.put("pageSize", this.pageSize);
        map.put("totalCount", paginationSupport.getTotalCount());
        return map;
    }

    /**
     * <code>doSave</code>
     * 增加操作
     * @param request
     * @param response
     * @return
     * @since   2015年3月25日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/doSave")
    public String doSave(HttpServletRequest request, HttpServletResponse response) {
        try {
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);

            String contractId = request.getParameter("contractId");
            String contractIds = request.getParameter("contractIds");
            String remark = request.getParameter("remark");

            SalseContractMergeModel mergeModel = new SalseContractMergeModel();
            mergeModel.setApplyTime(new Date());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String str = sdf.format(new Date());
            mergeModel.setName(str + systemUser.getStaffName() + "申请合同合并");
            mergeModel.setContractIds(contractIds);
            mergeModel.setRemark(remark);
            mergeModel.setEmpoyeeId(systemUser.getUserId());
            mergeModel.setState((short) 1);
            mergeModel.setContractId(Long.parseLong(contractId));

            getService().saveOrUpdate(mergeModel);
            //更新所选合同状态
            contractService.updateSalesState(contractIds, "HB");

            return SUCCESS;
        } catch (SinoRuntimeException e) {
            e.printStackTrace();
            return FAIL;
        }
    }

    /**
     * <code>detail</code>
     * 合同详情页
     * @param request
     * @param response
     * @return
     * @since   2015年3月25日    guokemenng
     */
    @RequestMapping("/detail")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/contractMerge/detail");

        String id = request.getParameter("id");
        SalseContractMergeModel mergeModel = getService().get(Long.parseLong(id));
        mav.addObject("model", mergeModel);

        List<SalesContractModel> salesList = contractService.getSalesByIds(mergeModel.getContractIds());
        for (SalesContractModel sales : salesList) {
            if (mergeModel.getContractId().equals(sales.getId())) {
                SalesContractModel contract = getService().getSalesContractByCode(sales.getContractCode() + "_HB");
                mav.addObject("contract", contract);
            }
        }

        mav.addObject("sales", salesList);
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        mav.addObject("systemUser", systemUser);

        return mav;
    }

    /**
     * <code>delete</code>
     * 删除
     * @param request
     * @param response
     * @return
     * @since   2015年3月25日    guokemenng
     */
    @ResponseBody
    @RequestMapping("/delete")
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("id");

            getService().delete(Long.parseLong(id));
            return SUCCESS;
        } catch (SinoRuntimeException e) {
            // TODO: handle exception
            e.printStackTrace();
            return FAIL;
        }
    }

    /**
     * <code>appproveSubmit</code>
     * 张洁卿审批
     * @param request
     * @param response
     * @return
     * @since   2015年3月25日    guokemenng
     */
    @ResponseBody
    @RequestMapping("/appproveSubmit")
    public String appproveSubmit(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("id");
            String state = request.getParameter("state");

            SalseContractMergeModel mergeModel = getService().get(Long.parseLong(id));
            mergeModel.setState(Short.parseShort(state));
            Long contractId = mergeModel.getContractId();
            String contractIds = mergeModel.getContractIds();

            //合并操作
            if ("2".equals(state)) {
                getService().mergeContracts(contractId, contractIds);
            } else {
                //审批不通过，更新所选合同状态，还原为通过审批
                contractService.updateSalesState(contractIds, "TGSH");
            }
            getService().update(mergeModel);
            return SUCCESS;
        } catch (SinoRuntimeException e) {
            // TODO: handle exception
            e.printStackTrace();
            return FAIL;
        }
    }

}
