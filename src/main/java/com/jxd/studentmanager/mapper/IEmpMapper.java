package com.jxd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxd.studentmanager.model.Emp;

public interface IEmpMapper extends BaseMapper<Emp> {
    int getLastInsertId();
}
