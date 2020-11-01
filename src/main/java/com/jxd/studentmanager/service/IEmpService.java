package com.jxd.studentmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.Emp;
import com.jxd.studentmanager.model.StudentScore;

import java.util.List;
import java.util.Map;

public interface IEmpService extends IService<Emp> {
    List<Emp> selectEmp(int eid);

    List<StudentScore> selectScores(int type, int eid);
}
