package com.chl.article.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
class StringUtilTest {
    @Resource
    private SmsUtil smsUtil;
    @Test
    void getVerifyCode() {
        smsUtil.sendSms("15505121006",StringUtil.getVerifyCode());
    }
}