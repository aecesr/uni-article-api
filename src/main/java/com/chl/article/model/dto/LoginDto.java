package com.chl.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: uni-article-api
 * @description:
 * @Author: 曹红亮
 * @create: 2022-03-22 20:25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    private String phone;
    private String password;
}
