package com.op.admin.mapping;

import com.op.admin.dto.OauthClientDetailsDTO;
import com.op.admin.dto.OauthClientDetailsSaveDTO;
import com.op.admin.entity.OauthClientDetails;
import com.op.admin.vo.OauthClientDetailsVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * oauth2-client mapping
 *
 * @author cdrcool
 */
@Mapper(componentModel = "spring")
public interface OauthClientDetailsMapping {

    /**
     * oauth2-client 保存 dto -> oauth2-client
     *
     * @param saveDTO oauth2-client 保存 dto
     * @return oauth2-client
     */
    OauthClientDetails toOauthClientDetails(OauthClientDetailsSaveDTO saveDTO);

    /**
     * 根据oauth2-client保存 dto 更新 oauth2-client
     *
     * @param saveDTO oauth2-client 保存 dto
     * @param client  oauth2-client
     */
    void update(OauthClientDetailsSaveDTO saveDTO, @MappingTarget OauthClientDetails client);

    /**
     * oauth2-client -> oauth2-client vo
     *
     * @param client oauth2-client
     * @return oauth2-client vo
     */
    OauthClientDetailsVO toOauthClientDetailsVO(OauthClientDetails client);

    /**
     * oauth2-client 列表 -> oauth2-client vo 列表
     *
     * @param clients oauth2-client 列表
     * @return oauth2-client vo 列表
     */
    List<OauthClientDetailsVO> toOauthClientDetailsVOList(List<OauthClientDetails> clients);

    /**
     * oauth2-client -> oauth2-client dto
     *
     * @param client oauth2-client
     * @return oauth2-client dto
     */
    OauthClientDetailsDTO toOauthClientDetailsDTO(OauthClientDetails client);
}
