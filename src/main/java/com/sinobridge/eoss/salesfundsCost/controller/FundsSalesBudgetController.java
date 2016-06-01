package com.sinobridge.eoss.salesfundsCost.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.base.core.utils.JacksonJsonMapper;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.base.customermanage.model.CustomerInfoModel;
import com.sinobridge.eoss.base.customermanage.service.CustomerInfoService;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.eoss.sales.invoice.model.SalesInvoicePlanModel;
import com.sinobridge.eoss.salesfundsCost.model.SalesBudgetFunds;
import com.sinobridge.eoss.salesfundsCost.model.SalesBudgetLog;
import com.sinobridge.eoss.salesfundsCost.service.FundsSalesBudgetService;
import com.sinobridge.eoss.salesfundsCost.utils.DateUtils;
import com.sinobridge.systemmanage.service.SysStaffService;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.vo.SystemUser;

@Controller
@RequestMapping(value = "/sales/funds/budgetFunds")
public class FundsSalesBudgetController extends DefaultBaseController<SalesBudgetFunds, FundsSalesBudgetService> {
    @Autowired
    private SalesContractService salesContractService;
    @Autowired
    private SysStaffService staffService;
    @Autowired
    private CustomerInfoService customerInfoService;

    //仅有日期的格式
    String DatePattern = "yyyy-MM-dd";
    private final String columns1[] = { "planedReceiveAmount", "planedReceiveDate", "payCondition" };
    private final String columnNames1[] = { "计划收款金额", "计划收款时间", "收款条件" };
    private final String columnTypes1[] = { "string", "datetime", "string" };
    private final String columns[] = { "planedInvoiceAmount", "planedInvoiceDate", "planCondition", "invoiceType", "id" };
    private final String columnNames[] = { "计划发票金额", "计划发票时间", "说明", "发票类型", "id" };
    private final String columnTypes[] = { "string", "datetime", "string", "select", "hidden" };
    private final String columnWidths[] = { "100", "130", "180", "200", "10" };
    private final String columnUrl[] = { "", "", "", "", "" };

    /**
     * 合同预算主界面
     * @param request
     * @param respsonse
     * @return
     */
    @RequestMapping(value = "/manageBug")
    public ModelAndView findAllSalesBudgetFunds(HttpServletRequest request, HttpServletResponse respsonse) {
        ModelAndView mav = new ModelAndView("sales/funds/budgetFunds/manageBug");
        /*SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        List<Map<String, Object>> sales = getService().getSalesName(systemUser);
        request.setAttribute("sales", sales);*/
        return mav;
    }

