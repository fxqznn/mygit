package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.ITermMapper;
import com.jxd.studentmanager.model.Term;
import com.jxd.studentmanager.service.ITermService;
import org.springframework.stereotype.Service;

/**
 * @ClassName TermServiceImpl
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Service
public class TermServiceImpl extends ServiceImpl<ITermMapper, Term> implements ITermService {
}
