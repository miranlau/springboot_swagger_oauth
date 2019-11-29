package com.tieto.springbootdemo.service.impl;

import com.tieto.springbootdemo.mapper.AlarmHistoryMapper;
import com.tieto.springbootdemo.model.AlarmHistory;
import com.tieto.springbootdemo.service.AlarmHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmHistoryServiceImpl implements AlarmHistoryService {
    private static final Logger logger = LoggerFactory.getLogger(AlarmHistoryServiceImpl.class);

    @Autowired
    private AlarmHistoryMapper alarmHistoryMapper;

    @Override
    public void insertAlarmHistory(AlarmHistory alarmHistory) throws Exception {
        logger.info("insert alarm history: {}" + alarmHistory);
        try {
            alarmHistoryMapper.insert(alarmHistory);
        } catch (Exception e) {
            logger.error("insert an alarm history exception", e);
            throw e;
        }
    }

    @Override
    public List<AlarmHistory> queryAll() throws Exception {
        logger.info("query all alarm history");
        try {
            return alarmHistoryMapper.queryAll();
        } catch (Exception e) {
            logger.error("query all alarm history exception", e);
            throw e;
        }
    }
}
