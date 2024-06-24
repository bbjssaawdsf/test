package com.example.demo.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.po.user_profile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface User_pMapper extends BaseMapper<user_profile> {
}
