package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.Mapper.CommodityMapper;
import com.example.demo.po.*;
import com.example.demo.Mapper.UserOrderMapper;
import com.example.demo.service.IUserOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-06-14
 */
@Service
public class UserOrderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrder> implements IUserOrderService {
    @Autowired
    UserOrderMapper userOrderMapper;
    @Autowired
    CommodityMapper
    commodityMapper;
    @Override
    public List<UserOrder> likeCommodity(String account) {
        QueryWrapper<UserOrder> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("account",account);
        List<UserOrder> users= userOrderMapper.selectList(queryWrapper);
        return users  ;
    }

    @Override
    public IPage<UserOrder> pageByAccount(Page<UserOrder> page, String account) {
        LambdaQueryWrapper<UserOrder> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(account)) {
            // 使用LIKE进行模糊查询
            queryWrapper.like(UserOrder::getAccount, account);
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<Map<Integer,Object>> countbycommodity() {

        return userOrderMapper.selectuser();
    }

    public Result Test(){
        List<Commodity> commodities = commodityMapper.selectList(new QueryWrapper<Commodity>());
        List<Orderall> orderalls = new ArrayList<>();
        List<Object> commodity_id1 = commodities.stream().map(aa -> {
            System.out.println(aa.getCommodityId());
            List<UserOrder> commodity_id2 = query().eq("commodity_id", aa.getCommodityId()).list();
            System.out.println(commodity_id2);
            int commodity_id = commodity_id2
                    .stream().mapToInt(UserOrder::getNumber).sum();
            orderalls.add(new Orderall(commodity_id, aa.getCommodityId()));
            return null;
        }).collect(Collectors.toList());
        Collections.sort(orderalls);
        return Result.success(orderalls);
    }

}

