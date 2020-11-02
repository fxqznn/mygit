package com.jxd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxd.studentmanager.model.Menu;

import java.util.List;
import java.util.Map;

public interface IMenuMapper extends BaseMapper<Menu> {
     List<Map<String, Object>> queryAllMenu(int role);
}
