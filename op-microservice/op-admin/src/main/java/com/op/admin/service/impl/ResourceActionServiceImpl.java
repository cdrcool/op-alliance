package com.op.admin.service.impl;

import com.op.admin.dto.ResourceActionSaveDTO;
import com.op.admin.dto.ResourcePathPermissionDto;
import com.op.admin.entity.ResourceAction;
import com.op.admin.mapper.ResourceActionDynamicSqlSupport;
import com.op.admin.mapper.ResourceActionMapper;
import com.op.admin.mapper.extend.ResourceActionMapperExtend;
import com.op.admin.mapping.ResourceActionMapping;
import com.op.admin.service.ResourceActionService;
import com.op.admin.vo.ResourceActionAssignVO;
import com.op.admin.vo.ResourceActionVO;
import com.op.framework.web.common.api.response.ResultCode;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * 资源动作 Service Impl
 *
 * @author cdrcool
 */
@Service
public class ResourceActionServiceImpl implements ResourceActionService {

    private final ResourceActionMapperExtend resourceActionMapper;
    private final ResourceActionMapping resourceActionMapping;

    public ResourceActionServiceImpl(ResourceActionMapperExtend resourceActionMapper, ResourceActionMapping resourceActionMapping) {
        this.resourceActionMapper = resourceActionMapper;
        this.resourceActionMapping = resourceActionMapping;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(ResourceActionSaveDTO saveDTO) {
        // 校验同一资源下，动作名称/动作路径是否重复
        validateActionNameAndActionPath(saveDTO.getResourceId(), saveDTO.getId(), saveDTO.getActionName(), saveDTO.getActionPath());

        if (saveDTO.getId() == null) {
            ResourceAction resourceAction = resourceActionMapping.toResourceAction(saveDTO);
            resourceActionMapper.insert(resourceAction);
        } else {
            Integer id = saveDTO.getId();
            ResourceAction resourceAction = resourceActionMapper.selectByPrimaryKey(id)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的资源动作"));
            resourceActionMapping.update(saveDTO, resourceAction);
            resourceActionMapper.updateByPrimaryKey(resourceAction);
        }
    }

    /**
     * 校验同一资源下，动作名称/动作路径是否重复
     *
     * @param resourceId 资源分类 id
     * @param id         主键
     * @param actionName 动作名称
     * @param actionPath 动作路径
     */
    private void validateActionNameAndActionPath(Integer resourceId, Integer id, String actionName, String actionPath) {
        SelectStatementProvider selectStatementProvider = select(ResourceActionDynamicSqlSupport.actionName, ResourceActionDynamicSqlSupport.actionPath)
                .from(ResourceActionDynamicSqlSupport.resourceAction)
                .where(ResourceActionDynamicSqlSupport.resourceId, isEqualTo(resourceId))
                .and(ResourceActionDynamicSqlSupport.actionName, isEqualTo(actionName), or(ResourceActionDynamicSqlSupport.actionPath, isEqualTo(actionPath)))
                .and(ResourceActionDynamicSqlSupport.id, isNotEqualToWhenPresent(id))
                .build().render(RenderingStrategies.MYBATIS3);
        Optional<ResourceAction> optional = resourceActionMapper.selectOne(selectStatementProvider);
        optional.ifPresent(resourceAction -> {
            if (actionName.equals(resourceAction.getActionName())) {
                throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "已存在相同动作名称的资源动作，动作名称不能重复");
            } else {
                throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "已存在相同动作名称的资源动作，动作名称不能重复");
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            DeleteStatementProvider deleteStatementProvider = deleteFrom(ResourceActionDynamicSqlSupport.resourceAction)
                    .where(ResourceActionDynamicSqlSupport.id, isIn(ids))
                    .build().render(RenderingStrategies.MYBATIS3);
            resourceActionMapper.delete(deleteStatementProvider);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByResourceId(Integer resourceId) {
        DeleteStatementProvider deleteStatementProvider = deleteFrom(ResourceActionDynamicSqlSupport.resourceAction)
                .where(ResourceActionDynamicSqlSupport.resourceId, isEqualTo(resourceId))
                .build().render(RenderingStrategies.MYBATIS3);
        resourceActionMapper.delete(deleteStatementProvider);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<ResourceActionVO> findByResourceId(Integer resourceId) {
        SelectStatementProvider selectStatementProvider = select(ResourceActionMapper.selectList)
                .from(ResourceActionDynamicSqlSupport.resourceAction)
                .where(ResourceActionDynamicSqlSupport.resourceId, isEqualTo(resourceId))
                .orderBy(ResourceActionDynamicSqlSupport.actionNo)
                .build().render(RenderingStrategies.MYBATIS3);
        List<ResourceAction> actions = resourceActionMapper.selectMany(selectStatementProvider);
        return resourceActionMapping.toResourceActionVOList(actions);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> findIdsByResourceId(Integer resourceId) {
        SelectStatementProvider selectStatementProvider = select(ResourceActionDynamicSqlSupport.id)
                .from(ResourceActionDynamicSqlSupport.resourceAction)
                .where(ResourceActionDynamicSqlSupport.resourceId, isEqualTo(resourceId))
                .build().render(RenderingStrategies.MYBATIS3);
        List<ResourceAction> actions = resourceActionMapper.selectMany(selectStatementProvider);
        return actions.stream().map(ResourceAction::getId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Map<Integer, List<ResourceActionVO>> findByResourceIds(List<Integer> resourceIds) {
        SelectStatementProvider selectStatementProvider = select(ResourceActionMapper.selectList)
                .from(ResourceActionDynamicSqlSupport.resourceAction)
                .where(ResourceActionDynamicSqlSupport.resourceId, isIn(resourceIds))
                .orderBy(ResourceActionDynamicSqlSupport.actionNo)
                .build().render(RenderingStrategies.MYBATIS3);
        List<ResourceAction> actions = resourceActionMapper.selectMany(selectStatementProvider);
        List<ResourceActionVO> list = resourceActionMapping.toResourceActionVOList(actions);
        return list.stream().collect(Collectors.groupingBy(ResourceActionVO::getResourceId));
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Map<Integer, List<ResourceActionAssignVO>> findAllForAssign() {
        SelectStatementProvider selectStatementProvider =
                select(ResourceActionDynamicSqlSupport.id, ResourceActionDynamicSqlSupport.resourceId, ResourceActionDynamicSqlSupport.actionName)
                        .from(ResourceActionDynamicSqlSupport.resourceAction)
                        .orderBy(ResourceActionDynamicSqlSupport.actionNo)
                        .build().render(RenderingStrategies.MYBATIS3);
        List<ResourceAction> actions = resourceActionMapper.selectMany(selectStatementProvider);
        List<ResourceActionAssignVO> actionAssignList = resourceActionMapping.toResourceActionAssignVOVOList(actions);
        return actionAssignList.stream().collect(Collectors.groupingBy(ResourceActionAssignVO::getResourceId));
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<String> getPermissions(List<Integer> ids) {
        SelectStatementProvider selectStatementProvider =
                select(ResourceActionDynamicSqlSupport.permission)
                        .from(ResourceActionDynamicSqlSupport.resourceAction)
                        .where(ResourceActionDynamicSqlSupport.id, isIn(ids))
                        .build().render(RenderingStrategies.MYBATIS3);
        List<ResourceAction> actions = resourceActionMapper.selectMany(selectStatementProvider);
        return actions.stream().map(ResourceAction::getPermission).collect(Collectors.toList());
    }

    @Cacheable(value = "resource", key = "'resourcePathPermissions'", sync = true)
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public Map<String, String> initResourcePathPermissions() {
        Map<String, String> results = new HashMap<>(64);
        List<ResourcePathPermissionDto> list = resourceActionMapper.queryPathPermissions();
        list.forEach(item -> {
            String actionPath = item.getActionPath();
            if (StringUtils.isNotBlank(actionPath)) {
                List<String> paths = Arrays.stream(actionPath.split(","))
                        .map(path -> "/" + item.getServerName() + item.getResourcePath() + path)
                        .collect(Collectors.toList());
                results.putAll(paths.stream().collect(Collectors.toMap(path -> path, path -> item.getPermission())));
            }
        });
        return results;
    }
}
