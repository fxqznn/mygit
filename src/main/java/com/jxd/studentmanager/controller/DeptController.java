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
    public IPage<Dept> getAllDept(Page<Dept> page, String dname , int isdel, int eid) {
        if(eid == 0){
            QueryWrapper<Dept> wrapper = new QueryWrapper<>();
            if (dname == null || "".equals(dname)) {
                wrapper.eq("isdel", isdel);
                return deptService.page(page, wrapper);
            } else {
                wrapper.like("dname", dname).eq("isdel", isdel);
                return deptService.page(page, wrapper);
            }
        } else {
            QueryWrapper<Dept> wrapper = new QueryWrapper<>();
            if (dname == null || "".equals(dname)) {
                wrapper.eq("isdel", isdel).eq("dheader",eid);
                return deptService.page(page, wrapper);
            } else {
                wrapper.like("dname", dname).eq("isdel", isdel).eq("dheader",eid);
                return deptService.page(page, wrapper);
            }
        }
    }

    @RequestMapping(value = "getAllDeptForEmp")
    @ResponseBody
    public List<Dept> getAllDeptForEmp(){
        return deptService.list();
    }

    @RequestMapping(value = "getDeptById")
    @ResponseBody
    public Dept getDeptById(int did) {
        return deptService.getById(did);
    }

    @RequestMapping(value = "addDept")
    @ResponseBody
    public String addDept(Dept dept) {
        boolean flag = deptService.save(dept);

        if (flag) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "editDept")
    @ResponseBody
    public String editDept(Dept dept) {
        boolean flag = deptService.updateById(dept);
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
