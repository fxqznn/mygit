package com.jxd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxd.studentmanager.model.Appraise;
import org.apache.ibatis.annotations.Param;

public interface IAppraiseMapper extends BaseMapper<Appraise> {
    int addAppraise(Appraise appraise);
    String getAppraise(@Param("eid")int eid,@Param("type") int type);
}
