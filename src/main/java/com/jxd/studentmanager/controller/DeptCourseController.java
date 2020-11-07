package com.jxd.studentmanager.controller;

import com.jxd.studentmanager.service.IDeptCourseService;
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
        return idcs.delDeptCourse(eid, cid);
    }

}
