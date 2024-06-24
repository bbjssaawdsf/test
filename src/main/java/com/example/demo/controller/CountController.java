package com.example.demo.controller;
import com.example.demo.po.UserOrder;
import com.example.demo.po.user_profile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.po.Result;
import com.example.demo.service.User_pService;
import com.example.demo.service.impl.UserOrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags="统计接口")
@RequestMapping("/count")
@CrossOrigin(origins = "*")

public class CountController {
    @Autowired
    User_pService service;
    @Autowired
    UserOrderServiceImpl userOrderService;
    @ApiOperation("统计男")
    @PostMapping("/gender")
    public Result CountGend(){
        QueryWrapper<user_profile> queryWrapper=new QueryWrapper();
        queryWrapper.eq("gender","男");
        return Result.success(service.count(queryWrapper));
    }
    @ApiOperation("统计女")
    @PostMapping("/gender2")
    public Result CountGend2(){
        QueryWrapper<user_profile> queryWrapper=new QueryWrapper();
        queryWrapper.eq("gender","女");
        return Result.success(service.count(queryWrapper));
    }
    @ApiOperation("返回前八个商品")
    @PostMapping("/commodity")
    public Result Countcommodity(){
/*        QueryWrapper<UserOrder> queryWrapper=new QueryWrapper();
        queryWrapper.groupBy("commodity_id");
        List<Map<Integer,Object>> commodity=userOrderService.countbycommodity();
        for (Map<Integer, Object> item:commodity){
            Long commodityId= (Long) item.get("commodity_id");
            BigDecimal orderCount =(BigDecimal) item.get("order_count");
            System.out.println(commodityId +":"+orderCount);
        }
        return*/
        return userOrderService.Test();

    }
}
