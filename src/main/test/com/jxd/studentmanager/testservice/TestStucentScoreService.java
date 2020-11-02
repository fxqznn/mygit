package com.jxd.studentmanager.testservice;


import com.jxd.studentmanager.StudentManagerApplication;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.service.IStudentScoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.spi.LocaleServiceProvider;

/**
 * @ClassName TestStucentScoreService
 * @Description TODO
 * @Author jinkaiyan
 * @Date 2020/10/28
 * @Version 1.0
 */
@RunWith(SpringRunner.class)//用谁作为启动器去加载所有的底层配置
@SpringBootTest(classes = StudentManagerApplication.class)
public class TestStucentScoreService {
    @Autowired
    private IStudentScoreService scoreService;

    @Test
    public void selectCourses() {
        List<Map<String, Object>> list = scoreService.selectCourses(1);
        System.out.println(list.toString());
    }

    @Test
    public void selectAbilities() {
        List<Map<String, Object>> list = scoreService.selectAbilities(1, 0);
        System.out.println(list.toString());
    }

    @Test
    public void updateScores() {
        boolean flag = scoreService.updateEmpScore(1, 4.0, 1);
        System.out.println("修改结果是：" + flag);
    }

    @Test
    public void getAllEntity(){
        List<Course> list = scoreService.getAllEntity(1,0);
        System.out.println(list.size());
    }

}
