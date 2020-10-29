package com.jxd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.Term;
import com.jxd.studentmanager.service.ICourseService;
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

    @RequestMapping(value = "getAllTerm")
    @ResponseBody
    public IPage<Term> getAllTerm(Page<Term> page, String tname){
        QueryWrapper<Term> wrapper = new QueryWrapper<>();
        IPage<Term> list = null;

        if(tname == null || tname == ""){
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

        List<Course> courses = courseService.list();
        map.put("courses",courses);

        List<Course> checkedCourses = courseService.selectCoursesByTid(tid);
        map.put("checkedCourses",checkedCourses);

        return map;
    }

}
