package com.op.admin.controller;

import com.op.admin.dto.OauthClientDetailsDTO;
import com.op.admin.dto.OauthClientDetailsPageQueryDTO;
import com.op.admin.dto.OauthClientDetailsSaveDTO;
import com.op.admin.service.OauthClientDetailsService;
import com.op.admin.vo.OauthClientDetailsVO;
import com.op.framework.web.common.api.response.NoApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * oauth2-client Controller
 *
 * @author cdrcool
 */
@Api(tags = "oauth2-client 管理")
@RequestMapping("oauthClientDetails")
@RestController
public class OauthClientDetailsController {
    private final OauthClientDetailsService oauthClientDetailsService;

    public OauthClientDetailsController(OauthClientDetailsService oauthClientDetailsService) {
        this.oauthClientDetailsService = oauthClientDetailsService;
    }

    @ApiOperation("保存 oauth2-client")
    @PostMapping("save")
    public void save(@Valid @RequestBody OauthClientDetailsSaveDTO saveDTO) {
        oauthClientDetailsService.save(saveDTO);
    }

    @ApiOperation("删除 oauth2-client")
    @PostMapping("delete")
    public void delete(@RequestParam Integer id) {
        oauthClientDetailsService.deleteById(id);
    }

    @ApiOperation("查看 oauth2-client 详情")
    @GetMapping("get")
    public OauthClientDetailsVO getByClientId(@RequestParam Integer id) {
        return oauthClientDetailsService.findById(id);
    }

    @NoApiResponse
    @ApiOperation("根据客户端标识查找 oauth2-client")
    @GetMapping("getByClientId")
    public OauthClientDetailsDTO get(@RequestParam String clientId) {
        return oauthClientDetailsService.findByClientId(clientId);
    }

    @ApiOperation("分页查询 oauth2-client")
    @PostMapping("page")
    public Page<OauthClientDetailsVO> queryPage(@PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable,
                                                @Valid @RequestBody OauthClientDetailsPageQueryDTO queryDTO) {
        return oauthClientDetailsService.queryPage(pageable, queryDTO);
    }
}
