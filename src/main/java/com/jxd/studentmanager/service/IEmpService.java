package com.jxd.studentmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxd.studentmanager.model.Emp;

import java.util.List;
import java.util.Map;

public interface IEmpService extends IService<Emp> {
    List<Map<String,Object>> selectEmp(int eid);

    List<Map<String,Object>> selectScores( int type, int eid);
}
