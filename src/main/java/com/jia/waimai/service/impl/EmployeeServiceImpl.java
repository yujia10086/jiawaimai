package com.jia.waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jia.waimai.entity.Employee;
import com.jia.waimai.service.EmployeeService;
import com.jia.waimai.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
* @author 王誉佳
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2022-12-22 17:08:11
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService{

}




