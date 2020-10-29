package com.jxd.studentmanager.controller;

import com.jxd.studentmanager.model.Student;
import com.jxd.studentmanager.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName StudentController
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Controller
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @RequestMapping("/getSelf")
    @ResponseBody
    public Student getSelf(int sid) {
        return studentService.getById(sid);
    }

    @RequestMapping("/updateSelf")
    @ResponseBody
    public String updateSelf(Student student) {
        boolean flag = studentService.updateById(student);
        if (flag) {
            return "修改信息成功";
        } else {
            return "修改信息失败";
        }
    }

}
