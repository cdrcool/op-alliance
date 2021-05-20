package com.op.framework.boot.sdk.client.request;

import com.jd.open.api.sdk.request.vopfp.VopInvoiceSubmitInvoiceApplyRequest;
import com.suning.api.entity.govbus.InvoicesupplementConfirmRequest;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 发票申请提交对象
 *
 * @author cdrcool
 */
@Data
public class InvoiceApplySubmitRequest {
    /**
     * 申请单号（不允许重复，根据该字段查询消息及撤回开票申请等）
     */
    private String markId;

    /**
     * 结算单号（一个结算单号可对对应多个第三方申请单号）
     * <p>
     * 京东特有必须，苏宁结算单号就是申请单号
     */
    private String settlementId;
    /**
     * 订单号
     * <p>
     * 苏宁特有，不能与supplyOrderId同时赋值
     */
    private String orderId;

    /**
     * 子订单号（批量以英文逗号分割）
     */
    private String supplierOrder;

    /**
     * 期望开票日期（格式：yyyy-MM-dd）
     * <p>
     * 京东特有必须
     */
    private String invoiceDate;

    /**
     * 发票类型
     * <p>
     * 京东：2-专票；3-电子发票
     * 苏宁：2-普票；4-电子发票，6-增票
     */
    private Integer invoiceType;

    /**
     * 公司名称
     * <p>
     * 苏宁特有，增票时必须
     */
    private String companyName;

    /**
     * 发票抬头
     */
    private String title;

    /**
     * 纳税人识别号
     * <p>
     * 京东必须，苏宁增票时必须
     */
    private String enterpriseTaxpayer;

    /**
     * 企业注册电话
     * <p>
     * 苏宁增票时必须
     */
    private String enterpriseRegPhone;

    /**
     * 企业注册地址
     * <p>
     * 苏宁增票时必须
     */
    private String enterpriseRegAddress;

    /**
     * 企业注册开户行名称
     * <p>
     * 苏宁增票时必须
     */
    private String enterpriseBankName;

    /**
     * 企业注册开户行账号
     * <p>
     * 苏宁增票时必须
     */
    private String enterpriseBankAccount;

    /**
     * 收件人姓名
     * <p>
     * 京东专票时必须，苏宁电子发票外必须
     */
    private String billToer;

    /**
     * 收票人联系电话
     */
    private String billToContact;

    /**
     * 收票人详细地址
     * <p>
     * 京东专票时必须，苏宁电子发票外必须
     */
    private String billToAddress;

    /**
     * 收票人地址（省）
     * <p>
     * 京东特有专票时必须
     */
    private Integer billToProvince;

    /**
     * 收票人地址（市）
     * <p>
     * 京东特有专票时必须
     */
    private Integer billToCity;

    /**
     * 收票人地址（区）
     * <p>
     * 京东特有专票时必须
     */
    private Integer billToCounty;

    /**
     * 收票人地址（街道）
     * <p>
     * 京东特有专票时必须，无四级地址传0
     */
    private Integer billToTown;

    /**
     * 备注
     * <p>
     * 苏宁必须
     */
    private String remark;

    /**
     * 总批次开发票价税合计
     * <p>
     * 京东特有必须
     */
    private BigDecimal totalBatchInvoiceAmount;

    /**
     * 总批次数
     * <p>
     * 京东特有必须
     */
    private Integer totalBatch;

    /**
     * 当前批次号
     * <p>
     * 京东特有必须
     */
    private Integer currentBatch;

    /**
     * 当前批次子订单总数
     * <p>
     * 京东特有必须
     */
    private Integer invoiceNum;

    /**
     * 当前批次含税总金额
     * <p>
     * 京东特有必须
     */
    private BigDecimal invoicePrice;

    /**
     * 开票机构
     * <p>
     * 京东特有必须（固定值：10）
     */
    private Integer invoiceOrg;

    /**
     * 开票内容
     * <p>
     * 京东：1-明细；100-大类，必须
     * 苏宁：1-明细
     */
    private Integer bizInvoiceContent;

