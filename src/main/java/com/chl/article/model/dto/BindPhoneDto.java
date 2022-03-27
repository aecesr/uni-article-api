package com.chl.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: uni-article-api
 * @description:
 * @Author: 曹红亮
 * @create: 2022-03-26 21:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BindPhoneDto {
    private String phone;
    private String code;
    private String wxOpenId;
}
