package com.sinobridge.eoss.business.order.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.hibernate.id.IdentifierGeneratorImpl;
import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.order.constant.BusinessOrderContant;
import com.sinobridge.eoss.business.order.dao.CloseContractDao;
import com.sinobridge.eoss.business.order.model.CloseContractModel;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;
import com.sinobridge.systemmanage.model.SysStaff;
import com.sinobridge.systemmanage.service.SysStaffService;

@Service
@Transactional
public class CloseContractService extends DefaultBaseService<CloseContractModel, CloseContractDao> {

    @Autowired
    private SysStaffService staffService;
    @Autowired
    private SalesContractService salesContractService;

    /**结算确认
     * @param closeContract
     * @param isAgree
     * @param salesId
     */
    public void close(CloseContractModel closeContract, int isAgree, Long salesId) {
        SalesContractModel salesContract = salesContractService.get(salesId);
        closeContract.setSalesContract(salesContract);
        if (isAgree == 1) {//同意
            closeContract.setStatuse(BusinessOrderContant.CLOSE_CONTRACT_TG);
            getDao().closeContractOk(salesId);
        }
        if (isAgree == 0) {//不同意
            closeContract.setStatuse(BusinessOrderContant.CLOSE_CONTRACT_CG);
        }
        getDao().saveOrUpdate(closeContract);
    }

    /**申请关闭合同
     * @param creatorId
     * @param contractAmount
     * @param contractType
     * @param contractShortName
     * @param id2
     */
    public void closeContract(long creatorId, BigDecimal contractAmount, int contractType, String contractShortName, String id2) {
        try {
            long id = IdentifierGeneratorImpl.generatorLong();
            CloseContractModel closeContract = new CloseContractModel();
            closeContract.setId(id);
            SysStaff user = staffService.get(Long.toString(creatorId));
            String contractCreator = user.getStaffName();
            SalesContractModel salesContract = salesContractService.get(Long.parseLong(id2));

            closeContract.setSalesContract(salesContract);
            closeContract.setContractAmount(contractAmount);
            closeContract.setContractCreator(contractCreator);
            closeContract.setContractShortName(contractShortName);
            closeContract.setContractType(contractType);
            closeContract.setStatuse(BusinessOrderContant.CLOSE_CONTRACT_SH);
            getDao().save(closeContract);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**通过合同ID，查询关闭合同中间表
     * @param salesId
     * @return
     */
    public boolean getCloseContractModelBySalesId(Long salesId){
        boolean b = true;
        String hql = "from CloseContractModel m where m.salesContract.id = ?";
        Object[] param = new Object[]{salesId};
        List<CloseContractModel> modelList = getDao().find(hql, param);
        if(modelList.size() > 0){
            b = false;
        }
        return b;
    }
    
    /**查询关闭合同中间表
     * @return
     */
    public List<CloseContractModel> findClosingContract(){
        String hql = "from CloseContractModel m where m.statuse = '1'";
        List<CloseContractModel> modelList = getDao().find(hql);
        return modelList;
    }
}
