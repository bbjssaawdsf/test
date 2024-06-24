package com.example.demo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CommodityDTO对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class CommodityDto {

    @ApiModelProperty(value = "商品")
    private String commodity;

    @ApiModelProperty(value = "商品编号")
    private Integer commodityId;

    @ApiModelProperty(value = "种类编号")
    private Integer varietyId;

    @ApiModelProperty(value = "商品数量")
    private Integer quantity;

    @ApiModelProperty(value = "商品介绍")
    private String introduce;

    @ApiModelProperty(value = "商品单价")
    private String unitPrice;

    @ApiModelProperty(value = "vip单价")
    private String vipPrice;
    @ApiModelProperty(value = "图片路径")
    private MultipartFile img;
}
