package com.aop.model;


import lombok.Data;

import java.util.Date;

/**
 * <h3>AspectDemo</h3>
 * <p>日志记录对象</p>
 * @author : 刘芳
 * @date : 2021-10-25
 **/

@Data
public class OperationLog {

        private String id;
        private Date createTime;
        /**
         * 日志等级
         */
        private Integer level;
        /**
         * 被操作的对象
         */
        private String operationUnit;
        /**
         * 方法名
         */
        private String method;
        /**
         * 参数
         */
        private String args;
        /**
         * 操作人id
         */
        private String userId;
        /**
         * 操作人
         */
        private String userName;
        /**
         * 日志描述
         */
        private String describe;
        /**
         * 操作类型
         */
        private String operationType;
        /**
         * 方法运行时间
         */
        private Long runTime;
        /**
         * 方法返回值
         */
        private String returnValue;

        @Override
        public String toString() {
            return "OperationLog{" +
                    "id='" + id + '\'' +
                    ", createTime=" + createTime +
                    ", level=" + level +
                    ", operationUnit='" + operationUnit + '\'' +
                    ", method='" + method + '\'' +
                    ", args='" + args + '\'' +
                    ", userId='" + userId + '\'' +
                    ", userName='" + userName + '\'' +
                    ", describe='" + describe + '\'' +
                    ", operationType='" + operationType + '\'' +
                    ", runTime=" + runTime +
                    ", returnValue='" + returnValue + '\'' +
                    '}';
        }


}
