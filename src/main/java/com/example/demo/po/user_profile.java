package com.example.demo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class user_profile {

    @TableId
    private String account;//账户名
    private int age;//用户年龄
    private String  type;//账户类型：普通，管理员
    private String preference;//用户偏好
    private String phone;//手机号
    private String  avatar;//用户头像
    private String gender;
}
