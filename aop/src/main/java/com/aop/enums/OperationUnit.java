package com.aop.enums;

/**
 * <h3>AspectDemo</h3>
 * <p>被操作的单元</p>
 * @author : 刘芳
 * @date : 2021-10-25
 **/

public enum OperationUnit {

        /**
         * 被操作的单元
         */
        UNKNOWN("unknown"),
        USER("user"),
        EMPLOYEE("employee"),
        Redis("redis");

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        OperationUnit(String value) {
            this.value = value;
        }


}
