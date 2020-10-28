package com.jxd.studentmanager.testmapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jxd.studentmanager.StudentManagerApplication;
import com.jxd.studentmanager.mapper.IStudentMapper;
import com.jxd.studentmanager.model.User;
import com.jxd.studentmanager.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
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
    public void main() {
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

}
