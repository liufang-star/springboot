package com.bs.epc.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.epc.entity.MyDept;
import com.bs.epc.entity.MyUser;
import com.bs.epc.mapper.MyUserMapper;
import com.bs.epc.service.MyIUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MyUserServiceImpl extends ServiceImpl<MyUserMapper, MyUser> implements MyIUserService {
    @Override
    public Boolean queryMgrByUserId(Integer userId) {
        return null;
    }

    @Override
    public List<MyUser> list(QueryWrapper<MyDept> queryWrapperU) {
        return null;
    }
//    @Autowired
//    private MyUserMapper myUserMapper;


}
