package com.jia.waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jia.waimai.common.R;
import com.jia.waimai.entity.Category;
import com.jia.waimai.entity.Setmeal;
import com.jia.waimai.entity.SetmealDish;
import com.jia.waimai.service.CategoryService;
import com.jia.waimai.service.SetmealDishService;
import com.jia.waimai.service.SetmealService;
import com.jia.waimai.vo.SetmealVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/page")
    public R<Page> pageR(int page, int pageSize,String name){
        Page<Setmeal> setmealPage=new Page<>(page,pageSize);
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name),Setmeal::getName,name).orderByAsc(Setmeal::getUpdateTime);
        setmealService.page(setmealPage,wrapper);
        Page<SetmealVo> setmealVoPage=new Page<>();
        BeanUtils.copyProperties(setmealPage,setmealVoPage,"records");
        List<Setmeal> records = setmealPage.getRecords();
        List<SetmealVo> setmealVos = records.stream().map((item) -> {
            SetmealVo setmealVo = new SetmealVo();
            BeanUtils.copyProperties(item, setmealVo);
            List<SetmealDish> list = setmealDishService.list(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, item.getId()));
            setmealVo.setSetmealDishes(list);
            Category category = categoryService.getById(item.getCategoryId());
            if (category!=null){
                setmealVo.setCategoryName(category.getName());
            }
            return setmealVo;
        }).collect(Collectors.toList());
        setmealVoPage.setRecords(setmealVos);
        return R.success(setmealVoPage);

    }
    @GetMapping("/{id}")
    public R<Setmeal> getR(@PathVariable Long id){
        SetmealVo setmealVo=setmealService.getByIdWithDish(id);
        return R.success(setmealVo);
    }
    @PostMapping
    public R<String> save(@RequestBody SetmealVo setmealVo){
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealVo,setmeal);
        setmealService.save(setmeal);
        List<SetmealDish> setmealDishes = setmealVo.getSetmealDishes();
        setmealDishes=setmealDishes.stream().map((item)->{
            item.setSetmealId(setmeal.getId().toString());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
        return R.success("成功");

    }
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.deleteWithDish(ids);
        return R.success("成功");
    }
}
