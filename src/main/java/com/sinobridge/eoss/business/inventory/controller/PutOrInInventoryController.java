package com.sinobridge.eoss.business.inventory.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Ehcache;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.business.inventory.model.BorrwInventoryProductModel;
import com.sinobridge.eoss.business.inventory.model.InventoryModel;
import com.sinobridge.eoss.business.inventory.model.OutOrInInventoryModel;
import com.sinobridge.eoss.business.inventory.model.PutOrInInventoryModel;
import com.sinobridge.eoss.business.inventory.service.BorrwInventoryProductService;
import com.sinobridge.eoss.business.inventory.service.InventoryService;
import com.sinobridge.eoss.business.inventory.service.OutOrInInventoryService;
import com.sinobridge.eoss.business.inventory.service.PutOrInInventoryService;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.util.Constants;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
import com.sinobridge.systemmanage.vo.SystemUser;

/**
 * 
 * <p>
 * Description: 
 * </p>
 *
 * @author Administrator
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2015年2月13日 上午11:28:30          liyx        1.0         To create
 * </p>
 *
 * @since 
 * @see
 */
@Controller
@RequestMapping(value = "business/putOrInInventory")
public class PutOrInInventoryController extends DefaultBaseController<PutOrInInventoryModel, PutOrInInventoryService> {
    @Autowired
    BorrwInventoryProductService productService;
    @Autowired
    OutOrInInventoryService outOrInInventoryService;
    @Autowired
    InventoryService inventoryService;

    private final String columns[] = { "productNo", "remark", "serialNum", "serailNo" };
    private final String columnNames[] = { "产品编号", "描述", "数量", "成本" };
    private final String columnTypes[] = { "string", "string", "string", "string" };
    private final static Ehcache systemUserCache = Constants.CACHE_MANAGER.getEhcache("systemUser");

    /**
     * <code>manage</code>
     *
     * @param request
     * @param response
     * @return
     * @throws ParseException 
     * @since   2015年2月13日    liyx
     */
    @RequestMapping(value = "/putStorageView")
    @ResponseBody
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ModelAndView mav = new ModelAndView("business/putOrInInventory/inStorageAdd");

