package com.op.framework.boot.sdk.client.response;

import com.jd.open.api.sdk.domain.vopfp.QueryInvoiceOpenProvider.response.queryInvoiceDeliveryNo.InvoiceLogisticsInformationOpenResp;
import com.suning.api.entity.govbus.InvoicelogistGetResponse;
import com.suning.api.entity.govbus.LogistdetailGetResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发票物流详情响应对象
 *
 * @author cdrcool
 */
@NoArgsConstructor
@Data
public class InvoiceLogisticsResponse {
    /**
     * 操作主题
     */
    private String opeTitle;

    /**
     * 操作时间
     */
    private String opeTime;

    /**
     * 操作备注
     */
    private String opeRemark;

    /**
     * 操作用户
     * <p>
     * 京东特有
     */
    private String opeName;

    /**
     * 物流单号
     */
    private String waybillCode;

    public static InvoiceLogisticsResponse buildFrom(InvoiceLogisticsInformationOpenResp resp) {
        InvoiceLogisticsResponse response = new InvoiceLogisticsResponse();
        response.setOpeTitle(resp.getOpeTitle());
        response.setOpeTime(resp.getOpeTime());
        response.setOpeRemark(resp.getOpeRemark());
        response.setOpeName(resp.getOpeName());
        response.setWaybillCode(resp.getWaybillCode());

        return response;
    }

    public static InvoiceLogisticsResponse buildFrom(InvoicelogistGetResponse.LogisticsInfoList logisticsInfoList) {
        InvoiceLogisticsResponse response = new InvoiceLogisticsResponse();
        response.setOpeTitle(logisticsInfoList.getOperateState());
        response.setOpeTime(logisticsInfoList.getOperateTime());
        response.setOpeRemark(logisticsInfoList.getOperateState());

        return response;
    }

    public static InvoiceLogisticsResponse buildFrom(LogistdetailGetResponse.LogisticsInfoList logisticsInfoList) {
        InvoiceLogisticsResponse response = new InvoiceLogisticsResponse();
        response.setOpeTitle(logisticsInfoList.getOperateState());
        response.setOpeTime(logisticsInfoList.getOperateTime());
        response.setOpeRemark(logisticsInfoList.getOperateState());

        return response;
    }
}
