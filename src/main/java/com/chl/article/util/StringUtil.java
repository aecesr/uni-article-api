package com.chl.article.util;

import javax.xml.transform.sax.SAXResult;
import java.util.Random;
import java.util.Stack;

/**
 * @program: uni-article-api
 * @description:
 * @Author: 曹红亮
 * @create: 2022-03-23 21:14
 **/

public class StringUtil {
    private final static int LENGTH = 6;

    public static String getVerifyCode() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();

    }
}