        //登录用户
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        StringBuffer serialNoStr = new StringBuffer("SZXQ-");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(new Date());

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PutOrInInventoryModel.class);
        //        long startTime = getStartTime();
        //        long endTime = getEndTime();
        //        detachedCriteria.add(Restrictions.ge("createDate", new Date(startTime)));
        //        detachedCriteria.add(Restrictions.le("createDate", new Date(endTime)));
        //detachedCriteria.add(Restrictions.eq("returnDate", time));
        detachedCriteria.add(Restrictions.eq("returnUser", systemUser.getUserName()));

        PaginationSupport paginationSupport = this.getService().findPageByCriteria(detachedCriteria, 0, 1000);
        //System.out.println(paginationSupport.getItems().size()+"－条数"+new Date(startTime)+">>>"+new Date(endTime));
        int serialNum = paginationSupport.getItems().size();
        serialNum += 1;

        serialNoStr.append(systemUser.getUserName()).append(dateStr).append(serialNum);
        mav.addObject("serialNoStr", serialNoStr);
        mav.addObject("userName", systemUser.getUserName());
        mav.addObject("staffName", systemUser.getStaffName());
        mav.addObject("dateTime", sdf2.format(new Date()));

        return mav;
    }

    /* private long getStartTime() {
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
     }*/

    @RequestMapping(value = "/getDatetable")
    @ResponseBody
    public void getDatetable(HttpServletRequest request) {
        int contactsBySize = 0;
        /**
         * 生成可编辑表格方法
         * @param request
         * @param response
         * @return
         */
        String productId = request.getParameter("productId");
        System.out.println(productId + "=productId");
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName"); //表格名称
        tableModel.put("defaultAmount", contactsBySize);//新建表格时，默认为0，如果是修改，应改表格数据的总行数。

        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据
        for (int i = 0; i < 4; i++) {
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("name", columns[i]); //字段名称，用于保存数据
            dataMap.put("label", columnNames[i]);//字段在页面上的显示名称
            dataMap.put("type", columnTypes[i]); //表格中输入字段的类型，目前只支持string,date
            rowNames.add(dataMap);
        }

        tableModel.put("fieldList", rowNames);
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map

        if (productId != null && !"".equals(productId)) {
            OutOrInInventoryModel inventoryModel = outOrInInventoryService.get(Long.parseLong(productId));
            Set<BorrwInventoryProductModel> product = inventoryModel.getBorrowProductModels();
            for (BorrwInventoryProductModel s : product) { //放入1行数据
                HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                dataMap.put(columns[0], s.getInventoryId().getProductNo());
                dataMap.put(columns[1], s.getInventoryId().getRemark());
                dataMap.put(columns[2], s.getInventoryId().getNumber());
                dataMap.put(columns[3], s.getInventoryId().getCost());
                rowDatas.add(dataMap);
            }
        } else {
            tableModel.put("defaultAmount", 0);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
        }
        tableModel.put("dataList", rowDatas);
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        try {
            String form = objectMapper.writeValueAsString(tableModel);
            //mav.addObject("form",form);
            request.setAttribute("form", form);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步获得借货单中未归还的设备
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getProductData")
    @ResponseBody
    public List<InventoryModel> getProductData(HttpServletRequest request, HttpServletResponse response) {
        String productId = request.getParameter("productId");
        List<InventoryModel> productList = new ArrayList<InventoryModel>();

        if (productId != null && !"".equals(productId)) {
            OutOrInInventoryModel inventoryModel = outOrInInventoryService.get(Long.parseLong(productId));
            Set<BorrwInventoryProductModel> productSet = inventoryModel.getBorrowProductModels();
            for (BorrwInventoryProductModel s : productSet) {
                InventoryModel inventory = s.getInventoryId();
                if (inventory.getState() == 3) {
                    productList.add(inventory);
                }
            }
        }
        return productList;
    }

    @RequestMapping(value = "/getProductData1")
    @ResponseBody
    public List<BorrwInventoryProductModel> getProductData1(HttpServletRequest request, HttpServletResponse response) {
        String productId = request.getParameter("productId");
        List<BorrwInventoryProductModel> productList = new ArrayList<BorrwInventoryProductModel>();

        if (productId != null && !"".equals(productId)) {
            OutOrInInventoryModel inventoryModel = outOrInInventoryService.get(Long.parseLong(productId));
            Set<BorrwInventoryProductModel> productSet = inventoryModel.getBorrowProductModels();

            for (BorrwInventoryProductModel s : productSet) {
                //s.getInventoryId().getProductNo();
                productList.add(s);
            }
        }

        return productList;
    }

    /**
     * 获得未归还的借货单
     * @param request
     * @param response
     * @return
     * @since 2015年2月12日    liyx
     */
    @RequestMapping(value = "/getAllOutOrInInventory")
    @ResponseBody
    public List<Map<String, Object>> getAllOutOrInInventory(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        List<OutOrInInventoryModel> listMap2 = outOrInInventoryService.getAllOutOrInInventory();

        if (listMap2.size() > 0) {
            for (int i = 0; i < listMap2.size(); i++) {
                OutOrInInventoryModel inventory = listMap2.get(i);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", inventory.getId());
                map.put("name", inventory.getSerialNum());
                listMap.add(map);
            }
        }
        return listMap;
    }

    /**
     * 保存归还单
     * @param request
     * @param response
     * @return
     * @since 2015年2月12日    liyx
     */
    @RequestMapping(value = "/doSave")
    @ResponseBody
    public String doSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String returnUser = request.getParameter("returnUser");
        String ids = request.getParameter("ids");
        String returnDate = request.getParameter("returnDate");
        String returnSerialNum = request.getParameter("returnSerialNum");
        String jhSerialNum = request.getParameter("jhSerialNum");
        String remark = request.getParameter("remark");

        //System.out.println("doSave" + ">>>>" + returnUser + "+ jhSerialNum" + jhSerialNum);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            PutOrInInventoryModel putOrInInventory = new PutOrInInventoryModel();
            putOrInInventory.setReturnDate(sdf.parse(returnDate));
            putOrInInventory.setSerialNum(returnSerialNum);
            putOrInInventory.setState((short) 2);
            putOrInInventory.setCreateDate(new Date());
            putOrInInventory.setRemark(remark);
            putOrInInventory.setReturnUser(returnUser);

            if (!StringUtil.isEmpty(returnUser)) {
                String staffName = ((SysStaff) systemUserCache.get(returnUser).getObjectValue()).getStaffName();
                putOrInInventory.setReturnUserName(staffName);
            }
            if (!StringUtil.isEmpty(jhSerialNum)) {
                Long id = Long.parseLong((jhSerialNum.split(",")[0]));
                putOrInInventory.setBorrowingId(id);
            }
            getService().saveOrUpdate(putOrInInventory);

            if (!StringUtil.isEmpty(ids)) {
                String[] ss = ids.split("_");
                Long borrowingId = 0l;
                String[] idStr = jhSerialNum.split(",");

                if (!StringUtil.isEmpty(idStr[0])) {
                    //OutOrInInventoryModel outInventoryModel = outOrInInventoryService.get(Long.parseLong(idStr[0]));
                    //if (outInventoryModel != null) {
                    //borrowingId = outInventoryModel.getId();
                    borrowingId = Long.parseLong(idStr[0]);
                    List<BorrwInventoryProductModel> lists1 = new ArrayList<BorrwInventoryProductModel>();
                    //**********此处可优化(使用sql直接更新数量)，数据量不大，暂时这样**********//
                    for (int i = 0; i < ss.length; i++) {
                        if (!StringUtil.isEmpty(ss[i])) {
                            Long inventoryId = Long.parseLong(ss[i]);
                            BorrwInventoryProductModel product = productService.getList(borrowingId, inventoryId);
                            product.setPutOrInInventoryModel(putOrInInventory);
                            productService.saveOrUpdate(product);
                            //lists1.add(product);
                        }
                    }
                    //productService.saveOrUpdate(lists1);
                    //}
                }

                //**********此处可优化(使用sql直接更新数量)，数据量不大，暂时这样**********//
                //List<InventoryModel> lists = new ArrayList<InventoryModel>();
                for (int i = 0; i < ss.length; i++) {
                    if (!StringUtil.isEmpty(ss[i])) {
                        Long id = Long.parseLong(ss[i]);
                        InventoryModel p = inventoryService.get(id);
                        p.setState((short) 1);

                        inventoryService.saveOrUpdate(p);
                        //lists.add(p);
                    }
                }
                //inventoryService.saveOrUpdate(lists);
            }
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    /**
     * <code>detail</code>
     * 借货单详情
     * @param request
     * @param response
     * @return
     * @since   2015年3月6日    liyx
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        ModelAndView mav = new ModelAndView("business/putOrInInventory/inStorageDetail");
        if (!StringUtil.isEmpty(id)) {
            PutOrInInventoryModel putOrInInventoryModel = this.getService().get(Long.parseLong(id));
            /*Set<BorrwInventoryProductModel> productSet = putOrInInventoryModel.getBorrowProductModels();
            List<InventoryModel> productList = new ArrayList<InventoryModel>();
            for (BorrwInventoryProductModel s : productSet) {
                InventoryModel inventory = s.getInventoryId();
                productList.add(inventory);
            }*/

            List<Map<String, Object>> productList = null;
            List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
            if (!StringUtil.isEmpty(id)) {
                productList = outOrInInventoryService.getProductsDetail(0, Integer.parseInt(id));
                if (productList.size() > 0) {
                    for (int i = 0; i < productList.size(); i++) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("id", productList.get(i).get("id"));
                        map.put("partner", productList.get(i).get("Partner"));
                        map.put("productNo", productList.get(i).get("ProductNo"));
                        map.put("quantity", 1);
                        map.put("status", productList.get(i).get("State"));
                        map.put("rent", productList.get(i).get("Rent"));
                        //map.put("borrowingId", productList.get(i).get("borrowingId"));
                        listMap.add(map);
                    }
                }
            }

            mav.addObject("inModel", putOrInInventoryModel);
            mav.addObject("productList", productList);
        }
        return mav;
    }

}
