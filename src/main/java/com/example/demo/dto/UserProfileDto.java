package com.example.demo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CommodityDTO对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

    private String account;//账户名
    private int age;//用户年龄
    private String  type;//账户类型：普通，管理员
    private String preference;//用户偏好
    private String phone;//手机号
    @Nullable
    private MultipartFile avatar;//用户头像
    private String gender;
}
