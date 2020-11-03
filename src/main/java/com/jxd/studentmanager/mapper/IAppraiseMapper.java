package com.jxd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxd.studentmanager.model.Appraise;

public interface IAppraiseMapper extends BaseMapper<Appraise> {
    int addAppraise(Appraise appraise);
}
