package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.IStudentScoreMapper;
import com.jxd.studentmanager.model.StudentScore;
import com.jxd.studentmanager.service.IStudentScoreService;
import org.springframework.stereotype.Service;

/**
 * @ClassName StudentScoreServiceImpl
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Service
public class StudentScoreServiceImpl extends ServiceImpl<IStudentScoreMapper, StudentScore> implements IStudentScoreService {
}
