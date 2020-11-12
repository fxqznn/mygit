package com.jxd.studentmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxd.studentmanager.model.Appraise;

public interface IAppraiseService extends IService<Appraise> {
    int addAppraise(int eid,int type);
    String getAppraise( int eid,  int type);

    int updateApp(String content,int sid,int type);

    int updateApp02(double sumscore,int sid,int type);

    double getSumscore( int eid,  int type);
}
