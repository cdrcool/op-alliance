package com.op.sdk.client.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公司信息
 *
 * @author cdr_c
 */
@Data
@TableName("sdk_company_info")
public class CompanyInfo {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 纳税人识别号
     */
    @ApiModelProperty(value = "纳税人识别号")
    private String taxpayerId;

    /**
     * 公司电话
     */
    @ApiModelProperty(value = "公司电话")
    private String companyPhone;

    /**
     * 公司注册地址
     */
    @ApiModelProperty(value = "公司注册地址")
    private String companyRegisteredAddress;

    /**
     * 开户行名称
     */
    @ApiModelProperty(value = "开户行名称")
    private String bankName;

    /**
     * 开户行账号
     */
    @ApiModelProperty(value = "开户行账号")
    private String bankAccount;

    /**
     * 京东帐号
     */
    private String jdAccount;

    /**
     * 苏宁帐号
     */
    private String snAccount;
}
