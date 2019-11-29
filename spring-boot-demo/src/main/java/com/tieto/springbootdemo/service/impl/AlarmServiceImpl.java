package com.tieto.springbootdemo.service.impl;

import com.tieto.springbootdemo.entity.enums.AlarmStatus;
import com.tieto.springbootdemo.mapper.AlarmHistoryMapper;
import com.tieto.springbootdemo.mapper.AlarmMapper;
import com.tieto.springbootdemo.model.Alarm;
import com.tieto.springbootdemo.model.AlarmHistory;
import com.tieto.springbootdemo.service.AlarmService;
import com.tieto.springbootdemo.util.AlarmUtil;
import com.tieto.springbootdemo.util.TimestampUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AlarmServiceImpl implements AlarmService {

    private static final Logger logger = LoggerFactory.getLogger(AlarmServiceImpl.class);

    @Autowired
    private AlarmMapper alarmMapper;

    @Autowired
    private AlarmHistoryMapper alarmHistoryMapper;

    @Override
    public void insertAlarm(Alarm alarm) throws Exception {
        logger.info("insert an alarm: {}", alarm);
        try {
            alarmMapper.insert(alarm);
        } catch(Exception e) {
            logger.error("insert an alarm exception", e);
            throw e;
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAlarm(Alarm alarm) throws Exception {
        logger.info("update an alarm: {}", alarm);
        try {
            alarmMapper.update(alarm);
            if(alarm.getStatus() != null) {
                AlarmHistory alarmHistory = AlarmUtil.newAlarmHistory(alarm);
                alarmHistoryMapper.insert(alarmHistory);
                logger.trace("insert an alarm history: {}", alarmHistory);
            }
        } catch(Exception e) {
            logger.error("update an alarm exception", e);
            throw e;
        }
    }

    @Override
    public List<Alarm> queryAlarms() throws Exception {
        logger.info("query all alarms");
        try {
            return alarmMapper.queryAll();
        } catch (Exception e) {
            logger.error("query all alarms exception", e);
            throw e;
        }
    }

    @Override
    public int deleteAlarms(int interval, TimeUnit timeUnit) throws Exception {
        logger.info("delete alarms by interval: {} and timeUnit: {}", interval, timeUnit);
        try {
            Long l = System.currentTimeMillis() - timeUnit.toMillis(interval);
            String condition = TimestampUtil.fromTime(l);
            return alarmMapper.deleteAlarms(condition);
        } catch (Exception e) {
            logger.error("delete alarms exception", e);
            throw e;
        }
    }

    @Override
    public int deleteAlarms(AlarmStatus status) throws Exception {
        logger.info("delete alarms by status: {}", status);
        try {
            return alarmMapper.deleteAlarmsByStatus(status.toString());
        } catch (Exception e) {
            logger.error("delete alarms exception", e);
            throw e;
        }
    }
}
