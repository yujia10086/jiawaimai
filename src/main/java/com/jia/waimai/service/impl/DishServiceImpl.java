package com.jia.waimai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jia.waimai.entity.Dish;
import com.jia.waimai.entity.DishFlavor;
import com.jia.waimai.service.DishFlavorService;
import com.jia.waimai.service.DishService;
import com.jia.waimai.mapper.DishMapper;
import com.jia.waimai.vo.DishVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 王誉佳
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2022-12-23 22:34:57
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

    @Autowired
    private DishFlavorService dishFlavorService;


    @Override
    public Integer dishCount(Long ids) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getCategoryId,ids);
        int count = this.count(wrapper);
        return count;
    }

    @Override
    @Transactional
    public void saveWithFlavor(DishVo dishVo) {
        this.save(dishVo);
        Long id = dishVo.getId();
        List<DishFlavor> flavors = dishVo.getFlavors();
        flavors= flavors.stream().map((item) -> {
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public void removeByIdsWithFlavor(List<Long> asList) {
        this.removeByIds(asList);
        dishFlavorService.deleteByIds(asList);
    }

    @Override
    public List<DishFlavor> get(Long id) {

        return null;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishVo dishVo) {
        this.updateById(dishVo);
        LambdaQueryWrapper<DishFlavor> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId,dishVo.getId());
        dishFlavorService.remove(wrapper);
        List<DishFlavor> flavors = dishVo.getFlavors();
        flavors=flavors.stream().map((item)->{
            item.setDishId(dishVo.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);

    }

}




