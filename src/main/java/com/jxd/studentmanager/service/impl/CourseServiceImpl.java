package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.ICourseMapper;
import com.jxd.studentmanager.model.Course;
import com.jxd.studentmanager.service.ICourseService;
import org.springframework.stereotype.Service;

/**
 * @ClassName CourseServiceImpl
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Service
public class CourseServiceImpl extends ServiceImpl<ICourseMapper, Course> implements ICourseService {
}
