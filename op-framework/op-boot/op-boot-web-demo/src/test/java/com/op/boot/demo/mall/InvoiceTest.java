package com.op.boot.demo.mall;

import com.op.boot.mall.utils.JsonUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Test
    public void test() {
        String sql = "SELECT * FROM polywuye_failure_invoice";
        List<FailureInvoice> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(FailureInvoice.class));

        RestTemplate restTemplate = new RestTemplateBuilder().build();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("GBK")));

        HttpHeaders httpHeaders = new HttpHeaders();

            //设置 请求头中 的  消息体 数据类型
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            //设置 请求头中 的  希望服务器返回给客户端的 数据类型
            List<MediaType> acceptableMediaTypes = new ArrayList<>();
            httpHeaders.setAccept(acceptableMediaTypes);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);

        list.forEach(item -> {
            String url = "https://bizapi.jd.com/api/invoice/select?token=" + item.getAccessToken() + "&markId=" + item.getMarkId();
            String result = restTemplate.postForObject(url, requestEntity, String.class);
            Response response = JsonUtils.parse(result, Response.class);
            jdbcTemplate.update("UPDATE polywuye_failure_invoice SET reson = ? WHERE mark_id = ?", response.getResultMessage(), item.getMarkId());
        });
    }

    @Test
    public void test2() {
        List<String> sqlList = new ArrayList<>();

        String sql = "SELECT * FROM polywuye_failure_invoice WHERE reson LIKE '%订单不存在于系统中%'";
         List<FailureInvoice> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(FailureInvoice.class));
        list.forEach(item -> {
             List<String> thirdOrderIds = Arrays.stream(item.getThirdOrderIds().split(",")).collect(Collectors.toList());
             thirdOrderIds.forEach(orderId -> {
                 String sql2 = "UPDATE cl_supply_order_account SET invoice_status = 0 WHERE supplier_supply_sn = '" + orderId + "';";
                 sqlList.add(sql2);
             });
        });

        sqlList.forEach(System.out::println);
    }

    @Data
    public static class FailureInvoice {
        private String markId;

        private String accessToken;

        private String reason;

        private String thirdOrderIds;
    }

    @Data
    public static class Response {
        private Boolean success;
        private String resultMessage;
        private String resultCode;
        private Object result;
    }
}
