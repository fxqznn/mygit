package com.jxd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxd.studentmanager.model.*;
import com.jxd.studentmanager.service.ICourseService;
import com.jxd.studentmanager.service.IDeptCourseService;
import com.jxd.studentmanager.service.ITermCourseService;
import com.jxd.studentmanager.service.ITermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CourseController
 * @Description TODO
 * @Author Zheng NaNa
 * @Date 2020/10/28
 * @Version 1.0
 */
@Controller
public class CourseController {
    @Autowired
    ICourseService courseService;

    @Autowired
    ITermCourseService termCourseService;

    @Autowired
    private IDeptCourseService deptCourseService;

    @Autowired
    private ITermService termService;
    /**
     * 查询课程信息，返回列表中包含tid
     * @param tid
     * @return
     */
    @RequestMapping(value = "getCourses",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Map<String,Object>> getCoursesByTid (int tid){
        return courseService.getCourseTerm(tid);
    }

    @RequestMapping(value = "getCoursesTerm",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Map<String,Object>> getCoursesTermById (int tid){
        return courseService.getCourseScoreTerm(tid);
    }

    @RequestMapping(value = "getTermCourses",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Course> getCoursesTerm (){
        QueryWrapper queryWrapper = new QueryWrapper();
        Map<String,Object> map = new HashMap<>();
        map.put("isdel",0);
        map.put("type",0);
        queryWrapper.allEq(map,true);
        return courseService.list(queryWrapper);
    }


    @RequestMapping(value = "getCourseTermByEid",produces = "application/json;charset=utf-8")
    @ResponseBody
    public IPage<List<Map<String,Object>>> getCourseTermByEid (int current,int size,int eid,String cname){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("eid",eid);
        List<Term> terms = termService.list(queryWrapper);
        int tid = 0;  //该老师管理下的最新的一个班级
        for (Term term : terms){
            if (tid<term.getTid()){
                tid = term.getTid();
            }
        }
        IPage page = new Page();
        page.setRecords(courseService.selectCoursesByTid(current,size,tid,0,cname));
        page.setCurrent(current);
        page.setSize(size);
        page.setTotal(courseService.selectCoursesCount(tid,0,cname).size());
        return page;

    }

    @RequestMapping(value = "insertTermCourse",produces = "html/text;charset=utf-8")
    @ResponseBody
    public String insertTermCourse(int eid,int cid){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("eid",eid);
        List<Term> terms = termService.list(queryWrapper);
        int tid = 0;  //该老师管理下的最新的一个班级
        for (Term term : terms){
            if (tid<term.getTid()){
                tid = term.getTid();
            }
        }
        TermCourse termCourse = new TermCourse();
        termCourse.setCid(cid);
        termCourse.setTid(tid);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("cid",cid);
        queryWrapper1.eq("tid",tid);
        TermCourse termCourse1 = termCourseService.getOne(queryWrapper1);
        boolean flag = termCourseService.save(termCourse);
        if(termCourse1 != null){
            return "exist";
        } else {
            if (flag){
                return "success";
            } else {
                return "fail";
            }
        }
    }
    @RequestMapping(value = "insertCourseAndTermCourse",produces = "html/text;charset=utf-8")
    @ResponseBody
    public String insertCourseAndTermCourse(String cname,int eid){
        Course course = new Course();
        course.setCname(cname);
        course.setIsdel(0);
        course.setType(0);
        boolean flag = courseService.save(course);
        int cid = course.getCid();
        System.out.println(cid);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("eid",eid);
        List<Term> terms = termService.list(queryWrapper);
        int tid = 0;  //该老师管理下的最新的一个班级
        for (Term term : terms){
            if (tid<term.getTid()){
                tid = term.getTid();
            }
        }
        TermCourse termCourse = new TermCourse();
        termCourse.setCid(cid);
        termCourse.setTid(tid);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("cid",cid);
        queryWrapper1.eq("tid",tid);
        TermCourse termCourse1 = termCourseService.getOne(queryWrapper1);
        boolean flag2 = termCourseService.save(termCourse);
        if(termCourse1 != null){
            return "exist";
        } else {
            if (flag && flag2){
                return "success";
            } else {
                return "fail";
            }
        }
    }
    /**
     * 老师往课程表中批量插入课程
     * @param courses
     * @return
     */
    @RequestMapping("/addCourseBatch")
    @ResponseBody
    public String addCourseBatch(List<Course> courses){
        boolean flag_tc = courseService.saveBatch(courses);
        if (flag_tc){
            return "success";
        } else {
            return "error";
        }
    }

    /**
     * 根据cid和tid添加学期课程，如果存在cid和tid与将要插入的同时相同时
     * 则不添加
     * @param cid
     * @param tid
     * @return
     */
    @RequestMapping(value = "addTermCourse" ,produces = "html/text;charset=utf-8")
    @ResponseBody
    public String addTermCourse(int cid,int tid){
        List<TermCourse> termCourses = termCourseService.list();
        boolean flag = false;
        for (TermCourse termCourse: termCourses){
            if (termCourse.getTid() == tid && termCourse.getCid() == cid){
                flag = false;
                break;
            } else {
                flag = true;
            }
        }
        if (flag){
            TermCourse termCourse = new TermCourse();
            termCourse.setTid(tid);
            termCourse.setCid(cid);
            if (termCourseService.save(termCourse)){
                return "课程添加成功";
            } else {
                return "课程已存在";
            }
        }else {
            return "课程添加失败";
        }
    }

    @RequestMapping(value = "updateCourseBatch",produces = "html/text;charset=utf-8")
    @ResponseBody
    public String updateCourseBatch(List<Course> courses){
        boolean flag = false;
        flag = courseService.updateBatchById(courses);
        if (!flag){
            return "修改失败";
        } else {
            return "修改成功";
        }
    }

    @RequestMapping(value = "updateCourse",produces = "html/text;charset=utf-8")
    @ResponseBody
    public String updateCourse(Course course){
        boolean flag = false;
        flag = courseService.updateById(course);
        if (!flag){
            return "修改失败";
        } else {
            return "修改成功";
        }
    }

    /**
     * 分页查询所有的课程，根据课程名称进行模糊查询，根据课程类别进行分类查询
     * 2代表查询全部，其余代表课程类型
     * @param page
     * @param type
     * @param cname
     * @return
     */
    public IPage<Course> getAllCourse(Page<Course> page, int type, String cname,int isdel){
        QueryWrapper<Course> wrapper = new QueryWrapper<>();

        //查询对应类别的的课程
        if(cname == null || "".equals(cname)){
            wrapper.eq("type",type).eq("isdel",isdel);
        } else {
            wrapper.like("cname",cname).eq("type",type).eq("isdel",isdel);
        }

        return courseService.page(page,wrapper);
    }

    @RequestMapping(value = "getCoursesForTerm")
    @ResponseBody
    public List<Map<String,Object>> getCoursesForTerm(int tid){
        List<Map<String,Object>> list = new ArrayList<>();
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("type",0);
        List<Course> courseListlist = courseService.list(courseQueryWrapper);
        for(Course course : courseListlist){
            Map<String,Object> map = new HashMap<>();
            map.put("cid",course.getCid());
            map.put("cname",course.getCname());
            if(course.getIsdel() == 0){
                map.put("isdel","");
            } else {
                map.put("isdel","已删除");
            }
            QueryWrapper<TermCourse> wrapper = new QueryWrapper<>();
            wrapper.eq("cid",course.getCid()).eq("tid",tid);
            TermCourse termCourse = termCourseService.getOne(wrapper);
            if(termCourse == null){
                map.put("checked",false);
            } else {
                map.put("checked",true);
            }
            list.add(map);
        }
        return list;
    }

    @RequestMapping(value = "getCoursesForDept")
    @ResponseBody
    public List<Map<String,Object>> getCoursesForDept(int did){
        List<Map<String,Object>> list = new ArrayList<>();
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("type",1);
        List<Course> courseListlist = courseService.list(courseQueryWrapper);
        for(Course course : courseListlist){
            Map<String,Object> map = new HashMap<>();
            map.put("cid",course.getCid());
            map.put("cname",course.getCname());
            if(course.getIsdel() == 0){
                map.put("isdel","");
            } else {
                map.put("isdel","已删除");
            }
            QueryWrapper<DeptCourse> wrapper = new QueryWrapper<>();
            wrapper.eq("cid",course.getCid()).eq("did",did);
            DeptCourse deptCourse = deptCourseService.getOne(wrapper);
            if(deptCourse == null){
                map.put("checked",false);
            } else {
                map.put("checked",true);
            }
            list.add(map);
        }
        return list;
    }

    @RequestMapping(value = "getCourseById")
    @ResponseBody
    public Course getCourse(int cid){
        return courseService.getById(cid);
    }

    @RequestMapping(value = "addCourse")
    @ResponseBody
    public String addCourse(Course course){
        boolean flag = courseService.save(course);
        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "editCourse")
    @ResponseBody
    public String editCourse(Course course){
        boolean flag = courseService.updateById(course);
        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "delCourseById")
    @ResponseBody
    public String delCourseById(int cid){
        boolean flag = false;

        QueryWrapper<TermCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("cid",cid);
        int num = termCourseService.count(wrapper);
        QueryWrapper<DeptCourse> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("cid",cid);
        int num1 = deptCourseService.count(wrapper1);

        if(num > 0 || num1 > 0){
            Course course = courseService.getById(cid);
            course.setIsdel(1);
            flag = courseService.updateById(course);
        }else {
            flag = courseService.removeById(cid);
        }

        if(flag){
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "delCoursesByIds")
    @ResponseBody
    public String delCoursesByIds(int[] cids){
        for(int cid : cids){
            delCourseById(cid);
        }
        return "success";
    }

    @RequestMapping(value = "delTermCoursesById")
    @ResponseBody
    public String delTermCoursesById(int cid, int eid){
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("eid",eid);
        List<Term> terms = termService.list(queryWrapper1);
        int tid = 0;  //该老师管理下的最新的一个班级
        for (Term term : terms){
            if (tid<term.getTid()){
                tid = term.getTid();
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("cid",cid);
        QueryWrapper queryWrapper = new QueryWrapper();
        boolean isdel = termCourseService.removeByMap(map);
        if (isdel){
            return "success";
        } else {
            return "fail";
        }

    }


    @RequestMapping(value = "delTermCoursesByIds")
    @ResponseBody
    public String delTermCoursesByIds(int[] cids){
        int eid = cids[cids.length-1];
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("eid",eid);
        List<Term> terms = termService.list(queryWrapper1);
        int tid = 0;  //该老师管理下的最新的一个班级
        for (Term term : terms){
            if (tid<term.getTid()){
                tid = term.getTid();
            }
        }
        boolean flag = true;
        for(int i = 0; i < cids.length-1; i++){
            Map<String,Object> map = new HashMap<>();
            map.put("cid",cids[i]);
            QueryWrapper queryWrapper = new QueryWrapper();
            boolean isdel = termCourseService.removeByMap(map);
            if (!isdel) {
                flag = false;
            }
        }
        if (flag){
            return "success";
        } else {
            return "fail";
        }

    }
}
