package com.op.framework.boot.sdk.client.response;

import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceWaybill.InvoiceDeliveryOpenResp;
import com.suning.api.entity.govbus.InvoicelogistGetResponse;
import com.suning.api.entity.govbus.LogistdetailGetResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 发票物流响应对象
 *
 * @author cdrcool
 */
@Slf4j
@NoArgsConstructor
@Data
public class InvoiceDeliveryResponse {
    /**
     * 配送公司
     */
    private String postCompany;

    /**
     * 运单号
     */
    private String deliveryId;

    /**
     * 配送时间
     */
    private Date postTime;

    /**
     * 邮寄id
     * <p>
     * 京东特有
     */
    private Long ivcPostId;

    /**
     * 配送状态（1-已邮寄；2-已取消）
     * <p>
     * 京东特有
     */
    private Integer state;

    /**
     * 发票物流详情列表
     */
    private List<InvoiceLogisticsResponse> details;

    public static InvoiceDeliveryResponse buildFrom(InvoiceDeliveryOpenResp resp) {
        InvoiceDeliveryResponse response = new InvoiceDeliveryResponse();
        response.setPostCompany(resp.getPostCompany());
        response.setDeliveryId(resp.getDeliveryId());
        response.setPostTime(resp.getPostTime());
        response.setIvcPostId(resp.getIvcPostId());
        response.setState(response.getState());

        return response;
    }

    public static InvoiceDeliveryResponse buildFrom(InvoicelogistGetResponse.Logistics logistics) {
        InvoiceDeliveryResponse response = new InvoiceDeliveryResponse();
        response.setPostCompany(logistics.getExpressCompany());
        response.setDeliveryId(logistics.getLogisticNumber());
        response.setDetails(logistics.getLogisticsInfoList().stream().map(InvoiceLogisticsResponse::buildFrom).collect(Collectors.toList()));

        return response;
    }

    public static InvoiceDeliveryResponse buildFrom(LogistdetailGetResponse.Logistics logistics) {
        InvoiceDeliveryResponse response = new InvoiceDeliveryResponse();
        response.setPostCompany(logistics.getExpressCompany());
        response.setDeliveryId(logistics.getLogisticNumber());
        try {
            response.setPostTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(logistics.getShippingTime()));
        } catch (ParseException e) {
            log.error("格式化配送日期异常", e);
        }
        response.setDetails(logistics.getLogisticsInfoList().stream().map(InvoiceLogisticsResponse::buildFrom).collect(Collectors.toList()));

        return response;
    }
}
