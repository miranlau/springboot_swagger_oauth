package com.tieto.springbootdemo.controller;

import com.google.common.collect.Lists;
import com.tieto.springbootdemo.entity.vo.BaseResult;
import com.tieto.springbootdemo.model.AlarmHistory;
import com.tieto.springbootdemo.service.AlarmHistoryService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v2/alarms/history")
@Api(value = "Alarm History Rest API", tags = "AlarmHistory", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AlarmHistoryController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AlarmHistoryService alarmHistoryService;

    @ApiOperation(value = "Query all alarm operation history", notes = "Query all alarm operation history conditionally",
            authorizations = {
                    @Authorization(value = "oauth2schema", scopes = {
                            @AuthorizationScope(scope = "query", description = "query alarm history"),
                            @AuthorizationScope(scope = "trust", description = "query history")
                    })
            }
    )
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    BaseResult queryAllAlarmHistory() {
        logger.info("Query all alarm operation history");
        try {
            List<AlarmHistory> alarmHistoryList = alarmHistoryService.queryAll();
            return BaseResult.successWithData(alarmHistoryList);
        } catch (Exception e) {
            logger.error("Query all alarm history exception", e);
            return BaseResult.failWithCodeAndMsg(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @ApiOperation(value = "Delete alarm operation history", notes = "Delete alarm operation history conditionally",
            authorizations = {
                    @Authorization(value = "oauth2schema", scopes = {
                            @AuthorizationScope(scope = "delete", description = "delete alarm history"),
                            @AuthorizationScope(scope = "trust", description = "delete alarm history")
                    })
            }
    )
    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity deleteAlarmHistory(String module, String uptoTimestamp) {
        logger.info("Delete alarm operation history by module:{}, uptoTimestamp:{}", module, uptoTimestamp);
        int deletedNum = 1;
        return ResponseEntity.status(HttpStatus.CREATED).body(deletedNum);
    }
}
