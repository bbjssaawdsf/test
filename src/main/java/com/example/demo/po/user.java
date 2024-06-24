package com.example.demo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class user {
    @TableId
    private String account;//账号
    private String password;//密码
    private String type;//账号类型：user:普通，admin:管理员，member:会员
}
