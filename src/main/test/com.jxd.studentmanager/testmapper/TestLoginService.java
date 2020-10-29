package com.jxd.studentmanager.testmapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jxd.studentmanager.StudentManagerApplication;
import com.jxd.studentmanager.mapper.IStudentMapper;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.TermCourse;
import com.jxd.studentmanager.model.User;
import com.jxd.studentmanager.service.ICourseService;
import com.jxd.studentmanager.service.ITermCourseService;
import com.jxd.studentmanager.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestStudentMapper
 * @Description TODO
 * @Author jinkaiyan
 * @Date 2020/10/28
 * @Version 1.0
 */
@RunWith(SpringRunner.class)//用谁作为启动器去加载所有的底层配置
@SpringBootTest(classes = StudentManagerApplication.class)
public class TestLoginService {
    @Resource
    private IStudentMapper studentMapper;

    @Resource
    private IUserService userService;

    @Test
    public void testLogin() {
        String uname = "111";
        String pwd = "123456";
        Map<String,Object> map = new HashMap<>();
        map.put("uname",uname);
        map.put("pwd",pwd);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(map,true);
        User user = userService.getOne(queryWrapper,true);
        if (user != null){
            System.out.println("success");;
        }else {
            System.out.println("error");;
        }
    }

    @Resource
    private ICourseService courseService;
    @Test
    public void testAddCourses(){
        Course course1 = new Course();
        course1.setCname("oracle数据库");
        Course course2 = new Course();
        course2.setCname("web开发");
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        boolean flag = courseService.saveBatch(courses);
        System.out.println(flag);
    }

    @Resource
    private ITermCourseService termCourseService;
    @Test
    public void testAddTermCourse() {
        /*Map<String, Object> map = new HashMap<>();
        map.put("cname", "java基础");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.allEq(map, true);
        List<TermCourse> termCourses = new ArrayList<>();
        List<Course> courses = courseService.list(queryWrapper);
        for (Course course : courses) {
            TermCourse termCourse = new TermCourse();
            termCourse.setCid(course.getCid());
            termCourse.setTid(1);
            termCourses.add(termCourse);
        }
        if (termCourseService.saveOrUpdateBatch(termCourses)) {
            System.out.println("success");
        } else {
            System.out.println("error");
        }*/
        int tid = 2;
        int cid = 2;
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
                System.out.println("success");
            } else {
                System.out.println("error");
            }
        }else {
            System.out.println("error");
        }
    }

    @Test
    public void testSelectCoursesByTid(){
        System.out.println(courseService.selectCoursesByTid(0).size());;
    }

    @Test
    public void updateCourse(){
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setCname("c");
        course1.setCid(1);
        courses.add(course1);
        boolean flag = false;
        for(Course course:courses){
            UpdateWrapper<Course> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("cid",course.getCid());
            flag = courseService.update(course,updateWrapper);
            if (!flag){
                System.out.println("修改失败");
            }
        }
        if (!flag){
            System.out.println("修改失败");
        } else {
            System.out.println("修改成功");
        }
    }
}
