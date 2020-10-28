package com.jxd.studentmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxd.studentmanager.model.StudentScore;

import java.util.List;
import java.util.Map;

public interface IStudentScoreService extends IService<StudentScore> {
    List<Map<String, Object>> selectCourses(int sid);

    List<Map<String, Object>> selectAbilities(int sid, int type);
}
