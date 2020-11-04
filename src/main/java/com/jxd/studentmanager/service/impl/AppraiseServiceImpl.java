package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.IAppraiseMapper;
import com.jxd.studentmanager.model.Appraise;
import com.jxd.studentmanager.service.IAppraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName AppraiseServiceImpl
 * @Description TODO
 * @Author Zheng NaNa
 * @Date 2020/10/30
 * @Version 1.0
 */
@Service
public class AppraiseServiceImpl extends ServiceImpl<IAppraiseMapper, Appraise> implements IAppraiseService {

    @Autowired
    private IAppraiseMapper appraiseMapper;


    @Override
    public int addAppraise(Appraise appraise) {
        return appraiseMapper.addAppraise(appraise);
    }
}
