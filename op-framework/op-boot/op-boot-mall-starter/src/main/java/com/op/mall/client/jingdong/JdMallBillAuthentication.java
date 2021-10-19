package com.op.mall.client.jingdong;

import com.op.mall.client.MallAuthentication;
import lombok.Data;

/**
 * 京东电商账单身份认证凭据
 *
 * @author chengdr01
 */
@Data
public class JdMallBillAuthentication implements MallAuthentication {

    /**
     * 访问令牌
     */
    private String accessToken;
}
