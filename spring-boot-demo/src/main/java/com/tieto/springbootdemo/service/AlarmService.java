package com.tieto.springbootdemo.service;

import com.tieto.springbootdemo.entity.enums.AlarmStatus;
import com.tieto.springbootdemo.model.Alarm;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface AlarmService {
    void insertAlarm(Alarm alarm) throws Exception;
    void updateAlarm(Alarm alarm) throws Exception;
    List<Alarm> queryAlarms() throws Exception;
    int deleteAlarms(int interval, TimeUnit timeUnit) throws Exception;
    int deleteAlarms(AlarmStatus status) throws Exception;
}
