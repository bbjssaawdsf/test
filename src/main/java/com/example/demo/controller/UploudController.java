package com.example.demo.controller;

import com.example.demo.po.Result;
import com.example.demo.service.User_pService;
import com.example.demo.utils.AliOSSUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Slf4j
@Api(tags="文件上传接口")
@RestController
@RequestMapping("/uploud")
@CrossOrigin(origins = "*")
public class UploudController {
    @Autowired
    private AliOSSUtils aliOSSUtils;

    @PostMapping()
    public Result upload(MultipartFile image) throws IOException {
        log.info("文件上传, 文件名: {}", image.getOriginalFilename());

        //调用阿里云OSS工具类进行文件上传
        String url = aliOSSUtils.upload(image);

        log.info("文件上传完成,文件访问的url: {}", url);
        log.info("文件上传完成,文件访问的url: {}", image.getOriginalFilename());
        return Result.success(url);
    }
}
