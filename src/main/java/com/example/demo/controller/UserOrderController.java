package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.po.*;
import com.example.demo.service.impl.CommodityServiceImpl;
import com.example.demo.service.impl.UserOrderServiceImpl;
import com.example.demo.service.impl.UserServicelmpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-06-14
 */
@Slf4j
@Api(tags="用户订单管理接口")
@RestController
@RequestMapping("/userorder")
@CrossOrigin(origins = "*")
public class UserOrderController {

    @Autowired
    UserOrderServiceImpl commodityService;
    @PostMapping("/save")
    @ApiOperation("新增商品接口")
    private Result saveCodi( @RequestBody UserOrder commodity){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("orderid",commodity.getOrderid());
        UserOrder u = commodityService.getOne(queryWrapper);
        if(u!=null){
            return Result.error("订单编号已经存在");
        }
        commodityService.save(commodity);
        return Result.success(commodity);
    }
    @ApiOperation("删除商品接口")
    @PostMapping("/delete/{orderid}")
    public Result deleteCommodityById(@ApiParam("用户orderid") @PathVariable("orderid") String orderid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("orderid",orderid);
        UserOrder u = commodityService.getOne(queryWrapper);
        if (u==null){
            return Result.error("订单不存在");
        }
        commodityService.removeById(orderid);
        return Result.success("成功");
    }

    @ApiOperation("根据订单编号查询商品接口")
    @PostMapping("/select/{orderid}")
    public Result queryUserById(@ApiParam("用户orderid") @PathVariable("orderid") String orderid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("orderid",orderid);
        UserOrder user = commodityService.getOne(queryWrapper);
        if(user==null){
            return Result.error("用户不存在");
        }
        return Result.success(user);

    }
    @ApiOperation("更改商品接口")
    @PostMapping("/change")
    public Result UpdateUser( @RequestBody UserOrder commodity) {
        log.info(commodity.toString());
        UpdateWrapper updateWrapperq=new UpdateWrapper();

        updateWrapperq.eq("orderid",commodity.getOrderid());
        if (commodity.getVarietyid()!= null) {
            updateWrapperq.set("varietyid", commodity.getVarietyid());
        }
        if (commodity.getBuyTime() != null) {
            updateWrapperq.set("buy_time", commodity.getBuyTime());
        }
        if (commodity.getCommodityId() != null) {
            updateWrapperq.set("commodity_id", commodity.getCommodityId());
        }
        if(commodity.getOrder_price()!=null){
            updateWrapperq.set("order_price",commodity.getOrder_price());
        }
        if(commodity.getNumber()!=null){
            updateWrapperq.set("number",commodity.getNumber());
        }
        if(commodity.getAccount()!=null){
            updateWrapperq.set("account",commodity.getAccount());
        }

        //service.update(user,updateWrapperq);
        boolean updateCount = commodityService.update(updateWrapperq); // 注意这里只需要传入UpdateWrapper，因为我们已经设置了所有要更新的字段和条件
        if (updateCount ) {
            UserOrder updatedUserProfile = commodityService.getById(commodity.getCommodityId()); // 尝试获取更新后的用户信息
            return Result.success(updatedUserProfile);
        } else {
            // 更新失败，返回相应的错误信息或结果
            return Result.error("更新失败");
        }
    }
    @ApiOperation("分页模糊查询接口")
    @GetMapping("/Page")
    public Result PageQuey(@RequestParam int pageNO,@RequestParam int pageSize,String account){
        Page<UserOrder> page = Page.of(pageNO, pageSize);
        //page.addOrder(new OrderItem("age",true));
        IPage<UserOrder> resultPage = commodityService.pageByAccount(page, account);

        long total= resultPage.getTotal();
        System.out.println("total"+total);
        long pages= resultPage.getPages();
        System.out.println("pages"+pages);
        List<UserOrder> commodities= resultPage.getRecords();

        commodities.forEach(System.out::println);
        return Result.success(commodities);
    }
    @ApiOperation("模糊查询商品接口")
    @PostMapping("/selectlike/{account}")
    public Result queryUserByname(@PathVariable("account") String account) {
        QueryWrapper queryWrapper= new QueryWrapper();
        if(account!=null)
            queryWrapper.like("account",account);
        List<UserOrder> user= commodityService.list(queryWrapper);
        return Result.success(user);

    }
    @ApiOperation("返回查询结果数量和总数接口")
    @GetMapping("/num")
    public Result num( String account) {
        QueryWrapper queryWrapper= new QueryWrapper();
        if(account!=null)
        queryWrapper.like("account",account);

        return Result.success(commodityService.count(queryWrapper));
    }
    @ApiOperation("批量删除商品接口")
    @PostMapping("/deletes")
    public Result deleteCommodityByIds(@ApiParam("用户account") @RequestBody List<String> account) {
        for (String string:account) {
            commodityService.removeById(string);
        }
        return Result.success("成功");
    }
}
