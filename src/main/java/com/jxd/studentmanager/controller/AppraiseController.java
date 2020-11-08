package com.jxd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jxd.studentmanager.model.Appraise;
import com.jxd.studentmanager.service.IAppraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AppraiseController
 * @Description TODO
 * @Author Zheng NaNa
 * @Date 2020/10/30
 * @Version 1.0
 */
@Controller
public class AppraiseController {
    @Autowired
    private IAppraiseService appraiseService;

    @RequestMapping("/getAppraise/{sid}/{type}")
    @ResponseBody
    public Appraise getAppraise(@PathVariable("sid") int sid, @PathVariable("type") int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("sid", sid);
        map.put("type", type);
        QueryWrapper<Appraise> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(map, true);
        return appraiseService.getOne(queryWrapper);
    }

    @RequestMapping("/addApp")
    @ResponseBody
    public int addApp(@RequestParam("eid") int eid, @RequestParam("type") int type){
        return appraiseService.addAppraise(eid, type);
    }

    @RequestMapping("/getApp")
    @ResponseBody
    public String getApp(@RequestParam("eid") int eid, @RequestParam("type") int type){
        return appraiseService.getAppraise(eid,type);
    }

    @RequestMapping("/updateApp")
    @ResponseBody
    public int updateApp(@RequestParam("content")String content,@RequestParam("sid")int sid,
                         @RequestParam("type")int type){
        return appraiseService.updateApp(content, sid, type);
    }
}
