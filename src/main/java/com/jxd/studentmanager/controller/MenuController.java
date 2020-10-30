package com.jxd.studentmanager.controller;

import com.jxd.studentmanager.service.IMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
public class MenuController {
    @Resource
    private IMenuService menuService;

    /**
     * 获取菜单
     *
     * @param role 登录角色
     * @return list
     */
    @RequestMapping("/getMenu/{role}")
    @ResponseBody
    public List<Map<String, Object>> getAllDeptByLike(@PathVariable("role") int role) {
        return menuService.queryMenuInfo(role);
    }


}
