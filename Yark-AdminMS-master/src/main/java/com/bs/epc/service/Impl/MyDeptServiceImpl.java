package com.bs.epc.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.bs.epc.entity.MyDept;
import com.bs.epc.mapper.MyDeptMapper;
import com.bs.epc.service.MyIDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 9216 kB 服务实现类
 * </p>
 *
 */
@Service
@Transactional
public class MyDeptServiceImpl extends ServiceImpl<MyDeptMapper, MyDept> implements MyIDeptService {

    @Override
    public MyDept getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean update(MyDept entity, Wrapper<MyDept> updateWrapper){
        return super.update(entity,updateWrapper);
    }

    @Override
    public boolean updateById(MyDept entity){
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id){
        return super.removeById(id);
    }

    @Override
    public boolean save(MyDept entity) {
        return super.save(entity);
    }
}

