package com.jxd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jxd.studentmanager.model.Emp;
import com.jxd.studentmanager.service.IEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName EmpController
 * @Description TODO
 * @Author Zheng NaNa
 * @Date 2020/10/28
 * @Version 1.0
 */
@Controller
public class EmpController {

    @Autowired
    private IEmpService empService;

    @RequestMapping(value = "getallemp" , produces = "text/html;charset=utf-8")
    @ResponseBody
    public Map<String,Object> getAllEmp(IPage<Emp> page, String ename){
        Map<String,Object> map = new HashMap<>();

        List<Emp> allList = empService.list();
        int empCount = allList.size();
        map.put("count",empCount);

        if(ename == null || ename == ""){
            IPage<Emp> list = empService.page(page);
            map.put("list",list);
        } else {
            QueryWrapper<Emp> wrapper =  new QueryWrapper<>();
            wrapper.like("ename",ename);
            IPage<Emp> list = empService.page(page,wrapper);
            map.put("list",list);
        }
        return map;
    }

}
