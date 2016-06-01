package com.sinobridge.eoss.business.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.stock.dao.SerialDao;
import com.sinobridge.eoss.business.stock.model.SerialModel;

/**
 *	序列号
 */
@Service
@Transactional
public class SerialService extends DefaultBaseService<SerialModel, SerialDao>{
	
}