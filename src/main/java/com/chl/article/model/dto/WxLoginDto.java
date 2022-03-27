package com.chl.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: uni-article-api
 * @description:
 * @Author: 曹红亮
 * @create: 2022-03-25 19:31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WxLoginDto {
    private String wxOpenid;
    private String nickname;
    private String gender;
    private String avatar;
}
