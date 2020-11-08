package com.jxd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jxd.studentmanager.model.TermCourse;
import com.jxd.studentmanager.service.ITermCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName TermCourseController
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Controller
public class TermCourseController {
    @Autowired
    ITermCourseService termCourseService;

    @RequestMapping(value = "addTermCourse")
    @ResponseBody
    public String addTermCourse(TermCourse termCourse){
        boolean flag = termCourseService.save(termCourse);
        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "delTermCourse")
    @ResponseBody
    public String delTermCourseById(int tid, int cid){
        UpdateWrapper<TermCourse> wrapper = new UpdateWrapper<>();
        wrapper.eq("cid",cid).eq("tid",tid);
        boolean flag = termCourseService.remove(wrapper);
        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }
}
