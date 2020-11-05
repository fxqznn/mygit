package com.jxd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxd.studentmanager.model.*;
import com.jxd.studentmanager.service.ICourseService;
import com.jxd.studentmanager.service.IDeptCourseService;
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

    @Autowired
    private IDeptCourseService deptCourseService;

    /**
     * 查询课程信息，返回列表中包含tid
     * @param tid
     * @return
     */
    @RequestMapping(value = "getCourses",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Map<String,Object>> getCoursesByTid (int tid){
        return courseService.getCourseTerm(tid);
    }

    @RequestMapping(value = "getCoursesTerm",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Map<String,Object>> getCoursesTerm (int tid){
        return courseService.getCourseScoreTerm(tid);
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
        flag = courseService.updateBatchById(courses);
        if (!flag){
            return "修改失败";
        } else {
            return "修改成功";
        }
    }

    @RequestMapping(value = "updateCourse",produces = "html/text;charset=utf-8")
    @ResponseBody
    public String updateCourse(Course course){
        boolean flag = false;
        flag = courseService.updateById(course);
        if (!flag){
            return "修改失败";
        } else {
            return "修改成功";
        }
    }

    /**
     * 分页查询所有的课程，根据课程名称进行模糊查询，根据课程类别进行分类查询
     * 2代表查询全部，其余代表课程类型
     * @param page
     * @param type
     * @param cname
     * @return
     */
    @RequestMapping(value = "getAllCourse")
    @ResponseBody
    public IPage<Course> getAllCourse(Page<Course> page, int type, String cname,int isdel){
        QueryWrapper<Course> wrapper = new QueryWrapper<>();

        //查询对应类别的的课程
        if(cname == null || "".equals(cname)){
            wrapper.eq("type",type).eq("isdel",isdel);
        } else {
            wrapper.like("cname",cname).eq("type",type).eq("isdel",isdel);
        }

        return courseService.page(page,wrapper);
    }

    @RequestMapping(value = "getCourseById")
    @ResponseBody
    public Course getCourse(int cid){
        return courseService.getById(cid);
    }

    @RequestMapping(value = "addCourse")
    @ResponseBody
    public String addCourse(Course course){
        boolean flag = courseService.save(course);
        if(flag){
            return "success";
        } else {
            return "true";
        }
    }

    @RequestMapping(value = "editCourse")
    @ResponseBody
    public String editCourse(Course course){
        boolean flag = courseService.updateById(course);
        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "delCourseById")
    @ResponseBody
    public String delCourseById(int cid){
        boolean flag = false;

        QueryWrapper<TermCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("cid",cid);
        int num = termCourseService.count(wrapper);
        QueryWrapper<DeptCourse> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("cid",cid);
        int num1 = deptCourseService.count(wrapper1);

        if(num > 0 || num1 > 0){
            Course course = courseService.getById(cid);
            course.setIsdel(1);
            flag = courseService.updateById(course);
        }else {
            flag = courseService.removeById(cid);
        }

        if(flag){
            return "success";
        } else {
            return "true";
        }
    }

    @RequestMapping(value = "delCoursesByIds")
    @ResponseBody
    public String delCoursesByIds(int[] cids){
        for(int cid : cids){
            delCourseById(cid);
        }
        return "success";
    }
}
