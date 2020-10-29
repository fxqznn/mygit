package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.IMenuMapper;
import com.jxd.studentmanager.model.Menu;
import com.jxd.studentmanager.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @description Todo
 * @date 2020-10-19 10:22
 */
@Service
public class MenuServiceImpl extends ServiceImpl<IMenuMapper, Menu> implements IMenuService {
    @Autowired
    private IMenuMapper menuMapper;

    /**
     * 查询菜单
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> queryMenuInfo(int role) {
        List<Map<String, Object>> allMenu = menuMapper.queryAllMenu(role);
        //根节点
        List<Map<String, Object>> rootMenu = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> nav : allMenu) {
            String parentId = String.valueOf(nav.get("parentId"));
            if ("0".equals(parentId)) {//父节点是0的，为根节点。
                rootMenu.add(nav);
            }
        }
        /* 根据Menu类的order排序 */
//	      Collections.sort(rootMenu, order());
        //为根菜单设置子菜单，getClild是递归调用的
        for (Map<String, Object> nav : rootMenu) {
            /* 获取根节点下的所有子节点 使用getChild方法*/
            String id = String.valueOf(nav.get("id"));
            List<Map<String, Object>> childList = getChild(id, allMenu);
//	        nav.setChildren(childList);//给根节点设置子节点
            nav.put("children", childList);
        }
        return rootMenu;
    }

    /**
     * 获取子节点
     *
     * @param id      父节点id
     * @param allMenu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    private List<Map<String, Object>> getChild(String id, List<Map<String, Object>> allMenu) {
        //子菜单
        List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            String parentId = String.valueOf(nav.get("parentId"));
            if (id.equals(parentId)) {
                childList.add(nav);
            }
        }
        //递归
        for (Map<String, Object> nav : childList) {
            String tempId = String.valueOf(nav.get("id"));
            //nav.setChildren(,getChild(tempId, allMenu));
            nav.put("child", getChild(tempId, allMenu));
        }
        //Collections.sort(childList,order());//排序
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<Map<String, Object>>();
        }
        return childList;
    }
}
