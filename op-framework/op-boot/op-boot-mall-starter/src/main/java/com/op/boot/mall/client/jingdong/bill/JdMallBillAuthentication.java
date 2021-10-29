package com.op.boot.mall.client.jingdong.bill;

import com.op.boot.mall.client.MallAuthentication;
import lombok.Data;

/**
 * 京东电商账单身份认证凭据
 *
 * @author cdrcool
 */
@Data
public class JdMallBillAuthentication implements MallAuthentication {

    /**
     * 访问令牌
     */
    private String accessToken;
}
