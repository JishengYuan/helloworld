package com.sinobridge.eoss.business.projectmanage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinobridge.base.core.service.DefaultBaseService;
import com.sinobridge.eoss.business.projectmanage.dao.VendorDao;
import com.sinobridge.eoss.business.projectmanage.model.VendorModel;

@Service
@Transactional
public class VendorService extends DefaultBaseService<VendorModel, VendorDao>{
}