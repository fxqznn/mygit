package com.jxd.studentmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxd.studentmanager.model.Course;

public interface ICourseService extends IService<Course> {

    List<Map<String,Object>> selectCoursesByTid(int tid);
}
