package com.jxd.studentmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxd.studentmanager.model.Course;

import java.util.List;
import java.util.Map;

public interface ICourseService extends IService<Course> {

    List<Course> selectCoursesByTid(int page,int size, int tid,int isdel,String cname);

    List<Course> selectCoursesByDid(int did);

    List<Map<String,Object>> getCourseTerm(int tid);
}
