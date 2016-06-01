package com.sinobridge.eoss.business.order.constant;

/**
 * <p>
 * Description: 商务订单中所用到的常量
 * </p>
 *
 * @author 3unshine
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2014年7月23日 下午8:34:14          wangya        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
public class BusinessOrderContant {
    //合同状态表的订单状态为完成时
    public static final String ORDER_STATUSE_OK = "采购完成";
    public static final String ORDER_STATUSE_START = "采购中";
    //订单未通过审批时的合同订单状态
    public static final String ORDER_STATUSE_SP = "采购申请中";
    //合同通过审批的状态
    public static final String CONTRACT_OK = "TGSH";
    //订单编码开头
    public static final String ORDER_CODE_FIRST = "SZXQ";
    //相孚用
    //public static final String ORDER_CODE_FIRST = "SHXF";
    //订单通过审批的状态
    public static final String ORDER_OK = "TGSP";
    //订单草稿状态
    public static final String ORDER_CG = "CG";
    /*    //订单放弃的状态
        public static final String ORDER_GIVEUP="giveUp";*/
    //订单审批流程key值
    public static final String ORDER_KEY = "bdd";
    //订单状态为审核中
    public static final String ORDER_SH = "SH";
    //审批的变量，是否重新提交
    public static final String IS_RE_SUBMIT = "is_re_submit";
    //付款、发票的草稿状态
    public static final String CG = "N";
    //付款、发票计划审批通过
    public static final String PAYMENT_OK = "10";
    //订单付款、发票状态,部分付款审批中：只填了部分金额。
    public static final String ORDER_PAYMENT_S = "S";
    //订单付款、发票状态，全部审批中:一次将所有金额都填了。
    public static final String ORDER_PAYMENT_ASP = "ASP";
    //订单付款、发票状态,完全付款
    public static final String ORDER_PAYMENT_A = "A";
    //订单付款、发票状态，草稿状态
    public static final String ORDER_PAYMENT_N = "N";
    //内采审批通过
    public static final String INTERPURCHAS_OK = "3";
    //内采订单状态：等待下订单
    public static final String INTER_ORDER_WAIT = "等待下单";
    //内采订单状态：订单处理中
    public static final String INTER_ORDER_DOING = "订单处理中";
    //内采订单状态：订单处理完毕
    public static final String INTER_ORDER_OK = "订单处理完毕";
    //重新提交的状态
    public static final String CXTJ = "CXTJ";
    //通过审批
    public static final String CWFH = "CWFH";
    //内采审批中,付款、发票计划的状态为审批时
    public static final String INTERPURCHAS_SH = "2";
    //付款计划审批key
    public static final String PAYMENT_KEY = "bfk";
    //发票计划的key
    public static final String REIM_KEY = "bfp";
    //发票计划的总金额
    public static final String REIM_AMOUNT = "invoice_amount";
    //付款计划的总金额
    public static final String PAYMENT_AMOUNT = "invoice_amount";
    //内采审批中,付款、发票计划的状态为草稿时,
    public static final String NFF_CG = "1";
    //关闭合同审批状态:申请
    public static final String CLOSE_CONTRACT_SH = "1";
    //关闭合同审批状态：通过
    public static final String CLOSE_CONTRACT_TG = "2";
    //关闭合同审批状态：不通过
    public static final String CLOSE_CONTRACT_CG = "0";
    //标记订单产品是关联合同的
    public static final String SELECTCONTRACT = "HT";
    //标记订单产品是关联内采的
    public static final String SELCETINTERPURCHAS = "NC";
    //付款计划工单标题
    public static final String PAYMENT = "付款计划";
    //发票计划工单标题
    public static final String REIMBURS = "发票计划";
    //订单到货状态
    public static final String COMMODITY = "已到客户";
    //订单到货状态
    public static final String COMMODITY_COMPANY = "到公司";
    //订单到货状态
    public static final String COMMODITY_NO = "未到客户";
    //订单到库状态：WareHouseStatus
    public static final String WAREHOUSESTAATUS_NO = "N";
    //订单到库状态：WareHouseStatus
    public static final String WAREHOUSESTAATUS_S = "S";
    //订单到库状态：WareHouseStatus
    public static final String WAREHOUSESTAATUS_A = "A";
    //订单审批页面：商务经理审批
    public static final String SWJLSP = "商务经理审批";
    //订单审批页面：商务主管审批
    public static final String SWZGSP = "商务主管审批";
    //订单审批页面：副总经理审批，相孚使用
    public static final String FZJLSP_XF = "副总经理审批";
    //订单审批页面：盖章审批，相孚使用
    public static final String GZSP_XF = "盖章审批";
    //变更申请状态：审批中
    public static final String CHANGE_SP = "SP";
    //变更申请状态：通过
    public static final String CHANGE_TGSP = "TGSP";
    //变更申请状态：驳回
    public static final String CHANGE_BTY = "BTY";

}
