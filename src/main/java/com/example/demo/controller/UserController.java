package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.po.*;
import com.example.demo.service.impl.CommodityServiceImpl;
import com.example.demo.service.impl.UserServicelmpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-06-14
 */
@Api(tags="账号管理接口")
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserServicelmpl commodityService;
    @PostMapping("/user/save")
    @ApiOperation("新增用户接口")
    private Result saveCodi(@RequestBody user commodity){
        user u=commodityService.getById(commodity.getAccount());
        if(u!=null){
            return Result.error("用户存在");
        }
        commodityService.getById(commodity.getAccount());
        commodityService.save(commodity);
        return Result.success(commodity);
    }
    @ApiOperation("删除用户接口")
    @PostMapping("/user/delete/{account}")
    public Result deleteCommodityById(@ApiParam("用户account") @PathVariable("account") String account) {
        user u=commodityService.getById(account);
        if(u==null){
            return Result.error("用户不存在");
        }
        commodityService.removeById(account);
       return Result.success("成功");
    }

    @ApiOperation("根据账户查询用户接口")
    @PostMapping("/user/select/{account}")
    public Result queryUserById(@ApiParam("用户account") @PathVariable("account") String account) {
        user user = commodityService.getById(account);
        if(user!=null){
            return Result.error("用户不存在");
        }
        return Result.success(user);

    }
    @ApiOperation("返回查询结果数量和总数接口")
    @PostMapping("/num/{account}")
    public Result num(@PathVariable("account") String account) {
        QueryWrapper queryWrapper= new QueryWrapper();
        if(account!=null)
        queryWrapper.like("account",account);
        return Result.success(commodityService.count(queryWrapper));
    }
    @ApiOperation("模糊查询商品接口")
    @PostMapping("/selectlike/{account}")
    public Result queryUserByname(@PathVariable("account") String account) {
        QueryWrapper queryWrapper= new QueryWrapper();
        if(account!=null)
            queryWrapper.like("account",account);
        List<user> user= commodityService.list(queryWrapper);
        return Result.success(user);

    }
    @ApiOperation("分页查询接口")
    @GetMapping("/Page")
    public Result PageQuey(@RequestParam int pageNO,@RequestParam int pageSize){
        Page<user> page = Page.of(pageNO, pageSize);
       // page.addOrder(new OrderItem("age",true));

        commodityService.page(page);
        long total= page.getTotal();
        System.out.println("total"+total);
        long pages= page.getPages();
        System.out.println("pages"+pages);
        List<user> commodities= page.getRecords();

        commodities.forEach(System.out::println);
        return Result.success(commodities);
    }
    @ApiOperation("更改密码接口")
    @PostMapping("/change")
    public Result UpdateUser(@RequestBody user variety) {
        UpdateWrapper updateWrapperq=new UpdateWrapper();
        if(variety.getAccount()!=null)
        updateWrapperq.eq("account",variety.getAccount());
        if(variety.getPassword()!=null)
            updateWrapperq.set("password", variety.getPassword());

        if (variety.getType()!=null)
            updateWrapperq.set("type", variety.getType());


        //service.update(user,updateWrapperq);
        boolean updateCount = commodityService.update(updateWrapperq); // 注意这里只需要传入UpdateWrapper，因为我们已经设置了所有要更新的字段和条件
        if (updateCount ) {
            user updatedUserProfile = commodityService.getById(variety.getAccount()); // 尝试获取更新后的用户信息
            return Result.success(updatedUserProfile);
        } else {
            // 更新失败，返回相应的错误信息或结果
            return Result.error("更新失败");
        }
    }

}
