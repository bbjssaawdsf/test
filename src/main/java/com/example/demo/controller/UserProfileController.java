package com.example.demo.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.domain.dto.UserFormDTO;
import com.example.demo.domain.vo.UserVO;
import com.example.demo.domain.pojo.User;
import com.example.demo.domain.query.UserQuery;

import com.example.demo.dto.UserProfileDto;
import com.example.demo.po.*;
import com.example.demo.service.UserService;
import com.example.demo.service.User_pService;
import com.example.demo.service.impl.UserServicelmpl;
import com.example.demo.service.impl.User_pServicelmpl;
import com.example.demo.utils.AliOSSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Api(tags="账户信息管理接口")
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserProfileController {

private final User_pServicelmpl service;
@Autowired
UserServicelmpl userService;

    @ApiOperation("新增用户接口")
    @PostMapping("/save")
    public Result saveUser(UserProfileDto user) throws IOException {
        System.out.printf(user.toString());
        user_profile p=service.getById(user.getAccount());
        if (p!=null){
            return Result.error("已存在");
        }
        com.example.demo.po.user user1 = new user();
        user1.setAccount(user.getAccount());
        user1.setType(user.getType());
        user1.setPassword("123456");



        user_profile user_profile = new user_profile();
        BeanUtil.copyProperties(user,user_profile,true);
        if(user.getAvatar()!=null){
            AliOSSUtils aliOSSUtils = new AliOSSUtils();
            String  url=aliOSSUtils.upload(user.getAvatar());
            user_profile.setAvatar(url);
        }
        System.out.println(user_profile);
        service.save(user_profile);
        userService.save(user1);
        return Result.success(user_profile);
    }

    @ApiOperation("删除用户接口")
    @PostMapping("/delete/{account}")
    public Result deleteUserById(@ApiParam("用户account") @PathVariable("account") String account) {

        user_profile u=service.getById(account);
        if (u==null){
            return Result.error("不存在");
        }
        service.removeById(account);
        return Result.success("成功");
    }

    @ApiOperation("根据账号查询用户接口")
    @GetMapping("/quer/{account}")
    public Result queryUserById(@ApiParam("用户account") @PathVariable("account") String account) {
        user_profile user = service.getById(account);
        if (user==null){
            return Result.error("不存在");
        }
        return Result.success(user);

    }
    @ApiOperation("获取默认图片")
    @GetMapping("/img")
    public Result Imga() {

        return Result.success("https://javaweb-s.oss-cn-beijing.aliyuncs.com/1.jpg");

    }

/*    @ApiOperation("查询用户接口")
    @PostMapping("/wwsss")
    public Result queryUserByIds(@ApiParam("用户账号") @RequestParam("account") user_profile user) {
        service.listByIds();
        return Result.success(users);
    }*/

    /*@ApiOperation("根据id扣余额接口")
    @PostMapping("/{id}/deduction{money}")
    public void deducbalanceByIds(

             @PathVariable("id") Long id,
            @ApiParam("扣减的金额") @PathVariable("money") Integer money) {
        service.deducbalanceById(id, money);
    }*/

    @ApiOperation("更改账户接口")
    @PostMapping("/update")
    public Result UpdateUser(UserProfileDto user ) throws IOException {
        UpdateWrapper  updateWrapperq=new UpdateWrapper();
        log.info(user.toString());
        updateWrapperq.eq("account",user.getAccount());
/*        updateWrapperq.set("age",user.getAge());
        updateWrapperq.set("preference",user.getPreference());
        updateWrapperq.set("phone",user.getPhone());
        updateWrapperq.set("avatar",user.getAvatar());*/
        if (user.getAge()!= 0) {
            updateWrapperq.set("age", user.getAge());
        }
        if (user.getPreference() != null) {
            updateWrapperq.set("preference", user.getPreference());
        }
        if (user.getPhone() != null) {
            updateWrapperq.set("phone", user.getPhone());
        }
        if (user.getAvatar() != null) {
            AliOSSUtils aliOSSUtils = new AliOSSUtils();
            String  url=aliOSSUtils.upload(user.getAvatar());
            updateWrapperq.set("avatar", url);
        }
        if (user.getType()!=null){
            updateWrapperq.set("type",user.getType());
        }
        if(user.getGender()!=null){
            updateWrapperq.set("gender",user.getGender());
        }
      //  base.mapper()

        //service.update(user,updateWrapperq);
        boolean updateCount = service.update(updateWrapperq); // 注意这里只需要传入UpdateWrapper，因为我们已经设置了所有要更新的字段和条件
        if (updateCount ) {
            user_profile updatedUserProfile = service.getById(user.getAccount()); // 尝试获取更新后的用户信息
            return Result.success(updatedUserProfile);
        } else {
            // 更新失败，返回相应的错误信息或结果
            return Result.error("更新失败");
        }
    }
    @ApiOperation("分页模糊查询接口")
    @GetMapping("/Page")
    public Result PageQuey(@RequestParam int pageNO,@RequestParam int pageSize, String account){
        Page<user_profile> page = Page.of(pageNO, pageSize);
        //page.addOrder(new OrderItem("age",true));
        IPage<user_profile> resultPage = service.pageByAccount(page, account);
        //service.page(page);
        long total= resultPage.getTotal();
        System.out.println("total "+total);
        long pages= resultPage.getPages();
        System.out.println("pages "+pages);
        List<user_profile> commodities= resultPage.getRecords();

        commodities.forEach(System.out::println);
        return Result.success(commodities);
    }
/*
    @Autowired
    private AliOSSUtils aliOSSUtils;

    @PostMapping()
    public Result upload(MultipartFile image) throws IOException {
        log.info("文件上传, 文件名: {}", image.getOriginalFilename());

        //调用阿里云OSS工具类进行文件上传
         url = aliOSSUtils.upload(image);

        log.info("文件上传完成,文件访问的url: {}", url);
        log.info("文件上传完成,文件访问的url: {}", image.getOriginalFilename());
        return Result.success(url);
    }
*/
@ApiOperation("模糊查询商品接口")
@GetMapping("/selectlike")
public Result queryUserByname(String account) {
    QueryWrapper queryWrapper= new QueryWrapper();
    if(account!=null)
        queryWrapper.like("account",account);
    List<user_profile> user= service.list(queryWrapper);
    return Result.success(user);

}
@ApiOperation("返回查询结果数量和总数接口")
@GetMapping("/num")
public Result num(@RequestParam String account) {
    QueryWrapper queryWrapper= new QueryWrapper();
    if(account!=null)
    queryWrapper.like("account",account);
    return Result.success(service.count(queryWrapper));
}
    @ApiOperation("批量删除商品接口")
    @PostMapping("/deletes")
    public Result deleteCommodityByIds(@ApiParam("用户account") @RequestBody List<String> account) {
        for (String string:account) {
            service.removeById(string);
        }
        return Result.success("成功");
    }
}
