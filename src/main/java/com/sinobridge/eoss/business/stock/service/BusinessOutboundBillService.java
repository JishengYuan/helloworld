/*
 * FileName: BusinessOutboundBillService.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.stock.dao.BusinessOutboundBillDao;
import com.sinobridge.eoss.business.stock.model.BusinessOutboundBill;

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
 * 2014年10月18日 上午11:41:19          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Service
@Transactional
public class BusinessOutboundBillService extends DefaultBaseService<BusinessOutboundBill,BusinessOutboundBillDao>{

}
