package com.jxd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jxd.studentmanager.model.DeptCourse;
import com.jxd.studentmanager.service.IDeptCourseService;
import com.jxd.studentmanager.service.IStudentScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName DeptCourseController
 * @Description TODO
 * @Author WangKai
 * @Date 2020/10/30
 * @Version 1.0
 */
@Controller
public class DeptCourseController {

    @Autowired
    private IDeptCourseService idcs;
    @Autowired
    private IStudentScoreService isss;


    @RequestMapping("/getNotSelectedAbility")
    @ResponseBody
    public List<Map> getAbility(int eid){
        return idcs.getNotSelectedAbility(eid);
    }

    @RequestMapping("/addAbility")
    @ResponseBody
    public int addAbility(int eid,int cid){
        return idcs.addAbility(eid, cid);
    }

    @RequestMapping("/delAbility")
    @ResponseBody
    public int delAbility(int eid,int cid){
        isss.delSs(cid);
        return idcs.delDeptCourse(eid, cid);
    }

    @RequestMapping(value = "addDeptCourse")
    @ResponseBody
    public String addDeptCourse(DeptCourse deptCourse){
        boolean flag = idcs.save(deptCourse);

        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "delDeptCourse")
    @ResponseBody
    public String delDeptCourse(int did, int cid){
        UpdateWrapper<DeptCourse> wrapper = new UpdateWrapper<>();
        wrapper.eq("did",did).eq("cid",cid);
        boolean flag = idcs.remove(wrapper);
        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

}
