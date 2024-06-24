package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.po.Commodity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.po.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-06-14
 */
public interface ICommodityService extends IService<Commodity> {


    List<Commodity> likeCommodity(String account);
    public boolean updateCommoditySelective(Commodity commodity, UpdateWrapper<Commodity> extraWrapper);

    IPage<Commodity> pageByAccount(Page<Commodity> page, String account);

    IPage<Commodity> pageByCommodity(Page<Commodity> page, String account);
     Result Test();
}
