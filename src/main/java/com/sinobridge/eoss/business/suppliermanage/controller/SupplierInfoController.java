package com.sinobridge.eoss.business.suppliermanage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.bpm.service.process.ProcessService;
import com.sinobridge.eoss.business.suppliermanage.model.SupplierInfoModel;
import com.sinobridge.eoss.business.suppliermanage.service.SupplierInfoService;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.service.SysStaffService;
import com.sinobridge.systemmanage.util.StringUtil;

/**
 * <code>SupplierInfoController</code>
 *
 * @version  1.0
 * @author  guokemenng
 * @since 1.0  2014骞�鏈�0鏃�
 */
@Controller
@RequestMapping(value = "/business/supplierm/supplierInfo")
public class SupplierInfoController extends DefaultBaseController<SupplierInfoModel, SupplierInfoService> {

    @Autowired
    private ProcessService processService;
    @Autowired
    private SysStaffService sysStaffService;

    /**
     * <code>manage</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014骞�鏈�0鏃�   guokemenng
     */
    @RequestMapping(value = "/manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/supplierm/supplierInfo/manage");
        return mav;
    }

    /**
     * <code>getList</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014骞�鏈�0鏃�   guokemenng
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
     * @since   2014骞�鏈�0鏃�   guokemenng
     */
    public void buildSearch(HttpServletRequest request, DetachedCriteria detachedCriteria) {
        String bizOwner = request.getParameter("bizOwner");
        String supplierType = request.getParameter("supplierType");
        String supplierCode = request.getParameter("supplierCode");
        String shortName = request.getParameter("shortName");
        if (!StringUtil.isEmpty(bizOwner)) {
            detachedCriteria.add(Restrictions.eq(SupplierInfoModel.BIZOWNER, bizOwner));
        }
        if (!StringUtil.isEmpty(supplierType)) {
            detachedCriteria.add(Restrictions.eq(SupplierInfoModel.SUPPLIERTYPE, supplierType));
        }
        if (!StringUtil.isEmpty(supplierCode)) {
            detachedCriteria.add(Restrictions.like(SupplierInfoModel.SUPPLIERCODE, "%" + supplierCode + "%"));
        }
        if (!StringUtil.isEmpty(shortName)) {
            detachedCriteria.add(Restrictions.like(SupplierInfoModel.SHORTNAME, "%" + shortName + "%"));
        }
    }

    /**
     * <code>search</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014骞�鏈�0鏃�   guokemenng
     */
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/supplierm/supplierInfo/search");
        return mav;
    }

    /**
     * <code>getBizOwner</code>
     * 鍟嗗姟璐熻矗浜�
     * @return
     * @since   2014骞�鏈�3鏃�   guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getBizOwner")
    public List<Map<String, Object>> getBizOwner() {
        return getService().getBizOwner();
    }

    /**
     * <code>saveOrUpdate</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014骞�鏈�0鏃�   guokemenng
     */
    @RequestMapping(value = "/saveOrUpdate")
    public ModelAndView saveOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/supplierm/supplierInfo/saveOrUpdate");

        String id = request.getParameter("id");

        Map<String, Object> tableModel = new HashMap<String, Object>(); //琛ㄦ牸鏁版嵁妯″瀷
        tableModel.put("name", "editGridName"); //琛ㄦ牸鍚嶇О
        tableModel.put("defaultAmount", 0);//鏂板缓琛ㄦ牸鏃讹紝榛樿涓�锛屽鏋滄槸淇敼锛屽簲璇ヨ〃鏍兼暟鎹殑鎬昏鏁般�
        List<Map<String, Object>> rowNames = getService().getRowNames(); //琛ㄦ牸涓殑鍒楁暟鎹�

        if (!StringUtil.isEmpty(id)) {
            SupplierInfoModel info = getService().get(Long.parseLong(id));
            List<Map<String, Object>> rowDatas = getService().getRowDatas(info); //琛ㄦ牸鎬荤殑鏁版嵁锛屼竴琛屾暟鎹搴斾竴涓猰ap
            tableModel.put("dataList", rowDatas);
            mav.addObject("model", info);

            Long[] supplierIds = new Long[rowDatas.size()];
            for (int x = 0; x < rowDatas.size(); x++) {
                Map<String, Object> row = rowDatas.get(x);
                Long supplierId = Long.parseLong(row.get("id").toString());
                supplierIds[x] = supplierId;
            }
            //放到ModelAndView中
            mav.addObject("contactIds", supplierIds);
        } else {
            tableModel.put("dataList", new ArrayList<Map<String, Object>>());
        }

        tableModel.put("fieldList", rowNames);
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        try {
            String form = objectMapper.writeValueAsString(tableModel);
            mav.addObject("form", form);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * <code>doSaveOrUpdate</code>
     * 鎵ц淇濆瓨鎿嶄綔
     * @param request
     * @param response
     * @param infoModel
     * @return
     * @since   2014骞�鏈�1鏃�   guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/doSaveOrUpdate")
    public String doSaveOrUpdate(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_eoss_business_supplier") SupplierInfoModel infoModel) {
        //System.out.println("doSaveOrUpdate executes." + infoModel.getSupplierName());
        try {
            //刚刚新建的供应商信息，将其状态state字段设置为0   表示未通过审批
            infoModel.setState("0");
            getService().doSaveOrUpdate(request, infoModel);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }

    /**
     * 保存供应商信息为草稿状态
     * @return
     */
    @RequestMapping(value = "/saveAsDraft")
    public String saveAsDraft(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("_eoss_business_supplier") SupplierInfoModel infoModel) {
        //2 表示该条记录处于草稿状态
        infoModel.setState("2");
        //此时的infoModel模型中已经封装了前端form表单中传递的数据了，只需要手动设置一下当前日期和创建人
        //等信息
        try {
            getService().saveSupplierInfoAsDraft(request, infoModel);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FAIL;
    }

    /**
     * <code>detail</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014骞�鏈�2鏃�   guokemenng
     */
    @RequestMapping(value = "/detail")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/supplierm/supplierInfo/detail");

        String id = request.getParameter("id");
        SupplierInfoModel infoModel = getService().get(Long.parseLong(id));

        mav.addObject("model", infoModel);
        return mav;
    }

    /**
     * <code>detele</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014骞�鏈�2鏃�   guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/detele")
    public String detele(HttpServletRequest request, HttpServletResponse response) {
        try {
            //            getService().doSaveOrUpdate(request, infoModel);
            String ids = request.getParameter("ids");
            getService().deleteIds(ids);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof RuntimeException)
                return "no";
            return FAIL;
        }
    }

    /**
     * <code>checkShortName</code>
     *妫�煡绠�О鏄惁閲嶅
     * @param request
     * @param response
     * @return
     * @since   2015骞�鏈�5鏃�   wangya
     */
    @ResponseBody
    @RequestMapping(value = "/checkShortName")
    public int checkShortName(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        int flat = getService().checkShortName(name);
        return flat;
    }

    /**
     * <code>checkSupplierCode</code>
     *妫�煡缂栧彿鏄惁閲嶅
     * @param request
     * @param response
     * @return
     * @since   2015骞�鏈�5鏃�   wangya
     */
    @ResponseBody
    @RequestMapping(value = "/checkSupplierCode")
    public int checkSupplierCode(HttpServletRequest request, HttpServletResponse response) {
        String supplierCode = request.getParameter("supplierCode");
        int flat = getService().checkSupplierCode(supplierCode);
        return flat;
    }

    /**
     * <code>getListApprove</code>
     * 瀹℃壒鍒楄〃鏁版嵁
     * @param request
     * @param response
     * @return
     * @since   2016骞�鏈�9鏃�   liyx
     */
    @ResponseBody
    @RequestMapping(value = "/getListApprove")
    public Map<String, Object> getListApprove(HttpServletRequest request, HttpServletResponse response) {

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
        detachedCriteria.add(Restrictions.eq("state", "0"));

        //按照创建时间进行降序排序
        detachedCriteria.addOrder(Order.desc("createTime"));
        PaginationSupport paginationSupport = getService().findPageByCriteria(detachedCriteria, this.pageNo * this.pageSize, this.pageSize);

        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    /**
     * <code>supplierApprove</code>
     * 渚涙柟淇℃伅瀹℃壒椤甸潰
     * @param request
     * @param response
     * @return
     * @since   2016骞�鏈�9鏃�   liyx
     */
    @RequestMapping(value = "/supplierApprove")
    public ModelAndView supplierApprove(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("business/supplierm/supplierInfo/supplierApprove");

        String id = request.getParameter("id");
        SupplierInfoModel infoModel = getService().get(Long.parseLong(id));

        mav.addObject("model", infoModel);
        return mav;
    }

    /**
     * <code>doApprove</code>
     * 瀹℃壒
     * @param request
     * @param response
     * @return
     * @since   2016骞�鏈�9鏃�   liyx
     */
    @ResponseBody
    @RequestMapping(value = "/doApprove")
    public String doApprove(HttpServletRequest request, HttpServletResponse response) {
        try {
            String isAgree = request.getParameter("isAgree");
            String id = request.getParameter("id");
            String cause = request.getParameter("cause");

            if (!StringUtil.isEmpty(id)) {
                SupplierInfoModel infoModel = getService().get(Long.parseLong(id));
                SysStaff staff = sysStaffService.get(infoModel.getBizOwner());
                String name = staff.getUserName();
                String supplier = infoModel.getSupplierName();
                if ("1".equals(isAgree)) {
                    infoModel.setState("1"); //审批通过
                    getService().saveOrUpdate(infoModel);
                    processService.sendEmail("系统提醒:供应商审批通过", "供应商名称:" + supplier, null, null, name, null, null);
                } else {
                    processService.sendEmail("系统提醒:供应商审批未通过", "供应商名称:" + supplier + "， <br> 备注：" + cause, null, null, name, null, null);
                    //修改state字段值为2
                    infoModel.setState("2");
                    getService().saveOrUpdate(infoModel);
                }
            }

            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }
}
