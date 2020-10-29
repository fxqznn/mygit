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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.URIParameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Controller
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IEmpService empService;
    @Autowired
    private IStudentService studentService;
    @Autowired
    private IStudentScoreService studentScoreService;

    @RequestMapping("/login/{uname}/{pwd}")
    @ResponseBody
    public User login(@PathVariable("uname") String uname, @PathVariable("pwd") String pwd){
        Map<String,Object> map = new HashMap<>();
        map.put("uname",uname);
        map.put("pwd",pwd);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(map,true);
        User user = userService.getOne(queryWrapper,true);
        if (user != null){
            return user;
        }else {
            return null;
        }
    }

    /**
     * 分页查询所有的账户信息
     * @param page current-当前页 size-条数
     * @param role  角色
     * @param uname 工号模糊查询
     * @return
     */
    @RequestMapping(value = "getAllUser")
    @ResponseBody
    public Map<String,Object> getAllUser(Page<User> page, int role, String uname){
        Map<String,Object> map = new HashMap<>();

        int userCount = 0;
        IPage<User> list = null;
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        if(role == -1){
            if(uname == null || uname == ""){
                userCount= userService.count();
                list = userService.page(page);
            } else {
                wrapper.likeRight("ename",uname);
                userCount = userService.count(wrapper);
                list = userService.page(page,wrapper);
            }
        } else {
            if(uname == null || uname == ""){
                wrapper.eq("role",role);
            } else {
                wrapper.likeRight("uname",uname).eq("role",role);
            }
            userCount = userService.count(wrapper);
            list = userService.page(page,wrapper);
        }

        map.put("count",userCount);
        map.put("list",list);

        return map;
    }

    /**
     * 获得一个用户的信息
     * @param uid
     * @return
     */
    @RequestMapping(value = "getUserById")
    @ResponseBody
    public User getUserById(int uid){
        return userService.getById(uid);
    }

    /**
     * 通过姓名和角色创建用户
     * 先创建员工信息，获取员工的id，在添加账号信息，如果是学生的话，需要为学生添加一个账户
     * @param name
     * @param role
     * @return
     */
    @RequestMapping(value = "addUser")
    @ResponseBody
    public String addUser(String name, int role){
        Emp emp = new Emp();
        emp.setEname(name);
        boolean flag = empService.save(emp);
        int eid = emp.getEid();
        User user = new User();
        user.setUname(eid);
        user.setRole(role);
        flag = userService.save(user);

        if(role == 3){
            //学生
            Student student = new Student();
            student.setSname(name);
            student.setEid(eid);
            flag = studentService.save(student);
            int sid = student.getSid();
            emp.setSid(sid);
            flag = empService.updateById(emp);
        }

        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 修改账号信息
     * @param user
     * @return
     */
    @RequestMapping(value = "editUser")
    @ResponseBody
    public String editUser(User user){
        boolean flag = userService.updateById(user);
        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 设置密码为默认密码
     * @param uid
     * @return
     */
    @RequestMapping(value = "setDefaultPwd")
    @ResponseBody
    public String setDefaultPwd(int uid){
        User user = userService.getById(uid);
        user.setPwd(null);
        boolean flag = userService.updateById(user);

        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 删除账号吧，并且根据账号所在角色进行级联删除
     * @param uid
     * @return
     */
    @RequestMapping(value = "delUserByIdCascade")
    @ResponseBody
    public String delUserWithEmp(int uid){
        boolean flag = false;

        User user = userService.getById(uid);

        if(user.getRole() == 3){
            //学生账号级联删除
            QueryWrapper<Student> wrapper = new QueryWrapper<>();
            wrapper.eq("eid",user.getUname());
            Student student = studentService.getOne(wrapper);

            UpdateWrapper<StudentScore> wrapper1 = new UpdateWrapper<>();
            wrapper1.eq("sid",student.getSid());
            studentScoreService.remove(wrapper1);

            studentService.removeById(student.getSid());
            flag = empService.removeById(user.getUname());
        } else if (user.getRole() == 0){
            //管理员账号级联删除
            empService.removeById(user.getUname());
        } else {
            //部门经理或者老师账号级联删除
            QueryWrapper<StudentScore> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("eid",user.getUname());
            int num = studentScoreService.count(wrapper1);

            if(num == 0){
                flag = empService.removeById(user.getUname());
            } else {
                Emp emp = empService.getById(user.getUname());
                emp.setIsdel(1);
                flag = empService.updateById(emp);
            }
        }

        flag = userService.removeById(uid);

        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "delUserById")
    @ResponseBody
    public String delUserById(int uid){
        boolean flag = userService.removeById(uid);
        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "delUsersByIdsCascade")
    @ResponseBody
    public String delUsersByIdsCascade(int[] uids){
        for(int uid : uids){
            String result = delUserById(uid);
            if(result.equals("fail")){
                return "fail";
            }
        }
        return "success";
    }

    @RequestMapping(value = "delUsersByIds")
    @ResponseBody
    public String delUsersByIds(int[] uids){
        List<Integer> list = new ArrayList<>();
        for(int uid : uids){
            list.add(uid);
        }
        boolean flag = userService.removeByIds(list);
        if(flag){
            return "success";
        } else {
            return "error";
        }
    }

}
