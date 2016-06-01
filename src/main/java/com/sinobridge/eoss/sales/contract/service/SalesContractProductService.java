package com.sinobridge.eoss.sales.contract.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.sales.contract.dao.SalesProductDao;
import com.sinobridge.eoss.sales.contract.model.SalesContractProductModel;

@Service
@Transactional
public class SalesContractProductService extends DefaultBaseService<SalesContractProductModel, SalesProductDao > {
    /**
     * <code>getBySalesContractId</code>
     * @param detachedCriteria
     * @since 2014年5月26日 3unshine
     */
    public List<SalesContractProductModel> getBySalesContractId(DetachedCriteria detachedCriteria) {
        List<SalesContractProductModel> salesContractProductModel = getDao().findByCriteria(detachedCriteria);
        return salesContractProductModel;
    }
}
