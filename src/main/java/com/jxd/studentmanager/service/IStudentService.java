package com.jxd.studentmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxd.studentmanager.model.Student;

import java.util.List;
import java.util.Map;

/**
 * @ClassName IStudentService
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
public interface IStudentService extends IService<Student>{
    List<Student> getStudentPage(int page, int size, int tid, String sname);
    List<Map<String,Object>> getScoreWithCourse(int page, int size,
                                                String sname, int tid);
    List<Map<String,Object>> getAllScoreWithCourse(String sname, int tid);
    Student getStudentByEid(int eid);

}
