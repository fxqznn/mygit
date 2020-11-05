package com.jxd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IStudentMapper extends BaseMapper<Student> {
    List<Student> getStudentPage(@Param("pageStart") int pageStart,
                                 @Param("pageSize") int pageSize, @Param("sname") String sname, @Param("tid") int tid);
    List<Map<String,Object>> getScoreWithCourse(@Param("pageStart") int pageStart,
                                                @Param("pageSize") int pageSize, @Param("courseList") List<Course> courses,
                                                @Param("sname") String sname,@Param("tid") int tid);
    List<Map<String,Object>> getAllScoreWithCourse(@Param("courseList") List<Course> courses,
                                                   @Param("sname") String sname,@Param("tid") int tid);

    Student getOneByEid(@Param("eid") int eid);

}
