package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.IRoleMenuMapper;
import com.jxd.studentmanager.model.RoleMenu;
import com.jxd.studentmanager.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName RoleMenuServiceImpl
 * @Description TODO
 * @Author jinkaiyan
 * @Date 2020/10/29
 * @Version 1.0
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<IRoleMenuMapper, RoleMenu> implements IRoleMenuService {
    @Autowired
    private IRoleMenuMapper roleMenuMapper;
}
