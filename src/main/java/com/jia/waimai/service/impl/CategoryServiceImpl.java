package com.jia.waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jia.waimai.common.CustomException;
import com.jia.waimai.common.R;
import com.jia.waimai.entity.Category;
import com.jia.waimai.service.CategoryService;
import com.jia.waimai.mapper.CategoryMapper;
import com.jia.waimai.service.DishService;
import com.jia.waimai.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 王誉佳
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2022-12-23 20:48:47
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    @Override
    public void remove(Long ids) {
        Integer dishCount = dishService.dishCount(ids);
        if (dishCount>0){
            throw new CustomException("关联了菜品，不能删除");
        }
        Integer setCount = setmealService.setCount(ids);
        if (setCount>0){
            throw new CustomException("关联了套餐，不能删除");
        }
        this.removeById(ids);
    }
}




