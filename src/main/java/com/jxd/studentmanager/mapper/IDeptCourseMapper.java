package com.jxd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.DeptCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IDeptCourseMapper extends BaseMapper<DeptCourse> {
    List<Map> getNotSelectedAbility(@Param("eid") int eid);
    int addAbility(@Param("eid")int eid,@Param("cid")int cid);
    int delDeptCourse(@Param("eid")int eid,@Param("cid")int cid);
}
