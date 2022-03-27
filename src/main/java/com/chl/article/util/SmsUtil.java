package com.chl.article.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import jdk.jfr.consumer.RecordedStackTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @program: uni-article-api
 * @description:
 * @Author: 曹红亮
 * @create: 2022-03-23 21:01
 **/
@Component
@Slf4j
public class SmsUtil {
    /**
     * 注入阿里云配置类实例
     */
    @Resource
    private AliyunResource aliyunResource;

    /**
     * * 发短信
     * *
     * * @param phone 手机号
     * * @param code  短信JSON串
     */
    public boolean sendSms(String phone, String code) {
        Config config = new Config();
        // 你的AccessKey ID
        config.setAccessKeyId(aliyunResource.getAccessKeyId());
        // 你的AccessKey Secret
        config.setAccessKeySecret(aliyunResource.getAccessKeySecret());
        System.out.println(aliyunResource.getAccessKeyId());
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
//        try {
////            System.out.println(config);
////            System.out.println("------------------------");
////            System.out.println(phone);
////            Client client = new Client(config);
////            SendSmsRequest sendSmsRequest = new SendSmsRequest();
////            sendSmsRequest.setPhoneNumbers(phone);
////            sendSmsRequest.setSignName(aliyunResource.getSignName());
////            sendSmsRequest.setTemplateCode(aliyunResource.getTemplateCode());
////            // 这里把原来写死的code换成变量，用工具类生成一个随机6位字符串
////            sendSmsRequest.setTemplateParam("{\"code\":\"" + code + "\"}");
////            SendSmsResponse resp = client.sendSms(sendSmsRequest);
////            System.out.println(resp.getBody());
////            log.info(resp.getBody().getCode());
////            log.info(resp.getBody().getMessage());
//            return true;
////            log.info(resp.toString());
////            //获得返回结果JSON串
////            String res = String.valueOf(resp.getBody());
////            log.info(res);
////            //转成JSON对象
////            JSONObject jsonObj = JSON.parseObject(res);
////            //返回发送成功与否的标记
////            if ("OK".equals(jsonObj.get("Code"))) {
////                return true;
////            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
        return true;
    }
}

