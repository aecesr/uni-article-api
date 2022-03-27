package com.chl.article.sample;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.*;

/**
 * @program: uni-article-api
 * @description:
 * @Author: 曹红亮
 * @create: 2022-03-23 20:46
 **/

public class Sample {
    /**
     * 使用AK&SK初始化账号Client     *
     * * @param accessKeyId     accessKeyId
     * * @param accessKeySecret accessKeySecret
     * * @return Client     * @throws Exception Exception
     */
    public static Client createClient(String accessKeyId,String accessKeySecret)throws Exception {
        Config config = new Config()
                // 你的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 你的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    public static void main(String[] args) throws Exception {
        Client client = Sample.createClient("LTAI5tQZoQndy1kZN95yBYcV", "bOE9YR4870HcgrPmXiZr44QDOfanFz");
        SendSmsRequest request = new SendSmsRequest();
        request.setSignName("曹大大短信测试");
        request.setTemplateCode("SMS_154950909");
        request.setPhoneNumbers("15505121006");
        request.setTemplateParam("{\"code\":\"1234\"}");
        SendSmsResponse resp = client.sendSms(request);
        System.out.println(resp);
    }


}

