package com.op.admin.service;

import com.op.admin.dto.OauthClientDetailsSaveDTO;
import com.op.admin.dto.OauthClientDetailsDTO;
import com.op.admin.dto.OauthClientDetailsPageQueryDTO;
import com.op.admin.vo.OauthClientDetailsVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * oauth2-client Service
 *
 * @author cdrcool
 */
public interface OauthClientDetailsService {

    /**
     * 保存 oauth2-client
     *
     * @param saveDTO oauth2-client 保存 dto
     */
    void save(OauthClientDetailsSaveDTO saveDTO);

    /**
     * 删除 oauth2-client
     *
     * @param id oauth2-client id
     */
    void deleteById(Integer id);

    /**
     * 批量删除 oauth2-client
     *
     * @param ids oauth2-client ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 查找 oauth2-client
     *
     * @param id oauth2-client id
     * @return oauth2-client vo
     */
    OauthClientDetailsVO findById(Integer id);

    /**
     * 查找 oauth2-client
     *
     * @param clientId 客户端标识
     * @return oauth2-client dto
     */
    OauthClientDetailsDTO findByClientId(String clientId);

    /**
     * 分页查询 oauth2-client
     *
     * @param pageable 分页对象
     * @param queryDTO 查询对象
     * @return oauth2-client vo 分页列表
     */
    Page<OauthClientDetailsVO> queryPage(Pageable pageable, OauthClientDetailsPageQueryDTO queryDTO);
}
