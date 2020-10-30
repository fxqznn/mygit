package com.jxd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxd.studentmanager.model.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IEmpMapper extends BaseMapper<Emp> {
    List<Map<String,Object>> selectEmp(@Param("eid") int eid);

    List<Map<String,Object>> selectScores(@Param("type") int type,@Param("eid") int eid);
}
