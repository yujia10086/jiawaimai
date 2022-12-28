package com.jia.waimai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jia.waimai.entity.DishFlavor;
import com.jia.waimai.service.DishFlavorService;
import com.jia.waimai.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 王誉佳
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-12-25 12:26:58
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

    @Override
    public void deleteByIds(List<Long> asList) {
        this.baseMapper.removeByDishId(asList);
    }
}




