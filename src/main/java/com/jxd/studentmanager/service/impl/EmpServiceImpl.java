package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.IEmpMapper;
import com.jxd.studentmanager.model.Emp;
import com.jxd.studentmanager.service.IEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName EmpServiceImpl
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Service
public class EmpServiceImpl extends ServiceImpl<IEmpMapper, Emp> implements IEmpService {

    @Autowired
    private IEmpMapper iem;

    @Override
    public List<Map<String, Object>> selectEmp(int eid) {
        return iem.selectEmp(eid);
    }

    @Override
    public List<Map<String, Object>> selectScores(int type, int eid) {
        return iem.selectScores(type, eid);
    }
}
