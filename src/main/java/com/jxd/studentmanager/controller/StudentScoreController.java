package com.jxd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jxd.studentmanager.mapper.IStudentScoreMapper;
import com.jxd.studentmanager.model.*;
import com.jxd.studentmanager.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName StudentScoreController
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Controller
public class StudentScoreController {


    @Autowired
    private IStudentScoreService studentScoreService;
    @Autowired
    private IStudentService studentService;
    @Autowired
    private IEmpService empService;
    @Autowired
    private ITermCourseService termCourseService;
    @Autowired
    private IDeptCourseService deptCourseService;
    @Autowired
    private ICourseService courseService;



    /**
     * 通过学生id查询成绩时调用,根据学生的id和查询成绩的类型调用
     * @param sid
     * @param type 查询成绩类型 0-4：转正、1年、2年、3年 -1：金桥
     */
    public void insertStudenScoreBySid(int sid,int type){
        Student student = studentService.getById(sid);

        if(type == -1){
            //该学生所在学期的选中的课程
            QueryWrapper<TermCourse> wrapper = new QueryWrapper<>();
            wrapper.eq("tid",student.getTid());
            List<TermCourse> schoolCourse = termCourseService.list(wrapper);

            //该学生选中学生课程表中选中的信息
            QueryWrapper<StudentScore> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("sid",student.getSid()).eq("type",type);
            List<StudentScore> allScore = studentScoreService.list(wrapper2);

            //学期中存在，成绩中不存在，需要添加新的成绩记录
            for(TermCourse termselectcourse : schoolCourse){
                boolean isexist = false;
                for(StudentScore studentScore : allScore){
                    if(termselectcourse.getCid() == studentScore.getCid()){
                        isexist = true;
                        break;
                    }
                }
                if(isexist == false){
                    StudentScore studentScore = new StudentScore();
                    studentScore.setSid(sid);
                    studentScore.setCid(termselectcourse.getCid());
                    studentScore.setType(type);
                    studentScoreService.save(studentScore);
                }
            }
            //成绩中存在，学期中不存在，需要删除成绩记录
            for(StudentScore score : allScore){
                boolean isexist = false;
                for(TermCourse termCourse : schoolCourse){
                    if(score.getCid() == termCourse.getCid()){
                        isexist = true;
                    }
                }
                if(isexist == false){
                    studentScoreService.removeById(score.getSsid());
                }
            }
        } else {
            //该学生所在部门选中的课程
            Emp emp = empService.getById(student.getEid());
            QueryWrapper<DeptCourse> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("did",emp.getDid());
            List<DeptCourse> deptCourse = deptCourseService.list();

            //该学生选中学生课程表中选中的信息
            QueryWrapper<StudentScore> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("sid",student.getSid()).eq("type",type);
            List<StudentScore> allScore = studentScoreService.list(wrapper2);

            //部门中存在，成绩中不存在，需要添加新的成绩记录
            for(DeptCourse deptselectcourse : deptCourse){
                boolean isexist = false;
                for(StudentScore studentScore : allScore){
                    if(deptselectcourse.getCid() == studentScore.getCid()){
                        isexist = true;
                        break;
                    }
                }
                if(isexist == false){
                    StudentScore studentScore = new StudentScore();
                    studentScore.setSid(sid);
                    studentScore.setCid(deptselectcourse.getCid());
                    studentScore.setType(type);
                    studentScoreService.save(studentScore);
                }
            }
            //成绩中存在，部门中不存在，需要删除成绩记录
            for(StudentScore score : allScore){
                boolean isexist = false;
                for(DeptCourse deptselectcourse : deptCourse){
                    if(score.getCid() == deptselectcourse.getCid()){
                        isexist = true;
                    }
                }
                if(isexist == false){
                    studentScoreService.removeById(score.getSsid());
                }
            }
        }
    }

