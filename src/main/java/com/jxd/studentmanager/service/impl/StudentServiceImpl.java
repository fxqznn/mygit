package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.IStudentMapper;
import com.jxd.studentmanager.model.Student;
import com.jxd.studentmanager.service.IStudentService;
import org.springframework.stereotype.Service;

/**
 * @ClassName StudentServiceImpl
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Service
public class StudentServiceImpl extends ServiceImpl<IStudentMapper, Student> implements IStudentService {
}
