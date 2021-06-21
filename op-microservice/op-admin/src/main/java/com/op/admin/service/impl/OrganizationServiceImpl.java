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
import com.op.admin.service.MenuService;
import com.op.admin.service.OrganizationService;
import com.op.admin.service.ResourceCategoryService;
import com.op.admin.service.RoleService;
import com.op.admin.utils.TreeUtils;
import com.op.admin.vo.*;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
    private final OrganizationMenuRelationMapper organizationMenuRelationMapper;
    private final RoleService roleService;
    private final ResourceCategoryService resourceCategoryService;
    private final MenuService menuService;

    public OrganizationServiceImpl(OrganizationMapperExtend organizationMapper, OrganizationMapping organizationMapping,
                                   OrganizationRoleRelationMapper organizationRoleRelationMapper,
                                   OrganizationResourceActionRelationMapper organizationResourceActionRelationMapper,
                                   OrganizationMenuRelationMapper organizationMenuRelationMapper,
                                   RoleService roleService,
                                   ResourceCategoryService resourceCategoryService,
                                   MenuService menuService) {
        this.organizationMapper = organizationMapper;
        this.organizationMapping = organizationMapping;
        this.organizationRoleRelationMapper = organizationRoleRelationMapper;
        this.organizationResourceActionRelationMapper = organizationResourceActionRelationMapper;
        this.organizationMenuRelationMapper = organizationMenuRelationMapper;
        this.roleService = roleService;
        this.resourceCategoryService = resourceCategoryService;
        this.menuService = menuService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(OrganizationSaveDTO saveDTO) {
        if (saveDTO.getId() == null) {
            Organization organization = organizationMapping.toOrganization(saveDTO);
            setOrganizationProps(organization, saveDTO.getPid());
            organizationMapper.insert(organization);
        } else {
            Integer id = saveDTO.getId();
            Organization organization = organizationMapper.selectByPrimaryKey(id)
                    .orElseThrow(() -> new BusinessException("找不到组织，组织id：" + id));
            setOrganizationProps(organization, saveDTO.getPid());
            organizationMapping.update(saveDTO, organization);
            organizationMapper.updateByPrimaryKey(organization);
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
                    .orElseThrow(() -> new BusinessException("找不到上级组织，上级组织id：" + pid));
            String orgCodeLink = pOrg.getOrgCodeLink();
            orgCodeLink = StringUtils.isNoneBlank(orgCodeLink) ? orgCodeLink + "," + organization.getOrgCode() : organization.getOrgCode();
            organization.setOrgCodeLink(orgCodeLink);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        organizationMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public OrganizationVO findById(Integer id) {
        Organization organization = organizationMapper.selectByPrimaryKey(id).orElse(new Organization());
        return organizationMapping.toOrganizationVO(organization);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public OrganizationTreeVO queryTree(OrganizationTreeQueryDTO queryDTO) {
        Integer pid = queryDTO.getPid() == null ? -1 : queryDTO.getPid();
        Organization organization = organizationMapper.selectByPrimaryKey(pid)
                .orElseThrow(() -> new BusinessException("找不到上级组织，上级组织id：" + pid));

        SelectStatementProvider selectStatementProvider = select(OrganizationMapper.selectList)
                .from(OrganizationDynamicSqlSupport.organization)
                .where(OrganizationDynamicSqlSupport.orgCodeLink, isLike(organization.getOrgCodeLink()))
                .build().render(RenderingStrategies.MYBATIS3);
        List<Organization> organizations = organizationMapper.selectMany(selectStatementProvider);

        List<OrganizationTreeVO> organizationTreeVOList = TreeUtils.buildTree(
                organizations,
                organizationMapping::toOrganizationTreeVO,
                OrganizationTreeVO::getPid,
                OrganizationTreeVO::getId,
                (menu, children) -> menu.setChildren(children.stream()
                        .sorted(Comparator.comparing(OrganizationTreeVO::getOrgCode))
                        .collect(Collectors.toList())), pid);
        return organizationTreeVOList.get(0);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<OrganizationVO> queryList(OrganizationListQueryDTO queryDTO) {
        SelectStatementProvider selectStatementProvider = select(OrganizationMapper.selectList)
                .from(OrganizationDynamicSqlSupport.organization)
                .where(OrganizationDynamicSqlSupport.orgName, isLikeWhenPresent(queryDTO.getSearchText()),
                        or(OrganizationDynamicSqlSupport.orgCode, isLikeWhenPresent(queryDTO.getSearchText())))
                .and(OrganizationDynamicSqlSupport.pid, isEqualToWhenPresent(queryDTO.getPid()))
                .build().render(RenderingStrategies.MYBATIS3);
        List<Organization> organizations = organizationMapper.selectMany(selectStatementProvider);

        return organizationMapping.toOrganizationVOList(organizations);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignRoles(Integer id, List<Integer> roleIds) {
        // 获取已建立关联的角色ids
        List<Integer> preRoleIds = loadRoleIds(id);

        // 获取要新建关联的角色ids
        List<Integer> toAddRoleIds = roleIds.stream()
                .filter(roleId -> !preRoleIds.contains(roleId)).collect(Collectors.toList());

        // 获取要删除关联的角色ids
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
            DeleteStatementProvider deleteStatementProvider = deleteFrom(OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation)
                    .where(OrganizationRoleRelationDynamicSqlSupport.orgId, isEqualTo(id))
                    .and(OrganizationRoleRelationDynamicSqlSupport.roleId, isIn(toDelRoleIds))
                    .build().render(RenderingStrategies.MYBATIS3);
            organizationRoleRelationMapper.delete(deleteStatementProvider);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignResources(Integer id, List<Integer> resourceActionIds) {
        // 获取已建立关联的资源动作ids
        List<Integer> preActionIds = loadResourceIds(id);

        // 获取要新建关联的资源动作ids
        List<Integer> toAddActionIds = resourceActionIds.stream()
                .filter(actionId -> !preActionIds.contains(actionId)).collect(Collectors.toList());

        // 获取要删除关联的资源动作ids
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
            DeleteStatementProvider deleteStatementProvider = deleteFrom(OrganizationResourceActionRelationDynamicSqlSupport.organizationResourceActionRelation)
                    .where(OrganizationResourceActionRelationDynamicSqlSupport.orgId, isEqualTo(id))
                    .and(OrganizationResourceActionRelationDynamicSqlSupport.actionId, isIn(toDelActionIds))
                    .build().render(RenderingStrategies.MYBATIS3);
            organizationResourceActionRelationMapper.delete(deleteStatementProvider);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignMenus(Integer id, List<Integer> menuIds) {
        // 获取已建立关联的菜单ids
        List<Integer> preMenuIds = this.loadMenuIds(id);

        // 获取要新建关联的菜单ids
        List<Integer> toAddMenuIds = menuIds.stream()
                .filter(menuId -> !preMenuIds.contains(menuId)).collect(Collectors.toList());

        // 获取要删除关联的菜单ids
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
            DeleteStatementProvider deleteStatementProvider = deleteFrom(OrganizationMenuRelationDynamicSqlSupport.organizationMenuRelation)
                    .where(OrganizationMenuRelationDynamicSqlSupport.orgId, isEqualTo(id))
                    .and(OrganizationMenuRelationDynamicSqlSupport.menuId, isIn(toDelMenuIds))
                    .build().render(RenderingStrategies.MYBATIS3);
            organizationMenuRelationMapper.delete(deleteStatementProvider);
        }
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> loadRoleIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(OrganizationRoleRelationDynamicSqlSupport.roleId)
                .from(OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation)
                .where(OrganizationRoleRelationDynamicSqlSupport.orgId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return organizationRoleRelationMapper.selectMany(selectStatementProvider).stream()
                .map(OrganizationRoleRelation::getRoleId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> loadResourceIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(OrganizationResourceActionRelationDynamicSqlSupport.actionId)
                .from(OrganizationResourceActionRelationDynamicSqlSupport.organizationResourceActionRelation)
                .where(OrganizationResourceActionRelationDynamicSqlSupport.orgId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return organizationResourceActionRelationMapper.selectMany(selectStatementProvider).stream()
                .map(OrganizationResourceActionRelation::getActionId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<Integer> loadMenuIds(Integer id) {
        SelectStatementProvider selectStatementProvider = select(OrganizationMenuRelationDynamicSqlSupport.menuId)
                .from(OrganizationMenuRelationDynamicSqlSupport.organizationMenuRelation)
                .where(OrganizationMenuRelationDynamicSqlSupport.orgId, isEqualTo(id))
                .build().render(RenderingStrategies.MYBATIS3);
        return organizationMenuRelationMapper.selectMany(selectStatementProvider).stream()
                .map(OrganizationMenuRelation::getMenuId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<RoleAssignVO> loadRolesWithAssignInfo(Integer id) {
        List<Integer> parentIds = organizationMapper.getParentIds(id);

        SelectStatementProvider selectStatementProvider = select(OrganizationRoleRelationDynamicSqlSupport.orgId, OrganizationRoleRelationDynamicSqlSupport.roleId)
                .from(OrganizationRoleRelationDynamicSqlSupport.organizationRoleRelation)
                .where(OrganizationRoleRelationDynamicSqlSupport.orgId, isIn(parentIds))
                .build().render(RenderingStrategies.MYBATIS3);
        List<OrganizationRoleRelation> relations = organizationRoleRelationMapper.selectMany(selectStatementProvider);
        Map<Integer, List<OrganizationRoleRelation>> relationsMap = relations.stream().collect(Collectors.groupingBy(OrganizationRoleRelation::getRoleId));

        List<RoleAssignVO> roles = roleService.findAllToAssign();
        roles.forEach(role -> {
            List<Integer> orgIds = relationsMap.get(role.getId()).stream()
                    .map(OrganizationRoleRelation::getOrgId).collect(Collectors.toList());
            role.setChecked(CollectionUtils.isNotEmpty(orgIds));
            role.setEnableUncheck(orgIds.stream().anyMatch(orgId -> !id.equals(orgId)));
        });

        return roles;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<ResourceCategoryAssignVO> loadResourcesWithAssignInfo(Integer id) {
        List<Integer> parentIds = organizationMapper.getParentIds(id);

        SelectStatementProvider selectStatementProvider = select(OrganizationResourceActionRelationDynamicSqlSupport.orgId, OrganizationResourceActionRelationDynamicSqlSupport.actionId)
                .from(OrganizationResourceActionRelationDynamicSqlSupport.organizationResourceActionRelation)
                .where(OrganizationResourceActionRelationDynamicSqlSupport.orgId, isIn(parentIds))
                .build().render(RenderingStrategies.MYBATIS3);
        List<OrganizationResourceActionRelation> relations = organizationResourceActionRelationMapper.selectMany(selectStatementProvider);
        Map<Integer, List<OrganizationResourceActionRelation>> relationsMap = relations.stream().collect(Collectors.groupingBy(OrganizationResourceActionRelation::getActionId));

        List<ResourceCategoryAssignVO> categories = resourceCategoryService.findAllToAssign();
        categories.forEach(category ->
                category.getResources().forEach(resource ->
                        resource.getActions().forEach(action -> {
                            List<Integer> orgIds = relationsMap.get(action.getId()).stream()
                                    .map(OrganizationResourceActionRelation::getOrgId).collect(Collectors.toList());
                            action.setChecked(CollectionUtils.isNotEmpty(orgIds));
                            action.setEnableUncheck(orgIds.stream().anyMatch(orgId -> !id.equals(orgId)));
                        })));

        return categories;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<MenuAssignVO> loadMenusWithAssignInfo(Integer id) {
        List<Integer> parentIds = organizationMapper.getParentIds(id);

        SelectStatementProvider selectStatementProvider = select(OrganizationMenuRelationDynamicSqlSupport.orgId, OrganizationMenuRelationDynamicSqlSupport.menuId)
                .from(OrganizationMenuRelationDynamicSqlSupport.organizationMenuRelation)
                .where(OrganizationMenuRelationDynamicSqlSupport.orgId, isIn(parentIds))
                .build().render(RenderingStrategies.MYBATIS3);
        List<OrganizationMenuRelation> relations = organizationMenuRelationMapper.selectMany(selectStatementProvider);
        Map<Integer, List<OrganizationMenuRelation>> relationsMap = relations.stream().collect(Collectors.groupingBy(OrganizationMenuRelation::getMenuId));

        List<MenuAssignVO> menus = menuService.findAllToAssign();
        setMenuItems(menus, relationsMap, id);

        return menus;
    }

    private void setMenuItems(List<MenuAssignVO> menus, Map<Integer, List<OrganizationMenuRelation>> relationsMap, Integer id) {
        menus.forEach(menu -> {
            List<Integer> orgIds = relationsMap.get(menu.getId()).stream()
                    .map(OrganizationMenuRelation::getOrgId).collect(Collectors.toList());
            menu.setChecked(CollectionUtils.isNotEmpty(orgIds));
            menu.setEnableUncheck(orgIds.stream().anyMatch(orgId -> !id.equals(orgId)));

            if (!CollectionUtils.isEmpty(menu.getChildren())) {
                setMenuItems(menu.getChildren(), relationsMap, id);
            }
        });
    }
}
