package com.jia.waimai.service;

import com.jia.waimai.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 王誉佳
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2022-12-23 20:48:47
*/
public interface CategoryService extends IService<Category> {

    void remove(Long ids);
}
