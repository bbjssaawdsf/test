package com.example.demo.Mapper;

import com.example.demo.po.UserOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.po.user_profile;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-06-14
 */
@Mapper
public interface UserOrderMapper extends BaseMapper<UserOrder> {
    List<Map<Integer,Object>> selectuser();

}
