package com.jxd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxd.studentmanager.model.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ICourseMapper extends BaseMapper<Course> {
    /**
     * 根据学期id查询所有的课程
     * @param tid
     * @return
     */
    List<Map<String,Object>> selectCoursesByTid(@Param("pageStart") int pageStart,@Param("pageSize") int pageSize,
                                    @Param("tid") int tid,@Param("isdel") int isdel,@Param("cname") String cname);

    List<Map<String,Object>> selectCoursesCount(@Param("tid") int tid,@Param("isdel") int isdel,@Param("cname") String cname);

    List<Course> selectCoursesByDid(int did);

    List<Map> selectCourseByType();





}