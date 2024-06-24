package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.po.Variety;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.po.user_profile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-06-14
 */
public interface IVarietyService extends IService<Variety> {

    List<Variety> likeCommodity(String account);

    IPage<Variety> pageByAccount(Page<Variety> page, String account);
}
