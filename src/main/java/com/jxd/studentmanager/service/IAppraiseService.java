package com.jxd.studentmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxd.studentmanager.model.Appraise;

public interface IAppraiseService extends IService<Appraise> {
    int addAppraise(Appraise appraise);
    String getAppraise( int sid,  int type);

    int updateApp(String content,int sid,int type);

}
