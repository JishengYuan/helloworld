/*
 * FileName: BusinessInboundBillService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.stock.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.base.core.utils.UploadUtil;
import com.sinobridge.eoss.business.stock.dao.BusinessInboundBillDao;
import com.sinobridge.eoss.business.stock.dao.InboundDao;
import com.sinobridge.eoss.business.stock.dao.StockDao;
import com.sinobridge.eoss.business.stock.model.BusinessInboundBill;
import com.sinobridge.eoss.business.stock.model.InboundModel;
import com.sinobridge.eoss.business.stock.model.StockModel;
import com.sinobridge.eoss.tools.DataSet;
import com.sinobridge.eoss.tools.ExcelOperation;
import com.sinobridge.systemmanage.util.Global;
import com.sinobridge.systemmanage.util.StringUtil;
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
 * 2014年10月10日 上午8:51:00          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class BusinessInboundBillService extends DefaultBaseService<BusinessInboundBill,BusinessInboundBillDao>{

    @Autowired
    InboundDao inboundDao;
    
    @Autowired
    StockDao stockDao;
    /**
     * <code>uploadFile</code>
     *
     * @param request
     * @since   2014年10月10日    guokemenng
     */
    public boolean uploadFile(HttpServletRequest request) {
        boolean b = true;
        try {
            String storePlace = request.getParameter("storePlace");
            String filePath = doUpload(request);
            String realPath = UploadUtil.buildWebRootPath() + filePath;
            List<BusinessInboundBill> billList = new ArrayList<BusinessInboundBill>();
            
            Long l = IdentifierGeneratorImpl.generatorLong();
            
            DataSet dataSet = ExcelOperation.readExcelPOI(realPath,0);
            List<String[]> datasList = dataSet.getDatasList();
            
            for(String[] strs : datasList){
                if(!StringUtil.isEmpty(strs[0])){
                    BusinessInboundBill bill = new BusinessInboundBill();
                    bill.setPono(strs[0]);
                    bill.setBoxCode(strs[2]);
                    bill.setProductCode(strs[3]);
                    bill.setProductNum(Integer.parseInt(strs[4]));
                    bill.setBoundLocation(strs[5]);
                    bill.setProductSn(strs[6]);
                    
                    bill.setInboundTime(new Date());
                    bill.setInboundCode(l.toString());
                    
                    bill.setState((short)0);
                    
                    billList.add(bill);
                    
                    if(!StringUtil.isEmpty(strs[6])&&Integer.parseInt(strs[4]) != 1){
                        return false;
                    }
                    
                }
            }
            getDao().saveOrUpdateAll(billList);
            getDao().flush();
            
            SystemUser systemUser = (SystemUser) request.getSession().getAttribute(Global.LOGIN_USER);
            
            makeInbound(l.toString(),storePlace,systemUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
    
    /**
     * <code>doUpload</code>
     *
     * @param request
     * @return
     * @since   2014年10月10日    guokemenng
     */
    public String doUpload(HttpServletRequest request) {
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("attachment");
        String picName = file.getFileItem().getName();
        String webPath = UploadUtil.buildWebPath(UploadUtil.getBasePath() + "excel/", picName);
        String storeFilePath = UploadUtil.buildStroeFilePath(webPath);
        
        File descPath = new File(UploadUtil.buildPath(storeFilePath));
        if (!descPath.exists()) {
            descPath.mkdirs();
        }

        File descF = new File(storeFilePath);
        try {
            FileCopyUtils.copy(file.getBytes(), descF);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return webPath;
    }
    
    /**
     * <code>makeInbound</code>
     * 汇总入库单到入库表
     * @param inboundCode
     * @param storePlace
     * @since   2014年10月10日    guokemenng
     */
    public void makeInbound(String inboundCode,String storePlace,SystemUser systemUser){
        List<Map<String,Object>> mapList = getMapBillListByInboundNo(inboundCode);
        List<InboundModel> inboundList = new ArrayList<InboundModel>();
        for(Map<String,Object> map : mapList){
            InboundModel inbound = new InboundModel();
            
            inbound.setInboundCode(inboundCode);
            inbound.setInboundTime(new Date());
            inbound.setProductCode(map.get("ProductCode").toString());
            inbound.setProductNum(Integer.parseInt(map.get("totalNum").toString()));
            inbound.setStorePlace(storePlace);
            
            inbound.setRecipientName(systemUser.getStaffName());
            
            inbound.setState((short)0);
            
            inboundList.add(inbound);
        }
        inboundDao.saveOrUpdateAll(inboundList);
    }
    
    /**
     * <code>getBillListByInboundNo</code>
     * 根据入库单号查询出入库单Map集合信息
     * @param inboundCode
     * @return
     * @since   2014年10月10日    guokemenng
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> getMapBillListByInboundNo(String inboundCode){
        String sql = "select *,sum(ProductNum) totalNum from business_inbound_bill where InboundCode = ? group by ProductCode ";
        String[] param = new String[1];
        param[0] = inboundCode;
        
        Query query = this.getDao().createSQLQuery(sql, param);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
       
    }
    
    /**
     * <code>getBillListByInboundNo</code>
     * 根据入库单号查询出入库单list
     * @param inboundCode
     * @return
     * @since   2014年10月11日    guokemenng
     */
    public List<BusinessInboundBill> getBillListByInboundNo(String inboundCode){
        String hql = "from BusinessInboundBill where inboundCode = ?";
        String[] param = new String[1];
        param[0] = inboundCode;
        return getDao().find(hql, param);
        
    }
    /**
     * <code>getInboundListByInboundNo</code>
     * 根据入库单号查询InboundModel list
     * @param inboundCode
     * @return
     * @since   2014年10月11日    guokemenng
     */
    public List<InboundModel> getInboundListByInboundNo(String inboundCode){
        String sql = "from InboundModel where inboundCode = ?";
        String[] param = new String[1];
        param[0] = inboundCode;
        return inboundDao.find(sql, param);
    }
    
    /**
     * <code>getStockListByInboundNo</code>
     * 库存list
     * @param inboundCode
     * @return
     * @since   2014年10月11日    guokemenng
     */
    public List<StockModel> getStockListByOrderCode(String orderCode){
        String sql = "from StockModel where orderCode = ?";
        String[] param = new String[1];
        param[0] = orderCode;
        return stockDao.find(sql, param);
    }
    
    /**
     * <code>updateInboundState</code>
     * 更新产品入库状态
     * 0  没入库  1 已入库
     * @param inboundCode
     * @since   2014年10月11日    guokemenng
     */
    public void updateInboundState(String inboundCode){
        String[] param = new String[1];
        param[0] = inboundCode;
        String sql = "update business_inbound_bill set state = '1' where inboundCode = ?";
        stockDao.createSQLQuery(sql,param).executeUpdate();
        String sql1 = "update business_inbound set state = '1' where inboundCode = ?";
        stockDao.createSQLQuery(sql1,param).executeUpdate();
    }
    
}
