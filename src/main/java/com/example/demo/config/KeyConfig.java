package com.example.demo.config;

import com.aliyun.credentials.Client;
import com.aliyun.credentials.models.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class KeyConfig {
    @Bean
    public Client aaa(){
        Config credentialConfig = new Config();
        credentialConfig.setType("access_key");
        credentialConfig.setAccessKeyId(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"));
        credentialConfig.setAccessKeySecret(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));
        Client credentialClient = new Client(credentialConfig);
        return credentialClient;
    }
}
