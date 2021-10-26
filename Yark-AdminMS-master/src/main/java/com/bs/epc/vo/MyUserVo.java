package com.bs.epc.vo;

import com.bs.epc.entity.MyUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MyUserVo extends MyUser {
    private Integer page = 1;
    private Integer limit = 10;

    /**
     * 验证码
     */
    private String vercode;
}