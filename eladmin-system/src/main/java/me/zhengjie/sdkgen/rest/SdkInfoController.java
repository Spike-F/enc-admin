/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.sdkgen.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.sdkgen.domain.SdkInfo;
import me.zhengjie.sdkgen.service.SdkInfoService;
import me.zhengjie.sdkgen.service.dto.SdkInfoQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import me.zhengjie.utils.PageResult;
import me.zhengjie.sdkgen.service.dto.SdkInfoDto;

/**
* @website https://eladmin.vip
* @author fengjb
* @date 2024-08-17
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "SDK生成管理")
@RequestMapping("/api/sdkInfo")
public class SdkInfoController {

    private final SdkInfoService sdkInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('sdkInfo:list')")
    public void exportSdkInfo(HttpServletResponse response, SdkInfoQueryCriteria criteria) throws IOException {
        sdkInfoService.download(sdkInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询SDK生成")
    @ApiOperation("查询SDK生成")
    @PreAuthorize("@el.check('sdkInfo:list')")
    public ResponseEntity<PageResult<SdkInfoDto>> querySdkInfo(SdkInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(sdkInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增SDK生成")
    @ApiOperation("新增SDK生成")
    @PreAuthorize("@el.check('sdkInfo:add')")
    public ResponseEntity<Object> createSdkInfo(@Validated @RequestBody SdkInfo resources){
        sdkInfoService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改SDK生成")
    @ApiOperation("修改SDK生成")
    @PreAuthorize("@el.check('sdkInfo:edit')")
    public ResponseEntity<Object> updateSdkInfo(@Validated @RequestBody SdkInfo resources){
        sdkInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除SDK生成")
    @ApiOperation("删除SDK生成")
    @PreAuthorize("@el.check('sdkInfo:del')")
    public ResponseEntity<Object> deleteSdkInfo(@RequestBody Integer[] ids) {
        sdkInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}