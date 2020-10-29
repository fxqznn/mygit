package com.jxd.studentmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.model.Menu;

import java.util.List;
import java.util.Map;

public interface IMenuService extends IService<Menu> {
    public List<Map<String, Object>> queryMenuInfo(int role);
}
