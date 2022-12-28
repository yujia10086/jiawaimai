package com.jia.waimai.vo;

import com.jia.waimai.entity.Dish;
import com.jia.waimai.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishVo extends Dish {

    private List<DishFlavor> flavors=new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
