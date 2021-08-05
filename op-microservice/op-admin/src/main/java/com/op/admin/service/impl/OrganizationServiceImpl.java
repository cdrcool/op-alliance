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
        Integer id = saveDTO.getId();
        Integer pid = saveDTO.getPid();

        // 校验同一组织下，下级组织名称/组织编码是否重复
        validateOrgNameAndOrgCode(pid, id, saveDTO.getOrgName(), saveDTO.getOrgCode());

        // 获取上级组织
        Organization parent = organizationMapper.selectByPrimaryKey(pid)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + pid + "】的上级组织"));

        if (id == null) {
            Organization organization = organizationMapping.toOrganization(saveDTO);
            organization.setOrgCodeLink(parent.getOrgCodeLink() + "." + organization.getOrgCodeLink());
            organizationMapper.insert(organization);
        } else {
            Organization organization = organizationMapper.selectByPrimaryKey(id)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的组织"));
            // 记录前组织编码链
            String preOrgCodeLink = organization.getOrgCodeLink();
            // 设置当前组织编码连
            String curOrgCodeLink = parent.getOrgCodeLink() + "." + organization.getOrgCode();
            organization.setOrgCodeLink(curOrgCodeLink);

            // 更新组织
            organizationMapping.update(saveDTO, organization);
            organizationMapper.updateByPrimaryKey(organization);

            // 如果组织编码连发生变化，则更新所有下级的组织编码连
            if (!curOrgCodeLink.equals(preOrgCodeLink)) {
                organizationMapper.updateChildrenOrgCodeLink(preOrgCodeLink, curOrgCodeLink);
            }
        }
    }

    /**
     * 校验同一组织下，下级组织名称/组织编码是否重复
     *
     * @param pid     父 id
     * @param id      主键
     * @param orgName 组织名称
     */
    private void validateOrgNameAndOrgCode(Integer pid, Integer id, String orgName, String orgCode) {
        SelectStatementProvider selectStatementProvider = countFrom(OrganizationDynamicSqlSupport.organization)
                .where(OrganizationDynamicSqlSupport.pid, isEqualTo(pid))
                .and(OrganizationDynamicSqlSupport.orgName, isEqualTo(orgName), or(OrganizationDynamicSqlSupport.orgCode, isEqualTo(orgCode)))
                .and(OrganizationDynamicSqlSupport.id, isNotEqualToWhenPresent(id))
                .build().render(RenderingStrategies.MYBATIS3);
        Optional<Organization> optional = organizationMapper.selectOne(selectStatementProvider);
        optional.ifPresent(organization -> {
            if (orgName.equals(organization.getOrgName())) {
                throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "同一组织下，已存在相同组织名称的下级组织，组织名称不能重复");
            } else {
                throw new BusinessException(ResultCode.PARAM_VALID_ERROR, "同一父组织下，已存在相同组织编码的下级组织，组织编码不能重复");
            }
        });
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
    public List<OrganizationTreeVO> queryTreeList(OrganizationTreeQueryDTO queryDTO) {
        queryDTO.setPid(Optional.ofNullable(queryDTO.getPid()).orElse(-1));
        List<Organization> organizations = queryAllOrganizationsInChain(queryDTO);
        List<OrganizationTreeVO> treeList = TreeUtils.buildTree(
                organizations,
                organizationMapping::toOrganizationTreeVO,
                OrganizationTreeVO::getPid,
                OrganizationTreeVO::getId,
                (parent, children) -> parent.setChildren(children.stream()
                        .sorted(Comparator.comparing(OrganizationTreeVO::getOrgCode))
                        .collect(Collectors.toList())),
                queryDTO.getPid());
        return CollectionUtils.isNotEmpty(treeList) ? treeList : new ArrayList<>();
    }

    /**
     * 查询满足条件的组织所在的组织链中的所有组织列表
     *
     * @param queryDTO 组织树查询对象
     * @return 组织列表
     */
    private List<Organization> queryAllOrganizationsInChain(OrganizationTreeQueryDTO queryDTO) {
        String keyword = queryDTO.getKeyword();

        SelectStatementProvider childrenSelectStatementProvider = select(OrganizationDynamicSqlSupport.orgCodeLink)
                .from(OrganizationDynamicSqlSupport.organization)
                .where(OrganizationDynamicSqlSupport.pid, isEqualTo(queryDTO.getPid()))
                .build().render(RenderingStrategies.MYBATIS3);
        Set<String> orgCodeLinks = organizationMapper.selectMany(childrenSelectStatementProvider).stream()
                .map(Organization::getOrgCodeLink).collect(Collectors.toSet());

        if (StringUtils.isNotBlank(keyword)) {
            SelectStatementProvider partSelectStatementProvider = select(OrganizationDynamicSqlSupport.orgCodeLink)
                    .from(OrganizationDynamicSqlSupport.organization)
                    .where(OrganizationDynamicSqlSupport.orgName, isLike(queryDTO.getKeyword())
                            .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%"))
                    .build().render(RenderingStrategies.MYBATIS3);
            List<String> partOrgCodeLinks = organizationMapper.selectMany(partSelectStatementProvider).stream()
                    .map(Organization::getOrgCodeLink).collect(Collectors.toList());
            orgCodeLinks.addAll(partOrgCodeLinks);
        }

        return orgCodeLinks.stream()
                .map(orgCodeLink -> {
                    SelectStatementProvider selectStatementProvider = select(OrganizationMapper.selectList)
                            .from(OrganizationDynamicSqlSupport.organization)
                            .where(OrganizationDynamicSqlSupport.orgCodeLink, isLike(orgCodeLink).map(v -> v + "%"),
                                    or(OrganizationDynamicSqlSupport.orgCodeLink, isLike(orgCodeLink).map(v -> "%" + v)))
                            .build().render(RenderingStrategies.MYBATIS3);
                    return organizationMapper.selectMany(selectStatementProvider);
                })
                .flatMap(Collection::stream)
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(Organization::getId))), ArrayList::new));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignRoles(Integer id, List<Integer> roleIds) {
        List<Integer> preRoleIds = getAssignedRoleIds(id, null);

        // 获取要新建关联的角色 ids
        List<Integer> toAddRoleIds = roleIds.stream()
                .filter(roleId -> !preRoleIds.contains(roleId))
                .collect(Collectors.toList());

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

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<RoleAssignVO> loadAssignedRoles(Integer id) {
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
    public List<Integer> getAssignedRoleIds(Integer id, Integer status) {
        List<Integer> parentIds = organizationMapper.getParentsIds(id);

        SelectStatementProvider selectStatementProvider = select(OrganizationRoleRelationDynamicSqlSupport.roleId)
                .from(OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation)
                .join(RoleDynamicSqlSupport.role)
                .on(RoleDynamicSqlSupport.id, equalTo(OrganizationRoleRelationDynamicSqlSupport.roleId))
                .where(OrganizationRoleRelationDynamicSqlSupport.orgId, isIn(parentIds))
                .and(RoleDynamicSqlSupport.status, isEqualToWhenPresent(status))
                .build().render(RenderingStrategies.MYBATIS3);
        List<OrganizationRoleRelation> relations = organizationRoleRelationMapper.selectMany(selectStatementProvider);


        return relations.stream().map(OrganizationRoleRelation::getRoleId).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignResourceActions(Integer id, List<Integer> resourceActionIds) {
        // 获取继承的分配的资源动作 ids
        List<Integer> inheritedActionIds = loadInheritedAssignedActionIds(id);

        // 获取已建立关联的资源动作 ids
        List<Integer> preActionIds = getAssignedResourceActionIds(id);

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
        List<Integer> assignedActionIds = new ArrayList<>();

        // 获取组织的角色所分配的资源动作 ids（本上级）
        List<Integer> roleIds = getAssignedRoleIds(id, 1);
        if (!CollectionUtils.isEmpty(roleIds)) {
            assignedActionIds.addAll(roleService.getAssignedResourceActionIds(roleIds));
        }

        return assignedActionIds;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<ResourceCategoryAssignVO> loadAssignedResources(Integer id) {
        // 获取组织所分配的资源动作 ids（本上级）
        List<Integer> parentIds = organizationMapper.getParentsIds(id);
        SelectStatementProvider selectStatementProvider =
                select(OrganizationResourceActionRelationDynamicSqlSupport.orgId, OrganizationResourceActionRelationDynamicSqlSupport.actionId)
                        .from(OrganizationResourceActionRelationDynamicSqlSupport.organizationResourceActionRelation)
                        .where(OrganizationResourceActionRelationDynamicSqlSupport.orgId, isIn(parentIds))
                        .build().render(RenderingStrategies.MYBATIS3);
        List<OrganizationResourceActionRelation> relations = organizationResourceActionRelationMapper.selectMany(selectStatementProvider);
        Map<Integer, List<OrganizationResourceActionRelation>> relationsMap = relations.stream()
                .collect(Collectors.groupingBy(OrganizationResourceActionRelation::getActionId));

        // 获取组织的角色所分配的资源动作 ids（本上级）
        List<Integer> roleAssignedActionIds = new ArrayList<>();
        List<Integer> roleIds = getAssignedRoleIds(id, 1);
        if (!CollectionUtils.isEmpty(roleIds)) {
            roleAssignedActionIds.addAll(roleService.getAssignedResourceActionIds(roleIds));
        }

        List<ResourceCategoryAssignVO> categories = resourceCategoryService.findAllForAssign();
        categories.forEach(category ->
                Optional.ofNullable(category.getResources()).orElse(new ArrayList<>()).forEach(resource ->
                        Optional.ofNullable(resource.getActions()).orElse(new ArrayList<>()).forEach(action -> {
                            Integer actionId = action.getId();
                            List<Integer> orgIds = relationsMap.getOrDefault(actionId, new ArrayList<>()).stream()
                                    .map(OrganizationResourceActionRelation::getOrgId).collect(Collectors.toList());
                            action.setChecked(CollectionUtils.isNotEmpty(orgIds) || roleAssignedActionIds.contains(actionId));
                            action.setEnableUncheck((CollectionUtils.isEmpty(orgIds) || orgIds.stream().allMatch(id::equals) ) && !roleAssignedActionIds.contains(actionId));
                        })));

        return categories;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> getAssignedResourceActionIds(Integer id) {
        List<Integer> parentIds = organizationMapper.getParentsIds(id);

        SelectStatementProvider selectStatementProvider = select(OrganizationResourceActionRelationDynamicSqlSupport.actionId)
                .from(OrganizationResourceActionRelationDynamicSqlSupport.organizationResourceActionRelation)
                .where(OrganizationResourceActionRelationDynamicSqlSupport.orgId, isIn(parentIds))
                .build().render(RenderingStrategies.MYBATIS3);
        return organizationResourceActionRelationMapper.selectMany(selectStatementProvider).stream()
                .map(OrganizationResourceActionRelation::getActionId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<TreeNodeVO> queryForAsyncTreeFlat(OrganizationTreeQueryDTO queryDTO) {
        Integer pid = Optional.ofNullable(queryDTO.getPid()).orElse(-1);
        String keyword = queryDTO.getKeyword();
        Integer filteredId = queryDTO.getFilteredId();
        List<Integer> appendedIds = queryDTO.getAppendedIds();

        Organization organization = organizationMapper.selectByPrimaryKey(filteredId).orElse(new Organization());
        String orgCodeLink = organization.getOrgCodeLink();
        Set<Integer> parentIds = CollectionUtils.isEmpty(appendedIds) ? new HashSet<>() :
                appendedIds.stream().map(this::getParentsIds).flatMap(Collection::stream).collect(Collectors.toSet());

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

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<TreeNodeVO> queryForAsyncTree(OrganizationTreeQueryDTO queryDTO) {
        Integer pid = Optional.ofNullable(queryDTO.getPid()).orElse(-1);
        List<TreeNodeVO> nodes = queryForAsyncTreeFlat(queryDTO);
        List<TreeNodeVO> treeList = TreeUtils.buildTreeRecursion(
                nodes,
                TreeNodeVO::getPid,
                TreeNodeVO::getId,
                (item -> item),
                TreeNodeVO::setChildren,
                pid,
                null,
                null);
        return CollectionUtils.isNotEmpty(treeList) ? treeList : new ArrayList<>();
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
}
