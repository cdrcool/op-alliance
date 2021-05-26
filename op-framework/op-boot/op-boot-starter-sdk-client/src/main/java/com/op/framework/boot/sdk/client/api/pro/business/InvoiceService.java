package com.op.framework.boot.sdk.client.api.pro.business;

import com.op.framework.boot.sdk.client.api.InvoiceApi;
import com.op.framework.boot.sdk.client.api.factrory.ThirdApiFactory;
import com.op.framework.boot.sdk.client.api.pro.SdkClient;
import com.op.framework.boot.sdk.client.api.pro.SdkRequest;
import com.op.framework.boot.sdk.client.api.pro.function.ActionNameConstants;
import com.op.framework.boot.sdk.client.api.pro.response.InvoiceApplySubmitResponse;
import com.op.framework.boot.sdk.client.base.ThirdSdkType;
import com.op.framework.boot.sdk.client.request.InvoiceApplySubmitRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 发票 Service
 *
 * @author cdrcool
 */
@Service
public class InvoiceService {
    private final ThirdApiFactory thirdApiFactory;
    private final SdkClient sdkClient;

    public InvoiceService(ThirdApiFactory thirdApiFactory, SdkClient sdkClient) {
        this.thirdApiFactory = thirdApiFactory;
        this.sdkClient = sdkClient;
    }

    public void submitInvoiceApply(String thirdType, Integer orderId, List<Integer> supplyOrderIds) {
        InvoiceApi invoiceApi = thirdApiFactory.getInvoiceApi(thirdType);
        InvoiceApplySubmitRequest invoiceApplySubmitRequest = buildInvoiceApplySubmitRequest(orderId, supplyOrderIds);

        String token = getToken(orderId);
        invoiceApi.submitInvoiceApply(token, invoiceApplySubmitRequest);
    }

    private InvoiceApplySubmitRequest buildInvoiceApplySubmitRequest(Integer orderId, List<Integer> supplyOrderIds) {
        InvoiceApplySubmitRequest invoiceApplySubmitRequest = new InvoiceApplySubmitRequest();

        invoiceApplySubmitRequest.setMarkId(UUID.randomUUID().toString());

        return invoiceApplySubmitRequest;
    }

    public void submitInvoiceApply2(String thirdType, Integer orderId, List<Integer> supplyOrderIds) {
        SdkRequest<InvoiceApplySubmitResponse> sdkRequest = new SdkRequest<>();
        sdkRequest.setSdkType(ThirdSdkType.getByValue(thirdType));
        sdkRequest.setActionName(ActionNameConstants.INVOICE_APPLY_SUBMIT);
        sdkRequest.setBillId(String.valueOf(orderId));
        sdkRequest.setToken(getToken(orderId));

        InvoiceApplySubmitResponse response = sdkClient.execute(sdkRequest);
    }

    private String getToken(Integer orderId) {
        return UUID.randomUUID().toString();
    }

}
