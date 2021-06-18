package com.op.admin.controller;

import com.op.admin.dto.OrganizationListQueryDTO;
import com.op.admin.dto.OrganizationSaveDTO;
import com.op.admin.service.OrganizationService;
import com.op.admin.vo.OrganizationTreeVO;
import com.op.admin.vo.OrganizationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 组织 Controller
 *
 * @author cdrcool
 */
@Api(tags = "组织管理")
@RequestMapping("organization")
@RestController
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @ApiOperation("保存组织")
    @PostMapping("save")
    public void save(@Valid @RequestBody OrganizationSaveDTO saveDTO) {
        organizationService.save(saveDTO);
    }

    @ApiOperation("删除组织")
    @PostMapping("delete")
    public void delete(@RequestParam Integer id) {
        organizationService.deleteById(id);
    }

    @ApiOperation("查看组织详情")
    @GetMapping("get")
    public OrganizationVO get(@RequestParam Integer id) {
        return organizationService.findById(id);
    }

    @ApiOperation("查询组织树")
    @PostMapping("queryTree")
    public OrganizationTreeVO queryTree(@Valid @RequestBody OrganizationListQueryDTO queryDTO) {
        return organizationService.queryTree(queryDTO);
    }
}
