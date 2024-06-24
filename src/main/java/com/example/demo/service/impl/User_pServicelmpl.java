package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.Mapper.User_pMapper;
import com.example.demo.Mapper.Usermapper;
import com.example.demo.po.Variety;
import com.example.demo.po.user_profile;
import com.example.demo.service.User_pService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class User_pServicelmpl extends ServiceImpl<User_pMapper, user_profile> implements User_pService {

    @Autowired
    private User_pMapper usermapper;
    @Override
    public List<user_profile> queryUser_p() {
        return usermapper.selectList(null);
    }

    @Override
    public List<user_profile> likeCommodity(String account) {
        QueryWrapper<user_profile> queryWrapper=new QueryWrapper<>();
        if(account!=null)
        queryWrapper.like("account",account);

        List<user_profile> users= usermapper.selectList(queryWrapper);
        return users  ;
    }

    @Override
    public IPage<user_profile> pageByAccount(Page<user_profile> page, String account) {
        LambdaQueryWrapper<user_profile> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(account)) {
            // 使用LIKE进行模糊查询
            queryWrapper.like(user_profile::getAccount, account);
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

}

