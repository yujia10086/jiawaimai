package com.jia.waimai.service;

import com.jia.waimai.entity.DishFlavor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 王誉佳
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service
* @createDate 2022-12-25 12:26:58
*/
public interface DishFlavorService extends IService<DishFlavor> {



    void deleteByIds(List<Long> asList);
}
