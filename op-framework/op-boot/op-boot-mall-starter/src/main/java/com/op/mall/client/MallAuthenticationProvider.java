package com.op.mall.client;

import com.op.mall.constans.MallType;

/**
 * 电商身份认证凭据提供者接口
 *
 * @author cdrcool
 */
public interface MallAuthenticationProvider {

    /**
     * 获取电商身份认证凭据
     *
     * @param taxpayerId 纳税人识别号
     * @return 电商身份认证凭据
     */
    MallAuthentication getAuthentication(String taxpayerId);

    /**
     * 如果可以处理当前请求，就返回 true
     *
     * @param mallType 电商类型
     * @return true or false
     */
    boolean supports(MallType mallType);
}
