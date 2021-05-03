package com.op.samples.business.controller;

import com.op.samples.business.dto.ExampleQueryDTO;
import com.op.samples.business.dto.ExampleSaveDTO;
import com.op.samples.business.vo.ExampleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author cdrcool
 */
@Api(tags = "Example")
@RequestMapping("example")
@RestController
public class ExampleController {

    @ApiOperation("查询Example列表")
    @PostMapping("query")
    public List<ExampleVO> query(@RequestBody ExampleQueryDTO exampleQueryDTO) {
        return null;
    }

    @ApiOperation("保存Example")
    @PostMapping("save")
    public void save(@Valid @RequestBody ExampleSaveDTO exampleSaveDTO) {

    }

    @ApiOperation("获取Example")
    @GetMapping("get")
    public ExampleVO get(@RequestParam String id) {
        return null;
    }

    @ApiOperation("删除Example")
    @PostMapping("delete")
    public void delete(@RequestParam String id) {

    }
}
