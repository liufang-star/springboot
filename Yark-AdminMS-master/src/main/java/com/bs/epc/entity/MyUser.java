package com.bs.epc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 居民信息实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user_epc")
@ToString
public class MyUser implements Serializable {
    private static final long serialVersionUID=1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String code;

    private String name;

    private String domicile;

    private String documenttype;

    private String documentphone;

    private String phone;

    private String address;

    private String household;

    private String relation;

    private String remark;

    private Integer deptid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date hiredate;

    /**
     * 上级
     */
    private Integer mgr;

    /**
     * 是否可用
     */
    private Integer available;

    /**
     * 排序码
     */
    private Integer ordernum;

    /**
     * 用户类型（0超级管理员，1管理员，2普通用户）
     */
    private Integer type;

    /**
     * 用户头像地址
     */
    private String imgpath;

    /**
     * 盐
     */
    private String salt;

    /**
     * 领导名称
     */
    @TableField(exist = false)
    private String leadername;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptname;

}
