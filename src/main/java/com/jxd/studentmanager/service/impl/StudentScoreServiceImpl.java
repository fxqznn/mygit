package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.IAppraiseMapper;
import com.jxd.studentmanager.mapper.IEmpMapper;
import com.jxd.studentmanager.mapper.IStudentScoreMapper;
import com.jxd.studentmanager.model.Appraise;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.Emp;
import com.jxd.studentmanager.model.StudentScore;
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
    private IEmpMapper empMapper;

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
    public boolean updateEmpScore(String cname, double score, int eid, int type,String ename,int did) {
        return studentScoreMapper.updateEmpScore(ename,did,cname,score,eid,type);
    }

    @Override
    public boolean updateStudentScore(String cname, double grade, int sid, int type, int eid) {
        return studentScoreMapper.updateStudentScore(cname, grade, sid, type, eid);
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
        QueryWrapper queryWrapper1 = new QueryWrapper();
        Map<String, Object> querymap1 = new HashMap<>();
        querymap1.put("sid", sid);
        querymap1.put("type", -1);
        queryWrapper1.allEq(querymap1,true);
        Appraise appraise = appraiseMapper.selectOne(queryWrapper1);
        for (Map map1 : list) {
            if ((Double) map1.get("score") != -1) {
                map.put(map1.get("cid").toString(), map1.get("score"));
            } else {
                map.put(map1.get("cid").toString(), "未评分");
                map.put("avg", "未评分");
            }
        }
        List<Course> courseList = studentScoreMapper.getStudentCourses(sid);
        if (courseList.size() != list.size()) {
            map.put("avg", "未评分");
        } else {
            map.put("avg", String.format("%.2f", appraise.getSumscore()));
        }
        return map;
    }

    @Override
    public Map<String, Object> selectAbilitiesScore(int sid, int type) {
        Map<String, Object> map = new HashMap<>();
        if (selectAbilities(sid, type).size() == 0) {
            return map;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        Map<String, Object> querymap = new HashMap<>();
        querymap.put("sid", sid);
        queryWrapper.allEq(querymap, true);
        Emp emp = empMapper.selectOne(queryWrapper);
        List<Map<String, Object>> list = selectAbilities(sid, type);
        map.put("ename", list.get(0).get("ename"));
        map.put("dname", list.get(0).get("dname"));
        map.put("job", emp.getJob());
        QueryWrapper queryWrapper1 = new QueryWrapper();
        Map<String, Object> querymap1 = new HashMap<>();
        querymap1.put("sid", sid);
        querymap1.put("type", type);
        queryWrapper1.allEq(querymap1, true);
        Appraise appraise = appraiseMapper.selectOne(queryWrapper1);
        for (Map map1 : list) {
            if ((Double) map1.get("score") != -1) {
                map.put(map1.get("cid").toString(), map1.get("score"));
            } else {
                map.put(map1.get("cid").toString(), "未评分");
                map.put("avg", "未评分");
            }
        }
        if (map.get("avg") != null) {
            return map;
        } else {
            map.put("avg", String.format("%.2f", appraise.getSumscore()));
        }
        return map;
    }

    @Override
    public List<StudentScore> getOneEmpAbilityScore(int eid, int type) {
        return studentScoreMapper.getOneEmpAbilityScore(eid, type);
    }

    @Override
    public int delSs(int cid) {
        return studentScoreMapper.delSs(cid);
    }


}
