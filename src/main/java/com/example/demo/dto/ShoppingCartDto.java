package com.example.demo.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *
 * </p>
 *
 * @author author
 * @since 2024-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ShoppingCartDt0对象", description="")
public class ShoppingCartDto  {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品编号")
    private Integer commodityId;

    @ApiModelProperty(value = "商品名")
    private String commodity;

    @ApiModelProperty(value = "用户名")
    private String account;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "购买数量")
    private Integer number;

    @ApiModelProperty(value = "图片")
    private MultipartFile img;


}

