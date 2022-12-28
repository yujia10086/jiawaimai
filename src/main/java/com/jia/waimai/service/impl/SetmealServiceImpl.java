package com.jia.waimai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jia.waimai.common.CustomException;
import com.jia.waimai.entity.Setmeal;
import com.jia.waimai.entity.SetmealDish;
import com.jia.waimai.service.SetmealDishService;
import com.jia.waimai.service.SetmealService;
import com.jia.waimai.mapper.SetmealMapper;
import com.jia.waimai.vo.SetmealVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 王誉佳
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2022-12-24 20:14:12
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{
    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    public Integer setCount(Long ids) {
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Setmeal::getCategoryId,ids);
        int count = this.count(wrapper);
        return count;
    }

    @Override
    public SetmealVo getByIdWithDish(Long id) {
        Setmeal setmeal = this.getById(id);
        SetmealVo setmealVo = new SetmealVo();
        BeanUtils.copyProperties(setmeal,setmealVo);
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId,setmeal.getId());
        List<SetmealDish> list = setmealDishService.list(wrapper);
        setmealVo.setSetmealDishes(list);

        return setmealVo;
    }

    @Override
    @Transactional
    public void deleteWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Setmeal::getId,ids).eq(Setmeal::getStatus,1);
        int count = this.count(wrapper);
        if (count>0){
            throw new CustomException("套餐正在售卖中,不能删除");
        }
        this.removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> wrapper1=new LambdaQueryWrapper<>();
        wrapper1.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(wrapper1);

    }
}




