package com.jia.waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jia.waimai.common.R;
import com.jia.waimai.entity.Category;
import com.jia.waimai.entity.Dish;
import com.jia.waimai.service.CategoryService;
import com.jia.waimai.service.DishService;
import com.jia.waimai.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/page")
    public R<Page> categoryPage(int page, int pageSize) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        Page page1 = new Page<>(page, pageSize);
        categoryService.page(page1, wrapper);
        return R.success(page1);
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Category category) {
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
//        Long employee = (Long) request.getSession().getAttribute("employee");
//        category.setCreateUser(employee);
//        category.setUpdateUser(employee);
        categoryService.save(category);
        log.info("{}", category);
        return R.success("添加成功");

    }

    @DeleteMapping
    public R<String> delete(Long ids) {
        categoryService.remove(ids);
        return R.success("删除成功");

    }

    @PutMapping
    public R<String> update(@RequestBody Category category) {
//        category.setUpdateTime(LocalDateTime.now());
//        Long employee = (Long) request.getSession().getAttribute("employee");
//        category.setUpdateUser(employee);
        categoryService.updateById(category);
        return R.success("success");
    }

    @GetMapping("/list")
    private R<List<Category>> list(@RequestParam("type") Long type) {
        List<Category> type1 = categoryService.list(new QueryWrapper<Category>().eq(type!=null,"type", type));
        return R.success(type1);
    }


}
