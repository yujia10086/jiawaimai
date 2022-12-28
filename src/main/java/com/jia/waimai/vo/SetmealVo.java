package com.jia.waimai.vo;

import com.jia.waimai.entity.Setmeal;
import com.jia.waimai.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealVo extends Setmeal {
    private List<SetmealDish> setmealDishes;
    private String categoryName;
}
