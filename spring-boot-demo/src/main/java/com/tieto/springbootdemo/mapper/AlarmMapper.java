package com.tieto.springbootdemo.mapper;

import com.tieto.springbootdemo.entity.enums.AlarmStatus;
import com.tieto.springbootdemo.model.Alarm;
import org.mapstruct.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Mapper
public interface AlarmMapper {
    void insert(Alarm alarm) throws Exception;
    void update(Alarm alarm) throws Exception;
    List<Alarm> queryAll() throws Exception;

    /**
     * delete alarms that creation time is earlier than the input
     * @param upto
     * @return
     * @throws Exception
     */
    int deleteAlarms(String upto) throws Exception;
    int deleteAlarmsByStatus(String status) throws Exception;
    List<String> getAlarmModules() throws Exception;
    List<Alarm> getModulesWithActiveAlarms() throws Exception;
    Long countActiveAlarms(String searchName, String moduleName);
    List<Alarm> getActiveAlarms(String searchName, String moduleName, int perPage, int position) throws Exception;
    Long countAlarms(String searchName, String moduleName);
    List<Alarm> getAlarms(String searchName, String moduleName, int perPage, int position) throws Exception;



}
