package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.IAppraiseMapper;
import com.jxd.studentmanager.mapper.IStudentScoreMapper;
import com.jxd.studentmanager.model.Appraise;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.StudentScore;
import com.jxd.studentmanager.service.IAppraiseService;
import com.jxd.studentmanager.service.IStudentScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    @Autowired
    private IAppraiseMapper appraiseMapper;


    @Override
    public List<Map<String, Object>> selectCourses(int sid) {
        return studentScoreMapper.selectCourses(sid);
    }

    @Override
    public List<Map<String, Object>> selectAbilities(int sid, int type) {
        return studentScoreMapper.selectAbilities(sid, type);
    }

    @Override
    public boolean updateEmpScore(int cid, double grade, int sid,int type) {
        return studentScoreMapper.updateEmpScore(cid, grade, sid,type);
    }

    @Override
    public List<Course> getAllEntity(int eid) {
        return studentScoreMapper.getAllEntity(eid);
    }

    @Override
    public List<Course> getStudentCourses(int sid) {
        return studentScoreMapper.getStudentCourses(sid);
    }

    @Override
    public Map<String, Object> selectCoursesScore(int sid) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = selectCourses(sid);
        map.put("ename", list.get(0).get("ename"));
        map.put("tname", list.get(0).get("tname"));
        double avg = 0;
        for (Map map1 : list) {
            map.put(map1.get("cid").toString(), map1.get("score"));
            double sum = 0;
            sum += (double) map1.get("score");
            avg = sum / list.size();
        }
        map.put("avg", avg);
        return map;
    }

    @Override
    public Map<String, Object> selectAbilitiesScore(int sid, int type) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = selectAbilities(sid, type);
        map.put("ename", list.get(0).get("ename"));
        map.put("dname", list.get(0).get("dname"));
        double avg = 0;
        for (Map map1 : list) {
            map.put((String) map1.get("cid"), map1.get("score"));
            double sum = 0;
            sum += (double) map1.get("score");
            avg = sum / list.size();
        }
        map.put("avg", avg);
        return map;
    }


    @Override
    public int insertSs(StudentScore ss){
        return studentScoreMapper.insertSs(ss);
    }
}
