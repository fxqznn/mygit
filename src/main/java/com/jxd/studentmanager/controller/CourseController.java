package com.jxd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.Term;
import com.jxd.studentmanager.model.TermCourse;
import com.jxd.studentmanager.model.User;
import com.jxd.studentmanager.service.ICourseService;
import com.jxd.studentmanager.service.ITermCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CourseController
 * @Description TODO
 * @Author Zheng NaNa
 * @Date 2020/10/28
 * @Version 1.0
 */
@Controller
public class CourseController {
    @Autowired
    ICourseService courseService;

    @Autowired
    ITermCourseService termCourseService;

    /**
     * 查询课程信息，返回列表中包含tid
     * @param tid
     * @return
     */
    @RequestMapping("/getCourses")
    @ResponseBody
    public List<Map<String,Object>> getCoursesBytid(int tid){
        return courseService.selectCoursesByTid(tid);
    }


    /**
     * 老师往课程表中批量插入课程
     * @param courses
     * @return
     */
    @RequestMapping("/addCourseBatch")
    @ResponseBody
    public String addCourseBatch(List<Course> courses){
        boolean flag_tc = courseService.saveBatch(courses);
        if (flag_tc){
            return "success";
        } else {
            return "error";
        }
    }

    /**
     * 根据cid和tid添加学期课程，如果存在cid和tid与将要插入的同时相同时
     * 则不添加
     * @param cid
     * @param tid
     * @return
     */
    @RequestMapping(value = "addTermCourse" ,produces = "html/text;charset=utf-8")
    @ResponseBody
    public String addTermCourse(int cid,int tid){
        List<TermCourse> termCourses = termCourseService.list();
        boolean flag = false;
        for (TermCourse termCourse: termCourses){
            if (termCourse.getTid() == tid && termCourse.getCid() == cid){
                flag = false;
                break;
            } else {
                flag = true;
            }
        }
        if (flag){
            TermCourse termCourse = new TermCourse();
            termCourse.setTid(tid);
            termCourse.setCid(cid);
            if (termCourseService.save(termCourse)){
                return "课程添加成功";
            } else {
                return "课程已存在";
            }
        }else {
            return "课程添加失败";
        }
    }

    @RequestMapping(value = "updateCourseBatch",produces = "html/text;charset=utf-8")
    @ResponseBody
    public String updateCourseBatch(List<Course> courses){
        boolean flag = false;
        for(Course course:courses){
            UpdateWrapper<Course> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("cid",course.getCid());
            flag = courseService.update(course,updateWrapper);
            if (!flag){
                return "修改失败";
            }
        }
        if (!flag){
            return "修改失败";
        } else {
            return "修改成功";
        }
    }
}
