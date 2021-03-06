package com.jxd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.Student;
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

    boolean updateEmpScore(@Param("ename")String ename,@Param("did") int did,
                           @Param("cname")String cname,@Param("score") double score,
                           @Param("eid") int eid,@Param("type") int type);

    boolean updateStudentScore(@Param("cname")String cname,@Param("score") double score,
                           @Param("sid") int sid,@Param("type") int type,@Param("eid") int eid);

    List<Course> getAllEntity(@Param("eid") int eid);

    List<Course> getStudentCourses(@Param("sid") int sid);

    int delSs(@Param("cid")int cid);

    List<StudentScore> getOneEmpAbilityScore(@Param("eid") int eid,@Param("type")int type);
}
