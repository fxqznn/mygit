package com.jxd.studentmanager.testmapper;

import com.jxd.studentmanager.StudentManagerApplication;
import com.jxd.studentmanager.mapper.IStudentScoreMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestStudentScoreMapper
 * @Description TODO
 * @Author jinkaiyan
 * @Date 2020/10/28
 * @Version 1.0
 */
@RunWith(SpringRunner.class)//用谁作为启动器去加载所有的底层配置
@SpringBootTest(classes = StudentManagerApplication.class)
public class TestStudentScoreMapper {
    @Autowired
    private IStudentScoreMapper scoreMapper;

    @Test
    public void selectCourses() {
        List<Map<String, Object>> list = scoreMapper.selectCourses(1);
        System.out.println(list.toString());
    }

    @Test
    public void selectAbilities() {
        List<Map<String, Object>> list = scoreMapper.selectAbilities(1, 0);
        System.out.println(list.toString());
    }

    @Test
    public void selectCoursesScore(){
    }
}
