package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.po.Commodity;
import com.example.demo.po.Variety;
import com.example.demo.Mapper.VarietyMapper;
import com.example.demo.po.user_profile;
import com.example.demo.service.IVarietyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-06-14
 */
@Service
public class VarietyServiceImpl extends ServiceImpl<VarietyMapper, Variety> implements IVarietyService {
    @Autowired
    VarietyMapper varietyMapper;
    @Override
    public List<Variety> likeCommodity(String account) {
        QueryWrapper<Variety> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("variety",account);
        List<Variety> users= varietyMapper.selectList(queryWrapper);
        return users  ;
    }

    @Override
    public IPage<Variety> pageByAccount(Page<Variety> page, String account) {
        LambdaQueryWrapper<Variety> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(account)) {
            // 使用LIKE进行模糊查询
            queryWrapper.like(Variety::getVarietyId, account);
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

}
