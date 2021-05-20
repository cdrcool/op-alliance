package com.op.framework.boot.sdk.client.response;

import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDetail.InvoiceSkuDetailOpenResp;
import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDetail.QueryInvoiceDetailOpenResp;
import com.suning.api.entity.govbus.GetinvoicesurfaceinfoQueryResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 发票明细响应对象
 *
 * @author cdrcool
 */
@Slf4j
@Data
public class InvoiceDetailResponse {
    /**
     * 发票号码
     */
    private String invoiceId;

    /**
     * 发票代码
     */
    private String invoiceCode;

    /**
     * 发票日期
     */
    private Date invoiceDate;

    /**
     * 发票类型
     * <p>
     * 京东：2-专票；3-电子发票
     * 苏宁：2-普票；4-电子发票，6-增票
     */
    private Integer invoiceType;

    /**
     * 发票抬头
     */
    private String title;

    /**
     * 纳税人识别号
     */
    private String taxpayer;

    /**
     * 电话
     */
    private String tel;

    /**
     * 地址
     */
    private String address;

    /**
     * 开户行
     */
    private String bank;

    /**
     * 账号
     */
    private String account;

    /**
     * 发票金额（裸价）
     */
    private BigDecimal invoiceNakedAmount;

    /**
     * 发票税率
     */
    private BigDecimal invoiceTaxRate;

    /**
     * 发票税额
     */
    private BigDecimal invoiceTaxAmount;

    /**
     * 价税合计
     */
    private BigDecimal invoiceAmount;

    /**
     * 冲红的原始发票号
     */
    private String originalInvoiceId;

    /**
     * 冲红的原始发票代码
     */
    private String originalInvoiceCode;

    /**
     * 发票状态（0-作废；1-正常；2-冲红）
     */
    private Integer state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 发票标识（-1-红票；1-蓝票）
     * <p>
     * 苏宁特有
     */
    private Integer invoiceSign;

    /**
     * 商品明细集合
     */
    private List<InvoiceSkuDetail> skuDetails;


    @Data
    public static class InvoiceSkuDetail {
        /**
         * 订单号
         */
        private String orderId;

        /**
         * 商品id
         */
        private String skuId;

        /**
         * 商品名称
         */
        private String skuName;

        /**
         * 规格型号
         */
        private String specification;

        /**
         * 结算单位
         */
        private String settleUnit;

        /**
         * 单价
         */
        private BigDecimal price;

        /**
         * 数量
         */
        private BigDecimal num;

        /**
         * 总金额（不含税）
         */
        private BigDecimal amountUnTax;

        /**
         * 税率
         */
        private BigDecimal taxRate;

        /**
         * 税额
         */
        private BigDecimal taxAmount;

        /**
         * 总金额（含税）
         */
        private BigDecimal amount;

        /**
         * 税分类编码
         * <p>
         * 苏宁特有
         */
        private String taxCode;

        public static InvoiceSkuDetail buildFrom(InvoiceSkuDetailOpenResp resp) {
            InvoiceSkuDetail detail = new InvoiceSkuDetail();
            detail.setOrderId(String.valueOf(resp.getJdOrderId()));
            detail.setSkuId(resp.getSkuId());
            detail.setSkuName(resp.getSkuName());
            detail.setSpecification(resp.getSpecification());
            detail.setSettleUnit(resp.getSettleUnit());
            detail.setPrice(resp.getPrice());
            detail.setNum(resp.getNum());
            detail.setAmountUnTax(resp.getAmountUnTax());
            detail.setTaxRate(resp.getTaxRate());
            detail.setTaxAmount(resp.getTaxAmount());
            detail.setAmount(resp.getAmount());

            return detail;
        }

        public static InvoiceSkuDetail buildFrom(GetinvoicesurfaceinfoQueryResponse.InvoiceItems invoiceItems) {
            InvoiceSkuDetail detail = new InvoiceSkuDetail();
            detail.setOrderId(String.valueOf(invoiceItems.getOrderItemId()));
            detail.setSkuName(invoiceItems.getProductName());
            detail.setPrice(new BigDecimal(invoiceItems.getPrice()));
            detail.setNum(new BigDecimal(invoiceItems.getNum()));
            detail.setAmountUnTax(new BigDecimal(invoiceItems.getNakedAmount()));
            detail.setTaxRate(new BigDecimal(invoiceItems.getInvoiceTaxRate()));
            detail.setTaxAmount(new BigDecimal(invoiceItems.getInvoiceTax()));
            detail.setTaxCode(invoiceItems.getTaxCode());

            return detail;
        }
    }

    public static InvoiceDetailResponse buildFrom(QueryInvoiceDetailOpenResp resp) {
        InvoiceDetailResponse response = new InvoiceDetailResponse();
        response.setInvoiceId(resp.getInvoiceId());
        response.setInvoiceCode(resp.getInvoiceCode());
        response.setInvoiceDate(resp.getInvoiceDate());
        response.setInvoiceType(resp.getInvoiceType());
        response.setTitle(resp.getTitle());
        response.setTaxpayer(resp.getTaxpayer());
        response.setTel(resp.getTel());
        response.setAddress(resp.getAddress());
        response.setBank(resp.getBank());
        response.setAccount(resp.getAccount());
        response.setInvoiceNakedAmount(resp.getInvoiceNakedAmount());
        response.setInvoiceTaxRate(resp.getInvoiceTaxRate());
        response.setInvoiceTaxAmount(resp.getInvoiceTaxAmount());
        response.setInvoiceAmount(resp.getInvoiceAmount());
        response.setOriginalInvoiceId(resp.getOriginalInvoiceId());
        response.setOriginalInvoiceCode(resp.getOriginalInvoiceCode());
        response.setState(resp.getState());
        response.setRemark(resp.getRemark());
        response.setSkuDetails(resp.getSkuDetailList().stream().map(InvoiceSkuDetail::buildFrom).collect(Collectors.toList()));

        return response;
    }

    public static InvoiceDetailResponse buildFrom(GetinvoicesurfaceinfoQueryResponse.InvoiceList invoiceList) {
        InvoiceDetailResponse response = new InvoiceDetailResponse();
        response.setInvoiceId(invoiceList.getInvoiceid());
        response.setInvoiceCode(invoiceList.getInvoiceCode());
        try {
            response.setInvoiceDate(new SimpleDateFormat("yyyy-MM-dd").parse(invoiceList.getInvoiceDate()));
        } catch (ParseException e) {
            log.error("格式化发票日期异常", e);
        }
        response.setInvoiceType(Integer.parseInt(invoiceList.getInvoiceType()));
        response.setTitle(invoiceList.getInvoiceTitle());
        response.setTaxpayer(invoiceList.getTaxPayerNo());
        response.setTel(invoiceList.getPayerPhone());
        response.setAddress(invoiceList.getPayerAddress());
        response.setBank(invoiceList.getPayerBank());
        response.setAccount(invoiceList.getPayerAccount());
        response.setInvoiceNakedAmount(new BigDecimal(invoiceList.getInvoiceNakedAmount()));
        response.setInvoiceTaxAmount(new BigDecimal(invoiceList.getInvoiceTaxAmount()));
        response.setInvoiceAmount(new BigDecimal(invoiceList.getInvoiceAmount()));
        response.setInvoiceSign(Integer.parseInt(invoiceList.getInvoiceSign()));
        response.setSkuDetails(invoiceList.getInvoiceItems().stream().map(InvoiceSkuDetail::buildFrom).collect(Collectors.toList()));

        return response;

    }
}
