/*
 * FileName: TestController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.business.suppliermanage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.eoss.business.suppliermanage.model.SupplierInfoModel;
import com.sinobridge.eoss.business.suppliermanage.service.SupplierInfoService;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author 袁继生
 * @version 1.0

 * <p>
 * History:
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2016年3月30日 上午10:11:41           袁继生           									1.0        To create
 * </p>
 *
 * @since
 * @see
 */

@Controller
@RequestMapping(value = "/business/supplierm/supplierInfo")
public class MyTestController extends DefaultBaseController<SupplierInfoModel, SupplierInfoService> {

    @RequestMapping("/getData")
    public String getData() {
        System.out.println("getData executes");

        return "index";
    }
}
