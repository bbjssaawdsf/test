package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.po.Commodity;
import com.example.demo.Mapper.CommodityMapper;
import com.example.demo.po.Result;
import com.example.demo.po.UserOrder;
import com.example.demo.service.ICommodityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-06-14
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {

    @Autowired
    CommodityMapper commodityMapper;
    @Override
    public List<Commodity> likeCommodity(String account) {
        QueryWrapper<Commodity> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("commodity",account);
        List<Commodity> users= commodityMapper.selectList(queryWrapper);
        return users  ;
    }
    @Override
    public boolean updateCommoditySelective(Commodity commodity, UpdateWrapper<Commodity> extraWrapper) {
        UpdateWrapper<Commodity> updateWrapper = new UpdateWrapper<>();

        // 使用Java 8的Stream API来遍历Commodity的非空字段
        Arrays.stream(Commodity.class.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()) && !Modifier.isTransient(field.getModifiers())) // 排除静态和瞬态字段
                .forEach(field -> {
                    try {
                        field.setAccessible(true); // 允许访问私有字段
                        Object value = field.get(commodity);
                        if (value != null) {
                            // 这里假设字段名直接对应数据库列名，或者你可以使用注解来获取列名
                            String columnName = field.getName(); // 或者使用更复杂的逻辑来获取列名
                            // 你可以在这里添加类型转换的逻辑，如果需要的话
                            updateWrapper.set(columnName, value);
                        }
                    } catch (IllegalAccessException e) {
                        // 处理异常
                        e.printStackTrace();
                    }
                });

        // 如果传入了额外的wrapper，则合并它（这里假设是逻辑AND）
        if (extraWrapper != null) {
         //   updateWrapper.and(extraWrapper);
        }

        // 调用Mapper的update方法执行更新操作
        return commodityMapper.update(commodity,updateWrapper) > 0; // 直接传入updateWrapper
    }

    @Override
    public IPage<Commodity> pageByAccount(Page<Commodity> page, String account) {
        LambdaQueryWrapper<Commodity> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(account)) {
            // 使用LIKE进行模糊查询
            queryWrapper.like(Commodity::getCommodityId, account);
        }
        return baseMapper.selectPage(page, queryWrapper);

    }

    @Override
    public IPage<Commodity> pageByCommodity(Page<Commodity> page, String account) {
        LambdaQueryWrapper<Commodity> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(account)) {
            // 使用LIKE进行模糊查询
            queryWrapper.like(Commodity::getCommodity, account);
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Result Test() {
        return null;
    }
}
