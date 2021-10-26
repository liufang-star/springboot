package com.bs.epc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.epc.entity.MyDept;
import com.bs.epc.entity.MyUser;

import java.util.List;

public interface MyIUserService extends IService<MyUser> {
    /**
     * 查询当前用户是否是其他用户的直属领导
     * @param
     * @return
     */
    Boolean queryMgrByUserId(Integer myUserId);

    List<MyUser> list(QueryWrapper<MyDept> queryWrapperU);
}
