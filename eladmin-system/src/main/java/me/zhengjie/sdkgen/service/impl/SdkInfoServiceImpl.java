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
package me.zhengjie.sdkgen.service.impl;

import me.zhengjie.sdkgen.domain.SdkInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.sdkgen.repository.SdkInfoRepository;
import me.zhengjie.sdkgen.service.SdkInfoService;
import me.zhengjie.sdkgen.service.dto.SdkInfoDto;
import me.zhengjie.sdkgen.service.dto.SdkInfoQueryCriteria;
import me.zhengjie.sdkgen.service.mapstruct.SdkInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import me.zhengjie.utils.PageResult;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author fengjb
* @date 2024-08-17
**/
@Service
@RequiredArgsConstructor
public class SdkInfoServiceImpl implements SdkInfoService {

    private final SdkInfoRepository sdkInfoRepository;
    private final SdkInfoMapper sdkInfoMapper;

    @Override
    public PageResult<SdkInfoDto> queryAll(SdkInfoQueryCriteria criteria, Pageable pageable){
        Page<SdkInfo> page = sdkInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(sdkInfoMapper::toDto));
    }

    @Override
    public List<SdkInfoDto> queryAll(SdkInfoQueryCriteria criteria){
        return sdkInfoMapper.toDto(sdkInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SdkInfoDto findById(Integer id) {
        SdkInfo sdkInfo = sdkInfoRepository.findById(id).orElseGet(SdkInfo::new);
        ValidationUtil.isNull(sdkInfo.getId(),"SdkInfo","id",id);
        return sdkInfoMapper.toDto(sdkInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SdkInfo resources) {
        sdkInfoRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SdkInfo resources) {
        SdkInfo sdkInfo = sdkInfoRepository.findById(resources.getId()).orElseGet(SdkInfo::new);
        ValidationUtil.isNull( sdkInfo.getId(),"SdkInfo","id",resources.getId());
        sdkInfo.copy(resources);
        sdkInfoRepository.save(sdkInfo);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            sdkInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SdkInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SdkInfoDto sdkInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("企业名称", sdkInfo.getCompanyName());
            map.put("证书信息", sdkInfo.getCertInfo());
            map.put("sdk路径", sdkInfo.getSdkPath());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}