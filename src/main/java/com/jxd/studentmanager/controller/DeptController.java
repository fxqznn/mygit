package com.jxd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.Dept;
import com.jxd.studentmanager.model.DeptCourse;
import com.jxd.studentmanager.model.Emp;
import com.jxd.studentmanager.service.ICourseService;
import com.jxd.studentmanager.service.IDeptCourseService;
import com.jxd.studentmanager.service.IDeptService;
import com.jxd.studentmanager.service.IEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DeptController
 * @Description TODO
 * @Author Zheng NaNa
 * @Date 2020/10/28
 * @Version 1.0
 */
@Controller
public class DeptController {
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IEmpService empService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IDeptCourseService deptCourseService;

    /**
     * 分页加部门名称模糊查询
     *
     * @param page
     * @param dname
     * @return
     */
    @RequestMapping(value = "getAllDept")
    @ResponseBody
    public IPage<Dept> getAllDept(Page<Dept> page, String dname) {
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        if (dname == null || "".equals(dname)) {
            wrapper.eq("isdel", 0);
            return deptService.page(page, wrapper);
        } else {
            wrapper.like("dname", dname).eq("isdel", 0);
            return deptService.page(page, wrapper);
        }
    }

    @RequestMapping(value = "getAllDeptForEmp")
    @ResponseBody
    public List<Dept> getAllDeptForEmp(){
        return deptService.list();
    }

    @RequestMapping(value = "getDeptById")
    @ResponseBody
    public Map<String, Object> getDeptById(int did) {
        Map<String,Object> map = new HashMap<>();

        Dept dept = deptService.getById(did);
        map.put("dept",dept);

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("isdel",0).ne("type",0);
        List<Course> courses = courseService.list(wrapper);
        map.put("courses",courses);

        List<Course> checkedCourses = courseService.selectCoursesByDid(did);
        map.put("checkedCourses",checkedCourses);

        return map;
    }

    @RequestMapping(value = "addDept")
    @ResponseBody
    public String addDept(Dept dept, List<Course> courses) {
        boolean flag = deptService.save(dept);

        for(Course course : courses) {
            DeptCourse deptCourse = new DeptCourse();
            deptCourse.setDid(dept.getDid());
            deptCourse.setCid(course.getCid());
            deptCourseService.save(deptCourse);
        }
        if (flag) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "editDept")
    @ResponseBody
    public String editDept(Dept dept, List<Course> checkCourses) {
        boolean flag = deptService.updateById(dept);

        List<Course> oldCheckedCourses = courseService.selectCoursesByDid(dept.getDid());

        //如果取消选择课程，需要删除学期课程中的信息,
        //旧的选择课程中有，新的没有
        for(Course oldCourse : oldCheckedCourses){
            boolean isexist = false;
            for(Course newCourse : checkCourses){
                if(newCourse.getCid() == oldCourse.getCid()){
                    isexist = true;
                    break;
                }
            }
            if(isexist == false){
                UpdateWrapper<DeptCourse> wrapper = new UpdateWrapper<>();
                wrapper.eq("did",dept.getDid()).eq("cid",oldCourse.getCid());
                deptCourseService.remove(wrapper);
            }
        }

        //新添加课程，需要在学期课程中添加信息
        //新的选择课程中有，旧的没有
        for(Course newCourse : checkCourses){
            boolean isexist = false;
            for(Course oldCourse : oldCheckedCourses){
                if(newCourse.getCid() == oldCourse.getCid()){
                    isexist = true;
                    break;
                }
            }
            if(isexist == false) {
                DeptCourse deptCourse = new DeptCourse();
                deptCourse.setDid(dept.getDid());
                deptCourse.setCid(newCourse.getCid());
                deptCourseService.save(deptCourse);
            }
        }


        if (flag) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "delDeptById")
    @ResponseBody
    public String delDeptById(int did) {
        boolean flag = false;

        QueryWrapper<Emp> wrapper = new QueryWrapper<>();
        wrapper.eq("did", did);
        int empNum = empService.count(wrapper);

        if (empNum > 0) {
            Dept dept = deptService.getById(did);
            dept.setIsdel(1);
            flag = deptService.updateById(dept);
        } else {
            flag = deptService.removeById(did);
        }

        if (flag) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "delDeptsByIds")
    @ResponseBody
    public String delDeptByIds(int[] dids) {
        for (int did : dids) {
            String result = delDeptById(did);
            if (result.equals("fail")) {
                return "fail";
            }
        }
        return "success";
    }
}
