package com.jxd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxd.studentmanager.model.Appraise;
import org.apache.ibatis.annotations.Param;

public interface IAppraiseMapper extends BaseMapper<Appraise> {
    int addAppraise(@Param("eid")int eid,@Param("type")int type);

    String getAppraise(@Param("eid") int eid, @Param("type") int type);

    int updateApp(@Param("content") String content ,@Param("sid") int sid, @Param("type") int type);

    int updateApp02(@Param("sumscore") double sumscore ,@Param("sid") int sid, @Param("type") int type);

    double getSumscore(@Param("eid") int eid, @Param("type") int type);
}
