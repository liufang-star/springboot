package com.aop.enums;


/**
 * <h3>AspectDemo</h3>
 * <p>操作类型</p>
 * @author : 刘芳
 * @date : 2021-10-25
 **/
public enum OperationType {

        /**
         * 操作类型
         */
        UNKNOWN("unknown"),
        DELETE("delete"),
        SELECT("select"),
        UPDATE("update"),
        INSERT("insert");

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        OperationType(String s) {
            this.value = s;
        }

}
