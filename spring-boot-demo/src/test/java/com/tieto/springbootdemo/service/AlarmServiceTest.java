package com.tieto.springbootdemo.service;

import com.google.common.collect.Lists;
import com.tieto.springbootdemo.AbstractTest;
import com.tieto.springbootdemo.entity.enums.AlarmStatus;
import com.tieto.springbootdemo.entity.enums.Severity;
import com.tieto.springbootdemo.mapper.AlarmHistoryMapper;
import com.tieto.springbootdemo.mapper.AlarmMapper;
import com.tieto.springbootdemo.model.Alarm;
import com.tieto.springbootdemo.model.AlarmHistory;
import com.tieto.springbootdemo.util.AlarmUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AlarmServiceTest extends AbstractTest {

    @Autowired
    private AlarmService alarmService;

    @MockBean
    private AlarmMapper mockAlarmMapper;

    @MockBean
    private AlarmHistoryMapper mockAlarmHistoryMapper;

    @Autowired
    private PlatformTransactionManager transactionManager;

    final String module = "1234567890";

    @Test
    public void testQueryAlarms() {
        Alarm alarm = new Alarm(null,"2019-08-01 01:01:01", "a123456", module, 0, "a123456", "{}", AlarmStatus.RAISED, Severity.INFO);
        List<Alarm> expected = Lists.newArrayList(alarm);
        try {
            BDDMockito.given(mockAlarmMapper.queryAll()).willReturn(expected);

            List<Alarm> alarms = alarmService.queryAlarms();

            Assert.assertEquals(1, alarms.size());
            Assert.assertEquals(module, alarms.get(0).getModule());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("should not be here, exception: " + e.getMessage());
        } finally {
            BDDMockito.reset(mockAlarmMapper);
        }
    }

    @Test
    public void testInsertAlarm() {
        try {
            Alarm alarm = new Alarm(null,"2019-08-01 01:01:01", "a123456", module, 0, "a123456", "{}", AlarmStatus.RAISED, Severity.INFO);
            alarmService.insertAlarm(alarm);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("should not be here, exception: " + e.getMessage());
        } finally {
            BDDMockito.reset(mockAlarmMapper);
        }
    }

    @Test
    public void testInsertAlarmException() {
        String errorMessage = "db connect error";
        Alarm alarm = new Alarm(null,"2019-08-01 01:01:01", "a123456", module, 0, "a123456", "{}", AlarmStatus.RAISED, Severity.INFO);
        try {
            BDDMockito.doThrow(new Exception(errorMessage)).when(mockAlarmMapper).insert(alarm);
            try {
                alarmService.insertAlarm(alarm);
            } catch (Exception t) {
                Assert.assertEquals(errorMessage, t.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("should not be here, exception: " + e.getMessage());
        } finally {
            BDDMockito.reset(mockAlarmMapper);
        }
    }

    @Test
    public void testupdateAlarm() {
        Alarm alarm = new Alarm(1L,"2019-08-01 01:01:01", "a123456", module, 0, "a123456", "{}", AlarmStatus.RAISED, Severity.INFO);
        try {
            alarmService.updateAlarm(alarm);

            BDDMockito.verify(mockAlarmMapper, Mockito.times(1)).update(alarm);
            BDDMockito.verify(mockAlarmHistoryMapper, Mockito.times(1)).insert(any(AlarmHistory.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("should not be here, exception: " + e.getMessage());
        } finally {
            Assert.assertFalse(transactionManager.getTransaction(null).isRollbackOnly());

            BDDMockito.reset(mockAlarmMapper);
            BDDMockito.reset(mockAlarmHistoryMapper);
        }
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void testupdateAlarmRollback() {
        String errorMessage = "db connect error";
        Alarm alarm = new Alarm(1L,"2019-08-01 01:01:01", "a123456", module, 0, "a123456", "{}", AlarmStatus.RAISED, Severity.INFO);
        try {
            BDDMockito.doThrow(new Exception(errorMessage)).when(mockAlarmHistoryMapper).insert(any(AlarmHistory.class));

            alarmService.updateAlarm(alarm);

            BDDMockito.verify(mockAlarmMapper, Mockito.times(1)).update(alarm);
            BDDMockito.verify(mockAlarmHistoryMapper, Mockito.times(1)).insert(any(AlarmHistory.class));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Assert.assertTrue(transactionManager.getTransaction(null).isRollbackOnly());

            BDDMockito.reset(mockAlarmMapper);
            BDDMockito.reset(mockAlarmHistoryMapper);
        }
    }

}
