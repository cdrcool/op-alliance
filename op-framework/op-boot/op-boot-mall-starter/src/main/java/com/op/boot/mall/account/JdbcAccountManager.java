package com.op.boot.mall.account;

import com.op.boot.mall.token.response.MallTokenResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Jdbc 电商账号管理实现类
 *
 * @author chengdr01
 */
@Component
public class JdbcAccountManager implements AccountManager {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Account account) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Account> findOne(String accountName, String accountType) {
        return Optional.empty();
    }

    @Override
    public Page<Account> queryPage(String accountName, Pageable pageable) {
        return null;
    }

    @Override
    public void updateTokenResponse(MallTokenResponse tokenResponse) {

    }
}
