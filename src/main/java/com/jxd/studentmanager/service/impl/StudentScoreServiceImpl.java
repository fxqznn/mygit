package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.IStudentScoreMapper;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.StudentScore;
import com.jxd.studentmanager.service.IStudentScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName StudentScoreServiceImpl
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Service
public class StudentScoreServiceImpl extends ServiceImpl<IStudentScoreMapper, StudentScore> implements IStudentScoreService {
    @Autowired
    private IStudentScoreMapper studentScoreMapper;


    @Override
    public List<Map<String, Object>> selectCourses(int sid) {
        return studentScoreMapper.selectCourses(sid);
    }

    @Override
    public List<Map<String, Object>> selectAbilities(int sid, int type) {
        return studentScoreMapper.selectAbilities(sid, type);
    }

    @Override
    public boolean updateEmpScore(int cid, double grade, int sid) {
        return studentScoreMapper.updateEmpScore(cid, grade, sid);
    }

    @Override
    public List<Course> getAllEntity(int eid, int type) {
        return studentScoreMapper.getAllEntity(eid,type);
    }


}
