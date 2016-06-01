/*
 * FileName: SalesContractContant.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.sinobridge.eoss.sales.contract.constant;

import java.math.BigDecimal;

/**
 * <p>
 * Description: 销售合同中所用到的常量
 * </p>
 *
 * @author 3unshine
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年6月23日 下午8:34:14          3unshine        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
public class SalesContractConstant {
    //流程变量中合同转发限额限制的KEY
    public static final String CONTRACT_AMOUNT_KEY = "contract_amount";
    //流程变量中合同转发限额限制的金额数
    public static final BigDecimal CONTRACT_AMOUNT = new BigDecimal(5000000.00);
    //合同审批流程创建工单时所需的工单定义KEY值
    public static final String CONTRACT_PROCDEFKEY = "ssp";
    //合同盖章申请审批流程创建工单时所需的工单定义KEY值
    public static final String CONTRACT_CACHET_PROCDEFKEY = "sgz";
    //合同发票申请审批流程创建工单时所需的工单定义KEY值
    public static final String CONTRACT_INVOICE_PROCDEFKEY = "sfp";
    //合同变更申请审批流程创建工单时所需的工单定义KEY值
    public static final String CONTRACT_CHANGE_PROCDEFKEY = "sbg";
    //合同金额变更审批流程创建工单时所需的工单定义KEY值
    public static final String CONTRACT_AMOUNT_CHANGE_PROCDEFKEY = "sjebg";
    //合同审批流程中流程变量是否为重新提交的KEY
    public static final String IS_RE_SUBMIT = "is_re_submit";
    //合同审批流程中流程变量是否为重新提交的KEY
    public static final String CONTRACT_CODE_FIRST = "SZXQ";
    //合同状态——草稿
    public static final String CONTRACT_STATE_CG = "CG";
    //合同状态——审核中
    public static final String CONTRACT_STATE_SH = "SH";
    //合同状态——重新提交
    public static final String CONTRACT_STATE_CXTJ = "CXTJ";
    //合同状态——审核通过进入订单
    public static final String CONTRACT_STATE_TGSH = "TGSH";
    //合同状态——审核不通过重新提交放弃提交(废弃状态)
    public static final String CONTRACT_STATE_FQ = "FQ";
    //申请变更后的发票状态
    public static final String CONTRACT_STATE_BG = "BG";
    
    //合同合并状态
    public static final String CONTRACT_STATE_HB = "HB";

    //合同待关闭状态
    public static final String CONTRACT_STATE_DGB = "DGB";
    //成本预估
    public static final String CONTRACT_STATE_CBYG = "CBYG";

    //盖章申请提交后的第一个任务节点名称
    public static final String CONTRACT_CACHET_FIRSTNODE = "商务主管盖章审批";
    //盖章申请创建工单时加的后缀名
    public static final String CONTRACT_CACHET_PROCTITLE = "盖章申请";
    //发票申请创建工单时加的后缀名
    public static final String CONTRACT_INVOICE_PROCTITLE = "发票申请";
    //变更申请创建工单时加的后缀名
    public static final String CONTRACT_CHANGE_PROCTITLE = "变更申请";
    //合同状态表中订单的初始状态
    public static final String CONTRACT_ORDER_INITSTATE = "未采购";
    public static final String CONTRACT_ORDER_APPLY = "采购审核中";
    public static final String CONTRACT_ORDER_DEVICEAPPLY = "设备采购中";
    public static final String CONTRACT_ORDER_DEVICEREACH = "设备到库房";
    public static final String CONTRACT_ORDER_REACHCUSTOM = "客户到货";
    public static final String CONTRACT_ORDER_FINISH = "采购完毕";

    //合同状态表中发票申请的初始状态
    public static final String CONTRACT_INVOICE_INITSTATE = "未申请";
    //合同状态表中发票申请中的状态
    public static final String CONTRACT_INVOICE_APPLYSTATE = "申请中";
    //合同状态表中发票废弃后待重新申请的状态
    public static final String CONTRACT_INVOICE_REAPPLYSTATE = "待重新申请";
    //合同状态表中发票废弃后待重新申请的状态
    public static final String CONTRACT_INVOICE_SP = "待提交";
    //合同状态表中发票审批通过的状态
    public static final String CONTRACT_INVOICE_ENDSTATE = "审批通过";
    //合同状态表中盖章申请的初始状态
    public static final String CONTRACT_CACHET_INITSTATE = "未申请";
    //合同状态表中盖章废弃后待重新申请的状态
    public static final String CONTRACT_CACHET_REAPPLYSTATE = "待重新申请";
    //合同状态表中盖章审批通过的状态
    public static final String CONTRACT_CACHET_ENDSTATE = "审批通过";
    public static final String CONTRACT_CACHET_PASSSTATE = "已盖章";
    //合同状态表中变更申请的初始状态
    public static final String CONTRACT_CHANGE_INITSTATE = "未申请";
    //合同状态表中变更申请的申请状态
    public static final String CONTRACT_CHANGE_APPLYSTATE = "变更申请中";
    //合同状态表中变更申请的通过状态
    public static final String CONTRACT_CHANGE_ENDSTATE = "变更申请通过";
    //合同状态表中变更申请的未通过状态
    public static final String CONTRACT_CHANGE_UNPASSSTATE = "变更申请不通过";
    //合同状态表中收款初始状态
    public static final String CONTRACT_RECIVE_INITSTATE = "未收款";

    //是否是一般员工提交合同流程变量   0是一般  1不是一般
    public static final String IS_DEPT = "isDept";
}
