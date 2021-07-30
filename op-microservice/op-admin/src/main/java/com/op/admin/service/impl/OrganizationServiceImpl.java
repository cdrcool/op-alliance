package com.op.admin.service.impl;

import com.op.admin.dto.OrganizationSaveDTO;
import com.op.admin.dto.OrganizationTreeQueryDTO;
import com.op.admin.entity.Organization;
import com.op.admin.entity.OrganizationResourceActionRelation;
import com.op.admin.entity.OrganizationRoleRelation;
import com.op.admin.mapper.*;
import com.op.admin.mapper.extend.OrganizationMapperExtend;
import com.op.admin.mapping.OrganizationMapping;
import com.op.admin.service.OrganizationService;
import com.op.admin.service.ResourceCategoryService;
import com.op.admin.service.RoleService;
import com.op.admin.service.UserService;
import com.op.admin.utils.TreeUtils;
import com.op.admin.vo.*;
import com.op.framework.web.common.api.response.ResultCode;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

/**
 * 组织 Service Impl
 *
 * @author cdrcool
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationMapperExtend organizationMapper;
    private final OrganizationMapping organizationMapping;
    private final OrganizationRoleRelationMapper organizationRoleRelationMapper;
    private final OrganizationResourceActionRelationMapper organizationResourceActionRelationMapper;
    private final UserService userService;
    private final RoleService roleService;
    private final ResourceCategoryService resourceCategoryService;

    public OrganizationServiceImpl(OrganizationMapperExtend organizationMapper, OrganizationMapping organizationMapping,
                                   OrganizationRoleRelationMapper organizationRoleRelationMapper,
                                   OrganizationResourceActionRelationMapper organizationResourceActionRelationMapper,
                                   @Lazy UserService userService,
                                   RoleService roleService,
                                   ResourceCategoryService resourceCategoryService) {
        this.organizationMapper = organizationMapper;
        this.organizationMapping = organizationMapping;
        this.organizationRoleRelationMapper = organizationRoleRelationMapper;
        this.organizationResourceActionRelationMapper = organizationResourceActionRelationMapper;
        this.userService = userService;
        this.roleService = roleService;
        this.resourceCategoryService = resourceCategoryService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(OrganizationSaveDTO saveDTO) {
        // 校验同一组织下，下级组织名称是否重复
        validateOrgName(saveDTO.getPid(), saveDTO.getId(), saveDTO.getOrgName());
        // 校验同一组织下，下级组织编码是否重复
        validateOrgCode(saveDTO.getPid(), saveDTO.getId(), saveDTO.getOrgCode());

        if (saveDTO.getId() == null) {
            Organization organization = organizationMapping.toOrganization(saveDTO);
            setOrganizationProps(organization, saveDTO.getPid());
            organizationMapper.insert(organization);
        } else {
            Integer id = saveDTO.getId();
            Organization organization = organizationMapper.selectByPrimaryKey(id)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的组织"));
            setOrganizationProps(organization, saveDTO.getPid());
            organizationMapping.update(saveDTO, organization);
            organizationMapper.updateByPrimaryKey(organization);
        }
    }

    /**
     * 校验同一组织下，下级组织名称是否重复
     *
     * @param pid     父 id
     * @param id      主键
     * @param orgName 组织名称
     */
    private void validateOrgName(Integer pid, Integer id, String orgName) {
        SelectStatementProvider selectStatementProvider = countFrom(OrganizationDynamicSqlSupport.organization)
                .where(OrganizationDynamicSqlSupport.pid, isEqualTo(pid))
                .and(OrganizationDynamicSqlSupport.orgName, isEqualTo(orgName))
                .and(OrganizationDynamicSqlSupport.id, isNotEqualToWhenPresent(id))
                .build().render(RenderingStrategies.MYBATIS3);
        long count = organizationMapper.count(selectStatementProvider);
        if (count > 0) {
            throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "同一组织下，已存在相同组织名称的下级组织，组织名称不能重复");
        }
    }

    /**
     * 校验同一组织下，下级组织编码是否重复
     *
     * @param pid     父 id
     * @param id      主键
     * @param orgCode 组织编码
     */
    private void validateOrgCode(Integer pid, Integer id, String orgCode) {
        SelectStatementProvider selectStatementProvider = countFrom(OrganizationDynamicSqlSupport.organization)
                .where(OrganizationDynamicSqlSupport.pid, isEqualTo(pid))
                .and(OrganizationDynamicSqlSupport.orgCode, isEqualTo(orgCode))
                .and(OrganizationDynamicSqlSupport.id, isNotEqualToWhenPresent(id))
                .build().render(RenderingStrategies.MYBATIS3);
        long count = organizationMapper.count(selectStatementProvider);
        if (count > 0) {
            throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "同一父组织下，已存在相同组织编码的下级组织，组织编码不能重复");
        }
    }

    /**
     * 设置组织的 pid、orgCodeLink 等属性
     *
     * @param organization 组织
     * @param pid          上级组织 id
     */
    private void setOrganizationProps(Organization organization, Integer pid) {
        Organization pOrg = organizationMapper.selectByPrimaryKey(pid)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + pid + "】的上级组织"));
        String orgCodeLink = pOrg.getOrgCodeLink();
        orgCodeLink = StringUtils.isNoneBlank(orgCodeLink) ? orgCodeLink + "." + organization.getOrgCode() : organization.getOrgCode();
        organization.setOrgCodeLink(orgCodeLink);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        // 获取本下级组织 ids
        List<Integer> childrenIds = organizationMapper.getChildrenIds(id);

        // 删除本下级组织
        DeleteStatementProvider deleteStatementProvider = deleteFrom(OrganizationDynamicSqlSupport.organization)
                .where(OrganizationDynamicSqlSupport.id, isIn(childrenIds))
                .build().render(RenderingStrategies.MYBATIS3);
        organizationMapper.delete(deleteStatementProvider);

        // 删除本下级组织下所有用户
        userService.deleteByOrgIds(childrenIds);

        // 删除本下级组织的组织-角色关联列表
        DeleteStatementProvider organizationRoleRelationProvider = deleteFrom(OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation)
                .where(OrganizationRoleRelationDynamicSqlSupport.orgId, isIn(childrenIds))
                .build().render(RenderingStrategies.MYBATIS3);
        organizationRoleRelationMapper.delete(organizationRoleRelationProvider);

        // 删除本下级组织的组织-资源动作关联列表
        DeleteStatementProvider organizationResourceActionRelationProvider = deleteFrom(OrganizationResourceActionRelationDynamicSqlSupport.organizationResourceActionRelation)
                .where(OrganizationRoleRelationDynamicSqlSupport.orgId, isIn(childrenIds))
                .build().render(RenderingStrategies.MYBATIS3);
        organizationResourceActionRelationMapper.delete(organizationResourceActionRelationProvider);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        ids.forEach(this::deleteById);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public OrganizationVO findById(Integer id) {
        return organizationMapper.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的组织"));
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public OrganizationTreeVO queryTree(OrganizationTreeQueryDTO queryDTO) {
        Integer pid = Optional.ofNullable(queryDTO.getPid()).orElse(-1);

        List<Organization> organizations = new ArrayList<>();
        if (StringUtils.isBlank(queryDTO.getKeyword())) {
            SelectStatementProvider rootSelectStatementProvider = select(OrganizationMapper.selectList)
                    .from(OrganizationDynamicSqlSupport.organization)
                    .where(OrganizationDynamicSqlSupport.pid, isEqualTo(pid))
                    .build().render(RenderingStrategies.MYBATIS3);
            Optional<Organization> optional = organizationMapper.selectOne(rootSelectStatementProvider);
            optional.ifPresent(organization -> {
                SelectStatementProvider selectStatementProvider = select(OrganizationMapper.selectList)
                        .from(OrganizationDynamicSqlSupport.organization)
                        .where(OrganizationDynamicSqlSupport.orgCodeLink, isLike(organization.getOrgCodeLink()).map(v -> v + "%"))
                        .build().render(RenderingStrategies.MYBATIS3);

                organizations.addAll(organizationMapper.selectMany(selectStatementProvider));
            });
        } else {
            SelectStatementProvider partSelectStatementProvider = select(OrganizationDynamicSqlSupport.orgCodeLink)
                    .from(OrganizationDynamicSqlSupport.organization)
                    .where(OrganizationDynamicSqlSupport.orgName, isLike(queryDTO.getKeyword())
                            .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%"))
                    .build().render(RenderingStrategies.MYBATIS3);
            List<String> orgCodeLinks = organizationMapper.selectMany(partSelectStatementProvider).stream()
                    .map(Organization::getOrgCodeLink).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(orgCodeLinks)) {
                organizations.addAll(orgCodeLinks.stream()
                        .map(organizationMapper::findAllInPath)
                        .flatMap(Collection::stream)
                        .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing(Organization::getOrgCodeLink))), ArrayList::new)));
            }
        }

        List<OrganizationTreeVO> treeList = TreeUtils.buildTree(
                organizations,
                organizationMapping::toOrganizationTreeVO,
                OrganizationTreeVO::getPid,
                OrganizationTreeVO::getId,
                (parent, children) -> parent.setChildren(children.stream()
                        .sorted(Comparator.comparing(OrganizationTreeVO::getOrgCode))
                        .collect(Collectors.toList())), pid);
        return CollectionUtils.isNotEmpty(treeList) ? treeList.get(0) : new OrganizationTreeVO();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignRoles(Integer id, List<Integer> roleIds) {
        // 获取继承的分配的角色 ids
        List<Integer> inheritedRoleIds = this.loadInheritedAssignedRoleIds(id);

        // 获取已建立关联的角色 ids
        List<Integer> preRoleIds = this.getAssignedRoleIds(id);

        // 获取要新建关联的角色 ids
        List<Integer> toAddRoleIds = roleIds.stream()
                .filter(roleId -> !inheritedRoleIds.contains(roleId))
                .filter(roleId -> !preRoleIds.contains(roleId)).collect(Collectors.toList());

        // 获取要删除关联的角色 ids
        List<Integer> toDelRoleIds = preRoleIds.stream()
                .filter(actionId -> !roleIds.contains(actionId)).collect(Collectors.toList());

        // 插入要新建的组织-角色关联
        List<OrganizationRoleRelation> relations = toAddRoleIds.stream()
                .map(roleId -> {
                    OrganizationRoleRelation relation = new OrganizationRoleRelation();
                    relation.setOrgId(id);
                    relation.setRoleId(roleId);

                    return relation;
                }).collect(Collectors.toList());
        relations.forEach(organizationRoleRelationMapper::insert);

        // 删除要删除的组织-角色关联
        if (!CollectionUtils.isEmpty(toDelRoleIds)) {
            DeleteStatementProvider deleteStatementProvider =
                    deleteFrom(OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation)
                            .where(OrganizationRoleRelationDynamicSqlSupport.orgId, isEqualTo(id))
                            .and(OrganizationRoleRelationDynamicSqlSupport.roleId, isIn(toDelRoleIds))
                            .build().render(RenderingStrategies.MYBATIS3);
            organizationRoleRelationMapper.delete(deleteStatementProvider);
        }
    }

    /**
     * 获取继承的分配的角色 ids
     *
     * @param id 用户 id
     * @return 继承的分配的角色 ids
     */
    private List<Integer> loadInheritedAssignedRoleIds(Integer id) {
        List<Integer> parentIds = organizationMapper.getParentsIds(id);
        parentIds.remove(id);

        SelectStatementProvider selectStatementProvider = select(OrganizationRoleRelationDynamicSqlSupport.roleId)
                .from(OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation)
                .where(OrganizationRoleRelationDynamicSqlSupport.orgId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        List<OrganizationRoleRelation> relations = organizationRoleRelationMapper.selectMany(selectStatementProvider);


        return relations.stream().map(OrganizationRoleRelation::getRoleId).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignResourceActions(Integer id, List<Integer> resourceActionIds) {
        // 获取继承的分配的资源动作 ids
        List<Integer> inheritedActionIds = this.loadInheritedAssignedActionIds(id);

        // 获取已建立关联的资源动作 ids
        List<Integer> preActionIds = this.getAssignedResourceActionIds(id);

        // 获取要新建关联的资源动作 ids
        List<Integer> toAddActionIds = resourceActionIds.stream()
                .filter(actionId -> !inheritedActionIds.contains(actionId))
                .filter(actionId -> !preActionIds.contains(actionId)).collect(Collectors.toList());

        // 获取要删除关联的资源动作 ids
        List<Integer> toDelActionIds = preActionIds.stream()
                .filter(actionId -> !resourceActionIds.contains(actionId)).collect(Collectors.toList());

        // 插入要新建的组织-资源动作关联
        List<OrganizationResourceActionRelation> relations = toAddActionIds.stream()
                .map(actionId -> {
                    OrganizationResourceActionRelation relation = new OrganizationResourceActionRelation();
                    relation.setOrgId(id);
                    relation.setActionId(actionId);

                    return relation;
                }).collect(Collectors.toList());
        relations.forEach(organizationResourceActionRelationMapper::insert);

        // 删除要删除的组织-资源动作关联
        if (!CollectionUtils.isEmpty(toDelActionIds)) {
            DeleteStatementProvider deleteStatementProvider =
                    deleteFrom(OrganizationResourceActionRelationDynamicSqlSupport.organizationResourceActionRelation)
                            .where(OrganizationResourceActionRelationDynamicSqlSupport.orgId, isEqualTo(id))
                            .and(OrganizationResourceActionRelationDynamicSqlSupport.actionId, isIn(toDelActionIds))
                            .build().render(RenderingStrategies.MYBATIS3);
            organizationResourceActionRelationMapper.delete(deleteStatementProvider);
        }
    }

    /**
     * 获取继承的分配的资源动作 ids
     *
     * @param id 用户 id
     * @return 继承的分配的资源动作 ids
     */
    private List<Integer> loadInheritedAssignedActionIds(Integer id) {
        List<Integer> parentIds = organizationMapper.getParentsIds(id);
        parentIds.remove(id);

        SelectStatementProvider selectStatementProvider =
                select(OrganizationResourceActionRelationDynamicSqlSupport.orgId, OrganizationResourceActionRelationDynamicSqlSupport.actionId)
                        .from(OrganizationResourceActionRelationDynamicSqlSupport.organizationResourceActionRelation)
                        .where(OrganizationResourceActionRelationDynamicSqlSupport.orgId, isIn(parentIds))
                        .build().render(RenderingStrategies.MYBATIS3);
        List<OrganizationResourceActionRelation> relations = organizationResourceActionRelationMapper.selectMany(selectStatementProvider);

        return relations.stream().map(OrganizationResourceActionRelation::getActionId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getAssignedRoleIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(OrganizationRoleRelationDynamicSqlSupport.roleId)
                .from(OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation)
                .where(OrganizationRoleRelationDynamicSqlSupport.orgId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return organizationRoleRelationMapper.selectMany(selectStatementProvider).stream()
                .map(OrganizationRoleRelation::getRoleId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getAssignedResourceActionIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(OrganizationResourceActionRelationDynamicSqlSupport.actionId)
                .from(OrganizationResourceActionRelationDynamicSqlSupport.organizationResourceActionRelation)
                .where(OrganizationResourceActionRelationDynamicSqlSupport.orgId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return organizationResourceActionRelationMapper.selectMany(selectStatementProvider).stream()
                .map(OrganizationResourceActionRelation::getActionId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<RoleAssignVO> loadRoles(Integer id) {
        List<Integer> parentIds = organizationMapper.getParentsIds(id);

        SelectStatementProvider selectStatementProvider =
                select(OrganizationRoleRelationDynamicSqlSupport.orgId, OrganizationRoleRelationDynamicSqlSupport.roleId)
                        .from(OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation)
                        .where(OrganizationRoleRelationDynamicSqlSupport.orgId, isIn(parentIds))
                        .build().render(RenderingStrategies.MYBATIS3);
        List<OrganizationRoleRelation> relations = organizationRoleRelationMapper.selectMany(selectStatementProvider);
        Map<Integer, List<OrganizationRoleRelation>> relationsMap = relations.stream()
                .collect(Collectors.groupingBy(OrganizationRoleRelation::getRoleId));

        List<RoleAssignVO> roles = roleService.findAllForAssign();
        roles.forEach(role -> {
            List<Integer> orgIds = relationsMap.getOrDefault(role.getId(), new ArrayList<>()).stream()
                    .map(OrganizationRoleRelation::getOrgId).collect(Collectors.toList());
            role.setChecked(CollectionUtils.isNotEmpty(orgIds));
            role.setEnableUncheck(orgIds.stream().allMatch(id::equals));
        });

        return roles;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<ResourceCategoryAssignVO> loadAssignedResources(Integer id) {
        List<Integer> parentIds = organizationMapper.getParentsIds(id);

        SelectStatementProvider selectStatementProvider =
                select(OrganizationResourceActionRelationDynamicSqlSupport.orgId, OrganizationResourceActionRelationDynamicSqlSupport.actionId)
                        .from(OrganizationResourceActionRelationDynamicSqlSupport.organizationResourceActionRelation)
                        .where(OrganizationResourceActionRelationDynamicSqlSupport.orgId, isIn(parentIds))
                        .build().render(RenderingStrategies.MYBATIS3);
        List<OrganizationResourceActionRelation> relations = organizationResourceActionRelationMapper.selectMany(selectStatementProvider);
        Map<Integer, List<OrganizationResourceActionRelation>> relationsMap = relations.stream()
                .collect(Collectors.groupingBy(OrganizationResourceActionRelation::getActionId));

        List<ResourceCategoryAssignVO> categories = resourceCategoryService.findAllForAssign();
        categories.forEach(category ->
                Optional.ofNullable(category.getResources()).orElse(new ArrayList<>()).forEach(resource ->
                        Optional.ofNullable(resource.getActions()).orElse(new ArrayList<>()).forEach(action -> {
                            List<Integer> orgIds = relationsMap.getOrDefault(action.getId(), new ArrayList<>()).stream()
                                    .map(OrganizationResourceActionRelation::getOrgId).collect(Collectors.toList());
                            action.setChecked(CollectionUtils.isNotEmpty(orgIds));
                            action.setEnableUncheck(orgIds.stream().allMatch(id::equals));
                        })));

        return categories;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getParentsIds(Integer id) {
        return organizationMapper.getParentsIds(id);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getChildrenIds(Integer id) {
        return organizationMapper.getChildrenIds(id);
    }

    @Override
    public List<TreeNodeVO> queryTreeSelectList(OrganizationTreeQueryDTO queryDTO) {
        Integer pid = Optional.ofNullable(queryDTO.getPid()).orElse(-1);
        String keyword = queryDTO.getKeyword();
        Integer filteredId = queryDTO.getFilteredId();
        Integer appendedId = queryDTO.getAppendedId();

        Organization organization = organizationMapper.selectByPrimaryKey(filteredId).orElse(new Organization());
        String orgCodeLink = organization.getOrgCodeLink();
        List<Integer> parentIds = appendedId == null ? new ArrayList<>() : getParentsIds(appendedId);

        SelectStatementProvider selectStatementProvider = select(OrganizationMapper.selectList)
                .from(OrganizationDynamicSqlSupport.organization)
                .where(OrganizationDynamicSqlSupport.pid, isEqualTo(pid),
                        // 如果是根组织，则带出一级组织列表
                        or(OrganizationDynamicSqlSupport.pid, isEqualTo(1).filter(v -> pid == -1)),
                        // 如果是要追加的组织，则还要返回该组织及其上级组织列表
                        or(OrganizationDynamicSqlSupport.id, isInWhenPresent(parentIds)))
                .and(OrganizationDynamicSqlSupport.orgName, isLike(keyword)
                        .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%"))
                // 如果是要过滤的组织，则不返回该组织及其下级组织列表
                .and(OrganizationDynamicSqlSupport.orgCodeLink, isNotLike(orgCodeLink)
                        .filter(StringUtils::isNotBlank).map(v -> v + "%"))
                .build().render(RenderingStrategies.MYBATIS3);
        List<Organization> organizations = organizationMapper.selectMany(selectStatementProvider);

        List<TreeNodeVO> nodes = organizationMapping.toTreeNodeVOList(organizations);
        nodes.forEach(node -> node.setIsExpand(parentIds.contains(node.getId())));

        return nodes;
    }
}