    /**
     * 通过员工id查询成绩时使用
     * @param eid
     * @param type  查询成绩类型
     */
    public void insertStudentScoreByEid(int eid,int type){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("eid",eid);
        Student student = studentService.getOne(wrapper);
        insertStudenScoreBySid(student.getSid(),type);
    }


    @RequestMapping("/getScoreCourses/{sid}")
    @ResponseBody
    public List<Map<String, Object>> getScoreCourses(@PathVariable("sid") int sid) {
        return studentScoreService.selectCourses(sid);
    }

    @RequestMapping("/getScoreAbilities/{sid}/{type}")
    @ResponseBody
    public List<Map<String, Object>> getScoreAbilities(@PathVariable("sid") int sid,@PathVariable("type") int type) {
        return studentScoreService.selectAbilities(sid,type);
    }

    /**
     * 修改成绩 (课程与能力)
     * @param cid  课程id
     * @param grade 课程成绩
     * @param sid   学生id
     * @param type  成绩类型 0-转正能力评价  1-第一年工作能力评价 2-第二年工作能力评价 3-第三年工作能力评价 4-课程成绩
     * @return
     */
    @RequestMapping("/updateEmpScore")
    @ResponseBody
    public String updateEmpScore(int cid, double grade, int sid,int type) {
        if (studentScoreService.updateEmpScore(cid, grade, sid,type)) {
            return "success";
        } else {
            return "false";
        }
    }

    /**
     * 根据根据经理工号和成绩类型动态查询表头
     * @param eid  经理工号
     * @param type 成绩类型 0 ：转正   1：第一年  2：第二年  3：第三年
     * @return
     */
    @RequestMapping("/getAllEntity")
    @ResponseBody
    public List<Map<String,Object>> getAllEntity(int eid,int type){
        List<Map<String,Object>> list = new ArrayList<>();
        List<Course> courseList = studentScoreService.getAllEntity(eid,type);
        for (Course course : courseList){
            String cid = Integer.toString(course.getCid());
            Map<String,Object> map = new HashMap<>();
            map.put("cid",cid);
            map.put("cname",course.getCname());
            list.add(map);
    }
        return list;
    }


    @RequestMapping("/updateStudentScore")
    @ResponseBody
    public String updateStudentScore(StudentScore studentScore){
        boolean flag = studentScoreService.updateById(studentScore);
        if (flag){
            return "success";
        } else {
            return "error";
        }
    }

    @RequestMapping(value = "getCourseWithScore", produces = "application/json;charset=utf-8")
    @ResponseBody
    public IPage<Map<String,Object>> getCourseWithScore(int current, int size, String snamelike, int tid){
        List<Map<String,Object>> courseWithScore_page = studentService.getScoreWithCourse(current,size,snamelike,tid);
        List<Map<String,Object>> courseWithScore = studentService.getAllScoreWithCourse(snamelike, tid);
        IPage<Map<String,Object>> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        page.setTotal(courseWithScore.size());
        page.setRecords(courseWithScore_page);
        return page;
    }

    @RequestMapping(value = "showAbilityScore", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Map<Object,Object>> showAs(@RequestParam("eid") int eid,int type,@RequestParam("ename") String ename){
        List<Map<Object,Object>> courseWithScore = new ArrayList<>();
        List<Emp> empList = empService.selectEmp(eid,ename);
        for (Emp emp: empList){
            Map<Object,Object> map = new HashMap<>();
            List<StudentScore> scoreList = empService.selectScores(type,emp.getEid());
            map.put("eid",emp.getEid());
            map.put("ename",emp.getEname());
            map.put("job",emp.getJob());
            for (StudentScore ss : scoreList){

                map.put(Integer.toString(ss.getCid()),Double.toString(ss.getScore()));
                double sum = 0;
                sum+=ss.getScore();
                double avg = sum/scoreList.size();
                map.put("avg",avg);
            }
            courseWithScore.add(map);
        }
        return courseWithScore;
    }

}
