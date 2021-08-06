package com.op.admin.mapping;

import com.op.admin.dto.WhiteResourceSaveDTO;
import com.op.admin.entity.WhiteResource;
import com.op.admin.vo.WhiteResourceVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * 白名单资源 mapping
 *
 * @author cdrcool
 */
@Mapper(componentModel = "spring")
public interface WhiteResourceMapping {

    /**
     * 白名单资源保存 dto -> 白名单资源
     *
     * @param saveDTO 白名单资源保存 dto
     * @return 白名单资源
     */
    WhiteResource toWhiteResource(WhiteResourceSaveDTO saveDTO);

    /**
     * 根据白名单资源保存 dto 更新白名单资源
     *
     * @param saveDTO       白名单资源保存 dto
     * @param WhiteResource 白名单资源
     */
    void update(WhiteResourceSaveDTO saveDTO, @MappingTarget WhiteResource WhiteResource);

    /**
     * 白名单资源 -> 白名单资源 vo
     *
     * @param WhiteResource 白名单资源
     * @return 白名单资源 vo
     */
    WhiteResourceVO toWhiteResourceVO(WhiteResource WhiteResource);

    /**
     * 白名单资源列表 -> 白名单资源 vo 列表
     *
     * @param WhiteResources 白名单资源列表
     * @return 白名单资源 vo 列表
     */
    List<WhiteResourceVO> toWhiteResourceVOList(List<WhiteResource> WhiteResources);
}
