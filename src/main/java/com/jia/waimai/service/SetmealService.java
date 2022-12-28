package com.jia.waimai.service;

import com.jia.waimai.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jia.waimai.vo.SetmealVo;

import java.util.List;

/**
* @author 王誉佳
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-12-24 20:14:12
*/
public interface SetmealService extends IService<Setmeal> {

    Integer setCount(Long ids);

    SetmealVo getByIdWithDish(Long id);

    void deleteWithDish(List<Long> ids);
}
