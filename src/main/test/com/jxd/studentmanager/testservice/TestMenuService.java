package com.jxd.studentmanager.testservice;

import com.jxd.studentmanager.StudentManagerApplication;
import com.jxd.studentmanager.service.IMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TestMenuService
 * @Description TODO
 * @Author jinkaiyan
 * @Date 2020/10/30
 * @Version 1.0
 */
@RunWith(SpringRunner.class)//用谁作为启动器去加载所有的底层配置
@SpringBootTest(classes = StudentManagerApplication.class)
public class TestMenuService {
    @Autowired
    private IMenuService menuService;

    @Test
    public void queryAllMenu() {
        List<Map<String, Object>> list = menuService.queryMenuInfo(3);
        System.out.println(list.toString());
    }
}
