package com.tieto.springbootdemo.entity.enums;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Status of Alarm entity")
public enum AlarmStatus {
    RAISED("RAISED"),
    CLEARED("CLEARED"),
    MANUAL_CLEARED("MANUALLY CLEARED");

    private final String mValue;

    private AlarmStatus(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }

}
