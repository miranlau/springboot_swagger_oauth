package com.tieto.springbootdemo.service;

import com.tieto.springbootdemo.model.AlarmHistory;

import java.util.List;

public interface AlarmHistoryService {
    void insertAlarmHistory(AlarmHistory alarmHistory) throws Exception;
    List<AlarmHistory> queryAll() throws Exception;
}
