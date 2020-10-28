package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.IDeptMapper;
import com.jxd.studentmanager.model.Dept;
import com.jxd.studentmanager.service.IDeptService;
import org.springframework.stereotype.Service;

/**
 * @ClassName DeptServiceImpl
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Service
public class DeptServiceImpl extends ServiceImpl<IDeptMapper, Dept> implements IDeptService {
}
