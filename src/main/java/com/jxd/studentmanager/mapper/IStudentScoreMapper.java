package com.jxd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.StudentScore;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName IStudentCourseMapper
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
public interface IStudentScoreMapper extends BaseMapper<StudentScore> {
    List<Map<String, Object>> selectCourses(int sid);

    List<Map<String, Object>> selectAbilities(@Param("sid") int sid, @Param("type") int type);

    boolean updateEmpScore(@Param("cid")int cid,@Param("grade") double grade,
                           @Param("sid") int sid,@Param("type") int type);

    List<Course> getAllEntity(@Param("eid") int eid);

    int insertSs(StudentScore ss);
}
