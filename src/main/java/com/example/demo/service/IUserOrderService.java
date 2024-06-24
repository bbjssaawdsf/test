package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.po.Result;
import com.example.demo.po.UserOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-06-14
 */
public interface IUserOrderService extends IService<UserOrder> {

    List<UserOrder> likeCommodity(String account);

    IPage<UserOrder> pageByAccount(Page<UserOrder> page, String account);

    List<Map<Integer,Object>> countbycommodity();
    Result Test();
}
