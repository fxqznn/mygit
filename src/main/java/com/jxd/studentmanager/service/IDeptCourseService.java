package com.jxd.studentmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.DeptCourse;

import java.util.List;
import java.util.Map;


public interface IDeptCourseService extends IService<DeptCourse> {
    List<Map> getNotSelectedAbility(int eid);

    int addAbility(int eid,int cid);

    int delDeptCourse(int eid,int cid);
}
