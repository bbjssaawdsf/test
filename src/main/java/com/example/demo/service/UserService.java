package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.po.user;
import com.example.demo.po.user_profile;

import java.util.List;

public interface UserService extends IService<user> {

    //员工登录
    user login(user user);
}
