package com.tieto.springbootdemo.mapper;

import com.tieto.springbootdemo.model.Alarm;
import com.tieto.springbootdemo.model.AlarmHistory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AlarmHistoryMapper {
    void insert(AlarmHistory alarmHistory) throws Exception;
    void update(AlarmHistory alarmHistory) throws Exception;
    List<AlarmHistory> queryByAlarmId(Long alarmId) throws Exception;
    List<AlarmHistory> queryAll() throws Exception;

}
