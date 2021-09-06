package com.mall.puirchase.request;

/**
 * @author chengdr01
 */
public interface MallRequestProvider {

    /**
     *
     * @param mallRequest
     * @return
     */
    boolean supports(Class<?> mallRequest);
}
