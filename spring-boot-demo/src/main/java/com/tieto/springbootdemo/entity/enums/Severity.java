package com.tieto.springbootdemo.entity.enums;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Severity of Alarm entity")
public enum Severity {
    INFO("INFO"),
    FATAL("FATAL"),
    WARNING("WARNING");

    private final String mValue;

    private Severity(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
