package com.jxd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxd.studentmanager.model.Emp;
import com.jxd.studentmanager.model.Student;
import com.jxd.studentmanager.model.StudentScore;
import com.jxd.studentmanager.model.User;
import com.jxd.studentmanager.service.IEmpService;
import com.jxd.studentmanager.service.IStudentScoreService;
import com.jxd.studentmanager.service.IStudentService;
import com.jxd.studentmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    @Autowired
    private IEmpService empService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IStudentScoreService studentScoreService;

    @RequestMapping("/getStudent")
    @ResponseBody
    public Student getStudent(int eid) {return studentService.getStudentByEid(eid);}


    @RequestMapping("/getSelf")
    @ResponseBody
    public Student getSelf(int sid) {
        return studentService.getById(sid);
    }

    @RequestMapping("/getByUser/{uid}")
    @ResponseBody
    public int getByUser(@PathVariable("uid") int uid) {
        return studentService.getByUser(uid);
    }

    @RequestMapping("/updateSelf")
    @ResponseBody
    public String updateSelf(Student student) {
        boolean flag = studentService.updateById(student);
        if("".equals(student.getBirthday())){
            student.setBirthday(null);
        }
        if (flag) {
            return "修改信息成功";
        } else {
            return "修改信息失败";
        }
    }


    /**
     * 分页查询
     *
     * @param page  分页信息
     * @param sname 姓名模糊查询
     * @param tid   学期（班级）主键
     * @return
     */
    @RequestMapping(value = "getAllStudent")
    @ResponseBody
    public IPage<Student> getAllStudent(Page<Student> page, String sname, int tid) {
        IPage<Student> list = null;
        QueryWrapper<Student> wrapper = new QueryWrapper<>();

        if (tid == -1) {
            if (sname == null || "".equals(sname)) {
                list = studentService.page(page);
            } else {
                wrapper.like("sname", sname);
                list = studentService.page(page, wrapper);
            }
        } else if (tid == 0) {
            if (sname == null || "".equals(sname)) {
                wrapper.eq("tid", 0).or().isNull("tid");
                list = studentService.page(page, wrapper);
            } else {
                wrapper.like("sname", sname).and(querywrapper -> querywrapper.eq("tid", 0).or().isNull("tid"));
                list = studentService.page(page, wrapper);
            }
        } else {
            if (sname == null || "".equals(sname)) {
                wrapper.eq("tid", tid);
                list = studentService.page(page, wrapper);
            } else {
                wrapper.eq("tid", tid).like("sname", sname);
                list = studentService.page(page, wrapper);
            }
        }

        return list;
    }


    @RequestMapping(value = "addStudent")
    @ResponseBody
    public String addStudent(Student student) {
        boolean flag = studentService.save(student);
        Emp emp = new Emp();
        emp.setEname(student.getSname());
        emp.setSid(student.getSid());
        empService.save(emp);
        student.setEid(emp.getEid());
        studentService.updateById(student);
        User user = new User();
        user.setRole(3);
        user.setUname(emp.getEid());
        userService.save(user);
        if (flag) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "addStudents")
    @ResponseBody
    public String addStudents(@RequestBody List<Student> students){
        for(Student student : students){
            addStudent(student);
        }
        return "success";
    }


    /**
     * 删除学生，删除学生的成绩、员工信息，用户信息
     *
     * @param sid
     * @return
     */
    @RequestMapping(value = "delStudentById")
    @ResponseBody
    public String delStudentById(int sid) {
        UpdateWrapper<StudentScore> wrapper = new UpdateWrapper<>();
        wrapper.eq("sid", sid);
        studentScoreService.remove(wrapper);

        Student student = studentService.getById(sid);
        boolean flag = studentService.removeById(sid);

        empService.removeById(student.getEid());

        UpdateWrapper<User> wrapper1 = new UpdateWrapper<>();
        wrapper1.eq("uname", student.getEid());
        userService.remove(wrapper1);

        if (flag) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "delStudentsByIds")
    @ResponseBody
    public String delStudentsByIds(int[] sids) {
        for (int sid : sids) {
            delStudentById(sid);
        }
        return "success";
    }


}
