package com.jxd.studentmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName EmpController
 * @Description TODO
 * @Author Zheng NaNa
 * @Date 2020/10/28
 * @Version 1.0
 */
@Controller
public class EmpController {

    @RequestMapping("/getEmpList")
    public String getEmpList(){
        return "";
    }

}
