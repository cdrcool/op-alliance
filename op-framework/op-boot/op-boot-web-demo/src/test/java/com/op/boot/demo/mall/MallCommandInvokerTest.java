package com.op.boot.demo.mall;

import com.op.boot.mall.business.requestbuilder.invoice.InvoiceDetailQueryRequestBuilder;
import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.invoker.MallCommandInvoker;
import com.op.boot.mall.response.invoice.InvoiceDetailQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MallCommandInvokerTest {
    @Autowired
    private MallCommandInvoker mallCommandInvoker;

    @Test
    public void queryInvoiceDetail() {
        InvoiceDetailQueryRequestBuilder invoiceDetailQueryRequestBuilder = new InvoiceDetailQueryRequestBuilder();
        invoiceDetailQueryRequestBuilder.setMallType(MallType.JD.getValue());
        invoiceDetailQueryRequestBuilder.setAccountName("保利总部VOP1");
        InvoiceDetailQueryResponse response = mallCommandInvoker.invoke(invoiceDetailQueryRequestBuilder.buildRequest());
    }
}
