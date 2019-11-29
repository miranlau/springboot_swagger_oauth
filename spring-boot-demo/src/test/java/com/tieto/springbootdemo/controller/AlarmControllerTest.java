package com.tieto.springbootdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.tieto.springbootdemo.AbstractTest;
import com.tieto.springbootdemo.entity.enums.AlarmStatus;
import com.tieto.springbootdemo.entity.enums.Severity;
import com.tieto.springbootdemo.mapper.AlarmMapper;
import com.tieto.springbootdemo.model.Alarm;
import com.tieto.springbootdemo.service.AlarmService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AlarmControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AlarmMapper mockAlarmMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAlarm() {
        String module = "1234567890";
        Alarm alarm = new Alarm(null,"2019-08-01 01:01:01", "a123456", module,
                0, "a123456", "{}", AlarmStatus.RAISED, Severity.INFO);
        List<Alarm> expected = Lists.newArrayList(alarm);

        try {
            String content = objectMapper.writeValueAsString(alarm);
            System.out.println("===content: " + content);
            RequestBuilder builder = MockMvcRequestBuilders.post("/v2/alarms")
                    .content(content).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
            ResultActions result = this.mvc.perform(builder);

            result.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("should not be here");
        }
    }

    @Test
    public void testQueryAlarms() {
        String module = "1234567890";
        Alarm alarm = new Alarm(null,"2019-08-01 01:01:01", "a123456", module,
                0, "a123456", "{}", AlarmStatus.RAISED, Severity.INFO);
        List<Alarm> expected = Lists.newArrayList(alarm);

        try {
            BDDMockito.given(mockAlarmMapper.queryAll()).willReturn(expected);

            RequestBuilder builder = MockMvcRequestBuilders.get("/v2/alarms")
                    .accept(MediaType.APPLICATION_JSON);
            ResultActions result = this.mvc.perform(builder);


            result.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("should not be here");
        }

    }
}
