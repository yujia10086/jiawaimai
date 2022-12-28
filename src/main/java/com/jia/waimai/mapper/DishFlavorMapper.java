package com.jia.waimai.mapper;

import com.jia.waimai.entity.DishFlavor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 王誉佳
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Mapper
* @createDate 2022-12-25 12:26:58
* @Entity com.jia.waimai.entity.DishFlavor
*/
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
     void removeByDishId(@Param("ids") List<Long> ids);

}




