package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.po.Commodity;
import com.example.demo.po.Result;
import com.example.demo.po.Variety;
import com.example.demo.po.user_profile;
import com.example.demo.service.impl.CommodityServiceImpl;
import com.example.demo.service.impl.VarietyServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Api(tags="商品种类管理接口")
@RestController
@RequestMapping("/variety")
@CrossOrigin(origins = "*")
public class VarietyController {
    @Autowired
    VarietyServiceImpl varietyService;
    @ApiOperation("新增商品种类接口")
    @PostMapping("/save")
    private Result saveCodi( @RequestBody Variety variety){
        Variety v=varietyService.getById(variety.getVarietyId());
        if (v!=null){
            return Result.error("已存在");
        }
        varietyService.save(variety);
        return Result.success("新增成功");
    }
    @ApiOperation("删除商品种类接口")
    @PostMapping("/delete/{account}")
    public Result deleteCommodityById(@ApiParam("用户account") @PathVariable("account") String account) {
        Variety variety= varietyService.getById(account);

        if(variety==null){
            return Result.error("不存在");
        }
        varietyService.removeById(account);
        return Result.success("成功");
    }

    @ApiOperation("根据商品编号查询商品种类接口")
    @PostMapping("/select/{account}")
    public Result queryUserById(@ApiParam("用户account") @PathVariable("account") String account) {
        Variety user = varietyService.getById(account);
        if(user==null){
            return Result.error("不存在");
        }
        return Result.success(user);

    }
    @ApiOperation("更改商品种类接口")
    @PostMapping("/change")
    public Result UpdateUser( @RequestBody Variety variety) {
        log.info(variety.toString());
        UpdateWrapper updateWrapperq=new UpdateWrapper();
        updateWrapperq.eq("variety_id",variety.getVarietyId());
        if (variety.getVariety()!= null) {
            updateWrapperq.set("variety", variety.getVariety());
        }
        if (variety.getIntroduce() != null) {
            updateWrapperq.set("introduce", variety.getIntroduce());
        }

        //service.update(user,updateWrapperq);
        boolean updateCount = varietyService.update(updateWrapperq); // 注意这里只需要传入UpdateWrapper，因为我们已经设置了所有要更新的字段和条件
        if (updateCount ) {
            Variety updatedUserProfile = varietyService.getById(variety.getVarietyId()); // 尝试获取更新后的用户信息
            return Result.success(updatedUserProfile);
        } else {
            // 更新失败，返回相应的错误信息或结果
            return Result.error("更新失败");
        }
    }
    @ApiOperation("分页模糊查询接口")
    @GetMapping("/Page")
    public Result PageQuey(@RequestParam int pageNO,@RequestParam int pageSize ,String account){
        Page<Variety> page = Page.of(pageNO, pageSize);
       // page.addOrder(new OrderItem("variety_id",true));
        IPage<Variety> resultPage = varietyService.pageByAccount(page, account);
        //varietyService.page(page);
        long total= resultPage.getTotal();
        System.out.println("total"+total);
        long pages= resultPage.getPages();
        System.out.println("pages"+pages);
        List<Variety> commodities= resultPage.getRecords();

        commodities.forEach(System.out::println);
        return Result.success(commodities);
    }
    @ApiOperation("模糊查询商品接口")
    @PostMapping("/selectlike/{account}")
    public Result queryUserByname( @PathVariable("account") String account) {
        QueryWrapper queryWrapper= new QueryWrapper();
        if(account!=null)
            queryWrapper.like("variety",account);
        List<Variety> user= varietyService.list(queryWrapper);
        return Result.success(user);

    }
    @ApiOperation("返回查询结果数量和总数接口")
    @GetMapping("/num")
    public Result num( String account) {
        QueryWrapper queryWrapper= new QueryWrapper();
        if(account!=null)
        queryWrapper.like("variety",account);
        return Result.success(varietyService.count(queryWrapper));
    }
    @ApiOperation("批量删除种类接口")
    @PostMapping("/deletes")
    public Result deleteCommodityByIds(@ApiParam("用户account") @RequestBody List<String> account) {
        for (String string:account) {
            varietyService.removeById(string);
        }
        return Result.success("成功");
    }
}
