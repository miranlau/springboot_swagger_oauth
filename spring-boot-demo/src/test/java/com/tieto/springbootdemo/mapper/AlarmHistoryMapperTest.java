package com.tieto.springbootdemo.mapper;

import com.tieto.springbootdemo.AbstractTest;
import com.tieto.springbootdemo.entity.enums.AlarmStatus;
import com.tieto.springbootdemo.entity.enums.Severity;
import com.tieto.springbootdemo.model.Alarm;
import com.tieto.springbootdemo.model.AlarmHistory;
import com.tieto.springbootdemo.util.AlarmUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AlarmHistoryMapperTest extends AbstractTest {
    @Autowired
    private AlarmHistoryMapper alarmHistoryMapper;

    @Test
    public void testInsert() throws Exception {
        Assert.assertNotNull(alarmHistoryMapper);
        Alarm alarm1 = new Alarm(1l,"2019-08-01 01:01:01", "a123456", "1",
                0, "a123456", "{}", AlarmStatus.RAISED, Severity.INFO);
        Alarm alarm2 = new Alarm(2l,"2019-08-01 01:01:01", "b123456", "2",
                0, "a123456", "{}", AlarmStatus.RAISED, Severity.INFO);
        Alarm alarm3 = new Alarm(3l,"2019-08-01 01:01:01", "b123456", "3",
                0, "a123456", "{}", AlarmStatus.CLEARED, Severity.INFO);

        alarmHistoryMapper.insert(AlarmUtil.newAlarmHistory(alarm1));
        alarmHistoryMapper.insert(AlarmUtil.newAlarmHistory(alarm2));
        alarmHistoryMapper.insert(AlarmUtil.newAlarmHistory(alarm3));

        List<AlarmHistory> alarms = alarmHistoryMapper.queryAll();
        Assert.assertEquals(3, alarms.size());

        Assert.assertEquals(1l, alarms.get(0).getAlarmId().longValue());
        Assert.assertEquals(2l, alarms.get(1).getAlarmId().longValue());
        Assert.assertEquals(3l, alarms.get(2).getAlarmId().longValue());
    }
}
