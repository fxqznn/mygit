package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.ICourseMapper;
import com.jxd.studentmanager.mapper.ITermCourseMapper;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.TermCourse;
import com.jxd.studentmanager.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CourseServiceImpl
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Service
public class CourseServiceImpl extends ServiceImpl<ICourseMapper, Course> implements ICourseService {
    @Resource
    private ICourseMapper courseMapper;
    @Autowired
    private ITermCourseMapper termCourseMapper;
    @Override
    public List<Course> selectCoursesByTid(int page,int size, int tid,int isdel,String cname) {
        int pageSize = size;
        int pageStart = pageSize * (page - 1);
        return courseMapper.selectCoursesByTid(pageStart,pageSize,tid,isdel,cname);
    }

    @Override
    public List<Course> selectCoursesByDid(int did) {
        return courseMapper.selectCoursesByDid(did);
    }

    @Override
    public List<Map<String,Object>> getCourseTerm(int tid) {
        List<Map<String,Object>> CnameWithCid = new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("tid",tid);
        List<TermCourse> termCourses = termCourseMapper.selectList(queryWrapper);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("type",0);
        List<Course> courses = courseMapper.selectList(queryWrapper1);

        for(TermCourse termCourse:termCourses){
            Map<String,Object> map = new HashMap<>();
            for(Course course : courses){
                if (termCourse.getCid() == course.getCid()){
                    map.put("cid","z"+termCourse.getCid());
                    map.put("cname",course.getCname());

                    break;
                } else {
                    continue;
                }
            }
            CnameWithCid.add(map);
        }

        return CnameWithCid;
    }

    @Override
    public List<Map<String, Object>> getCourseScoreTerm(int tid) {
        List<Map<String,Object>> CnameWithCid = new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("tid",tid);
        List<TermCourse> termCourses = termCourseMapper.selectList(queryWrapper);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("type",0);
        List<Course> courses = courseMapper.selectList(queryWrapper1);

        for(TermCourse termCourse:termCourses){
            Map<String,Object> map = new HashMap<>();
            for(Course course : courses){
                if (termCourse.getCid() == course.getCid()){
                    map.put("cid",Integer.toString(termCourse.getCid()));
                    map.put("cname",course.getCname());

                    break;
                } else {
                    continue;
                }
            }
            CnameWithCid.add(map);
        }

        return CnameWithCid;
    }

    @Override
    public List<Map> selectCourseByType() {
        return courseMapper.selectCourseByType();
    }
}
