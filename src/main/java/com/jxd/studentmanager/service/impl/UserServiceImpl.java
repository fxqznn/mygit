package com.jxd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxd.studentmanager.mapper.IUserMapper;
import com.jxd.studentmanager.model.User;
import com.jxd.studentmanager.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, User> implements IUserService {
}
