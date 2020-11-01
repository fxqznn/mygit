package com.jxd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.Emp;
import com.jxd.studentmanager.model.StudentScore;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IEmpMapper extends BaseMapper<Emp> {
    List<Emp> selectEmp(@Param("eid") int eid);

    List<StudentScore> selectScores(@Param("type") int type, @Param("eid") int eid);
}
