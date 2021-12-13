package com.op.boot.mall.account;

import com.op.boot.mall.token.response.MallTokenResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * 电商账号管理接口
 *
 * @author chengdr01
 */
public interface AccountManager {

    /**
     * 保存电商账号
     *
     * @param account 电商账号
     */
    void save(Account account);

    /**
     * 移除电商账号
     *
     * @param id 主键
     */
    void deleteById(Long id);

    /**
     * 查找电商账号
     *
     * @param id 主键
     * @return 电商账号
     */
    Optional<Account> findById(Long id);

    /**
     * 查找电商账号
     *
     * @param accountName 账号名
     * @param accountType 账号类型
     * @return 电商账号
     */
    Optional<Account> findOne(String accountName, String accountType);

    /**
     * 分页查询电商账号
     *
     * @param accountName 账号名
     * @param pageable    分页信息
     * @return 电商账号分页列表
     */
    Page<Account> queryPage(String accountName, Pageable pageable);

    /**
     * 更新电商 Token 响应
     *
     * @param tokenResponse 电商 Token 响应
     */
    void updateTokenResponse(MallTokenResponse tokenResponse);
}
