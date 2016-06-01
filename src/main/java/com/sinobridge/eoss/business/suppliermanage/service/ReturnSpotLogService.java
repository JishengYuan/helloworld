package com.sinobridge.eoss.business.suppliermanage.service;

import com.sinobridge.eoss.business.suppliermanage.model.ReturnSpotLogModel;
import com.sinobridge.eoss.business.suppliermanage.dao.ReturnSpotLogDao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sinobridge.base.core.service.DefaultBaseService;

@Service
@Transactional
public class ReturnSpotLogService extends DefaultBaseService<ReturnSpotLogModel, ReturnSpotLogDao>{
}