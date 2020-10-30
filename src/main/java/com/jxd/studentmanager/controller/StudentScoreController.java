package com.jxd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jxd.studentmanager.mapper.IStudentScoreMapper;
import com.jxd.studentmanager.model.*;
import com.jxd.studentmanager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @RequestMapping("/getScoreCourses")
    @ResponseBody
    public List<Map<String, Object>> getScoreCourses(int sid) {
        return studentScoreService.selectCourses(sid);
    }

    @RequestMapping("/getScoreAbilities")
    @ResponseBody
    public List<Map<String, Object>> getScoreAbilities(int sid, int type) {
        return studentScoreService.selectAbilities(sid,type);
    }

    @RequestMapping("/updateEmpScore")
    @ResponseBody
    public String updateEmpScore(int cid, double grade, int sid) {
        if (studentScoreService.updateEmpScore(cid, grade, sid)) {
            return "success";
        } else {
            return "false";
        }
    }

    /**
     * 根据根据经理工号和成绩类型查询该员工的成绩
     * @param eid  经理工号
     * @param type
     * @return
     */
    @RequestMapping("/getAllEntity")
    @ResponseBody
    public List<Map<String,Object>> getAllEntity(int eid,int type){
        return studentScoreService.getAllEntity(eid,type);
    }

}
