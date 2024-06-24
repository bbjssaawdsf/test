package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.po.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-06-22
 */
public interface IShoppingCartService extends IService<ShoppingCart> {

             IPage<ShoppingCart> pageByCommodity(Page<ShoppingCart> page, String account) ;

        IPage<ShoppingCart> pageByAccount(Page<ShoppingCart> page, String account);
}
