package com.op.boot.mall.receiver;

import com.op.boot.mall.receiver.config.ZkhMallClientFeignConfig;
import com.op.boot.mall.request.invoice.custom.ZkhMallInvoiceApplySubmitRequest;
import com.op.boot.mall.request.invoice.custom.ZkhMallInvoiceDetailQueryRequest;
import com.op.boot.mall.response.ZkhMallBaseResponse;
import com.op.boot.mall.response.invoice.custom.ZkhInvoiceApplySubmitResponse;
import com.op.boot.mall.response.invoice.custom.ZkhInvoiceDetailQueryResponse;
import com.op.boot.mall.token.request.zkh.ZkhTokenAcquireRequest;
import com.op.boot.mall.token.request.zkh.ZkhTokenRefreshRequest;
import com.op.boot.mall.token.response.ZkhMallTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 震坤行 Client Feign
 *
 * @author chengdr01
 */
@FeignClient(name = "zkh-mall", url = "${mall.zkh.server-url}", configuration = ZkhMallClientFeignConfig.class)
public interface ZkhMallClientFeign {

    /**
     * 获取访问令牌
     *
     * @param acquireRequest 请求对象
     * @return 请求响应
     */
    @PostMapping("/accessToken")
    ZkhMallBaseResponse<ZkhMallTokenResponse> getAccessToken(@RequestBody ZkhTokenAcquireRequest acquireRequest);

    /**
     * 刷新 token
     *
     * @param refreshRequest 请求对象
     * @return 请求响应
     */
    @PostMapping("/refresh_token")
    ZkhMallTokenResponse refreshToken(@RequestBody ZkhTokenRefreshRequest refreshRequest);

    /**
     * 发票详情查询
     *
     * @param request 请求参数
     * @return 请求响应
     */
    @PostMapping("/invoice/query")
    ZkhMallBaseResponse<ZkhInvoiceDetailQueryResponse> invoiceQueryDetail(@RequestBody ZkhMallInvoiceDetailQueryRequest request);

    /**
     * 发票申请提交
     *
     * @param request 请求参数
     * @return 请求响应
     */
    @PostMapping("/invoice/apply")
    ZkhMallBaseResponse<ZkhInvoiceApplySubmitResponse> invoiceSubmitApply(@RequestBody ZkhMallInvoiceApplySubmitRequest request);

}
