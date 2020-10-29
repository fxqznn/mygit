package com.jxd.studentmanager.controller;

import com.jxd.studentmanager.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @ClassName DeptController
 * @Description TODO
 * @Author Zheng NaNa
 * @Date 2020/10/28
 * @Version 1.0
 */
@Controller
public class DeptController {
    @Autowired
    private IDeptService deptService;

    /*@RequestMapping(value = "getAllDept")
    @ResponseBody
    public Map getAllDept(){

    }*/
}
