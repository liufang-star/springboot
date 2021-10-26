package com.bs.sys.vo;

import lombok.Data;

/**
 * 和excel表一一映射
 */
@Data
public class UsersExcelVo {
//    //行政区划码
//    private String adcode;
//
//    //姓名
//    private String name;
//
//    //户籍所在地
//    private String domicile;
//
//    //证件类型
//    private String documenttype;
//
//    //证件号码
//    private String documentphone;
//
//    //手机号码
//    private String phone;
//
//    //常住地址
//    private String address;
//
//    //户主身份证号码
//    private String household;
//
//    //与户主关系
//    private String relation;
//
//    //备注
//    private String remark;
//
//    private String loginname;
//
//    //直属上级
//    private String mgrname;
//
//    //所属区域
//    private String deptname;


//    //角色
//    private String rolename;
    //登陆名称
    private String name;
    //用户名
    private String loginname;
    //所属部门
    private String deptname;
    //直属领导
    private String mgrname;
    //用户备注
    private String remark;
    //用户地址
    private String address;
    //角色
    private String rolename;
}
