/*
 * FileName: PresaleContractService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.exception.SinoRuntimeException;
import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.PaginationSupport;
import com.sinobridge.eoss.sales.contract.dao.PresaleContractDao;
import com.sinobridge.eoss.sales.contract.dao.PresalesContractTypeDao;
import com.sinobridge.eoss.sales.contract.model.PresaleContractModel;
import com.sinobridge.eoss.sales.contract.model.PresalesContractTypeModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.utils.DateUtils;
import com.sinobridge.systemmanage.util.StringUtil;

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
 * 2015年11月9日 下午3:59:48          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class PresaleContractService extends DefaultBaseService<PresaleContractModel,PresaleContractDao>{

    
    @Autowired
    PresalesContractTypeDao presalesContractTypeDao;
    /**
     * <code>findPageBySearchMap</code>
     * 通过查询条件 得到主页面列表
     * @param searchMap
     * @param startIndex
     * @param pageSize
     * @return
     * @since   2015年11月9日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public PaginationSupport findPageBySearchMap(HashMap<String, Object> searchMap, int startIndex, Integer pageSize) {
        int start = 0;
        Object[] values = new Object[searchMap.size()];
        Object creatorString = searchMap.get("creator");
        Object contractAmountString = searchMap.get("contractAmount");
        Object contractAmountStringb = searchMap.get("contractAmountb");
        Object vendor = searchMap.get(PresaleContractModel.VENDOR);
        Object customerName = searchMap.get(PresaleContractModel.CUSTOMERNAME);
        Object startTime = searchMap.get("startTime");
        Object endTime = searchMap.get("endTime");
        Object proTechnology = searchMap.get(PresaleContractModel.PROTECHNOLOGY);
        Object customerType = searchMap.get(PresaleContractModel.CUSTOMERTYPE);
        Object contractCode = searchMap.get(PresaleContractModel.CONTRACTCODE);
        Object projectSite = searchMap.get(PresaleContractModel.PROJECTSITE);
        Object preContractCode = searchMap.get(PresaleContractModel.PRECONTRACTCODE);
        

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.`id`,c.`CreatorName`,c.`ContractCode`,p.`customerType`,cust.`Name`,c.`ContractName`,p.`vendor`,p.`proTechnology`,p.`projectSite`,c.`ContractAmount`,p.`preContractCode` ");
        sb.append("FROM `sales_contract` c LEFT JOIN `sales_precontract` p ");
        sb.append("ON c.`ContractCode` = p.`contractCode` LEFT JOIN customer_info cust ON c.CustomerId=cust.id ");
        sb.append("where 0=0 AND c.`ContractState` = 'TGSH' ");
        if (creatorString != null) {
            Long creator = Long.parseLong(creatorString.toString());
            values[start++] = creator;
            sb.append("and c.`Creator` = ? ");
        }
        if (contractAmountString != null) {
            float contractAmount = Float.parseFloat(contractAmountString + "0000");
            values[start++] = contractAmount;
            sb.append("and c." + SalesContractModel.CONTRACTAMOUNT + ">=? ");
        }
        if (contractAmountStringb != null) {
            float contractAmount = Float.parseFloat(contractAmountStringb + "0000");
            values[start++] = contractAmount;
            sb.append("and c." + SalesContractModel.CONTRACTAMOUNT + "<=? ");
        }
        if (vendor != null) {
            values[start++] = vendor.toString();
            sb.append("and p." + PresaleContractModel.VENDOR + " like ? ");
        }
        if (customerName != null) {
            values[start++] = customerName.toString();
            sb.append("and cust.`Name` like ? ");
        }
        if (startTime != null) {
            values[start++] = startTime.toString();
            sb.append("and c.SalesStartDate >=? ");
        }
        if (endTime != null) {
            values[start++] = endTime.toString();
            sb.append("and c.SalesStartDate <=? ");
        }
        if (proTechnology != null) {
            values[start++] = proTechnology.toString();
            sb.append("and p.`proTechnology` like ? ");
        }
        if (customerType != null) {
            values[start++] = customerType.toString();
            sb.append("and p.`customerType`=? ");
        }
        if (contractCode != null) {
            values[start++] = contractCode.toString();
            sb.append("and c.`ContractCode`=? ");
        }
        if (projectSite != null) {
            values[start++] = projectSite.toString();
            sb.append("and p.`projectSite` like ? ");
        }
        if (preContractCode != null) {
            values[start++] = preContractCode.toString();
            sb.append("and p.`preContractCode`=? ");
        }
        
        int totals = getTotalCount(sb.toString(), values);
        sb.append("order By c.id DESC");

        Query query =  getDao().createSQLQuery(sb.toString(), values);
        List<Object> items = query.setFirstResult(startIndex).setMaxResults(pageSize).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        PaginationSupport ps = new PaginationSupport(items, totals, pageSize, startIndex);
//        ps.setItems(items);
        return ps;
    }
    
    /**
     * <code>findTotallAmount</code>
     * 得到查询后所有合同的总金额
     * @param searchMap
     * @return
     * @since   2015年11月10日    guokemenng
     */
    public String findTotallAmount(HashMap<String, Object> searchMap) {
        int start = 0;
        Object[] values = new Object[searchMap.size()];
        Object creatorString = searchMap.get("creator");
        Object contractAmountString = searchMap.get("contractAmount");
        Object contractAmountStringb = searchMap.get("contractAmountb");
        Object vendor = searchMap.get(PresaleContractModel.VENDOR);
        Object customerName = searchMap.get(PresaleContractModel.CUSTOMERNAME);
        Object startTime = searchMap.get("startTime");
        Object endTime = searchMap.get("endTime");
        Object proTechnology = searchMap.get(PresaleContractModel.PROTECHNOLOGY);
        Object customerType = searchMap.get(PresaleContractModel.CUSTOMERTYPE);
        Object contractCode = searchMap.get(PresaleContractModel.CONTRACTCODE);
        Object projectSite = searchMap.get(PresaleContractModel.PROJECTSITE);
        Object preContractCode = searchMap.get(PresaleContractModel.PRECONTRACTCODE);
        

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT SUM(c.ContractAmount) amount ");
        sb.append("FROM `sales_contract` c LEFT JOIN `sales_precontract` p ");
        sb.append("ON c.`ContractCode` = p.`contractCode` LEFT JOIN customer_info cust ON c.CustomerId=cust.id ");
        sb.append("where 0=0 AND c.`ContractState` = 'TGSH' ");
        if (creatorString != null) {
            Long creator = Long.parseLong(creatorString.toString());
            values[start++] = creator;
            sb.append("and c.`Creator` = ? ");
        }
        if (contractAmountString != null) {
            float contractAmount = Float.parseFloat(contractAmountString + "0000");
            values[start++] = contractAmount;
            sb.append("and c." + SalesContractModel.CONTRACTAMOUNT + ">=? ");
        }
        if (contractAmountStringb != null) {
            float contractAmount = Float.parseFloat(contractAmountStringb + "0000");
            values[start++] = contractAmount;
            sb.append("and c." + SalesContractModel.CONTRACTAMOUNT + "<=? ");
        }
        if (vendor != null) {
            values[start++] = vendor.toString();
            sb.append("and p." + PresaleContractModel.VENDOR + "=? ");
        }
        if (customerName != null) {
            values[start++] = customerName.toString();
            sb.append("and cust.`Name` like ? ");
        }
        if (startTime != null) {
            values[start++] = startTime.toString();
            sb.append("and c.SalesStartDate >=? ");
        }
        if (endTime != null) {
            values[start++] = endTime.toString();
            sb.append("and c.SalesStartDate <=? ");
        }
        if (proTechnology != null) {
            values[start++] = proTechnology.toString();
            sb.append("and p.`proTechnology` = ? ");
        }
        if (customerType != null) {
            values[start++] = customerType.toString();
            sb.append("and p.`customerType`=? ");
        }
        if (contractCode != null) {
            values[start++] = contractCode.toString();
            sb.append("and c.`ContractCode`=? ");
        }
        if (projectSite != null) {
            values[start++] = projectSite.toString();
            sb.append("and p.`projectSite` like ? ");
        }
        if (preContractCode != null) {
            values[start++] = preContractCode.toString();
            sb.append("and p.`preContractCode`=? ");
        }
        
        Query query = getDao().createSQLQuery(sb.toString(), values);
        BigDecimal amount = (BigDecimal) query.uniqueResult();
        String totall = "0";
        if (amount != null) {
            totall = amount.toString();
        }
        return totall;
    }
    
    
    /**
     * <code>getTotalCount</code>
     * 得到总条数
     * @param sql
     * @param params
     * @return
     * @since   2014年11月24日    guokemenng
     */
    @SuppressWarnings("rawtypes")
    public Integer getTotalCount(String sql, Object[] params) {
        String countQueryString = " select COUNT(*) from (" + sql + ") t";
        List countlist = getDao().createSQLQuery(countQueryString, params).list();
        return Integer.parseInt(countlist.get(0).toString());
    }
    
    /**
     * <code>readExcel</code>
     * 读取Excel内容并存储
     * @param path
     * @param category
     * @since   2015年11月10日    guokemenng
     */
    public void readExcel(String path, String category) {

        try {
            File excelFile = new File(path);
            String fileName = excelFile.getName();
            fileName = fileName.toLowerCase();
            if (fileName.toLowerCase().endsWith(".xls")) {
                HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excelFile));
                //读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作    HSSFSheet sheet = wb.getSheetAt(0);
                Sheet sheet = wb.getSheetAt(0);
                //上传合同
                if("1".equals(category)){
                    List<PresaleContractModel> modelList = new ArrayList<PresaleContractModel>();
                    //循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
                    //从第三行开始读取数据
                    for (int j = 2; j < sheet.getLastRowNum() + 1; j++) {
                        PresaleContractModel preSale = new PresaleContractModel();
                        Row row = sheet.getRow(j);
                        makePresaleContract(preSale, row);
                        if(!StringUtil.isEmpty(preSale.getContractCode())){
                            modelList.add(preSale);
                        }
                    }
                    getDao().saveOrUpdateAll(modelList);
                    //上传厂商信息
                } else if("2".equals(category)){
                    List<PresalesContractTypeModel> modelList = new ArrayList<PresalesContractTypeModel>();
                    //循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
                    //从第三行开始读取数据
                    for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                        PresalesContractTypeModel preSale = new PresalesContractTypeModel();
                        Row row = sheet.getRow(j);
                        makePresaleContractType(preSale, row);
                        preSale.setType((short)1);
                        if(!StringUtil.isEmpty(preSale.getName())){
                            modelList.add(preSale);
                        }
                    }
                    presalesContractTypeDao.saveOrUpdateAll(modelList);
                    //上传产品技术信息
                } else if("3".equals(category)){
                    List<PresalesContractTypeModel> modelList = new ArrayList<PresalesContractTypeModel>();
                    //循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
                    //从第三行开始读取数据
                    for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                        PresalesContractTypeModel preSale = new PresalesContractTypeModel();
                        Row row = sheet.getRow(j);
                        makePresaleContractType(preSale, row);
                        preSale.setType((short)2);
                        if(!StringUtil.isEmpty(preSale.getName())){
                            modelList.add(preSale);
                        }
                    }
                    presalesContractTypeDao.saveOrUpdateAll(modelList);
                }
            } else {
                InputStream input = new FileInputStream(excelFile);
                XSSFWorkbook workBook = new XSSFWorkbook(input);
                // 获取Sheet数量
                //                Integer sheetNum = workBook.getNumberOfSheets();
                XSSFSheet sheet = workBook.getSheetAt(0);
              //上传合同
                if("1".equals(category)){
                    List<PresaleContractModel> modelList = new ArrayList<PresaleContractModel>();
                    //循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
                    //从第三行开始读取数据
                    for (int j = 2; j < sheet.getLastRowNum() + 1; j++) {
                        PresaleContractModel preSale = new PresaleContractModel();
                        Row row = sheet.getRow(j);
                        makePresaleContract(preSale, row);
                        if(!StringUtil.isEmpty(preSale.getContractCode())){
                            modelList.add(preSale);
                        }
                    }
                    getDao().saveOrUpdateAll(modelList);
                    //上传厂商信息
                } else if("2".equals(category)){
                    List<PresalesContractTypeModel> modelList = new ArrayList<PresalesContractTypeModel>();
                    //循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
                    //从第三行开始读取数据
                    for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                        PresalesContractTypeModel preSale = new PresalesContractTypeModel();
                        Row row = sheet.getRow(j);
                        makePresaleContractType(preSale, row);
                        preSale.setType((short)1);
                        if(!StringUtil.isEmpty(preSale.getName())){
                            modelList.add(preSale);
                        }
                    }
                    presalesContractTypeDao.saveOrUpdateAll(modelList);
                    //上传产品技术信息
                } else if("3".equals(category)){
                    List<PresalesContractTypeModel> modelList = new ArrayList<PresalesContractTypeModel>();
                    //循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
                    //从第三行开始读取数据
                    for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                        PresalesContractTypeModel preSale = new PresalesContractTypeModel();
                        Row row = sheet.getRow(j);
                        makePresaleContractType(preSale, row);
                        preSale.setType((short)2);
                        if(!StringUtil.isEmpty(preSale.getName())){
                            modelList.add(preSale);
                        }
                    }
                    presalesContractTypeDao.saveOrUpdateAll(modelList);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new SinoRuntimeException("操作失败");
        } catch (IOException e) {
            e.printStackTrace();
            throw new SinoRuntimeException("操作失败");
        }
    }
    
    /**
     * <code>makePresaleContract</code>
     * 组建合同对象
     * @param salary
     * @param row
     * @return
     * @since   2015年11月10日    guokemenng
     */
    public PresaleContractModel makePresaleContract(PresaleContractModel preSale, Row row) {
        try {

            //把一行里的每一个字段遍历出来
            for (int i = 0; i < row.getLastCellNum(); i++) {
                //创建一个行里的一个字段的对象，也就是获取到的一个单元格中的值
                Cell cell = row.getCell(i);
                Object o = getCellvalue(cell);
                try {
                    switch (i) {
                        case 0: {
                            if (o != null) {
                                String str = o.toString();
                                preSale.setPreContractCode(str);
                            }
                            break;
                        }
                        case 1: {
                            if (o != null) {
                                String str = o.toString();
                                preSale.setContractName(str);
                            }
                            break;
                        }
                        case 2: {
                            if (o != null) {
                                String str = o.toString();
                                preSale.setCustomerName(str);
                            }
                            break;
                        }
                        case 3: {
                            if (o != null) {
                                String str = o.toString();
                                preSale.setCustomerType(str);
                            }
                            break;
                        }
                        case 4: {
                            if (o != null) {
                                String str = o.toString();
                                preSale.setVendor(str);
                            }
                            break;
                        }
                        case 5: {
                            if (o != null) {
                                String str = o.toString();
                                preSale.setProTechnology(str);
                            }
                            break;
                        }
                        case 6: {
                            if (o != null) {
                                String str = o.toString();
                                preSale.setProjectSite(str);
                            }
                            break;
                        }
                        case 7: {
                            if (o != null) {
                                String str = o.toString();
                                preSale.setContractAmount(new BigDecimal(str));;
                            }
                            break;
                        }
                        case 8: {
                            if (o != null) {
                                String str = o.toString();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                preSale.setContractTime(sdf.parse(str));
                            }
                            break;
                        }
                        case 9: {
                            if (o != null) {
                                String str = o.toString();
                                preSale.setContractCode(str);
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                preSale.setCreateTime(new Date());
            }
        } catch (SinoRuntimeException e) {
            e.printStackTrace();
            throw new SinoRuntimeException("操作失败");
        }
        return preSale;
    }
    
    
    /**
     * <code>makePresaleContract</code>
     * 组建合同类型对象
     * @param salary
     * @param row
     * @return
     * @since   2015年11月10日    guokemenng
     */
    public PresalesContractTypeModel makePresaleContractType(PresalesContractTypeModel preSale, Row row) {
        try {

            //把一行里的每一个字段遍历出来
            for (int i = 0; i < row.getLastCellNum(); i++) {
                //创建一个行里的一个字段的对象，也就是获取到的一个单元格中的值
                Cell cell = row.getCell(i);
                Object o = getCellvalue(cell);
                try {
                    switch (i) {
                        case 0: {
                            if (o != null) {
                                String str = o.toString();
                                preSale.setName(str);
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                preSale.setCreateTime(new Date());
            }
        } catch (SinoRuntimeException e) {
            e.printStackTrace();
            throw new SinoRuntimeException("操作失败");
        }
        return preSale;
    }
    
    
    /**
     * <code>getCellvalue</code>
     *
     * @param salary
     * @param cell
     * @return
     * @since   2015年11月10日    guokemenng
     */
    public Object getCellvalue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    try {
                        return DateUtils.convertDateToString(cell.getDateCellValue(), "yyyy-MM-dd");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_BLANK:
                return null;
            case Cell.CELL_TYPE_FORMULA:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String str = cell.getStringCellValue();
                return str;
            default:
                return null;
        }
    }
    
    
}
