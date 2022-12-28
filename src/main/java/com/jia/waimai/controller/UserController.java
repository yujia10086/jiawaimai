package com.jia.waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jia.waimai.common.R;
import com.jia.waimai.entity.User;
import com.jia.waimai.service.UserService;
import com.jia.waimai.utils.SMSUtils;
import com.jia.waimai.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code======{}",code);
//            SMSUtils.sendMessage();
            session.setAttribute(phone, code);
            return R.success("");
        }
        return R.error("");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info("map{}", map.toString());
        String phone = (String) map.get("phone");
        String code = (String) map.get("code");
        Object attribute = session.getAttribute(phone);
        if (attribute != null && attribute.equals(code)) {
            LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(User::getPhone,phone);
            User one = userService.getOne(wrapper);
            if (one==null){
                one=new User();
                one.setPhone(phone);
                userService.save(one);
            }
            session.setAttribute("user",one.getId());
            return R.success(one);

        }

        return R.error("");
    }

}
