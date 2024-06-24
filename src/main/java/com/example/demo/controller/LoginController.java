package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.Mapper.Usermapper;
import com.example.demo.po.Result;
import com.example.demo.po.user;
import com.example.demo.po.user_profile;
import com.example.demo.service.UserService;
import com.example.demo.service.User_pService;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.SendSMS;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Api(tags="用户登录管理接口")
@Slf4j
@RestController
@CrossOrigin(origins = "*" )
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    User_pService user_pService;
    @Autowired
    private Usermapper usermapper;
    @ApiOperation("登录接口")
    @PostMapping()
    public Result login(@RequestBody user user){
        log.info("员工登录：{}", user);
        user u= userService.login(user);
        if(u!=null){
            Map<String, Object> claims=new HashMap<>();
            claims.put("account",u.getAccount());
            claims.put("password",u.getPassword());
            String Jwt = JwtUtils.generateJwt(claims);
            return Result.success(Jwt);
        }
        return Result.error("用户名或密码错误");

    }
    @ApiOperation("注册接口")
    @GetMapping("/register")
    public Result register( user user,@ApiParam("验证码")@RequestParam int CAPTCHA,@RequestParam String Phonnumber) throws Exception {
    /*    LambdaQueryWrapper<user> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(user::getAccount,user);*/
        user u=userService.getById(user.getAccount());
/*        int rand;
        rand=SendSMS.sendSms();
        if(rand!=CAPTCHA){
            return Result.error("验证码错误");
        }*/
       if(SendSMS.chek(CAPTCHA)==0){
           return Result.error("验证码错误");
       };

        if( u!=null ){
            return Result.error("用户已经存在");
        }
        userService.save(user);
        user_profile user_profile=new user_profile();
        user_profile.setAccount(user.getAccount());
        user_profile.setType(user.getType());
        user_profile.setPhone(Phonnumber);
        user_pService.save(user_profile);

        return Result.success(user);
    }
    @ApiOperation("短信发送接口")
    @GetMapping("/sendsms")
    public Result SendSms(String Phonnumber) throws Exception {

         SendSMS.sendSms(Phonnumber);
         return Result.success();

    }
}
