package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.po.Variety;
import com.example.demo.po.user_profile;

import java.util.List;

public interface User_pService extends IService<user_profile> {
    List<user_profile> queryUser_p();

    List<user_profile> likeCommodity(String account);

    IPage<user_profile> pageByAccount(Page<user_profile> page, String account);
}
