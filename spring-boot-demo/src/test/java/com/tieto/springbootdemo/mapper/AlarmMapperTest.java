package com.tieto.springbootdemo.mapper;

import com.tieto.springbootdemo.AbstractTest;
import com.tieto.springbootdemo.SpringBootDemoApplication;
import com.tieto.springbootdemo.entity.enums.AlarmStatus;
import com.tieto.springbootdemo.entity.enums.Severity;
import com.tieto.springbootdemo.model.Alarm;
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
public class AlarmMapperTest extends AbstractTest {
    @Autowired
    private AlarmMapper alarmMapper;

    @Test
    public void testInsert() throws Exception {
        Assert.assertNotNull(alarmMapper);

        alarmMapper.insert(new Alarm(null,"2019-08-01 01:01:01", "a123456", "1", 0, "a123456", "{}", AlarmStatus.RAISED, Severity.INFO));
        alarmMapper.insert(new Alarm(null,"2019-08-01 01:01:01", "b123456", "2", 0, "a123456", "{}", AlarmStatus.RAISED, Severity.INFO));
        alarmMapper.insert(new Alarm(null,"2019-08-01 01:01:01", "b123456", "3", 0, "a123456", "{}", AlarmStatus.CLEARED, Severity.INFO));

        List<Alarm> alarms = alarmMapper.queryAll();
        Assert.assertEquals(3, alarms.size());

        Assert.assertEquals("1", alarms.get(0).getModule());
        Assert.assertEquals("2", alarms.get(1).getModule());
        Assert.assertEquals("3", alarms.get(2).getModule());
    }

}
