package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.po.Commodity;
import com.example.demo.po.ShoppingCart;
import com.example.demo.Mapper.ShoppingCartMapper;
import com.example.demo.service.IShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-06-22
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements IShoppingCartService {

    @Override
    public IPage<ShoppingCart> pageByAccount(Page<ShoppingCart> page, String account) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(account)) {
            // 使用LIKE进行模糊查询
            queryWrapper.like(ShoppingCart::getAccount, account);
        }
        return baseMapper.selectPage(page, queryWrapper);
    }


    @Override
    public IPage<ShoppingCart> pageByCommodity(Page<ShoppingCart> page, String account) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(account)) {
            // 使用LIKE进行模糊查询
            queryWrapper.eq(ShoppingCart::getAccount, account);
        }
        return baseMapper.selectPage(page, queryWrapper);

    }
}
