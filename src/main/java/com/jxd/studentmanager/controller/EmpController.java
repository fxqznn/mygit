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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName EmpController
 * @Description TODO
 * @Author Zheng NaNa
 * @Date 2020/10/28
 * @Version 1.0
 */
@Controller
public class EmpController {

    @Autowired
    private IEmpService empService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IStudentService studentService;
    @Autowired
    private IStudentScoreService studentScoreService;

    /**
     * 分页查询代码，前端分页显示Emp
     * @param page  封装分页参数 current-当前页码 size-每页显示记录条数
     * @param ename 模糊查询，通过姓名进行模糊查询
     * @return
     */
    @RequestMapping(value = "getAllEmp" , produces = "application/json;charset=utf-8")
    @ResponseBody
    public IPage<Emp> getAllEmp(Page<Emp> page, String ename, int did){

        IPage<Emp> list = null;
        QueryWrapper<Emp> wrapper =  new QueryWrapper<>();

        if(did == -1){
            //查询全部
            if(ename == null || ename == ""){
                wrapper.eq("isdel",0);
                list = empService.page(page,wrapper);

            } else {
                wrapper.like("ename",ename).eq("isdel",0);
                list = empService.page(page,wrapper);
            }
        } else if (did == 0) {
            //查询没有部门的，插入的时候did应该是0的部门或者为空的部门
            if(ename == null || ename == ""){
                wrapper.eq("isdel",0).and(querywrapper -> querywrapper.eq("did",0).or().isNull("did"));
                list = empService.page(page);
            } else {
                wrapper.like("ename",ename).eq("isdel",0).and(querywrapper -> querywrapper.eq("did",0).or().isNull("did"));
                list = empService.page(page,wrapper);
            }

        } else {
            if(ename == null || ename == ""){
                wrapper.eq("did",did).eq("isdel",0);
                list = empService.page(page);
            } else {
                wrapper.like("ename",ename).eq("did",did).eq("isdel",0);
                list = empService.page(page,wrapper);
            }
        }

        return list;
    }

    /**
     * 添加一个员工信息，并且为这个员工创建一个可以登入的账号，如果是金桥的学生，还需要为它创建一个学生记录
     * @param emp 前端传递过来的员工信息
     * @param role  前端传递过来的为账户赋值的权限
     * @return
     */
    @RequestMapping(value = "addEmp")
    @ResponseBody
    public String addEmpWithUser(Emp emp, int role){
        boolean flag = empService.save(emp);
        int eid = emp.getEid();
        User user = new User();
        user.setUname(eid);
        user.setRole(role);
        flag = userService.save(user);
        if(role == 3){
            Student student = new Student();
            student.setSname(emp.getEname());
            student.setEid(eid);
            flag = studentService.save(student);
            emp.setSid(student.getSid());
            flag = empService.save(emp);
        }
        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 修改一个员工的信息
     * @param emp
     * @return
     */
    @RequestMapping(value = "editEmp")
    @ResponseBody
    public String editEmp(Emp emp){
        boolean flag = empService.updateById(emp);
        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 根据员工的id查找员工的信息
     * @param eid
     * @return
     */
    @RequestMapping(value = "getEmpById")
    @ResponseBody
    public Emp getEmpById(int eid){
        return empService.getById(eid);
    }

    /**
     * 根据员工的id删除员工信息,如果员工是经理或者老师，有打分的情况不允许删除
     * 如果员工是学生，则删除学生账号，删除学生的分数记录
     * 如果是管理员，则直接删除账号，删除员工记录
     * @param eid
     * @return
     */
    @RequestMapping(value = "delEmpByIdCascade")
    @ResponseBody
    public String delEmpByIdCascade(int eid){
        boolean flag = false;

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("eid",eid);
        User user = userService.getOne(wrapper);

        if(user.getRole() == 0){
            //管理员账号
            flag = userService.removeById(user.getUid());
            flag = empService.removeById(eid);

        } else if (user.getRole() == 3){
            //学生账号
            QueryWrapper<Student> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("eid",eid);
            Student student = studentService.getOne(wrapper1);

            UpdateWrapper<StudentScore> wrapper2 = new UpdateWrapper<>();
            wrapper2.eq("sid",student.getSid());
            flag = studentScoreService.remove(wrapper2);

            flag = studentService.removeById(student.getEid());
        } else {
            //教师或者部门经理账号
            QueryWrapper<StudentScore> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("eid",eid);
            int num = studentScoreService.count(wrapper1);

            if(num == 0){
                flag = empService.removeById(eid);
            } else {
                Emp emp = empService.getById(eid);
                emp.setIsdel(1);
                flag = empService.updateById(emp);
            }
        }
        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "delEmpById")
    @ResponseBody
    public String delEmpById(int eid){
        boolean flag = empService.removeById(eid);
        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 删除多个员工的信息，调用删除个人的方法
     */
    @RequestMapping(value = "delEmpsByIdsCascade")
    @ResponseBody
    public String delEmpsByIdsCascade(int[] eids){
        for(int eid : eids){
            String result = delEmpById(eid);
            if(result.equals("fail")){
                return "fail";
            }
        }
        return "success";
    }

    @RequestMapping(value = "delEmpsByIds")
    @ResponseBody
    public String delEmpsByIds(int[] eids){
        List<Integer> list = new ArrayList<>();
        for(int eid : eids){
            list.add(eid);
        }
        boolean flag = empService.removeByIds(list);
        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }
}
