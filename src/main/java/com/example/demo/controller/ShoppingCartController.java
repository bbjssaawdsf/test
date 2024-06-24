package com.example.demo.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dto.CommodityDto;
import com.example.demo.dto.ShoppingCartDto;
import com.example.demo.po.Commodity;
import com.example.demo.po.Result;
import com.example.demo.po.ShoppingCart;
import com.example.demo.service.impl.CommodityServiceImpl;
import com.example.demo.service.impl.ShoppingCartServiceImpl;
import com.example.demo.utils.AliOSSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-06-22
 *
 *
 */
@Slf4j
@Api(tags="购物车")
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController

@RequestMapping("/shopping-cart")

public class ShoppingCartController {
    @Autowired
    ShoppingCartServiceImpl shoppingCartService;
    @Autowired
    CommodityServiceImpl commodity;
    @GetMapping("/save")
    @ApiOperation("新增商品接口")
    private Result saveCodi(@RequestParam String account,@RequestParam int commodityid,@RequestParam int number) throws IOException {
    //    ShoppingCart c=shoppingCartService.getById(dto.getId());
 /*       ShoppingCart commodity=new ShoppingCart();
        BeanUtil.copyProperties(dto,commodity,true);*/
        Random random=new Random();
        int min=1;
        int max=9999;
        int rand=min+random.nextInt(max-min+1);
        ShoppingCart shoppingCart = new ShoppingCart();
        Commodity c=commodity.getById(commodityid);
        shoppingCart.setCommodity(c.getCommodity())
                .setCommodityId(commodityid)
                .setImg(c.getImg())
                .setNumber(number)
                .setAccount(account)
                .setId(rand);
/*        if   (dto.getImg() != null) {
            // 假设你有一个方法可以将 MultipartFile 保存到文件系统中并返回文件路径
            AliOSSUtils aliOSSUtils = new AliOSSUtils();
            String  url=aliOSSUtils.upload(dto.getImg());
            commodity.setImg(url);
        }*/

/*
        if(c!=null){
            return Result.error("以存在");
        }

*/

        shoppingCartService.save(shoppingCart);
        return Result.success(shoppingCart);
    }
    @ApiOperation("删除商品接口")
    @GetMapping("/delete")
    public Result deleteCommodityById(@ApiParam("用户id") @RequestParam int id) {
        ShoppingCart c=shoppingCartService.getById(id);
        if (c==null){
            return Result.error("不存在");
        }
        shoppingCartService.removeById(id);
        return Result.success("成功");
    }
    @ApiOperation("更改商品接口")
    @PostMapping("/change")
    public Result UpdateUser( ShoppingCartDto dto) throws IOException {
        ShoppingCart commodity=new ShoppingCart();
        UpdateWrapper updateWrapperq=new UpdateWrapper();
        BeanUtil.copyProperties(dto,commodity,true);

        if (dto.getImg() != null) {
            // 假设你有一个方法可以将 MultipartFile 保存到文件系统中并返回文件路径
            AliOSSUtils aliOSSUtils = new AliOSSUtils();
            String  url=aliOSSUtils.upload(dto.getImg());
            updateWrapperq.set("img",url);
        }
        updateWrapperq.eq("id",commodity.getId());
        if (commodity.getCommodity()!= null) {
            updateWrapperq.set("commodity", commodity.getCommodity());
        }
        if (commodity.getCommodityId() != null) {
            updateWrapperq.set("commodity_id", commodity.getCommodityId());
        }
        if (commodity.getAccount() != null) {
            updateWrapperq.set("account", commodity.getAccount());
        }
        if (commodity.getNumber() != null) {
            updateWrapperq.set("number", commodity.getNumber());
        }
        //service.update(user,updateWrapperq);
        boolean updateCount = shoppingCartService.update(updateWrapperq); // 注意这里只需要传入UpdateWrapper，因为我们已经设置了所有要更新的字段和条件
        if (updateCount ) {
            ShoppingCart updatedUserProfile = shoppingCartService.getById(commodity.getId()); // 尝试获取更新后的用户信息
            return Result.success(updatedUserProfile);
        } else {
            // 更新失败，返回相应的错误信息或结果
            return Result.error("更新失败");
        }
    }
    @ApiOperation("分页模糊按account查询接口")
    @GetMapping
            ("/Page")
    public Result PageQuey(@RequestParam int pageNO,@RequestParam int pageSize ,String account){
        Page<ShoppingCart> page = Page.of(pageNO, pageSize);
        //page.addOrder(new OrderItem("commodity_id",true));
        IPage<ShoppingCart> resultPage = shoppingCartService.pageByAccount(page, account);

        long total= resultPage.getTotal();
        System.out.println("total"+total);
        long pages= resultPage.getPages();
        System.out.println("pages"+pages);
        List<ShoppingCart> commodities= resultPage.getRecords();
        commodities.forEach(System.out::println);
        return Result.success(commodities);
    }
    @ApiOperation("返回全部数量的数据")
    @GetMapping("/nums")
    public Result getCommoditiesByLimit(@RequestParam String account) {
        int pageNO=0;

        QueryWrapper<ShoppingCart> queryWrapper =new QueryWrapper<>();
        queryWrapper.like("commodity","");
        long limit=shoppingCartService.count(queryWrapper);
        log.info(+limit+"w");
        Page<ShoppingCart> page = Page.of(pageNO, limit);
        IPage<ShoppingCart> resultPage = shoppingCartService.pageByCommodity(page, account);
        List<ShoppingCart> commodities= resultPage.getRecords();
        return Result.success(commodities);
    }

    @ApiOperation("购物车中是否已存在")
    @GetMapping("/isExist")
    public Result isExist(@RequestParam String account, @RequestParam String commodityId) {
        int pageNO=0;

        QueryWrapper<ShoppingCart> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("commodity_id", commodityId).eq("account", account);
        ShoppingCart shoppingCart=shoppingCartService.getOne(queryWrapper);
      //  log.info(shoppingCart.to);
        if (shoppingCart == null) return Result.error("购物车中已存在");
        return Result.success();
    }
}
