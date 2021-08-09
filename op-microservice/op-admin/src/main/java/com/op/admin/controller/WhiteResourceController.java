package com.op.admin.controller;

import com.op.admin.dto.WhiteResourceChangeEnabledDTO;
import com.op.admin.dto.WhiteResourcePageQueryDTO;
import com.op.admin.dto.WhiteResourceSaveDTO;
import com.op.admin.service.WhiteResourceService;
import com.op.admin.vo.WhiteResourceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 白名单资源 Controller
 *
 * @author cdrcool
 */
@Api(tags = "白名单资源")
@RequestMapping("whiteResource")
@RestController
public class WhiteResourceController {
    private final WhiteResourceService whiteResourceService;

    public WhiteResourceController(WhiteResourceService whiteResourceService) {
        this.whiteResourceService = whiteResourceService;
    }

    @ApiOperation("保存白名单资源")
    @PostMapping("save")
    public void save(@Valid @RequestBody WhiteResourceSaveDTO saveDTO) {
        whiteResourceService.save(saveDTO);
    }

    @ApiOperation("删除白名单资源")
    @PostMapping("delete")
    public void delete(@RequestParam Integer id) {
        whiteResourceService.deleteById(id);
    }

    @ApiOperation("批量删除白名单资源")
    @PostMapping("batchDelete")
    public void batchDelete(@RequestBody List<Integer> ids) {
        ids.forEach(whiteResourceService::deleteById);
    }

    @ApiOperation("查看白名单资源详情")
    @GetMapping("get")
    public WhiteResourceVO get(@RequestParam Integer id) {
        return whiteResourceService.findById(id);
    }

    @ApiOperation("分页查询白名单资源")
    @PostMapping("queryPage")
    public Page<WhiteResourceVO> queryPage(@PageableDefault(sort = "resource_no", direction = Sort.Direction.ASC) Pageable pageable,
                                           @Valid @RequestBody WhiteResourcePageQueryDTO queryDTO) {
        return whiteResourceService.queryPage(pageable, queryDTO);
    }

    @ApiOperation("启用/禁用角色")
    @PostMapping("changeEnabled")
    public void changeEnabled(@Valid @RequestBody WhiteResourceChangeEnabledDTO changeEnabledDTO) {
        changeEnabledDTO.getIds().forEach(id -> whiteResourceService.changeEnabled(id, changeEnabledDTO.getEnable()));
    }

    @ApiOperation("初始化白名单资源路径列表")
    @PostMapping("initWhiteResourcePaths")
    public List<String> initWhiteResourcePaths() {
        return whiteResourceService.initWhiteResourcePaths();
    }
}
