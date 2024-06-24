package com.example.demo.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dto.CommodityDto;
import com.example.demo.po.*;
import com.example.demo.service.User_pService;
import com.example.demo.service.impl.CommodityServiceImpl;
import com.example.demo.service.impl.UserOrderServiceImpl;
import com.example.demo.service.impl.VarietyServiceImpl;
import com.example.demo.utils.AliOSSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 *
 *
 *
 *
 * @author author
 * @since 2024-06-14
 *
 * 商品控制器
 * 包含增删查改
 * 模糊查询
 * 库存管理
 *
 */
@Api(tags="商品管理接口")
@RestController
@RequestMapping("/commodity")
@CrossOrigin(origins = "*")
public class CommodityController {
    static int num;
    static int allnum;
    @Autowired
    CommodityServiceImpl commodityService;
    @Autowired
    User_pService service;
    @Autowired
    UserOrderServiceImpl userOrderService;
    @Autowired
    VarietyServiceImpl varietyService;


    /*根据前端传的商品Dto数据,
    把图片上传oss后把返回的路径
    再用hutoll考培工具把Dto拷贝到po,给po
    再将po类存到数据库中

    * */
    @PostMapping("/save")
    @ApiOperation("新增商品接口")
    private Result saveCodi( CommodityDto dto) throws IOException {
        Commodity c=commodityService.getById(dto.getCommodityId());
        Commodity commodity=new Commodity();
        BeanUtil.copyProperties(dto,commodity,true);

        if (dto.getImg() != null) {
            // 假设你有一个方法可以将 MultipartFile 保存到文件系统中并返回文件路径
            AliOSSUtils aliOSSUtils = new AliOSSUtils();
            String  url=aliOSSUtils.upload(dto.getImg());
            commodity.setImg(url);
        }

        if(c!=null){
            return Result.error("以存在");
        }


        commodityService.save(commodity);
        return Result.success(commodity);
    }
    /*
    *
    * 根据用户账户删除表中对应字段的商品
    *用mybatisplus中的Iservice接口提供的方法
    *
    * */
    @ApiOperation("删除商品接口")
    @PostMapping("/delete/{account}")
    public Result deleteCommodityById(@ApiParam("用户account") @PathVariable("account") String account) {
        Commodity c=commodityService.getById(account);
        if (c==null){
            return Result.error("不存在");
        }
        commodityService.removeById(account);
        return Result.success("成功");
    }

    /*

    * 根据一个list集合
    * 遍历其中的字段
    * 删除对应表中的字段
    * 达到删除
    * 用mybatisplus中的Iservice接口提供的方法
    * */
    @ApiOperation("批量删除商品接口")
    @PostMapping("/deletes")
    public Result deleteCommodityByIds(@ApiParam("用户account") @RequestBody List<String> account) {
        for (String string:account) {
            commodityService.removeById(string);
        }
        return Result.success("成功");
    }
/*
*
* 用iservice自带的方法完成根据商品编号的字段查询
* 并返回查到的数据
*
* */
    @ApiOperation("根据商品编号查询商品接口")
    @PostMapping("/select/{account}")
    public Result queryUserById(@ApiParam("用户account") @PathVariable("account") String account) {
        Commodity user = commodityService.getById(account);
        if(user==null){
            return Result.error("不存在");
        }
        return Result.success(user);

    }

    /*根据前端传的商品Dto数据,
    把图片上传oss后把返回的路径
    再用hutoll考培工具把Dto拷贝到po,给po
    再将po类存到数据库中
    用UpdateWrapper构造设置跟新条件
    判断dto中所有字段是否为空
    再用iservice中的update根据条件跟新数据

    * */
    @ApiOperation("更改商品接口")
    @PostMapping("/change")
    public Result UpdateUser( CommodityDto dto) throws IOException {
        Commodity commodity=new Commodity();
        UpdateWrapper updateWrapperq=new UpdateWrapper();
        BeanUtil.copyProperties(dto,commodity,true);

        if (dto.getImg() != null) {
            // 假设你有一个方法可以将 MultipartFile 保存到文件系统中并返回文件路径
            AliOSSUtils aliOSSUtils = new AliOSSUtils();
            String  url=aliOSSUtils.upload(dto.getImg());
            updateWrapperq.set("img",url);
        }
        updateWrapperq.eq("commodity_id",commodity.getCommodityId());
        if (commodity.getCommodity()!= null) {
            updateWrapperq.set("commodity", commodity.getCommodity());
        }
        if (commodity.getVarietyId() != null) {
            updateWrapperq.set("variety_id", commodity.getVarietyId());
        }
        if (commodity.getQuantity() != null) {
            updateWrapperq.set("quantity", commodity.getQuantity());
        }
        if (commodity.getIntroduce() != null) {
            updateWrapperq.set("introduce", commodity.getIntroduce());
        }
        if (commodity.getUnitPrice() != null) {
            updateWrapperq.set("unit_price", commodity.getUnitPrice());
        }
        if (commodity.getVipPrice() != null) {
            updateWrapperq.set("vip_price", commodity.getVipPrice());
        }

        //service.update(user,updateWrapperq);
        boolean updateCount = commodityService.update(updateWrapperq); // 注意这里只需要传入UpdateWrapper，因为我们已经设置了所有要更新的字段和条件
        if (updateCount ) {
            Commodity updatedUserProfile = commodityService.getById(commodity.getCommodityId()); // 尝试获取更新后的用户信息
            return Result.success(updatedUserProfile);
        } else {
            // 更新失败，返回相应的错误信息或结果
            return Result.error("更新失败");
        }
    }