    /**
     * 转换为京东发票申请提交对象
     *
     * @return 京东发票申请提交对象
     */
    public VopInvoiceSubmitInvoiceApplyRequest toJdInvoiceApplySubmitRequest() {
        VopInvoiceSubmitInvoiceApplyRequest request = new VopInvoiceSubmitInvoiceApplyRequest();
        request.setMarkId(markId);
        request.setSettlementId(settlementId);
        request.setSupplierOrder(supplierOrder);
        request.setInvoiceDate(invoiceDate);
        request.setInvoiceType(invoiceType);
        request.setTitle(title);
        request.setEnterpriseTaxpayer(enterpriseTaxpayer);
        request.setEnterpriseRegPhone(enterpriseRegPhone);
        request.setEnterpriseRegAddress(enterpriseRegAddress);
        request.setEnterpriseBankName(enterpriseBankName);
        request.setEnterpriseBankAccount(enterpriseBankAccount);
        request.setBillToer(billToer);
        request.setBillToContact(billToContact);
        request.setBillToAddress(billToAddress);
        request.setBillToProvince(billToProvince);
        request.setBillToCity(billToCity);
        request.setBillToCounty(billToCounty);
        request.setBillToTown(billToTown);
        request.setInvoiceRemark(remark);
        request.setTotalBatchInvoiceAmount(totalBatchInvoiceAmount);
        request.setTotalBatch(totalBatch);
        request.setCurrentBatch(currentBatch);
        request.setInvoiceNum(invoiceNum);
        request.setInvoicePrice(invoicePrice);
        request.setInvoiceOrg(invoiceOrg);
        request.setBizInvoiceContent(bizInvoiceContent);

        return request;
    }

    /**
     * 转换为苏宁发票申请提交对象
     *
     * @return 苏宁发票申请提交对象
     */
    public InvoicesupplementConfirmRequest toSnInvoiceApplySubmitRequest() {
        InvoicesupplementConfirmRequest.ApplyForInvoiceReqDTO applyForInvoiceReqDTO = new InvoicesupplementConfirmRequest.ApplyForInvoiceReqDTO();
        applyForInvoiceReqDTO.setMarkId(markId);
        applyForInvoiceReqDTO.setInvoiceType(String.valueOf(invoiceType));
        applyForInvoiceReqDTO.setTitle(title);
        applyForInvoiceReqDTO.setCompanyName(companyName);
        applyForInvoiceReqDTO.setTaxNo(enterpriseTaxpayer);
        applyForInvoiceReqDTO.setRegTel(enterpriseRegPhone);
        applyForInvoiceReqDTO.setRegAdd(enterpriseRegAddress);
        applyForInvoiceReqDTO.setRegBank(enterpriseBankName);
        applyForInvoiceReqDTO.setRegAccount(enterpriseBankAccount);
        applyForInvoiceReqDTO.setConsigneeName(billToer);
        applyForInvoiceReqDTO.setConsigneeMobileNum(billToContact);
        applyForInvoiceReqDTO.setAddress(billToAddress);
        applyForInvoiceReqDTO.setRemark(remark);
        applyForInvoiceReqDTO.setInvoiceContent(String.valueOf(bizInvoiceContent));

        InvoicesupplementConfirmRequest.OrderInfoDTO orderInfoDTO = new InvoicesupplementConfirmRequest.OrderInfoDTO();
        orderInfoDTO.setGcOrderNo(orderId);
        List<InvoicesupplementConfirmRequest.OrderInfoDTO> orderInfoDTOList = StringUtils.hasText(supplierOrder) ?
                Arrays.stream(supplierOrder.split(",")).map(supplyOrderId -> {
                    InvoicesupplementConfirmRequest.OrderInfoDTO item = new InvoicesupplementConfirmRequest.OrderInfoDTO();
                    item.setGcOrderNo(orderId);
                    item.setGcItemNo(supplyOrderId);

                    return item;
                }).collect(Collectors.toList()) : Collections.singletonList(orderInfoDTO);

        InvoicesupplementConfirmRequest request = new InvoicesupplementConfirmRequest();
        request.setApplyForInvoiceReqDTO(applyForInvoiceReqDTO);
        request.setOrderInfoDTO(orderInfoDTOList);

        return request;
    }
}
