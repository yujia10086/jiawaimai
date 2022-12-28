package com.jia.waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jia.waimai.common.R;
import com.jia.waimai.entity.Category;
import com.jia.waimai.entity.Dish;
import com.jia.waimai.entity.DishFlavor;
import com.jia.waimai.entity.Employee;
import com.jia.waimai.service.CategoryService;
import com.jia.waimai.service.DishFlavorService;
import com.jia.waimai.service.DishService;
import com.jia.waimai.vo.DishVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Dish> dishPage=new Page<>(page,pageSize);
        LambdaQueryWrapper<Dish> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name), Dish::getName,name).orderByDesc(Dish::getSort);
        dishService.page(dishPage,wrapper);
        Page<DishVo> dishVoPage=new Page<>();
        BeanUtils.copyProperties(dishPage,dishVoPage,"records");
        List<Dish> records = dishPage.getRecords();
        List<DishVo> list=records.stream().map((item)->{
            DishVo dishVo=new DishVo();
            BeanUtils.copyProperties(item,dishVo);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category!=null){
                String categoryName = category.getName();
                dishVo.setCategoryName(categoryName);

            }
            return dishVo;
        }).collect(Collectors.toList());
        dishVoPage.setRecords(list);
        return R.success(dishVoPage);

    }
    @GetMapping("/{id}")
    public R<DishVo> getById(@PathVariable("id") Long id){
        log.info("id={}",id);
        DishVo dishVo=new DishVo();
        Dish byId = dishService.getById(id);
        List<DishFlavor> list = dishFlavorService.list(new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, id));
        BeanUtils.copyProperties(byId,dishVo);
        dishVo.setFlavors(list);
        return R.success(dishVo);
    }
    @DeleteMapping
    public R<String> delete(Long[] ids){
        dishService.removeByIdsWithFlavor(Arrays.asList(ids));
        return R.success("批量删除成功");
    }
    @PostMapping()
    public R<String> save(@RequestBody DishVo dishVo){
        dishService.saveWithFlavor(dishVo);
        return R.success("保存成功");
    }
    @PutMapping
    public R<String> update(@RequestBody DishVo dishVo){
        log.info("dishVo{}",dishVo);
        dishService.updateWithFlavor(dishVo);
        return R.success("修改成功");
    }
    @GetMapping("/list")
    public R<List<Dish>> listR(Dish dish){
        List<Dish> list = dishService.list(
                new LambdaQueryWrapper<Dish>()
                        .eq(dish.getCategoryId()!=null,Dish::getCategoryId, dish.getCategoryId())
                        .orderByAsc(Dish::getSort)
                        .orderByDesc(Dish::getUpdateTime)
                        .eq(Dish::getStatus,1));
        return R.success(list);
    }

}
