package com.jxd.studentmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.StudentScore;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IStudentScoreService extends IService<StudentScore> {
    List<Map<String, Object>> selectCourses(int sid);

    List<Map<String, Object>> selectAbilities(int sid, int type);

    boolean updateEmpScore(String cname, double grade, int eid, int type);

    List<Course> getAllEntity(int eid);

    List<Course> getStudentCourses(int sid);

    Map<String,Object> selectCoursesScore(int sid);

    Map<String,Object> selectAbilitiesScore(@Param("sid") int sid, @Param("type") int type);

    List<StudentScore> getOneEmpAbilityScore(int eid,int type);
}
