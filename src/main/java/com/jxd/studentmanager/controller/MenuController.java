package com.jxd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jxd.studentmanager.model.Menu;
import com.jxd.studentmanager.model.RoleMenu;
import com.jxd.studentmanager.service.IMenuService;
import com.jxd.studentmanager.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    IRoleMenuService roleMenuService;

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

    @RequestMapping(value = "addMenu")
    @ResponseBody
    public String addMenu(Menu menu, int role){
        boolean flag = menuService.save(menu);
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRole(role);
        roleMenu.setMid(menu.getMid());
        roleMenuService.save(roleMenu);
        if(flag) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "getMenuById")
    @ResponseBody
    public Menu getMenuById(int mid){
        return menuService.getById(mid);
    }

    @RequestMapping(value = "editMenu")
    @ResponseBody
    public String editMenu(Menu menu){
        boolean flag = menuService.updateById(menu);
        if(flag) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "delMenuById")
    @ResponseBody
    public String delMenuById(int mid, int role){
        del(mid,role);
        return "success";
    }

    public void del(int mid, int role){
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("parentId",mid);
        List<Menu> list = menuService.list(wrapper);
        for(Menu menu : list){
            del(menu.getMid(),role);
        }
        QueryWrapper<RoleMenu> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("mid",mid).eq("role",role);
        roleMenuService.remove(wrapper1);
        menuService.removeById(mid);
    }

    @RequestMapping(value = "delMenuByIds")
    @ResponseBody
    public String delMenuByIds(int[] mids, int role){
        for(int mid : mids){
            del(mid,role);
        }
        return "success";
    }


}
