package com.jia.waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jia.waimai.entity.User;
import com.jia.waimai.service.UserService;
import com.jia.waimai.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 王誉佳
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-12-26 22:50:55
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




