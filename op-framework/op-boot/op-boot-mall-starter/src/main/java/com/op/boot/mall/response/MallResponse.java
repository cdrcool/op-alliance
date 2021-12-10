package com.op.boot.mall.response;

import com.op.boot.mall.constants.MallType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 电商响应
 *
 * @author chengdr01
 */
@Slf4j
@Data
public class MallResponse implements Serializable {
    /**
     * 电商类型
     */
    private MallType mallType;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 错误响应建造者
     */
    public static class ErrorBuilder {
        /**
         * 电商类型
         */
        private MallType mallType;

        /**
         * 错误码
         */
        private String errorCode;

        /**
         * 错误信息
         */
        private String errorMsg;

        public ErrorBuilder mallType(MallType mallType) {
            this.mallType = mallType;
            return this;
        }

        public ErrorBuilder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public ErrorBuilder errorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
            return this;
        }

        public <T extends MallResponse> T build(Class<T> responseClass) {
            T response;
            try {
                response = responseClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("实例化请求响应类：【" + responseClass.getName() + "】异常", e);
                return null;
            }
            response.setMallType(this.mallType);
            response.setSuccess(false);
            response.setErrorCode(this.errorCode);
            response.setErrorMsg(this.errorMsg);
            return response;
        }
    }
}
