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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    @Value(value = "${web.upload-path}")
    private String path;

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
        if("".equals(student.getBirthday())){
            student.setBirthday(null);
        }

        boolean flag = studentService.updateById(student);

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

    @RequestMapping("/modifyDetails")
    @ResponseBody
    public String modifyDetails(Student student) {
        Student s = studentService.getById(student.getSid());
        boolean flag = studentService.updateById(student);
        boolean flag_emp = true;
        if (!student.getSname().equals(s.getSname())) {
            Map<String, Object> map = new HashMap<>();
            map.put("sid", student.getSid());
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.allEq(map, true);
            Emp emp = empService.getOne(queryWrapper);
            emp.setEname(student.getSname());
            flag_emp = empService.updateById(emp);
        }
        if (flag && flag_emp) {
            return "success";
        } else {
            return "fail";
        }
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

    @RequestMapping(value = "upload", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String imgUpload(@RequestParam("photo") MultipartFile file) {
        File file_save = new File(path);
        if (!file_save.exists() && !file_save.isDirectory()) {
            file_save.mkdir();
        }
        //处理文件名，添加UUID，保证每个文件名全局唯一
        //获取源文件名
        String fname_old = file.getOriginalFilename();
        //获取UUID,全局唯一32位的字符串，包含数字字母和-
        String uuid = UUID.randomUUID().toString();
        String fname_new = uuid + fname_old;

        File savefile = new File(path, fname_new);
        try {
            file.transferTo(savefile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fname_new;
    }

    @RequestMapping("/delImg")
    @ResponseBody
    public String delImg(String imgName) {
        if (imgName == null || imgName.length() == 0) {
            return "none";
        }
        File file = new File(path, imgName);
        if (!file.exists()) {
            //文件不存在
            return "none";
        }
        if (file.delete()) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping("/updatePic/{sid}/{pic}")
    @ResponseBody
    public String updatePic(@PathVariable("sid") int sid, @PathVariable("pic") String pic) {
        Student student = studentService.getById(sid);
        student.setPic(pic);
        boolean flag = studentService.updateById(student);
        if (flag) {
            return "success";
        } else {
            return "fail";
        }
    }
}
