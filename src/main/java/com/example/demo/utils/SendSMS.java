package com.example.demo.utils;
// This file is auto-generated, don't edit it. Thanks.
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.tea.*;

import com.aliyun.teautil.models.RuntimeOptions;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class SendSMS {

    /**
     * <b>description</b> :
     * <p>使用AK&amp;SK初始化账号Client</p>
     * @return Client
     *
     * @throws Exception
     */
    public static Client createClient() throws Exception {
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考。
        // 建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html。


        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID。
                .setAccessKeyId(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"))
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
                .setAccessKeySecret(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }
    static int rand;

    public static  void sendSms(String Phonnumber) throws Exception {
        // SendSmsRequest.Builder builder = SendSmsRequest.newBuilder();
        log.info(Phonnumber);
        if(Phonnumber==null){
            log.info("号码问题");
        }
        Random random=new Random();
        int min=1000;
        int max=9999;
         rand=min+random.nextInt(max-min+1);
        Client client = SendSMS.createClient();
        SendSmsRequest request =new SendSmsRequest()
                .setPhoneNumbers(Phonnumber)
                .setSignName("服装店管理注册")
                .setTemplateCode("SMS_468230390")
                .setTemplateParam("{\"code\":\""+rand+"\"}"); // 注意这里可能是.build()或其他方法来完成构建

        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            log.info("1111");

            client.sendSmsWithOptions(request, runtime);
        } catch (TeaException error) {
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println("11"+error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println("22"+error.getMessage());
            // 诊断地址
        }
    }


    public static int chek(int captcha) {
        if(rand==captcha)return 1;
        else return 0;
    }
}


