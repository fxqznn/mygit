package com.jxd.studentmanager.testmapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxd.studentmanager.StudentManagerApplication;
import com.jxd.studentmanager.mapper.ICourseMapper;
import com.jxd.studentmanager.mapper.IStudentMapper;
import com.jxd.studentmanager.model.*;
import com.jxd.studentmanager.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Resource
    private ICourseService courseService;

    @Resource
    private IStudentScoreService studentScoreService;

    @Resource
    private IStudentService studentService;




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
        List<Course> coursesAll = courseService.list();
        List<Course> coursePage = courseService.selectCoursesByTid(1,5,1,2,null);
        for (Course course:coursePage){
            System.out.println(course.getCname());
        }
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

    @Test
    public void getCourseWithScore(){
        List<Map<String,Object>> courseWithScore = new ArrayList<>();
        List<Student> studentList = studentService.list();
        List<Course> courseList = courseService.list();
        for (Student student:studentList){
            int sid = student.getSid();
            Map<String,Object> map = new HashMap<>();
            for (Course course:courseList){
                int cid = course.getCid();
                int type = course.getType();
                String cname = course.getCname();
                QueryWrapper queryWrapper = new QueryWrapper();
                Map<String,Object> map1 = new HashMap<>();
                map1.put("sid",sid);
                map1.put("cid",cid);
                map1.put("type",type);
                queryWrapper.allEq(map1);
                List<StudentScore> studentScores= studentScoreService.list(queryWrapper);
                for (StudentScore studentScore:studentScores){
                    Double score = studentScore.getScore();
                    //System.out.println(cname+"\t"+score);
                    map.put(cname,score);
                }

            }
            courseWithScore.add(map);
        }

    }

    @Test
    public void getScoreWithCourse(){
        List<Map<String,Object>> list = studentService.getScoreWithCourse(1,5,"张",1);
        for (Map map:list){
            System.out.println(map.get("z1")+"\t"+map.get("s1"));
        }
    }



}
