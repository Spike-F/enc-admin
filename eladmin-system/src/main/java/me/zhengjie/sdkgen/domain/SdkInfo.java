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
package me.zhengjie.sdkgen.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author fengjb
* @date 2024-08-17
**/
@Entity
@Data
@Table(name="sdk_info")
public class SdkInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "`company_name`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @Column(name = "`cert_info`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "证书信息")
    private String certInfo;

    @Column(name = "`sdk_path`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "sdk路径")
    private String sdkPath;

    public void copy(SdkInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
