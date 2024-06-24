package com.example.demo.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.Mapper.User_pMapper;
import com.example.demo.Mapper.Usermapper;
import com.example.demo.po.user;
import com.example.demo.po.user_profile;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServicelmpl extends ServiceImpl<Usermapper, user>  implements UserService {
    @Autowired
    private Usermapper usermapper;
    @Override
    public user login(user user) {
        return usermapper.getByUsernameAndPassword(user);
    }


}
