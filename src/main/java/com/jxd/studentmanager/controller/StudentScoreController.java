package com.jxd.studentmanager.controller;

import com.jxd.studentmanager.service.IStudentScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName StudentScoreController
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Controller
public class StudentScoreController {

    @Autowired
    private IStudentScoreService studentScoreService;

    @RequestMapping("/getScoreCourses")
    @ResponseBody
    public List<Map<String, Object>> getScoreCourses(int sid) {
        return studentScoreService.selectCourses(sid);
    }

    @RequestMapping("/getScoreAbilities")
    @ResponseBody
    public List<Map<String, Object>> getScoreAbilities(int sid, int type) {
        return studentScoreService.selectAbilities(sid,type);
    }
}
