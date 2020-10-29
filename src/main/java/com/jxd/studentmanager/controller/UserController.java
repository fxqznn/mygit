package com.jxd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jxd.studentmanager.model.User;
import com.jxd.studentmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Controller
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/login/{uname}/{pwd}")
    @ResponseBody
    public User login(@PathVariable("uname") String uname, @PathVariable("pwd") String pwd) {
        Map<String, Object> map = new HashMap<>();
        map.put("uname", uname);
        map.put("pwd", pwd);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(map, true);
        User user = userService.getOne(queryWrapper, true);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }
}