    /*
    *
    * 配置好page分页
    * 调用自己写的service 层的bycommodity
    * 把分页条件和商品名传进去查到满足条件的数据
    * 用Result封装返回结果
    *
    * */
    @ApiOperation("分页模糊按commodityID查询接口")
    @GetMapping
            ("/Page")
    public Result PageQuey(@RequestParam int pageNO,@RequestParam int pageSize ,String account){
        Page<Commodity> page = Page.of(pageNO, pageSize);
        page.addOrder(new OrderItem("commodity_id",true));
        IPage<Commodity> resultPage = commodityService.pageByAccount(page, account);

        long total= resultPage.getTotal();
        System.out.println("total"+total);
        long pages= resultPage.getPages();
        System.out.println("pages"+pages);
        List<Commodity> commodities= resultPage.getRecords();
        commodities.forEach(System.out::println);
        return Result.success(commodities);
     }
    /*
     *
     * 配置好page分页
     * 调用自己写的service 层的bycommodity
     * 把分页条件和商品名传进去查到满足条件的数据
     * 用Result封装返回结果
     *
     * */
    @ApiOperation("分页模糊按commodity查询接口")
    @GetMapping("/Page2")
    public Result PageQuey2(@RequestParam int pageNO,@RequestParam int pageSize ,String account){
        Page<Commodity> page = Page.of(pageNO, pageSize);
        page.addOrder(new OrderItem("commodity_id",true));
        IPage<Commodity> resultPage = commodityService.pageByCommodity(page, account);

        long total= resultPage.getTotal();
        System.out.println("total"+total);
        long pages= resultPage.getPages();
        System.out.println("pages"+pages);
        List<Commodity> commodities= resultPage.getRecords();
        commodities.forEach(System.out::println);
        return Result.success(commodities);
    }

    /*
    * 分页查询改良
    * */
    @ApiOperation("模糊查询商品接口")
    @PostMapping("/selectlike/{accpont}")
    public Result queryUserByname(@PathVariable("account") String account) {
        QueryWrapper queryWrapper= new QueryWrapper();
        if(account!=null)
            queryWrapper.like("commodity",account);
        List<Commodity> user= commodityService.list(queryWrapper);
        return Result.success(user);

    }

    /**
    *
     *
     * 分页查询改良
     * 返回指定数据给前端展示商品
    * */
    @ApiOperation("返回指定数量的数据")
    @GetMapping("/nums")
    public Result getCommoditiesByLimit(@RequestParam(value = "limit") int limit,@RequestParam String commodity) {
       int pageNO=1;
        Page<Commodity> page = Page.of(pageNO, limit);
        IPage<Commodity> resultPage = commodityService.pageByCommodity(page, commodity);
        List<Commodity> commodities= resultPage.getRecords();
        return Result.success(commodities);
    }

    /*
    *
    * 统计指定字段
    * 模糊查询指定字段
    * 空的话就是查全部
    * 返回统计数
    *
    * */
        @ApiOperation("返回查询结果数量和总数接口")
    @GetMapping("/num")
    public Result num( String account) {
        QueryWrapper  queryWrapper= new QueryWrapper();
        if(account!=null)
        queryWrapper.like("commodity",account);
        return Result.success(commodityService.count(queryWrapper));
    }

    /*
    *
    * 用于商品购买
    * 加同步函数解决线程安全问题
    *
    *
    * */
    @ApiOperation("库存减接口")
    @GetMapping("/update")
    public  synchronized Result SellTicket1( @ApiParam("账户id") @RequestParam int commodityid , @RequestParam String account, @ApiParam("扣减数量") @RequestParam int number){
        Commodity commodity=commodityService.getById( commodityid);
        user_profile user_profile= service.getById(account);
        if(commodity.getQuantity()>=number){
            commodity.setQuantity(commodity.getQuantity()-number);
            commodityService.updateById(commodity);

            UserOrder userOrder = new UserOrder();

            Random random=new Random();
            int min=1000;
            int max=9999;
            int rand=min+random.nextInt(max-min+1);
/*            int i=1;
            while(i==1)
            if(userOrderService.getById(rand)!=null){
                rand=min+random.nextInt(max-min+1);
            }*/

            userOrder.setAccount(account)
                    .setCommodityId(commodityid)
                    .setVarietyid(commodity.getVarietyId())
                    .setNumber(number)
                    .setBuyTime(LocalDate.now())
                    .setOrderid(rand);
            if(user_profile.getType().equals("user")){
                int price=number * commodity.getUnitPrice();
                userOrder.setOrder_price(price);
            }else{
                int price=number * commodity.getVipPrice();
                userOrder.setOrder_price(price);
            }
            userOrderService.save(userOrder);

        }else return Result.error(commodity.getCommodity()+"库存不足");

        return Result.success("成功购买");
    }

}
