package com.op.boot.mall.handler.jingdong.bill;

import com.op.boot.mall.client.jingdong.JdBaseResponse;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.bill.JdMallBillAuthentication;
import com.op.boot.mall.client.jingdong.bill.JdMallBillFeign;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.bill.BillPullAccountsRequest;
import com.op.boot.mall.response.bill.BillPullOrdersResponse;
import com.op.boot.mall.response.bill.jingdong.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 京东账单拉取对账单处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdBillPullAccountsHandler extends JdMallRequestHandler<BillPullAccountsRequest<Void>,
        Void, BillPullOrdersResponse> {
    private final JdMallBillFeign jdMallBillFeign;

    public JdBillPullAccountsHandler(JdMallClient jdMallClient, JdMallBillFeign jdMallBillFeign) {
        super(jdMallClient);
        this.jdMallBillFeign = jdMallBillFeign;
    }

    @Override
    public BillPullOrdersResponse handle(BillPullAccountsRequest<Void> request) {
        // 1. 获取京东请求凭证
        JdMallBillAuthentication authentication = (JdMallBillAuthentication) request.getAuthentication();

        // 2. 获取请求参数
        String beginPeriod = request.getBeginPeriod();
        String endPeriod = request.getEndPeriod();

        // 3. 获取起止期间内所有账单号
        List<String> billNos = pullBillNos(authentication.getAccessToken(), beginPeriod, endPeriod);
        log.info("账单号：{}", billNos);

        // 4. 获取账单号下所有订单号
        List<String> orderNos = billNos.stream()
                .map(billNo -> pullOrderNos(authentication.getAccessToken(), billNo))
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
        log.info("订单号：{}", orderNos);

        // 5. 根据订单号查询获取订单详情
        List<JdBillOrderDetailResponse> orderDetails = orderNos.stream()
                .map(orderNo -> pullJdBills(authentication.getAccessToken(), null, null, null, orderNo))
                .map(JdBillResponse::getOrderDetail)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // 6. 转换为标准请求响应
        BillPullOrdersResponse response = new BillPullOrdersResponse();
        response.setItems(orderDetails.stream()
                .map(orderDetail -> {
                    BillPullOrdersResponse.BillPullBillsItemResponse itemResponse = new BillPullOrdersResponse.BillPullBillsItemResponse();
                    itemResponse.setOrderNo(orderDetail.getOrderNo());
                    itemResponse.setOrderAmount(orderDetail.getOrderAmount());
                    itemResponse.setRefundAmount(orderDetail.getSumRefundAmount());
                    return itemResponse;
                })
                .collect(Collectors.toList()));

        return response;
    }

    private JdBillResponse pullJdBills(String token, Integer pageNo, Integer pageSize, String billId, String orderId) {
        JdBaseResponse<JdBillResponse> jdBaseResponse = jdMallBillFeign.getBillDetail(
                token,
                pageNo,
                pageSize,
                billId,
                orderId);
        if (!Objects.equals(jdBaseResponse.getSuccess(), true)) {
            log.error("拉取京东对账单失败，错误码：{}，错误消息：{}", jdBaseResponse.getResultCode(), jdBaseResponse.getResultMessage());
            throw new JdMallException(jdBaseResponse.getResultCode(), "拉取京东对账单失败：" + jdBaseResponse.getResultMessage());
        }
        return jdBaseResponse.getResult();
    }

    private List<String> pullBillNos(String token, String beginPeriod, String endPeriod) {
        List<String> allBillNos = new ArrayList<>();

        int pageIndex = 1;
        int pageSize = 100;
        List<String> billNos;
        do {
            JdBillResponse billResponse = pullJdBills(token, pageIndex, pageSize, null, null);
            JdBillInfoResponse billInfoResponse = Optional.ofNullable(billResponse.getBillInfo()).orElse(new JdBillInfoResponse());
            billNos = Optional.ofNullable(billInfoResponse.getResults()).orElse(new ArrayList<>()).stream()
                    .map(JdBillItemResponse::getBillNo)
                    .filter(billNo -> formatJdBillNo(billNo).compareTo(beginPeriod) >= 0)
                    .collect(Collectors.toList());
            allBillNos.addAll(billNos.stream().filter(billNo -> formatJdBillNo(billNo).compareTo(endPeriod) <= 0).collect(Collectors.toList()));

            pageIndex++;
        } while (billNos.size() == pageSize);

        return allBillNos;
    }

    private List<String> pullOrderNos(String token, String billNo) {
        List<String> allOrderNos = new ArrayList<>();

        int pageIndex = 1;
        int pageSize = 100;
        List<String> orderNos;
        do {
            JdBillResponse billResponse = pullJdBills(token, pageIndex, pageSize, billNo, null);
            JdBillOrderStatusResponse billOrderStatusResponse = Optional.ofNullable(billResponse.getOrderStatusDetail()).orElse(new JdBillOrderStatusResponse());
            orderNos = Optional.ofNullable(billOrderStatusResponse.getResults()).orElse(new ArrayList<>()).stream()
                    .map(JdBillOrderItemResponse::getOrderNo)
                    .collect(Collectors.toList());
            allOrderNos.addAll(orderNos);

            pageIndex++;
        } while (orderNos.size() == pageSize);

        return allOrderNos;
    }

    private String formatJdBillNo(String jdBillNo) {
        // 京东账单号格式为：BL2021080100315151107861N2，其中月份比账期的月份要大 1
        String year = jdBillNo.substring(2, 6);
        String month = jdBillNo.substring(6, 8);
        int monthValue = Integer.parseInt(month) - 1;
        return year + "-" + (month.startsWith("0") ? "0" + monthValue : monthValue);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG_BILL, MallMethodConstants.BILL_PULL_ACCOUNTS), this);
    }
}
