package com.example.demo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("variety")
@ApiModel(value="Variety对象", description="")
public class Variety implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品种类")
    private String variety;

    @ApiModelProperty(value = "种类介绍")
    private String introduce;

    @ApiModelProperty(value = "种类编号")
    @TableId(value = "variety_id", type = IdType.AUTO)
    private Integer varietyId;


}
