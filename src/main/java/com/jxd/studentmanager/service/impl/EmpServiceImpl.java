package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.IEmpMapper;
import com.jxd.studentmanager.model.Emp;
import com.jxd.studentmanager.service.IEmpService;
import org.springframework.stereotype.Service;

/**
 * @ClassName EmpServiceImpl
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Service
public class EmpServiceImpl extends ServiceImpl<IEmpMapper, Emp> implements IEmpService {
}
