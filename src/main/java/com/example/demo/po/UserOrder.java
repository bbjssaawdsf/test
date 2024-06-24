package com.example.demo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2024-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_order")
@ApiModel(value="UserOrder对象", description="")
public class UserOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号")

    private String account;

    @ApiModelProperty(value = "种类编号：格式：yyyy-MM-dd")
    private Integer varietyid;

    @ApiModelProperty(value = "购买时间:")

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate buyTime;

    @ApiModelProperty(value = "商品编号")
    private Integer commodityId;

    @ApiModelProperty(value="订单编号")
    @TableId(value = "orderid")
     private Integer orderid;
    @ApiModelProperty(value="订单金额")
    private Integer order_price;
    @ApiModelProperty(value="单个数量")
    private Integer number;


}
