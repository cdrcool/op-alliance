package com.op.mall.client.jingdong;

import com.op.mall.response.jd.JdBaseResponse;
import com.op.mall.response.jd.JdBillResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 京东电商对账 Feign
 *
 * @author chengdr01
 */
public interface JdMallBillFeign {

    /**
     * 拉取京东账单对账单
     *
     * @param token    访问令牌
     * @param pageNo   页码
     * @param pageSize 页大小
     * @param billId   账单号
     * @param orderId  订单号
     * @return 京东账单响应
     */
    @PostMapping({"/jincai/getBillDetail"})
    JdBaseResponse<JdBillResponse> getBillDetail(@RequestParam("token") String token,
                                                 @RequestParam("pageNo") Integer pageNo,
                                                 @RequestParam("pageSize") Integer pageSize,
                                                 @RequestParam("billId") String billId,
                                                 @RequestParam("orderId") String orderId);
}
