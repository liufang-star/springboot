package com.bs.epc.vo;

import lombok.Data;

/**
 * 和Excel表中的数据一一对应
 */
@Data
public class MyUserExcelVo {
    //行政区划码
    private String code;

    //姓名
    private String name;

    //户籍所在地
    private String domicile;

    //证件类型
    private String documenttype;

    //证件号码
    private String documentphone;

    //手机号码
    private String phone;

    //常住地址
    private String address;

    //户主身份证号码
    private String household;

    //与户主关系
    private String relation;

    //备注
    private String remark;

}
