/*
 * FileName: SalesStatisticController.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sinobridge.base.chart.fusioncharts.dto.ChartGraph;
import com.sinobridge.base.chart.fusioncharts.dto.ChartSet;
import com.sinobridge.base.chart.fusioncharts.util.FusionChartUtil;
import com.sinobridge.base.core.controller.DefaultBaseController;
import com.sinobridge.eoss.sales.contract.model.SalesContractModel;
import com.sinobridge.eoss.sales.contract.service.SalesContractService;

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
 * 2014年11月18日 下午8:52:29          guo_kemeng        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
@Controller
@RequestMapping(value = "/sales/statistic")
public class SalesStatisticController extends DefaultBaseController<SalesContractModel, SalesContractService>{

    /**
     * <code>simplePage</code>
     *
     * @param request
     * @param response
     * @return
     * @since   2014年11月18日    guokemenng
     */
    @RequestMapping(value = "/simplePage")
    public ModelAndView simplePage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("sales/statistic/simplePage");
        return mav;
    }
    
    /**
     * <code>simpleDataXml</code>
     *
     * @param request
     * @param response
     * @since   2014年11月19日    guokemenng
     */
    @RequestMapping(value = "/simpleDataXml")
    public void simpleDataXml(HttpServletRequest request, HttpServletResponse response){
        ChartGraph m = new ChartGraph();
        m.setCaption("销售部金额统计");
        m.setYaxisname("金额");
        m.setXaxisname("部门");
        m.setNumberSuffix("%A5");
        m.setDecimalPrecision(2);
        List<ChartSet> chartSetList = new ArrayList<ChartSet>(0);
        List<Map<String,Object>> mapList = getService().getSimpleDataXml();
        for(Map<String,Object> map : mapList){
            ChartSet c = new ChartSet();
            c.setName(map.get("orgName").toString());
            c.setValueStr(map.get("totalAmount").toString());
            chartSetList.add(c);
        }
        m.setChartSetList(chartSetList);
        String xml = m.toDocument().asXML();
        FusionChartUtil.renderXml(response, xml);
    }
}
