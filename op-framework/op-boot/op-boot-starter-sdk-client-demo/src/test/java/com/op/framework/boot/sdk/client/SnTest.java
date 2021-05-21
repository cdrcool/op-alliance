package com.op.framework.boot.sdk.client;

import com.op.framework.boot.sdk.client.response.AreaResponse;
import com.op.web.SdkClientDemoApplication;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.govbus.CountyGetRequest;
import com.suning.api.entity.govbus.CountyGetResponse;
import com.suning.api.exception.SuningApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = SdkClientDemoApplication.class)
public class SnTest {
    private DefaultSuningClient snClient;

    @BeforeEach
    public void init() {
        String serverUrl = "https://openxgpre.cnsuning.com/api/http/sopRequest";
        String appKey = "1dbcd1e3a2583dfa865c24b1ce369933";
        String appSecret = "2f57bf027e9d1970d24effb0fedb6f31";
        snClient = new DefaultSuningClient(serverUrl, appKey, appSecret);
    }

    @Test
    public void test() throws SuningApiException {
        CountyGetRequest request = new CountyGetRequest();
        request.setTransportCode("010");

        CountyGetResponse response = snClient.excute(request);

        CountyGetResponse.SnBody snBody = response.getSnbody();
        CountyGetResponse.GetCounty getCounty = snBody.getGetCounty();
        List<AreaResponse> responses = getCounty.getResultInfo().stream().map(AreaResponse::buildFrom).collect(Collectors.toList());
    }
}