    /**预测统计
     * @param request
     * @param respsonse
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getStatist")
    public Map<String, Object> getStatist(HttpServletRequest request, HttpServletResponse respsonse) {
        Map<String, Object> map = new HashMap<String, Object>();
        String timeSpace = request.getParameter("time");
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        map = getService().getStatist(timeSpace, systemUser);
        return map;
    }

    /**预测统计:自定义
     * @param request
     * @param respsonse
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSelfChoice")
    public Map<String, Object> getSelfChoice(HttpServletRequest request, HttpServletResponse respsonse) {
        Map<String, Object> map = new HashMap<String, Object>();
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        map = getService().getSelfChoice(start, end, systemUser);
        return map;
    }

    /**
     * <code>getAllFundsSalesBudget</code>
     * 得到表格列表
     * @param request
     * @param response
     * @return
     * @since   2016年1月27日    zhangshudi
     */
    @ResponseBody
    @RequestMapping(value = "/getAll")
    public Map<String, Object> getAll(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String contractName = request.getParameter("contractName");
        String supplier = request.getParameter("customerInfo");
        String hql = "";
        if (contractName != null && !"".equals(contractName)) {
            hql += " and s.contractName LIKE '%" + contractName + "%'";
        }
        if (supplier != null && !"".equals(supplier)) {
            hql += " and s.CustomerId='" + supplier + "'";
        }
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        hql += " and s.Creator='" + systemUser.getUserId() + "' ";
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo) - 1;
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        //  Map<String, Object> searchMap = new HashMap<String, Object>();
        PaginationSupport paginationSupport = getService().getModelPage(hql, this.pageNo * this.pageSize, this.pageSize);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    /**
     * <code>getHisReceive</code>
     * 得到预测回款历史记录
     * @param request
     * @param response
     * @return
     * @since   2016年3月4日   wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getHisReceive")
    public Map<String, Object> getHisReceive(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String contractName = request.getParameter("contractName");
        String hql = "";
        if (contractName != null && !"".equals(contractName)) {
            hql += " and s.contractName LIKE '%" + contractName + "%'";
        }
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        hql += " and s.Creator='" + systemUser.getUserId() + "' ";
        try {
            hql += " and f.budgetDate>'" + DateUtils.convertDateToString(new Date(), "yyyy-MM-dd") + "' ";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo) - 1;
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        PaginationSupport paginationSupport = getService().getHisReceive(hql, this.pageNo * this.pageSize, this.pageSize);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    /**
     * <code>getHisInvoice</code>
     * 得到预测发票历史记录
     * @param request
     * @param response
     * @return
     * @since   2016年3月4日   wangya
     */
    @ResponseBody
    @RequestMapping(value = "/getHisInvoice")
    public Map<String, Object> getHisInvoice(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String contractName = request.getParameter("contractName");
        String hql = "";
        if (contractName != null && !"".equals(contractName)) {
            hql += " and s.contractName LIKE '%" + contractName + "%'";
        }
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        hql += " and s.Creator='" + systemUser.getUserId() + "' ";
        /*        try {
                    hql += " and f.budgetDate>'" + DateUtils.convertDateToString(new Date(), "yyyy-MM-dd") + "' ";
                } catch (ParseException e) {
                    e.printStackTrace();
                }
        */
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo) - 1;
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        PaginationSupport paginationSupport = getService().getHisInvoice(hql, this.pageNo * this.pageSize, this.pageSize);
        map.put("rows", paginationSupport.getItems());
        map.put("total", paginationSupport.getTotalCount());
        map.put("page", pageNo);
        return map;
    }

    /**
     * 合同预算搜索
     * @param request
     * @param respsonse
     * @return
     */
    @RequestMapping(value = "/searchBudgetFunds")
    public ModelAndView searchBudgetFunds(HttpServletRequest request, HttpServletResponse respsonse) {
        ModelAndView mav = new ModelAndView("sales/funds/budgetFunds/searchBudgetFunds");
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        List<Map<String, Object>> sales = getService().getSalesName(systemUser);
        request.setAttribute("sales", sales);
        return mav;
    }

    /**
     * 合同回款预算添加
     * @param request
     * @param respsonse
     * @return
     */
    @RequestMapping(value = "/addRecieveDialog")
    public ModelAndView addRecieveDialog(HttpServletRequest request, HttpServletResponse respsonse) {
        ModelAndView mav = new ModelAndView("sales/funds/budgetFunds/addRecieveDialog");
        String saleId = request.getParameter("salesId");
        if (saleId != null) {
            request.setAttribute("saleId", saleId);
        }
        Date time = new Date();
        String fundsId = request.getParameter("fundid");
        if (fundsId != null) {
            SalesBudgetFunds funds = getService().get(Long.parseLong(fundsId));
            request.setAttribute("funds", funds);
            List<SalesBudgetLog> logs = getService().getSalesLogs(funds.getId());
            request.setAttribute("logs", logs);
            int flat = 0;
            try {
                flat = DateUtils.timeCompare(DateUtils.convertDateToString(time, "yyyy-MM-dd"), DateUtils.convertDateToString(funds.getBudgetDate(), "yyyy-MM-dd"));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (flat >= 0) {//判断是否已过时，过时的预测信息只能查看，无法修改
                mav = new ModelAndView("sales/funds/budgetFunds/detailRecieveDialog");
            }
        }
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        List<Map<String, Object>> sales = getService().getSalesName(systemUser);
        request.setAttribute("sales", sales);
        try {
            String severDay = DateUtils.someDayAgo(time, 1);
            request.setAttribute("startTime", severDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mav;
    }

    /**
     * 合同发票预算添加
     * @param request
     * @param respsonse
     * @return
     * 2016-3-3
     */
    @RequestMapping(value = "/addInvoiceDialog")
    public ModelAndView addInvoiceDialog(HttpServletRequest request, HttpServletResponse respsonse) {
        ModelAndView mav = new ModelAndView("sales/funds/budgetFunds/addInvoiceDialog");
        String saleId = request.getParameter("salesId");
        if (saleId != null) {
            request.setAttribute("saleId", saleId);
            String formDataJson = "";
            formDataJson = tableGridDataInvoice(saleId, "update");//所有没提交过的发票信息
            request.setAttribute("form3", formDataJson);
            List<SalesBudgetLog> logs = getService().getInvoiecLogBySaleID(saleId);//此合同相关所有发票日志
            request.setAttribute("logs", logs);
        }
        Date time = new Date();
        /* String fundsId = request.getParameter("fundid");
        if (fundsId != null) {
             SalesBudgetFunds funds = getService().get(Long.parseLong(fundsId));
             request.setAttribute("funds", funds);
             List<SalesBudgetLog> logs = getService().getSalesLogs(funds.getId());
             request.setAttribute("logs", logs);
             int flat = 0;
             try {
                 flat = DateUtils.timeCompare(DateUtils.convertDateToString(time, "yyyy-MM-dd"), DateUtils.convertDateToString(funds.getBudgetDate(), "yyyy-MM-dd"));
             } catch (ParseException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
             if (flat >= 0) {//判断是否已过时，过时的预测信息只能查看，无法修改
                 mav = new ModelAndView("sales/funds/budgetFunds/detailInvoiceDialog");
             }
         }*/
        try {
            String severDay = DateUtils.someDayAgo(time, 1);
            request.setAttribute("startTime", severDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * @param saleId
     * @param string
     * @return
     */
    private String tableGridDataInvoice(String saleId, String showType) {
        String formDataJson = "";
        Map<String, Object> tableModel = new HashMap<String, Object>(); //表格数据模型
        tableModel.put("name", "editGridName3"); //表格名称
        List<HashMap<String, Object>> rowNames = new ArrayList<HashMap<String, Object>>(); //表格中的列数据

        for (int i = 0; i < columns.length; i++) {
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("name", columns[i]); //字段名称，用于保存数据
            dataMap.put("label", columnNames[i]);//字段在页面上的显示名称
            dataMap.put("type", columnTypes[i]); //表格中输入字段的类型，目前只支持string,date
            dataMap.put("width", columnWidths[i]); //表格中输入字段的类型，目前只支持string,date
            dataMap.put("url", columnUrl[i]); //表格中输入字段的类型，目前只支持string,date
            if (saleId != null && showType.equals("detail"))
                dataMap.put("boolWrite", false); //表格中输入字段的类型，目前只支持string,date
            rowNames.add(dataMap);
        }

        tableModel.put("fieldList", rowNames);
        List<HashMap<String, Object>> rowDatas = new ArrayList<HashMap<String, Object>>(); //表格总的数据，一行数据对应一个map
        if (saleId != null) {
            List<SalesBudgetFunds> invoices = getService().getFundsInvoiceBySaleId(saleId);
            int rows = invoices.size();
            tableModel.put("defaultAmount", rows);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
            for (SalesBudgetFunds s : invoices) { //放入2行数据
                HashMap<String, Object> dataMap = new HashMap<String, Object>(); //因为先前在rowNames中定义表格有3列
                if (showType.equals("update")) {
                    dataMap.put(columns[0], s.getBudgetInvoice());
                } else {
                    dataMap.put(columns[0], "￥" + getShowNumber(s.getBudgetInvoice()));
                }
                dataMap.put(columns[1], s.getBudgetDate());
                dataMap.put(columns[2], s.getRemark());
                dataMap.put(columns[3], s.getInvoiceType());
                dataMap.put(columns[4], s.getId());
                rowDatas.add(dataMap);
            }
        } else {
            tableModel.put("defaultAmount", 0);//新建表格时，默认为0，如果是修改，应该表格数据的总行数。
        }

        tableModel.put("dataList", rowDatas);
        ObjectMapper objectMapper = JacksonJsonMapper.getInstance();
        try {
            formDataJson = objectMapper.writeValueAsString(tableModel);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formDataJson;
    }

    public static String getShowNumber(BigDecimal number) {

        String sb = "";
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        dfs.setMonetaryDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("###,###.##", dfs);
        sb = df.format(number);
        if (!sb.contains(".")) {
            sb = sb + ".00";
        }
        return sb.toString();
    }

    /**保存回款预测
     * @param request
     * @param respsonse
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/doRecieve")
    public Map<String, Object> doRecieve(HttpServletRequest request, HttpServletResponse respsonse) {
        Map<String, Object> msg = new HashMap<String, Object>();
        String id = request.getParameter("id");
        String fundsId = request.getParameter("fundsid");
        String recieveAmount = request.getParameter("recieveAmount");
        String recieveTime = request.getParameter("recieveTime");
        msg = getService().doRecieve(id, recieveAmount, recieveTime, fundsId);
        return msg;
    }

    /**保存发票预测
     * @param request
     * @param respsonse
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/doInvoice")
    public Map<String, Object> doInvoice(HttpServletRequest request, HttpServletResponse respsonse) {
        Map<String, Object> msg = new HashMap<String, Object>();
        String id = request.getParameter("saleId");
        String tableData3 = request.getParameter("tableData3");
        String flat = request.getParameter("flat");
        List<SalesInvoicePlanModel> invoice = new ArrayList<SalesInvoicePlanModel>();
        invoice = getService().doInvoice(id, tableData3);

        if (flat != null) {
            //创建者
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            //String choise = request.getParameter("choise");
            String rs = getService().doSaveInvoice(id, invoice, systemUser);
            msg.put("date", rs);
        }
        return msg;
    }

    /**
     * 合同下拉框名称
     * @param request
     * @param respsonse
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/searchContract")
    public List<Map<String, String>> searchContract(HttpServletRequest request, HttpServletResponse respsonse) {
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        List<SalesContractModel> salesContract = getService().findSalesContractByCreator(systemUser.getStaffName());
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (salesContract != null) {
            for (SalesContractModel salesContractModel : salesContract) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", salesContractModel.getId().toString());
                map.put("name", salesContractModel.getContractName());
                list.add(map);
            }
        }
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * <code>getSalesContractByCode</code>
     * 根据Code 查合同Name
     * @param request
     * @param response
     * @return
     * @since   2014年12月25日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getSalesContractByCode")
    public Map<String, Object> getSalesContractByCode(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Map<String, Object> map = new HashMap<String, Object>();
        SalesContractModel sales = salesContractService.get(Long.parseLong(id));
        map.put("name", sales.getContractName());
        return map;
    }

    /**
     * <code>getSalesContractByName</code>
     * 合同列表
     * @param request
     * @param response
     * @return
     * @since   2014年12月25日    guokemenng
     */
    @ResponseBody
    @RequestMapping(value = "/getSalesContract")
    public Map<String, Object> getSalesContractByName(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name");
        Map<String, Object> map = new HashMap<String, Object>();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String sql = "";
        if (pageNo != null) {
            this.pageNo = Integer.parseInt(pageNo);
        }
        if (pageSize != null) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
        sql += " and s.Creator='" + systemUser.getUserId() + "' ";
        if (name != null) {
            sql += " and (s.contractName like '%" + name + "%' or i.Name LIKE '%" + name + "%') ";
        }
        PaginationSupport paginationSupport = getService().findSalesTotalInfo(sql, this.pageNo * this.pageSize, this.pageSize);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> salesList = (List<Map<String, Object>>) paginationSupport.getItems();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> s : salesList) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("id", s.get("id"));
            m.put("code", s.get("ContractCode"));
            m.put("name", s.get("ContractName"));
            mapList.add(m);
        }

        map.put("datas", mapList);
        map.put("pageNo", this.pageNo);
        map.put("pageSize", this.pageSize);
        map.put("totalCount", paginationSupport.getTotalCount());
        return map;
    }

    /**查询合同总体信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalesInfo")
    public Map<String, Object> getSalesInfo(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String saleId = request.getParameter("id");
        SalesContractModel sales = salesContractService.get(Long.parseLong(saleId));
        CustomerInfoModel customer = customerInfoService.get(sales.getCustomerId());
        map.put("contractAmount", sales.getContractAmount());
        map.put("contractName", sales.getContractName());
        map.put("customer", customer.getShortName());
        String receive = getService().getReceiveAmount(saleId);
        map.put("receive", receive);
        String invoice = getService().getInvoiceAmount(saleId);
        map.put("invoice", invoice);
        String invoicett = getService().getInvoiceAmounttt(saleId);
        map.put("unAmount", invoicett);
        List<Map<String, Object>> invoiceList = getService().getSalesInvoice(saleId);
        map.put("invoiceList", invoiceList);
        map.put("recieveList", sales.getSalesContractReceivePlans());
        return map;
    }

    /**
     * 合同预算删除
     * @param request
     * @param respsonse
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delBudget")
    public String delBudget(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        try {
            getService().delBudget(id);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return FAIL;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkTime")
    public String checkTime(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        SalesBudgetFunds funds = getService().get(Long.parseLong(id));
        funds.setFlat("1");
        try {
            getService().saveOrUpdate(funds);
            return "success";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }

    }
}
