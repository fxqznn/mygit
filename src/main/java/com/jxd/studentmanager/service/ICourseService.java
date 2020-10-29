package com.jxd.studentmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxd.studentmanager.model.Course;

import java.util.List;
import java.util.Map;

public interface ICourseService extends IService<Course> {

    List<Map<String,Object>> selectCoursesByTid(int tid);
}
