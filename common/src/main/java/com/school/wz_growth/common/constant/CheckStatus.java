package com.school.wz_growth.common.constant;

/**
 * Created by zenglingzhuo on 16/6/8.
 */
public enum CheckStatus {

        不可用(0),可用(1), ;
        private final int value;
        CheckStatus(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
}
