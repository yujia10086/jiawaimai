package com.jia.waimai.service;

import com.jia.waimai.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jia.waimai.entity.DishFlavor;
import com.jia.waimai.vo.DishVo;

import java.util.List;

/**
* @author 王誉佳
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-12-23 22:34:57
*/
public interface DishService extends IService<Dish> {

    Integer dishCount(Long ids);

    void saveWithFlavor(DishVo dishVo);

    void removeByIdsWithFlavor(List<Long> asList);

    List<DishFlavor> get(Long id);

    void updateWithFlavor(DishVo dishVo);
}
