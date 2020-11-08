package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.IDeptCourseMapper;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.model.DeptCourse;
import com.jxd.studentmanager.service.IDeptCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName DeptCourseImpl
 * @Description TODO
 * @Author WangKai
 * @Date 2020/10/30
 * @Version 1.0
 */
@Service
public class DeptCourseServiceImpl extends ServiceImpl<IDeptCourseMapper, DeptCourse> implements IDeptCourseService {

    @Autowired
    private IDeptCourseMapper idcm;

    @Override
    public List<Map> getNotSelectedAbility(int eid) {
        return idcm.getNotSelectedAbility(eid);
    }

    @Override
    public int addAbility(int eid, int cid) {
        return idcm.addAbility(eid,cid);
    }

    @Override
    public int delDeptCourse(int eid, int cid) {
        return idcm.delDeptCourse(eid, cid);
    }

}
