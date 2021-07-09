package com.op.admin.service.impl;

import com.op.admin.dto.OrganizationListQueryDTO;
import com.op.admin.dto.OrganizationSaveDTO;
import com.op.admin.dto.OrganizationTreeQueryDTO;
import com.op.admin.entity.Organization;
import com.op.admin.entity.OrganizationMenuRelation;
import com.op.admin.entity.OrganizationResourceActionRelation;
import com.op.admin.entity.OrganizationRoleRelation;
import com.op.admin.mapper.*;
import com.op.admin.mapper.extend.OrganizationMapperExtend;
import com.op.admin.mapping.OrganizationMapping;
import com.op.admin.utils.TreeUtils;
import com.op.admin.vo.*;
import com.op.admin.service.*;
import com.op.framework.web.common.api.response.ResultCode;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private final OrganizationMenuRelationMapper organizationMenuRelationMapper;
    private final UserService userService;
    private final RoleService roleService;
    private final ResourceCategoryService resourceCategoryService;
    private final MenuService menuService;

    public OrganizationServiceImpl(OrganizationMapperExtend organizationMapper, OrganizationMapping organizationMapping,
                                   OrganizationRoleRelationMapper organizationRoleRelationMapper,
                                   OrganizationResourceActionRelationMapper organizationResourceActionRelationMapper,
                                   OrganizationMenuRelationMapper organizationMenuRelationMapper,
                                   @Lazy UserService userService,
                                   RoleService roleService,
                                   ResourceCategoryService resourceCategoryService,
                                   MenuService menuService) {
        this.organizationMapper = organizationMapper;
        this.organizationMapping = organizationMapping;
        this.organizationRoleRelationMapper = organizationRoleRelationMapper;
        this.organizationResourceActionRelationMapper = organizationResourceActionRelationMapper;
        this.organizationMenuRelationMapper = organizationMenuRelationMapper;
        this.userService = userService;
        this.roleService = roleService;
        this.resourceCategoryService = resourceCategoryService;
        this.menuService = menuService;
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
     * 设置组织 pid、orgCodeLink 等属性
     *
     * @param organization 组织
     * @param pid          上级组织 id
     */
    private void setOrganizationProps(Organization organization, Integer pid) {
        if (pid == null) {
            organization.setPid(-1);
            organization.setOrgCodeLink("");
        } else if (!pid.equals(organization.getPid())) {
            Organization pOrg = organizationMapper.selectByPrimaryKey(pid)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + pid + "】的上级组织"));
            String orgCodeLink = pOrg.getOrgCodeLink();
            orgCodeLink = StringUtils.isNoneBlank(orgCodeLink) ? orgCodeLink + "." + organization.getOrgCode() : organization.getOrgCode();
            organization.setOrgCodeLink(orgCodeLink);
        }
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

        // 删除本下级组织的组织-菜单关联列表
        DeleteStatementProvider organizationMenuRelationProvider = deleteFrom(OrganizationMenuRelationDynamicSqlSupport.organizationMenuRelation)
                .where(OrganizationRoleRelationDynamicSqlSupport.orgId, isIn(childrenIds))
                .build().render(RenderingStrategies.MYBATIS3);
        organizationMenuRelationMapper.delete(organizationMenuRelationProvider);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        ids.forEach(this::deleteById);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public OrganizationVO findById(Integer id) {
        Organization organization = organizationMapper.selectByPrimaryKey(id)
                .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到id为【" + id + "】的组织"));
        return organizationMapping.toOrganizationVO(organization);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public OrganizationTreeVO queryTree(OrganizationTreeQueryDTO queryDTO) {
        Integer pid = queryDTO.getPid() == null ? -1 : queryDTO.getPid();

        List<Organization> organizations = new ArrayList<>();
        if (StringUtils.isBlank(queryDTO.getKeyword())) {
            SelectStatementProvider rootSelectStatementProvider = select(OrganizationMapper.selectList)
                    .from(OrganizationDynamicSqlSupport.organization)
                    .where(OrganizationDynamicSqlSupport.pid, isEqualTo(pid))
                    .build().render(RenderingStrategies.MYBATIS3);
            Organization organization = organizationMapper.selectOne(rootSelectStatementProvider)
                    .orElseThrow(() -> new BusinessException(ResultCode.PARAM_VALID_ERROR, "找不到pid为【" + pid + "】的组织"));

            SelectStatementProvider selectStatementProvider = select(OrganizationMapper.selectList)
                    .from(OrganizationDynamicSqlSupport.organization)
                    .where(OrganizationDynamicSqlSupport.orgCodeLink, isLike(organization.getOrgCodeLink()).map(v -> v + "%"))
                    .build().render(RenderingStrategies.MYBATIS3);

            organizations = organizationMapper.selectMany(selectStatementProvider);
        } else {
            SelectStatementProvider partSelectStatementProvider = select(OrganizationDynamicSqlSupport.orgCodeLink)
                    .from(OrganizationDynamicSqlSupport.organization)
                    .where(OrganizationDynamicSqlSupport.orgName, isLike(queryDTO.getKeyword()).map(v -> "%" + v + "%"))
                    .build().render(RenderingStrategies.MYBATIS3);
            List<String> orgCodeLinks = organizationMapper.selectMany(partSelectStatementProvider).stream()
                    .map(Organization::getOrgCodeLink).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(orgCodeLinks)) {
                organizations = orgCodeLinks.stream()
                        .map(organizationMapper::findAllInPath)
                        .flatMap(Collection::stream)
                        .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing(Organization::getOrgCodeLink))), ArrayList::new));
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

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<OrganizationVO> queryList(OrganizationListQueryDTO queryDTO) {
        SelectStatementProvider selectStatementProvider = select(OrganizationMapper.selectList)
                .from(OrganizationDynamicSqlSupport.organization)
                .where(OrganizationDynamicSqlSupport.orgName, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%"),
                        or(OrganizationDynamicSqlSupport.orgCode, isLike(queryDTO.getKeyword())
                                .filter(StringUtils::isNotBlank).map(v -> "%" + v + "%")))
                .and(OrganizationDynamicSqlSupport.pid, isEqualToWhenPresent(queryDTO.getPid()))
                .build().render(RenderingStrategies.MYBATIS3);
        List<Organization> organizations = organizationMapper.selectMany(selectStatementProvider);

        return organizationMapping.toOrganizationVOList(organizations);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignRoles(Integer id, List<Integer> roleIds) {
        // 获取已建立关联的角色 ids
        List<Integer> preRoleIds = this.getAssignedRoleIds(id);

        // 获取要新建关联的角色 ids
        List<Integer> toAddRoleIds = roleIds.stream()
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignResourceActions(Integer id, List<Integer> resourceActionIds) {
        // 获取已建立关联的资源动作 ids
        List<Integer> preActionIds = this.getAssignedResourceActionIds(id);

        // 获取要新建关联的资源动作 ids
        List<Integer> toAddActionIds = resourceActionIds.stream()
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignMenus(Integer id, List<Integer> menuIds) {
        // 获取已建立关联的菜单 ids
        List<Integer> preMenuIds = this.getAssignedMenuIds(id);

        // 获取要新建关联的菜单 ids
        List<Integer> toAddMenuIds = menuIds.stream()
                .filter(menuId -> !preMenuIds.contains(menuId)).collect(Collectors.toList());

        // 获取要删除关联的菜单 ids
        List<Integer> toDelMenuIds = preMenuIds.stream()
                .filter(menuId -> !menuIds.contains(menuId)).collect(Collectors.toList());

        // 插入要新建的角色-菜单关联
        List<OrganizationMenuRelation> relations = toAddMenuIds.stream()
                .map(menuId -> {
                    OrganizationMenuRelation relation = new OrganizationMenuRelation();
                    relation.setOrgId(id);
                    relation.setMenuId(menuId);

                    return relation;
                }).collect(Collectors.toList());
        relations.forEach(organizationMenuRelationMapper::insert);

        // 删除要删除的角色-菜单关联
        if (!CollectionUtils.isEmpty(toDelMenuIds)) {
            DeleteStatementProvider deleteStatementProvider =
                    deleteFrom(OrganizationMenuRelationDynamicSqlSupport.organizationMenuRelation)
                            .where(OrganizationMenuRelationDynamicSqlSupport.orgId, isEqualTo(id))
                            .and(OrganizationMenuRelationDynamicSqlSupport.menuId, isIn(toDelMenuIds))
                            .build().render(RenderingStrategies.MYBATIS3);
            organizationMenuRelationMapper.delete(deleteStatementProvider);
        }
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
    public List<Integer> getAssignedMenuIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(OrganizationMenuRelationDynamicSqlSupport.menuId)
                .from(OrganizationMenuRelationDynamicSqlSupport.organizationMenuRelation)
                .where(OrganizationMenuRelationDynamicSqlSupport.orgId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return organizationMenuRelationMapper.selectMany(selectStatementProvider).stream()
                .map(OrganizationMenuRelation::getMenuId).collect(Collectors.toList());
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
            List<Integer> orgIds = relationsMap.get(role.getId()).stream()
                    .map(OrganizationRoleRelation::getOrgId).collect(Collectors.toList());
            role.setChecked(CollectionUtils.isNotEmpty(orgIds));
            role.setEnableUncheck(orgIds.stream().allMatch(id::equals));
        });

        return roles;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<ResourceCategoryAssignVO> loadResources(Integer id) {
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
                category.getResources().forEach(resource ->
                        resource.getActions().forEach(action -> {
                            List<Integer> orgIds = relationsMap.get(action.getId()).stream()
                                    .map(OrganizationResourceActionRelation::getOrgId).collect(Collectors.toList());
                            action.setChecked(CollectionUtils.isNotEmpty(orgIds));
                            action.setEnableUncheck(orgIds.stream().allMatch(id::equals));
                        })));

        return categories;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<MenuAssignVO> loadMenus(Integer id) {
        List<Integer> parentIds = organizationMapper.getParentsIds(id);

        SelectStatementProvider selectStatementProvider =
                select(OrganizationMenuRelationDynamicSqlSupport.orgId, OrganizationMenuRelationDynamicSqlSupport.menuId)
                        .from(OrganizationMenuRelationDynamicSqlSupport.organizationMenuRelation)
                        .where(OrganizationMenuRelationDynamicSqlSupport.orgId, isIn(parentIds))
                        .build().render(RenderingStrategies.MYBATIS3);
        List<OrganizationMenuRelation> relations = organizationMenuRelationMapper.selectMany(selectStatementProvider);
        Map<Integer, List<OrganizationMenuRelation>> relationsMap = relations.stream()
                .collect(Collectors.groupingBy(OrganizationMenuRelation::getMenuId));

        List<MenuAssignVO> menus = menuService.findAllForAssign();
        setMenuItems(menus, relationsMap, id);

        return menus;
    }

    private void setMenuItems(List<MenuAssignVO> menus, Map<Integer, List<OrganizationMenuRelation>> relationsMap, Integer id) {
        menus.forEach(menu -> {
            List<Integer> orgIds = relationsMap.get(menu.getId()).stream()
                    .map(OrganizationMenuRelation::getOrgId).collect(Collectors.toList());
            menu.setChecked(CollectionUtils.isNotEmpty(orgIds));
            menu.setEnableUncheck(orgIds.stream().allMatch(id::equals));

            if (!CollectionUtils.isEmpty(menu.getChildren())) {
                setMenuItems(menu.getChildren(), relationsMap, id);
            }
        });
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
