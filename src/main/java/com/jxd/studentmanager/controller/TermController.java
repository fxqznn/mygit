package com.jxd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.Student;
import com.jxd.studentmanager.model.Term;
import com.jxd.studentmanager.model.TermCourse;
import com.jxd.studentmanager.service.ICourseService;
import com.jxd.studentmanager.service.IStudentService;
import com.jxd.studentmanager.service.ITermCourseService;
import com.jxd.studentmanager.service.ITermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TermController
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Controller
public class TermController {

    @Autowired
    private ITermService termService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private ITermCourseService termCourseService;
    @Autowired
    private IStudentService studentService;

    @RequestMapping(value = "getAllTerm")
    @ResponseBody
    public IPage<Term> getAllTerm(Page<Term> page, String tname){
        QueryWrapper<Term> wrapper = new QueryWrapper<>();
        IPage<Term> list = null;

        if(tname == null || "".equals(tname)){
            list = termService.page(page);
        } else {
            wrapper.like("tname",tname);
            list = termService.page(page,wrapper);
        }
        return list;
    }

    /**
     * 获得学期为修改学期信息做准备
     * 查出学期已经选择的课程
     * 查出所有的课程进行选择
     * @param tid
     * @return
     */
    @RequestMapping(value = "getTermById")
    @ResponseBody
    public Map<String,Object> getTermById(int tid){
        Map<String,Object> map = new HashMap<>();

        Term term = termService.getById(tid);
        map.put("term",term);

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("isdel",0).ne("type",1);
        List<Course> courses = courseService.list(wrapper);
        map.put("courses",courses);

        List<Course> checkedCourses = courseService.selectCoursesByTid(0,0,tid,2,null);
        map.put("checkedCourses",checkedCourses);

        return map;
    }

    /**
     * 修改term表中的信息，包含修改term对应的课程信息
     * 新添加的课程需要
     * @param term
     * @param checkCourses
     * @return
     */
    @RequestMapping(value = "editTerm")
    @ResponseBody
    public String editTerm(Term term, List<Course> checkCourses){
        boolean flag = termService.updateById(term);

        List<Course> oldCheckedCourses = courseService.selectCoursesByTid(0,0,term.getTid(),2,null);

        //如果取消选择课程，需要删除学期课程中的信息,
        //旧的选择课程中有，新的没有
        for(Course oldCourse : oldCheckedCourses){
            boolean isexist = false;
            for(Course newCourse : checkCourses){
                if(newCourse.getCid() == oldCourse.getCid()){
                    isexist = true;
                    break;
                }
            }
            if(isexist == false){
                UpdateWrapper<TermCourse> wrapper = new UpdateWrapper<>();
                wrapper.eq("tid",term.getTid()).eq("cid",oldCourse.getCid());
                termCourseService.remove(wrapper);
            }
        }

        //新添加课程，需要在学期课程中添加信息
        //新的选择课程中有，旧的没有
        for(Course newCourse : checkCourses){
            boolean isexist = false;
            for(Course oldCourse : oldCheckedCourses){
                if(newCourse.getCid() == oldCourse.getCid()){
                    isexist = true;
                    break;
                }
            }
            if(isexist == false) {
                TermCourse termCourse = new TermCourse();
                termCourse.setTid(term.getTid());
                termCourse.setCid(newCourse.getCid());
                termCourseService.save(termCourse);
            }
        }

        if(flag) {
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 添加一个学期（班级），并且选择课程，也可以不选择课程
     * @param term
     * @param courses
     * @return
     */
    @RequestMapping(value = "addTerm")
    @ResponseBody
    public String addTerm(Term term, List<Course> courses){
        boolean flag = termService.save(term);

        for(Course course : courses) {
            TermCourse termCourse = new TermCourse();
            termCourse.setTid(term.getTid());
            termCourse.setCid(course.getCid());
            termCourseService.save(termCourse);
        }

        if(flag) {
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 班级里存在学生的话，不允许删除，如果没有学生允许删除，并且删除学期课程中的信息
     * @param tid
     * @return
     */
    @RequestMapping(value = "delTermById")
    @ResponseBody
    public String delTermById(int tid){
        boolean flag = false;

        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("tid",tid);
        List<Student> list = studentService.list(wrapper);
        if(list.size() == 0){
            //删除学期课程中的信息
            UpdateWrapper<TermCourse> wrapper1 = new UpdateWrapper<>();
            wrapper1.eq("tid",tid);
            flag = termCourseService.remove(wrapper1);
            if(flag){
                return "success";
            } else {
                return "fail";
            }
        } else {
            return "notdel";
        }
    }

    @RequestMapping(value = "delTermsByIds")
    @ResponseBody
    public String delTermsByIds(int[] tids){
        for(int tid : tids){
            delTermById(tid);
        }
        return "success";
    }

    @RequestMapping(value = "getTermByEid" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Term> getTermByEid(int eid){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("eid",eid);
        List<Term> terms = termService.list(queryWrapper);
        return terms;
    }
}
