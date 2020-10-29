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
    List<Map<String,Object>> selectCoursesBytid(@Param("tid") int tid);

}
