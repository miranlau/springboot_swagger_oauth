package com.tieto.springbootdemo.util;

import com.tieto.springbootdemo.entity.enums.AlarmStatus;
import com.tieto.springbootdemo.model.Alarm;
import com.tieto.springbootdemo.model.AlarmHistory;

public final class AlarmUtil {
    public static AlarmHistory newAlarmHistory(Alarm alarm) {
        AlarmHistory alarmHistory = new AlarmHistory();
        alarmHistory.setAlarmId(alarm.getId());
        alarmHistory.setTs(alarm.getTs());
        alarmHistory.setAction("update to " + alarm.getStatus());
        alarmHistory.setDetails("update the status to " + alarm.getStatus());
        return alarmHistory;
    }
}
