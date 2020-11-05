package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.ICourseMapper;
import com.jxd.studentmanager.mapper.IStudentMapper;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.Student;
import com.jxd.studentmanager.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName StudentServiceImpl
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Service
public class StudentServiceImpl extends ServiceImpl<IStudentMapper, Student> implements IStudentService {

    @Autowired
    IStudentMapper studentMapper;
    @Autowired
    ICourseMapper courseMapper;

    @Override
    public List<Student> getStudentPage(int page, int pageSize, int tid, String sname) {
        int pageStart = pageSize * (page - 1);
        return studentMapper.getStudentPage(pageStart, pageSize, sname, tid);
    }

    @Override
    public List<Map<String, Object>> getScoreWithCourse(int page, int size, String sname, int tid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type", 0);
        List<Course> courses = courseMapper.selectList(queryWrapper);
        int pageStart = size * (page - 1);
        return studentMapper.getScoreWithCourse(pageStart, size, courses, sname, tid);
    }

    @Override
    public List<Map<String, Object>> getAllScoreWithCourse(String sname, int tid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type", 0);
        List<Course> courses = courseMapper.selectList(queryWrapper);
        return studentMapper.getAllScoreWithCourse(courses, sname, tid);
    }

    @Override
    public int getByUser(int uid) {
        return studentMapper.getByUser(uid);
    }
    @Override
    public Student getStudentByEid(int eid) {
        return studentMapper.getOneByEid(eid);
    }

}
