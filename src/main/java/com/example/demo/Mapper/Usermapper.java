package com.example.demo.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.pojo.User;
import com.example.demo.po.user;
import com.example.demo.po.user_profile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface Usermapper extends BaseMapper<user> {
    @Select("select *from fashion_pro.user where account=#{account} and password=#{password}")
    user getByUsernameAndPassword(user user);

/*
    @Update("UPDATE user set balance=balance-#{money} WHERE id=#{id} ")
    void deducbalance(@Param("id") Long id,@Param("money") Integer money);
*/
}
